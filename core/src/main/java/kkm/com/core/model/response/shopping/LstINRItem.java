package kkm.com.core.model.response.shopping;

import com.google.gson.annotations.SerializedName;

public class LstINRItem{

	@SerializedName("saleAmount")
	private double saleAmount;

	@SerializedName("subId")
	private String subId;

	@SerializedName("fK_MEMId")
	private int fKMEMId;

	@SerializedName("payout")
	private double payout;

	@SerializedName("storeName")
	private String storeName;

	@SerializedName("transactionDate")
	private String transactionDate;

	@SerializedName("transactionId")
	private String transactionId;

	@SerializedName("status")
	private String status;

	public void setSaleAmount(double saleAmount){
		this.saleAmount = saleAmount;
	}

	public double getSaleAmount(){
		return saleAmount;
	}

	public void setSubId(String subId){
		this.subId = subId;
	}

	public String getSubId(){
		return subId;
	}

	public void setFKMEMId(int fKMEMId){
		this.fKMEMId = fKMEMId;
	}

	public int getFKMEMId(){
		return fKMEMId;
	}

	public void setPayout(double payout){
		this.payout = payout;
	}

	public double getPayout(){
		return payout;
	}

	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public String getStoreName(){
		return storeName;
	}

	public void setTransactionDate(String transactionDate){
		this.transactionDate = transactionDate;
	}

	public String getTransactionDate(){
		return transactionDate;
	}

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LstINRItem{" + 
			"saleAmount = '" + saleAmount + '\'' + 
			",subId = '" + subId + '\'' + 
			",fK_MEMId = '" + fKMEMId + '\'' + 
			",payout = '" + payout + '\'' + 
			",storeName = '" + storeName + '\'' + 
			",transactionDate = '" + transactionDate + '\'' + 
			",transactionId = '" + transactionId + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}