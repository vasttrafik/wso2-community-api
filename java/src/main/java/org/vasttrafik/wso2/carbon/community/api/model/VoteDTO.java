package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'Vote' entity
 * 
 * @author Lars Andersson
 *
 */
public final class VoteDTO implements Serializable {

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
    // DB : com_type varchar 
    private String type;
    // DB : com_user_id int 
    private Integer memberId;
    // DB : com_points smallint 
    private Short points;

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
     * Sets the postId value
     * @param postId The value to set.
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * Retrieves the postId value
     * @return The postId value.
     */
    public Long getPostId() {
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
    /**
     * Sets the points value
     * @param points The value to set.
     */
    public void setPoints(Short points) {
        this.points = points;
    }

    /**
     * Retrieves the points value
     * @return The points value.
     */
    public Short getPoints() {
        return this.points;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(postId);
        sb.append("|");
        sb.append(type);
        sb.append("|");
        sb.append(memberId);
        sb.append("|");
        sb.append(points);
        return sb.toString(); 
    } 
}
