package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCompanyName{

	@SerializedName("response")
	private String response;

	@SerializedName("companyBankMasterSelectlist")
	private List<CompanyBankMasterSelectlistItem> companyBankMasterSelectlist;

	public String getResponse(){
		return response;
	}

	public List<CompanyBankMasterSelectlistItem> getCompanyBankMasterSelectlist(){
		return companyBankMasterSelectlist;
	}
}