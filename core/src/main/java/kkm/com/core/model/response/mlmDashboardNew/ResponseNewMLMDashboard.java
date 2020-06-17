package kkm.com.core.model.response.mlmDashboardNew;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNewMLMDashboard{

	@SerializedName("newsAndInfo")
	private List<NewsAndInfoItem> newsAndInfo;

	@SerializedName("dashboardMlm")
	private DashboardMlm dashboardMlm;

	@SerializedName("response")
	private String response;

	@SerializedName("banners")
	private List<BannersItem> banners;

	public List<NewsAndInfoItem> getNewsAndInfo(){
		return newsAndInfo;
	}

	public DashboardMlm getDashboardMlm(){
		return dashboardMlm;
	}

	public String getResponse(){
		return response;
	}

	public List<BannersItem> getBanners(){
		return banners;
	}
}