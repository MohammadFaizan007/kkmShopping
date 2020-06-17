package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestAmazonAdd{

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("ProductName")
	private String productName;

	@SerializedName("StoreName")
	private String storeName;

	@SerializedName("Amount")
	private String amount;

	@SerializedName("ShoppingDate")
	private String shoppingDate;

	@SerializedName("InvoiceNo")
	private String invoiceNo;

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public void setShoppingDate(String shoppingDate){
		this.shoppingDate = shoppingDate;
	}

	public void setInvoiceNo(String invoiceNo){
		this.invoiceNo = invoiceNo;
	}
}