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

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("product_type")
        @Expose
        private String productType;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("product_details")
        @Expose
        private String productDetails;
        @SerializedName("product_tags")
        @Expose
        private String productTags;
        @SerializedName("brandtags")
        @Expose
        private String brandtags;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("price_rating")
        @Expose
        private String priceRating;
        @SerializedName("quality_rating")
        @Expose
        private String qualityRating;
        @SerializedName("stock_rating")
        @Expose
        private String stockRating;
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
        @SerializedName("productlikecount")
        @Expose
        private String productlikecount;
        @SerializedName("productlikestatus")
        @Expose
        private String productlikestatus;
        @SerializedName("prate")
        @Expose
        private String prate;
        @SerializedName("prate1")
        @Expose
        private String prate1;
        @SerializedName("prate2")
        @Expose
        private String prate2;
        @SerializedName("prate3")
        @Expose
        private String prate3;
        @SerializedName("storeOwner")
        @Expose
        private String storeOwner;
        @SerializedName("productrating")
        @Expose
        private String productrating;
        @SerializedName("productTagNames")
        @Expose
        private String productTagNames;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
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

        public String getProductDetails() {
            return productDetails;
        }

        public String getStoreOwner() {
            return storeOwner;
        }

        public void setStoreOwner(String storeOwner) {
            this.storeOwner = storeOwner;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
        }

        public String getProductTags() {
            return productTags;
        }

        public void setProductTags(String productTags) {
            this.productTags = productTags;
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

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getPriceRating() {
            return priceRating;
        }

        public void setPriceRating(String priceRating) {
            this.priceRating = priceRating;
        }

        public String getQualityRating() {
            return qualityRating;
        }

        public void setQualityRating(String qualityRating) {
            this.qualityRating = qualityRating;
        }

        public String getStockRating() {
            return stockRating;
        }

        public void setStockRating(String stockRating) {
            this.stockRating = stockRating;
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

        public String getProductlikecount() {
            return productlikecount;
        }

        public void setProductlikecount(String productlikecount) {
            this.productlikecount = productlikecount;
        }

        public String getProductlikestatus() {
            return productlikestatus;
        }

        public void setProductlikestatus(String productlikestatus) {
            this.productlikestatus = productlikestatus;
        }

        public String getPrate() {
            return prate;
        }

        public void setPrate(String prate) {
            this.prate = prate;
        }

        public String getPrate1() {
            return prate1;
        }

        public void setPrate1(String prate1) {
            this.prate1 = prate1;
        }

        public String getPrate2() {
            return prate2;
        }

        public void setPrate2(String prate2) {
            this.prate2 = prate2;
        }

        public String getPrate3() {
            return prate3;
        }

        public void setPrate3(String prate3) {
            this.prate3 = prate3;
        }

        public String getProductrating() {
            return productrating;
        }

        public void setProductrating(String productrating) {
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
