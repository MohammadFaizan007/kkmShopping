package kkm.com.core.model.response.unclearLedger;

import com.google.gson.annotations.SerializedName;

public class PayoutLedgerSelectDetailsListItem{

	@SerializedName("comStatus")
	private String comStatus;

	@SerializedName("amount")
	private String amount;

	@SerializedName("commissionPercentage")
	private String commissionPercentage;

	@SerializedName("incomeType")
	private String incomeType;

	@SerializedName("currentDate")
	private String currentDate;

	@SerializedName("remark")
	private String remark;

	@SerializedName("businessAmount")
	private String businessAmount;

	@SerializedName("fromLogin")
	private String fromLogin;

	public void setComStatus(String comStatus){
		this.comStatus = comStatus;
	}

	public String getComStatus(){
		return comStatus;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setCommissionPercentage(String commissionPercentage){
		this.commissionPercentage = commissionPercentage;
	}

	public String getCommissionPercentage(){
		return commissionPercentage;
	}

	public void setIncomeType(String incomeType){
		this.incomeType = incomeType;
	}

	public String getIncomeType(){
		return incomeType;
	}

	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}

	public String getCurrentDate(){
		return currentDate;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setBusinessAmount(String businessAmount){
		this.businessAmount = businessAmount;
	}

	public String getBusinessAmount(){
		return businessAmount;
	}

	public void setFromLogin(String fromLogin){
		this.fromLogin = fromLogin;
	}

	public String getFromLogin(){
		return fromLogin;
	}

	@Override
 	public String toString(){
		return 
			"PayoutLedgerSelectDetailsListItem{" + 
			"comStatus = '" + comStatus + '\'' + 
			",amount = '" + amount + '\'' + 
			",commissionPercentage = '" + commissionPercentage + '\'' + 
			",incomeType = '" + incomeType + '\'' + 
			",currentDate = '" + currentDate + '\'' + 
			",remark = '" + remark + '\'' + 
			",businessAmount = '" + businessAmount + '\'' + 
			",fromLogin = '" + fromLogin + '\'' + 
			"}";
		}
}