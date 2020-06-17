package kkm.com.core.model.response.tatasky.response;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem{

	@SerializedName("region_id")
	private int regionId;

	@SerializedName("region_name")
	private String regionName;

	public void setRegionId(int regionId){
		this.regionId = regionId;
	}

	public int getRegionId(){
		return regionId;
	}

	public void setRegionName(String regionName){
		this.regionName = regionName;
	}

	public String getRegionName(){
		return regionName;
	}

	@Override
 	public String toString(){
		return 
			"AddinfoItem{" + 
			"region_id = '" + regionId + '\'' + 
			",region_name = '" + regionName + '\'' + 
			"}";
		}
}