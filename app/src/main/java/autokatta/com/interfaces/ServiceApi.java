package autokatta.com.interfaces;

import autokatta.com.request.AddOwnVehicle;
import autokatta.com.request.CreateAuctionRequest;
import autokatta.com.request.CreateExchangeMelaRequest;
import autokatta.com.request.CreateLoanMelaRequest;
import autokatta.com.request.CreateSaleMelaRequest;
import autokatta.com.request.CreateServiceMelaRequest;
import autokatta.com.request.CreateStoreRequest;
import autokatta.com.request.RegistrationCompanyBasedrequest;
import autokatta.com.request.RegistrationRequest;
import autokatta.com.request.SaveSearchRequest;
import autokatta.com.request.UpdateMyVehicleRequest;
import autokatta.com.request.UpdateProfileRequest;
import autokatta.com.request.UpdateStoreRequest;
import autokatta.com.request.UploadUsedVehicleRequest;
import autokatta.com.response.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ak-001 on 18/3/17.
 */

public interface ServiceApi {

    //Wall Notifications...
    @GET("GetAllWallNotification")
    Call<WallResponse> _getWallNotifications(@Query("Contact") String contact, @Query("UserContact") String userContact,
                                             @Query("Layout") String layout);

    // Login API...
    @POST("Login")
    Call<LoginResponse> _autokattaLogin(@Query("Contact") String username, @Query("Password") String password);


    //Get Profile Data...
    @GET("GetProfileData")
    Call<ProfileAboutResponse> _autokattaGetProfile(@Query("Contact") String mycontact,
                                                    @Query("SenderContact") String otherContact);

    //Get Groups...
    @GET("GetGroups")
    Call<ProfileGroupResponse> _autokattaProfileGroup(@Query("Contact") String contact);

    //Get Upload Count...
    @GET("GetUploadCount")
    Call<String> _autokattaGetVehicleCount(@Query("Contact") String contact);

    //Get Own Store...
    @GET("GetOwnStoreList")
    Call<MyStoreResponse> _autokattaGetMyStoreList(@Query("Contact") String contact);

    //Get Vehicle List...
    @GET("GetVehicleType")
    Call<GetVehicleListResponse> _autokattaGetVehicleList();

    //Get Category...
    @GET("GetModule")
    Call<CategoryResponse> _autokattaGetCategories(@Query("type") String type);

    //Forgot Password
    @POST("GetContactForgotPassword")
    Call<String> _autokattaForgotPassword(@Query("Contact") String contact);

    //SearchStore Result
    @POST("getStoreByContact.php")
    Call<SearchStoreResponse> _autokattaGetSearchStore(@Query("mycontact") String myContact, @Query("storecontact") String storecontact,
                                                       @Query("location") String location, @Query("category") String category,
                                                       @Query("phrase") String phrase, @Query("radius") String radius, @Query("brandTags") String brands);


    //Registered Contact Validation
    @POST("RegistrationValidation")
    Call<String> regContactValidation(@Query("Contact") String contact);


    //add other category
    @POST("AddModule")
    Call<String> addOtherCategory(@Query("title") String contact);


    // get Industries
    @GET("GetRegisteredIndustries")
    Call<IndustryResponse> _getindustry();

    //add other Industry
    @POST("AddOtherIndustry")
    Call<String> addOtherIndustry(@Query("newIndustry") String industryname);

    // get OTP
    @POST("otp.php")
    Call<String> _autokattagetOTP(@Query("number") String contact);


    // After OTP Registration
    @POST("Registration1")
    Call<String> _autokattaAfterOtpRegistration(@Body RegistrationRequest registrationRequest);

    //get My Search
    @GET("GetMyVehicleSearch")
    Call<MySearchResponse> _autokattaGetMySearch(@Query("Contact") String myContact);

    //New Password
    @POST("UpdateForgotPassword")
    Call<String> _autokattanewpassword(@Query("Contact") String contact, @Query("newPassword") String newPass);

    //get My Uploaded vehicles
    @GET("GetUploadedvehicles")
    Call<MyUploadedVehiclesResponse> _autokattaGetMyUploadedVehicles(@Query("Contact") String myContact);

    //get My Active Events
    @GET("GetAuctionEvents")
    Call<MyActiveAuctionResponse> _autokattaGetMyActiveAuction(@Query("Contact") String myContact, @Query("Status") String status, @Query("timestamp") int timestamp);

    //get My Active Loan Mela
    @GET("GetAllLoanMela")
    Call<MyActiveLoanMelaResponse> _autokattaGetMyActiveLoanMela(@Query("Contact") String myContact);

    //get My Active Exchange Mela
    @GET("GetAllMyExchangeMela")
    Call<MyActiveExchangeMelaResponse> _autokattaGetMyActiveExchangeMela(@Query("Contact") String myContact);

    //get My Upcoming Auction
    @GET("GetMyUpcomingAuction")
    Call<MyUpcomingAuctionResponse> __autokattaGetMyUpcomingAuction(@Query("Contact") String myContact);

    //get My Upcoming Loan Mela
    @GET("GetUpcomingLoanMela")
    Call<MyUpcomingLoanMelaResponse> __autokattaGetMyUpcomingLoanMela(@Query("Contact") String myContact);

    //get My Upcoming Exchange Mela
    @GET("GetUpcomingExchangeMela")
    Call<MyUpcomingExchangeMelaResponse> __autokattaGetMyUpcomingExchangeMela(@Query("Contact") String myContact);

    //get My Upcoming Sale Mela
    @GET("GetUpcomingSaleMela")
    Call<MyUpcomingExchangeMelaResponse> __autokattaGetMyUpcomingSaleMela(@Query("Contact") String myContact);

    //get My Upcoming Service Mela
    @GET("GetUpcomingServiceMela")
    Call<MyUpcomingExchangeMelaResponse> __autokattaGetMyUpcomingServiceMela(@Query("Contact") String myContact);

    //get saved Auctions
    @GET("GetMySavedAuction")
    Call<MySavedAuctionResponse> _autokattaMySavedAuctions(@Query("Contact") String myContact);

    //Create Group
    @POST("CreateGroup")
    Call<String> _autokattaCreateGroup(@Query("Title") String title, @Query("Image") String image, @Query("AdminContact") String contact);

    //get Vehicle Sub Types...
    @GET("GetVehicleSubType")
    Call<GetVehicleSubTypeResponse> _autokattaGetVehicleSubType(@Query("CategoryID") int vehicleId);

    //get Blacklisted contacts
    @GET("GetMyBlacklistedContact")
    Call<BlacklistMemberResponse> _autokattaBlacklistMembers(@Query("Contact") String contact);

    //Update Registration
    @POST("UpdateRegistration")
    Call<String> _autokattaUpdateRegistration(@Body RegistrationCompanyBasedrequest registrationCompanyBasedrequest);

    /*
    Add Brand Model Version...
     */
    //Add Brand
    @POST("AddYourOptions")
    Call<String> _autokattaAddBrand(@Query("Keyword") String keyword, @Query("Title") String title,
                                    @Query("CategoryID") int categoryId, @Query("SubCategoryID") String subCatID);

