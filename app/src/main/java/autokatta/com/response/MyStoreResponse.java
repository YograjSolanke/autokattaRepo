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
        private String id;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("name")
        @Expose
        private String name;
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
        @SerializedName("working_days")
        @Expose
        private String workingDays;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("coverImage")
        @Expose
        private String coverImage;
        @SerializedName("rating")
        @Expose
        private Object rating;
        @SerializedName("likecount")
        @Expose
        private String likecount;
        @SerializedName("followcount")
        @Expose
        private String followcount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(String workingDays) {
            this.workingDays = workingDays;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public Object getRating() {
            return rating;
        }

        public void setRating(Object rating) {
            this.rating = rating;
        }

        public String getLikecount() {
            return likecount;
        }

        public void setLikecount(String likecount) {
            this.likecount = likecount;
        }

        public String getFollowcount() {
            return followcount;
        }

        public void setFollowcount(String followcount) {
            this.followcount = followcount;
        }
    }
}
