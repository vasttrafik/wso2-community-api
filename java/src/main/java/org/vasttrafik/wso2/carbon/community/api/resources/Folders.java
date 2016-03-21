package org.vasttrafik.wso2.carbon.community.api.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.beans.Folder;
import org.vasttrafik.wso2.carbon.community.api.impl.FoldersApiServiceImpl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/folders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Folders {
	
	private FoldersApiServiceImpl delegate = new FoldersApiServiceImpl();
   
    @POST
    public Response postFolder(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{folder.notnull}") @Valid final Folder folder
    ) 
    	throws ClientErrorException 
    {
        return delegate.createFolder(authorization, folder);
    }
    
    @GET
    @Path("/{id}")
    public Response getFolder(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    )
    	throws ClientErrorException 
    {
        return delegate.getFolder(authorization, id);
    }
    
    @PUT
    @Path("/{id}")
    public Response putFolder(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.updateFolder(authorization, id);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteFolder(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.deleteFolder(authorization, id);
    }
    
    @GET
    @Path("/{id}/messages")
    public Response getFolderMessages(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@QueryParam("query") final String query,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    )
    	throws ClientErrorException 
    {
        return delegate.getFolderMessages(authorization, id, query, offset, limit);
    }
    
    @PUT
    @Path("/{id}/messages")
    public Response moveFolderMessages(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@NotNull(message= "{folder.target.notnull}") @QueryParam("targetFolder") final Long targetFolder,
    		@NotNull(message= "{folder.messages.notnull}") List<Long> messages) 
    	throws ClientErrorException 
    {
        return delegate.moveFolderMessages(authorization, id, targetFolder, messages);
    }
    
    @DELETE
    @Path("/{id}/messages")
    public Response deleteFolderMessages(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@NotNull(message= "{folder.messages.notnull}") List<Long> messages
    ) 
    	throws ClientErrorException 
    {
        return delegate.deleteFolderMessages(authorization, id, messages);
    }
}

