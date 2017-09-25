package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 25/9/17.
 */

public class VehicleForCompareResponse {

    @SerializedName("Success")
    @Expose
    private Success success;
    @SerializedName("Error")
    @Expose
    private Object error;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }


    public class Success {

        @SerializedName("UsedVehicle")
        @Expose
        private List<UsedVehicle> usedVehicle = null;

        public List<UsedVehicle> getUsedVehicle() {
            return usedVehicle;
        }

        public void setUsedVehicle(List<UsedVehicle> usedVehicle) {
            this.usedVehicle = usedVehicle;
        }


        public class UsedVehicle {

            @SerializedName("vehicle_id")
            @Expose
            private Integer vehicleId;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("contact_no")
            @Expose
            private String contactNo;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("sub_category")
            @Expose
            private String subCategory;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("manufacturer")
            @Expose
            private String manufacturer;
            @SerializedName("Version")
            @Expose
            private String version;
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("location_city")
            @Expose
            private String locationCity;
            @SerializedName("location_state")
            @Expose
            private String locationState;
            @SerializedName("location_country")
            @Expose
            private String locationCountry;
            @SerializedName("month_of_registration")
            @Expose
            private String monthOfRegistration;
            @SerializedName("year_of_registration")
            @Expose
            private String yearOfRegistration;
            @SerializedName("month_of_manufacture")
            @Expose
            private String monthOfManufacture;
            @SerializedName("year_of_manufacture")
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
            @SerializedName("insuranceDate")
            @Expose
            private String insuranceDate;
            @SerializedName("insurance_idv")
            @Expose
            private String insuranceIdv;
            @SerializedName("emissionVersion")
            @Expose
            private String emissionVersion;
            @SerializedName("financeStatus")
            @Expose
            private String financeStatus;
            @SerializedName("exchangeStatus")
            @Expose
            private String exchangeStatus;
            @SerializedName("tax_validity_1")
            @Expose
            private String taxValidity1;
            @SerializedName("tax_validity")
            @Expose
            private String taxValidity;
            @SerializedName("tax_paid_upto")
            @Expose
            private String taxPaidUpto;
            @SerializedName("fitness_validity_1")
            @Expose
            private String fitnessValidity1;
            @SerializedName("fitness_validity")
            @Expose
            private String fitnessValidity;
            @SerializedName("permit_validity_1")
            @Expose
            private String permitValidity1;
            @SerializedName("permit_validity")
            @Expose
            private String permitValidity;
            @SerializedName("permit_yesno")
            @Expose
            private String permitYesno;
            @SerializedName("fitness_yesno")
            @Expose
            private String fitnessYesno;
            @SerializedName("fual_type")
            @Expose
            private String fualType;
            @SerializedName("seating_capacity")
            @Expose
            private String seatingCapacity;
            @SerializedName("permit")
            @Expose
            private String permit;
            @SerializedName("fiananceExchange")
            @Expose
            private String fiananceExchange;
            @SerializedName("kms_running")
            @Expose
            private Double kmsRunning;
            @SerializedName("Hrs_running")
            @Expose
            private String hrsRunning;
            @SerializedName("no_of_owners")
            @Expose
            private Integer noOfOwners;
            @SerializedName("bodyManufacturer")
            @Expose
            private String bodyManufacturer;
            @SerializedName("seatManufacturer")
            @Expose
            private String seatManufacturer;
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
            @SerializedName("application")
            @Expose
            private String application;
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
            @SerializedName("finance_req")
            @Expose
            private String financeReq;
            @SerializedName("privacy")
            @Expose
            private String privacy;
            @SerializedName("hp_capacity")
            @Expose
            private String hpCapacity;
            @SerializedName("JIB")
            @Expose
            private String jIB;
            @SerializedName("Boon")
            @Expose
            private String boon;
            @SerializedName("viewcount")
            @Expose
            private Integer viewcount;
            @SerializedName("callcount")
            @Expose
            private Integer callcount;
            @SerializedName("date_1")
            @Expose
            private String date1;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("start_price")
            @Expose
            private String startPrice;
            @SerializedName("reserve_price")
            @Expose
            private String reservePrice;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("brakes")
            @Expose
            private String brakes;
            @SerializedName("pump")
            @Expose
            private String pump;
            @SerializedName("steering")
            @Expose
            private String steering;
            @SerializedName("YearOfManufaturer")
            @Expose
            private Object yearOfManufaturer;
            @SerializedName("enquiryCount")
            @Expose
            private Integer enquiryCount;

            public Integer getVehicleId() {
                return vehicleId;
            }

            public void setVehicleId(Integer vehicleId) {
                this.vehicleId = vehicleId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
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

            public String getManufacturer() {
                return manufacturer;
            }

            public void setManufacturer(String manufacturer) {
                this.manufacturer = manufacturer;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
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

            public String getMonthOfRegistration() {
                return monthOfRegistration;
            }

            public void setMonthOfRegistration(String monthOfRegistration) {
                this.monthOfRegistration = monthOfRegistration;
            }

            public String getYearOfRegistration() {
                return yearOfRegistration;
            }

            public void setYearOfRegistration(String yearOfRegistration) {
                this.yearOfRegistration = yearOfRegistration;
            }

            public String getMonthOfManufacture() {
                return monthOfManufacture;
            }

            public void setMonthOfManufacture(String monthOfManufacture) {
                this.monthOfManufacture = monthOfManufacture;
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

            public String getEmissionVersion() {
                return emissionVersion;
            }

            public void setEmissionVersion(String emissionVersion) {
                this.emissionVersion = emissionVersion;
            }

            public String getFinanceStatus() {
                return financeStatus;
            }

            public void setFinanceStatus(String financeStatus) {
                this.financeStatus = financeStatus;
            }

            public String getExchangeStatus() {
                return exchangeStatus;
            }

            public void setExchangeStatus(String exchangeStatus) {
                this.exchangeStatus = exchangeStatus;
            }

            public String getTaxValidity1() {
                return taxValidity1;
            }

            public void setTaxValidity1(String taxValidity1) {
                this.taxValidity1 = taxValidity1;
            }

            public String getTaxValidity() {
                return taxValidity;
            }

            public void setTaxValidity(String taxValidity) {
                this.taxValidity = taxValidity;
            }

            public String getTaxPaidUpto() {
                return taxPaidUpto;
            }

            public void setTaxPaidUpto(String taxPaidUpto) {
                this.taxPaidUpto = taxPaidUpto;
            }

            public String getFitnessValidity1() {
                return fitnessValidity1;
            }

            public void setFitnessValidity1(String fitnessValidity1) {
                this.fitnessValidity1 = fitnessValidity1;
            }

            public String getFitnessValidity() {
                return fitnessValidity;
            }

            public void setFitnessValidity(String fitnessValidity) {
                this.fitnessValidity = fitnessValidity;
            }

            public String getPermitValidity1() {
                return permitValidity1;
            }

            public void setPermitValidity1(String permitValidity1) {
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

            public String getFiananceExchange() {
                return fiananceExchange;
            }

            public void setFiananceExchange(String fiananceExchange) {
                this.fiananceExchange = fiananceExchange;
            }

            public Double getKmsRunning() {
                return kmsRunning;
            }

            public void setKmsRunning(Double kmsRunning) {
                this.kmsRunning = kmsRunning;
            }

            public String getHrsRunning() {
                return hrsRunning;
            }

            public void setHrsRunning(String hrsRunning) {
                this.hrsRunning = hrsRunning;
            }

            public Integer getNoOfOwners() {
                return noOfOwners;
            }

            public void setNoOfOwners(Integer noOfOwners) {
                this.noOfOwners = noOfOwners;
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

            public String getApplication() {
                return application;
            }

            public void setApplication(String application) {
                this.application = application;
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

            public String getFinanceReq() {
                return financeReq;
            }

            public void setFinanceReq(String financeReq) {
                this.financeReq = financeReq;
            }

            public String getPrivacy() {
                return privacy;
            }

            public void setPrivacy(String privacy) {
                this.privacy = privacy;
            }

            public String getHpCapacity() {
                return hpCapacity;
            }

            public void setHpCapacity(String hpCapacity) {
                this.hpCapacity = hpCapacity;
            }

            public String getJIB() {
                return jIB;
            }

            public void setJIB(String jIB) {
                this.jIB = jIB;
            }

            public String getBoon() {
                return boon;
            }

            public void setBoon(String boon) {
                this.boon = boon;
            }

            public Integer getViewcount() {
                return viewcount;
            }

            public void setViewcount(Integer viewcount) {
                this.viewcount = viewcount;
            }

            public Integer getCallcount() {
                return callcount;
            }

            public void setCallcount(Integer callcount) {
                this.callcount = callcount;
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

            public String getStartPrice() {
                return startPrice;
            }

            public void setStartPrice(String startPrice) {
                this.startPrice = startPrice;
            }

            public String getReservePrice() {
                return reservePrice;
            }

            public void setReservePrice(String reservePrice) {
                this.reservePrice = reservePrice;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getBrakes() {
                return brakes;
            }

            public void setBrakes(String brakes) {
                this.brakes = brakes;
            }

            public String getPump() {
                return pump;
            }

            public void setPump(String pump) {
                this.pump = pump;
            }

            public String getSteering() {
                return steering;
            }

            public void setSteering(String steering) {
                this.steering = steering;
            }

            public Object getYearOfManufaturer() {
                return yearOfManufaturer;
            }

            public void setYearOfManufaturer(Object yearOfManufaturer) {
                this.yearOfManufaturer = yearOfManufaturer;
            }

            public Integer getEnquiryCount() {
                return enquiryCount;
            }

            public void setEnquiryCount(Integer enquiryCount) {
                this.enquiryCount = enquiryCount;
            }

        }
    }
}
