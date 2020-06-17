package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponsePincodeDetail{

	@SerializedName("pincode")
	private String pincode;

	@SerializedName("cityName")
	private String cityName;

	@SerializedName("stateName")
	private String stateName;

	@SerializedName("taluk")
	private String taluk;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	public void setPincode(String pincode){
		this.pincode = pincode;
	}

	public String getPincode(){
		return pincode;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setTaluk(String taluk){
		this.taluk = taluk;
	}

	public String getTaluk(){
		return taluk;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePincodeDetail{" + 
			"pincode = '" + pincode + '\'' + 
			",cityName = '" + cityName + '\'' + 
			",stateName = '" + stateName + '\'' + 
			",taluk = '" + taluk + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}