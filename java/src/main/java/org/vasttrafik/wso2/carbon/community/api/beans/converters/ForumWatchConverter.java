package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import org.vasttrafik.wso2.carbon.community.api.beans.ForumWatch;
import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.model.ForumWatchDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class ForumWatchConverter {
	
	public ForumWatch convert(ForumWatchDTO watchDTO) {
		ForumWatch forumWatch = new ForumWatch();
		forumWatch.setId(watchDTO.getId());
		forumWatch.setForumId(watchDTO.getForumId());
		
		Member member = new Member();
		member.setId(watchDTO.getMemberId());
		forumWatch.setMember(member);
		
		return forumWatch;
	}
}
