package org.vasttrafik.wso2.carbon.community.api.impl;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class RankingsApiServiceImpl extends CommunityApiServiceImpl {
	
	public Response getRankings(
    		String type,
    		String sorting,
    		Integer offset,
    		Integer limit
    ) 
    	throws ServerErrorException 
    {
        return null;
    }

}
