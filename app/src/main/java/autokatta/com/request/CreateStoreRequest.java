package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-001 on 12/7/17.
 */

public class CreateStoreRequest {

    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("StoreImage")
    @Expose
    private String storeImage;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("StoreType")
    @Expose
    private String storeType;
    @SerializedName("WebSite")
    @Expose
    private String webSite;
    @SerializedName("WorkingDays")
    @Expose
    private String workingDays;
    @SerializedName("OpenTime")
    @Expose
    private String openTime;
    @SerializedName("CloseTime")
    @Expose
    private String closeTime;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CoverImage")
    @Expose
    private String coverImage;
    @SerializedName("BrandTags")
    @Expose
    private String brandTags;
    @SerializedName("Brands")
    @Expose
    private String brands;

    public CreateStoreRequest(String contactNo, String name, String storeImage, String location, String category,
                              String storeType, String webSite, String workingDays, String openTime, String closeTime,
                              String address, String description, String coverImage, String brandTags, String brands) {
        this.contactNo = contactNo;
        this.name = name;
        this.storeImage = storeImage;
        this.location = location;
        this.category = category;
        this.storeType = storeType;
        this.webSite = webSite;
        this.workingDays = workingDays;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.description = description;
        this.coverImage = coverImage;
        this.brandTags = brandTags;
        this.brands = brands;
    }
}
