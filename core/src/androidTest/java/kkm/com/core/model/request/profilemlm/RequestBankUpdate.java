package kkm.com.core.model.request.profilemlm;

import com.google.gson.annotations.SerializedName;

public class RequestBankUpdate {

    @SerializedName("Fk_MemId")
    private String fkMemId;

    @SerializedName("BankName")
    private String bankName;



    @SerializedName("AccountNo")
    private String accountNo;

    @SerializedName("IFSCCode")
    private String iFSCCode;

    @SerializedName("AccountHolderName")
    private String accountHolderName;

    @SerializedName("BranchName")
    private String branchName;



//    @SerializedName("UPI")
//    private String UPI;
//
//    public void setUPI(String UPI) {
//        this.UPI = UPI;
//    }

    public void setFkMemId(String fkMemId) {
        this.fkMemId = fkMemId;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }



    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setIFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }


}