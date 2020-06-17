package kkm.com.core.model.response.profile;

import com.google.gson.annotations.SerializedName;

public class ApiUserProfile{

	@SerializedName("uniqueNo")
	private String uniqueNo;

	@SerializedName("country")
	private String country;

	@SerializedName("loginId")
	private String loginId;

	@SerializedName("fathersName")
	private String fathersName;

	@SerializedName("profilepic")
	private String profilepic;

	@SerializedName("cancelledChequeAttach")
	private String cancelledChequeAttach;

	@SerializedName("joiningDate")
	private String joiningDate;

	@SerializedName("policyNo")
	private String policyNo;

	@SerializedName("insuranceType")
	private String insuranceType;

	@SerializedName("temppermanent")
	private String temppermanent;

	@SerializedName("state")
	private String state;

	@SerializedName("addressBackAttach")
	private String addressBackAttach;

	@SerializedName("adhaarCardAttach")
	private String adhaarCardAttach;

	@SerializedName("panCard")
	private String panCard;

	@SerializedName("aadhaarNo")
	private String aadhaarNo;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("vehicleNo")
	private String vehicleNo;

	@SerializedName("marritalStatus")
	private String marritalStatus;

	@SerializedName("purchasedYear")
	private String purchasedYear;

	@SerializedName("isApprovedAdhaar")
	private String isApprovedAdhaar;

	@SerializedName("panCardAttach")
	private String panCardAttach;

	@SerializedName("dob")
	private String dob;

	@SerializedName("pinCode")
	private String pinCode;

	@SerializedName("policyHolderName")
	private String policyHolderName;

	@SerializedName("insNomineeName")
	private String insNomineeName;

	@SerializedName("relationwithNominee")
	private String relationwithNominee;

	@SerializedName("status")
	private String status;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("gender")
	private String gender;

	@SerializedName("city")
	private String city;

	@SerializedName("displayName")
	private String displayName;

	@SerializedName("bankName")
	private String bankName;

	@SerializedName("fk_MemId")
	private String fkMemId;

	@SerializedName("accountHolderName")
	private String accountHolderName;

	@SerializedName("expiryDate")
	private String expiryDate;

	@SerializedName("premium")
	private String premium;

	@SerializedName("accountNo")
	private String accountNo;

	@SerializedName("ifscCode")
	private String ifscCode;

	@SerializedName("email")
	private String email;

	@SerializedName("address3")
	private String address3;

	@SerializedName("genCompanyName")
	private String genCompanyName;

	@SerializedName("address2")
	private String address2;

	@SerializedName("address1")
	private String address1;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("branchName")
	private String branchName;

	@SerializedName("nomineeName")
	private String nomineeName;

	@SerializedName("totalMember")
	private String totalMember;

	@SerializedName("tehsil")
	private String tehsil;

	@SerializedName("genPremium")
	private String genPremium;

	@SerializedName("insCompanyName")
	private String insCompanyName;

	@SerializedName("isApprovedPanCard")
	private String isApprovedPanCard;

	@SerializedName("insuranceNo")
	private String insuranceNo;

	@SerializedName("addressProofType")
	private String addressProofType;

	@SerializedName("isApprovedCheque")
	private Object isApprovedCheque;

	public void setUniqueNo(String uniqueNo){
		this.uniqueNo = uniqueNo;
	}

