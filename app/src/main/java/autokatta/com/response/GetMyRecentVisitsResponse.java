package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 8/12/17.
 */

public class GetMyRecentVisitsResponse {

    @SerializedName("Success")
    @Expose
    private Success success;
    @SerializedName("Error")
    @Expose
    private Object error;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("MyRecentVisit")
        @Expose
        private List<MyRecentVisit> myRecentVisit = null;

        public List<MyRecentVisit> getMyRecentVisit() {
            return myRecentVisit;
        }

        public void setMyRecentVisit(List<MyRecentVisit> myRecentVisit) {
            this.myRecentVisit = myRecentVisit;
        }


        public class MyRecentVisit {

            @SerializedName("WatchedItemID")
            @Expose
            private Integer watchedItemID;
            @SerializedName("LayOut")
            @Expose
            private String layOut;
            @SerializedName("NewVehicleID")
            @Expose
            private Integer newVehicleID;
            @SerializedName("NewVehicleImage")
            @Expose
            private String newVehicleImage;
            @SerializedName("UploadVehicleID")
            @Expose
            private Integer uploadVehicleID;
            @SerializedName("UploadVehicleImage")
            @Expose
            private String uploadVehicleImage;
            @SerializedName("UploadVehicleTitile")
            @Expose
            private String uploadVehicleTitile;
            @SerializedName("StoreServiceID")
            @Expose
            private Integer storeServiceID;
            @SerializedName("StoreServiceImage")
            @Expose
            private String storeServiceImage;
            @SerializedName("StoreServiceName")
            @Expose
            private String storeServiceName;
            @SerializedName("ProductID")
            @Expose
            private Integer productID;
            @SerializedName("ProductImage")
            @Expose
            private String productImage;
            @SerializedName("ProductName")
            @Expose
            private String productName;
            @SerializedName("StoreID")
            @Expose
            private Integer storeID;
            @SerializedName("StoreImage")
            @Expose
            private String storeImage;
            @SerializedName("StoreName")
            @Expose
            private String storeName;
            @SerializedName("ProfileID")
            @Expose
            private Integer profileID;
            @SerializedName("UserName")
            @Expose
            private String userName;
            @SerializedName("ProfilePicture")
            @Expose
            private String profilePicture;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("Date_1")
            @Expose
            private String date1;
            @SerializedName("Date")
            @Expose
            private String date;

            public Integer getWatchedItemID() {
                return watchedItemID;
            }

            public void setWatchedItemID(Integer watchedItemID) {
                this.watchedItemID = watchedItemID;
            }

            public String getLayOut() {
                return layOut;
            }

            public void setLayOut(String layOut) {
                this.layOut = layOut;
            }

            public Integer getNewVehicleID() {
                return newVehicleID;
            }

            public void setNewVehicleID(Integer newVehicleID) {
                this.newVehicleID = newVehicleID;
            }

            public String getNewVehicleImage() {
                return newVehicleImage;
            }

            public void setNewVehicleImage(String newVehicleImage) {
                this.newVehicleImage = newVehicleImage;
            }

            public Integer getUploadVehicleID() {
                return uploadVehicleID;
            }

            public void setUploadVehicleID(Integer uploadVehicleID) {
                this.uploadVehicleID = uploadVehicleID;
            }

            public String getUploadVehicleImage() {
                return uploadVehicleImage;
            }

            public void setUploadVehicleImage(String uploadVehicleImage) {
                this.uploadVehicleImage = uploadVehicleImage;
            }

            public String getUploadVehicleTitile() {
                return uploadVehicleTitile;
            }

            public void setUploadVehicleTitile(String uploadVehicleTitile) {
                this.uploadVehicleTitile = uploadVehicleTitile;
            }

            public Integer getStoreServiceID() {
                return storeServiceID;
            }

            public void setStoreServiceID(Integer storeServiceID) {
                this.storeServiceID = storeServiceID;
            }

            public String getStoreServiceImage() {
                return storeServiceImage;
            }

            public void setStoreServiceImage(String storeServiceImage) {
                this.storeServiceImage = storeServiceImage;
            }

            public String getStoreServiceName() {
                return storeServiceName;
            }

            public void setStoreServiceName(String storeServiceName) {
                this.storeServiceName = storeServiceName;
            }

            public Integer getProductID() {
                return productID;
            }

            public void setProductID(Integer productID) {
                this.productID = productID;
            }

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public Integer getStoreID() {
                return storeID;
            }

            public void setStoreID(Integer storeID) {
                this.storeID = storeID;
            }

            public String getStoreImage() {
                return storeImage;
            }

            public void setStoreImage(String storeImage) {
                this.storeImage = storeImage;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public Integer getProfileID() {
                return profileID;
            }

            public void setProfileID(Integer profileID) {
                this.profileID = profileID;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getProfilePicture() {
                return profilePicture;
            }

            public void setProfilePicture(String profilePicture) {
                this.profilePicture = profilePicture;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getDate1() {
                return date1;
            }

            public void setDate1(String date1) {
                this.date1 = date1;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }


}
