package org.vasttrafik.wso2.carbon.community.api.beans;

public final class Vote  {
  
	public enum TypeEnum {question,  answer};
  
	private Long id = null;
	private Long postId = null;
	private TypeEnum type = null;
	private Integer memberId = null;
	private Short points = null;

	/**
	 * Vote id
	 **/
	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * The post id
	 **/
	public Long getPostId() {
		return postId;
	}
  
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	/**
	 * The voting type
	 **/
	public TypeEnum getType() {
		return type;
	}
  
	public void setType(TypeEnum type) {
		this.type = type;
	}

	/**
	 * The user casting the vote
	 **/
	public Integer getMemberId() {
		return memberId;
	}
  
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	/**
	 * Number of points awarded
	 **/
	public Short getPoints() {
		return points;
	}
  
	public void setPoints(Short points) {
		this.points = points;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Vote {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  postId: ").append(postId).append("\n");
		sb.append("  type: ").append(type).append("\n");
		sb.append("  memberId: ").append(memberId).append("\n");
		sb.append("  points: ").append(points).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
