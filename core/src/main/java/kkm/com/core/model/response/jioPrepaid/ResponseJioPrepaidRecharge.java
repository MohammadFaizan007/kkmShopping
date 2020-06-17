package kkm.com.core.model.response.jioPrepaid;

import com.google.gson.annotations.SerializedName;

public class ResponseJioPrepaidRecharge{

	@SerializedName("DATE")
	private String date;

	@SerializedName("RESULT")
	private String result;

	@SerializedName("AUTHCODE")
	private String authcode;

	@SerializedName("TRNXSTATUS")
	private String trnxstatus;

	@SerializedName("SESSION")
	private String session;

	@SerializedName("TRANSID")
	private String transid;


	@SerializedName("ERRMSG")
	private String errmsg;

	@SerializedName("ERROR")
	private String error;

	@SerializedName("ADDINFO")
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

	@Override
 	public String toString(){
		return 
			"ResponseJioPrepaidRecharge{" + 
			"date = '" + date + '\'' + 
			",result = '" + result + '\'' + 
			",authcode = '" + authcode + '\'' + 
			",trnxstatus = '" + trnxstatus + '\'' + 
			",session = '" + session + '\'' + 
			",transid = '" + transid + '\'' + 
			",errmsg = '" + errmsg + '\'' + 
			",error = '" + error + '\'' + 
			",addinfo = '" + addinfo + '\'' + 
			"}";
		}
}