package org.vasttrafik.wso2.carbon.community.api.beans.converters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vasttrafik.wso2.carbon.community.api.beans.Category;
import org.vasttrafik.wso2.carbon.community.api.model.CategoryDTO;

/**
 * 
 * @author Lars Andersson
 *
 */
public final class CategoryConverter {
	
	public CategoryConverter() {
	}
	
	public List<Category> convert(List<CategoryDTO> categoriesDTO) {
		List<Category> categories = new ArrayList<Category>(categoriesDTO.size());
		
		for (Iterator<CategoryDTO> it = categoriesDTO.iterator(); it.hasNext();) {
			CategoryDTO categoryDTO = it.next();
			categories.add(convert(categoryDTO));
		}
		
		return categories;
	}
	
	public CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setImageUrl(category.getImageURL());
		categoryDTO.setIsPublic(category.getIsPublic()); 
		return categoryDTO;
	}
	
	public Category convert(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setId(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		category.setImageURL(categoryDTO.getImageUrl());
		category.setIsPublic(categoryDTO.getIsPublic());
		category.setNumberOfForums(categoryDTO.getNumForums());
		return category;
	}
}
