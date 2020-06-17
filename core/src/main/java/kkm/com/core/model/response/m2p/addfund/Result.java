package kkm.com.core.model.response.m2p.addfund;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("result")
	private Result result;

	@SerializedName("exception")
	private String exception;

	@SerializedName("pagination")
	private String pagination;

	@SerializedName("txId")
	private long txId;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
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

	public void setTxId(long txId){
		this.txId = txId;
	}

	public long getTxId(){
		return txId;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"result = '" + result + '\'' + 
			",exception = '" + exception + '\'' + 
			",pagination = '" + pagination + '\'' + 
			",txId = '" + txId + '\'' + 
			"}";
		}
}