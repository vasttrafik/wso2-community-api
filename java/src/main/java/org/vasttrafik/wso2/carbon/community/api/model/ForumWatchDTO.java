package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'ForumWatch' entity
 * 
 * @author Lars Andersson
 *
 */
public final class ForumWatchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_forum_id int 
    private Integer forumId;
    // DB : com_user_id int 
    private Integer memberId;

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
    /**
     * Sets the memberId value
     * @param memberId The value to set.
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * Retrieves the memberId value
     * @return The memberId value.
     */
    public Integer getMemberId() {
        return this.memberId;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(forumId);
        sb.append("|");
        sb.append(memberId);
        return sb.toString(); 
    } 

}
