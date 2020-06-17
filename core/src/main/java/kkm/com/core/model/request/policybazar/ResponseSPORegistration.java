package kkm.com.core.model.request.policybazar;

import com.google.gson.annotations.SerializedName;

public class ResponseSPORegistration{

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSPORegistration{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}