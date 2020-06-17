package kkm.com.core.model.response.team;

import com.google.gson.annotations.SerializedName;

public class DownlineListItem {

    @SerializedName("sponsorId")
    private String sponsorId;

    @SerializedName("sponsorName")
    private String sponsorName;

    @SerializedName("memberName")
    private String memberName;

    @SerializedName("joiningDate")
    private String joiningDate;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("status")
    private String status;

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}