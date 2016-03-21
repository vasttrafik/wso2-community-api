package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Message  {
  
	private Long id = null;
	private Long originalId = null;
	private Long folderId = null;
	private String subject = null;
	private String body = null;
	private Boolean htmlEnabled = null;
	private Date createDate = null;
	private Date readDate = null;
	private Member sender = null;
	private List<Member> recipients = new ArrayList<Member>();
	private Integer numberOfAttachments = null;
	private List<Attachment> attachments = new ArrayList<Attachment>();

	/**
	 * Message id
	 **/
	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}

	public Long getOriginalId() {
		return originalId;
	}

	public void setOriginalId(Long originalId) {
		this.originalId = originalId;
	}

	/**
	 * The folder this message belongs to
	 **/
	public Long getFolderId() {
		return folderId;
	}
  
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	/**
	 * The subject of the message
	 **/
	public String getSubject() {
		return subject;
	}
  
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * The text of the message
	 **/
	public String getBody() {
		return body;
	}
  
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Whether html rendering is enabled
	 **/
	public Boolean getHtmlEnabled() {
		return htmlEnabled;
	}
  
	public void setHtmlEnabled(Boolean htmlEnabled) {
		this.htmlEnabled = htmlEnabled;
	}

	/**
	 * Date and time when the message was submitted
	 **/
	public Date getCreateDate() {
		return createDate;
	}
  
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	/**
	 * The user that submitted the message
	 **/
	public Member getSender() {
		return sender;
	}
  
	public void setSener(Member sender) {
		this.sender = sender;
	}

	/**
	 * The user that submitted the message
	 **/
	public List<Member> getRecipients() {
		return recipients;
	}
  
	public void setRecipients(List<Member> recipients) {
		this.recipients = recipients;
	}

	public Integer getNumberOfAttachments() {
		return numberOfAttachments;
	}

	public void setNumberOfAttachments(Integer numberOfAttachments) {
		this.numberOfAttachments = numberOfAttachments;
	}

	/**
	 * Attachments
	 **/
	public List<Attachment> getAttachments() {
		return attachments;
	}
  
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Message {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  folderId: ").append(folderId).append("\n");
		sb.append("  subject: ").append(subject).append("\n");
		sb.append("  body: ").append(body).append("\n");
		sb.append("  htmlEnabled: ").append(htmlEnabled).append("\n");
		sb.append("  createDate: ").append(createDate).append("\n");
		sb.append("  sender: ").append(sender).append("\n");
		sb.append("  recipients: ").append(recipients).append("\n");
		sb.append("  attachments: ").append(attachments).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
