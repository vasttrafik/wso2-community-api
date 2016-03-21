package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;

import java.util.Date;

/**
 * Java bean for 'Message' entity
 * 
 * @author Lars Andersson
 *
 */
public final class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_original_id int identity 
    private Integer originalId;
    // DB : com_subject varchar 
    private String subject;
    // DB : com_body nvarchar 
    private String body;
    // DB : com_sender_id int 
    private Integer senderId;
    // DB : com_create_date datetime 
    private Date createDate;
    // DB : com_enable_html bit 
    private Boolean enableHtml;

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
    public void setOriginalId( Integer originalId ) {
        this.originalId = originalId ;
    }

    public Integer getOriginalId() {
        return this.originalId;
    }
    
    /**
     * Sets the subject value
     * @param subject The value to set.
     */
    public void setSubject( String subject ) {
        this.subject = subject;
    }

    /**
     * Retrieves the subject value
     * @return The subject value.
     */
    public String getSubject() {
        return this.subject;
    }
    /**
     * Sets the body value
     * @param body The value to set.
     */
    public void setBody( String body ) {
        this.body = body;
    }

    /**
     * Retrieves the body value
     * @return The body value.
     */
    public String getBody() {
        return this.body;
    }
    /**
     * Sets the senderId value
     * @param senderId The value to set.
     */
    public void setSenderId( Integer senderId ) {
        this.senderId = senderId;
    }

    /**
     * Retrieves the senderId value
     * @return The senderId value.
     */
    public Integer getSenderId() {
        return this.senderId;
    }
    /**
     * Sets the createDate value
     * @param createDate The value to set.
     */
    public void setCreateDate( Date createDate ) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the createDate value
     * @return The createDate value.
     */
    public Date getCreateDate() {
        return this.createDate;
    }
    /**
     * Sets the enableHtml value
     * @param enableHtml The value to set.
     */
    public void setEnableHtml(Boolean enableHtml) {
        this.enableHtml = enableHtml;
    }

    /**
     * Retrieves the enableHtml value
     * @return The enableHtml value.
     */
    public Boolean getEnableHtml() {
        return this.enableHtml;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(originalId);
        sb.append("|");
        sb.append(subject);
        sb.append("|");
        sb.append(body);
        sb.append("|");
        sb.append(senderId);
        sb.append("|");
        sb.append(createDate);
        sb.append("|");
        sb.append(enableHtml);
        return sb.toString(); 
    } 

}
