package kkm.com.core.model.response.aeps_login;

import com.google.gson.annotations.SerializedName;

public class OwnerInfo{

	@SerializedName("ownerType")
	private String ownerType;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("mobileNumber")
	private String mobileNumber;

	@SerializedName("distributorId")
	private int distributorId;

	@SerializedName("emailId")
	private String emailId;

	@SerializedName("status")
	private String status;

	@SerializedName("identificationId")
	private int identificationId;

	public void setOwnerType(String ownerType){
		this.ownerType = ownerType;
	}

	public String getOwnerType(){
		return ownerType;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public void setDistributorId(int distributorId){
		this.distributorId = distributorId;
	}

	public int getDistributorId(){
		return distributorId;
	}

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setIdentificationId(int identificationId){
		this.identificationId = identificationId;
	}

	public int getIdentificationId(){
		return identificationId;
	}
}