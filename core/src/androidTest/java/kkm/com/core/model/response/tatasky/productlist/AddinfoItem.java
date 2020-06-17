package kkm.com.core.model.response.tatasky.productlist;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem{

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("packduration")
	private String packduration;

	@SerializedName("description")
	private String description;

	@SerializedName("ProductId")
	private String productId;

	@SerializedName("boxtype")
	private String boxtype;

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPackduration(String packduration){
		this.packduration = packduration;
	}

	public String getPackduration(){
		return packduration;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setBoxtype(String boxtype){
		this.boxtype = boxtype;
	}

	public String getBoxtype(){
		return boxtype;
	}

	@Override
 	public String toString(){
		return 
			"AddinfoItem{" + 
			"price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",packduration = '" + packduration + '\'' + 
			",description = '" + description + '\'' + 
			",productId = '" + productId + '\'' + 
			",boxtype = '" + boxtype + '\'' + 
			"}";
		}
}