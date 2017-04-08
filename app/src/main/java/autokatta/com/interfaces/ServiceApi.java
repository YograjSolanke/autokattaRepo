package autokatta.com.interfaces;

import autokatta.com.response.AdminExcelSheetResponse;
import autokatta.com.response.AdminVehiclesResponse;
import autokatta.com.response.AuctionAllVehicleResponse;
import autokatta.com.response.AuctionAnalyticsResponse;
import autokatta.com.response.AuctionCreateResponse;
import autokatta.com.response.AuctionParticipantsResponse;
import autokatta.com.response.AuctionReauctionVehicleResponse;
import autokatta.com.response.BlacklistMemberResponse;
import autokatta.com.response.BodyAndSeatResponse;
import autokatta.com.response.BroadcastReceivedResponse;
import autokatta.com.response.BroadcastSendResponse;
import autokatta.com.response.BrowseStoreResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.CreateStoreResponse;
import autokatta.com.response.CreateUserResponse;
import autokatta.com.response.ExchangeMelaCreateResponse;
import autokatta.com.response.GetAuctionEventResponse;
import autokatta.com.response.GetAutokattaContactResponse;
import autokatta.com.response.GetBodyTypeResponse;
import autokatta.com.response.GetBrandModelVersionResponse;
import autokatta.com.response.GetBreaks;
import autokatta.com.response.GetCompaniesResponse;
import autokatta.com.response.GetContactByCompanyResponse;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetDistrictsResponse;
import autokatta.com.response.GetGroupContactsResponse;
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.response.GetLiveEventsResponse;
import autokatta.com.response.GetMyUploadedVehicleResponse;
import autokatta.com.response.GetPumpResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetRegisteredContactsResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.GetStatesResponse;
import autokatta.com.response.GetStoreProfileInfoResponse;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleByIdResponse;
import autokatta.com.response.GetVehicleColor;
import autokatta.com.response.GetVehicleImplementsResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.response.LoanMelaCreateResponse;
import autokatta.com.response.LoginResponse;
import autokatta.com.response.MyActiveAuctionAboveReservedBidResponse;
import autokatta.com.response.MyActiveAuctionHighBidResponse;
import autokatta.com.response.MyActiveAuctionNoBidResponse;
import autokatta.com.response.MyActiveAuctionResponse;
import autokatta.com.response.MyActiveExchangeMelaResponse;
import autokatta.com.response.MyActiveLoanMelaResponse;
import autokatta.com.response.MyBroadcastGroupsResponse;
import autokatta.com.response.MySavedAuctionResponse;
import autokatta.com.response.MySearchResponse;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.MyUpcomingAuctionResponse;
import autokatta.com.response.MyUpcomingExchangeMelaResponse;
import autokatta.com.response.MyUpcomingLoanMelaResponse;
import autokatta.com.response.MyUploadedVehiclesResponse;
import autokatta.com.response.PriceSuggestionResponse;
import autokatta.com.response.ProfileAboutResponse;
import autokatta.com.response.ProfileGroupResponse;
import autokatta.com.response.SearchStoreResponse;
import autokatta.com.response.SpecialClauseAddResponse;
import autokatta.com.response.SpecialClauseGetResponse;
import autokatta.com.response.StoreOldAdminResponse;
import autokatta.com.response.YourBidResponse;
import autokatta.com.response.getBussinessChatResponse;
import autokatta.com.response.getDealsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ak-001 on 18/3/17.
 */

public interface ServiceApi {

    // Login API...
    @POST("login.php")
    Call<LoginResponse> _autokattaLogin(@Query("contact") String username, @Query("password") String password);

    //Get Profile Data...
    @GET("getProfileData.php")
    Call<ProfileAboutResponse> _autokattaProfileAbout(@Query("contact") String contact);

    /*//Get Profile Data...
    @GET("getProfileData.php")
    Call<ProfileAboutResponse> _autokattaOtherProfile(@Query("contact") String contact,
                                                      @Query("sender_contact") String senderContact);*/

    //Get Groups...
    @GET("getGroups.php")
    Call<ProfileGroupResponse> _autokattaProfileGroup(@Query("contact") String contact);

    //Get Upload Count...
    @GET("getuploadcount.php")
    Call<String> _autokattaGetVehicleCount(@Query("contact") String contact);

