package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.Vote;
import org.vasttrafik.wso2.carbon.community.api.impl.PostsApiServiceImpl;
import org.vasttrafik.wso2.carbon.community.api.impl.utils.CacheControl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Posts {
	
	private final PostsApiServiceImpl delegate = new PostsApiServiceImpl();
    
	@GET
    @CacheControl("no-cache")
    public Response getPosts(
    		@QueryParam("label") final String label,
    		@QueryParam("query") final String query,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getPosts(label, query, offset, limit);
    }
	
	@POST 
    public Response postPost(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{post.notnull}") @Valid final Post post
    ) 
    	throws ClientErrorException 
    {
        return delegate.createPost(authorization, post);
    }
    
    @GET
    @Path("/{id}")
    @CacheControl("no-cache")
    public Response getPost(@PathParam("id") final Long id) 
    	throws ClientErrorException 
    {
        return delegate.getPost(id);
    }
    
    @PUT
    @Path("/{id}")
    public Response putPost(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@QueryParam("action") final String action, 
    		@NotNull(message= "{post.notnull}") @Valid Post body
    ) 
    	throws ClientErrorException 
    {
        return delegate.updatePost(authorization, id, action, body);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletePost(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.deletePost(authorization, id);
    }
    
    @GET
    @Path("/{id}/edits")
    @CacheControl("no-cache")
    public Response getEdits(@PathParam("id") final Long id) 
    	throws ClientErrorException 
    {
        return delegate.getPostEdits(id);
    }
    
    @POST
    @Path("/{id}/votes")
    public Response postVote(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@NotNull(message= "{post.notnull}") @Valid final Vote vote
    ) 
    	throws ClientErrorException 
    {
        return delegate.createVote(authorization, id, vote);
    }
}

