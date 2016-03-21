package org.vasttrafik.wso2.carbon.community.api.impl;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.community.api.beans.Report;
import org.vasttrafik.wso2.carbon.community.api.beans.ReportMeasure;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class ReportsApiServiceImpl extends CommunityApiServiceImpl {
	
	public Response getReports(
    		String authorization,
    		String ifModifiedSince,
    		String measure,
    		Integer offset,
    		Integer limit
    ) 
    	throws ServerErrorException 
    {
        return null;
    }
	
    public Response createReport(String authorization, Report body) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response getReport(String authorization, Long id) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response updateReport(
    		String authorization,
    		Long id, 
    		Report report
    ) 
    	throws ServerErrorException 
    {
        return null;
    }
    
    public Response createMeasure(
    		String authorization,
    		Long id, 
    		ReportMeasure measure
    ) 
    	throws ServerErrorException 
    {
        return null;
    }

}
