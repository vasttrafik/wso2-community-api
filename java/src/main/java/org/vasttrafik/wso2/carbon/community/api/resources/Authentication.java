package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.common.api.beans.Credentials;
import org.vasttrafik.wso2.carbon.community.api.impl.AuthenticationApiServiceImpl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/authenticate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Authentication {
	
	private AuthenticationApiServiceImpl delegate = new AuthenticationApiServiceImpl();
	
	@POST
    public Response postAuthenticate(@NotNull(message= "{credentials.notnull}") @Valid final Credentials credentials) 
    	throws ClientErrorException 
    {
        return delegate.authenticate(credentials);
    }
}
