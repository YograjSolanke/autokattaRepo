package autokatta.com.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ak-003 on 19/4/17.
 */

public class WallResponse {

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

        @SerializedName("MyAction")
        @Expose
        private List<MyAction> myAction = null;
        @SerializedName("MyNotification")
        @Expose
        private List<MyNotification> myNotification = null;
        @SerializedName("IFollowing")
        @Expose
        private List<IFollowing> iFollowing = null;

        public List<MyAction> getMyAction() {
            return myAction;
        }

        public void setMyAction(List<MyAction> myAction) {
            this.myAction = myAction;
        }

        public List<MyNotification> getMyNotification() {
            return myNotification;
        }

        public void setMyNotification(List<MyNotification> myNotification) {
            this.myNotification = myNotification;
        }

        public List<IFollowing> getIFollowing() {
            return iFollowing;
        }

        public void setIFollowing(List<IFollowing> iFollowing) {
            this.iFollowing = iFollowing;
        }

    }

    public class IFollowing {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("layout")
        @Expose
        private String layout;
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
        @SerializedName("senderlikecount")
        @Expose
        private String senderlikecount;
        @SerializedName("senderfollowcount")
        @Expose
        private String senderfollowcount;
        @SerializedName("myfvrtstatus")
        @Expose
        private String myfvrtstatus;
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
        @SerializedName("status_id")
        @Expose
        private String statusId;
        @SerializedName("statuslikestatus")
        @Expose
        private String statuslikestatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("group_vehicles")
        @Expose
        private String groupVehicles;
        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("group_name")
        @Expose
        private String groupName;
        @SerializedName("group_image")
        @Expose
        private String groupImage;
        @SerializedName("group_members")
        @Expose
        private String groupMembers;
        @SerializedName("storelikestatus")
        @Expose
        private String storelikestatus;
        @SerializedName("storefollowstatus")
        @Expose
        private String storefollowstatus;
        @SerializedName("storerating")
        @Expose
        private String storerating;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("storelikecount")
        @Expose
        private String storelikecount;
        @SerializedName("storefollowcount")
        @Expose
        private String storefollowcount;
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
        @SerializedName("storetiming")
        @Expose
        private String storetiming;
        @SerializedName("vehiclelikestatus")
        @Expose
        private String vehiclelikestatus;
        @SerializedName("vehiclefollowstatus")
        @Expose
        private String vehiclefollowstatus;
        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
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
        @SerializedName("regno")
        @Expose
        private String regno;
        @SerializedName("kms")
        @Expose
        private String kms;
        @SerializedName("hrs")
        @Expose
        private String hrs;
        @SerializedName("rto")
        @Expose
        private String rto;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("Upvehicle_id")
        @Expose
        private String upvehicleId;
        @SerializedName("Upvehiclelikestatus")
        @Expose
        private String upvehiclelikestatus;
        @SerializedName("Upvehiclefollowstatus")
        @Expose
        private String upvehiclefollowstatus;
        @SerializedName("Upvehiclelikecount")
        @Expose
        private String upvehiclelikecount;
        @SerializedName("Upvehiclefollowcount")
        @Expose
        private String upvehiclefollowcount;
        @SerializedName("UpVehicleContact")
        @Expose
        private String upVehicleContact;
        @SerializedName("Uptitle")
        @Expose
        private String uptitle;
        @SerializedName("Upimage")
        @Expose
        private String upimage;
        @SerializedName("Upprice")
        @Expose
        private String upprice;
        @SerializedName("Upmodel")
        @Expose
        private String upmodel;
        @SerializedName("Upbrand")
        @Expose
        private String upbrand;
        @SerializedName("Upyear")
        @Expose
        private String upyear;
        @SerializedName("Upregno")
        @Expose
        private String upregno;
        @SerializedName("Upkms")
        @Expose
        private String upkms;
        @SerializedName("Uphrs")
        @Expose
        private String uphrs;
        @SerializedName("Uprto")
        @Expose
        private String uprto;
        @SerializedName("Uplocation")
        @Expose
        private String uplocation;
        @SerializedName("productlikestatus")
        @Expose
        private String productlikestatus;
        @SerializedName("productfollowstatus")
        @Expose
        private String productfollowstatus;
        @SerializedName("product_id")
        @Expose
        private String productId;
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
        @SerializedName("search_id")
        @Expose
        private String searchId;
        @SerializedName("searchlikestatus")
        @Expose
        private String searchlikestatus;
        @SerializedName("scategory")
        @Expose
        private String scategory;
        @SerializedName("sBrand")
        @Expose
        private String sBrand;
        @SerializedName("smodel")
        @Expose
        private String smodel;
        @SerializedName("srto_city")
        @Expose
        private String srtoCity;
        @SerializedName("slocation_city")
        @Expose
        private String slocationCity;
        @SerializedName("scolor")
        @Expose
        private String scolor;
        @SerializedName("sprice")
        @Expose
        private String sprice;
        @SerializedName("simages")
        @Expose
        private String simages;
        @SerializedName("syear_of_manufactur")
        @Expose
        private String syearOfManufactur;
        @SerializedName("searchdate")
        @Expose
        private String searchdate;
        @SerializedName("searchleads")
        @Expose
        private String searchleads;
        @SerializedName("servicelikestatus")
        @Expose
        private String servicelikestatus;
        @SerializedName("servicefollowstatus")
        @Expose
        private String servicefollowstatus;
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("servicelikecount")
        @Expose
        private String servicelikecount;
        @SerializedName("servicefollowcount")
        @Expose
        private String servicefollowcount;
        @SerializedName("serive_name")
        @Expose
        private String seriveName;
        @SerializedName("service_type")
        @Expose
        private String serviceType;
        @SerializedName("serviceimages")
        @Expose
        private String serviceimages;
        @SerializedName("auction_id")
        @Expose
        private String auctionId;
        @SerializedName("action_title")
        @Expose
        private String actionTitle;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("no_of_vehicles")
        @Expose
        private String noOfVehicles;
        @SerializedName("auction_type")
        @Expose
        private String auctionType;
        @SerializedName("goingcount")
        @Expose
        private String goingcount;
        @SerializedName("ignorecount")
        @Expose
        private String ignorecount;

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

        public String getMyfvrtstatus() {
            return myfvrtstatus;
        }

        public void setMyfvrtstatus(String myfvrtstatus) {
            this.myfvrtstatus = myfvrtstatus;
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

        public String getStatusId() {
            return statusId;
        }

        public void setStatusId(String statusId) {
            this.statusId = statusId;
        }

        public String getStatuslikestatus() {
            return statuslikestatus;
        }

        public void setStatuslikestatus(String statuslikestatus) {
            this.statuslikestatus = statuslikestatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGroupVehicles() {
            return groupVehicles;
        }

        public void setGroupVehicles(String groupVehicles) {
            this.groupVehicles = groupVehicles;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
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

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
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

        public String getStoretiming() {
            return storetiming;
        }

        public void setStoretiming(String storetiming) {
            this.storetiming = storetiming;
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

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
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

        public String getRegno() {
            return regno;
        }

        public void setRegno(String regno) {
            this.regno = regno;
        }

        public String getKms() {
            return kms;
        }

        public void setKms(String kms) {
            this.kms = kms;
        }

        public String getHrs() {
            return hrs;
        }

        public void setHrs(String hrs) {
            this.hrs = hrs;
        }

        public String getRto() {
            return rto;
        }

        public void setRto(String rto) {
            this.rto = rto;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUpvehicleId() {
            return upvehicleId;
        }

        public void setUpvehicleId(String upvehicleId) {
            this.upvehicleId = upvehicleId;
        }

        public String getUpvehiclelikestatus() {
            return upvehiclelikestatus;
        }

        public void setUpvehiclelikestatus(String upvehiclelikestatus) {
            this.upvehiclelikestatus = upvehiclelikestatus;
        }

        public String getUpvehiclefollowstatus() {
            return upvehiclefollowstatus;
        }

        public void setUpvehiclefollowstatus(String upvehiclefollowstatus) {
            this.upvehiclefollowstatus = upvehiclefollowstatus;
        }

        public String getUpvehiclelikecount() {
            return upvehiclelikecount;
        }

        public void setUpvehiclelikecount(String upvehiclelikecount) {
            this.upvehiclelikecount = upvehiclelikecount;
        }

        public String getUpvehiclefollowcount() {
            return upvehiclefollowcount;
        }

        public void setUpvehiclefollowcount(String upvehiclefollowcount) {
            this.upvehiclefollowcount = upvehiclefollowcount;
        }

        public String getUpVehicleContact() {
            return upVehicleContact;
        }

        public void setUpVehicleContact(String upVehicleContact) {
            this.upVehicleContact = upVehicleContact;
        }

        public String getUptitle() {
            return uptitle;
        }

        public void setUptitle(String uptitle) {
            this.uptitle = uptitle;
        }

        public String getUpimage() {
            return upimage;
        }

        public void setUpimage(String upimage) {
            this.upimage = upimage;
        }

        public String getUpprice() {
            return upprice;
        }

        public void setUpprice(String upprice) {
            this.upprice = upprice;
        }

        public String getUpmodel() {
            return upmodel;
        }

        public void setUpmodel(String upmodel) {
            this.upmodel = upmodel;
        }

        public String getUpbrand() {
            return upbrand;
        }

        public void setUpbrand(String upbrand) {
            this.upbrand = upbrand;
        }

        public String getUpyear() {
            return upyear;
        }

        public void setUpyear(String upyear) {
            this.upyear = upyear;
        }

        public String getUpregno() {
            return upregno;
        }

        public void setUpregno(String upregno) {
            this.upregno = upregno;
        }

        public String getUpkms() {
            return upkms;
        }

        public void setUpkms(String upkms) {
            this.upkms = upkms;
        }

        public String getUphrs() {
            return uphrs;
        }

        public void setUphrs(String uphrs) {
            this.uphrs = uphrs;
        }

        public String getUprto() {
            return uprto;
        }

        public void setUprto(String uprto) {
            this.uprto = uprto;
        }

        public String getUplocation() {
            return uplocation;
        }

        public void setUplocation(String uplocation) {
            this.uplocation = uplocation;
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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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

        public String getSearchId() {
            return searchId;
        }

        public void setSearchId(String searchId) {
            this.searchId = searchId;
        }

        public String getSearchlikestatus() {
            return searchlikestatus;
        }

        public void setSearchlikestatus(String searchlikestatus) {
            this.searchlikestatus = searchlikestatus;
        }

        public String getScategory() {
            return scategory;
        }

        public void setScategory(String scategory) {
            this.scategory = scategory;
        }

        public String getSBrand() {
            return sBrand;
        }

        public void setSBrand(String sBrand) {
            this.sBrand = sBrand;
        }

        public String getSmodel() {
            return smodel;
        }

        public void setSmodel(String smodel) {
            this.smodel = smodel;
        }

        public String getSrtoCity() {
            return srtoCity;
        }

        public void setSrtoCity(String srtoCity) {
            this.srtoCity = srtoCity;
        }

        public String getSlocationCity() {
            return slocationCity;
        }

        public void setSlocationCity(String slocationCity) {
            this.slocationCity = slocationCity;
        }

        public String getScolor() {
            return scolor;
        }

        public void setScolor(String scolor) {
            this.scolor = scolor;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public String getSimages() {
            return simages;
        }

        public void setSimages(String simages) {
            this.simages = simages;
        }

        public String getSyearOfManufactur() {
            return syearOfManufactur;
        }

        public void setSyearOfManufactur(String syearOfManufactur) {
            this.syearOfManufactur = syearOfManufactur;
        }

        public String getSearchdate() {
            return searchdate;
        }

        public void setSearchdate(String searchdate) {
            this.searchdate = searchdate;
        }

        public String getSearchleads() {
            return searchleads;
        }

        public void setSearchleads(String searchleads) {
            this.searchleads = searchleads;
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

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
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

        public String getSeriveName() {
            return seriveName;
        }

        public void setSeriveName(String seriveName) {
            this.seriveName = seriveName;
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

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getActionTitle() {
            return actionTitle;
        }

        public void setActionTitle(String actionTitle) {
            this.actionTitle = actionTitle;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getNoOfVehicles() {
            return noOfVehicles;
        }

        public void setNoOfVehicles(String noOfVehicles) {
            this.noOfVehicles = noOfVehicles;
        }

        public String getAuctionType() {
            return auctionType;
        }

        public void setAuctionType(String auctionType) {
            this.auctionType = auctionType;
        }

        public String getGoingcount() {
            return goingcount;
        }

        public void setGoingcount(String goingcount) {
            this.goingcount = goingcount;
        }

        public String getIgnorecount() {
            return ignorecount;
        }

        public void setIgnorecount(String ignorecount) {
            this.ignorecount = ignorecount;
        }

    }


    public class MyAction {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("layout")
        @Expose
        private String layout;
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
        @SerializedName("sendername")
        @Expose
        private String sendername;
        @SerializedName("sender_pic")
        @Expose
        private String senderPic;
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
        @SerializedName("myfvrtstatus")
        @Expose
        private String myfvrtstatus;
        @SerializedName("receiverlikestatus")
        @Expose
        private String receiverlikestatus;
        @SerializedName("receiverfollowstatus")
        @Expose
        private String receiverfollowstatus;
        @SerializedName("status_id")
        @Expose
        private String statusId;
        @SerializedName("statuslikestatus")
        @Expose
        private String statuslikestatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("Upvehicle_id")
        @Expose
        private String upvehicleId;
        @SerializedName("Upvehiclelikestatus")
        @Expose
        private String upvehiclelikestatus;
        @SerializedName("Upvehiclefollowstatus")
        @Expose
        private String upvehiclefollowstatus;
        @SerializedName("Upvehiclelikecount")
        @Expose
        private String upvehiclelikecount;
        @SerializedName("Upvehiclefollowcount")
        @Expose
        private String upvehiclefollowcount;
        @SerializedName("UpVehicleContact")
        @Expose
        private String upVehicleContact;
        @SerializedName("Uptitle")
        @Expose
        private String uptitle;
        @SerializedName("Upimage")
        @Expose
        private String upimage;
        @SerializedName("Upprice")
        @Expose
        private String upprice;
        @SerializedName("Upmodel")
        @Expose
        private String upmodel;
        @SerializedName("Upbrand")
        @Expose
        private String upbrand;
        @SerializedName("Upyear")
        @Expose
        private String upyear;
        @SerializedName("Upregno")
        @Expose
        private String upregno;
        @SerializedName("Upkms")
        @Expose
        private String upkms;
        @SerializedName("Uphrs")
        @Expose
        private String uphrs;
        @SerializedName("Uprto")
        @Expose
        private String uprto;
        @SerializedName("Uplocation")
        @Expose
        private String uplocation;
        @SerializedName("search_id")
        @Expose
        private String searchId;
        @SerializedName("searchlikestatus")
        @Expose
        private String searchlikestatus;
        @SerializedName("scategory")
        @Expose
        private String scategory;
        @SerializedName("sBrand")
        @Expose
        private String sBrand;
        @SerializedName("smodel")
        @Expose
        private String smodel;
        @SerializedName("srto_city")
        @Expose
        private String srtoCity;
        @SerializedName("slocation_city")
        @Expose
        private String slocationCity;
        @SerializedName("scolor")
        @Expose
        private String scolor;
        @SerializedName("sprice")
        @Expose
        private String sprice;
        @SerializedName("simages")
        @Expose
        private String simages;
        @SerializedName("syear_of_manufactur")
        @Expose
        private String syearOfManufactur;
        @SerializedName("searchdate")
        @Expose
        private String searchdate;
        @SerializedName("searchleads")
        @Expose
        private String searchleads;
        @SerializedName("storelikestatus")
        @Expose
        private String storelikestatus;
        @SerializedName("storefollowstatus")
        @Expose
        private String storefollowstatus;
        @SerializedName("storerating")
        @Expose
        private String storerating;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("storelikecount")
        @Expose
        private String storelikecount;
        @SerializedName("storefollowcount")
        @Expose
        private String storefollowcount;
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
        @SerializedName("storetiming")
        @Expose
        private String storetiming;
        @SerializedName("group_vehicles")
        @Expose
        private String groupVehicles;
        @SerializedName("group_id")
        @Expose
        private String groupId;
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
        @SerializedName("product_id")
        @Expose
        private String productId;
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
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("servicelikecount")
        @Expose
        private String servicelikecount;
        @SerializedName("servicefollowcount")
        @Expose
        private String servicefollowcount;
        @SerializedName("serive_name")
        @Expose
        private String seriveName;
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
        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
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
        @SerializedName("regno")
        @Expose
        private String regno;
        @SerializedName("kms")
        @Expose
        private String kms;
        @SerializedName("hrs")
        @Expose
        private String hrs;
        @SerializedName("rto")
        @Expose
        private String rto;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("auction_id")
        @Expose
        private String auctionId;
        @SerializedName("action_title")
        @Expose
        private String actionTitle;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("no_of_vehicles")
        @Expose
        private String noOfVehicles;
        @SerializedName("auction_type")
        @Expose
        private String auctionType;
        @SerializedName("goingcount")
        @Expose
        private String goingcount;
        @SerializedName("ignorecount")
        @Expose
        private String ignorecount;

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

        public String getMyfvrtstatus() {
            return myfvrtstatus;
        }

        public void setMyfvrtstatus(String myfvrtstatus) {
            this.myfvrtstatus = myfvrtstatus;
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

        public String getStatusId() {
            return statusId;
        }

        public void setStatusId(String statusId) {
            this.statusId = statusId;
        }

        public String getStatuslikestatus() {
            return statuslikestatus;
        }

        public void setStatuslikestatus(String statuslikestatus) {
            this.statuslikestatus = statuslikestatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpvehicleId() {
            return upvehicleId;
        }

        public void setUpvehicleId(String upvehicleId) {
            this.upvehicleId = upvehicleId;
        }

        public String getUpvehiclelikestatus() {
            return upvehiclelikestatus;
        }

        public void setUpvehiclelikestatus(String upvehiclelikestatus) {
            this.upvehiclelikestatus = upvehiclelikestatus;
        }

        public String getUpvehiclefollowstatus() {
            return upvehiclefollowstatus;
        }

        public void setUpvehiclefollowstatus(String upvehiclefollowstatus) {
            this.upvehiclefollowstatus = upvehiclefollowstatus;
        }

        public String getUpvehiclelikecount() {
            return upvehiclelikecount;
        }

        public void setUpvehiclelikecount(String upvehiclelikecount) {
            this.upvehiclelikecount = upvehiclelikecount;
        }

        public String getUpvehiclefollowcount() {
            return upvehiclefollowcount;
        }

        public void setUpvehiclefollowcount(String upvehiclefollowcount) {
            this.upvehiclefollowcount = upvehiclefollowcount;
        }

        public String getUpVehicleContact() {
            return upVehicleContact;
        }

        public void setUpVehicleContact(String upVehicleContact) {
            this.upVehicleContact = upVehicleContact;
        }

        public String getUptitle() {
            return uptitle;
        }

        public void setUptitle(String uptitle) {
            this.uptitle = uptitle;
        }

        public String getUpimage() {
            return upimage;
        }

        public void setUpimage(String upimage) {
            this.upimage = upimage;
        }

        public String getUpprice() {
            return upprice;
        }

        public void setUpprice(String upprice) {
            this.upprice = upprice;
        }

        public String getUpmodel() {
            return upmodel;
        }

        public void setUpmodel(String upmodel) {
            this.upmodel = upmodel;
        }

        public String getUpbrand() {
            return upbrand;
        }

        public void setUpbrand(String upbrand) {
            this.upbrand = upbrand;
        }

        public String getUpyear() {
            return upyear;
        }

        public void setUpyear(String upyear) {
            this.upyear = upyear;
        }

        public String getUpregno() {
            return upregno;
        }

        public void setUpregno(String upregno) {
            this.upregno = upregno;
        }

        public String getUpkms() {
            return upkms;
        }

        public void setUpkms(String upkms) {
            this.upkms = upkms;
        }

        public String getUphrs() {
            return uphrs;
        }

        public void setUphrs(String uphrs) {
            this.uphrs = uphrs;
        }

        public String getUprto() {
            return uprto;
        }

        public void setUprto(String uprto) {
            this.uprto = uprto;
        }

        public String getUplocation() {
            return uplocation;
        }

        public void setUplocation(String uplocation) {
            this.uplocation = uplocation;
        }

        public String getSearchId() {
            return searchId;
        }

        public void setSearchId(String searchId) {
            this.searchId = searchId;
        }

        public String getSearchlikestatus() {
            return searchlikestatus;
        }

        public void setSearchlikestatus(String searchlikestatus) {
            this.searchlikestatus = searchlikestatus;
        }

        public String getScategory() {
            return scategory;
        }

        public void setScategory(String scategory) {
            this.scategory = scategory;
        }

        public String getSBrand() {
            return sBrand;
        }

        public void setSBrand(String sBrand) {
            this.sBrand = sBrand;
        }

        public String getSmodel() {
            return smodel;
        }

        public void setSmodel(String smodel) {
            this.smodel = smodel;
        }

        public String getSrtoCity() {
            return srtoCity;
        }

        public void setSrtoCity(String srtoCity) {
            this.srtoCity = srtoCity;
        }

        public String getSlocationCity() {
            return slocationCity;
        }

        public void setSlocationCity(String slocationCity) {
            this.slocationCity = slocationCity;
        }

        public String getScolor() {
            return scolor;
        }

        public void setScolor(String scolor) {
            this.scolor = scolor;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public String getSimages() {
            return simages;
        }

        public void setSimages(String simages) {
            this.simages = simages;
        }

        public String getSyearOfManufactur() {
            return syearOfManufactur;
        }

        public void setSyearOfManufactur(String syearOfManufactur) {
            this.syearOfManufactur = syearOfManufactur;
        }

        public String getSearchdate() {
            return searchdate;
        }

        public void setSearchdate(String searchdate) {
            this.searchdate = searchdate;
        }

        public String getSearchleads() {
            return searchleads;
        }

        public void setSearchleads(String searchleads) {
            this.searchleads = searchleads;
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

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
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

        public String getStoretiming() {
            return storetiming;
        }

        public void setStoretiming(String storetiming) {
            this.storetiming = storetiming;
        }

        public String getGroupVehicles() {
            return groupVehicles;
        }

        public void setGroupVehicles(String groupVehicles) {
            this.groupVehicles = groupVehicles;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
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

        public String getSeriveName() {
            return seriveName;
        }

        public void setSeriveName(String seriveName) {
            this.seriveName = seriveName;
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

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
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

        public String getRegno() {
            return regno;
        }

        public void setRegno(String regno) {
            this.regno = regno;
        }

        public String getKms() {
            return kms;
        }

        public void setKms(String kms) {
            this.kms = kms;
        }

        public String getHrs() {
            return hrs;
        }

        public void setHrs(String hrs) {
            this.hrs = hrs;
        }

        public String getRto() {
            return rto;
        }

        public void setRto(String rto) {
            this.rto = rto;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getActionTitle() {
            return actionTitle;
        }

        public void setActionTitle(String actionTitle) {
            this.actionTitle = actionTitle;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getNoOfVehicles() {
            return noOfVehicles;
        }

        public void setNoOfVehicles(String noOfVehicles) {
            this.noOfVehicles = noOfVehicles;
        }

        public String getAuctionType() {
            return auctionType;
        }

        public void setAuctionType(String auctionType) {
            this.auctionType = auctionType;
        }

        public String getGoingcount() {
            return goingcount;
        }

        public void setGoingcount(String goingcount) {
            this.goingcount = goingcount;
        }

        public String getIgnorecount() {
            return ignorecount;
        }

        public void setIgnorecount(String ignorecount) {
            this.ignorecount = ignorecount;
        }

    }


    public class MyNotification {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("layout")
        @Expose
        private String layout;
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
        @SerializedName("subLayout")
        @Expose
        private String subLayout;
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
        @SerializedName("myfvrtstatus")
        @Expose
        private String myfvrtstatus;
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
        @SerializedName("status_id")
        @Expose
        private String statusId;
        @SerializedName("statuslikestatus")
        @Expose
        private String statuslikestatus;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("storelikestatus")
        @Expose
        private String storelikestatus;
        @SerializedName("storefollowstatus")
        @Expose
        private String storefollowstatus;
        @SerializedName("storerating")
        @Expose
        private String storerating;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("storelikecount")
        @Expose
        private String storelikecount;
        @SerializedName("storefollowcount")
        @Expose
        private String storefollowcount;
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
        @SerializedName("storetiming")
        @Expose
        private String storetiming;
        @SerializedName("group_vehicles")
        @Expose
        private String groupVehicles;
        @SerializedName("group_id")
        @Expose
        private String groupId;
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
        @SerializedName("product_id")
        @Expose
        private String productId;
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
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("servicelikecount")
        @Expose
        private String servicelikecount;
        @SerializedName("servicefollowcount")
        @Expose
        private String servicefollowcount;
        @SerializedName("serive_name")
        @Expose
        private String seriveName;
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
        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
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
        @SerializedName("regno")
        @Expose
        private String regno;
        @SerializedName("kms")
        @Expose
        private String kms;
        @SerializedName("hrs")
        @Expose
        private String hrs;
        @SerializedName("rto")
        @Expose
        private String rto;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("Upvehicle_id")
        @Expose
        private String upvehicleId;
        @SerializedName("Upvehiclelikestatus")
        @Expose
        private String upvehiclelikestatus;
        @SerializedName("Upvehiclefollowstatus")
        @Expose
        private String upvehiclefollowstatus;
        @SerializedName("Upvehiclelikecount")
        @Expose
        private String upvehiclelikecount;
        @SerializedName("Upvehiclefollowcount")
        @Expose
        private String upvehiclefollowcount;
        @SerializedName("UpVehicleContact")
        @Expose
        private String upVehicleContact;
        @SerializedName("Uptitle")
        @Expose
        private String uptitle;
        @SerializedName("Upimage")
        @Expose
        private String upimage;
        @SerializedName("Upprice")
        @Expose
        private String upprice;
        @SerializedName("Upmodel")
        @Expose
        private String upmodel;
        @SerializedName("Upbrand")
        @Expose
        private String upbrand;
        @SerializedName("Upyear")
        @Expose
        private String upyear;
        @SerializedName("Upregno")
        @Expose
        private String upregno;
        @SerializedName("Upkms")
        @Expose
        private String upkms;
        @SerializedName("Uphrs")
        @Expose
        private String uphrs;
        @SerializedName("Uprto")
        @Expose
        private String uprto;
        @SerializedName("Uplocation")
        @Expose
        private String uplocation;
        @SerializedName("search_id")
        @Expose
        private String searchId;
        @SerializedName("searchlikestatus")
        @Expose
        private String searchlikestatus;
        @SerializedName("scategory")
        @Expose
        private String scategory;
        @SerializedName("sBrand")
        @Expose
        private String sBrand;
        @SerializedName("smodel")
        @Expose
        private String smodel;
        @SerializedName("srto_city")
        @Expose
        private String srtoCity;
        @SerializedName("slocation_city")
        @Expose
        private String slocationCity;
        @SerializedName("scolor")
        @Expose
        private String scolor;
        @SerializedName("sprice")
        @Expose
        private String sprice;
        @SerializedName("simages")
        @Expose
        private String simages;
        @SerializedName("syear_of_manufactur")
        @Expose
        private String syearOfManufactur;
        @SerializedName("searchdate")
        @Expose
        private String searchdate;
        @SerializedName("searchleads")
        @Expose
        private String searchleads;
        @SerializedName("auction_id")
        @Expose
        private String auctionId;
        @SerializedName("action_title")
        @Expose
        private String actionTitle;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("no_of_vehicles")
        @Expose
        private String noOfVehicles;
        @SerializedName("auction_type")
        @Expose
        private String auctionType;
        @SerializedName("goingcount")
        @Expose
        private String goingcount;
        @SerializedName("ignorecount")
        @Expose
        private String ignorecount;

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

        public String getSubLayout() {
            return subLayout;
        }

        public void setSubLayout(String subLayout) {
            this.subLayout = subLayout;
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

        public String getMyfvrtstatus() {
            return myfvrtstatus;
        }

        public void setMyfvrtstatus(String myfvrtstatus) {
            this.myfvrtstatus = myfvrtstatus;
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

        public String getStatusId() {
            return statusId;
        }

        public void setStatusId(String statusId) {
            this.statusId = statusId;
        }

        public String getStatuslikestatus() {
            return statuslikestatus;
        }

        public void setStatuslikestatus(String statuslikestatus) {
            this.statuslikestatus = statuslikestatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
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

        public String getStoretiming() {
            return storetiming;
        }

        public void setStoretiming(String storetiming) {
            this.storetiming = storetiming;
        }

        public String getGroupVehicles() {
            return groupVehicles;
        }

        public void setGroupVehicles(String groupVehicles) {
            this.groupVehicles = groupVehicles;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
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

        public String getSeriveName() {
            return seriveName;
        }

        public void setSeriveName(String seriveName) {
            this.seriveName = seriveName;
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

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
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

        public String getRegno() {
            return regno;
        }

        public void setRegno(String regno) {
            this.regno = regno;
        }

        public String getKms() {
            return kms;
        }

        public void setKms(String kms) {
            this.kms = kms;
        }

        public String getHrs() {
            return hrs;
        }

        public void setHrs(String hrs) {
            this.hrs = hrs;
        }

        public String getRto() {
            return rto;
        }

        public void setRto(String rto) {
            this.rto = rto;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUpvehicleId() {
            return upvehicleId;
        }

        public void setUpvehicleId(String upvehicleId) {
            this.upvehicleId = upvehicleId;
        }

        public String getUpvehiclelikestatus() {
            return upvehiclelikestatus;
        }

        public void setUpvehiclelikestatus(String upvehiclelikestatus) {
            this.upvehiclelikestatus = upvehiclelikestatus;
        }

        public String getUpvehiclefollowstatus() {
            return upvehiclefollowstatus;
        }

        public void setUpvehiclefollowstatus(String upvehiclefollowstatus) {
            this.upvehiclefollowstatus = upvehiclefollowstatus;
        }

        public String getUpvehiclelikecount() {
            return upvehiclelikecount;
        }

        public void setUpvehiclelikecount(String upvehiclelikecount) {
            this.upvehiclelikecount = upvehiclelikecount;
        }

        public String getUpvehiclefollowcount() {
            return upvehiclefollowcount;
        }

        public void setUpvehiclefollowcount(String upvehiclefollowcount) {
            this.upvehiclefollowcount = upvehiclefollowcount;
        }

        public String getUpVehicleContact() {
            return upVehicleContact;
        }

        public void setUpVehicleContact(String upVehicleContact) {
            this.upVehicleContact = upVehicleContact;
        }

        public String getUptitle() {
            return uptitle;
        }

        public void setUptitle(String uptitle) {
            this.uptitle = uptitle;
        }

        public String getUpimage() {
            return upimage;
        }

        public void setUpimage(String upimage) {
            this.upimage = upimage;
        }

        public String getUpprice() {
            return upprice;
        }

        public void setUpprice(String upprice) {
            this.upprice = upprice;
        }

        public String getUpmodel() {
            return upmodel;
        }

        public void setUpmodel(String upmodel) {
            this.upmodel = upmodel;
        }

        public String getUpbrand() {
            return upbrand;
        }

        public void setUpbrand(String upbrand) {
            this.upbrand = upbrand;
        }

        public String getUpyear() {
            return upyear;
        }

        public void setUpyear(String upyear) {
            this.upyear = upyear;
        }

        public String getUpregno() {
            return upregno;
        }

        public void setUpregno(String upregno) {
            this.upregno = upregno;
        }

        public String getUpkms() {
            return upkms;
        }

        public void setUpkms(String upkms) {
            this.upkms = upkms;
        }

        public String getUphrs() {
            return uphrs;
        }

        public void setUphrs(String uphrs) {
            this.uphrs = uphrs;
        }

        public String getUprto() {
            return uprto;
        }

        public void setUprto(String uprto) {
            this.uprto = uprto;
        }

        public String getUplocation() {
            return uplocation;
        }

        public void setUplocation(String uplocation) {
            this.uplocation = uplocation;
        }

        public String getSearchId() {
            return searchId;
        }

        public void setSearchId(String searchId) {
            this.searchId = searchId;
        }

        public String getSearchlikestatus() {
            return searchlikestatus;
        }

        public void setSearchlikestatus(String searchlikestatus) {
            this.searchlikestatus = searchlikestatus;
        }

        public String getScategory() {
            return scategory;
        }

        public void setScategory(String scategory) {
            this.scategory = scategory;
        }

        public String getSBrand() {
            return sBrand;
        }

        public void setSBrand(String sBrand) {
            this.sBrand = sBrand;
        }

        public String getSmodel() {
            return smodel;
        }

        public void setSmodel(String smodel) {
            this.smodel = smodel;
        }

        public String getSrtoCity() {
            return srtoCity;
        }

        public void setSrtoCity(String srtoCity) {
            this.srtoCity = srtoCity;
        }

        public String getSlocationCity() {
            return slocationCity;
        }

        public void setSlocationCity(String slocationCity) {
            this.slocationCity = slocationCity;
        }

        public String getScolor() {
            return scolor;
        }

        public void setScolor(String scolor) {
            this.scolor = scolor;
        }

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public String getSimages() {
            return simages;
        }

        public void setSimages(String simages) {
            this.simages = simages;
        }

        public String getSyearOfManufactur() {
            return syearOfManufactur;
        }

        public void setSyearOfManufactur(String syearOfManufactur) {
            this.syearOfManufactur = syearOfManufactur;
        }

        public String getSearchdate() {
            return searchdate;
        }

        public void setSearchdate(String searchdate) {
            this.searchdate = searchdate;
        }

        public String getSearchleads() {
            return searchleads;
        }

        public void setSearchleads(String searchleads) {
            this.searchleads = searchleads;
        }

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getActionTitle() {
            return actionTitle;
        }

        public void setActionTitle(String actionTitle) {
            this.actionTitle = actionTitle;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getNoOfVehicles() {
            return noOfVehicles;
        }

        public void setNoOfVehicles(String noOfVehicles) {
            this.noOfVehicles = noOfVehicles;
        }

        public String getAuctionType() {
            return auctionType;
        }

        public void setAuctionType(String auctionType) {
            this.auctionType = auctionType;
        }

        public String getGoingcount() {
            return goingcount;
        }

        public void setGoingcount(String goingcount) {
            this.goingcount = goingcount;
        }

        public String getIgnorecount() {
            return ignorecount;
        }

        public void setIgnorecount(String ignorecount) {
            this.ignorecount = ignorecount;
        }

    }


}
