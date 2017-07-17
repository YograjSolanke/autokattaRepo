package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.WallNotificationFragment;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.WallResponse;
import autokatta.com.view.ShareWithinAppActivity;
import retrofit2.Response;

/**
 * Created by ak-001 on 1/4/17.
 */

public class WallNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {
    private Activity mActivity;
    private List<WallResponse.Success.WallNotification> notificationList = new ArrayList<>();
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private OnLoadMoreListener mOnLoadMoreListener;
    private String mLoginContact = "";
    private ApiCall mApiCall;
    private int profile_likecountint, profile_followcountint;

    public WallNotificationAdapter(Activity mActivity1, List<WallResponse.Success.WallNotification> notificationList) {
        this.mActivity = mActivity1;
        this.notificationList = notificationList;
        this.mApiCall = new ApiCall(mActivity, this);
        mLoginContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
    }

    public WallNotificationAdapter() {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) WallNotificationFragment.mRecyclerView.getLayoutManager();
        WallNotificationFragment.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    /*
    Profile Notification Class...
     */
    private static class ProfileNotifications extends RecyclerView.ViewHolder {
        CardView mProfileCardView;
        ImageView mProfilePic, mProfileImage;
        ImageButton mShareAutokatta, mShareOther, mCall, mLike, mUnlike, mFav, mUnfav;
        TextView mProfileName, mProfileContact, mProfileTitle, mUserName, mProfileWorkAt, mProfileWebSite, mLocation,
                mFollowCount, mLikes, mShares;

        private ProfileNotifications(View profileView) {
            super(profileView);
            mProfileCardView = (CardView) profileView.findViewById(R.id.profile_card_view);
            mProfilePic = (ImageView) profileView.findViewById(R.id.pro_pic);
            mProfileImage = (ImageView) profileView.findViewById(R.id.profile_image);
            mProfileName = (TextView) profileView.findViewById(R.id.profile_name);
            mProfileContact = (TextView) profileView.findViewById(R.id.profile_time);
            mProfileTitle = (TextView) profileView.findViewById(R.id.profile_title);
            mUserName = (TextView) profileView.findViewById(R.id.username);
            mProfileWorkAt = (TextView) profileView.findViewById(R.id.profileworkat);
            mProfileWebSite = (TextView) profileView.findViewById(R.id.profilewebsite);
            mLocation = (TextView) profileView.findViewById(R.id.profilelocation);
            mFollowCount = (TextView) profileView.findViewById(R.id.followcnt);
            mLikes = (TextView) profileView.findViewById(R.id.likes);
            mShares = (TextView) profileView.findViewById(R.id.share);
            mShareAutokatta = (ImageButton) profileView.findViewById(R.id.share_autokatta);
            mShareOther = (ImageButton) profileView.findViewById(R.id.share_other);
            mCall = (ImageButton) profileView.findViewById(R.id.call);
            mLike = (ImageButton) profileView.findViewById(R.id.like);
            mUnlike = (ImageButton) profileView.findViewById(R.id.unlike);
            mFav = (ImageButton) profileView.findViewById(R.id.profile_favourite);
            mUnfav = (ImageButton) profileView.findViewById(R.id.profile_unfavourite);
        }
    }

    /*
    Store Notification Class...
     */
    private static class StoreNotifications extends RecyclerView.ViewHolder {
        CardView mStoreCardView;
        ImageView mStorePic, mStoreImage;
        ImageButton mShareAutokatta, mShareOther, mCall, mLike, mFollow;
        RatingBar mStoreRating;
        TextView mStoreActionName, mActionTime, mStoreName, mStoreCategory, mStoreWorkAt, mStoreWebSite, mStoreTiming, mStoreWorkingDay,
                mStoreLocation, mFollowCount, mLikes, mShares;

        private StoreNotifications(View storeView) {
            super(storeView);
            mStoreCardView = (CardView) storeView.findViewById(R.id.store_card_view);
            mStorePic = (ImageView) storeView.findViewById(R.id.store_pic);
            mStoreImage = (ImageView) storeView.findViewById(R.id.store_image);

            mShareAutokatta = (ImageButton) storeView.findViewById(R.id.share_autokatta);
            mShareOther = (ImageButton) storeView.findViewById(R.id.share_other);
            mCall = (ImageButton) storeView.findViewById(R.id.call);
            mLike = (ImageButton) storeView.findViewById(R.id.like);
            mFollow = (ImageButton) storeView.findViewById(R.id.follow_store);
            mStoreRating = (RatingBar) storeView.findViewById(R.id.store_rating);

            mStoreActionName = (TextView) storeView.findViewById(R.id.store_action_names);
            mActionTime = (TextView) storeView.findViewById(R.id.store_action_time);
            mStoreName = (TextView) storeView.findViewById(R.id.store_name);
            mStoreCategory = (TextView) storeView.findViewById(R.id.store_category);
            mStoreWorkAt = (TextView) storeView.findViewById(R.id.store_workat);
            mStoreWebSite = (TextView) storeView.findViewById(R.id.store_website);
            mStoreTiming = (TextView) storeView.findViewById(R.id.store_time);
            mStoreWorkingDay = (TextView) storeView.findViewById(R.id.store_working_day);
            mStoreLocation = (TextView) storeView.findViewById(R.id.store_location);
            mFollowCount = (TextView) storeView.findViewById(R.id.followcnt);
            mLikes = (TextView) storeView.findViewById(R.id.likes);
            mShares = (TextView) storeView.findViewById(R.id.share);
        }
    }

