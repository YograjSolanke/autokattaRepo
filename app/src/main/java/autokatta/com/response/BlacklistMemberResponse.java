package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 22/3/17.
 */

public class BlacklistMemberResponse {


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
        @SerializedName("blacklistContact")
        @Expose
        private String blacklistContact;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("userimage")
        @Expose
        private String userimage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBlacklistContact() {
            return blacklistContact;
        }

        public void setBlacklistContact(String blacklistContact) {
            this.blacklistContact = blacklistContact;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserimage() {
            return userimage;
        }

        public void setUserimage(String userimage) {
            this.userimage = userimage;
        }

    }

}
