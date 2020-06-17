package kkm.com.core.model.request.billpayment;

import com.google.gson.annotations.SerializedName;

public class RequestCollectPay{

	@SerializedName("NUMBER")
	private String nUMBER;

	@SerializedName("Type")
	private String type;

	@SerializedName("AMOUNT")
	private String aMOUNT;

	@SerializedName("AMOUNT_ALL")
	private String aMOUNTALL;

	@SerializedName("BillNumber")
	private String billNumber;

	@SerializedName("COMMENT")
	private String cOMMENT;

	@SerializedName("Fk_MemId")
	private String Fk_MemId;

	public String getFk_MemId() {
		return Fk_MemId;
	}

	public void setFk_MemId(String fk_MemId) {
		Fk_MemId = fk_MemId;
	}

	public void setNUMBER(String nUMBER){
		this.nUMBER = nUMBER;
	}

	public String getNUMBER(){
		return nUMBER;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setAMOUNT(String aMOUNT){
		this.aMOUNT = aMOUNT;
	}

	public String getAMOUNT(){
		return aMOUNT;
	}

	public void setAMOUNTALL(String aMOUNTALL){
		this.aMOUNTALL = aMOUNTALL;
	}

	public String getAMOUNTALL(){
		return aMOUNTALL;
	}

	public void setBillNumber(String billNumber){
		this.billNumber = billNumber;
	}

	public String getBillNumber(){
		return billNumber;
	}

	public void setCOMMENT(String cOMMENT){
		this.cOMMENT = cOMMENT;
	}

	public String getCOMMENT(){
		return cOMMENT;
	}
}