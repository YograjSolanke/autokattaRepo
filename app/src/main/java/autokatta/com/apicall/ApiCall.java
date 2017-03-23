package autokatta.com.apicall;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BlacklistMemberResponse;
import autokatta.com.response.BodyAndSeatResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetBodyTypeResponse;
import autokatta.com.response.GetBreaks;
import autokatta.com.response.GetPumpResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetVehicleColor;
import autokatta.com.response.GetVehicleImplementsResponse;
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
import autokatta.com.response.MyUpcomingAuctionResponse;
import autokatta.com.response.MyUpcomingExchangeMelaResponse;
import autokatta.com.response.MyUpcomingLoanMelaResponse;
import autokatta.com.response.MyUploadedVehiclesResponse;
import autokatta.com.response.ProfileAboutResponse;
import autokatta.com.response.ProfileGroupResponse;
import autokatta.com.response.SearchStoreResponse;
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
    private RequestNotifier mNotifier;
    private ConnectionDetector mConnectionDetector;


    public ApiCall(Activity mContext, RequestNotifier mNotifier) {
        this.mContext = mContext;
        this.mNotifier = mNotifier;
        mConnectionDetector = new ConnectionDetector(mContext);

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
    Get Profile Data About...
     */

    public void profileAbout(String contact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<ProfileAboutResponse> mProfileAboutCall = mServiceApi._autokattaProfileAbout(contact);
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
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
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

    public void Categories() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<CategoryResponse> categoryResponseCall = serviceApi._autokattaGetCategories();
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

    public void SearchStore(String myContact, String storecontact, String location, String finalCategory, String phrase, String radius) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<SearchStoreResponse> searchStoreResponseCall = serviceApi._autokattaGetSearchStore(myContact, storecontact, location,
                        finalCategory, phrase, radius);
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
                Call<String> afterOtpRegistrationResponseCall = serviceApi._autokattaAfterOtpRegistration(username, contact, email, dob, gender, pincode, city, profession, password, sub_profession, industry);
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
                Call<MyActiveAuctionResponse> myActiveAuctionResponseCall = serviceApi._autokattaGetMyActiveAuction(myContact, status);
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
        My Upcoming Auction
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
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
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

    public void getVehicleSubtype(String vehicleId) {
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
    public void updateRegistration(String Regid, String page, String profileImage, String about, String website) {
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
                Call<String> mUpdateRegistration = serviceApi._autokattaUpdateRegistration(Regid, page, profileImage, about, website);
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
    public void addBrand(String keyword, String title, String categoryId, String subCatID) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddBrand = mServiceApi._autokattaAddBrand(keyword, title, categoryId, subCatID);
                mAddBrand.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
    public void addModel(String keyword, String title, String categoryId, String subCatID, String brandId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddModel = mServiceApi._autokattaAddModel(keyword, title, categoryId, subCatID, brandId);
                mAddModel.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
    public void addVersion(String keyword, String title, String categoryId, String subCatID, String brandId,
                           String modleId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddVersion = mServiceApi._autokattaAddVersion(keyword, title, categoryId, subCatID, brandId, modleId);
                mAddVersion.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddBreaks = mServiceApi._autokattaAddBreaks(otherBreaks);
                mAddBreaks.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddPump = mServiceApi._autokattaAddPump(otherPump);
                mAddPump.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAdd = mServiceApi._autokattaAddBodyAndSeatManufacturers(bodyManufactureName, seatManufacture);
                mAdd.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mAddBodyType = mServiceApi._autokattaAddBodyType(keyword, title);
                mAddBodyType.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
    Get Fragment
     */
    /*
    Get Brand
     */
    public void getBrand(String category, String subCategory) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mGetBrand = mServiceApi._autokattaGetBrand(category, subCategory);
                mGetBrand.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
    public void getModel(String category, String subCategory, String brandId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mGetModel = mServiceApi._autokattaGetModel(category, subCategory, brandId);
                mGetModel.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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
    public void getVersion(String category, String subCategory, String brandId, String modelId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<String> mGetVersion = mServiceApi._autokattaGetVersion(category, subCategory, brandId, modelId);
                mGetVersion.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

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

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }
}
