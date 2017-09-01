package autokatta.com.fragment;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import autokatta.com.R;
import autokatta.com.adapter.WallNotificationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.WallResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 17/3/17.
 */

public class WallNotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    public static RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mWallNotify;
    TextView mNoData;
    LinearLayout layout;
    boolean _hasLoadedOnce = false;
    List<WallResponse.Success.WallNotification> notificationList = new ArrayList<>();
    WallNotificationAdapter adapter;
    private String mLoginContact = "";
    ConnectionDetector mConnectionDetector;
    Button mRetry;
    Locale myLocale;
    String mLanguage;
    String mMarathiStr, mEnglishStr;

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
        getData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                //getData();
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
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getData();
                    }
                });
                mRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                    }
                });
            }
        });
        mConnectionDetector = new ConnectionDetector(getActivity());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new WallNotificationAdapter();
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i("Loaded", "->");
            }
        });
    }

    private void getData() {
        try {
            if (mConnectionDetector.isConnectedToInternet()) {
                ApiCall apiCall = new ApiCall(getActivity(), this);
                apiCall.wallNotifications(mLoginContact, mLoginContact, "");
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
                if (!wallResponse.getSuccess().getWallNotifications().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    notificationList.clear();
                    for (WallResponse.Success.WallNotification notification : wallResponse.getSuccess().getWallNotifications()) {
                        notification.setActionID(notification.getActionID());
                        notification.setLayout(notification.getLayout());

                        notification.setSender(notification.getSender());
                        notification.setAction(notification.getAction());
                        notification.setReceiver(notification.getReceiver());
                        String sublayout = notification.getSubLayout();
                        if (sublayout.contains("=")) {
                            String arr[] = sublayout.split("=", 2);

                            notification.setSubLayout(arr[0]);
                            if (!arr[1].equals(""))
                                notification.setShareSubData(arr[1]);
                            else
                                notification.setShareSubData("No data");

                            //arr[1].equals("") ? notification.setShareSubData(arr[1]) : notification.setShareSubData("No data");

                        } else {

                            notification.setSubLayout(sublayout);
                            notification.setShareSubData(sublayout);
                        }
                        notification.setSenderName(notification.getSenderName());
                        notification.setSenderPicture(notification.getSenderPicture());
                        notification.setReceiverName(notification.getReceiverName());
                        notification.setReceiverPicture(notification.getReceiverPicture());
                        notification.setReceiverProfession(notification.getReceiverProfession());
                        notification.setReceiverWebsite(notification.getReceiverWebsite());
                        notification.setReceiverCity(notification.getReceiverCity());
                        notification.setReceiverLikeCount(notification.getReceiverLikeCount());
                        notification.setReceiverFollowCount(notification.getReceiverFollowCount());
                        notification.setReceiverLikeStatus(notification.getReceiverLikeStatus());
                        notification.setReceiverFollowStatus(notification.getReceiverFollowStatus());

                        notification.setStatusID(notification.getStatusID());
                        notification.setStatusLikeStatus(notification.getStatusLikeStatus());
                        notification.setStatus(notification.getStatus());
                        notification.setMyFavStatus(notification.getMyFavStatus());

                        notification.setUpVehicleLikeStatus(notification.getUpVehicleLikeStatus());
                        notification.setUpVehicleFollowStatus(notification.getUpVehicleFollowStatus());
                        notification.setUploadVehicleID(notification.getUploadVehicleID());
                        notification.setUpVehicleLikeCount(notification.getUpVehicleLikeCount());
                        notification.setUpVehicleFollowCount(notification.getUpVehicleFollowCount());
                        notification.setUpVehicleContact(notification.getUpVehicleContact());
                        notification.setUpVehicleTitle(notification.getUpVehicleTitle());
                        notification.setUpVehicleShareCount(notification.getUpVehicleShareCount());
                        notification.setUpVehiclePrice(notification.getUpVehiclePrice());
                        notification.setUpVehicleModel(notification.getUpVehicleModel());
                        notification.setUpVehicleBrand(notification.getUpVehicleBrand());
                        notification.setUpVehicleManfYear(notification.getUpVehicleManfYear());
                        notification.setUpVehicleRegNo(notification.getUpVehicleRegNo());
                        notification.setUpVehicleKmsRun(notification.getUpVehicleKmsRun());
                        notification.setUpVehicleHrsRun(notification.getUpVehicleHrsRun());
                        notification.setUpVehicleRtoCity(notification.getUpVehicleRtoCity());
                        notification.setUpVehicleLocationCity(notification.getUpVehicleLocationCity());
                        String vehicleImage = notification.getUpVehicleImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            notification.setUpVehicleImage(items[0]);
                            /*for (String item : items) {
                                notification.setUpVehicleImage(item);
                            }*/
                        } else {
                            notification.setUpVehicleImage(vehicleImage);
                        }

                        notification.setSearchLikeStatus(notification.getSearchLikeStatus());
                        notification.setSearchCategory(notification.getSearchCategory());
                        notification.setSearchBrand(notification.getSearchBrand());
                        notification.setSearchModel(notification.getSearchModel());
                        notification.setSearchRtoCity(notification.getSearchRtoCity());
                        notification.setSearchLocationCity(notification.getSearchLocationCity());
                        notification.setSearchColor(notification.getSearchColor());
                        notification.setSearchPrice(notification.getSearchPrice());
                        notification.setSearchManfYear(notification.getSearchManfYear());
                        notification.setSearchDate(notification.getSearchDate());
                        notification.setSearchLeads(notification.getSearchLeads());

                        notification.setSenderProfession(notification.getSenderProfession());
                        notification.setSenderWebsite(notification.getSenderWebsite());
                        notification.setSenderCity(notification.getSenderCity());
                        notification.setSenderLikeCount(notification.getSenderLikeCount());
                        notification.setSenderFollowCount(notification.getSenderFollowCount());
                        notification.setSenderLikeStatus(notification.getSenderLikeStatus());
                        notification.setSenderFollowStatus(notification.getSenderFollowStatus());

                        notification.setStoreLikeStatus(notification.getStoreLikeStatus());
                        notification.setStoreFollowStatus(notification.getStoreFollowStatus());
                        notification.setStoreRating(notification.getStoreRating());
                        notification.setStoreID(notification.getStoreID());
                        notification.setStoreLikeCount(notification.getStoreLikeCount());
                        notification.setStoreFollowCount(notification.getStoreFollowCount());
                        notification.setStoreContact(notification.getStoreContact());
                        notification.setStoreName(notification.getStoreName());
                        notification.setStoreImage(notification.getStoreImage());
                        notification.setStoreType(notification.getStoreType());
                        notification.setStoreWebsite(notification.getStoreWebsite());
                        notification.setWorkingDays(notification.getWorkingDays());
                        notification.setStoreLocation(notification.getStoreLocation());
                        notification.setStoreTiming(notification.getStoreTiming());
                        notification.setStoreCategory(notification.getStoreCategory());
                        notification.setStoreShareCount(notification.getStoreShareCount());

                        notification.setGroupVehicles(notification.getGroupVehicles());
                        notification.setGroupID(notification.getGroupID());
                        notification.setGroupName(notification.getGroupName());
                        notification.setGroupImage(notification.getGroupImage());
                        notification.setGroupMembers(notification.getGroupMembers());
                        notification.setGroupProductCount(notification.getGroupProductCount());
                        notification.setGroupServiceCount(notification.getGroupServiceCount());

                        notification.setProductLikeStatus(notification.getProductLikeStatus());
                        notification.setProductFollowStatus(notification.getProductFollowStatus());
                        notification.setProductID(notification.getProductID());
                        notification.setProductLikeCount(notification.getProductLikeCount());
                        notification.setProductFollowCount(notification.getProductFollowCount());
                        notification.setProductName(notification.getProductName());
                        notification.setProductType(notification.getProductType());
                        notification.setProductRating(notification.getProductRating());
                        notification.setProductShareCount(notification.getProductShareCount());
                        String proImage = notification.getProductImage();
                        if (proImage.contains(",")) {
                            String[] items = proImage.split(",");
                            notification.setProductImage(items[0]);
                            /*for (String item : items) {
                                notification.setProductImage(item);
                            }*/
                        } else {
                            notification.setProductImage(proImage);
                        }


                        notification.setServiceLikeStatus(notification.getServiceLikeStatus());
                        notification.setServiceFollowStatus(notification.getServiceFollowStatus());
                        notification.setServiceID(notification.getServiceID());
                        notification.setServiceLikeCount(notification.getServiceLikeCount());
                        notification.setServiceFollowCount(notification.getServiceFollowCount());
                        notification.setServiceName(notification.getServiceName());
                        notification.setServiceType(notification.getServiceType());
                        notification.setServiceRating(notification.getServiceRating());
                        notification.setServiceShareCount(notification.getServiceShareCount());
                        String serviceImage = notification.getServiceImage();
                        if (serviceImage.contains(",")) {
                            String[] items = serviceImage.split(",");
                            notification.setServiceImage(items[0]);
                            /*for (String item : items) {
                                notification.setServiceImage(item);
                            }*/
                        } else {
                            notification.setServiceImage(serviceImage);
                        }

                        notification.setAuctionID(notification.getAuctionID());
                        notification.setActionTitle(notification.getActionTitle());
                        notification.setEndDate(notification.getEndDate());
                        notification.setEndTime(notification.getEndTime());
                        notification.setNoOfVehicles(notification.getNoOfVehicles());
                        notification.setAuctionType(notification.getAuctionType());
                        notification.setGoingCount(notification.getGoingCount());
                        notification.setIgnoreCount(notification.getIgnoreCount());

                        notification.setLayoutType(notification.getLayoutType());
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
                            notification.setDateTime(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        notificationList.add(notification);
                    }
                    adapter = new WallNotificationAdapter(getActivity(), notificationList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
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
}
