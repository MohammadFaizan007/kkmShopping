package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestUnclearedBalance{

	@SerializedName("FK_ToId")
	private String fKToId;

	public void setFKToId(String fKToId){
		this.fKToId = fKToId;
	}

	public String getFKToId(){
		return fKToId;
	}

	@Override
 	public String toString(){
		return 
			"RequestUnclearedBalance{" + 
			"fK_ToId = '" + fKToId + '\'' + 
			"}";
		}
}