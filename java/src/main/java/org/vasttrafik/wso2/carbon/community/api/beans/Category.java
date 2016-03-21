package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Category  {
  
	private Integer id = null;
	@NotNull(message = "{category.name.notnull}")
	private String name = null;
	@NotNull(message = "{category.isPublic.notnull}")
	private Boolean isPublic = null;
	private String imageURL = null;
	private Integer numberOfForums = null;
	private List<Forum> forums = new ArrayList<Forum>();

	/**
	 * Category id
	 **/
	public Integer getId() {
		return id;
	}
  
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Category name
	 **/
	public String getName() {
		return name;
	}
  
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * An image url
	 **/
	public String getImageURL() {
		return imageURL;
	}
  
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
 
	public Integer getNumberOfForums() {
		return numberOfForums;
	}

	public void setNumberOfForums(Integer numberOfForums) {
		this.numberOfForums = numberOfForums;
	}

	/**
	 * Forums belonging to category
	 **/
	public List<Forum> getForums() {
		return forums;
	}
  
	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Category {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  isPublic: ").append(isPublic).append("\n");
		sb.append("  name: ").append(name).append("\n");
		sb.append("  imageURL: ").append(imageURL).append("\n");
		sb.append("  forums: ").append(forums).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