    /*
    Group Notification Class...
     */
    private static class GroupNotifications extends RecyclerView.ViewHolder {
        CardView mGroupCardView;
        ImageView mUserPic, mGroupImage;
        ImageButton mGroupFavourite, mGroupUnFav;
        TextView mActionName, mActionTime, mGroupName, mGroupMembers, mGroupNoOfVehicles, mGroupNoOfProducts,
                mGroupNoOfServices;

        private GroupNotifications(View groupView) {
            super(groupView);
            mGroupCardView = (CardView) groupView.findViewById(R.id.group_card_view);
            mUserPic = (ImageView) groupView.findViewById(R.id.profile_pic);
            mActionName = (TextView) groupView.findViewById(R.id.action_names);
            mActionTime = (TextView) groupView.findViewById(R.id.action_time);

            mGroupImage = (ImageView) groupView.findViewById(R.id.group_image);
            mGroupFavourite = (ImageButton) groupView.findViewById(R.id.group_favourite);
            mGroupUnFav = (ImageButton) groupView.findViewById(R.id.group_unfavourite);
            mGroupName = (TextView) groupView.findViewById(R.id.group_name);
            mGroupMembers = (TextView) groupView.findViewById(R.id.group_no_of_members);
            mGroupNoOfVehicles = (TextView) groupView.findViewById(R.id.group_no_of_vehicles);
            mGroupNoOfProducts = (TextView) groupView.findViewById(R.id.group_no_of_products);
            mGroupNoOfServices = (TextView) groupView.findViewById(R.id.group_no_of_services);
        }
    }

    /*
    Vehicle Notification Class...
     */
    private static class VehicleNotifications extends RecyclerView.ViewHolder {
        CardView mVehicleCardView;
        ImageView mUserPic, mVehicleImage;
        ImageButton mShareAutokatta, mShareOther, mCall, mLike, mVehicleFavourite;
        TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikesTxt, mSharesTxt;

        private VehicleNotifications(View upVehicleView) {
            super(upVehicleView);
            mVehicleCardView = (CardView) upVehicleView.findViewById(R.id.vehicle_card_view);
            mUserPic = (ImageView) upVehicleView.findViewById(R.id.profile_pic);
            mActionTime = (TextView) upVehicleView.findViewById(R.id.action_time);
            mActionName = (TextView) upVehicleView.findViewById(R.id.action_name);

            mShareAutokatta = (ImageButton) upVehicleView.findViewById(R.id.share_autokatta);
            mShareOther = (ImageButton) upVehicleView.findViewById(R.id.share_other);
            mCall = (ImageButton) upVehicleView.findViewById(R.id.vehicle_call);
            mLike = (ImageButton) upVehicleView.findViewById(R.id.like);
            mVehicleFavourite = (ImageButton) upVehicleView.findViewById(R.id.vehicle_favourite);

            mVehicleImage = (ImageView) upVehicleView.findViewById(R.id.vehicle_image);
            mVehicleRegistration = (TextView) upVehicleView.findViewById(R.id.vehicle_registration);
            mVehicleName = (TextView) upVehicleView.findViewById(R.id.vehicle_name);
            mVehiclePrice = (TextView) upVehicleView.findViewById(R.id.vehicle_price);
            mVehicleBrand = (TextView) upVehicleView.findViewById(R.id.vehicle_brand);
            mVehicleModel = (TextView) upVehicleView.findViewById(R.id.vehicle_model);
            mVehicleYearOfMfg = (TextView) upVehicleView.findViewById(R.id.vehicle_year_of_mfg);
            mVehicleKmsHrs = (TextView) upVehicleView.findViewById(R.id.vehicle_kms_hrs);
            mVehicleLocation = (TextView) upVehicleView.findViewById(R.id.vehicle_locations);
            mRtoCity = (TextView) upVehicleView.findViewById(R.id.vehicle_rto_city);
            mLikesTxt = (TextView) upVehicleView.findViewById(R.id.likes);
            mSharesTxt = (TextView) upVehicleView.findViewById(R.id.share);
        }
    }

    /*
    Product Notification Class...
     */
    private static class ProductNotifications extends RecyclerView.ViewHolder {
        CardView mProductCardView;
        ImageView mProductPic, mProductImage;
        ImageButton mProductAutokatta, mProductOther, mCall, mLike, mVehicleFavourite;
        RatingBar mProductRating;
        TextView mProductActionName, mProductActionTime, mProductTitle, mProductName, mProductType, mLikes, mShares;

