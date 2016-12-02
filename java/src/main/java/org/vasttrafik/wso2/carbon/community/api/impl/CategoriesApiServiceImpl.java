package org.vasttrafik.wso2.carbon.community.api.impl;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Category;
import org.vasttrafik.wso2.carbon.community.api.beans.Forum;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.MemberRanking;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.CategoryConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.ForumConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberRankingConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.CategoryDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.ForumDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberRankingDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.CategoryDTO;
import org.vasttrafik.wso2.carbon.community.api.model.ForumDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberRankingDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class CategoriesApiServiceImpl extends CommunityApiServiceImpl {
	
	/**
	 * Category converter
	 */
	private CategoryConverter categoryConverter = new CategoryConverter();
	
	/**
	 * Forum converter
	 */
	private ForumConverter forumConverter = new ForumConverter();
	
	/**
	 * Logger
	 */
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(CategoriesApiServiceImpl.class);
	
	/**
	 * Retrieves a list of categories
	 * @param includePrivate Flag indicating if private categories should be included in the list
	 * @param includeForums Flag indicating if each category in the list should contain a list of forums
	 * @return List of categories
	 * @throws ServerErrorException If the list of categories could not be retrieved
	 */
	public Response getCategories(Boolean includePrivate, Boolean includeForums) 
    	throws ServerErrorException 
    {
		List<Category> categories = null;
		
        try {
        	// Get DAO implementation
        	CategoryDAO categoryDAO = DAOProvider.getDAO(CategoryDAO.class);
        	// Get list of categories
        	List<CategoryDTO> categoriesDTO = categoryDAO.find(includePrivate);
        	// Convert to bean
        	categories = categoryConverter.convert(categoriesDTO);
        	
        	if (!categories.isEmpty() && includeForums) {
        		// Get a forums DAO
        		ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        		
	        	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
	        	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
        	
        		for (Iterator<Category> it = categories.iterator(); it.hasNext();) {
        			// Get the category
        			Category category = it.next();
        			// Get the forums
        			List<ForumDTO> forumsDTO = forumDAO.findByCategory(category.getId(), null, null, null);
        			// Convert to beans
        			List<Forum> forums = forumConverter.convert(forumsDTO);
        			
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
        			
        			// Assign
        		    category.setForums(forums);
        		}
        	}
        }
        catch (Exception e) {
        	Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
        }
        
        return Response.status(200).entity(categories).build();
    }
	
	/**
	 * Retrieves the category with the specified resource id, including a list of the forums in the category
	 * @param id The resource id of the category to retrieve
	 * @return The category with the provided resource id
	 * @throws ServerErrorException
	 */
	public Response getCategory(Integer id) 
	    throws ServerErrorException
	{
		try {
			// Get the DAO implementation
	        CategoryDAO categoryDAO = DAOProvider.getDAO(CategoryDAO.class);
	        // Lookup the category
	        CategoryDTO categoryDTO = categoryDAO.find(id);
	        	
	        // Check for not found, throw error
	        if (categoryDTO == null) {
	        	return responseUtils.notFound(1002L, null);
	        }
	        else {
	        	// Convert to bean
	        	Category category = categoryConverter.convert(categoryDTO);
	        	// Get a forums DAO
        		ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
        		// Get the forums
    			List<ForumDTO> forumsDTO = forumDAO.findByCategory(category.getId(), null, null, null);
    			// Convert to beans
    			List<Forum> forums = forumConverter.convert(forumsDTO);
    			
            	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
            	PostDAO postDAO = DAOProvider.getDAO(PostDAO.class);
            	
    			for(Iterator<Forum> itf = forums.iterator(); itf.hasNext();) {
    				
    				Forum forum = itf.next();
    				// Get the last post from id
    				if(forum.getLastPost() != null) {
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
    			
    			// Assign
    		    category.setForums(forums);
	        	// Return result
	        	return Response.status(200).entity(category).build();
	        }
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Admin resource to create a new category
	 * @param authorization
	 * @param category The category to create
	 * @return The category, with the id attribute updated with the resource id
	 * @throws BadRequestException
	 * @throws ServerErrorException
	 */
	public Response createCategory(String authorization, Category category) 
    	throws ServerErrorException
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);

			// Categories can only be created by an admin
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Convert the category
        	CategoryDTO categoryDTO = categoryConverter.convert(category);
			// Get the DAO implementation
        	CategoryDAO categoryDAO = DAOProvider.getDAO(CategoryDAO.class);
        	// Perform the insert
        	Integer id = categoryDAO.insert(categoryDTO);
        	// Set the id
        	category.setId(id);
        	// Return result
        	return Response.status(201).entity(category).build();
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
	 * Admin resource to update a category
	 * @param authorization
	 * @param category The category to update
	 * @return The category
	 * @throws BadRequestException
	 * @throws ServerErrorException
	 */
	public Response updateCategory(String authorization, Category category) 
    	throws ServerErrorException 
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
						
			// Categories can only be updated by an admin
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
						
			// Convert the category
        	CategoryDTO categoryDTO = categoryConverter.convert(category);
			// Get the DAO implementation
        	CategoryDAO categoryDAO = DAOProvider.getDAO(CategoryDAO.class);
        	// Perform the insert
        	Integer count = categoryDAO.update(categoryDTO);
        	
        	// Check result
        	if (count == 0) // = Not found
        		return responseUtils.notFound(1002L, null);
        	else
        		// Return result
        		return Response.status(200).entity(category).build();
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
	 * Admin resource to create a new category
	 * @param authorization
	 * @param id The category resource id
	 * @return Response with status code 200
	 * @throws NotAuthorizedException
	 * @throws ServerErrorException
	 */
	public Response deleteCategory(String authorization, Integer id) 
    	throws ServerErrorException 
    {
		try {
			// Authorize. May throw NotAuthorizedException
			authorize(authorization);
									
			// Categories can only be deleted by an admin
			if (!isAdmin())
				return responseUtils.notAuthorizedError(1104L, null);
			
			// Get the DAO implementation
        	CategoryDAO categoryDAO = DAOProvider.getDAO(CategoryDAO.class);
        	// Perform the insert
        	Integer count = categoryDAO.delete(id);
        	
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
	 * Retrieves a list of forums in the category with the specified resource id
	 * @param ifModifiedSince Retrieve only forums modified after the specified date and time
	 * @param categoryId The category resource id
	 * @param offset If paging is requird, the ofset row number to retrieve
	 * @param limit If paging is required, the number of rows to return
	 * @return A list of forums, or BadRequestException if the specified http header (if present) could not be parsed
	 * @throws ServerErrorException
	 */
	public Response getForums(
    		String ifModifiedSince, 
    		Integer categoryId,
    		Integer offset,
    		Integer limit) 
    	throws ServerErrorException 
    {
		try {
			// Convert the header, if present
			String iso8601Date = rfc822ToISO8601Date(ifModifiedSince);
			// Get a forums DAO
    		ForumDAO forumDAO = DAOProvider.getDAO(ForumDAO.class);
    		// Get the list of forums
    		List<ForumDTO> forumsDTO = forumDAO.findByCategory(categoryId, iso8601Date, offset, limit);
			// Convert to beans
			List<Forum> forums = forumConverter.convert(forumsDTO);
			// Return result
			return Response.status(200).entity(forums).build();
		}
		catch (ParseException pe) {
			return responseUtils.badRequest(1001L, new Object[][]{{},{ifModifiedSince, "If-Modified-Since"}});
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
}
