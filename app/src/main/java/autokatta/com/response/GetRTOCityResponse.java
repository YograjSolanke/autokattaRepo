package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 23/3/17.
 */

public class GetRTOCityResponse {
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

        @SerializedName("rtoCityId")
        @Expose
        private String rtoCityId;
        @SerializedName("rtoCode")
        @Expose
        private String rtoCode;
        @SerializedName("rtoCityName")
        @Expose
        private String rtoCityName;

        public String getRtoCityId() {
            return rtoCityId;
        }

        public void setRtoCityId(String rtoCityId) {
            this.rtoCityId = rtoCityId;
        }

        public String getRtoCode() {
            return rtoCode;
        }

        public void setRtoCode(String rtoCode) {
            this.rtoCode = rtoCode;
        }

        public String getRtoCityName() {
            return rtoCityName;
        }

        public void setRtoCityName(String rtoCityName) {
            this.rtoCityName = rtoCityName;
        }
    }
}
