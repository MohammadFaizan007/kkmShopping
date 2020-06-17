package kkm.com.core.model.response.themeParkResponse.themeParkAvailabilityArray;

import com.google.gson.annotations.SerializedName;

public class ResponseAvailabilityArray {

    @SerializedName("metadata")
    private Metadata metadata;

    @SerializedName("data")
    private Data data;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return
                "ResponseAvailabilityArray{" +
                        "metadata = '" + metadata + '\'' +
                        ",data = '" + data + '\'' +
                        "}";
    }
}