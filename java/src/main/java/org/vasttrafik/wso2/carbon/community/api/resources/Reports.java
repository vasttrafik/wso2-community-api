package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.beans.Report;
import org.vasttrafik.wso2.carbon.community.api.beans.ReportMeasure;
import org.vasttrafik.wso2.carbon.community.api.impl.ReportsApiServiceImpl;
import org.vasttrafik.wso2.carbon.community.api.impl.utils.CacheControl;

/**
 * 
 * @author Lars Andersson
 *
 */
@SuppressWarnings("unused")
@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Reports {
	
	private ReportsApiServiceImpl delegate = new ReportsApiServiceImpl();
	
	/*
	@GET 
	@CacheControl("no-cache")
    public Response getReports(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@HeaderParam("If-Modified-Since") final String ifModifiedSince,
    		@QueryParam("measure") final String measure,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getReports(authorization, ifModifiedSince, measure, offset, limit);
    }
	
	@POST 
    public Response submitReport(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{report.notnull}") @Valid final Report report
    ) 
    	throws ClientErrorException 
    {
        return delegate.createReport(authorization, report);
    }
    
    @GET
    @Path("/{id}")
    @CacheControl("no-cache")
    public Response getReport(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.getReport(authorization, id);
    }
    
    @PUT
    @Path("/{id}")
    public Response putReport(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@NotNull(message= "{report.notnull}") @Valid final Report report
    ) 
    	throws ClientErrorException 
    {
        return delegate.updateReport(authorization, id, report);
    }
    
    @POST 
    @Path("/{id}/measures")
    public Response submitMeasure(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@NotNull(message= "{measure.notnull}") @Valid final ReportMeasure measure
    ) 
    	throws ClientErrorException 
    {
        return delegate.createMeasure(authorization, id, measure);
    }
    */
}
