package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;

/**
 * Java bean for 'Tag' entity
 * 
 * @author Lars Andersson
 *
 */
public final class TagDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    // DB : com_id int identity 
    private Integer id;
    
    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    // DB : com_label varchar
    private String label;
    
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
     * Sets the label value
     * @param label The label to set.
     */
    public void setLabel( String label ) {
        this.label = label;
    }

    /**
     * Retrieves the label value
     * @return The label value.
     */
    public String getLabel() {
        return this.label;
    }
}
