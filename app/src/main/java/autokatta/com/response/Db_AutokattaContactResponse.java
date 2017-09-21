package autokatta.com.response;

import java.util.Comparator;

/**
 * Created by ak-003 on 30/3/17.
 */

public class Db_AutokattaContactResponse {

    private String contact;
    private String username;
    private String userprofile;
    private String followstatus;
    private String mystatus;
    private String city;
    private Boolean contactVisibility;
    private String status;
    private String groupIds;
    private String groupNames;

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

    public String getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(String userprofile) {
        this.userprofile = userprofile;
    }

    public String getFollowstatus() {
        return followstatus;
    }

    public void setFollowstatus(String followstatus) {
        this.followstatus = followstatus;
    }

    public String getMystatus() {
        return mystatus;
    }

    public void setMystatus(String mystatus) {
        this.mystatus = mystatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getContactVisibility() {
        return contactVisibility;
    }

    public void setContactVisibility(Boolean contactVisibility) {
        this.contactVisibility = contactVisibility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    /*Comparator for sorting the list by Student Name*/
    public static Comparator<Db_AutokattaContactResponse> StuNameComparator = new Comparator<Db_AutokattaContactResponse>() {

        public int compare(Db_AutokattaContactResponse s1, Db_AutokattaContactResponse s2) {
            String StudentName1 = s1.username.toUpperCase();
            String StudentName2 = s2.username.toUpperCase();

            //ascending order
            // return StudentName1.compareTo(StudentName2);

            //descending order
            return StudentName2.compareTo(StudentName1);
        }
    };
}
