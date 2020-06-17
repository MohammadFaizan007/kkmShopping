package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseUnclearedBalance{

	@SerializedName("totalTransactionsAMT")
	private String totalTransactionsAMT;

	@SerializedName("kitNo")
	private String kitNo;

	@SerializedName("response")
	private String response;

	@SerializedName("m2pStatus")
	private String m2pStatus;

	@SerializedName("fK_ToId")
	private String fKToId;

	public void setTotalTransactionsAMT(String totalTransactionsAMT){
		this.totalTransactionsAMT = totalTransactionsAMT;
	}

	public String getTotalTransactionsAMT(){
		return totalTransactionsAMT;
	}

	public void setKitNo(String kitNo){
		this.kitNo = kitNo;
	}

	public String getKitNo(){
		return kitNo;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setM2pStatus(String m2pStatus){
		this.m2pStatus = m2pStatus;
	}

	public String getM2pStatus(){
		return m2pStatus;
	}

	public void setFKToId(String fKToId){
		this.fKToId = fKToId;
	}

	public String getFKToId(){
		return fKToId;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUnclearedBalance{" + 
			"totalTransactionsAMT = '" + totalTransactionsAMT + '\'' + 
			",kitNo = '" + kitNo + '\'' + 
			",response = '" + response + '\'' + 
			",m2pStatus = '" + m2pStatus + '\'' + 
			",fK_ToId = '" + fKToId + '\'' + 
			"}";
		}
}