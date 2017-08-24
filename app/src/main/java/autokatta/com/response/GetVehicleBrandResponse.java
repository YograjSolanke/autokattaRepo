package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 23/3/17.
 */

public class GetVehicleBrandResponse {


    @SerializedName("Success")
    @Expose
    private List<Success> success = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public class Success {

        @SerializedName("brand_id")
        @Expose
        private int brandId;
        @SerializedName("brand_title")
        @Expose
        private String brandTitle;

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getBrandTitle() {
            return brandTitle;
        }

        public void setBrandTitle(String brandTitle) {
            this.brandTitle = brandTitle;
        }
    }
}
