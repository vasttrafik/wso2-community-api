package org.vasttrafik.wso2.carbon.community.api.impl;

import java.util.List;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Tag;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.TagConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.TagDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.TagDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public class TagsApiServiceImpl extends CommunityApiServiceImpl {
	
	/**
	 * A tag converter
	 */
	private TagConverter converter = new TagConverter();
	
	/**
	 * Retrieves tags.
	 * @param offset If paging is required, the offset into the result set
	 * @param limit If paging is required, the number of rows to return
	 * @return A list of tags
	 * @throws ServerErrorException If internal error occurs
	 */
	public Response getTags(Integer offset, Integer limit) 
    	throws ServerErrorException
    {
        try {
        	// Get DAO implementation
        	TagDAO tagDAO = DAOProvider.getDAO(TagDAO.class);
        	// Get list of tags
        	List<TagDTO> tagsDTOs = tagDAO.findAll(offset, limit);
        	// Convert to beans
        	List<Tag> tags = converter.convert(tagsDTOs);
        	// Return result
        	return Response.status(200).entity(tags).build();
        }
        catch (Exception e) {
        	Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
        }
    }
}
