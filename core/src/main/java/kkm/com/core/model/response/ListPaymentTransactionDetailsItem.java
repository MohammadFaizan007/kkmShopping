package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ListPaymentTransactionDetailsItem{

	@SerializedName("transactionNo")
	private String transactionNo;

	@SerializedName("tax")
	private String tax;

	@SerializedName("sessionId")
	private String sessionId;

	@SerializedName("type")
	private String type;

	@SerializedName("fk_MemId")
	private String fkMemId;

	@SerializedName("customerName")
	private String customerName;

	@SerializedName("total")
	private String total;

	@SerializedName("shipping")
	private String shipping;

	@SerializedName("subtotal")
	private String subtotal;

	@SerializedName("payerFirstName")
	private String payerFirstName;

	@SerializedName("transDesc")
	private String transDesc;

	@SerializedName("invoiceNo")
	private String invoiceNo;

	@SerializedName("fk_OrderId")
	private String fkOrderId;

	@SerializedName("paymentStatus")
	private String paymentStatus;

	@SerializedName("payerLastName")
	private String payerLastName;

	public void setTransactionNo(String transactionNo){
		this.transactionNo = transactionNo;
	}

	public String getTransactionNo(){
		return transactionNo;
	}

	public void setTax(String tax){
		this.tax = tax;
	}

	public String getTax(){
		return tax;
	}

	public void setSessionId(String sessionId){
		this.sessionId = sessionId;
	}

	public String getSessionId(){
		return sessionId;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setFkMemId(String fkMemId){
		this.fkMemId = fkMemId;
	}

	public String getFkMemId(){
		return fkMemId;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public String getCustomerName(){
		return customerName;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setShipping(String shipping){
		this.shipping = shipping;
	}

	public String getShipping(){
		return shipping;
	}

	public void setSubtotal(String subtotal){
		this.subtotal = subtotal;
	}

	public String getSubtotal(){
		return subtotal;
	}

	public void setPayerFirstName(String payerFirstName){
		this.payerFirstName = payerFirstName;
	}

	public String getPayerFirstName(){
		return payerFirstName;
	}

	public void setTransDesc(String transDesc){
		this.transDesc = transDesc;
	}

	public String getTransDesc(){
		return transDesc;
	}

	public void setInvoiceNo(String invoiceNo){
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceNo(){
		return invoiceNo;
	}

	public void setFkOrderId(String fkOrderId){
		this.fkOrderId = fkOrderId;
	}

	public String getFkOrderId(){
		return fkOrderId;
	}

	public void setPaymentStatus(String paymentStatus){
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentStatus(){
		return paymentStatus;
	}

	public void setPayerLastName(String payerLastName){
		this.payerLastName = payerLastName;
	}

	public String getPayerLastName(){
		return payerLastName;
	}
}