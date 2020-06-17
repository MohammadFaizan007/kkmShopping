package kkm.com.core.model.response.jioPrepaid;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAddInfoJio{

	@SerializedName("customerFacingServices")
	private List<CustomerfacingservicesItem> customerfacingservices;

	@SerializedName("STATE")
	private String state;

	@SerializedName("circleId")
	private String circleid;

	@SerializedName("totalRecords")
	private int totalrecords;

	@SerializedName("planOffering")
	private List<PlanofferingItem> planoffering;

	public List<CustomerfacingservicesItem> getCustomerfacingservices(){
		return customerfacingservices;
	}

	public String getState(){
		return state;
	}

	public String getCircleid(){
		return circleid;
	}

	public int getTotalrecords(){
		return totalrecords;
	}

	public List<PlanofferingItem> getPlanoffering(){
		return planoffering;
	}
}