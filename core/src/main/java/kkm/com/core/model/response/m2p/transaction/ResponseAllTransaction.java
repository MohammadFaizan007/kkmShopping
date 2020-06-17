package kkm.com.core.model.response.m2p.transaction;

import com.google.gson.annotations.SerializedName;

public class ResponseAllTransaction{

	@SerializedName("result")
	private Result result;

	@SerializedName("exception")
	private Object exception;

	@SerializedName("response")
	private String response;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setException(Object exception){
		this.exception = exception;
	}

	public Object getException(){
		return exception;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}
}