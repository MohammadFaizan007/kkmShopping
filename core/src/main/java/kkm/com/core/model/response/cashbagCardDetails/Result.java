package kkm.com.core.model.response.cashbagCardDetails;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("result")
	private Result result;

	@SerializedName("exception")
	private String exception;

	@SerializedName("pagination")
	private String pagination;

	@SerializedName("cardList")
	private String cardList;

	@SerializedName("cardStatusList")
	private String cardStatusList;

	@SerializedName("cardTypeList")
	private String cardTypeList;

	@SerializedName("kitList")
	private String kitList;

	@SerializedName("networkTypeList")
	private String networkTypeList;

	@SerializedName("expiryDateList")
	private String expiryDateList;

	@SerializedName("Name")
	private String Name;

	@SerializedName("Dob")
	private String Dob;

	public String getDob() {
		return Dob;
	}

	public String getName() {
		return Name;
	}

	public Result getResult(){
		return result;
	}

	public String getException(){
		return exception;
	}

	public String getPagination(){
		return pagination;
	}

	public String getCardList(){
		return cardList;
	}

	public String getCardStatusList(){
		return cardStatusList;
	}

	public String getCardTypeList(){
		return cardTypeList;
	}

	public String getKitList(){
		return kitList;
	}

	public String getNetworkTypeList(){
		return networkTypeList;
	}

	public String getExpiryDateList(){
		return expiryDateList;
	}
}