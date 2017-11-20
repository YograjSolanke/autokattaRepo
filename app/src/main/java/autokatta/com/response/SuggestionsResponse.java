package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 4/11/17.
 */

public class SuggestionsResponse {

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

        @SerializedName("WallSuggestions")
        @Expose
        private List<WallSuggestion> wallSuggestions = null;

        public List<WallSuggestion> getWallSuggestions() {
            return wallSuggestions;
        }

        public void setWallSuggestions(List<WallSuggestion> wallSuggestions) {
            this.wallSuggestions = wallSuggestions;
        }


        public class WallSuggestion {

            @SerializedName("Layout")
            @Expose
            private Integer layout;
            //*************************************************************
            /* Vehicle Based Suggestions*/
            @SerializedName("UploadVehicleID")
            @Expose
            private Integer uploadVehicleID;
            @SerializedName("AirCondition")
            @Expose
            private String airCondition;
            @SerializedName("Application")
            @Expose
            private String application;
            @SerializedName("BoatType")
            @Expose
            private String boatType;
            @SerializedName("BodyManufacturer")
            @Expose
            private String bodyManufacturer;
            @SerializedName("BodyType")
            @Expose
            private String bodyType;
            @SerializedName("Boon")
            @Expose
            private String boon;
            @SerializedName("Brakes")
            @Expose
            private String brakes;
            @SerializedName("BusType")
            @Expose
            private String busType;
            @SerializedName("CallCount")
            @Expose
            private String callCount;
            @SerializedName("Category")
            @Expose
            private String category;
            @SerializedName("ChassisNo")
            @Expose
            private String chassisNo;
            @SerializedName("Color")
            @Expose
            private String color;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("Date")
            @Expose
            private String date;
            @SerializedName("date_1")
            @Expose
            private String date1;
            @SerializedName("Drive")
            @Expose
            private String drive;
            @SerializedName("EmissionVersion")
            @Expose
            private String emissionVersion;
            @SerializedName("EngineNo")
            @Expose
            private String engineNo;
            @SerializedName("ExchangeStatus")
            @Expose
            private String exchangeStatus;
            @SerializedName("FinanceExchange")
            @Expose
            private String financeExchange;
            @SerializedName("FinanceStatus")
            @Expose
            private String financeStatus;
            @SerializedName("FinanceRequest")
            @Expose
            private String financeRequest;
            @SerializedName("FitnessValidity")
            @Expose
            private String fitnessValidity;
            @SerializedName("FitnessValidity_1")
            @Expose
            private String fitnessValidity1;
            @SerializedName("FitnessYesNo")
            @Expose
            private String fitnessYesNo;
            @SerializedName("FuelType")
            @Expose
            private String fuelType;
            @SerializedName("HPCapacity")
            @Expose
            private String hPCapacity;
            @SerializedName("HrsRunning")
            @Expose
            private String hrsRunning;
            @SerializedName("Hypothication")
            @Expose
            private String hypothication;
            @SerializedName("Image")
            @Expose
            private String image;
            @SerializedName("Implements")
            @Expose
            private String _implements;
            @SerializedName("InsuranceDate")
            @Expose
            private String insuranceDate;
            @SerializedName("InsuranceIdv")
            @Expose
            private String insuranceIdv;
            @SerializedName("InsuranceValid")
            @Expose
            private String insuranceValid;
            @SerializedName("Invoice")
            @Expose
            private String invoice;
            @SerializedName("JIB")
            @Expose
            private String jIB;
            @SerializedName("KmsRunning")
            @Expose
            private Integer kmsRunning;
            @SerializedName("LocationCity")
            @Expose
            private String locationCity;
            @SerializedName("LocationCountry")
            @Expose
            private String locationCountry;
            @SerializedName("LocationState")
            @Expose
            private String locationState;
            @SerializedName("LocationAddress")
            @Expose
            private String locationAddress;
            @SerializedName("Manufacturer")
            @Expose
            private String manufacturer;
            @SerializedName("Model")
            @Expose
            private String model;
            @SerializedName("MonthOfManufacturer")
            @Expose
            private String monthOfManufacturer;
            @SerializedName("MonthOfRegistration")
            @Expose
            private String monthOfRegistration;
            @SerializedName("NoOfOwners")
            @Expose
            private Integer noOfOwners;
            @SerializedName("Permit")
            @Expose
            private String permit;
            @SerializedName("PermitValidity")
            @Expose
            private String permitValidity;
            @SerializedName("PermitValidity_1")
            @Expose
            private String permitValidity1;
            @SerializedName("PermitYesNo")
            @Expose
            private String permitYesNo;
            @SerializedName("Price")
            @Expose
            private String price;
            @SerializedName("Privacy")
            @Expose
            private String privacy;
            @SerializedName("Pump")
            @Expose
            private String pump;
            @SerializedName("RCAvailable")
            @Expose
            private String rCAvailable;
            @SerializedName("RegistrationNumber")
            @Expose
            private String registrationNumber;
            @SerializedName("ReservePrice")
            @Expose
            private String reservePrice;
            @SerializedName("RTOCity")
            @Expose
            private String rTOCity;
            @SerializedName("RVType")
            @Expose
            private String rVType;
            @SerializedName("SeatingCapacity")
            @Expose
            private String seatingCapacity;
            @SerializedName("SeatManufacturer")
            @Expose
            private String seatManufacturer;
            @SerializedName("StartPrice")
            @Expose
            private String startPrice;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("Steering")
            @Expose
            private String steering;
            @SerializedName("SubCategory")
            @Expose
            private String subCategory;
            @SerializedName("TaxPaidUpto")
            @Expose
            private String taxPaidUpto;
            @SerializedName("TaxValidity")
            @Expose
            private String taxValidity;
            @SerializedName("TaxValidity_1")
            @Expose
            private String taxValidity1;
            @SerializedName("Titile")
            @Expose
            private String titile;
            @SerializedName("Transmission")
            @Expose
            private String transmission;
            @SerializedName("TyreCondition")
            @Expose
            private String tyreCondition;
            @SerializedName("Version")
            @Expose
            private String version;
            @SerializedName("ViewCount")
            @Expose
            private String viewCount;
            @SerializedName("YearOfManufaturer")
            @Expose
            private String yearOfManufaturer;
            @SerializedName("YearOfRegistration")
            @Expose
            private String yearOfRegistration;
            @SerializedName("StockType")
            @Expose
            private String stockType;
            @SerializedName("Latitude")
            @Expose
            private Double latitude;
            @SerializedName("Longitude")
            @Expose
            private Double longitude;
            @SerializedName("CloneID")
            @Expose
            private String cloneID;
            @SerializedName("Distance")
            @Expose
            private Integer distance;

