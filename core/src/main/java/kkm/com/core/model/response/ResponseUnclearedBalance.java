package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseUnclearedBalance{

	@SerializedName("unclearedamount")
	private String unclearedamount;

	@SerializedName("kitNo")
	private String kitNo;

	@SerializedName("bagAmount")
	private String bagAmount;

	@SerializedName("response")
	private String response;

	@SerializedName("m2pStatus")
	private String m2pStatus;

	@SerializedName("holdAmount")
	private String holdAmount;

	@SerializedName("cardType")
	private String cardType;

	@SerializedName("fK_ToId")
	private String fKToId;

	@SerializedName("holdReason")
	private String holdReason;

	@SerializedName("totalWalletAmount")
	private String totalWalletAmount;

	@SerializedName("totalCommission")
	private String totalCommission;

	@SerializedName("notice")
	private String notice;

	public void setUnclearedamount(String unclearedamount){
		this.unclearedamount = unclearedamount;
	}

	public String getUnclearedamount(){
		return unclearedamount;
	}

	public void setKitNo(String kitNo){
		this.kitNo = kitNo;
	}

	public String getKitNo(){
		return kitNo;
	}

	public void setBagAmount(String bagAmount){
		this.bagAmount = bagAmount;
	}

	public String getBagAmount(){
		return bagAmount;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setM2pStatus(String m2pStatus){
		this.m2pStatus = m2pStatus;
	}

	public String getM2pStatus(){
		return m2pStatus;
	}

	public void setHoldAmount(String holdAmount){
		this.holdAmount = holdAmount;
	}

	public String getHoldAmount(){
		return holdAmount;
	}

	public void setCardType(String cardType){
		this.cardType = cardType;
	}

	public String getCardType(){
		return cardType;
	}

	public void setFKToId(String fKToId){
		this.fKToId = fKToId;
	}

	public String getFKToId(){
		return fKToId;
	}

	public void setHoldReason(String holdReason){
		this.holdReason = holdReason;
	}

	public String getHoldReason(){
		return holdReason;
	}

	public void setTotalWalletAmount(String totalWalletAmount){
		this.totalWalletAmount = totalWalletAmount;
	}

	public String getTotalWalletAmount(){
		return totalWalletAmount;
	}

	public void setTotalCommission(String totalCommission){
		this.totalCommission = totalCommission;
	}

	public String getTotalCommission(){
		return totalCommission;
	}

	public void setNotice(String notice){
		this.notice = notice;
	}

	public String getNotice(){
		return notice;
	}
}