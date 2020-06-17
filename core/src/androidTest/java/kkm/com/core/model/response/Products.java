package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class Products{

	@SerializedName("features")
	private String features;

	@SerializedName("faQs")
	private String faQs;

	@SerializedName("productCode")
	private String productCode;

	@SerializedName("productImage")
	private String productImage;

	@SerializedName("productId")
	private String productId;

	@SerializedName("response")
	private Object response;

	@SerializedName("brandID")
	private String brandID;

	@SerializedName("availability")
	private String availability;

	@SerializedName("productName")
	private String productName;

	@SerializedName("productDescription")
	private String productDescription;

	@SerializedName("addedOn")
	private String addedOn;

	public void setFeatures(String features){
		this.features = features;
	}

	public String getFeatures(){
		return features;
	}

	public void setFaQs(String faQs){
		this.faQs = faQs;
	}

	public String getFaQs(){
		return faQs;
	}

	public void setProductCode(String productCode){
		this.productCode = productCode;
	}

	public String getProductCode(){
		return productCode;
	}

	public void setProductImage(String productImage){
		this.productImage = productImage;
	}

	public String getProductImage(){
		return productImage;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setResponse(Object response){
		this.response = response;
	}

	public Object getResponse(){
		return response;
	}

	public void setBrandID(String brandID){
		this.brandID = brandID;
	}

	public String getBrandID(){
		return brandID;
	}

	public void setAvailability(String availability){
		this.availability = availability;
	}

	public String getAvailability(){
		return availability;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductDescription(String productDescription){
		this.productDescription = productDescription;
	}

	public String getProductDescription(){
		return productDescription;
	}

	public void setAddedOn(String addedOn){
		this.addedOn = addedOn;
	}

	public String getAddedOn(){
		return addedOn;
	}

	@Override
 	public String toString(){
		return 
			"Products{" + 
			"features = '" + features + '\'' + 
			",faQs = '" + faQs + '\'' + 
			",productCode = '" + productCode + '\'' + 
			",productImage = '" + productImage + '\'' + 
			",productId = '" + productId + '\'' + 
			",response = '" + response + '\'' + 
			",brandID = '" + brandID + '\'' + 
			",availability = '" + availability + '\'' + 
			",productName = '" + productName + '\'' + 
			",productDescription = '" + productDescription + '\'' + 
			",addedOn = '" + addedOn + '\'' + 
			"}";
		}
}