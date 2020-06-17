package kkm.com.core.model.response.memberNameFromMobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseName{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("response")
	private String response;

    public String getMessage() {
        return message;
    }

    @SerializedName("message")
	private String message;

	public List<ResultItem> getResult(){
		return result;
	}

	public String getResponse(){
		return response;
	}
}