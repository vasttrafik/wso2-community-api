package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Java bean for 'PostEdit' entity
 * 
 * @author Lars Andersson
 *
 */
public final class PostEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_post_id int 
    private Long postId;
    // DB : com_edit_version tinyint 
    private Short editVersion;
    // DB : com_text nvarchar 
    private String text;
    // DB : com_text_format
    private String textFormat;
    // DB : com_created_by_id int 
    private Integer createdById;
    // DB : com_create_date datetime 
    private Date createDate;

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id ;
    }

    public Long getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    /**
     * Sets the comPostId value
     * @param PostId The value to set.
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * Retrieves the PostId value
     * @return The PostId value.
     */
    public Long getPostId() {
        return this.postId;
    }
    /**
     * Sets the editVersion value
     * @param editVersion The value to set.
     */
    public void setEditVersion(Short editVersion) {
        this.editVersion = editVersion;
    }

    /**
     * Retrieves the editVersion value
     * @return The editVersion value.
     */
    public Short getEditVersion() {
        return this.editVersion;
    }
    /**
     * Sets the text value
     * @param text The value to set.
     */
    public void setText( String text ) {
        this.text = text;
    }

    /**
     * Retrieves the text value
     * @return The text value.
     */
    public String getText() {
        return this.text;
    }
    
    public String getTextFormat() {
		return textFormat;
	}

	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}

	/**
     * Sets the createdById value
     * @param createdById The value to set.
     */
    public void setCreatedById( Integer createdById ) {
        this.createdById = createdById;
    }

    /**
     * Retrieves the createdById value
     * @return The createdById value.
     */
    public Integer getCreatedById() {
        return this.createdById;
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

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(postId);
        sb.append("|");
        sb.append(editVersion);
        sb.append("|");
        sb.append(text);
        sb.append("|");
        sb.append(textFormat);
        sb.append("|");
        sb.append(createdById);
        sb.append("|");
        sb.append(createDate);
        return sb.toString(); 
    } 

}
