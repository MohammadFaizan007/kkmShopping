package kkm.com.core.model.response.register;

import com.google.gson.annotations.SerializedName;

public class TahsilNamelistItem {

    @SerializedName("tehsilName")
    private String tehsilName;

    public String getTehsilName() {
        return tehsilName;
    }

    public void setTehsilName(String tehsilName) {
        this.tehsilName = tehsilName;
    }

    @Override
    public String toString() {
        return
                "TahsilNamelistItem{" +
                        "tehsilName = '" + tehsilName + '\'' +
                        "}";
    }
}