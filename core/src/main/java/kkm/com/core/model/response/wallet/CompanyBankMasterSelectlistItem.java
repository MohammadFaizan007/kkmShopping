package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class CompanyBankMasterSelectlistItem{

	@SerializedName("bankLogoURL")
	private String bankLogoURL;

	@SerializedName("companyName")
	private String companyName;

	@SerializedName("accountNo")
	private String accountNo;

	@SerializedName("pK_BankId")
	private String pKBankId;

	@SerializedName("branchName")
	private String branchName;

	@SerializedName("bankName")
	private String bankName;

	@SerializedName("ifscCode")
	private String ifscCode;

	public String getBankLogoURL(){
		return bankLogoURL;
	}

	public String getCompanyName(){
		return companyName;
	}

	public String getAccountNo(){
		return accountNo;
	}

	public String getPKBankId(){
		return pKBankId;
	}

	public String getBranchName(){
		return branchName;
	}

	public String getBankName(){
		return bankName;
	}

	public String getIfscCode(){
		return ifscCode;
	}
}