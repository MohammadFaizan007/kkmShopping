package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseSendQuery{

	@SerializedName("response")
	private String response;

	@SerializedName("messageId")
	private String messageId;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setMessageId(String messageId){
		this.messageId = messageId;
	}

	public String getMessageId(){
		return messageId;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSendQuery{" + 
			"response = '" + response + '\'' + 
			",messageId = '" + messageId + '\'' + 
			"}";
		}
}