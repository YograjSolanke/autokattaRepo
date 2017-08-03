package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 21/3/17.
 */

public class SearchStoreResponse {

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

        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("store_id")
        @Expose
        private Integer storeId;
        @SerializedName("store_name")
        @Expose
        private String storeName;
        @SerializedName("store_image")
        @Expose
        private String storeImage;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("open_time")
        @Expose
        private String openTime;
        @SerializedName("close_time")
        @Expose
        private String closeTime;
        @SerializedName("ratings")
        @Expose
        private String ratings;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("store_type")
        @Expose
        private String storeType;
        @SerializedName("working_days")
        @Expose
        private Integer workingDays;
        @SerializedName("likestatus")
        @Expose
        private String likestatus;
        @SerializedName("followstatus")
        @Expose
        private String followstatus;
        @SerializedName("likecount")
        @Expose
        private Integer likecount;
        @SerializedName("followcount")
        @Expose
        private Integer followcount;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("km")
        @Expose
        private String km;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreImage() {
            return storeImage;
        }

        public void setStoreImage(String storeImage) {
            this.storeImage = storeImage;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getCloseTime() {
            return closeTime;
        }

        public void setCloseTime(String closeTime) {
            this.closeTime = closeTime;
        }

        public String getRatings() {
            return ratings;
        }

        public void setRatings(String ratings) {
            this.ratings = ratings;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public Integer getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(Integer workingDays) {
            this.workingDays = workingDays;
        }

        public String getLikestatus() {
            return likestatus;
        }

        public void setLikestatus(String likestatus) {
            this.likestatus = likestatus;
        }

        public String getFollowstatus() {
            return followstatus;
        }

        public void setFollowstatus(String followstatus) {
            this.followstatus = followstatus;
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

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

    }
}
