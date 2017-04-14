package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 22/3/17.
 */

public class MyActiveAuctionResponse {

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


        public List<Auction> getAuction() {
            return auction;
        }

        public void setAuction(List<Auction> auction) {
            this.auction = auction;
        }

        ////////////
        @SerializedName("Vehicles")
        @Expose
        private List<Vehicle> vehicles = null;

        public List<Vehicle> getVehicles() {
            return vehicles;
        }

        public void setVehicles(List<Vehicle> vehicles) {
            this.vehicles = vehicles;
        }

        public class Auction {

            @SerializedName("Vehicles")
            @Expose
            private List<Vehicle> vehicles = null;

            public List<Vehicle> getVehicles() {
                return vehicles;
            }

            public void setVehicles(List<Vehicle> vehicles) {
                this.vehicles = vehicles;
            }

            @SerializedName("auction_id")
            @Expose
            private String auctionId;
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

            @SerializedName("special_clauses")
            @Expose
            private String specialClauses;
            @SerializedName("vehicle_ids")
            @Expose
            private String vehicleIds;
            @SerializedName("no_of_vehicle")
            @Expose
            private String noOfVehicle;
            @SerializedName("startDateTime")
            @Expose
            private String startDateTime;
            @SerializedName("endDateTime")
            @Expose
            private String endDateTime;
            @SerializedName("goingcount")
            @Expose
            private String goingcount;

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

            public String getNoOfVehicle() {
                return noOfVehicle;
            }

            public void setNoOfVehicle(String noOfVehicle) {
                this.noOfVehicle = noOfVehicle;
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

            public String getGoingcount() {
                return goingcount;
            }

            public void setGoingcount(String goingcount) {
                this.goingcount = goingcount;
            }
        }

        public class Vehicle {

            @SerializedName("auction_id")
            @Expose
            private String auctionId;
            @SerializedName("vehicle_id")
            @Expose
            private String vehicleId;
            @SerializedName("title")
            @Expose
            private String title;
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
            @SerializedName("start_price")
            @Expose
            private String startPrice;
            @SerializedName("reserve_price")
            @Expose
            private String reservePrice;

            public String getAuctionId() {
                return auctionId;
            }

            public void setAuctionId(String auctionId) {
                this.auctionId = auctionId;
            }

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
        }
    }

}
