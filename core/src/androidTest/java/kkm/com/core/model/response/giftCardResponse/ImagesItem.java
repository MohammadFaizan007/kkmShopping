package kkm.com.core.model.response.giftCardResponse;

import com.google.gson.annotations.SerializedName;

public class ImagesItem {

    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}