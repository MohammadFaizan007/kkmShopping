package kkm.com.core.model.response.team;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTeamStatus {

    @SerializedName("response")
    private String response;

    @SerializedName("teamStatus")
    private List<TeamStatusItem> teamStatus;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<TeamStatusItem> getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(List<TeamStatusItem> teamStatus) {
        this.teamStatus = teamStatus;
    }

    @Override
    public String toString() {
        return
                "ResponseTeamStatus{" +
                        "response = '" + response + '\'' +
                        ",teamStatus = '" + teamStatus + '\'' +
                        "}";
    }
}