    //Get Own Store...
    @GET("getOwnStores.php")
    Call<MyStoreResponse> _autokattaGetMyStoreList(@Query("contact") String contact);

    //Get Vehicle List...
    @GET("getVehicleType.php")
    Call<GetVehicleListResponse> _autokattaGetVehicleList();

    //Get Category...
    @GET("getModule.php")
    Call<CategoryResponse> _autokattaGetCategories(@Query("type") String type);

    //Forgot Password
    @POST("getContactForgotPass.php")
    Call<String> _autokattaForgotPassword(@Query("contact") String contact);

    //SearchStore Result
    @POST("getStoreByContact.php")
    Call<SearchStoreResponse> _autokattaGetSearchStore(@Query("mycontact") String myContact, @Query("storecontact") String storecontact,
                                                       @Query("location") String location, @Query("category") String category,
                                                       @Query("phrase") String phrase, @Query("radius") String radius);


    //Registered Contact Validation
    @POST("registrationValidation.php")
    Call<String> regContactValidation(@Query("contact") String contact);


    //add other category
    @POST("add_module.php")
    Call<String> addOtherCategory(@Query("title") String contact);


    // get Industries
    @POST("getRegisteredIndustries.php")
    Call<IndustryResponse> _getindustry();

    //add other Industry
    @POST("add_other_industry.php")
    Call<String> addOtherIndustry(@Query("newIndustry") String contact);

    // get OTP
    @POST("otp.php")
    Call<String> _autokattagetOTP(@Query("number") String contact);


    // After OTP Registration
    @POST("registration1.php")
    Call<String> _autokattaAfterOtpRegistration(@Query("username") String username, @Query("contact") String contact, @Query("email") String email,
                                                @Query("dob") String dob, @Query("gender") String gender, @Query("pincode") String pincode,
                                                @Query("city") String city, @Query("profession") String profession, @Query("password") String password,
                                                @Query("sub_profession") String sub_profession, @Query("industry") String industry);

    //get My Search
    @POST("getMyVehicleSearch.php")
    Call<MySearchResponse> _autokattaGetMySearch(@Query("contact") String myContact);

    //New Password
    @POST("UpdateForgotPass.php")
    Call<String> _autokattanewpassword(@Query("contact") String contact, @Query("newPass") String newPass);

    //get My Uploaded vehicles
    @POST("getUploadedvehicles.php")
    Call<MyUploadedVehiclesResponse> _autokattaGetMyUploadedVehicles(@Query("mycontact") String myContact);

    //get My Active Events
    @POST("getAuctionEvents.php")
    Call<MyActiveAuctionResponse> _autokattaGetMyActiveAuction(@Query("contact") String myContact, @Query("status") String status);

    //get My Active Loan Mela
    @POST("getAllMyloanMela.php")
    Call<MyActiveLoanMelaResponse> _autokattaGetMyActiveLoanMela(@Query("contact") String myContact);

    //get My Active Exchange Mela
    @POST("getAllMyExchangeMela.php")
    Call<MyActiveExchangeMelaResponse> _autokattaGetMyActiveExchangeMela(@Query("contact") String myContact);

    //get My Upcoming Auction
    @POST("getMyUpcomingAuction.php")
    Call<MyUpcomingAuctionResponse> __autokattaGetMyUpcomingAuction(@Query("contact") String myContact);

    //get My Upcoming Loan Mela
    @POST("getUpcomingLoanMela.php")
    Call<MyUpcomingLoanMelaResponse> __autokattaGetMyUpcomingLoanMela(@Query("contact") String myContact);

    //get My Upcoming Loan Mela
    @POST("getUpcomingExchangeMela.php")
    Call<MyUpcomingExchangeMelaResponse> __autokattaGetMyUpcomingExchangeMela(@Query("contact") String myContact);

    //get saved Auctions
    @POST("getMySavedAuction.php")
    Call<MySavedAuctionResponse> _autokattaMySavedAuctions(@Query("contact") String myContact);

    //Create Group
    @POST("createGroup.php")
    Call<String> _autokattaCreateGroup(@Query("title") String title, @Query("image") String image, @Query("admin_contact") String contact);

    //get Vehicle Sub Types...
    @GET("getVehicleSubType.php")
    Call<GetVehicleSubTypeResponse> _autokattaGetVehicleSubType(@Query("vehicle_id") String vehicleId);

    //get Blacklisted contacts
    @GET("getMyBlacklistedContact.php")
    Call<BlacklistMemberResponse> _autokattaBlacklistMembers(@Query("contact") String contact);

