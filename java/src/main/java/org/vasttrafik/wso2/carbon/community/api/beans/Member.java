package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Member  {
	
	public enum StatusEnum {inactive,  active,  probation, banned, deleted};  
  
	@NotNull(message = "{member.id.notnull}")
	private Integer id = null;
	@NotNull(message = "{member.username.notnull}")
	private String userName;
	@NotNull(message = "{member.email.notnull}")
	private String email = null;
	private StatusEnum status = null;
	private Boolean showEmail = null;
	private String signature = null;
	private Boolean useGravatar = null;
	private String gravatarEmail = null;
	private String gravatarEmailHash = null;
	private Boolean acceptAllMessages = null;
	private Boolean notifyEmail = null;
	private Boolean notifyMessage = null;
	private Boolean notifyText = null;
	private Boolean showRankings = null;
	private List<MemberRanking> rankings = null;

	/**
	 * User id
	 **/
	public Integer getId() {
		return id;
	}
  
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * User email
	 **/
	public String getEmail() {
		return email;
	}
  
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * The status of the user: INACTIVE, ACTIVE, PROBATION, BANNED, DELETED
	 **/
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	/**
	 * Whether the email of the user should be displayed
	 **/
	public Boolean getShowEmail() {
		return showEmail;
	}
  
	public void setShowEmail(Boolean showEmail) {
		this.showEmail = showEmail;
	}

	/**
	 * Signature to use
	 **/
	public String getSignature() {
		return signature;
	}
  
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * Whether to use Gravatar
	 **/
	public Boolean getUseGravatar() {
		return useGravatar;
	}
  
	public void setUseGravatar(Boolean useGravatar) {
		this.useGravatar = useGravatar;
	}

	/**
	 * The Gravatar email of the user
	 **/
	public String getGravatarEmail() {
		return gravatarEmail;
	}
  
	public void setGravatarEmail(String gravatarEmail) {
		this.gravatarEmail = gravatarEmail;
	}

	/**
	 * The Gravatar email hash of the user
	 **/
	public String getGravatarEmailHash() {
		return gravatarEmailHash;
	}
  
	public void setGravatarEmailHash(String gravatarEmailHash) {
		this.gravatarEmailHash = gravatarEmailHash;
	}
	

	/**
	 * Whether private messages is enabled
	 **/
	public Boolean getAcceptAllMessages() {
		return acceptAllMessages;
	}
  
	public void setAcceptAllMessages(Boolean acceptAllMessages) {
		this.acceptAllMessages = acceptAllMessages;
	}

	/**
	 * Whether the user should be notified by email
	 **/
	public Boolean getNotifyEmail() {
		return notifyEmail;
	}
  
	public void setNotifyEmail(Boolean notifyEmail) {
		this.notifyEmail = notifyEmail;
	}

	/**
	 * Whether the user should be notified by internal message
	 **/
	public Boolean getNotifyMessage() {
		return notifyMessage;
	}
  
	public void setNotifyMessage(Boolean notifyMessage) {
		this.notifyMessage = notifyMessage;
	}

	/**
	 * Whether the text should be included in notifications
	 **/
	public Boolean getNotifyText() {
		return notifyText;
	}
  
	public void setNotifyText(Boolean notifyText) {
		this.notifyText = notifyText;
	}

	public Boolean getShowRankings() {
		return showRankings;
	}

	public void setShowRankings(Boolean showRankings) {
		this.showRankings = showRankings;
	}

	/**
	 * The user rankings
	 **/
	public List<MemberRanking> getRankings() {
		return rankings;
	}
  
	public void setRankings(List<MemberRanking> rankings) {
		this.rankings = rankings;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class User {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  userName: ").append(userName).append("\n");
		sb.append("  email: ").append(email).append("\n");
		sb.append("  status: ").append(status).append("\n");
		sb.append("  showEmail: ").append(showEmail).append("\n");
		sb.append("  signature: ").append(signature).append("\n");
		sb.append("  useGravatar: ").append(useGravatar).append("\n");
		sb.append("  gravatarEmail: ").append(gravatarEmail).append("\n");
		sb.append("  acceptAllMessages: ").append(acceptAllMessages).append("\n");
		sb.append("  notifyEmail: ").append(notifyEmail).append("\n");
		sb.append("  notifyMessage: ").append(notifyMessage).append("\n");
		sb.append("  notifyText: ").append(notifyText).append("\n");
		sb.append("  rankings: ").append(rankings).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
	
	public static Member getDefault() {
		Member member = new Member();
		member.userName = "";
		member.email = "";
		member.status = StatusEnum.inactive;
		member.showEmail = Boolean.FALSE;
		member.signature = "";
		member.useGravatar = Boolean.FALSE;
		member.gravatarEmail = "";
		member.acceptAllMessages = Boolean.FALSE;
		member.notifyEmail = Boolean.TRUE;
		member.notifyMessage = Boolean.FALSE;
		member.notifyText = Boolean.FALSE;
		member.showRankings = Boolean.FALSE;
		return member;
	}
}
