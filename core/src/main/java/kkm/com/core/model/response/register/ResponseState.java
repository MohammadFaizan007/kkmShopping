package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseState {

    @SerializedName("response")
    private String response;

    @SerializedName("stateNamelist")
    private List<StateNamelistItem> stateNamelist;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<StateNamelistItem> getStateNamelist() {
        return stateNamelist;
    }

    public void setStateNamelist(List<StateNamelistItem> stateNamelist) {
        this.stateNamelist = stateNamelist;
    }


}