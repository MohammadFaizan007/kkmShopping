package kkm.com.core.model.request.m2p;

import com.google.gson.annotations.SerializedName;

public class KYCDocument{

	@SerializedName(" docType ")
	private String docType;

	@SerializedName(" docNo ")
	private String docNo;

	@SerializedName("docExpDate")
	private String docExpDate;

	public void setDocType(String docType){
		this.docType = docType;
	}

	public String getDocType(){
		return docType;
	}

	public void setDocNo(String docNo){
		this.docNo = docNo;
	}

	public String getDocNo(){
		return docNo;
	}

	public void setDocExpDate(String docExpDate){
		this.docExpDate = docExpDate;
	}

	public String getDocExpDate(){
		return docExpDate;
	}

	@Override
 	public String toString(){
		return 
			"KYCDocument{" + 
			" docType  = '" + docType + '\'' + 
			", docNo  = '" + docNo + '\'' + 
			",docExpDate = '" + docExpDate + '\'' + 
			"}";
		}
}