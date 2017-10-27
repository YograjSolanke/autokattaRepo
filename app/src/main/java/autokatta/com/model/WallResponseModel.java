package autokatta.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ak-001 on 27/10/17.
 */

public class WallResponseModel {
    @SerializedName("Notif_Title")
    @Expose
    private String layoutType;

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    @SerializedName("ActionID")
    @Expose
    private int actionID;
    @SerializedName("Layout")
    @Expose
    private String layout;
    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("Sender")
    @Expose
    private String sender;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("Receiver")
    @Expose
    private String receiver;
    @SerializedName("SubLayout")
    @Expose
    private String subLayout;
    @SerializedName("SenderName")
    @Expose
    private String senderName;
    @SerializedName("SenderPicture")
    @Expose
    private String senderPicture;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("ReceiverPicture")
    @Expose
    private String receiverPicture;
    @SerializedName("ReceiverProfession")
    @Expose
    private String receiverProfession;
    @SerializedName("ReceiverWebsite")
    @Expose
    private String receiverWebsite;
    @SerializedName("ReceiverCity")
    @Expose
    private String receiverCity;
    @SerializedName("ReceiverLikeCount")
    @Expose
    private int receiverLikeCount;
    @SerializedName("ReceiverFollowCount")
    @Expose
    private int receiverFollowCount;
    @SerializedName("ReceiverLikeStatus")
    @Expose
    private String receiverLikeStatus;
    @SerializedName("ReceiverFollowStatus")
    @Expose
    private String receiverFollowStatus;
    @SerializedName("StatusID")
    @Expose
    private int statusID;
    @SerializedName("StatusLikeStatus")
    @Expose
    private String statusLikeStatus;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("MyFavStatus")
    @Expose
    private String myFavStatus;
    @SerializedName("UpVehicleLikeStatus")
    @Expose
    private String upVehicleLikeStatus;
    @SerializedName("UpVehicleFollowStatus")
    @Expose
    private String upVehicleFollowStatus;
    @SerializedName("UploadVehicleID")
    @Expose
    private int uploadVehicleID;
    @SerializedName("UpVehicleLikeCount")
    @Expose
    private int upVehicleLikeCount;
    @SerializedName("UpVehicleFollowCount")
    @Expose
    private int upVehicleFollowCount;
    @SerializedName("UpVehicleContact")
    @Expose
    private String upVehicleContact;
    @SerializedName("UpVehicleTitle")
    @Expose
    private String upVehicleTitle;
    @SerializedName("UpVehicleImage")
    @Expose
    private String upVehicleImage;
    @SerializedName("UpVehiclePrice")
    @Expose
    private String upVehiclePrice;
    @SerializedName("UpVehicleModel")
    @Expose
    private String upVehicleModel;
    @SerializedName("UpVehicleBrand")
    @Expose
    private String upVehicleBrand;
    @SerializedName("UpVehicleManfYear")
    @Expose
    private String upVehicleManfYear;
    @SerializedName("UpVehicleRegNo")
    @Expose
    private String upVehicleRegNo;
    @SerializedName("UpVehicleKmsRun")
    @Expose
    private String upVehicleKmsRun;
    @SerializedName("UpVehicleHrsRun")
    @Expose
    private String upVehicleHrsRun;
    @SerializedName("UpVehicleRtoCity")
    @Expose
    private String upVehicleRtoCity;
    @SerializedName("UpVehicleLocationCity")
    @Expose
    private String upVehicleLocationCity;

    @SerializedName("UpVehicleShareCount")
    @Expose
    private int UpVehicleShareCount;

