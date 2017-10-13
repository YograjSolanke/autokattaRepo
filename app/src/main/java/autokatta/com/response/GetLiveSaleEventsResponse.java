package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 27/4/17.
 */

public class GetLiveSaleEventsResponse {

    @SerializedName("Success")
    @Expose
    private List<Success> success = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }


    public class Success {
        @SerializedName("Id")
        @Expose
        private int id;
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
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("saleOwnerName")
        @Expose
        private String saleOwnerName;
        @SerializedName("ExchangeOwnerName")
        @Expose
        private String exchangeOwnerName;
        @SerializedName("loanOwnerName")
        @Expose
        private String loanOwnerName;
        @SerializedName("serviceOwnerName")
        @Expose
        private String serviceOwnerName;
        @SerializedName("ignoreGoingStatus")
        @Expose
        private String ignoreGoingStatus;
        @SerializedName("mycontact")
        @Expose
        private String mycontact;
        @SerializedName("blackListStatus")
        @Expose
        private String blackListStatus;
        @SerializedName("eventOwner")
        @Expose
        private String eventOwner;

        private String keyWord;

        @SerializedName("city")
        @Expose
        private String saleOwnerCity;

        @SerializedName("pLatitude")
        @Expose
        private String saleOwnerCityLatitude;

        @SerializedName("pLongitude")
        @Expose
        private String saleOwnerCityLongitude;

        /*@SerializedName("LocationCity")
        @Expose
        private String vehicleCity;*/

        @SerializedName("smLatitude")
        @Expose
        private String eventCityLatitude;

        @SerializedName("smLongitude")
        @Expose
        private String eventCityLongitude;

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSaleOwnerName() {
            return saleOwnerName;
        }

        public void setSaleOwnerName(String saleOwnerName) {
            this.saleOwnerName = saleOwnerName;
        }

        public String getExchangeOwnerName() {
            return exchangeOwnerName;
        }

        public void setExchangeOwnerName(String exchangeOwnerName) {
            this.exchangeOwnerName = exchangeOwnerName;
        }

        public String getLoanOwnerName() {
            return loanOwnerName;
        }

        public void setLoanOwnerName(String loanOwnerName) {
            this.loanOwnerName = loanOwnerName;
        }

        public String getServiceOwnerName() {
            return serviceOwnerName;
        }

        public void setServiceOwnerName(String serviceOwnerName) {
            this.serviceOwnerName = serviceOwnerName;
        }

        public String getMycontact() {
            return mycontact;
        }

        public void setMycontact(String mycontact) {
            this.mycontact = mycontact;
        }

        public String getBlackListStatus() {
            return blackListStatus;
        }

        public void setBlackListStatus(String blackListStatus) {
            this.blackListStatus = blackListStatus;
        }

        public String getEventOwner() {
            return eventOwner;
        }

        public void setEventOwner(String eventOwner) {
            this.eventOwner = eventOwner;
        }

        public String getIgnoreGoingStatus() {
            return ignoreGoingStatus;
        }

        public void setIgnoreGoingStatus(String ignoreGoingStatus) {
            this.ignoreGoingStatus = ignoreGoingStatus;
        }

        public String getSaleOwnerCity() {
            return saleOwnerCity;
        }

        public void setSaleOwnerCity(String saleOwnerCity) {
            this.saleOwnerCity = saleOwnerCity;
        }

        public String getSaleOwnerCityLatitude() {
            return saleOwnerCityLatitude;
        }

        public void setSaleOwnerCityLatitude(String saleOwnerCityLatitude) {
            this.saleOwnerCityLatitude = saleOwnerCityLatitude;
        }

        public String getSaleOwnerCityLongitude() {
            return saleOwnerCityLongitude;
        }

        public void setSaleOwnerCityLongitude(String saleOwnerCityLongitude) {
            this.saleOwnerCityLongitude = saleOwnerCityLongitude;
        }

        public String getEventCityLatitude() {
            return eventCityLatitude;
        }

        public void setEventCityLatitude(String eventCityLatitude) {
            this.eventCityLatitude = eventCityLatitude;
        }

        public String getEventCityLongitude() {
            return eventCityLongitude;
        }

        public void setEventCityLongitude(String eventCityLongitude) {
            this.eventCityLongitude = eventCityLongitude;
        }
    }
}
