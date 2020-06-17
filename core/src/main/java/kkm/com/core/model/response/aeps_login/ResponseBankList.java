package kkm.com.core.model.response.aeps_login;

import com.google.gson.annotations.SerializedName;

public class ResponseBankList{

	@SerializedName("bank_code")
	private String bankCode;

	@SerializedName("iin_code")
	private int iinCode;

	@SerializedName("bank_name")
	private String bankName;

	@SerializedName("id")
	private int id;

	@SerializedName("acquired_id")
	private int acquiredId;

	@SerializedName("status")
	private String status;

	public void setBankCode(String bankCode){
		this.bankCode = bankCode;
	}

	public String getBankCode(){
		return bankCode;
	}

	public void setIinCode(int iinCode){
		this.iinCode = iinCode;
	}

	public int getIinCode(){
		return iinCode;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}

	public String getBankName(){
		return bankName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAcquiredId(int acquiredId){
		this.acquiredId = acquiredId;
	}

	public int getAcquiredId(){
		return acquiredId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}