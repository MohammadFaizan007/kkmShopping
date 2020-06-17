package kkm.com.core.model.response.updateProfile;

import com.google.gson.annotations.SerializedName;

public class ResponseGetProfile{

	@SerializedName("personalDetailOld")
	private PersonalDetailOld personalDetailOld;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("personalDetailNew")
	private PersonalDetailNew personalDetailNew;

	@SerializedName("status")
	private String status;

	public PersonalDetailOld getPersonalDetailOld(){
		return personalDetailOld;
	}

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public PersonalDetailNew getPersonalDetailNew(){
		return personalDetailNew;
	}

	public String getStatus(){
		return status;
	}
}