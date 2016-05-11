package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Topic  {
  
	private Long id = null;
	private Integer categoryId = null;
	private String categoryName = null;
	@NotNull(message = "{topic.forum.notnull}")
	private Integer forumId = null;
	private String forumName = null;
	@NotNull(message = "{topic.subject.notnull}")
	private String subject = null;
	private Date createDate = null;
	private Date lastPostDate = null;
	private Member createdBy = null;
	private List<Tag> tags = new ArrayList<Tag>();
	@NotNull(message = "{topic.posts.notnull}")
	private List<Post> posts = new ArrayList<Post>();
	private Post firstPost = null;
	private Post lastPost = null;
	private Date closedDate = null;
	private Member closedBy = null;
	private Short numberOfPosts = null;
	private Short numberOfViews = null;
	private Short numberOfAnswers = null;
	private Long answeredByPostId = null;
	private Boolean isDeleted = null;

	/**
	 * Topic id
	 **/
	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}
  
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * The forum this topic was created in
	 **/
	public Integer getForumId() {
		return forumId;
	}
  
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	/**
	 * Topic subject
	 **/
	public String getSubject() {
		return subject;
	}
  
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Date and time when the topic was created
	 **/
	public Date getCreateDate() {
		return createDate;
	}
  
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Date and time when the last post as posted to this topic
	 * This is only applicable from using view
	 **/
	public Date getLastPostDate() {
		return lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}

	/**
	 * The user that created the topic
	 **/
	public Member getCreatedBy() {
		return createdBy;
	}
  
	public void setCreatedBy(Member createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * Tags for the topic
	 **/
	public List<Tag> getTags() {
		return tags;
	}
  
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * Posts in the topic
	 **/
	public List<Post> getPosts() {
		return posts;
	}
  
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
  
	/**
	 * The last post in the forum
	 **/
	public Post getFirstPost() {
		return firstPost;
	}
  
	public void setFirstPost(Post firstPost) {
		this.firstPost = firstPost;
	}

	/**
	 * The last post in the forum
	 **/
	public Post getLastPost() {
		return lastPost;
	}
  
	public void setLastPost(Post lastPost) {
		this.lastPost = lastPost;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	
	/**
	 * The user that created the topic
	 **/
	public Member getClosedBy() {
		return closedBy;
	}
  
	public void setClosedBy(Member closedBy) {
		this.closedBy = closedBy;
	}

	public Short getNumberOfPosts() {
		return numberOfPosts;
	}

	public void setNumberOfPosts(Short numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}

	public Short getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(Short numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Short getNumberOfAnswers() {
		return numberOfAnswers;
	}

	public void setNumberOfAnswers(Short numberOfAnswers) {
		this.numberOfAnswers = numberOfAnswers;
	}

	public Long getAnsweredByPostId() {
		return answeredByPostId;
	}

	public void setAnsweredByPostId(Long answeredByPostId) {
		this.answeredByPostId = answeredByPostId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Topic {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  categoryId: ").append(categoryId).append("\n");
		sb.append("  categoryName: ").append(categoryName).append("\n");
		sb.append("  forumId: ").append(forumId).append("\n");
		sb.append("  forumName: ").append(forumName).append("\n");
		sb.append("  subject: ").append(subject).append("\n");
		sb.append("  createDate: ").append(createDate).append("\n");
		sb.append("  createdBy: ").append(createdBy).append("\n");
		sb.append("  tags: ").append(tags).append("\n");
		sb.append("  posts: ").append(posts).append("\n");
		sb.append("  firstPost: ").append(firstPost).append("\n");
		sb.append("  lastPost: ").append(lastPost).append("\n");
		sb.append("  numberOfPosts: ").append(numberOfPosts).append("\n");
		sb.append("  numberOfViews: ").append(numberOfViews).append("\n");
		sb.append("  numberOfAnswers: ").append(numberOfAnswers).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
