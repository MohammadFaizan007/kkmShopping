package kkm.com.core.model.response.unclearLedger;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUnclearBalance{

	@SerializedName("response")
	private String response;

	@SerializedName("payoutLedgerSelectDetailsList")
	private List<PayoutLedgerSelectDetailsListItem> payoutLedgerSelectDetailsList;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setPayoutLedgerSelectDetailsList(List<PayoutLedgerSelectDetailsListItem> payoutLedgerSelectDetailsList){
		this.payoutLedgerSelectDetailsList = payoutLedgerSelectDetailsList;
	}

	public List<PayoutLedgerSelectDetailsListItem> getPayoutLedgerSelectDetailsList(){
		return payoutLedgerSelectDetailsList;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUnclearBalance{" + 
			"response = '" + response + '\'' + 
			",payoutLedgerSelectDetailsList = '" + payoutLedgerSelectDetailsList + '\'' + 
			"}";
		}
}