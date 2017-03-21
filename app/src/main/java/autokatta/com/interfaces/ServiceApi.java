package autokatta.com.interfaces;

import autokatta.com.response.AfterOtpRegistrationResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.response.LoginResponse;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.OTPResponse;
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
    Call<SearchStoreResponse> _getSearchStore(@Query("mycontact") String myContact, @Query("storecontact") String storecontact,
                                              @Query("location") String location, @Query("category") String category,
                                              @Query("phrase") String phrase, @Query("radius") String radius);


    //Registered Contact Validation
    @POST("registrationValidation.php")
    Call<String> regContactValidation(@Query("contact") String contact);

    // get OTP
    @POST("otp.php")
    Call<OTPResponse> _autokattagetOTP(@Query("number") String contact);


    // After OTP Registration
    @POST("registration1.php")
    Call<AfterOtpRegistrationResponse> _autokattaAfterOtpRegistration(@Query("username") String username, @Query("number") String contact, @Query("email") String email,
                                                                      @Query("dob") String dob, @Query("gender") String gender, @Query("pincode") String pincode,
                                                                      @Query("city") String city, @Query("profession") String profession, @Query("password") String password,
                                                                      @Query("sub_profession") String sub_profession, @Query("industry") String industry);

    // get Industries
    @POST("getRegisteredIndustries.php")
    Call<IndustryResponse> _getindustry();
}
