package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 7/4/17.
 */

public class YourBidResponse {
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

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("year")
        @Expose
        private String year;
        @SerializedName("vehicleid")
        @Expose
        private String vehicleid;
        @SerializedName("auctionid")
        @Expose
        private String auctionid;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("location_city")
        @Expose
        private String locationCity;
        @SerializedName("rto_city")
        @Expose
        private String rtoCity;
        @SerializedName("rc_available")
        @Expose
        private String rcAvailable;
        @SerializedName("kms_running")
        @Expose
        private String kmsRunning;
        @SerializedName("Hrs_running")
        @Expose
        private String hrsRunning;
        @SerializedName("invoice")
        @Expose
        private String invoice;
        @SerializedName("reg_no")
        @Expose
        private String regNo;
        @SerializedName("startPrice")
        @Expose
        private String startPrice;
        @SerializedName("reserve_price")
        @Expose
        private String reservePrice;
        @SerializedName("lotNo")
        @Expose
        private String lotNo;
        @SerializedName("CurrentBid_price")
        @Expose
        private String currentBidPrice;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("auctionBidId")
        @Expose
        private String auctionBidId;
        @SerializedName("BidReceived_price")
        @Expose
        private String bidReceivedPrice;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getVehicleid() {
            return vehicleid;
        }

        public void setVehicleid(String vehicleid) {
            this.vehicleid = vehicleid;
        }

        public String getAuctionid() {
            return auctionid;
        }

        public void setAuctionid(String auctionid) {
            this.auctionid = auctionid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public String getRtoCity() {
            return rtoCity;
        }

        public void setRtoCity(String rtoCity) {
            this.rtoCity = rtoCity;
        }

        public String getRcAvailable() {
            return rcAvailable;
        }

        public void setRcAvailable(String rcAvailable) {
            this.rcAvailable = rcAvailable;
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

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
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

        public String getLotNo() {
            return lotNo;
        }

        public void setLotNo(String lotNo) {
            this.lotNo = lotNo;
        }

        public String getCurrentBidPrice() {
            return currentBidPrice;
        }

        public void setCurrentBidPrice(String currentBidPrice) {
            this.currentBidPrice = currentBidPrice;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAuctionBidId() {
            return auctionBidId;
        }

        public void setAuctionBidId(String auctionBidId) {
            this.auctionBidId = auctionBidId;
        }

        public String getBidReceivedPrice() {
            return bidReceivedPrice;
        }

        public void setBidReceivedPrice(String bidReceivedPrice) {
            this.bidReceivedPrice = bidReceivedPrice;
        }

    }
}
