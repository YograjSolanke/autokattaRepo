package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 5/4/17.
 */

public class GetAuctionEventResponse {

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
        @SerializedName("Vehicles")
        @Expose
        private List<Vehicle> vehicles = null;

        public List<Auction> getAuction() {
            return auction;
        }

        public void setAuction(List<Auction> auction) {
            this.auction = auction;
        }

        public List<Vehicle> getVehicles() {
            return vehicles;
        }

        public void setVehicles(List<Vehicle> vehicles) {
            this.vehicles = vehicles;
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
        private String startPrice;
        @SerializedName("reserve_price")
        @Expose
        private String reservePrice;
        @SerializedName("chassis_no")
        @Expose
        private String chassisNo;
        @SerializedName("engineNo")
        @Expose
        private Object engineNo;
        @SerializedName("lotNo")
        @Expose
        private Object lotNo;
        @SerializedName("vehicleStatus")
        @Expose
        private String vehicleStatus;

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

        public String getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(String startPrice) {
            this.startPrice = startPrice;
        }

        public String getReservePrice() {
            return reservePrice;
        }

        public void setReservePrice(String reservePrice) {
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

        public Object getLotNo() {
            return lotNo;
        }

        public void setLotNo(Object lotNo) {
            this.lotNo = lotNo;
        }

        public String getVehicleStatus() {
            return vehicleStatus;
        }

        public void setVehicleStatus(String vehicleStatus) {
            this.vehicleStatus = vehicleStatus;
        }

    }

    public class Auction {

        @SerializedName("auction_id")
        @Expose
        private String auctionId;
        @SerializedName("contact")
        @Expose
        private String contact;
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
        @SerializedName("product_category")
        @Expose
        private String productCategory;
        @SerializedName("startDateTime")
        @Expose
        private String startDateTime;
        @SerializedName("endDateTime")
        @Expose
        private String endDateTime;
        @SerializedName("no_of_vehicles")
        @Expose
        private String noOfVehicles;
        @SerializedName("lotNo")
        @Expose
        private String lotNo;
        @SerializedName("goingcount")
        @Expose
        private String goingcount;
        @SerializedName("special_clauses")
        @Expose
        private String specialClauses;

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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

        public String getProductCategory() {
            return productCategory;
        }

        public void setProductCategory(String productCategory) {
            this.productCategory = productCategory;
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

        public String getNoOfVehicles() {
            return noOfVehicles;
        }

        public void setNoOfVehicles(String noOfVehicles) {
            this.noOfVehicles = noOfVehicles;
        }

        public String getLotNo() {
            return lotNo;
        }

        public void setLotNo(String lotNo) {
            this.lotNo = lotNo;
        }

        public String getGoingcount() {
            return goingcount;
        }

        public void setGoingcount(String goingcount) {
            this.goingcount = goingcount;
        }

        public String getSpecialClauses() {
            return specialClauses;
        }

        public void setSpecialClauses(String specialClauses) {
            this.specialClauses = specialClauses;
        }

    }
}