    @SerializedName("SearchLikeStatus")
    @Expose
    private String searchLikeStatus;
    @SerializedName("SearchCategory")
    @Expose
    private String searchCategory;
    @SerializedName("SearchBrand")
    @Expose
    private String searchBrand;
    @SerializedName("SearchModel")
    @Expose
    private String searchModel;
    @SerializedName("SearchRtoCity")
    @Expose
    private String searchRtoCity;
    @SerializedName("SearchLocationCity")
    @Expose
    private String searchLocationCity;
    @SerializedName("SearchColor")
    @Expose
    private String searchColor;
    @SerializedName("SearchPrice")
    @Expose
    private String searchPrice;
    @SerializedName("SearchManfYear")
    @Expose
    private String searchManfYear;
    @SerializedName("SearchDate")
    @Expose
    private String searchDate;
    @SerializedName("SearchLeads")
    @Expose
    private int searchLeads;
    @SerializedName("SearchID")
    @Expose
    private int searchId;
    @SerializedName("SenderProfession")
    @Expose
    private String senderProfession;
    @SerializedName("SenderWebsite")
    @Expose
    private String senderWebsite;
    @SerializedName("SenderCity")
    @Expose
    private String senderCity;
    @SerializedName("SenderLikeCount")
    @Expose
    private int senderLikeCount;
    @SerializedName("SenderFollowCount")
    @Expose
    private int senderFollowCount;
    @SerializedName("SenderLikeStatus")
    @Expose
    private String senderLikeStatus;
    @SerializedName("SenderFollowStatus")
    @Expose
    private String senderFollowStatus;
    @SerializedName("StoreLikeStatus")
    @Expose
    private String storeLikeStatus;
    @SerializedName("StoreFollowStatus")
    @Expose
    private String storeFollowStatus;
    @SerializedName("StoreRating")
    @Expose
    private int storeRating;
    @SerializedName("StoreID")
    @Expose
    private int storeID;
    @SerializedName("StoreLikeCount")
    @Expose
    private int storeLikeCount;
    @SerializedName("StoreFollowCount")
    @Expose
    private int storeFollowCount;
    @SerializedName("StoreContact")
    @Expose
    private String storeContact;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("StoreImage")
    @Expose
    private String storeImage;
    @SerializedName("StoreType")
    @Expose
    private String storeType;
    @SerializedName("StoreWebsite")
    @Expose
    private String storeWebsite;
    @SerializedName("WorkingDays")
    @Expose
    private String workingDays;
    @SerializedName("StoreLocation")
    @Expose
    private String storeLocation;
    @SerializedName("StoreTiming")
    @Expose
    private String storeTiming;

    @SerializedName("StoreCategory")
    @Expose
    private String StoreCategory;
    @SerializedName("StoreShareCount")
    @Expose
    private int StoreShareCount;

    @SerializedName("GroupVehicles")
    @Expose
    private int groupVehicles;
    @SerializedName("GroupID")
    @Expose
    private int groupID;
    @SerializedName("GroupName")
    @Expose
    private String groupName;
    @SerializedName("GroupImage")
    @Expose
    private String groupImage;
    @SerializedName("GroupMembers")
    @Expose
    private int groupMembers;
    @SerializedName("GroupProductCount")
    @Expose
    private int groupProductCount;
    @SerializedName("GroupServiceCount")
    @Expose
    private int groupServiceCount;
    @SerializedName("ProductLikeStatus")
    @Expose
    private String productLikeStatus;
    @SerializedName("ProductFollowStatus")
    @Expose
    private String productFollowStatus;
    @SerializedName("ProductRating")
    @Expose
    private int productRating;
    @SerializedName("ProductID")
    @Expose
    private int productID;
    @SerializedName("ProductLikeCount")
    @Expose
    private int productLikeCount;
    @SerializedName("ProductFollowCount")
    @Expose
    private int productFollowCount;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductType")
    @Expose
    private String productType;
    @SerializedName("ProductImage")
    @Expose
    private String productImage;

    @SerializedName("ProductShareCount")
    @Expose
    private int ProductShareCount;

    @SerializedName("ServiceLikeStatus")
    @Expose
    private String serviceLikeStatus;
    @SerializedName("ServiceFollowStatus")
    @Expose
    private String serviceFollowStatus;
    @SerializedName("ServiceRating")
    @Expose
    private int serviceRating;
    @SerializedName("ServiceID")
    @Expose
    private int serviceID;
    @SerializedName("ServiceLikeCount")
    @Expose
    private int serviceLikeCount;
    @SerializedName("ServiceFollowCount")
    @Expose
    private int serviceFollowCount;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("ServiceImage")
    @Expose
    private String serviceImage;

    @SerializedName("ServiceShareCount")
    @Expose
    private int ServiceShareCount;

    @SerializedName("AuctionID")
    @Expose
    private int auctionID;
    @SerializedName("ActionTitle")
    @Expose
    private String actionTitle;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("NoOfVehicles")
    @Expose
    private String noOfVehicles;
    @SerializedName("AuctionType")
    @Expose
    private String auctionType;
    @SerializedName("GoingCount")
    @Expose
    private int goingCount;
    @SerializedName("IgnoreCount")
    @Expose
    private int ignoreCount;