    //Update Registration
    @POST("updateRegistrationInfo.php")
    Call<String> _autokattaUpdateRegistration(@Query("Regid") String Regid, @Query("page") String page, @Query("profileImage") String profileImage,
                                              @Query("about") String about,
                                              @Query("website") String website);

    /*
    Add Brand Model Version...
     */
    //Add Brand
    @POST("addYourOptions.php")
    Call<String> _autokattaAddBrand(@Query("keyword") String keyword, @Query("title") String title,
                                    @Query("category_id") String categoryId, @Query("subcat_id") String subCatID);

    //Add Model
    @POST("addYourOptions.php")
    Call<String> _autokattaAddModel(@Query("keyword") String keyword, @Query("title") String title,
                                    @Query("category_id") String categoryId, @Query("subcat_id") String subCatID,
                                    @Query("brand_id") String brandId);

    //Add Version
    @POST("addYourOptions.php")
    Call<String> _autokattaAddVersion(@Query("keyword") String keyword, @Query("title") String title,
                                      @Query("category_id") String categoryId, @Query("subcat_id") String subCatID,
                                      @Query("brand_id") String brandId, @Query("model_id") String modleId);

    //Add Break..
    @POST("post_other_brake.php")
    Call<String> _autokattaAddBreaks(@Query("otherBrake") String otherBreaks);

    //Add Pump...
    @POST("post_other_pump.php")
    Call<String> _autokattaAddPump(@Query("otherPump") String otherPump);

    //addBodyAndSeatManufacturers
    @POST("addBodyAndSeatManufacturers.php")
    Call<String> _autokattaAddBodyAndSeatManufacturers(@Query("bodyManufacturerName") String bodyManufactureName,
                                                       @Query("seatManufacturerName") String seatManufacture);

    // Add Body Type
    @POST("addYourOptions.php")
    Call<String> _autokattaAddBodyType(@Query("keyword") String keyword, @Query("title") String title);

    /*
    Get Data...
     */

    //Get Brand
    @GET("getVehicleBrand.php")
    Call<GetVehicleBrandResponse> _autokattaGetBrand(@Query("category") String category, @Query("subcategory") String subCategory);

    //Get Model
    @GET("getVehicleModel.php")
    Call<GetVehicleModelResponse> _autokattaGetModel(@Query("category") String category, @Query("subcategory") String subCategory,
                                                     @Query("brand_id") String brandId);

    //Get Version
    @GET("getVehicleVersion.php")
    Call<GetVehicleVersionResponse> _autokattaGetVersion(@Query("category") String category, @Query("subcategory") String subCategory,
                                                         @Query("brand_id") String brandId, @Query("model_id") String modelId);

    //Get Breaks
    @GET("getBrakes.php")
    Call<GetBreaks> _autokattaGetBreaks();

    //Get getPumps
    @GET("getPump.php")
    Call<GetPumpResponse> _autokattaGetPumps();

    /*
    GetRTOCity
     */
    @GET("getVehicleRTOCity.php")
    Call<GetRTOCityResponse> _autokattaGetVehicleRTOCity();

    //Get Body and Seat Manufacture
    @GET("getBodyAndSeatManufacturers.php")
    Call<BodyAndSeatResponse> _autokattaGetBodyAndSeatManufacture();

    //Get Body Type
    @GET("getBodytype.php")
    Call<GetBodyTypeResponse> _autokattaGetBodyType();

    //Get Vehicle Color
    @GET("getVehicleColor.php")
    Call<GetVehicleColor> _autokattaGetColor();

    //Get Vehicle Implements...
    @GET("getVehicleImplements.php")
    Call<GetVehicleImplementsResponse> _autokattaGetVehicleImplements();

    //create loan mela event
    @GET("createLoanMela.php")
    Call<LoanMelaCreateResponse> _createLoanMela(@Query("title") String title, @Query("location") String location,
                                                 @Query("address") String address, @Query("start_date") String start_date,
                                                 @Query("start_time") String start_time, @Query("end_date") String end_date,
                                                 @Query("end_time") String end_time, @Query("image") String image,
                                                 @Query("details") String details, @Query("contact") String contact);


