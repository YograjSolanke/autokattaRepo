package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 18/3/17.
 */

public class ProfileAboutResponse {

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
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("profession")
        @Expose
        private String profession;
        @SerializedName("sub_profession")
        @Expose
        private String subProfession;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("profile_pic")
        @Expose
        private String profilePic;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("reg_id")
        @Expose
        private String regId;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("companyName")
        @Expose
        private String companyName;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("skills")
        @Expose
        private String skills;
        @SerializedName("likecount")
        @Expose
        private String likecount;
        @SerializedName("followcount")
        @Expose
        private String followcount;
        @SerializedName("likestatus")
        @Expose
        private String likestatus;
        @SerializedName("followstatus")
        @Expose
        private String followstatus;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getSubProfession() {
            return subProfession;
        }

        public void setSubProfession(String subProfession) {
            this.subProfession = subProfession;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegId() {
            return regId;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skills) {
            this.skills = skills;
        }

        public String getLikecount() {
            return likecount;
        }

        public void setLikecount(String likecount) {
            this.likecount = likecount;
        }

        public String getFollowcount() {
            return followcount;
        }

        public void setFollowcount(String followcount) {
            this.followcount = followcount;
        }

        public String getLikestatus() {
            return likestatus;
        }

        public void setLikestatus(String likestatus) {
            this.likestatus = likestatus;
        }

        public String getFollowstatus() {
            return followstatus;
        }

        public void setFollowstatus(String followstatus) {
            this.followstatus = followstatus;
        }

    }
}
