package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by ak-004 on 13/4/17.
 */

public class SellerResponse {

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

        @SerializedName("saved_search")
        @Expose
        private List<SavedSearch> savedSearch = null;

        @SerializedName("matchedResult")

        public List<SavedSearch> getSavedSearch() {
            return savedSearch;
        }

        public void setSavedSearch(List<SavedSearch> savedSearch) {
            this.savedSearch = savedSearch;
        }

        private List<MatchedResult> matchedResult = null;


        public List<MatchedResult> getMatchedResult() {
            return matchedResult;
        }

        public void setMatchedResult(List<MatchedResult> matchedResult) {
            this.matchedResult = matchedResult;
        }


        public class SavedSearch {

            @SerializedName("search_id")
            @Expose
            private Integer searchId;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("manufacturer")
            @Expose
            private String manufacturer;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("version")
            @Expose
            private String version;
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("year_of_manufacture")
            @Expose
            private String yearOfManufacture;
            @SerializedName("rc_available")
            @Expose
            private String rcAvailable;
            @SerializedName("no_of_owners")
            @Expose
            private Integer noOfOwners;
            @SerializedName("air_condition")
            @Expose
            private String airCondition;
            @SerializedName("application")
            @Expose
            private String application;
            @SerializedName("boat_type")
            @Expose
            private String boatType;
            @SerializedName("body_type")
            @Expose
            private String bodyType;
            @SerializedName("bus_type")
            @Expose
            private String busType;
            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("contact_no")
            @Expose
            private String contactNo;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("drive")
            @Expose
            private String drive;
            @SerializedName("finance_req")
            @Expose
            private String financeReq;
            @SerializedName("fitness_validity")
            @Expose
            private String fitnessValidity;
            @SerializedName("fual_type")
            @Expose
            private String fualType;
            @SerializedName("hypothication")
            @Expose
            private String hypothication;
            @SerializedName("implements")
            @Expose
            private String _implements;
            @SerializedName("insurance_valid")
            @Expose
            private String insuranceValid;
            @SerializedName("invoice")
            @Expose
            private String invoice;
            @SerializedName("kms_running")
            @Expose
            private String kmsRunning;
            @SerializedName("Hrs_running")
            @Expose
            private String hrsRunning;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("registration_number")
            @Expose
            private String registrationNumber;
            @SerializedName("location_city")
            @Expose
            private String locationCity;
            @SerializedName("location_city2")
            @Expose
            private String locationCity2;
            @SerializedName("location_city3")
            @Expose
            private String locationCity3;
            @SerializedName("location_city4")
            @Expose
            private String locationCity4;
            @SerializedName("location_city5")
            @Expose
            private String locationCity5;
            @SerializedName("location_state")
            @Expose
            private String locationState;
            @SerializedName("permit")
            @Expose
            private String permit;
            @SerializedName("permit_validity")
            @Expose
            private String permitValidity;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("rto_city2")
            @Expose
            private String rtoCity2;
            @SerializedName("rto_city3")
            @Expose
            private String rtoCity3;
            @SerializedName("rto_city4")
            @Expose
            private String rtoCity4;
            @SerializedName("rto_city5")
            @Expose
            private String rtoCity5;
            @SerializedName("rv_type")
            @Expose
            private String rvType;
            @SerializedName("seating_capacity")
            @Expose
            private String seatingCapacity;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("tax_validity")
            @Expose
            private String taxValidity;
            @SerializedName("transmission")
            @Expose
            private String transmission;
            @SerializedName("tyre_condition")
            @Expose
            private String tyreCondition;
            @SerializedName("year_of_registration")
            @Expose
            private String yearOfRegistration;
            @SerializedName("hpcapacity")
            @Expose
            private String hpcapacity;

            public Integer getSearchId() {
                return searchId;
            }

