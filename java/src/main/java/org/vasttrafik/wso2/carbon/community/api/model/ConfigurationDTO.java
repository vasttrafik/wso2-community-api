package org.vasttrafik.wso2.carbon.community.api.model;

import java.io.Serializable;


/**
 * Java bean for 'Configuration' entity
 * 
 * @author Lars Andersson
 *
 */
public final class ConfigurationDTO implements Serializable {

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
    // DB : com_value varchar 
    private String value;

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
     * Sets the value value
     * @param value The value to set.
     */
    public void setValue( String value ) {
        this.value = value;
    }

    /**
     * Retrieves the value value
     * @return The value value.
     */
    public String getValue() {
        return this.value;
    }

    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(name);
        sb.append("|");
        sb.append(value);
        return sb.toString(); 
    } 

}
