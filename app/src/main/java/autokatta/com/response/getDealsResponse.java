package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 27/3/17.
 */

public class getDealsResponse {


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
