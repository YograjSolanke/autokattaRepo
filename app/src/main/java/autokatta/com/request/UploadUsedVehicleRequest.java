package autokatta.com.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-003 on 10/5/17.
 */

public class UploadUsedVehicleRequest {


    @SerializedName("Contact")
    @Expose
    private String contact;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("SubCategoryID")
    @Expose
    private Integer subCategoryID;
    @SerializedName("BrandID")
    @Expose
    private Integer brandID;
    @SerializedName("ModelID")
    @Expose
    private Integer modelID;
    @SerializedName("VersionId")
    @Expose
    private Integer versionId;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Privacy")
    @Expose
    private String privacy;
    @SerializedName("SubCategory")
    @Expose
    private String subCategory;
    @SerializedName("Permit")
    @Expose
    private String permit;
    @SerializedName("Exchange")
    @Expose
    private String exchange;
    @SerializedName("Model")
    @Expose
    private String model;
    @SerializedName("Manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("RegistrationMonth")
    @Expose
    private String registrationMonth;
    @SerializedName("RegistrationYear")
    @Expose
    private String registrationYear;
    @SerializedName("ManufacturerMonth")
    @Expose
    private String manufacturerMonth;
    @SerializedName("ManufacturerYear")
    @Expose
    private String manufacturerYear;
    @SerializedName("KmsRunning")
    @Expose
    private Double kmsRunning;
    @SerializedName("HrsRunning")
    @Expose
    private String hrsRunning;
    @SerializedName("RTOcity")
    @Expose
    private String rTOcity;
    @SerializedName("LocationCity")
    @Expose
    private String locationCity;
    @SerializedName("RegistrationNumber")
    @Expose
    private String registrationNumber;
    @SerializedName("Owners")
    @Expose
    private Integer owners;
    @SerializedName("BodyManufacturer")
    @Expose
    private String bodyManufacturer;
    @SerializedName("SeatManufacturer")
    @Expose
    private String seatManufacturer;
    @SerializedName("Brakes")
    @Expose
    private String brakes;
    @SerializedName("Pump")
    @Expose
    private String pump;
    @SerializedName("Hypothetication")
    @Expose
    private String hypothetication;
    @SerializedName("RcAvailable")
    @Expose
    private String rcAvailable;
    @SerializedName("InsuranceValid")
    @Expose
    private String insuranceValid;
    @SerializedName("InsuranceIdv")
    @Expose
    private String insuranceIdv;
    @SerializedName("TaxValidity")
    @Expose
    private String taxValidity;
    @SerializedName("TaxPaid")
    @Expose
    private String taxPaid;
    @SerializedName("PermitYesNo")
    @Expose
    private String permitYesNo;
    @SerializedName("PermitValidity")
    @Expose
    private String permitValidity;
    @SerializedName("FitnessYesNo")
    @Expose
    private String fitnessYesNo;
    @SerializedName("FitnessValidity")
    @Expose
    private String fitnessValidity;
    @SerializedName("ChassisNO")
    @Expose
    private String chassisNO;
    @SerializedName("EngineNo")
    @Expose
    private String engineNo;
    @SerializedName("Drive")
    @Expose
    private String drive;
    @SerializedName("Transmission")
    @Expose
    private String transmission;
    @SerializedName("BodyType")
    @Expose
    private String bodyType;
    @SerializedName("BoatType")
    @Expose
    private String boatType;
    @SerializedName("EditBodystr")
    @Expose
    private String editBodystr;
    @SerializedName("BusType")
    @Expose
    private String busType;
    @SerializedName("AirCondition")
    @Expose
    private String airCondition;
    @SerializedName("Invoice")
    @Expose
    private String invoice;
    @SerializedName("Application")
    @Expose
    private String application;
    @SerializedName("SeatCapacity")
    @Expose
    private String seatCapacity;
    @SerializedName("TyreCondition")
    @Expose
    private String tyreCondition;
    @SerializedName("Color")
    @Expose
    private String color;
    @SerializedName("Fuel")
    @Expose
    private String fuel;
    @SerializedName("HpCapacity")
    @Expose
    private String hpCapacity;
    @SerializedName("JIB")
    @Expose
    private String jIB;
    @SerializedName("Boon")
    @Expose
    private String boon;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("RvType")
    @Expose
    private String rvType;
    @SerializedName("InsuranceDate")
    @Expose
    private String insuranceDate;
    @SerializedName("FinanceStatus")
    @Expose
    private String financeStatus;
    @SerializedName("ExchangeStatus")
    @Expose
    private String exchangeStatus;
    @SerializedName("Emmission")
    @Expose
    private String emmission;
    @SerializedName("Steering")
    @Expose
    private String steering;
    @SerializedName("Implements")
    @Expose
    private String _implements;
    @SerializedName("Implementstr")
    @Expose
    private String implementstr;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(Integer subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public Integer getBrandID() {
        return brandID;
    }

    public void setBrandID(Integer brandID) {
        this.brandID = brandID;
    }

    public Integer getModelID() {
        return modelID;
    }

    public void setModelID(Integer modelID) {
        this.modelID = modelID;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
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

    public String getRegistrationMonth() {
        return registrationMonth;
    }

    public void setRegistrationMonth(String registrationMonth) {
        this.registrationMonth = registrationMonth;
    }

    public String getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(String registrationYear) {
        this.registrationYear = registrationYear;
    }

    public String getManufacturerMonth() {
        return manufacturerMonth;
    }

    public void setManufacturerMonth(String manufacturerMonth) {
        this.manufacturerMonth = manufacturerMonth;
    }

    public String getManufacturerYear() {
        return manufacturerYear;
    }

    public void setManufacturerYear(String manufacturerYear) {
        this.manufacturerYear = manufacturerYear;
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

    public String getRTOcity() {
        return rTOcity;
    }

    public void setRTOcity(String rTOcity) {
        this.rTOcity = rTOcity;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getOwners() {
        return owners;
    }

    public void setOwners(Integer owners) {
        this.owners = owners;
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

    public String getHypothetication() {
        return hypothetication;
    }

    public void setHypothetication(String hypothetication) {
        this.hypothetication = hypothetication;
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

    public String getTaxPaid() {
        return taxPaid;
    }

    public void setTaxPaid(String taxPaid) {
        this.taxPaid = taxPaid;
    }

    public String getPermitYesNo() {
        return permitYesNo;
    }

    public void setPermitYesNo(String permitYesNo) {
        this.permitYesNo = permitYesNo;
    }

    public String getPermitValidity() {
        return permitValidity;
    }

    public void setPermitValidity(String permitValidity) {
        this.permitValidity = permitValidity;
    }

    public String getFitnessYesNo() {
        return fitnessYesNo;
    }

    public void setFitnessYesNo(String fitnessYesNo) {
        this.fitnessYesNo = fitnessYesNo;
    }

    public String getFitnessValidity() {
        return fitnessValidity;
    }

    public void setFitnessValidity(String fitnessValidity) {
        this.fitnessValidity = fitnessValidity;
    }

    public String getChassisNO() {
        return chassisNO;
    }

    public void setChassisNO(String chassisNO) {
        this.chassisNO = chassisNO;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
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

    public String getEditBodystr() {
        return editBodystr;
    }

    public void setEditBodystr(String editBodystr) {
        this.editBodystr = editBodystr;
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

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(String seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getTyreCondition() {
        return tyreCondition;
    }

    public void setTyreCondition(String tyreCondition) {
        this.tyreCondition = tyreCondition;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
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

    public String getRvType() {
        return rvType;
    }

    public void setRvType(String rvType) {
        this.rvType = rvType;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        this.insuranceDate = insuranceDate;
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

    public String getEmmission() {
        return emmission;
    }

    public void setEmmission(String emmission) {
        this.emmission = emmission;
    }

    public String getSteering() {
        return steering;
    }

    public void setSteering(String steering) {
        this.steering = steering;
    }

    public String getImplements() {
        return _implements;
    }

    public void setImplements(String _implements) {
        this._implements = _implements;
    }

    public String getImplementstr() {
        return implementstr;
    }

    public void setImplementstr(String implementstr) {
        this.implementstr = implementstr;
    }


/*
    private String title, myContact, category, sub_category, model, manufacturer, Version, rto_city, location_city,
            month_of_registration, year_of_registration, month_of_manufacture, year_of_manufacture, color, registration_number,
            rc_available, insurance_valid, insurance_idv, tax_validity, tax_paid_upto, fitness_validity, permit_validity,
            permit_yesno, fitness_yesno, fual_type, seating_capacity, permit, fiananceExchange, kms_running, Hrs_running,
            no_of_owners, bodyManufacturer, seatManufacturer, hypothication, engine_no, chassis_no, price, image, drive,
            transmission, body_type, boat_type, rv_type, application, tyre_condition, bus_type, air_condition, invoice,
            implementss, privacy, hp_capacity, JIB, Boon, brakes, pump, insuranceDate, emissionVersion, financeStatus,
            exchangeStatus, steering, category_id, sub_cat_id, BrandID, ModelID, VersionId;
*/

    public UploadUsedVehicleRequest(String title, String myContact, String category, String sub_category, String model,
                                    String manufacturer, String Version, String rto_city, String location_city,
                                    String month_of_registration, String year_of_registration, String month_of_manufacture,
                                    String year_of_manufacture, String color, String registration_number,
                                    String rc_available, String insurance_valid, String insurance_idv, String tax_validity,
                                    String tax_paid_upto, String fitness_validity, String permit_validity,
                                    String permit_yesno, String fitness_yesno, String fual_type, String seating_capacity,
                                    String permit, String fiananceExchange, double kms_running, String Hrs_running,
                                    int no_of_owners, String bodyManufacturer, String seatManufacturer,
                                    String hypothication, String engine_no, String chassis_no, String price, String image,
                                    String drive, String transmission, String body_type, String boat_type, String rv_type,
                                    String application, String tyre_condition, String bus_type, String air_condition,
                                    String invoice, String implementss, String privacy, String hp_capacity, String JIB,
                                    String Boon, String brakes, String pump, String insuranceDate, String emissionVersion,
                                    String financeStatus, String exchangeStatus, String steering,
                                    int category_id, int sub_cat_id, int BrandID, int ModelID, int VersionId) {
        this.title = title;
        this.contact = myContact;
        this.category = category;
        this.subCategory = sub_category;
        this.model = model;
        this.manufacturer = manufacturer;
        this.version = Version;
        this.rTOcity = rto_city;
        this.locationCity = location_city;
        this.registrationMonth = month_of_registration;
        this.registrationYear = year_of_registration;
        this.manufacturerMonth = month_of_manufacture;
        this.manufacturerYear = year_of_manufacture;
        this.color = color;
        this.registrationNumber = registration_number;
        this.rcAvailable = rc_available;
        this.insuranceValid = insurance_valid;
        this.insuranceIdv = insurance_idv;
        this.taxValidity = tax_validity;
        this.taxPaid = tax_paid_upto;
        this.fitnessValidity = fitness_validity;
        this.permitValidity = permit_validity;
        this.permitYesNo = permit_yesno;
        this.fitnessYesNo = fitness_yesno;
        this.fuel = fual_type;
        this.seatCapacity = seating_capacity;
        this.permit = permit;
        this.financeStatus = fiananceExchange;
        this.kmsRunning = kms_running;
        this.hrsRunning = Hrs_running;
        this.owners = no_of_owners;
        this.bodyManufacturer = bodyManufacturer;
        this.seatManufacturer = seatManufacturer;
        this.hypothetication = hypothication;
        this.engineNo = engine_no;
        this.chassisNO = chassis_no;
        this.price = price;
        this.image = image;
        this.drive = drive;
        this.transmission = transmission;
        this.bodyType = body_type;
        this.boatType = boat_type;
        this.rvType = rv_type;
        this.application = application;
        this.tyreCondition = tyre_condition;
        this.busType = bus_type;
        this.airCondition = air_condition;
        this.invoice = invoice;
        this._implements = implementss;
        this.privacy = privacy;
        this.hpCapacity = hp_capacity;
        this.jIB = JIB;
        this.boon = Boon;
        this.brakes = brakes;
        this.pump = pump;
        this.insuranceDate = insuranceDate;
        this.emmission = emissionVersion;
        this.financeStatus = financeStatus;
        this.exchangeStatus = exchangeStatus;
        this.steering = steering;
        this.categoryID = category_id;
        this.subCategoryID = sub_cat_id;
        this.brandID = BrandID;
        this.modelID = ModelID;
        this.versionId = VersionId;
    }
}
