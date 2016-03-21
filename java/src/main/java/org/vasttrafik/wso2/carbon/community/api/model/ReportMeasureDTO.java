package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Java bean for 'ReportMeasure' entity
 * 
 * @author Lars Andersson
 *
 */
public final class ReportMeasureDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;
    
    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_report_id int 
    private Integer reportId;
    // DB : com_type varchar 
    private String type;
    // DB : com_message_id int 
    private Integer messageId;
    // DB : com_measure_date datetime 
    private Date measureDate;
    // DB : com_rectified_by_id int 
    private Integer rectifiedById;
    // DB : com_notification_id int 
    private Integer notificationId;
    
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
     * Sets the reportId value
     * @param reportId The value to set.
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    /**
     * Retrieves the reportId value
     * @return The reportId value.
     */
    public Integer getReportId() {
        return this.reportId;
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
     * Sets the messageId value
     * @param messageId The value to set.
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * Retrieves the messageId value
     * @return The messageId value.
     */
    public Integer getMessageId() {
        return this.messageId;
    }
    
    /**
     * Sets the measureDate value
     * @param measureDate The value to set.
     */
    public void setMeasureDate(Date measureDate) {
        this.measureDate = measureDate;
    }

    /**
     * Retrieves the measureDate value
     * @return The measureDate value.
     */
    public Date getMeasureDate() {
        return this.measureDate;
    }
    /**
     * Sets the rectifiedById value
     * @param rectifiedById The value to set.
     */
    public void setRectifiedById(Integer rectifiedById) {
        this.rectifiedById = rectifiedById;
    }

    /**
     * Retrieves the rectifiedById value
     * @return The rectifiedById value.
     */
    public Integer getRectifiedById() {
        return this.rectifiedById;
    }
    
    /**
     * Sets the notificationId value
     * @param notificationId The value to set.
     */
    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Retrieves the notificationId value
     * @return The notificationId value.
     */
    public Integer getNotificationId() {
        return this.notificationId;
    }
}
