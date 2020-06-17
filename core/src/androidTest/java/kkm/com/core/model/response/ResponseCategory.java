package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCategory{

	@SerializedName("response")
	private String response;

	@SerializedName("homePage")
	private List<HomePageItem> homePage;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setHomePage(List<HomePageItem> homePage){
		this.homePage = homePage;
	}

	public List<HomePageItem> getHomePage(){
		return homePage;
	}
}