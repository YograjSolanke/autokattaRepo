package autokatta.com.interfaces;

import autokatta.com.response.BlacklistMemberResponse;
import autokatta.com.response.BodyAndSeatResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.ExchangeMelaCreateResponse;
import autokatta.com.response.GetBodyTypeResponse;
import autokatta.com.response.GetBrandModelVersionResponse;
import autokatta.com.response.GetBreaks;
import autokatta.com.response.GetPumpResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetVehicleColor;
import autokatta.com.response.GetVehicleImplementsResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.response.LoanMelaCreateResponse;
import autokatta.com.response.LoginResponse;
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
    Call<CategoryResponse> _autokattaGetCategories();

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
    Call<String> _autokattaGetBrand(@Query("category") String category, @Query("subcategory") String subCategory);

    //Get Model
    @GET("getVehicleModel.php")
    Call<String> _autokattaGetModel(@Query("category") String category, @Query("subcategory") String subCategory,
                                    @Query("brand_id") String brandId);

    //Get Version
    @GET("getVehicleVersion.php")
    Call<String> _autokattaGetVersion(@Query("category") String category, @Query("subcategory") String subCategory,
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

}
