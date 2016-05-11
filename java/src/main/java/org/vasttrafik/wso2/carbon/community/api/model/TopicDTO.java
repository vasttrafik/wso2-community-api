package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Java bean for 'Topic' entity
 * 
 * @author Lars Andersson
 *
 */
public final class TopicDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_category_id int 
    private Integer categoryId;
    // DB : com_name varchar
    private String categoryName;
    // DB : com_forum_id int 
    private Integer forumId;
    // DB : com_name varchar
    private String forumName;
    // DB : com_subject varchar 
    private String subject;
    // DB : com_created_date datetime 
    private Date createdDate;
    // DB : com_last_post_date datetime 
    private Date lastPostDate;
    // DB : com_created_by_id int 
    private Integer createdById;
    // DB : com_num_posts smallint 
    private Short numPosts;
    // DB : com_num_views smallint 
    private Short numViews;
    // DB : com_num_answers smallint 
    private Short numAnswers;
    // DB : com_first_post_id int 
    private Long firstPostId;
    // DB : com_last_post_id int 
    private Long lastPostId;
    // DB : com_answer_post_id int 
    private Long answerPostId;
    // DB : com_closed_date
    private Date closedDate;
    // DB : com_closed_by_id int 
    private Integer closedById;
    // DB : com_is_deleted bit 
    private Boolean isDeleted;

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id ;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	//----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    /**
     * Sets the forumId value
     * @param forumId The value to set.
     */
    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    /**
     * Retrieves the forumId value
     * @return The forumId value.
     */
    public Integer getForumId() {
        return this.forumId;
    }
    
    public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	/**
     * Sets the subject value
     * @param subject The value to set.
     */
    public void setSubject(String subject) {
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
     * Sets the createdDate value
     * @param createdDate The value to set.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Retrieves the createdDate value
     * @return The createdDate value.
     */
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public Date getLastPostDate() {
		return lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}

	/**
     * Sets the createdById value
     * @param createdById The value to set.
     */
    public void setCreatedById(Integer createdById) {
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
     * Sets the numPosts value
     * @param numPosts The value to set.
     */
    public void setNumPosts(Short numPosts) {
        this.numPosts = numPosts;
    }

    /**
     * Retrieves the numPosts value
     * @return The numPosts value.
     */
    public Short getNumPosts() {
        return this.numPosts;
    }
    /**
     * Sets the numViews value
     * @param numViews The value to set.
     */
    public void setNumViews(Short numViews) {
        this.numViews = numViews;
    }

    /**
     * Retrieves the numViews value
     * @return The numViews value.
     */
    public Short getNumViews() {
        return this.numViews;
    }
    /**
     * Sets the numAnswers value
     * @param numAnswers The value to set.
     */
    public void setNumAnswers(Short numAnswers) {
        this.numAnswers = numAnswers;
    }

    /**
     * Retrieves the numAnswers value
     * @return The numAnswers value.
     */
    public Short getNumAnswers() {
        return this.numAnswers;
    }
    /**
     * Sets the firstPostId value
     * @param firstPostId The value to set.
     */
    public void setFirstPostId(Long firstPostId) {
        this.firstPostId = firstPostId;
    }

    /**
     * Retrieves the firstPostId value
     * @return The firstPostId value.
     */
    public Long getFirstPostId() {
        return this.firstPostId;
    }
    /**
     * Sets the lastPostId value
     * @param lastPostId The value to set.
     */
    public void setLastPostId(Long lastPostId) {
        this.lastPostId = lastPostId;
    }

    /**
     * Retrieves the lastPostId value
     * @return The lastPostId value.
     */
    public Long getLastPostId() {
        return this.lastPostId;
    }
    
    public Long getAnswerPostId() {
		return answerPostId;
	}

	public void setAnswerPostId(Long answerPostId) {
		this.answerPostId = answerPostId;
	}

	/**
     * Sets the closedDate value
     * @param closedDate The value to set.
     */
    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    /**
     * Retrieves the closedDate value
     * @return The closedDate value.
     */
    public Date getClosedDate() {
        return this.closedDate;
    }
    
    /**
     * Sets the closedById value
     * @param closedById The value to set.
     */
    public void setClosedById(Integer closedById) {
        this.closedById = closedById;
    }

    /**
     * Retrieves the closedById value
     * @return The closedById value.
     */
    public Integer getClosedById() {
        return this.closedById;
    }

    public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(categoryId);
        sb.append("|");
        sb.append(categoryName);
        sb.append("|");
        sb.append(forumId);
        sb.append("|");
        sb.append(forumName);
        sb.append("|");
        sb.append(subject);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(createdById);
        sb.append("|");
        sb.append(numPosts);
        sb.append("|");
        sb.append(numViews);
        sb.append("|");
        sb.append(numAnswers);
        sb.append("|");
        sb.append(firstPostId);
        sb.append("|");
        sb.append(lastPostId);
        sb.append("|");
        sb.append(answerPostId);
        sb.append("|");
        sb.append(closedDate);
        sb.append("|");
        sb.append(closedById);
        return sb.toString(); 
    } 
}
