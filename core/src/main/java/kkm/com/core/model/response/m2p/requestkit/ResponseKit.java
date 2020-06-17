package kkm.com.core.model.response.m2p.requestkit;

import com.google.gson.annotations.SerializedName;

public class ResponseKit {

    @SerializedName("result")
    private Result result;

    @SerializedName("response")
    private String response;

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return
                "ResponseKit{" +
                        "result = '" + result + '\'' +
                        ",response = '" + response + '\'' +
                        "}";
    }
}