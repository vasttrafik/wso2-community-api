package org.vasttrafik.wso2.carbon.community.api.beans;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Attachment  {
  
	private Long messageId = null;
	private Long id = null;
	private String fileName = null;
	private String mimeType = null;
	private Long size = null;

	/**
	 * The post this attachment belongs to
	 **/
	public Long getMessageId() {
		return messageId;
	}
  
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * Attachment id
	 **/
	public Long getId() {
		return id;
	}
  
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * The file name
	 **/
	public String getFileName() {
		return fileName;
	}
  
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Mime type
	 **/
	public String getMimeType() {
		return mimeType;
	}
  
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Attachment size in bytes
	 **/
	public Long getSize() {
		return size;
	}
  
	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Attachment {\n");
		sb.append("  messageId: ").append(messageId).append("\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  fileName: ").append(fileName).append("\n");
		sb.append("  mimeType: ").append(mimeType).append("\n");
		sb.append("  size: ").append(size).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
