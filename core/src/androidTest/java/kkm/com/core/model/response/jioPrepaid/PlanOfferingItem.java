package kkm.com.core.model.response.jioPrepaid;

import com.google.gson.annotations.SerializedName;

public class PlanOfferingItem{

	@SerializedName("billingType")
	private String billingType;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("circle")
	private String circle;

	@SerializedName("jio_user")
	private String jioUser;

	public void setBillingType(String billingType){
		this.billingType = billingType;
	}

	public String getBillingType(){
		return billingType;
	}

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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCircle(String circle){
		this.circle = circle;
	}

	public String getCircle(){
		return circle;
	}

	public void setJioUser(String jioUser){
		this.jioUser = jioUser;
	}

	public String getJioUser(){
		return jioUser;
	}

	@Override
 	public String toString(){
		return 
			"PlanOfferingItem{" + 
			"billingType = '" + billingType + '\'' + 
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",circle = '" + circle + '\'' + 
			",jio_user = '" + jioUser + '\'' + 
			"}";
		}
}