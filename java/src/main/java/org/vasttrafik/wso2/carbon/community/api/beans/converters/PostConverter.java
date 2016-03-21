package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Member;
import org.vasttrafik.wso2.carbon.community.api.beans.Post;
import org.vasttrafik.wso2.carbon.community.api.model.PostDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class PostConverter {

	public List<Post> convert(List<PostDTO> postDTOs) {
		List<Post> posts = new ArrayList<Post>();
		for (PostDTO postDTO : postDTOs) {
			Post post = convert(postDTO);
			posts.add(post);
		}
		return posts;
	}
	
	public PostDTO convert(Post post) {
		PostDTO postDTO = new PostDTO();
		postDTO.setCreateDate(post.getCreateDate());
		postDTO.setEditDate(post.getEditDate());
		postDTO.setForumId(post.getForumId());
		postDTO.setCategoryId(post.getCategoryId());
		postDTO.setId(post.getId());
		postDTO.setIsAnswer(post.getIsAnswer());
		postDTO.setIsDeleted(post.getIsDeleted());
		postDTO.setIsModerated(post.getIsModerated());
		postDTO.setTextFormat(post.getTextFormat().toString());
		postDTO.setEditCount(post.getNumberOfTimesEdited());
		postDTO.setPointsAwarded(post.getPointsAwarded());
		postDTO.setText(post.getText());
		postDTO.setTopicId(post.getTopicId());
		Post commentTo = post.getCommentTo();
		if (commentTo != null) postDTO.setCommentToId(commentTo.getId());
		Member createdBy = post.getCreatedBy();
		if (createdBy != null) postDTO.setCreatedById(createdBy.getId());
		Member editedBy = post.getEditedBy();
		if (editedBy != null) postDTO.setEditedById(editedBy.getId());
		Post.TypeEnum type = post.getType();
		if (type != null) postDTO.setType(type.toString());	
		return postDTO;
	}
	
	public Post convert(PostDTO postDTO) {
		Post post = new Post();
		post.setCreateDate(postDTO.getCreateDate());
		post.setEditDate(postDTO.getEditDate());
		post.setForumId(postDTO.getForumId());
		post.setCategoryId(postDTO.getCategoryId());
		post.setId(postDTO.getId());
		post.setIsAnswer(postDTO.getIsAnswer());
		post.setIsDeleted(postDTO.getIsDeleted());
		post.setIsModerated(postDTO.getIsModerated());
		post.setNumberOfTimesEdited(postDTO.getEditCount());
		post.setPointsAwarded(postDTO.getPointsAwarded());
		post.setText(postDTO.getText());
		post.setTextFormat(Post.FormatEnum.valueOf(postDTO.getTextFormat()));
		post.setTopicId(postDTO.getTopicId());
		
		if (postDTO.getCommentToId() != null) {
			Post commentTo = new Post();
			commentTo.setId(postDTO.getCommentToId());
			post.setCommentTo(commentTo); 
		}
		
		Member createdBy = new Member();
		createdBy.setId(postDTO.getCreatedById());
		post.setCreatedBy(createdBy); 	
		
		if (postDTO.getEditedById() != null) {
			Member editedBy = new Member();
			editedBy.setId(postDTO.getEditedById());
			post.setCreatedBy(editedBy);
		}
		
		Post.TypeEnum type = Post.TypeEnum.valueOf(postDTO.getType());
		post.setType(type);
		return post;
	}
}
