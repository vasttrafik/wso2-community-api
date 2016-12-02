package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.beans.Message;
import org.vasttrafik.wso2.carbon.community.api.impl.MessagesApiServiceImpl;
import org.vasttrafik.wso2.carbon.community.api.impl.utils.CacheControl;

/**
 * 
 * @author Lars Andersson
 *
 */
@SuppressWarnings("unused")
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Messages  {
	
	private MessagesApiServiceImpl delegate = new MessagesApiServiceImpl();
   
	/*
    @POST
    public Response postMessage(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{message.notnull}") @Valid final Message message
    ) 
    	throws ClientErrorException 
    {
        return delegate.createMessage(authorization, message);
    }
    
    @GET
    @Path("/{id}")
        @CacheControl("no-cache")
    public Response getMessage(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.getMessage(authorization, id);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteMessage(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.deleteMessage(authorization, id);
    }
    */
}