    //create loan mela event
    @GET("createExchangeMela.php")
    Call<ExchangeMelaCreateResponse> _createExchangeMela(@Query("title") String title, @Query("location") String location,
                                                         @Query("address") String address, @Query("start_date") String start_date,
                                                         @Query("start_time") String start_time, @Query("end_date") String end_date,
                                                         @Query("end_time") String end_time, @Query("image") String image,
                                                         @Query("details") String details, @Query("contact") String contact);


    //get My Broadcast groups
    @POST("getBroadcastGroups.php")
    Call<MyBroadcastGroupsResponse> _autokattaGetBroadcastGroups(@Query("owner") String myContact);


    //Upload Vehicle
    @POST("updateMyVehicle.php")
    Call<String> _autokattaUploadVehicle(@Query("id") String id, @Query("vehicle_no") String vehicle_no,
                                         @Query("vehicle_type") String vehicle_type, @Query("subcategory") String subcategory,
                                         @Query("model_no") String model_no, @Query("brand") String brand,
                                         @Query("version") String version, @Query("year") String year,
                                         @Query("tax_validity") String tax_validity, @Query("fitness_validity") String fitness_validity,
                                         @Query("permit_validity") String permit_validity, @Query("insurance") String insurance,
                                         @Query("PUC") String PUC, @Query("last_service_date") String last_service_date,
                                         @Query("next_service_date") String next_service_date);


    //ADD own Vehicle
    @POST("addOwnVehicles.php")
    Call<String> _autokattaAddOwn(@Query("contact") String contact, @Query("vehicle_no") String vehicle_no,
                                  @Query("vehicle_type") String vehicle_type, @Query("subcategory") String subcategory,
                                  @Query("model_no") String model_no, @Query("brand") String brand,
                                  @Query("version") String version, @Query("year") String year,
                                  @Query("tax_validity") String tax_validity, @Query("fitness_validity") String fitness_validity,
                                  @Query("permit_validity") String permit_validity, @Query("insurance") String insurance,
                                  @Query("PUC") String PUC, @Query("last_service_date") String last_service_date, @Query("next_service_date") String next_service_date);


    //Get Brand Modle Version
    @POST("getbrand_model_version.php")
    Call<GetBrandModelVersionResponse> _autokattaGetBrandModelVersion(@Query("sub_category_id") String sub_category_id);

    //create loan mela event
    @GET("getPriceSuggestion.php")
    Call<PriceSuggestionResponse> _autokattaGetPriceSuggestion(@Query("CategoryId") String categoryId, @Query("SubCategoryId") String subCategoryId,
                                                               @Query("BrandID") String brandId, @Query("ModelID") String modelId,
                                                               @Query("VersionId") String versionId, @Query("ManufactureYear") String mfgYear,
                                                               @Query("RTOCity") String rtoCity);

    //Get Group Vehicles
    @GET("getGroupVehicles.php")
    Call<GetGroupVehiclesResponse> _autokattaGetGroupVehicles(@Query("group_id") String groupId, @Query("brand") String brand,
                                                              @Query("model") String model, @Query("version") String version,
                                                              @Query("city") String city, @Query("RTOcity") String rtoCity,
                                                              @Query("price") String price, @Query("reg_year") String regYear,
                                                              @Query("mgf_year") String mgfYear, @Query("kms") String kms,
                                                              @Query("owners") String owners);

    //Get My Uploaded Vehicle...
    @GET("getMyUploadedVehicles.php")
    Call<GetMyUploadedVehicleResponse> _autokattaMyUploadedVehicles(@Query("contact") String contact);


    //Get SpecialCaluses For Auction
    @GET("specialclauses.php")
    Call<SpecialClauseGetResponse> getSpecialClauses(@Query("keyword") String keyword);


    //Add SpecialCaluses For Auction
    @GET("specialclauses.php")
    Call<SpecialClauseAddResponse> addSpecialClauses(@Query("keyword") String keyword, @Query("clause") String clause);

    //Get Group Contacts...
    @GET("getGroupContacts.php")
    Call<GetGroupContactsResponse> _autokattaGetGroupContacts(@Query("group_id") String groupId);


    //Get Group Vehicles
    @GET("createAuction.php")
    Call<AuctionCreateResponse> createAuction(@Query("title") String title, @Query("start_date") String start_date,
                                              @Query("start_time") String start_time, @Query("end_date") String end_date,
                                              @Query("end_time") String end_time, @Query("auction_type") String auction_type,
                                              @Query("contact") String contact, @Query("location") String location,
                                              @Query("product_category") String product_category, @Query("special_clauses") String special_clauses,
                                              @Query("openClose") String openClose);


