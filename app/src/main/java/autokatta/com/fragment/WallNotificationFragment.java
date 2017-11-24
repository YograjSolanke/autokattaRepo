package autokatta.com.fragment;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.adapter.WallNotificationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.model.WallResponseModel;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import autokatta.com.response.WallResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 17/3/17
 */

public class WallNotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mWallNotify;
    TextView mNoData;
    LinearLayout layout;
    boolean _hasLoadedOnce = false;
    List<WallResponseModel> notificationList = new ArrayList<>();
    WallNotificationAdapter adapter;
    private String mLoginContact = "";
    ConnectionDetector mConnectionDetector;
    Button mRetry;
    Locale myLocale;
    String mLanguage;
    int index = 1;

    public WallNotificationFragment() {
        //Empty Constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWallNotify = inflater.inflate(R.layout.fragment_wall_notification, container, false);
        return mWallNotify;
    }

    @Override
    public void onRefresh() {
        getData(1, 10);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                getData(1, 10);
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");*/
                mLoginContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).
                        getString("loginContact", "");
                mLanguage = getActivity().getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE).getString("Language", "");
                Log.i("Language", "->" + mLanguage);
                setLocale(mLanguage);
                mNoData = (TextView) mWallNotify.findViewById(R.id.no_category);
                layout = (LinearLayout) mWallNotify.findViewById(R.id.no_connection);
                mRetry = (Button) mWallNotify.findViewById(R.id.retry);
                mRecyclerView = (RecyclerView) mWallNotify.findViewById(R.id.wall_recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mWallNotify.findViewById(R.id.wall_swipe_refresh_layout);

                adapter = new WallNotificationAdapter(getActivity(), notificationList, mLoginContact);
                adapter.setLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                //int index = myUploadedVehiclesResponseList.size() - 1;
                                index++;
                                Log.i("index", "->" + index);
                                loadMore(index);
                            }
                        });
                        //Calling loadMore function in Runnable to fix the
                        // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
                    }
                });
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                //mLayoutManager.setReverseLayout(true);
                //mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);

                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getData(1, 10);
                    }
                });
                mRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getData(1, 10);
                    }
                });
            }
        });
        mConnectionDetector = new ConnectionDetector(getActivity());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        /*adapter = new WallNotificationAdapter();
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i("Loaded", "->");
            }
        });*/
    }

    private void loadMore(int index) {
        //Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_LONG).show();
        //dialog.show();
        adapter.notifyItemInserted(notificationList.size());
        getData(index, 10);
    }

    private void getData(int pageNo, int viewRecord) {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                ApiCall apiCall = new ApiCall(getActivity(), this);
                apiCall.wallNotifications(mLoginContact, mLoginContact, "", pageNo, viewRecord);
            } else {
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string.no_internet));
                layout.setVisibility(View.VISIBLE);
                mNoData.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLocale(String language) {
        Log.i("Languageset1", "->" + mLanguage);
        myLocale = new Locale(language);
        saveLocale(language);
        Log.i("Languageset2", "->" + mLanguage);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = myLocale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void saveLocale(String language) {
        mLanguage = language;
        Log.i("Languagesave", "->" + mLanguage);
        getActivity().getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE).edit().putString("Language", language).apply();
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                WallResponse wallResponse = (WallResponse) response.body();
                if (wallResponse.getSuccess() != null) {
                    if (wallResponse.getSuccess().getWallNotifications().size() > 0) {
                        if (!wallResponse.getSuccess().getWallNotifications().isEmpty()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mNoData.setVisibility(View.GONE);
                            layout.setVisibility(View.GONE);
                            //notificationList.clear();

                            for (WallResponse.Success.WallNotification notification : wallResponse.getSuccess().getWallNotifications()) {
                                WallResponseModel responseModel = new WallResponseModel();

                                try {
                                    /*if (notification.getLayout().equals("0") && !getSuggestionData(notification.getSuggestionURL())) {
//                                        responseModel.setLayout(notification.getLayout());
//                                        Log.i("Wall URL", "->" + notification.getSuggestionURL());
//                                        responseModel.setSuggestionURL(notification.getSuggestionURL());
                                        Log.i("Restricted Wall If ", "->" + notification.getSuggestionURL());
                                        System.out.println("Restricted Wall If");
                                        continue aa;

                                    } else {*/
                                    if (!notification.getLayout().equals("0")) {
                                        Log.i("Restricted Wall else", "->" + notification.getSuggestionURL());
                                        System.out.println("Restricted Wall else");
                                        responseModel.setActionID(notification.getActionID());
                                        responseModel.setLayout(notification.getLayout());
                                        responseModel.setSuggestionURL(notification.getSuggestionURL());

                                        responseModel.setSender(notification.getSender());
                                        responseModel.setAction(notification.getAction());
                                        responseModel.setReceiver(notification.getReceiver());
                                        String sublayout = notification.getSubLayout();
                                        if (sublayout.contains("=")) {
                                            String arr[] = sublayout.split("=", 2);

                                            responseModel.setSubLayout(arr[0]);
                                            if (!arr[1].equals(""))
                                                responseModel.setShareSubData(arr[1]);
                                            else
                                                responseModel.setShareSubData("No data");

                                            //arr[1].equals("") ? notification.setShareSubData(arr[1]) : notification.setShareSubData("No data");

                                        } else {

                                            responseModel.setSubLayout(sublayout);
                                            responseModel.setShareSubData(sublayout);
                                        }
                                        responseModel.setSenderName(notification.getSenderName());
                                        responseModel.setSenderPicture(notification.getSenderPicture());
                                        responseModel.setReceiverName(notification.getReceiverName());
                                        responseModel.setReceiverPicture(notification.getReceiverPicture());
                                        responseModel.setReceiverProfession(notification.getReceiverProfession());
                                        responseModel.setReceiverWebsite(notification.getReceiverWebsite());
                                        responseModel.setReceiverCity(notification.getReceiverCity());
                                        responseModel.setReceiverLikeCount(notification.getReceiverLikeCount());
                                        responseModel.setReceiverFollowCount(notification.getReceiverFollowCount());
                                        responseModel.setReceiverLikeStatus(notification.getReceiverLikeStatus());
                                        responseModel.setReceiverFollowStatus(notification.getReceiverFollowStatus());

                                        responseModel.setStatusID(notification.getStatusID());
                                        responseModel.setStatusLikeStatus(notification.getStatusLikeStatus());
                                        responseModel.setStatus(notification.getStatus());
                                        responseModel.setStatusType(notification.getStatusType());
                                        responseModel.setStatusImages(notification.getStatusImages());
                                        responseModel.setStatusVideos(notification.getStatusVideos());
                                        responseModel.setStatusInterest(notification.getStatusInterest());

                                        responseModel.setMyFavStatus(notification.getMyFavStatus());

                                        responseModel.setUpVehicleLikeStatus(notification.getUpVehicleLikeStatus());
                                        responseModel.setUpVehicleFollowStatus(notification.getUpVehicleFollowStatus());
                                        responseModel.setUploadVehicleID(notification.getUploadVehicleID());
                                        responseModel.setUpVehicleLikeCount(notification.getUpVehicleLikeCount());
                                        responseModel.setUpVehicleFollowCount(notification.getUpVehicleFollowCount());
                                        responseModel.setUpVehicleContact(notification.getUpVehicleContact());
                                        responseModel.setUpVehicleTitle(notification.getUpVehicleTitle());
                                        responseModel.setUpVehicleShareCount(notification.getUpVehicleShareCount());
                                        responseModel.setUpVehiclePrice(notification.getUpVehiclePrice());
                                        responseModel.setUpVehicleModel(notification.getUpVehicleModel());
                                        responseModel.setUpVehicleBrand(notification.getUpVehicleBrand());
                                        responseModel.setUpVehicleManfYear(notification.getUpVehicleManfYear());
                                        responseModel.setUpVehicleRegNo(notification.getUpVehicleRegNo());
                                        responseModel.setUpVehicleKmsRun(notification.getUpVehicleKmsRun());
                                        responseModel.setUpVehicleHrsRun(notification.getUpVehicleHrsRun());
                                        responseModel.setUpVehicleRtoCity(notification.getUpVehicleRtoCity());
                                        responseModel.setUpVehicleLocationCity(notification.getUpVehicleLocationCity());
                                        String vehicleImage = notification.getUpVehicleImage();
                                        if (vehicleImage.contains(",")) {
                                            String[] items = vehicleImage.split(",");
                                            responseModel.setUpVehicleImage(items[0]);
                            /*for (String item : items) {
                                notification.setUpVehicleImage(item);
                            }*/
                                        } else {
                                            responseModel.setUpVehicleImage(vehicleImage);
                                        }

                                        responseModel.setSearchLikeStatus(notification.getSearchLikeStatus());
                                        responseModel.setSearchCategory(notification.getSearchCategory());
                                        responseModel.setSearchBrand(notification.getSearchBrand());
                                        responseModel.setSearchModel(notification.getSearchModel());
                                        responseModel.setSearchRtoCity(notification.getSearchRtoCity());
                                        responseModel.setSearchLocationCity(notification.getSearchLocationCity());
                                        responseModel.setSearchColor(notification.getSearchColor());

                                        responseModel.setSearchPrice(notification.getSearchPrice() == null ? "" : notification.getSearchPrice());
                                        responseModel.setSearchManfYear(notification.getSearchManfYear());
                                        responseModel.setSearchDate(notification.getSearchDate());
                                        responseModel.setSearchLeads(notification.getSearchLeads());

                                        responseModel.setSenderProfession(notification.getSenderProfession());
                                        responseModel.setSenderWebsite(notification.getSenderWebsite());
                                        responseModel.setSenderCity(notification.getSenderCity());
                                        responseModel.setSenderLikeCount(notification.getSenderLikeCount());
                                        responseModel.setSenderFollowCount(notification.getSenderFollowCount());
                                        responseModel.setSenderLikeStatus(notification.getSenderLikeStatus());
                                        responseModel.setSenderFollowStatus(notification.getSenderFollowStatus());

                                        responseModel.setStoreLikeStatus(notification.getStoreLikeStatus());
                                        responseModel.setStoreFollowStatus(notification.getStoreFollowStatus());
                                        responseModel.setStoreRating(notification.getStoreRating());
                                        responseModel.setStoreID(notification.getStoreID());
                                        responseModel.setStoreLikeCount(notification.getStoreLikeCount());
                                        responseModel.setStoreFollowCount(notification.getStoreFollowCount());
                                        responseModel.setStoreContact(notification.getStoreContact());
                                        responseModel.setStoreName(notification.getStoreName());
                                        responseModel.setStoreImage(notification.getStoreImage());
                                        responseModel.setStoreType(notification.getStoreType());
                                        responseModel.setStoreWebsite(notification.getStoreWebsite());
                                        responseModel.setWorkingDays(notification.getWorkingDays());
                                        responseModel.setStoreLocation(notification.getStoreLocation());
                                        responseModel.setStoreTiming(notification.getStoreTiming());
                                        responseModel.setStoreCategory(notification.getStoreCategory());
                                        responseModel.setStoreShareCount(notification.getStoreShareCount());

                                        responseModel.setGroupVehicles(notification.getGroupVehicles());
                                        responseModel.setGroupID(notification.getGroupID());
                                        responseModel.setGroupName(notification.getGroupName());
                                        responseModel.setGroupImage(notification.getGroupImage());
                                        responseModel.setGroupMembers(notification.getGroupMembers());
                                        responseModel.setGroupProductCount(notification.getGroupProductCount());
                                        responseModel.setGroupServiceCount(notification.getGroupServiceCount());

                                        responseModel.setProductLikeStatus(notification.getProductLikeStatus());
                                        responseModel.setProductFollowStatus(notification.getProductFollowStatus());
                                        responseModel.setProductID(notification.getProductID());
                                        responseModel.setProductLikeCount(notification.getProductLikeCount());
                                        responseModel.setProductFollowCount(notification.getProductFollowCount());
                                        responseModel.setProductName(notification.getProductName());
                                        responseModel.setProductType(notification.getProductType());
                                        responseModel.setProductRating(notification.getProductRating());
                                        responseModel.setProductShareCount(notification.getProductShareCount());
                                        String proImage = notification.getProductImage();
                                        if (proImage.contains(",")) {
                                            String[] items = proImage.split(",");
                                            responseModel.setProductImage(items[0]);
                            /*for (String item : items) {
                                notification.setProductImage(item);
                            }*/
                                        } else {
                                            responseModel.setProductImage(proImage);
                                        }


                                        responseModel.setServiceLikeStatus(notification.getServiceLikeStatus());
                                        responseModel.setServiceFollowStatus(notification.getServiceFollowStatus());
                                        responseModel.setServiceID(notification.getServiceID());
                                        responseModel.setServiceLikeCount(notification.getServiceLikeCount());
                                        responseModel.setServiceFollowCount(notification.getServiceFollowCount());
                                        responseModel.setServiceName(notification.getServiceName());
                                        responseModel.setServiceType(notification.getServiceType());
                                        responseModel.setServiceRating(notification.getServiceRating());
                                        responseModel.setServiceShareCount(notification.getServiceShareCount());
                                        String serviceImage = notification.getServiceImage();
                                        if (serviceImage.contains(",")) {
                                            String[] items = serviceImage.split(",");
                                            responseModel.setServiceImage(items[0]);
                            /*for (String item : items) {
                                notification.setServiceImage(item);
                            }*/
                                        } else {
                                            responseModel.setServiceImage(serviceImage);
                                        }

                                        responseModel.setAuctionID(notification.getAuctionID());
                                        responseModel.setActionTitle(notification.getActionTitle());
                                        responseModel.setEndDate(notification.getEndDate());
                                        responseModel.setEndTime(notification.getEndTime());
                                        responseModel.setNoOfVehicles(notification.getNoOfVehicles());
                                        responseModel.setAuctionType(notification.getAuctionType());
                                        responseModel.setGoingCount(notification.getGoingCount());
                                        responseModel.setIgnoreCount(notification.getIgnoreCount());

                                        responseModel.setLayoutType(notification.getLayoutType());
                                        //notification.setDateTime(notification.getDateTime());
                                        try {
                                            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                            //format of date coming from services
                                            DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                                            inputFormat.setTimeZone(utc);

                                            //format of date which we want to show
                                            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                                            outputFormat.setTimeZone(utc);

                                            Date date = inputFormat.parse(notification.getDateTime());
                                            //System.out.println("jjj"+date);
                                            String output = outputFormat.format(date);
                                            //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                                            responseModel.setDateTime(output);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                        notificationList.add(responseModel);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(getActivity(), "No More Data Available", Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            layout.setVisibility(View.VISIBLE);
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            layout.setVisibility(View.VISIBLE);
        } else {
            Log.i("Check Class-", "Wall Notification Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    /*private boolean getSuggestionData(String mUrl) throws JSONException {
        result = false;
        try {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initLog().build())
                    .build();

            ServiceApi serviceApi = retrofit.create(ServiceApi.class);
            Call<SuggestionsResponse> mediaResponseCall = serviceApi.getClientList(mUrl);
            mediaResponseCall.enqueue(new Callback<SuggestionsResponse>() {
                @Override
                public void onResponse(Call<SuggestionsResponse> call, Response<SuggestionsResponse> response) {
                    if (response.isSuccessful()) {
                        SuggestionsResponse suggestion = response.body();

                        result = suggestion.getSuccess() != null || !suggestion.getSuccess().isEmpty();
                        System.out.println("Suggestion Result=" + String.valueOf(result));
                    }
                }

                @Override
                public void onFailure(Call<SuggestionsResponse> call, Throwable t) {

                }
            });
        *//*RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, mUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject parentObject = null;
                        try {
                            parentObject = new JSONObject(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        JSONArray new_array = null;
                        try {
                            assert parentObject != null;
                            new_array = parentObject.getJSONArray("Success");
                            if (new_array.length() == 0)
                                result = false;
                            else
                                result = true;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                *//**//*params.put("contact", contact);
                params.put("timestamp", TimeStampConstants.storetimeId);
                System.out.println("+++++++++going timestamp" + storetimeId);*//**//*
                return new HashMap<>();
            }
        };
        requestQueue.add(request);*//*

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/

    /***
     * Retrofit Logs
     ***/
    public static OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging).build();
        return httpClient;
    }
}