    //Add Model
    @POST("AddYourOptions")
    Call<String> _autokattaAddModel(@Query("Keyword") String keyword, @Query("Title") String title,
                                    @Query("CategoryID") int categoryId, @Query("SubCategoryID") String subCatID,
                                    @Query("BrandID") String brandId);

    //Add Version
    @POST("AddYourOptions")
    Call<String> _autokattaAddVersion(@Query("Keyword") String keyword, @Query("Title") String title,
                                      @Query("CategoryID") int categoryId, @Query("SubCategoryID") String subCatID,
                                      @Query("BrandID") String brandId, @Query("ModelID") String modleId);

    //Add Break..
    @POST("PostOtherBrake")
    Call<String> _autokattaAddBreaks(@Query("OtherBrake") String otherBreaks);

    //Add Pump...
    @POST("PostOtherPump")
    Call<String> _autokattaAddPump(@Query("OtherPump") String otherPump);

    //addBodyAndSeatManufacturers
    @POST("AddBodyAndSeatManufacturers")
    Call<String> _autokattaAddBodyAndSeatManufacturers(@Query("bodyManufacturerName") String bodyManufactureName,
                                                       @Query("seatManufacturerName") String seatManufacture);

    // Add Body Type
    @POST("AddYourOptions")
    Call<String> _autokattaAddBodyType(@Query("Keyword") String keyword, @Query("Title") String title);

    /*
    Get Data...
     */

    //Get Brand
    @GET("GetVehicleBrands")
    Call<GetVehicleBrandResponse> _autokattaGetBrand(@Query("CategoryID") int category, @Query("SubCategoryID") String subCategory);

    //Get Model
    @GET("GetVehicleModel")
    Call<GetVehicleModelResponse> _autokattaGetModel(@Query("CategoryID") int category, @Query("SubCategoryID") String subCategory,
                                                     @Query("BrandID") String brandId);

    //Get Version
    @GET("GetVehicleVersion")
    Call<GetVehicleVersionResponse> _autokattaGetVersion(@Query("CategoryID") int category, @Query("SubCategoryID") String subCategory,
                                                         @Query("BrandID") String brandId, @Query("ModelID") String modelId);

    //Get Breaks
    @GET("GetBrakes")
    Call<GetBreaks> _autokattaGetBreaks();

    //Get getPumps
    @GET("GetPump")
    Call<GetPumpResponse> _autokattaGetPumps();

    /*
    GetRTOCity
     */
    @GET("GetVehicleRTOCity")
    Call<GetRTOCityResponse> _autokattaGetVehicleRTOCity();

    //Get Body and Seat Manufacture
    @GET("GetBodyAndSeatManufacturers")
    Call<BodyAndSeatResponse> _autokattaGetBodyAndSeatManufacture();

    //Get Body Type
    @GET("GetBodyType")
    Call<GetBodyTypeResponse> _autokattaGetBodyType();

    //Get Vehicle Color
    @GET("GetVehicleColor")
    Call<GetVehicleColor> _autokattaGetColor();

    //Get Vehicle Implements...
    @GET("GetVehicleImplements")
    Call<GetVehicleImplementsResponse> _autokattaGetVehicleImplements();

    //create loan mela event
    @POST("CreateLoanMela")
    Call<LoanMelaCreateResponse> _createLoanMela(@Body CreateLoanMelaRequest createLoanMelaRequest);


    //create loan mela event
    @POST("CreateExchangeMela")
    Call<ExchangeMelaCreateResponse> _createExchangeMela(@Body CreateExchangeMelaRequest createExchangeMelaRequest);

    //create Sale mela event
    @POST("CreateSaleMela")
    Call<SaleMelaCreateResponse> _createSaleMela(@Body CreateSaleMelaRequest createSaleMelaRequest);

    //create Service mela event
    @POST("CreateServiceMela")
    Call<ServiceMelaCreateResponse> _createServiceMela(@Body CreateServiceMelaRequest createServiceMelaRequest);

    //get My Broadcast groups
    @GET("GetBroadcastGroups")
    Call<MyBroadcastGroupsResponse> _autokattaGetBroadcastGroups(@Query("Owner") String myContact);


    //Upload Vehicle
    @POST("UpdateMyVehicle")
    Call<String> _autokattaUploadVehicle(@Body UpdateMyVehicleRequest updateMyVehicleRequest);


    //ADD own Vehicle
    @POST("AddOwnVehicles")
    Call<String> _autokattaAddOwn(@Body AddOwnVehicle addOwnVehicle);


    //Get Brand Modle Version
    @GET("GetBrandModelVersion")
    Call<GetBrandModelVersionResponse> _autokattaGetBrandModelVersion(@Query("SubCategoryID") String sub_category_id);

    //create loan mela event
    @GET("GetPriceSuggestion")
    Call<PriceSuggestionResponse> _autokattaGetPriceSuggestion(@Query("CategoryId") int categoryId, @Query("SubCategoryId") String subCategoryId,
                                                               @Query("BrandID") String brandId, @Query("ModelID") String modelId,
                                                               @Query("VersionId") String versionId, @Query("ManufactureYear") String mfgYear,
                                                               @Query("RTOCity") String rtoCity);

    //Get Group Vehicles
    @GET("GetGroupVehicles")
    Call<GetGroupVehiclesResponse> _autokattaGetGroupVehicles(@Query("GroupID") int groupId, @Query("Brand") String brand,
                                                              @Query("Model") String model, @Query("Version") String version,
                                                              @Query("City") String city, @Query("RTOcity") String rtoCity,
                                                              @Query("Price") String price, @Query("RegistrationYear") String regYear,
                                                              @Query("ManufactureYear") String mgfYear, @Query("Kms") String kms,
                                                              @Query("Owners") int owners);

    //Get My Uploaded Vehicle...
    @GET("GetMyUploadedVehicles")
    Call<GetGroupVehiclesResponse> _autokattaMyUploadedVehicles(@Query("Contact") String contact);


    //Get SpecialCaluses For Auction
    @POST("SpecialClauses")
    Call<SpecialClauseGetResponse> getSpecialClauses(@Query("KeyWord") String keyword, @Query("Clause") String clause);


    //Add SpecialCaluses For Auction
    @POST("SpecialClauses")
    Call<SpecialClauseAddResponse> addSpecialClauses(@Query("KeyWord") String keyword, @Query("Clause") String clause);

    //Get Group Contacts...
    @GET("GetGroupContacts")
    Call<GetGroupContactsResponse> _autokattaGetGroupContacts(@Query("GroupID") int groupId);

    //Get Group Products...
    @GET("GetGroupProducts")
    Call<StoreInventoryResponse> _autokattaGetGroupProducts(@Query("GroupID") int groupId, @Query("MyContact") String myContact);

    //Get Group Services...
    @GET("GetGroupServices")
    Call<StoreInventoryResponse> _autokattaGetGroupServices(@Query("GroupID") int groupId, @Query("MyContact") String myContact);

