package autokatta.com.interfaces;

import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.ProfileAboutResponse;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ak-001 on 18/3/17.
 */

public interface ServiceApi {

    @GET("getProfileData.php")
    Call<ProfileAboutResponse> _autokattaProfileAbout(@Query("contact") String contact);

    @GET("getGroups.php")
    Call<ProfileGroupResponse> _autokattaProfileGroup(@Query("contact") String contact);

    @GET("getuploadcount.php")
    Call<String> _autokattaGetVehicleCount(@Query("contact") String contact);

    @GET("getOwnStores.php")
    Call<MyStoreResponse> _autokattaGetMyStoreList(@Query("contact") String contact);
}
