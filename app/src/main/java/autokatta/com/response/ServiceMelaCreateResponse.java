package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-003 on 27/4/17.
 */

public class ServiceMelaCreateResponse {

    @SerializedName("Success")
    @Expose
    private ServiceMelaCreateResponse.Success success;

    public ServiceMelaCreateResponse.Success getSuccess() {
        return success;
    }

    public void setSuccess(ServiceMelaCreateResponse.Success success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("Service_ID")
        @Expose
        private Integer serviceID;

        public Integer getServiceID() {
            return serviceID;
        }

        public void setServiceID(Integer serviceID) {
            this.serviceID = serviceID;
        }

    }
}
