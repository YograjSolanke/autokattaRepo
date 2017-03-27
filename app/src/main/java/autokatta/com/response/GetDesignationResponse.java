package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 27/3/17.
 */

public class GetDesignationResponse {


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

        @SerializedName("desgId")
        @Expose
        private String desgId;
        @SerializedName("designationName")
        @Expose
        private String designationName;

        public String getDesgId() {
            return desgId;
        }

        public void setDesgId(String desgId) {
            this.desgId = desgId;
        }

        public String getDesignationName() {
            return designationName;
        }

        public void setDesignationName(String designationName) {
            this.designationName = designationName;
        }
    }
}