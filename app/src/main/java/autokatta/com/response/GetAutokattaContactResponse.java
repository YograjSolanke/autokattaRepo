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

        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("profilePic")
        @Expose
        private String profilePic;
        @SerializedName("mystatus")
        @Expose
        private String mystatus;
        @SerializedName("followStatus")
        @Expose
        private String followStatus;
        @SerializedName("groupIds")
        @Expose
        private String groupIds = "24,25";
        @SerializedName("groupNames")
        @Expose
        private String groupNames = "yoyo,newGroup";

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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

        public String getGroupIds() {
            return groupIds;
        }

        public void setGroupIds(String groupIds) {
            this.groupIds = groupIds;
        }

        public String getGroupNames() {
            return groupNames;
        }

        public void setGroupNames(String groupNames) {
            this.groupNames = groupNames;
        }
    }
}
