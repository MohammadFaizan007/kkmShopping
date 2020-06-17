package kkm.com.core.model.response.team;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTSLevelView {

	@SerializedName("teamStatusdetails")
	private List<TeamStatusdetailsItem> teamStatusdetails;

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	public void setTeamStatusdetails(List<TeamStatusdetailsItem> teamStatusdetails){
		this.teamStatusdetails = teamStatusdetails;
	}

	public List<TeamStatusdetailsItem> getTeamStatusdetails(){
		return teamStatusdetails;
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
			"RequestTSLevelView{" + 
			"teamStatusdetails = '" + teamStatusdetails + '\'' + 
			",response = '" + response + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}