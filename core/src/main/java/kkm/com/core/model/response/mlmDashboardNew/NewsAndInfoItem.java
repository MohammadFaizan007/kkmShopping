package kkm.com.core.model.response.mlmDashboardNew;

import com.google.gson.annotations.SerializedName;

public class NewsAndInfoItem{

	@SerializedName("infoImgUrl")
	private String infoImgUrl;

	@SerializedName("infoLink")
	private String infoLink;

	public String getInfoImgUrl(){
		return infoImgUrl;
	}

	public String getInfoLink(){
		return infoLink;
	}
}