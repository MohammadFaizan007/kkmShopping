package kkm.com.core.model.response.tatasky.request;

import com.google.gson.annotations.SerializedName;

public class RequestRegion{

	@SerializedName("Type")
	private String type;

	@SerializedName("Amount_All")
	private String amountAll;

	@SerializedName("Amount")
	private String amount;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setAmountAll(String amountAll){
		this.amountAll = amountAll;
	}

	public String getAmountAll(){
		return amountAll;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	@Override
 	public String toString(){
		return 
			"RequestRegion{" + 
			"type = '" + type + '\'' + 
			",amount_All = '" + amountAll + '\'' + 
			",amount = '" + amount + '\'' + 
			"}";
		}
}