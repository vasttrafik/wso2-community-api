package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.impl.TagsApiServiceImpl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Tags {
	
	private final TagsApiServiceImpl delegate = new TagsApiServiceImpl();
	
	@GET
    public Response getTags(
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getTags(offset, limit);
    }
}
