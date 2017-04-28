package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 28/4/17.
 */

public class LoanMelaParticipantsResponse {

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
        @SerializedName("profile_photo")
        @Expose
        private String profilePhoto;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("profession")
        @Expose
        private String profession;
        @SerializedName("subprofession")
        @Expose
        private String subprofession;
        @SerializedName("blackliststatus")
        @Expose
        private String blackliststatus;

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getSubprofession() {
            return subprofession;
        }

        public void setSubprofession(String subprofession) {
            this.subprofession = subprofession;
        }

        public String getBlackliststatus() {
            return blackliststatus;
        }

        public void setBlackliststatus(String blackliststatus) {
            this.blackliststatus = blackliststatus;
        }

    }
}
