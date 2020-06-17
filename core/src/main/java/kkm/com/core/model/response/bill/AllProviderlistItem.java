package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllProviderlistItem{

	@SerializedName("ElectricityBillPaymentProviderlist")
	private List<BillPaymentProviderListItem> electricityBillPaymentProviderlist;

	@SerializedName("BroadbandProviderProviderlist")
	private List<BillPaymentProviderListItem> broadbandProviderProviderlist;

	@SerializedName("DometsticMoneyTransferProviderlist")
	private List<BillPaymentProviderListItem> dometsticMoneyTransferProviderlist;

	@SerializedName("waterbillpaymentProviderlist")
	private List<BillPaymentProviderListItem> waterbillpaymentProviderlist;

	@SerializedName("InsuranceProviderlist")
	private List<BillPaymentProviderListItem> insuranceProviderlist;

	@SerializedName("PostpaidrechargeProviderlist")
	private List<BillPaymentProviderListItem> postpaidrechargeProviderlist;

	@SerializedName("GasBillPaymentProviderlist")
	private List<BillPaymentProviderListItem> gasBillPaymentProviderlist;

	public void setElectricityBillPaymentProviderlist(List<BillPaymentProviderListItem> electricityBillPaymentProviderlist){
		this.electricityBillPaymentProviderlist = electricityBillPaymentProviderlist;
	}

	public List<BillPaymentProviderListItem> getElectricityBillPaymentProviderlist(){
		return electricityBillPaymentProviderlist;
	}

	public void setBroadbandProviderProviderlist(List<BillPaymentProviderListItem> broadbandProviderProviderlist){
		this.broadbandProviderProviderlist = broadbandProviderProviderlist;
	}

	public List<BillPaymentProviderListItem> getBroadbandProviderProviderlist(){
		return broadbandProviderProviderlist;
	}

	public void setDometsticMoneyTransferProviderlist(List<BillPaymentProviderListItem> dometsticMoneyTransferProviderlist){
		this.dometsticMoneyTransferProviderlist = dometsticMoneyTransferProviderlist;
	}

	public List<BillPaymentProviderListItem> getDometsticMoneyTransferProviderlist(){
		return dometsticMoneyTransferProviderlist;
	}

	public void setWaterbillpaymentProviderlist(List<BillPaymentProviderListItem> waterbillpaymentProviderlist){
		this.waterbillpaymentProviderlist = waterbillpaymentProviderlist;
	}

	public List<BillPaymentProviderListItem> getWaterbillpaymentProviderlist(){
		return waterbillpaymentProviderlist;
	}

	public void setInsuranceProviderlist(List<BillPaymentProviderListItem> insuranceProviderlist){
		this.insuranceProviderlist = insuranceProviderlist;
	}

	public List<BillPaymentProviderListItem> getInsuranceProviderlist(){
		return insuranceProviderlist;
	}

	public void setPostpaidrechargeProviderlist(List<BillPaymentProviderListItem> postpaidrechargeProviderlist){
		this.postpaidrechargeProviderlist = postpaidrechargeProviderlist;
	}

	public List<BillPaymentProviderListItem> getPostpaidrechargeProviderlist(){
		return postpaidrechargeProviderlist;
	}

	public void setGasBillPaymentProviderlist(List<BillPaymentProviderListItem> gasBillPaymentProviderlist){
		this.gasBillPaymentProviderlist = gasBillPaymentProviderlist;
	}

	public List<BillPaymentProviderListItem> getGasBillPaymentProviderlist(){
		return gasBillPaymentProviderlist;
	}
}