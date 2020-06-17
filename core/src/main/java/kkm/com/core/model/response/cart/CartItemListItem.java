package kkm.com.core.model.response.cart;

import com.google.gson.annotations.SerializedName;

public class CartItemListItem{

	@SerializedName("productAvailability")
	private String productAvailability;

	@SerializedName("courierPrice")
	private String courierPrice;

	@SerializedName("sizeID")
	private String sizeID;

	@SerializedName("productId")
	private String productId;

	@SerializedName("productShipping")
	private String productShipping;

	@SerializedName("productName")
	private String productName;

	@SerializedName("productQuantity")
	private String productQuantity;

	@SerializedName("productImage")
	private String productImage;

	@SerializedName("sizeName")
	private String sizeName;

	@SerializedName("productOldPrice")
	private String productOldPrice;

	@SerializedName("fk_ProductDetailId")
	private String fkProductDetailId;

	@SerializedName("productDescription")
	private String productDescription;

	@SerializedName("productPrice")
	private String productPrice;

	public void setProductAvailability(String productAvailability){
		this.productAvailability = productAvailability;
	}

	public String getProductAvailability(){
		return productAvailability;
	}

	public void setCourierPrice(String courierPrice){
		this.courierPrice = courierPrice;
	}

	public String getCourierPrice(){
		return courierPrice;
	}

	public void setSizeID(String sizeID){
		this.sizeID = sizeID;
	}

	public String getSizeID(){
		return sizeID;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setProductShipping(String productShipping){
		this.productShipping = productShipping;
	}

	public String getProductShipping(){
		return productShipping;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductQuantity(String productQuantity){
		this.productQuantity = productQuantity;
	}

	public String getProductQuantity(){
		return productQuantity;
	}

	public void setProductImage(String productImage){
		this.productImage = productImage;
	}

	public String getProductImage(){
		return productImage;
	}

	public void setSizeName(String sizeName){
		this.sizeName = sizeName;
	}

	public String getSizeName(){
		return sizeName;
	}

	public void setProductOldPrice(String productOldPrice){
		this.productOldPrice = productOldPrice;
	}

	public String getProductOldPrice(){
		return productOldPrice;
	}

	public void setFkProductDetailId(String fkProductDetailId){
		this.fkProductDetailId = fkProductDetailId;
	}

	public String getFkProductDetailId(){
		return fkProductDetailId;
	}

	public void setProductDescription(String productDescription){
		this.productDescription = productDescription;
	}

	public String getProductDescription(){
		return productDescription;
	}

	public void setProductPrice(String productPrice){
		this.productPrice = productPrice;
	}

	public String getProductPrice(){
		return productPrice;
	}

	@Override
 	public String toString(){
		return 
			"CartItemListItem{" + 
			"productAvailability = '" + productAvailability + '\'' + 
			",courierPrice = '" + courierPrice + '\'' + 
			",sizeID = '" + sizeID + '\'' + 
			",productId = '" + productId + '\'' + 
			",productShipping = '" + productShipping + '\'' + 
			",productName = '" + productName + '\'' + 
			",productQuantity = '" + productQuantity + '\'' + 
			",productImage = '" + productImage + '\'' + 
			",sizeName = '" + sizeName + '\'' + 
			",productOldPrice = '" + productOldPrice + '\'' + 
			",fk_ProductDetailId = '" + fkProductDetailId + '\'' + 
			",productDescription = '" + productDescription + '\'' + 
			",productPrice = '" + productPrice + '\'' + 
			"}";
		}
}