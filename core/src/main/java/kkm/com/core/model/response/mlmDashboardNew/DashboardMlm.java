package kkm.com.core.model.response.mlmDashboardNew;

import com.google.gson.annotations.SerializedName;

public class DashboardMlm{

	@SerializedName("bankStatus")
	private String bankStatus;

	@SerializedName("selfincome")
	private String selfincome;

	@SerializedName("teamsize")
	private String teamsize;

	@SerializedName("adharStatus")
	private String adharStatus;

	@SerializedName("panCardStatus")
	private String panCardStatus;

	@SerializedName("uncleared")
	private String uncleared;

	@SerializedName("firstLoginDate")
	private String firstLoginDate;

	@SerializedName("doj")
	private String doj;

	public String getBankStatus(){
		return bankStatus;
	}

	public String getSelfincome(){
		return selfincome;
	}

	public String getTeamsize(){
		return teamsize;
	}

	public String getAdharStatus(){
		return adharStatus;
	}

	public String getPanCardStatus(){
		return panCardStatus;
	}

	public String getUncleared(){
		return uncleared;
	}

	public String getFirstLoginDate(){
		return firstLoginDate;
	}

	public String getDoj(){
		return doj;
	}
}