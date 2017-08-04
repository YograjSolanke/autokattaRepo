package autokatta.com.response;

/**
 * Created by ak-003 on 20/4/17.
 */

public class FavouriteAllResponse {

    /*
        Notification Array variables
     */
    private int id;
    private String layout;

    public String getLayoutNo() {
        return layoutNo;
    }

    public void setLayoutNo(String layoutNo) {
        this.layoutNo = layoutNo;
    }

    private String layoutNo;
    private String sender;
    private String action;
    private String receiver;
    private int vehicleId;
    private int productId;
    private int serviceId;
    private int storeId;
    private int groupId;
    private String senderprofession;
    private String senderwebsite;
    private String sendercity;
    private int senderlikecount;
    private int senderfollowcount;
    private String senderlikestatus;
    private String senderfollowstatus;
    private String receivername;
    private String receiverPic;
    private String receiverprofession;
    private String receiverwebsite;
    private String receivercity;
    private int receiverlikecount;
    private int receiverfollowcount;
    private String receiverlikestatus;
    private String receiverfollowstatus;
    private String storelikestatus;
    private String storefollowstatus;
    private int storerating;
    private int storelikecount;
    private int storefollowcount;
    private String storetiming;
    private String storeContact;
    private String storeName;
    private String storeImage;
    private String storeType;
    private String storeWebsite;
    private String workingDays;
    private String storeLocation;
    private int groupVehicles;
    private String groupName;
    private String groupImage;
    private int groupMembers;
    private String productlikestatus;
    private String productfollowstatus;
    private int productlikecount;
    private int productfollowcount;
    private String productName;
    private String productType;
    private String productimages;
    private String servicelikestatus;
    private String servicefollowstatus;
    private int servicelikecount;
    private int servicefollowcount;
    private String serviceName;
    private String serviceType;
    private String serviceimages;
    /*private String vehiclelikestatus;
    private String vehiclefollowstatus;
    private int vehiclelikecount;
    private int vehiclefollowcount;
    private String vehicleContact;
    private String title;
    private String image;
    private String year;*/

    private int ServiceRating;

    private int ProductRating;

    private String StoreCategory;

    private int StoreShareCount;

    private int GroupProductCount;

    private int GroupServiceCount;

    private int ProductShareCount;

    private int ServiceShareCount;

    private int VehicleShareCount;


    private String upVehicleLikeStatus;

    private String upVehicleFollowStatus;
    /*@SerializedName("UploadVehicleID")
    @Expose
    private int uploadVehicleID;*/
    private int upVehicleLikeCount;
    private int upVehicleFollowCount;
    private String upVehicleContact;
    private String upVehicleTitle;
    private String upVehicleImage;
    private String upVehiclePrice;
    private String upVehicleModel;
    private String upVehicleBrand;
    private String upVehicleManfYear;
    private String upVehicleRegNo;
    private String upVehicleKmsRun;
    private String upVehicleHrsRun;
    private String upVehicleRtoCity;
    private String upVehicleLocationCity;
    private int UpVehicleShareCount;

    public int getFavid() {
        return favid;
    }

