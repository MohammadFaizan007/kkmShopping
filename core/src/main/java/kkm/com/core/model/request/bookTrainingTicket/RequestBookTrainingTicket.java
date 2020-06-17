package kkm.com.core.model.request.bookTrainingTicket;

import com.google.gson.annotations.SerializedName;

public class RequestBookTrainingTicket{

	@SerializedName("Fk_MemId")
	private String fkMemId;

	@SerializedName("EventId")
	private String eventId;

	@SerializedName("Mobile")
	private String mobile;

	@SerializedName("Name")
	private String name;

	@SerializedName("PinCode")
	private String pinCode;

	public void setFkMemId(String fkMemId){
		this.fkMemId = fkMemId;
	}

	public String getFkMemId(){
		return fkMemId;
	}

	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public String getEventId(){
		return eventId;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPinCode(String pinCode){
		this.pinCode = pinCode;
	}

	public String getPinCode(){
		return pinCode;
	}

	@Override
 	public String toString(){
		return 
			"RequestBookTrainingTicket{" + 
			"fk_MemId = '" + fkMemId + '\'' + 
			",eventId = '" + eventId + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",name = '" + name + '\'' + 
			",pinCode = '" + pinCode + '\'' + 
			"}";
		}
}