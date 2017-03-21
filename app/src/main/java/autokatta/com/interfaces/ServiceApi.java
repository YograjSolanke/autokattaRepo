package autokatta.com.interfaces;

import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.LoginResponse;
import autokatta.com.response.MyStoreResponse;
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
}
