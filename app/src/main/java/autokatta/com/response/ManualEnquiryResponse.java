package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 5/5/17.
 */

public class ManualEnquiryResponse {
    @SerializedName("Success")
    @Expose
    private List<Success> success = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    @SerializedName("Error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public class Success {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("myContact")
        @Expose
        private String myContact;
        @SerializedName("custName")
        @Expose
        private String custName;
        @SerializedName("custContact")
        @Expose
        private String custContact;
        @SerializedName("custAddress")
        @Expose
        private String custAddress;
        @SerializedName("custFullAddress")
        @Expose
        private String custFullAddress;
        @SerializedName("custInventoryType")
        @Expose
        private String custInventoryType;
        @SerializedName("discussion")
        @Expose
        private String discussion;
        @SerializedName("nextFollowupDate")
        @Expose
        private String nextFollowupDate;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMyContact() {
            return myContact;
        }

        public void setMyContact(String myContact) {
            this.myContact = myContact;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getCustContact() {
            return custContact;
        }

        public void setCustContact(String custContact) {
            this.custContact = custContact;
        }

        public String getCustAddress() {
            return custAddress;
        }

        public void setCustAddress(String custAddress) {
            this.custAddress = custAddress;
        }

        public String getCustFullAddress() {
            return custFullAddress;
        }

        public void setCustFullAddress(String custFullAddress) {
            this.custFullAddress = custFullAddress;
        }

        public String getCustInventoryType() {
            return custInventoryType;
        }

        public void setCustInventoryType(String custInventoryType) {
            this.custInventoryType = custInventoryType;
        }

        public String getDiscussion() {
            return discussion;
        }

        public void setDiscussion(String discussion) {
            this.discussion = discussion;
        }

        public String getNextFollowupDate() {
            return nextFollowupDate;
        }

        public void setNextFollowupDate(String nextFollowupDate) {
            this.nextFollowupDate = nextFollowupDate;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }
    }
}
