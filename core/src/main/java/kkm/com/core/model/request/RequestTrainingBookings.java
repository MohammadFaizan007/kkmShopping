package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestTrainingBookings{

	@SerializedName("Fk_MemId")
	private String fkMemId;

	@SerializedName("EventId")
	private String eventId;

	public void setFkMemId(String fkMemId){
		this.fkMemId = fkMemId;
	}

	public void setEventId(String eventId){
		this.eventId = eventId;
	}
}