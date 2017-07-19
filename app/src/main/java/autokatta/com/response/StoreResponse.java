package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreResponse {

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
        private int id;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("store_image")
        @Expose
        private String storeImage;
        @SerializedName("store_type")
        @Expose
        private String storeType;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("store_open_time")
        @Expose
        private String storeOpenTime;
        @SerializedName("store_close_time")
        @Expose
        private String storeCloseTime;
        @SerializedName("timing")
        @Expose
        private String timing;
        @SerializedName("working_days")
        @Expose
        private String workingDays;
        @SerializedName("storeDescription")
        @Expose
        private String storeDescription;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("coverImage")
        @Expose
        private String coverImage;
        @SerializedName("likecount")
        @Expose
        private int likecount;
        @SerializedName("followcount")
        @Expose
        private int followcount;
        @SerializedName("likestatus")
        @Expose
        private String likestatus;
        @SerializedName("followstatus")
        @Expose
        private String followstatus;
        @SerializedName("rating")
        @Expose
        private int rating;
        @SerializedName("rate1")
        @Expose
        private int rate1;
        @SerializedName("rate2")
        @Expose
        private int rate2;
        @SerializedName("rate3")
        @Expose
        private int rate3;
        @SerializedName("rate4")
        @Expose
        private int rate4;

        @SerializedName("rate5")
        @Expose
        private int rate5;
        @SerializedName("rate")
        @Expose
        private int rate;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("dealingWith")
        @Expose
        private String dealingWith;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStoreImage() {
            return storeImage;
        }

        public void setStoreImage(String storeImage) {
            this.storeImage = storeImage;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
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

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public String getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(String workingDays) {
            this.workingDays = workingDays;
        }

        public String getStoreDescription() {
            return storeDescription;
        }

        public void setStoreDescription(String storeDescription) {
            this.storeDescription = storeDescription;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public int getLikecount() {
            return likecount;
        }

        public void setLikecount(int likecount) {
            this.likecount = likecount;
        }

        public int getFollowcount() {
            return followcount;
        }

        public void setFollowcount(int followcount) {
            this.followcount = followcount;
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

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getRate1() {
            return rate1;
        }

        public void setRate1(int rate1) {
            this.rate1 = rate1;
        }

        public int getRate2() {
            return rate2;
        }

        public void setRate2(int rate2) {
            this.rate2 = rate2;
        }

        public int getRate3() {
            return rate3;
        }

        public void setRate3(int rate3) {
            this.rate3 = rate3;
        }

        public int getRate4() {
            return rate4;
        }

        public int getRate5() {
            return rate5;
        }

        public void setRate4(int rate4) {
            this.rate4 = rate4;
        }

        public void setRate5(int rate5) {
            this.rate4 = rate5;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
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

        public String getDealingWith() {
            return dealingWith;
        }

        public void setDealingWith(String dealingWith) {
            this.dealingWith = dealingWith;
        }

    }
}
