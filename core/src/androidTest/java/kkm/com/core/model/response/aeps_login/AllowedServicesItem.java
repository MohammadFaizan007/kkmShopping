package kkm.com.core.model.response.aeps_login;

import com.google.gson.annotations.SerializedName;

public class AllowedServicesItem{

	@SerializedName("ownerType")
	private String ownerType;

	@SerializedName("displayName")
	private String displayName;

	@SerializedName("id")
	private int id;

	@SerializedName("serviceName")
	private String serviceName;

	@SerializedName("ownerId")
	private int ownerId;

	@SerializedName("status")
	private String status;

	public void setOwnerType(String ownerType){
		this.ownerType = ownerType;
	}

	public String getOwnerType(){
		return ownerType;
	}

	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}

	public String getServiceName(){
		return serviceName;
	}

	public void setOwnerId(int ownerId){
		this.ownerId = ownerId;
	}

	public int getOwnerId(){
		return ownerId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}