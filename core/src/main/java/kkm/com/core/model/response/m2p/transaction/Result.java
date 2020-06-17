package kkm.com.core.model.response.m2p.transaction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result{

	@SerializedName("result")
	private List<ResultItem> result;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}
}