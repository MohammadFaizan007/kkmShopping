package kkm.com.core.model.request.m2p;

import com.google.gson.annotations.SerializedName;

public class RequestKit{

	@SerializedName("clientId")
	private String clientId;

	@SerializedName("LoginId")
	private String loginId;

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return clientId;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	@Override
 	public String toString(){
		return 
			"RequestKit{" + 
			"clientId = '" + clientId + '\'' + 
			",loginId = '" + loginId + '\'' + 
			"}";
		}
}