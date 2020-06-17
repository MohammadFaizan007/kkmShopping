package kkm.com.core.model.response.m2p.requestkit;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("result")
	private String result;

	@SerializedName("exception")
	private String exception;

	@SerializedName("pagination")
	private String pagination;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setException(String exception){
		this.exception = exception;
	}

	public String getException(){
		return exception;
	}

	public void setPagination(String pagination){
		this.pagination = pagination;
	}

	public String getPagination(){
		return pagination;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"result = '" + result + '\'' + 
			",exception = '" + exception + '\'' + 
			",pagination = '" + pagination + '\'' + 
			"}";
		}
}