package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 5/4/17.
 */

public class BrowseStoreResponse {

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


        public boolean isVisibility() {
            return visibility;
        }

        public void setVisibility(boolean visibility) {
            this.visibility = visibility;
        }

        private boolean visibility;

        @SerializedName("store_id")
        @Expose
        private Integer storeId;
        @SerializedName("contact_no")
        @Expose
        private String contactNo;
        @SerializedName("store_name")
        @Expose
        private String storeName;
        @SerializedName("store_image")
        @Expose
        private String storeImage;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("store_type")
        @Expose
        private String storeType;
        @SerializedName("working_days")
        @Expose
        private String workingDays;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("store_open_time")
        @Expose
        private String storeOpenTime;
        @SerializedName("store_close_time")
        @Expose
        private String storeCloseTime;
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
        private Double rating;
        @SerializedName("productcount")
        @Expose
        private Integer productcount;
        @SerializedName("servicecount")
        @Expose
        private Integer servicecount;
        @SerializedName("vehiclecount")
        @Expose
        private Integer vehiclecount;

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getWorkingDays() {
            return workingDays;
        }

        public void setWorkingDays(String workingDays) {
            this.workingDays = workingDays;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
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

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
    }

        public Integer getProductcount() {
            return productcount;
        }

        public void setProductcount(Integer productcount) {
            this.productcount = productcount;
        }

        public Integer getServicecount() {
            return servicecount;
        }

        public void setServicecount(Integer servicecount) {
            this.servicecount = servicecount;
        }

        public Integer getVehiclecount() {
            return vehiclecount;
        }

        public void setVehiclecount(Integer vehiclecount) {
            this.vehiclecount = vehiclecount;
        }

    }
}
