package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class HoldwalletListItem{

	@SerializedName("date")
	private String date;

	@SerializedName("amount")
	private String amount;

	@SerializedName("loginId")
	private String loginId;

	@SerializedName("name")
	private String name;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"HoldwalletListItem{" + 
			"date = '" + date + '\'' + 
			",amount = '" + amount + '\'' + 
			",loginId = '" + loginId + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}