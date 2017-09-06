package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 7/4/17.
 */

public class BroadcastReceivedResponse {

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

        @SerializedName("MessageID")
        @Expose
        private Integer messageID;
        @SerializedName("sender")
        @Expose
        private String sender;
        @SerializedName("sendername")
        @Expose
        private String sendername;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("profileImage")
        @Expose
        private String profileImage;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("date_1")
        @Expose
        private String date1;
        @SerializedName("date")
        @Expose
        private String date;

        public Integer getMessageID() {
            return messageID;
        }

        public void setMessageID(Integer messageID) {
            this.messageID = messageID;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSendername() {
            return sendername;
        }

        public void setSendername(String sendername) {
            this.sendername = sendername;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getLocation() {
            return location;
    }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDate1() {
            return date1;
        }

        public void setDate1(String date1) {
            this.date1 = date1;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
