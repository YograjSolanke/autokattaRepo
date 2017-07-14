package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 10/4/17.
 */

public class FavouriteResponse {

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

        @SerializedName("next")
        @Expose
        private String next;
        @SerializedName("Notification")
        @Expose
        private List<Notification> notification = null;
        @SerializedName("Search")
        @Expose
        private List<Search> search = null;
        @SerializedName("SellerVehicle")
        @Expose
        private List<SellerVehicle> sellerVehicle = null;
        @SerializedName("BuyerSearch")
        @Expose
        private List<BuyerSearch> buyerSearch = null;
        @SerializedName("time")
        @Expose
        private Double time;

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public List<Notification> getNotification() {
            return notification;
        }

        public void setNotification(List<Notification> notification) {
            this.notification = notification;
        }

        public List<Search> getSearch() {
            return search;
        }

        public void setSearch(List<Search> search) {
            this.search = search;
        }

        public List<SellerVehicle> getSellerVehicle() {
            return sellerVehicle;
        }

        public void setSellerVehicle(List<SellerVehicle> sellerVehicle) {
            this.sellerVehicle = sellerVehicle;
        }

        public List<BuyerSearch> getBuyerSearch() {
            return buyerSearch;
        }

        public void setBuyerSearch(List<BuyerSearch> buyerSearch) {
            this.buyerSearch = buyerSearch;
        }

        public Double getTime() {
            return time;
        }

        public void setTime(Double time) {
            this.time = time;
        }


        public class Notification {

            @SerializedName("favid")
            @Expose
            private String favid;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("layout")
            @Expose
            private String layout;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("datetime")
            @Expose
            private String datetime;
            @SerializedName("sender")
            @Expose
            private String sender;
            @SerializedName("action")
            @Expose
            private String action;
            @SerializedName("receiver")
            @Expose
            private String receiver;
            @SerializedName("vehicle_id")
            @Expose
            private String vehicleId;
            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("service_id")
            @Expose
            private String serviceId;
            @SerializedName("store_id")
            @Expose
            private int storeId;
            @SerializedName("group_id")
            @Expose
            private String groupId;
            @SerializedName("sendername")
            @Expose
            private String sendername;
            @SerializedName("sender_pic")
            @Expose
            private String senderPic;
            @SerializedName("senderprofession")
            @Expose
            private String senderprofession;
            @SerializedName("senderwebsite")
            @Expose
            private String senderwebsite;
            @SerializedName("sendercity")
            @Expose
            private String sendercity;
            @SerializedName("senderlikecount")
            @Expose
            private String senderlikecount;
            @SerializedName("senderfollowcount")
            @Expose
            private String senderfollowcount;
            @SerializedName("senderlikestatus")
            @Expose
            private String senderlikestatus;
            @SerializedName("senderfollowstatus")
            @Expose
            private String senderfollowstatus;
            @SerializedName("receivername")
            @Expose
            private String receivername;
            @SerializedName("receiver_pic")
            @Expose
            private String receiverPic;
            @SerializedName("receiverprofession")
            @Expose
            private String receiverprofession;
            @SerializedName("receiverwebsite")
            @Expose
            private String receiverwebsite;
            @SerializedName("receivercity")
            @Expose
            private String receivercity;
            @SerializedName("receiverlikecount")
            @Expose
            private String receiverlikecount;
            @SerializedName("receiverfollowcount")
            @Expose
            private String receiverfollowcount;
            @SerializedName("receiverlikestatus")
            @Expose
            private String receiverlikestatus;
            @SerializedName("receiverfollowstatus")
            @Expose
            private String receiverfollowstatus;
            @SerializedName("storelikestatus")
            @Expose
            private String storelikestatus;
            @SerializedName("storefollowstatus")
            @Expose
            private String storefollowstatus;
            @SerializedName("storerating")
            @Expose
            private String storerating;
            @SerializedName("storelikecount")
            @Expose
            private String storelikecount;
            @SerializedName("storefollowcount")
            @Expose
            private String storefollowcount;
            @SerializedName("storetiming")
            @Expose
            private String storetiming;
            @SerializedName("store_contact")
            @Expose
            private String storeContact;
            @SerializedName("store_name")
            @Expose
            private String storeName;
            @SerializedName("store_image")
            @Expose
            private String storeImage;
            @SerializedName("store_type")
            @Expose
            private String storeType;
            @SerializedName("store_website")
            @Expose
            private String storeWebsite;
            @SerializedName("working_days")
            @Expose
            private String workingDays;
            @SerializedName("store_location")
            @Expose
            private String storeLocation;
            @SerializedName("group_vehicles")
            @Expose
            private String groupVehicles;
            @SerializedName("group_name")
            @Expose
            private String groupName;
            @SerializedName("group_image")
            @Expose
            private String groupImage;
            @SerializedName("group_members")
            @Expose
            private String groupMembers;
            @SerializedName("productlikestatus")
            @Expose
            private String productlikestatus;
            @SerializedName("productfollowstatus")
            @Expose
            private String productfollowstatus;
            @SerializedName("productlikecount")
            @Expose
            private String productlikecount;
            @SerializedName("productfollowcount")
            @Expose
            private String productfollowcount;
            @SerializedName("product_name")
            @Expose
            private String productName;
            @SerializedName("product_type")
            @Expose
            private String productType;
            @SerializedName("productimages")
            @Expose
            private String productimages;
            @SerializedName("servicelikestatus")
            @Expose
            private String servicelikestatus;
            @SerializedName("servicefollowstatus")
            @Expose
            private String servicefollowstatus;
            @SerializedName("servicelikecount")
            @Expose
            private String servicelikecount;
            @SerializedName("servicefollowcount")
            @Expose
            private String servicefollowcount;
            @SerializedName("serive_name")
            @Expose
            private String serviceName;
            @SerializedName("service_type")
            @Expose
            private String serviceType;
            @SerializedName("serviceimages")
            @Expose
            private String serviceimages;
            @SerializedName("vehiclelikestatus")
            @Expose
            private String vehiclelikestatus;
            @SerializedName("vehiclefollowstatus")
            @Expose
            private String vehiclefollowstatus;
            @SerializedName("vehiclelikecount")
            @Expose
            private String vehiclelikecount;
            @SerializedName("vehiclefollowcount")
            @Expose
            private String vehiclefollowcount;
            @SerializedName("VehicleContact")
            @Expose
            private String vehicleContact;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("brand")
            @Expose
            private String brand;
            @SerializedName("year")
            @Expose
            private String year;

            public String getFavid() {
                return favid;
            }

