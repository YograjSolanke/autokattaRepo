package autokatta.com.apicall;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
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
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ak-001 on 18/3/17.
 */

public class ApiCall {
    private Activity mContext;
    private Context context;
    private RequestNotifier mNotifier;
    private ConnectionDetector mConnectionDetector;


    public ApiCall(Activity mContext, RequestNotifier mNotifier) {
        this.mContext = mContext;
        this.mNotifier = mNotifier;
        mConnectionDetector = new ConnectionDetector(mContext);

    }

    public ApiCall(Context mContext, RequestNotifier mNotifier) {
        this.context = mContext;
        this.mNotifier = mNotifier;
    }

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://autokatta.acquiscent.com/api/AutoKattaWebService/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(initLogs().build())
                .build();
    }

    /*
    Wall Notifications...
     */

    public void wallNotifications(String contact, String userContact, String layout) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<WallResponse> mLoginCall = mServiceApi._getWallNotifications(contact, userContact, layout);
                mLoginCall.enqueue(new Callback<WallResponse>() {
                    @Override
                    public void onResponse(Call<WallResponse> call, Response<WallResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<WallResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Login Api Call
     */
    public void userLogin(String userName, String password) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<LoginResponse> mLoginCall = mServiceApi._autokattaLogin(userName, password);
                mLoginCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Profile Data Of Both Other And Self...
     */

    public void profileAbout(String myContact, String othercontact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<ProfileAboutResponse> mProfileAboutCall = mServiceApi._autokattaGetProfile(myContact, othercontact);
                mProfileAboutCall.enqueue(new Callback<ProfileAboutResponse>() {
                    @Override
                    public void onResponse(Call<ProfileAboutResponse> call, Response<ProfileAboutResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ProfileAboutResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Profile Data Group...
     */

    public void profileGroup(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<ProfileGroupResponse> mProfileGroupCall = mServiceApi._autokattaProfileGroup(contact);
                mProfileGroupCall.enqueue(new Callback<ProfileGroupResponse>() {
                    @Override
                    public void onResponse(Call<ProfileGroupResponse> call, Response<ProfileGroupResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ProfileGroupResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getVehicleCount(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mProfileGroupCall = mServiceApi._autokattaGetVehicleCount(contact);
                mProfileGroupCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Vehicle List...
     */

    public void getVehicleList() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetVehicleListResponse> mGetVehicleListResponseCall = mServiceApi._autokattaGetVehicleList();
                mGetVehicleListResponseCall.enqueue(new Callback<GetVehicleListResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleListResponse> call, Response<GetVehicleListResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleListResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
    getGroups
     */

    public void Groups(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ProfileGroupResponse> groupResponseCall = serviceApi._autokattaProfileGroup(contact);
                groupResponseCall.enqueue(new Callback<ProfileGroupResponse>() {
                    @Override
                    public void onResponse(Call<ProfileGroupResponse> call, Response<ProfileGroupResponse> response) {
                        Log.i("Response", "Groups->" + response);
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ProfileGroupResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      /*
        getMyStores
     */

    public void MyStoreList(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyStoreResponse> storeResponseCall = serviceApi._autokattaGetMyStoreList(contact);
                storeResponseCall.enqueue(new Callback<MyStoreResponse>() {
                    @Override
                    public void onResponse(Call<MyStoreResponse> call, Response<MyStoreResponse> response) {
                        Log.i("Response", "Store->" + response);
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyStoreResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    getCategories
     */

    public void Categories(String type) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<CategoryResponse> categoryResponseCall = serviceApi._autokattaGetCategories(type);
                categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                        Log.i("loo", "" + response);
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    Forget Password
     */

    public void forgetPassword(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> forgetPasswordResponseCall = serviceApi._autokattaForgotPassword(contact);
                forgetPasswordResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
        Search Store
     */

    public void SearchStore(String myContact, String storecontact, String location, String finalCategory, String phrase, String radius, String brands) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SearchStoreResponse> searchStoreResponseCall = serviceApi._autokattaGetSearchStore(myContact, storecontact, location,
                        finalCategory, phrase, radius, brands);
                searchStoreResponseCall.enqueue(new Callback<SearchStoreResponse>() {
                    @Override
                    public void onResponse(Call<SearchStoreResponse> call, Response<SearchStoreResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SearchStoreResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void registrationContactValidation(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> forgetPasswordResponseCall = serviceApi.regContactValidation(contact);
                forgetPasswordResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //add other Category in database
    public void addOtherCategory(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> forgetPasswordResponseCall = serviceApi.addOtherCategory(contact);
                forgetPasswordResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //add other Industry in database
    public void addOtherIndustry(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> forgetPasswordResponseCall = serviceApi.addOtherIndustry(contact);
                forgetPasswordResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
        Get OTP
     */

    public void getOTP(String contact) {
        try {

            //JSON to Gson conversion
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> otpResponseCall = serviceApi._autokattagetOTP(contact);
                otpResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i("Response", "OTP->" + response);
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



      /*
        Registration after getting OTP
     */

    public void registrationAfterOtp(String username, String contact, String email, String dob, String gender, String pincode, String city, String profession, String password, String sub_profession, String industry) {
        try {
            //JSON to Gson conversion
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                RegistrationRequest registrationRequest = new RegistrationRequest(username, contact, email, dob, gender, city, profession, password, sub_profession, industry);

                Call<String> afterOtpRegistrationResponseCall = serviceApi._autokattaAfterOtpRegistration(registrationRequest);
                afterOtpRegistrationResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i("Response", "Registration->" + response);
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    getIndustries
     */

    public void Industries() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<IndustryResponse> industryResponseCall = serviceApi._getindustry();
                industryResponseCall.enqueue(new Callback<IndustryResponse>() {
                    @Override
                    public void onResponse(Call<IndustryResponse> call, Response<IndustryResponse> response) {
                        Log.i("loo", "" + response);
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<IndustryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        MySearch Result
     */

    public void MySearchResult(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MySearchResponse> mySearchResponseCall = serviceApi._autokattaGetMySearch(myContact);
                mySearchResponseCall.enqueue(new Callback<MySearchResponse>() {
                    @Override
                    public void onResponse(Call<MySearchResponse> call, Response<MySearchResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MySearchResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
        New Password
     */

    public void newPassword(String contact, String newpassword) {
        try {

            //JSON to Gson conversion
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> newPasswordResponseCall = serviceApi._autokattanewpassword(contact, newpassword);
                newPasswordResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        MyUploaded Vehicles
     */

    public void MyUploadedVehicles(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyUploadedVehiclesResponse> myUploadedVehiclesResponseCall = serviceApi._autokattaGetMyUploadedVehicles(myContact);
                myUploadedVehiclesResponseCall.enqueue(new Callback<MyUploadedVehiclesResponse>() {
                    @Override
                    public void onResponse(Call<MyUploadedVehiclesResponse> call, Response<MyUploadedVehiclesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyUploadedVehiclesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
        MyActive Auction
     */

    public void MyActiveAuction(String myContact, String status) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveAuctionResponse> myActiveAuctionResponseCall = serviceApi._autokattaGetMyActiveAuction(myContact, status, 0);
                myActiveAuctionResponseCall.enqueue(new Callback<MyActiveAuctionResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveAuctionResponse> call, Response<MyActiveAuctionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveAuctionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        MyActive Loan Mela
     */

    public void MyActiveLoanMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveLoanMelaResponse> myActiveLoanMela = serviceApi._autokattaGetMyActiveLoanMela(myContact);
                myActiveLoanMela.enqueue(new Callback<MyActiveLoanMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveLoanMelaResponse> call, Response<MyActiveLoanMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveLoanMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        MyActive Exchange Mela
     */

    public void MyActiveExchangeMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveExchangeMelaResponse> myActiveLoanMela = serviceApi._autokattaGetMyActiveExchangeMela(myContact);
                myActiveLoanMela.enqueue(new Callback<MyActiveExchangeMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveExchangeMelaResponse> call, Response<MyActiveExchangeMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveExchangeMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        My Upcoming Auction
     */

    public void MyUpcomingAuction(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyUpcomingAuctionResponse> myUpcomingAuctionResponseCall = serviceApi.__autokattaGetMyUpcomingAuction(myContact);
                myUpcomingAuctionResponseCall.enqueue(new Callback<MyUpcomingAuctionResponse>() {
                    @Override
                    public void onResponse(Call<MyUpcomingAuctionResponse> call, Response<MyUpcomingAuctionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyUpcomingAuctionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        My Upcoming Auction
     */

    public void MyUpcomingLoanMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyUpcomingLoanMelaResponse> myUpcomingLoanResponseCall = serviceApi.__autokattaGetMyUpcomingLoanMela(myContact);
                myUpcomingLoanResponseCall.enqueue(new Callback<MyUpcomingLoanMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyUpcomingLoanMelaResponse> call, Response<MyUpcomingLoanMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyUpcomingLoanMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
        My Upcoming Exchange Mela
     */

    public void MyUpcomingExchangeMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyUpcomingExchangeMelaResponse> myUpcomingExchangeResponseCall = serviceApi.__autokattaGetMyUpcomingExchangeMela(myContact);
                myUpcomingExchangeResponseCall.enqueue(new Callback<MyUpcomingExchangeMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyUpcomingExchangeMelaResponse> call, Response<MyUpcomingExchangeMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyUpcomingExchangeMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        My Upcoming Sale Mela
     */

    public void MyUpcomingSaleMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyUpcomingExchangeMelaResponse> myUpcomingExchangeResponseCall = serviceApi.__autokattaGetMyUpcomingSaleMela(myContact);
                myUpcomingExchangeResponseCall.enqueue(new Callback<MyUpcomingExchangeMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyUpcomingExchangeMelaResponse> call, Response<MyUpcomingExchangeMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyUpcomingExchangeMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        My Upcoming Service Mela
     */

    public void MyUpcomingServiceMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyUpcomingExchangeMelaResponse> myUpcomingExchangeResponseCall = serviceApi.__autokattaGetMyUpcomingServiceMela(myContact);
                myUpcomingExchangeResponseCall.enqueue(new Callback<MyUpcomingExchangeMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyUpcomingExchangeMelaResponse> call, Response<MyUpcomingExchangeMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyUpcomingExchangeMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      /*
        My Saved Auctions
     */

    public void getMySavedAuctions(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MySavedAuctionResponse> myUploadedVehiclesResponseCall = serviceApi._autokattaMySavedAuctions(myContact);
                myUploadedVehiclesResponseCall.enqueue(new Callback<MySavedAuctionResponse>() {
                    @Override
                    public void onResponse(Call<MySavedAuctionResponse> call, Response<MySavedAuctionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MySavedAuctionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Create Group...
     */
    public void createGroup(String title, String image, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mCreateGroup = serviceApi._autokattaCreateGroup(title, image, contact);
                mCreateGroup.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Vehicle Type Response...
     */

    public void getVehicleSubtype(int vehicleId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetVehicleSubTypeResponse> mSubTypeResponseCall = serviceApi._autokattaGetVehicleSubType(vehicleId);
                mSubTypeResponseCall.enqueue(new Callback<GetVehicleSubTypeResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleSubTypeResponse> call, Response<GetVehicleSubTypeResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleSubTypeResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
    Get My Blacklisted Members
     */

    public void getBlackListMembers(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<BlacklistMemberResponse> mSubTypeResponseCall = serviceApi._autokattaBlacklistMembers(contact);
                mSubTypeResponseCall.enqueue(new Callback<BlacklistMemberResponse>() {
                    @Override
                    public void onResponse(Call<BlacklistMemberResponse> call, Response<BlacklistMemberResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BlacklistMemberResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
 Update Registration/continue Registration
   */
    public void updateRegistration(Integer Regid, Integer page, String profileImage, String about, String website) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                Log.i("Regid---->", "->" + Regid);

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                RegistrationCompanyBasedrequest registrationCompanyBasedrequest = new RegistrationCompanyBasedrequest(Regid, page, profileImage, about, website);
                Call<String> mUpdateRegistration = serviceApi._autokattaUpdateRegistration(registrationCompanyBasedrequest);
                mUpdateRegistration.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Add Fragment...
     */
    /*
    Add Brand
     */
    public void addBrand(String keyword, String title, int categoryId, String subCatID) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddBrand = mServiceApi._autokattaAddBrand(keyword, title, categoryId, subCatID);
                mAddBrand.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Add Model
     */
    public void addModel(String keyword, String title, int categoryId, String subCatID, String brandId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddModel = mServiceApi._autokattaAddModel(keyword, title, categoryId, subCatID, brandId);
                mAddModel.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Add Version
     */
    public void addVersion(String keyword, String title, int categoryId, String subCatID, String brandId,
                           String modleId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddVersion = mServiceApi._autokattaAddVersion(keyword, title, categoryId, subCatID, brandId, modleId);
                mAddVersion.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Add Break
     */
    public void addBreak(String otherBreaks) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddBreaks = mServiceApi._autokattaAddBreaks(otherBreaks);
                mAddBreaks.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Add Pump
     */
    public void addPump(String otherPump) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddPump = mServiceApi._autokattaAddPump(otherPump);
                mAddPump.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    addBodyAndSeatManufacturers
     */
    public void addBodyAndSeatManufacturers(String bodyManufactureName, String seatManufacture) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAdd = mServiceApi._autokattaAddBodyAndSeatManufacturers(bodyManufactureName, seatManufacture);
                mAdd.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Add Body Type
     */
    public void addBodyType(String keyword, String title) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddBodyType = mServiceApi._autokattaAddBodyType(keyword, title);
                mAddBodyType.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Brand
     */
    public void getBrand(int category, String subCategory) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetVehicleBrandResponse> mGetBrand = mServiceApi._autokattaGetBrand(category, subCategory);
                mGetBrand.enqueue(new Callback<GetVehicleBrandResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleBrandResponse> call, Response<GetVehicleBrandResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleBrandResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Model
     */
    public void getModel(int category, String subCategory, String brandId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetVehicleModelResponse> mGetModel = mServiceApi._autokattaGetModel(category, subCategory, brandId);
                mGetModel.enqueue(new Callback<GetVehicleModelResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleModelResponse> call, Response<GetVehicleModelResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleModelResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Version
     */
    public void getVersion(int category, String subCategory, String brandId, String modelId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetVehicleVersionResponse> mGetVersion = mServiceApi._autokattaGetVersion(category, subCategory, brandId, modelId);
                mGetVersion.enqueue(new Callback<GetVehicleVersionResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleVersionResponse> call, Response<GetVehicleVersionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleVersionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Breaks
     */
    public void getBreaks() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetBreaks> mGetBreaks = mServiceApi._autokattaGetBreaks();
                mGetBreaks.enqueue(new Callback<GetBreaks>() {
                    @Override
                    public void onResponse(Call<GetBreaks> call, Response<GetBreaks> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetBreaks> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Pump
     */
    public void getPump() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetPumpResponse> mGetPump = mServiceApi._autokattaGetPumps();
                mGetPump.enqueue(new Callback<GetPumpResponse>() {
                    @Override
                    public void onResponse(Call<GetPumpResponse> call, Response<GetPumpResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetPumpResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Vehicle RTO City
     */
    public void getVehicleRTOCity() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetRTOCityResponse> mGetRTOCity = mServiceApi._autokattaGetVehicleRTOCity();
                mGetRTOCity.enqueue(new Callback<GetRTOCityResponse>() {
                    @Override
                    public void onResponse(Call<GetRTOCityResponse> call, Response<GetRTOCityResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetRTOCityResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Body & Seat Manufacture...
     */
    public void getBodyAndSeatManufacture() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<BodyAndSeatResponse> mGetBodySeat = mServiceApi._autokattaGetBodyAndSeatManufacture();
                mGetBodySeat.enqueue(new Callback<BodyAndSeatResponse>() {
                    @Override
                    public void onResponse(Call<BodyAndSeatResponse> call, Response<BodyAndSeatResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BodyAndSeatResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Body Type
     */
    public void getBodyType() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetBodyTypeResponse> mGetBodType = mServiceApi._autokattaGetBodyType();
                mGetBodType.enqueue(new Callback<GetBodyTypeResponse>() {
                    @Override
                    public void onResponse(Call<GetBodyTypeResponse> call, Response<GetBodyTypeResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetBodyTypeResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Body Type
     */
    public void getVehicleColor() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetVehicleColor> mGetVehicleColor = mServiceApi._autokattaGetColor();
                mGetVehicleColor.enqueue(new Callback<GetVehicleColor>() {
                    @Override
                    public void onResponse(Call<GetVehicleColor> call, Response<GetVehicleColor> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleColor> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Body Type
     */
    public void getVehicleImplements() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetVehicleImplementsResponse> mGetVehicleImplementation = mServiceApi._autokattaGetVehicleImplements();
                mGetVehicleImplementation.enqueue(new Callback<GetVehicleImplementsResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleImplementsResponse> call, Response<GetVehicleImplementsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleImplementsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
  create Loan Mela Event
   */
    public void createLoanMela(String title, String location,
                               String address, String start_date,
                               String start_time, String end_date,
                               String end_time, String image,
                               String details, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                CreateLoanMelaRequest createLoanMelaRequest = new CreateLoanMelaRequest(title, location, address,
                        start_date, start_time, end_date, end_time, image, details, contact);
                Call<LoanMelaCreateResponse> mGetVehicleImplementation = mServiceApi._createLoanMela(createLoanMelaRequest);
                mGetVehicleImplementation.enqueue(new Callback<LoanMelaCreateResponse>() {
                    @Override
                    public void onResponse(Call<LoanMelaCreateResponse> call, Response<LoanMelaCreateResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<LoanMelaCreateResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
  create Exchange Mela Event
   */
    public void createExchangeMela(String title, String location,
                                   String address, String start_date,
                                   String start_time, String end_date,
                                   String end_time, String image,
                                   String details, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                CreateExchangeMelaRequest createExchangeMelaRequest = new CreateExchangeMelaRequest(title, location, address,
                        start_date, start_time, end_date, end_time, image, details, contact);
                Call<ExchangeMelaCreateResponse> mGetVehicleImplementation = mServiceApi._createExchangeMela(createExchangeMelaRequest);
                mGetVehicleImplementation.enqueue(new Callback<ExchangeMelaCreateResponse>() {
                    @Override
                    public void onResponse(Call<ExchangeMelaCreateResponse> call, Response<ExchangeMelaCreateResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ExchangeMelaCreateResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
create Sale Mela Event
 */
    public void createSaleMela(String title, String location,
                               String address, String start_date,
                               String start_time, String end_date,
                               String end_time, String image,
                               String details, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                CreateSaleMelaRequest createSaleMelaRequest = new CreateSaleMelaRequest(title, location, address,
                        start_date, start_time, end_date, end_time, image, details, contact);
                Call<SaleMelaCreateResponse> mGetVehicleImplementation = mServiceApi._createSaleMela(createSaleMelaRequest);
                mGetVehicleImplementation.enqueue(new Callback<SaleMelaCreateResponse>() {
                    @Override
                    public void onResponse(Call<SaleMelaCreateResponse> call, Response<SaleMelaCreateResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SaleMelaCreateResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
create Service Mela Event
*/
    public void createServiceMela(String title, String location,
                                  String address, String start_date,
                                  String start_time, String end_date,
                                  String end_time, String image,
                                  String details, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                CreateServiceMelaRequest createServiceMelaRequest = new CreateServiceMelaRequest(title, location, address,
                        start_date, start_time, end_date, end_time, image, details, contact);
                Call<ServiceMelaCreateResponse> mGetVehicleImplementation = mServiceApi._createServiceMela(createServiceMelaRequest);
                mGetVehicleImplementation.enqueue(new Callback<ServiceMelaCreateResponse>() {
                    @Override
                    public void onResponse(Call<ServiceMelaCreateResponse> call, Response<ServiceMelaCreateResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ServiceMelaCreateResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        get My Broadcast Groups
     */

    public void MyBroadcastGroups(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<MyBroadcastGroupsResponse> broadcastGroupsResponseCall = mServiceApi._autokattaGetBroadcastGroups(contact);
                broadcastGroupsResponseCall.enqueue(new Callback<MyBroadcastGroupsResponse>() {
                    @Override
                    public void onResponse(Call<MyBroadcastGroupsResponse> call, Response<MyBroadcastGroupsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyBroadcastGroupsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   Get Brand Model Version
    */
    public void getBrandModelVersion(String sub_category_id) {
        {
            try {
                if (mConnectionDetector.isConnectedToInternet()) {
                    Retrofit mRetrofit = new Retrofit.Builder()
                            .baseUrl(mContext.getString(R.string.base_url))
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(initLog().build())
                            .build();
                    ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                    Call<GetBrandModelVersionResponse> mGetBrandModelVersionResponse = mServiceApi._autokattaGetBrandModelVersion(sub_category_id);
                    mGetBrandModelVersionResponse.enqueue(new Callback<GetBrandModelVersionResponse>() {
                        @Override
                        public void onResponse(Call<GetBrandModelVersionResponse> call, Response<GetBrandModelVersionResponse> response) {
                            mNotifier.notifySuccess(response);
                        }

                        @Override
                        public void onFailure(Call<GetBrandModelVersionResponse> call, Throwable t) {
                            mNotifier.notifyError(t);
                        }
                    });

                } else {
                    CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
 ADD own
  */
    public void addOwn(String contact, String vehicle_no, String vehicle_type, String subcategory, String model_no, String brand,
                       String version, String year, String tax_validity, String fitness_validity, String permit_validity, String insurance, String PUC, String last_service_date, String next_service_date) {
        {
            try {
                if (mConnectionDetector.isConnectedToInternet()) {

                    //JSON to Gson conversion
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit mRetrofit = new Retrofit.Builder()
                            .baseUrl(mContext.getString(R.string.base_url))
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(initLog().build())
                            .build();
                    ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                    AddOwnVehicle addOwnVehicle = new AddOwnVehicle(contact, vehicle_no, vehicle_type, subcategory, model_no, brand, version, year, tax_validity, fitness_validity, permit_validity, insurance, PUC, last_service_date, next_service_date);
                    Call<String> mAddOwn = mServiceApi._autokattaAddOwn(addOwnVehicle);
                    mAddOwn.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            mNotifier.notifyString(response.body());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            mNotifier.notifyError(t);
                        }
                    });

                } else {
                    CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
Upload Vehicle
*/
    public void uploadVehicle(String ids, String vehicle_no, String vehicle_type, String subcategory, String model_no, String brand,
                              String version, String year, String tax_validity, String fitness_validity, String permit_validity, String insurance, String PUC, String last_service_date, String next_service_date) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                UpdateMyVehicleRequest updateMyVehicleRequest = new UpdateMyVehicleRequest(ids, vehicle_no, vehicle_type, subcategory, model_no, brand, version, year, tax_validity, fitness_validity, permit_validity, insurance, PUC, last_service_date, next_service_date);
                Call<String> mUploadVehicle = mServiceApi._autokattaUploadVehicle(updateMyVehicleRequest);
                mUploadVehicle.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
  Get Suggested Price
   */
    public void SuggestedPrice(int categoryId, String subCategoryId, String brandId, String modelId, String versionId,
                               String mfgYear, String rtoCity) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<PriceSuggestionResponse> priceSuggestionResponseCall = mServiceApi._autokattaGetPriceSuggestion(categoryId, subCategoryId,
                        brandId, modelId, versionId, mfgYear, rtoCity);

                priceSuggestionResponseCall.enqueue(new Callback<PriceSuggestionResponse>() {
                    @Override
                    public void onResponse(Call<PriceSuggestionResponse> call, Response<PriceSuggestionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<PriceSuggestionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    Get Group Vehicles...
     */
    public void getGroupVehicles(int groupId, String brand, String model, String version, String city, String rtoCity,
                                 String price, String regYear, String mgfYear, String kms, int owners) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetGroupVehiclesResponse> mVehiclesResponse = mServiceApi._autokattaGetGroupVehicles(groupId, brand, model,
                        version, city, rtoCity, price, regYear, mgfYear, kms, owners);
                mVehiclesResponse.enqueue(new Callback<GetGroupVehiclesResponse>() {
                    @Override
                    public void onResponse(Call<GetGroupVehiclesResponse> call, Response<GetGroupVehiclesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetGroupVehiclesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get My Vehicles...
     */
    public void getMyVehicles(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetGroupVehiclesResponse> mVehiclesResponse = mServiceApi._autokattaMyUploadedVehicles(contact);
                mVehiclesResponse.enqueue(new Callback<GetGroupVehiclesResponse>() {
                    @Override
                    public void onResponse(Call<GetGroupVehiclesResponse> call, Response<GetGroupVehiclesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetGroupVehiclesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
   Get Special Clauses For Auction
    */
    public void getSpecialClauses(String keyword, String Clause) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<SpecialClauseGetResponse> mGetVehicleImplementation = mServiceApi.getSpecialClauses(keyword, Clause);
                mGetVehicleImplementation.enqueue(new Callback<SpecialClauseGetResponse>() {
                    @Override
                    public void onResponse(Call<SpecialClauseGetResponse> call, Response<SpecialClauseGetResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SpecialClauseGetResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
   Add Special Clauses For Auction
    */
    public void addSpecialClauses(String keyword, String clause) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<SpecialClauseAddResponse> mGetVehicleImplementation = mServiceApi.addSpecialClauses(keyword, clause);
                mGetVehicleImplementation.enqueue(new Callback<SpecialClauseAddResponse>() {
                    @Override
                    public void onResponse(Call<SpecialClauseAddResponse> call, Response<SpecialClauseAddResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SpecialClauseAddResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Group Contacts
     */

    public void getGroupContacts(int groupId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetGroupContactsResponse> mGetGroupContactsResponseCall = mServiceApi._autokattaGetGroupContacts(groupId);
                mGetGroupContactsResponseCall.enqueue(new Callback<GetGroupContactsResponse>() {
                    @Override
                    public void onResponse(Call<GetGroupContactsResponse> call, Response<GetGroupContactsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetGroupContactsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Delete Group Members
     */
    public void DeleteGroupMembers(int group_id, String grouptype, String contact, String mycontact,
                                   String next, String membercount) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mUploadVehicle = mServiceApi._autokattaDeleteGroupMembers(group_id, grouptype, contact, mycontact,
                        next, membercount);

                mUploadVehicle.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    Make Group Admins
     */
    public void makeGroupAdmin(int mGroupId, String contact, String action) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mUploadVehicle = mServiceApi._autokattaMakeGroupAdmin(mGroupId, contact, action);

                mUploadVehicle.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Group Products
     */

    public void getGroupProducts(int groupId, String myContact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<StoreInventoryResponse> mGetGroupContactsResponseCall = mServiceApi._autokattaGetGroupProducts(groupId, myContact);
                mGetGroupContactsResponseCall.enqueue(new Callback<StoreInventoryResponse>() {
                    @Override
                    public void onResponse(Call<StoreInventoryResponse> call, Response<StoreInventoryResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<StoreInventoryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Group Services
     */

    public void getGroupService(int groupId, String myContact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<StoreInventoryResponse> mGetGroupContactsResponseCall = mServiceApi._autokattaGetGroupServices(groupId, myContact);
                mGetGroupContactsResponseCall.enqueue(new Callback<StoreInventoryResponse>() {
                    @Override
                    public void onResponse(Call<StoreInventoryResponse> call, Response<StoreInventoryResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<StoreInventoryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   create Auction Event
     */
    public void createAuction(String title, String start_date,
                              String start_time, String end_date,
                              String end_time, String auction_type,
                              String contact, String location,
                              String auction_category, String special_clauses,
                              String openClose, String stockLocation) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                CreateAuctionRequest createAuctionRequest = new CreateAuctionRequest(title, start_date, start_time, end_date,
                        end_time, auction_type, contact, location, auction_category, special_clauses, openClose, stockLocation);
                Call<AuctionCreateResponse> mVehiclesResponse = mServiceApi.createAuction(createAuctionRequest);
                mVehiclesResponse.enqueue(new Callback<AuctionCreateResponse>() {
                    @Override
                    public void onResponse(Call<AuctionCreateResponse> call, Response<AuctionCreateResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AuctionCreateResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
    Get  All States
     */

    public void getAllStates() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<AllStatesResponse> mGetStatesResponseCall = mServiceApi._autokattaGetAllStates();
                mGetStatesResponseCall.enqueue(new Callback<AllStatesResponse>() {
                    @Override
                    public void onResponse(Call<AllStatesResponse> call, Response<AllStatesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AllStatesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Update Company Based Registration */

    public void updateRegistration(int Regid, int page, String area, String bykm, String bydistrict,
                                   String bystate, String company, String designation, String skills, String deals,
                                   String categoryName, String subCategoryName, String brandName) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                Log.i("Regid---->", "->" + Regid);

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                RegistrationCompanyBasedrequest registrationCompanyBasedrequest = new RegistrationCompanyBasedrequest(Regid, page, area, bykm,
                        bydistrict, bystate, company, designation, skills, deals, categoryName, subCategoryName, brandName);

                Call<String> mUpdateRegistration = serviceApi._autokattaUpdateCompanyRegistration(registrationCompanyBasedrequest);
                mUpdateRegistration.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 /*
    Get States
     */

    public void getStates() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetStatesResponse> mGetStatesResponseCall = mServiceApi._autokattaGetStates();
                mGetStatesResponseCall.enqueue(new Callback<GetStatesResponse>() {
                    @Override
                    public void onResponse(Call<GetStatesResponse> call, Response<GetStatesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetStatesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Districts
     */

    public void getDistricts() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetDistrictsResponse> mGetDistrictsResponseCall = mServiceApi._autokattaGetDistricts();
                mGetDistrictsResponseCall.enqueue(new Callback<GetDistrictsResponse>() {
                    @Override
                    public void onResponse(Call<GetDistrictsResponse> call, Response<GetDistrictsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetDistrictsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      /*Add New Designation */

    public void addNewDesignation(String designationName) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mAddNewDesignation = serviceApi._autokattaAddNewDesignation(designationName);
                mAddNewDesignation.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 /*Add New Designation */

    public void addNewCompany(String companyName) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mAddNewCompany = serviceApi._autokattaAddNewCompany(companyName);
                mAddNewCompany.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*Add New Deal */

    public void addNewDeal(String deals) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mAddNewDeals = serviceApi._autokattaAddNewDeal(deals);
                mAddNewDeals.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 /*Add New Skills */

    public void addNewSkills(String skill) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mAddNewSkills = serviceApi._autokattaAddNewSkills(skill);
                mAddNewSkills.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 /*get New Deals */

    public void getDeals() {


        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<getDealsResponse> mGetDeals = serviceApi._autokattaGetDeals();
                mGetDeals.enqueue(new Callback<getDealsResponse>() {
                    @Override
                    public void onResponse(Call<getDealsResponse> call, Response<getDealsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<getDealsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 /*get  Skills */

    public void getSkills() {


        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetSkillsResponse> mGetSkillsResponseCall = serviceApi._autokattaGetSkills();
                mGetSkillsResponseCall.enqueue(new Callback<GetSkillsResponse>() {
                    @Override
                    public void onResponse(Call<GetSkillsResponse> call, Response<GetSkillsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetSkillsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 /*get  Designation */

    public void getCompany() {


        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetCompaniesResponse> mGetCompaniesResponseCall = serviceApi._autokattaGetCompany();
                mGetCompaniesResponseCall.enqueue(new Callback<GetCompaniesResponse>() {
                    @Override
                    public void onResponse(Call<GetCompaniesResponse> call, Response<GetCompaniesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetCompaniesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 /*get  Company */

    public void getDesignation() {


        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetDesignationResponse> mGetDesignationReponseCall = serviceApi._autokattaGetDesignation();
                mGetDesignationReponseCall.enqueue(new Callback<GetDesignationResponse>() {
                    @Override
                    public void onResponse(Call<GetDesignationResponse> call, Response<GetDesignationResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetDesignationResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     /*
        Ended Auction
     */

    public void getMyEndedAuction(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveAuctionResponse> myActiveAuctionResponseCall = serviceApi.getEndedAuctions(myContact);
                myActiveAuctionResponseCall.enqueue(new Callback<MyActiveAuctionResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveAuctionResponse> call, Response<MyActiveAuctionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveAuctionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     /*
        Set vehicle Privacy to show it in groups and stores
     */

    public void VehiclePrivacy(String myContact, int vehicleId, String groupIds, String storeIds) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> setVehiclePrivacy = serviceApi._autokattaSetVehiclePrivacy(myContact, vehicleId, groupIds, storeIds);
                setVehiclePrivacy.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    Get Vehicle By Id...
     */
    public void getVehicleById(String contact, int vehicleId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetVehicleByIdResponse> getVehicleById = serviceApi._autokattaGetVehicleById(contact, vehicleId);
                getVehicleById.enqueue(new Callback<GetVehicleByIdResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleByIdResponse> call, Response<GetVehicleByIdResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleByIdResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
        Delete a store
     */

    public void DeleteStore(int storeId, String keyword) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> deleteStore = serviceApi._autokattaDeleteStore(storeId, keyword);
                deleteStore.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Create a store
     */

    public void CreateStore(String name, String contact, String location, String website, String storetype, String lastWord,
                            String workdays, String open, String close, String category, String address,
                            String coverlastWord, String storeDescription, String textbrand, String strBrandSpinner) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                CreateStoreRequest storeRequest = new CreateStoreRequest(contact, name, lastWord, location, category, storetype,
                        website, workdays, open, close, address, storeDescription, coverlastWord, textbrand, strBrandSpinner);
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<CreateStoreResponse> deleteStore = serviceApi._autokattaCreatetore(storeRequest);
                deleteStore.enqueue(new Callback<CreateStoreResponse>() {
                    @Override
                    public void onResponse(Call<CreateStoreResponse> call, Response<CreateStoreResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<CreateStoreResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Get a store admins
     */

    public void StoreAdmin(int store_id) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<StoreOldAdminResponse> adminResponse = serviceApi._autokattaGetStoreAdmin(store_id);
                adminResponse.enqueue(new Callback<StoreOldAdminResponse>() {
                    @Override
                    public void onResponse(Call<StoreOldAdminResponse> call, Response<StoreOldAdminResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<StoreOldAdminResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Add new store admins
     */

    public void newStoreAdmin(int storeId, String adminNos) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> adminResponse = serviceApi._autokattaAddNewStoreAdmin(storeId, adminNos);
                adminResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 Get Contact By Company
    */
    public void getContactByCompany(String contact, String page) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetContactByCompanyResponse> mGetContactByCompany = mServiceApi._autokattaGetContactByCompany(contact, page);
                mGetContactByCompany.enqueue(new Callback<GetContactByCompanyResponse>() {
                    @Override
                    public void onResponse(Call<GetContactByCompanyResponse> call, Response<GetContactByCompanyResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetContactByCompanyResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 Get Store Profile Info
    */
    public void getStoreProfileInfo(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetStoreProfileInfoResponse> mStoreCall = mServiceApi._autokattaGetProfileInfo(contact);
                mStoreCall.enqueue(new Callback<GetStoreProfileInfoResponse>() {
                    @Override
                    public void onResponse(Call<GetStoreProfileInfoResponse> call, Response<GetStoreProfileInfoResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetStoreProfileInfoResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      /*
       Get Registered Contacts
     */

    public void getRegisteredContacts(String contact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetRegisteredContactsResponse> mGetRegisteredContact = serviceApi._autokattaGetRegisteredContact(contact);
                mGetRegisteredContact.enqueue(new Callback<GetRegisteredContactsResponse>() {
                    @Override
                    public void onResponse(Call<GetRegisteredContactsResponse> call, Response<GetRegisteredContactsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetRegisteredContactsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      /*
       Create User
     */

    public void createUser(String username, String contact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<CreateUserResponse> mCreateUserResponse = serviceApi._autokattaCreateUser(username, contact);
                mCreateUserResponse.enqueue(new Callback<CreateUserResponse>() {
                    @Override
                    public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
       Follow
     */

    public void Follow(String mycontact, String otherContact, String layout, int storeid, int vehicleid, int pid, int servid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mFollowResponse = serviceApi._autokattaFollow(mycontact, otherContact, layout, storeid, vehicleid, pid, servid);
                mFollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
       Un Follow
     */

    public void UnFollow(String senderContact, String receiverContact, String layout, int storeid, int vid, int pid, int sid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaUnfollow(senderContact, receiverContact, layout, storeid, vid, pid, sid);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
remove contact from blacklist contact
*/
    public void removeFromBlacklist(String myContact, String contact, String keyword) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUpdateRegistration = serviceApi.removeContactFromBlacklist(myContact, "", contact, keyword, "");
                mUpdateRegistration.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
get Admin Excel file Names for auction
*/
    public void ExcelSheetName(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<AdminExcelSheetResponse> adminExcelSheetNames = serviceApi._autokattaGetAdminExcelSheetNames(myContact);
                adminExcelSheetNames.enqueue(new Callback<AdminExcelSheetResponse>() {
                    @Override
                    public void onResponse(Call<AdminExcelSheetResponse> call, Response<AdminExcelSheetResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AdminExcelSheetResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
delete uploaded vehicle
*/
    public void deleteUploadedVehicle(String vehicle_id, String keyword) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUpdateRegistration = serviceApi.deleteUploadedVehicles(vehicle_id, keyword);
                mUpdateRegistration.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get Admin Vehicles for auction
*/
    public void AdminVehicles(String myContact, String fileName, String userId) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<AdminVehiclesResponse> adminVehiclesResponseCall = serviceApi._autokattaGetAdminVehicles(myContact, fileName,
                        userId);
                adminVehiclesResponseCall.enqueue(new Callback<AdminVehiclesResponse>() {
                    @Override
                    public void onResponse(Call<AdminVehiclesResponse> call, Response<AdminVehiclesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AdminVehiclesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
get All Vehicles for auction
*/
    public void AllAuctionVehicle(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<AuctionAllVehicleResponse> auctionVehiclesResponseCall = serviceApi._autokattaGetAuctionAllVehicles(myContact);
                auctionVehiclesResponseCall.enqueue(new Callback<AuctionAllVehicleResponse>() {
                    @Override
                    public void onResponse(Call<AuctionAllVehicleResponse> call, Response<AuctionAllVehicleResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AuctionAllVehicleResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     get Reauctioned Vehicles for auction
    */
    public void ReauctionedVehicles(String myContact, String AuctionId) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<AuctionReauctionVehicleResponse> auctionVehiclesResponseCall = serviceApi._autokattaGetReauctionedVehicle(myContact, AuctionId);
                auctionVehiclesResponseCall.enqueue(new Callback<AuctionReauctionVehicleResponse>() {
                    @Override
                    public void onResponse(Call<AuctionReauctionVehicleResponse> call, Response<AuctionReauctionVehicleResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AuctionReauctionVehicleResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    send uploaded vehicle start and stop notification
    */
    public void sendNotificationOfUploadedVehicle(String vehicle_id, String keyword) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUpdateRegistration = serviceApi.sendNotificationOfUploadedVehicle(vehicle_id, keyword);
                mUpdateRegistration.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   Delete Group
   */
    public void deleteGroup(int group_id, String keyword, String contact) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUpdateRegistration = serviceApi.deleteGroup(group_id, keyword, contact);
                mUpdateRegistration.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     Edit Group
    */
    public void editGroup(String groupname, int group_id, String profile) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUpdateRegistration = serviceApi.editGroup(groupname, group_id, profile);
                mUpdateRegistration.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get All Live Events...
     */
    public void getLiveAuctionEvents(String userName) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetLiveEventsResponse> mLiveEvents = serviceApi.getLiveEvents(userName);
                mLiveEvents.enqueue(new Callback<GetLiveEventsResponse>() {
                    @Override
                    public void onResponse(Call<GetLiveEventsResponse> call, Response<GetLiveEventsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetLiveEventsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get All Live Loan Events...
     */
    public void getLiveLoanEvents(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetLiveLoanEventsResponse> mLiveEvents = serviceApi.getLiveLoanEvents(contact);
                mLiveEvents.enqueue(new Callback<GetLiveLoanEventsResponse>() {
                    @Override
                    public void onResponse(Call<GetLiveLoanEventsResponse> call, Response<GetLiveLoanEventsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetLiveLoanEventsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get All Live Exchange Events...
     */
    public void getLiveExchangeEvents(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetLiveExchangeEventsResponse> mLiveEvents = serviceApi.getLiveExchangeEvents(contact);
                mLiveEvents.enqueue(new Callback<GetLiveExchangeEventsResponse>() {
                    @Override
                    public void onResponse(Call<GetLiveExchangeEventsResponse> call, Response<GetLiveExchangeEventsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetLiveExchangeEventsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get All Live Sale Events...
     */
    public void getLiveSaleEvents(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetLiveSaleEventsResponse> mLiveEvents = serviceApi.getLiveSaleEvents(contact);
                mLiveEvents.enqueue(new Callback<GetLiveSaleEventsResponse>() {
                    @Override
                    public void onResponse(Call<GetLiveSaleEventsResponse> call, Response<GetLiveSaleEventsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetLiveSaleEventsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get All Live Service Events...
     */
    public void getLiveServiceEvents(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetLiveServiceEventsResponse> mLiveEvents = serviceApi.getLiveServiceEvents(contact);
                mLiveEvents.enqueue(new Callback<GetLiveServiceEventsResponse>() {
                    @Override
                    public void onResponse(Call<GetLiveServiceEventsResponse> call, Response<GetLiveServiceEventsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetLiveServiceEventsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Going Events...
     */
    public void geGoingAuctionEvents(String userName) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetLiveEventsResponse> mLiveEvents = serviceApi.getGoingEvents(userName);
                mLiveEvents.enqueue(new Callback<GetLiveEventsResponse>() {
                    @Override
                    public void onResponse(Call<GetLiveEventsResponse> call, Response<GetLiveEventsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetLiveEventsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Upcoming Events...
     */
    public void getUpcomingEvents(String userName) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetLiveEventsResponse> mLiveEvents = serviceApi.getUpcomingEvents(userName);
                mLiveEvents.enqueue(new Callback<GetLiveEventsResponse>() {
                    @Override
                    public void onResponse(Call<GetLiveEventsResponse> call, Response<GetLiveEventsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetLiveEventsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


      /*
       Follow
     */

    public void deleteMySearch(int search_id, String keyword) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mFollowResponse = serviceApi.deleteMySearch(search_id, keyword);
                mFollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Update Auction data
    */
    /*
params.put("auction_id", bundleAuctionId);
                params.put("vehicle_ids", stringVehicleIds);
                params.put("status", SaveActivate);
                params.put("ShowHide", ShowHide);
                params.put("NoVehicle", stringNoofVehicle);
 */
    public void UpdateAuction(String auction_id, String auctionTitleUpdate, String startDateUpdate, String startTimeUpdate,
                              String endDateUpdate, String endTimeUpdate, String specialClausesIDUpdate, String vehicle_ids,
                              String status, String ShowHide, String NoVehicle) {
        //JSON to Gson conversion
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUpdateAuction = serviceApi._autokattaUpdateAuctionCreation(auction_id, auctionTitleUpdate, startDateUpdate,
                        startTimeUpdate, endDateUpdate, endTimeUpdate, specialClausesIDUpdate, vehicle_ids, status,
                        ShowHide, NoVehicle);
                mUpdateAuction.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add start and reserved price

    public void Start_ReservedPrice(String auctionId, String vehicleId, String startPrice, String reservedPrice) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> addPrice = serviceApi._autokattaAddStart_ReservedPrice(auctionId, vehicleId, startPrice,
                        reservedPrice);
                addPrice.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add start and reserved price

    public void SendAuctionMail(String loginContact, int strAuctionId) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> sendMail = serviceApi._autokattaSendAuctionMail(loginContact, strAuctionId);
                sendMail.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   Get Auction Participants
    */
    public void AuctionParticipantData(String myContact, String strAuctionId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<AuctionParticipantsResponse> mAuctionParticipant = serviceApi._autokattaGetAuctionParticipants(myContact, strAuctionId);
                mAuctionParticipant.enqueue(new Callback<AuctionParticipantsResponse>() {
                    @Override
                    public void onResponse(Call<AuctionParticipantsResponse> call, Response<AuctionParticipantsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AuctionParticipantsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add remove a blacklist contact in auction
    public void Add_RemoveBlacklistContact(String myContact, String strAuctionId, String rContact, String keyword, String eventType) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> addRemoveBlacklist = serviceApi._autokattaAddRemoveBlacklist(myContact, strAuctionId, rContact, keyword, eventType);
                addRemoveBlacklist.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
  Get Auction Analytics
   */
    public void AuctionAnalyticsData(String strAuctionId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<AuctionAnalyticsResponse> mAuctionAnalytics = serviceApi._autokattaGetAuctionAnalytics(strAuctionId);
                mAuctionAnalytics.enqueue(new Callback<AuctionAnalyticsResponse>() {
                    @Override
                    public void onResponse(Call<AuctionAnalyticsResponse> call, Response<AuctionAnalyticsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AuctionAnalyticsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Get Auction Analytics
      */
    public void LoanMelaAnalytics(String loanid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<LoanMelaAnalyticsResponse> mAuctionAnalytics = serviceApi._autokattaGetLoanAnalytics(loanid);
                mAuctionAnalytics.enqueue(new Callback<LoanMelaAnalyticsResponse>() {
                    @Override
                    public void onResponse(Call<LoanMelaAnalyticsResponse> call, Response<LoanMelaAnalyticsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<LoanMelaAnalyticsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Get Active Auction High Bid
    */
    public void ActiveAuctionHighBid(String myContact, String mAuctionId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveAuctionHighBidResponse> mAuctionHighBid = serviceApi._autokattaGetActiveAuctionHighBid(myContact, mAuctionId);
                mAuctionHighBid.enqueue(new Callback<MyActiveAuctionHighBidResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveAuctionHighBidResponse> call, Response<MyActiveAuctionHighBidResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveAuctionHighBidResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
       Get Active Auction Above reserved price Bid
   */
    public void ActiveAuctionAboveReservedPrice(String myContact, String mAuctionId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveAuctionAboveReservedResponse> mAuctionAboveReserved = serviceApi._autokattaGetActiveAuctionAboveReservedPrice(myContact, mAuctionId);
                mAuctionAboveReserved.enqueue(new Callback<MyActiveAuctionAboveReservedResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveAuctionAboveReservedResponse> call, Response<MyActiveAuctionAboveReservedResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveAuctionAboveReservedResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
       Get Active Auction No bid Bid
   */
    public void ActiveAuctionNoBid(String mAuctionId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveAuctionNoBidResponse> mAuctionNoBid = serviceApi._autokattaGetActiveAuctionNoBid(mAuctionId);
                mAuctionNoBid.enqueue(new Callback<MyActiveAuctionNoBidResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveAuctionNoBidResponse> call, Response<MyActiveAuctionNoBidResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveAuctionNoBidResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   Get Approved vehicle Bid data
    */
    public void EndedAuctionApprovedVehi(String myContact, String mAuctionId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<EndedAuctionApprovedVehiResponse> yourBid = serviceApi._autokattaGetEndedApproveVehi(myContact, mAuctionId);
                yourBid.enqueue(new Callback<EndedAuctionApprovedVehiResponse>() {
                    @Override
                    public void onResponse(Call<EndedAuctionApprovedVehiResponse> call, Response<EndedAuctionApprovedVehiResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<EndedAuctionApprovedVehiResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add vehicle in reauction
    public void addToReauction(String vehicleid, String mAuctionId) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> addRemoveBlacklist = serviceApi._autokattaAddVehicleToReauction(vehicleid, mAuctionId);
                addRemoveBlacklist.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //approve auction vehicle with bid
    public void ApproveVehicle(String mAuctionId, String keyword1, String vehicleid, String bidderContact, String bidPrice) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ApprovedVehicleResponse> addRemoveBlacklist = serviceApi._autokattaApproveAnVehiclewithBid(mAuctionId, keyword1, vehicleid,
                        bidderContact, bidPrice);
                addRemoveBlacklist.enqueue(new Callback<ApprovedVehicleResponse>() {
                    @Override
                    public void onResponse(Call<ApprovedVehicleResponse> call, Response<ApprovedVehicleResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ApprovedVehicleResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Browse store

    public void getBrowseStores(String contact, String keyword) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<BrowseStoreResponse> mFollowResponse = serviceApi.getBrowseStores(contact, keyword);
                mFollowResponse.enqueue(new Callback<BrowseStoreResponse>() {
                    @Override
                    public void onResponse(Call<BrowseStoreResponse> call, Response<BrowseStoreResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BrowseStoreResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    Get Auction Event
     */
    public void getAuctionEvent(int auctionId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetAuctionEventResponse> mAuctionEvent = serviceApi.getAuctionEvent(auctionId);
                mAuctionEvent.enqueue(new Callback<GetAuctionEventResponse>() {
                    @Override
                    public void onResponse(Call<GetAuctionEventResponse> call, Response<GetAuctionEventResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetAuctionEventResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Delete Broadcast Group

    public void deleteBroadcastgroup(String keyword, String groupid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> delbgrp = serviceApi.deleteBroadcastGroup("", "", "", keyword, groupid);
                delbgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Your Bid data
     */
    public void getYourBid(String id, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<YourBidResponse> yourBid = serviceApi.getYourBid(id, contact);
                yourBid.enqueue(new Callback<YourBidResponse>() {
                    @Override
                    public void onResponse(Call<YourBidResponse> call, Response<YourBidResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<YourBidResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Out Bid data
     */
    public void getOutBid(String id, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<YourBidResponse> yourBid = serviceApi.getOutBid(id, contact);
                yourBid.enqueue(new Callback<YourBidResponse>() {
                    @Override
                    public void onResponse(Call<YourBidResponse> call, Response<YourBidResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<YourBidResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Highest Bid data
     */
    public void getHighestBid(String id, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<YourBidResponse> yourBid = serviceApi.getHighestBid(id, contact);
                yourBid.enqueue(new Callback<YourBidResponse>() {
                    @Override
                    public void onResponse(Call<YourBidResponse> call, Response<YourBidResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<YourBidResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get userWatchedItems Bid data
     */
    public void userWatchedItems(String id, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<YourBidResponse> yourBid = serviceApi.userWatchedItems(id, contact);
                yourBid.enqueue(new Callback<YourBidResponse>() {
                    @Override
                    public void onResponse(Call<YourBidResponse> call, Response<YourBidResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<YourBidResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Broadcast Group Message
    public void broadcastGroupMessage(String groupid, String message, String image) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> bgrpmsg = serviceApi.broadCastGroupMessage(groupid, message, image);
                bgrpmsg.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Create Broadcast Group
    public void createBroadcastgroup(String title, String owner, String member, String keyword) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> createbrdcstgrp = serviceApi.createBroadcastGroup(title, owner, member, keyword, "");
                createbrdcstgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Update Broadcast Group
    public void updateBroadcastgroup(String title, String owner, String member, String keyword, int groupid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> createbrdcstgrp = serviceApi.updateBroadcastGroup(title, owner, member, keyword, groupid);
                createbrdcstgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   Get getBroadcastReceivers data
    */
    public void getBroadcastReceivers(String myContact, String product_id, String service_id, String vehicle_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<BroadcastReceivedResponse> yourBid = serviceApi.getBroadcastReceivers(myContact, product_id, service_id,
                        vehicle_id);
                yourBid.enqueue(new Callback<BroadcastReceivedResponse>() {
                    @Override
                    public void onResponse(Call<BroadcastReceivedResponse> call, Response<BroadcastReceivedResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BroadcastReceivedResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
  Get getBroadcastSenders data
   */
    public void getBroadcastSenders(String myContact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<BroadcastSendResponse> yourBid = serviceApi.getBroadcastSenders(myContact);
                yourBid.enqueue(new Callback<BroadcastSendResponse>() {
                    @Override
                    public void onResponse(Call<BroadcastSendResponse> call, Response<BroadcastSendResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BroadcastSendResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Get MyBussinessChat
 */
    public void getBussinessChat(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<getBussinessChatResponse> mgetBChat = serviceApi.getBussinessChat(contact);
                mgetBChat.enqueue(new Callback<getBussinessChatResponse>() {
                    @Override
                    public void onResponse(Call<getBussinessChatResponse> call, Response<getBussinessChatResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<getBussinessChatResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
  Get getChatMessageData
   */
    public void getChatMessageData(String sender_contact, String receiver_contact, int product_id, int service_id,
                                   int vehicle_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<BroadcastMessageResponse> yourBid = serviceApi.getChatMessageData(sender_contact, receiver_contact,
                        product_id, service_id, vehicle_id);
                yourBid.enqueue(new Callback<BroadcastMessageResponse>() {
                    @Override
                    public void onResponse(Call<BroadcastMessageResponse> call, Response<BroadcastMessageResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BroadcastMessageResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //save chat message at server side

    //Update Broadcast Group
    public void sendChatMessage(String sender_contact, String receiver_contact,
                                String message, String image, int product_id,
                                int service_id, int vehicle_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> createbrdcstgrp = serviceApi.sendChatMessage(sender_contact, receiver_contact, message,
                        image, product_id, service_id, vehicle_id);
                createbrdcstgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
Get ChatElementData
 */
    public void getChatElementData(int product_id, int service_id, int vehicle_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ChatElementDetails> yourBid = serviceApi.getChatElementData(product_id, service_id, vehicle_id);
                yourBid.enqueue(new Callback<ChatElementDetails>() {
                    @Override
                    public void onResponse(Call<ChatElementDetails> call, Response<ChatElementDetails> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ChatElementDetails> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
Get uploaded Vehicle Buyer list
*/
    public void getUploadedVehicleBuyerlist(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<BuyerResponse> yourBid = serviceApi.getUploadedVehicleBuyerlist(contact);
                yourBid.enqueue(new Callback<BuyerResponse>() {
                    @Override
                    public void onResponse(Call<BuyerResponse> call, Response<BuyerResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BuyerResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      /*
       Update Profile
     */

    public void updateProfile(int regID, String emialID, String city, String profession, String subProfession, String website, String companyName, String designation, String skills) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(regID, emialID, city, profession, subProfession, website, companyName, designation, skills);
                Call<String> setVehiclePrivacy = serviceApi._autokattaUpdateProfile(updateProfileRequest);
                setVehiclePrivacy.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

       /*
       Update UserName and Profile pic
     */

    public void updateUsername(int regID, String profilePicture, String userName) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(regID, profilePicture, userName);
                Call<String> setVehiclePrivacy = serviceApi._autokattaUpdateUserName(updateProfileRequest);
                setVehiclePrivacy.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Colors
    */
    public void getColor() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ColorResponse> yourBid = serviceApi._autokattaGetAllColor();
                yourBid.enqueue(new Callback<ColorResponse>() {
                    @Override
                    public void onResponse(Call<ColorResponse> call, Response<ColorResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ColorResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Save My Search
    public void saveMySearch(String myContact, String category, String subCategory, String brand1, String model1,
                             String version1, String color1, String mfgYear, String insurance1, String Kms, String Hrs,
                             String hpCap, int owner1, String price, String tyre, String city1, String city11, String city12,
                             String city13, String city14, String city2, String city21, String city22, String city23,
                             String city24, String rc1, String insurance11, String tax_validity1, String fitness_validity1,
                             String permit_validity1, String fual1, String seating1, String permit1, String hypo1,
                             String drive1, String finance1, String transmission1, String body1, String boat1, String rv1,
                             String use1, String implement1, String bus_type1, String air1, String invoice1, String action,
                             int sid, String callPermission) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                SaveSearchRequest saveSearchRequest = new SaveSearchRequest(action, sid,
                        myContact, category, brand1, model1,
                        city2, city1, "", "", /*locationState,  registrationYear,*/
                        mfgYear, color1, rc1, insurance11,
                        tax_validity1, fitness_validity1, permit_validity1, fual1,
                        seating1, permit1, Kms, Hrs, hpCap,
                        owner1, hypo1, price, drive1, transmission1,
                        body1, boat1, rv1, use1, city11, city12,
                        city13, city14, city21, city22, city23, city24,
                        tyre, implement1, bus_type1, air1, invoice1,
                        finance1, version1, callPermission);
                Call<String> createbrdcstgrp = serviceApi._autokattaSaveMySearch(saveSearchRequest);
                createbrdcstgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //send buyer call date
    public void sendLastCallDate(String caller, String callie, String calldate, String callcount) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> createbrdcstgrp = serviceApi.sendLastCallDate(caller, callie, calldate, callcount);
                createbrdcstgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //add to favourite
    public void addToFavorite(String contact, String buyer_vehicle_id, int search_id, String seller_vehicle_id,
                              int notification_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> createbrdcstgrp = serviceApi.autokatta_AddToFavorite(contact, buyer_vehicle_id, search_id,
                        seller_vehicle_id, notification_id);
                createbrdcstgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Remove from favourite
    public void removeFromFavorite(String contact, String buyer_vehicle_id, int search_id, String seller_vehicle_id,
                                   int notification_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> createbrdcstgrp = serviceApi.autokatta_RemoveFromFavorite(contact, buyer_vehicle_id, search_id,
                        seller_vehicle_id, notification_id);
                createbrdcstgrp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
Get saved search Seller list
*/
    public void getSavedSearchSellerList(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SellerResponse> yourBid = serviceApi.getSavedSearchSellerList(contact);
                yourBid.enqueue(new Callback<SellerResponse>() {
                    @Override
                    public void onResponse(Call<SellerResponse> call, Response<SellerResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SellerResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*
    Get Own Vehicles.
     */

    public void getOwnVehicles(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetOwnVehiclesResponse> mGetOwnVehicles = mServiceApi._autokattaGetOwnVehicles(contact);
                mGetOwnVehicles.enqueue(new Callback<GetOwnVehiclesResponse>() {
                    @Override
                    public void onResponse(Call<GetOwnVehiclesResponse> call, Response<GetOwnVehiclesResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetOwnVehiclesResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Followers.
     */

    public void getFollowers(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetFollowersResponse> mGetfollowers = mServiceApi._autokattaGetFollowers(contact);
                mGetfollowers.enqueue(new Callback<GetFollowersResponse>() {
                    @Override
                    public void onResponse(Call<GetFollowersResponse> call, Response<GetFollowersResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetFollowersResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





     /*
    Get Store response.
     */

    public void getStoreData(String contact, int store_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<StoreResponse> mGetfollowers = mServiceApi.getStoreData(contact, store_id);
                mGetfollowers.enqueue(new Callback<StoreResponse>() {
                    @Override
                    public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Vehicle Auction response.
     */

    public void getVehicleAuction(String auctionId, String vehicleId, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetVehicleForAuctionResponse> mGetAuction = mServiceApi.getVehicleAuction(auctionId, vehicleId, contact);
                mGetAuction.enqueue(new Callback<GetVehicleForAuctionResponse>() {
                    @Override
                    public void onResponse(Call<GetVehicleForAuctionResponse> call, Response<GetVehicleForAuctionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetVehicleForAuctionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Admin Auction response.
     */

    public void getAdminAuction(String vehicleId, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<GetAdminVehicleResponse> mGetAuction = mServiceApi.getAdminAuction(vehicleId, contact);
                mGetAuction.enqueue(new Callback<GetAdminVehicleResponse>() {
                    @Override
                    public void onResponse(Call<GetAdminVehicleResponse> call, Response<GetAdminVehicleResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetAdminVehicleResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Get Brand tags response.
     */

    public void getBrandTags(String Type) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<BrandsTagResponse> mGetAuction = mServiceApi.autokattaGetBrandTags(Type);
                mGetAuction.enqueue(new Callback<BrandsTagResponse>() {
                    @Override
                    public void onResponse(Call<BrandsTagResponse> call, Response<BrandsTagResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BrandsTagResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //add other Brand tags in database
    public void addOtherBrandTags(String brandtag, String type) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<OtherBrandTagAddedResponse> forgetPasswordResponseCall = serviceApi.addOtherBrandTags(brandtag, type);
                forgetPasswordResponseCall.enqueue(new Callback<OtherBrandTagAddedResponse>() {
                    @Override
                    public void onResponse(Call<OtherBrandTagAddedResponse> call, Response<OtherBrandTagAddedResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<OtherBrandTagAddedResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 updateTagAssociation
  */
    public void updateTagAssociation(int product_id, int serviceId, String tagId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> updateStore = serviceApi.updateTagAssociation(product_id, serviceId, tagId);
                updateStore.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
  updateTagAssociation
   */
    public void TagAssociation(int product_id, int serviceId, String tagId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> updateStore = serviceApi.tagAssociation(product_id, serviceId, tagId);
                updateStore.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
    Get all products,service and vehicles related to single store
     */

    public void getStoreInventory(int store_id, String mycontact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<StoreInventoryResponse> mGetAuction = mServiceApi.getStoreInventory(store_id, mycontact);
                mGetAuction.enqueue(new Callback<StoreInventoryResponse>() {
                    @Override
                    public void onResponse(Call<StoreInventoryResponse> call, Response<StoreInventoryResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<StoreInventoryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    Get all products,service and vehicles related to single store
     */

    public void getMyInventory_Catalog(String mycontact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<StoreInventoryResponse> mGetAuction = mServiceApi.getInventoryCatalog(mycontact);
                mGetAuction.enqueue(new Callback<StoreInventoryResponse>() {
                    @Override
                    public void onResponse(Call<StoreInventoryResponse> call, Response<StoreInventoryResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<StoreInventoryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });

            } else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /*
       Like
     */

    public void Like(String myContact, String othercontact, String layout, int store_id, int gid, int vid, int pid,
                     int sid, int statusid, int searchid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaLike(myContact, othercontact, layout, store_id, gid, vid, pid, sid, statusid, searchid);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*
      UnLike
     */

    public void UnLike(String myContact, String othercontact, String layout, int store_id, int gid, int vid,
                       int pid, int sid, int statusid, int searchid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaUnLike(myContact, othercontact, layout, store_id, gid, vid, pid, sid, statusid, searchid);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     /*
     Delete product
     */

    public void deleteProduct(int product_id, String keyword) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.deleteProduct(product_id, keyword);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




     /*
     Delete Service
     */

    public void deleteService(int service_id, String keyword) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.deleteService(service_id, keyword);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




     /*
     Delete Vehicle
     */

    public void deleteVehicle(int vehicle_id, String keyword) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.deleteVehicle(vehicle_id, keyword);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Search product
     */

    public void searchProduct(String key, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetSearchProductResponse> mUnfollowResponse = serviceApi.searchProduct(key, contact);
                mUnfollowResponse.enqueue(new Callback<GetSearchProductResponse>() {
                    @Override
                    public void onResponse(Call<GetSearchProductResponse> call, Response<GetSearchProductResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetSearchProductResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Search Person
     */

    public void getPersonSearchData(String key, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SearchPersonResponse> mUnfollowResponse = serviceApi.getPersonSearchData(key, contact);
                mUnfollowResponse.enqueue(new Callback<SearchPersonResponse>() {
                    @Override
                    public void onResponse(Call<SearchPersonResponse> call, Response<SearchPersonResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SearchPersonResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Search Vehicle
     */

    public void getVehicleSearchData(String key, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SearchVehicleResponse> mUnfollowResponse = serviceApi.getVehicleSearchData(key, contact);
                mUnfollowResponse.enqueue(new Callback<SearchVehicleResponse>() {
                    @Override
                    public void onResponse(Call<SearchVehicleResponse> call, Response<SearchVehicleResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SearchVehicleResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Search Auction
     */

    public void getSearchAuctionData(String key) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetSearchAuctionResponse> mSearchAuction = serviceApi.getSearchAuctionData(key);
                mSearchAuction.enqueue(new Callback<GetSearchAuctionResponse>() {
                    @Override
                    public void onResponse(Call<GetSearchAuctionResponse> call, Response<GetSearchAuctionResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetSearchAuctionResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     post product Review...
     */
    public void postProductReview(String contact, String storeId, int productId, String review, int servicesid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> productReview = serviceApi.postProductReview(contact, storeId, productId, review, servicesid);
                productReview.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i("postProduct", "->" + response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*  *//*
     Vehicle Like
     *//*
done
    public void vehicleLike(String myContac, String otherContact, String layout, String vehicleid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaVehicleLike(myContac, otherContact, layout,"","", vehicleid,"","","","");
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
      /*
    Calling in Vehicle Details
     */

    public void callingVehicleDetails(int vehicle_id, String keyword) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaVehicleCalling(vehicle_id, keyword);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*   *//*
     Vehicle UnLike
     *//*

    public void vehicleUnLike(String myContac, String otherContact, String layout, String vehicleid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaVehicleUnLike(myContac, otherContact, layout,"","", vehicleid,"","","","");
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    /*
     Get Tags
      */
    public void _autoGetTags(String type) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetTagsResponse> getTags = serviceApi._autoGetTags(type);
                getTags.enqueue(new Callback<GetTagsResponse>() {
                    @Override
                    public void onResponse(Call<GetTagsResponse> call, Response<GetTagsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetTagsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Add Tags
      */
    public void _autoAddTags(String tag, String type) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<OtherTagAddedResponse> addTags = serviceApi._autoAddTags(tag, type);
                addTags.enqueue(new Callback<OtherTagAddedResponse>() {
                    @Override
                    public void onResponse(Call<OtherTagAddedResponse> call, Response<OtherTagAddedResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<OtherTagAddedResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*/*
     Product Like
     *//*

    public void _autokattaProductView(String myContac, String otherContact, String layout, String productId) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> like = serviceApi._autokattaProductView(myContac, otherContact, layout, "","","",productId,"","","");
                like.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



    /*
     Service Like
     *//*

    public void _autokattaServiceView(String myContac, String otherContact, String layout, String serviceId) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> like = serviceApi._autokattaServiceView(myContac, otherContact, layout,"","","","", serviceId,"","");
                like.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


   /* *//*
    Product Unlike
     *//*
    public void _autokattaProductViewUnlike(String myContac, String otherContact, String layout, String productID) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaProductViewUnlike(myContac, otherContact, layout, "","","",productID,"","","");
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

/*

    */
/*
   Service Unlike
    *//*

    public void _autokattaServiceViewUnlike(String myContac, String otherContact, String layout, String ServiceId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaServiceViewUnlike(myContac, otherContact, layout,"","","","", ServiceId,"","");
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    /*
    To Share data
     */
    public void shareTaskInApp(String sender_contact, String receiver_contact, int group_id, int broadcastgroup_id,
                               String caption_data, int layout, String profile_id, int store_id,
                               int vehicle_id, int product_id, int service_id, int status_id,
                               int search_id, int auction_id, int loan_id, int exchange_id) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> setVehiclePrivacy = serviceApi._autokattaShareData(sender_contact, receiver_contact, group_id,
                        broadcastgroup_id, caption_data, layout, profile_id, store_id, vehicle_id, product_id, service_id, status_id,
                        search_id, auction_id, loan_id, exchange_id);
                setVehiclePrivacy.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
     Search Service
     */

    public void searchService(String key, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetServiceSearchResponse> searchService = serviceApi.searchService(key, contact);
                searchService.enqueue(new Callback<GetServiceSearchResponse>() {
                    @Override
                    public void onResponse(Call<GetServiceSearchResponse> call, Response<GetServiceSearchResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetServiceSearchResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     Search Store...
     */

    public void searchStore(String key, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<BrowseStoreResponse> searchService = serviceApi.searchStore(key, contact);
                searchService.enqueue(new Callback<BrowseStoreResponse>() {
                    @Override
                    public void onResponse(Call<BrowseStoreResponse> call, Response<BrowseStoreResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<BrowseStoreResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*   *//*
     other Store Follow
        *//*

    public void otherStoreFollow(String mycontact, String otherContact, String layout, int storeid,String vehicleid,String pid,String servid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaFollowStore(mycontact, otherContact, layout, storeid,vehicleid,pid,servid);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
 /*   *//*
     Other Store UNfollow
     *//*

    public void otherStoreUnFollow(String mycontact, String otherContact, String layout, int storeid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaUnfollowStore(mycontact, otherContact, layout, storeid,"","","");
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
   /* *//*
     OtherStore unLike
     *//*

    public void otherStoreUnlike(String mycontact, String otherContact, String layout, int storeid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaUnlikeStore(mycontact, otherContact, layout, storeid,"","","","","","");
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
  /*  *//*
     store Like
     *//*

    public void otherStoreLike(String mycontact, String otherContact, String layout, int storeid) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi._autokattaLikeStore(mycontact, otherContact, layout, storeid,"","","","","","");
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



      /*
    send new rating
     */

    public void sendNewrating(String contact, int store_id, int product_id, int service_id, String rate, String rate1,
                              String rate2, String rate3, String rate4, String rate5, String type) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.sendNewRating(contact, store_id, product_id, service_id, rate,
                        rate1, rate2, rate3, rate4, rate5, type);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


      /*
    send updated rating
     */

    public void sendUpdatedrating(String contact, int store_id, int product_id, int service_id, String rate, String rate1,
                                  String rate2, String rate3, String rate4, String rate5, String type) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.sendupdatedRating(contact, store_id, product_id, service_id, rate,
                        rate1, rate2, rate3, rate4, rate5, type);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



     /*
    recommend store
     */

    public void recommendStore(String contact, int store_id) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.recommendStore(contact, store_id);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    get Favourite Notification
     */
    public void FavouriteNotification(String loginContact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<FavouriteResponse> mUnfollowResponse = serviceApi.autokatta_getMyFavourites(loginContact);
                mUnfollowResponse.enqueue(new Callback<FavouriteResponse>() {
                    @Override
                    public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

       /*
    create Group
     */

    public void createGroups(String title, String image, String adminComtact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.createGroup(title, image, adminComtact);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /*
    Add contact in group
     */

    public void addContactInGroup(int groupid, String contact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.addContactToGroup(groupid, contact);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      /*   *//*
    Notifiction in group like
     *//*

    public void groupLikeNotification(String groupid, String mycontact, String othercontact, String layout,String StoreID,
                                      String VehicleID,String ProductID,String ServiceID, String StatusID,String SearchID) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.notificationLikegroup(groupid, mycontact, othercontact, layout,StoreID,
                         VehicleID, ProductID, ServiceID,  StatusID, SearchID);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/





       /*
    update store
     */

    public void updateStore(String storename, int store_id, String location, String website, String open, String close,
                            String profile, String category, String working_days, String storeDescription, String storetype,
                            String address, String coverImage, String textbrand, String strBrandSpinner) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                UpdateStoreRequest updateStoreRequest = new UpdateStoreRequest(storename, store_id, location, website, open, close,
                        profile, category, working_days, storeDescription, storetype, address, coverImage, textbrand, strBrandSpinner);
                Call<String> mUnfollowResponse = serviceApi.updateStore(updateStoreRequest);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Firebase Device Registration...
     */
    public void firebaseToken(String contactNo, String token) {
        try {
//            if (mConnectionDetector.isConnectedToInternet()){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initLog().build())
                    .build();
            ServiceApi serviceApi = retrofit.create(ServiceApi.class);
            Call<String> firebase = serviceApi.firebaseToken(contactNo, token);
            firebase.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    mNotifier.notifyString(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mNotifier.notifyError(t);
                }
            });

           /* }else {
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
 Add product
  */
    public void addProduct(int store_id,
                           String product_name,
                           String price,
                           String product_details,
                           String product_tags,
                           String product_type,
                           String images,
                           String category,
                           String brandtags,
                           String group_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ProductAddedResponse> addTags = serviceApi.addProduct(store_id, product_name, price, product_details,
                        product_tags, product_type, images, category, brandtags, group_id);
                addTags.enqueue(new Callback<ProductAddedResponse>() {
                    @Override
                    public void onResponse(Call<ProductAddedResponse> call, Response<ProductAddedResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ProductAddedResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
Add service
 */
    public void addService(int store_id,
                           String service_name,
                           String price,
                           String service_details,
                           String service_tags,
                           String service_type,
                           String images,
                           String category,
                           String brandtags,
                           String group_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ServiceAddedResponse> addTags = serviceApi.addService(store_id, service_name, price, service_details,
                        service_tags, service_type, images, category, brandtags, group_id);
                addTags.enqueue(new Callback<ServiceAddedResponse>() {
                    @Override
                    public void onResponse(Call<ServiceAddedResponse> call, Response<ServiceAddedResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ServiceAddedResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get product data
*/
    public void getProductDetails(int product_id, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ProductResponse> mUnfollowResponse = serviceApi.getProductDetails(product_id, contact);
                mUnfollowResponse.enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get product data
*/
    public void getServiceDetails(int service_id, String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ServiceResponse> mUnfollowResponse = serviceApi.getServiceDetails(service_id, contact);
                mUnfollowResponse.enqueue(new Callback<ServiceResponse>() {
                    @Override
                    public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ServiceResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get All Sale Mela data
*/
    public void getSaleMelaDetails(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveSaleMelaResponse> mSaleMelaResponse = serviceApi._autokattaGetSaleMelaDetails(contact);
                mSaleMelaResponse.enqueue(new Callback<MyActiveSaleMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveSaleMelaResponse> call, Response<MyActiveSaleMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveSaleMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get ServiceMela Data
*/
    public void getServiceMelaDetails(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<MyActiveServiceMelaResponse> mServiceMelaResponse = serviceApi._autokattaGetServiceMelaDetails(contact);
                mServiceMelaResponse.enqueue(new Callback<MyActiveServiceMelaResponse>() {
                    @Override
                    public void onResponse(Call<MyActiveServiceMelaResponse> call, Response<MyActiveServiceMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<MyActiveServiceMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






       /*
    update product
     */

    public void updateProduct(int product_id,
                              String product_name,
                              String price,
                              String product_details,
                              String product_tags,
                              String product_type,
                              String images,
                              String category,
                              String brandtags) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.updateProduct(product_id, product_name, price, product_details, product_tags,
                        product_type, images, category, brandtags);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




       /*
    update Service
     */

    public void updateService(int service_id,
                              String service_name,
                              String price,
                              String service_details,
                              String service_tags,
                              String service_type,
                              String images,
                              String category,
                              String brandtags) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> mUnfollowResponse = serviceApi.updateService(service_id, service_name, price, service_details, service_tags,
                        service_type, images, category, brandtags);
                mUnfollowResponse.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get Ended Sale Mela Data
*/
    public void getEndedSaleMelaDetails(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<EndedSaleMelaResponse> mServiceMelaResponse = serviceApi._autokattaGetEndedSaleMelaDetails(contact);
                mServiceMelaResponse.enqueue(new Callback<EndedSaleMelaResponse>() {
                    @Override
                    public void onResponse(Call<EndedSaleMelaResponse> call, Response<EndedSaleMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<EndedSaleMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
get Ended Service Mela Data
*/
    public void getEndedServiceMela(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<EndedSaleMelaResponse> mServiceMelaResponse = serviceApi._autokattaGetEndedServiceMelaDetails(contact);
                mServiceMelaResponse.enqueue(new Callback<EndedSaleMelaResponse>() {
                    @Override
                    public void onResponse(Call<EndedSaleMelaResponse> call, Response<EndedSaleMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<EndedSaleMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get LoanMela Participants Data*/
    public void getLoanMelaParticipants(String contact, String loanid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<LoanMelaParticipantsResponse> mServiceMelaResponse = serviceApi._autokattagetConfirmedParticipants_Loan(contact, loanid);
                mServiceMelaResponse.enqueue(new Callback<LoanMelaParticipantsResponse>() {
                    @Override
                    public void onResponse(Call<LoanMelaParticipantsResponse> call, Response<LoanMelaParticipantsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<LoanMelaParticipantsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get SaleMela Participants Data*/
    public void getSaleMelaParticipants(String contact, String saleid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SaleMelaParticipantsResponse> mServiceMelaResponse = serviceApi._autokattagetConfirmedParticipants_Sale(contact, saleid);
                mServiceMelaResponse.enqueue(new Callback<SaleMelaParticipantsResponse>() {
                    @Override
                    public void onResponse(Call<SaleMelaParticipantsResponse> call, Response<SaleMelaParticipantsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SaleMelaParticipantsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
get SaleMela analytics Data*/
    public void getSaleMelaanalytics(String saleid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<LoanMelaAnalyticsResponse> msaleMelaResponse = serviceApi._autokattagetanalytics_Sale(saleid);
                msaleMelaResponse.enqueue(new Callback<LoanMelaAnalyticsResponse>() {
                    @Override
                    public void onResponse(Call<LoanMelaAnalyticsResponse> call, Response<LoanMelaAnalyticsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<LoanMelaAnalyticsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get Service Mela Participants Data
*/
    public void getServiceMelaParticipants(String contact, String Serviceid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ServiceMelaParticipantsResponse> mServiceMelaResponse = serviceApi._autokattagetConfirmedParticipants_Service(contact, Serviceid);
                mServiceMelaResponse.enqueue(new Callback<ServiceMelaParticipantsResponse>() {
                    @Override
                    public void onResponse(Call<ServiceMelaParticipantsResponse> call, Response<ServiceMelaParticipantsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ServiceMelaParticipantsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
get Service Mela Participants Data
*/
    public void getServiceMelaAnalytics(String Serviceid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<LoanMelaAnalyticsResponse> mServiceMelaResponse = serviceApi._autokattagetServiceAnalytics(Serviceid);
                mServiceMelaResponse.enqueue(new Callback<LoanMelaAnalyticsResponse>() {
                    @Override
                    public void onResponse(Call<LoanMelaAnalyticsResponse> call, Response<LoanMelaAnalyticsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<LoanMelaAnalyticsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
get ExchangeMela Participants Data
*/
    public void getExchangeMelaParticipants(String mycontact, String exchangeid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ExchangeMelaParticipantsResponse> mServiceMelaResponse = serviceApi._autokattagetConfirmedParticipants_Exchange(mycontact, exchangeid);
                mServiceMelaResponse.enqueue(new Callback<ExchangeMelaParticipantsResponse>() {
                    @Override
                    public void onResponse(Call<ExchangeMelaParticipantsResponse> call, Response<ExchangeMelaParticipantsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ExchangeMelaParticipantsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
get ExchangeMela Analytics Data
*/
    public void getExchangeMelaAnalytics(String exchangeid) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<LoanMelaAnalyticsResponse> mServiceMelaResponse = serviceApi._autokattagetExchangeAnalytics(exchangeid);
                mServiceMelaResponse.enqueue(new Callback<LoanMelaAnalyticsResponse>() {
                    @Override
                    public void onResponse(Call<LoanMelaAnalyticsResponse> call, Response<LoanMelaAnalyticsResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<LoanMelaAnalyticsResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
        Ended Loan Mela
     */

    public void getEndedLoanMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<EndedSaleMelaResponse> myActiveLoanMela = serviceApi.getEndedLoanMela(myContact);
                myActiveLoanMela.enqueue(new Callback<EndedSaleMelaResponse>() {
                    @Override
                    public void onResponse(Call<EndedSaleMelaResponse> call, Response<EndedSaleMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<EndedSaleMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
        Ended Exchange Mela
     */

    public void getEndedExchangeMela(String myContact) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<EndedSaleMelaResponse> myActiveLoanMela = serviceApi.getEndedExchangeMela(myContact);
                myActiveLoanMela.enqueue(new Callback<EndedSaleMelaResponse>() {
                    @Override
                    public void onResponse(Call<EndedSaleMelaResponse> call, Response<EndedSaleMelaResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<EndedSaleMelaResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



       /*
     get chat enquiry status
     */

    public void getChatEnquiryStatus(String sender, String receiver, int product_id, int service_id, int vehicle_id) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                //JSON to Gson conversion
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<String> setVehiclePrivacy = serviceApi.getChatEnquiryStatus(sender, receiver, product_id, service_id, vehicle_id);
                setVehiclePrivacy.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mNotifier.notifyString(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     Get Enquiry count of product,service,vehicle
    */
    public void getEnquiryCount(String contact, int product_id, int service_id, int vehicle_id) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<EnquiryCountResponse> mServiceMelaResponse = serviceApi.getEnquiryCount(contact, product_id, service_id, vehicle_id);
                mServiceMelaResponse.enqueue(new Callback<EnquiryCountResponse>() {
                    @Override
                    public void onResponse(Call<EnquiryCountResponse> call, Response<EnquiryCountResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<EnquiryCountResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
      Get Manual Enquiry Details...
    */

    public void getManualEnquiry(String myContact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ManualEnquiryResponse> mServiceMelaResponse = serviceApi.getManualEnquiry(myContact, "null", "null", "null", "null", "null", "null", "null", "null", "null");
                mServiceMelaResponse.enqueue(new Callback<ManualEnquiryResponse>() {
                    @Override
                    public void onResponse(Call<ManualEnquiryResponse> call, Response<ManualEnquiryResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ManualEnquiryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
      Get Person Details...
    */
    public void getPersonData(String id, String keyword) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetPersonDataResponse> mPersonData = serviceApi.getPersonData(id, keyword);
                mPersonData.enqueue(new Callback<GetPersonDataResponse>() {
                    @Override
                    public void onResponse(Call<GetPersonDataResponse> call, Response<GetPersonDataResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetPersonDataResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
      Post Manual Enquiry Details...
    */
    public void addManualEnquiryData(String myContact, String custName, String custContact, String custAddress,
                                     String custFullAddress, String custInventoryType, String custEnquiryStatus,
                                     String discussion, String nextFollowupDate, String idsList) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                //      AddManualEnquiryRequest addManualEnquiryRequest = new AddManualEnquiryRequest();

                Call<AddManualEnquiryResponse> mServiceMelaResponse = serviceApi._autokattaAddManualEnquiry(myContact, custName, custContact, custAddress,
                        custFullAddress, custInventoryType, custEnquiryStatus, discussion, nextFollowupDate, idsList);
                mServiceMelaResponse.enqueue(new Callback<AddManualEnquiryResponse>() {
                    @Override
                    public void onResponse(Call<AddManualEnquiryResponse> call, Response<AddManualEnquiryResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<AddManualEnquiryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
      get Inventory...
    */

    public void getMyInventoryData(String myContact, String keyword) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetInventoryResponse> mInventory = serviceApi.getMyInventoryData(myContact, keyword);
                mInventory.enqueue(new Callback<GetInventoryResponse>() {
                    @Override
                    public void onResponse(Call<GetInventoryResponse> call, Response<GetInventoryResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<GetInventoryResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
         Post Used Vehicle Data.....
       */
    public void uploadUsedVehicle(String strTitle, String myContact, String strCategoryName, String strSubcategoryName,
                                  String strModelName, String strBrandName, String strVersionName, String strRto,
                                  String strLocation, String strRegmonth, String strRegyear, String strMakemonth,
                                  String strMakeyear, String strColor, String strRegno, String strRc, String strInsurance,
                                  String strInsuranceIdv, String strTaxvalid, String strTaxDate, String strFitnessvalid,
                                  String strPermitvalid, String strPermit, String strFitnessvalid1, String strFuel,
                                  String strSeatcap, String strPermitDate, String strFinancestatus, double strKms, String strHrs,
                                  int strOwner, String strBodyMfg, String strSeatMfg, String strHypo, String strEngine,
                                  String strChasis, String s, String strImages, String strDrive, String strTrans,
                                  String strBodytype, String s1, String s2, String strApp, String strTyreContext,
                                  String strBustype, String strAir, String strInvoice, String strImplement, String strGroupprivacy,
                                  String strHp, String strJib, String strBoon, String strBrakename, String strPumpname,
                                  String strInsuDate, String strEmission, String strFinancestatus1, String strExhangestatus,
                                  String strStearing, int strCategoryId, String strSubcategoryId, String strBrandId,
                                  String strModelId, String strVersionId) {

        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                UploadUsedVehicleRequest usedVehicleRequest = new UploadUsedVehicleRequest(strTitle, myContact, strCategoryName, strSubcategoryName,
                        strModelName, strBrandName, strVersionName, strRto,
                        strLocation, strRegmonth, strRegyear, strMakemonth,
                        strMakeyear, strColor, strRegno, strRc, strInsurance,
                        strInsuranceIdv, strTaxvalid, strTaxDate, strFitnessvalid,
                        strPermitvalid, strPermit, strFitnessvalid1, strFuel,
                        strSeatcap, strPermitDate, strFinancestatus, strKms, strHrs,
                        strOwner, strBodyMfg, strSeatMfg, strHypo, strEngine,
                        strChasis, s, strImages, strDrive, strTrans,
                        strBodytype, s1, s2, strApp, strTyreContext,
                        strBustype, strAir, strInvoice, strImplement, strGroupprivacy,
                        strHp, strJib, strBoon, strBrakename, strPumpname,
                        strInsuDate, strEmission, strFinancestatus1, strExhangestatus,
                        strStearing, strCategoryId, strSubcategoryId, strBrandId,
                        strModelId, strVersionId);

                Call<UploadUsedVehicleResponse> mServiceMelaResponse = serviceApi._autokattaUploadUsedVehicle(usedVehicleRequest);
                mServiceMelaResponse.enqueue(new Callback<UploadUsedVehicleResponse>() {
                    @Override
                    public void onResponse(Call<UploadUsedVehicleResponse> call, Response<UploadUsedVehicleResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<UploadUsedVehicleResponse> call, Throwable t) {
                        mNotifier.notifyError(t);
                    }
                });
            } else
                CustomToast.customToast(mContext, mContext.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        return httpClient;
    }

    private static OkHttpClient.Builder initLogs() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        return httpClient;
    }


}
