package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.beans.Forum;
import org.vasttrafik.wso2.carbon.community.api.impl.ForumsApiServiceImpl;
import org.vasttrafik.wso2.carbon.community.api.impl.utils.CacheControl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/forums")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Forums {
	
	private final ForumsApiServiceImpl delegate = new ForumsApiServiceImpl();
   
    @GET
    @CacheControl("no-cache")
    public Response getForums(
    		@QueryParam("name") final String name,
    		@QueryParam("label") final String label,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    )
    	throws ClientErrorException 
    {
        return delegate.getForums(name, label, offset, limit);
    }
    
    @POST
    public Response postForum(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{forum.notnull}") @Valid final Forum forum
    ) 
    	throws ClientErrorException 
    {
        return delegate.createForum(authorization, forum);
    }
    
    @GET
    @Path("/{id}")
    @CacheControl("no-cache")
    public Response getForum(@PathParam("id") final Integer id) 
    	throws ClientErrorException
    {
        return delegate.getForum(id);
    }
    
    @PUT
    @Path("/{id}")
    public Response putForum(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id,
    		@NotNull(message= "{forum.notnull}") @Valid final Forum forum
    ) 
    	throws ClientErrorException 
    {
        return delegate.updateForum(authorization, id, forum);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteForum(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id
    ) 
    	throws ClientErrorException 
    {
        return delegate.deleteForum(authorization, id);
    }
    
   
    @GET
    @Path("/{id}/topics")
    public Response getTopics(
    		@HeaderParam("If-Modified-Since") final String ifModifiedSince, 
    		@PathParam("id") final Integer forumId,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getTopics(ifModifiedSince, forumId, offset, limit);
    }
    
    /*
    @POST
    @Path("/{id}/watches")
    public Response postWatch(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer forumId
    ) 
    	throws ClientErrorException 
    {
        return delegate.createWatch(authorization, forumId);
    }
    
    @DELETE
    @Path("/{id}/watches/{watchId}")
    public Response deleteWatch(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id,
    		@PathParam("watchId") final Long watchId
    )
    	throws ClientErrorException 
    {
        return delegate.deleteWatch(authorization, watchId);
    }
    */
}

