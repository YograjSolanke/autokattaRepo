package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 4/4/17.
 */

public class GetLiveEventsResponse {
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
        private String auctionId;
        @SerializedName("action_title")
        @Expose
        private String actionTitle;
        @SerializedName("contact")
        @Expose
        private String contact;
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

        @SerializedName("special_clauses")
        @Expose
        private String specialClauses;
        @SerializedName("vehicle_ids")
        @Expose
        private String vehicleIds;
        @SerializedName("lotNo")
        @Expose
        private String lotNo;
        @SerializedName("vehicleEndDateTime")
        @Expose
        private String vehicleEndDateTime;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("openClose")
        @Expose
        private String openClose;
        @SerializedName("showPrice")
        @Expose
        private String showPrice;
        @SerializedName("no_of_vehicles")
        @Expose
        private String noOfVehicles;
        @SerializedName("startDateTime")
        @Expose
        private String startDateTime;
        @SerializedName("endDateTime")
        @Expose
        private String endDateTime;
        @SerializedName("vehicleEndStatus")
        @Expose
        private String vehicleEndStatus;
        @SerializedName("createDate")
        @Expose
        private String createDate;
        @SerializedName("auctionStatus")
        @Expose
        private String auctionStatus;
        @SerializedName("mailSent")
        @Expose
        private String mailSent;
        @SerializedName("isReauctioned")
        @Expose
        private String isReauctioned;
        @SerializedName("countReauctioned")
        @Expose
        private String countReauctioned;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("ClausesNames")
        @Expose
        private String clausesNames;
        @SerializedName("auctioneer")
        @Expose
        private String auctioneer;
        @SerializedName("ignoreGoingStatus")
        @Expose
        private String ignoreGoingStatus;
        @SerializedName("blackListStatus")
        @Expose
        private String blackListStatus;
        @SerializedName("mycontact")
        @Expose
        private String mycontact;

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

        private String keyWord;

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getActionTitle() {
            return actionTitle;
        }

        public void setActionTitle(String actionTitle) {
            this.actionTitle = actionTitle;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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


        public String getSpecialClauses() {
            return specialClauses;
        }

        public void setSpecialClauses(String specialClauses) {
            this.specialClauses = specialClauses;
        }

        public String getVehicleIds() {
            return vehicleIds;
        }

        public void setVehicleIds(String vehicleIds) {
            this.vehicleIds = vehicleIds;
        }

        public String getLotNo() {
            return lotNo;
        }

        public void setLotNo(String lotNo) {
            this.lotNo = lotNo;
        }

        public String getVehicleEndDateTime() {
            return vehicleEndDateTime;
        }

        public void setVehicleEndDateTime(String vehicleEndDateTime) {
            this.vehicleEndDateTime = vehicleEndDateTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOpenClose() {
            return openClose;
        }

        public void setOpenClose(String openClose) {
            this.openClose = openClose;
        }

        public String getShowPrice() {
            return showPrice;
        }

        public void setShowPrice(String showPrice) {
            this.showPrice = showPrice;
        }

        public String getNoOfVehicles() {
            return noOfVehicles;
        }

        public void setNoOfVehicles(String noOfVehicles) {
            this.noOfVehicles = noOfVehicles;
        }

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }

        public String getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(String endDateTime) {
            this.endDateTime = endDateTime;
        }

        public String getVehicleEndStatus() {
            return vehicleEndStatus;
        }

        public void setVehicleEndStatus(String vehicleEndStatus) {
            this.vehicleEndStatus = vehicleEndStatus;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getAuctionStatus() {
            return auctionStatus;
        }

        public void setAuctionStatus(String auctionStatus) {
            this.auctionStatus = auctionStatus;
        }

        public String getMailSent() {
            return mailSent;
        }

        public void setMailSent(String mailSent) {
            this.mailSent = mailSent;
        }

        public String getIsReauctioned() {
            return isReauctioned;
        }

        public void setIsReauctioned(String isReauctioned) {
            this.isReauctioned = isReauctioned;
        }

        public String getCountReauctioned() {
            return countReauctioned;
        }

        public void setCountReauctioned(String countReauctioned) {
            this.countReauctioned = countReauctioned;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getClausesNames() {
            return clausesNames;
        }

        public void setClausesNames(String clausesNames) {
            this.clausesNames = clausesNames;
        }

        public String getAuctioneer() {
            return auctioneer;
        }

        public void setAuctioneer(String auctioneer) {
            this.auctioneer = auctioneer;
        }

        public String getIgnoreGoingStatus() {
            return ignoreGoingStatus;
        }

        public void setIgnoreGoingStatus(String ignoreGoingStatus) {
            this.ignoreGoingStatus = ignoreGoingStatus;
        }

        public String getBlackListStatus() {
            return blackListStatus;
        }

        public void setBlackListStatus(String blackListStatus) {
            this.blackListStatus = blackListStatus;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public String getMycontact() {
            return mycontact;
        }

        public void setMycontact(String mycontact) {
            this.mycontact = mycontact;
        }
    }
}
