package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBankDetails{

	@SerializedName("lstOutputGetBankDetailsByLoginId")
	private List<LstOutputGetBankDetailsByLoginIdItem> lstOutputGetBankDetailsByLoginId;

	@SerializedName("response")
	private String response;

	public List<LstOutputGetBankDetailsByLoginIdItem> getLstOutputGetBankDetailsByLoginId(){
		return lstOutputGetBankDetailsByLoginId;
	}

	public String getResponse(){
		return response;
	}
}