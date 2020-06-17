package kkm.com.core.model.response.team;

import com.google.gson.annotations.SerializedName;

public class TeamStatusdetailsItem {

    @SerializedName("loginId")
    private String loginId;

    @SerializedName("profilepic")
    private String profilepic;

    @SerializedName("inviteCode")
    private String inviteCode;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("totalMembers")
    private String totalMembers;

    @SerializedName("memberName")
    private String memberName;

    @SerializedName("levelName")
    private String levelName;

    @SerializedName("kycStatus")
    private String kycStatus;

    public String getKycStatus() {
        return kycStatus;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }


    public String getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(String totalMembers) {
        this.totalMembers = totalMembers;
    }
}