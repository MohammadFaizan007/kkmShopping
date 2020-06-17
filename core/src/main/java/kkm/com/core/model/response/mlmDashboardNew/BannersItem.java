package kkm.com.core.model.response.mlmDashboardNew;

import com.google.gson.annotations.SerializedName;

public class BannersItem{

	@SerializedName("bannerImgUrl")
	private String bannerImgUrl;

	@SerializedName("bannerLink")
	private String bannerLink;

	public String getBannerImgUrl(){
		return bannerImgUrl;
	}

	public String getBannerLink(){
		return bannerLink;
	}
}