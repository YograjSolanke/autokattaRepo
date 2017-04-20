package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.FavouriteNotificationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.FavouriteAllResponse;
import autokatta.com.response.FavouriteResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 20/4/17.
 */

public class FavoriteNotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mFavNotify;
    String nextCount = "0";
    Double strTime;


    public FavoriteNotificationFragment() {
        //Empty Constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFavNotify = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mRecyclerView = (RecyclerView) mFavNotify.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mFavNotify.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getFavouriteData();
            }
        });

        return mFavNotify;
    }

    private void getFavouriteData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        /*mApiCall.FavouriteNotification(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "7841023392"), nextCount);*/
        mApiCall.FavouriteNotification("8007855589", nextCount);
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                List<FavouriteAllResponse> mainList = new ArrayList<>();


                FavouriteResponse favouriteResponse = (FavouriteResponse) response.body();
                nextCount = favouriteResponse.getSuccess().getNext();
                strTime = favouriteResponse.getSuccess().getTime();

                //Wall Notification
                //successNotifiList.clear();
                for (FavouriteResponse.Success.Notification successNotification : favouriteResponse.getSuccess().getNotification()) {
                    FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                    favouriteAllResponse.setLayoutNo(Integer.parseInt(successNotification.getLayout()));
                    favouriteAllResponse.setSender(successNotification.getSender());
                    favouriteAllResponse.setAction(successNotification.getAction());
                    favouriteAllResponse.setReceiver(successNotification.getReceiver());
                    favouriteAllResponse.setVehicleId(successNotification.getVehicleId());
                    favouriteAllResponse.setProductId(successNotification.getProductId());
                    favouriteAllResponse.setServiceId(successNotification.getServiceId());
                    favouriteAllResponse.setStoreId(successNotification.getStoreId());
                    favouriteAllResponse.setGroupId(successNotification.getGroupId());
                    favouriteAllResponse.setSenderprofession(successNotification.getSenderprofession());
                    favouriteAllResponse.setSenderwebsite(successNotification.getSenderwebsite());
                    favouriteAllResponse.setSendercity(successNotification.getSendercity());
                    favouriteAllResponse.setSenderlikecount(successNotification.getSenderlikecount());
                    favouriteAllResponse.setSenderfollowcount(successNotification.getSenderfollowcount());
                    favouriteAllResponse.setSenderlikestatus(successNotification.getSenderlikestatus());
                    favouriteAllResponse.setSenderfollowstatus(successNotification.getSenderfollowstatus());
                    favouriteAllResponse.setReceivername(successNotification.getReceivername());
                    favouriteAllResponse.setReceiverPic(successNotification.getReceiverPic());
                    favouriteAllResponse.setReceiverprofession(successNotification.getReceiverprofession());
                    favouriteAllResponse.setReceiverwebsite(successNotification.getReceiverwebsite());
                    favouriteAllResponse.setReceivercity(successNotification.getReceivercity());
                    favouriteAllResponse.setReceiverlikecount(successNotification.getReceiverlikecount());
                    favouriteAllResponse.setReceiverfollowcount(successNotification.getReceiverfollowcount());
                    favouriteAllResponse.setReceiverlikestatus(successNotification.getReceiverlikestatus());
                    favouriteAllResponse.setReceiverfollowstatus(successNotification.getReceiverfollowstatus());
                    favouriteAllResponse.setStorelikestatus(successNotification.getStorelikestatus());
                    favouriteAllResponse.setStorefollowstatus(successNotification.getStorefollowstatus());
                    favouriteAllResponse.setStorerating(successNotification.getStorerating());
                    favouriteAllResponse.setStorelikecount(successNotification.getStorelikecount());
                    favouriteAllResponse.setStorefollowcount(successNotification.getStorefollowcount());
                    favouriteAllResponse.setStoretiming(successNotification.getStoretiming());
                    favouriteAllResponse.setStoreContact(successNotification.getStoreContact());
                    favouriteAllResponse.setStoreName(successNotification.getStoreName());
                    favouriteAllResponse.setStoreImage(successNotification.getStoreImage());
                    favouriteAllResponse.setStoreType(successNotification.getStoreType());
                    favouriteAllResponse.setStoreWebsite(successNotification.getStoreWebsite());
                    favouriteAllResponse.setWorkingDays(successNotification.getWorkingDays());
                    favouriteAllResponse.setStoreLocation(successNotification.getStoreLocation());


                    /*private String groupVehicles;
                    private String groupName;
                    private String groupImage;
                    private String groupMembers;
                    private String productlikestatus;
                    private String productfollowstatus;
                    private String productlikecount;
                    private String productfollowcount;
                    private String productName;
                    private String productType;
                    private String productimages;
                    private String servicelikestatus;
                    private String servicefollowstatus;
                    private String servicelikecount;
                    private String servicefollowcount;
                    private String seriveName;
                    private String serviceType;
                    private String serviceimages;
                    private String vehiclelikestatus;
                    private String vehiclefollowstatus;
                    private Integer vehiclelikecount;
                    private String vehiclefollowcount;
                    private String vehicleContact;
                    private String title;
                    private String image;
                    private String year;*/
                    mainList.add(favouriteAllResponse);
                }

                //BuyerSearch
                //successBuyerList.clear();
                for (FavouriteResponse.Success.BuyerSearch successBuyerSearch : favouriteResponse.getSuccess().getBuyerSearch()) {

                    FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                    favouriteAllResponse.setLayoutNo(111);
                    mainList.add(favouriteAllResponse);

                }

                //Seller
                //successSellerList.clear();
                for (FavouriteResponse.Success.SellerVehicle successSellerVehicle : favouriteResponse.getSuccess().getSellerVehicle()) {

                    FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                    favouriteAllResponse.setLayoutNo(112);
                    mainList.add(favouriteAllResponse);
                }

                //Search
                //successSearchList.clear();
                for (FavouriteResponse.Success.Search successSearch : favouriteResponse.getSuccess().getSearch()) {

                    FavouriteAllResponse favouriteAllResponse = new FavouriteAllResponse();
                    favouriteAllResponse.setLayoutNo(113);
                    mainList.add(favouriteAllResponse);
                }


                mSwipeRefreshLayout.setRefreshing(false);
                FavouriteNotificationAdapter adapter = new FavouriteNotificationAdapter(getActivity(), mainList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }


    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class", "Favourite Notification");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}

