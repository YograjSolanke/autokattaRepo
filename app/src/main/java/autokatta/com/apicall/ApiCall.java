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
import autokatta.com.response.AuctionCreateResponse;
import autokatta.com.response.BlacklistMemberResponse;
import autokatta.com.response.BodyAndSeatResponse;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.ExchangeMelaCreateResponse;
import autokatta.com.response.GetBodyTypeResponse;
import autokatta.com.response.GetBrandModelVersionResponse;
import autokatta.com.response.GetBreaks;
import autokatta.com.response.GetCompaniesResponse;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetDistrictsResponse;
import autokatta.com.response.GetGroupContactsResponse;
import autokatta.com.response.GetGroupVehiclesResponse;
import autokatta.com.response.GetPumpResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.GetStatesResponse;
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
import autokatta.com.response.getDealsResponse;
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
    public void addModel(String keyword, String title, String categoryId, String subCatID, String brandId) {
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
    public void addVersion(String keyword, String title, String categoryId, String subCatID, String brandId,
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
    public void getBrand(String category, String subCategory) {
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
    public void getModel(String category, String subCategory, String brandId) {
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
    public void getVersion(String category, String subCategory, String brandId, String modelId) {
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
                Call<LoanMelaCreateResponse> mGetVehicleImplementation = mServiceApi._createLoanMela(title, location, address,
                        start_date, start_time, end_date, end_time, image, details, contact);
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
                Call<ExchangeMelaCreateResponse> mGetVehicleImplementation = mServiceApi._createExchangeMela(title, location, address,
                        start_date, start_time, end_date, end_time, image, details, contact);
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
                    Call<String> mAddOwn = mServiceApi._autokattaAddOwn(contact, vehicle_no, vehicle_type, subcategory, model_no, brand, version, year, tax_validity, fitness_validity, permit_validity, insurance, PUC, last_service_date, next_service_date);
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
                Call<String> mUploadVehicle = mServiceApi._autokattaUploadVehicle(ids, vehicle_no, vehicle_type, subcategory, model_no, brand, version, year, tax_validity, fitness_validity, permit_validity, insurance, PUC, last_service_date, next_service_date);
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
    public void SuggestedPrice(String categoryId, String subCategoryId, String brandId, String modelId, String versionId, String mfgYear, String rtoCity) {
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
    public void getGroupVehicles(String groupId, String brand, String model, String version, String city, String rtoCity,
                                 String price, String regYear, String mgfYear, String kms, String owners) {
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
   Get Special Clauses For Auction
    */
    public void getSpecialClauses(String keyword) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<SpecialClauseGetResponse> mGetVehicleImplementation = mServiceApi.getSpecialClauses(keyword);
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

    public void getGroupContacts(String groupId) {
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
   create Auction Event
     */
    public void createAuction(String title, String start_date,
                              String start_time, String end_date,
                              String end_time, String auction_type,
                              String contact, String location,
                              String product_category, String special_clauses,
                              String openClose) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit mRetrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();
                ServiceApi mServiceApi = mRetrofit.create(ServiceApi.class);
                Call<AuctionCreateResponse> mVehiclesResponse = mServiceApi.createAuction(title, start_date,
                        start_time, end_date,
                        end_time, auction_type,
                        contact, location,
                        product_category, special_clauses,
                        openClose);
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

    /*Update Company Based Registration */

    public void updateRegistration(String Regid, String page, String area, String bykm, String bydistrict,
                                   String bystate, String company, String designation, String skills, String deals) {
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
                Call<String> mUpdateRegistration = serviceApi._autokattaUpdateCompanyRegistration(Regid, page, area, bykm, bydistrict, bystate, company, designation, skills, deals);
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
                Call<MyActiveLoanMelaResponse> myActiveLoanMela = serviceApi.getEndedLoanMela(myContact);
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
                Call<MyActiveExchangeMelaResponse> myActiveLoanMela = serviceApi.getEndedExchangeMela(myContact);
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
        Set vehicle Privacy to show it in groups and stores
     */

    public void VehiclePrivacy(String myContact, String vehicleId, String groupIds, String storeIds) {

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
        get Other Profile Data...
     */

    public void getOtherProfile(String myContact, String senderContact) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<ProfileAboutResponse> profileOther = serviceApi._autokattaOtherProfile(myContact, senderContact);
                profileOther.enqueue(new Callback<ProfileAboutResponse>() {
                    @Override
                    public void onResponse(Call<ProfileAboutResponse> call, Response<ProfileAboutResponse> response) {
                        mNotifier.notifySuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ProfileAboutResponse> call, Throwable t) {
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
    public void getVehicleById(String vehicleId) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mContext.getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(initLog().build())
                        .build();

                ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                Call<GetVehicleByIdResponse> getVehicleById = serviceApi._autokattaGetVehicleById(vehicleId);
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

    public void DeleteStore(String storeId, String keyword) {

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
