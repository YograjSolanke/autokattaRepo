package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 27/3/17.
 */

public class GetStatesResponse {


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

        @SerializedName("stateId")
        @Expose
        private String stateId;
        @SerializedName("stateName")
        @Expose
        private String stateName;

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

    }

}
