package org.vasttrafik.wso2.carbon.community.api.beans;

public final class TopicWatch  {
  
	private Long id = null;
	private Long topicId = null;
	private Member member = null;

	/**
	 * Topic watch id
	 **/
	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}
  
	/**
	 * Topic id
	 **/
	public Long getTopicId() {
		return topicId;
	}
  
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	/**
	 * The user watching a topic
	 **/
	public Member getMember() {
		return member;
	}
  
	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class TopicWatch {\n");
		sb.append("  topicId: ").append(topicId).append("\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  member: ").append(member).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
