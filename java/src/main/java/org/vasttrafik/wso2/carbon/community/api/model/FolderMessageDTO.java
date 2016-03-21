package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;

import java.util.Date;

/**
 * Java bean for 'FolderMessage' entity
 * 
 * @author Lars Andersson
 *
 */
public final class FolderMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_folder_id int 
    private Integer folderId;
    // DB : com_message_id int 
    private Integer messageId;
    // DB : com_read_date datetime 
    private Date readDate;

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
     * Sets the folderId value
     * @param folderId The value to set.
     */
    public void setFolderId( Integer folderId ) {
        this.folderId = folderId;
    }

    /**
     * Retrieves the folderId value
     * @return The folderId value.
     */
    public Integer getFolderId() {
        return this.folderId;
    }
    /**
     * Sets the messageId value
     * @param messageId The value to set.
     */
    public void setMessageId( Integer messageId ) {
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
     * Sets the readDate value
     * @param readDate The value to set.
     */
    public void setReadDate( Date readDate ) {
        this.readDate = readDate;
    }

    /**
     * Retrieves the readDate value
     * @return The readDate value.
     */
    public Date getReadDate() {
        return this.readDate;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(folderId);
        sb.append("|");
        sb.append(messageId);
        sb.append("|");
        sb.append(readDate);
        return sb.toString(); 
    } 
}
