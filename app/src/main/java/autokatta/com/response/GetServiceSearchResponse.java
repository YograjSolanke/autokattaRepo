package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 18/4/17.
 */

public class GetServiceSearchResponse {
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

        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("brandtags")
        @Expose
        private String brandtags;
        @SerializedName("details")
        @Expose
        private String details;
        @SerializedName("storecontact")
        @Expose
        private String storecontact;
        @SerializedName("store_name")
        @Expose
        private String storeName;
        @SerializedName("storewebsite")
        @Expose
        private String storewebsite;
        @SerializedName("storerating")
        @Expose
        private String storerating;
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
        @SerializedName("service_tags")
        @Expose
        private String serviceTags;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getBrandtags() {
            return brandtags;
        }

        public void setBrandtags(String brandtags) {
            this.brandtags = brandtags;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getStorecontact() {
            return storecontact;
        }

        public void setStorecontact(String storecontact) {
            this.storecontact = storecontact;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStorewebsite() {
            return storewebsite;
        }

        public void setStorewebsite(String storewebsite) {
            this.storewebsite = storewebsite;
        }

        public String getStorerating() {
            return storerating;
        }

        public void setStorerating(String storerating) {
            this.storerating = storerating;
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

        public String getServiceTags() {
            return serviceTags;
        }

        public void setServiceTags(String serviceTags) {
            this.serviceTags = serviceTags;
        }

    }
}
