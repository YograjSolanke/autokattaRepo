package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 20/11/17.
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

        @SerializedName("UsedVehicle")
        @Expose
        private List<UsedVehicle> usedVehicle = null;
        @SerializedName("Newvehicle")
        @Expose
        private List<Newvehicle> newvehicle = null;
        @SerializedName("Store")
        @Expose
        private List<Store> store = null;
        @SerializedName("Product")
        @Expose
        private List<Product> product = null;
        @SerializedName("Service")
        @Expose
        private List<Service> service = null;

        @SerializedName("Profile")
        @Expose
        private List<Profile> profile = null;

        public List<Profile> getProfile() {
            return profile;
        }

        public void setProfile(List<Profile> profile) {
            this.profile = profile;
        }

        public List<UsedVehicle> getUsedVehicle() {
            return usedVehicle;
        }

        public void setUsedVehicle(List<UsedVehicle> usedVehicle) {
            this.usedVehicle = usedVehicle;
        }

        public List<Newvehicle> getNewvehicle() {
            return newvehicle;
        }

        public void setNewvehicle(List<Newvehicle> newvehicle) {
            this.newvehicle = newvehicle;
        }

        public List<Store> getStore() {
            return store;
        }

        public void setStore(List<Store> store) {
            this.store = store;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

        public List<Service> getService() {
            return service;
        }

        public void setService(List<Service> service) {
            this.service = service;
        }


        public class Newvehicle {

            @SerializedName("StoreID")
            @Expose
            private Integer storeID;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("Address")
            @Expose
            private String address;
            @SerializedName("Brands")
            @Expose
            private String brands;
            @SerializedName("BrandTags")
            @Expose
            private String brandTags;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("CoverImage")
            @Expose
            private String coverImage;
            @SerializedName("CreationDate")
            @Expose
            private String creationDate;
            @SerializedName("Latitude")
            @Expose
            private String latitude;
            @SerializedName("Location")
            @Expose
            private String location;
            @SerializedName("Longitude")
            @Expose
            private String longitude;
            @SerializedName("ModifiedDate")
            @Expose
            private String modifiedDate;
            @SerializedName("RatingStatus")
            @Expose
            private String ratingStatus;
            @SerializedName("StoreImage")
            @Expose
            private String storeImage;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("OpenTime")
            @Expose
            private String openTime;
            @SerializedName("CloseTime")
            @Expose
            private String closeTime;
            @SerializedName("StoreType")
            @Expose
            private String storeType;
            @SerializedName("VehicleID")
            @Expose
            private Integer vehicleID;
            @SerializedName("WebSite")
            @Expose
            private String webSite;
            @SerializedName("WorkingDays")
            @Expose
            private String workingDays;

            public Integer getStoreID() {
                return storeID;
            }

            public void setStoreID(Integer storeID) {
                this.storeID = storeID;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBrands() {
                return brands;
            }

            public void setBrands(String brands) {
                this.brands = brands;
            }

            public String getBrandTags() {
                return brandTags;
            }

            public void setBrandTags(String brandTags) {
                this.brandTags = brandTags;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getCoverImage() {
                return coverImage;
            }

            public void setCoverImage(String coverImage) {
                this.coverImage = coverImage;
            }

            public String getCreationDate() {
                return creationDate;
            }

            public void setCreationDate(String creationDate) {
                this.creationDate = creationDate;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getModifiedDate() {
                return modifiedDate;
            }

            public void setModifiedDate(String modifiedDate) {
                this.modifiedDate = modifiedDate;
            }

            public String getRatingStatus() {
                return ratingStatus;
            }

            public void setRatingStatus(String ratingStatus) {
                this.ratingStatus = ratingStatus;
            }

            public String getStoreImage() {
                return storeImage;
            }

            public void setStoreImage(String storeImage) {
                this.storeImage = storeImage;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }

            public String getCloseTime() {
                return closeTime;
            }

            public void setCloseTime(String closeTime) {
                this.closeTime = closeTime;
            }

            public String getStoreType() {
                return storeType;
            }

            public void setStoreType(String storeType) {
                this.storeType = storeType;
            }

            public Integer getVehicleID() {
                return vehicleID;
            }

            public void setVehicleID(Integer vehicleID) {
                this.vehicleID = vehicleID;
            }

            public String getWebSite() {
                return webSite;
            }

            public void setWebSite(String webSite) {
                this.webSite = webSite;
            }

            public String getWorkingDays() {
                return workingDays;
            }

            public void setWorkingDays(String workingDays) {
                this.workingDays = workingDays;
            }

        }


        public class Product {

            @SerializedName("ProductID")
            @Expose
            private Integer productID;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Type")
            @Expose
            private String type;
            @SerializedName("Details")
            @Expose
            private String details;
            @SerializedName("ProductTags")
            @Expose
            private String productTags;
            @SerializedName("BrandTags")
            @Expose
            private String brandTags;
            @SerializedName("Category")
            @Expose
            private String category;
            @SerializedName("CreatedDate")
            @Expose
            private String createdDate;
            @SerializedName("GroupID")
            @Expose
            private Integer groupID;
            @SerializedName("Image")
            @Expose
            private String image;
            @SerializedName("ModifiedDate")
            @Expose
            private String modifiedDate;
            @SerializedName("Price")
            @Expose
            private String price;
            @SerializedName("PriceRating")
            @Expose
            private String priceRating;
            @SerializedName("QuantityRating")
            @Expose
            private String quantityRating;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("StockRating")
            @Expose
            private String stockRating;
            @SerializedName("StoreID")
            @Expose
            private Integer storeID;
            @SerializedName("AddedBy")
            @Expose
            private String addedBy;

            public Integer getProductID() {
                return productID;
            }

            public void setProductID(Integer productID) {
                this.productID = productID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getProductTags() {
                return productTags;
            }

            public void setProductTags(String productTags) {
                this.productTags = productTags;
            }

            public String getBrandTags() {
                return brandTags;
            }

            public void setBrandTags(String brandTags) {
                this.brandTags = brandTags;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public Integer getGroupID() {
                return groupID;
            }

            public void setGroupID(Integer groupID) {
                this.groupID = groupID;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getModifiedDate() {
                return modifiedDate;
            }

            public void setModifiedDate(String modifiedDate) {
                this.modifiedDate = modifiedDate;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPriceRating() {
                return priceRating;
            }

            public void setPriceRating(String priceRating) {
                this.priceRating = priceRating;
            }

            public String getQuantityRating() {
                return quantityRating;
            }

            public void setQuantityRating(String quantityRating) {
                this.quantityRating = quantityRating;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStockRating() {
                return stockRating;
            }

            public void setStockRating(String stockRating) {
                this.stockRating = stockRating;
            }

            public Integer getStoreID() {
                return storeID;
            }

            public void setStoreID(Integer storeID) {
                this.storeID = storeID;
            }

            public String getAddedBy() {
                return addedBy;
            }

            public void setAddedBy(String addedBy) {
                this.addedBy = addedBy;
            }

        }


        public class Service {

            @SerializedName("StoreServiceID")
            @Expose
            private Integer storeServiceID;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("BrandTags")
            @Expose
            private String brandTags;
            @SerializedName("Category")
            @Expose
            private String category;
            @SerializedName("CreateDate")
            @Expose
            private String createDate;
            @SerializedName("GroupID")
            @Expose
            private Integer groupID;
            @SerializedName("Image")
            @Expose
            private String image;
            @SerializedName("Price")
            @Expose
            private String price;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("StoreID")
            @Expose
            private Integer storeID;
            @SerializedName("Tags")
            @Expose
            private String tags;
            @SerializedName("Type")
            @Expose
            private String type;
            @SerializedName("AddedBy")
            @Expose
            private String addedBy;

            public Integer getStoreServiceID() {
                return storeServiceID;
            }

            public void setStoreServiceID(Integer storeServiceID) {
                this.storeServiceID = storeServiceID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBrandTags() {
                return brandTags;
            }

            public void setBrandTags(String brandTags) {
                this.brandTags = brandTags;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public Integer getGroupID() {
                return groupID;
            }

            public void setGroupID(Integer groupID) {
                this.groupID = groupID;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Integer getStoreID() {
                return storeID;
            }

            public void setStoreID(Integer storeID) {
                this.storeID = storeID;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAddedBy() {
                return addedBy;
            }

            public void setAddedBy(String addedBy) {
                this.addedBy = addedBy;
            }

        }


        public class Store {

            @SerializedName("StoreID")
            @Expose
            private Integer storeID;
            @SerializedName("Description")
            @Expose
            private String description;
            @SerializedName("Address")
            @Expose
            private String address;
            @SerializedName("Brands")
            @Expose
            private String brands;
            @SerializedName("BrandTags")
            @Expose
            private String brandTags;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("CoverImage")
            @Expose
            private String coverImage;
            @SerializedName("CreationDate")
            @Expose
            private String creationDate;
            @SerializedName("Latitude")
            @Expose
            private String latitude;
            @SerializedName("Location")
            @Expose
            private String location;
            @SerializedName("Longitude")
            @Expose
            private String longitude;
            @SerializedName("ModifiedDate")
            @Expose
            private String modifiedDate;
            @SerializedName("RatingStatus")
            @Expose
            private String ratingStatus;
            @SerializedName("StoreImage")
            @Expose
            private String storeImage;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("OpenTime")
            @Expose
            private String openTime;
            @SerializedName("CloseTime")
            @Expose
            private String closeTime;
            @SerializedName("StoreType")
            @Expose
            private String storeType;
            @SerializedName("VehicleID")
            @Expose
            private Integer vehicleID;
            @SerializedName("WebSite")
            @Expose
            private String webSite;
            @SerializedName("WorkingDays")
            @Expose
            private String workingDays;

            public Integer getStoreID() {
                return storeID;
            }

            public void setStoreID(Integer storeID) {
                this.storeID = storeID;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBrands() {
                return brands;
            }

            public void setBrands(String brands) {
                this.brands = brands;
            }

            public String getBrandTags() {
                return brandTags;
            }

            public void setBrandTags(String brandTags) {
                this.brandTags = brandTags;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getCoverImage() {
                return coverImage;
            }

            public void setCoverImage(String coverImage) {
                this.coverImage = coverImage;
            }

            public String getCreationDate() {
                return creationDate;
            }

            public void setCreationDate(String creationDate) {
                this.creationDate = creationDate;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getModifiedDate() {
                return modifiedDate;
            }

            public void setModifiedDate(String modifiedDate) {
                this.modifiedDate = modifiedDate;
            }

            public String getRatingStatus() {
                return ratingStatus;
            }

            public void setRatingStatus(String ratingStatus) {
                this.ratingStatus = ratingStatus;
            }

            public String getStoreImage() {
                return storeImage;
            }

            public void setStoreImage(String storeImage) {
                this.storeImage = storeImage;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }

            public String getCloseTime() {
                return closeTime;
            }

            public void setCloseTime(String closeTime) {
                this.closeTime = closeTime;
            }

            public String getStoreType() {
                return storeType;
            }

            public void setStoreType(String storeType) {
                this.storeType = storeType;
            }

            public Integer getVehicleID() {
                return vehicleID;
            }

            public void setVehicleID(Integer vehicleID) {
                this.vehicleID = vehicleID;
            }

            public String getWebSite() {
                return webSite;
            }

            public void setWebSite(String webSite) {
                this.webSite = webSite;
            }

            public String getWorkingDays() {
                return workingDays;
            }

            public void setWorkingDays(String workingDays) {
                this.workingDays = workingDays;
            }

        }

        public class UsedVehicle {

            @SerializedName("UploadVehicleID")
            @Expose
            private Integer uploadVehicleID;
            @SerializedName("Titile")
            @Expose
            private String titile;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("Category")
            @Expose
            private String category;
            @SerializedName("SubCategory")
            @Expose
            private String subCategory;
            @SerializedName("Model")
            @Expose
            private String model;
            @SerializedName("Manufacturer")
            @Expose
            private String manufacturer;
            @SerializedName("Version")
            @Expose
            private String version;
            @SerializedName("RTOCity")
            @Expose
            private String rTOCity;
            @SerializedName("LocationCity")
            @Expose
            private String locationCity;
            @SerializedName("LocationState")
            @Expose
            private String locationState;
            @SerializedName("LocationCountry")
            @Expose
            private String locationCountry;
            @SerializedName("MonthOfManufacturer")
            @Expose
            private String monthOfManufacturer;
            @SerializedName("YearOfRegistration")
            @Expose
            private String yearOfRegistration;
            @SerializedName("YearOfManufaturer")
            @Expose
            private String yearOfManufaturer;
            @SerializedName("Color")
            @Expose
            private String color;
            @SerializedName("RegistrationNumber")
            @Expose
            private String registrationNumber;
            @SerializedName("RCAvailable")
            @Expose
            private String rCAvailable;
            @SerializedName("InsuranceValid")
            @Expose
            private String insuranceValid;
            @SerializedName("InsuranceDate")
            @Expose
            private String insuranceDate;
            @SerializedName("InsuranceIdv")
            @Expose
            private String insuranceIdv;
            @SerializedName("EmissionVersion")
            @Expose
            private String emissionVersion;
            @SerializedName("FinanceStatus")
            @Expose
            private String financeStatus;
            @SerializedName("ExchangeStatus")
            @Expose
            private String exchangeStatus;
            @SerializedName("TaxValidity")
            @Expose
            private String taxValidity;
            @SerializedName("TaxPaidUpto")
            @Expose
            private String taxPaidUpto;
            @SerializedName("FitnessValidity")
            @Expose
            private String fitnessValidity;
            @SerializedName("PermitValidity")
            @Expose
            private String permitValidity;
            @SerializedName("PermitYesNo")
            @Expose
            private String permitYesNo;
            @SerializedName("FitnessYesNo")
            @Expose
            private String fitnessYesNo;
            @SerializedName("FuelType")
            @Expose
            private String fuelType;
            @SerializedName("SeatingCapacity")
            @Expose
            private String seatingCapacity;
            @SerializedName("Permit")
            @Expose
            private String permit;
            @SerializedName("FinanceExchange")
            @Expose
            private Object financeExchange;
            @SerializedName("KmsRunning")
            @Expose
            private String kmsRunning;
            @SerializedName("HrsRunning")
            @Expose
            private String hrsRunning;
            @SerializedName("NoOfOwners")
            @Expose
            private String noOfOwners;
            @SerializedName("BodyManufacturer")
            @Expose
            private String bodyManufacturer;
            @SerializedName("SeatManufacturer")
            @Expose
            private String seatManufacturer;
            @SerializedName("Hypothication")
            @Expose
            private String hypothication;
            @SerializedName("EngineNo")
            @Expose
            private String engineNo;
            @SerializedName("ChassisNo")
            @Expose
            private String chassisNo;
            @SerializedName("Price")
            @Expose
            private String price;
            @SerializedName("Image")
            @Expose
            private String image;
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
            @SerializedName("RVType")
            @Expose
            private String rVType;
            @SerializedName("Application")
            @Expose
            private String application;
            @SerializedName("TyreCondition")
            @Expose
            private String tyreCondition;
            @SerializedName("BusType")
            @Expose
            private String busType;
            @SerializedName("AirCondition")
            @Expose
            private String airCondition;
            @SerializedName("Invoice")
            @Expose
            private String invoice;
            @SerializedName("Implements")
            @Expose
            private String _implements;
            @SerializedName("FinanceRequest")
            @Expose
            private String financeRequest;
            @SerializedName("Privacy")
            @Expose
            private String privacy;
            @SerializedName("HPCapacity")
            @Expose
            private String hPCapacity;
            @SerializedName("JIB")
            @Expose
            private String jIB;
            @SerializedName("Boon")
            @Expose
            private String boon;
            @SerializedName("ViewCount")
            @Expose
            private String viewCount;
            @SerializedName("CallCount")
            @Expose
            private String callCount;
            @SerializedName("Date")
            @Expose
            private String date;
            @SerializedName("StartPrice")
            @Expose
            private String startPrice;
            @SerializedName("ReservePrice")
            @Expose
            private String reservePrice;
            @SerializedName("Status")
            @Expose
            private String status;
            @SerializedName("Brakes")
            @Expose
            private String brakes;
            @SerializedName("Pump")
            @Expose
            private String pump;
            @SerializedName("Steering")
            @Expose
            private String steering;

            public Integer getUploadVehicleID() {
                return uploadVehicleID;
            }

            public void setUploadVehicleID(Integer uploadVehicleID) {
                this.uploadVehicleID = uploadVehicleID;
            }

            public String getTitile() {
                return titile;
            }

            public void setTitile(String titile) {
                this.titile = titile;
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

            public String getMonthOfManufacturer() {
                return monthOfManufacturer;
            }

            public void setMonthOfManufacturer(String monthOfManufacturer) {
                this.monthOfManufacturer = monthOfManufacturer;
            }

            public String getYearOfRegistration() {
                return yearOfRegistration;
            }

            public void setYearOfRegistration(String yearOfRegistration) {
                this.yearOfRegistration = yearOfRegistration;
            }

            public String getYearOfManufaturer() {
                return yearOfManufaturer;
            }

            public void setYearOfManufaturer(String yearOfManufaturer) {
                this.yearOfManufaturer = yearOfManufaturer;
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

            public String getRCAvailable() {
                return rCAvailable;
            }

            public void setRCAvailable(String rCAvailable) {
                this.rCAvailable = rCAvailable;
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

            public String getPermitYesNo() {
                return permitYesNo;
            }

            public void setPermitYesNo(String permitYesNo) {
                this.permitYesNo = permitYesNo;
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

            public Object getFinanceExchange() {
                return financeExchange;
            }

            public void setFinanceExchange(Object financeExchange) {
                this.financeExchange = financeExchange;
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

            public String getRVType() {
                return rVType;
            }

            public void setRVType(String rVType) {
                this.rVType = rVType;
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

            public String getFinanceRequest() {
                return financeRequest;
            }

            public void setFinanceRequest(String financeRequest) {
                this.financeRequest = financeRequest;
            }

            public String getPrivacy() {
                return privacy;
            }

            public void setPrivacy(String privacy) {
                this.privacy = privacy;
            }

            public String getHPCapacity() {
                return hPCapacity;
            }

            public void setHPCapacity(String hPCapacity) {
                this.hPCapacity = hPCapacity;
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

            public String getViewCount() {
                return viewCount;
            }

            public void setViewCount(String viewCount) {
                this.viewCount = viewCount;
            }

            public String getCallCount() {
                return callCount;
            }

            public void setCallCount(String callCount) {
                this.callCount = callCount;
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
        }

        public class Profile {

            @SerializedName("ProfileID")
            @Expose
            private Integer profileID;
            @SerializedName("About")
            @Expose
            private String about;
            @SerializedName("AreaOfOperation")
            @Expose
            private String areaOfOperation;
            @SerializedName("ByDistrict")
            @Expose
            private String byDistrict;
            @SerializedName("ByKMS")
            @Expose
            private Object byKMS;
            @SerializedName("ByState")
            @Expose
            private String byState;
            @SerializedName("CategoryName")
            @Expose
            private String categoryName;
            @SerializedName("City")
            @Expose
            private String city;
            @SerializedName("CompanyName")
            @Expose
            private String companyName;
            @SerializedName("ContactNo")
            @Expose
            private String contactNo;
            @SerializedName("Country")
            @Expose
            private String country;
            @SerializedName("DealingWith")
            @Expose
            private String dealingWith;
            @SerializedName("Designation")
            @Expose
            private Object designation;
            @SerializedName("DOB")
            @Expose
            private String dOB;
            @SerializedName("Email")
            @Expose
            private String email;
            @SerializedName("EstimatedNextServiceDate")
            @Expose
            private String estimatedNextServiceDate;
            @SerializedName("FitnessValidity")
            @Expose
            private String fitnessValidity;
            @SerializedName("FollowID")
            @Expose
            private Integer followID;
            @SerializedName("Gender")
            @Expose
            private String gender;
            @SerializedName("Industry")
            @Expose
            private String industry;
            @SerializedName("InsuranceValidityDate_1")
            @Expose
            private Object insuranceValidityDate1;
            @SerializedName("InsuranceValidityDate")
            @Expose
            private String insuranceValidityDate;
            @SerializedName("IsExchange")
            @Expose
            private String isExchange;
            @SerializedName("IsFinance")
            @Expose
            private String isFinance;
            @SerializedName("IsOneTimeBuyer")
            @Expose
            private String isOneTimeBuyer;
            @SerializedName("IsOneTimeSeller")
            @Expose
            private String isOneTimeSeller;
            @SerializedName("IsRegularBuyer")
            @Expose
            private String isRegularBuyer;
            @SerializedName("IsRegularSeller")
            @Expose
            private String isRegularSeller;
            @SerializedName("LastServiceDate_2")
            @Expose
            private Object lastServiceDate2;
            @SerializedName("LastServiceDate")
            @Expose
            private String lastServiceDate;
            @SerializedName("LikeID")
            @Expose
            private Integer likeID;
            @SerializedName("Password")
            @Expose
            private String password;
            @SerializedName("PermitValidity")
            @Expose
            private String permitValidity;
            @SerializedName("Profession")
            @Expose
            private String profession;
            @SerializedName("ProfilePicture")
            @Expose
            private String profilePicture;
            @SerializedName("PUCValidityDate_1")
            @Expose
            private Object pUCValidityDate1;
            @SerializedName("PUCValidityDate")
            @Expose
            private String pUCValidityDate;
            @SerializedName("Rating")
            @Expose
            private String rating;
            @SerializedName("RegistrationID")
            @Expose
            private Integer registrationID;
            @SerializedName("Skills")
            @Expose
            private String skills;
            @SerializedName("State")
            @Expose
            private String state;
            @SerializedName("SubCategoryName")
            @Expose
            private String subCategoryName;
            @SerializedName("SubProfession")
            @Expose
            private String subProfession;
            @SerializedName("TaxValidity")
            @Expose
            private String taxValidity;
            @SerializedName("UserName")
            @Expose
            private String userName;
            @SerializedName("VehicleNo")
            @Expose
            private String vehicleNo;
            @SerializedName("VehicleType")
            @Expose
            private String vehicleType;
            @SerializedName("WebSite")
            @Expose
            private String webSite;
            @SerializedName("Interest")
            @Expose
            private String interest;
            @SerializedName("Latitude")
            @Expose
            private String latitude;
            @SerializedName("Longitude")
            @Expose
            private String longitude;

            public Integer getProfileID() {
                return profileID;
            }

            public void setProfileID(Integer profileID) {
                this.profileID = profileID;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getAreaOfOperation() {
                return areaOfOperation;
            }

            public void setAreaOfOperation(String areaOfOperation) {
                this.areaOfOperation = areaOfOperation;
            }

            public String getByDistrict() {
                return byDistrict;
            }

            public void setByDistrict(String byDistrict) {
                this.byDistrict = byDistrict;
            }

            public Object getByKMS() {
                return byKMS;
            }

            public void setByKMS(Object byKMS) {
                this.byKMS = byKMS;
            }

            public String getByState() {
                return byState;
            }

            public void setByState(String byState) {
                this.byState = byState;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getContactNo() {
                return contactNo;
            }

            public void setContactNo(String contactNo) {
                this.contactNo = contactNo;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getDealingWith() {
                return dealingWith;
            }

            public void setDealingWith(String dealingWith) {
                this.dealingWith = dealingWith;
            }

            public Object getDesignation() {
                return designation;
            }

            public void setDesignation(Object designation) {
                this.designation = designation;
            }

            public String getDOB() {
                return dOB;
            }

            public void setDOB(String dOB) {
                this.dOB = dOB;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getEstimatedNextServiceDate() {
                return estimatedNextServiceDate;
            }

            public void setEstimatedNextServiceDate(String estimatedNextServiceDate) {
                this.estimatedNextServiceDate = estimatedNextServiceDate;
            }

            public String getFitnessValidity() {
                return fitnessValidity;
            }

            public void setFitnessValidity(String fitnessValidity) {
                this.fitnessValidity = fitnessValidity;
            }

            public Integer getFollowID() {
                return followID;
            }

            public void setFollowID(Integer followID) {
                this.followID = followID;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getIndustry() {
                return industry;
            }

            public void setIndustry(String industry) {
                this.industry = industry;
            }

            public Object getInsuranceValidityDate1() {
                return insuranceValidityDate1;
            }

            public void setInsuranceValidityDate1(Object insuranceValidityDate1) {
                this.insuranceValidityDate1 = insuranceValidityDate1;
            }

            public String getInsuranceValidityDate() {
                return insuranceValidityDate;
            }

            public void setInsuranceValidityDate(String insuranceValidityDate) {
                this.insuranceValidityDate = insuranceValidityDate;
            }

            public String getIsExchange() {
                return isExchange;
            }

            public void setIsExchange(String isExchange) {
                this.isExchange = isExchange;
            }

            public String getIsFinance() {
                return isFinance;
            }

            public void setIsFinance(String isFinance) {
                this.isFinance = isFinance;
            }

            public String getIsOneTimeBuyer() {
                return isOneTimeBuyer;
            }

            public void setIsOneTimeBuyer(String isOneTimeBuyer) {
                this.isOneTimeBuyer = isOneTimeBuyer;
            }

            public String getIsOneTimeSeller() {
                return isOneTimeSeller;
            }

            public void setIsOneTimeSeller(String isOneTimeSeller) {
                this.isOneTimeSeller = isOneTimeSeller;
            }

            public String getIsRegularBuyer() {
                return isRegularBuyer;
            }

            public void setIsRegularBuyer(String isRegularBuyer) {
                this.isRegularBuyer = isRegularBuyer;
            }

            public String getIsRegularSeller() {
                return isRegularSeller;
            }

            public void setIsRegularSeller(String isRegularSeller) {
                this.isRegularSeller = isRegularSeller;
            }

            public Object getLastServiceDate2() {
                return lastServiceDate2;
            }

            public void setLastServiceDate2(Object lastServiceDate2) {
                this.lastServiceDate2 = lastServiceDate2;
            }

            public String getLastServiceDate() {
                return lastServiceDate;
            }

            public void setLastServiceDate(String lastServiceDate) {
                this.lastServiceDate = lastServiceDate;
            }

            public Integer getLikeID() {
                return likeID;
            }

            public void setLikeID(Integer likeID) {
                this.likeID = likeID;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPermitValidity() {
                return permitValidity;
            }

            public void setPermitValidity(String permitValidity) {
                this.permitValidity = permitValidity;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public String getProfilePicture() {
                return profilePicture;
            }

            public void setProfilePicture(String profilePicture) {
                this.profilePicture = profilePicture;
            }

            public Object getPUCValidityDate1() {
                return pUCValidityDate1;
            }

            public void setPUCValidityDate1(Object pUCValidityDate1) {
                this.pUCValidityDate1 = pUCValidityDate1;
            }

            public String getPUCValidityDate() {
                return pUCValidityDate;
            }

            public void setPUCValidityDate(String pUCValidityDate) {
                this.pUCValidityDate = pUCValidityDate;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public Integer getRegistrationID() {
                return registrationID;
            }

            public void setRegistrationID(Integer registrationID) {
                this.registrationID = registrationID;
            }

            public String getSkills() {
                return skills;
            }

            public void setSkills(String skills) {
                this.skills = skills;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getSubCategoryName() {
                return subCategoryName;
            }

            public void setSubCategoryName(String subCategoryName) {
                this.subCategoryName = subCategoryName;
            }

            public String getSubProfession() {
                return subProfession;
            }

            public void setSubProfession(String subProfession) {
                this.subProfession = subProfession;
            }

            public String getTaxValidity() {
                return taxValidity;
            }

            public void setTaxValidity(String taxValidity) {
                this.taxValidity = taxValidity;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getVehicleNo() {
                return vehicleNo;
            }

            public void setVehicleNo(String vehicleNo) {
                this.vehicleNo = vehicleNo;
            }

            public String getVehicleType() {
                return vehicleType;
            }

            public void setVehicleType(String vehicleType) {
                this.vehicleType = vehicleType;
            }

            public String getWebSite() {
                return webSite;
            }

            public void setWebSite(String webSite) {
                this.webSite = webSite;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

        }
    }
}