    private String shareSubData;

    public String getShareSubData() {
        return shareSubData;
    }

    public void setShareSubData(String shareSubData) {
        this.shareSubData = shareSubData;
    }

    public int getGroupProductCount() {
        return groupProductCount;
    }

    public void setGroupProductCount(int groupProductCount) {
        this.groupProductCount = groupProductCount;
    }

    public int getGroupServiceCount() {
        return groupServiceCount;
    }

    public void setGroupServiceCount(int groupServiceCount) {
        this.groupServiceCount = groupServiceCount;
    }

    public int getProductRating() {
        return productRating;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }

    public int getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(int serviceRating) {
        this.serviceRating = serviceRating;
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        this.senderPicture = senderPicture;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPicture() {
        return receiverPicture;
    }

    public void setReceiverPicture(String receiverPicture) {
        this.receiverPicture = receiverPicture;
    }

    public String getReceiverProfession() {
        return receiverProfession;
    }

    public void setReceiverProfession(String receiverProfession) {
        this.receiverProfession = receiverProfession;
    }

    public String getReceiverWebsite() {
        return receiverWebsite;
    }

    public void setReceiverWebsite(String receiverWebsite) {
        this.receiverWebsite = receiverWebsite;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public int getReceiverLikeCount() {
        return receiverLikeCount;
    }

    public void setReceiverLikeCount(int receiverLikeCount) {
        this.receiverLikeCount = receiverLikeCount;
    }

    public int getReceiverFollowCount() {
        return receiverFollowCount;
    }

    public void setReceiverFollowCount(int receiverFollowCount) {
        this.receiverFollowCount = receiverFollowCount;
    }

    public String getReceiverLikeStatus() {
        return receiverLikeStatus;
    }

    public void setReceiverLikeStatus(String receiverLikeStatus) {
        this.receiverLikeStatus = receiverLikeStatus;
    }

    public String getReceiverFollowStatus() {
        return receiverFollowStatus;
    }

    public void setReceiverFollowStatus(String receiverFollowStatus) {
        this.receiverFollowStatus = receiverFollowStatus;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatusLikeStatus() {
        return statusLikeStatus;
    }

    public void setStatusLikeStatus(String statusLikeStatus) {
        this.statusLikeStatus = statusLikeStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMyFavStatus() {
        return myFavStatus;
    }

    public void setMyFavStatus(String myFavStatus) {
        this.myFavStatus = myFavStatus;
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

    public int getUploadVehicleID() {
        return uploadVehicleID;
    }

    public void setUploadVehicleID(int uploadVehicleID) {
        this.uploadVehicleID = uploadVehicleID;
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

    public String getSearchLikeStatus() {
        return searchLikeStatus;
    }

    public void setSearchLikeStatus(String searchLikeStatus) {
        this.searchLikeStatus = searchLikeStatus;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public String getSearchBrand() {
        return searchBrand;
    }

    public void setSearchBrand(String searchBrand) {
        this.searchBrand = searchBrand;
    }

    public String getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(String searchModel) {
        this.searchModel = searchModel;
    }

    public String getSearchRtoCity() {
        return searchRtoCity;
    }

    public void setSearchRtoCity(String searchRtoCity) {
        this.searchRtoCity = searchRtoCity;
    }

    public String getSearchLocationCity() {
        return searchLocationCity;
    }

    public void setSearchLocationCity(String searchLocationCity) {
        this.searchLocationCity = searchLocationCity;
    }

    public String getSearchColor() {
        return searchColor;
    }

    public void setSearchColor(String searchColor) {
        this.searchColor = searchColor;
    }

    public String getSearchPrice() {
        return searchPrice;
    }

    public void setSearchPrice(String searchPrice) {
        this.searchPrice = searchPrice;
    }

    public String getSearchManfYear() {
        return searchManfYear;
    }

    public void setSearchManfYear(String searchManfYear) {
        this.searchManfYear = searchManfYear;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public int getSearchLeads() {
        return searchLeads;
    }

    public void setSearchLeads(int searchLeads) {
        this.searchLeads = searchLeads;
    }

    public String getSenderProfession() {
        return senderProfession;
    }

    public void setSenderProfession(String senderProfession) {
        this.senderProfession = senderProfession;
    }

    public String getSenderWebsite() {
        return senderWebsite;
    }

    public void setSenderWebsite(String senderWebsite) {
        this.senderWebsite = senderWebsite;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public int getSenderLikeCount() {
        return senderLikeCount;
    }

    public void setSenderLikeCount(int senderLikeCount) {
        this.senderLikeCount = senderLikeCount;
    }

    public int getSenderFollowCount() {
        return senderFollowCount;
    }

    public void setSenderFollowCount(int senderFollowCount) {
        this.senderFollowCount = senderFollowCount;
    }

    public String getSenderLikeStatus() {
        return senderLikeStatus;
    }

    public void setSenderLikeStatus(String senderLikeStatus) {
        this.senderLikeStatus = senderLikeStatus;
    }

    public String getSenderFollowStatus() {
        return senderFollowStatus;
    }

    public void setSenderFollowStatus(String senderFollowStatus) {
        this.senderFollowStatus = senderFollowStatus;
    }

    public String getStoreLikeStatus() {
        return storeLikeStatus;
    }

    public void setStoreLikeStatus(String storeLikeStatus) {
        this.storeLikeStatus = storeLikeStatus;
    }

    public String getStoreFollowStatus() {
        return storeFollowStatus;
    }

    public void setStoreFollowStatus(String storeFollowStatus) {
        this.storeFollowStatus = storeFollowStatus;
    }

    public int getStoreRating() {
        return storeRating;
    }

    public void setStoreRating(int storeRating) {
        this.storeRating = storeRating;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getStoreLikeCount() {
        return storeLikeCount;
    }

    public void setStoreLikeCount(int storeLikeCount) {
        this.storeLikeCount = storeLikeCount;
    }

    public int getStoreFollowCount() {
        return storeFollowCount;
    }

    public void setStoreFollowCount(int storeFollowCount) {
        this.storeFollowCount = storeFollowCount;
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

    public String getStoreTiming() {
        return storeTiming;
    }

    public void setStoreTiming(String storeTiming) {
        this.storeTiming = storeTiming;
    }

    public int getGroupVehicles() {
        return groupVehicles;
    }

    public void setGroupVehicles(int groupVehicles) {
        this.groupVehicles = groupVehicles;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
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

    public String getProductLikeStatus() {
        return productLikeStatus;
    }

    public void setProductLikeStatus(String productLikeStatus) {
        this.productLikeStatus = productLikeStatus;
    }

    public String getProductFollowStatus() {
        return productFollowStatus;
    }

    public void setProductFollowStatus(String productFollowStatus) {
        this.productFollowStatus = productFollowStatus;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductLikeCount() {
        return productLikeCount;
    }

    public void setProductLikeCount(int productLikeCount) {
        this.productLikeCount = productLikeCount;
    }

    public int getProductFollowCount() {
        return productFollowCount;
    }

    public void setProductFollowCount(int productFollowCount) {
        this.productFollowCount = productFollowCount;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getServiceLikeStatus() {
        return serviceLikeStatus;
    }

    public void setServiceLikeStatus(String serviceLikeStatus) {
        this.serviceLikeStatus = serviceLikeStatus;
    }

    public String getServiceFollowStatus() {
        return serviceFollowStatus;
    }

    public void setServiceFollowStatus(String serviceFollowStatus) {
        this.serviceFollowStatus = serviceFollowStatus;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getServiceLikeCount() {
        return serviceLikeCount;
    }

    public void setServiceLikeCount(int serviceLikeCount) {
        this.serviceLikeCount = serviceLikeCount;
    }

    public int getServiceFollowCount() {
        return serviceFollowCount;
    }

    public void setServiceFollowCount(int serviceFollowCount) {
        this.serviceFollowCount = serviceFollowCount;
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

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
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

    public int getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(int goingCount) {
        this.goingCount = goingCount;
    }

    public int getIgnoreCount() {
        return ignoreCount;
    }

    public void setIgnoreCount(int ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public int getUpVehicleShareCount() {
        return UpVehicleShareCount;
    }

    public void setUpVehicleShareCount(int upVehicleShareCount) {
        UpVehicleShareCount = upVehicleShareCount;
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

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }
}