    //delete group members...
    @POST("DeleteMyGroupMembers")
    Call<String> _autokattaDeleteGroupMembers(@Query("GroupID") int group_id, @Query("GroupType") String grouptype,
                                              @Query("Contact") String contact, @Query("MyContact") String mycontact,
                                              @Query("Next") String next, @Query("MemberCount") String membercount);

    //make group admin
    @POST("MakeAdmin")
    Call<String> _autokattaMakeGroupAdmin(@Query("GroupID") int mGroupId, @Query("Contact") String contact,
                                          @Query("Action") String action);

    //Create an Auction
    @POST("CreateAuction")
    Call<AuctionCreateResponse> createAuction(@Body CreateAuctionRequest createAuctionRequest);


    //Update Company Based Registration
    @POST("UpdateRegistration")
    Call<String> _autokattaUpdateCompanyRegistration(@Body RegistrationCompanyBasedrequest registrationCompanyBasedrequest);

    //Get States
    @GET("GetRegisteredStates")
    Call<GetStatesResponse> _autokattaGetStates();

    //Get Districts
    @GET("GetRegisteredDistricts")
    Call<GetDistrictsResponse> _autokattaGetDistricts();

    //Get New Designation
    @POST("AddOtherDesignations")
    Call<String> _autokattaAddNewDesignation(@Query("DesignationName") String designationName);

    //Add New Company
    @POST("AddOtherCompanyNames")
    Call<String> _autokattaAddNewCompany(@Query("CompanyName") String companyName);

    //Add New Deal
    @POST("AddOtherDealing")
    Call<String> _autokattaAddNewDeal(@Query("deals") String deals);

    //Add New Skill
    @POST("AddOtherSkills")
    Call<String> _autokattaAddNewSkills(@Query("Skill") String skills);

    //Get deals
    @GET("GetRegisteredDeals")
    Call<getDealsResponse> _autokattaGetDeals();

    //Get  Skills
    @GET("GetRegisteredSkills")
    Call<GetSkillsResponse> _autokattaGetSkills();

    //Get  Designation
    @GET("GetRegisteredDesignation")
    Call<GetDesignationResponse> _autokattaGetDesignation();


    //Get  Company
    @GET("GetRegisteredCompanies")
    Call<GetCompaniesResponse> _autokattaGetCompany();


    //get  Ended Auction Events
    @GET("GetEndedAuction")
    Call<MyActiveAuctionResponse> getEndedAuctions(@Query("Contact") String myContact);


    //get Ended Loan Mela
    @GET("GetEndedLoanMela")
    Call<EndedSaleMelaResponse> getEndedLoanMela(@Query("Contact") String myContact);


    //get Ended Exchange Mela
    @GET("GetEndedExchangeMela")
    Call<EndedSaleMelaResponse> getEndedExchangeMela(@Query("Contact") String myContact);

    //set vehicle privacy
    @POST("VehicleGroupStoreRef")
    Call<String> _autokattaSetVehiclePrivacy(@Query("Contact") String myContact, @Query("VehicleID") int vehicleid,
                                             @Query("GroupIDs") String groupIds, @Query("StoreID") String storeIds);

    //Get Vehicle By Id...
    @GET("GetVehicleByID")
    Call<GetVehicleByIdResponse> _autokattaGetVehicleById(@Query("Contact") String yourcontact, @Query("VehicleID") int vehicleId);

    //Delete a store...
    @POST("DeleteMyStore")
    Call<String> _autokattaDeleteStore(@Query("StoreID") int storeId, @Query("keyword") String keyword);

    //Create a store...
    @POST("CreateStore")
    Call<CreateStoreResponse> _autokattaCreatetore(@Body CreateStoreRequest request);

    //Get Store Admins...
    @GET("GetStoreAdmin")
    Call<StoreOldAdminResponse> _autokattaGetStoreAdmin(@Query("StoreID") int store_id);

    //Add new Store Admins...
    @POST("AddStoreAdmin")
    Call<String> _autokattaAddNewStoreAdmin(@Query("StoreID") int store_id, @Query("Contact") String admins);

    //get Contact By Company
    @GET("GetContactsBasedOnCompany")
    Call<GetContactByCompanyResponse> _autokattaGetContactByCompany(@Query("Contact") String contact, @Query("Page") String Page);

    //Get Store Profile Info...
    @GET("GetStoreProfileInfo")
    Call<GetStoreProfileInfoResponse> _autokattaGetProfileInfo(@Query("Contact") String contact);

    //Get My Autokatta Contacts...
    @GET("GetAutokattaContact")
    Call<GetAutokattaContactResponse> getAutokattaContact(@Query("Contacts") String contact, @Query("MyContact") String number,
                                                          @Query("Names") String name);

    //Get My Registered Contacts...
    @GET("GetAllContactResistered")
    Call<GetRegisteredContactsResponse> _autokattaGetRegisteredContact(@Query("Contact") String contact);

    //create User.
    @POST("CreateDefaultUser")
    Call<CreateUserResponse> _autokattaCreateUser(@Query("UserName") String username, @Query("Contact") String contact);

    //Follow profile
    @POST("NewFollow")
    Call<String> _autokattaFollow(@Query("SenderContact") String senderContact, @Query("ReceiverContact") String receiverContact,
                                  @Query("Layout") String layout, @Query("StoreID") int storeid,
                                  @Query("VehicleID") int vehicleid, @Query("ProductID") int pid,
                                  @Query("ServiceID") int servid);

    //Un Follow
    @POST("NewUnfollow")
    Call<String> _autokattaUnfollow(@Query("SenderContact") String senderContact, @Query("ReceiverContact") String receiverContact,
                                    @Query("Layout") String layout, @Query("StoreID") int storeid,
                                    @Query("VehicleID") int vid, @Query("ProductID") int pid, @Query("ServiceID") int sid);


    //remove Contact From blacklist
    @POST("AddRemoveToBlacklist")
    Call<String> removeContactFromBlacklist(@Query("MyContact") String myContact, @Query("AuctionID") String strAuctionId,
                                            @Query("Contact") String rContact, @Query("Keyword") String keyword,
                                            @Query("EventType") String eventType);


    //delete uploaded vehicle
    @POST("DeleteMyUploadedVehicles")
    Call<String> deleteUploadedVehicles(@Query("VehicleID") String vehicle_id, @Query("Keyword") String keyword);


    //create Excel sheet names from admin
    @GET("GetMyExcelSheetName")
    Call<AdminExcelSheetResponse> _autokattaGetAdminExcelSheetNames(@Query("contact") String contact);

    //get Admin vehicles
    @GET("GetVehiclesFromAdmin")
    Call<AdminVehiclesResponse> _autokattaGetAdminVehicles(@Query("Contact") String contact, @Query("FileName") String filename,
                                                           @Query("UserID") String userid);

    //get All vehicles for Auction
    @GET("GetUploadedAndReauctionVehiclesForAuction")
    Call<AuctionAllVehicleResponse> _autokattaGetAuctionAllVehicles(@Query("Contact") String contact);

