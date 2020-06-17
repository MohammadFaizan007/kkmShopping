package kkm.com.core.model.response.m2p.card_details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result{

	@SerializedName("result")
	private Result result;

	@SerializedName("exception")
	private String exception;

	@SerializedName("pagination")
	private String pagination;

	@SerializedName("cardList")
	private List<String> cardList;

	@SerializedName("cardStatusList")
	private List<String> cardStatusList;

	@SerializedName("cardTypeList")
	private List<String> cardTypeList;

	@SerializedName("kitList")
	private List<String> kitList;

	@SerializedName("networkTypeList")
	private List<String> networkTypeList;

	@SerializedName("expiryDateList")
	private List<String> expiryDateList;

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

	public void setCardList(List<String> cardList){
		this.cardList = cardList;
	}

	public List<String> getCardList(){
		return cardList;
	}

	public void setCardStatusList(List<String> cardStatusList){
		this.cardStatusList = cardStatusList;
	}

	public List<String> getCardStatusList(){
		return cardStatusList;
	}

	public void setCardTypeList(List<String> cardTypeList){
		this.cardTypeList = cardTypeList;
	}

	public List<String> getCardTypeList(){
		return cardTypeList;
	}

	public void setKitList(List<String> kitList){
		this.kitList = kitList;
	}

	public List<String> getKitList(){
		return kitList;
	}

	public void setNetworkTypeList(List<String> networkTypeList){
		this.networkTypeList = networkTypeList;
	}

	public List<String> getNetworkTypeList(){
		return networkTypeList;
	}

	public void setExpiryDateList(List<String> expiryDateList){
		this.expiryDateList = expiryDateList;
	}

	public List<String> getExpiryDateList(){
		return expiryDateList;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"result = '" + result + '\'' + 
			",exception = '" + exception + '\'' + 
			",pagination = '" + pagination + '\'' + 
			",cardList = '" + cardList + '\'' + 
			",cardStatusList = '" + cardStatusList + '\'' + 
			",cardTypeList = '" + cardTypeList + '\'' + 
			",kitList = '" + kitList + '\'' + 
			",networkTypeList = '" + networkTypeList + '\'' + 
			",expiryDateList = '" + expiryDateList + '\'' + 
			"}";
		}
}