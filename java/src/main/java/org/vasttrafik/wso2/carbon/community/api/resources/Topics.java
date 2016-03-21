package org.vasttrafik.wso2.carbon.community.api.resources;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vasttrafik.wso2.carbon.community.api.beans.Topic;
import org.vasttrafik.wso2.carbon.community.api.impl.TopicsApiServiceImpl;

/**
 * 
 * @author Lars Andersson
 *
 */
@Path("/topics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class Topics {
	
	private final TopicsApiServiceImpl delegate = new TopicsApiServiceImpl();
	
	private static final Log log = LogFactory.getLog(Topics.class);

    @GET
    public Response getTopics(
    		@QueryParam("label") final String label,
    		@QueryParam("query") final String query,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getTopics(label, query, offset, limit);
    }
    
    @POST
    public Response postTopic(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@NotNull(message= "{topic.notnull}") @Valid final Topic topic
    ) 
    	throws ClientErrorException 
    {
    	log.error("postTopic");
        return delegate.createTopic(authorization, topic);
    }
    
    @GET
    @Path("/{id}")
    public Response getTopic(@PathParam("id") final Long id) 
    	throws ClientErrorException 
    {
        return delegate.getTopic(id);
    }
    
    @PUT
    @Path("/{id}")
    public Response putTopic(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id, 
    		@QueryParam("action") final String action, 
    		@NotNull(message= "{topic.notnull}") @Valid final Topic topic
    ) 
    	throws ClientErrorException 
    {
        return delegate.updateTopic(authorization, id, action, topic);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteTopic(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id
    ) 
    	throws ClientErrorException 
    {
        return delegate.deleteTopic(authorization, id);
    }
    
    @GET
    @Path("/{id}/posts")
    public Response getTopicPosts(
    		@HeaderParam("If-Modified-Since") final String ifModifiedSince,
    		@PathParam("id") final Long topicId,
    		@QueryParam("offset") @Min(1) @DefaultValue("1") final Integer offset,
    		@QueryParam("limit") @Min(1) @Max(50) @DefaultValue("10") final Integer limit
    ) 
    	throws ClientErrorException 
    {
        return delegate.getPosts(ifModifiedSince, topicId, offset, limit);
    }
    
    @GET
    @Path("/{id}/votes")
    public Response getTopicVotes(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long topicId,
    		@QueryParam("memberId") final Integer memberId) 
    	throws ClientErrorException 
    {
        return delegate.getVotes(authorization, topicId, memberId);
    }
    
    @POST
    @Path("/{id}/watches")
    public Response postTopicWatch(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long topicId) 
    	throws ClientErrorException 
    {
        return delegate.createWatch(authorization, topicId);
    }
    
    @DELETE
    @Path("/{id}/watches/{watchId}")
    public Response deleteTopicWatch(
    		@NotNull(message= "{assertion.notnull}") @HeaderParam("X-JWT-Assertion") final String authorization,
    		@PathParam("id") final Long id,
    		@PathParam("watchId") final Long watchId) 
    	throws ClientErrorException 
    {
        return delegate.deleteWatch(authorization, watchId);
    }
}

