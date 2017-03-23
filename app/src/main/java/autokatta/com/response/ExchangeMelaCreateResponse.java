package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-004 on 23/3/17.
 */

public class ExchangeMelaCreateResponse {


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

        @SerializedName("Exchange_ID")
        @Expose
        private Integer exchangeID;

        public Integer getExchangeID() {
            return exchangeID;
        }

        public void setExchangeID(Integer exchangeID) {
            this.exchangeID = exchangeID;
        }

    }
}
