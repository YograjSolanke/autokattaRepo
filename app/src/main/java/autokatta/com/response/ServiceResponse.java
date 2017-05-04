package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 27/4/17.
 */

public class ServiceResponse {

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
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("details")
        @Expose
        private String details;
        @SerializedName("tags")
        @Expose
        private String tags;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("brandtags")
        @Expose
        private String brandtags;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("storeName")
        @Expose
        private String storeName;
        @SerializedName("storeContact")
        @Expose
        private String storeContact;
        @SerializedName("storeWebsite")
        @Expose
        private String storeWebsite;
        @SerializedName("storeRating")
        @Expose
        private String storeRating;
        @SerializedName("servicelikecount")
        @Expose
        private String servicelikecount;
        @SerializedName("servicelikestatus")
        @Expose
        private String servicelikestatus;
        @SerializedName("srate")
        @Expose
        private String srate;
        @SerializedName("srate1")
        @Expose
        private String srate1;
        @SerializedName("srate2")
        @Expose
        private String srate2;
        @SerializedName("srate3")
        @Expose
        private String srate3;
        @SerializedName("servicerating")
        @Expose
        private String servicerating;
        @SerializedName("servicetags")
        @Expose
        private String servicetags;
        @SerializedName("storeOwner")
        @Expose
        private String storeOwner;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getBrandtags() {
            return brandtags;
        }

        public void setBrandtags(String brandtags) {
            this.brandtags = brandtags;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreContact() {
            return storeContact;
        }

        public void setStoreContact(String storeContact) {
            this.storeContact = storeContact;
        }

        public String getStoreWebsite() {
            return storeWebsite;
        }

        public void setStoreWebsite(String storeWebsite) {
            this.storeWebsite = storeWebsite;
        }

        public String getStoreRating() {
            return storeRating;
        }

        public void setStoreRating(String storeRating) {
            this.storeRating = storeRating;
        }

        public String getServicelikecount() {
            return servicelikecount;
        }

        public void setServicelikecount(String servicelikecount) {
            this.servicelikecount = servicelikecount;
        }

        public String getServicelikestatus() {
            return servicelikestatus;
        }

        public void setServicelikestatus(String servicelikestatus) {
            this.servicelikestatus = servicelikestatus;
        }

        public String getSrate() {
            return srate;
        }

        public void setSrate(String srate) {
            this.srate = srate;
        }

        public String getSrate1() {
            return srate1;
        }

        public void setSrate1(String srate1) {
            this.srate1 = srate1;
        }

        public String getSrate2() {
            return srate2;
        }

        public void setSrate2(String srate2) {
            this.srate2 = srate2;
        }

        public String getSrate3() {
            return srate3;
        }

        public void setSrate3(String srate3) {
            this.srate3 = srate3;
        }

        public String getServicerating() {
            return servicerating;
        }

        public void setServicerating(String servicerating) {
            this.servicerating = servicerating;
        }

        public String getServicetags() {
            return servicetags;
        }

        public void setServicetags(String servicetags) {
            this.servicetags = servicetags;
        }

        public String getStoreOwner() {
            return storeOwner;
        }

        public void setStoreOwner(String storeOwner) {
            this.storeOwner = storeOwner;
        }
    }
}
