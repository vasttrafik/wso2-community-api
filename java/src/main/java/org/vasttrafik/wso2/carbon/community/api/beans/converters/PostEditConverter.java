package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.PostEdit;
import org.vasttrafik.wso2.carbon.community.api.model.PostEditDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class PostEditConverter {

	public List<PostEdit> convert(List<PostEditDTO> editDTOs) {
		List<PostEdit> edits = new ArrayList<PostEdit>();
		for (PostEditDTO editDTO : editDTOs) {
			PostEdit edit = convert(editDTO);
			edits.add(edit);
		}
		return edits;
	}
	
	public PostEdit convert(PostEditDTO editDTO) {
		PostEdit edit = new PostEdit();
		edit.setCreateDate(editDTO.getCreateDate());
		edit.setId(editDTO.getId());
		edit.setTextFormat(editDTO.getTextFormat());
		edit.setPostId(editDTO.getPostId());
		edit.setText(editDTO.getText());
		edit.setVersion(editDTO.getEditVersion());
		Member createdBy = new Member();
		createdBy.setId(editDTO.getCreatedById());
		edit.setCreatedBy(createdBy); 
		return edit;
	}
}
