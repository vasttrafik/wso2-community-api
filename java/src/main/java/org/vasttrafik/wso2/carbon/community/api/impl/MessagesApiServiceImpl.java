package org.vasttrafik.wso2.carbon.community.api.impl;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.community.api.beans.Message;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class MessagesApiServiceImpl extends CommunityApiServiceImpl {
	
	public Response createMessage(String authorization, Message message) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response getMessage(String authorization, Long id) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response deleteMessage(String authorization, Long id) 
    	throws ServerErrorException 
    {
        return null;
    }

}
