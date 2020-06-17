package kkm.com.core.model.response.jioPrepaid;

import com.google.gson.annotations.SerializedName;

public class CustomerfacingservicesItem{

	@SerializedName("servicetype")
	private String servicetype;

	public String getServicetype(){
		return servicetype;
	}
}