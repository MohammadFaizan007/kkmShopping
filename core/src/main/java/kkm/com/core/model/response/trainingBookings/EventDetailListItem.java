package kkm.com.core.model.response.trainingBookings;

import com.google.gson.annotations.SerializedName;

public class EventDetailListItem{

	@SerializedName("bookingNo")
	private String bookingNo;

	@SerializedName("eventId")
	private String eventId;

	@SerializedName("venue")
	private String venue;

	@SerializedName("pinCode")
	private String pinCode;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("bookingStatus")
	private String bookingStatus;

	@SerializedName("eventName")
	private String eventName;

	@SerializedName("location")
	private String location;

	@SerializedName("fk_MemId")
	private String fkMemId;

	@SerializedName("bookinDate")
	private String bookinDate;

	@SerializedName("eventDate")
	private String eventDate;

	public void setBookingNo(String bookingNo){
		this.bookingNo = bookingNo;
	}

	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public void setVenue(String venue){
		this.venue = venue;
	}

	public void setPinCode(String pinCode){
		this.pinCode = pinCode;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public void setBookingStatus(String bookingStatus){
		this.bookingStatus = bookingStatus;
	}

	public void setEventName(String eventName){
		this.eventName = eventName;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public void setFkMemId(String fkMemId){
		this.fkMemId = fkMemId;
	}

	public void setBookinDate(String bookinDate){
		this.bookinDate = bookinDate;
	}

	public void setEventDate(String eventDate){
		this.eventDate = eventDate;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public String getEventId() {
		return eventId;
	}

	public String getVenue() {
		return venue;
	}

	public String getPinCode() {
		return pinCode;
	}

	public String getName() {
		return name;
	}

	public String getMobile() {
		return mobile;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public String getEventName() {
		return eventName;
	}

	public String getLocation() {
		return location;
	}

	public String getFkMemId() {
		return fkMemId;
	}

	public String getBookinDate() {
		return bookinDate;
	}

	public String getEventDate() {
		return eventDate;
	}
}