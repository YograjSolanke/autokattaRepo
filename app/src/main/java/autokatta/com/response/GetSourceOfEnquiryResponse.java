package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 13/11/17.
 */

public class GetSourceOfEnquiryResponse {


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

        @SerializedName("SourceOfEnquiryID")
        @Expose
        private Integer sourceOfEnquiryID;
        @SerializedName("Source")
        @Expose
        private String source;

        public Integer getSourceOfEnquiryID() {
            return sourceOfEnquiryID;
        }

        public void setSourceOfEnquiryID(Integer sourceOfEnquiryID) {
            this.sourceOfEnquiryID = sourceOfEnquiryID;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

    }
}
