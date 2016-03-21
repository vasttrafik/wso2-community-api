package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.TopicWatch;
import org.vasttrafik.wso2.carbon.community.api.model.TopicWatchDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class TopicWatchConverter {

	public TopicWatch convert(TopicWatchDTO watchDTO) {
		TopicWatch topicWatch = new TopicWatch();
		topicWatch.setId(watchDTO.getId());
		topicWatch.setTopicId(watchDTO.getTopicId());
		
		Member member = new Member();
		member.setId(watchDTO.getMemberId());
		topicWatch.setMember(member);
		
		return topicWatch;
	}
}
