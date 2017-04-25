package org.vasttrafik.wso2.carbon.community.api.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Watch;
import org.vasttrafik.wso2.carbon.community.api.impl.MembersApiServiceImpl;
import org.vasttrafik.wso2.carbon.community.api.impl.utils.CacheControl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Members  {
	
	private final MembersApiServiceImpl delegate = new MembersApiServiceImpl();
	
	@GET
    @CacheControl("no-cache")
    public Response getMembers(
    		@QueryParam("member") @NotNull(message= "{member.search.notnull}") final String member,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
	{
        return delegate.getMembers(member, offset, limit);
    }
	
	@POST 
    public Response postMember(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{member.notnull}") @Valid final Member member
    ) 
    	throws ClientErrorException 
    {
        return delegate.createMember(authorization, member);
    }

	@GET
    @Path("/{id}")
    @CacheControl("no-cache")
    public Response getMember(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id
    ) 
    	throws ClientErrorException 
	{
        return delegate.getMember(authorization, id);
    }
	
	@PUT
    @Path("/{id}")
    public Response putMember(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id, 
    		@NotNull(message= "{member.notnull}") @Valid final Member member
    ) 
    	throws ClientErrorException 
	{
        return delegate.updateMember(authorization, id, member);
    }
	
	@DELETE
    @Path("/{id}")
    @CacheControl("no-cache")
    public Response deleteMember(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id
    ) 
    	throws ClientErrorException 
	{
        return delegate.deleteMember(authorization, id);
    }
	
	@GET
    @Path("/{id}/folders")
    @CacheControl("no-cache")
    public Response getFolders(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id
    ) 
    	throws ClientErrorException 
	{
        return delegate.getFolders(authorization, id);
    }
    
    @GET
    @Path("/{id}/watches")
    public Response getWatches(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer memberId,
    		@QueryParam("setInfo") @DefaultValue("false") final boolean setInfo,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getWatches(authorization, memberId, setInfo, offset, limit);
    }
    
    @DELETE
    @Path("/{id}/watches")
    public Response deleteWatches(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id, 
    		@NotNull(message= "{watches.notnull}") final List<Watch> watches) 
    	throws ClientErrorException 
    {
        return delegate.deleteWatches(authorization, id, watches);
    }
}

