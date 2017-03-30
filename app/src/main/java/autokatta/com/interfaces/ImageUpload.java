package autokatta.com.interfaces;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ak-005 on 23/3/17.
 */

public interface ImageUpload {
    //Upload Profile image
    @Multipart
    @POST("upload_profile_profile_pics.php")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);


    //Upload Loan and Exchange image
    @Multipart
    @POST("upload_loanExchangepics.php")
    Call<ResponseBody> postLoanAndExchangeImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

    //Upload Store image
    @Multipart
    @POST("upload_store_profile.php")
    Call<ResponseBody> postStoreImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);
}
