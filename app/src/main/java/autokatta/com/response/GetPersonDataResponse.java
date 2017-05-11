package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 11/5/17.
 */

public class GetPersonDataResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;
    @SerializedName("Error")
    @Expose
    private String error;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("reg_id")
        @Expose
        private String regId;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("contact_no")
        @Expose
        private String contactNo;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("profession")
        @Expose
        private String profession;
        @SerializedName("industry")
        @Expose
        private String industry;
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
        @SerializedName("vehicle_no")
        @Expose
        private String vehicleNo;
        @SerializedName("vehicle_type")
        @Expose
        private String vehicleType;
        @SerializedName("companyName")
        @Expose
        private String companyName;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("skills")
        @Expose
        private String skills;
        @SerializedName("dealingWith")
        @Expose
        private String dealingWith;
        @SerializedName("AreaOfOperation")
        @Expose
        private String areaOfOperation;
        @SerializedName("byKms")
        @Expose
        private String byKms;
        @SerializedName("byDistrict")
        @Expose
        private String byDistrict;
        @SerializedName("byState")
        @Expose
        private String byState;
        @SerializedName("isOnetimeBuyer")
        @Expose
        private String isOnetimeBuyer;
        @SerializedName("isOnetimeSeller")
        @Expose
        private String isOnetimeSeller;
        @SerializedName("isRegularBuyer")
        @Expose
        private String isRegularBuyer;
        @SerializedName("isRegularSeller")
        @Expose
        private String isRegularSeller;
        @SerializedName("isFinance")
        @Expose
        private String isFinance;
        @SerializedName("isExchange")
        @Expose
        private String isExchange;
        @SerializedName("tax_validity")
        @Expose
        private String taxValidity;
        @SerializedName("fitness_validity")
        @Expose
        private String fitnessValidity;
        @SerializedName("permit_validity")
        @Expose
        private String permitValidity;
        @SerializedName("insurance_val_date")
        @Expose
        private String insuranceValDate;
        @SerializedName("puc_val_date")
        @Expose
        private String pucValDate;
        @SerializedName("last_service_date")
        @Expose
        private String lastServiceDate;
        @SerializedName("estimated_next_service_date")
        @Expose
        private String estimatedNextServiceDate;
        @SerializedName("follows")
        @Expose
        private String follows;
        @SerializedName("likes")
        @Expose
        private String likes;
        @SerializedName("ratings")
        @Expose
        private String ratings;
        @SerializedName("nextFollowupDate")
        @Expose
        private String nextFollowupDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRegId() {
            return regId;
        }

        public void setRegId(String regId) {
            this.regId = regId;
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

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
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

        public String getAreaOfOperation() {
            return areaOfOperation;
        }

        public void setAreaOfOperation(String areaOfOperation) {
            this.areaOfOperation = areaOfOperation;
        }

        public String getByKms() {
            return byKms;
        }

        public void setByKms(String byKms) {
            this.byKms = byKms;
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

        public String getIsOnetimeBuyer() {
            return isOnetimeBuyer;
        }

        public void setIsOnetimeBuyer(String isOnetimeBuyer) {
            this.isOnetimeBuyer = isOnetimeBuyer;
        }

        public String getIsOnetimeSeller() {
            return isOnetimeSeller;
        }

        public void setIsOnetimeSeller(String isOnetimeSeller) {
            this.isOnetimeSeller = isOnetimeSeller;
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

        public String getIsFinance() {
            return isFinance;
        }

        public void setIsFinance(String isFinance) {
            this.isFinance = isFinance;
        }

        public String getIsExchange() {
            return isExchange;
        }

        public void setIsExchange(String isExchange) {
            this.isExchange = isExchange;
        }

        public String getTaxValidity() {
            return taxValidity;
        }

        public void setTaxValidity(String taxValidity) {
            this.taxValidity = taxValidity;
        }

        public String getFitnessValidity() {
            return fitnessValidity;
        }

        public void setFitnessValidity(String fitnessValidity) {
            this.fitnessValidity = fitnessValidity;
        }

        public String getPermitValidity() {
            return permitValidity;
        }

        public void setPermitValidity(String permitValidity) {
            this.permitValidity = permitValidity;
        }

        public String getInsuranceValDate() {
            return insuranceValDate;
        }

        public void setInsuranceValDate(String insuranceValDate) {
            this.insuranceValDate = insuranceValDate;
        }

        public String getPucValDate() {
            return pucValDate;
        }

        public void setPucValDate(String pucValDate) {
            this.pucValDate = pucValDate;
        }

        public String getLastServiceDate() {
            return lastServiceDate;
        }

        public void setLastServiceDate(String lastServiceDate) {
            this.lastServiceDate = lastServiceDate;
        }

        public String getEstimatedNextServiceDate() {
            return estimatedNextServiceDate;
        }

        public void setEstimatedNextServiceDate(String estimatedNextServiceDate) {
            this.estimatedNextServiceDate = estimatedNextServiceDate;
        }

        public String getFollows() {
            return follows;
        }

        public void setFollows(String follows) {
            this.follows = follows;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getRatings() {
            return ratings;
        }

        public void setRatings(String ratings) {
            this.ratings = ratings;
        }

        public String getNextFollowupDate() {
            return nextFollowupDate;
        }

        public void setNextFollowupDate(String nextFollowupDate) {
            this.nextFollowupDate = nextFollowupDate;
        }

    }
}
