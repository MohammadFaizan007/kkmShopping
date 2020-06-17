package kkm.com.core.model.response.tatasky.productlisttwo;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem{

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("packduration")
	private String packduration;

	@SerializedName("ProductId")
	private String productId;

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

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	@Override
 	public String toString(){
		return 
			"AddinfoItem{" + 
			"price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",packduration = '" + packduration + '\'' + 
			",productId = '" + productId + '\'' + 
			"}";
		}
}