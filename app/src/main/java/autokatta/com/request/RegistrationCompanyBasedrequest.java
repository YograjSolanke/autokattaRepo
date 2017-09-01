package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 11/7/17.
 */

public class RegistrationCompanyBasedrequest {
    @SerializedName("RegID")
    @Expose
    private int regID;
    @SerializedName("PageNo")
    @Expose
    private int pageNo;
    @SerializedName("About")
    @Expose
    private String about;
    @SerializedName("ProfilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("WebSite")
    @Expose
    private String webSite;
    @SerializedName("AreaOfOperation")
    @Expose
    private String areaOfOperation;
    @SerializedName("ByKMS")
    @Expose
    private String byKMS;
    @SerializedName("ByDistrict")
    @Expose
    private String byDistrict;
    @SerializedName("ByState")
    @Expose
    private String byState;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("Skills")
    @Expose
    private String skills;
    @SerializedName("DealingWith")
    @Expose
    private String dealingWith;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("SubCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("Interest")
    @Expose
    private String interest;
    @SerializedName("Interest1")
    @Expose
    private String interest1;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("InterestIDs")
    @Expose
    private String interestIDs;

    public int getRegID() {
        return regID;
    }

    public void setRegID(int regID) {
        this.regID = regID;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getAreaOfOperation() {
        return areaOfOperation;
    }

    public void setAreaOfOperation(String areaOfOperation) {
        this.areaOfOperation = areaOfOperation;
    }

    public String getByKMS() {
        return byKMS;
    }

    public void setByKMS(String byKMS) {
        this.byKMS = byKMS;
    }

    public String getByDistrict() {
        return byDistrict;
    }

    public void setByDistrict(String byDistrict) {
        this.byDistrict = byDistrict;
    }

    public String getByState() {
        return byState;
    }

    public void setByState(String byState) {
        this.byState = byState;
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

    public String getDealingWith() {
        return dealingWith;
    }

    public void setDealingWith(String dealingWith) {
        this.dealingWith = dealingWith;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getInterest1() {
        return interest1;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInterestIDs() {
        return interestIDs;
    }

    public void setInterestIDs(String interestIDs) {
        this.interestIDs = interestIDs;
    }

    //update registration in continue registration
    public RegistrationCompanyBasedrequest(Integer Regid, Integer page, String profileImage, String about, String website) {
        this.regID = Regid;
        this.pageNo = page;
        this.about = about;
        this.profilePicture = profileImage;
        this.webSite = website;
    }

    //update company based registration
    public RegistrationCompanyBasedrequest(Integer Regid, Integer page, String area, String bykm, String bydistrict,
                                           String bystate, String company, String designation, String skills, String deals,
                                           String categoryName, String subCategoryName, String brandName, String interest) {
        this.regID = Regid;
        this.pageNo = page;
        this.areaOfOperation = area;
        this.byKMS = bykm;
        this.byDistrict = bydistrict;
        this.byState = bystate;
        this.companyName = company;
        this.designation = designation;
        this.skills = skills;
        this.dealingWith = deals;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.brandName = brandName;
        this.interestIDs = interest;
    }
}
