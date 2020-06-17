package kkm.com.core.model.request.m2p;

import com.google.gson.annotations.SerializedName;

public class RequestKit {

    @SerializedName("LoginId")
    private String loginId;

    @SerializedName("CardType")
    private String cardType;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    @Override
    public String toString() {
        return
                "RequestKit{" +
                        ",loginId = '" + loginId + '\'' +
                        "}";
    }
}