package org.vasttrafik.wso2.carbon.community.api.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Forum;
import org.vasttrafik.wso2.carbon.community.api.beans.ForumWatch;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.MemberRanking;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.Topic;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.ForumConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.ForumWatchConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberRankingConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.TopicConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.ForumDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.ForumWatchDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberRankingDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.TopicDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.ForumDTO;
import org.vasttrafik.wso2.carbon.community.api.model.ForumWatchDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberRankingDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;
import org.vasttrafik.wso2.carbon.community.api.model.TopicDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class ForumsApiServiceImpl extends CommunityApiServiceImpl {
	
	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(ForumsApiServiceImpl.class);
	
	private ForumConverter forumConverter = new ForumConverter();
	
	private static final String[] labels = {"popular", "recent"};
	
	/**
	 * Retrieves a list of forums
	 * @param name The name of the forum
	 * @param label If supplied, limits the result to forums that have been labeled accordingly. 
	 * Currently, this parameter can take the values "popular" and "recent"
	 * @param offset If paging is required, the offset into the result set
	 * @param limit If paging is required, the number of rows to return
	 * @return The list of forums matching the given criteria
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response getForums(
    		String name,
    		String label,
    		Integer offset,
    		Integer limit) 
    	throws NotFoundException, ServerErrorException 
    {
		try {
			// Check parameter values
			responseUtils.checkParameter("label", false, labels, label);
			
			List<ForumDTO> forumDTOs = null;
			
			// Get the DAO implementation
        	ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        	
        	if (name != null) {
        		// Perform the search
        		forumDTOs = forumDAO.findByName(name);
        	}
        	else if (label != null) {
        		// Perform the search
        		forumDTOs = forumDAO.findByLabel(label, offset, limit);
        	}
        	else {
        		// Perform the search
        		forumDTOs = forumDAO.find(offset, limit);
        	}
        	
        	List<Forum> forums = forumConverter.convert(forumDTOs);
        	
        	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
        	
			for(Iterator<Forum> itf = forums.iterator(); itf.hasNext();) {
				
				Forum forum = itf.next();
				
				if(forum.getLastPost() != null) {
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
	            	MemberRankingConverter converter = new MemberRankingConverter();
	            	List<MemberRanking> rankings = converter.convert(rankingDTOs);
	            	// Assign the value
	            	member.setRankings(rankings);
		    		
		    		// Assign the member to the post
		    		post.setCreatedBy(member);
		    		// Assign the post to the forum
		    		forum.setLastPost(post);
				}
			}
        	
        	return Response.status(200).entity(forums).build();
		}
		catch (BadRequestException bre) {
			throw bre;
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Creates a forum
	 * @param authorization
	 * @param forum The forum to create
	 * @return
	 * @throws NotAuthorizedException If the user is not authorized to create a forum
	 * @throws ServerErrorException If internal error occurs
	 */
	public Response createForum(String authorization, Forum forum) 
    	throws ServerErrorException 
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
						
			// Forums can only be created by an admin
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Convert the category
        	ForumDTO forumDTO = forumConverter.convert(forum);
			// Get the DAO implementation
        	ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        	// Perform the insert
        	Integer id = forumDAO.insert(forumDTO);
        	// Set the id
        	forum.setId(id);
        	// Return result
        	return Response.status(201).entity(getForum(id).getEntity()).build();
		}
		catch (SQLIntegrityConstraintViolationException icve) {
			log.error(icve.getMessage());
			// Konstruera ett felobjekt Error och baka in i felet
			// Om detta inte funkar, kolla på http://stackoverflow.com/questions/1988570/how-to-catch-a-specific-exception-in-jdbc
			throw new NotFoundException();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Retrieves a forum
	 * @param id The id of the forum to retrieve
	 * @return
	 * @throws ServerErrorException If internal error occurs
	 */
	@SuppressWarnings("unchecked")
	public Response getForum(Integer id) 
    	throws ServerErrorException
    {
		try {
			// Get the DAO implementation
        	ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        	// Lookup the category
        	ForumDTO forumDTO = forumDAO.find(id);
        	
        	// Check for not found, throw error
        	if (forumDTO == null) {
        		return responseUtils.notFound(1002L, null);
        	}
        	else {
        		// Convert to bean
        		Forum forum = forumConverter.convert(forumDTO);
        		
        		List<Topic> topics = (List<Topic>)getTopics(null, forum.getId(), null, null).getEntity();
        		
        		forum.setTopics(topics);
        		
        		// Return result
        		return Response.status(200).entity(forum).build();
        	}
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Updates a forum
	 * @param authorization
	 * @param id The id of the forum to update
	 * @param forum The forum entity
	 * @return
	 * @throws NotAuthorizedException If the user is not authorized to delete the forum
	 * @throws ServerErrorException If internal error occurs
	 */
	public Response updateForum(String authorization, Integer id, Forum forum) 
    	throws ServerErrorException 
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
									
			// Forums can only be updated by an admin
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Set resource id
			forum.setId(id);
			// Convert the category
        	ForumDTO forumDTO = forumConverter.convert(forum);
			// Get the DAO implementation
        	ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        	// Perform the update
        	Integer count = forumDAO.update(forumDTO);
        	
        	// Check result
        	if (count == 0) // = Not found
        		return responseUtils.notFound(1002L, null);
        	else
        		// Return result
        		return Response.status(200).entity(getForum(id).getEntity()).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Deletes a forum.
	 * @param authorization 
	 * @param id The id of the forum to delete
	 * @return
	 * @throws NotAuthorizedException If the user is not authorized to delete the forum
	 * @throws ServerErrorException If internal error occurs
	 */
	public Response deleteForum(String authorization, Integer id) 
    	throws ServerErrorException 
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
												
			// Forums can only be deleted by an admin
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Get the DAO implementation
			ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        	// Perform the delete
        	Integer count = forumDAO.delete(id);
        	
        	// Check result
        	if (count == 0) // = Not found
        		return responseUtils.notFound(1002L, null);
        	else
        		// Return result
        		return Response.status(200).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Retrieves a list of topics belonging to the forum
	 * @param ifModifiedSince If supplied, limits the list of forums to those modified after the specified date (RFC 822)
	 * @param forumId The forum id
	 * @param offset If paging is required, the offset into the result set
	 * @param limit If paging is required, the number of rows to return
	 * @return
	 * @throws ServerErrorException If internal error occurs
	 */
	public Response getTopics(
			String ifModifiedSince, 
    		Integer forumId,
    		Integer offset,
    		Integer limit) 
    	throws ServerErrorException 
    {
		try {
			// Get the date header, if present
			String isoDate = rfc822ToISO8601Date(ifModifiedSince);
			// Get the DAO implementation
			TopicDAO topicDAO = DAOProvider.getDAO(TopicDAO.class);
			// Perform the search
			List<TopicDTO> topicDTOs = topicDAO.findByForum(forumId, isoDate, offset, limit);
			// Convert the result
			List<Topic> topics = new TopicConverter().convert(topicDTOs);
			
			// Get the DAO implementations
        	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
    		
    		for(Topic topic : topics) {
    			// Get the member that created the post
	    		MemberDTO memberDTO = memberDAO.find(topic.getCreatedBy().getId());
	    		// Convert
	    		Member member = new MemberConverter().convert(memberDTO);
	    		
	    		// Get the DAO implementation
                MemberRankingDAO rankingDAO = DAOProvider.getDAO(MemberRankingDAO.class);
            	// Get rankings List<MemberRanking>
            	List<MemberRankingDTO> rankingDTOs = rankingDAO.findByMember(member.getId());
            	// Convert to bean
            	MemberRankingConverter converter = new MemberRankingConverter();
            	List<MemberRanking> rankings = converter.convert(rankingDTOs);
            	// Assign the value
            	member.setRankings(rankings);
	    		
	    		// Assign the member to the post
	    		topic.setCreatedBy(member);
	    		
	    		// Get the first post from id
	    		PostDTO postDTO = postDAO.find(topic.getFirstPost().getId());
	    		// Convert
	    		Post post = new PostConverter().convert(postDTO);
	    		// Assign the post to the topic
	    		topic.setFirstPost(post);
	    		
	    		// Get the last post from id
	    		postDTO = postDAO.find(topic.getLastPost().getId());
	    		// Convert
	    		post = new PostConverter().convert(postDTO);
	    		// Assign the post to the topic
	    		topic.setLastPost(post);
    		}
			
			// Return the result
			return Response.status(200).entity(topics).build();
		}
		catch (ParseException pe) {
			return responseUtils.badRequest(1001L, new Object[][]{{},{ifModifiedSince, "If-Modified-Since"}});
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Creates a forum watch
	 * @param authorization
	 * @param forumId The forum id
	 * @return
	 * @throws NotAuthorizedException If the user is not authorized to create a watch
	 * @throws NotFoundException If the forum id could not be found
	 * @throws ServerErrorException If internal error occurs
	 */
	public Response createWatch(String authorization, Integer forumId) 
    	throws NotFoundException, ServerErrorException 
    {
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			// Get the DAO implementation
			ForumWatchDAO watchDAO = DAOProvider.getDAO(ForumWatchDAO.class);
			
			// Create the DTO
			ForumWatchDTO watchDTO = new ForumWatchDTO();
			watchDTO.setForumId(forumId);
			watchDTO.setMemberId(user.getUserId());
			
			// Create watch
			Long watchId = watchDAO.insert(watchDTO);
			// Update DTO with generated key
			watchDTO.setId(watchId); 
			// Convert to bean
			ForumWatchConverter watchConverter = new ForumWatchConverter();
			ForumWatch watch = watchConverter.convert(watchDTO);
			// Return result
			return Response.status(201).entity(watch).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		}
		catch (SQLIntegrityConstraintViolationException icve) {
			throw new NotFoundException();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Deletes a forum watch
	 * @param authorization
	 * @param watchId The watch id
	 * @return
	 * @throws NotAuthorizedException If the user is not authorized to delete the watch
	 * @throws ServerErrorException If internal error occurs
	 */
	public Response deleteWatch(String authorization, Long watchId)
    	throws ServerErrorException 
    {
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			// TO-DO: Check user is owner of watch
			if (!isOwnerOrAdmin(user.getUserId()))
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Get the DAO implementation
			ForumWatchDAO watchDAO = DAOProvider.getDAO(ForumWatchDAO.class);
			
			if (!(user.getUserId().equals(watchDAO.find(watchId).getMemberId())))
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Delete the watch
			Integer result = watchDAO.delete(watchId);
			
			if (result == 0)
				return responseUtils.notFound(1002L, null);
			else
				// Return result
				return Response.status(200).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
}
