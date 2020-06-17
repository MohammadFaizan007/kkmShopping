package kkm.com.core.model.response.transportMode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTransportModes{

	@SerializedName("response")
	private String response;

	@SerializedName("getTransportDetails")
	private List<GetTransportDetailsItem> getTransportDetails;

	public String getResponse(){
		return response;
	}

	public List<GetTransportDetailsItem> getGetTransportDetails(){
		return getTransportDetails;
	}
}