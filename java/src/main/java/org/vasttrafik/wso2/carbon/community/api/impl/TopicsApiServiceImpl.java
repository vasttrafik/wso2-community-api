package org.vasttrafik.wso2.carbon.community.api.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;



import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.Topic;
import org.vasttrafik.wso2.carbon.community.api.beans.TopicWatch;
import org.vasttrafik.wso2.carbon.community.api.beans.Vote;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.TopicConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.TopicWatchConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.VoteConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.TopicDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.TopicWatchDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.VoteDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;
import org.vasttrafik.wso2.carbon.community.api.model.TopicDTO;
import org.vasttrafik.wso2.carbon.community.api.model.TopicWatchDTO;
import org.vasttrafik.wso2.carbon.community.api.model.VoteDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public class TopicsApiServiceImpl extends CommunityApiServiceImpl {
	
	/**
	 * A topic converter
	 */
	private TopicConverter topicConverter = new TopicConverter();
	
	/**
	 * A post converter
	 */
	private PostConverter postConverter = new PostConverter();
	
	private static final String[] actions = {"subject", "close", "tag"};
	private static final String[] labels = {"popular", "recent", "answered", "unanswered", "votes"};
	
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(TopicsApiServiceImpl.class);
	
	/**
	 * Retrieves a list of topics either being labeled according to the label parameter, or matching the query conditions 
	 * provided by the query parameter, but not both.
	 * @param label Restrict the result to topics being labeled accordingly. The following labels are currently supported:
	 * "popular", "recent", "unanswered", "votes"
	 * @param query Query string that will be matched against the following attributes: createDate, createdBy, subject, 
	 * text, categoryId and forumId. For example, to search for topics created after a certain date, use the following:
	 * query=;createDate,>,2016-03-01 12:00
	 * @param offset If paging is required, the offset into the result set
	 * @param limit If paging is required, the number of rows to return
	 * @return
	 * @throws BadRequestException If invalid label parameter is supplied
	 * @throws ServerErrorException
	 */
	public Response getTopics(
    		String label,
    		String query,
    		Integer offset,
    		Integer limit) 
    	throws BadRequestException, ServerErrorException 
    {
		try {
			// Verify the label parameter
			responseUtils.checkParameter("label", false, labels, label);
			
			// Make sure we have either label or query
			if ((label == null || "".equals(label)) && (query == null || "".equals(query)))
					return responseUtils.badRequest(1000L, null);
			
			List<TopicDTO> topicDTOs = null;
			
			// Get the DAO implementation
        	TopicDAO topicDAO = DAOProvider.getDAO(TopicDAO.class);
        	
        	if (label != null) {
        		// Perform the search
        		topicDTOs = topicDAO.findByLabel(label, offset, limit);
        	}
        	else if (query != null) {
        		// Perform the search
        		topicDTOs = topicDAO.findByQuery(query, offset, limit);
        	}
        	
        	List<Topic> topics = topicConverter.convert(topicDTOs);
        	return Response.status(200).entity(topics).build();
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
	 * Creates a topic.
	 * @param authorization
	 * @param topic The topc to create
	 * @return
	 * @throws BadRequestException
	 * @throws NotAuthorizedException
	 * @throws ServerErrorException
	 */
	public Response createTopic(String authorization, Topic topic) 
		    throws ServerErrorException 
	{
		TopicDAO topicDAO = null;
		
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			
			//TODO change this when opening up community 
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Start by making sure there is a question in the array of posts
			Post post = getQuestion(topic);
			// Convert the topic
			TopicDTO topicDTO = topicConverter.convert(topic);
			// Convert the post to a DTO
			PostDTO postDTO = postConverter.convert(post);
			
			// Get the DAO implementation
			topicDAO = DAOProvider.getDAO(TopicDAO.class);
			// Turn off autocommit to create one single transaction
			topicDAO.setAutoCommit(false);
			
			Long topicId = null;
			
			try {
				// Assign the member id to the createdbyid
				topicDTO.setCreatedById(user.getUserId());
				// Create the topic
				topicId = topicDAO.insert(topicDTO);
			}
			catch (Exception e) {
				topicDAO.rollbackTransaction(true);
				throw e;
			}
			
        	// Get the DAO implementation
        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
        	// Turn off autocommit
        	postDAO.setAutoCommit(false);
        	// Assign the same connection to this DAO
        	postDAO.setConnection(topicDAO.getConnection());
        	
        	// Create the question post
        	try {
        		// Assign the topic id to the post
				postDTO.setTopicId(topicId);
				// Assign the created by to the post
        		postDTO.setCreatedById(user.getUserId());
        		// Create the post
        		postDAO.insert(postDTO);
        	}
        	catch (Exception e) {
        		topicDAO.rollbackTransaction(true);
				throw e;
        	}
        	
        	// Commit the transaction
        	topicDAO.commitTransaction(true);
        	// Get the created topic
        	
        	return Response.status(201).entity(getTopic(topicId).getEntity()).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(bre.getCause())
					.build();
		}
		catch (BadRequestException bre) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(responseUtils.buildError(1203L, null))
					.build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Retrieves a topic
	 * @param id The id of the topic to retrieve
	 * @return
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response getTopic(Long id) 
    	throws ServerErrorException 
    {
		try {
			// Get the DAO implementation
			TopicDAO topicDAO = DAOProvider.getDAO(TopicDAO.class);
        	// Lookup the topic
        	TopicDTO topicDTO = topicDAO.find(id);
        	
        	// Check for not found, throw error
        	if (topicDTO == null) {
        		return responseUtils.notFound(1002L, null);
        	}
        	else {
        		// Convert to bean
        		Topic topic = topicConverter.convert(topicDTO);
        		
        		// Get the DAO implementation
            	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
            	// Get the member that created the topic
        		MemberDTO memberDTO = memberDAO.find(topic.getCreatedBy().getId());
        		// Convert
        		Member member = new MemberConverter().convert(memberDTO);
        		// Assign the member to the topic
        		topic.setCreatedBy(member);
        		
        		if (topic.getClosedBy() != null) {
        			// Get the member that closed the topic
            		memberDTO = memberDAO.find(topic.getClosedBy().getId());
            		// Convert
            		member = new MemberConverter().convert(memberDTO);
            		// Assign the member to the topic
            		topic.setClosedBy(member);
        		}
        		
        		// TO-DO: Get all posts, then set first and last post accordingly
        		
        		// Update number of view for each get request
        		topicDAO.incrementViews(topicDTO);
        		
        		@SuppressWarnings("unchecked")
				List<Post> posts = (List<Post>)getPosts(null, topic.getId(), null, null).getEntity();
        		
        		topic.setPosts(posts);
        		topic.setFirstPost(posts.get(0));
        		topic.setLastPost(posts.get(posts.size()-1));
        		
        		// Return result
        		return Response.status(200).entity(topic).build();
        	}
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	// take care of different actions: subject, close, tag (requires different converts etc.)
	/**
	 * Updates a topic
	 * @param authorization
	 * @param id The topic id
	 * @param action The update action to perform: subject (update subject), close (close topic) or tag (tag topic)
	 * @param topic The topic to update
	 * @return
	 * @throws BadRequestException
	 * @throws NotAuthorizedException
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response updateTopic(String authorization, Long id, String action, Topic topic) 
    	throws ServerErrorException  
    {
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			//TODO change this when opening up community 
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Make sure the action parameter is set and valid
			responseUtils.checkParameter(
					"action", 
					true, 
					actions, 
					action);
			
			// Convert the topic
        	TopicDTO topicDTO = topicConverter.convert(topic);
			// Get the DAO implementation
        	TopicDAO topicDAO = DAOProvider.getDAO(TopicDAO.class);
        	
        	Integer count = null;
        	
        	if (action.equals("subject")) { // Subject has been updated
        		// TO-DO: Check required attributes
        		count = topicDAO.updateSubject(user, topicDTO);
        	}
        	else if (action.equals("close")) { // Topic is being closed
        		// TO-DO: Check required attributes
            	count = topicDAO.closeTopic(topicDTO);
        	}
        	else
        		count = topicDAO.update(topicDTO);
        	
        	// Check result
        	if (count == 0) // = Not found
        		return responseUtils.notFound(1002L, null);
        	else
        		// Return result
        		return Response.status(200).entity(topic).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(bre.getCause())
					.build();
		}
		catch (BadRequestException bre) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(responseUtils.buildError(1202L, null))
					.build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Deletes a topic. Topics can only be deleted by an admin or the creator of the topic provided that no answers 
	 * or comments have been posted.
	 * @param authorization
	 * @param topicId The id of the topic to delete
	 * @return
	 * @throws NotAuthorizedException
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response deleteTopic(String authorization, Long topicId) 
    	throws ServerErrorException  
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
						
			// Topic can only be deleted by an admin or the creator of the topic if no replies have been posted
			// TO-DO: Implement check if user created topic and no replies have been posted
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Get the DAO implementation
			TopicDAO topicDAO = DAOProvider.getDAO(TopicDAO.class);
        	// Perform the delete
        	Integer count = topicDAO.delete(topicId);
        	
        	// Check result
        	if (count == 0) // = Not found
        		return responseUtils.notFound(1002L, null);
        	else
        		// Return result
        		return Response.status(200).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(bre.getCause())
					.build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Retrieves posts belonging to the specified topic
	 * @param ifModifiedSince If set, returns only posts submitted after the specified date
	 * @param topicId The id of the topic
	 * @param offset If paging is required, the offset into the result set
	 * @param limit If paging is required, the number of rows to return
	 * @return
	 * @throws ServerErrorException
	 */
	public Response getPosts(
			String ifModifiedSince,
			Long topicId,
			Integer offset,
			Integer limit) 
	    throws ServerErrorException 
	{
		try {
			// Get the date header, if present
			String isoDate = rfc822ToISO8601Date(ifModifiedSince);
			// Get the DAO implementation
			PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
			// Perform the search
			List<PostDTO> postDTOs = postDAO.findByTopic(topicId, isoDate, offset, limit);
			// Convert the result
			List<Post> posts = postConverter.convert(postDTOs);
			
			// Get the DAO implementation
        	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
			
			for(Post post : posts) {
				// Get the member that created the post
	    		MemberDTO memberDTO = memberDAO.find(post.getCreatedBy().getId());
	    		// Convert
	    		Member member = new MemberConverter().convert(memberDTO);
	    		// Assign the member to the post
	    		post.setCreatedBy(member);
			}
			
			// Return the result
			return Response.status(200).entity(posts).build();
			
			// TO-DO:Fyll på varje post med nödvändig info (created by, comment to?)
		}
		catch (ParseException pe) {
			return responseUtils.badRequest(1001L, new Object[][]{{ifModifiedSince, "If-Modified-Since"}});
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Retrieves votes that have been casted on all the posts belonging to the topic
	 * @param authorization
	 * @param topicId The topic id
	 * @param memberId If specified, returns only votes casted by the member with this id
	 * @return
	 * @throws NotAuthorizedException
	 * @throws ServerErrorException
	 */
	public Response getVotes(
    		String authorization,
    		Long topicId,
    		Integer memberId) 
    	throws NotAuthorizedException, ServerErrorException 
    {
        try {
        	// Authorize. May throw NotAuthorizedException
        	authorize(authorization);
        	// Get the DAO implementation
        	VoteDAO voteDAO = DAOProvider.getDAO(VoteDAO.class);
        	// Get the votes
        	List<VoteDTO> voteDTOs = voteDAO.findByTopicAndMember(topicId, memberId);
        	// Convert the result
        	List<Vote> votes = new VoteConverter().convert(voteDTOs);
        	// Return the result
        	return Response.status(200).entity(votes).build();
        }
        catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Creates a topic watch
	 * @param authorization
	 * @param topicId The topic id
	 * @return
	 * @throws NotAuthorizedException
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response createWatch(String authorization, Long topicId) 
    	throws ServerErrorException  
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
			
			// Get the DAO implementation
			TopicWatchDAO watchDAO = DAOProvider.getDAO(TopicWatchDAO.class);
			
			// Get the member id
			Integer memberId = getEndUserId();
						
			// Create the DTO
			TopicWatchDTO watchDTO = new TopicWatchDTO();
			watchDTO.setTopicId(topicId);
			watchDTO.setMemberId(memberId);
			
			// Create watch
			Long watchId = watchDAO.insert(watchDTO);
			// Update DTO with generated key
			watchDTO.setId(watchId); 
			// Convert to bean
			TopicWatch watch = new TopicWatchConverter().convert(watchDTO);
			// Return result
			return Response.status(201).entity(watch).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(bre.getCause()).build();
		}
		catch (SQLIntegrityConstraintViolationException icve) {
			// Konstruera ett felobjekt Error och baka in i felet
			// Om detta inte funkar, kolla på http://stackoverflow.com/questions/1988570/how-to-catch-a-specific-exception-in-jdbc
			throw new NotFoundException();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Deletes a topic watch.
	 * @param authorization
	 * @param watchId The id of the watch
	 * @return
	 * @throws NotAuthorizedException
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response deleteWatch(String authorization, Long watchId) 
    	throws ServerErrorException  
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
						
			// TO-DO: Check user is owner of watch
			
			// Get the DAO implementation
			TopicWatchDAO watchDAO = DAOProvider.getDAO(TopicWatchDAO.class);
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
	
	/**
	 * Retrieves the topic question
	 * @param topic
	 * @return
	 * @throws BadRequestException If the question is missing
	 */
	protected Post getQuestion(Topic topic) throws BadRequestException {
		List<Post> posts = topic.getPosts();
					
		if (posts == null || posts.size() != 1)
			throw new BadRequestException();
					
		Post question = posts.get(0);
					
		if (question.getType() != Post.TypeEnum.question)
			throw new BadRequestException();
		else
			return question;
	}
	
	/**
	 * Retrieves a post from the db
	 * @param post The post to load
	 * @return
	 */
	protected Post loadPost(Post post) {
		if (post != null) {
			// Get the DAO implementation
        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);

        	try {
        		// Lookup the post
        		PostDTO postDTO = postDAO.find(post.getId());
        		// Convert the result
        		post = postConverter.convert(postDTO);
        		// Get a DAO to look up member information
        		MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        		// Get the created by
        		MemberDTO memberDTO = memberDAO.find(post.getCreatedBy().getId());
        		// Convert to bean
        		Member member = new MemberConverter().convert(memberDTO);
        		// Assign it
        		post.setCreatedBy(member);
        		// Return result
        		return post;
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        	
		}
	
		return null;
	}
}
