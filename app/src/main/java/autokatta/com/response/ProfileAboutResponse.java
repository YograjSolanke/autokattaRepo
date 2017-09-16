package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileAboutResponse {

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

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("dob_1")
    @Expose
    private String dob1;
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
    private Integer regId;
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
    @SerializedName("interests")
    @Expose
    private String interests;
    @SerializedName("likecount")
    @Expose
    private Integer likecount;
    @SerializedName("followcount")
    @Expose
    private Integer followcount;
    @SerializedName("likestatus")
    @Expose
    private String likestatus;
    @SerializedName("followstatus")
    @Expose
    private String followstatus;
    @SerializedName("AreaOfOperation")
    @Expose
    private String areaOfOperation;
    @SerializedName("BrandName")
    @Expose
    private String brandName;
    @SerializedName("ByDistrict")
    @Expose
    private String byDistrict;
    @SerializedName("ByKMS")
    @Expose
    private String byKMS;
    @SerializedName("ByState")
    @Expose
    private String byState;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("DealingWith")
    @Expose
    private String dealingWith;
    @SerializedName("EstimatedNextServiceDate_1")
    @Expose
    private Object estimatedNextServiceDate1;
    @SerializedName("EstimatedNextServiceDate")
    @Expose
    private String estimatedNextServiceDate;
    @SerializedName("FitnessValidity_1")
    @Expose
    private Object fitnessValidity1;
    @SerializedName("FitnessValidity")
    @Expose
    private String fitnessValidity;
    @SerializedName("FollowID")
    @Expose
    private Object followID;
    @SerializedName("Industry")
    @Expose
    private String industry;
    @SerializedName("InsuranceValidityDate_1")
    @Expose
    private Object insuranceValidityDate1;
    @SerializedName("InsuranceValidityDate")
    @Expose
    private String insuranceValidityDate;
    @SerializedName("IsExchange")
    @Expose
    private String isExchange;
    @SerializedName("IsFinance")
    @Expose
    private String isFinance;
    @SerializedName("IsOneTimeBuyer")
    @Expose
    private String isOneTimeBuyer;
    @SerializedName("IsOneTimeSeller")
    @Expose
    private String isOneTimeSeller;
    @SerializedName("IsRegularBuyer")
    @Expose
    private String isRegularBuyer;
    @SerializedName("IsRegularSeller")
    @Expose
    private String isRegularSeller;
    @SerializedName("LastServiceDate_1")
    @Expose
    private Object lastServiceDate1;
    @SerializedName("LastServiceDate")
    @Expose
    private String lastServiceDate;
    @SerializedName("LikeID")
    @Expose
    private Object likeID;
    @SerializedName("PermitValidity_1")
    @Expose
    private Object permitValidity1;
    @SerializedName("PermitValidity")
    @Expose
    private String permitValidity;
    @SerializedName("PUCValidityDate_1")
    @Expose
    private Object pUCValidityDate1;
    @SerializedName("PUCValidityDate")
    @Expose
    private String pUCValidityDate;
    @SerializedName("TaxValidity_1")
    @Expose
    private Object taxValidity1;
    @SerializedName("TaxValidity")
    @Expose
    private String taxValidity;
    @SerializedName("Rating")
    @Expose
    private Object rating;
    @SerializedName("SubCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("VehicleNo")
    @Expose
    private String vehicleNo;
    @SerializedName("VehicleType")
    @Expose
    private String vehicleType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDob1() {
        return dob1;
    }

    public void setDob1(String dob1) {
        this.dob1 = dob1;
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

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
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

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    public Integer getFollowcount() {
        return followcount;
    }

    public void setFollowcount(Integer followcount) {
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

    public String getAreaOfOperation() {
        return areaOfOperation;
    }

    public void setAreaOfOperation(String areaOfOperation) {
        this.areaOfOperation = areaOfOperation;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getByDistrict() {
        return byDistrict;
    }

    public void setByDistrict(String byDistrict) {
        this.byDistrict = byDistrict;
    }

    public String getByKMS() {
        return byKMS;
    }

    public void setByKMS(String byKMS) {
        this.byKMS = byKMS;
    }

    public String getByState() {
        return byState;
    }

    public void setByState(String byState) {
        this.byState = byState;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDealingWith() {
        return dealingWith;
    }

    public void setDealingWith(String dealingWith) {
        this.dealingWith = dealingWith;
    }

    public Object getEstimatedNextServiceDate1() {
        return estimatedNextServiceDate1;
    }

    public void setEstimatedNextServiceDate1(Object estimatedNextServiceDate1) {
        this.estimatedNextServiceDate1 = estimatedNextServiceDate1;
    }

    public String getEstimatedNextServiceDate() {
        return estimatedNextServiceDate;
    }

    public void setEstimatedNextServiceDate(String estimatedNextServiceDate) {
        this.estimatedNextServiceDate = estimatedNextServiceDate;
    }

    public Object getFitnessValidity1() {
        return fitnessValidity1;
    }

    public void setFitnessValidity1(Object fitnessValidity1) {
        this.fitnessValidity1 = fitnessValidity1;
    }

    public String getFitnessValidity() {
        return fitnessValidity;
    }

    public void setFitnessValidity(String fitnessValidity) {
        this.fitnessValidity = fitnessValidity;
    }

    public Object getFollowID() {
        return followID;
    }

    public void setFollowID(Object followID) {
        this.followID = followID;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Object getInsuranceValidityDate1() {
        return insuranceValidityDate1;
    }

    public void setInsuranceValidityDate1(Object insuranceValidityDate1) {
        this.insuranceValidityDate1 = insuranceValidityDate1;
    }

    public String getInsuranceValidityDate() {
        return insuranceValidityDate;
    }

    public void setInsuranceValidityDate(String insuranceValidityDate) {
        this.insuranceValidityDate = insuranceValidityDate;
    }

    public String getIsExchange() {
        return isExchange;
    }

    public void setIsExchange(String isExchange) {
        this.isExchange = isExchange;
    }

    public String getIsFinance() {
        return isFinance;
    }

    public void setIsFinance(String isFinance) {
        this.isFinance = isFinance;
    }

    public String getIsOneTimeBuyer() {
        return isOneTimeBuyer;
    }

    public void setIsOneTimeBuyer(String isOneTimeBuyer) {
        this.isOneTimeBuyer = isOneTimeBuyer;
    }

    public String getIsOneTimeSeller() {
        return isOneTimeSeller;
    }

    public void setIsOneTimeSeller(String isOneTimeSeller) {
        this.isOneTimeSeller = isOneTimeSeller;
    }

    public String getIsRegularBuyer() {
        return isRegularBuyer;
    }

    public void setIsRegularBuyer(String isRegularBuyer) {
        this.isRegularBuyer = isRegularBuyer;
    }

    public String getIsRegularSeller() {
        return isRegularSeller;
    }

    public void setIsRegularSeller(String isRegularSeller) {
        this.isRegularSeller = isRegularSeller;
    }

    public Object getLastServiceDate1() {
        return lastServiceDate1;
    }

    public void setLastServiceDate1(Object lastServiceDate1) {
        this.lastServiceDate1 = lastServiceDate1;
    }

    public String getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public Object getLikeID() {
        return likeID;
    }

    public void setLikeID(Object likeID) {
        this.likeID = likeID;
    }

    public Object getPermitValidity1() {
        return permitValidity1;
    }

    public void setPermitValidity1(Object permitValidity1) {
        this.permitValidity1 = permitValidity1;
    }

    public String getPermitValidity() {
        return permitValidity;
    }

    public void setPermitValidity(String permitValidity) {
        this.permitValidity = permitValidity;
    }

    public Object getPUCValidityDate1() {
        return pUCValidityDate1;
    }

    public void setPUCValidityDate1(Object pUCValidityDate1) {
        this.pUCValidityDate1 = pUCValidityDate1;
    }

    public String getPUCValidityDate() {
        return pUCValidityDate;
    }

    public void setPUCValidityDate(String pUCValidityDate) {
        this.pUCValidityDate = pUCValidityDate;
    }

    public Object getTaxValidity1() {
        return taxValidity1;
    }

    public void setTaxValidity1(Object taxValidity1) {
        this.taxValidity1 = taxValidity1;
    }

    public String getTaxValidity() {
        return taxValidity;
    }

    public void setTaxValidity(String taxValidity) {
        this.taxValidity = taxValidity;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
}