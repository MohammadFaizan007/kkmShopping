package kkm.com.core.model.response.tatasky.productlisttwo;

import com.google.gson.annotations.SerializedName;

public class RequestProductListTwo{

	@SerializedName("Type")
	private String type;

	@SerializedName("Amount_All")
	private String amountAll;

	@SerializedName("NUMBER")
	private String nUMBER;

	@SerializedName("Amount")
	private String amount;

	@SerializedName("ProductId")
	private String productId;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setAmountAll(String amountAll){
		this.amountAll = amountAll;
	}

	public String getAmountAll(){
		return amountAll;
	}

	public void setNUMBER(String nUMBER){
		this.nUMBER = nUMBER;
	}

	public String getNUMBER(){
		return nUMBER;
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

	@Override
 	public String toString(){
		return 
			"RequestProductListTwo{" + 
			"type = '" + type + '\'' + 
			",amount_All = '" + amountAll + '\'' + 
			",nUMBER = '" + nUMBER + '\'' + 
			",amount = '" + amount + '\'' + 
			",productId = '" + productId + '\'' + 
			"}";
		}
}