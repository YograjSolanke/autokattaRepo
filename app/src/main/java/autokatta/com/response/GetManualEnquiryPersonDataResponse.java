package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 12/9/17.
 */

public class GetManualEnquiryPersonDataResponse {

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

        @SerializedName("created_date_1")
        @Expose
        private String createdDate1;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Contact")
        @Expose
        private String contact;
        @SerializedName("EnquiryStatus")
        @Expose
        private String enquiryStatus;
        @SerializedName("FullAddress")
        @Expose
        private String fullAddress;
        @SerializedName("InventoryType")
        @Expose
        private String inventoryType;
        @SerializedName("CustomerName")
        @Expose
        private String customerName;
        @SerializedName("Discussion")
        @Expose
        private String discussion;
        @SerializedName("MyContact")
        @Expose
        private String myContact;
        @SerializedName("NextFollowUpDate_1")
        @Expose
        private String nextFollowUpDate1;
        @SerializedName("NextFollowUpDate")
        @Expose
        private String nextFollowUpDate;

        public String getCreatedDate1() {
            return createdDate1;
        }

        public void setCreatedDate1(String createdDate1) {
            this.createdDate1 = createdDate1;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getEnquiryStatus() {
            return enquiryStatus;
        }

        public void setEnquiryStatus(String enquiryStatus) {
            this.enquiryStatus = enquiryStatus;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getInventoryType() {
            return inventoryType;
        }

        public void setInventoryType(String inventoryType) {
            this.inventoryType = inventoryType;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getDiscussion() {
            return discussion;
        }

        public void setDiscussion(String discussion) {
            this.discussion = discussion;
        }

        public String getMyContact() {
            return myContact;
        }

        public void setMyContact(String myContact) {
            this.myContact = myContact;
        }

        public String getNextFollowUpDate1() {
            return nextFollowUpDate1;
        }

        public void setNextFollowUpDate1(String nextFollowUpDate1) {
            this.nextFollowUpDate1 = nextFollowUpDate1;
        }

        public String getNextFollowUpDate() {
            return nextFollowUpDate;
        }

        public void setNextFollowUpDate(String nextFollowUpDate) {
            this.nextFollowUpDate = nextFollowUpDate;
        }

    }
}
