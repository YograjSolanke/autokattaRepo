package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-004 on 23/3/17.
 */

public class LoanMelaCreateResponse {
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

        @SerializedName("Loan_ID")
        @Expose
        private Integer loanID;

        public Integer getLoanID() {
            return loanID;
        }

        public void setLoanID(Integer loanID) {
            this.loanID = loanID;
        }

    }
}