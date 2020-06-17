package kkm.com.core.model.response.getMyTraining;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMyTrainings{

	@SerializedName("eventDetails")
	private List<EventDetailsItem> eventDetails;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	public void setEventDetails(List<EventDetailsItem> eventDetails){
		this.eventDetails = eventDetails;
	}

	public List<EventDetailsItem> getEventDetails(){
		return eventDetails;
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
			"ResponseMyTrainings{" + 
			"eventDetails = '" + eventDetails + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}