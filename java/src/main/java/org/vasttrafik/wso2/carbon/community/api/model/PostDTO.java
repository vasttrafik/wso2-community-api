package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;

import java.util.Date;

/**
 * Java bean for 'Post' entity
 * 
 * @author Lars Andersson
 *
 */
public final class PostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_topic_id int 
    private Long topicId;
    // DB : com_forum_id int 
    private Integer forumId;
    // DB : com_category_id int 
    private Integer categoryId;
    // DB : com_type varchar 
    private String type;
    // DB : com_text nvarchar 
    private String text;
    // DB : com_text_format
    private String textFormat;
    // DB : com_created_by_id int 
    private Integer createdById;
    // DB : com_create_date datetime 
    private Date createDate;
    // DB : com_comment_to_id int 
    private Long commentToId;
    // DB : com_points_awarded smallint 
    private Short pointsAwarded;
    // DB : com_is_answer bit 
    private Boolean isAnswer;
    // DB : com_edit_date datetime 
    private Date editDate;
    // DB : com_edited_by_id int 
    private Integer editedById;
    // DB : com_edit_count int 
    private Short editCount;
    // DB : com_is_moderated bit 
    private Boolean isModerated;
    // DB : com_is_deleted bit 
    private Boolean isDeleted;
    // DB : com_is_reported bit 
    private Boolean isReported;

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
     * Sets the topicId value
     * @param topicId The value to set.
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * Retrieves the topicId value
     * @return The topicId value.
     */
    public Long getTopicId() {
        return this.topicId;
    }
    /**
     * Sets the forumId value
     * @param forumId The value to set.
     */
    public void setForumId( Integer forumId ) {
        this.forumId = forumId;
    }

    /**
     * Retrieves the forumId value
     * @return The forumId value.
     */
    public Integer getForumId() {
        return this.forumId;
    }
    public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
     * Sets the type value
     * @param type The value to set.
     */
    public void setType( String type ) {
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
    /**
     * Sets the commentToId value
     * @param commentToId The value to set.
     */
    public void setCommentToId(Long commentToId) {
        this.commentToId = commentToId;
    }

    /**
     * Retrieves the commentToId value
     * @return The commentToId value.
     */
    public Long getCommentToId() {
        return this.commentToId;
    }
    /**
     * Sets the pointsAwarded value
     * @param pointsAwarded The value to set.
     */
    public void setPointsAwarded( Short pointsAwarded ) {
        this.pointsAwarded = pointsAwarded;
    }

    /**
     * Retrieves the pointsAwarded value
     * @return The pointsAwarded value.
     */
    public Short getPointsAwarded() {
        return this.pointsAwarded;
    }
    /**
     * Sets the isAnswer value
     * @param isAnswer The value to set.
     */
    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    /**
     * Retrieves the isAnswer value
     * @return The isAnswer value.
     */
    public Boolean getIsAnswer() {
        return this.isAnswer;
    }
   
    /**
     * Sets the editDate value
     * @param editDate The value to set.
     */
    public void setEditDate( Date editDate ) {
        this.editDate = editDate;
    }

    /**
     * Retrieves the editDate value
     * @return The editDate value.
     */
    public Date getEditDate() {
        return this.editDate;
    }
    /**
     * Sets the editedById value
     * @param editedById The value to set.
     */
    public void setEditedById( Integer editedById ) {
        this.editedById = editedById;
    }

    /**
     * Retrieves the editedById value
     * @return The editedById value.
     */
    public Integer getEditedById() {
        return this.editedById;
    }
    /**
     * Sets the editCount value
     * @param editCount The value to set.
     */
    public void setEditCount(Short editCount) {
        this.editCount = editCount;
    }

    /**
     * Retrieves the editCount value
     * @return The editCount value.
     */
    public Short getEditCount() {
        return this.editCount;
    }
    /**
     * Sets the isModerated value
     * @param isModerated The value to set.
     */
    public void setIsModerated(Boolean isModerated) {
        this.isModerated = isModerated;
    }

    /**
     * Retrieves the isModerated value
     * @return The isModerated value.
     */
    public Boolean getIsModerated() {
        return this.isModerated;
    }
    /**
     * Sets the isDeleted value
     * @param isDeleted The value to set.
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * Retrieves the isDeleted value
     * @return The isDeleted value.
     */
    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

	public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(topicId);
        sb.append("|");
        sb.append(forumId);
        sb.append("|");
        sb.append(type);
        sb.append("|");
        sb.append(text);
        sb.append("|");
        sb.append(textFormat);
        sb.append("|");
        sb.append(createdById);
        sb.append("|");
        sb.append(createDate);
        sb.append("|");
        sb.append(commentToId);
        sb.append("|");
        sb.append(pointsAwarded);
        sb.append("|");
        sb.append(isAnswer);
        sb.append("|");
        sb.append(editDate);
        sb.append("|");
        sb.append(editedById);
        sb.append("|");
        sb.append(editCount);
        sb.append("|");
        sb.append(isModerated);
        sb.append("|");
        sb.append(isDeleted);
        sb.append("|");
        sb.append(isReported);
        return sb.toString(); 
    } 

}
