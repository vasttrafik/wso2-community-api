package org.vasttrafik.wso2.carbon.community.api.beans;

public final class Watch  {
	
	public enum TypeEnum {forum,  topic};
  
	private Long id = null;
	private TypeEnum type = null;
	private String title = null;
	private Integer forumId;
	private Long topicId;
	
	private Forum forum;
	private Topic topic;

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	/**
	 * Watch id
	 **/
	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * The watch type
	 **/
	public TypeEnum getType() {
		return type;
	}
  
	public void setType(TypeEnum type) {
		this.type = type;
	}

	/**
	 * Forum or topic title
	 **/
	public String getTitle() {
		return title;
	}
  
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getForumId() {
		return forumId;
	}

	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Watch {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  type: ").append(type).append("\n");
		sb.append("  title: ").append(title).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
