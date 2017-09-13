package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 13/9/17.
 */

public class ShowQrResponse {
    @SerializedName("Success")
    @Expose
    private List<Success> success = null;
    @SerializedName("Error")
    @Expose
    private Object error;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("StoreName")
        @Expose
        private String storeName;
        @SerializedName("StoreLogoImage")
        @Expose
        private String storeLogoImage;

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreLogoImage() {
            return storeLogoImage;
        }

        public void setStoreLogoImage(String storeLogoImage) {
            this.storeLogoImage = storeLogoImage;
        }
    }
}
