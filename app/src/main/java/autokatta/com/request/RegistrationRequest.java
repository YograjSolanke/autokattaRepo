package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-001 on 9/6/17.
 */

public class RegistrationRequest {

    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("ProfilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("Contact")
    @Expose
    private String contact;
    @SerializedName("Emial")
    @Expose
    private String emial;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Profession")
    @Expose
    private String profession;
    @SerializedName("SubProfession")
    @Expose
    private String subProfession;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("About")
    @Expose
    private String about;
    @SerializedName("WebSite")
    @Expose
    private String webSite;
    @SerializedName("VehicleNo")
    @Expose
    private String vehicleNo;
    @SerializedName("VehicleType")
    @Expose
    private String vehicleType;
    @SerializedName("Industry")
    @Expose
    private String industry;
    @SerializedName("TaxValidity")
    @Expose
    private String taxValidity;
    @SerializedName("FitnessValidity")
    @Expose
    private String fitnessValidity;
    @SerializedName("PermitValidity")
    @Expose
    private String permitValidity;

    @SerializedName("BrandName")
    @Expose
    private String BrandName;

    public RegistrationRequest(String userName, String profilePicture, String contact, String emial, String dOB,
                               String country, String state, String city, String profession, String subProfession,
                               String gender, String password, String about, String webSite, String vehicleNo,
                               String vehicleType, String industry, String taxValidity, String fitnessValidity,
                               String permitValidity,String Brand) {
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.contact = contact;
        this.emial = emial;
        this.dOB = dOB;
        this.country = country;
        this.state = state;
        this.city = city;
        this.profession = profession;
        this.subProfession = subProfession;
        this.gender = gender;
        this.password = password;
        this.about = about;
        this.webSite = webSite;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.industry = industry;
        this.taxValidity = taxValidity;
        this.fitnessValidity = fitnessValidity;
        this.permitValidity = permitValidity;
        this.BrandName=Brand;
    }


//registration activity
    public RegistrationRequest(String username, String contact, String email, String dob, String gender, String city, String profession, String password, String sub_profession, String industry,String brand) {
        this.userName = username;
        this.contact = contact;
        this.emial = email;
        this.dOB = dob;
        this.gender = gender;
        this.city = city;
        this.profession = profession;
        this.password = password;
        this.subProfession = sub_profession;
        this.industry = industry;
        this.BrandName = brand;
    }
}
