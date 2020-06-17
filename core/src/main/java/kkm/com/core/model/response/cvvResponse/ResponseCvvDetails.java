package kkm.com.core.model.response.cvvResponse;

import com.google.gson.annotations.SerializedName;

public class ResponseCvvDetails{

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