    //get All vehicles for Auction
    @GET("getReauctionVehicleByNameAndContact.php")
    Call<AuctionReauctionVehicleResponse> _autokattaGetReauctionedVehicle(@Query("contact") String contact,
                                                                          @Query("auctionID") String auctionID);


    //send notification of upload vehicle
    @POST("uploaded_vehicle_notification.php")
    Call<String> sendNotificationOfUploadedVehicle(@Query("vehicle_id") String vehicle_id, @Query("keyword") String keyword);


    //Edit Group
    @POST("UpdateGroupProfile")
    Call<String> editGroup(@Query("GroupName") String groupname, @Query("GroupID") String group_id, @Query("ProfilePicture") String profile);

    //Delete Group
    @GET("DeleteMyGroups")
    Call<String> deleteGroup(@Query("GroupID") int group_id, @Query("Keyword") String keyword, @Query("Mycontact") String contact);

    //All Live Events
    @POST("getAllLiveEvents.php")
    Call<GetLiveEventsResponse> getLiveEvents(@Query("contact") String contact);

    //All Live Loan Events
    @POST("getAllLiveLoanEvents.php")
    Call<GetLiveLoanEventsResponse> getLiveLoanEvents(@Query("contact") String contact);

    //All Live Exchange Events
    @GET("getAllLiveExchangeEvents.php")
    Call<GetLiveExchangeEventsResponse> getLiveExchangeEvents(@Query("contact") String contact);

    //All Live Sale Events
    @GET("getAllLiveSaleEvents.php")
    Call<GetLiveSaleEventsResponse> getLiveSaleEvents(@Query("contact") String contact);

    //All Live Service Events
    @GET("getAllLiveServiceEvents.php")
    Call<GetLiveServiceEventsResponse> getLiveServiceEvents(@Query("contact") String contact);

    //All Upcoming Sale Events Events
    @GET("getAllUpcomingSaleEvents.php")
    Call<GetLiveSaleEventsResponse> getUpcomingSaleEvents(@Query("contact") String contact);

    //All Upcoming Loan Events Events
    @GET("getAllUpcomingLoanEvents.php")
    Call<GetLiveSaleEventsResponse> getUpcomingLoanEvents(@Query("contact") String contact);

    //All Upcoming Exchange Events Events
    @GET("getAllUpcomingExchangeEvents.php")
    Call<GetLiveSaleEventsResponse> getUpcomingExchangeEvents(@Query("contact") String contact);

    //All Upcoming Service Events Events
    @GET("getAllUpcomingServiceEvents.php")
    Call<GetLiveSaleEventsResponse> getUpcomingServiceEvents(@Query("contact") String contact);

    //All Going Events
    @GET("GetAllGoingEventsByMe")
    Call<GetLiveEventsResponse> getGoingEvents(@Query("Contact") String userName);

    //All Going Loan Events
    @GET("GetAllGoingLoanEventsByMe")
    Call<GetLiveSaleEventsResponse> getGoingLoanEvents(@Query("Contact") String userName);

    //All Going Exchange Events
    @GET("GetAllGoingExchangeEventsByMe")
    Call<GetLiveSaleEventsResponse> getGoingExchangeEvents(@Query("Contact") String userName);

    //All Going Service Events
    @GET("GetAllGoingServiceEventsByMe")
    Call<GetLiveSaleEventsResponse> getGoingServiceEvents(@Query("Contact") String userName);

    //All Going Sale Events
    @GET("GetAllGoingSaleEventsByMe")
    Call<GetLiveSaleEventsResponse> getGoingSaleEvents(@Query("Contact") String userName);

    //All Upcoming Events
    @GET("getAllUpcomingEventsUpto50kms.php")
    Call<GetLiveEventsResponse> getUpcomingEvents(@Query("contact") String userName);

    //delete my search item
    @GET("DeleteUpdateMySearch")
    Call<String> deleteMySearch(@Query("SearchID") int search_id, @Query("Keyword") String keyword);

    //Update Auction
    @POST("UpdateAuctionCreation")
    Call<String> _autokattaUpdateAuctionCreation(@Query("AuctionID") String auction_id, @Query("Title") String title,
                                                 @Query("StartDate") String start_date, @Query("StartTime") String start_time,
                                                 @Query("EndDate") String end_date, @Query("EndTime") String end_time,
                                                 @Query("SpecialClauses") String special_clauses, @Query("VehicleIDs") String vehicle_ids,
                                                 @Query("Status") String status, @Query("ShowHide") String ShowHide,
                                                 @Query("NoVehicle") String NoVehicle);

    //Addstart and reserved price
    @POST("AddStartReservedPrice")
    Call<String> _autokattaAddStart_ReservedPrice(@Query("AuctionID") String auctionId, @Query("VehicleID") String vehicleId,
                                                  @Query("StartPrice") String startPrice, @Query("ReservedPrice") String reservedPrice);

    //Send Auction mail...
    @GET("email_v.php")
    Call<String> _autokattaSendAuctionMail(@Query("contact") String myContact, @Query("auction_id") int strAuctionId);

    //get Auction Participants
    @GET("GetAuctionConfirmedParticipants")
    Call<AuctionParticipantsResponse> _autokattaGetAuctionParticipants(@Query("MyContact") String myContact, @Query("AuctionID")
            String strAuctionId);

    //Add/remove blacklist contact
    @POST("AddRemoveToBlacklist")
    Call<String> _autokattaAddRemoveBlacklist(@Query("MyContact") String myContact, @Query("AuctionID") String strAuctionId,
                                              @Query("Contact") String rContact, @Query("Keyword") String keyword,
                                              @Query("EventType") String eventType);

    // Get Auction Analytics
    @GET("GetAnalyticsCount")
    Call<AuctionAnalyticsResponse> _autokattaGetAuctionAnalytics(@Query("AuctionID") String strAuctionId);

    // Get Loan Analytics
    @GET("GetLoanAnalyticsCount")
    Call<LoanMelaAnalyticsResponse> _autokattaGetLoanAnalytics(@Query("LoanMelaID") String loanid);

    //get Active Auction high bid
    @POST("AuctionHighestBidding")
    Call<MyActiveAuctionHighBidResponse> _autokattaGetActiveAuctionHighBid(@Query("contact") String myContact,
                                                                           @Query("AuctionID") String mAuctionId);

    //get Active Auction Above reserved price bid
    @POST("auctionReservedPrice.php")
    Call<MyActiveAuctionAboveReservedResponse> _autokattaGetActiveAuctionAboveReservedPrice(@Query("contact") String myContact,
                                                                                            @Query("auctionid") String mAuctionId);

    //get Active Auction No bid
    @POST("AuctionNoBidding")
    Call<MyActiveAuctionNoBidResponse> _autokattaGetActiveAuctionNoBid(@Query("AuctionID") String mAuctionId);

    //Get Approve vehicle
    @POST("AuctionApprovedVehicles")
    Call<EndedAuctionApprovedVehiResponse> _autokattaGetEndedApproveVehi(@Query("contact") String myContact,
                                                                         @Query("AuctionID") String mAuctionId);

