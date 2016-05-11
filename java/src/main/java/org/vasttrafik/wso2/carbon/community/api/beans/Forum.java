package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class Forum  {
  
	@NotNull(message = "{forum.category.notnull}")
	private Integer categoryId = null;
	private String categoryName = null;
	private Integer id = null;
	@NotNull(message = "{forum.name.notnull}")
	private String name = null;
	@NotNull(message = "{forum.description.notnull}")
	private String description = null;
	private String imageURL = null;
	private Integer numberOfTopics = null;
	private Integer numberOfPosts = null;
	private Post lastPost = null;
	private List<Topic> topics = new ArrayList<Topic>();

	/**
	 * The category this forum belongs to
	 **/
	public Integer getCategoryId() {
		return categoryId;
	}
  
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * The category name this forum belongs to
	 **/
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Forum id
	 **/
	public Integer getId() {
		return id;
	}
  
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Forum name
	 **/
	public String getName() {
		return name;
	}
  
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Forum description
	 **/
	public String getDescription() {
		return description;
	}
  
	public void setDescription(String description) {
		this.description = description;
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
 
	/**
	 * Number of topics in the forum
	 **/
	public Integer getNumberOfTopics() {
		return numberOfTopics;
	}
  
	public void setNumberOfTopics(Integer numberOfTopics) {
		this.numberOfTopics = numberOfTopics;
	}
 
	/**
	 * Number of posts in the forum
	 **/
	public Integer getNumberOfPosts() {
		return numberOfPosts;
	}
  
	public void setNumberOfPosts(Integer totalPosts) {
		this.numberOfPosts = totalPosts;
	}

	/**
	 * The last post in the forum
	 **/
	public Post getLastPost() {
		return lastPost;
	}
  
	public void setLastPost(Post lastPost) {
		this.lastPost = lastPost;
	}

	/**
	 * Forum topics
	 **/
	public List<Topic> getTopics() {
		return topics;
	}
  
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder();
		sb.append("class Forum {\n");
		sb.append("  categoryId: ").append(categoryId).append("\n");
		sb.append("  id: ").append(id).append("\n");
		sb.append("  name: ").append(name).append("\n");
		sb.append("  description: ").append(description).append("\n");
		sb.append("  imageURL: ").append(imageURL).append("\n");
		sb.append("  numberOfTopics: ").append(numberOfTopics).append("\n");
		sb.append("  numberOfPosts: ").append(numberOfPosts).append("\n");
		sb.append("  lastPost: ").append(lastPost).append("\n");
		sb.append("  topics: ").append(topics).append("\n");
		sb.append("}\n");
		return sb.toString();
	}
}
