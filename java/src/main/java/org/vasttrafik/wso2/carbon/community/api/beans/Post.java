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
public final class Post  {
  
	public enum TypeEnum {question,  answer,  comment, report};  
	public enum FormatEnum {html,  md,  plain};  
	
	private Long id = null;
	@NotNull(message = "{post.topic.notnull}")
	private Long topicId = null;
	@NotNull(message = "{post.forum.notnull}")
	private Integer forumId = null;
	private Integer categoryId = null;
	@NotNull(message = "{post.text.notnull}")
	private String text = null;
	@NotNull(message = "{post.textFormat.notnull}")
	private FormatEnum textFormat = null;
	@NotNull(message = "{post.type.notnull}")
	private TypeEnum type = null;
	private Date createDate = null;
	private Member createdBy = null;
	private Post commentTo = null;
	private Short pointsAwarded = null;
	private Boolean isAnswer = null;
	private Date editDate = null;
	private Member editedBy = null;
	private Short numberOfTimesEdited = null;
	private List<PostEdit> edits = new ArrayList<PostEdit>();
	private List<Report> reports = new ArrayList<Report>();
	private List<Vote> votes = new ArrayList<Vote>();
	private Boolean isModerated = null;
	private Boolean isDeleted = null;
	private Boolean isReported = null;
  
	/**
	 * Post id
	 **/
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	/**
	 * The topic this post was submitted to
	 **/
	public Long getTopicId() {
		return topicId;
	}
  
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Integer getForumId() {
		return forumId;
	}

	public void setForumId(Integer forumId) {
  		this.forumId = forumId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * The text of the post
	 **/
	public String getText() {
		return text;
	}
  
	public void setText(String text) {
		this.text = text;
	}

	public FormatEnum getTextFormat() {
		return textFormat;
	}

	public void setTextFormat(FormatEnum textFormat) {
		this.textFormat = textFormat;
	}

	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	/**
	 * Date and time when the post was submitted
	 **/
	public Date getCreateDate() {
		return createDate;
	}
  
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * The user that submitted the post
	 **/
	public Member getCreatedBy() {
		return createdBy;
	}
  
	public void setCreatedBy(Member createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Date and time the post was last edited
	 */
	public Date getEditDate() {
	  return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public Member getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(Member editedBy) {
		this.editedBy = editedBy;
	}

	/**
	 * The number of times the post has been edited
	 */
	public Short getNumberOfTimesEdited() {
		return numberOfTimesEdited;
	}

	public void setNumberOfTimesEdited(Short numberOfTimesEdited) {
		this.numberOfTimesEdited = numberOfTimesEdited;
	}

	public List<PostEdit> getEdits() {
		return edits;
	}

	public void setEdits(List<PostEdit> edits) {
		this.edits = edits;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	
	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	/**
	 * If the post is a reply to some other post
	 **/
	public Post getCommentTo() {
		return commentTo;
	}
  
	public void setCommentTo(Post commentTo) {
		this.commentTo = commentTo;
	}

	/**
	 * Points awarded by votes
	 **/
	public Short getPointsAwarded() {
		return pointsAwarded;
	}
  
	public void setPointsAwarded(Short pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

	/**
	 * If there is a post that has been accepted as an answer the question
	 **/
	public Boolean getIsAnswer() {
		return isAnswer;
	}
  
	public void setIsAnswer(Boolean isAnswer) {
		this.isAnswer = isAnswer;
	}

	/**
	 * If the post has been moderated
	 **/
	public Boolean getIsModerated() {
		return isModerated;
	}
  
	public void setIsModerated(Boolean isModerated) {
		this.isModerated = isModerated;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Post {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  topicId: ").append(topicId).append("\n");
		sb.append("  forumId: ").append(forumId).append("\n");
		sb.append("  text: ").append(text).append("\n");
		sb.append("  type: ").append(type).append("\n");
		sb.append("  createDate: ").append(createDate).append("\n");
		sb.append("  createdBy: ").append(createdBy).append("\n");
		sb.append("  commentTo: ").append(commentTo).append("\n");
		sb.append("  pointsAwarded: ").append(pointsAwarded).append("\n");
		sb.append("  isAnswer: ").append(isAnswer).append("\n");
		sb.append("  editDate: ").append(editDate).append("\n");
		sb.append("  editedBy: ").append(editedBy).append("\n");
		sb.append("  numberOfTimesEdited: ").append(numberOfTimesEdited).append("\n");
		sb.append("  edits: ").append(edits).append("\n");
		sb.append("  isModerated: ").append(isModerated).append("\n");
		sb.append("  isReported: ").append(isReported).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
