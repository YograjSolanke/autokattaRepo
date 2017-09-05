package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 7/4/17.
 */

public class BroadcastSendResponse {


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

        @SerializedName("receiver")
        @Expose
        private String receiver;
        @SerializedName("receivername")
        @Expose
        private String receivername;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("date")
        @Expose
        private String date;

        @SerializedName("profileImage")
        @Expose
        private String profileImage;


        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getReceivername() {
            return receivername;
        }

        public void setReceivername(String receivername) {
            this.receivername = receivername;
        }

    }
}
