package kkm.com.core.model.response.bill;

import com.google.gson.annotations.SerializedName;

public class ResponseCollectUpi{

	@SerializedName("date")
	private String date;

	@SerializedName("result")
	private String result;

	@SerializedName("authcode")
	private String authcode;

	@SerializedName("trnxstatus")
	private String trnxstatus;

	@SerializedName("session")
	private String session;

	@SerializedName("transid")
	private String transid;

	@SerializedName("errmsg")
	private String errmsg;

	@SerializedName("error")
	private String error;

	@SerializedName("addinfo")
	private String addinfo;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setAuthcode(String authcode){
		this.authcode = authcode;
	}

	public String getAuthcode(){
		return authcode;
	}

	public void setTrnxstatus(String trnxstatus){
		this.trnxstatus = trnxstatus;
	}

	public String getTrnxstatus(){
		return trnxstatus;
	}

	public void setSession(String session){
		this.session = session;
	}

	public String getSession(){
		return session;
	}

	public void setTransid(String transid){
		this.transid = transid;
	}

	public String getTransid(){
		return transid;
	}

	public void setErrmsg(String errmsg){
		this.errmsg = errmsg;
	}

	public String getErrmsg(){
		return errmsg;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	public void setAddinfo(String addinfo){
		this.addinfo = addinfo;
	}

	public String getAddinfo(){
		return addinfo;
	}
}