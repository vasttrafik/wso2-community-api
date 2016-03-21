package org.vasttrafik.wso2.carbon.community.api.impl;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.beans.Error;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.MemberRanking;
import org.vasttrafik.wso2.carbon.community.api.beans.Watch;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberRankingConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.WatchConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberRankingDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.WatchDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.MemberRankingDTO;
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
	
	/**
	 * Auto-complete method to search for members
	 * @param member A query string that will be matched against email and signature
	 * @param offset If paging is required, the offset into the result set
	 * @param limit If paging is required, the number of rows to return
	 * @return A list of members matching the query string
	 * @throws ServerErrorException
	 */
	public Response getMembers(
    		String member,
    		Integer offset,
    		Integer limit) 
    	throws ServerErrorException 
	{
        try {
        	// Get the DAO implementation
        	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        	// Perform the search
        	List<MemberDTO> memberDTOs = memberDAO.findByEmailOrSignature(member, offset, limit);
        	// Convert the result
        	List<Member> members = memberConverter.convert(memberDTOs);
        	// Return the result
        	return Response.status(200).entity(members).build();
        }
        catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
	/**
	 * Creates a member profile
	 * @param authorization
	 * @param member The member to create
	 * @return The newly created member profile, with the id of the profile
	 * @throws BadRequestException
	 * @throws NotAuthorizedException
	 * @throws ServerErrorException
	 */
    public Response createMember(String authorization, Member member) 
    	throws ServerErrorException 
    {
    	// TO-DO: Create the folders of the member, and the member rankings
    	// Do it in SQL?
    	try {
    		// Authorize. May throw NotAuthorizedException
    		AuthenticatedUser user = authorize(authorization);
    		// Get the username of the authenticated user
    		String authenticatedUser = user.getUserName();
    		
    		// Make sure the usernames match
    		if (!authenticatedUser.equalsIgnoreCase(member.getUserName()))
    				throw new NotAuthorizedException("Usernames do not match");
    		
			// Convert the category
        	MemberDTO memberDTO = memberConverter.convert(member);
			// Get the DAO implementation
        	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        	// Perform the insert
        	memberDAO.insert(memberDTO);
        	// Return result
        	return Response.status(201).entity(member).build();
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
     * Retrieves a member profile. If a profile is not found, returns an empty one
     * @param authorization
     * @param id The id of the member profile
     * @return The profile of the member, or a default empty one if one could not be found
     * @throws ServerErrorException
     */
    public Response getMember(String authorization, Integer memberId) 
    	throws ServerErrorException  
    {
    	try {
    		// Authorize. May throw NotAuthorizedException
    		authorize(authorization);
    		
    		// Topic can only be updated by an admin or the creator of the topic
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
        	} 
        	else // Create a default profile
        		member = Member.getDefault();
        		
        	// Return result
        	return Response.status(200).entity(member).build();
        	
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
    
    /**
     * Updates a member profile
     * @param authorization
     * @param id The id of the member profile
     * @param member The member profile to update
     * @return
     * @throws BadRequestException
     * @throws NotAuthorizedException
     * @throws NotFoundException
     * @throws ServerErrorException
     */
    public Response updateMember(String authorization, Integer memberId, Member member) 
    	throws ServerErrorException 
	{
    	try {
    		// Authorize. May throw NotAuthorizedException
    		authorize(authorization);
    		
    		// Topic can only be updated by an admin or the creator of the topic
    		if (!isAdmin() && !isOwnerOrAdmin(memberId))
    			return responseUtils.notAuthorizedError(1104L, null);
    		
    		// Convert the category
        	MemberDTO memberDTO = memberConverter.convert(member);
			// Get the DAO implementation
        	MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        	// Perform the insert
        	Integer count = memberDAO.update(memberDTO);
        	
        	// Check result
        	if (count == 0) // = Not found
        		return responseUtils.notFound(1002L, null);
        	else
        		// Return result
        		return Response.status(200).entity(member).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
    }
	
    // Iteration 3
    public Response getFolders(String authorization, Integer memberId) 
    	throws NotAuthorizedException, NotFoundException, ServerErrorException  
    {
        return null;
    }
    
    /**
     * Retrieves a list of member watches (both forum and topic watches).
     * @param authorization
     * @param memberId The member profile id
     * @param offset If paging is required, the offset into the result list
     * @param limit If paging is required, the number of rows to return from the result list
     * @return
     * @throws ServerErrorException If error occurs
     */
    public Response getWatches(String authorization, Integer memberId, Integer offset, Integer limit) 
    	throws ServerErrorException 
    {
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
        	// Return result
        	return Response.status(200).entity(watches).build();
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
     * Deletes a list of member watches.
     * @param authorization
     * @param memberId The member id
     * @param watches The list of watches to delete
     * @return
     * @throws NotAuthorizedException
     * @throws NotFoundException
     * @throws ServerErrorException
     */
    public Response deleteWatches(String authorization, Integer memberId, List<Watch> watches) 
    	throws ServerErrorException 
    {
    	try {
        	// Authorize. May throw NotAuthorizedException
        	authorize(authorization);
        	// Convert the watches 
        	List<WatchDTO> watchesDTO = new WatchConverter().convertToDTOs(watches);
        	// Get the DAO
        	WatchDAO watchDAO = DAOProvider.getDAO(WatchDAO.class);
        	// Delete the watches. Admin can delete any watch. Other users can only delete their
        	// own ones, so pass along the user id supplied as part of authorization and ignore
        	// the path parameter
        	int result = watchDAO.delete(isAdmin(), getEndUserId(), watchesDTO);
        	
        	if (result != watchesDTO.size()) {
        		// Not all the watches were deleted, e.g. if someone tries to remove a watch not belonging
        		// to them or watches that dows not exist.
        		Error error = responseUtils.buildError(1003L, new Object[][]{null, {watchesDTO.size(), result}});
        		// Conflict response with error information
        		return Response.status(Response.Status.CONFLICT).entity(error).build();
        	}
        	else
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
