package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 16/3/17.
 */

public class LoginResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;
    @SerializedName("Error")
    @Expose
    private List<String> error = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("Reg_ID")
        @Expose
        private int regID;

        public int getRegID() {
            return regID;
        }

        public void setRegID(int regID) {
            this.regID = regID;
        }
    }
}
