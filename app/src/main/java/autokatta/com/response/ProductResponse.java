package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 27/4/17.
 */

public class ProductResponse {
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
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("price_rating")
        @Expose
        private Integer priceRating;
        @SerializedName("product_details")
        @Expose
        private String productDetails;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_type")
        @Expose
        private String productType;
        @SerializedName("quality_rating")
        @Expose
        private Integer qualityRating;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("stock_rating")
        @Expose
        private Integer stockRating;
        @SerializedName("store_id")
        @Expose
        private Integer storeId;
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
        private Integer storeRating;
        @SerializedName("productlikecount")
        @Expose
        private Integer productlikecount;
        @SerializedName("productlikestatus")
        @Expose
        private String productlikestatus;
        @SerializedName("prate")
        @Expose
        private Integer prate;
        @SerializedName("prate1")
        @Expose
        private Integer prate1;
        @SerializedName("prate2")
        @Expose
        private Integer prate2;
        @SerializedName("prate3")
        @Expose
        private Integer prate3;
        @SerializedName("productrating")
        @Expose
        private Integer productrating;
        @SerializedName("productTagNames")
        @Expose
        private String productTagNames;

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

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getPriceRating() {
            return priceRating;
        }

        public void setPriceRating(Integer priceRating) {
            this.priceRating = priceRating;
        }

        public String getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public Integer getQualityRating() {
            return qualityRating;
        }

        public void setQualityRating(Integer qualityRating) {
            this.qualityRating = qualityRating;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getStockRating() {
            return stockRating;
        }

        public void setStockRating(Integer stockRating) {
            this.stockRating = stockRating;
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

        public Integer getStoreRating() {
            return storeRating;
        }

        public void setStoreRating(Integer storeRating) {
            this.storeRating = storeRating;
        }

        public Integer getProductlikecount() {
            return productlikecount;
        }

        public void setProductlikecount(Integer productlikecount) {
            this.productlikecount = productlikecount;
        }

        public String getProductlikestatus() {
            return productlikestatus;
        }

        public void setProductlikestatus(String productlikestatus) {
            this.productlikestatus = productlikestatus;
        }

        public Integer getPrate() {
            return prate;
        }

        public void setPrate(Integer prate) {
            this.prate = prate;
        }

        public Integer getPrate1() {
            return prate1;
        }

        public void setPrate1(Integer prate1) {
            this.prate1 = prate1;
        }

        public Integer getPrate2() {
            return prate2;
        }

        public void setPrate2(Integer prate2) {
            this.prate2 = prate2;
        }

        public Integer getPrate3() {
            return prate3;
        }

        public void setPrate3(Integer prate3) {
            this.prate3 = prate3;
        }

        public Integer getProductrating() {
            return productrating;
        }

        public void setProductrating(Integer productrating) {
            this.productrating = productrating;
        }

        public String getProductTagNames() {
            return productTagNames;
        }

        public void setProductTagNames(String productTagNames) {
            this.productTagNames = productTagNames;
    }

    }
}
