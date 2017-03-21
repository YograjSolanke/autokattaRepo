package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 18/3/17.
 */

public class IndustryResponse {

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

        @SerializedName("indus_id")
        @Expose
        private String indusId;
        @SerializedName("indus_name")
        @Expose
        private String indusName;

        public String getIndusId() {
            return indusId;
        }

        public void setIndusId(String indusId) {
            this.indusId = indusId;
        }

        public String getIndusName() {
            return indusName;
        }

        public void setIndusName(String indusName) {
            this.indusName = indusName;
        }
    }
}
