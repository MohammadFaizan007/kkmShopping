package kkm.com.core.model.response.transportMode;

import com.google.gson.annotations.SerializedName;

public class GetTransportDetailsItem{

	@SerializedName("pk_TransportId")
	private String pkTransportId;

	@SerializedName("transportMode")
	private String transportMode;

	public String getPkTransportId(){
		return pkTransportId;
	}

	public String getTransportMode(){
		return transportMode;
	}
}