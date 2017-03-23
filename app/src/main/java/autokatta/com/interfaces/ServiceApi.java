package autokatta.com.interfaces;

import autokatta.com.response.BlacklistMemberResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.response.LoginResponse;
import autokatta.com.response.MyActiveAuctionResponse;
import autokatta.com.response.MyActiveExchangeMelaResponse;
import autokatta.com.response.MyActiveLoanMelaResponse;
import autokatta.com.response.MySavedAuctionResponse;
import autokatta.com.response.MySearchResponse;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.MyUploadedVehiclesResponse;
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

    // get Industries
    @POST("getRegisteredIndustries.php")
    Call<IndustryResponse> _getindustry();

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
    Call<String>_autokattaUpdateRegistration(@Query("Regid") String Regid,@Query("page") String page,@Query("profileImage") String profileImage,
                                             @Query("about") String about,
                                             @Query("website") String website);


}
