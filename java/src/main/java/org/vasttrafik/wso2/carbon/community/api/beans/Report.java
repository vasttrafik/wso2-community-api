package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Report {
	
	public enum TypeEnum {user,  system};  
	
	private Long id = null;
	private Post post = null;
	private TypeEnum type = null;
	private String text = null;
	private Date reportDate = null;
	private Member reportedBy = null;
	private List<ReportMeasure> measures = null;
	
	
	/**
	 * Report id
	 **/
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}
	
	/**
	 * The text of the report
	 **/
	public String getText() {
		return text;
	}
  
	public void setText(String text) {
		this.text = text;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Member getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(Member reportedBy) {
		this.reportedBy = reportedBy;
	}

	public List<ReportMeasure> getMeasures() {
		return measures;
	}

	public void setMeasures(List<ReportMeasure> measures) {
		this.measures = measures;
	}

}