    public void setFavid(int favid) {
        this.favid = favid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
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

    public int getSenderlikecount() {
        return senderlikecount;
    }

    public void setSenderlikecount(int senderlikecount) {
        this.senderlikecount = senderlikecount;
    }

    public int getSenderfollowcount() {
        return senderfollowcount;
    }

    public void setSenderfollowcount(int senderfollowcount) {
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

    public int getReceiverlikecount() {
        return receiverlikecount;
    }

    public void setReceiverlikecount(int receiverlikecount) {
        this.receiverlikecount = receiverlikecount;
    }

    public int getReceiverfollowcount() {
        return receiverfollowcount;
    }

    public void setReceiverfollowcount(int receiverfollowcount) {
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

    public int getStorerating() {
        return storerating;
    }

    public void setStorerating(int storerating) {
        this.storerating = storerating;
    }

    public int getStorelikecount() {
        return storelikecount;
    }

    public void setStorelikecount(int storelikecount) {
        this.storelikecount = storelikecount;
    }

    public int getStorefollowcount() {
        return storefollowcount;
    }

    public void setStorefollowcount(int storefollowcount) {
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

    public int getGroupVehicles() {
        return groupVehicles;
    }

    public void setGroupVehicles(int groupVehicles) {
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

    public int getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(int groupMembers) {
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

    public int getProductlikecount() {
        return productlikecount;
    }

    public void setProductlikecount(int productlikecount) {
        this.productlikecount = productlikecount;
    }

    public int getProductfollowcount() {
        return productfollowcount;
    }

    public void setProductfollowcount(int productfollowcount) {
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

    public int getServicelikecount() {
        return servicelikecount;
    }

    public void setServicelikecount(int servicelikecount) {
        this.servicelikecount = servicelikecount;
    }

    public int getServicefollowcount() {
        return servicefollowcount;
    }

    public void setServicefollowcount(int servicefollowcount) {
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

    public int getServiceRating() {
        return ServiceRating;
    }

    public void setServiceRating(int serviceRating) {
        ServiceRating = serviceRating;
    }

    public int getProductRating() {
        return ProductRating;
    }

    public void setProductRating(int productRating) {
        ProductRating = productRating;
    }

    public String getStoreCategory() {
        return StoreCategory;
    }

    public void setStoreCategory(String storeCategory) {
        StoreCategory = storeCategory;
    }

    public int getStoreShareCount() {
        return StoreShareCount;
    }

    public void setStoreShareCount(int storeShareCount) {
        StoreShareCount = storeShareCount;
    }

    public int getGroupProductCount() {
        return GroupProductCount;
    }

    public void setGroupProductCount(int groupProductCount) {
        GroupProductCount = groupProductCount;
    }

    public int getGroupServiceCount() {
        return GroupServiceCount;
    }

    public void setGroupServiceCount(int groupServiceCount) {
        GroupServiceCount = groupServiceCount;
    }

    public int getProductShareCount() {
        return ProductShareCount;
    }

    public void setProductShareCount(int productShareCount) {
        ProductShareCount = productShareCount;
    }

    public int getServiceShareCount() {
        return ServiceShareCount;
    }

    public void setServiceShareCount(int serviceShareCount) {
        ServiceShareCount = serviceShareCount;
    }

    public int getVehicleShareCount() {
        return VehicleShareCount;
    }

    public void setVehicleShareCount(int vehicleShareCount) {
        VehicleShareCount = vehicleShareCount;
    }

    public String getUpVehicleLikeStatus() {
        return upVehicleLikeStatus;
    }

    public void setUpVehicleLikeStatus(String upVehicleLikeStatus) {
        this.upVehicleLikeStatus = upVehicleLikeStatus;
    }

    public String getUpVehicleFollowStatus() {
        return upVehicleFollowStatus;
    }

    public void setUpVehicleFollowStatus(String upVehicleFollowStatus) {
        this.upVehicleFollowStatus = upVehicleFollowStatus;
    }

    public int getUpVehicleLikeCount() {
        return upVehicleLikeCount;
    }

    public void setUpVehicleLikeCount(int upVehicleLikeCount) {
        this.upVehicleLikeCount = upVehicleLikeCount;
    }

    public int getUpVehicleFollowCount() {
        return upVehicleFollowCount;
    }

    public void setUpVehicleFollowCount(int upVehicleFollowCount) {
        this.upVehicleFollowCount = upVehicleFollowCount;
    }

    public String getUpVehicleContact() {
        return upVehicleContact;
    }

    public void setUpVehicleContact(String upVehicleContact) {
        this.upVehicleContact = upVehicleContact;
    }

    public String getUpVehicleTitle() {
        return upVehicleTitle;
    }

    public void setUpVehicleTitle(String upVehicleTitle) {
        this.upVehicleTitle = upVehicleTitle;
    }

    public String getUpVehicleImage() {
        return upVehicleImage;
    }

    public void setUpVehicleImage(String upVehicleImage) {
        this.upVehicleImage = upVehicleImage;
    }

    public String getUpVehiclePrice() {
        return upVehiclePrice;
    }

    public void setUpVehiclePrice(String upVehiclePrice) {
        this.upVehiclePrice = upVehiclePrice;
    }

    public String getUpVehicleModel() {
        return upVehicleModel;
    }

    public void setUpVehicleModel(String upVehicleModel) {
        this.upVehicleModel = upVehicleModel;
    }

    public String getUpVehicleBrand() {
        return upVehicleBrand;
    }

    public void setUpVehicleBrand(String upVehicleBrand) {
        this.upVehicleBrand = upVehicleBrand;
    }

    public String getUpVehicleManfYear() {
        return upVehicleManfYear;
    }

    public void setUpVehicleManfYear(String upVehicleManfYear) {
        this.upVehicleManfYear = upVehicleManfYear;
    }

    public String getUpVehicleRegNo() {
        return upVehicleRegNo;
    }

    public void setUpVehicleRegNo(String upVehicleRegNo) {
        this.upVehicleRegNo = upVehicleRegNo;
    }

    public String getUpVehicleKmsRun() {
        return upVehicleKmsRun;
    }

    public void setUpVehicleKmsRun(String upVehicleKmsRun) {
        this.upVehicleKmsRun = upVehicleKmsRun;
    }

    public String getUpVehicleHrsRun() {
        return upVehicleHrsRun;
    }

    public void setUpVehicleHrsRun(String upVehicleHrsRun) {
        this.upVehicleHrsRun = upVehicleHrsRun;
    }

    public String getUpVehicleRtoCity() {
        return upVehicleRtoCity;
    }

    public void setUpVehicleRtoCity(String upVehicleRtoCity) {
        this.upVehicleRtoCity = upVehicleRtoCity;
    }

    public String getUpVehicleLocationCity() {
        return upVehicleLocationCity;
    }

    public void setUpVehicleLocationCity(String upVehicleLocationCity) {
        this.upVehicleLocationCity = upVehicleLocationCity;
    }

    public int getUpVehicleShareCount() {
        return UpVehicleShareCount;
    }

    public void setUpVehicleShareCount(int upVehicleShareCount) {
        UpVehicleShareCount = upVehicleShareCount;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private int SearchID;

    private String SearchLikeStatus;

    private String SearchCategory;

    private String SearchBrand;

    private String SearchModel;

    private String SearchRtoCity;

    private String SearchLocationCity;

    private String SearchColor;

    private String SearchPrice;

    private String SearchManfYear;

    private String SearchDate;

    private int SearchLeads;


    public int getSearchID() {
        return SearchID;
    }

    public void setSearchID(int searchID) {
        SearchID = searchID;
    }

    public String getSearchLikeStatus() {
        return SearchLikeStatus;
    }

    public void setSearchLikeStatus(String searchLikeStatus) {
        SearchLikeStatus = searchLikeStatus;
    }

    public String getSearchCategory() {
        return SearchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        SearchCategory = searchCategory;
    }

    public String getSearchBrand() {
        return SearchBrand;
    }

    public void setSearchBrand(String searchBrand) {
        SearchBrand = searchBrand;
    }

    public String getSearchModel() {
        return SearchModel;
    }

    public void setSearchModel(String searchModel) {
        SearchModel = searchModel;
    }

    public String getSearchRtoCity() {
        return SearchRtoCity;
    }

    public void setSearchRtoCity(String searchRtoCity) {
        SearchRtoCity = searchRtoCity;
    }

    public String getSearchLocationCity() {
        return SearchLocationCity;
    }

    public void setSearchLocationCity(String searchLocationCity) {
        SearchLocationCity = searchLocationCity;
    }

    public String getSearchColor() {
        return SearchColor;
    }

    public void setSearchColor(String searchColor) {
        SearchColor = searchColor;
    }

    public String getSearchPrice() {
        return SearchPrice;
    }

    public void setSearchPrice(String searchPrice) {
        SearchPrice = searchPrice;
    }

    public String getSearchManfYear() {
        return SearchManfYear;
    }

    public void setSearchManfYear(String searchManfYear) {
        SearchManfYear = searchManfYear;
    }

    public String getSearchDate() {
        return SearchDate;
    }

    public void setSearchDate(String searchDate) {
        SearchDate = searchDate;
    }

    public int getSearchLeads() {
        return SearchLeads;
    }

    public void setSearchLeads(int searchLeads) {
        SearchLeads = searchLeads;
    }



    /*
    Buyer Search
     */

    public int getVvehicleId() {
        return vvehicleId;
    }

    public void setVvehicleId(int vvehicleId) {
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

    public String getvVersion() {
        return vVersion;
    }

    public void setvVersion(String vVersion) {
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

    public String getvHrsRunning() {
        return vHrsRunning;
    }

    public void setvHrsRunning(String vHrsRunning) {
        this.vHrsRunning = vHrsRunning;
    }

    public int getVnoOfOwners() {
        return vnoOfOwners;
    }

    public void setVnoOfOwners(int vnoOfOwners) {
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

    /*
    Seller
     */

    private int vvehicleId;
    private String vtitle;
    private String vcontactNo;
    private String vcategory;
    private String vmodel;
    private String vmanufacturer;
    private String vVersion;
    private String vrtoCity;
    private String vlocationCity;
    private String vlocationState;
    private String vlocationCountry;
    private String vmonthOfRegistration;
    private String vyearOfRegistration;
    private String vmonthOfManufacture;
    private String vyearOfManufacture;
    private String vcolor;
    private String vregistrationNumber;
    private String vrcAvailable;
    private String vinsuranceValid;
    private String vinsuranceIdv;
    private String vtaxValidity;
    private String vtaxPaidUpto;
    private String vfitnessValidity;
    private String vpermitValidity;
    private String vpermitYesno;
    private String vfitnessYesno;
    private String vfualType;
    private String vseatingCapacity;
    private String vpermit;
    private String vkmsRunning;
    private String vHrsRunning;
    private int vnoOfOwners;
    private String vhypothication;
    private String vengineNo;
    private String vchassisNo;
    private String vprice;
    private String vimage;
    private String vdrive;
    private String vtransmission;
    private String vbodyType;
    private String vboatType;
    private String vrvType;
    private String vapplication;
    private String vtyreCondition;
    private String vbusType;
    private String vairCondition;
    private String vinvoice;
    private String vimplements;
    private String vfinanceReq;
    private String vprivacy;
    private String vviewcount;
    private String vcallcount;
    private String vdate;
    private String vstartPrice;
    private String vreservePrice;
    private String vstatus;
    private String vhpcapacity;


    private String sendername;
    private String senderPic;
    private int ssearchId;
    private String scontactNo;
    private String sstatus;
    private String scategory;
    private String smanufacturer;
    private String smodel;
    private String sversion;
    private String srtoCity;
    private String srtoCity2;
    private String srtoCity3;
    private String srtoCity4;
    private String srtoCity5;
    private String slocationCity;
    private String slocationCity2;
    private String slocationCity3;
    private String slocationCity4;
    private String slocationCity5;
    private String slocationState;
    private String syearOfRegistration;
    private String scolor;
    private String srcAvailable;
    private String syearOfManufacture;
    private String sinsuranceValid;
    private String staxValidity;
    private String sfitnessValidity;
    private String spermitValidity;
    private String sfualType;
    private String sseatingCapacity;
    private String spermit;
    private String skmsRunning;
    private int snoOfOwners;
    private String shypothication;
    private String sprice;
    private String sdrive;
    private String stransmission;
    private String sbodyType;
    private String sboatType;
    private String srvType;
    private String sapplication;
    private String sdate;
    private String scalldate;
    private String sstopdate;
    private String styreCondition;
    private String simplements;
    private String sbusType;
    private String sairCondition;
    private String sfinanceReq;
    private String sinvoice;
    private String shpcapacity;

    public String getVhpcapacity() {
        return vhpcapacity;
    }

    public void setVhpcapacity(String vhpcapacity) {
        this.vhpcapacity = vhpcapacity;
    }

    public String getShpcapacity() {
        return shpcapacity;
    }

    public void setShpcapacity(String shpcapacity) {
        this.shpcapacity = shpcapacity;
    }

    public String getVVersion() {
        return vVersion;
    }

    public void setVVersion(String vVersion) {
        this.vVersion = vVersion;
    }


    public int getSsearchId() {
        return ssearchId;
    }

    public void setSsearchId(int ssearchId) {
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

    public int getSnoOfOwners() {
        return snoOfOwners;
    }

    public void setSnoOfOwners(int snoOfOwners) {
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

/*
Search
 */


    private int searchId;
    private String date;
    private String datetime;
    private String category;
    private String brand;
    private String model;
    private String rtoCity;
    private String locationCity;
    private String color;
    private String price;
    private String yearOfManufactur;
    private int favid;
    private String images;

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

    public String getYearOfManufactur() {
        return yearOfManufactur;
    }

    public void setYearOfManufactur(String yearOfManufactur) {
        this.yearOfManufactur = yearOfManufactur;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


}
