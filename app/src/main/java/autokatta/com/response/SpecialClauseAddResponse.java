package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-004 on 25/3/17.
 */

public class SpecialClauseAddResponse {
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

        @SerializedName("Clause_ID")
        @Expose
        private Integer clauseID;

        public Integer getClauseID() {
            return clauseID;
        }

        public void setClauseID(Integer clauseID) {
            this.clauseID = clauseID;
        }

    }
}
