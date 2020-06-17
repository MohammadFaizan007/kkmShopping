package kkm.com.core.model.response.aeps_login;

import com.google.gson.annotations.SerializedName;

public class ResponseAepsLogin{

	@SerializedName("result")
	private Result result;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}
}