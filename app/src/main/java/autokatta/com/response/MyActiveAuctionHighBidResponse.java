package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 7/4/17.
 */

public class MyActiveAuctionHighBidResponse {


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

        @SerializedName("vehicleList")
        @Expose
        private List<VehicleList> vehicleList = null;
        @SerializedName("biddersList")
        @Expose
        private List<BiddersList> biddersList = null;

        public List<VehicleList> getVehicleList() {
            return vehicleList;
        }

        public void setVehicleList(List<VehicleList> vehicleList) {
            this.vehicleList = vehicleList;
        }

        public List<BiddersList> getBiddersList() {
            return biddersList;
        }

        public void setBiddersList(List<BiddersList> biddersList) {
            this.biddersList = biddersList;
        }

        public class BiddersList {

            @SerializedName("auctionid")
            @Expose
            private String auctionid;
            @SerializedName("vehicleid")
            @Expose
            private String vehicleid;
            @SerializedName("contact")
            @Expose
            private String contact;
            @SerializedName("bidderName")
            @Expose
            private String bidderName;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("bidPrice")
            @Expose
            private String bidPrice;
            @SerializedName("approvedStatus")
            @Expose
            private String approvedStatus;
            @SerializedName("approvedDate")
            @Expose
            private String approvedDate;
            @SerializedName("rejectStatus")
            @Expose
            private String rejectStatus;
            @SerializedName("counter")
            @Expose
            private Integer counter;
            @SerializedName("blackList")
            @Expose
            private String blackList;
            @SerializedName("noOfBids")
            @Expose
            private String noOfBids;

            public String getAuctionid() {
                return auctionid;
            }

            public void setAuctionid(String auctionid) {
                this.auctionid = auctionid;
            }

            public String getVehicleid() {
                return vehicleid;
            }

            public void setVehicleid(String vehicleid) {
                this.vehicleid = vehicleid;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getBidderName() {
                return bidderName;
            }

            public void setBidderName(String bidderName) {
                this.bidderName = bidderName;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getBidPrice() {
                return bidPrice;
            }

            public void setBidPrice(String bidPrice) {
                this.bidPrice = bidPrice;
            }

            public String getApprovedStatus() {
                return approvedStatus;
            }

            public void setApprovedStatus(String approvedStatus) {
                this.approvedStatus = approvedStatus;
            }

            public String getApprovedDate() {
                return approvedDate;
            }

            public void setApprovedDate(String approvedDate) {
                this.approvedDate = approvedDate;
            }

            public String getRejectStatus() {
                return rejectStatus;
            }

            public void setRejectStatus(String rejectStatus) {
                this.rejectStatus = rejectStatus;
            }

            public Integer getCounter() {
                return counter;
            }

            public void setCounter(Integer counter) {
                this.counter = counter;
            }

            public String getBlackList() {
                return blackList;
            }

            public void setBlackList(String blackList) {
                this.blackList = blackList;
            }

            public String getNoOfBids() {
                return noOfBids;
            }

            public void setNoOfBids(String noOfBids) {
                this.noOfBids = noOfBids;
            }

        }


        public class VehicleList {

            @SerializedName("auctionid")
            @Expose
            private String auctionid;
            @SerializedName("vehicleid")
            @Expose
            private String vehicleid;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("contact")
            @Expose
            private String contact;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("brand")
            @Expose
            private String brand;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("year")
            @Expose
            private String year;
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("location")
            @Expose
            private String location;
            @SerializedName("kms_running")
            @Expose
            private String kmsRunning;
            @SerializedName("hrs_running")
            @Expose
            private String hrsRunning;
            @SerializedName("reg_no")
            @Expose
            private String regNo;
            @SerializedName("start_price")
            @Expose
            private String startPrice;
            @SerializedName("reserve_price")
            @Expose
            private String reservePrice;
            @SerializedName("vehicleStatus")
            @Expose
            private String vehicleStatus;
            @SerializedName("lotNo")
            @Expose
            private String lotNo;
            @SerializedName("bidReceived")
            @Expose
            private String bidReceived;

            public String getAuctionid() {
                return auctionid;
            }

            public void setAuctionid(String auctionid) {
                this.auctionid = auctionid;
            }

            public String getVehicleid() {
                return vehicleid;
            }

            public void setVehicleid(String vehicleid) {
                this.vehicleid = vehicleid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getRtoCity() {
                return rtoCity;
            }

            public void setRtoCity(String rtoCity) {
                this.rtoCity = rtoCity;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
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

            public String getRegNo() {
                return regNo;
            }

            public void setRegNo(String regNo) {
                this.regNo = regNo;
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

            public String getVehicleStatus() {
                return vehicleStatus;
            }

            public void setVehicleStatus(String vehicleStatus) {
                this.vehicleStatus = vehicleStatus;
            }

            public String getLotNo() {
                return lotNo;
            }

            public void setLotNo(String lotNo) {
                this.lotNo = lotNo;
            }

            public String getBidReceived() {
                return bidReceived;
            }

            public void setBidReceived(String bidReceived) {
                this.bidReceived = bidReceived;
            }

        }
    }
}
