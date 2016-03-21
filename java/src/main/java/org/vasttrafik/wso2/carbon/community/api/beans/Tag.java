package org.vasttrafik.wso2.carbon.community.api.beans;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Tag {
	
	private Integer id;
	private String label;
	
	/**
	 * Retrieves the tag id
	 * @return The tag id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets the tag id
	 * @param id The tag id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Retrieves the tag label
	 * @return The tag label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets the tag label
	 * @param labelThe label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
