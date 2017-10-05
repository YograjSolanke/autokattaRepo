package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 8/4/17.
 */

public class MyActiveAuctionNoBidResponse {
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

        public List<VehicleList> getVehicleList() {
            return vehicleList;
        }

        public void setVehicleList(List<VehicleList> vehicleList) {
            this.vehicleList = vehicleList;
        }


        public class VehicleList {

            @SerializedName("auctionid")
            @Expose
            private int auctionid;
            @SerializedName("vehicleid")
            @Expose
            private int vehicleid;
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
            @SerializedName("version")
            @Expose
            private String version;
            @SerializedName("color")
            @Expose
            private String color;
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
            private Object lotNo;
            @SerializedName("bidReceived")
            @Expose
            private String bidReceived;

            public int getAuctionid() {
                return auctionid;
            }

            public void setAuctionid(int auctionid) {
                this.auctionid = auctionid;
            }

            public int getVehicleid() {
                return vehicleid;
            }

            public void setVehicleid(int vehicleid) {
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

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
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

            public Object getLotNo() {
                return lotNo;
            }

            public void setLotNo(Object lotNo) {
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
