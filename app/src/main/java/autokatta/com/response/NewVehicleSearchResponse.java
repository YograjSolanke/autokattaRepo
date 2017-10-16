package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 16/10/17.
 */

public class NewVehicleSearchResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;
    @SerializedName("Error")
    @Expose
    private Object error;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }


    public class Success {

        @SerializedName("StoreID")
        @Expose
        private Integer storeID;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Brands")
        @Expose
        private String brands;
        @SerializedName("BrandTags")
        @Expose
        private String brandTags;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("ContactNo")
        @Expose
        private String contactNo;
        @SerializedName("CoverImage")
        @Expose
        private String coverImage;
        @SerializedName("CreationDate_1")
        @Expose
        private String creationDate1;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("Longitude")
        @Expose
        private String longitude;
        @SerializedName("ModifiedDate_1")
        @Expose
        private String modifiedDate1;
        @SerializedName("ModifiedDate")
        @Expose
        private String modifiedDate;
        @SerializedName("Rating")
        @Expose
        private Integer rating;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("StoreImage")
        @Expose
        private String storeImage;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("OpenTime")
        @Expose
        private String openTime;
        @SerializedName("StoreType")
        @Expose
        private String storeType;
        @SerializedName("VehicleID")
        @Expose
        private Integer vehicleID;
        @SerializedName("WebSite")
        @Expose
        private String webSite;
        @SerializedName("WorkingDays")
        @Expose
        private String workingDays;
        @SerializedName("AverageRate")
        @Expose
        private String averageRate;

        public Integer getStoreID() {
            return storeID;
        }

        public void setStoreID(Integer storeID) {
            this.storeID = storeID;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBrands() {
            return brands;
        }

        public void setBrands(String brands) {
            this.brands = brands;
        }

        public String getBrandTags() {
            return brandTags;
        }

        public void setBrandTags(String brandTags) {
            this.brandTags = brandTags;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getCreationDate1() {
            return creationDate1;
        }

        public void setCreationDate1(String creationDate1) {
            this.creationDate1 = creationDate1;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getModifiedDate1() {
            return modifiedDate1;
        }

        public void setModifiedDate1(String modifiedDate1) {
            this.modifiedDate1 = modifiedDate1;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStoreImage() {
            return storeImage;
        }

        public void setStoreImage(String storeImage) {
            this.storeImage = storeImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public Integer getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(Integer vehicleID) {
            this.vehicleID = vehicleID;
        }

        public String getWebSite() {
            return webSite;
        }

        public void setWebSite(String webSite) {
            this.webSite = webSite;
        }

        public String getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(String workingDays) {
            this.workingDays = workingDays;
        }

        public String getAverageRate() {
            return averageRate;
        }

        public void setAverageRate(String averageRate) {
            this.averageRate = averageRate;
        }
    }
}