    //Update Company Based Registration
    @POST("updateRegistrationInfo.php")
    Call<String> _autokattaUpdateCompanyRegistration(@Query("Regid") String Regid, @Query("page") String page, @Query("area") String area,
                                                     @Query("bykms") String bykms,
                                                     @Query("bydistrict") String bydistrict
            , @Query("bystate") String bystate, @Query("company") String company,
                                                     @Query("designation") String designation, @Query("skills") String skills, @Query("deals") String deals);

    //Get States
    @GET("getRegisteredStates.php")
    Call<GetStatesResponse> _autokattaGetStates();

    //Get Districts
    @GET("getRegisteredDistricts.php")
    Call<GetDistrictsResponse> _autokattaGetDistricts();

    //Get New Designation
    @GET("add_other_designations.php")
    Call<String> _autokattaAddNewDesignation(@Query("designationName") String designationName);

    //Add New Company
    @GET("add_other_companyNames.php")
    Call<String> _autokattaAddNewCompany(@Query("companyName") String companyName);

    //Add New Deal
    @GET("add_other_dealing.php")
    Call<String> _autokattaAddNewDeal(@Query("deals") String deals);

    //Add New Skill
    @GET("add_other_skills.php")
    Call<String> _autokattaAddNewSkills(@Query("skill") String deals);

    //Get deals
    @GET("getRegisteredDeals.php")
    Call<getDealsResponse> _autokattaGetDeals();

    //Get  Skills
    @GET("getRegisteredSkills.php")
    Call<GetSkillsResponse> _autokattaGetSkills();

    //Get  Designation
    @GET("getRegisteredDesignation.php")
    Call<GetDesignationResponse> _autokattaGetDesignation();


    //Get  Company
    @GET("getRegisteredCompanies.php")
    Call<GetCompaniesResponse> _autokattaGetCompany();


    //get  Ended Auction Events
    @POST("getEndedAuction.php")
    Call<MyActiveAuctionResponse> getEndedAuctions(@Query("contact") String myContact);


    //get Ended Loan Mela
    @POST("getEndedLoanMela.php")
    Call<MyActiveLoanMelaResponse> getEndedLoanMela(@Query("contact") String myContact);


    //get Ended Exchange Mela
    @POST("getEndedExchangeMela.php")
    Call<MyActiveExchangeMelaResponse> getEndedExchangeMela(@Query("contact") String myContact);

    //set vehicle privacy
    @POST("vehicle_group_store_ref.php")
    Call<String> _autokattaSetVehiclePrivacy(@Query("contact") String myContact, @Query("vehicle_id") String vehicleid,
                                             @Query("group_ids") String groupIds, @Query("store_ids") String storeIds);

    //Get Vehicle By Id...
    @POST("getVehicleById.php")
    Call<GetVehicleByIdResponse> _autokattaGetVehicleById(@Query("vehicle_id") String vehicleId);

    //Delete a store...
    @POST("deleteMyStore.php")
    Call<String> _autokattaDeleteStore(@Query("store_id") String storeId, @Query("keyword") String keyword);

    //Create a store...
    @POST("createStore.php")
    Call<CreateStoreResponse> _autokattaCreatetore(@Query("store_name") String name, @Query("contact_no") String contact,
                                                   @Query("location") String location, @Query("website") String website,
                                                   @Query("store_type") String storetype, @Query("store_image") String lastWord,
                                                   @Query("workingdays") String workdays, @Query("store_open_time") String open,
                                                   @Query("store_close_time") String close, @Query("category") String category,
                                                   @Query("address") String address, @Query("coverImage") String coverlastWord,
                                                   @Query("storeDescription") String storeDescription);

    //Get Store Admins...
    @POST("getStoreAdmin.php")
    Call<StoreOldAdminResponse> _autokattaGetStoreAdmin(@Query("store_id") String store_id);

    //Add new Store Admins...
    @POST("addStoreAdmin.php")
    Call<String> _autokattaAddNewStoreAdmin(@Query("store_id") String store_id, @Query("adminContact") String admins);

    //get Contact By Company
    @POST("getContactsBasedOnCompany.php")
    Call<GetContactByCompanyResponse> _autokattaGetContactByCompany(@Query("page") String page, @Query("mycontact") String contact);

