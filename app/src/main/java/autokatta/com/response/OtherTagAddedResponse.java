package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-004 on 24/4/17.
 */

public class OtherTagAddedResponse {


    @SerializedName("Success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }


    public class Success {

        @SerializedName("tagID")
        @Expose
        private Integer tagID;

        public Integer getTagID() {
            return tagID;
        }

        public void setTagID(Integer tagID) {
            this.tagID = tagID;
        }

    }
}