            public Integer getUploadVehicleID() {
                return uploadVehicleID;
            }

            public void setUploadVehicleID(Integer uploadVehicleID) {
                this.uploadVehicleID = uploadVehicleID;
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

            public String getCallCount() {
                return callCount;
            }

            public void setCallCount(String callCount) {
                this.callCount = callCount;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

            public String getDate1() {
                return date1;
            }

            public void setDate1(String date1) {
                this.date1 = date1;
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

            public String getFinanceExchange() {
                return financeExchange;
            }

            public void setFinanceExchange(String financeExchange) {
                this.financeExchange = financeExchange;
            }

            public String getFinanceStatus() {
                return financeStatus;
            }

            public void setFinanceStatus(String financeStatus) {
                this.financeStatus = financeStatus;
            }

            public String getFinanceRequest() {
                return financeRequest;
            }

            public void setFinanceRequest(String financeRequest) {
                this.financeRequest = financeRequest;
            }

            public String getFitnessValidity() {
                return fitnessValidity;
            }

            public void setFitnessValidity(String fitnessValidity) {
                this.fitnessValidity = fitnessValidity;
            }

            public String getFitnessValidity1() {
                return fitnessValidity1;
            }

            public void setFitnessValidity1(String fitnessValidity1) {
                this.fitnessValidity1 = fitnessValidity1;
            }

            public String getFitnessYesNo() {
                return fitnessYesNo;
            }

            public void setFitnessYesNo(String fitnessYesNo) {
                this.fitnessYesNo = fitnessYesNo;
            }

            public String getFuelType() {
                return fuelType;
            }

            public void setFuelType(String fuelType) {
                this.fuelType = fuelType;
            }

            public String getHPCapacity() {
                return hPCapacity;
            }

            public void setHPCapacity(String hPCapacity) {
                this.hPCapacity = hPCapacity;
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

            public Integer getKmsRunning() {
                return kmsRunning;
            }

            public void setKmsRunning(Integer kmsRunning) {
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

            public String getLocationAddress() {
                return locationAddress;
            }

            public void setLocationAddress(String locationAddress) {
                this.locationAddress = locationAddress;
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

            public String getMonthOfManufacturer() {
                return monthOfManufacturer;
            }

            public void setMonthOfManufacturer(String monthOfManufacturer) {
                this.monthOfManufacturer = monthOfManufacturer;
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

            public String getPermitValidity() {
                return permitValidity;
            }

            public void setPermitValidity(String permitValidity) {
                this.permitValidity = permitValidity;
            }

            public String getPermitValidity1() {
                return permitValidity1;
            }

            public void setPermitValidity1(String permitValidity1) {
                this.permitValidity1 = permitValidity1;
            }

            public String getPermitYesNo() {
                return permitYesNo;
            }

            public void setPermitYesNo(String permitYesNo) {
                this.permitYesNo = permitYesNo;
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

            public String getRCAvailable() {
                return rCAvailable;
            }

            public void setRCAvailable(String rCAvailable) {
                this.rCAvailable = rCAvailable;
            }

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
            }

            public String getReservePrice() {
                return reservePrice;
            }

            public void setReservePrice(String reservePrice) {
                this.reservePrice = reservePrice;
            }

            public String getRTOCity() {
                return rTOCity;
            }

            public void setRTOCity(String rTOCity) {
                this.rTOCity = rTOCity;
            }

            public String getRVType() {
                return rVType;
            }

            public void setRVType(String rVType) {
                this.rVType = rVType;
            }

            public String getSeatingCapacity() {
                return seatingCapacity;
            }

            public void setSeatingCapacity(String seatingCapacity) {
                this.seatingCapacity = seatingCapacity;
            }

            public String getSeatManufacturer() {
                return seatManufacturer;
            }

            public void setSeatManufacturer(String seatManufacturer) {
                this.seatManufacturer = seatManufacturer;
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

            public String getSteering() {
                return steering;
            }

            public void setSteering(String steering) {
                this.steering = steering;
            }

            public String getSubCategory() {
                return subCategory;
            }

            public void setSubCategory(String subCategory) {
                this.subCategory = subCategory;
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

            public String getTaxValidity1() {
                return taxValidity1;
            }

            public void setTaxValidity1(String taxValidity1) {
                this.taxValidity1 = taxValidity1;
            }

            public String getTitile() {
                return titile;
            }

            public void setTitile(String titile) {
                this.titile = titile;
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

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getViewCount() {
                return viewCount;
            }

            public void setViewCount(String viewCount) {
                this.viewCount = viewCount;
            }

            public String getYearOfManufaturer() {
                return yearOfManufaturer;
            }

            public void setYearOfManufaturer(String yearOfManufaturer) {
                this.yearOfManufaturer = yearOfManufaturer;
            }

            public String getYearOfRegistration() {
                return yearOfRegistration;
            }

            public void setYearOfRegistration(String yearOfRegistration) {
                this.yearOfRegistration = yearOfRegistration;
            }

            public String getStockType() {
                return stockType;
            }

            public void setStockType(String stockType) {
                this.stockType = stockType;
            }

            public Double getLatitude() {
                return latitude;
            }

            public void setLatitude(Double latitude) {
                this.latitude = latitude;
            }

            public Double getLongitude() {
                return longitude;
            }

            public void setLongitude(Double longitude) {
                this.longitude = longitude;
            }

            public String getCloneID() {
                return cloneID;
            }

            public void setCloneID(String cloneID) {
                this.cloneID = cloneID;
            }

            public Integer getDistance() {
                return distance;
            }

            public void setDistance(Integer distance) {
                this.distance = distance;
            }

            //*************************************************************
    /*Profile Based Suggestions*/

            @SerializedName("contact")
            @Expose
            private String contact;
            @SerializedName("profile_pic")
            @Expose
            private String profilePic;
            @SerializedName("username")
            @Expose
            private String username;

            public Integer getLayout() {
                return layout;
            }

            public void setLayout(Integer layout) {
                this.layout = layout;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
