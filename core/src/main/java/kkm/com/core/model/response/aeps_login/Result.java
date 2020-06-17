package kkm.com.core.model.response.aeps_login;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("payload")
	private Payload payload;

	@SerializedName("responseCode")
	private int responseCode;

	@SerializedName("status")
	private String status;

	public void setPayload(Payload payload){
		this.payload = payload;
	}

	public Payload getPayload(){
		return payload;
	}

	public void setResponseCode(int responseCode){
		this.responseCode = responseCode;
	}

	public int getResponseCode(){
		return responseCode;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}