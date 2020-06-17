package kkm.com.core.model.request.m2p;

import com.google.gson.annotations.SerializedName;

public class RequestgetCardDetails{

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("ClientId")
	private String clientId;

	@SerializedName("entityId")
	private String entityId;

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public void setEntityId(String entityId){
		this.entityId = entityId;
	}
}