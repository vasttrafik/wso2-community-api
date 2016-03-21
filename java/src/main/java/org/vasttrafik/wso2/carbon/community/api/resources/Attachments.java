package org.vasttrafik.wso2.carbon.community.api.resources;

/*
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
*/
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.impl.AttachmentsApiServiceImpl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/attachments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Attachments  {
	
	private final AttachmentsApiServiceImpl delegate = new AttachmentsApiServiceImpl();
	
   /*
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response postAttachments(
    		@HeaderParam("Authorization") String authorization,
    		@FormDataParam("attachment") FormDataBodyPart body) 
    	throws ClientErrorException 
    {
    	for(BodyPart part : body.getParent().getBodyParts()){
            InputStream is = part.getEntityAs(InputStream.class);
            ContentDisposition meta = part.getContentDisposition();
            // do something with file
        }
    	
        return null;
    }*/
    
    @GET
    @Path("/{id}")
    public Response getAttachment(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.getAttachment(authorization, id);
    }
}

