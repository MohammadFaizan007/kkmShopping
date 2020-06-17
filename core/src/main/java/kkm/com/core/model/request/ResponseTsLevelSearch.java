package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class ResponseTsLevelSearch{

	@SerializedName("LoginId")
	private String loginId;

	@SerializedName("LevelName")
	private String levelName;

	@SerializedName("SearchKey")
	private String searchKey;

	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setLevelName(String levelName){
		this.levelName = levelName;
	}

	public String getLevelName(){
		return levelName;
	}

	public void setSearchKey(String searchKey){
		this.searchKey = searchKey;
	}

	public String getSearchKey(){
		return searchKey;
	}
}