package org.vasttrafik.wso2.carbon.community.api.impl.utils;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.PostEdit;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.MemberConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostConverter;
import org.vasttrafik.wso2.carbon.community.api.beans.converters.PostEditConverter;
import org.vasttrafik.wso2.carbon.community.api.dao.MemberDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.PostEditDAO;
import org.vasttrafik.wso2.carbon.community.api.dao.commons.DAOProvider;
import org.vasttrafik.wso2.carbon.community.api.model.MemberDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;
import org.vasttrafik.wso2.carbon.community.api.model.PostEditDTO;

public class GenerateUtil {
	
	/**
	 * Converting between post bean and DTO
	 */
	private static PostConverter postConverter = new PostConverter();
	
	/**
	 * Converting between member bean and DTO
	 */
	private static MemberConverter memberConverter = new MemberConverter();
	
	public static Post generatePost(PostDTO postDTO) throws SQLException {
		// Convert the result
		Post post = postConverter.convert(postDTO);
		// Get a DAO to look up member information
		MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
		// Get the created by
		MemberDTO memberDTO = memberDAO.find(post.getCreatedBy().getId());
		// Convert to bean
		Member member = memberConverter.convert(memberDTO);
		// Assign it
		post.setCreatedBy(member);

		// Retrieve dit information
		if (post.getNumberOfTimesEdited() > 0) {
			// Get the created by
			memberDTO = memberDAO.find(post.getEditedBy().getId());
			// Convert to bean
			member = memberConverter.convert(memberDTO);
			// Assign it
			post.setEditedBy(member);
			// Get the edits
			List<PostEdit> edits = getEdits(post.getId());
			post.setEdits(edits);
		}

		return post;
	}
	
	protected static List<PostEdit> getEdits(Long postId) throws ServerErrorException {
		try {
			// Get the DAO implementation
        	PostEditDAO postDAO = DAOProvider.getDAO(PostEditDAO.class);
        	// Perform the search
        	List<PostEditDTO> editDTOs = postDAO.findByPost(postId);
        	// Convert the result
        	PostEditConverter converter = new PostEditConverter();
        	List<PostEdit> edits = converter.convert(editDTOs);
        	
        	// Add member to PostEdits
        	for(PostEdit edit : edits) {
        		// Get a DAO to look up member information
        		MemberDAO memberDAO = DAOProvider.getDAO(MemberDAO.class);
        		// Get the created by
        		MemberDTO memberDTO = memberDAO.find(edit.getCreatedBy().getId());
        		// Convert to bean
        		Member member = memberConverter.convert(memberDTO);
        		// Assign it
        		edit.setCreatedBy(member);
        	}
        	
        	// Return response
        	return edits;
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}

}
