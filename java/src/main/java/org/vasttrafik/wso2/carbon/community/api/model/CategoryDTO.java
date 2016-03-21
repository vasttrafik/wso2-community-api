package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'Category' entity
 * 
 * @author Lars Andersson
 *
 */
public final class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_name varchar 
    private String name;
    // DB : com_image_url varchar 
    private String imageUrl;
    // DB : com_is_public tinyint 
    private Boolean isPublic;
    // DB : com_num_forums
    private Integer numForums;

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

    public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getNumForums() {
		return numForums;
	}

	public void setNumForums(Integer numForums) {
		this.numForums = numForums;
	}

	public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(name);
        sb.append("|");
        sb.append(isPublic);
        sb.append("|");
        sb.append(imageUrl);
        sb.append("|");
        sb.append(numForums);
        return sb.toString(); 
    } 

}
