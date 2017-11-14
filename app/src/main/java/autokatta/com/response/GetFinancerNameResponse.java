package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 14/11/17.
 */

public class GetFinancerNameResponse {

    @SerializedName("Success")
    @Expose
    private List<GetFinancerNameResponse.Success> success = null;
    @SerializedName("Error")
    @Expose
    private Object error;

    public List<GetFinancerNameResponse.Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<GetFinancerNameResponse.Success> success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("FinancierID")
        @Expose
        private Integer FinancierID;
        @SerializedName("FinancierName")
        @Expose
        private String FinancierName;

        public Integer getFinancierID() {
            return FinancierID;
        }

        public void setFinancierID(Integer sourceOfEnquiryID) {
            this.FinancierID = sourceOfEnquiryID;
        }

        public String getFinancierName() {
            return FinancierName;
        }

        public void setFinancierName(String source) {
            this.FinancierName = source;
        }

    }
}
