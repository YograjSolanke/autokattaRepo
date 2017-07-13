package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-001 on 9/6/17.
 */

public class CreateLoanMelaRequest {
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
    @SerializedName("Details")
    @Expose
    private String details;

    public CreateLoanMelaRequest(String title, String location,
                                 String address, String start_date,
                                 String start_time, String end_date,
                                 String end_time, String image,
                                 String details, String contact) {
        this.contact = contact;
        this.name = title;
        this.startDate = start_date;
        this.startTime = start_time;
        this.endDate = end_date;
        this.endTime = end_time;
        this.location = location;
        this.address = address;
        this.image = image;
        this.details = details;
    }
}
