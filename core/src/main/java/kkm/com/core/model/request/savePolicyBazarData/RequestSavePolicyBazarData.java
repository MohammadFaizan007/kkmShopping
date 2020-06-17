package kkm.com.core.model.request.savePolicyBazarData;

import com.google.gson.annotations.SerializedName;

public class RequestSavePolicyBazarData {

    @SerializedName("PBPassword")
    private String pBPassword;

    @SerializedName("PBUserName")
    private String pBUserName;

    @SerializedName("DOB")
    private String dOB;

    @SerializedName("PBID")
    private String pBID;

    @SerializedName("FK_MemId")
    private String fKMemId;

    @SerializedName("Email")
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPBPassword(String pBPassword) {
        this.pBPassword = pBPassword;
    }

    public String getPBPassword() {
        return pBPassword;
    }

    public void setPBUserName(String pBUserName) {
        this.pBUserName = pBUserName;
    }

    public String getPBUserName() {
        return pBUserName;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getDOB() {
        return dOB;
    }

    public void setPBID(String pBID) {
        this.pBID = pBID;
    }

    public String getPBID() {
        return pBID;
    }

    public void setFKMemId(String fKMemId) {
        this.fKMemId = fKMemId;
    }

    public String getFKMemId() {
        return fKMemId;
    }

    @Override
    public String toString() {
        return
                "ResponseSavePolicyBazarData{" +
                        "pBPassword = '" + pBPassword + '\'' +
                        ",pBUserName = '" + pBUserName + '\'' +
                        ",dOB = '" + dOB + '\'' +
                        ",pBID = '" + pBID + '\'' +
                        ",fK_MemId = '" + fKMemId + '\'' +
                        "}";
    }
}