        private ProductNotifications(View productView) {
            super(productView);
            mProductCardView = (CardView) productView.findViewById(R.id.product_card_view);
            mProductPic = (ImageView) productView.findViewById(R.id.product_pro_pic);
            mProductImage = (ImageView) productView.findViewById(R.id.product_image);

            mProductAutokatta = (ImageButton) productView.findViewById(R.id.share_autokatta);
            mProductOther = (ImageButton) productView.findViewById(R.id.share_other);
            mCall = (ImageButton) productView.findViewById(R.id.call);
            mLike = (ImageButton) productView.findViewById(R.id.like);
            mVehicleFavourite = (ImageButton) productView.findViewById(R.id.vehicle_favourite);
            mProductRating = (RatingBar) productView.findViewById(R.id.product_rating);

            mProductActionName = (TextView) productView.findViewById(R.id.product_action_names);
            mProductActionTime = (TextView) productView.findViewById(R.id.product_action_time);
            mProductTitle = (TextView) productView.findViewById(R.id.product_title);
            mProductName = (TextView) productView.findViewById(R.id.product_name);
            mProductType = (TextView) productView.findViewById(R.id.product_type);
            mLikes = (TextView) productView.findViewById(R.id.likes);
            mShares = (TextView) productView.findViewById(R.id.share);
        }
    }

    /*
    Service Notification Class...
     */
    private static class ServiceNotifications extends RecyclerView.ViewHolder {
        CardView mServiceCardView;
        ImageView mServicePic, mServiceImage;
        ImageButton mServiceAutokatta, mServiceOther, mCall, mLike, mServiceFavourite;
        RatingBar mServiceRating;
        TextView mServiceActionName, mServiceActionTime, mServiceTitle, mServiceName, mServiceType, mLikes, mShares;

        private ServiceNotifications(View serviceView) {
            super(serviceView);
            mServiceCardView = (CardView) serviceView.findViewById(R.id.service_card_view);
            mServicePic = (ImageView) serviceView.findViewById(R.id.service_pro_pic);
            mServiceImage = (ImageView) serviceView.findViewById(R.id.service_image);

            mServiceAutokatta = (ImageButton) serviceView.findViewById(R.id.share_autokatta);
            mServiceOther = (ImageButton) serviceView.findViewById(R.id.share_other);
            mCall = (ImageButton) serviceView.findViewById(R.id.call);
            mLike = (ImageButton) serviceView.findViewById(R.id.like);
            mServiceFavourite = (ImageButton) serviceView.findViewById(R.id.service_favourite);
            mServiceRating = (RatingBar) serviceView.findViewById(R.id.service_rating);

            mServiceActionName = (TextView) serviceView.findViewById(R.id.service_action_names);
            mServiceActionTime = (TextView) serviceView.findViewById(R.id.service_action_time);
            mServiceTitle = (TextView) serviceView.findViewById(R.id.service_title);
            mServiceName = (TextView) serviceView.findViewById(R.id.service_name);
            mServiceType = (TextView) serviceView.findViewById(R.id.service_type);
            mLikes = (TextView) serviceView.findViewById(R.id.likes);
            mShares = (TextView) serviceView.findViewById(R.id.share);
        }
    }

    /*
    Post Notification Class...
     */
    private static class PostNotifications extends RecyclerView.ViewHolder {
        private PostNotifications(View postView) {
            super(postView);
        }
    }

    /*
    Search Notification Class...
     */
    private static class SearchNotifications extends RecyclerView.ViewHolder {
        CardView mSearchCardView;
        ImageView mSearchPic;
        ImageButton mSearchAutokatta, mSearchOther, mCall, mLike;
        TextView mSearchActionName, mSearchActionTime, mSearchCategory, mSearchBrand, mSearchModel, mSearchPrice, mSearchYear,
                mSearchDate, mSearchLeads;

        private SearchNotifications(View serviceView) {
            super(serviceView);
            mSearchCardView = (CardView) serviceView.findViewById(R.id.search_card_view);
            mSearchPic = (ImageView) serviceView.findViewById(R.id.search_pro_pic);

            mSearchAutokatta = (ImageButton) serviceView.findViewById(R.id.share_autokatta);
            mSearchOther = (ImageButton) serviceView.findViewById(R.id.share_other);
            mCall = (ImageButton) serviceView.findViewById(R.id.call);
            mLike = (ImageButton) serviceView.findViewById(R.id.like);

            mSearchActionName = (TextView) serviceView.findViewById(R.id.search_action_names);
            mSearchActionTime = (TextView) serviceView.findViewById(R.id.search_action_time);
            mSearchCategory = (TextView) serviceView.findViewById(R.id.search_category);
            mSearchBrand = (TextView) serviceView.findViewById(R.id.search_brand);
            mSearchModel = (TextView) serviceView.findViewById(R.id.search_model);
            mSearchPrice = (TextView) serviceView.findViewById(R.id.search_price);
            mSearchYear = (TextView) serviceView.findViewById(R.id.search_year);
            mSearchDate = (TextView) serviceView.findViewById(R.id.search_date);
            mSearchLeads = (TextView) serviceView.findViewById(R.id.search_leads);

        }
    }

