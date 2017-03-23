package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 23/3/17.
 */

public class GetVehicleImplementsResponse {
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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("implement_name")
        @Expose
        private String implementName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImplementName() {
            return implementName;
        }

        public void setImplementName(String implementName) {
            this.implementName = implementName;
        }
    }
}
