package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'Attachment' entity
 * 
 * @author Lars Andersson
 *
 */
public final class AttachmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_message_id int 
    private Integer messageId;
    // DB : com_file_name varchar 
    private String fileName;
    // DB : com_content varbinary 
    private byte[] content;
    // DB : com_mime_type varchar 
    private String mimeType;
    // DB : com_size int 
    private Integer size;

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer Id ) {
        this.id = Id ;
    }

    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
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
     * Sets the fileName value
     * @param fileName The value to set.
     */
    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

    /**
     * Retrieves the fileName value
     * @return The fileName value.
     */
    public String getFileName() {
        return this.fileName;
    }
    /**
     * Sets the content value
     * @param content The value to set.
     */
    public void setContent( byte[] content ) {
        this.content = content;
    }

    /**
     * Retrieves the content value
     * @return The content value.
     */
    public byte[] getContent() {
        return this.content;
    }
    /**
     * Sets the mimeType value
     * @param mimeType The value to set.
     */
    public void setMimeType( String mimeType ) {
        this.mimeType = mimeType;
    }

    /**
     * Retrieves the mimeType value
     * @return The mimeType value.
     */
    public String getMimeType() {
        return this.mimeType;
    }
    /**
     * Sets the size value
     * @param size The value to set.
     */
    public void setSize( Integer size ) {
        this.size = size;
    }

    /**
     * Retrieves the size value
     * @return The size value.
     */
    public Integer getSize() {
        return this.size;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(messageId);
        sb.append("|");
        sb.append(fileName);
        // attribute 'comContent' not usable (type = byte[])
        sb.append("|");
        sb.append(mimeType);
        sb.append("|");
        sb.append(size);
        return sb.toString(); 
    } 

}
