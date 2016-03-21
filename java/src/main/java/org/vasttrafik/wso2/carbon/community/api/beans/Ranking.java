package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Ranking  {
  
	public enum TypeEnum {poster,  reputation};
  
	private Integer id = null;
	private String title = null;
	private TypeEnum type = null;
	private String imageURL = null;
	private Integer minPoints = null;
	private List<Member> members = new ArrayList<Member>();

	/**
	 * Ranking id
	 **/
	public Integer getId() {
		return id;
	}
  
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Ranking title
	 **/
	public String getTitle() {
		return title;
	}
  
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Ranking type
	 **/
	public TypeEnum getType() {
		return type;
	}
  
	public void setType(TypeEnum type) {
		this.type = type;
	}

	/**
	 * A ranking image
	 **/
	public String getImageURL() {
		return imageURL;
	}
  
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * Minimum points for ranking
	 **/
	public Integer getMinPoints() {
		return minPoints;
	}
  
	public void setMinPoints(Integer minPoints) {
		this.minPoints = minPoints;
	}

	/**
	 * The users having this ranking
	 **/
	public List<Member> getMembers() {
		return members;
	}
  
	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Ranking {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  title: ").append(title).append("\n");
		sb.append("  type: ").append(type).append("\n");
		sb.append("  imageURL: ").append(imageURL).append("\n");
		sb.append("  minPoints: ").append(minPoints).append("\n");
		sb.append("  members: ").append(members).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
