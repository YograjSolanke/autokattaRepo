package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 27/4/17.
 */

public class GetLiveExchangeEventsResponse {

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
        @SerializedName("ExchangeOwnerName")
        @Expose
        private String exchangeOwnerName;
        @SerializedName("ignoreGoingStatus")
        @Expose
        private String ignoreGoingStatus;

        private String keyWord;

        @SerializedName("city")
        @Expose
        private String exchangeOwnerCity;

        @SerializedName("pLatitude")
        @Expose
        private String exchangeOwnerCityLatitude;

        @SerializedName("pLongitude")
        @Expose
        private String exchangeOwnerCityLongitude;

        /*@SerializedName("LocationCity")
        @Expose
        private String vehicleCity;*/

        @SerializedName("emLatitude")
        @Expose
        private String eventCityLatitude;

        @SerializedName("emLongitude")
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

        public String getExchangeOwnerName() {
            return exchangeOwnerName;
        }

        public void setExchangeOwnerName(String exchangeOwnerName) {
            this.exchangeOwnerName = exchangeOwnerName;
        }

        public String getIgnoreGoingStatus() {
            return ignoreGoingStatus;
        }

        public void setIgnoreGoingStatus(String ignoreGoingStatus) {
            this.ignoreGoingStatus = ignoreGoingStatus;
        }

        public String getExchangeOwnerCity() {
            return exchangeOwnerCity;
        }

        public void setExchangeOwnerCity(String exchangeOwnerCity) {
            this.exchangeOwnerCity = exchangeOwnerCity;
        }

        public String getExchangeOwnerCityLatitude() {
            return exchangeOwnerCityLatitude;
        }

        public void setExchangeOwnerCityLatitude(String exchangeOwnerCityLatitude) {
            this.exchangeOwnerCityLatitude = exchangeOwnerCityLatitude;
        }

        public String getExchangeOwnerCityLongitude() {
            return exchangeOwnerCityLongitude;
        }

        public void setExchangeOwnerCityLongitude(String exchangeOwnerCityLongitude) {
            this.exchangeOwnerCityLongitude = exchangeOwnerCityLongitude;
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
