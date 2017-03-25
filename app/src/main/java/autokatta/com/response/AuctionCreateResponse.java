package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-004 on 25/3/17.
 */

public class AuctionCreateResponse {

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

        @SerializedName("Auction_ID")
        @Expose
        private Integer auctionID;

        public Integer getAuctionID() {
            return auctionID;
        }

        public void setAuctionID(Integer auctionID) {
            this.auctionID = auctionID;
        }

    }
}
