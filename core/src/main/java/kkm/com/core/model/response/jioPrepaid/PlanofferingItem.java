package kkm.com.core.model.response.jioPrepaid;

import com.google.gson.annotations.SerializedName;

public class PlanofferingItem{

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("billingtype")
	private String billingtype;

	@SerializedName("id")
	private String id;

	@SerializedName("circle")
	private String circle;

	@SerializedName("jio_user")
	private String jioUser;

	public String getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public String getBillingtype(){
		return billingtype;
	}

	public String getId(){
		return id;
	}

	public String getCircle(){
		return circle;
	}

	public String getJioUser(){
		return jioUser;
	}
}