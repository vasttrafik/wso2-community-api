package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.Date;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class PostEdit {

	private Long id = null;
	private Long postId = null;
	private Short version = null;
	private String text = null;
	private String textFormat = null;
	private Member createdBy = null;
	private Date createDate = null;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Short getVersion() {
		return version;
	}
	
	public void setVersion(Short version) {
		this.version = version;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getTextFormat() {
		return textFormat;
	}

	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}

	public Member getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Member createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
