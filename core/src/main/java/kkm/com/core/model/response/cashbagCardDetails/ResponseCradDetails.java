package kkm.com.core.model.response.cashbagCardDetails;

import com.google.gson.annotations.SerializedName;

public class ResponseCradDetails{

	@SerializedName("result")
	private Result result;

	@SerializedName("response")
	private String response;

	public Result getResult(){
		return result;
	}

	public String getResponse(){
		return response;
	}
}