    /*
    Active Notification Class...
     */
    private static class ActiveNotifications extends RecyclerView.ViewHolder {
        CardView mAuctionCardView;
        ImageView mAuctionPic;
        ImageButton mAuctionAutokatta, mAuctionOther, mAuctionFavourite;
        Button mAuctionGoing, mAuctionIgnore;
        TextView mAuctionActionName, mAuctionActionTime, mAuctionTitle, mAuctionNoOfVehicles, mAuctionEndDate, mAuctionEndTime,
                mAuctionType, mAuctionGoingCount, mAuctionIgnoreCount;

        private ActiveNotifications(View activeView) {
            super(activeView);
            mAuctionCardView = (CardView) activeView.findViewById(R.id.auction_card_view);
            mAuctionPic = (ImageView) activeView.findViewById(R.id.auction_pro_pic);

            mAuctionAutokatta = (ImageButton) activeView.findViewById(R.id.share_autokatta);
            mAuctionOther = (ImageButton) activeView.findViewById(R.id.share_other);
            mAuctionFavourite = (ImageButton) activeView.findViewById(R.id.auction_favourite);
            mAuctionGoing = (Button) activeView.findViewById(R.id.btn_going);
            mAuctionIgnore = (Button) activeView.findViewById(R.id.btn_ignore);

            mAuctionActionName = (TextView) activeView.findViewById(R.id.auction_action_names);
            mAuctionActionTime = (TextView) activeView.findViewById(R.id.auction_action_times);
            mAuctionTitle = (TextView) activeView.findViewById(R.id.auction_title);
            mAuctionNoOfVehicles = (TextView) activeView.findViewById(R.id.auction_no_of_vehicles);
            mAuctionEndDate = (TextView) activeView.findViewById(R.id.auction_end_date);
            mAuctionEndTime = (TextView) activeView.findViewById(R.id.auction_end_time);
            mAuctionType = (TextView) activeView.findViewById(R.id.auction_type);
            mAuctionGoingCount = (TextView) activeView.findViewById(R.id.auction_going_count);
            mAuctionIgnoreCount = (TextView) activeView.findViewById(R.id.auction_ignore_count);
        }
    }

    /*
    UpVehicle Notification Class...
     */
    private static class UpVehicleNotifications extends RecyclerView.ViewHolder {
        CardView mVehicleCardView;
        ImageView mUserPic, mVehicleImage;
        ImageButton mShareAutokatta, mShareOther, mCall, mLike, mVehicleFavourite;
        TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikesTxt, mSharesTxt;

        private UpVehicleNotifications(View upVehicleView) {
            super(upVehicleView);
            mVehicleCardView = (CardView) upVehicleView.findViewById(R.id.vehicle_card_view);
            mUserPic = (ImageView) upVehicleView.findViewById(R.id.profile_pic);
            mActionTime = (TextView) upVehicleView.findViewById(R.id.action_time);
            mActionName = (TextView) upVehicleView.findViewById(R.id.action_name);

            mShareAutokatta = (ImageButton) upVehicleView.findViewById(R.id.share_autokatta);
            mShareOther = (ImageButton) upVehicleView.findViewById(R.id.share_other);
            mCall = (ImageButton) upVehicleView.findViewById(R.id.vehicle_call);
            mLike = (ImageButton) upVehicleView.findViewById(R.id.like);
            mVehicleFavourite = (ImageButton) upVehicleView.findViewById(R.id.vehicle_favourite);

            mVehicleImage = (ImageView) upVehicleView.findViewById(R.id.vehicle_image);
            mVehicleRegistration = (TextView) upVehicleView.findViewById(R.id.vehicle_registration);
            mVehicleName = (TextView) upVehicleView.findViewById(R.id.vehicle_name);
            mVehiclePrice = (TextView) upVehicleView.findViewById(R.id.vehicle_price);
            mVehicleBrand = (TextView) upVehicleView.findViewById(R.id.vehicle_brand);
            mVehicleModel = (TextView) upVehicleView.findViewById(R.id.vehicle_model);
            mVehicleYearOfMfg = (TextView) upVehicleView.findViewById(R.id.vehicle_year_of_mfg);
            mVehicleKmsHrs = (TextView) upVehicleView.findViewById(R.id.vehicle_kms_hrs);
            mVehicleLocation = (TextView) upVehicleView.findViewById(R.id.vehicle_locations);
            mRtoCity = (TextView) upVehicleView.findViewById(R.id.vehicle_rto_city);
            mLikesTxt = (TextView) upVehicleView.findViewById(R.id.likes);
            mSharesTxt = (TextView) upVehicleView.findViewById(R.id.share);
        }
    }

    /*
    Adding Share Notifications Notification Class...
     */
    private static class ShareNotifications extends RecyclerView.ViewHolder {
        CardView mShareCardView;
        ImageView mSharePic;
        ImageButton mShareFavourite;
        TextView mShareActionName, mShareActionTime, mShareProfileName, mShareProfileLocation, mShareProfileWorkAt,
                mShareProfileWebSite;
        TextView mShareStoreName, mShareType, mShareLocation, mShareWebSite, mShareTiming, mShareWorkingDay;
        TextView mShareProductName, mShareProductType, mShareServiceName, mShareServiceType;
        TextView mShareTitle, mSharePrice, mShareBrand, mShareModel, mShareYear, mShareKmsHrs, mShareRto, mShareRegistrationNo, mShareVehicleLocation;
        TextView mShareMySearchCategory, mShareMySearchBrand, mShareMySearchModel, mShareMySearchPrice, mShareMySearchYear,
                mShareDateOfSearch, mShareMySearchLeads;
        TextView mShareAuctionName, mShareAuctionNoOfVehicles, mShareAuctionEndDate, mShareAuctionEndTime, mShareAuctionType,
                mShareAuctionGoingCount, mShareAuctionIgnoreCount;
        TextView mShareStatus;

