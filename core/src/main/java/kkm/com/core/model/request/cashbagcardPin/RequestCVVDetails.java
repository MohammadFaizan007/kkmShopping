package kkm.com.core.model.request.cashbagcardPin;

import com.google.gson.annotations.SerializedName;

public class RequestCVVDetails{

	@SerializedName("expiryDate")
	private String expiryDate;

	@SerializedName("kitNo")
	private String kitNo;

	@SerializedName("dob")
	private String dob;

	@SerializedName("entityId")
	private String entityId;

	public void setExpiryDate(String expiryDate){
		this.expiryDate = expiryDate;
	}

	public void setKitNo(String kitNo){
		this.kitNo = kitNo;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public void setEntityId(String entityId){
		this.entityId = entityId;
	}
}