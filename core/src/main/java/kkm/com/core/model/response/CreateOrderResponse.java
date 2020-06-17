package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class CreateOrderResponse{

	@SerializedName("orderNo")
	private String orderNo;

	@SerializedName("payAmount")
	private String payAmount;

	@SerializedName("walletAmmount")
	private String walletAmmount;

	@SerializedName("orderId")
	private String orderId;

	@SerializedName("response")
	private String response;

	@SerializedName("uType")
	private String uType;

	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	public String getOrderNo(){
		return orderNo;
	}

	public void setPayAmount(String payAmount){
		this.payAmount = payAmount;
	}

	public String getPayAmount(){
		return payAmount;
	}

	public void setWalletAmmount(String walletAmmount){
		this.walletAmmount = walletAmmount;
	}

	public String getWalletAmmount(){
		return walletAmmount;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setUType(String uType){
		this.uType = uType;
	}

	public String getUType(){
		return uType;
	}
}