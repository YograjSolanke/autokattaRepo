package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 28/3/17.
 */

public class GetVehicleByIdResponse {
    @SerializedName("Success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public class Success {

        @SerializedName("VehicleData")
        @Expose
        private List<VehicleDatum> vehicleData = null;
        @SerializedName("VehicleSpecification")
        @Expose
        private List<Object> vehicleSpecification = null;

        public List<VehicleDatum> getVehicleData() {
            return vehicleData;
        }

        public void setVehicleData(List<VehicleDatum> vehicleData) {
            this.vehicleData = vehicleData;
        }

        public List<Object> getVehicleSpecification() {
            return vehicleSpecification;
        }

        public void setVehicleSpecification(List<Object> vehicleSpecification) {
            this.vehicleSpecification = vehicleSpecification;
        }

    }

    public class VehicleDatum {

        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("SubCategory")
        @Expose
        private String subCategory;
        @SerializedName("Model")
        @Expose
        private String model;
        @SerializedName("Brand")
        @Expose
        private String brand;
        @SerializedName("RTO_city")
        @Expose
        private String rTOCity;
        @SerializedName("Location_city")
        @Expose
        private String locationCity;
        @SerializedName("Location_state")
        @Expose
        private String locationState;
        @SerializedName("Location_country")
        @Expose
        private String locationCountry;
        @SerializedName("Location_address")
        @Expose
        private Object locationAddress;
        @SerializedName("Year_of_registration")
        @Expose
        private String yearOfRegistration;
        @SerializedName("Year_of_Manufacture")
        @Expose
        private String yearOfManufacture;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("registration_number")
        @Expose
        private String registrationNumber;
        @SerializedName("rc_available")
        @Expose
        private String rcAvailable;
        @SerializedName("insurance_valid")
        @Expose
        private String insuranceValid;
        @SerializedName("insurance_idv")
        @Expose
        private String insuranceIdv;
        @SerializedName("tax_validity")
        @Expose
        private String taxValidity;
        @SerializedName("fitness_validity")
        @Expose
        private String fitnessValidity;
        @SerializedName("permit_validity")
        @Expose
        private String permitValidity;
        @SerializedName("fual_type")
        @Expose
        private String fualType;
        @SerializedName("seating_capacity")
        @Expose
        private String seatingCapacity;
        @SerializedName("permit")
        @Expose
        private String permit;
        @SerializedName("kms_running")
        @Expose
        private String kmsRunning;
        @SerializedName("no_of_owners")
        @Expose
        private String noOfOwners;
        @SerializedName("hypothication")
        @Expose
        private String hypothication;
        @SerializedName("engine_no")
        @Expose
        private String engineNo;
        @SerializedName("chassis_no")
        @Expose
        private String chassisNo;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("drive")
        @Expose
        private String drive;
        @SerializedName("transmission")
        @Expose
        private String transmission;
        @SerializedName("body_type")
        @Expose
        private String bodyType;
        @SerializedName("boat_type")
        @Expose
        private String boatType;
        @SerializedName("rv_type")
        @Expose
        private String rvType;
        @SerializedName("tyre_condition")
        @Expose
        private String tyreCondition;
        @SerializedName("bus_type")
        @Expose
        private String busType;
        @SerializedName("air_condition")
        @Expose
        private String airCondition;
        @SerializedName("invoice")
        @Expose
        private String invoice;
        @SerializedName("implements")
        @Expose
        private String _implements;
        @SerializedName("application")
        @Expose
        private String application;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("views")
        @Expose
        private String views;
        @SerializedName("calls")
        @Expose
        private String calls;
        @SerializedName("bodyManufacturer")
        @Expose
        private String bodyManufacturer;
        @SerializedName("seatManufacturer")
        @Expose
        private String seatManufacturer;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("status")
        @Expose
        private String status;

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getRTOCity() {
            return rTOCity;
        }

