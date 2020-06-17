package kkm.com.core.model.request.profilemlm;

import com.google.gson.annotations.SerializedName;

public class RequestInsuranceUpdate{

	@SerializedName("Fk_MemId")
	private String fkMemId;

	@SerializedName("PolicyNo")
	private String policyNo;

	@SerializedName("ExpiryDate")
	private String expiryDate;

	@SerializedName("VehicleNo")
	private String vehicleNo;

	@SerializedName("Premium")
	private String premium;

	@SerializedName("InsuranceType")
	private String insuranceType;

	@SerializedName("InsuranceNo")
	private String insuranceNo;

	@SerializedName("PolicyHolderName")
	private String policyHolderName;

	@SerializedName("CompanyName")
	private String companyName;

	@SerializedName("PurchasedYear")
	private String purchasedYear;

	@SerializedName("GenCompanyName")
	private String genCompanyName;

	@SerializedName("GenPremium")
	private String genPremium;

	@SerializedName("NomineeName")
	private String nomineeName;

	public void setFkMemId(String fkMemId){
		this.fkMemId = fkMemId;
	}

	public String getFkMemId(){
		return fkMemId;
	}

	public void setPolicyNo(String policyNo){
		this.policyNo = policyNo;
	}

	public String getPolicyNo(){
		return policyNo;
	}

	public void setExpiryDate(String expiryDate){
		this.expiryDate = expiryDate;
	}

	public String getExpiryDate(){
		return expiryDate;
	}

	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleNo(){
		return vehicleNo;
	}

	public void setPremium(String premium){
		this.premium = premium;
	}

	public String getPremium(){
		return premium;
	}

	public void setInsuranceType(String insuranceType){
		this.insuranceType = insuranceType;
	}

	public String getInsuranceType(){
		return insuranceType;
	}

	public void setInsuranceNo(String insuranceNo){
		this.insuranceNo = insuranceNo;
	}

	public String getInsuranceNo(){
		return insuranceNo;
	}

	public void setPolicyHolderName(String policyHolderName){
		this.policyHolderName = policyHolderName;
	}

	public String getPolicyHolderName(){
		return policyHolderName;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setPurchasedYear(String purchasedYear){
		this.purchasedYear = purchasedYear;
	}

	public String getPurchasedYear(){
		return purchasedYear;
	}

	public void setGenCompanyName(String genCompanyName){
		this.genCompanyName = genCompanyName;
	}

	public String getGenCompanyName(){
		return genCompanyName;
	}

	public void setGenPremium(String genPremium){
		this.genPremium = genPremium;
	}

	public String getGenPremium(){
		return genPremium;
	}

	public void setNomineeName(String nomineeName){
		this.nomineeName = nomineeName;
	}

	public String getNomineeName(){
		return nomineeName;
	}
}