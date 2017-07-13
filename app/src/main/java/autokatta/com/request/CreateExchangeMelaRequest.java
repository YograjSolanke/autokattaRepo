package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 12/7/17.
 */

public class CreateExchangeMelaRequest {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Start_Date")
    @Expose
    private String startDate;
    @SerializedName("Start_Time")
    @Expose
    private String startTime;
    @SerializedName("End_Date")
    @Expose
    private String endDate;
    @SerializedName("End_Time")
    @Expose
    private String endTime;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("startDateTime")
    @Expose
    private String startDateTime;
    @SerializedName("endDateTime")
    @Expose
    private String endDateTime;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("Details")
    @Expose
    private String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
public CreateExchangeMelaRequest(String title, String location,
                                 String address, String start_date,
                                 String start_time, String end_date,
                                 String end_time, String image,
                                 String details, String contact){
    this.name=title;
    this.location=location;
    this.address=address;
    this.startDate=start_date;
    this.startTime=start_time;
    this.endDate=end_date;
    this.endTime=end_time;
    this.image=image;
    this.details=details;
    this.contact=contact;

}
}
