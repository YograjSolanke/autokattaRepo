package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 27/3/17.
 */

public class GetStatesResponse {



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
