package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'Folder' entity
 * 
 * @author Lars Andersson
 *
 */
public final class FolderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_parent_id int 
    private Integer parentId;
    // DB : com_user_id int 
    private Integer memberId;
    // DB : com_name varchar 
    private String name;
    // DB : com_type varchar 
    private String type;
    // DB : com_image_url varchar 
    private String imageUrl;
    // DB : com_num_msgs smallint 
    private Short numMsgs;
    // DB : com_num_unread_msgs smallint 
    private Short numUnreadMsgs;
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
     * Sets the parentId value
     * @param parentId The value to set.
     */
    public void setParentId( Integer parentId ) {
        this.parentId = parentId;
    }

    /**
     * Retrieves the parentId value
     * @return The parentId value.
     */
    public Integer getParentId() {
        return this.parentId;
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
    /**
     * Sets the Name value
     * @param Name The value to set.
     */
    public void setName( String Name ) {
        this.name = Name;
    }

    /**
     * Retrieves the name value
     * @return The name value.
     */
    public String getName() {
        return this.name;
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
     * Sets the numMsgs value
     * @param numMsgs The value to set.
     */
    public void setNumMsgs( Short numMsgs ) {
        this.numMsgs = numMsgs;
    }

    /**
     * Retrieves the numMsgs value
     * @return The numMsgs value.
     */
    public Short getNumMsgs() {
        return this.numMsgs;
    }
    /**
     * Sets the numUnreadMsgs value
     * @param numUnreadMsgs The value to set.
     */
    public void setNumUnreadMsgs( Short numUnreadMsgs ) {
        this.numUnreadMsgs = numUnreadMsgs;
    }

    /**
     * Retrieves the numUnreadMsgs value
     * @return The numUnreadMsgs value.
     */
    public Short getNumUnreadMsgs() {
        return this.numUnreadMsgs;
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
        sb.append(parentId);
        sb.append("|");
        sb.append(memberId);
        sb.append("|");
        sb.append(name);
        sb.append("|");
        sb.append(type);
        sb.append("|");
        sb.append(imageUrl);
        sb.append("|");
        sb.append(numMsgs);
        sb.append("|");
        sb.append(numUnreadMsgs);
        sb.append("|");
        sb.append(size);
        return sb.toString(); 
    } 

}
