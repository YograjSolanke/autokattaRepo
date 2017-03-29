package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 29/3/17.
 */

public class GetContactByCompanyResponse {


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
        private String contact;
        @SerializedName("profile_pic")
        @Expose
        private String profilePic;
        @SerializedName("username")
        @Expose
        private String username;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }
}
