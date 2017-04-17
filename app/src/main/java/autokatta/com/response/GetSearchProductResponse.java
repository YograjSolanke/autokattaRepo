package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 17/4/17.
 */

public class GetSearchProductResponse {

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
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("product_type")
        @Expose
        private String productType;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("brandtags")
        @Expose
        private String brandtags;
        @SerializedName("product_details")
        @Expose
        private String productDetails;
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
        @SerializedName("productrating")
        @Expose
        private String productrating;
        @SerializedName("product_tags")
        @Expose
        private String productTags;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
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

        public String getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
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

        public String getProductTags() {
            return productTags;
        }

        public void setProductTags(String productTags) {
            this.productTags = productTags;
        }
    }
}
