package kkm.com.core.model.response.shopping;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoryDataItem implements Serializable {

	@SerializedName("section_title")
	private String sectionTitle;

	@SerializedName("subCategory")
	private List<SubCategoryItem> subCategory;

	@SerializedName("childCategory")
	private List<ChildCategoryItem> childCategory;

	@SerializedName("categoryGroupWise")
	private List<CategoryGroupWiseItem> categoryGroupWise;

	public void setSectionTitle(String sectionTitle){
		this.sectionTitle = sectionTitle;
	}

	public String getSectionTitle(){
		return sectionTitle;
	}

	public void setSubCategory(List<SubCategoryItem> subCategory){
		this.subCategory = subCategory;
	}

	public List<SubCategoryItem> getSubCategory(){
		return subCategory;
	}

	public void setChildCategory(List<ChildCategoryItem> childCategory){
		this.childCategory = childCategory;
	}

	public List<ChildCategoryItem> getChildCategory(){
		return childCategory;
	}

	public void setCategoryGroupWise(List<CategoryGroupWiseItem> categoryGroupWise){
		this.categoryGroupWise = categoryGroupWise;
	}

	public List<CategoryGroupWiseItem> getCategoryGroupWise(){
		return categoryGroupWise;
	}

	@Override
 	public String toString(){
		return 
			"CategoryDataItem{" + 
			"section_title = '" + sectionTitle + '\'' + 
			",subCategory = '" + subCategory + '\'' + 
			",childCategory = '" + childCategory + '\'' + 
			",categoryGroupWise = '" + categoryGroupWise + '\'' + 
			"}";
		}
}