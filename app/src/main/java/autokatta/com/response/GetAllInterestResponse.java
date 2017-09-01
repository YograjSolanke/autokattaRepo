package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 1/9/17.
 */

public class GetAllInterestResponse {
    @SerializedName("Success")
    @Expose
    private List<Success> success = null;
    @SerializedName("Error")
    @Expose
    private Object error;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("InterestID")
        @Expose
        private Integer interestID;
        @SerializedName("Title")
        @Expose
        private String title;

        public Integer getInterestID() {
            return interestID;
        }

        public void setInterestID(Integer interestID) {
            this.interestID = interestID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
