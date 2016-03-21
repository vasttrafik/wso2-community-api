package org.vasttrafik.wso2.carbon.community.api.beans;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class MemberRanking  {
  
	public enum TypeEnum {poster,  reputation};
  
	private Integer id = null;
	private Integer memberId = null;
	private String title = null;
	private TypeEnum type = null;
	private String imageURL = null;
	private Integer minPoints = null;
	private Integer currentPoints = null;
	
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
	 * Member id
	 **/
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
	 * Current points for ranking
	 **/
	public Integer getCurrentPoints() {
		return currentPoints;
	}

	public void setCurrentPoints(Integer currentPoints) {
		this.currentPoints = currentPoints;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Ranking {\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  memberId: ").append(id).append("\n");
		sb.append("  title: ").append(title).append("\n");
		sb.append("  type: ").append(type).append("\n");
		sb.append("  imageURL: ").append(imageURL).append("\n");
		sb.append("  minPoints: ").append(minPoints).append("\n");
		sb.append("  currentPoints: ").append(currentPoints).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
