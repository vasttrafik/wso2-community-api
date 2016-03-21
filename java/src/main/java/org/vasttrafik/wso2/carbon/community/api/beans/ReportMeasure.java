package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.Date;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class ReportMeasure {
	
	public enum TypeEnum {asked, warned, banned, none};  
	
	private Long id = null;
	private TypeEnum type = null;
	private Message message = null;
	private Date measureDate = null;
	private Member rectifiedBy = null;
	private Message reporterNotification = null;
	
	/**
	 * Report id
	 **/
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
	
	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Date getMeasureDate() {
		return measureDate;
	}

	public void setMeasureDate(Date measureDate) {
		this.measureDate = measureDate;
	}

	public Member getRectifiedBy() {
		return rectifiedBy;
	}

	public void setRectifiedBy(Member rectifiedBy) {
		this.rectifiedBy = rectifiedBy;
	}

	public Message getReporterNotification() {
		return reporterNotification;
	}

	public void setReporterNotification(Message reporterNotification) {
		this.reporterNotification = reporterNotification;
	}

}
