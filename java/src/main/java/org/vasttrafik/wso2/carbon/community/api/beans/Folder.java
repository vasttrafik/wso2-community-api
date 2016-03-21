package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Folder  {
  
	public enum TypeEnum {inbox,  sent,  drafts,  trash,  user};
	  
	private Integer id = null;
	private Integer parentId = null;
	private Integer memberId = null;
	private String name = null;
	private TypeEnum type = null;
	private String imageURL = null;
	private List<Message> messages = new ArrayList<Message>();
	private Short numberOfMessages = null;
	private Short numberOfUnreadMessages = null;
	private Integer size = null;

	/**
	 * Folder id
	 **/
	public Integer getId() {
		return id;
	}
  
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * The folder parent id
	 **/
	public Integer getParentId() {
		return parentId;
	}
  
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
  
	/**
	 * The user this folder belongs to
	 **/
	public Integer getMemberId() {
		return memberId;
	}
  
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	/**
	 * The folder name
	 **/
	public String getName() {
		return name;
	}
  
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The folder type
	 **/
	public TypeEnum getType() {
		return type;
	}
  
	public void setType(TypeEnum type) {
		this.type = type;
	}
  
	/**
	 * Folder icon URL
	 **/
	public String getImageURL() {
		return imageURL;
	}
  
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
  
	/**
	 * The messages in the folder
	 **/
	public List<Message> getMessages() {
		return messages;
	}
  
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	/**
	 * The number of messages in the folder
	 **/
	public Short getNumberOfMessages() {
		return numberOfMessages;
	}
  
	public void setNumberOfMessages(Short numberOfMessages) {
		this.numberOfMessages = numberOfMessages;
	}

	/**
	 * The number of unread messages in the folder
	 **/
	public Short getNumberOfUnreadMessages() {
		return numberOfUnreadMessages;
	}
  
	public void setNumberOfUnreadMessages(Short numberOfUnreadMessages) {
		this.numberOfUnreadMessages = numberOfUnreadMessages;
	}

	/**
	 * The size of the folder, in bytes
	 * @return
	 */
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Folder {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  parentId: ").append(parentId).append("\n");
		sb.append("  memberId: ").append(memberId).append("\n");
		sb.append("  name: ").append(name).append("\n");
		sb.append("  type: ").append(type).append("\n");
		sb.append("  imageURL: ").append(imageURL).append("\n");
		sb.append("  messages: ").append(messages).append("\n");
		sb.append("  numberOfMessages: ").append(numberOfMessages).append("\n");
		sb.append("  numberOfUnreadMessages: ").append(numberOfUnreadMessages).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