	public String getUniqueNo(){
		return uniqueNo;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setFathersName(String fathersName){
		this.fathersName = fathersName;
	}

	public String getFathersName(){
		return fathersName;
	}

	public void setProfilepic(String profilepic){
		this.profilepic = profilepic;
	}

	public String getProfilepic(){
		return profilepic;
	}

	public void setCancelledChequeAttach(String cancelledChequeAttach){
		this.cancelledChequeAttach = cancelledChequeAttach;
	}

	public String getCancelledChequeAttach(){
		return cancelledChequeAttach;
	}

	public void setJoiningDate(String joiningDate){
		this.joiningDate = joiningDate;
	}

	public String getJoiningDate(){
		return joiningDate;
	}

	public void setPolicyNo(String policyNo){
		this.policyNo = policyNo;
	}

	public String getPolicyNo(){
		return policyNo;
	}

	public void setInsuranceType(String insuranceType){
		this.insuranceType = insuranceType;
	}

	public String getInsuranceType(){
		return insuranceType;
	}

	public void setTemppermanent(String temppermanent){
		this.temppermanent = temppermanent;
	}

	public String getTemppermanent(){
		return temppermanent;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setAddressBackAttach(String addressBackAttach){
		this.addressBackAttach = addressBackAttach;
	}

	public String getAddressBackAttach(){
		return addressBackAttach;
	}

	public void setAdhaarCardAttach(String adhaarCardAttach){
		this.adhaarCardAttach = adhaarCardAttach;
	}

	public String getAdhaarCardAttach(){
		return adhaarCardAttach;
	}

	public void setPanCard(String panCard){
		this.panCard = panCard;
	}

	public String getPanCard(){
		return panCard;
	}

	public void setAadhaarNo(String aadhaarNo){
		this.aadhaarNo = aadhaarNo;
	}

	public String getAadhaarNo(){
		return aadhaarNo;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleNo(){
		return vehicleNo;
	}

	public void setMarritalStatus(String marritalStatus){
		this.marritalStatus = marritalStatus;
	}

	public String getMarritalStatus(){
		return marritalStatus;
	}

	public void setPurchasedYear(String purchasedYear){
		this.purchasedYear = purchasedYear;
	}

	public String getPurchasedYear(){
		return purchasedYear;
	}

	public void setIsApprovedAdhaar(String isApprovedAdhaar){
		this.isApprovedAdhaar = isApprovedAdhaar;
	}

	public String getIsApprovedAdhaar(){
		return isApprovedAdhaar;
	}

	public void setPanCardAttach(String panCardAttach){
		this.panCardAttach = panCardAttach;
	}

	public String getPanCardAttach(){
		return panCardAttach;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setPinCode(String pinCode){
		this.pinCode = pinCode;
	}

	public String getPinCode(){
		return pinCode;
	}

	public void setPolicyHolderName(String policyHolderName){
		this.policyHolderName = policyHolderName;
	}

	public String getPolicyHolderName(){
		return policyHolderName;
	}

	public void setInsNomineeName(String insNomineeName){
		this.insNomineeName = insNomineeName;
	}

	public String getInsNomineeName(){
		return insNomineeName;
	}

	public void setRelationwithNominee(String relationwithNominee){
		this.relationwithNominee = relationwithNominee;
	}

	public String getRelationwithNominee(){
		return relationwithNominee;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankName(){
		return bankName;
	}

	public void setFkMemId(String fkMemId){
		this.fkMemId = fkMemId;
	}

	public String getFkMemId(){
		return fkMemId;
	}

	public void setAccountHolderName(String accountHolderName){
		this.accountHolderName = accountHolderName;
	}

	public String getAccountHolderName(){
		return accountHolderName;
	}

	public void setExpiryDate(String expiryDate){
		this.expiryDate = expiryDate;
	}

	public String getExpiryDate(){
		return expiryDate;
	}

	public void setPremium(String premium){
		this.premium = premium;
	}

	public String getPremium(){
		return premium;
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}

	public String getAccountNo(){
		return accountNo;
	}

	public void setIfscCode(String ifscCode){
		this.ifscCode = ifscCode;
	}

	public String getIfscCode(){
		return ifscCode;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAddress3(String address3){
		this.address3 = address3;
	}

	public String getAddress3(){
		return address3;
	}

	public void setGenCompanyName(String genCompanyName){
		this.genCompanyName = genCompanyName;
	}

	public String getGenCompanyName(){
		return genCompanyName;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return address2;
	}

	public void setAddress1(String address1){
		this.address1 = address1;
	}

	public String getAddress1(){
		return address1;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setBranchName(String branchName){
		this.branchName = branchName;
	}

	public String getBranchName(){
		return branchName;
	}

	public void setNomineeName(String nomineeName){
		this.nomineeName = nomineeName;
	}

	public String getNomineeName(){
		return nomineeName;
	}

	public void setTotalMember(String totalMember){
		this.totalMember = totalMember;
	}

	public String getTotalMember(){
		return totalMember;
	}

	public void setTehsil(String tehsil){
		this.tehsil = tehsil;
	}

	public String getTehsil(){
		return tehsil;
	}

	public void setGenPremium(String genPremium){
		this.genPremium = genPremium;
	}

	public String getGenPremium(){
		return genPremium;
	}

	public void setInsCompanyName(String insCompanyName){
		this.insCompanyName = insCompanyName;
	}

	public String getInsCompanyName(){
		return insCompanyName;
	}

	public void setIsApprovedPanCard(String isApprovedPanCard){
		this.isApprovedPanCard = isApprovedPanCard;
	}

	public String getIsApprovedPanCard(){
		return isApprovedPanCard;
	}

	public void setInsuranceNo(String insuranceNo){
		this.insuranceNo = insuranceNo;
	}

	public String getInsuranceNo(){
		return insuranceNo;
	}

	public void setAddressProofType(String addressProofType){
		this.addressProofType = addressProofType;
	}

	public String getAddressProofType(){
		return addressProofType;
	}

	public void setIsApprovedCheque(Object isApprovedCheque){
		this.isApprovedCheque = isApprovedCheque;
	}

	public Object getIsApprovedCheque(){
		return isApprovedCheque;
	}
}