package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProviderList{

	@SerializedName("Response")
	private String response;

	@SerializedName("AllProviderlist")
	private List<AllProviderlistItem> allProviderlist;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setAllProviderlist(List<AllProviderlistItem> allProviderlist){
		this.allProviderlist = allProviderlist;
	}

	public List<AllProviderlistItem> getAllProviderlist(){
		return allProviderlist;
	}
}