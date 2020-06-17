package kkm.com.core.model.response.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNotification{

	@SerializedName("notificationList")
	private List<NotificationListItem> notificationList;

	@SerializedName("response")
	private String response;

	public void setNotificationList(List<NotificationListItem> notificationList){
		this.notificationList = notificationList;
	}

	public List<NotificationListItem> getNotificationList(){
		return notificationList;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}
}