package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.beans.Topic;
import org.vasttrafik.wso2.carbon.community.api.model.TopicDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class TopicConverter {

	public List<Topic> convert(List<TopicDTO> topicDTOs) {
		List<Topic> topics = new ArrayList<Topic>(topicDTOs.size());
		for (TopicDTO topicDTO : topicDTOs) {
			Topic topic = convert(topicDTO);
			topics.add(topic);
		}
		return topics;
	}
	
	public Topic convert(TopicDTO topicDTO) {
		Topic topic = new Topic();
		
		topic.setClosedDate(topicDTO.getClosedDate());
		topic.setCreateDate(topicDTO.getCreatedDate());
		topic.setCategoryId(topicDTO.getCategoryId());
		topic.setCategoryName(topicDTO.getCategoryName());
		topic.setForumId(topicDTO.getForumId());
		topic.setForumName(topicDTO.getForumName());
		topic.setId(topicDTO.getId());
		topic.setIsDeleted(topicDTO.getIsDeleted()); 
		topic.setNumberOfPosts(topicDTO.getNumPosts()); 
		topic.setNumberOfAnswers(topicDTO.getNumAnswers()); 
		topic.setNumberOfViews(topicDTO.getNumViews()); 
		topic.setSubject(topicDTO.getSubject());
		topic.setAnsweredByPostId(topicDTO.getAnswerPostId());
		
		Member createdBy = new Member();
		createdBy.setId(topicDTO.getCreatedById());
		topic.setCreatedBy(createdBy); 
		
		if (topicDTO.getClosedById() != null) {
			Member closedBy = new Member();
			closedBy.setId(topicDTO.getClosedById());
			topic.setClosedBy(closedBy);
		}
		
		Post firstPost = new Post();
		firstPost.setId(topicDTO.getFirstPostId());
		topic.setFirstPost(firstPost);
		
		Post lastPost = new Post();
		lastPost.setId(topicDTO.getLastPostId());
		topic.setLastPost(lastPost);
		
		//List<Post> posts = new ArrayList<Post>();
		//topic.setPosts(posts); 
		
		return topic;
	}
	
	public TopicDTO convert(Topic topic) {
		TopicDTO topicDTO = new TopicDTO();
		
		topicDTO.setClosedDate(topic.getClosedDate());
		topicDTO.setCreatedDate(topic.getCreateDate());
		topicDTO.setForumId(topic.getForumId());
		topicDTO.setId(topic.getId());
		topicDTO.setIsDeleted(topic.getIsDeleted()); 
		topicDTO.setSubject(topic.getSubject());
		topicDTO.setAnswerPostId(topic.getAnsweredByPostId());
		Member createdBy = topic.getCreatedBy();
		if (createdBy != null) topicDTO.setCreatedById(createdBy.getId());
		Member closedBy = topic.getClosedBy();
		if (closedBy != null) topicDTO.setClosedById(closedBy.getId());
		return topicDTO;
	}
}
