package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.community.api.impl.RankingsApiServiceImpl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/rankings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Rankings {
	
	private RankingsApiServiceImpl delegate = new RankingsApiServiceImpl();

    @GET
    public Response getRankings(
    		@QueryParam("type") @DefaultValue("reputation") final String type,
    		@QueryParam("sorting") @DefaultValue("desc") final String sorting,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getRankings(type, sorting, offset, limit);
    }
}

