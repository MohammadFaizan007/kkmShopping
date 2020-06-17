package kkm.com.core.model.response.bookTrainingTicket;

import com.google.gson.annotations.SerializedName;

public class ResponseBookTrainingTicket{

	@SerializedName("bookingNo")
	private String bookingNo;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	public void setBookingNo(String bookingNo){
		this.bookingNo = bookingNo;
	}

	public String getBookingNo(){
		return bookingNo;
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
			"ResponseBookTrainingTicket{" + 
			"bookingNo = '" + bookingNo + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}