package kkm.com.core.model.response.giftCardResponse;

import com.google.gson.annotations.SerializedName;

public class AddinfoItem {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}