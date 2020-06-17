package kkm.com.core.model.response.cvvResponse;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("result")
	private Result result;

	@SerializedName("exception")
	private String exception;

	@SerializedName("pagination")
	private String pagination;

	@SerializedName("cvv")
	private String cvv;

	public Result getResult(){
		return result;
	}

	public String getException(){
		return exception;
	}

	public String getPagination(){
		return pagination;
	}

	public String getCvv(){
		return cvv;
	}
}