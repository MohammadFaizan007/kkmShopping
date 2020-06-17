package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("response")
	private String response;

	@SerializedName("authonticate")
	private Authonticate authonticate;

	@SerializedName("message")
	private String message;

	@SerializedName("bithdayUrl")
	private String bithdayUrl;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setAuthonticate(Authonticate authonticate){
		this.authonticate = authonticate;
	}

	public Authonticate getAuthonticate(){
		return authonticate;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setBithdayUrl(String bithdayUrl){
		this.bithdayUrl = bithdayUrl;
	}

	public String getBithdayUrl(){
		return bithdayUrl;
	}

	@Override
 	public String toString(){
		return 
			"ResponseLogin{" + 
			"response = '" + response + '\'' + 
			",authonticate = '" + authonticate + '\'' + 
			",message = '" + message + '\'' + 
			",bithdayUrl = '" + bithdayUrl + '\'' + 
			"}";
		}
}