package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-004 on 4/5/17.
 */

public class EnquiryCountResponse {

    @SerializedName("Success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("Enquiry_count")
        @Expose
        private String enquiryCount;

        public String getEnquiryCount() {
            return enquiryCount;
        }

        public void setEnquiryCount(String enquiryCount) {
            this.enquiryCount = enquiryCount;
        }

    }
}
