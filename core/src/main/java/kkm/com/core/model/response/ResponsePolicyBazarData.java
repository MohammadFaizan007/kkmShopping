package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponsePolicyBazarData{

	@SerializedName("response")
	private String response;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePolicyBazarData{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}