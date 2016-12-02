package org.vasttrafik.wso2.carbon.community.api.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.beans.Error;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.PostEdit;
import org.vasttrafik.wso2.carbon.community.api.beans.Vote;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostEditConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.VoteConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.CategoryDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.ForumDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostEditDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.TopicDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.VoteDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.CategoryDTO;
import org.vasttrafik.wso2.carbon.community.api.model.ForumDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostEditDTO;
import org.vasttrafik.wso2.carbon.community.api.model.VoteDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class PostsApiServiceImpl extends CommunityApiServiceImpl {
	
	/**
	 * Converting between post bean and DTO
	 */
	private PostConverter postConverter = new PostConverter();
	
	/**
	 * Converting between member bean and DTO
	 */
	private MemberConverter memberConverter = new MemberConverter();
	
	/**
	 * Converting between vote bean and DTO
	 */
	private VoteConverter voteConverter = new VoteConverter();
	
	private static final String[] actions = {"answered", "edited"};
	private static final String[] labels = {"popular", "recent", "votes", "unanswered"};
	
	/**
	 * Retrieves posts, filtered by label or query, possibly with paging.
	 * @param label The following labels are supported: popular, recent, votes, unanswered
	 * @param query String that will be matched against the following attributes: createDate, 
	 * createdBy, text, categoryId and forumId. For example, to search for topics created after 
	 * a certain date, use the following: query=;createDate,>,2016-03-01 12:00
	 * @param offset If paging is required, the offset into the result set
	 * @param limit If paging is required, the number of rows to return
	 * @return
	 * @throws BadRequestException
	 * @throws ServerErrorException
	 */
	public Response getPosts(
    		String label,
    		String query,
    		Integer offset,
    		Integer limit) 
    	throws ServerErrorException  
    {
		try {
			// Verify the label parameter
			responseUtils.checkParameter("label", false, labels, label);
			
			// Make sure we have either label or query
			if ((label == null || "".equals(label)) && (query == null || "".equals(query)))
					return responseUtils.badRequest(1000L, null);
						
			List<PostDTO> postDTOs = null;
			
			// Get the DAO implementation
        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
        	
        	if (label != null) {
        		// Perform the search by label
        		postDTOs = postDAO.findByLabel(label, offset, limit);
        	}
        	else {
        		// Perform the search by query
        		postDTOs = postDAO.findByQuery(query, offset, limit);
        	}
        	
        	// Convert the result
        	List<Post> posts = postConverter.convert(postDTOs);
        	
        	for(Post post : posts) {
        		
        		// Set edit info
        		if(post.getNumberOfTimesEdited() > 0) {
        			post.setEdits(getEdits(post.getId()));

        			// Get a DAO to look up member information
            		MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
            		// Get the created by
            		MemberDTO memberDTO = memberDAO.find(post.getEditedBy().getId());
            		// Convert to bean
            		Member member = memberConverter.convertPublic(memberDTO);
            		// Assign it
            		post.setEditedBy(member);
        		}
        	}
        	
        	// Return response
        	return Response.status(200).entity(posts).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Creates a post.
	 * @param authorization
	 * @param post The post to create
	 * @return The newly created post, with the id set
	 * @throws ServerErrorException If error occurs
	 */
	public Response createPost(String authorization, Post post) 
	    throws ServerErrorException  
	{
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			if (!isOwnerOrAdmin(user.getUserId()))
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Only admins may create posts as html
			if (post.getTextFormat() == Post.FormatEnum.html && !isAdmin())
				throw new BadRequestException();
			
			// Get the DAO implementation
        	ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        	// Lookup the category
        	ForumDTO forumDTO = forumDAO.find(post.getForumId());
        	
			// Get the DAO implementation
	        CategoryDAO categoryDAO = DAOProvider.getDAO(CategoryDAO.class);
	        // Lookup the category
	        CategoryDTO categoryDTO = categoryDAO.find(forumDTO.getCategoryId());
	        
	        if(!categoryDTO.getIsPublic() && !isAdmin())
	        	return responseUtils.notAuthorizedError(1104L, null);
			
			// Convert the post
        	PostDTO postDTO = postConverter.convert(post);
			// Get the DAO implementation
        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
        	// Set the created by id
        	postDTO.setCreatedById(getEndUserId());
        	// Perform the insert
        	Long id = postDAO.insert(postDTO);
        	// Lookup the post to get the default values
        	postDTO = postDAO.find(id);

        	// Populate and return result
        	return Response.status(201).entity(generatePost(postDTO)).build();
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(bre.getCause())
					.build();
		}
		catch (BadRequestException bre) {
			// Create an error
			Error error = responseUtils.buildError(1205L, null);
			// Return result
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(error)
					.build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Retrieves a post.
	 * @param id The id of the post to retrieve.
	 * @return
	 * @throws ServerErrorException If error occurs
	 */
	public Response getPost(Long postId) 
		throws ServerErrorException   
	{
		try {
			// Get the DAO implementation
			PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
        	// Lookup the post
        	PostDTO postDTO = postDAO.find(postId);
        	
        	// Check for not found, throw error
        	if (postDTO == null) {
        		return responseUtils.notFound(1002L, null);
        	}
        	else {
        		
        		// Return response
        		return Response.status(200).entity(generatePost(postDTO)).build();
        	}
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Updates a post. Two types of updates are allowed. The first is when a post is marked as an
	 * answer to a question and the other is when the text of the post has been edited.
	 * @param authorization
	 * @param id The post id
	 * @param action The type of update to perform. Supported actions are mark post as answered 
	 * (=answered) and edit the text of the post (=edited)
	 * @param post The post to update
	 * @return
	 * @throws ServerErrorException If error occurs
	 */
	public Response updatePost(String authorization, Long id, String action, Post post) 
    	throws ServerErrorException 
    {
		try {
			// TO-DO: Check that the user updating the post is the owner of the topic
			
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			TopicDAO topicDAO = DAOProvider.getDAO(TopicDAO.class);
			// Get the DAO implementation
			PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
    		// Turn off autocommit so we can handle update and insert as one
    		// single transaction
    		postDAO.setAutoCommit(false);
    		// Get the DAO implementation
			VoteDAO voteDAO = DAOProvider.getDAO(VoteDAO.class);
			
			int topicCreatedById = topicDAO.find(post.getTopicId()).getCreatedById();
			int postCreatedById = postDAO.find(post.getId()).getCreatedById();
			
			if (!action.equals("answered")) {
				// Posts may only be updated by admins or the user that created it
				if (!isOwnerOrAdmin(user.getUserId()) && (!isAdmin() && user.getUserId() != postCreatedById))
					return responseUtils.notAuthorizedError(1104L, null);
			} else {

	        	//The creator of the topic which the post belongs to is able to update any post as answer
				if (!isOwnerOrAdmin(user.getUserId()) && user.getUserId() != topicCreatedById)
					return responseUtils.notAuthorizedError(1104L, null);
			}

			// Only admins may create posts as html
			if (post.getTextFormat() == Post.FormatEnum.html && !isAdmin()) {
				// Create an error
				Error error = responseUtils.buildError(1205L, null);
				// Return result
				return Response.status(Response.Status.BAD_REQUEST)
						.entity(error)
						.build();
			}
				
			// Make sure the action parameter is set and valid
			try {
				responseUtils.checkParameter(
					"action", 
					true, 
					actions, 
					action);
			}
			catch (BadRequestException e) {
				// Create an error
				Error error = responseUtils.buildError(1001L, new Object[][]{null,{action, "action"}});
				// Return result
				return Response.status(Response.Status.BAD_REQUEST)
						.entity(error)
						.build();
			}
			
			// TO-DO: If topic is closed or deleted, it can not be updated: check in db
			
			// Convert the post
        	PostDTO postDTO = postConverter.convert(post);
        	
        	Integer count = null;
        	
        	if (action.equals("answered")) {
        		// Mark the post as an answer. A trigger on the table
        		// will update the topic to set the answer to this post
        		
    			// Lookup the post
    			PostDTO oldPostDTO = postDAO.find(id);
    			
    			Post oldPost = postConverter.convert(oldPostDTO);
    			
    			if(oldPost.getIsAnswer())
    				return responseUtils.badRequest(1209L, null);
        		
        		try {
        			// Write the changes to database
        			count = postDAO.markAsAnswer(postDTO);
        		}
        		catch (Exception e) {
    				postDAO.rollbackTransaction(true);
    				throw e;
    			}
        		
        		if (count == 0) // = Not found
        			return responseUtils.notFound(1002L, null);
    			
    			boolean hasVoted = false;
    			
    			List<VoteDTO> voteDTOs = voteDAO.findByPost(post.getId());
    			
    			for(VoteDTO voteDTO : voteDTOs) {
    				if(voteDTO.getMemberId().equals(user.getUserId())) {
    					hasVoted = true;
    					break;
    				}
    			}
    			
    			// Can only vote once and cannot vote for own post
    			if(!hasVoted && (oldPostDTO.getCreatedById() != user.getUserId())) {
    				
        			VoteDTO newVoteDTO = new VoteDTO();
        			newVoteDTO.setPoints((short)1);
        			newVoteDTO.setPostId(post.getId());
        			newVoteDTO.setType(post.getType().name());
        			
        			// Force memberId from actual user
        			newVoteDTO.setMemberId(user.getUserId());
    				
    				voteDAO.setAutoCommit(false);
    				// Assign the same connection to this DAO
    	        	voteDAO.setConnection(postDAO.getConnection());

                	try {
                		// Perform insert of vote
                		voteDAO.insert(newVoteDTO);
                	}
                	catch (Exception e) {
                		postDAO.rollbackTransaction(true);
        				throw e;
                	}
    			}
    			
            	// Commit the transaction
            	postDAO.commitTransaction(true);
        	}
        	else { // Post has been edited
        		// Get the current version
        		PostDTO currentVersion = postDAO.find(post.getId());
        		// Create a PostEdit version
        		PostEditDTO postEdit = createEditPost(currentVersion);
        		
        		// Set the edit date
        		postDTO.setEditDate(new Date());
        		// Set the edited by id
        		postDTO.setEditedById(getEndUserId());
        		// Update the post edit count
        		postDTO.setEditCount((short)(currentVersion.getEditCount().shortValue() + 1));
        		
        		try {
        			// Write the changes to database
        			count = postDAO.update(postDTO);
        		}
        		catch (Exception e) {
    				postDAO.rollbackTransaction(true);
    				throw e;
    			}
        		
        		if (count == 0) {
        			// Commit transaction
        			postDAO.commitTransaction(true);
        			// Return NotFound
        			return responseUtils.notFound(1002L, null);
        		}
        		
        		// Get the DAO implementation
            	PostEditDAO editDAO = DAOProvider.getDAO(PostEditDAO.class);
        		// Turn off autocommit
            	editDAO.setAutoCommit(false);
            	// Assign the same connection to this DAO
            	editDAO.setConnection(postDAO.getConnection());
            	
            	try {
            		// Perform insert of post edit
            		editDAO.insert(postEdit);
            	}
            	catch (Exception e) {
            		postDAO.rollbackTransaction(true);
    				throw e;
            	}
            	// Commit the transaction
            	postDAO.commitTransaction(true);
        	}
        	
        	voteDAO = DAOProvider.getDAO(VoteDAO.class);
			// Retrieve individual votes for this post
			List<VoteDTO> voteDTOs = voteDAO.findByPost(post.getId());

			List<Vote> votes = new ArrayList<Vote>();
			for (VoteDTO voteDTO : voteDTOs) {
				votes.add(voteConverter.convert(voteDTO));
			}
			
			// Retrieve updated post to be able to send it as response
			PostDTO newPostDTO = postDAO.find(post.getId());

			Post newPost = generatePost(newPostDTO);
			
			// Add votes to post
			newPost.setVotes(votes);
        	
        	// Return updated result
        	return Response.status(200).entity(newPost).build();
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
	 * Deletes a post
	 * @param authorization
	 * @param id The id of the post to delete
	 * @return
	 * @throws NotAuthorizedException
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response deletePost(String authorization, Long id) 
	    throws ServerErrorException 
	{
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
			int postCreatedById = postDAO.find(id).getCreatedById();
						
			// Posts may only be updated by admins or the member that created the post
			if (!isOwnerOrAdmin(user.getUserId()) && (!isAdmin() && user.getUserId() != postCreatedById))
				return responseUtils.notAuthorizedError(1104L, null);
		
        	// Perform the delete
        	Integer count = postDAO.delete(id);
        	
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
	 * Retrieves the edits for this post.
	 * @param id The id of the post
	 * @return
	 * @throws NotFoundException
	 * @throws ServerErrorException
	 */
	public Response getPostEdits(Long postId) 
	    throws ServerErrorException  
	{
		try {
        	List<PostEdit> edits = getEdits(postId);
        	// Return response
        	return Response.status(200).entity(edits).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	public Response createVote(String authorization, Long id, Vote vote) 
		throws ClientErrorException 
	{
		try {
			// Authorize. May throw NotAuthorizedException
			AuthenticatedUser user = authorize(authorization);
			
			if (!isOwnerOrAdmin(user.getUserId()))
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Convert the vote
			VoteDTO voteDTO = voteConverter.convert(vote);
			// Get the DAO implementation
			VoteDAO voteDAO = DAOProvider.getDAO(VoteDAO.class);
			
			// Force memberId from actual user
			voteDTO.setMemberId(user.getUserId());
			
			// Default to 1 point
			if(vote.getPoints() == null)
				vote.setPoints((short)1);
			
			// Get the DAO implementation
			PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
			// Lookup the post
			PostDTO oldPostDTO = postDAO.find(id);
			
			Post oldPost = postConverter.convert(oldPostDTO);
			
			boolean hasVoted = false;
			for(Vote oldVote : oldPost.getVotes()) {
				if(oldVote.getMemberId().equals(user.getUserId())) {
					hasVoted = true;
					break;
				}
			}
			
			// Can only vote once and cannot vote for own post
			if(!hasVoted && (oldPostDTO.getCreatedById() != user.getUserId())) {
				Long voteId = voteDAO.insert(voteDTO);
				
				voteDTO = voteDAO.find(voteId);

	        	// Populate and return result
	        	return Response.status(201).entity(voteConverter.convert(voteDTO)).build();
			} else {
				throw new BadRequestException();
			}
		}
		catch (NotAuthorizedException bre) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(bre.getCause())
					.build();
		}
		catch (BadRequestException bre) {
			// Create an error
			Error error = responseUtils.buildError(1206L, null);
			// Return result
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(error)
					.build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

	protected List<PostEdit> getEdits(Long postId) throws ServerErrorException {
		try {
			// Get the DAO implementation
        	PostEditDAO postDAO = DAOProvider.getDAO(PostEditDAO.class);
        	// Perform the search
        	List<PostEditDTO> editDTOs = postDAO.findByPost(postId);
        	// Convert the result
        	PostEditConverter converter = new PostEditConverter();
        	List<PostEdit> edits = converter.convert(editDTOs);
        	
        	// Add member to PostEdits
        	for(PostEdit edit : edits) {
        		// Get a DAO to look up member information
        		MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        		// Get the created by
        		MemberDTO memberDTO = memberDAO.find(edit.getCreatedBy().getId());
        		// Convert to bean
        		Member member = memberConverter.convertPublic(memberDTO);
        		// Assign it
        		edit.setCreatedBy(member);
        	}
        	
        	// Return response
        	return edits;
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	protected Post generatePost(PostDTO postDTO) throws SQLException {
		// Convert the result
		Post post = postConverter.convert(postDTO);
		// Get a DAO to look up member information
		MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
		// Get the created by
		MemberDTO memberDTO = memberDAO.find(post.getCreatedBy().getId());
		// Convert to bean
		Member member = memberConverter.convertPublic(memberDTO);
		// Assign it
		post.setCreatedBy(member);
		
		// Retrieve edit information
		if (post.getNumberOfTimesEdited() > 0) {
			// Get the created by
			memberDTO = memberDAO.find(post.getEditedBy().getId());
			// Convert to bean
			member = memberConverter.convertPublic(memberDTO);
			// Assign it
			post.setEditedBy(member);
			// Get the edits
			List<PostEdit> edits = getEdits(post.getId());
			post.setEdits(edits);
		}

		return post;
	}
	
	protected PostEditDTO createEditPost(PostDTO postDTO) {
		PostEditDTO editDTO = new PostEditDTO();
		editDTO.setCreateDate(postDTO.getCreateDate());
		editDTO.setCreatedById(postDTO.getCreatedById());
		editDTO.setEditVersion(postDTO.getEditCount());
		editDTO.setTextFormat(postDTO.getTextFormat());
		editDTO.setPostId(postDTO.getId());
		editDTO.setText(postDTO.getText());
		return editDTO;
	}
}