        private ShareNotifications(View shareView) {
            super(shareView);
            mShareCardView = (CardView) shareView.findViewById(R.id.share_card_view);
            mSharePic = (ImageView) shareView.findViewById(R.id.share_pro_pic);
            mShareFavourite = (ImageButton) shareView.findViewById(R.id.share_favourite);

            mShareActionName = (TextView) shareView.findViewById(R.id.share_action_names);
            mShareActionTime = (TextView) shareView.findViewById(R.id.share_action_time);
            mShareProfileName = (TextView) shareView.findViewById(R.id.share_profile_name);
            mShareProfileLocation = (TextView) shareView.findViewById(R.id.share_profilelocation);
            mShareProfileWorkAt = (TextView) shareView.findViewById(R.id.share_profileworkat);
            mShareProfileWebSite = (TextView) shareView.findViewById(R.id.share_profilewebsite);

            mShareStoreName = (TextView) shareView.findViewById(R.id.share_storename);
            mShareType = (TextView) shareView.findViewById(R.id.share_storetype);
            mShareLocation = (TextView) shareView.findViewById(R.id.share_location);
            mShareWebSite = (TextView) shareView.findViewById(R.id.share_website);
            mShareTiming = (TextView) shareView.findViewById(R.id.share_timing);
            mShareWorkingDay = (TextView) shareView.findViewById(R.id.share_workday);

            mShareProductName = (TextView) shareView.findViewById(R.id.share_productname);
            mShareProductType = (TextView) shareView.findViewById(R.id.share_producttype);

            mShareServiceName = (TextView) shareView.findViewById(R.id.share_servicename);
            mShareServiceType = (TextView) shareView.findViewById(R.id.share_servicetype);

            mShareTitle = (TextView) shareView.findViewById(R.id.share_title);
            mSharePrice = (TextView) shareView.findViewById(R.id.share_price);
            mShareBrand = (TextView) shareView.findViewById(R.id.share_brand);
            mShareModel = (TextView) shareView.findViewById(R.id.share_model);
            mShareYear = (TextView) shareView.findViewById(R.id.share_year);
            mShareKmsHrs = (TextView) shareView.findViewById(R.id.share_km_hrs);
            mShareRto = (TextView) shareView.findViewById(R.id.share_RTO);
            mShareRegistrationNo = (TextView) shareView.findViewById(R.id.share_registrationNo);
            mShareVehicleLocation = (TextView) shareView.findViewById(R.id.share_vehilocation);

            mShareMySearchCategory = (TextView) shareView.findViewById(R.id.share_mysearch_category);
            mShareMySearchBrand = (TextView) shareView.findViewById(R.id.share_mysearch_brand);
            mShareMySearchModel = (TextView) shareView.findViewById(R.id.share_mysearch_model);
            mShareMySearchPrice = (TextView) shareView.findViewById(R.id.share_mysearch_price);
            mShareMySearchYear = (TextView) shareView.findViewById(R.id.share_mysearch_year);
            mShareDateOfSearch = (TextView) shareView.findViewById(R.id.share_searchdate);
            mShareMySearchLeads = (TextView) shareView.findViewById(R.id.share_buyerleads);

            mShareAuctionName = (TextView) shareView.findViewById(R.id.share_auc_name);
            mShareAuctionNoOfVehicles = (TextView) shareView.findViewById(R.id.share_auc_noofvehicle);
            mShareAuctionEndDate = (TextView) shareView.findViewById(R.id.share_auc_enddate);
            mShareAuctionEndTime = (TextView) shareView.findViewById(R.id.share_auc_endtime);
            mShareAuctionType = (TextView) shareView.findViewById(R.id.share_auc_type);
            mShareAuctionGoingCount = (TextView) shareView.findViewById(R.id.share_going_cnt);
            mShareAuctionIgnoreCount = (TextView) shareView.findViewById(R.id.share_ignore_cnt);

            mShareStatus = (TextView) shareView.findViewById(R.id.share_statustxt);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return Integer.parseInt(notificationList.get(position).getLayout());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType) {
            case 1:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_profile_notifications, parent, false);
                return new ProfileNotifications(mView);

            case 2:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_store_notifications, parent, false);
                return new StoreNotifications(mView);

