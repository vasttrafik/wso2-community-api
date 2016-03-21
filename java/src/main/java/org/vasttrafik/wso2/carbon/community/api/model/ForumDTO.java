package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'Forum' entity
 * 
 * @author Lars Andersson
 *
 */
public final class ForumDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_category_id int 
    private Integer categoryId;
    // DB : com_name varchar 
    private String name;
    // DB : com_desc varchar 
    private String desc;
    // DB : com_image_url varchar 
    private String imageUrl;
    // DB : com_num_topics int 
    private Integer numTopics;
    // DB : com_num_posts int 
    private Integer numPosts;
    // DB : com_last_post_id int 
    private Long lastPostId;

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
     * Sets the categoryId value
     * @param categoryId The value to set.
     */
    public void setCategoryId( Integer categoryId ) {
        this.categoryId = categoryId;
    }

    /**
     * Retrieves the categoryId value
     * @return The categoryId value.
     */
    public Integer getCategoryId() {
        return this.categoryId;
    }
    /**
     * Sets the name value
     * @param name The value to set.
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Retrieves the name value
     * @return The name value.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Sets the desc value
     * @param desc The value to set.
     */
    public void setDesc( String desc ) {
        this.desc = desc;
    }

    /**
     * Retrieves the desc value
     * @return The desc value.
     */
    public String getDesc() {
        return this.desc;
    }
    /**
     * Sets the imageUrl value
     * @param imageUrl The value to set.
     */
    public void setImageUrl( String imageUrl ) {
        this.imageUrl = imageUrl;
    }

    /**
     * Retrieves the imageUrl value
     * @return The imageUrl value.
     */
    public String getImageUrl() {
        return this.imageUrl;
    }
    /**
     * Sets the numTopics value
     * @param numTopics The value to set.
     */
    public void setNumTopics( Integer numTopics ) {
        this.numTopics = numTopics;
    }

    /**
     * Retrieves the numTopics value
     * @return The numTopics value.
     */
    public Integer getNumTopics() {
        return this.numTopics;
    }
    /**
     * Sets the numPosts value
     * @param numPosts The value to set.
     */
    public void setNumPosts( Integer numPosts ) {
        this.numPosts = numPosts;
    }

    /**
     * Retrieves the numPosts value
     * @return The numPosts value.
     */
    public Integer getNumPosts() {
        return this.numPosts;
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

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(categoryId);
        sb.append("|");
        sb.append(name);
        sb.append("|");
        sb.append(desc);
        sb.append("|");
        sb.append(imageUrl);
        sb.append("|");
        sb.append(numTopics);
        sb.append("|");
        sb.append(numPosts);
        sb.append("|");
        sb.append(lastPostId);
        return sb.toString(); 
    } 

}
