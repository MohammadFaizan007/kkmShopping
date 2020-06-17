package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseElectricityState {

	@SerializedName("Response")
	private String response;

	@SerializedName("GetAllProviderStateWiseList")
	private List<GetAllProviderStateWiseListItem> getAllProviderStateWiseList;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setGetAllProviderStateWiseList(List<GetAllProviderStateWiseListItem> getAllProviderStateWiseList){
		this.getAllProviderStateWiseList = getAllProviderStateWiseList;
	}

	public List<GetAllProviderStateWiseListItem> getGetAllProviderStateWiseList(){
		return getAllProviderStateWiseList;
	}
}