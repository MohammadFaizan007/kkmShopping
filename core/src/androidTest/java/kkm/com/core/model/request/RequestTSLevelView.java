package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestTSLevelView{
//	PageNumber PageSize

	@SerializedName("PageNumber")
	private String PageNumber;

	public void setPageNumber(String pageNumber) {
		PageNumber = pageNumber;
	}

	public void setPageSize(String pageSize) {
		PageSize = pageSize;
	}

	@SerializedName("PageSize")
	private String PageSize;

	@SerializedName("LoginId")
	private String fkMemID;

	@SerializedName("LevelName")
	private String levelName;

	public void setFkMemID(String fkMemID){
		this.fkMemID = fkMemID;
	}

	public String getFkMemID(){
		return fkMemID;
	}

	public void setLevelName(String levelName){
		this.levelName = levelName;
	}

	public String getLevelName(){
		return levelName;
	}

	@Override
 	public String toString(){
		return 
			"RequestTSLevelView{" + 
			"fk_MemID = '" + fkMemID + '\'' + 
			",levelName = '" + levelName + '\'' + 
			"}";
		}
}