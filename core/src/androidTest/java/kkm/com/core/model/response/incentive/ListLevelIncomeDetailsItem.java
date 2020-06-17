package kkm.com.core.model.response.incentive;

import com.google.gson.annotations.SerializedName;

public class ListLevelIncomeDetailsItem{

	@SerializedName("amount")
	private String amount;

	@SerializedName("utilityName")
	private String utilityName;

	@SerializedName("businessAmount")
	private String businessAmount;

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setUtilityName(String utilityName){
		this.utilityName = utilityName;
	}

	public String getUtilityName(){
		return utilityName;
	}

	public void setBusinessAmount(String businessAmount){
		this.businessAmount = businessAmount;
	}

	public String getBusinessAmount(){
		return businessAmount;
	}
}