    //Get Store Profile Info...
    @POST("getStoreProfileInfo.php")
    Call<GetStoreProfileInfoResponse> _autokattaGetProfileInfo(@Query("contact") String contact);

    //Get My Autokatta Contacts...
    @POST("getAutokattaContact.php")
    Call<GetAutokattaContactResponse> getAutokattaContact(@Query("mycontact") String contact, @Query("numberstring") String number,
                                                          @Query("namestring") String name);

    //Get My Registered Contacts...
    @POST("getAllContactResistered.php")
    Call<GetRegisteredContactsResponse> _autokattaGetRegisteredContact();

    //create User.
    @POST("createDefaultUser.php")
    Call<CreateUserResponse> _autokattaCreateUser(@Query("username") String username, @Query("contact") String contact);

    //Follow
    @POST("newfollow.php")
    Call<String> _autokattaFollow(@Query("sender_contact") String senderContact, @Query("receiver_contact") String receiverContact,
                                  @Query("layout") String layout);

    //Un Follow
    @POST("newUnfollow.php")
    Call<String> _autokattaUnfollow(@Query("sender_contact") String senderContact, @Query("receiver_contact") String receiverContact,
                                    @Query("layout") String layout);


    //remove Contact From blacklist
    @POST("addRemoveToBlacklist.php")
    Call<String> removeContactFromBlacklist(@Query("mycontact") String mycontact, @Query("contact") String contact,
                                            @Query("keyword") String keyword);


    //delete uploaded vehicle
    @POST("deleteMyUploadedVehicles.php")
    Call<String> deleteUploadedVehicles(@Query("vehicle_id") String vehicle_id, @Query("keyword") String keyword);


    //create Excel sheet names from admin
    @POST("getMyExcelSheetName.php")
    Call<AdminExcelSheetResponse> _autokattaGetAdminExcelSheetNames(@Query("contact") String contact);

    //get Admin vehicles
    @POST("getVehiclesFromAdmin.php")
    Call<AdminVehiclesResponse> _autokattaGetAdminVehicles(@Query("contact") String contact, @Query("filename") String filename,
                                                           @Query("userid") String userid);

    //get All vehicles for Auction
    @POST("getUploadedAndReauctionVehiclesForAuction.php")
    Call<AuctionAllVehicleResponse> _autokattaGetAuctionAllVehicles(@Query("contact") String contact);

    //get All vehicles for Auction
    @POST("getReauctionVehicleByNameAndContact.php")
    Call<AuctionReauctionVehicleResponse> _autokattaGetReauctionedVehicle(@Query("contact") String contact,
                                                                          @Query("auctionID") String auctionID);


    //send notification of upload vehicle
    @POST("uploaded_vehicle_notification.php")
    Call<String> sendNotificationOfUploadedVehicle(@Query("vehicle_id") String vehicle_id, @Query("keyword") String keyword);


    //Edit Group
    @GET("updateGroupProfile.php")
    Call<String> editGroup(@Query("groupname") String groupname, @Query("group_id") String group_id, @Query("profile") String profile);

    //Delete Group
    @GET("deleteMyGroups.php")
    Call<String> deleteGroup(@Query("group_id") String group_id, @Query("keyword") String keyword, @Query("contact") String contact);

    //All Live Events
    @GET("getAllLiveEvents.php")
    Call<GetLiveEventsResponse> getLiveEvents(@Query("contact") String userName);

    //All Live Loan Events...


    //delete my search item
    @GET("deleteUpdateMysearch.php")
    Call<String> deleteMySearch(@Query("search_id") String search_id, @Query("keyword") String keyword);

    //Update Auction
    @GET("UpdateAuctionCreation.php")
    Call<String> _autokattaUpdateAuctionCreation(@Query("auction_id") String auction_id, @Query("title") String title,
                                                 @Query("start_date") String start_date, @Query("start_time") String start_time,
                                                 @Query("end_date") String end_date, @Query("end_time") String end_time,
                                                 @Query("special_clauses") String special_clauses, @Query("vehicle_ids") String vehicle_ids,
                                                 @Query("status") String status, @Query("ShowHide") String ShowHide,
                                                 @Query("NoVehicle") String NoVehicle);

    //Addstart and reserved price
    @GET("addStartReservedPrice.php")
    Call<String> _autokattaAddStart_ReservedPrice(@Query("auction_id") String auctionId, @Query("vehicle_id") String vehicleId,
                                                  @Query("startPrice") String startPrice, @Query("reservedPrice") String reservedPrice);

