package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 21/3/17.
 */

public class MyUploadedVehiclesResponse {
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

        @SerializedName("vehicle_id")
        @Expose
        private int vehicleId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("contact_vehicle")
        @Expose
        private String contactVehicle;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("notificationstatus")
        @Expose
        private String notificationstatus;
        @SerializedName("year_of_manufacture")
        @Expose
        private String yearOfManufacture;
        @SerializedName("registration_number")
        @Expose
        private String registrationNumber;
        @SerializedName("kms_running")
        @Expose
        private String kmsRunning;
        @SerializedName("Hrs_running")
        @Expose
        private String hrsRunning;
        @SerializedName("rto_city")
        @Expose
        private String rtoCity;
        @SerializedName("location_city")
        @Expose
        private String locationCity;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("vehiclelikestatus")
        @Expose
        private String vehiclelikestatus;
        @SerializedName("vehiclefollowstatus")
        @Expose
        private String vehiclefollowstatus;
        @SerializedName("BuyerLeads")
        @Expose
        private String buyerLeads;

        public int getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getContactVehicle() {
            return contactVehicle;
        }

        public void setContactVehicle(String contactVehicle) {
            this.contactVehicle = contactVehicle;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getNotificationstatus() {
            return notificationstatus;
        }

        public void setNotificationstatus(String notificationstatus) {
            this.notificationstatus = notificationstatus;
        }

        public String getYearOfManufacture() {
            return yearOfManufacture;
        }

        public void setYearOfManufacture(String yearOfManufacture) {
            this.yearOfManufacture = yearOfManufacture;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

        public String getKmsRunning() {
            return kmsRunning;
        }

        public void setKmsRunning(String kmsRunning) {
            this.kmsRunning = kmsRunning;
        }

        public String getHrsRunning() {
            return hrsRunning;
        }

        public void setHrsRunning(String hrsRunning) {
            this.hrsRunning = hrsRunning;
        }

        public String getRtoCity() {
            return rtoCity;
        }

        public void setRtoCity(String rtoCity) {
            this.rtoCity = rtoCity;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getVehiclelikestatus() {
            return vehiclelikestatus;
        }

        public void setVehiclelikestatus(String vehiclelikestatus) {
            this.vehiclelikestatus = vehiclelikestatus;
        }

        public String getVehiclefollowstatus() {
            return vehiclefollowstatus;
        }

        public void setVehiclefollowstatus(String vehiclefollowstatus) {
            this.vehiclefollowstatus = vehiclefollowstatus;
        }

        public String getBuyerLeads() {
            return buyerLeads;
        }

        public void setBuyerLeads(String buyerLeads) {
            this.buyerLeads = buyerLeads;
        }
    }
}
