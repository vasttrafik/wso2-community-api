package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Java bean for 'Report' entity
 * 
 * @author Lars Andersson
 *
 */
public final class ReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;
    
    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_post_id int 
    private Integer postId;
    // DB : com_type varchar 
    private String type;
    // DB : com_text varchar 
    private String text;
    // DB : com_report_date datetime 
    private Date reportDate;
    // DB : com_reported_by_id int 
    private Integer reportedById;
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }

    public Integer getId() {
        return this.id;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    /**
     * Sets the postId value
     * @param postId The value to set.
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * Retrieves the postId value
     * @return The postId value.
     */
    public Integer getPostId() {
        return this.postId;
    }
    
    /**
     * Sets the type value
     * @param type The value to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the type value
     * @return The type value.
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Sets the text value
     * @param text The value to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Retrieves the text value
     * @return The text value.
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * Sets the reportDate value
     * @param reportDate The value to set.
     */
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * Retrieves the reportDate value
     * @return The reportDate value.
     */
    public Date getReportDate() {
        return this.reportDate;
    }
    /**
     * Sets the reportedById value
     * @param reportedById The value to set.
     */
    public void setReportedById(Integer reportedById) {
        this.reportedById = reportedById;
    }

    /**
     * Retrieves the reportedById value
     * @return The reportedById value.
     */
    public Integer getReportedById() {
        return this.reportedById;
    }
    
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(postId);
        sb.append("|");
        sb.append(type);
        sb.append("|");
        sb.append(text);
        sb.append("|");
        sb.append(reportDate);
        sb.append("|");
        sb.append(reportedById);
        return sb.toString(); 
    } 
}
