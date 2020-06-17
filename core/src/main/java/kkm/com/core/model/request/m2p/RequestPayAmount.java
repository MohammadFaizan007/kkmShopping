package kkm.com.core.model.request.m2p;

import com.google.gson.annotations.SerializedName;

public class RequestPayAmount{

	@SerializedName("billRefNo")
	private String billRefNo;

	@SerializedName("fromEntityId")
	private String fromEntityId;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("amount")
	private String amount;

	@SerializedName("productId")
	private String productId;

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("description")
	private String description;

	@SerializedName("businessEntityId")
	private String businessEntityId;

	@SerializedName("senderCardNo")
	private String senderCardNo;

	@SerializedName("merchantData")
	private String merchantData;

	@SerializedName("transactionOrigin")
	private String transactionOrigin;

	@SerializedName("transactionType")
	private String transactionType;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("ClientId")
	private String clientId;

	@SerializedName("toEntityId")
	private String toEntityId;

	@SerializedName("externalTransactionId")
	private String externalTransactionId;

	@SerializedName("additionalData")
	private String additionalData;

	@SerializedName("businessType")
	private String businessType;

	@SerializedName("contactNo")
	private String contactNo;

	public void setBillRefNo(String billRefNo){
		this.billRefNo = billRefNo;
	}

	public String getBillRefNo(){
		return billRefNo;
	}

	public void setFromEntityId(String fromEntityId){
		this.fromEntityId = fromEntityId;
	}

	public String getFromEntityId(){
		return fromEntityId;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setBusinessEntityId(String businessEntityId){
		this.businessEntityId = businessEntityId;
	}

	public String getBusinessEntityId(){
		return businessEntityId;
	}

	public void setSenderCardNo(String senderCardNo){
		this.senderCardNo = senderCardNo;
	}

	public String getSenderCardNo(){
		return senderCardNo;
	}

	public void setMerchantData(String merchantData){
		this.merchantData = merchantData;
	}

	public String getMerchantData(){
		return merchantData;
	}

	public void setTransactionOrigin(String transactionOrigin){
		this.transactionOrigin = transactionOrigin;
	}

	public String getTransactionOrigin(){
		return transactionOrigin;
	}

	public void setTransactionType(String transactionType){
		this.transactionType = transactionType;
	}

	public String getTransactionType(){
		return transactionType;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return clientId;
	}

	public void setToEntityId(String toEntityId){
		this.toEntityId = toEntityId;
	}

	public String getToEntityId(){
		return toEntityId;
	}

	public void setExternalTransactionId(String externalTransactionId){
		this.externalTransactionId = externalTransactionId;
	}

	public String getExternalTransactionId(){
		return externalTransactionId;
	}

	public void setAdditionalData(String additionalData){
		this.additionalData = additionalData;
	}

	public String getAdditionalData(){
		return additionalData;
	}

	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}

	public String getBusinessType(){
		return businessType;
	}

	public void setContactNo(String contactNo){
		this.contactNo = contactNo;
	}

	public String getContactNo(){
		return contactNo;
	}

	@Override
 	public String toString(){
		return 
			"RequestPayAmount{" + 
			"billRefNo = '" + billRefNo + '\'' + 
			",fromEntityId = '" + fromEntityId + '\'' + 
			",lastName = '" + lastName + '\'' + 
			",amount = '" + amount + '\'' + 
			",productId = '" + productId + '\'' + 
			",loginId = '" + loginId + '\'' + 
			",description = '" + description + '\'' + 
			",businessEntityId = '" + businessEntityId + '\'' + 
			",senderCardNo = '" + senderCardNo + '\'' + 
			",merchantData = '" + merchantData + '\'' + 
			",transactionOrigin = '" + transactionOrigin + '\'' + 
			",transactionType = '" + transactionType + '\'' + 
			",firstName = '" + firstName + '\'' + 
			",clientId = '" + clientId + '\'' + 
			",toEntityId = '" + toEntityId + '\'' + 
			",externalTransactionId = '" + externalTransactionId + '\'' + 
			",additionalData = '" + additionalData + '\'' + 
			",businessType = '" + businessType + '\'' + 
			",contactNo = '" + contactNo + '\'' + 
			"}";
		}
}