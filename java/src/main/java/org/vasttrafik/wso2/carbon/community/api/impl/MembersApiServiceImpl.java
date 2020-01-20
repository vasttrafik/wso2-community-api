package org.vasttrafik.wso2.carbon.community.api.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.beans.Error;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Forum;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.MemberRanking;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.Topic;
import org.vasttrafik.wso2.carbon.community.api.beans.Watch;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.ForumConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberRankingConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.TopicConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.WatchConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.ForumDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberRankingDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.TopicDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.WatchDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.ForumDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberRankingDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;
import org.vasttrafik.wso2.carbon.community.api.model.TopicDTO;
import org.vasttrafik.wso2.carbon.community.api.model.WatchDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class MembersApiServiceImpl extends CommunityApiServiceImpl {

	/**
	 * Member converter
	 */
	private MemberConverter memberConverter = new MemberConverter();

	private static final Log log = LogFactory.getLog(MembersApiServiceImpl.class);

	/**
	 * Auto-complete method to search for members
	 * 
	 * @param member
	 *            A query string that will be matched against email and
	 *            signature
	 * @param offset
	 *            If paging is required, the offset into the result set
	 * @param limit
	 *            If paging is required, the number of rows to return
	 * @return A list of members matching the query string
	 * @throws ServerErrorException
	 */
	public Response getMembers(String member, Integer offset, Integer limit) throws ServerErrorException {
		try {
			// Get the DAO implementation
			MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
			// Perform the search
			List<MemberDTO> memberDTOs = memberDAO.findByEmailOrSignature(member, offset, limit);
			// Convert the result
			List<Member> members = memberConverter.convertPublic(memberDTOs);
			// Return the result
			return Response.status(200).entity(members).build();
		} catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

	/**
	 * Creates a member profile
	 * 
	 * @param authorization
	 * @param member
	 *            The member to create
	 * @return The newly created member profile, with the id of the profile
	 * @throws BadRequestException
	 * @throws NotAuthorizedException
	 * @throws ServerErrorException
	 */
	public Response createMember(String authorization, Member member) throws ServerErrorException {
		// TO-DO: Create the folders of the member
		// Do it in SQL?
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			// Get the username of the authenticated user
			String authenticatedUser = user.getUserName();

			// Make sure the usernames match
			if (!authenticatedUser.equalsIgnoreCase(member.getUserName()))
				throw new NotAuthorizedException("Usernames do not match");

			// Check for valid signature
			if (!isAdmin() && !validSignature(member.getSignature()))
				return responseUtils.badRequest(1207L, null);

			// Convert the category
			MemberDTO memberDTO = memberConverter.convert(member);
			// Get the DAO implementation
			MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
			// Perform the insert
			memberDAO.setAutoCommit(false);

			try {
				// Write the changes to database
				memberDAO.insert(memberDTO);
			} catch (Exception e) {
				memberDAO.rollbackTransaction(true);
				throw e;
			}

			// Add a Member ranking as well with the created member id
			MemberRankingDTO memberRankingDTO = new MemberRankingDTO();
			memberRankingDTO.setMemberId(user.getUserId());
			memberRankingDTO.setRankingId(1); // Default ranking id
			memberRankingDTO.setCurrentScore(1);

			MemberRankingDAO memberRankingDAO = DAOProvider.getDAO(MemberRankingDAO.class);
			memberRankingDAO.setAutoCommit(false);
			// Assign the same connection to this DAO
			memberRankingDAO.setConnection(memberDAO.getConnection());

			try {
				// Perform insert of member ranking
				memberRankingDAO.insert(memberRankingDTO);
			} catch (Exception e) {
				memberDAO.rollbackTransaction(true);
				throw e;
			}

			memberDAO.commitTransaction(true);

			if (member.getUseGravatar() != null && member.getUseGravatar() && member.getGravatarEmail() != null
					&& member.getGravatarEmail().length() > 0) {
				member.setGravatarEmailHash(memberConverter.getMD5Hash(member.getGravatarEmail()));
			}

			// Return result
			return Response.status(201).entity(member).build();
		} catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		} catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

	/**
	 * Retrieves a member profile. If a profile is not found, returns an empty
	 * one
	 * 
	 * @param authorization
	 * @param id
	 *            The id of the member profile
	 * @return The profile of the member, or a default empty one if one could
	 *         not be found
	 * @throws ServerErrorException
	 */
	public Response getMember(String authorization, Integer memberId) throws ServerErrorException {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);

			// Member info can only be obtained by an admin or the member
			if (!isAdmin() && !isOwnerOrAdmin(memberId))
				return responseUtils.notAuthorizedError(1104L, null);

			Member member = null;
			// Get the DAO implementation
			MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
			// Lookup the member
			MemberDTO memberDTO = memberDAO.find(memberId);

			if (memberDTO != null) {
				// Convert to bean
				member = memberConverter.convert(memberDTO);
				// Get the DAO implementation
				MemberRankingDAO rankingDAO = DAOProvider.getDAO(MemberRankingDAO.class);
				// Get rankings List<MemberRanking>
				List<MemberRankingDTO> rankingDTOs = rankingDAO.findByMember(member.getId());
				// Convert to bean
				MemberRankingConverter converter = new MemberRankingConverter();
				List<MemberRanking> rankings = converter.convert(rankingDTOs);
				// Assign the value
				member.setRankings(rankings);
			} else // Create a default profile
				member = Member.getDefault();

			// Return result
			return Response.status(200).entity(member).build();

		} catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		} catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Deletes a member profile. (Actually renames member to remove all linkable traces to specific user).
	 * 
	 * @param authorization
	 * @param id
	 *            The id of the member profile
	 * @return OK response
	 * @throws ServerErrorException
	 */
	public Response deleteMember(String authorization, Integer memberId) throws ServerErrorException {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);

			// Member info can only be obtained by an admin or the member
			if (!isAdmin() && !isOwnerOrAdmin(memberId))
				return responseUtils.notAuthorizedError(1104L, null);

			// Get the DAO implementation
			MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
			// Lookup the member
			MemberDTO memberDTO = memberDAO.find(memberId);

			if (memberDTO != null) {
				
				memberDTO.setUserName("Deleteduser" + memberDTO.getId());
				memberDTO.setEmail("deleted@user.com");
				memberDTO.setSignature("Borttagen användare");
				memberDTO.setGravatarEmail(null);
				memberDTO.setUseGravatar(false);
				memberDTO.setStatus("deleted");
				
				Integer count = memberDAO.update(memberDTO);

				// Check result
				if (count == 0) // = Not found
					return responseUtils.notFound(1002L, null);
				
			} else {
				return responseUtils.notFound(1002L, null);
			}

			// Return result
			return Response.status(200).build();

		} catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		} catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

	/**
	 * Updates a member profile
	 * 
	 * @param authorization
	 * @param id
	 *            The id of the member profile
	 * @param member
	 *            The member profile to update
	 * @return
	 * @throws BadRequestException
	 * @throws NotAuthorizedException
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response updateMember(String authorization, Integer memberId, Member member) throws ServerErrorException {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);

			// Topic can only be updated by an admin or the creator of the topic
			if (!isAdmin() && !isOwnerOrAdmin(memberId))
				return responseUtils.notAuthorizedError(1104L, null);

			if (!isAdmin() && !validSignature(member.getSignature()))
				return responseUtils.badRequest(1207L, null);

			// Convert the member
			MemberDTO memberDTO = memberConverter.convert(member);
			// Get the DAO implementation
			MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
			// Perform the insert
			Integer count = memberDAO.update(memberDTO);

			// Check result
			if (count == 0) // = Not found
				return responseUtils.notFound(1002L, null);
			else {

				// Add Gravatar MD5 Hash if needed
				if (member.getUseGravatar() != null && member.getUseGravatar() && member.getGravatarEmail() != null
						&& member.getGravatarEmail().length() > 0)
					member.setGravatarEmailHash(memberConverter.getMD5Hash(member.getGravatarEmail()));

				// Return result
				return Response.status(200).entity(member).build();

			}

		} catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		} catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

	// Iteration 3
	public Response getFolders(String authorization, Integer memberId)
			throws NotAuthorizedException, NotFoundException, ServerErrorException {
		return null;
	}

	/**
	 * Retrieves a list of member watches (both forum and topic watches).
	 * 
	 * @param authorization
	 * @param memberId
	 *            The member profile id
	 * @param offset
	 *            If paging is required, the offset into the result list
	 * @param limit
	 *            If paging is required, the number of rows to return from the
	 *            result list
	 * @return
	 * @throws ServerErrorException
	 *             If error occurs
	 */
	public Response getWatches(String authorization, Integer memberId, boolean setInfo, Integer offset, Integer limit)
			throws ServerErrorException {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);

			// Forums can only be deleted by an admin
			if (!isAdmin() && !this.isOwnerOrAdmin(memberId))
				return responseUtils.notAuthorizedError(1104L, null);

			// Get DAO
			WatchDAO watchDAO = DAOProvider.getDAO(WatchDAO.class);
			// Get the watches
			List<WatchDTO> watchesDTO = watchDAO.findByMember(memberId, offset, limit);
			// Convert the result
			List<Watch> watches = new WatchConverter().convertToBeans(watchesDTO);

			if (setInfo) {

				// Get the DAO implementation
				ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
				TopicDAO topicDAO = DAOProvider.getDAO(TopicDAO.class);
				MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
				PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);

				TopicConverter topicConverter = new TopicConverter();
				ForumConverter forumConverter = new ForumConverter();
				MemberRankingConverter converter = new MemberRankingConverter();

				// When a topic or forum is deleted, the watches are still active, but not visible when looking up info
				List<Watch> removeWatches = new ArrayList<Watch>();
				
				for (Watch watch : watches) {

					if (watch.getForumId() != null) {
						ForumDTO forumDTO = forumDAO.find(watch.getForumId());

						// Check for not found, throw error
						if (forumDTO == null) {
							log.warn("Watch with id: " + watch.getId() + " has a Forum with id: " + watch.getForumId() + " found in watch, but not found when looking it up");
							removeWatches.add(watch); // Forum deleted
						} else {
							// Convert to bean
							Forum forum = forumConverter.convert(forumDTO);

							if (forum.getLastPost() != null) {
								// Get the last post from id
								PostDTO postDTO = postDAO.find(forum.getLastPost().getId());
								// Convert
								Post post = new PostConverter().convert(postDTO);
								// Get the member that created the post
								MemberDTO memberDTO = memberDAO.find(post.getCreatedBy().getId());
								// Convert
								Member member = new MemberConverter().convert(memberDTO);

								// Get the DAO implementation
								MemberRankingDAO rankingDAO = DAOProvider.getDAO(MemberRankingDAO.class);
								// Get rankings List<MemberRanking>
								List<MemberRankingDTO> rankingDTOs = rankingDAO.findByMember(member.getId());
								// Convert to bean
								List<MemberRanking> rankings = converter.convert(rankingDTOs);
								// Assign the value
								member.setRankings(rankings);

								// Assign the member to the post
								post.setCreatedBy(member);
								// Assign the post to the forum
								forum.setLastPost(post);
							}

							watch.setForum(forum);
						}
					} else if (watch.getTopicId() != null) {
						TopicDTO topicDTO = topicDAO.find(watch.getTopicId());

						// Check for not found, throw error
						if (topicDTO == null) {
							log.warn("Watch with id: " + watch.getId() + " has a Topic with id: " + watch.getTopicId() + " found in watch, but not found when looking it up");
							removeWatches.add(watch); // Topic deleted
						} else {
							// Convert to bean
							Topic topic = topicConverter.convert(topicDTO);

							if (topic.getCreatedBy() != null) {
								// Get the member that created the topic
								MemberDTO memberDTO = memberDAO.find(topic.getCreatedBy().getId());
								// Convert
								Member member = new MemberConverter().convertPublic(memberDTO);

								// Get the DAO implementation
								MemberRankingDAO rankingDAO = DAOProvider.getDAO(MemberRankingDAO.class);
								// Get rankings List<MemberRanking>
								List<MemberRankingDTO> rankingDTOs = rankingDAO.findByMember(member.getId());
								// Convert to bean
								List<MemberRanking> rankings = converter.convert(rankingDTOs);
								// Assign the value
								member.setRankings(rankings);

								// Assign the member to the post
								topic.setCreatedBy(member);
							}

							if (topic.getLastPost() != null) {
								// Get the last post from id
								PostDTO postDTO = postDAO.find(topic.getLastPost().getId());
								// Convert
								Post post = new PostConverter().convert(postDTO);
								// Get the member that created the post
								MemberDTO memberDTO = memberDAO.find(post.getCreatedBy().getId());
								// Convert
								Member member = new MemberConverter().convert(memberDTO);

								// Get the DAO implementation
								MemberRankingDAO rankingDAO = DAOProvider.getDAO(MemberRankingDAO.class);
								// Get rankings List<MemberRanking>
								List<MemberRankingDTO> rankingDTOs = rankingDAO.findByMember(member.getId());
								// Convert to bean
								List<MemberRanking> rankings = converter.convert(rankingDTOs);
								// Assign the value
								member.setRankings(rankings);

								// Assign the member to the post
								post.setCreatedBy(member);
								// Assign the post to the forum
								topic.setLastPost(post);

							}

							if (topic.getFirstPost() != null) {
								PostDTO postDTO = postDAO.find(topic.getFirstPost().getId());
								// Convert
								Post post = new PostConverter().convert(postDTO);
								// Get the member that created the post
								MemberDTO memberDTO = memberDAO.find(post.getCreatedBy().getId());
								// Convert
								Member member = new MemberConverter().convert(memberDTO);

								// Get the DAO implementation
								MemberRankingDAO rankingDAO = DAOProvider.getDAO(MemberRankingDAO.class);
								// Get rankings List<MemberRanking>
								List<MemberRankingDTO> rankingDTOs = rankingDAO.findByMember(member.getId());
								// Convert to bean
								converter = new MemberRankingConverter();
								List<MemberRanking> rankings = converter.convert(rankingDTOs);
								// Assign the value
								member.setRankings(rankings);

								// Assign the member to the post
								post.setCreatedBy(member);
								// Assign the post to the forum
								topic.setFirstPost(post);
							}

							watch.setTopic(topic);
						}
					}

				}
				
				// Remove all non found watches
				watches.removeAll(removeWatches);
			}

			// Return result
			return Response.status(200).entity(watches).build();
		} catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		} catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

	/**
	 * Deletes a list of member watches.
	 * 
	 * @param authorization
	 * @param memberId
	 *            The member id
	 * @param watches
	 *            The list of watches to delete
	 * @return
	 * @throws NotAuthorizedException
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response deleteWatches(String authorization, Integer memberId, List<Watch> watches)
			throws ServerErrorException {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
			// Convert the watches
			List<WatchDTO> watchesDTO = new WatchConverter().convertToDTOs(watches);
			// Get the DAO
			WatchDAO watchDAO = DAOProvider.getDAO(WatchDAO.class);
			// Delete the watches. Admin can delete any watch. Other users can
			// only delete their
			// own ones, so pass along the user id supplied as part of
			// authorization and ignore
			// the path parameter
			int result = watchDAO.delete(isAdmin(), getEndUserId(), watchesDTO);

			if (result != watchesDTO.size()) {
				// Not all the watches were deleted, e.g. if someone tries to
				// remove a watch not belonging
				// to them or watches that does not exist.
				Error error = responseUtils.buildError(1003L, new Object[][] { null, { watchesDTO.size(), result } });
				// Conflict response with error information
				return Response.status(Response.Status.CONFLICT).entity(error).build();
			} else
				return Response.status(200).build();
		} catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		} catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

	private boolean validSignature(String signature) throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("signature.blacklist").getFile());
		Set<String> lines = new HashSet<String>(FileUtils.readLines(file));

		for (String line : lines) {
			if (signature.toLowerCase().contains(line))
				return false;
		}

		return true;

	}
}
