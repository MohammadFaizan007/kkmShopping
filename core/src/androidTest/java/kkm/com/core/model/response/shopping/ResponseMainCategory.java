package kkm.com.core.model.response.shopping;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMainCategory implements Serializable {

	@SerializedName("response")
	private String response;

	@SerializedName("categoryData")
	private List<CategoryDataItem> categoryData;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setCategoryData(List<CategoryDataItem> categoryData){
		this.categoryData = categoryData;
	}

	public List<CategoryDataItem> getCategoryData(){
		return categoryData;
	}

	@Override
 	public String toString(){
		return 
			"ResponseMainCategory{" + 
			"response = '" + response + '\'' + 
			",categoryData = '" + categoryData + '\'' + 
			"}";
		}
}