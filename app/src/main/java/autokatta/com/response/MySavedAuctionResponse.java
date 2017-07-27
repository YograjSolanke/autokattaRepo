package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 22/3/17.
 */

public class MySavedAuctionResponse {

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

        @SerializedName("auction_id")
        @Expose
        private int auctionId;
        @SerializedName("action_title")
        @Expose
        private String actionTitle;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("auction_type")
        @Expose
        private String auctionType;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("no_of_vehicles")
        @Expose
        private String noOfVehicles;
        @SerializedName("special_clauses")
        @Expose
        private String specialClauses;
        @SerializedName("special_ids")
        @Expose
        private String specialIds;
        @SerializedName("special_position")
        @Expose
        private String specialPosition;
        @SerializedName("positionArray")
        @Expose
        private boolean positionArray[] = null;

        @SerializedName("auction_category")
        @Expose
        private String auctionCategory;

        @SerializedName("stockLocation")
        @Expose
        private String stockLocation;

        public String getStockLocation() {
            return stockLocation;
        }

        public void setStockLocation(String stockLocation) {
            this.stockLocation = stockLocation;
        }

        public String getAuctioncategory() {
            return auctionCategory;
        }

        public void setAuctioncategory(String auctionCategory) {
            this.auctionCategory = auctionCategory;
        }

        public int getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(int auctionId) {
            this.auctionId = auctionId;
        }

        public String getActionTitle() {
            return actionTitle;
        }

        public void setActionTitle(String actionTitle) {
            this.actionTitle = actionTitle;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAuctionType() {
            return auctionType;
        }

        public void setAuctionType(String auctionType) {
            this.auctionType = auctionType;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getNoOfVehicles() {
            return noOfVehicles;
        }

        public void setNoOfVehicles(String noOfVehicles) {
            this.noOfVehicles = noOfVehicles;
        }

        public String getSpecialClauses() {
            return specialClauses;
        }

        public void setSpecialClauses(String specialClauses) {
            this.specialClauses = specialClauses;
        }

        public String getSpecialIds() {
            return specialIds;
        }

        public void setSpecialIds(String specialIds) {
            this.specialIds = specialIds;
        }

        public String getSpecialPosition() {
            return specialPosition;
        }

        public void setSpecialPosition(String specialPosition) {
            this.specialPosition = specialPosition;
        }

        public boolean[] getPositionArray() {
            return positionArray;
        }

        public void setPositionArray(boolean[] positionArray) {
            this.positionArray = positionArray;
        }

    }
}
