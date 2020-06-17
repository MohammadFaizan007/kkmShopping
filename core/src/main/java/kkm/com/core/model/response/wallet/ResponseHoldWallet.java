package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHoldWallet{

	@SerializedName("holdwalletList")
	private List<HoldwalletListItem> holdwalletList;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	public void setHoldwalletList(List<HoldwalletListItem> holdwalletList){
		this.holdwalletList = holdwalletList;
	}

	public List<HoldwalletListItem> getHoldwalletList(){
		return holdwalletList;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseHoldWallet{" + 
			"holdwalletList = '" + holdwalletList + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}