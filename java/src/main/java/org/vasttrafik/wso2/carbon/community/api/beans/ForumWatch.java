package org.vasttrafik.wso2.carbon.community.api.beans;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class ForumWatch  {
  
	private Long id = null;
	private Integer forumId = null;
	private Member member = null;

	/**
	 * Forum watch id
	 **/
	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}
  
	/**
	 * The forum this watch belongs to
	 **/
	public Integer getForumId() {
		return forumId;
	}
  
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
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
		sb.append("class ForumWatch {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  forumId: ").append(forumId).append("\n");
		sb.append("  member: ").append(member).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
