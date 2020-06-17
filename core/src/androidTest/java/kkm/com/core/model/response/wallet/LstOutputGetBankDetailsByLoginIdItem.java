package kkm.com.core.model.response.wallet;

import com.google.gson.annotations.SerializedName;

public class LstOutputGetBankDetailsByLoginIdItem {

    @SerializedName("section_Tittle")
    private Object sectionTittle;

    @SerializedName("memberAccNo")
    private String memberAccNo;

    @SerializedName("memberBankName")
    private String memberBankName;

    @SerializedName("bankHolderName")
    private String bankHolderName;

    @SerializedName("ifscCode")
    private String ifscCode;

    public Object getSectionTittle() {
        return sectionTittle;
    }

    public String getMemberAccNo() {
        return memberAccNo;
    }

    public String getMemberBankName() {
        return memberBankName;
    }

    public String getBankHolderName() {
        return bankHolderName;
    }

    public String getIfscCode() {
        return ifscCode;
    }
}