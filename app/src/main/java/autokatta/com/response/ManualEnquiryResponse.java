package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-001 on 5/5/17.
 */

public class ManualEnquiryResponse {
    @SerializedName("Success")
    @Expose
    private Success success;
    @SerializedName("Error")
    @Expose
    private String error;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class Success {

        @SerializedName("New Vehicle")
        @Expose
        private List<Object> newVehicle = null;
        @SerializedName("UsedVehicle")
        @Expose
        private List<UsedVehicle> usedVehicle = null;
        @SerializedName("Products")
        @Expose
        private List<Product> products = null;
        @SerializedName("Services")
        @Expose
        private List<Service> services = null;

        public List<Object> getNewVehicle() {
            return newVehicle;
        }

        public void setNewVehicle(List<Object> newVehicle) {
            this.newVehicle = newVehicle;
        }

        public List<UsedVehicle> getUsedVehicle() {
            return usedVehicle;
        }

        public void setUsedVehicle(List<UsedVehicle> usedVehicle) {
            this.usedVehicle = usedVehicle;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

        public class Product {

            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("store_id")
            @Expose
            private String storeId;
            @SerializedName("product_type")
            @Expose
            private String productType;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("images")
            @Expose
            private String images;
            @SerializedName("product_name")
            @Expose
            private String productName;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("product_details")
            @Expose
            private String productDetails;
            @SerializedName("product_tags")
            @Expose
            private String productTags;
            @SerializedName("brandtags")
            @Expose
            private String brandtags;

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
            @SerializedName("group_id")
            @Expose
            private String groupId;
            @SerializedName("storeContact")
            @Expose
            private String storeContact;
            @SerializedName("enquiryCount")
            @Expose
            private String enquiryCount;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("myContact")
            @Expose
            private String myContact;
            @SerializedName("custName")
            @Expose
            private String custName;
            @SerializedName("custContact")
            @Expose
            private String custContact;
            @SerializedName("custAddress")
            @Expose
            private String custAddress;
            @SerializedName("custFullAddress")
            @Expose
            private String custFullAddress;
            @SerializedName("custInventoryType")
            @Expose
            private String custInventoryType;
            @SerializedName("idsList")
            @Expose
            private String idsList;
            @SerializedName("custEnquiryStatus")
            @Expose
            private String custEnquiryStatus;
            @SerializedName("discussion")
            @Expose
            private String discussion;

            @SerializedName("createdDate")
            @Expose
            private String createdDate;

            @SerializedName("nextFollowupDate")
            @Expose
            private String nextFollowupDate;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
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

            public String getEnquiryCount() {
                return enquiryCount;
            }

            public void setEnquiryCount(String enquiryCount) {
                this.enquiryCount = enquiryCount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMyContact() {
                return myContact;
            }

            public void setMyContact(String myContact) {
                this.myContact = myContact;
            }

            public String getCustName() {
                return custName;
            }

            public void setCustName(String custName) {
                this.custName = custName;
            }

            public String getCustContact() {
                return custContact;
            }

            public void setCustContact(String custContact) {
                this.custContact = custContact;
            }

            public String getCustAddress() {
                return custAddress;
            }

            public void setCustAddress(String custAddress) {
                this.custAddress = custAddress;
            }

            public String getCustFullAddress() {
                return custFullAddress;
            }

            public void setCustFullAddress(String custFullAddress) {
                this.custFullAddress = custFullAddress;
            }

            public String getCustInventoryType() {
                return custInventoryType;
            }

            public void setCustInventoryType(String custInventoryType) {
                this.custInventoryType = custInventoryType;
            }

            public String getIdsList() {
                return idsList;
            }

            public void setIdsList(String idsList) {
                this.idsList = idsList;
            }

            public String getCustEnquiryStatus() {
                return custEnquiryStatus;
            }

            public void setCustEnquiryStatus(String custEnquiryStatus) {
                this.custEnquiryStatus = custEnquiryStatus;
            }

            public String getDiscussion() {
                return discussion;
            }

            public void setDiscussion(String discussion) {
                this.discussion = discussion;
            }

            public String getNextFollowupDate() {
                return nextFollowupDate;
            }

            public void setNextFollowupDate(String nextFollowupDate) {
                this.nextFollowupDate = nextFollowupDate;
            }

        }

        public class Service {

            @SerializedName("service_id")
            @Expose
            private String id;
            @SerializedName("store_id")
            @Expose
            private String storeId;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("price")
            @Expose
            private String price;
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

            @SerializedName("group_id")
            @Expose
            private String groupId;
            @SerializedName("storeContact")
            @Expose
            private String storeContact;
            @SerializedName("enquiryCount")
            @Expose
            private String enquiryCount;
            @SerializedName("myContact")
            @Expose
            private String myContact;
            @SerializedName("custName")
            @Expose
            private String custName;
            @SerializedName("custContact")
            @Expose
            private String custContact;
            @SerializedName("custAddress")
            @Expose
            private String custAddress;
            @SerializedName("custFullAddress")
            @Expose
            private String custFullAddress;
            @SerializedName("custInventoryType")
            @Expose
            private String custInventoryType;
            @SerializedName("idsList")
            @Expose
            private String idsList;
            @SerializedName("custEnquiryStatus")
            @Expose
            private String custEnquiryStatus;
            @SerializedName("discussion")
            @Expose
            private String discussion;

            @SerializedName("createdDate")
            @Expose
            private String createdDate;

            @SerializedName("nextFollowupDate")
            @Expose
            private String nextFollowupDate;

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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getEnquiryCount() {
                return enquiryCount;
            }

            public void setEnquiryCount(String enquiryCount) {
                this.enquiryCount = enquiryCount;
            }

            public String getMyContact() {
                return myContact;
            }

            public void setMyContact(String myContact) {
                this.myContact = myContact;
            }

            public String getCustName() {
                return custName;
            }

            public void setCustName(String custName) {
                this.custName = custName;
            }

            public String getCustContact() {
                return custContact;
            }

            public void setCustContact(String custContact) {
                this.custContact = custContact;
            }

            public String getCustAddress() {
                return custAddress;
            }

            public void setCustAddress(String custAddress) {
                this.custAddress = custAddress;
            }

            public String getCustFullAddress() {
                return custFullAddress;
            }

            public void setCustFullAddress(String custFullAddress) {
                this.custFullAddress = custFullAddress;
            }

            public String getCustInventoryType() {
                return custInventoryType;
            }

            public void setCustInventoryType(String custInventoryType) {
                this.custInventoryType = custInventoryType;
            }

            public String getIdsList() {
                return idsList;
            }

            public void setIdsList(String idsList) {
                this.idsList = idsList;
            }

            public String getCustEnquiryStatus() {
                return custEnquiryStatus;
            }

            public void setCustEnquiryStatus(String custEnquiryStatus) {
                this.custEnquiryStatus = custEnquiryStatus;
            }

            public String getDiscussion() {
                return discussion;
            }

            public void setDiscussion(String discussion) {
                this.discussion = discussion;
            }

            public String getNextFollowupDate() {
                return nextFollowupDate;
            }

            public void setNextFollowupDate(String nextFollowupDate) {
                this.nextFollowupDate = nextFollowupDate;
            }

        }

        public class UsedVehicle {

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
            @SerializedName("enquiryCount")
            @Expose
            private String enquiryCount;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("myContact")
            @Expose
            private String myContact;
            @SerializedName("custName")
            @Expose
            private String custName;
            @SerializedName("custContact")
            @Expose
            private String custContact;
            @SerializedName("custAddress")
            @Expose
            private String custAddress;
            @SerializedName("custFullAddress")
            @Expose
            private String custFullAddress;
            @SerializedName("custInventoryType")
            @Expose
            private String custInventoryType;
            @SerializedName("idsList")
            @Expose
            private String idsList;
            @SerializedName("custEnquiryStatus")
            @Expose
            private String custEnquiryStatus;
            @SerializedName("discussion")
            @Expose
            private String discussion;

            @SerializedName("createdDate")
            @Expose
            private String createdDate;

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            @SerializedName("nextFollowupDate")
            @Expose
            private String nextFollowupDate;

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

            public String getEnquiryCount() {
                return enquiryCount;
            }

            public void setEnquiryCount(String enquiryCount) {
                this.enquiryCount = enquiryCount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMyContact() {
                return myContact;
            }

            public void setMyContact(String myContact) {
                this.myContact = myContact;
            }

            public String getCustName() {
                return custName;
            }

            public void setCustName(String custName) {
                this.custName = custName;
            }

            public String getCustContact() {
                return custContact;
            }

            public void setCustContact(String custContact) {
                this.custContact = custContact;
            }

            public String getCustAddress() {
                return custAddress;
            }

            public void setCustAddress(String custAddress) {
                this.custAddress = custAddress;
            }

            public String getCustFullAddress() {
                return custFullAddress;
            }

            public void setCustFullAddress(String custFullAddress) {
                this.custFullAddress = custFullAddress;
            }

            public String getCustInventoryType() {
                return custInventoryType;
            }

            public void setCustInventoryType(String custInventoryType) {
                this.custInventoryType = custInventoryType;
            }

            public String getIdsList() {
                return idsList;
            }

            public void setIdsList(String idsList) {
                this.idsList = idsList;
            }

            public String getCustEnquiryStatus() {
                return custEnquiryStatus;
            }

            public void setCustEnquiryStatus(String custEnquiryStatus) {
                this.custEnquiryStatus = custEnquiryStatus;
            }

            public String getDiscussion() {
                return discussion;
            }

            public void setDiscussion(String discussion) {
                this.discussion = discussion;
            }

            public String getNextFollowupDate() {
                return nextFollowupDate;
            }

            public void setNextFollowupDate(String nextFollowupDate) {
                this.nextFollowupDate = nextFollowupDate;
            }

        }
    }
}
