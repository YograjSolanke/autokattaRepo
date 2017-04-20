package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 19/4/17.
 */

public class GetSearchAuctionResponse {
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

        @SerializedName("AuctionLive")
        @Expose
        private List<AuctionLive> auctionLive = null;
        @SerializedName("VehiclesLive")
        @Expose
        private List<VehiclesLive> vehiclesLive = null;
        @SerializedName("AuctionUp")
        @Expose
        private List<AuctionUp> auctionUp = null;
        @SerializedName("VehiclesUp")
        @Expose
        private List<VehiclesUp> vehiclesUp = null;
        @SerializedName("ExchangeMelaLive")
        @Expose
        private List<Object> exchangeMelaLive = null;
        @SerializedName("ExchangeMelaUp")
        @Expose
        private List<Object> exchangeMelaUp = null;
        @SerializedName("LoanMelaLive")
        @Expose
        private List<Object> loanMelaLive = null;
        @SerializedName("LoanMelaUp")
        @Expose
        private List<Object> loanMelaUp = null;

        public List<AuctionLive> getAuctionLive() {
            return auctionLive;
        }

        public void setAuctionLive(List<AuctionLive> auctionLive) {
            this.auctionLive = auctionLive;
        }

        public List<VehiclesLive> getVehiclesLive() {
            return vehiclesLive;
        }

        public void setVehiclesLive(List<VehiclesLive> vehiclesLive) {
            this.vehiclesLive = vehiclesLive;
        }

        public List<AuctionUp> getAuctionUp() {
            return auctionUp;
        }

        public void setAuctionUp(List<AuctionUp> auctionUp) {
            this.auctionUp = auctionUp;
        }

        public List<VehiclesUp> getVehiclesUp() {
            return vehiclesUp;
        }

        public void setVehiclesUp(List<VehiclesUp> vehiclesUp) {
            this.vehiclesUp = vehiclesUp;
        }

        public List<Object> getExchangeMelaLive() {
            return exchangeMelaLive;
        }

        public void setExchangeMelaLive(List<Object> exchangeMelaLive) {
            this.exchangeMelaLive = exchangeMelaLive;
        }

        public List<Object> getExchangeMelaUp() {
            return exchangeMelaUp;
        }

        public void setExchangeMelaUp(List<Object> exchangeMelaUp) {
            this.exchangeMelaUp = exchangeMelaUp;
        }

        public List<Object> getLoanMelaLive() {
            return loanMelaLive;
        }

        public void setLoanMelaLive(List<Object> loanMelaLive) {
            this.loanMelaLive = loanMelaLive;
        }

        public List<Object> getLoanMelaUp() {
            return loanMelaUp;
        }

        public void setLoanMelaUp(List<Object> loanMelaUp) {
            this.loanMelaUp = loanMelaUp;
        }


        public class AuctionLive {

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
            @SerializedName("product_category")
            @Expose
            private Object productCategory;
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
            @SerializedName("key")
            @Expose
            private Integer key;
            @SerializedName("EventType")
            @Expose
            private String eventType;

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

            public Object getProductCategory() {
                return productCategory;
            }

            public void setProductCategory(Object productCategory) {
                this.productCategory = productCategory;
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

            public Integer getKey() {
                return key;
            }

            public void setKey(Integer key) {
                this.key = key;
            }

            public String getEventType() {
                return eventType;
            }

            public void setEventType(String eventType) {
                this.eventType = eventType;
            }
        }

        public class AuctionUp {

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
            @SerializedName("product_category")
            @Expose
            private Object productCategory;
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
            @SerializedName("key")
            @Expose
            private Integer key;
            @SerializedName("EventType")
            @Expose
            private String eventType;

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

            public Object getProductCategory() {
                return productCategory;
            }

            public void setProductCategory(Object productCategory) {
                this.productCategory = productCategory;
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

            public Integer getKey() {
                return key;
            }

            public void setKey(Integer key) {
                this.key = key;
            }

            public String getEventType() {
                return eventType;
            }

            public void setEventType(String eventType) {
                this.eventType = eventType;
            }

        }

        public class VehiclesLive {

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

        public class VehiclesUp {

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

