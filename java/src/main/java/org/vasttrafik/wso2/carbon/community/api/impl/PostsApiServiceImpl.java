package org.vasttrafik.wso2.carbon.community.api.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.common.api.beans.Error;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.PostEdit;
import org.vasttrafik.wso2.carbon.community.api.beans.Vote;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostEditConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostEditDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostEditDTO;

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
			authorize(authorization);
			
			// Only admins may create posts as html
			if (post.getTextFormat() == Post.FormatEnum.html && !isAdmin())
				throw new BadRequestException();
			
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
			authorize(authorization);
			
			// Posts may only be updated by admins or the member that created the post
			if (!isAdmin() && post.getCreatedBy() != null) {
				if (!isOwnerOrAdmin(post.getCreatedBy().getId()))
					return responseUtils.notAuthorizedError(1004L, null);
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
			// Get the DAO implementation
        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
        	
        	Integer count = null;
        	
        	if (action.equals("answered")) {
        		// Mark the post as an answer. A trigger on the table
        		// will update the topic to set the answer to this post
        		count = postDAO.markAsAnswer(postDTO);
        		
        		if (count == 0) // = Not found
        			return responseUtils.notFound(1002L, null);
        	}
        	else { // Post has been edited
        		// Get the current version
        		PostDTO currentVersion = postDAO.find(post.getId());
        		// Create a PostEdit version
        		PostEditDTO postEdit = createEditPost(currentVersion);
        		
        		// Turn off autocommit so we can handle update and insert as one
        		// single transaction
        		postDAO.setAutoCommit(false);
        		
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
        	
        	// Return updated result
        	return Response.status(200).entity(generatePost(postDAO.find(post.getId()))).build();
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
			authorize(authorization);
						
			// Posts may only be updated by admins or the member that created the post
			if (!isAdmin()) {
				if (!isOwnerOrAdmin(id))
					return responseUtils.notAuthorizedError(1004L, null);
			}
			
			// Get the DAO implementation
			PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
			
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
	
	// Iteration 2
	public Response createVote(String authorization, Long id, Vote vote) 
		throws ClientErrorException 
	{
		return null;
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
        		Member member = memberConverter.convert(memberDTO);
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
		Member member = memberConverter.convert(memberDTO);
		// Assign it
		post.setCreatedBy(member);

		// Retrieve dit information
		if (post.getNumberOfTimesEdited() > 0) {
			// Get the created by
			memberDTO = memberDAO.find(post.getEditedBy().getId());
			// Convert to bean
			member = memberConverter.convert(memberDTO);
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
