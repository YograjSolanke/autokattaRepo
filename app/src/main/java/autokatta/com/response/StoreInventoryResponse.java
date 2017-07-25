package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-004 on 17/4/17.
 */

public class StoreInventoryResponse {

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

        @SerializedName("category")
        @Expose
        private List<Category> category = null;
        @SerializedName("product")
        @Expose
        private List<Product> product = null;
        @SerializedName("service")
        @Expose
        private List<Service> service = null;
        @SerializedName("vehicle")
        @Expose
        private List<Vehicle> vehicle = null;

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
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

        public List<Vehicle> getVehicle() {
            return vehicle;
        }

        public void setVehicle(List<Vehicle> vehicle) {
            this.vehicle = vehicle;
        }



        public class Category {

            @SerializedName("module_name")
            @Expose
            private String moduleName;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("id")
            @Expose
            private Integer id;

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }
        }

        public class Product {



            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            private boolean visibility;

            public String getStorecontact() {
                return storecontact;
            }

            public void setStorecontact(String storecontact) {
                this.storecontact = storecontact;
            }

            @SerializedName("storecontact")
            @Expose

            private String storecontact;

            @SerializedName("store_id")
            @Expose
            private Integer storeId;
            @SerializedName("product_id")
            @Expose
            private Integer productId;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("product_image")
            @Expose
            private String productImage;
            @SerializedName("product_type")
            @Expose
            private String productType;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("brandtags")
            @Expose
            private String brandtags;
            @SerializedName("product_details")
            @Expose
            private String productDetails;
            @SerializedName("productlikecount")
            @Expose
            private Integer productlikecount;
            @SerializedName("productlikestatus")
            @Expose
            private String productlikestatus;
            @SerializedName("productfollowstatus")
            @Expose
            private String productfollowstatus;
            @SerializedName("prate")
            @Expose
            private Double prate;
            @SerializedName("prate1")
            @Expose
            private Double prate1;
            @SerializedName("prate2")
            @Expose
            private Double prate2;
            @SerializedName("prate3")
            @Expose
            private Double prate3;
            @SerializedName("productrating")
            @Expose
            private String productrating;
            @SerializedName("product_tags")
            @Expose
            private String productTags;

            public Integer getStoreId() {
                return storeId;
            }

            public void setStoreId(Integer storeId) {
                this.storeId = storeId;
            }

            public Integer getProductId() {
                return productId;
            }

            public void setProductId(Integer productId) {
                this.productId = productId;
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

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
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

            public String getBrandtags() {
                return brandtags;
            }

            public void setBrandtags(String brandtags) {
                this.brandtags = brandtags;
            }

            public String getProductDetails() {
                return productDetails;
            }

            public void setProductDetails(String productDetails) {
                this.productDetails = productDetails;
            }

            public Integer getProductlikecount() {
                return productlikecount;
            }

            public void setProductlikecount(Integer productlikecount) {
                this.productlikecount = productlikecount;
            }

            public String getProductlikestatus() {
                return productlikestatus;
            }

            public void setProductlikestatus(String productlikestatus) {
                this.productlikestatus = productlikestatus;
            }

            public String getProductfollowstatus() {
                return productfollowstatus;
            }

            public void setProductfollowstatus(String productfollowstatus) {
                this.productfollowstatus = productfollowstatus;
            }

            public Double getPrate() {
                return prate;
            }

            public void setPrate(Double prate) {
                this.prate = prate;
            }

            public Double getPrate1() {
                return prate1;
            }

            public void setPrate1(Double prate1) {
                this.prate1 = prate1;
            }

            public Double getPrate2() {
                return prate2;
            }

            public void setPrate2(Double prate2) {
                this.prate2 = prate2;
            }

            public Double getPrate3() {
                return prate3;
            }

            public void setPrate3(Double prate3) {
                this.prate3 = prate3;
            }

            public String getProductrating() {
                return productrating;
            }

            public void setProductrating(String productrating) {
                this.productrating = productrating;
            }

            public String getProductTags() {
                return productTags;
            }

            public void setProductTags(String productTags) {
                this.productTags = productTags;
            }

        }


        public class Service {


            public boolean isVisibility() {
                return visibility;
            }

            public void setVisibility(boolean visibility) {
                this.visibility = visibility;
            }

            private boolean visibility;

            public String getStorecontact() {
                return storecontact;
            }

            public void setStorecontact(String storecontact) {
                this.storecontact = storecontact;
            }

            @SerializedName("storecontact")
            @Expose

            private String storecontact;

            @SerializedName("service_name")
            @Expose
            private String serviceName;
            @SerializedName("service_id")
            @Expose
            private Integer serviceId;
            @SerializedName("servicecategory")
            @Expose
            private String servicecategory;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("service_type")
            @Expose
            private String serviceType;
            @SerializedName("service_price")
            @Expose
            private String servicePrice;
            @SerializedName("service_details")
            @Expose
            private String serviceDetails;
            @SerializedName("service_images")
            @Expose
            private String serviceImages;
            @SerializedName("brandtags")
            @Expose
            private String brandtags;
            @SerializedName("created_date")
            @Expose
            private String createdDate;
            @SerializedName("servicelikecount")
            @Expose
            private Integer servicelikecount;
            @SerializedName("servicelikestatus")
            @Expose
            private String servicelikestatus;
            @SerializedName("servicefollowstatus")
            @Expose
            private String servicefollowstatus;
            @SerializedName("srate")
            @Expose
            private Double srate;
            @SerializedName("srate1")
            @Expose
            private Double srate1;
            @SerializedName("srate2")
            @Expose
            private Double srate2;
            @SerializedName("srate3")
            @Expose
            private Double srate3;
            @SerializedName("servicerating")
            @Expose
            private String servicerating;
            @SerializedName("servicetags")
            @Expose
            private String servicetags;

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public Integer getServiceId() {
                return serviceId;
            }

            public void setServiceId(Integer serviceId) {
                this.serviceId = serviceId;
            }

            public String getServicecategory() {
                return servicecategory;
            }

            public void setServicecategory(String servicecategory) {
                this.servicecategory = servicecategory;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public String getServicePrice() {
                return servicePrice;
            }

            public void setServicePrice(String servicePrice) {
                this.servicePrice = servicePrice;
            }

            public String getServiceDetails() {
                return serviceDetails;
            }

            public void setServiceDetails(String serviceDetails) {
                this.serviceDetails = serviceDetails;
            }

            public String getServiceImages() {
                return serviceImages;
            }

            public void setServiceImages(String serviceImages) {
                this.serviceImages = serviceImages;
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

            public Integer getServicelikecount() {
                return servicelikecount;
            }

            public void setServicelikecount(Integer servicelikecount) {
                this.servicelikecount = servicelikecount;
            }

            public String getServicelikestatus() {
                return servicelikestatus;
            }

            public void setServicelikestatus(String servicelikestatus) {
                this.servicelikestatus = servicelikestatus;
            }

            public String getServicefollowstatus() {
                return servicefollowstatus;
            }

            public void setServicefollowstatus(String servicefollowstatus) {
                this.servicefollowstatus = servicefollowstatus;
            }

            public Double getSrate() {
                return srate;
            }

            public void setSrate(Double srate) {
                this.srate = srate;
            }

            public Double getSrate1() {
                return srate1;
            }

            public void setSrate1(Double srate1) {
                this.srate1 = srate1;
            }

            public Double getSrate2() {
                return srate2;
            }

            public void setSrate2(Double srate2) {
                this.srate2 = srate2;
            }

            public Double getSrate3() {
                return srate3;
            }

            public void setSrate3(Double srate3) {
                this.srate3 = srate3;
            }

            public String getServicerating() {
                return servicerating;
            }

            public void setServicerating(String servicerating) {
                this.servicerating = servicerating;
            }

            public String getServicetags() {
                return servicetags;
            }

            public void setServicetags(String servicetags) {
                this.servicetags = servicetags;
            }
        }

        public class Vehicle {

            public String getStorecontact() {
                return storecontact;
            }

            public void setStorecontact(String storecontact) {
                this.storecontact = storecontact;
            }

            @SerializedName("storecontact")
            @Expose

            private String storecontact;

            @SerializedName("vehicle_id")
            @Expose
            private Integer vehicleId;
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
            @SerializedName("regno")
            @Expose
            private String regno;
            @SerializedName("year")
            @Expose
            private String year;
            @SerializedName("location")
            @Expose
            private String location;
            @SerializedName("rto")
            @Expose
            private String rto;
            @SerializedName("kms")
            @Expose
            private String kms;
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

            public String getRegno() {
                return regno;
            }

            public void setRegno(String regno) {
                this.regno = regno;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getRto() {
                return rto;
            }

            public void setRto(String rto) {
                this.rto = rto;
            }

            public String getKms() {
                return kms;
            }

            public void setKms(String kms) {
                this.kms = kms;
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

}
