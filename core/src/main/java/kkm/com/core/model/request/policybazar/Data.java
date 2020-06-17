package kkm.com.core.model.request.policybazar;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("user")
	private int user;

	@SerializedName("errorDetails")
	private List<String> errorDetails;

	@SerializedName("username")
	private String username;

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUser(int user){
		this.user = user;
	}

	public int getUser(){
		return user;
	}

	public void setErrorDetails(List<String> errorDetails){
		this.errorDetails = errorDetails;
	}

	public List<String> getErrorDetails(){
		return errorDetails;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			",user = '" + user + '\'' + 
			",errorDetails = '" + errorDetails + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}