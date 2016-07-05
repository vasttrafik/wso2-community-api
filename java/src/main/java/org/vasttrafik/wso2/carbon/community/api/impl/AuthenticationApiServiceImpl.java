package org.vasttrafik.wso2.carbon.community.api.impl;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.common.api.beans.AccessToken;
import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.beans.Credentials;
import org.vasttrafik.wso2.carbon.identity.api.utils.UserAdminUtils;
import org.vasttrafik.wso2.carbon.identity.oauth.authcontext.JWTToken;
import org.vasttrafik.wso2.carbon.identity.oauth.authcontext.JWTTokenGenerator;

/**
 * 
 * @author Lars Andersson
 *
 */
@SuppressWarnings("unused")
public final class AuthenticationApiServiceImpl extends CommunityApiServiceImpl {

	private static JWTTokenGenerator tokenGenerator = new JWTTokenGenerator();
	
	public Response authenticate(Credentials credentials) 
			throws ServerErrorException 
	{
		try {
			// Get username
			String userName = credentials.getUserName();
						
			if ("admin".equalsIgnoreCase(userName)) {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
							
			// Authenticate the user
			UserAdminUtils.authenticateCredentials(userName, credentials.getCredential());
						
			// Generate a token
			JWTToken jwtToken = tokenGenerator.generateToken(userName);
			// Create the response object
			AuthenticatedUser user = new AuthenticatedUser(jwtToken);
			// Create the response object
			return Response.status(201).entity(user).build();
		}
		catch(NotAuthorizedException | InternalServerErrorException ie) {
			return Response.status(Response.Status.UNAUTHORIZED)
				.entity(ie.getCause())
				.build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
}
