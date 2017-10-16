package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 16/10/17.
 */

public class GetVehicleInventoryScrapResponse {


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

        @SerializedName("VehicleInsuranceScrapID")
        @Expose
        private Integer vehicleInsuranceScrapID;
        @SerializedName("CustomerName")
        @Expose
        private String customerName;
        @SerializedName("MyContact")
        @Expose
        private String myContact;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Fulladdress")
        @Expose
        private String fulladdress;
        @SerializedName("PurchasePrice")
        @Expose
        private Integer purchasePrice;
        @SerializedName("PurchaseDate")
        @Expose
        private String purchaseDate;
        @SerializedName("VehicleID")
        @Expose
        private Integer vehicleID;

        public Integer getVehicleInsuranceScrapID() {
            return vehicleInsuranceScrapID;
        }

        public void setVehicleInsuranceScrapID(Integer vehicleInsuranceScrapID) {
            this.vehicleInsuranceScrapID = vehicleInsuranceScrapID;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
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

        public String getFulladdress() {
            return fulladdress;
        }

        public void setFulladdress(String fulladdress) {
            this.fulladdress = fulladdress;
        }

        public Integer getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(Integer purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public Integer getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(Integer vehicleID) {
            this.vehicleID = vehicleID;
        }

    }
}
