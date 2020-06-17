package kkm.com.core.model.response.tatasky.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseRegionList{

	@SerializedName("date")
	private String date;

	@SerializedName("result")
	private String result;

	@SerializedName("session")
	private String session;

	@SerializedName("error")
	private String error;

	@SerializedName("addinfo")
	private List<AddinfoItem> addinfo;

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

	public void setSession(String session){
		this.session = session;
	}

	public String getSession(){
		return session;
	}

	public void setError(String error){
		this.error = error;
	}

	public String getError(){
		return error;
	}

	public void setAddinfo(List<AddinfoItem> addinfo){
		this.addinfo = addinfo;
	}

	public List<AddinfoItem> getAddinfo(){
		return addinfo;
	}

	@Override
 	public String toString(){
		return 
			"ResponseRegionList{" + 
			"date = '" + date + '\'' + 
			",result = '" + result + '\'' + 
			",session = '" + session + '\'' + 
			",error = '" + error + '\'' + 
			",addinfo = '" + addinfo + '\'' + 
			"}";
		}
}