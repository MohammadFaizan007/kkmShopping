package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestTeamStatus {

//  {"LoginId":"190377","SessionLoginId":"190377"}

    @SerializedName("LoginId")
    private String LoginId;

    @SerializedName("SessionLoginId")
    private String SessionLoginId;

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public void setSessionLoginId(String sessionLoginId) {
        SessionLoginId = sessionLoginId;
    }
}