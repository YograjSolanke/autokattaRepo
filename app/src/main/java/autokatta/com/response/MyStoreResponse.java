package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 19/3/17.
 */

public class MyStoreResponse {
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
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("Brands")
        @Expose
        private String brands;
        @SerializedName("BrandTags")
        @Expose
        private String brandTags;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("coverImage")
        @Expose
        private String coverImage;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Longitude")
        @Expose
        private String longitude;
        @SerializedName("ModifiedDate")
        @Expose
        private String modifiedDate;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("Status")
        @Expose
        private Object status;
        @SerializedName("store_image")
        @Expose
        private String storeImage;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("store_open_time")
        @Expose
        private String storeOpenTime;
        @SerializedName("store_close_time")
        @Expose
        private String storeCloseTime;
        @SerializedName("store_type")
        @Expose
        private Object storeType;
        @SerializedName("VehicleID")
        @Expose
        private Object vehicleID;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("working_days")
        @Expose
        private String workingDays;
        @SerializedName("AverageRate")
        @Expose
        private int averageRate;
        @SerializedName("likecount")
        @Expose
        private Integer likecount;
        @SerializedName("followcount")
        @Expose
        private Integer followcount;
        @SerializedName("Role")
        @Expose
        private String Role;

        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            Role = role;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
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

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
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

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
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

        public String getStoreOpenTime() {
            return storeOpenTime;
        }

        public void setStoreOpenTime(String storeOpenTime) {
            this.storeOpenTime = storeOpenTime;
        }

        public String getStoreCloseTime() {
            return storeCloseTime;
        }

        public void setStoreCloseTime(String storeCloseTime) {
            this.storeCloseTime = storeCloseTime;
        }

        public Object getStoreType() {
            return storeType;
        }

        public void setStoreType(Object storeType) {
            this.storeType = storeType;
        }

        public Object getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(Object vehicleID) {
            this.vehicleID = vehicleID;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(String workingDays) {
            this.workingDays = workingDays;
        }

        public int getAverageRate() {
            return averageRate;
        }

        public void setAverageRate(int averageRate) {
            this.averageRate = averageRate;
        }

        public Integer getLikecount() {
            return likecount;
        }

        public void setLikecount(Integer likecount) {
            this.likecount = likecount;
        }

        public Integer getFollowcount() {
            return followcount;
        }

        public void setFollowcount(Integer followcount) {
            this.followcount = followcount;
        }

    }
}
