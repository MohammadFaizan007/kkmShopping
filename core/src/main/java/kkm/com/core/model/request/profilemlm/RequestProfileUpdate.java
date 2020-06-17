package kkm.com.core.model.request.profilemlm;

import com.google.gson.annotations.SerializedName;

public class RequestProfileUpdate{

	@SerializedName("Email")
	private String email;

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("Address")
	private String address;

	@SerializedName("FirstName")
	private String firstName;

	@SerializedName("State")
	private String state;

	@SerializedName("LastName")
	private String lastName;

	@SerializedName("City")
	private String city;

	@SerializedName("Mobile")
	private String mobile;

	@SerializedName("Pincode")
	private String pincode;

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setPincode(String pincode){
		this.pincode = pincode;
	}

	public String getPincode(){
		return pincode;
	}

	@Override
 	public String toString(){
		return 
			"RequestProfileUpdate{" + 
			"email = '" + email + '\'' + 
			",loginId = '" + loginId + '\'' + 
			",address = '" + address + '\'' + 
			",firstName = '" + firstName + '\'' + 
			",state = '" + state + '\'' + 
			",lastName = '" + lastName + '\'' + 
			",city = '" + city + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",pincode = '" + pincode + '\'' + 
			"}";
		}
}