package kkm.com.core.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseAmazonAdd{

//	{
//		"response": "Success",
//			"message": "Record Saved Successfully",
//			"amazonId": 2
//	}

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("amazonId")
	private String amazonId;

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public String getAmazonId(){
		return amazonId;
	}
}