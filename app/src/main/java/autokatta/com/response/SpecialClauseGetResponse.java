package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 25/3/17.
 */

public class SpecialClauseGetResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("clause_id")
        @Expose
        private String clauseId;
        @SerializedName("clause")
        @Expose
        private String clause;

        public String getClauseId() {
            return clauseId;
        }

        public void setClauseId(String clauseId) {
            this.clauseId = clauseId;
        }

        public String getClause() {
            return clause;
        }

        public void setClause(String clause) {
            this.clause = clause;
        }

    }
}
