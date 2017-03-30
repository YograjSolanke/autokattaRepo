package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 30/3/17.
 */

public class GetAutokattaContactResponse {
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

        @SerializedName("contact")
        @Expose
        private long contact;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("profilePic")
        @Expose
        private String profilePic;
        @SerializedName("mystatus")
        @Expose
        private String mystatus;
        @SerializedName("followStatus")
        @Expose
        private String followStatus;

        public long getContact() {
            return contact;
        }

        public void setContact(long contact) {
            this.contact = contact;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getMystatus() {
            return mystatus;
        }

        public void setMystatus(String mystatus) {
            this.mystatus = mystatus;
        }

        public String getFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(String followStatus) {
            this.followStatus = followStatus;
        }

    }
}
