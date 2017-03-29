package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-003 on 29/3/17.
 */

public class CreateStoreResponse {

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

        @SerializedName("Store_ID")
        @Expose
        private Integer storeID;

        public Integer getStoreID() {
            return storeID;
        }

        public void setStoreID(Integer storeID) {
            this.storeID = storeID;
        }

    }
}
