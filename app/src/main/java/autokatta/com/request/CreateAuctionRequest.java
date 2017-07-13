package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-005 on 12/7/17.
 */

public class CreateAuctionRequest {

    @SerializedName("action_title")
    @Expose
    private String actionTitle;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("auction_type")
    @Expose
    private String auctionType;
    @SerializedName("special_clauses")
    @Expose
    private String specialClauses;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("openClose")
    @Expose
    private String openClose;
    @SerializedName("stockLocation")
    @Expose
    private String stockLocation;
    @SerializedName("auction_category")
    @Expose
    private String auctionCategory;
    @SerializedName("Contact")
    @Expose
    private String contact;

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
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

    public String getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
    }

    public String getSpecialClauses() {
        return specialClauses;
    }

    public void setSpecialClauses(String specialClauses) {
        this.specialClauses = specialClauses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOpenClose() {
        return openClose;
    }

    public void setOpenClose(String openClose) {
        this.openClose = openClose;
    }

    public String getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(String stockLocation) {
        this.stockLocation = stockLocation;
    }

    public String getAuctionCategory() {
        return auctionCategory;
    }

    public void setAuctionCategory(String auctionCategory) {
        this.auctionCategory = auctionCategory;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

   public CreateAuctionRequest(String title, String start_date,
                         String start_time, String end_date,
                         String end_time, String auction_type,
                         String contact, String location,
                         String auction_category, String special_clauses,
                         String openClose, String stockLocation)
    {
        this.actionTitle=title;
        this.startDate=start_date;
        this.startTime=start_time;
        this.endDate=end_date;
        this.endTime=end_time;
        this.auctionType=auction_type;
        this.contact=contact;
        this.location=location;
        this.auctionCategory=auction_category;
        this.specialClauses=special_clauses;
        this.openClose=openClose;
        this.stockLocation=stockLocation;
    }
}
