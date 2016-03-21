package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'Ranking' entity
 * 
 * @author Lars Andersson
 *
 */
public final class RankingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_title varchar 
    private String title;
    // DB : com_type varchar 
    private String type;
    // DB : com_min_points int 
    private Integer minPoints;
    // DB : com_image_url varchar 
    private String imageUrl;

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
     * Sets the title value
     * @param title The value to set.
     */
    public void setTitle( String title ) {
        this.title = title;
    }

    /**
     * Retrieves the title value
     * @return The title value.
     */
    public String getTitle() {
        return this.title;
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
     * Sets the minPoints value
     * @param minPoints The value to set.
     */
    public void setMinPoints( Integer minPoints ) {
        this.minPoints = minPoints;
    }

    /**
     * Retrieves the minPoints value
     * @return The minPoints value.
     */
    public Integer getMinPoints() {
        return this.minPoints;
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

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(title);
        sb.append("|");
        sb.append(type);
        sb.append("|");
        sb.append(minPoints);
        sb.append("|");
        sb.append(imageUrl);
        return sb.toString(); 
    } 

}
