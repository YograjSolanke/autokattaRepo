package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-005 on 27/9/17.
 */

public class VehicleOfferRecivedResponse {

        @SerializedName("Success")
        @Expose
        private List<Success> success = null;
        @SerializedName("Error")
        @Expose
        private Object error;

        public List<Success> getSuccess() {
            return success;
        }

        public void setSuccess(List<Success> success) {
            this.success = success;
        }

        public Object getError() {
            return error;
        }

        public void setError(Object error) {
            this.error = error;
        }


    public class Success {

        @SerializedName("MessageID")
        @Expose
        private Integer messageID;
        @SerializedName("vehicleId")
        @Expose
        private Integer vehicleId;
        @SerializedName("veihicletitle")
        @Expose
        private String veihicletitle;
        @SerializedName("subcategory")
        @Expose
        private String subcategory;
        @SerializedName("air_condition")
        @Expose
        private String airCondition;
        @SerializedName("application")
        @Expose
        private String application;
        @SerializedName("boat_type")
        @Expose
        private String boatType;
        @SerializedName("bodyManufacturer")
        @Expose
        private String bodyManufacturer;
        @SerializedName("body_type")
        @Expose
        private String bodyType;
        @SerializedName("Boon")
        @Expose
        private String boon;
        @SerializedName("brakes")
        @Expose
        private String brakes;
        @SerializedName("bus_type")
        @Expose
        private String busType;
        @SerializedName("callcount")
        @Expose
        private Integer callcount;
        @SerializedName("chassis_no")
        @Expose
        private String chassisNo;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("contact_no")
        @Expose
        private String contactNo;
        @SerializedName("date_1")
        @Expose
        private String date1;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("drive")
        @Expose
        private String drive;
        @SerializedName("emissionVersion")
        @Expose
        private String emissionVersion;
        @SerializedName("engine_no")
        @Expose
        private String engineNo;
        @SerializedName("exchangeStatus")
        @Expose
        private String exchangeStatus;
        @SerializedName("fiananceExchange")
        @Expose
        private String fiananceExchange;
        @SerializedName("financeStatus")
        @Expose
        private String financeStatus;
        @SerializedName("finance_req")
        @Expose
        private String financeReq;
        @SerializedName("fitness_validity_1")
        @Expose
        private Object fitnessValidity1;
        @SerializedName("fitness_validity")
        @Expose
        private String fitnessValidity;
        @SerializedName("fitness_yesno")
        @Expose
        private String fitnessYesno;
        @SerializedName("fual_type")
        @Expose
        private String fualType;
        @SerializedName("hp_capacity")
        @Expose
        private String hpCapacity;
        @SerializedName("Hrs_running")
        @Expose
        private String hrsRunning;
        @SerializedName("hypothication")
        @Expose
        private String hypothication;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("implements")
        @Expose
        private String _implements;
        @SerializedName("insuranceDate")
        @Expose
        private String insuranceDate;
        @SerializedName("insurance_idv")
        @Expose
        private String insuranceIdv;
        @SerializedName("insurance_valid")
        @Expose
        private String insuranceValid;
        @SerializedName("invoice")
        @Expose
        private String invoice;
        @SerializedName("JIB")
        @Expose
        private String jIB;
        @SerializedName("kms_running")
        @Expose
        private Double kmsRunning;
        @SerializedName("location_city")
        @Expose
        private String locationCity;
        @SerializedName("location_country")
        @Expose
        private String locationCountry;
        @SerializedName("location_state")
        @Expose
        private String locationState;
        @SerializedName("manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("month_of_manufacture")
        @Expose
        private String monthOfManufacture;
        @SerializedName("month_of_registration")
        @Expose
        private String monthOfRegistration;
        @SerializedName("no_of_owners")
        @Expose
        private Integer noOfOwners;
        @SerializedName("permit")
        @Expose
        private String permit;
        @SerializedName("permit_validity_1")
        @Expose
        private Object permitValidity1;
        @SerializedName("permit_validity")
        @Expose
        private String permitValidity;
        @SerializedName("permit_yesno")
        @Expose
        private String permitYesno;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("privacy")
        @Expose
        private String privacy;
        @SerializedName("pump")
        @Expose
        private String pump;
        @SerializedName("rc_available")
        @Expose
        private String rcAvailable;
        @SerializedName("keyword")
        @Expose
        private String keyword;

        public Integer getMessageID() {
            return messageID;
        }

        public void setMessageID(Integer messageID) {
            this.messageID = messageID;
        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getVeihicletitle() {
            return veihicletitle;
        }

        public void setVeihicletitle(String veihicletitle) {
            this.veihicletitle = veihicletitle;
        }

        public String getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public String getAirCondition() {
            return airCondition;
        }

        public void setAirCondition(String airCondition) {
            this.airCondition = airCondition;
        }

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }

        public String getBoatType() {
            return boatType;
        }

        public void setBoatType(String boatType) {
            this.boatType = boatType;
        }

        public String getBodyManufacturer() {
            return bodyManufacturer;
        }

        public void setBodyManufacturer(String bodyManufacturer) {
            this.bodyManufacturer = bodyManufacturer;
        }

        public String getBodyType() {
            return bodyType;
        }

        public void setBodyType(String bodyType) {
            this.bodyType = bodyType;
        }

        public String getBoon() {
            return boon;
        }

        public void setBoon(String boon) {
            this.boon = boon;
        }

        public String getBrakes() {
            return brakes;
        }

        public void setBrakes(String brakes) {
            this.brakes = brakes;
        }

        public String getBusType() {
            return busType;
        }

        public void setBusType(String busType) {
            this.busType = busType;
        }

        public Integer getCallcount() {
            return callcount;
        }

        public void setCallcount(Integer callcount) {
            this.callcount = callcount;
        }

        public String getChassisNo() {
            return chassisNo;
        }

        public void setChassisNo(String chassisNo) {
            this.chassisNo = chassisNo;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getDate1() {
            return date1;
        }

        public void setDate1(String date1) {
            this.date1 = date1;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDrive() {
            return drive;
        }

        public void setDrive(String drive) {
            this.drive = drive;
        }

        public String getEmissionVersion() {
            return emissionVersion;
        }

        public void setEmissionVersion(String emissionVersion) {
            this.emissionVersion = emissionVersion;
        }

        public String getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(String engineNo) {
            this.engineNo = engineNo;
        }

        public String getExchangeStatus() {
            return exchangeStatus;
        }

        public void setExchangeStatus(String exchangeStatus) {
            this.exchangeStatus = exchangeStatus;
        }

        public String getFiananceExchange() {
            return fiananceExchange;
        }

        public void setFiananceExchange(String fiananceExchange) {
            this.fiananceExchange = fiananceExchange;
        }

        public String getFinanceStatus() {
            return financeStatus;
        }

        public void setFinanceStatus(String financeStatus) {
            this.financeStatus = financeStatus;
        }

        public String getFinanceReq() {
            return financeReq;
        }

        public void setFinanceReq(String financeReq) {
            this.financeReq = financeReq;
        }

        public Object getFitnessValidity1() {
            return fitnessValidity1;
        }

        public void setFitnessValidity1(Object fitnessValidity1) {
            this.fitnessValidity1 = fitnessValidity1;
        }

        public String getFitnessValidity() {
            return fitnessValidity;
        }

        public void setFitnessValidity(String fitnessValidity) {
            this.fitnessValidity = fitnessValidity;
        }

        public String getFitnessYesno() {
            return fitnessYesno;
        }

        public void setFitnessYesno(String fitnessYesno) {
            this.fitnessYesno = fitnessYesno;
        }

        public String getFualType() {
            return fualType;
        }

        public void setFualType(String fualType) {
            this.fualType = fualType;
        }

        public String getHpCapacity() {
            return hpCapacity;
        }

        public void setHpCapacity(String hpCapacity) {
            this.hpCapacity = hpCapacity;
        }

        public String getHrsRunning() {
            return hrsRunning;
        }

        public void setHrsRunning(String hrsRunning) {
            this.hrsRunning = hrsRunning;
        }

        public String getHypothication() {
            return hypothication;
        }

        public void setHypothication(String hypothication) {
            this.hypothication = hypothication;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImplements() {
            return _implements;
        }

        public void setImplements(String _implements) {
            this._implements = _implements;
        }

        public String getInsuranceDate() {
            return insuranceDate;
        }

        public void setInsuranceDate(String insuranceDate) {
            this.insuranceDate = insuranceDate;
        }

        public String getInsuranceIdv() {
            return insuranceIdv;
        }

        public void setInsuranceIdv(String insuranceIdv) {
            this.insuranceIdv = insuranceIdv;
        }

        public String getInsuranceValid() {
            return insuranceValid;
        }

        public void setInsuranceValid(String insuranceValid) {
            this.insuranceValid = insuranceValid;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public String getJIB() {
            return jIB;
        }

        public void setJIB(String jIB) {
            this.jIB = jIB;
        }

        public Double getKmsRunning() {
            return kmsRunning;
        }

        public void setKmsRunning(Double kmsRunning) {
            this.kmsRunning = kmsRunning;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public String getLocationCountry() {
            return locationCountry;
        }

        public void setLocationCountry(String locationCountry) {
            this.locationCountry = locationCountry;
        }

        public String getLocationState() {
            return locationState;
        }

        public void setLocationState(String locationState) {
            this.locationState = locationState;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getMonthOfManufacture() {
            return monthOfManufacture;
        }

        public void setMonthOfManufacture(String monthOfManufacture) {
            this.monthOfManufacture = monthOfManufacture;
        }

        public String getMonthOfRegistration() {
            return monthOfRegistration;
        }

        public void setMonthOfRegistration(String monthOfRegistration) {
            this.monthOfRegistration = monthOfRegistration;
        }

        public Integer getNoOfOwners() {
            return noOfOwners;
        }

        public void setNoOfOwners(Integer noOfOwners) {
            this.noOfOwners = noOfOwners;
        }

        public String getPermit() {
            return permit;
        }

        public void setPermit(String permit) {
            this.permit = permit;
        }

        public Object getPermitValidity1() {
            return permitValidity1;
        }

        public void setPermitValidity1(Object permitValidity1) {
            this.permitValidity1 = permitValidity1;
        }

        public String getPermitValidity() {
            return permitValidity;
        }

        public void setPermitValidity(String permitValidity) {
            this.permitValidity = permitValidity;
        }

        public String getPermitYesno() {
            return permitYesno;
        }

        public void setPermitYesno(String permitYesno) {
            this.permitYesno = permitYesno;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public String getPump() {
            return pump;
        }

        public void setPump(String pump) {
            this.pump = pump;
        }

        public String getRcAvailable() {
            return rcAvailable;
        }

        public void setRcAvailable(String rcAvailable) {
            this.rcAvailable = rcAvailable;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

    }
}
