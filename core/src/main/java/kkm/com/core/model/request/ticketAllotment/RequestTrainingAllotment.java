package kkm.com.core.model.request.ticketAllotment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestTrainingAllotment{

	@SerializedName("TrainingList")
	private List<TrainingItems> trainingList;

	@SerializedName("NotificationMessage")
	private String notificationMessage;

	@SerializedName("TrainingId")
	private String trainingId;

	@SerializedName("AssignedBy")
	private String assignedBy;

	@SerializedName("NotificationTitle")
	private String notificationTitle;

	@SerializedName("TrainingDate")
	private String TrainingDate;

	public void setTrainingDate(String trainingDate) {
		TrainingDate = trainingDate;
	}

	public void setTrainingList(List<TrainingItems> trainingList){
		this.trainingList = trainingList;
	}

	public List<TrainingItems> getTrainingList(){
		return trainingList;
	}

	public void setNotificationMessage(String notificationMessage){
		this.notificationMessage = notificationMessage;
	}

	public String getNotificationMessage(){
		return notificationMessage;
	}

	public void setTrainingId(String trainingId){
		this.trainingId = trainingId;
	}

	public String getTrainingId(){
		return trainingId;
	}

	public void setAssignedBy(String assignedBy){
		this.assignedBy = assignedBy;
	}

	public String getAssignedBy(){
		return assignedBy;
	}

	public void setNotificationTitle(String notificationTitle){
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationTitle(){
		return notificationTitle;
	}

	@Override
 	public String toString(){
		return 
			"RequestTrainingAllotment{" + 
			"trainingList = '" + trainingList + '\'' + 
			",notificationMessage = '" + notificationMessage + '\'' + 
			",trainingId = '" + trainingId + '\'' + 
			",assignedBy = '" + assignedBy + '\'' + 
			",notificationTitle = '" + notificationTitle + '\'' + 
			"}";
		}
}