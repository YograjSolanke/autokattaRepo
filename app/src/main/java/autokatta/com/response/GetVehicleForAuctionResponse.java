package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 15/4/17.
 */

public class GetVehicleForAuctionResponse {

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

        @SerializedName("Auction")
        @Expose
        private List<Auction> auction = null;
        @SerializedName("Vehicle")
        @Expose
        private List<Vehicle> vehicle = null;

        public List<Auction> getAuction() {
            return auction;
        }

        public void setAuction(List<Auction> auction) {
            this.auction = auction;
        }

        public List<Vehicle> getVehicle() {
            return vehicle;
        }

        public void setVehicle(List<Vehicle> vehicle) {
            this.vehicle = vehicle;
        }

    }

    public class Auction {

        @SerializedName("auction_id")
        @Expose
        private int auctionId;
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
        @SerializedName("stockLocation")
        @Expose
        private String stockLocation;
        @SerializedName("auction_category")
        @Expose
        private String auctionCategory;
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
        @SerializedName("auctioneer")
        @Expose
        private String auctioneer;

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

        public String getStockLocation() {
            return stockLocation;
        }

        public void setStockLocation(String stockLocation) {
            this.stockLocation = stockLocation;
        }

        public String getAuctionCategory() {
            return auctionCategory;
        }

        public void setAuctionCategory(String auctionCategory) {
            this.auctionCategory = auctionCategory;
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

        public String getAuctioneer() {
            return auctioneer;
        }

        public void setAuctioneer(String auctioneer) {
            this.auctioneer = auctioneer;
        }

    }


    public class Vehicle {

        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("sub_category")
        @Expose
        private String subCategory;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("location_city")
        @Expose
        private String locationCity;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("reg_no")
        @Expose
        private String regNo;
        @SerializedName("rto_city")
        @Expose
        private String rtoCity;
        @SerializedName("kms_running")
        @Expose
        private String kmsRunning;
        @SerializedName("hrs_running")
        @Expose
        private String hrsRunning;
        @SerializedName("year")
        @Expose
        private String year;
        @SerializedName("start_price")
        @Expose
        private Object startPrice;
        @SerializedName("reserve_price")
        @Expose
        private Object reservePrice;
        @SerializedName("chassis_no")
        @Expose
        private String chassisNo;
        @SerializedName("engineNo")
        @Expose
        private Object engineNo;
        @SerializedName("lotNo")
        @Expose
        private String lotNo;
        @SerializedName("vehicleStatus")
        @Expose
        private String vehicleStatus;
        @SerializedName("CurrentBid_price")
        @Expose
        private String currentBidPrice;
        @SerializedName("BidReceived_price")
        @Expose
        private String bidReceivedPrice;
        @SerializedName("startPrize")
        @Expose
        private String startPrize;

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRegNo() {
            return regNo;
        }

        public void setRegNo(String regNo) {
            this.regNo = regNo;
        }

        public String getRtoCity() {
            return rtoCity;
        }

        public void setRtoCity(String rtoCity) {
            this.rtoCity = rtoCity;
        }

        public String getKmsRunning() {
            return kmsRunning;
        }

        public void setKmsRunning(String kmsRunning) {
            this.kmsRunning = kmsRunning;
        }

        public String getHrsRunning() {
            return hrsRunning;
        }

        public void setHrsRunning(String hrsRunning) {
            this.hrsRunning = hrsRunning;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public Object getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(Object startPrice) {
            this.startPrice = startPrice;
        }

        public Object getReservePrice() {
            return reservePrice;
        }

        public void setReservePrice(Object reservePrice) {
            this.reservePrice = reservePrice;
        }

        public String getChassisNo() {
            return chassisNo;
        }

        public void setChassisNo(String chassisNo) {
            this.chassisNo = chassisNo;
        }

        public Object getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(Object engineNo) {
            this.engineNo = engineNo;
        }

        public String getLotNo() {
            return lotNo;
        }

        public void setLotNo(String lotNo) {
            this.lotNo = lotNo;
        }

        public String getVehicleStatus() {
            return vehicleStatus;
        }

        public void setVehicleStatus(String vehicleStatus) {
            this.vehicleStatus = vehicleStatus;
        }

        public String getCurrentBidPrice() {
            return currentBidPrice;
        }

        public void setCurrentBidPrice(String currentBidPrice) {
            this.currentBidPrice = currentBidPrice;
        }

        public String getBidReceivedPrice() {
            return bidReceivedPrice;
        }

        public void setBidReceivedPrice(String bidReceivedPrice) {
            this.bidReceivedPrice = bidReceivedPrice;
        }

        public String getStartPrize() {
            return startPrize;
        }

        public void setStartPrize(String startPrize) {
            this.startPrize = startPrize;
        }

    }
}
