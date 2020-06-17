package kkm.com.core.model.response.memberNameFromMobile;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("loginId")
	private String loginId;

	@SerializedName("name")
	private String name;

	@SerializedName("fk_MemId")
	private String fkMemId;

	@SerializedName("deviceId")
	private String deviceId;

	public String getLoginId(){
		return loginId;
	}

	public String getName(){
		return name;
	}

	public String getFkMemId(){
		return fkMemId;
	}

	public String getDeviceId(){
		return deviceId;
	}
}