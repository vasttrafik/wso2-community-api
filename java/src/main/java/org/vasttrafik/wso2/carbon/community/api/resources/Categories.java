package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.beans.Category;
import org.vasttrafik.wso2.carbon.community.api.impl.CategoriesApiServiceImpl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Categories  {
	
	private final CategoriesApiServiceImpl delegate = new CategoriesApiServiceImpl();
  
    @GET
    public Response getCategories(
    		@QueryParam("includePrivate") @DefaultValue("false") final Boolean includePrivate,
    		@QueryParam("includeForums")  @DefaultValue("false") final Boolean includeForums
    ) 
    	throws ClientErrorException, ServerErrorException
    {
        return delegate.getCategories(includePrivate, includeForums);
    }
    
    @POST
    public Response postCategory(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{category.notnull}") @Valid final Category body
    ) 
    	throws ClientErrorException, ServerErrorException
    {
        return delegate.createCategory(authorization, body);
    }
    
    @GET
    @Path("/{id}")
    public Response getCategory(
    		@PathParam("id") final Integer id
    ) 
    	throws ClientErrorException, ServerErrorException
    {
        return delegate.getCategory(id);
    }
    
    @PUT
    @Path("/{id}")
    public Response putCategory(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id, 
    		@NotNull(message= "{category.notnull}") @Valid final Category body
    ) 
    	throws ClientErrorException, ServerErrorException
    {
        return delegate.updateCategory(authorization, body);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCategory(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Integer id
    ) 
    	throws ClientErrorException, ServerErrorException 
    {
        return delegate.deleteCategory(authorization, id);
    }
    
    @GET
    @Path("/{id}/forums")
    public Response getForums(
    		@HeaderParam("If-Modified-Since") final String ifModifiedSince, 
    		@PathParam("id") final Integer id,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException, ServerErrorException 
    {
        return delegate.getForums(ifModifiedSince, id, offset, limit);
    }
}

