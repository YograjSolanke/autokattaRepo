package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 3/4/17.
 */

public class AuctionAllVehicleResponse {

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

        @SerializedName("UploadedVehicles")
        @Expose
        private List<UploadedVehicle> uploadedVehicles = null;
        @SerializedName("ReauctionVehicles")
        @Expose
        private List<ReauctionVehicle> reauctionVehicles = null;
        @SerializedName("Auctions")
        @Expose
        private List<Auction> auctions = null;
        @SerializedName("AdminVehicles")
        @Expose
        private List<AdminVehicle> adminVehicles = null;

        public List<UploadedVehicle> getUploadedVehicles() {
            return uploadedVehicles;
        }

        public void setUploadedVehicles(List<UploadedVehicle> uploadedVehicles) {
            this.uploadedVehicles = uploadedVehicles;
        }

        public List<ReauctionVehicle> getReauctionVehicles() {
            return reauctionVehicles;
        }

        public void setReauctionVehicles(List<ReauctionVehicle> reauctionVehicles) {
            this.reauctionVehicles = reauctionVehicles;
        }

        public List<Auction> getAuctions() {
            return auctions;
        }

        public void setAuctions(List<Auction> auctions) {
            this.auctions = auctions;
        }

        public List<AdminVehicle> getAdminVehicles() {
            return adminVehicles;
        }

        public void setAdminVehicles(List<AdminVehicle> adminVehicles) {
            this.adminVehicles = adminVehicles;
        }


        public class ReauctionVehicle {

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
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("location_city")
            @Expose
            private String locationCity;
            @SerializedName("year")
            @Expose
            private String year;
            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("registration_number")
            @Expose
            private String registrationNumber;
            @SerializedName("StartPrice")
            @Expose
            private String startPrice;
            @SerializedName("ReservedPrice")
            @Expose
            private String reservedPrice;

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

            public String getRtoCity() {
                return rtoCity;
            }

            public void setRtoCity(String rtoCity) {
                this.rtoCity = rtoCity;
            }

            public String getLocationCity() {
                return locationCity;
            }

            public void setLocationCity(String locationCity) {
                this.locationCity = locationCity;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
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

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
            }

            public String getStartPrice() {
                return startPrice;
            }

            public void setStartPrice(String startPrice) {
                this.startPrice = startPrice;
            }

            public String getReservedPrice() {
                return reservedPrice;
            }

            public void setReservedPrice(String reservedPrice) {
                this.reservedPrice = reservedPrice;
            }

        }


        public class UploadedVehicle {

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
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("location_city")
            @Expose
            private String locationCity;
            @SerializedName("year")
            @Expose
            private String year;
            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("registration_number")
            @Expose
            private String registrationNumber;
            @SerializedName("StartPrice")
            @Expose
            private String startPrice;
            @SerializedName("ReservedPrice")
            @Expose
            private String reservedPrice;

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

            public String getRtoCity() {
                return rtoCity;
            }

            public void setRtoCity(String rtoCity) {
                this.rtoCity = rtoCity;
            }

            public String getLocationCity() {
                return locationCity;
            }

            public void setLocationCity(String locationCity) {
                this.locationCity = locationCity;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
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

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
            }

            public String getStartPrice() {
                return startPrice;
            }

            public void setStartPrice(String startPrice) {
                this.startPrice = startPrice;
            }

            public String getReservedPrice() {
                return reservedPrice;
            }

            public void setReservedPrice(String reservedPrice) {
                this.reservedPrice = reservedPrice;
            }

        }

        public class AdminVehicle {

            @SerializedName("vehicle_id")
            @Expose
            private String vehicleId;
            @SerializedName("contactNo")
            @Expose
            private String contactNo;
            @SerializedName("lotNo")
            @Expose
            private String lotNo;
            @SerializedName("registrationYear")
            @Expose
            private String registrationYear;
            @SerializedName("repoDate")
            @Expose
            private String repoDate;
            @SerializedName("yardRentPerDay")
            @Expose
            private String yardRentPerDay;
            @SerializedName("brand")
            @Expose
            private String brand;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("version")
            @Expose
            private String version;
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("location_city")
            @Expose
            private String locationCity;
            @SerializedName("year")
            @Expose
            private String year;
            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("registration_number")
            @Expose
            private String registrationNumber;
            @SerializedName("StartPrice")
            @Expose
            private String startPrice;
            @SerializedName("ReservedPrice")
            @Expose
            private String reservedPrice;

            public String getVehicleId() {
                return vehicleId;
            }

            public void setVehicleId(String vehicleId) {
                this.vehicleId = vehicleId;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getLotNo() {
                return lotNo;
            }

            public void setLotNo(String lotNo) {
                this.lotNo = lotNo;
            }

            public String getRegistrationYear() {
                return registrationYear;
            }

            public void setRegistrationYear(String registrationYear) {
                this.registrationYear = registrationYear;
            }

            public String getRepoDate() {
                return repoDate;
            }

            public void setRepoDate(String repoDate) {
                this.repoDate = repoDate;
            }

            public String getYardRentPerDay() {
                return yardRentPerDay;
            }

            public void setYardRentPerDay(String yardRentPerDay) {
                this.yardRentPerDay = yardRentPerDay;
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

            public String getRtoCity() {
                return rtoCity;
            }

            public void setRtoCity(String rtoCity) {
                this.rtoCity = rtoCity;
            }

            public String getLocationCity() {
                return locationCity;
            }

            public void setLocationCity(String locationCity) {
                this.locationCity = locationCity;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
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

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
            }

            public String getStartPrice() {
                return startPrice;
            }

            public void setStartPrice(String startPrice) {
                this.startPrice = startPrice;
            }

            public String getReservedPrice() {
                return reservedPrice;
            }

            public void setReservedPrice(String reservedPrice) {
                this.reservedPrice = reservedPrice;
            }

        }


        public class Auction {

            @SerializedName("action_title")
            @Expose
            private String actionTitle;
            @SerializedName("auction_id")
            @Expose
            private String auctionId;
            @SerializedName("reauctionvehiCount")
            @Expose
            private String reauctionvehiCount;

            public String getActionTitle() {
                return actionTitle;
            }

            public void setActionTitle(String actionTitle) {
                this.actionTitle = actionTitle;
            }

            public String getAuctionId() {
                return auctionId;
            }

            public void setAuctionId(String auctionId) {
                this.auctionId = auctionId;
            }

            public String getReauctionvehiCount() {
                return reauctionvehiCount;
            }

            public void setReauctionvehiCount(String reauctionvehiCount) {
                this.reauctionvehiCount = reauctionvehiCount;
            }

        }
    }
}
