package kkm.com.core.model.response.getMyTraining;

import com.google.gson.annotations.SerializedName;

public class EventDetailsItem{

	@SerializedName("venue")
	private String venue;

	@SerializedName("pk_EventNameId")
	private String pkEventNameId;

	@SerializedName("ticketAmount")
	private String ticketAmount;

	@SerializedName("availableTickets")
	private String availableTickets;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("eventDescription")
	private String eventDescription;

	@SerializedName("eventName")
	private String eventName;

	@SerializedName("fromtime")
	private String fromtime;

	@SerializedName("location")
	private String location;

	@SerializedName("numberOfAllowedtickets")
	private String numberOfAllowedtickets;

	@SerializedName("toTime")
	private String toTime;

	@SerializedName("eventDate")
	private String eventDate;

	public void setVenue(String venue){
		this.venue = venue;
	}

	public String getVenue(){
		return venue;
	}

	public void setPkEventNameId(String pkEventNameId){
		this.pkEventNameId = pkEventNameId;
	}

	public String getPkEventNameId(){
		return pkEventNameId;
	}

	public void setTicketAmount(String ticketAmount){
		this.ticketAmount = ticketAmount;
	}

	public String getTicketAmount(){
		return ticketAmount;
	}

	public void setAvailableTickets(String availableTickets){
		this.availableTickets = availableTickets;
	}

	public String getAvailableTickets(){
		return availableTickets;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setEventDescription(String eventDescription){
		this.eventDescription = eventDescription;
	}

	public String getEventDescription(){
		return eventDescription;
	}

	public void setEventName(String eventName){
		this.eventName = eventName;
	}

	public String getEventName(){
		return eventName;
	}

	public void setFromtime(String fromtime){
		this.fromtime = fromtime;
	}

	public String getFromtime(){
		return fromtime;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setNumberOfAllowedtickets(String numberOfAllowedtickets){
		this.numberOfAllowedtickets = numberOfAllowedtickets;
	}

	public String getNumberOfAllowedtickets(){
		return numberOfAllowedtickets;
	}

	public void setToTime(String toTime){
		this.toTime = toTime;
	}

	public String getToTime(){
		return toTime;
	}

	public void setEventDate(String eventDate){
		this.eventDate = eventDate;
	}

	public String getEventDate(){
		return eventDate;
	}

	@Override
 	public String toString(){
		return 
			"EventDetailsItem{" + 
			"venue = '" + venue + '\'' + 
			",pk_EventNameId = '" + pkEventNameId + '\'' + 
			",ticketAmount = '" + ticketAmount + '\'' + 
			",availableTickets = '" + availableTickets + '\'' + 
			",imageUrl = '" + imageUrl + '\'' + 
			",eventDescription = '" + eventDescription + '\'' + 
			",eventName = '" + eventName + '\'' + 
			",fromtime = '" + fromtime + '\'' + 
			",location = '" + location + '\'' + 
			",numberOfAllowedtickets = '" + numberOfAllowedtickets + '\'' + 
			",toTime = '" + toTime + '\'' + 
			",eventDate = '" + eventDate + '\'' + 
			"}";
		}
}