            public void setSearchId(Integer searchId) {
                this.searchId = searchId;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

            public String getYearOfManufacture() {
                return yearOfManufacture;
            }

            public void setYearOfManufacture(String yearOfManufacture) {
                this.yearOfManufacture = yearOfManufacture;
            }

            public String getRcAvailable() {
                return rcAvailable;
            }

            public void setRcAvailable(String rcAvailable) {
                this.rcAvailable = rcAvailable;
            }

            public Integer getNoOfOwners() {
                return noOfOwners;
            }

            public void setNoOfOwners(Integer noOfOwners) {
                this.noOfOwners = noOfOwners;
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

            public String getBodyType() {
                return bodyType;
            }

            public void setBodyType(String bodyType) {
                this.bodyType = bodyType;
            }

            public String getBusType() {
                return busType;
            }

            public void setBusType(String busType) {
                this.busType = busType;
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

            public String getFinanceReq() {
                return financeReq;
            }

            public void setFinanceReq(String financeReq) {
                this.financeReq = financeReq;
            }

            public String getFitnessValidity() {
                return fitnessValidity;
            }

            public void setFitnessValidity(String fitnessValidity) {
                this.fitnessValidity = fitnessValidity;
            }

            public String getFualType() {
                return fualType;
            }

            public void setFualType(String fualType) {
                this.fualType = fualType;
            }

            public String getHypothication() {
                return hypothication;
            }

            public void setHypothication(String hypothication) {
                this.hypothication = hypothication;
            }

            public String getImplements() {
                return _implements;
            }

            public void setImplements(String _implements) {
                this._implements = _implements;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
            }

            public String getLocationCity() {
                return locationCity;
            }

            public void setLocationCity(String locationCity) {
                this.locationCity = locationCity;
            }

            public String getLocationCity2() {
                return locationCity2;
            }

            public void setLocationCity2(String locationCity2) {
                this.locationCity2 = locationCity2;
            }

            public String getLocationCity3() {
                return locationCity3;
            }

            public void setLocationCity3(String locationCity3) {
                this.locationCity3 = locationCity3;
            }

            public String getLocationCity4() {
                return locationCity4;
            }

            public void setLocationCity4(String locationCity4) {
                this.locationCity4 = locationCity4;
            }

            public String getLocationCity5() {
                return locationCity5;
            }

            public void setLocationCity5(String locationCity5) {
                this.locationCity5 = locationCity5;
            }

            public String getLocationState() {
                return locationState;
            }

            public void setLocationState(String locationState) {
                this.locationState = locationState;
            }

            public String getPermit() {
                return permit;
            }

            public void setPermit(String permit) {
                this.permit = permit;
            }

            public String getPermitValidity() {
                return permitValidity;
            }

            public void setPermitValidity(String permitValidity) {
                this.permitValidity = permitValidity;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getRtoCity2() {
                return rtoCity2;
            }

            public void setRtoCity2(String rtoCity2) {
                this.rtoCity2 = rtoCity2;
            }

            public String getRtoCity3() {
                return rtoCity3;
            }

            public void setRtoCity3(String rtoCity3) {
                this.rtoCity3 = rtoCity3;
            }

            public String getRtoCity4() {
                return rtoCity4;
            }

            public void setRtoCity4(String rtoCity4) {
                this.rtoCity4 = rtoCity4;
            }

            public String getRtoCity5() {
                return rtoCity5;
            }

            public void setRtoCity5(String rtoCity5) {
                this.rtoCity5 = rtoCity5;
            }

            public String getRvType() {
                return rvType;
            }

            public void setRvType(String rvType) {
                this.rvType = rvType;
            }

            public String getSeatingCapacity() {
                return seatingCapacity;
            }

            public void setSeatingCapacity(String seatingCapacity) {
                this.seatingCapacity = seatingCapacity;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTaxValidity() {
                return taxValidity;
            }

            public void setTaxValidity(String taxValidity) {
                this.taxValidity = taxValidity;
            }

            public String getTransmission() {
                return transmission;
            }

            public void setTransmission(String transmission) {
                this.transmission = transmission;
            }

            public String getTyreCondition() {
                return tyreCondition;
            }

            public void setTyreCondition(String tyreCondition) {
                this.tyreCondition = tyreCondition;
            }

            public String getYearOfRegistration() {
                return yearOfRegistration;
            }

            public void setYearOfRegistration(String yearOfRegistration) {
                this.yearOfRegistration = yearOfRegistration;
            }

            public String getHpcapacity() {
                return hpcapacity;
            }

            public void setHpcapacity(String hpcapacity) {
                this.hpcapacity = hpcapacity;
            }

            @Expose
            private List<MatchedResult> matchedResult = null;


            public List<MatchedResult> getMatchedResult() {
                return matchedResult;
            }

            public void setMatchedResult(List<MatchedResult> matchedResult) {
                this.matchedResult = matchedResult;
            }


        }

        public class MatchedResult {


            private Date lastCallDateNew;
            private Date uploaddate;

            public Date getLastCallDateNew() {
                return lastCallDateNew;
            }

            public Date getUploaddate() {
                return uploaddate;
            }

            public void setUploaddate(Date uploaddate) {
                this.uploaddate = uploaddate;
            }

            public void setLastCallDateNew(Date lastCallDateNew) {
                this.lastCallDateNew = lastCallDateNew;
            }

            @SerializedName("search_id")
            @Expose
            private int searchId;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("manufacturer")
            @Expose
            private String manufacturer;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("Version")
            @Expose
            private String version;
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("year_of_manufacture")
            @Expose
            private String yearOfManufacture;
            @SerializedName("rc_available")
            @Expose
            private String rcAvailable;
            @SerializedName("no_of_owners")
            @Expose
            private String noOfOwners;
            @SerializedName("vehicle_id")
            @Expose
            private String vehicleId;
            @SerializedName("air_condition")
            @Expose
            private String airCondition;
            @SerializedName("application")
            @Expose
            private String application;
            @SerializedName("boat_type")
            @Expose
            private String boatType;
            @SerializedName("body_type")
            @Expose
            private String bodyType;
            @SerializedName("bus_type")
            @Expose
            private String busType;
            @SerializedName("callcount")
            @Expose
            private String callcount;
            @SerializedName("chassis_no")
            @Expose
            private String chassisNo;
            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("contact_no")
            @Expose
            private String contactNo;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("drive")
            @Expose
            private String drive;
            @SerializedName("engine_no")
            @Expose
            private String engineNo;
            @SerializedName("finance_req")
            @Expose
            private String financeReq;
            @SerializedName("fitness_validity")
            @Expose
            private String fitnessValidity;
            @SerializedName("fual_type")
            @Expose
            private String fualType;
            @SerializedName("Hrs_running")
            @Expose
            private String hrsRunning;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("registration_number")
            @Expose
            private String registrationNumber;
            @SerializedName("hypothication")
            @Expose
            private String hypothication;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("implements")
            @Expose
            private String _implements;
            @SerializedName("insurance_idv")
            @Expose
            private String insuranceIdv;
            @SerializedName("insurance_valid")
            @Expose
            private String insuranceValid;
            @SerializedName("invoice")
            @Expose
            private String invoice;
            @SerializedName("kms_running")
            @Expose
            private String kmsRunning;
            @SerializedName("location_city")
            @Expose
            private String locationCity;
            @SerializedName("location_country")
            @Expose
            private String locationCountry;
            @SerializedName("location_state")
            @Expose
            private String locationState;
            @SerializedName("month_of_manufacture")
            @Expose
            private String monthOfManufacture;
            @SerializedName("month_of_registration")
            @Expose
            private String monthOfRegistration;
            @SerializedName("permit")
            @Expose
            private String permit;
            @SerializedName("permit_validity")
            @Expose
            private String permitValidity;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("privacy")
            @Expose
            private String privacy;
            @SerializedName("reserve_price")
            @Expose
            private String reservePrice;
            @SerializedName("rv_type")
            @Expose
            private String rvType;
            @SerializedName("seating_capacity")
            @Expose
            private String seatingCapacity;
            @SerializedName("start_price")
            @Expose
            private String startPrice;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("tax_paid_upto")
            @Expose
            private String taxPaidUpto;
            @SerializedName("tax_validity")
            @Expose
            private String taxValidity;
            @SerializedName("transmission")
            @Expose
            private String transmission;
            @SerializedName("tyre_condition")
            @Expose
            private String tyreCondition;
            @SerializedName("viewcount")
            @Expose
            private String viewcount;
            @SerializedName("year_of_registration")
            @Expose
            private String yearOfRegistration;
            @SerializedName("hp_capacity")
            @Expose
            private String hpCapacity;
            @SerializedName("favstatus")
            @Expose
            private String favstatus;
            @SerializedName("lastcall")
            @Expose
            private String lastcall;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("profile_pic")
            @Expose
            private String profilePic;
            @SerializedName("count")
            @Expose
            private Integer count;

            public int getSearchId() {
                return searchId;
            }

            public void setSearchId(int searchId) {
                this.searchId = searchId;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

            public String getYearOfManufacture() {
                return yearOfManufacture;
            }

            public void setYearOfManufacture(String yearOfManufacture) {
                this.yearOfManufacture = yearOfManufacture;
            }

            public String getRcAvailable() {
                return rcAvailable;
            }

            public void setRcAvailable(String rcAvailable) {
                this.rcAvailable = rcAvailable;
            }

            public String getNoOfOwners() {
                return noOfOwners;
            }

            public void setNoOfOwners(String noOfOwners) {
                this.noOfOwners = noOfOwners;
            }

            public String getVehicleId() {
                return vehicleId;
            }

            public void setVehicleId(String vehicleId) {
                this.vehicleId = vehicleId;
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

            public String getBodyType() {
                return bodyType;
            }

            public void setBodyType(String bodyType) {
                this.bodyType = bodyType;
            }

            public String getBusType() {
                return busType;
            }

            public void setBusType(String busType) {
                this.busType = busType;
            }

            public String getCallcount() {
                return callcount;
            }

            public void setCallcount(String callcount) {
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

            public String getEngineNo() {
                return engineNo;
            }

            public void setEngineNo(String engineNo) {
                this.engineNo = engineNo;
            }

            public String getFinanceReq() {
                return financeReq;
            }

            public void setFinanceReq(String financeReq) {
                this.financeReq = financeReq;
            }

            public String getFitnessValidity() {
                return fitnessValidity;
            }

            public void setFitnessValidity(String fitnessValidity) {
                this.fitnessValidity = fitnessValidity;
            }

            public String getFualType() {
                return fualType;
            }

            public void setFualType(String fualType) {
                this.fualType = fualType;
            }

            public String getHrsRunning() {
                return hrsRunning;
            }

            public void setHrsRunning(String hrsRunning) {
                this.hrsRunning = hrsRunning;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
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

            public String getKmsRunning() {
                return kmsRunning;
            }

            public void setKmsRunning(String kmsRunning) {
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

            public String getPermit() {
                return permit;
            }

            public void setPermit(String permit) {
                this.permit = permit;
            }

            public String getPermitValidity() {
                return permitValidity;
            }

            public void setPermitValidity(String permitValidity) {
                this.permitValidity = permitValidity;
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

            public String getReservePrice() {
                return reservePrice;
            }

            public void setReservePrice(String reservePrice) {
                this.reservePrice = reservePrice;
            }

            public String getRvType() {
                return rvType;
            }

            public void setRvType(String rvType) {
                this.rvType = rvType;
            }

            public String getSeatingCapacity() {
                return seatingCapacity;
            }

            public void setSeatingCapacity(String seatingCapacity) {
                this.seatingCapacity = seatingCapacity;
            }

            public String getStartPrice() {
                return startPrice;
            }

            public void setStartPrice(String startPrice) {
                this.startPrice = startPrice;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTaxPaidUpto() {
                return taxPaidUpto;
            }

            public void setTaxPaidUpto(String taxPaidUpto) {
                this.taxPaidUpto = taxPaidUpto;
            }

            public String getTaxValidity() {
                return taxValidity;
            }

            public void setTaxValidity(String taxValidity) {
                this.taxValidity = taxValidity;
            }

            public String getTransmission() {
                return transmission;
            }

            public void setTransmission(String transmission) {
                this.transmission = transmission;
            }

            public String getTyreCondition() {
                return tyreCondition;
            }

            public void setTyreCondition(String tyreCondition) {
                this.tyreCondition = tyreCondition;
            }

            public String getViewcount() {
                return viewcount;
            }

            public void setViewcount(String viewcount) {
                this.viewcount = viewcount;
            }

            public String getYearOfRegistration() {
                return yearOfRegistration;
            }

            public void setYearOfRegistration(String yearOfRegistration) {
                this.yearOfRegistration = yearOfRegistration;
            }

            public String getHpCapacity() {
                return hpCapacity;
            }

            public void setHpCapacity(String hpCapacity) {
                this.hpCapacity = hpCapacity;
            }

            public String getFavstatus() {
                return favstatus;
            }

            public void setFavstatus(String favstatus) {
                this.favstatus = favstatus;
            }

            public String getLastcall() {
                return lastcall;
            }

            public void setLastcall(String lastcall) {
                this.lastcall = lastcall;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
            }

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }


        }


    }

}
