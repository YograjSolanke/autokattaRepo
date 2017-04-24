package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-004 on 24/4/17.
 */

public class OtherBrandTagAddedResponse {


    @SerializedName("Success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("BrandTagID")
        @Expose
        private Integer brandTagID;

        public Integer getBrandTagID() {
            return brandTagID;
        }

        public void setBrandTagID(Integer brandTagID) {
            this.brandTagID = brandTagID;
        }

    }
}
