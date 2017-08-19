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

        @SerializedName("brandtags")
        @Expose
        private String brandtags;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("details")
        @Expose
        private String details;
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private int price;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("store_id")
        @Expose
        private int storeId;
        @SerializedName("tags")
        @Expose
        private String tags;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("storeName")
        @Expose
        private String storeName;
        @SerializedName("storeContact")
        @Expose
        private String storeContact;
        @SerializedName("storeWebsite")
        @Expose
        private String storeWebsite;
        @SerializedName("storeOwner")
        @Expose
        private String storeOwner;
        @SerializedName("storeRating")
        @Expose
        private int storeRating;
        @SerializedName("servicelikecount")
        @Expose
        private int servicelikecount;
        @SerializedName("servicelikestatus")
        @Expose
        private String servicelikestatus;
        @SerializedName("srate")
        @Expose
        private int srate;
        @SerializedName("srate1")
        @Expose
        private int srate1;
        @SerializedName("srate2")
        @Expose
        private int srate2;
        @SerializedName("srate3")
        @Expose
        private int srate3;
        @SerializedName("servicerating")
        @Expose
        private int servicerating;
        @SerializedName("servicetags")
        @Expose
        private String servicetags;

        public String getBrandtags() {
            return brandtags;
        }

        public void setBrandtags(String brandtags) {
            this.brandtags = brandtags;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getStoreOwner() {
            return storeOwner;
        }

        public void setStoreOwner(String storeOwner) {
            this.storeOwner = storeOwner;
        }

        public int getStoreRating() {
            return storeRating;
        }

        public void setStoreRating(int storeRating) {
            this.storeRating = storeRating;
        }

        public int getServicelikecount() {
            return servicelikecount;
        }

        public void setServicelikecount(int servicelikecount) {
            this.servicelikecount = servicelikecount;
        }

        public String getServicelikestatus() {
            return servicelikestatus;
        }

        public void setServicelikestatus(String servicelikestatus) {
            this.servicelikestatus = servicelikestatus;
        }

        public int getSrate() {
            return srate;
        }

        public void setSrate(int srate) {
            this.srate = srate;
        }

        public int getSrate1() {
            return srate1;
        }

        public void setSrate1(int srate1) {
            this.srate1 = srate1;
        }

        public int getSrate2() {
            return srate2;
        }

        public void setSrate2(int srate2) {
            this.srate2 = srate2;
        }

        public int getSrate3() {
            return srate3;
        }

        public void setSrate3(int srate3) {
            this.srate3 = srate3;
        }

        public int getServicerating() {
            return servicerating;
        }

        public void setServicerating(int servicerating) {
            this.servicerating = servicerating;
        }

        public String getServicetags() {
            return servicetags;
        }

        public void setServicetags(String servicetags) {
            this.servicetags = servicetags;
        }

        }
    }