    //Send Auction mail...
    @GET("email_v.php")
    Call<String> _autokattaSendAuctionMail(@Query("contact") String myContact, @Query("auction_id") String strAuctionId);

    //get Auction Participants
    @POST("getAuctionConfirmedparticipants.php")
    Call<AuctionParticipantsResponse> _autokattaGetAuctionParticipants(@Query("mycontact") String myContact, @Query("auction_id")
            String strAuctionId);

    //Add/remove blacklist contact
    @POST("addRemoveToBlacklist.php")
    Call<String> _autokattaAddRemoveBlacklist(@Query("mycontact") String myContact, @Query("auction_id") String strAuctionId,
                                              @Query("contact") String rContact, @Query("keyword") String keyword);

    // Get Auction Analytics
    @POST("get_analytics_count.php")
    Call<AuctionAnalyticsResponse> _autokattaGetAuctionAnalytics(@Query("auctionid") String strAuctionId);

    //get Active Auction high bid
    @POST("auctionHighestBidding.php")
    Call<MyActiveAuctionHighBidResponse> _autokattaGetActiveAuctionHighBid(@Query("contact") String myContact,
                                                                           @Query("auctionid") String mAuctionId);

    //get Active Auction Above reserved price bid
    @POST("auctionReservedPrice.php")
    Call<MyActiveAuctionAboveReservedBidResponse> _autokattaGetActiveAuctionAboveReservedPrice(@Query("contact") String myContact,
                                                                                               @Query("auctionid") String mAuctionId);

    //get Active Auction No bid
    @POST("auctionNoBidding.php")
    Call<MyActiveAuctionNoBidResponse> _autokattaGetActiveAuctionNoBid(@Query("auctionid") String mAuctionId);

    //get Browse store data
    @GET("getBrowseStores.php")
    Call<BrowseStoreResponse> getBrowseStores(@Query("yourcontact") String yourcontact, @Query("keyword") String keyword);

    //Get Auction Preview By Id...
    @GET("getAuctionEvent_Details.php")
    Call<GetAuctionEventResponse> getAuctionEvent(@Query("auction_id") String auctionId);

    // Create  BroadCast Group

    @POST("createBroadcastGroups.php")
    Call<String> createBroadcastGroup(@Query("title") String title, @Query("owner") String owner, @Query("members") String members
            , @Query("keyword") String keyword);


    // Delete BroadCast Group

    @POST("createBroadcastGroups.php")
    Call<String> deleteBroadcastGroup(@Query("keyword") String keyword, @Query("group_id") String groupid);

    // Update BroadCast Group
    @POST("createBroadcastGroups.php")
    Call<String> updateBroadcastGroup(@Query("title") String title, @Query("owner") String owner, @Query("members") String members
            , @Query("keyword") String keyword, @Query("group_id") String groupid);

    //Get Your Bid Response
    @GET("userYourBid.php")
    Call<YourBidResponse> getYourBid(@Query("auctionId") String id, @Query("userContactNo") String contact);

    //Get Highest Bid Response
    @GET("userHighestBid.php")
    Call<YourBidResponse> getHighestBid(@Query("auctionId") String id, @Query("userContactNo") String contact);

    //send broadcast message
    @GET("sendBroadcastMessage.php")
    Call<String> broadCastGroupMessage(@Query("grp_id") String groupid, @Query("msgText") String msgText, @Query("msgImage") String lastword);

    @POST("addMyBids.php")
    Call<String> addMyBid(@Query("auction_id") String auctionId, @Query("vehicle_id") String vehicleID,
                          @Query("bid_amount") String bidAmount, @Query("tabNo") String tabNo, @Query("mycontact") String contact);


    //get broadcast recievers
    @GET("getReplySenders.php")
    Call<BroadcastReceivedResponse> getBroadcastReceivers(@Query("myContact") String myContact, @Query("product_id") String product_id,
                                                          @Query("service_id") String service_id, @Query("vehicle_id") String vehicle_id);


    //Get  broadcast senders
    @GET("getMySenders.php")
    Call<BroadcastSendResponse> getBroadcastSenders(@Query("myContact") String myContact);


    @POST("getMyChatDetails.php")
    Call<getBussinessChatResponse> getBussinessChat(@Query("mycontact") String contact);

}
