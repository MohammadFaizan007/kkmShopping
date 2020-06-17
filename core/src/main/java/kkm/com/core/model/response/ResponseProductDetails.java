package kkm.com.core.model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseProductDetails{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private String success;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"ResponseProductDetails{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}