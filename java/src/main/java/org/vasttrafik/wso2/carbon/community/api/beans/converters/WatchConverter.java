package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Watch;
import org.vasttrafik.wso2.carbon.community.api.model.WatchDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class WatchConverter {
	
	public List<Watch> convertToBeans(List<WatchDTO> watchesDTO) {
		List<Watch> watches = new ArrayList<Watch>();
		for (WatchDTO watchDTO : watchesDTO) {
			Watch watch = convert(watchDTO);
			watches.add(watch);
		}
		return watches;
	}
	
	public List<WatchDTO> convertToDTOs(List<Watch> watches) {
		List<WatchDTO> watchesDTO = new ArrayList<WatchDTO>();
		for (Watch watch : watches) {
			WatchDTO watchDTO = convert(watch);
			watchesDTO.add(watchDTO);
		}
		return watchesDTO;
	}

	public Watch convert(WatchDTO watchDTO) {
		Watch watch = new Watch();
		watch.setId(watchDTO.getId());
		watch.setTitle(watchDTO.getTitle());
		watch.setForumId(watchDTO.getForumId());
		watch.setTopicId(watchDTO.getTopicId());
		
		if (watchDTO.getForumId() != null)
			watch.setType(Watch.TypeEnum.forum);
		else
			watch.setType(Watch.TypeEnum.topic);
		
		return watch;
	}
	
	public WatchDTO convert(Watch watch) {
		WatchDTO watchDTO = new WatchDTO();
		watchDTO.setId(watch.getId());
		watchDTO.setType(watch.getType().toString());
		return watchDTO;
	}
}
