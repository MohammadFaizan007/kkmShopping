package kkm.com.core.model.request.m2p;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RequestM2PRegistration{

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("country")
	private String country;

	@SerializedName("kycStatus")
	private String kycStatus;

	@SerializedName("gender")
	private String gender;

	@SerializedName("city")
	private String city;

	@SerializedName("documents")
	private ArrayList<KYCDocument> documents;

	@SerializedName("businessId")
	private String businessId;

	@SerializedName("title")
	private String title;

	@SerializedName("idNumber")
	private String idNumber;

	@SerializedName("emailAddress")
	private String emailAddress;

	@SerializedName("countryCode")
	private String countryCode;

	@SerializedName("ClientId")
	private String clientId;

	@SerializedName("state")
	private String state;

	@SerializedName("pincode")
	private String pincode;

	@SerializedName("kitNo")
	private String kitNo;

	@SerializedName("programType")
	private String programType;

	@SerializedName("address")
	private String address;

	@SerializedName("idType")
	private String idType;

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("address2")
	private String address2;

	@SerializedName("entityType")
	private String entityType;

	@SerializedName("cardType")
	private String cardType;

	@SerializedName("eKycRefNo")
	private String eKycRefNo;

	@SerializedName("entityId")
	private String entityId;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("idExpiry")
	private String idExpiry;

	@SerializedName("countryofIssue")
	private String countryofIssue;

	@SerializedName("businessType")
	private String businessType;

	@SerializedName("specialDate")
	private String specialDate;

	@SerializedName("contactNo")
	private String contactNo;

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public void setKycStatus(String kycStatus){
		this.kycStatus = kycStatus;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public void setCity(String city){
		this.city = city;
	}

	public void setDocuments(ArrayList<KYCDocument> documents){
		this.documents = documents;
	}

	public void setBusinessId(String businessId){
		this.businessId = businessId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public void setIdNumber(String idNumber){
		this.idNumber = idNumber;
	}

	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public void setState(String state){
		this.state = state;
	}

	public void setPincode(String pincode){
		this.pincode = pincode;
	}

	public void setKitNo(String kitNo){
		this.kitNo = kitNo;
	}

	public void setProgramType(String programType){
		this.programType = programType;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public void setIdType(String idType){
		this.idType = idType;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public void setEntityType(String entityType){
		this.entityType = entityType;
	}

	public void setCardType(String cardType){
		this.cardType = cardType;
	}

	public void setEKycRefNo(String eKycRefNo){
		this.eKycRefNo = eKycRefNo;
	}

	public void setEntityId(String entityId){
		this.entityId = entityId;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setIdExpiry(String idExpiry){
		this.idExpiry = idExpiry;
	}

	public void setCountryofIssue(String countryofIssue){
		this.countryofIssue = countryofIssue;
	}

	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}

	public void setSpecialDate(String specialDate){
		this.specialDate = specialDate;
	}

	public void setContactNo(String contactNo){
		this.contactNo = contactNo;
	}
}