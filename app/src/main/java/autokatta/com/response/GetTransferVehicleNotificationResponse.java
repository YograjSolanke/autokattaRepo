package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 30/10/17.
 */

public class GetTransferVehicleNotificationResponse {

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

        @SerializedName("TransferID")
        @Expose
        private Integer transferID;
        @SerializedName("NewOwnerName")
        @Expose
        private String newOwnerName;
        @SerializedName("TransaferDate")
        @Expose
        private String transaferDate;
        @SerializedName("TransferReason")
        @Expose
        private String transferReason;
        @SerializedName("TransferStatus")
        @Expose
        private String transferStatus;
        @SerializedName("MyContact")
        @Expose
        private String myContact;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("FullAddress")
        @Expose
        private String fullAddress;
        @SerializedName("CustomerContact")
        @Expose
        private String customerContact;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("VehicleID")
        @Expose
        private Integer vehicleID;
        @SerializedName("ActionDate")
        @Expose
        private String actionDate;
        @SerializedName("VehicleName")
        @Expose
        private String vehicleName;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("OldOwnerName")
        @Expose
        private String oldOwnerName;

        public Integer getTransferID() {
            return transferID;
        }

        public void setTransferID(Integer transferID) {
            this.transferID = transferID;
        }

        public String getNewOwnerName() {
            return newOwnerName;
        }

        public void setNewOwnerName(String newOwnerName) {
            this.newOwnerName = newOwnerName;
        }

        public String getTransaferDate() {
            return transaferDate;
        }

        public void setTransaferDate(String transaferDate) {
            this.transaferDate = transaferDate;
        }

        public String getTransferReason() {
            return transferReason;
        }

        public void setTransferReason(String transferReason) {
            this.transferReason = transferReason;
        }

        public String getTransferStatus() {
            return transferStatus;
        }

        public void setTransferStatus(String transferStatus) {
            this.transferStatus = transferStatus;
        }

        public String getMyContact() {
            return myContact;
        }

        public void setMyContact(String myContact) {
            this.myContact = myContact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getCustomerContact() {
            return customerContact;
        }

        public void setCustomerContact(String customerContact) {
            this.customerContact = customerContact;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(Integer vehicleID) {
            this.vehicleID = vehicleID;
        }

        public String getActionDate() {
            return actionDate;
        }

        public void setActionDate(String actionDate) {
            this.actionDate = actionDate;
        }

        public String getVehicleName() {
            return vehicleName;
        }

        public void setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOldOwnerName() {
            return oldOwnerName;
        }

        public void setOldOwnerName(String oldOwnerName) {
            this.oldOwnerName = oldOwnerName;
        }

    }
}
