package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 9/5/17.
 */

public class GetInventoryResponse {
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

        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
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
        @SerializedName("tax_validity")
        @Expose
        private String taxValidity;
        @SerializedName("tax_paid_upto")
        @Expose
        private String taxPaidUpto;
        @SerializedName("fitness_validity")
        @Expose
        private String fitnessValidity;
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
        private String kmsRunning;
        @SerializedName("Hrs_running")
        @Expose
        private String hrsRunning;
        @SerializedName("no_of_owners")
        @Expose
        private String noOfOwners;
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
        private String viewcount;
        @SerializedName("callcount")
        @Expose
        private String callcount;
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
        @SerializedName("InventoryType")
        @Expose
        private String inventoryType;

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

        public String getNoOfOwners() {
            return noOfOwners;
        }

        public void setNoOfOwners(String noOfOwners) {
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

        public String getViewcount() {
            return viewcount;
        }

        public void setViewcount(String viewcount) {
            this.viewcount = viewcount;
        }

        public String getCallcount() {
            return callcount;
        }

        public void setCallcount(String callcount) {
            this.callcount = callcount;
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

        public String getInventoryType() {
            return inventoryType;
        }

        public void setInventoryType(String inventoryType) {
            this.inventoryType = inventoryType;
        }


        /*
        Services...
         */
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("details")
        @Expose
        private String details;
        @SerializedName("tags")
        @Expose
        private String tags;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("brandtags")
        @Expose
        private String brandtags;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("StoreContact")
        @Expose
        private String storeContact;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getBrandtags() {
            return brandtags;
        }

        public void setBrandtags(String brandtags) {
            this.brandtags = brandtags;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getStoreContact() {
            return storeContact;
        }

        public void setStoreContact(String storeContact) {
            this.storeContact = storeContact;
        }

        /*
        Products...
         */
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("product_type")
        @Expose
        private String productType;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_details")
        @Expose
        private String productDetails;
        @SerializedName("product_tags")
        @Expose
        private String productTags;
        @SerializedName("modified_date")
        @Expose
        private String modifiedDate;
        @SerializedName("price_rating")
        @Expose
        private String priceRating;
        @SerializedName("quality_rating")
        @Expose
        private String qualityRating;
        @SerializedName("stock_rating")
        @Expose
        private String stockRating;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
        }

        public String getProductTags() {
            return productTags;
        }

        public void setProductTags(String productTags) {
            this.productTags = productTags;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getPriceRating() {
            return priceRating;
        }

        public void setPriceRating(String priceRating) {
            this.priceRating = priceRating;
        }

        public String getQualityRating() {
            return qualityRating;
        }

        public void setQualityRating(String qualityRating) {
            this.qualityRating = qualityRating;
        }

        public String getStockRating() {
            return stockRating;
        }

        public void setStockRating(String stockRating) {
            this.stockRating = stockRating;
        }


        /* New Vehicle Response */

        @SerializedName("NewVehicleID")
        @Expose
        private Integer newVehicleID;
        @SerializedName("ThreePointLinkage")
        @Expose
        private String threePointLinkage;
        @SerializedName("ABS")
        @Expose
        private String aBS;
        @SerializedName("AdditionalInformation")
        @Expose
        private String additionalInformation;
        @SerializedName("AirBags")
        @Expose
        private String airBags;
        @SerializedName("AirCleaner")
        @Expose
        private String airCleaner;
        /*@SerializedName("AirCondition")
        @Expose
        private String airCondition;*/
        @SerializedName("AlternateFuelType")
        @Expose
        private String alternateFuelType;
        @SerializedName("AuxiliaryHydValve")
        @Expose
        private String auxiliaryHydValve;
        @SerializedName("AxleConfiguration")
        @Expose
        private String axleConfiguration;
        @SerializedName("BackUpTorque")
        @Expose
        private String backUpTorque;
        @SerializedName("BodyOptions")
        @Expose
        private String bodyOptions;
        @SerializedName("Boreinmm")
        @Expose
        private String boreinmm;
        @SerializedName("BoreStroke")
        @Expose
        private String boreStroke;
        /*@SerializedName("Brakes")
        @Expose
        private String brakes;*/
        @SerializedName("BrakeType")
        @Expose
        private String brakeType;
        @SerializedName("BrandID")
        @Expose
        private Integer brandID;
        @SerializedName("BrandName")
        @Expose
        private String brandName;
        @SerializedName("CategoryID")
        @Expose
        private Integer categoryID;
        @SerializedName("CategoryName")
        @Expose
        private String categoryName;
        @SerializedName("CentralLoking")
        @Expose
        private String centralLoking;
        @SerializedName("ChildSeat")
        @Expose
        private String childSeat;
        @SerializedName("Clutch")
        @Expose
        private String clutch;
        @SerializedName("ClutchType")
        @Expose
        private String clutchType;
        @SerializedName("CoolingSystem")
        @Expose
        private String coolingSystem;
        @SerializedName("CruiseControl")
        @Expose
        private String cruiseControl;
        @SerializedName("CylinderCapacityCC")
        @Expose
        private String cylinderCapacityCC;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("DisplacementCC")
        @Expose
        private String displacementCC;
        /*@SerializedName("Drive")
        @Expose
        private String drive;*/
        @SerializedName("DualStageAirbags")
        @Expose
        private String dualStageAirbags;
        @SerializedName("EmissionNorms")
        @Expose
        private String emissionNorms;
        @SerializedName("EngineCC")
        @Expose
        private String engineCC;
        @SerializedName("EngineDetails")
        @Expose
        private String engineDetails;
        @SerializedName("EngineLocation")
        @Expose
        private String engineLocation;
        @SerializedName("EngineRatedRPM")
        @Expose
        private String engineRatedRPM;
        @SerializedName("EngineType")
        @Expose
        private String engineType;
        @SerializedName("FIP")
        @Expose
        private String fIP;
        @SerializedName("FloorType")
        @Expose
        private String floorType;
        @SerializedName("FogLamps")
        @Expose
        private String fogLamps;
        @SerializedName("FoldingRearSeat")
        @Expose
        private String foldingRearSeat;
        @SerializedName("FrontAxleSuspension")
        @Expose
        private String frontAxleSuspension;
        @SerializedName("FrontTyre")
        @Expose
        private String frontTyre;
        @SerializedName("FuelTank")
        @Expose
        private String fuelTank;
        @SerializedName("FuelTankCapacityLtr")
        @Expose
        private String fuelTankCapacityLtr;
        @SerializedName("FuelType")
        @Expose
        private String fuelType;
        @SerializedName("FuelSystem")
        @Expose
        private String fuelSystem;
        @SerializedName("GearBox")
        @Expose
        private String gearBox;
        @SerializedName("GradeAbility")
        @Expose
        private String gradeAbility;
        @SerializedName("GroundClearance")
        @Expose
        private String groundClearance;
        @SerializedName("GVWGCW")
        @Expose
        private String gVWGCW;
        @SerializedName("HeadRest")
        @Expose
        private String headRest;
        @SerializedName("HP")
        @Expose
        private String hP;
        @SerializedName("HPCat")
        @Expose
        private String hPCat;
        @SerializedName("Hydraulic")
        @Expose
        private String hydraulic;
        @SerializedName("HydraulicLiftCapacityKg")
        @Expose
        private String hydraulicLiftCapacityKg;
        @SerializedName("HydraulicType")
        @Expose
        private String hydraulicType;
        @SerializedName("IntegratedMusicSystem")
        @Expose
        private String integratedMusicSystem;
        @SerializedName("KerbWeight")
        @Expose
        private String kerbWeight;
        @SerializedName("KeylessButtonStart")
        @Expose
        private String keylessButtonStart;
        @SerializedName("LiftingCapacityAtStandardFrame")
        @Expose
        private String liftingCapacityAtStandardFrame;
        @SerializedName("LoadBody")
        @Expose
        private String loadBody;
        @SerializedName("LoadBodyDimensions")
        @Expose
        private String loadBodyDimensions;
        @SerializedName("MaxPower")
        @Expose
        private String maxPower;
        @SerializedName("MaxReserveSpeed")
        @Expose
        private String maxReserveSpeed;
        @SerializedName("MaxSpeed")
        @Expose
        private String maxSpeed;
        @SerializedName("MaxTorqueKgm")
        @Expose
        private String maxTorqueKgm;
        @SerializedName("Mileage")
        @Expose
        private String mileage;
        @SerializedName("ModelID")
        @Expose
        private Integer modelID;
        @SerializedName("ModelName")
        @Expose
        private String modelName;
        @SerializedName("NoOfCylinder")
        @Expose
        private String noOfCylinder;
        @SerializedName("NoOfgears")
        @Expose
        private String noOfgears;
        @SerializedName("NoOfGearsForword")
        @Expose
        private String noOfGearsForword;
        @SerializedName("NoOfGearsReverse")
        @Expose
        private String noOfGearsReverse;
        @SerializedName("OverAllLength")
        @Expose
        private String overAllLength;
        @SerializedName("OverAllWidth")
        @Expose
        private String overAllWidth;
        @SerializedName("ParkingSensors")
        @Expose
        private String parkingSensors;
        @SerializedName("PayLoad")
        @Expose
        private String payLoad;
        @SerializedName("PowerKW")
        @Expose
        private String powerKW;
        @SerializedName("PowerWindow")
        @Expose
        private String powerWindow;
        @SerializedName("PTOHP")
        @Expose
        private String pTOHP;
        @SerializedName("PTORPM")
        @Expose
        private String pTORPM;
        @SerializedName("PTOSpeed")
        @Expose
        private String pTOSpeed;
        @SerializedName("PTOType")
        @Expose
        private String pTOType;
        @SerializedName("PumpType")
        @Expose
        private String pumpType;
        @SerializedName("RainSensingWiper")
        @Expose
        private String rainSensingWiper;
        @SerializedName("RatedRPM")
        @Expose
        private String ratedRPM;
        @SerializedName("RearAC")
        @Expose
        private String rearAC;
        @SerializedName("RearAxelSuspension")
        @Expose
        private String rearAxelSuspension;
        @SerializedName("RearDefogger")
        @Expose
        private String rearDefogger;
        @SerializedName("RearTyre")
        @Expose
        private String rearTyre;
        @SerializedName("RearWiper")
        @Expose
        private String rearWiper;
        @SerializedName("RPTOGRPTO")
        @Expose
        private String rPTOGRPTO;
        @SerializedName("SeatBelt")
        @Expose
        private String seatBelt;
        @SerializedName("SeatBeltWarning")
        @Expose
        private String seatBeltWarning;
        /*@SerializedName("SeatingCapacity")
        @Expose
        private String seatingCapacity;*/
        @SerializedName("SeatType")
        @Expose
        private String seatType;
        @SerializedName("SeatUpholstery")
        @Expose
        private String seatUpholstery;
        @SerializedName("Speed")
        @Expose
        private String speed;
        @SerializedName("SportMode")
        @Expose
        private String sportMode;
        /*@SerializedName("Steering")
        @Expose
        private String steering;*/
        @SerializedName("SteeringMountedControl")
        @Expose
        private String steeringMountedControl;
        @SerializedName("SteeringType")
        @Expose
        private String steeringType;
        @SerializedName("StrokeInMM")
        @Expose
        private String strokeInMM;
        @SerializedName("SubCategoryID")
        @Expose
        private Integer subCategoryID;
        @SerializedName("SubCategoryName")
        @Expose
        private String subCategoryName;
        @SerializedName("SunRoofMoonRoof")
        @Expose
        private String sunRoofMoonRoof;
        @SerializedName("TMPS")
        @Expose
        private String tMPS;
        @SerializedName("Torque")
        @Expose
        private String torque;
        @SerializedName("TotalWeight")
        @Expose
        private String totalWeight;
        @SerializedName("TransmissionType")
        @Expose
        private String transmissionType;
        @SerializedName("TuboChargeNutralAspirated")
        @Expose
        private String tuboChargeNutralAspirated;
        @SerializedName("TurningRadius")
        @Expose
        private String turningRadius;
        @SerializedName("TurningRadiusOfBrakes")
        @Expose
        private String turningRadiusOfBrakes;
        @SerializedName("TypeOfClutchAndBrakePedal")
        @Expose
        private String typeOfClutchAndBrakePedal;
        @SerializedName("TypeOfGearReduction")
        @Expose
        private String typeOfGearReduction;
        @SerializedName("Tyres")
        @Expose
        private String tyres;
        @SerializedName("TyreSize")
        @Expose
        private String tyreSize;
        @SerializedName("TyreSizeFront")
        @Expose
        private String tyreSizeFront;
        @SerializedName("TyreSizeRear")
        @Expose
        private String tyreSizeRear;
        @SerializedName("VersionID")
        @Expose
        private Integer versionID;
        @SerializedName("VersionName")
        @Expose
        private String versionName;
        @SerializedName("WarrantyKilometer")
        @Expose
        private String warrantyKilometer;
        @SerializedName("WarrantyYear")
        @Expose
        private String warrantyYear;
        @SerializedName("WebSite")
        @Expose
        private String webSite;
        @SerializedName("WeightKg")
        @Expose
        private String weightKg;
        @SerializedName("WheelBaseMM")
        @Expose
        private String wheelBaseMM;
        /*@SerializedName("Image")
        @Expose
        private String image;*/

        public String get_implements() {
            return _implements;
        }

        public void set_implements(String _implements) {
            this._implements = _implements;
        }

        public String getjIB() {
            return jIB;
        }

        public void setjIB(String jIB) {
            this.jIB = jIB;
        }

        public Integer getNewVehicleID() {
            return newVehicleID;
        }

        public void setNewVehicleID(Integer newVehicleID) {
            this.newVehicleID = newVehicleID;
        }

        public String getThreePointLinkage() {
            return threePointLinkage;
        }

        public void setThreePointLinkage(String threePointLinkage) {
            this.threePointLinkage = threePointLinkage;
        }

        public String getaBS() {
            return aBS;
        }

        public void setaBS(String aBS) {
            this.aBS = aBS;
        }

        public String getAdditionalInformation() {
            return additionalInformation;
        }

        public void setAdditionalInformation(String additionalInformation) {
            this.additionalInformation = additionalInformation;
        }

        public String getAirBags() {
            return airBags;
        }

        public void setAirBags(String airBags) {
            this.airBags = airBags;
        }

        public String getAirCleaner() {
            return airCleaner;
        }

        public void setAirCleaner(String airCleaner) {
            this.airCleaner = airCleaner;
        }

        public String getAlternateFuelType() {
            return alternateFuelType;
        }

        public void setAlternateFuelType(String alternateFuelType) {
            this.alternateFuelType = alternateFuelType;
        }

        public String getAuxiliaryHydValve() {
            return auxiliaryHydValve;
        }

        public void setAuxiliaryHydValve(String auxiliaryHydValve) {
            this.auxiliaryHydValve = auxiliaryHydValve;
        }

        public String getAxleConfiguration() {
            return axleConfiguration;
        }

        public void setAxleConfiguration(String axleConfiguration) {
            this.axleConfiguration = axleConfiguration;
        }

        public String getBackUpTorque() {
            return backUpTorque;
        }

        public void setBackUpTorque(String backUpTorque) {
            this.backUpTorque = backUpTorque;
        }

        public String getBodyOptions() {
            return bodyOptions;
        }

        public void setBodyOptions(String bodyOptions) {
            this.bodyOptions = bodyOptions;
        }

        public String getBoreinmm() {
            return boreinmm;
        }

        public void setBoreinmm(String boreinmm) {
            this.boreinmm = boreinmm;
        }

        public String getBoreStroke() {
            return boreStroke;
        }

        public void setBoreStroke(String boreStroke) {
            this.boreStroke = boreStroke;
        }

        public String getBrakeType() {
            return brakeType;
        }

        public void setBrakeType(String brakeType) {
            this.brakeType = brakeType;
        }

        public Integer getBrandID() {
            return brandID;
        }

        public void setBrandID(Integer brandID) {
            this.brandID = brandID;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public Integer getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(Integer categoryID) {
            this.categoryID = categoryID;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCentralLoking() {
            return centralLoking;
        }

        public void setCentralLoking(String centralLoking) {
            this.centralLoking = centralLoking;
        }

        public String getChildSeat() {
            return childSeat;
        }

        public void setChildSeat(String childSeat) {
            this.childSeat = childSeat;
        }

        public String getClutch() {
            return clutch;
        }

        public void setClutch(String clutch) {
            this.clutch = clutch;
        }

        public String getClutchType() {
            return clutchType;
        }

        public void setClutchType(String clutchType) {
            this.clutchType = clutchType;
        }

        public String getCoolingSystem() {
            return coolingSystem;
        }

        public void setCoolingSystem(String coolingSystem) {
            this.coolingSystem = coolingSystem;
        }

        public String getCruiseControl() {
            return cruiseControl;
        }

        public void setCruiseControl(String cruiseControl) {
            this.cruiseControl = cruiseControl;
        }

        public String getCylinderCapacityCC() {
            return cylinderCapacityCC;
        }

        public void setCylinderCapacityCC(String cylinderCapacityCC) {
            this.cylinderCapacityCC = cylinderCapacityCC;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDisplacementCC() {
            return displacementCC;
        }

        public void setDisplacementCC(String displacementCC) {
            this.displacementCC = displacementCC;
        }

        public String getDualStageAirbags() {
            return dualStageAirbags;
        }

        public void setDualStageAirbags(String dualStageAirbags) {
            this.dualStageAirbags = dualStageAirbags;
        }

        public String getEmissionNorms() {
            return emissionNorms;
        }

        public void setEmissionNorms(String emissionNorms) {
            this.emissionNorms = emissionNorms;
        }

        public String getEngineCC() {
            return engineCC;
        }

        public void setEngineCC(String engineCC) {
            this.engineCC = engineCC;
        }

        public String getEngineDetails() {
            return engineDetails;
        }

        public void setEngineDetails(String engineDetails) {
            this.engineDetails = engineDetails;
        }

        public String getEngineLocation() {
            return engineLocation;
        }

        public void setEngineLocation(String engineLocation) {
            this.engineLocation = engineLocation;
        }

        public String getEngineRatedRPM() {
            return engineRatedRPM;
        }

        public void setEngineRatedRPM(String engineRatedRPM) {
            this.engineRatedRPM = engineRatedRPM;
        }

        public String getEngineType() {
            return engineType;
        }

        public void setEngineType(String engineType) {
            this.engineType = engineType;
        }

        public String getfIP() {
            return fIP;
        }

        public void setfIP(String fIP) {
            this.fIP = fIP;
        }

        public String getFloorType() {
            return floorType;
        }

        public void setFloorType(String floorType) {
            this.floorType = floorType;
        }

        public String getFogLamps() {
            return fogLamps;
        }

        public void setFogLamps(String fogLamps) {
            this.fogLamps = fogLamps;
        }

        public String getFoldingRearSeat() {
            return foldingRearSeat;
        }

        public void setFoldingRearSeat(String foldingRearSeat) {
            this.foldingRearSeat = foldingRearSeat;
        }

        public String getFrontAxleSuspension() {
            return frontAxleSuspension;
        }

        public void setFrontAxleSuspension(String frontAxleSuspension) {
            this.frontAxleSuspension = frontAxleSuspension;
        }

        public String getFrontTyre() {
            return frontTyre;
        }

        public void setFrontTyre(String frontTyre) {
            this.frontTyre = frontTyre;
        }

        public String getFuelTank() {
            return fuelTank;
        }

        public void setFuelTank(String fuelTank) {
            this.fuelTank = fuelTank;
        }

        public String getFuelTankCapacityLtr() {
            return fuelTankCapacityLtr;
        }

        public void setFuelTankCapacityLtr(String fuelTankCapacityLtr) {
            this.fuelTankCapacityLtr = fuelTankCapacityLtr;
        }

        public String getFuelType() {
            return fuelType;
        }

        public void setFuelType(String fuelType) {
            this.fuelType = fuelType;
        }

        public String getFuelSystem() {
            return fuelSystem;
        }

        public void setFuelSystem(String fuelSystem) {
            this.fuelSystem = fuelSystem;
        }

        public String getGearBox() {
            return gearBox;
        }

        public void setGearBox(String gearBox) {
            this.gearBox = gearBox;
        }

        public String getGradeAbility() {
            return gradeAbility;
        }

        public void setGradeAbility(String gradeAbility) {
            this.gradeAbility = gradeAbility;
        }

        public String getGroundClearance() {
            return groundClearance;
        }

        public void setGroundClearance(String groundClearance) {
            this.groundClearance = groundClearance;
        }

        public String getgVWGCW() {
            return gVWGCW;
        }

        public void setgVWGCW(String gVWGCW) {
            this.gVWGCW = gVWGCW;
        }

        public String getHeadRest() {
            return headRest;
        }

        public void setHeadRest(String headRest) {
            this.headRest = headRest;
        }

        public String gethP() {
            return hP;
        }

        public void sethP(String hP) {
            this.hP = hP;
        }

        public String gethPCat() {
            return hPCat;
        }

        public void sethPCat(String hPCat) {
            this.hPCat = hPCat;
        }

        public String getHydraulic() {
            return hydraulic;
        }

        public void setHydraulic(String hydraulic) {
            this.hydraulic = hydraulic;
        }

        public String getHydraulicLiftCapacityKg() {
            return hydraulicLiftCapacityKg;
        }

        public void setHydraulicLiftCapacityKg(String hydraulicLiftCapacityKg) {
            this.hydraulicLiftCapacityKg = hydraulicLiftCapacityKg;
        }

        public String getHydraulicType() {
            return hydraulicType;
        }

        public void setHydraulicType(String hydraulicType) {
            this.hydraulicType = hydraulicType;
        }

        public String getIntegratedMusicSystem() {
            return integratedMusicSystem;
        }

        public void setIntegratedMusicSystem(String integratedMusicSystem) {
            this.integratedMusicSystem = integratedMusicSystem;
        }

        public String getKerbWeight() {
            return kerbWeight;
        }

        public void setKerbWeight(String kerbWeight) {
            this.kerbWeight = kerbWeight;
        }

        public String getKeylessButtonStart() {
            return keylessButtonStart;
        }

        public void setKeylessButtonStart(String keylessButtonStart) {
            this.keylessButtonStart = keylessButtonStart;
        }

        public String getLiftingCapacityAtStandardFrame() {
            return liftingCapacityAtStandardFrame;
        }

        public void setLiftingCapacityAtStandardFrame(String liftingCapacityAtStandardFrame) {
            this.liftingCapacityAtStandardFrame = liftingCapacityAtStandardFrame;
        }

        public String getLoadBody() {
            return loadBody;
        }

        public void setLoadBody(String loadBody) {
            this.loadBody = loadBody;
        }

        public String getLoadBodyDimensions() {
            return loadBodyDimensions;
        }

        public void setLoadBodyDimensions(String loadBodyDimensions) {
            this.loadBodyDimensions = loadBodyDimensions;
        }

        public String getMaxPower() {
            return maxPower;
        }

        public void setMaxPower(String maxPower) {
            this.maxPower = maxPower;
        }

        public String getMaxReserveSpeed() {
            return maxReserveSpeed;
        }

        public void setMaxReserveSpeed(String maxReserveSpeed) {
            this.maxReserveSpeed = maxReserveSpeed;
        }

        public String getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(String maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public String getMaxTorqueKgm() {
            return maxTorqueKgm;
        }

        public void setMaxTorqueKgm(String maxTorqueKgm) {
            this.maxTorqueKgm = maxTorqueKgm;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public Integer getModelID() {
            return modelID;
        }

        public void setModelID(Integer modelID) {
            this.modelID = modelID;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getNoOfCylinder() {
            return noOfCylinder;
        }

        public void setNoOfCylinder(String noOfCylinder) {
            this.noOfCylinder = noOfCylinder;
        }

        public String getNoOfgears() {
            return noOfgears;
        }

        public void setNoOfgears(String noOfgears) {
            this.noOfgears = noOfgears;
        }

        public String getNoOfGearsForword() {
            return noOfGearsForword;
        }

        public void setNoOfGearsForword(String noOfGearsForword) {
            this.noOfGearsForword = noOfGearsForword;
        }

        public String getNoOfGearsReverse() {
            return noOfGearsReverse;
        }

        public void setNoOfGearsReverse(String noOfGearsReverse) {
            this.noOfGearsReverse = noOfGearsReverse;
        }

        public String getOverAllLength() {
            return overAllLength;
        }

        public void setOverAllLength(String overAllLength) {
            this.overAllLength = overAllLength;
        }

        public String getOverAllWidth() {
            return overAllWidth;
        }

        public void setOverAllWidth(String overAllWidth) {
            this.overAllWidth = overAllWidth;
        }

        public String getParkingSensors() {
            return parkingSensors;
        }

        public void setParkingSensors(String parkingSensors) {
            this.parkingSensors = parkingSensors;
        }

        public String getPayLoad() {
            return payLoad;
        }

        public void setPayLoad(String payLoad) {
            this.payLoad = payLoad;
        }

        public String getPowerKW() {
            return powerKW;
        }

        public void setPowerKW(String powerKW) {
            this.powerKW = powerKW;
        }

        public String getPowerWindow() {
            return powerWindow;
        }

        public void setPowerWindow(String powerWindow) {
            this.powerWindow = powerWindow;
        }

        public String getpTOHP() {
            return pTOHP;
        }

        public void setpTOHP(String pTOHP) {
            this.pTOHP = pTOHP;
        }

        public String getpTORPM() {
            return pTORPM;
        }

        public void setpTORPM(String pTORPM) {
            this.pTORPM = pTORPM;
        }

        public String getpTOSpeed() {
            return pTOSpeed;
        }

        public void setpTOSpeed(String pTOSpeed) {
            this.pTOSpeed = pTOSpeed;
        }

        public String getpTOType() {
            return pTOType;
        }

        public void setpTOType(String pTOType) {
            this.pTOType = pTOType;
        }

        public String getPumpType() {
            return pumpType;
        }

        public void setPumpType(String pumpType) {
            this.pumpType = pumpType;
        }

        public String getRainSensingWiper() {
            return rainSensingWiper;
        }

        public void setRainSensingWiper(String rainSensingWiper) {
            this.rainSensingWiper = rainSensingWiper;
        }

        public String getRatedRPM() {
            return ratedRPM;
        }

        public void setRatedRPM(String ratedRPM) {
            this.ratedRPM = ratedRPM;
        }

        public String getRearAC() {
            return rearAC;
        }

        public void setRearAC(String rearAC) {
            this.rearAC = rearAC;
        }

        public String getRearAxelSuspension() {
            return rearAxelSuspension;
        }

        public void setRearAxelSuspension(String rearAxelSuspension) {
            this.rearAxelSuspension = rearAxelSuspension;
        }

        public String getRearDefogger() {
            return rearDefogger;
        }

        public void setRearDefogger(String rearDefogger) {
            this.rearDefogger = rearDefogger;
        }

        public String getRearTyre() {
            return rearTyre;
        }

        public void setRearTyre(String rearTyre) {
            this.rearTyre = rearTyre;
        }

        public String getRearWiper() {
            return rearWiper;
        }

        public void setRearWiper(String rearWiper) {
            this.rearWiper = rearWiper;
        }

        public String getrPTOGRPTO() {
            return rPTOGRPTO;
        }

        public void setrPTOGRPTO(String rPTOGRPTO) {
            this.rPTOGRPTO = rPTOGRPTO;
        }

        public String getSeatBelt() {
            return seatBelt;
        }

        public void setSeatBelt(String seatBelt) {
            this.seatBelt = seatBelt;
        }

        public String getSeatBeltWarning() {
            return seatBeltWarning;
        }

        public void setSeatBeltWarning(String seatBeltWarning) {
            this.seatBeltWarning = seatBeltWarning;
        }

        public String getSeatType() {
            return seatType;
        }

        public void setSeatType(String seatType) {
            this.seatType = seatType;
        }

        public String getSeatUpholstery() {
            return seatUpholstery;
        }

        public void setSeatUpholstery(String seatUpholstery) {
            this.seatUpholstery = seatUpholstery;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getSportMode() {
            return sportMode;
        }

        public void setSportMode(String sportMode) {
            this.sportMode = sportMode;
        }

        public String getSteeringMountedControl() {
            return steeringMountedControl;
        }

        public void setSteeringMountedControl(String steeringMountedControl) {
            this.steeringMountedControl = steeringMountedControl;
        }

        public String getSteeringType() {
            return steeringType;
        }

        public void setSteeringType(String steeringType) {
            this.steeringType = steeringType;
        }

        public String getStrokeInMM() {
            return strokeInMM;
        }

        public void setStrokeInMM(String strokeInMM) {
            this.strokeInMM = strokeInMM;
        }

        public Integer getSubCategoryID() {
            return subCategoryID;
        }

        public void setSubCategoryID(Integer subCategoryID) {
            this.subCategoryID = subCategoryID;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSunRoofMoonRoof() {
            return sunRoofMoonRoof;
        }

        public void setSunRoofMoonRoof(String sunRoofMoonRoof) {
            this.sunRoofMoonRoof = sunRoofMoonRoof;
        }

        public String gettMPS() {
            return tMPS;
        }

        public void settMPS(String tMPS) {
            this.tMPS = tMPS;
        }

        public String getTorque() {
            return torque;
        }

        public void setTorque(String torque) {
            this.torque = torque;
        }

        public String getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(String totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getTransmissionType() {
            return transmissionType;
        }

        public void setTransmissionType(String transmissionType) {
            this.transmissionType = transmissionType;
        }

        public String getTuboChargeNutralAspirated() {
            return tuboChargeNutralAspirated;
        }

        public void setTuboChargeNutralAspirated(String tuboChargeNutralAspirated) {
            this.tuboChargeNutralAspirated = tuboChargeNutralAspirated;
        }

        public String getTurningRadius() {
            return turningRadius;
        }

        public void setTurningRadius(String turningRadius) {
            this.turningRadius = turningRadius;
        }

        public String getTurningRadiusOfBrakes() {
            return turningRadiusOfBrakes;
        }

        public void setTurningRadiusOfBrakes(String turningRadiusOfBrakes) {
            this.turningRadiusOfBrakes = turningRadiusOfBrakes;
        }

        public String getTypeOfClutchAndBrakePedal() {
            return typeOfClutchAndBrakePedal;
        }

        public void setTypeOfClutchAndBrakePedal(String typeOfClutchAndBrakePedal) {
            this.typeOfClutchAndBrakePedal = typeOfClutchAndBrakePedal;
        }

        public String getTypeOfGearReduction() {
            return typeOfGearReduction;
        }

        public void setTypeOfGearReduction(String typeOfGearReduction) {
            this.typeOfGearReduction = typeOfGearReduction;
        }

        public String getTyres() {
            return tyres;
        }

        public void setTyres(String tyres) {
            this.tyres = tyres;
        }

        public String getTyreSize() {
            return tyreSize;
        }

        public void setTyreSize(String tyreSize) {
            this.tyreSize = tyreSize;
        }

        public String getTyreSizeFront() {
            return tyreSizeFront;
        }

        public void setTyreSizeFront(String tyreSizeFront) {
            this.tyreSizeFront = tyreSizeFront;
        }

        public String getTyreSizeRear() {
            return tyreSizeRear;
        }

        public void setTyreSizeRear(String tyreSizeRear) {
            this.tyreSizeRear = tyreSizeRear;
        }

        public Integer getVersionID() {
            return versionID;
        }

        public void setVersionID(Integer versionID) {
            this.versionID = versionID;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getWarrantyKilometer() {
            return warrantyKilometer;
        }

        public void setWarrantyKilometer(String warrantyKilometer) {
            this.warrantyKilometer = warrantyKilometer;
        }

        public String getWarrantyYear() {
            return warrantyYear;
        }

        public void setWarrantyYear(String warrantyYear) {
            this.warrantyYear = warrantyYear;
        }

        public String getWebSite() {
            return webSite;
        }

        public void setWebSite(String webSite) {
            this.webSite = webSite;
        }

        public String getWeightKg() {
            return weightKg;
        }

        public void setWeightKg(String weightKg) {
            this.weightKg = weightKg;
        }

        public String getWheelBaseMM() {
            return wheelBaseMM;
        }

        public void setWheelBaseMM(String wheelBaseMM) {
            this.wheelBaseMM = wheelBaseMM;
        }
    }
}
