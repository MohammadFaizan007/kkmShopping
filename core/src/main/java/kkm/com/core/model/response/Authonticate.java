package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class Authonticate{

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("pbUserName")
	private String pbUserName;

	@SerializedName("kitno")
	private String kitno;

	@SerializedName("loginId")
	private String loginId;

	@SerializedName("kycStatus")
	private String kycStatus;

	@SerializedName("ewalletPassword")
	private String ewalletPassword;

	@SerializedName("city")
	private String city;

	@SerializedName("profilepic")
	private String profilepic;

	@SerializedName("lastLoginDate")
	private String lastLoginDate;

	@SerializedName("pbPassword")
	private String pbPassword;

	@SerializedName("state")
	private String state;

	@SerializedName("fk_SponsorId")
	private String fkSponsorId;

	@SerializedName("clientId")
	private String clientId;

	@SerializedName("address")
	private String address;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("emailID")
	private String emailID;

	@SerializedName("entityId")
	private String entityId;

	@SerializedName("message")
	private String message;

	@SerializedName("fk_MemID")
	private String fkMemID;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("isblocked")
	private String isblocked;

	@SerializedName("dob")
	private String dob;

	@SerializedName("doa")
	private String doa;

	@SerializedName("pinCode")
	private String pinCode;

	@SerializedName("inviteCode")
	private String inviteCode;

	@SerializedName("m2pStatus")
	private String m2pStatus;

	@SerializedName("pbid")
	private String pbid;

	@SerializedName("generatePin")
	private String generatePin;

	@SerializedName("tempPermanent")
	private String tempPermanent;

	@SerializedName("doj")
	private String doj;

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setPbUserName(String pbUserName){
		this.pbUserName = pbUserName;
	}

	public String getPbUserName(){
		return pbUserName;
	}

	public void setKitno(String kitno){
		this.kitno = kitno;
	}

	public String getKitno(){
		return kitno;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setKycStatus(String kycStatus){
		this.kycStatus = kycStatus;
	}

	public String getKycStatus(){
		return kycStatus;
	}

	public void setEwalletPassword(String ewalletPassword){
		this.ewalletPassword = ewalletPassword;
	}

	public String getEwalletPassword(){
		return ewalletPassword;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setProfilepic(String profilepic){
		this.profilepic = profilepic;
	}

	public String getProfilepic(){
		return profilepic;
	}

	public void setLastLoginDate(String lastLoginDate){
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginDate(){
		return lastLoginDate;
	}

	public void setPbPassword(String pbPassword){
		this.pbPassword = pbPassword;
	}

	public String getPbPassword(){
		return pbPassword;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setFkSponsorId(String fkSponsorId){
		this.fkSponsorId = fkSponsorId;
	}

	public String getFkSponsorId(){
		return fkSponsorId;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return clientId;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setEmailID(String emailID){
		this.emailID = emailID;
	}

	public String getEmailID(){
		return emailID;
	}

	public void setEntityId(String entityId){
		this.entityId = entityId;
	}

	public String getEntityId(){
		return entityId;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setFkMemID(String fkMemID){
		this.fkMemID = fkMemID;
	}

	public String getFkMemID(){
		return fkMemID;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setIsblocked(String isblocked){
		this.isblocked = isblocked;
	}

	public String getIsblocked(){
		return isblocked;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setDoa(String doa){
		this.doa = doa;
	}

	public String getDoa(){
		return doa;
	}

	public void setPinCode(String pinCode){
		this.pinCode = pinCode;
	}

	public String getPinCode(){
		return pinCode;
	}

	public void setInviteCode(String inviteCode){
		this.inviteCode = inviteCode;
	}

	public String getInviteCode(){
		return inviteCode;
	}

	public void setM2pStatus(String m2pStatus){
		this.m2pStatus = m2pStatus;
	}

	public String getM2pStatus(){
		return m2pStatus;
	}

	public void setPbid(String pbid){
		this.pbid = pbid;
	}

	public String getPbid(){
		return pbid;
	}

	public void setGeneratePin(String generatePin){
		this.generatePin = generatePin;
	}

	public String getGeneratePin(){
		return generatePin;
	}

	public void setTempPermanent(String tempPermanent){
		this.tempPermanent = tempPermanent;
	}

	public String getTempPermanent(){
		return tempPermanent;
	}

	public void setDoj(String doj){
		this.doj = doj;
	}

	public String getDoj(){
		return doj;
	}

	@Override
 	public String toString(){
		return 
			"Authonticate{" + 
			"lastName = '" + lastName + '\'' + 
			",pbUserName = '" + pbUserName + '\'' + 
			",kitno = '" + kitno + '\'' + 
			",loginId = '" + loginId + '\'' + 
			",kycStatus = '" + kycStatus + '\'' + 
			",ewalletPassword = '" + ewalletPassword + '\'' + 
			",city = '" + city + '\'' + 
			",profilepic = '" + profilepic + '\'' + 
			",lastLoginDate = '" + lastLoginDate + '\'' + 
			",pbPassword = '" + pbPassword + '\'' + 
			",state = '" + state + '\'' + 
			",fk_SponsorId = '" + fkSponsorId + '\'' + 
			",clientId = '" + clientId + '\'' + 
			",address = '" + address + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",emailID = '" + emailID + '\'' + 
			",entityId = '" + entityId + '\'' + 
			",message = '" + message + '\'' + 
			",fk_MemID = '" + fkMemID + '\'' + 
			",firstName = '" + firstName + '\'' + 
			",isblocked = '" + isblocked + '\'' + 
			",dob = '" + dob + '\'' + 
			",doa = '" + doa + '\'' + 
			",pinCode = '" + pinCode + '\'' + 
			",inviteCode = '" + inviteCode + '\'' + 
			",m2pStatus = '" + m2pStatus + '\'' + 
			",pbid = '" + pbid + '\'' + 
			",generatePin = '" + generatePin + '\'' + 
			",tempPermanent = '" + tempPermanent + '\'' + 
			",doj = '" + doj + '\'' + 
			"}";
		}
}