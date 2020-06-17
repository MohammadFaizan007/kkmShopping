package kkm.com.core.model.response.aeps_login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload{

	@SerializedName("cnfId")
	private int cnfId;

	@SerializedName("redirectionInfo")
	private RedirectionInfo redirectionInfo;

	@SerializedName("allowedServices")
	private List<AllowedServicesItem> allowedServices;

	@SerializedName("ownerInfo")
	private OwnerInfo ownerInfo;

	@SerializedName("tokenExpiry")
	private int tokenExpiry;

	@SerializedName("token")
	private String token;

	public void setCnfId(int cnfId){
		this.cnfId = cnfId;
	}

	public int getCnfId(){
		return cnfId;
	}

	public void setRedirectionInfo(RedirectionInfo redirectionInfo){
		this.redirectionInfo = redirectionInfo;
	}

	public RedirectionInfo getRedirectionInfo(){
		return redirectionInfo;
	}

	public void setAllowedServices(List<AllowedServicesItem> allowedServices){
		this.allowedServices = allowedServices;
	}

	public List<AllowedServicesItem> getAllowedServices(){
		return allowedServices;
	}

	public void setOwnerInfo(OwnerInfo ownerInfo){
		this.ownerInfo = ownerInfo;
	}

	public OwnerInfo getOwnerInfo(){
		return ownerInfo;
	}

	public void setTokenExpiry(int tokenExpiry){
		this.tokenExpiry = tokenExpiry;
	}

	public int getTokenExpiry(){
		return tokenExpiry;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}