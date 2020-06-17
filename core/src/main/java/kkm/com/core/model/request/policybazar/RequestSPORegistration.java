package kkm.com.core.model.request.policybazar;

import com.google.gson.annotations.SerializedName;

public class RequestSPORegistration{

	@SerializedName("DateOfBirth")
	private String dateOfBirth;

	@SerializedName("Email")
	private String email;

	@SerializedName("referralCode")
	private String referralCode;

	@SerializedName("userType")
	private String userType;

	@SerializedName("Mobile")
	private String mobile;

	@SerializedName("Source")
	private String source;

	@SerializedName("Password")
	private String password;

	@SerializedName("Name")
	private String name;

	@SerializedName("PinCode")
	private String pinCode;

	public void setDateOfBirth(String dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfBirth(){
		return dateOfBirth;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setReferralCode(String referralCode){
		this.referralCode = referralCode;
	}

	public String getReferralCode(){
		return referralCode;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

	public String getUserType(){
		return userType;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPinCode(String pinCode){
		this.pinCode = pinCode;
	}

	public String getPinCode(){
		return pinCode;
	}

	@Override
 	public String toString(){
		return 
			"RequestSPORegistration{" + 
			"dateOfBirth = '" + dateOfBirth + '\'' + 
			",email = '" + email + '\'' + 
			",referralCode = '" + referralCode + '\'' + 
			",userType = '" + userType + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",source = '" + source + '\'' + 
			",password = '" + password + '\'' + 
			",name = '" + name + '\'' + 
			",pinCode = '" + pinCode + '\'' + 
			"}";
		}
}