    //add vehicle for reauction
    @POST("AddToReauction")
    Call<String> _autokattaAddVehicleToReauction(@Query("VehicleId") String vehicleid, @Query("AuctionID") String mAuctionId);

    //Approve an vehicle
    @POST("AddToApprovedVehicles")
    Call<ApprovedVehicleResponse> _autokattaApproveAnVehiclewithBid(@Query("AuctionID") String mAuctionId, @Query("Keyword") String keyword1,
                                                                    @Query("VehicleID") String vehicleid, @Query("BidderContact") String bidderContact,
                                                                    @Query("BidAmount") String bidPrice);

    //get Browse store data
    @GET("GetBrowseStores")
    Call<BrowseStoreResponse> getBrowseStores(@Query("YourContact") String yourcontact, @Query("Keyword") String keyword);

    //Get Auction Preview By Id...
    @GET("GetAuctionEventDetails")
    Call<GetAuctionEventResponse> getAuctionEvent(@Query("AuctionID") int auctionId);

    // Create  BroadCast Group

    @POST("CreateBroadCastGroups")
    Call<String> createBroadcastGroup(@Query("Title") String title, @Query("Owner") String owner, @Query("Members") String members
            , @Query("Keyword") String keyword, @Query("GroupID") String groupid);


    // Delete BroadCast Group

    @POST("CreateBroadCastGroups")
    Call<String> deleteBroadcastGroup(@Query("Title") String title, @Query("Owner") String owner, @Query("Members") String members
            , @Query("Keyword") String keyword, @Query("GroupID") String groupid);

    // Update BroadCast Group
    @POST("CreateBroadCastGroups")
    Call<String> updateBroadcastGroup(@Query("Title") String title, @Query("Owner") String owner, @Query("Members") String members
            , @Query("Keyword") String keyword, @Query("GroupID") String groupid);

    //Get Your Bid Response
    @GET("userYourBid.php")
    Call<YourBidResponse> getYourBid(@Query("auctionId") String id, @Query("userContactNo") String contact);

    //Get Out Bid Response
    @GET("userOutBid.php")
    Call<YourBidResponse> getOutBid(@Query("auctionId") String id, @Query("userContactNo") String contact);

    //Get Highest Bid Response
    @GET("userHighestBid.php")
    Call<YourBidResponse> getHighestBid(@Query("auctionId") String id, @Query("userContactNo") String contact);

    //Get Highest Bid Response
    @GET("userWatchedItems.php")
    Call<YourBidResponse> userWatchedItems(@Query("auctionId") String id, @Query("userContactNo") String contact);

    //send broadcast message
    @POST("SendBroadCastMessage")
    Call<String> broadCastGroupMessage(@Query("GroupID") String groupid, @Query("MsgText") String msgText, @Query("MsgImage") String lastword);

    @POST("AddMyBids")
    Call<String> addMyBid(@Query("AuctionID") String auctionId, @Query("VehicleID") String vehicleID,
                          @Query("BidAmount") String bidAmount, @Query("TabNo") String tabNo, @Query("MyContact") String contact);


    //get broadcast recievers
    @GET("GetReplySenders")
    Call<BroadcastReceivedResponse> getBroadcastReceivers(@Query("MyContact") String myContact, @Query("ProductID") String product_id,
                                                          @Query("ServiceID") String service_id, @Query("VehicleID") String vehicle_id);


    //Get  broadcast senders
    @GET("GetMySenders")
    Call<BroadcastSendResponse> getBroadcastSenders(@Query("MyContact") String myContact);


    @GET("GetMyChatDetails")
    Call<getBussinessChatResponse> getBussinessChat(@Query("MyContact") String contact);

    //get Chat message
    @GET("GetChatMessage")
    Call<BroadcastMessageResponse> getChatMessageData(@Query("SenderContact") String sender_contact, @Query("ReceiverContact") String receiver_contact,
                                                      @Query("ProductID") int product_id,
                                                      @Query("ServiceID") int service_id, @Query("VehicleID") int vehicle_id);

    //Get Ignore Going me...
    @POST("AddIgnoreGoingMe")
    Call<String> addIgnoreGoingMe(@Query("Contact") String contact, @Query("AuctionID") int auctionId, @Query("LoanID") String loanId,
                                  @Query("ExchangeID") int exchangeId, @Query("SaleID") String saleId, @Query("ServiceID") String serviceId,
                                  @Query("Action") String action);

    //send Chat Message
    @POST("SaveChatMessage")
    Call<String> sendChatMessage(@Query("SenderContact") String sender_contact, @Query("ReceiverContact") String receiver_contact,
                                 @Query("Message") String message, @Query("Image") String image,
                                 @Query("ProductID") int product_id, @Query("ServiceID") int service_id, @Query("VehicleID") int vehicle_id);


    //get Chat message elements details
    @GET("GetMyChatAllData")
    Call<ChatElementDetails> getChatElementData(@Query("ProductID") int product_id, @Query("ServiceID") int service_id,
                                                @Query("VehicleID") int vehicle_id);


    //get Uploaded vehicle buyer list
    @GET("GetBuyerNotification")
    Call<BuyerResponse> getUploadedVehicleBuyerlist(@Query("Contact") String contact);

    //Update Profile
    @POST("UpdateProfile")
    Call<String> _autokattaUpdateProfile(@Body UpdateProfileRequest updateProfileRequest);


    //Update Profile Username And Image
    @POST("UpdateProfile")
    Call<String> _autokattaUpdateUserName(@Body UpdateProfileRequest updateProfileRequest);

    //Update Profile
    @GET("GetColors")
    Call<ColorResponse> _autokattaGetAllColor();

    //Group Profile
    @Multipart
    @POST("upload_profile.php")
    Call<String> uploaGroupProfilePic(@Part MultipartBody.Part file, @Part("file") RequestBody name);


