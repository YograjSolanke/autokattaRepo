package autokatta.com.response;

import java.util.Comparator;

/**
 * Created by ak-005 on 31/3/17.
 */

public class GetRegisteredContactsResponse {

    private String contact;
    private String username;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*Comparator for sorting the list by Student Name*/
    public static Comparator<GetRegisteredContactsResponse> StuNameComparator = new Comparator<GetRegisteredContactsResponse>() {

        public int compare(GetRegisteredContactsResponse s1, GetRegisteredContactsResponse s2) {
            String StudentName1 = s1.username.toUpperCase();
            String StudentName2 = s2.username.toUpperCase();

            //ascending order
            // return StudentName1.compareTo(StudentName2);

            //descending order
            return StudentName2.compareTo(StudentName1);
        }
    };

    /*@SerializedName("Success")
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
        @SerializedName("mystatus")
        @Expose
        private String mystatus;

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

        public String getMystatus() {
            return mystatus;
        }

        public void setMystatus(String mystatus) {
            this.mystatus = mystatus;
        }

    }*/

}