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


        @SerializedName("OwnerName")
        @Expose
        private String ownerName;

        @SerializedName("id")
        @Expose
        private Integer id;
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
        @SerializedName("brandtags")
        @Expose
        private String brandtags;
        @SerializedName("coverImage")
        @Expose
        private String coverImage;
        @SerializedName("likecount")
        @Expose
        private Integer likecount;
        @SerializedName("followcount")
        @Expose
        private Integer followcount;
        @SerializedName("likestatus")
        @Expose
        private String likestatus;
        @SerializedName("followstatus")
        @Expose
        private String followstatus;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("rate1")
        @Expose
        private Integer rate1;
        @SerializedName("rate2")
        @Expose
        private Integer rate2;
        @SerializedName("rate3")
        @Expose
        private Integer rate3;
        @SerializedName("rate4")
        @Expose
        private Integer rate4;
        @SerializedName("rate5")
        @Expose
        private Integer rate5;
        @SerializedName("rate")
        @Expose
        private Integer rate;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("dealingWith")
        @Expose
        private String dealingWith;


        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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

        public String getBrandtags() {
            return brandtags;
        }

        public void setBrandtags(String brandtags) {
            this.brandtags = brandtags;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
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

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getRate1() {
            return rate1;
        }

        public void setRate1(Integer rate1) {
            this.rate1 = rate1;
        }

        public Integer getRate2() {
            return rate2;
        }

        public void setRate2(Integer rate2) {
            this.rate2 = rate2;
        }

        public Integer getRate3() {
            return rate3;
        }

        public void setRate3(Integer rate3) {
            this.rate3 = rate3;
        }

        public Integer getRate4() {
            return rate4;
        }

        public void setRate4(Integer rate4) {
            this.rate4 = rate4;
        }

        public Integer getRate5() {
            return rate5;
        }

        public void setRate5(Integer rate5) {
            this.rate5 = rate5;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
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