            case 3:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_group_notifications, parent, false);
                return new GroupNotifications(mView);

            case 4:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_vehicle_notifications, parent, false);
                return new VehicleNotifications(mView);

            case 5:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_product_notification, parent, false);
                return new ProductNotifications(mView);

            case 6:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_service_notifications, parent, false);
                return new ServiceNotifications(mView);

            case 7:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_profile_notifications, parent, false);
                return new PostNotifications(mView);

            case 8:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_search_notifications, parent, false);
                return new SearchNotifications(mView);

            case 9:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_active_notifications, parent, false);
                return new ActiveNotifications(mView);

            case 10:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_vehicle_notifications, parent, false);
                return new UpVehicleNotifications(mView);

            case 11:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_adding_share_notifications, parent, false);
                return new ShareNotifications(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("Wall", "Adapter-LayoutNo ->" + holder.getItemViewType());
        switch (holder.getItemViewType()) {
            case 1:
                final ProfileNotifications mProfileHolder = (ProfileNotifications) holder;
                Log.i("Wall", "Profile-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction"))
                    mProfileHolder.mCall.setVisibility(View.GONE);
                else
                    mProfileHolder.mCall.setVisibility(View.VISIBLE);

                mProfileHolder.mProfileName.setText(notificationList.get(position).getSenderName() + " "
                        + notificationList.get(position).getAction() + " " + notificationList.get(position).getReceiverName() + " " + "Profile");

                mProfileHolder.mProfileContact.setText(notificationList.get(position).getDateTime());
                mProfileHolder.mUserName.setText(notificationList.get(position).getSenderName());
                mProfileHolder.mProfileWorkAt.setText(notificationList.get(position).getSenderProfession());
                mProfileHolder.mProfileWebSite.setText(notificationList.get(position).getSenderWebsite());
                mProfileHolder.mLocation.setText(notificationList.get(position).getSenderCity());
                mProfileHolder.mFollowCount.setText("Followers(" + notificationList.get(position).getSenderFollowCount() + ")");
                mProfileHolder.mLikes.setText("Likes(" + notificationList.get(position).getSenderLikeCount() + ")");

                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mProfileHolder.mProfileImage.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    /*Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + notificationList.get(position).getSenderPicture())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mProfileHolder.mProfileImage);*/
                }

                mProfileHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mProfileHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });

     /* Like & Unlike Functionality */
                if (notificationList.get(position).getSenderLikeStatus().equalsIgnoreCase("yes")) {
                    mProfileHolder.mLike.setVisibility(View.VISIBLE);
                    mProfileHolder.mUnlike.setVisibility(View.GONE);
                } else {
                    mProfileHolder.mUnlike.setVisibility(View.VISIBLE);
                    mProfileHolder.mLike.setVisibility(View.GONE);
                }

                mProfileHolder.mLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mProfileHolder.getAdapterPosition()).getSender();
                        mProfileHolder.mLike.setVisibility(View.GONE);
                        mProfileHolder.mUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "1", 0, "", "", "", "", "", "");
                        profile_likecountint = Integer.parseInt(notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount());
                        profile_likecountint = profile_likecountint - 1;
                        mProfileHolder.mLikes.setText(String.valueOf("Likes(" + profile_likecountint + ")"));
                        /*storeLikeCount = String.valueOf(profile_likecountint);
                        likeUnlike.setCount(String.valueOf(profile_likecountint));*/
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderLikeCount(String.valueOf(profile_likecountint));
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderLikeStatus("no");
                    }
                });

                mProfileHolder.mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mProfileHolder.getAdapterPosition()).getSender();
                        mProfileHolder.mUnlike.setVisibility(View.GONE);
                        mProfileHolder.mLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "1", 0, "", "", "", "", "", "");
                        profile_likecountint = Integer.parseInt(notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount());
                        profile_likecountint = profile_likecountint + 1;
                        mProfileHolder.mLikes.setText(String.valueOf("Likes(" + profile_likecountint + ")"));
                        /*storeLikeCount = String.valueOf(profile_likecountint);
                        likeUnlike.setCount(String.valueOf(profile_likecountint));*/
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderLikeCount(String.valueOf(profile_likecountint));
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderLikeStatus("yes");
                    }
                });

      /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mProfileHolder.mFav.setVisibility(View.VISIBLE);
                    mProfileHolder.mUnfav.setVisibility(View.GONE);
                } else {
                    mProfileHolder.mUnfav.setVisibility(View.VISIBLE);
                    mProfileHolder.mFav.setVisibility(View.GONE);
                }

                mProfileHolder.mFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        String notiId = notificationList.get(mProfileHolder.getAdapterPosition()).getActionID();
                        mProfileHolder.mFav.setVisibility(View.GONE);
                        mProfileHolder.mUnfav.setVisibility(View.VISIBLE);
                        /*mApiCall.UnLike(mLoginContact, otherContact, "1", 0, "", "", "", "", "", "");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("no");*/
                        Toast.makeText(mActivity, "unFavorite", Toast.LENGTH_SHORT).show();
                    }
                });

                mProfileHolder.mUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        String notiId = notificationList.get(mProfileHolder.getAdapterPosition()).getActionID();
                        mProfileHolder.mUnfav.setVisibility(View.GONE);
                        mProfileHolder.mFav.setVisibility(View.VISIBLE);
                        /*mApiCall.addRemovefavouriteStatus(mLoginContact, notiId, "1", 0, "", "", "", "", "", "");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("yes");*/
                        Toast.makeText(mActivity, "Favorite", Toast.LENGTH_SHORT).show();
                    }
                });

                mProfileHolder.mShareAutokatta.setOnClickListener(new View.OnClickListener() {
                    String imageFilePath = "", imagename;
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    @Override
                    public void onClick(View v) {
                        //shareProfileData();
                        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(mActivity);
                        alert.setTitle("Share");
                        alert.setMessage("with Autokatta or to other?");
                        alert.setIconAttribute(android.R.attr.alertDialogIcon);

                        alert.setPositiveButton("Autokatta", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String allProfileDetails = mProfileHolder.mUserName.getText().toString() + "=" +
                                        mProfileHolder.mProfileWorkAt.getText().toString() + "=" +
                                        mProfileHolder.mProfileWebSite.getText().toString() + "=" +
                                        mProfileHolder.mLocation.getText().toString() + "=" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPicture() + "=" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount() + "=" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderFollowCount();

                                System.out.println("all sender detailssss======Auto " + allProfileDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allProfileDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_profile_contact", notificationList.get(mProfileHolder.getAdapterPosition()).getSender()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "profile").apply();

                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPicture().equalsIgnoreCase("") ||
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPicture().equalsIgnoreCase(null) ||
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPicture().equalsIgnoreCase("null")) {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    imagename = "http://autokatta.com/mobile/profile_profile_pics/" + notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPicture();
                                }
                                Log.e("TAG", "img : " + imagename);

                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(imagename));
                                request.allowScanningByMediaScanner();
                                String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                                Log.e("ShareImagePath :", filename);
                                Log.e("TAG", "img : " + imagename);

                                DownloadManager manager = (DownloadManager) mActivity.getApplication()
                                        .getSystemService(Context.DOWNLOAD_SERVICE);

                                Log.e("TAG", "img URL: " + imagename);

                                manager.enqueue(request);

                                imageFilePath = "/storage/emulated/0/Download/" + filename;
                                System.out.println("ImageFilePath:" + imageFilePath);

                                String allProfileDetails = "Username : " + mProfileHolder.mUserName.getText().toString() + "\n" +
                                        "Working at : " + mProfileHolder.mProfileWorkAt.getText().toString() + "\n" +
                                        "Website : " + mProfileHolder.mProfileWebSite.getText().toString() + "\n" +
                                        "Address : " + mProfileHolder.mLocation.getText().toString() /*+ "\n" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount() + "\n"+
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderFollowCount()*/;

                                System.out.println("all sender detailssss======Other " + allProfileDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my profile on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/profile/other/" + notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPicture());
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));


                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allProfileDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });


                break;

            case 2:
                ImageView mStorePic, mStoreImage;
                ImageButton mShareAutokatta, mShareOther, mCall, mLike, mFollow;
                RatingBar mStoreRating;
                TextView mStoreActionName, mActionTime, mStoreName, mStoreCategory, mStoreWorkAt, mStoreWebSite, mStoreTiming, mStoreWorkingDay,
                        mStoreLocation, mFollowCount, mLikes, mShares;

                break;

            case 3:
                /*ImageView mUserPic, mGroupImage;
                ImageButton mGroupFavourite;
                TextView mActionName, mActionTime, mGroupName, mGroupMembers, mGroupNoOfVehicles, mGroupNoOfProducts,
                        mGroupNoOfServices;*/
                final GroupNotifications mGroupHolder = (GroupNotifications) holder;

                mGroupHolder.mActionName.setText(notificationList.get(position).getSenderName() + " " +
                        notificationList.get(position).getAction() + " " + notificationList.get(position).getReceiverName() +
                        " in " + notificationList.get(position).getGroupName()
                        + " group");
                mGroupHolder.mGroupName.setText(notificationList.get(position).getGroupName());
                mGroupHolder.mActionTime.setText(notificationList.get(position).getDateTime());
                mGroupHolder.mGroupMembers.setText(notificationList.get(position).getGroupMembers());
                mGroupHolder.mGroupNoOfVehicles.setText(notificationList.get(position).getGroupVehicles());
               /* mGroupHolder.mGroupNoOfProducts.setText(notificationList.get(position));
                mGroupHolder.mGroupNoOfServices.setText(notificationList.get(position));*/

               /* Profile pic */
                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mGroupHolder.mUserPic.setBackgroundResource(R.mipmap.profile);
                } else {
                    /*Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + notificationList.get(position).getSenderPicture())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroupHolder.mUserPic);*/
                }

               /* Group pic */
                if (notificationList.get(position).getGroupImage() == null ||
                        notificationList.get(position).getGroupImage().equals("") ||
                        notificationList.get(position).getGroupImage().equals("null")) {
                    mGroupHolder.mGroupImage.setBackgroundResource(R.drawable.group);
                } else {
                    /*Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + notificationList.get(position).getGroupImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroupHolder.mGroupImage);*/
                }

                /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mGroupHolder.mGroupFavourite.setVisibility(View.VISIBLE);
                    mGroupHolder.mGroupUnFav.setVisibility(View.GONE);
                } else {
                    mGroupHolder.mGroupUnFav.setVisibility(View.VISIBLE);
                    mGroupHolder.mGroupFavourite.setVisibility(View.GONE);
                }

                mGroupHolder.mGroupFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        String notiId = notificationList.get(mGroupHolder.getAdapterPosition()).getActionID();
                        mGroupHolder.mGroupFavourite.setVisibility(View.GONE);
                        mGroupHolder.mGroupUnFav.setVisibility(View.VISIBLE);
                        /*mApiCall.UnLike(mLoginContact, otherContact, "1", 0, "", "", "", "", "", "");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("no");*/
                        Toast.makeText(mActivity, "unFavorite", Toast.LENGTH_SHORT).show();
                    }
                });

                mGroupHolder.mGroupUnFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        String notiId = notificationList.get(mGroupHolder.getAdapterPosition()).getActionID();
                        mGroupHolder.mGroupUnFav.setVisibility(View.GONE);
                        mGroupHolder.mGroupFavourite.setVisibility(View.VISIBLE);
                        /*mApiCall.addRemovefavouriteStatus(mLoginContact, notiId, "1", 0, "", "", "", "", "", "");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("yes");*/
                        Toast.makeText(mActivity, "Favorite", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 4:
                ImageView mUserPic, mVehicleImage;
                /*ImageButton mShareAutokatta, mShareOther, mCall, mLike, mVehicleFavourite;
                TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                        mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikesTxt, mSharesTxt;*/
                break;

            case 5:
                /*ImageView mProductPic, mProductImage;
                ImageButton mProductAutokatta, mProductOther, mCall, mLike, mVehicleFavourite;
                RatingBar mProductRating;
                TextView mProductActionName, mProductActionTime, mProductTitle, mProductName, mProductType, mLikes, mShares;*/
                break;

            case 6:
                /*ImageView mServicePic, mServiceImage;
                ImageButton mServiceAutokatta, mServiceOther, mCall, mLike, mServiceFavourite;
                RatingBar mServiceRating;
                TextView mServiceActionName, mServiceActionTime, mServiceTitle, mServiceName, mServiceType, mLikes, mShares;*/
                break;

            case 7:

                break;

            case 8:
                /*ImageView mSearchPic;
                ImageButton mSearchAutokatta, mSearchOther, mCall, mLike;
                TextView mSearchActionName, mSearchActionTime, mSearchCategory, mSearchBrand, mSearchModel, mSearchPrice, mSearchYear,
                        mSearchDate, mSearchLeads;*/
                break;

            case 9:
                ImageView mAuctionPic;
                ImageButton mAuctionAutokatta, mAuctionOther, mAuctionFavourite;
                Button mAuctionGoing, mAuctionIgnore;
                TextView mAuctionActionName, mAuctionActionTime, mAuctionTitle, mAuctionNoOfVehicles, mAuctionEndDate, mAuctionEndTime,
                        mAuctionType, mAuctionGoingCount, mAuctionIgnoreCount;
                break;

            case 10:
                /*ImageView mUserPic, mVehicleImage;
                ImageButton mShareAutokatta, mShareOther, mCall, mLike, mVehicleFavourite;
                TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                        mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikesTxt, mSharesTxt;*/
                break;

            case 11:
                ImageView mSharePic;
                ImageButton mShareFavourite;
                TextView mShareActionName, mShareActionTime, mShareProfileName, mShareProfileLocation, mShareProfileWorkAt,
                        mShareProfileWebSite;
                TextView mShareStoreName, mShareType, mShareLocation, mShareWebSite, mShareTiming, mShareWorkingDay;
                TextView mShareProductName, mShareProductType, mShareServiceName, mShareServiceType;
                TextView mShareTitle, mSharePrice, mShareBrand, mShareModel, mShareYear, mShareKmsHrs, mShareRto, mShareRegistrationNo, mShareVehicleLocation;
                TextView mShareMySearchCategory, mShareMySearchBrand, mShareMySearchModel, mShareMySearchPrice, mShareMySearchYear,
                        mShareDateOfSearch, mShareMySearchLeads;
                TextView mShareAuctionName, mShareAuctionNoOfVehicles, mShareAuctionEndDate, mShareAuctionEndTime, mShareAuctionType,
                        mShareAuctionGoingCount, mShareAuctionIgnoreCount;
                TextView mShareStatus;
                break;
        }
    }


    private void call(String otherContact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + otherContact));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success_follow")) {
                CustomToast.customToast(mActivity, "Following");
                /*mFollow.setVisibility(View.GONE);
                mUnFollow.setVisibility(View.VISIBLE);
                mFolllowstr = "yes";*/
            } else if (str.equals("success_unfollow")) {
                CustomToast.customToast(mActivity, "UnFollowing");
                /*mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);
                mFolllowstr = "no";*/
            } else if (str.equals("success_like")) {
                CustomToast.customToast(mActivity, "Liked");
                /*mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);*/
                //mLikestr = "yes";
            } else if (str.equals("success_unlike")) {
                CustomToast.customToast(mActivity, "Unliked");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                //mLikestr = "no";
            }
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "Wall Notification Adapter");
            error.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
