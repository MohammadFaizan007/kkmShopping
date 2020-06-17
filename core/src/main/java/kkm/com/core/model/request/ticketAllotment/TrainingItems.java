package kkm.com.core.model.request.ticketAllotment;

import com.google.gson.annotations.SerializedName;

public class TrainingItems{

	@SerializedName("AssignedTo")
	private String assignedTo;

	@SerializedName("Fk_MemId")
	private String fkMemId;

	@SerializedName("AssignTicket")
	private String assignTicket;

	@SerializedName("DeviceId")
	private String deviceId;

	@SerializedName("Name")
	private String Name;

	@SerializedName("Mobile")
	private String Mobile;

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setAssignedTo(String assignedTo){
		this.assignedTo = assignedTo;
	}

	public String getAssignedTo(){
		return assignedTo;
	}

	public void setFkMemId(String fkMemId){
		this.fkMemId = fkMemId;
	}

	public String getFkMemId(){
		return fkMemId;
	}

	public void setAssignTicket(String assignTicket){
		this.assignTicket = assignTicket;
	}

	public String getAssignTicket(){
		return assignTicket;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	@Override
 	public String toString(){
		return 
			"TrainingItems{" + 
			"assignedTo = '" + assignedTo + '\'' + 
			",fk_MemId = '" + fkMemId + '\'' + 
			",assignTicket = '" + assignTicket + '\'' + 
			",deviceId = '" + deviceId + '\'' + 
			"}";
		}
}