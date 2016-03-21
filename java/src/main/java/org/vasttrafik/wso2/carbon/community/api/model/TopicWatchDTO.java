package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'TopicWatch' entity
 * 
 * @author Lars Andersson
 *
 */
public final class TopicWatchDTO implements Serializable {

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
     * Sets the memberId value
     * @param memberId The value to set.
     */
    public void setMemberId( Integer memberId ) {
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
        sb.append(topicId);
        sb.append("|");
        sb.append(memberId);
        return sb.toString(); 
    } 

}
