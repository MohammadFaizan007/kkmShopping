package kkm.com.core.model.response.trainingBookings;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTrainingBookings{

	@SerializedName("eventDetailList")
	private List<EventDetailListItem> eventDetailList;

	public List<EventDetailListItem> getEventDetailList() {
		return eventDetailList;
	}

	public String getResponse() {
		return response;
	}

	@SerializedName("response")
	private String response;

	public void setEventDetailList(List<EventDetailListItem> eventDetailList){
		this.eventDetailList = eventDetailList;
	}

	public void setResponse(String response){
		this.response = response;
	}
}