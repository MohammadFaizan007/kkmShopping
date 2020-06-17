package kkm.com.core.model.request.billpayment;

import com.google.gson.annotations.SerializedName;

public class RequestGasVerification{

	@SerializedName("NUMBER")
	private String nUMBER;

	@SerializedName("Type")
	private String type;

	@SerializedName("AMOUNT_ALL")
	private String aMOUNTALL;

	@SerializedName("AMOUNT")
	private String aMOUNT;

	@SerializedName("FK_MemId")
	private String fKMemId;

	@SerializedName("Provider")
	private String provider;

	public void setNUMBER(String nUMBER){
		this.nUMBER = nUMBER;
	}

	public void setType(String type){
		this.type = type;
	}

	public void setAMOUNTALL(String aMOUNTALL){
		this.aMOUNTALL = aMOUNTALL;
	}

	public void setAMOUNT(String aMOUNT){
		this.aMOUNT = aMOUNT;
	}

	public void setFKMemId(String fKMemId){
		this.fKMemId = fKMemId;
	}

	public void setProvider(String provider){
		this.provider = provider;
	}
}