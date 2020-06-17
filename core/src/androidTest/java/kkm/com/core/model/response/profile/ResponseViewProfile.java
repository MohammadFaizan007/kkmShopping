package kkm.com.core.model.response.profile;

import com.google.gson.annotations.SerializedName;

public class ResponseViewProfile{

	@SerializedName("response")
	private String response;

	@SerializedName("apiUserProfile")
	private ApiUserProfile apiUserProfile;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setApiUserProfile(ApiUserProfile apiUserProfile){
		this.apiUserProfile = apiUserProfile;
	}

	public ApiUserProfile getApiUserProfile(){
		return apiUserProfile;
	}
}