            public void setFavid(String favid) {
                this.favid = favid;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLayout() {
                return layout;
            }

            public void setLayout(String layout) {
                this.layout = layout;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getSender() {
                return sender;
            }

            public void setSender(String sender) {
                this.sender = sender;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getVehicleId() {
                return vehicleId;
            }

            public void setVehicleId(String vehicleId) {
                this.vehicleId = vehicleId;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getSendername() {
                return sendername;
            }

            public void setSendername(String sendername) {
                this.sendername = sendername;
            }

            public String getSenderPic() {
                return senderPic;
            }

            public void setSenderPic(String senderPic) {
                this.senderPic = senderPic;
            }

            public String getSenderprofession() {
                return senderprofession;
            }

            public void setSenderprofession(String senderprofession) {
                this.senderprofession = senderprofession;
            }

            public String getSenderwebsite() {
                return senderwebsite;
            }

            public void setSenderwebsite(String senderwebsite) {
                this.senderwebsite = senderwebsite;
            }

            public String getSendercity() {
                return sendercity;
            }

            public void setSendercity(String sendercity) {
                this.sendercity = sendercity;
            }

            public String getSenderlikecount() {
                return senderlikecount;
            }

            public void setSenderlikecount(String senderlikecount) {
                this.senderlikecount = senderlikecount;
            }

            public String getSenderfollowcount() {
                return senderfollowcount;
            }

            public void setSenderfollowcount(String senderfollowcount) {
                this.senderfollowcount = senderfollowcount;
            }

            public String getSenderlikestatus() {
                return senderlikestatus;
            }

            public void setSenderlikestatus(String senderlikestatus) {
                this.senderlikestatus = senderlikestatus;
            }

            public String getSenderfollowstatus() {
                return senderfollowstatus;
            }

            public void setSenderfollowstatus(String senderfollowstatus) {
                this.senderfollowstatus = senderfollowstatus;
            }

            public String getReceivername() {
                return receivername;
            }

            public void setReceivername(String receivername) {
                this.receivername = receivername;
            }

            public String getReceiverPic() {
                return receiverPic;
            }

            public void setReceiverPic(String receiverPic) {
                this.receiverPic = receiverPic;
            }

            public String getReceiverprofession() {
                return receiverprofession;
            }

            public void setReceiverprofession(String receiverprofession) {
                this.receiverprofession = receiverprofession;
            }

            public String getReceiverwebsite() {
                return receiverwebsite;
            }

            public void setReceiverwebsite(String receiverwebsite) {
                this.receiverwebsite = receiverwebsite;
            }

            public String getReceivercity() {
                return receivercity;
            }

            public void setReceivercity(String receivercity) {
                this.receivercity = receivercity;
            }

            public String getReceiverlikecount() {
                return receiverlikecount;
            }

            public void setReceiverlikecount(String receiverlikecount) {
                this.receiverlikecount = receiverlikecount;
            }

            public String getReceiverfollowcount() {
                return receiverfollowcount;
            }

            public void setReceiverfollowcount(String receiverfollowcount) {
                this.receiverfollowcount = receiverfollowcount;
            }

            public String getReceiverlikestatus() {
                return receiverlikestatus;
            }

            public void setReceiverlikestatus(String receiverlikestatus) {
                this.receiverlikestatus = receiverlikestatus;
            }

            public String getReceiverfollowstatus() {
                return receiverfollowstatus;
            }

            public void setReceiverfollowstatus(String receiverfollowstatus) {
                this.receiverfollowstatus = receiverfollowstatus;
            }

            public String getStorelikestatus() {
                return storelikestatus;
            }

            public void setStorelikestatus(String storelikestatus) {
                this.storelikestatus = storelikestatus;
            }

            public String getStorefollowstatus() {
                return storefollowstatus;
            }

            public void setStorefollowstatus(String storefollowstatus) {
                this.storefollowstatus = storefollowstatus;
            }

            public String getStorerating() {
                return storerating;
            }

            public void setStorerating(String storerating) {
                this.storerating = storerating;
            }

            public String getStorelikecount() {
                return storelikecount;
            }

            public void setStorelikecount(String storelikecount) {
                this.storelikecount = storelikecount;
            }

            public String getStorefollowcount() {
                return storefollowcount;
            }

            public void setStorefollowcount(String storefollowcount) {
                this.storefollowcount = storefollowcount;
            }

            public String getStoretiming() {
                return storetiming;
            }

            public void setStoretiming(String storetiming) {
                this.storetiming = storetiming;
            }

            public String getStoreContact() {
                return storeContact;
            }

            public void setStoreContact(String storeContact) {
                this.storeContact = storeContact;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getStoreImage() {
                return storeImage;
            }

            public void setStoreImage(String storeImage) {
                this.storeImage = storeImage;
            }

            public String getStoreType() {
                return storeType;
            }

            public void setStoreType(String storeType) {
                this.storeType = storeType;
            }

            public String getStoreWebsite() {
                return storeWebsite;
            }

            public void setStoreWebsite(String storeWebsite) {
                this.storeWebsite = storeWebsite;
            }

            public String getWorkingDays() {
                return workingDays;
            }

            public void setWorkingDays(String workingDays) {
                this.workingDays = workingDays;
            }

            public String getStoreLocation() {
                return storeLocation;
            }

            public void setStoreLocation(String storeLocation) {
                this.storeLocation = storeLocation;
            }

            public String getGroupVehicles() {
                return groupVehicles;
            }

            public void setGroupVehicles(String groupVehicles) {
                this.groupVehicles = groupVehicles;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getGroupImage() {
                return groupImage;
            }

            public void setGroupImage(String groupImage) {
                this.groupImage = groupImage;
            }

            public String getGroupMembers() {
                return groupMembers;
            }

            public void setGroupMembers(String groupMembers) {
                this.groupMembers = groupMembers;
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

            public String getProductlikecount() {
                return productlikecount;
            }

            public void setProductlikecount(String productlikecount) {
                this.productlikecount = productlikecount;
            }

            public String getProductfollowcount() {
                return productfollowcount;
            }

            public void setProductfollowcount(String productfollowcount) {
                this.productfollowcount = productfollowcount;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public String getProductimages() {
                return productimages;
            }

            public void setProductimages(String productimages) {
                this.productimages = productimages;
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

            public String getServicelikecount() {
                return servicelikecount;
            }

            public void setServicelikecount(String servicelikecount) {
                this.servicelikecount = servicelikecount;
            }

            public String getServicefollowcount() {
                return servicefollowcount;
            }

            public void setServicefollowcount(String servicefollowcount) {
                this.servicefollowcount = servicefollowcount;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public String getServiceimages() {
                return serviceimages;
            }

            public void setServiceimages(String serviceimages) {
                this.serviceimages = serviceimages;
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

            public String getVehiclelikecount() {
                return vehiclelikecount;
            }

            public void setVehiclelikecount(String vehiclelikecount) {
                this.vehiclelikecount = vehiclelikecount;
            }

            public String getVehiclefollowcount() {
                return vehiclefollowcount;
            }

            public void setVehiclefollowcount(String vehiclefollowcount) {
                this.vehiclefollowcount = vehiclefollowcount;
            }

            public String getVehicleContact() {
                return vehicleContact;
            }

            public void setVehicleContact(String vehicleContact) {
                this.vehicleContact = vehicleContact;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

        }

        public class BuyerSearch {

            @SerializedName("favid")
            @Expose
            private String favid;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("Ssearch_id")
            @Expose
            private String ssearchId;
            @SerializedName("Scontact_no")
            @Expose
            private String scontactNo;
            @SerializedName("Sstatus")
            @Expose
            private String sstatus;
            @SerializedName("Scategory")
            @Expose
            private String scategory;
            @SerializedName("Smanufacturer")
            @Expose
            private String smanufacturer;
            @SerializedName("Smodel")
            @Expose
            private String smodel;
            @SerializedName("Sversion")
            @Expose
            private String sversion;
            @SerializedName("Srto_city")
            @Expose
            private String srtoCity;
            @SerializedName("Srto_city2")
            @Expose
            private String srtoCity2;
            @SerializedName("Srto_city3")
            @Expose
            private String srtoCity3;
            @SerializedName("Srto_city4")
            @Expose
            private String srtoCity4;
            @SerializedName("Srto_city5")
            @Expose
            private String srtoCity5;
            @SerializedName("Slocation_city")
            @Expose
            private String slocationCity;
            @SerializedName("Slocation_city2")
            @Expose
            private String slocationCity2;
            @SerializedName("Slocation_city3")
            @Expose
            private String slocationCity3;
            @SerializedName("Slocation_city4")
            @Expose
            private String slocationCity4;
            @SerializedName("Slocation_city5")
            @Expose
            private String slocationCity5;
            @SerializedName("Slocation_state")
            @Expose
            private String slocationState;
            @SerializedName("Syear_of_registration")
            @Expose
            private String syearOfRegistration;
            @SerializedName("Scolor")
            @Expose
            private String scolor;
            @SerializedName("Src_available")
            @Expose
            private String srcAvailable;
            @SerializedName("Syear_of_manufacture")
            @Expose
            private String syearOfManufacture;
            @SerializedName("Sinsurance_valid")
            @Expose
            private String sinsuranceValid;
            @SerializedName("Stax_validity")
            @Expose
            private String staxValidity;
            @SerializedName("Sfitness_validity")
            @Expose
            private String sfitnessValidity;
            @SerializedName("Spermit_validity")
            @Expose
            private String spermitValidity;
            @SerializedName("Sfual_type")
            @Expose
            private String sfualType;
            @SerializedName("Sseating_capacity")
            @Expose
            private String sseatingCapacity;
            @SerializedName("Spermit")
            @Expose
            private String spermit;
            @SerializedName("Skms_running")
            @Expose
            private String skmsRunning;
            @SerializedName("Sno_of_owners")
            @Expose
            private String snoOfOwners;
            @SerializedName("Shypothication")
            @Expose
            private String shypothication;
            @SerializedName("Sprice")
            @Expose
            private String sprice;
            @SerializedName("Sdrive")
            @Expose
            private String sdrive;
            @SerializedName("Stransmission")
            @Expose
            private String stransmission;
            @SerializedName("Sbody_type")
            @Expose
            private String sbodyType;
            @SerializedName("Sboat_type")
            @Expose
            private String sboatType;
            @SerializedName("Srv_type")
            @Expose
            private String srvType;
            @SerializedName("Sapplication")
            @Expose
            private String sapplication;
            @SerializedName("Sdate")
            @Expose
            private String sdate;
            @SerializedName("Scalldate")
            @Expose
            private String scalldate;
            @SerializedName("Sstopdate")
            @Expose
            private String sstopdate;
            @SerializedName("Styre_condition")
            @Expose
            private String styreCondition;
            @SerializedName("Simplements")
            @Expose
            private String simplements;
            @SerializedName("Sbus_type")
            @Expose
            private String sbusType;
            @SerializedName("Sair_condition")
            @Expose
            private String sairCondition;
            @SerializedName("Sfinance_req")
            @Expose
            private String sfinanceReq;
            @SerializedName("Sinvoice")
            @Expose
            private String sinvoice;
            @SerializedName("Vvehicle_id")
            @Expose
            private String vvehicleId;
            @SerializedName("Vtitle")
            @Expose
            private String vtitle;
            @SerializedName("Vcontact_no")
            @Expose
            private String vcontactNo;
            @SerializedName("Vcategory")
            @Expose
            private String vcategory;
            @SerializedName("Vmodel")
            @Expose
            private String vmodel;
            @SerializedName("Vmanufacturer")
            @Expose
            private String vmanufacturer;
            @SerializedName("VVersion")
            @Expose
            private String vVersion;
            @SerializedName("Vrto_city")
            @Expose
            private String vrtoCity;
            @SerializedName("Vlocation_city")
            @Expose
            private String vlocationCity;
            @SerializedName("Vlocation_state")
            @Expose
            private String vlocationState;
            @SerializedName("Vlocation_country")
            @Expose
            private String vlocationCountry;
            @SerializedName("Vmonth_of_registration")
            @Expose
            private String vmonthOfRegistration;
            @SerializedName("Vyear_of_registration")
            @Expose
            private String vyearOfRegistration;
            @SerializedName("Vmonth_of_manufacture")
            @Expose
            private String vmonthOfManufacture;
            @SerializedName("Vyear_of_manufacture")
            @Expose
            private String vyearOfManufacture;
            @SerializedName("Vcolor")
            @Expose
            private String vcolor;
            @SerializedName("Vregistration_number")
            @Expose
            private String vregistrationNumber;
            @SerializedName("Vrc_available")
            @Expose
            private String vrcAvailable;
            @SerializedName("Vinsurance_valid")
            @Expose
            private String vinsuranceValid;
            @SerializedName("Vinsurance_idv")
            @Expose
            private String vinsuranceIdv;
            @SerializedName("Vtax_validity")
            @Expose
            private String vtaxValidity;
            @SerializedName("Vtax_paid_upto")
            @Expose
            private String vtaxPaidUpto;
            @SerializedName("Vfitness_validity")
            @Expose
            private String vfitnessValidity;
            @SerializedName("Vpermit_validity")
            @Expose
            private String vpermitValidity;
            @SerializedName("Vpermit_yesno")
            @Expose
            private String vpermitYesno;
            @SerializedName("Vfitness_yesno")
            @Expose
            private String vfitnessYesno;
            @SerializedName("Vfual_type")
            @Expose
            private String vfualType;
            @SerializedName("Vseating_capacity")
            @Expose
            private String vseatingCapacity;
            @SerializedName("Vpermit")
            @Expose
            private String vpermit;
            @SerializedName("Vkms_running")
            @Expose
            private String vkmsRunning;
            @SerializedName("VHrs_running")
            @Expose
            private String vHrsRunning;
            @SerializedName("Vno_of_owners")
            @Expose
            private String vnoOfOwners;
            @SerializedName("Vhypothication")
            @Expose
            private String vhypothication;
            @SerializedName("Vengine_no")
            @Expose
            private String vengineNo;
            @SerializedName("Vchassis_no")
            @Expose
            private String vchassisNo;
            @SerializedName("Vprice")
            @Expose
            private String vprice;
            @SerializedName("Vimage")
            @Expose
            private String vimage;
            @SerializedName("Vdrive")
            @Expose
            private String vdrive;
            @SerializedName("Vtransmission")
            @Expose
            private String vtransmission;
            @SerializedName("Vbody_type")
            @Expose
            private String vbodyType;
            @SerializedName("Vboat_type")
            @Expose
            private String vboatType;
            @SerializedName("Vrv_type")
            @Expose
            private String vrvType;
            @SerializedName("Vapplication")
            @Expose
            private String vapplication;
            @SerializedName("Vtyre_condition")
            @Expose
            private String vtyreCondition;
            @SerializedName("Vbus_type")
            @Expose
            private String vbusType;
            @SerializedName("Vair_condition")
            @Expose
            private String vairCondition;
            @SerializedName("Vinvoice")
            @Expose
            private String vinvoice;
            @SerializedName("Vimplements")
            @Expose
            private String vimplements;
            @SerializedName("Vfinance_req")
            @Expose
            private String vfinanceReq;
            @SerializedName("Vprivacy")
            @Expose
            private String vprivacy;
            @SerializedName("Vviewcount")
            @Expose
            private String vviewcount;
            @SerializedName("Vcallcount")
            @Expose
            private String vcallcount;
            @SerializedName("Vdate")
            @Expose
            private String vdate;
            @SerializedName("Vstart_price")
            @Expose
            private String vstartPrice;
            @SerializedName("Vreserve_price")
            @Expose
            private String vreservePrice;
            @SerializedName("Vstatus")
            @Expose
            private String vstatus;
            @SerializedName("sendername")
            @Expose
            private String sendername;
            @SerializedName("sender_pic")
            @Expose
            private String senderPic;

            public String getvVersion() {
                return vVersion;
            }

            public void setvVersion(String vVersion) {
                this.vVersion = vVersion;
            }

            public String getvHrsRunning() {
                return vHrsRunning;
            }

            public void setvHrsRunning(String vHrsRunning) {
                this.vHrsRunning = vHrsRunning;
            }


            @SerializedName("Vhpcapacity")
            @Expose
            private String vhpCapacity;

            public String getVhpCapacity() {
                return vhpCapacity;
            }

            public void setVhpCapacity(String vhpCapacity) {
                this.vhpCapacity = vhpCapacity;
            }

            @SerializedName("Shpcapacity")
            @Expose
            private String shpCapacity;

            public String getShpCapacity() {
                return shpCapacity;
            }

            public void setShpCapacity(String shpCapacity) {
                this.shpCapacity = shpCapacity;
            }

            public String getFavid() {
                return favid;
            }

            public void setFavid(String favid) {
                this.favid = favid;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSsearchId() {
                return ssearchId;
            }

            public void setSsearchId(String ssearchId) {
                this.ssearchId = ssearchId;
            }

            public String getScontactNo() {
                return scontactNo;
            }

            public void setScontactNo(String scontactNo) {
                this.scontactNo = scontactNo;
            }

            public String getSstatus() {
                return sstatus;
            }

            public void setSstatus(String sstatus) {
                this.sstatus = sstatus;
            }

            public String getScategory() {
                return scategory;
            }

            public void setScategory(String scategory) {
                this.scategory = scategory;
            }

            public String getSmanufacturer() {
                return smanufacturer;
            }

            public void setSmanufacturer(String smanufacturer) {
                this.smanufacturer = smanufacturer;
            }

            public String getSmodel() {
                return smodel;
            }

            public void setSmodel(String smodel) {
                this.smodel = smodel;
            }

            public String getSversion() {
                return sversion;
            }

            public void setSversion(String sversion) {
                this.sversion = sversion;
            }

            public String getSrtoCity() {
                return srtoCity;
            }

            public void setSrtoCity(String srtoCity) {
                this.srtoCity = srtoCity;
            }

            public String getSrtoCity2() {
                return srtoCity2;
            }

            public void setSrtoCity2(String srtoCity2) {
                this.srtoCity2 = srtoCity2;
            }

            public String getSrtoCity3() {
                return srtoCity3;
            }

            public void setSrtoCity3(String srtoCity3) {
                this.srtoCity3 = srtoCity3;
            }

            public String getSrtoCity4() {
                return srtoCity4;
            }

            public void setSrtoCity4(String srtoCity4) {
                this.srtoCity4 = srtoCity4;
            }

            public String getSrtoCity5() {
                return srtoCity5;
            }

            public void setSrtoCity5(String srtoCity5) {
                this.srtoCity5 = srtoCity5;
            }

            public String getSlocationCity() {
                return slocationCity;
            }

            public void setSlocationCity(String slocationCity) {
                this.slocationCity = slocationCity;
            }

            public String getSlocationCity2() {
                return slocationCity2;
            }

            public void setSlocationCity2(String slocationCity2) {
                this.slocationCity2 = slocationCity2;
            }

            public String getSlocationCity3() {
                return slocationCity3;
            }

            public void setSlocationCity3(String slocationCity3) {
                this.slocationCity3 = slocationCity3;
            }

            public String getSlocationCity4() {
                return slocationCity4;
            }

            public void setSlocationCity4(String slocationCity4) {
                this.slocationCity4 = slocationCity4;
            }

            public String getSlocationCity5() {
                return slocationCity5;
            }

            public void setSlocationCity5(String slocationCity5) {
                this.slocationCity5 = slocationCity5;
            }

            public String getSlocationState() {
                return slocationState;
            }

            public void setSlocationState(String slocationState) {
                this.slocationState = slocationState;
            }

            public String getSyearOfRegistration() {
                return syearOfRegistration;
            }

            public void setSyearOfRegistration(String syearOfRegistration) {
                this.syearOfRegistration = syearOfRegistration;
            }

            public String getScolor() {
                return scolor;
            }

            public void setScolor(String scolor) {
                this.scolor = scolor;
            }

            public String getSrcAvailable() {
                return srcAvailable;
            }

            public void setSrcAvailable(String srcAvailable) {
                this.srcAvailable = srcAvailable;
            }

            public String getSyearOfManufacture() {
                return syearOfManufacture;
            }

            public void setSyearOfManufacture(String syearOfManufacture) {
                this.syearOfManufacture = syearOfManufacture;
            }

            public String getSinsuranceValid() {
                return sinsuranceValid;
            }

            public void setSinsuranceValid(String sinsuranceValid) {
                this.sinsuranceValid = sinsuranceValid;
            }

            public String getStaxValidity() {
                return staxValidity;
            }

            public void setStaxValidity(String staxValidity) {
                this.staxValidity = staxValidity;
            }

            public String getSfitnessValidity() {
                return sfitnessValidity;
            }

            public void setSfitnessValidity(String sfitnessValidity) {
                this.sfitnessValidity = sfitnessValidity;
            }

            public String getSpermitValidity() {
                return spermitValidity;
            }

            public void setSpermitValidity(String spermitValidity) {
                this.spermitValidity = spermitValidity;
            }

            public String getSfualType() {
                return sfualType;
            }

            public void setSfualType(String sfualType) {
                this.sfualType = sfualType;
            }

            public String getSseatingCapacity() {
                return sseatingCapacity;
            }

            public void setSseatingCapacity(String sseatingCapacity) {
                this.sseatingCapacity = sseatingCapacity;
            }

            public String getSpermit() {
                return spermit;
            }

            public void setSpermit(String spermit) {
                this.spermit = spermit;
            }

            public String getSkmsRunning() {
                return skmsRunning;
            }

            public void setSkmsRunning(String skmsRunning) {
                this.skmsRunning = skmsRunning;
            }

            public String getSnoOfOwners() {
                return snoOfOwners;
            }

            public void setSnoOfOwners(String snoOfOwners) {
                this.snoOfOwners = snoOfOwners;
            }

            public String getShypothication() {
                return shypothication;
            }

            public void setShypothication(String shypothication) {
                this.shypothication = shypothication;
            }

            public String getSprice() {
                return sprice;
            }

            public void setSprice(String sprice) {
                this.sprice = sprice;
            }

            public String getSdrive() {
                return sdrive;
            }

            public void setSdrive(String sdrive) {
                this.sdrive = sdrive;
            }

            public String getStransmission() {
                return stransmission;
            }

            public void setStransmission(String stransmission) {
                this.stransmission = stransmission;
            }

            public String getSbodyType() {
                return sbodyType;
            }

            public void setSbodyType(String sbodyType) {
                this.sbodyType = sbodyType;
            }

            public String getSboatType() {
                return sboatType;
            }

            public void setSboatType(String sboatType) {
                this.sboatType = sboatType;
            }

            public String getSrvType() {
                return srvType;
            }

            public void setSrvType(String srvType) {
                this.srvType = srvType;
            }

            public String getSapplication() {
                return sapplication;
            }

            public void setSapplication(String sapplication) {
                this.sapplication = sapplication;
            }

            public String getSdate() {
                return sdate;
            }

            public void setSdate(String sdate) {
                this.sdate = sdate;
            }

            public String getScalldate() {
                return scalldate;
            }

            public void setScalldate(String scalldate) {
                this.scalldate = scalldate;
            }

            public String getSstopdate() {
                return sstopdate;
            }

            public void setSstopdate(String sstopdate) {
                this.sstopdate = sstopdate;
            }

            public String getStyreCondition() {
                return styreCondition;
            }

            public void setStyreCondition(String styreCondition) {
                this.styreCondition = styreCondition;
            }

            public String getSimplements() {
                return simplements;
            }

            public void setSimplements(String simplements) {
                this.simplements = simplements;
            }

            public String getSbusType() {
                return sbusType;
            }

            public void setSbusType(String sbusType) {
                this.sbusType = sbusType;
            }

            public String getSairCondition() {
                return sairCondition;
            }

            public void setSairCondition(String sairCondition) {
                this.sairCondition = sairCondition;
            }

            public String getSfinanceReq() {
                return sfinanceReq;
            }

            public void setSfinanceReq(String sfinanceReq) {
                this.sfinanceReq = sfinanceReq;
            }

            public String getSinvoice() {
                return sinvoice;
            }

            public void setSinvoice(String sinvoice) {
                this.sinvoice = sinvoice;
            }

            public String getVvehicleId() {
                return vvehicleId;
            }

            public void setVvehicleId(String vvehicleId) {
                this.vvehicleId = vvehicleId;
            }

            public String getVtitle() {
                return vtitle;
            }

            public void setVtitle(String vtitle) {
                this.vtitle = vtitle;
            }

            public String getVcontactNo() {
                return vcontactNo;
            }

            public void setVcontactNo(String vcontactNo) {
                this.vcontactNo = vcontactNo;
            }

            public String getVcategory() {
                return vcategory;
            }

            public void setVcategory(String vcategory) {
                this.vcategory = vcategory;
            }

            public String getVmodel() {
                return vmodel;
            }

            public void setVmodel(String vmodel) {
                this.vmodel = vmodel;
            }

            public String getVmanufacturer() {
                return vmanufacturer;
            }

            public void setVmanufacturer(String vmanufacturer) {
                this.vmanufacturer = vmanufacturer;
            }

            public String getVVersion() {
                return vVersion;
            }

            public void setVVersion(String vVersion) {
                this.vVersion = vVersion;
            }

            public String getVrtoCity() {
                return vrtoCity;
            }

            public void setVrtoCity(String vrtoCity) {
                this.vrtoCity = vrtoCity;
            }

            public String getVlocationCity() {
                return vlocationCity;
            }

            public void setVlocationCity(String vlocationCity) {
                this.vlocationCity = vlocationCity;
            }

            public String getVlocationState() {
                return vlocationState;
            }

            public void setVlocationState(String vlocationState) {
                this.vlocationState = vlocationState;
            }

            public String getVlocationCountry() {
                return vlocationCountry;
            }

            public void setVlocationCountry(String vlocationCountry) {
                this.vlocationCountry = vlocationCountry;
            }

            public String getVmonthOfRegistration() {
                return vmonthOfRegistration;
            }

            public void setVmonthOfRegistration(String vmonthOfRegistration) {
                this.vmonthOfRegistration = vmonthOfRegistration;
            }

            public String getVyearOfRegistration() {
                return vyearOfRegistration;
            }

            public void setVyearOfRegistration(String vyearOfRegistration) {
                this.vyearOfRegistration = vyearOfRegistration;
            }

            public String getVmonthOfManufacture() {
                return vmonthOfManufacture;
            }

            public void setVmonthOfManufacture(String vmonthOfManufacture) {
                this.vmonthOfManufacture = vmonthOfManufacture;
            }

            public String getVyearOfManufacture() {
                return vyearOfManufacture;
            }

            public void setVyearOfManufacture(String vyearOfManufacture) {
                this.vyearOfManufacture = vyearOfManufacture;
            }

            public String getVcolor() {
                return vcolor;
            }

            public void setVcolor(String vcolor) {
                this.vcolor = vcolor;
            }

            public String getVregistrationNumber() {
                return vregistrationNumber;
            }

            public void setVregistrationNumber(String vregistrationNumber) {
                this.vregistrationNumber = vregistrationNumber;
            }

            public String getVrcAvailable() {
                return vrcAvailable;
            }

            public void setVrcAvailable(String vrcAvailable) {
                this.vrcAvailable = vrcAvailable;
            }

            public String getVinsuranceValid() {
                return vinsuranceValid;
            }

            public void setVinsuranceValid(String vinsuranceValid) {
                this.vinsuranceValid = vinsuranceValid;
            }

            public String getVinsuranceIdv() {
                return vinsuranceIdv;
            }

            public void setVinsuranceIdv(String vinsuranceIdv) {
                this.vinsuranceIdv = vinsuranceIdv;
            }

            public String getVtaxValidity() {
                return vtaxValidity;
            }

            public void setVtaxValidity(String vtaxValidity) {
                this.vtaxValidity = vtaxValidity;
            }

            public String getVtaxPaidUpto() {
                return vtaxPaidUpto;
            }

            public void setVtaxPaidUpto(String vtaxPaidUpto) {
                this.vtaxPaidUpto = vtaxPaidUpto;
            }

            public String getVfitnessValidity() {
                return vfitnessValidity;
            }

            public void setVfitnessValidity(String vfitnessValidity) {
                this.vfitnessValidity = vfitnessValidity;
            }

            public String getVpermitValidity() {
                return vpermitValidity;
            }

            public void setVpermitValidity(String vpermitValidity) {
                this.vpermitValidity = vpermitValidity;
            }

            public String getVpermitYesno() {
                return vpermitYesno;
            }

            public void setVpermitYesno(String vpermitYesno) {
                this.vpermitYesno = vpermitYesno;
            }

            public String getVfitnessYesno() {
                return vfitnessYesno;
            }

            public void setVfitnessYesno(String vfitnessYesno) {
                this.vfitnessYesno = vfitnessYesno;
            }

            public String getVfualType() {
                return vfualType;
            }

            public void setVfualType(String vfualType) {
                this.vfualType = vfualType;
            }

            public String getVseatingCapacity() {
                return vseatingCapacity;
            }

            public void setVseatingCapacity(String vseatingCapacity) {
                this.vseatingCapacity = vseatingCapacity;
            }

            public String getVpermit() {
                return vpermit;
            }

            public void setVpermit(String vpermit) {
                this.vpermit = vpermit;
            }

            public String getVkmsRunning() {
                return vkmsRunning;
            }

            public void setVkmsRunning(String vkmsRunning) {
                this.vkmsRunning = vkmsRunning;
            }

            public String getVHrsRunning() {
                return vHrsRunning;
            }

            public void setVHrsRunning(String vHrsRunning) {
                this.vHrsRunning = vHrsRunning;
            }

            public String getVnoOfOwners() {
                return vnoOfOwners;
            }

            public void setVnoOfOwners(String vnoOfOwners) {
                this.vnoOfOwners = vnoOfOwners;
            }

            public String getVhypothication() {
                return vhypothication;
            }

            public void setVhypothication(String vhypothication) {
                this.vhypothication = vhypothication;
            }

            public String getVengineNo() {
                return vengineNo;
            }

            public void setVengineNo(String vengineNo) {
                this.vengineNo = vengineNo;
            }

            public String getVchassisNo() {
                return vchassisNo;
            }

            public void setVchassisNo(String vchassisNo) {
                this.vchassisNo = vchassisNo;
            }

            public String getVprice() {
                return vprice;
            }

            public void setVprice(String vprice) {
                this.vprice = vprice;
            }

            public String getVimage() {
                return vimage;
            }

            public void setVimage(String vimage) {
                this.vimage = vimage;
            }

            public String getVdrive() {
                return vdrive;
            }

            public void setVdrive(String vdrive) {
                this.vdrive = vdrive;
            }

            public String getVtransmission() {
                return vtransmission;
            }

            public void setVtransmission(String vtransmission) {
                this.vtransmission = vtransmission;
            }

            public String getVbodyType() {
                return vbodyType;
            }

            public void setVbodyType(String vbodyType) {
                this.vbodyType = vbodyType;
            }

            public String getVboatType() {
                return vboatType;
            }

            public void setVboatType(String vboatType) {
                this.vboatType = vboatType;
            }

            public String getVrvType() {
                return vrvType;
            }

            public void setVrvType(String vrvType) {
                this.vrvType = vrvType;
            }

            public String getVapplication() {
                return vapplication;
            }

            public void setVapplication(String vapplication) {
                this.vapplication = vapplication;
            }

            public String getVtyreCondition() {
                return vtyreCondition;
            }

            public void setVtyreCondition(String vtyreCondition) {
                this.vtyreCondition = vtyreCondition;
            }

            public String getVbusType() {
                return vbusType;
            }

            public void setVbusType(String vbusType) {
                this.vbusType = vbusType;
            }

            public String getVairCondition() {
                return vairCondition;
            }

            public void setVairCondition(String vairCondition) {
                this.vairCondition = vairCondition;
            }

            public String getVinvoice() {
                return vinvoice;
            }

            public void setVinvoice(String vinvoice) {
                this.vinvoice = vinvoice;
            }

            public String getVimplements() {
                return vimplements;
            }

            public void setVimplements(String vimplements) {
                this.vimplements = vimplements;
            }

            public String getVfinanceReq() {
                return vfinanceReq;
            }

            public void setVfinanceReq(String vfinanceReq) {
                this.vfinanceReq = vfinanceReq;
            }

            public String getVprivacy() {
                return vprivacy;
            }

            public void setVprivacy(String vprivacy) {
                this.vprivacy = vprivacy;
            }

            public String getVviewcount() {
                return vviewcount;
            }

            public void setVviewcount(String vviewcount) {
                this.vviewcount = vviewcount;
            }

            public String getVcallcount() {
                return vcallcount;
            }

            public void setVcallcount(String vcallcount) {
                this.vcallcount = vcallcount;
            }

            public String getVdate() {
                return vdate;
            }

            public void setVdate(String vdate) {
                this.vdate = vdate;
            }

            public String getVstartPrice() {
                return vstartPrice;
            }

            public void setVstartPrice(String vstartPrice) {
                this.vstartPrice = vstartPrice;
            }

            public String getVreservePrice() {
                return vreservePrice;
            }

            public void setVreservePrice(String vreservePrice) {
                this.vreservePrice = vreservePrice;
            }

            public String getVstatus() {
                return vstatus;
            }

            public void setVstatus(String vstatus) {
                this.vstatus = vstatus;
            }

            public String getSendername() {
                return sendername;
            }

            public void setSendername(String sendername) {
                this.sendername = sendername;
            }

            public String getSenderPic() {
                return senderPic;
            }

            public void setSenderPic(String senderPic) {
                this.senderPic = senderPic;
            }

        }

        public class SellerVehicle {

            @SerializedName("favid")
            @Expose
            private String favid;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("Vvehicle_id")
            @Expose
            private String vvehicleId;
            @SerializedName("Vtitle")
            @Expose
            private String vtitle;
            @SerializedName("Vcontact_no")
            @Expose
            private String vcontactNo;
            @SerializedName("Vcategory")
            @Expose
            private String vcategory;
            @SerializedName("Vmodel")
            @Expose
            private String vmodel;
            @SerializedName("Vmanufacturer")
            @Expose
            private String vmanufacturer;
            @SerializedName("VVersion")
            @Expose
            private String vVersion;
            @SerializedName("Vrto_city")
            @Expose
            private String vrtoCity;
            @SerializedName("Vlocation_city")
            @Expose
            private String vlocationCity;
            @SerializedName("Vlocation_state")
            @Expose
            private String vlocationState;
            @SerializedName("Vlocation_country")
            @Expose
            private String vlocationCountry;
            @SerializedName("Vmonth_of_registration")
            @Expose
            private String vmonthOfRegistration;
            @SerializedName("Vyear_of_registration")
            @Expose
            private String vyearOfRegistration;
            @SerializedName("Vmonth_of_manufacture")
            @Expose
            private String vmonthOfManufacture;
            @SerializedName("Vyear_of_manufacture")
            @Expose
            private String vyearOfManufacture;
            @SerializedName("Vcolor")
            @Expose
            private String vcolor;
            @SerializedName("Vregistration_number")
            @Expose
            private String vregistrationNumber;
            @SerializedName("Vrc_available")
            @Expose
            private String vrcAvailable;
            @SerializedName("Vinsurance_valid")
            @Expose
            private String vinsuranceValid;
            @SerializedName("Vinsurance_idv")
            @Expose
            private String vinsuranceIdv;
            @SerializedName("Vtax_validity")
            @Expose
            private String vtaxValidity;
            @SerializedName("Vtax_paid_upto")
            @Expose
            private String vtaxPaidUpto;
            @SerializedName("Vfitness_validity")
            @Expose
            private String vfitnessValidity;
            @SerializedName("Vpermit_validity")
            @Expose
            private String vpermitValidity;
            @SerializedName("Vpermit_yesno")
            @Expose
            private String vpermitYesno;
            @SerializedName("Vfitness_yesno")
            @Expose
            private String vfitnessYesno;
            @SerializedName("Vfual_type")
            @Expose
            private String vfualType;
            @SerializedName("Vseating_capacity")
            @Expose
            private String vseatingCapacity;
            @SerializedName("Vpermit")
            @Expose
            private String vpermit;
            @SerializedName("Vkms_running")
            @Expose
            private String vkmsRunning;
            @SerializedName("VHrs_running")
            @Expose
            private String vHrsRunning;
            @SerializedName("Vno_of_owners")
            @Expose
            private String vnoOfOwners;
            @SerializedName("Vhypothication")
            @Expose
            private String vhypothication;
            @SerializedName("Vengine_no")
            @Expose
            private String vengineNo;
            @SerializedName("Vchassis_no")
            @Expose
            private String vchassisNo;
            @SerializedName("Vprice")
            @Expose
            private String vprice;
            @SerializedName("Vimage")
            @Expose
            private String vimage;
            @SerializedName("Vdrive")
            @Expose
            private String vdrive;
            @SerializedName("Vtransmission")
            @Expose
            private String vtransmission;
            @SerializedName("Vbody_type")
            @Expose
            private String vbodyType;
            @SerializedName("Vboat_type")
            @Expose
            private String vboatType;
            @SerializedName("Vrv_type")
            @Expose
            private String vrvType;
            @SerializedName("Vapplication")
            @Expose
            private String vapplication;
            @SerializedName("Vtyre_condition")
            @Expose
            private String vtyreCondition;
            @SerializedName("Vbus_type")
            @Expose
            private String vbusType;
            @SerializedName("Vair_condition")
            @Expose
            private String vairCondition;
            @SerializedName("Vinvoice")
            @Expose
            private String vinvoice;
            @SerializedName("Vimplements")
            @Expose
            private String vimplements;
            @SerializedName("Vfinance_req")
            @Expose
            private String vfinanceReq;
            @SerializedName("Vprivacy")
            @Expose
            private String vprivacy;
            @SerializedName("Vviewcount")
            @Expose
            private String vviewcount;
            @SerializedName("Vcallcount")
            @Expose
            private String vcallcount;
            @SerializedName("Vdate")
            @Expose
            private String vdate;
            @SerializedName("Vstart_price")
            @Expose
            private String vstartPrice;
            @SerializedName("Vreserve_price")
            @Expose
            private String vreservePrice;
            @SerializedName("Vstatus")
            @Expose
            private String vstatus;
            @SerializedName("sendername")
            @Expose
            private String sendername;
            @SerializedName("sender_pic")
            @Expose
            private String senderPic;
            @SerializedName("Ssearch_id")
            @Expose
            private String ssearchId;
            @SerializedName("Scontact_no")
            @Expose
            private String scontactNo;
            @SerializedName("Sstatus")
            @Expose
            private String sstatus;
            @SerializedName("Scategory")
            @Expose
            private String scategory;
            @SerializedName("Smanufacturer")
            @Expose
            private String smanufacturer;
            @SerializedName("Smodel")
            @Expose
            private String smodel;
            @SerializedName("Sversion")
            @Expose
            private String sversion;
            @SerializedName("Srto_city")
            @Expose
            private String srtoCity;
            @SerializedName("Srto_city2")
            @Expose
            private String srtoCity2;
            @SerializedName("Srto_city3")
            @Expose
            private String srtoCity3;
            @SerializedName("Srto_city4")
            @Expose
            private String srtoCity4;
            @SerializedName("Srto_city5")
            @Expose
            private String srtoCity5;
            @SerializedName("Slocation_city")
            @Expose
            private String slocationCity;
            @SerializedName("Slocation_city2")
            @Expose
            private String slocationCity2;
            @SerializedName("Slocation_city3")
            @Expose
            private String slocationCity3;
            @SerializedName("Slocation_city4")
            @Expose
            private String slocationCity4;
            @SerializedName("Slocation_city5")
            @Expose
            private String slocationCity5;
            @SerializedName("Slocation_state")
            @Expose
            private String slocationState;
            @SerializedName("Syear_of_registration")
            @Expose
            private String syearOfRegistration;
            @SerializedName("Scolor")
            @Expose
            private String scolor;
            @SerializedName("Src_available")
            @Expose
            private String srcAvailable;
            @SerializedName("Syear_of_manufacture")
            @Expose
            private String syearOfManufacture;
            @SerializedName("Sinsurance_valid")
            @Expose
            private String sinsuranceValid;
            @SerializedName("Stax_validity")
            @Expose
            private String staxValidity;
            @SerializedName("Sfitness_validity")
            @Expose
            private String sfitnessValidity;
            @SerializedName("Spermit_validity")
            @Expose
            private String spermitValidity;
            @SerializedName("Sfual_type")
            @Expose
            private String sfualType;
            @SerializedName("Sseating_capacity")
            @Expose
            private String sseatingCapacity;
            @SerializedName("Spermit")
            @Expose
            private String spermit;
            @SerializedName("Skms_running")
            @Expose
            private String skmsRunning;
            @SerializedName("Sno_of_owners")
            @Expose
            private String snoOfOwners;
            @SerializedName("Shypothication")
            @Expose
            private String shypothication;
            @SerializedName("Sprice")
            @Expose
            private String sprice;
            @SerializedName("Sdrive")
            @Expose
            private String sdrive;
            @SerializedName("Stransmission")
            @Expose
            private String stransmission;
            @SerializedName("Sbody_type")
            @Expose
            private String sbodyType;
            @SerializedName("Sboat_type")
            @Expose
            private String sboatType;
            @SerializedName("Srv_type")
            @Expose
            private String srvType;
            @SerializedName("Sapplication")
            @Expose
            private String sapplication;
            @SerializedName("Sdate")
            @Expose
            private String sdate;
            @SerializedName("Scalldate")
            @Expose
            private String scalldate;
            @SerializedName("Sstopdate")
            @Expose
            private String sstopdate;
            @SerializedName("Styre_condition")
            @Expose
            private String styreCondition;
            @SerializedName("Simplements")
            @Expose
            private String simplements;
            @SerializedName("Sbus_type")
            @Expose
            private String sbusType;
            @SerializedName("Sair_condition")
            @Expose
            private String sairCondition;
            @SerializedName("Sfinance_req")
            @Expose
            private String sfinanceReq;
            @SerializedName("Sinvoice")
            @Expose
            private String sinvoice;

            @SerializedName("Vhpcapacity")
            @Expose
            private String vhpCapacity;

            public String getVhpCapacity() {
                return vhpCapacity;
            }

            public void setVhpCapacity(String vhpCapacity) {
                this.vhpCapacity = vhpCapacity;
            }

            @SerializedName("Shpcapacity")
            @Expose
            private String shpCapacity;

            public String getShpCapacity() {
                return shpCapacity;
            }

            public void setShpCapacity(String shpCapacity) {
                this.shpCapacity = shpCapacity;
            }

            public String getFavid() {
                return favid;
            }

            public void setFavid(String favid) {
                this.favid = favid;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getVvehicleId() {
                return vvehicleId;
            }

            public void setVvehicleId(String vvehicleId) {
                this.vvehicleId = vvehicleId;
            }

            public String getVtitle() {
                return vtitle;
            }

            public void setVtitle(String vtitle) {
                this.vtitle = vtitle;
            }

            public String getVcontactNo() {
                return vcontactNo;
            }

            public void setVcontactNo(String vcontactNo) {
                this.vcontactNo = vcontactNo;
            }

            public String getVcategory() {
                return vcategory;
            }

            public void setVcategory(String vcategory) {
                this.vcategory = vcategory;
            }

            public String getVmodel() {
                return vmodel;
            }

            public void setVmodel(String vmodel) {
                this.vmodel = vmodel;
            }

            public String getVmanufacturer() {
                return vmanufacturer;
            }

            public void setVmanufacturer(String vmanufacturer) {
                this.vmanufacturer = vmanufacturer;
            }

            public String getVVersion() {
                return vVersion;
            }

            public void setVVersion(String vVersion) {
                this.vVersion = vVersion;
            }

            public String getVrtoCity() {
                return vrtoCity;
            }

            public void setVrtoCity(String vrtoCity) {
                this.vrtoCity = vrtoCity;
            }

            public String getVlocationCity() {
                return vlocationCity;
            }

            public void setVlocationCity(String vlocationCity) {
                this.vlocationCity = vlocationCity;
            }

            public String getVlocationState() {
                return vlocationState;
            }

            public void setVlocationState(String vlocationState) {
                this.vlocationState = vlocationState;
            }

            public String getVlocationCountry() {
                return vlocationCountry;
            }

            public void setVlocationCountry(String vlocationCountry) {
                this.vlocationCountry = vlocationCountry;
            }

            public String getVmonthOfRegistration() {
                return vmonthOfRegistration;
            }

            public void setVmonthOfRegistration(String vmonthOfRegistration) {
                this.vmonthOfRegistration = vmonthOfRegistration;
            }

            public String getVyearOfRegistration() {
                return vyearOfRegistration;
            }

            public void setVyearOfRegistration(String vyearOfRegistration) {
                this.vyearOfRegistration = vyearOfRegistration;
            }

            public String getVmonthOfManufacture() {
                return vmonthOfManufacture;
            }

            public void setVmonthOfManufacture(String vmonthOfManufacture) {
                this.vmonthOfManufacture = vmonthOfManufacture;
            }

            public String getVyearOfManufacture() {
                return vyearOfManufacture;
            }

            public void setVyearOfManufacture(String vyearOfManufacture) {
                this.vyearOfManufacture = vyearOfManufacture;
            }

            public String getVcolor() {
                return vcolor;
            }

            public void setVcolor(String vcolor) {
                this.vcolor = vcolor;
            }

            public String getVregistrationNumber() {
                return vregistrationNumber;
            }

            public void setVregistrationNumber(String vregistrationNumber) {
                this.vregistrationNumber = vregistrationNumber;
            }

            public String getVrcAvailable() {
                return vrcAvailable;
            }

            public void setVrcAvailable(String vrcAvailable) {
                this.vrcAvailable = vrcAvailable;
            }

            public String getVinsuranceValid() {
                return vinsuranceValid;
            }

            public void setVinsuranceValid(String vinsuranceValid) {
                this.vinsuranceValid = vinsuranceValid;
            }

            public String getVinsuranceIdv() {
                return vinsuranceIdv;
            }

            public void setVinsuranceIdv(String vinsuranceIdv) {
                this.vinsuranceIdv = vinsuranceIdv;
            }

            public String getVtaxValidity() {
                return vtaxValidity;
            }

            public void setVtaxValidity(String vtaxValidity) {
                this.vtaxValidity = vtaxValidity;
            }

            public String getVtaxPaidUpto() {
                return vtaxPaidUpto;
            }

            public void setVtaxPaidUpto(String vtaxPaidUpto) {
                this.vtaxPaidUpto = vtaxPaidUpto;
            }

            public String getVfitnessValidity() {
                return vfitnessValidity;
            }

            public void setVfitnessValidity(String vfitnessValidity) {
                this.vfitnessValidity = vfitnessValidity;
            }

            public String getVpermitValidity() {
                return vpermitValidity;
            }

            public void setVpermitValidity(String vpermitValidity) {
                this.vpermitValidity = vpermitValidity;
            }

            public String getVpermitYesno() {
                return vpermitYesno;
            }

            public void setVpermitYesno(String vpermitYesno) {
                this.vpermitYesno = vpermitYesno;
            }

            public String getVfitnessYesno() {
                return vfitnessYesno;
            }

            public void setVfitnessYesno(String vfitnessYesno) {
                this.vfitnessYesno = vfitnessYesno;
            }

            public String getVfualType() {
                return vfualType;
            }

            public void setVfualType(String vfualType) {
                this.vfualType = vfualType;
            }

            public String getVseatingCapacity() {
                return vseatingCapacity;
            }

            public void setVseatingCapacity(String vseatingCapacity) {
                this.vseatingCapacity = vseatingCapacity;
            }

            public String getVpermit() {
                return vpermit;
            }

            public void setVpermit(String vpermit) {
                this.vpermit = vpermit;
            }

            public String getVkmsRunning() {
                return vkmsRunning;
            }

            public void setVkmsRunning(String vkmsRunning) {
                this.vkmsRunning = vkmsRunning;
            }

            public String getVHrsRunning() {
                return vHrsRunning;
            }

            public void setVHrsRunning(String vHrsRunning) {
                this.vHrsRunning = vHrsRunning;
            }

            public String getVnoOfOwners() {
                return vnoOfOwners;
            }

            public void setVnoOfOwners(String vnoOfOwners) {
                this.vnoOfOwners = vnoOfOwners;
            }

            public String getVhypothication() {
                return vhypothication;
            }

            public void setVhypothication(String vhypothication) {
                this.vhypothication = vhypothication;
            }

            public String getVengineNo() {
                return vengineNo;
            }

            public void setVengineNo(String vengineNo) {
                this.vengineNo = vengineNo;
            }

            public String getVchassisNo() {
                return vchassisNo;
            }

            public void setVchassisNo(String vchassisNo) {
                this.vchassisNo = vchassisNo;
            }

            public String getVprice() {
                return vprice;
            }

            public void setVprice(String vprice) {
                this.vprice = vprice;
            }

            public String getVimage() {
                return vimage;
            }

            public void setVimage(String vimage) {
                this.vimage = vimage;
            }

            public String getVdrive() {
                return vdrive;
            }

            public void setVdrive(String vdrive) {
                this.vdrive = vdrive;
            }

            public String getVtransmission() {
                return vtransmission;
            }

            public void setVtransmission(String vtransmission) {
                this.vtransmission = vtransmission;
            }

            public String getVbodyType() {
                return vbodyType;
            }

            public void setVbodyType(String vbodyType) {
                this.vbodyType = vbodyType;
            }

            public String getVboatType() {
                return vboatType;
            }

            public void setVboatType(String vboatType) {
                this.vboatType = vboatType;
            }

            public String getVrvType() {
                return vrvType;
            }

            public void setVrvType(String vrvType) {
                this.vrvType = vrvType;
            }

            public String getVapplication() {
                return vapplication;
            }

            public void setVapplication(String vapplication) {
                this.vapplication = vapplication;
            }

            public String getVtyreCondition() {
                return vtyreCondition;
            }

            public void setVtyreCondition(String vtyreCondition) {
                this.vtyreCondition = vtyreCondition;
            }

            public String getVbusType() {
                return vbusType;
            }

            public void setVbusType(String vbusType) {
                this.vbusType = vbusType;
            }

            public String getVairCondition() {
                return vairCondition;
            }

            public void setVairCondition(String vairCondition) {
                this.vairCondition = vairCondition;
            }

            public String getVinvoice() {
                return vinvoice;
            }

            public void setVinvoice(String vinvoice) {
                this.vinvoice = vinvoice;
            }

            public String getVimplements() {
                return vimplements;
            }

            public void setVimplements(String vimplements) {
                this.vimplements = vimplements;
            }

            public String getVfinanceReq() {
                return vfinanceReq;
            }

            public void setVfinanceReq(String vfinanceReq) {
                this.vfinanceReq = vfinanceReq;
            }

            public String getVprivacy() {
                return vprivacy;
            }

            public void setVprivacy(String vprivacy) {
                this.vprivacy = vprivacy;
            }

            public String getVviewcount() {
                return vviewcount;
            }

            public void setVviewcount(String vviewcount) {
                this.vviewcount = vviewcount;
            }

            public String getVcallcount() {
                return vcallcount;
            }

            public void setVcallcount(String vcallcount) {
                this.vcallcount = vcallcount;
            }

            public String getVdate() {
                return vdate;
            }

            public void setVdate(String vdate) {
                this.vdate = vdate;
            }

            public String getVstartPrice() {
                return vstartPrice;
            }

            public void setVstartPrice(String vstartPrice) {
                this.vstartPrice = vstartPrice;
            }

            public String getVreservePrice() {
                return vreservePrice;
            }

            public void setVreservePrice(String vreservePrice) {
                this.vreservePrice = vreservePrice;
            }

            public String getVstatus() {
                return vstatus;
            }

            public void setVstatus(String vstatus) {
                this.vstatus = vstatus;
            }

            public String getSendername() {
                return sendername;
            }

            public void setSendername(String sendername) {
                this.sendername = sendername;
            }

            public String getSenderPic() {
                return senderPic;
            }

            public void setSenderPic(String senderPic) {
                this.senderPic = senderPic;
            }

            public String getSsearchId() {
                return ssearchId;
            }

            public void setSsearchId(String ssearchId) {
                this.ssearchId = ssearchId;
            }

            public String getScontactNo() {
                return scontactNo;
            }

            public void setScontactNo(String scontactNo) {
                this.scontactNo = scontactNo;
            }

            public String getSstatus() {
                return sstatus;
            }

            public void setSstatus(String sstatus) {
                this.sstatus = sstatus;
            }

            public String getScategory() {
                return scategory;
            }

            public void setScategory(String scategory) {
                this.scategory = scategory;
            }

            public String getSmanufacturer() {
                return smanufacturer;
            }

            public void setSmanufacturer(String smanufacturer) {
                this.smanufacturer = smanufacturer;
            }

            public String getSmodel() {
                return smodel;
            }

            public void setSmodel(String smodel) {
                this.smodel = smodel;
            }

            public String getSversion() {
                return sversion;
            }

            public void setSversion(String sversion) {
                this.sversion = sversion;
            }

            public String getSrtoCity() {
                return srtoCity;
            }

            public void setSrtoCity(String srtoCity) {
                this.srtoCity = srtoCity;
            }

            public String getSrtoCity2() {
                return srtoCity2;
            }

            public void setSrtoCity2(String srtoCity2) {
                this.srtoCity2 = srtoCity2;
            }

            public String getSrtoCity3() {
                return srtoCity3;
            }

            public void setSrtoCity3(String srtoCity3) {
                this.srtoCity3 = srtoCity3;
            }

            public String getSrtoCity4() {
                return srtoCity4;
            }

            public void setSrtoCity4(String srtoCity4) {
                this.srtoCity4 = srtoCity4;
            }

            public String getSrtoCity5() {
                return srtoCity5;
            }

            public void setSrtoCity5(String srtoCity5) {
                this.srtoCity5 = srtoCity5;
            }

            public String getSlocationCity() {
                return slocationCity;
            }

            public void setSlocationCity(String slocationCity) {
                this.slocationCity = slocationCity;
            }

            public String getSlocationCity2() {
                return slocationCity2;
            }

            public void setSlocationCity2(String slocationCity2) {
                this.slocationCity2 = slocationCity2;
            }

            public String getSlocationCity3() {
                return slocationCity3;
            }

            public void setSlocationCity3(String slocationCity3) {
                this.slocationCity3 = slocationCity3;
            }

            public String getSlocationCity4() {
                return slocationCity4;
            }

            public void setSlocationCity4(String slocationCity4) {
                this.slocationCity4 = slocationCity4;
            }

            public String getSlocationCity5() {
                return slocationCity5;
            }

            public void setSlocationCity5(String slocationCity5) {
                this.slocationCity5 = slocationCity5;
            }

            public String getSlocationState() {
                return slocationState;
            }

            public void setSlocationState(String slocationState) {
                this.slocationState = slocationState;
            }

            public String getSyearOfRegistration() {
                return syearOfRegistration;
            }

            public void setSyearOfRegistration(String syearOfRegistration) {
                this.syearOfRegistration = syearOfRegistration;
            }

            public String getScolor() {
                return scolor;
            }

            public void setScolor(String scolor) {
                this.scolor = scolor;
            }

            public String getSrcAvailable() {
                return srcAvailable;
            }

            public void setSrcAvailable(String srcAvailable) {
                this.srcAvailable = srcAvailable;
            }

            public String getSyearOfManufacture() {
                return syearOfManufacture;
            }

            public void setSyearOfManufacture(String syearOfManufacture) {
                this.syearOfManufacture = syearOfManufacture;
            }

            public String getSinsuranceValid() {
                return sinsuranceValid;
            }

            public void setSinsuranceValid(String sinsuranceValid) {
                this.sinsuranceValid = sinsuranceValid;
            }

            public String getStaxValidity() {
                return staxValidity;
            }

            public void setStaxValidity(String staxValidity) {
                this.staxValidity = staxValidity;
            }

            public String getSfitnessValidity() {
                return sfitnessValidity;
            }

            public void setSfitnessValidity(String sfitnessValidity) {
                this.sfitnessValidity = sfitnessValidity;
            }

            public String getSpermitValidity() {
                return spermitValidity;
            }

            public void setSpermitValidity(String spermitValidity) {
                this.spermitValidity = spermitValidity;
            }

            public String getSfualType() {
                return sfualType;
            }

            public void setSfualType(String sfualType) {
                this.sfualType = sfualType;
            }

            public String getSseatingCapacity() {
                return sseatingCapacity;
            }

            public void setSseatingCapacity(String sseatingCapacity) {
                this.sseatingCapacity = sseatingCapacity;
            }

            public String getSpermit() {
                return spermit;
            }

            public void setSpermit(String spermit) {
                this.spermit = spermit;
            }

            public String getSkmsRunning() {
                return skmsRunning;
            }

            public void setSkmsRunning(String skmsRunning) {
                this.skmsRunning = skmsRunning;
            }

            public String getSnoOfOwners() {
                return snoOfOwners;
            }

            public void setSnoOfOwners(String snoOfOwners) {
                this.snoOfOwners = snoOfOwners;
            }

            public String getShypothication() {
                return shypothication;
            }

            public void setShypothication(String shypothication) {
                this.shypothication = shypothication;
            }

            public String getSprice() {
                return sprice;
            }

            public void setSprice(String sprice) {
                this.sprice = sprice;
            }

            public String getSdrive() {
                return sdrive;
            }

            public void setSdrive(String sdrive) {
                this.sdrive = sdrive;
            }

            public String getStransmission() {
                return stransmission;
            }

            public void setStransmission(String stransmission) {
                this.stransmission = stransmission;
            }

            public String getSbodyType() {
                return sbodyType;
            }

            public void setSbodyType(String sbodyType) {
                this.sbodyType = sbodyType;
            }

            public String getSboatType() {
                return sboatType;
            }

            public void setSboatType(String sboatType) {
                this.sboatType = sboatType;
            }

            public String getSrvType() {
                return srvType;
            }

            public void setSrvType(String srvType) {
                this.srvType = srvType;
            }

            public String getSapplication() {
                return sapplication;
            }

            public void setSapplication(String sapplication) {
                this.sapplication = sapplication;
            }

            public String getSdate() {
                return sdate;
            }

            public void setSdate(String sdate) {
                this.sdate = sdate;
            }

            public String getScalldate() {
                return scalldate;
            }

            public void setScalldate(String scalldate) {
                this.scalldate = scalldate;
            }

            public String getSstopdate() {
                return sstopdate;
            }

            public void setSstopdate(String sstopdate) {
                this.sstopdate = sstopdate;
            }

            public String getStyreCondition() {
                return styreCondition;
            }

            public void setStyreCondition(String styreCondition) {
                this.styreCondition = styreCondition;
            }

            public String getSimplements() {
                return simplements;
            }

            public void setSimplements(String simplements) {
                this.simplements = simplements;
            }

            public String getSbusType() {
                return sbusType;
            }

            public void setSbusType(String sbusType) {
                this.sbusType = sbusType;
            }

            public String getSairCondition() {
                return sairCondition;
            }

            public void setSairCondition(String sairCondition) {
                this.sairCondition = sairCondition;
            }

            public String getSfinanceReq() {
                return sfinanceReq;
            }

            public void setSfinanceReq(String sfinanceReq) {
                this.sfinanceReq = sfinanceReq;
            }

            public String getSinvoice() {
                return sinvoice;
            }

            public void setSinvoice(String sinvoice) {
                this.sinvoice = sinvoice;
            }

        }

        public class Search {

            @SerializedName("search_id")
            @Expose
            private String searchId;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("datetime")
            @Expose
            private String datetime;
            @SerializedName("category")
            @Expose
            private String category;
            @SerializedName("Brand")
            @Expose
            private String brand;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("rto_city")
            @Expose
            private String rtoCity;
            @SerializedName("location_city")
            @Expose
            private String locationCity;
            @SerializedName("color")
            @Expose
            private String color;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("year_of_manufactur")
            @Expose
            private String yearOfManufactur;
            @SerializedName("favid")
            @Expose
            private String favid;
            @SerializedName("images")
            @Expose
            private String images;

            public String getSearchId() {
                return searchId;
            }

            public void setSearchId(String searchId) {
                this.searchId = searchId;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
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

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getYearOfManufactur() {
                return yearOfManufactur;
            }

            public void setYearOfManufactur(String yearOfManufactur) {
                this.yearOfManufactur = yearOfManufactur;
            }

            public String getFavid() {
                return favid;
            }

            public void setFavid(String favid) {
                this.favid = favid;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

        }


    }
}


