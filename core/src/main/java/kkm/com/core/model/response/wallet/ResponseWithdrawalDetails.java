package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWithdrawalDetails{

	@SerializedName("response")
	private String response;

	@SerializedName("getPayoutRequestDetails")
	private List<GetPayoutRequestDetailsItem> getPayoutRequestDetails;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setGetPayoutRequestDetails(List<GetPayoutRequestDetailsItem> getPayoutRequestDetails){
		this.getPayoutRequestDetails = getPayoutRequestDetails;
	}

	public List<GetPayoutRequestDetailsItem> getGetPayoutRequestDetails(){
		return getPayoutRequestDetails;
	}

	@Override
 	public String toString(){
		return 
			"ResponseWithdrawalDetails{" + 
			"response = '" + response + '\'' + 
			",getPayoutRequestDetails = '" + getPayoutRequestDetails + '\'' + 
			"}";
		}
}