    @Multipart
    @POST("upload_profile_profile_pics.php")
    Call<String> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    //Broadcast Groups
    @Multipart
    @POST("upload_broadcastimages.php")
    Call<String> uploadImageBroadcast(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @Multipart
    @POST("upload_store_profile.php")
    Call<String> uploadStorePic(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    //Upload Loan and Exchange image
    @Multipart
    @POST("upload_loanExchangepics.php")
    Call<String> uploadEventsPic(@Part MultipartBody.Part file, @Part("file") RequestBody name);


    //send buyer call date
    @POST("SendBuyerCalldate")
    Call<String> sendLastCallDate(@Query("Caller") String caller,
                                  @Query("Callie") String callie,
                                  @Query("CallDate") String calldate,
                                  @Query("CallCount") String callcount);


    //add to favourite
    @POST("AddMyFavourites")
    Call<String> autokatta_AddToFavorite(@Query("Contact") String contact,
                                         @Query("BuyerVehicleID") String buyer_vehicle_id,
                                         @Query("SearchID") int search_id,
                                         @Query("SellerVehicleID") String seller_vehicle_id,
                                         @Query("NotificationID") int noti_id);

    //Remove to favourite
    @POST("RemoveMyFavourites")
    Call<String> autokatta_RemoveFromFavorite(@Query("Contact") String contact,
                                              @Query("BuyerVehicleID") String buyer_vehicle_id,
                                              @Query("SearchID") int search_id,
                                              @Query("SellerVehicleID") String seller_vehicle_id,
                                              @Query("NotificationID") int noti_id);

    //Save My Search
    @POST("SaveSearch")
    Call<String> _autokattaSaveMySearch(SaveSearchRequest saveSearchRequest);


    //get Uploaded vehicle buyer list
    @GET("GetSellerNotification")
    Call<SellerResponse> getSavedSearchSellerList(@Query("Contact") String contact);

    //get Own Vehicles
    @GET("GetOwnVehicles")
    Call<GetOwnVehiclesResponse> _autokattaGetOwnVehicles(@Query("Contact") String contact);

    //get All States
    @GET("GetStates")
    Call<AllStatesResponse> _autokattaGetAllStates();

    //get Followers
    @GET("GetFollowers")
    Call<GetFollowersResponse> _autokattaGetFollowers(@Query("Contact") String contact);


    //get single store info
    @GET("GetStoreInfo")
    Call<StoreResponse> getStoreData(@Query("MyContact") String contact, @Query("StoreID") int store_id);

    @GET("GetVehicleForAuction")
    Call<GetVehicleForAuctionResponse> getVehicleAuction(@Query("AuctionID") String auctionId, @Query("VehicleID") String vehicleId,
                                                         @Query("Contact") String contact);

    //contact not used
    @POST("AdminVehicleMoreDetails")
    Call<GetAdminVehicleResponse> getAdminAuction(@Query("VehicleID") String vehicleId, @Query("contact") String contact);

    //get tags For Brand
    @GET("GetBrandTag")
    Call<BrandsTagResponse> autokattaGetBrandTags(@Query("type") String type);

    //add other Brand tags
    @POST("AddOtherBrandTags")
    Call<OtherBrandTagAddedResponse> addOtherBrandTags(@Query("Tag") String brandtag, @Query("Type") String type);

    //update_tag_association.php
    @POST("UpdateTagAssociation")
    Call<String> updateTagAssociation(@Query("ProductID") int product_id, @Query("ServiceID") String serviceId, @Query("TagID") String tagId);


    //tag_association.php
    @POST("TagAssociation")
    Call<String> tagAssociation(@Query("ProductID") int product_id, @Query("ServiceID") int serviceId, @Query("TagID") String tagId);


       /*
    Get all products,service and vehicles related to single store
     */

    @GET("GetProductByCategory")
    Call<StoreInventoryResponse> getStoreInventory(@Query("StoreID") int store_id, @Query("MyContact") String mycontact);

     /*
    Get all products,service and vehicles related to single store
     */

    @GET("GetMyInventoryCatalogDetails")
    Call<StoreInventoryResponse> getInventoryCatalog(@Query("MyContact") String mycontact);

    /*
    Like
     */

    @POST("Newlikes")
    Call<String> _autokattaLike(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                @Query("Layout") String layout, @Query("StoreID") int store_id,
                                @Query("GroupID") int gid, @Query("VehicleID") int vid,
                                @Query("ProductID") int pid, @Query("ServiceID") int sid,
                                @Query("StatusID") int statusid, @Query("SearchID") int searchid);

      /*
    UnLike
     */

    @POST("NewUnlikes")
    Call<String> _autokattaUnLike(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                  @Query("Layout") String layout, @Query("StoreID") int store_id,
                                  @Query("GroupID") int gid, @Query("VehicleID") int vid,
                                  @Query("ProductID") int pid, @Query("ServiceID") int sid,
                                  @Query("StatusID") int statusid, @Query("SearchID") int searchid);


    //delete product
    @POST("DeleteMyProduct")
    Call<String> deleteProduct(@Query("ProductID") int product_id, @Query("Keyword") String keyword);

    //delete service
    @POST("DeleteMyService")
    Call<String> deleteService(@Query("ServiceID") int service_id, @Query("Keyword") String keyword);

    //delete vehicle
    @POST("DeleteMyUploadedVehicles")
    Call<String> deleteVehicle(@Query("VehicleID") int vehicle_id, @Query("Keyword") String keyword);

    //Search Product...
    @GET("GetProductSearchData")
    Call<GetSearchProductResponse> searchProduct(@Query("SearchKey") String key, @Query("MyContact") String contact);

    //Search Person...
    @GET("GetPersonSearchData")
    Call<SearchPersonResponse> getPersonSearchData(@Query("SearchKey") String key, @Query("MyContact") String contact);

    //Search Vehicle...
    @GET("GetVehicleSearchData")
    Call<SearchVehicleResponse> getVehicleSearchData(@Query("SearchKey") String key, @Query("MyContact") String contact);

    //Search Auction...
    @GET("GetSearchAuctionData")
    Call<GetSearchAuctionResponse> getSearchAuctionData(@Query("SearchKey") String key);

    //Post Product Review...
    @POST("PostProductReview")
    Call<String> postProductReview(@Query("Contact") String contact, @Query("StoreID") String storeId,
                                   @Query("ProductID") int productId, @Query("Review") String review, @Query("ServiceID") int serviceid);

   /* //Likes in Vehicle details
    @POST("Newlikes")
    Call<String> _autokattaVehicleLike(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                       @Query("Layout") String layout, @Query("StoreID") String store_id,
                                       @Query("GroupID") String gid ,@Query("VehicleID") String vid,
                                       @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                       @Query("StatusID") String statusid, @Query("SearchID") String searchid);*/

    //Calling in Vehicle details
    @POST("calling")
    Call<String> _autokattaVehicleCalling(@Query("VehicleID") int vehicle_id, @Query("Keyword") String keyword);


    /* //UnLikes in Vehicle details
     @POST("NewUnlikes")
     Call<String> _autokattaVehicleUnLike(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                          @Query("Layout") String layout, @Query("StoreID") String store_id,
                                          @Query("GroupID") String gid ,@Query("VehicleID") String vid,
                                          @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                          @Query("StatusID") String statusid, @Query("SearchID") String searchid);
 */
    //get tags
    @GET("GetTags")
    Call<GetTagsResponse> _autoGetTags(@Query("Type") String type);

    //get tags
    @POST("AddOtherTags")
    Call<OtherTagAddedResponse> _autoAddTags(@Query("Tag") String tag, @Query("Type") String type);
/*

    //Likes in Product View
    @POST("Newlikes")
    Call<String> _autokattaProductView(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                       @Query("Layout") String layout, @Query("StoreID") String store_id,
                                       @Query("GroupID") String gid ,@Query("VehicleID") String vid,
                                       @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                       @Query("StatusID") String statusid, @Query("SearchID") String searchid);
*/


   /* //Likes in service View
    @POST("Newlikes")
    Call<String> _autokattaServiceView(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                       @Query("Layout") String layout, @Query("StoreID") String store_id,
                                       @Query("GroupID") String gid ,@Query("VehicleID") String vid,
                                       @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                       @Query("StatusID") String statusid, @Query("SearchID") String searchid);
*/
   /* //UnLikes in Product unlike
    @POST("NewUnlikes")
    Call<String> _autokattaProductViewUnlike(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                             @Query("Layout") String layout, @Query("StoreID") String store_id,
                                             @Query("GroupID") String gid ,@Query("VehicleID") String vid,
                                             @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                             @Query("StatusID") String statusid, @Query("SearchID") String searchid);
*/

    /* //UnLikes in Service unlike
     @POST("NewUnlikes")
     Call<String> _autokattaServiceViewUnlike(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                              @Query("Layout") String layout, @Query("StoreID") String store_id,
                                              @Query("GroupID") String gid ,@Query("VehicleID") String vid,
                                              @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                              @Query("StatusID") String statusid, @Query("SearchID") String searchid);
 */
    //Share data within app
    @POST("NewShare")
    Call<String> _autokattaShareData(@Query("SenderContact") String sender_contact, @Query("ReceiverContact") String receiver_contact,
                                     @Query("GroupID") int group_id, @Query("BroadCastGroupID") int broadcastgroup_id,
                                     @Query("CaptionData") String caption_data, @Query("Layout") int layout,
                                     @Query("ProfileContact") String profile_id, @Query("StoreID") int store_id,
                                     @Query("VehicleID") int vehicle_id, @Query("ProductID") int product_id,
                                     @Query("ServiceID") int service_id, @Query("StatusID") int status_id,
                                     @Query("SearchID") int search_id, @Query("AuctionID") int auction_id,
                                     @Query("LoanID") int loan_id, @Query("ExchangeID") int exchange_id);

    //Search Service...
    @GET("GetServiceSearchData")
    Call<GetServiceSearchResponse> searchService(@Query("SearchKey") String key, @Query("MyContact") String contact);


    //Search Store Data...
    @GET("GetStoreSearchData")
    Call<BrowseStoreResponse> searchStore(@Query("SearchKey") String key, @Query("MyContact") String contact);


   /* //Likes in Otherstore
    @POST("Newlikes")
    Call<String> _autokattaLikeStore(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                     @Query("Layout") String layout, @Query("StoreID") int store_id,
                                     @Query("GroupID") String gid , @Query("VehicleID") String vid,
                                     @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                     @Query("StatusID") String statusid, @Query("SearchID") String searchid);
*/
  /*  //UnLikes in Otherstore
    @POST("NewUnlikes")
    Call<String> _autokattaUnlikeStore(@Query("SenderContact") String myContact, @Query("ReceiverContact") String othercontact,
                                       @Query("Layout") String layout, @Query("StoreID") int store_id,
                                       @Query("GroupID") String gid , @Query("VehicleID") String vid,
                                       @Query("ProductID") String pid, @Query("ServiceID") String sid,
                                       @Query("StatusID") String statusid, @Query("SearchID") String searchid);

*/
  /*  //Follow Otherstore
    @POST("NewFollow")
    Call<String> _autokattaFollowStore(@Query("SenderContact") String senderContact, @Query("ReceiverContact") String receiverContact,
                                       @Query("Layout") String layout, @Query("StoreID") int storeid,
                                       @Query("VehicleID") String vehicleid, @Query("ProductID") String pid, @Query("ServiceID") String servid);
*/
  /*  //Un Follow Otherstore
    @POST("NewUnfollow")
    Call<String> _autokattaUnfollowStore(@Query("SenderContact") String senderContact, @Query("ReceiverContact") String receiverContact,
                                         @Query("Layout") String layout, @Query("StoreID") int storeid,
                                         @Query("VehicleID") String vid, @Query("ProductID") String pid, @Query("ServiceID") String sid
    );
*/

    //send new rating
    @POST("NewRating")
    Call<String> sendNewRating(@Query("Contact") String contact,
                               @Query("StoreID") int store_id,
                               @Query("ProductID") int product_id,
                               @Query("ServiceID") int service_id,
                               @Query("Rate") String rate,
                               @Query("Rate1") String rate1,
                               @Query("Rate2") String rate2,
                               @Query("Rate3") String rate3,
                               @Query("Rate4") String rate4,
                               @Query("Rate5") String rate5,
                               @Query("Type") String type);


    //send updated rating
    @POST("UpdateRatings")
    Call<String> sendupdatedRating(@Query("Contact") String contact,
                                   @Query("StoreID") int store_id,
                                   @Query("ProductID") int product_id,
                                   @Query("ServiceID") int service_id,
                                   @Query("Rate") String rate,
                                   @Query("Rate1") String rate1,
                                   @Query("Rate2") String rate2,
                                   @Query("Rate3") String rate3,
                                   @Query("Rate4") String rate4,
                                   @Query("Rate5") String rate5,
                                   @Query("Type") String type);

    //recommend
    @POST("RecommendStore")
    Call<String> recommendStore(@Query("Contact") String contact, @Query("StoreID") int Store_id);

    //getting Favourite data
    @GET("GetMyFavourites")
    Call<FavouriteResponse> autokatta_getMyFavourites(@Query("Contact") String contact);

    //Create Groups
    @POST("CreateGroup")
    Call<String> createGroup(@Query("Title") String title, @Query("Image") String image, @Query("AdminContact") String admin_contact);

    //Add contacts to  Groups
    @POST("AddContacts")
    Call<String> addContactToGroup(@Query("GroupID") int groupid, @Query("Contacts") String contacts);


  /*  //Notification Like Group
    @POST("Newlikes")
    Call<String> notificationLikegroup(@Query("GroupID") String groupid, @Query("SenderContact") String mycontact, @Query("ReceiverContact") String OtherContact,
                                       @Query("Layout") String layout, @Query("StoreID") String StoreID,
                                       @Query("VehicleID") String VehicleID, @Query("ProductID") String ProductID,
                                       @Query("ServiceID") String ServiceID,@Query("StatusID")
                                               String StatusID,@Query("SearchID") String SearchID);
*/

    //update a store...
    @POST("UpdateStore")
    Call<String> updateStore(@Body UpdateStoreRequest updateStoreRequest);

    //Device Registration...
    @POST("DeviceRegistration")
    Call<String> firebaseToken(@Query("Contact") String contact, @Query("Token") String token);


    //add product into store...
    @POST("AddStoreProduct")
    Call<ProductAddedResponse> addProduct(@Query("StoreID") int store_id,
                                          @Query("ProductName") String product_name,
                                          @Query("Price") String price,
                                          @Query("ProductDetails") String product_details,
                                          @Query("ProductTags") String product_tags,
                                          @Query("ProductType") String product_type,
                                          @Query("Images") String images,
                                          @Query("Category") String category,
                                          @Query("BrandTags") String brandtags,
                                          @Query("GroupID") String group_id);


    //add service into store...
    @POST("AddStoreNewServices")
    Call<ServiceAddedResponse> addService(@Query("StoreID") int store_id,
                                          @Query("ServiceName") String service_name,
                                          @Query("Price") String price,
                                          @Query("ServiceDetails") String service_details,
                                          @Query("ServiceTags") String service_tags,
                                          @Query("ServiceType") String service_type,
                                          @Query("Images") String images,
                                          @Query("Category") String category,
                                          @Query("BrandTags") String brandtags,
                                          @Query("GroupID") String group_id);


    @Multipart
    @POST("upload_product_pics.php")
    Call<String> uploadProductPic(@Part MultipartBody.Part file, @Part("file") RequestBody name);


    @Multipart
    @POST("upload_service_pics.php")
    Call<String> uploadServicePic(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @Multipart
    @POST("savetofile.php")
    Call<String> uploadVehiclePic(@Part MultipartBody.Part file, @Part("file") RequestBody name);


    //get product data
    @GET("GetProductDataById")
    Call<ProductResponse> getProductDetails(@Query("ProductID") int product_id, @Query("MyContact") String mycontact);


    //get Service data
    @GET("GetServiceDataById")
    Call<ServiceResponse> getServiceDetails(@Query("ServiceID") int Serviceid, @Query("MyContact") String mycontact);

    //get Service Mela data
    @GET("GetAllMyServiceMela")
    Call<MyActiveServiceMelaResponse> _autokattaGetServiceMelaDetails(@Query("Contact") String mycontact);

    //get Sale Mela data
    @GET("GetAllMySaleMela")
    Call<MyActiveSaleMelaResponse> _autokattaGetSaleMelaDetails(@Query("Contact") String mycontact);

    //get Ended Sale Mela data
    @GET("GetEndedSaleMela")
    Call<EndedSaleMelaResponse> _autokattaGetEndedSaleMelaDetails(@Query("Contact") String mycontact);

    //get Ended Sale Mela data
    @GET("GetEndedServiceMela")
    Call<EndedSaleMelaResponse> _autokattaGetEndedServiceMelaDetails(@Query("Contact") String mycontact);

    //get Loan Mela Participants data
    @GET("GetConfirmedParticipantsLoan")
    Call<LoanMelaParticipantsResponse> _autokattagetConfirmedParticipants_Loan(@Query("MyContact") String mycontact, @Query("LoanID") String loan_id);

    //get  Sale Mela Participants data
    @GET("GetConfirmedParticipantsSale")
    Call<SaleMelaParticipantsResponse> _autokattagetConfirmedParticipants_Sale(@Query("MyContact") String mycontact, @Query("SaleID") String sale_id);

    //get  Sale Mela analytics data
    @GET("GetSaleAnalyticsCount")
    Call<LoanMelaAnalyticsResponse> _autokattagetanalytics_Sale(@Query("SaleMelaID") String sale_id);

    //get  Service Mela Participants data
    @GET("GetConfirmedParticipantsService")
    Call<ServiceMelaParticipantsResponse> _autokattagetConfirmedParticipants_Service(@Query("MyContact") String mycontact, @Query("ServiceID") String service_id);

    //get  Service Mela analytics data
    @GET("GetServiceAnalyticsCount")
    Call<LoanMelaAnalyticsResponse> _autokattagetServiceAnalytics(@Query("ServiceMelaID") String service_id);

    //get Exchange Mela  Participantsdata
    @GET("GetConfirmedParticipantsExchange")
    Call<ExchangeMelaParticipantsResponse> _autokattagetConfirmedParticipants_Exchange(@Query("MyContact") String mycontact, @Query("ExchangeID") String exchange_id);

    //get Exchange Mela  Analytics
    @GET("GetExchangeAnalyticsCount")
    Call<LoanMelaAnalyticsResponse> _autokattagetExchangeAnalytics(@Query("ExchangeMelaID") String exchange_id);

    //update product details
    @POST("UpdateStoreProduct")
    Call<String> updateProduct(@Query("ProductID") int product_id,
                               @Query("ProductName") String product_name,
                               @Query("Price") String price,
                               @Query("ProductDetails") String product_details,
                               @Query("ProductTags") String product_tags,
                               @Query("ProductType") String product_type,
                               @Query("Images") String images,
                               @Query("Category") String category,
                               @Query("BrandTags") String brandtags);


    //update Service details
    @POST("UpdateStoreService")
    Call<String> updateService(@Query("ServiceID") int service_id,
                               @Query("ServiceName") String service_name,
                               @Query("ServicePrice") String price,
                               @Query("ServiceDetails") String service_details,
                               @Query("ServiceTags") String service_tags,
                               @Query("ServiceType") String service_type,
                               @Query("Images") String images,
                               @Query("Category") String category,
                               @Query("BrandTags") String brandtags);


    //get chat enquiry status
    @GET("GetEnquiryChatStatus")
    Call<String> getChatEnquiryStatus(@Query("Sender") String sender, @Query("Receiver") String receiver,
                                      @Query("ProductID") int product_id,
                                      @Query("ServiceID") int service_id,
                                      @Query("VehicleID") int vehicle_id);


    //get chat enquiry status
    @GET("GetEnquiryCount")
    Call<EnquiryCountResponse> getEnquiryCount(@Query("Sender") String sender,
                                               @Query("ProductID") int product_id,
                                               @Query("ServiceID") int service_id,
                                               @Query("VehicleID") int vehicle_id);

    //Get Manual enquiry
    @GET("AddEnquiryData")
    Call<ManualEnquiryResponse> getManualEnquiry(@Query("MyContact") String myContact, @Query("custName") String custName,
                                                 @Query("custContact") String custContact, @Query("custAddress") String custAddress
            , @Query("custFullAddress") String custFullAddress, @Query("custInventoryType") String custInventoryType,
                                                 @Query("custEnquiryStatus") String custEnquiryStatus, @Query("discussion") String discussion,
                                                 @Query("nextFollowupDate") String nextFollowupDate, @Query("idsList") String idsList);

    //Get Manual enquiry
    @GET("GetEnquiredPersonsData")
    Call<GetPersonDataResponse> getPersonData(@Query("idsList") String id, @Query("Keyword") String keyword);

    //Post Manual enquiry
    @POST("AddEnquiryData")
    Call<AddManualEnquiryResponse> _autokattaAddManualEnquiry(@Query("MyContact") String myContact, @Query("custName") String custName,
                                                              @Query("custContact") String custContact, @Query("custAddress") String custAddress
            , @Query("custFullAddress") String custFullAddress,
                                                              @Query("custInventoryType") String custInventoryType,
                                                              @Query("custEnquiryStatus") String custEnquiryStatus, @Query("discussion") String discussion,
                                                              @Query("nextFollowupDate") String nextFollowupDate, @Query("idsList") String idsList);

    //Get Inventory Data...
    @GET("GetMyInventoryData")
    Call<GetInventoryResponse> getMyInventoryData(@Query("MyContact") String myContact, @Query("Keyword") String keyword);

    //Post Used Vehicle Data
    @POST("UploadUsedVehicle")
    Call<UploadUsedVehicleResponse> _autokattaUploadUsedVehicle(@Body UploadUsedVehicleRequest usedVehicleRequest);
}
