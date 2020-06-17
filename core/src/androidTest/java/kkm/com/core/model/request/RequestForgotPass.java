package kkm.com.core.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestForgotPass {


    @SerializedName("Mobile")
    private String mobile;



    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return
                "RequestForgotPass{" +

                        ",mobile = '" + mobile + '\'' +
                        "}";
    }
}