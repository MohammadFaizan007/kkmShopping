package kkm.com.core.model.response.m2p;

import com.google.gson.annotations.SerializedName;

public class Exception{

	@SerializedName("detailMessage")
	private String detailMessage;

	public String getDetailMessage(){
		return detailMessage;
	}
}