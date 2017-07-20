package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 14/7/17.
 */

public class UpdateStoreRequest {
    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("ProfilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("WebSite")
    @Expose
    private String webSite;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("WorkingDays")
    @Expose
    private String workingDays;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("OpenTme")
    @Expose
    private String openTme;
    @SerializedName("CloseTime")
    @Expose
    private String closeTime;
    @SerializedName("StoreType")
    @Expose
    private String storeType;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("CoverImage")
    @Expose
    private String coverImage;

    @SerializedName("BrandTags")
    @Expose
    private String BrandTags;

    public String getBrandTags() {
        return BrandTags;
    }

    public void setBrandTags(String brandTags) {
        BrandTags = brandTags;
    }

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpenTme() {
        return openTme;
    }

    public void setOpenTme(String openTme) {
        this.openTme = openTme;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
public UpdateStoreRequest(String storename, int store_id, String location, String website, String open, String close,
                          String profile, String category, String working_days, String storeDescription, String storetype,
                          String address, String coverImage, String brandTags, String strBrandSpinner) {
  this.storeName=storename;
  this.storeID=store_id;
  this.location=location;
  this.webSite=website;
  this.openTme=open;
  this.closeTime=close;
  this.profilePicture=profile;
  this.category=category;
  this.workingDays=working_days;
  this.description=storeDescription;
  this.storeType=storetype;
  this.address=address;
  this.coverImage=coverImage;
    this.BrandTags = brandTags;
}
}
