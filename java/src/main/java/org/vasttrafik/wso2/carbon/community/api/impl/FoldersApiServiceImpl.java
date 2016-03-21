package org.vasttrafik.wso2.carbon.community.api.impl;

import java.util.List;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.community.api.beans.Folder;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class FoldersApiServiceImpl extends CommunityApiServiceImpl {
	
	public Response createFolder(String authorization, Folder folder) 
    	throws ServerErrorException 
    {
        return null;
    }
	
	public Response getFolder(String authorization, Long id)
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response updateFolder(String authorization, Long id) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response deleteFolder(String authorization, Long id) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response getFolderMessages(
    		String authorization,
    		Long id, 
    		String query,
    		Integer offset,
    		Integer limit
    )
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response moveFolderMessages(
    		String authorization,
    		Long id, 
    		Long targetFolder,
    		List<Long> messages
    ) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response deleteFolderMessages(
    		String authorization,
    		Long id, 
    		List<Long> messages
    ) 
    	throws ServerErrorException 
    {
        return null;
    }
}
