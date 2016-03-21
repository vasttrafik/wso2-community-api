package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Forum;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.model.ForumDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class ForumConverter {
	
	public List<Forum> convert(List<ForumDTO> forumsDTO) {
		List<Forum> forums = new ArrayList<Forum>(forumsDTO.size());
		
		for (Iterator<ForumDTO> it = forumsDTO.iterator(); it.hasNext();) {
			ForumDTO forumDTO = it.next();
			Forum forum = convert(forumDTO);
			forums.add(forum);
		}
		
		return forums;
	}
	
	public Forum convert(ForumDTO forumDTO) {
		Forum forum = new Forum();
		forum.setId(forumDTO.getId());
		forum.setCategoryId(forumDTO.getCategoryId());
		forum.setName(forumDTO.getName());
		forum.setDescription(forumDTO.getDesc());
		forum.setImageURL(forumDTO.getImageUrl());
		forum.setNumberOfTopics(forumDTO.getNumTopics());
		forum.setNumberOfPosts(forumDTO.getNumPosts());
		
		Long lastPostId = forumDTO.getLastPostId();
		
		if (lastPostId != null) {
			Post post = new Post();
			post.setId(lastPostId);
			forum.setLastPost(post);
		}
		
		return forum;
	}
	
	public ForumDTO convert(Forum forum) {
		ForumDTO forumDTO = new ForumDTO();
		forumDTO.setId(forum.getId());
		forumDTO.setCategoryId(forum.getCategoryId());
		forumDTO.setName(forum.getName());
		forumDTO.setDesc(forum.getDescription());
		forumDTO.setImageUrl(forum.getImageURL());
		return forumDTO;
	}
}
