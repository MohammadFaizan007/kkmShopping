package kkm.com.core.model.response.tatasky.response;

import com.google.gson.annotations.SerializedName;

public class ResponseTataskyBooking{

	@SerializedName("date")
	private String date;

	@SerializedName("result")
	private String result;

	@SerializedName("session")
	private String session;

	@SerializedName("errmsg")
	private String errmsg;

	@SerializedName("error")
	private String error;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setSession(String session){
		this.session = session;
	}

	public String getSession(){
		return session;
	}

	public void setErrmsg(String errmsg){
		this.errmsg = errmsg;
	}

	public String getErrmsg(){
		return errmsg;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ResponseTataskyBooking{" + 
			"date = '" + date + '\'' + 
			",result = '" + result + '\'' + 
			",session = '" + session + '\'' + 
			",errmsg = '" + errmsg + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}