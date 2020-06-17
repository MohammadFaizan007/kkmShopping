package kkm.com.core.model.response.team;

import com.google.gson.annotations.SerializedName;

public class DirectMembersItem {

    @SerializedName("bv")
    private String bv;

    @SerializedName("inviteCode")
    private String inviteCode;

    @SerializedName("memberName")
    private String memberName;

    @SerializedName("joiningDate")
    private String joiningDate;

    @SerializedName("packageName")
    private String packageName;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("packageAmount")
    private String packageAmount;

    @SerializedName("status")
    private String status;

    @SerializedName("mobile")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(String packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "DirectMembersItem{" +
                        "bv = '" + bv + '\'' +
                        ",inviteCode = '" + inviteCode + '\'' +
                        ",memberName = '" + memberName + '\'' +
                        ",joiningDate = '" + joiningDate + '\'' +
                        ",packageName = '" + packageName + '\'' +
                        ",memberId = '" + memberId + '\'' +
                        ",packageAmount = '" + packageAmount + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}