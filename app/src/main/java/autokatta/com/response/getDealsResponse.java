package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 27/3/17.
 */

public class getDealsResponse {


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

        @SerializedName("deal_id")
        @Expose
        private String dealId;
        @SerializedName("dealNames")
        @Expose
        private String dealNames;

        public String getDealId() {
            return dealId;
        }

        public void setDealId(String dealId) {
            this.dealId = dealId;
        }

        public String getDealNames() {
            return dealNames;
        }

        public void setDealNames(String dealNames) {
            this.dealNames = dealNames;
        }
    }
    }
