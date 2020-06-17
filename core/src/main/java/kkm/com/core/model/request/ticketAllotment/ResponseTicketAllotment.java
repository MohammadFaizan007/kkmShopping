package kkm.com.core.model.request.ticketAllotment;

import com.google.gson.annotations.SerializedName;

public class ResponseTicketAllotment{

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}
}