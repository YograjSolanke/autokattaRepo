package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-003 on 27/4/17.
 */

public class SaleMelaCreateResponse {

    @SerializedName("Success")
    @Expose
    private SaleMelaCreateResponse.Success success;

    public SaleMelaCreateResponse.Success getSuccess() {
        return success;
    }

    public void setSuccess(SaleMelaCreateResponse.Success success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("Sale_ID")
        @Expose
        private Integer saleID;

        public Integer getSaleID() {
            return saleID;
        }

        public void setSaleID(Integer saleID) {
            this.saleID = saleID;
        }

    }
}
