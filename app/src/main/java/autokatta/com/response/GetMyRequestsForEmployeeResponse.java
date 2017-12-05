package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 4/12/17.
 */

public class GetMyRequestsForEmployeeResponse {

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

        @SerializedName("StoreEmplyeeID")
        @Expose
        private Integer storeEmplyeeID;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("ContactNo")
        @Expose
        private String contactNo;
        @SerializedName("Designation")
        @Expose
        private String designation;
        @SerializedName("StoreID")
        @Expose
        private Integer storeID;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Permission")
        @Expose
        private String permission;
        @SerializedName("StoreContactNo")
        @Expose
        private String storeContactNo;
        @SerializedName("DeleteStatus")
        @Expose
        private String deleteStatus;
        @SerializedName("Date_1")
        @Expose
        private Object date1;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("SenderName")
        @Expose
        private String senderName;
        @SerializedName("SenderPicture")
        @Expose
        private String senderPicture;
        @SerializedName("StoreName")
        @Expose
        private String storeName;

        public Integer getStoreEmplyeeID() {
            return storeEmplyeeID;
        }

        public void setStoreEmplyeeID(Integer storeEmplyeeID) {
            this.storeEmplyeeID = storeEmplyeeID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public Integer getStoreID() {
            return storeID;
        }

        public void setStoreID(Integer storeID) {
            this.storeID = storeID;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getStoreContactNo() {
            return storeContactNo;
        }

        public void setStoreContactNo(String storeContactNo) {
            this.storeContactNo = storeContactNo;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public Object getDate1() {
            return date1;
        }

        public void setDate1(Object date1) {
            this.date1 = date1;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSenderPicture() {
            return senderPicture;
        }

        public void setSenderPicture(String senderPicture) {
            this.senderPicture = senderPicture;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

    }
}
