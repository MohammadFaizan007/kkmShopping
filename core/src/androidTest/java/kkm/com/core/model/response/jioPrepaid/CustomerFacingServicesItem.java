package kkm.com.core.model.response.jioPrepaid;

import com.google.gson.annotations.SerializedName;

public class CustomerFacingServicesItem{

	@SerializedName("serviceType")
	private String serviceType;

	public void setServiceType(String serviceType){
		this.serviceType = serviceType;
	}

	public String getServiceType(){
		return serviceType;
	}

	@Override
 	public String toString(){
		return 
			"CustomerFacingServicesItem{" + 
			"serviceType = '" + serviceType + '\'' + 
			"}";
		}
}