package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Tag;
import org.vasttrafik.wso2.carbon.community.api.model.TagDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class TagConverter {

	public List<Tag> convert(List<TagDTO> tagDTOs) {
		List<Tag> tags = new ArrayList<Tag>(tagDTOs.size());
		for (TagDTO dto : tagDTOs) {
			Tag tag = new Tag();
			tag.setId(dto.getId());
			tag.setLabel(dto.getLabel());
			tags.add(tag);
		}
		return tags;
	}
}
