package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 19/7/17.
 */

public class UpdateProfileRequest {

    @SerializedName("RegID")
    @Expose
    private int regID;
    @SerializedName("EmialID")
    @Expose
    private String emialID;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Profession")
    @Expose
    private String profession;
    @SerializedName("SubProfession")
    @Expose
    private String subProfession;
    @SerializedName("Website")
    @Expose
    private String website;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("Skills")
    @Expose
    private String skills;

    public UpdateProfileRequest(int regID, String profilePicture, String userName) {
        this.regID = regID;
        this.profilePicture = profilePicture;
        this.userName = userName;
    }

    public UpdateProfileRequest(int regID, String emialID, String city, String profession, String subProfession, String website, String companyName, String designation, String skills) {
        this.regID = regID;
        this.emialID = emialID;
        this.city = city;
        this.profession = profession;
        this.subProfession = subProfession;
        this.website = website;
        this.companyName = companyName;
        this.designation = designation;
        this.skills = skills;
        this.userName=null;
        this.profilePicture=null;
    }

    @SerializedName("ProfilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("UserName")
    @Expose
    private String userName;

    public Integer getRegID() {
        return regID;
    }

    public void setRegID(Integer regID) {
        this.regID = regID;
    }

    public String getEmialID() {
        return emialID;
    }

    public void setEmialID(String emialID) {
        this.emialID = emialID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
