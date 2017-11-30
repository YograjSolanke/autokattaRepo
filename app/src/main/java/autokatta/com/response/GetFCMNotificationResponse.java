package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 30/11/17.
 */

public class GetFCMNotificationResponse {

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

        @SerializedName("FCMNotification")
        @Expose
        private List<FCMNotification> fCMNotification = null;

        public List<FCMNotification> getFCMNotification() {
            return fCMNotification;
        }

        public void setFCMNotification(List<FCMNotification> fCMNotification) {
            this.fCMNotification = fCMNotification;
        }


        public class FCMNotification {

            @SerializedName("FCMNotificationID")
            @Expose
            private Integer fCMNotificationID;
            @SerializedName("ReceiverContact")
            @Expose
            private String receiverContact;
            @SerializedName("Message")
            @Expose
            private String message;
            @SerializedName("Layout")
            @Expose
            private String layout;
            @SerializedName("SenderContact")
            @Expose
            private String senderContact;
            @SerializedName("DeviceToken")
            @Expose
            private String deviceToken;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("UserName")
            @Expose
            private String userName;
            @SerializedName("StoreID")
            @Expose
            private Integer storeID;
            @SerializedName("GroupID")
            @Expose
            private Integer groupID;
            @SerializedName("VehicleID")
            @Expose
            private Integer vehicleID;
            @SerializedName("ProductID")
            @Expose
            private Integer productID;
            @SerializedName("ServiceID")
            @Expose
            private Integer serviceID;
            @SerializedName("StatusID")
            @Expose
            private Integer statusID;
            @SerializedName("SearchID")
            @Expose
            private Integer searchID;

            public Integer getFCMNotificationID() {
                return fCMNotificationID;
            }

            public void setFCMNotificationID(Integer fCMNotificationID) {
                this.fCMNotificationID = fCMNotificationID;
            }

            public String getReceiverContact() {
                return receiverContact;
            }

            public void setReceiverContact(String receiverContact) {
                this.receiverContact = receiverContact;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getLayout() {
                return layout;
            }

            public void setLayout(String layout) {
                this.layout = layout;
            }

            public String getSenderContact() {
                return senderContact;
            }

            public void setSenderContact(String senderContact) {
                this.senderContact = senderContact;
            }

            public String getDeviceToken() {
                return deviceToken;
            }

            public void setDeviceToken(String deviceToken) {
                this.deviceToken = deviceToken;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public Integer getStoreID() {
                return storeID;
            }

            public void setStoreID(Integer storeID) {
                this.storeID = storeID;
            }

            public Integer getGroupID() {
                return groupID;
            }

            public void setGroupID(Integer groupID) {
                this.groupID = groupID;
            }

            public Integer getVehicleID() {
                return vehicleID;
            }

            public void setVehicleID(Integer vehicleID) {
                this.vehicleID = vehicleID;
            }

            public Integer getProductID() {
                return productID;
            }

            public void setProductID(Integer productID) {
                this.productID = productID;
            }

            public Integer getServiceID() {
                return serviceID;
            }

            public void setServiceID(Integer serviceID) {
                this.serviceID = serviceID;
            }

            public Integer getStatusID() {
                return statusID;
            }

            public void setStatusID(Integer statusID) {
                this.statusID = statusID;
            }

            public Integer getSearchID() {
                return searchID;
            }

            public void setSearchID(Integer searchID) {
                this.searchID = searchID;
            }
        }
    }

}
