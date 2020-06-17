package kkm.com.core.model.response.mlmDashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardItem {

    @SerializedName("cmbusiness")
    private List<CmbusinessItem> cmbusiness;

    @SerializedName("sectionTitle")
    private String sectionTitle;

    @SerializedName("newsndinfo")
    private List<NewsndinfoItem> newsndinfo;

    @SerializedName("kycverification")
    private Kycverification kycverification;

    public List<CmbusinessItem> getCmbusiness() {
        return cmbusiness;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public List<NewsndinfoItem> getNewsndinfo() {
        return newsndinfo;
    }

    public Kycverification getKycverification() {
        return kycverification;
    }
}