        public void setRTOCity(String rTOCity) {
            this.rTOCity = rTOCity;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public String getLocationState() {
            return locationState;
        }

        public void setLocationState(String locationState) {
            this.locationState = locationState;
        }

        public String getLocationCountry() {
            return locationCountry;
        }

        public void setLocationCountry(String locationCountry) {
            this.locationCountry = locationCountry;
        }

        public Object getLocationAddress() {
            return locationAddress;
        }

        public void setLocationAddress(Object locationAddress) {
            this.locationAddress = locationAddress;
        }

        public String getYearOfRegistration() {
            return yearOfRegistration;
        }

        public void setYearOfRegistration(String yearOfRegistration) {
            this.yearOfRegistration = yearOfRegistration;
        }

        public String getYearOfManufacture() {
            return yearOfManufacture;
        }

        public void setYearOfManufacture(String yearOfManufacture) {
            this.yearOfManufacture = yearOfManufacture;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

        public String getRcAvailable() {
            return rcAvailable;
        }

        public void setRcAvailable(String rcAvailable) {
            this.rcAvailable = rcAvailable;
        }

        public String getInsuranceValid() {
            return insuranceValid;
        }

        public void setInsuranceValid(String insuranceValid) {
            this.insuranceValid = insuranceValid;
        }

        public String getInsuranceIdv() {
            return insuranceIdv;
        }

        public void setInsuranceIdv(String insuranceIdv) {
            this.insuranceIdv = insuranceIdv;
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

        public String getFualType() {
            return fualType;
        }

        public void setFualType(String fualType) {
            this.fualType = fualType;
        }

        public String getSeatingCapacity() {
            return seatingCapacity;
        }

        public void setSeatingCapacity(String seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
        }

        public String getPermit() {
            return permit;
        }

        public void setPermit(String permit) {
            this.permit = permit;
        }

        public String getKmsRunning() {
            return kmsRunning;
        }

        public void setKmsRunning(String kmsRunning) {
            this.kmsRunning = kmsRunning;
        }

        public String getNoOfOwners() {
            return noOfOwners;
        }

        public void setNoOfOwners(String noOfOwners) {
            this.noOfOwners = noOfOwners;
        }

        public String getHypothication() {
            return hypothication;
        }

        public void setHypothication(String hypothication) {
            this.hypothication = hypothication;
        }

        public String getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(String engineNo) {
            this.engineNo = engineNo;
        }

        public String getChassisNo() {
            return chassisNo;
        }

        public void setChassisNo(String chassisNo) {
            this.chassisNo = chassisNo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDrive() {
            return drive;
        }

        public void setDrive(String drive) {
            this.drive = drive;
        }

        public String getTransmission() {
            return transmission;
        }

        public void setTransmission(String transmission) {
            this.transmission = transmission;
        }

        public String getBodyType() {
            return bodyType;
        }

        public void setBodyType(String bodyType) {
            this.bodyType = bodyType;
        }

        public String getBoatType() {
            return boatType;
        }

        public void setBoatType(String boatType) {
            this.boatType = boatType;
        }

        public String getRvType() {
            return rvType;
        }

        public void setRvType(String rvType) {
            this.rvType = rvType;
        }

        public String getTyreCondition() {
            return tyreCondition;
        }

        public void setTyreCondition(String tyreCondition) {
            this.tyreCondition = tyreCondition;
        }

        public String getBusType() {
            return busType;
        }

        public void setBusType(String busType) {
            this.busType = busType;
        }

        public String getAirCondition() {
            return airCondition;
        }

        public void setAirCondition(String airCondition) {
            this.airCondition = airCondition;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public String getImplements() {
            return _implements;
        }

        public void setImplements(String _implements) {
            this._implements = _implements;
        }

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getCalls() {
            return calls;
        }

        public void setCalls(String calls) {
            this.calls = calls;
        }

        public String getBodyManufacturer() {
            return bodyManufacturer;
        }

        public void setBodyManufacturer(String bodyManufacturer) {
            this.bodyManufacturer = bodyManufacturer;
        }

        public String getSeatManufacturer() {
            return seatManufacturer;
        }

        public void setSeatManufacturer(String seatManufacturer) {
            this.seatManufacturer = seatManufacturer;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
