package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.FavouriteAllResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.ShareWithinAppActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

/**
 * Created by ak-003 on 20/4/17.
 */

public class FavouriteNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<FavouriteAllResponse> notificationList = new ArrayList<>();
    private int storelikecountint;
    private String mLoginContact = "";
    private int profile_likecountint, profile_followcountint, product_likecountint, service_likecountint, store_likecountint,
            store_followcountint, store_sharecountint, vehicle_likecountint, vehicle_followcountint, vehicle_sharecountint;
    private ApiCall mApiCall;

    public FavouriteNotificationAdapter(Activity mActivity, List<FavouriteAllResponse> responseList) {
        this.mActivity = mActivity;
        notificationList = responseList;
        mApiCall = new ApiCall(mActivity, this);
        mLoginContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
    }

    /*
    Profile Notification Class...
     */
    private static class ProfileNotifications extends RecyclerView.ViewHolder {
        CardView mProfileCardView;
        ImageView mProfilePic, mProfileImage;
        ImageButton mShareAutokatta, mCall, mLike, mUnlike, mDelete, mFav;
        TextView mProfileAction, mActionTime, mProfileTitle, mUserName, mProfileWorkAt, mProfileWebSite, mLocation,
                mFollowCount, mLikes, mShares;
        RelativeLayout mRelativeLike;

        private ProfileNotifications(View profileView) {
            super(profileView);
            mProfileCardView = (CardView) profileView.findViewById(R.id.profile_card_view);
            mProfilePic = (ImageView) profileView.findViewById(R.id.pro_pic);
            mProfileImage = (ImageView) profileView.findViewById(R.id.profile_image);
            mProfileAction = (TextView) profileView.findViewById(R.id.profile_name);
            mActionTime = (TextView) profileView.findViewById(R.id.profile_time);
            mProfileTitle = (TextView) profileView.findViewById(R.id.profile_title);
            mUserName = (TextView) profileView.findViewById(R.id.username);
            mProfileWorkAt = (TextView) profileView.findViewById(R.id.profileworkat);
            mProfileWebSite = (TextView) profileView.findViewById(R.id.profilewebsite);
            mLocation = (TextView) profileView.findViewById(R.id.profilelocation);
            mFollowCount = (TextView) profileView.findViewById(R.id.followcnt);
            mLikes = (TextView) profileView.findViewById(R.id.likes);
            mShares = (TextView) profileView.findViewById(R.id.share);
            mShareAutokatta = (ImageButton) profileView.findViewById(R.id.share_autokatta);
            mCall = (ImageButton) profileView.findViewById(R.id.call);
            mLike = (ImageButton) profileView.findViewById(R.id.like);
            mUnlike = (ImageButton) profileView.findViewById(R.id.unlike);
            mFav = (ImageButton) profileView.findViewById(R.id.profile_favourite);
            mDelete = (ImageButton) profileView.findViewById(R.id.profile_unfavourite);
            mRelativeLike = (RelativeLayout) profileView.findViewById(R.id.rlLike);
        }
    }

    /*
    Store Notification Class...
     */
    private static class StoreNotifications extends RecyclerView.ViewHolder {
        CardView mStoreCardView;
        ImageView mProfilePic, mStoreImage;
        ImageButton mStoreAutokattaShare, mCall, mLike, mUnlike, mFollow, mUnfollow, mStoreFav, mStoreUnfav;
        RatingBar mStoreRating;
        TextView mStoreActionName, mActionTime, mStoreName, mStoreCategory, mStoreType, mStoreWebSite, mStoreTiming,
                mStoreWorkingDay, mStoreLocation, mFollowCount, mLikes, mShares;
        RelativeLayout mRelativeLike;

        private StoreNotifications(View storeView) {
            super(storeView);
            mStoreCardView = (CardView) storeView.findViewById(R.id.store_card_view);
            mProfilePic = (ImageView) storeView.findViewById(R.id.store_pic);
            mStoreImage = (ImageView) storeView.findViewById(R.id.store_image);

            mStoreAutokattaShare = (ImageButton) storeView.findViewById(R.id.share_autokatta);
            mCall = (ImageButton) storeView.findViewById(R.id.call);
            mLike = (ImageButton) storeView.findViewById(R.id.like);
            mUnlike = (ImageButton) storeView.findViewById(R.id.unlike);
            mFollow = (ImageButton) storeView.findViewById(R.id.follow_store);
            mUnfollow = (ImageButton) storeView.findViewById(R.id.unfollow_store);
            mStoreRating = (RatingBar) storeView.findViewById(R.id.store_rating);
            mStoreFav = (ImageButton) storeView.findViewById(R.id.store_favourite);
            mStoreUnfav = (ImageButton) storeView.findViewById(R.id.store_unfavourite);

            mStoreActionName = (TextView) storeView.findViewById(R.id.store_action_names);
            mActionTime = (TextView) storeView.findViewById(R.id.store_action_time);
            mStoreName = (TextView) storeView.findViewById(R.id.store_name);
            mStoreCategory = (TextView) storeView.findViewById(R.id.store_category);
            mStoreType = (TextView) storeView.findViewById(R.id.store_type);
            mStoreWebSite = (TextView) storeView.findViewById(R.id.store_website);
            mStoreTiming = (TextView) storeView.findViewById(R.id.store_time);
            mStoreWorkingDay = (TextView) storeView.findViewById(R.id.store_working_day);
            mStoreLocation = (TextView) storeView.findViewById(R.id.store_location);
            mFollowCount = (TextView) storeView.findViewById(R.id.followcnt);
            mLikes = (TextView) storeView.findViewById(R.id.likes);
            mShares = (TextView) storeView.findViewById(R.id.share);
            mRelativeLike = (RelativeLayout) storeView.findViewById(R.id.rlLike);
        }
    }

    /*
    Group Notification Class...
     */
    private static class GroupNotifications extends RecyclerView.ViewHolder {
        CardView mGroupCardView;
        ImageView mUserPic, mGroupImage;
        ImageButton mGroupFavourite, mDelete;
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
            mDelete = (ImageButton) groupView.findViewById(R.id.group_unfavourite);
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
        ImageButton mVehicleAutokattaShare, mCall, mVehicleLike, mVehicleUnlike, mVehicleFavourite, mVehicleUnfav,
                mFollow, mUnfollow;
        TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikes, mShares, mFollowCount;
        RelativeLayout mRelativeLike;

        private VehicleNotifications(View upVehicleView) {
            super(upVehicleView);
            mVehicleCardView = (CardView) upVehicleView.findViewById(R.id.vehicle_card_view);
            mUserPic = (ImageView) upVehicleView.findViewById(R.id.profile_pic);
            mActionTime = (TextView) upVehicleView.findViewById(R.id.action_time);
            mActionName = (TextView) upVehicleView.findViewById(R.id.action_name);
            mVehicleImage = (ImageView) upVehicleView.findViewById(R.id.vehicle_image);

            mVehicleAutokattaShare = (ImageButton) upVehicleView.findViewById(R.id.share_autokatta);
            mCall = (ImageButton) upVehicleView.findViewById(R.id.call);
            mVehicleLike = (ImageButton) upVehicleView.findViewById(R.id.like);
            mVehicleUnlike = (ImageButton) upVehicleView.findViewById(R.id.unlike);
            mVehicleFavourite = (ImageButton) upVehicleView.findViewById(R.id.vehicle_favourite);
            mVehicleUnfav = (ImageButton) upVehicleView.findViewById(R.id.vehicle_unfavourite);
            mFollow = (ImageButton) upVehicleView.findViewById(R.id.follow_vehicle);
            mUnfollow = (ImageButton) upVehicleView.findViewById(R.id.unfollow_vehicle);


            mVehicleRegistration = (TextView) upVehicleView.findViewById(R.id.vehicle_registration);
            mVehicleName = (TextView) upVehicleView.findViewById(R.id.vehicle_name);
            mVehiclePrice = (TextView) upVehicleView.findViewById(R.id.vehicle_price);
            mVehicleBrand = (TextView) upVehicleView.findViewById(R.id.vehicle_brand);
            mVehicleModel = (TextView) upVehicleView.findViewById(R.id.vehicle_model);
            mVehicleYearOfMfg = (TextView) upVehicleView.findViewById(R.id.vehicle_year_of_mfg);
            mVehicleKmsHrs = (TextView) upVehicleView.findViewById(R.id.vehicle_kms_hrs);
            mVehicleLocation = (TextView) upVehicleView.findViewById(R.id.vehicle_locations);
            mRtoCity = (TextView) upVehicleView.findViewById(R.id.vehicle_rto_city);
            mLikes = (TextView) upVehicleView.findViewById(R.id.likes);
            mShares = (TextView) upVehicleView.findViewById(R.id.share);
            mFollowCount = (TextView) upVehicleView.findViewById(R.id.followcnt);
            mRelativeLike = (RelativeLayout) upVehicleView.findViewById(R.id.rlLike);
        }
    }

    /*
    Product Notification Class...
     */
    private static class ProductNotifications extends RecyclerView.ViewHolder {
        CardView mProductCardView;
        ImageView mUserPic, mProductImage;
        ImageButton mProductAutokattaShare, mProductCall, mProductLike, mProductFav, mDelete,
                mProductUnlike;
        RatingBar mProductRating;
        TextView mProductActionName, mProductActionTime, mProductTitle, mProductName, mProductType, mLikes, mShares;
        RelativeLayout mRelativeLike;

        private ProductNotifications(View productView) {
            super(productView);
            mProductCardView = (CardView) productView.findViewById(R.id.product_card_view);
            mUserPic = (ImageView) productView.findViewById(R.id.product_pro_pic);
            mProductImage = (ImageView) productView.findViewById(R.id.product_image);

            mProductAutokattaShare = (ImageButton) productView.findViewById(R.id.share_autokatta);
            mProductCall = (ImageButton) productView.findViewById(R.id.call);
            mProductLike = (ImageButton) productView.findViewById(R.id.like);
            mProductUnlike = (ImageButton) productView.findViewById(R.id.unlike);
            mProductRating = (RatingBar) productView.findViewById(R.id.product_rating);

            mProductActionName = (TextView) productView.findViewById(R.id.product_action_names);
            mProductActionTime = (TextView) productView.findViewById(R.id.product_action_time);
            mProductTitle = (TextView) productView.findViewById(R.id.product_title);
            mProductName = (TextView) productView.findViewById(R.id.product_name);
            mProductType = (TextView) productView.findViewById(R.id.product_type);
            mLikes = (TextView) productView.findViewById(R.id.likes);
            mShares = (TextView) productView.findViewById(R.id.share);
            mProductFav = (ImageButton) productView.findViewById(R.id.product_favourite);
            mDelete = (ImageButton) productView.findViewById(R.id.product_unfavourite);
            mRelativeLike = (RelativeLayout) productView.findViewById(R.id.rlLike);
        }
    }

    /*
    Service Notification Class...
     */
    private static class ServiceNotifications extends RecyclerView.ViewHolder {
        CardView mServiceCardView;
        ImageView mUserPic, mServiceImage;
        ImageButton mServiceAutokattaShare, mServiceCall, mServiceLike, mServiceUnlike, mServiceFavourite, mDelete;
        RatingBar mServiceRating;
        TextView mServiceActionName, mServiceActionTime, mServiceTitle, mServiceName, mServiceType, mLikes, mShares;
        RelativeLayout mRelativeLike;

        private ServiceNotifications(View serviceView) {
            super(serviceView);
            mServiceCardView = (CardView) serviceView.findViewById(R.id.service_card_view);
            mUserPic = (ImageView) serviceView.findViewById(R.id.service_pro_pic);
            mServiceImage = (ImageView) serviceView.findViewById(R.id.service_image);

            mServiceAutokattaShare = (ImageButton) serviceView.findViewById(R.id.share_autokatta);
            mServiceCall = (ImageButton) serviceView.findViewById(R.id.call);
            mServiceLike = (ImageButton) serviceView.findViewById(R.id.like);
            mServiceUnlike = (ImageButton) serviceView.findViewById(R.id.unlike);
            mServiceRating = (RatingBar) serviceView.findViewById(R.id.service_rating);

            mServiceActionName = (TextView) serviceView.findViewById(R.id.service_action_names);
            mServiceActionTime = (TextView) serviceView.findViewById(R.id.service_action_time);
            mServiceTitle = (TextView) serviceView.findViewById(R.id.service_title);
            mServiceName = (TextView) serviceView.findViewById(R.id.service_name);
            mServiceType = (TextView) serviceView.findViewById(R.id.service_type);
            mLikes = (TextView) serviceView.findViewById(R.id.likes);
            mShares = (TextView) serviceView.findViewById(R.id.share);
            mServiceFavourite = (ImageButton) serviceView.findViewById(R.id.service_favourite);
            mDelete = (ImageButton) serviceView.findViewById(R.id.service_unfavourite);
            mRelativeLike = (RelativeLayout) serviceView.findViewById(R.id.rlLike);
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
        ImageView mUserPic;
        ImageButton mSearchAutokattaShare, mCall, mSearchLike, mSearchUnlike, mSearchFavorite, mSearchUnfav;
        TextView mSearchActionName, mSearchActionTime, mSearchCategory, mSearchBrand, mSearchModel, mSearchPrice, mSearchYear,
                mSearchDate, mSearchLeads;
        RelativeLayout mRelativeLike;

        private SearchNotifications(View searchView) {
            super(searchView);
            mSearchCardView = (CardView) searchView.findViewById(R.id.search_card_view);
            mUserPic = (ImageView) searchView.findViewById(R.id.profile_pro_pic);

            mSearchAutokattaShare = (ImageButton) searchView.findViewById(R.id.share_autokatta);
            mCall = (ImageButton) searchView.findViewById(R.id.call);
            mSearchLike = (ImageButton) searchView.findViewById(R.id.like);
            mSearchUnlike = (ImageButton) searchView.findViewById(R.id.unlike);
            mSearchFavorite = (ImageButton) searchView.findViewById(R.id.search_favourite);
            mSearchUnfav = (ImageButton) searchView.findViewById(R.id.search_unfavourite);

            mSearchActionName = (TextView) searchView.findViewById(R.id.search_action_names);
            mSearchActionTime = (TextView) searchView.findViewById(R.id.search_action_time);
            mSearchCategory = (TextView) searchView.findViewById(R.id.search_category);
            mSearchBrand = (TextView) searchView.findViewById(R.id.search_brand);
            mSearchModel = (TextView) searchView.findViewById(R.id.search_model);
            mSearchPrice = (TextView) searchView.findViewById(R.id.search_price);
            mSearchYear = (TextView) searchView.findViewById(R.id.search_year);
            mSearchDate = (TextView) searchView.findViewById(R.id.search_date);
            mSearchLeads = (TextView) searchView.findViewById(R.id.search_leads);
            mRelativeLike = (RelativeLayout) searchView.findViewById(R.id.rlLike);

        }
    }

    /*
    Active Notification Class...
     */
    private static class ActiveNotifications extends RecyclerView.ViewHolder {
        CardView mAuctionCardView;
        ImageView mUserPic;
        ImageButton mAuctionAutokattaShare, mAuctionFavourite, mAuctionUnfav;
        Button mAuctionGoing, mAuctionIgnore;
        TextView mAuctionActionName, mAuctionActionTime, mAuctionTitle, mAuctionNoOfVehicles, mAuctionEndDate, mAuctionEndTime,
                mAuctionType, mAuctionGoingCount, mAuctionIgnoreCount;

        private ActiveNotifications(View activeView) {
            super(activeView);
            mAuctionCardView = (CardView) activeView.findViewById(R.id.auction_card_view);
            mUserPic = (ImageView) activeView.findViewById(R.id.user_pro_pic);

            mAuctionAutokattaShare = (ImageButton) activeView.findViewById(R.id.share_autokatta);
            mAuctionFavourite = (ImageButton) activeView.findViewById(R.id.active_favourite);
            mAuctionUnfav = (ImageButton) activeView.findViewById(R.id.active_unfavourite);
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
    private static class UploadVehicleNotifications extends RecyclerView.ViewHolder {
        CardView mVehicleCardView;
        ImageView mUserPic, mVehicleImage;
        ImageButton mVehicleAutokattaShare, mCall, mVehicleLike, mVehicleUnlike, mVehicleFavourite, mVehicleUnfav,
                mFollow, mUnfollow;
        TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikes, mShares, mFollowCount;
        RelativeLayout mRelativeLike;

        private UploadVehicleNotifications(View upVehicleView) {
            super(upVehicleView);
            mVehicleCardView = (CardView) upVehicleView.findViewById(R.id.vehicle_card_view);
            mUserPic = (ImageView) upVehicleView.findViewById(R.id.profile_pic);
            mActionTime = (TextView) upVehicleView.findViewById(R.id.action_time);
            mActionName = (TextView) upVehicleView.findViewById(R.id.action_name);
            mVehicleImage = (ImageView) upVehicleView.findViewById(R.id.vehicle_image);

            mVehicleAutokattaShare = (ImageButton) upVehicleView.findViewById(R.id.share_autokatta);
            mCall = (ImageButton) upVehicleView.findViewById(R.id.call);
            mVehicleLike = (ImageButton) upVehicleView.findViewById(R.id.like);
            mVehicleUnlike = (ImageButton) upVehicleView.findViewById(R.id.unlike);
            mVehicleFavourite = (ImageButton) upVehicleView.findViewById(R.id.vehicle_favourite);
            mVehicleUnfav = (ImageButton) upVehicleView.findViewById(R.id.vehicle_unfavourite);
            mFollow = (ImageButton) upVehicleView.findViewById(R.id.follow_vehicle);
            mUnfollow = (ImageButton) upVehicleView.findViewById(R.id.unfollow_vehicle);


            mVehicleRegistration = (TextView) upVehicleView.findViewById(R.id.vehicle_registration);
            mVehicleName = (TextView) upVehicleView.findViewById(R.id.vehicle_name);
            mVehiclePrice = (TextView) upVehicleView.findViewById(R.id.vehicle_price);
            mVehicleBrand = (TextView) upVehicleView.findViewById(R.id.vehicle_brand);
            mVehicleModel = (TextView) upVehicleView.findViewById(R.id.vehicle_model);
            mVehicleYearOfMfg = (TextView) upVehicleView.findViewById(R.id.vehicle_year_of_mfg);
            mVehicleKmsHrs = (TextView) upVehicleView.findViewById(R.id.vehicle_kms_hrs);
            mVehicleLocation = (TextView) upVehicleView.findViewById(R.id.vehicle_locations);
            mRtoCity = (TextView) upVehicleView.findViewById(R.id.vehicle_rto_city);
            mLikes = (TextView) upVehicleView.findViewById(R.id.likes);
            mShares = (TextView) upVehicleView.findViewById(R.id.share);
            mFollowCount = (TextView) upVehicleView.findViewById(R.id.followcnt);
            mRelativeLike = (RelativeLayout) upVehicleView.findViewById(R.id.rlLike);
        }
    }

    /*
    Adding Share Notifications Notification Class...
     */
    private static class ShareNotifications extends RecyclerView.ViewHolder {
        CardView mShareCardView;
        ImageView mUserPic;
        ImageButton mShareFavourite, mShareUnfav;
        TextView mShareActionName, mShareActionTime, mShareProfileName, mShareProfileLocation, mShareProfileWorkAt,
                mShareProfileWebSite, mCaptionData;
        TextView mShareStoreName, mShareType, mShareLocation, mShareWebSite, mShareTiming, mShareWorkingDay;
        TextView mShareProductName, mShareProductType, mShareServiceName, mShareServiceType;
        TextView mShareTitle, mSharePrice, mShareBrand, mShareModel, mShareYear, mShareKmsHrs, mShareRto, mShareRegistrationNo, mShareVehicleLocation;
        TextView mShareMySearchCategory, mShareMySearchBrand, mShareMySearchModel, mShareMySearchPrice, mShareMySearchYear,
                mShareDateOfSearch, mShareMySearchLeads;
        TextView mShareAuctionName, mShareAuctionNoOfVehicles, mShareAuctionEndDate, mShareAuctionEndTime, mShareAuctionType,
                mShareAuctionGoingCount, mShareAuctionIgnoreCount;
        TextView mShareStatus;
        RelativeLayout mProfileRelative, mStoreRelative, mProductRelative, mServiceRelative, mVehicleRelative,
                mMySearchRelative, mAuctionRelative;

        private ShareNotifications(View shareView) {
            super(shareView);
            mShareCardView = (CardView) shareView.findViewById(R.id.share_card_view);
            mUserPic = (ImageView) shareView.findViewById(R.id.user_pro_pic);
            mShareFavourite = (ImageButton) shareView.findViewById(R.id.share_favourite);
            mShareUnfav = (ImageButton) shareView.findViewById(R.id.share_unfavourite);

            mShareActionName = (TextView) shareView.findViewById(R.id.share_action_names);
            mShareActionTime = (TextView) shareView.findViewById(R.id.share_action_time);
            mCaptionData = (TextView) shareView.findViewById(R.id.sharedataview);

    /* profile notification layout*/
            mProfileRelative = (RelativeLayout) shareView.findViewById(R.id.profilerel);
            mShareProfileName = (TextView) shareView.findViewById(R.id.share_profile_name);
            mShareProfileLocation = (TextView) shareView.findViewById(R.id.share_profilelocation);
            mShareProfileWorkAt = (TextView) shareView.findViewById(R.id.share_profileworkat);
            mShareProfileWebSite = (TextView) shareView.findViewById(R.id.share_profilewebsite);

    /* store notification layout*/
            mStoreRelative = (RelativeLayout) shareView.findViewById(R.id.storerel);
            mShareStoreName = (TextView) shareView.findViewById(R.id.share_storename);
            mShareType = (TextView) shareView.findViewById(R.id.share_storetype);
            mShareLocation = (TextView) shareView.findViewById(R.id.share_location);
            mShareWebSite = (TextView) shareView.findViewById(R.id.share_website);
            mShareTiming = (TextView) shareView.findViewById(R.id.share_timing);
            mShareWorkingDay = (TextView) shareView.findViewById(R.id.share_workday);

    /* product notification layout*/
            mProductRelative = (RelativeLayout) shareView.findViewById(R.id.productrel);
            mShareProductName = (TextView) shareView.findViewById(R.id.share_productname);
            mShareProductType = (TextView) shareView.findViewById(R.id.share_producttype);

     /* service notification layout*/
            mServiceRelative = (RelativeLayout) shareView.findViewById(R.id.servicerel);
            mShareServiceName = (TextView) shareView.findViewById(R.id.share_servicename);
            mShareServiceType = (TextView) shareView.findViewById(R.id.share_servicetype);

    /* vehicle notification layout*/
            mVehicleRelative = (RelativeLayout) shareView.findViewById(R.id.vehiclerel);
            mShareTitle = (TextView) shareView.findViewById(R.id.share_title);
            mSharePrice = (TextView) shareView.findViewById(R.id.share_price);
            mShareBrand = (TextView) shareView.findViewById(R.id.share_brand);
            mShareModel = (TextView) shareView.findViewById(R.id.share_model);
            mShareYear = (TextView) shareView.findViewById(R.id.share_year);
            mShareKmsHrs = (TextView) shareView.findViewById(R.id.share_km_hrs);
            mShareRto = (TextView) shareView.findViewById(R.id.share_RTO);
            mShareRegistrationNo = (TextView) shareView.findViewById(R.id.share_registrationNo);
            mShareVehicleLocation = (TextView) shareView.findViewById(R.id.share_vehilocation);

    /* mySearch notification layout*/
            mMySearchRelative = (RelativeLayout) shareView.findViewById(R.id.searchrel);
            mShareMySearchCategory = (TextView) shareView.findViewById(R.id.share_mysearch_category);
            mShareMySearchBrand = (TextView) shareView.findViewById(R.id.share_mysearch_brand);
            mShareMySearchModel = (TextView) shareView.findViewById(R.id.share_mysearch_model);
            mShareMySearchPrice = (TextView) shareView.findViewById(R.id.share_mysearch_price);
            mShareMySearchYear = (TextView) shareView.findViewById(R.id.share_mysearch_year);
            mShareDateOfSearch = (TextView) shareView.findViewById(R.id.share_searchdate);
            mShareMySearchLeads = (TextView) shareView.findViewById(R.id.share_buyerleads);

    /* auction notification layout*/
            mAuctionRelative = (RelativeLayout) shareView.findViewById(R.id.auctionrel);
            mShareAuctionName = (TextView) shareView.findViewById(R.id.share_auc_name);
            mShareAuctionNoOfVehicles = (TextView) shareView.findViewById(R.id.share_auc_noofvehicle);
            mShareAuctionEndDate = (TextView) shareView.findViewById(R.id.share_auc_enddate);
            mShareAuctionEndTime = (TextView) shareView.findViewById(R.id.share_auc_endtime);
            mShareAuctionType = (TextView) shareView.findViewById(R.id.share_auc_type);
            mShareAuctionGoingCount = (TextView) shareView.findViewById(R.id.share_going_cnt);
            mShareAuctionIgnoreCount = (TextView) shareView.findViewById(R.id.share_ignore_cnt);

    /* status notification layout*/
            mAuctionRelative = (RelativeLayout) shareView.findViewById(R.id.auctionrel);
            mShareStatus = (TextView) shareView.findViewById(R.id.share_statustxt);
        }
    }

    /*
    Buyer Notification Class...
     */
    private static class BuyerNotifications extends RecyclerView.ViewHolder {
        ImageView buyer_lead_image, callbuyer, favouritebuyer, deleteFav;
        TextView mBuyerUserName, mBuyerLocation, mItemNameCity, buyertext;
        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBoxRc, checkBoxIns,
                checkBoxHpcap, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBoxRcRight,
                checkBoxINSRight, checkBoxHPcapRight;

        private BuyerNotifications(View buyerView) {
            super(buyerView);

            mBuyerUserName = (TextView) buyerView.findViewById(R.id.buyerusername);
            mBuyerLocation = (TextView) buyerView.findViewById(R.id.buyerlocation);
            buyer_lead_image = (ImageView) buyerView.findViewById(R.id.buyer_lead_image);
            callbuyer = (ImageView) buyerView.findViewById(R.id.callbuyer);
            favouritebuyer = (ImageView) buyerView.findViewById(R.id.favouritebuyer);
            deleteFav = (ImageView) buyerView.findViewById(R.id.deletefav);

            mItemNameCity = (TextView) buyerView.findViewById(R.id.namecity);
            checkBox1 = (CheckBox) buyerView.findViewById(R.id.checkBox1);
            checkBox2 = (CheckBox) buyerView.findViewById(R.id.checkBox2);
            checkBox3 = (CheckBox) buyerView.findViewById(R.id.checkBox3);
            checkBox4 = (CheckBox) buyerView.findViewById(R.id.checkBox4);
            checkBox5 = (CheckBox) buyerView.findViewById(R.id.checkBox5);
            checkBoxRc = (CheckBox) buyerView.findViewById(R.id.checkBoxRc);
            checkBoxIns = (CheckBox) buyerView.findViewById(R.id.checkBoxINS);
            checkBoxHpcap = (CheckBox) buyerView.findViewById(R.id.checkBoxHPcap);

            checkBox6 = (CheckBox) buyerView.findViewById(R.id.checkBox6);
            checkBox7 = (CheckBox) buyerView.findViewById(R.id.checkBox7);
            checkBox8 = (CheckBox) buyerView.findViewById(R.id.checkBox8);
            checkBox9 = (CheckBox) buyerView.findViewById(R.id.checkBox9);
            checkBox10 = (CheckBox) buyerView.findViewById(R.id.checkBox10);
            checkBoxRcRight = (CheckBox) buyerView.findViewById(R.id.checkBoxRcRight);
            checkBoxINSRight = (CheckBox) buyerView.findViewById(R.id.checkBoxINSRight);
            checkBoxHPcapRight = (CheckBox) buyerView.findViewById(R.id.checkBoxHPcapRight);

            buyertext = (TextView) buyerView.findViewById(R.id.buyertext);
        }
    }


    /*
    Seller Notification Class...
     */
    private static class SellerNotifications extends RecyclerView.ViewHolder {
        ImageView mCallimg, mFavimg, deleteFav;
        TextView mUserName, mVehicleCount, mDateTime, lastcall, sellertext, Title;
        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBoxRc, checkBoxIns,
                checkBoxHp, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBoxRcright,
                checkBoxInsRight, checkBoxHpRight;
        ViewFlipper mViewFlippersell;

        private SellerNotifications(View sellerView) {
            super(sellerView);

            mViewFlippersell = (ViewFlipper) sellerView.findViewById(R.id.sellvehicalimgflicker);
            mUserName = (TextView) sellerView.findViewById(R.id.username);
            mVehicleCount = (TextView) sellerView.findViewById(R.id.vehiclecount);
            mDateTime = (TextView) sellerView.findViewById(R.id.addon);
            lastcall = (TextView) sellerView.findViewById(R.id.lastcall);
            Title = (TextView) sellerView.findViewById(R.id.title);

            mCallimg = (ImageView) sellerView.findViewById(R.id.sellcallimg);
            mFavimg = (ImageView) sellerView.findViewById(R.id.sellfevimg);
            deleteFav = (ImageView) sellerView.findViewById(R.id.deletefav);

            checkBox1 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox1);
            checkBox2 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox2);
            checkBox3 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox3);
            checkBox4 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox4);
            checkBox5 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox5);
            checkBoxRc = (CheckBox) sellerView.findViewById(R.id.sellcheckBoxRc);
            checkBoxIns = (CheckBox) sellerView.findViewById(R.id.sellcheckBoxIns);
            checkBoxHp = (CheckBox) sellerView.findViewById(R.id.sellcheckBoxHp);

            checkBox6 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox6);
            checkBox7 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox7);
            checkBox8 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox8);
            checkBox9 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox9);
            checkBox10 = (CheckBox) sellerView.findViewById(R.id.sellcheckBox10);
            checkBoxRcright = (CheckBox) sellerView.findViewById(R.id.sellcheckBoxRcRight);
            checkBoxInsRight = (CheckBox) sellerView.findViewById(R.id.sellcheckBoxInsRight);
            checkBoxHpRight = (CheckBox) sellerView.findViewById(R.id.sellcheckBoxHpRight);

            sellertext = (TextView) sellerView.findViewById(R.id.sellertext);
        }
    }

    /*
   My Search Class...
    */
    private static class MySearch extends RecyclerView.ViewHolder {
        TextView mCategory, mBrand, mModel, mPrice, mYear, mSearchDate, mLeads, mStopdate;
        ImageView editImg, deleteImg, favImg, unfavImg, share, mAutokattaShare;
        Button Stopsearch, Startsearch;
        RelativeLayout relativeLayout, relativeSearchTitle;
        CardView cardView;

        private MySearch(View searchView) {
            super(searchView);
            mCategory = (TextView) itemView.findViewById(R.id.mysearch_category);
            mBrand = (TextView) itemView.findViewById(R.id.mysearch_brand);
            mModel = (TextView) itemView.findViewById(R.id.mysearch_model);
            mPrice = (TextView) itemView.findViewById(R.id.mysearch_price);
            mYear = (TextView) itemView.findViewById(R.id.mysearch_year);
            mSearchDate = (TextView) itemView.findViewById(R.id.searchdate);
            mLeads = (TextView) itemView.findViewById(R.id.buyerleads);

            editImg = (ImageView) itemView.findViewById(R.id.editpen);
            deleteImg = (ImageView) itemView.findViewById(R.id.deletevehi);
            favImg = (ImageView) itemView.findViewById(R.id.favsearch);
            unfavImg = (ImageView) itemView.findViewById(R.id.unfavsearch);
            share = (ImageView) itemView.findViewById(R.id.sharesearch);
            mAutokattaShare = (ImageView) itemView.findViewById(R.id.sharesearch1);
            Stopsearch = (Button) itemView.findViewById(R.id.stopsearch);
            Startsearch = (Button) itemView.findViewById(R.id.startsearch);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relbutton);
            relativeSearchTitle = (RelativeLayout) itemView.findViewById(R.id.reltitlerow);
            mStopdate = (TextView) itemView.findViewById(R.id.txtdate);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            share.setVisibility(View.GONE);


        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        //return position % 2 * 2;
        return Integer.parseInt(notificationList.get(position).getLayoutNo());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        Log.i("Layout", "BindHolder" + viewType);
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
                return new UploadVehicleNotifications(mView);

            case 11:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_adding_share_notifications, parent, false);
                return new ShareNotifications(mView);

            case 111:
                //buyer
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_buyer_notification, parent, false);
                return new BuyerNotifications(mView);

            case 112:
                //seller
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_seller_notification, parent, false);
                return new SellerNotifications(mView);

            case 113:
                //search
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_search_adapter, parent, false);
                return new MySearch(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final FavouriteAllResponse objAllResponse = notificationList.get(position);
        Log.i("Layout", "BindHolder" + holder.getItemViewType());
        switch (holder.getItemViewType()) {

            case 1:
//                ProfileNotifications mProfileHolder = (ProfileNotifications) holder;
                final ProfileNotifications mProfileHolder = (ProfileNotifications) holder;
//                Log.i("Wall", "Profile-LayType ->" + notificationList.get(position).getLayoutType());
//
//                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction"))
//                    mProfileHolder.mCall.setVisibility(View.GONE);
//                else
//                    mProfileHolder.mCall.setVisibility(View.VISIBLE);
                mProfileHolder.mDelete.setImageResource(R.drawable.ic_delete);

                mProfileHolder.mProfileAction.setText(notificationList.get(position).getSendername() + " "
                        + notificationList.get(position).getAction() + " " + notificationList.get(position).getReceivername() + " " + "Profile");

                mProfileHolder.mActionTime.setText(notificationList.get(position).getDatetime());
                mProfileHolder.mUserName.setText(notificationList.get(position).getSendername());
                mProfileHolder.mProfileWorkAt.setText(notificationList.get(position).getSenderprofession());
                mProfileHolder.mProfileWebSite.setText(notificationList.get(position).getSenderwebsite());
                mProfileHolder.mLocation.setText(notificationList.get(position).getSendercity());
                mProfileHolder.mFollowCount.setText("Followers(" + notificationList.get(position).getSenderfollowcount() + ")");
                mProfileHolder.mLikes.setText("Likes(" + notificationList.get(position).getSenderlikecount() + ")");

                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mProfileHolder.mProfilePic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mProfileHolder.mProfileImage);
                }

                mProfileHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mProfileHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });

     /* Like & Unlike Functionality */
                if (notificationList.get(position).getSenderlikestatus().equalsIgnoreCase("yes")) {
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
                        mApiCall.UnLike(mLoginContact, otherContact, "1", 0, 0, 0, 0, 0, 0, 0);
                        profile_likecountint = notificationList.get(mProfileHolder.getAdapterPosition()).getSenderlikecount();
                        profile_likecountint = profile_likecountint - 1;
                        mProfileHolder.mLikes.setText("Likes(" + profile_likecountint + ")");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderlikecount(profile_likecountint);
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderlikestatus("no");
                    }
                });

                mProfileHolder.mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mProfileHolder.getAdapterPosition()).getSender();
                        mProfileHolder.mUnlike.setVisibility(View.GONE);
                        mProfileHolder.mLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "1", 0, 0, 0, 0, 0, 0, 0);
                        profile_likecountint = notificationList.get(mProfileHolder.getAdapterPosition()).getSenderlikecount();
                        profile_likecountint = profile_likecountint + 1;
                        mProfileHolder.mLikes.setText("Likes(" + profile_likecountint + ")");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderlikecount(profile_likecountint);
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderlikestatus("yes");
                    }
                });

                mProfileHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mProfileHolder.getAdapterPosition()).getId();
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        //notificationList.get(mGroupHolder.getAdapterPosition()).setMyFavStatus("no");
                        notificationList.remove(mProfileHolder.getAdapterPosition());
                        notifyItemRemoved(mProfileHolder.getAdapterPosition());
                        notifyItemRangeChanged(mProfileHolder.getAdapterPosition(), notificationList.size());
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
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPic() + "=" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderlikecount() + "=" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderfollowcount();

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
                                if (notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPic().equalsIgnoreCase("") ||
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPic().equalsIgnoreCase(null) ||
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPic().equalsIgnoreCase("null")) {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    imagename = "http://autokatta.com/mobile/profile_profile_pics/" + notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPic();
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
                                        "Profession : " + mProfileHolder.mProfileWorkAt.getText().toString() + "\n" +
                                        "Website : " + mProfileHolder.mProfileWebSite.getText().toString() + "\n" +
                                        "Address : " + mProfileHolder.mLocation.getText().toString() /*+ "\n" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount() + "\n"+
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderFollowCount()*/;

                                System.out.println("all sender detailssss======Other " + allProfileDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my profile on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/profile/other/" + notificationList.get(mProfileHolder.getAdapterPosition()).getSender()
                                        + "\n" + "\n" + allProfileDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));


                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allProfileDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }

                });
                break;

            case 2:


                final StoreNotifications mStoreHolder = (StoreNotifications) holder;
                /*Log.i("Wall", "Store-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    mStoreHolder.mCall.setVisibility(View.GONE);
                    mStoreHolder.mRelativeLike.setVisibility(View.GONE);

                } else {
                    mStoreHolder.mCall.setVisibility(View.VISIBLE);
                    mStoreHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }*/

                mStoreHolder.mStoreUnfav.setImageResource(R.drawable.ic_delete);

                mStoreHolder.mStoreActionName.setText(notificationList.get(position).getSendername() + " "
                        + notificationList.get(position).getAction() + " " + notificationList.get(position).getReceivername() + " "
                        + notificationList.get(position).getStoreName() + " " + "Store");

                mStoreHolder.mActionTime.setText(notificationList.get(position).getDatetime());
                mStoreHolder.mStoreName.setText(notificationList.get(position).getStoreName());
                mStoreHolder.mStoreCategory.setText(notificationList.get(position).getStoreCategory());
                mStoreHolder.mStoreType.setText(notificationList.get(position).getStoreType());
                mStoreHolder.mStoreWebSite.setText(notificationList.get(position).getStoreWebsite());
                mStoreHolder.mStoreTiming.setText(notificationList.get(position).getStoretiming());
                mStoreHolder.mStoreWorkingDay.setText(notificationList.get(position).getWorkingDays());
                mStoreHolder.mStoreLocation.setText(notificationList.get(position).getStoreLocation());
                mStoreHolder.mFollowCount.setText("Followers(" + notificationList.get(position).getStorefollowcount() + ")");
                mStoreHolder.mLikes.setText("Likes(" + notificationList.get(position).getStorelikecount() + ")");
                mStoreHolder.mShares.setText("Shares(" + notificationList.get(position).getStoreShareCount() + ")");
                mStoreHolder.mStoreRating.setRating(notificationList.get(position).getStorerating());

         /* Sender Profile Pic */

                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mStoreHolder.mProfilePic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mStoreHolder.mProfilePic);
                }

        /* Store pic */

                if (notificationList.get(position).getStoreImage() == null ||
                        notificationList.get(position).getStoreImage().equals("") ||
                        notificationList.get(position).getStoreImage().equals("null")) {
                    mStoreHolder.mStoreImage.setBackgroundResource(R.drawable.store);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getStoreImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mStoreHolder.mProfilePic);
                }

                mStoreHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });

     /* Like & Unlike Functionality */

                if (notificationList.get(position).getStorelikestatus().equalsIgnoreCase("yes")) {
                    mStoreHolder.mLike.setVisibility(View.VISIBLE);
                    mStoreHolder.mUnlike.setVisibility(View.GONE);
                } else {
                    mStoreHolder.mUnlike.setVisibility(View.VISIBLE);
                    mStoreHolder.mLike.setVisibility(View.GONE);
                }

                mStoreHolder.mLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreId();
                        mStoreHolder.mLike.setVisibility(View.GONE);
                        mStoreHolder.mUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "2", storeId, 0, 0, 0, 0, 0, 0);
                        store_likecountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStorelikecount();
                        store_likecountint = store_likecountint - 1;
                        mStoreHolder.mLikes.setText("Likes(" + store_likecountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorelikecount(store_likecountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorelikestatus("no");
                    }
                });

                mStoreHolder.mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreId();
                        mStoreHolder.mUnlike.setVisibility(View.GONE);
                        mStoreHolder.mLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "2", storeId, 0, 0, 0, 0, 0, 0);
                        store_likecountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStorelikecount();
                        store_likecountint = store_likecountint + 1;
                        mStoreHolder.mLikes.setText("Likes(" + store_likecountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorelikecount(store_likecountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorelikestatus("yes");
                    }
                });

        /* Follow & UnfollowFunctionality */

                if (notificationList.get(position).getStorefollowstatus().equalsIgnoreCase("yes")) {
                    mStoreHolder.mFollow.setVisibility(View.VISIBLE);
                    mStoreHolder.mUnfollow.setVisibility(View.GONE);
                } else {
                    mStoreHolder.mUnfollow.setVisibility(View.VISIBLE);
                    mStoreHolder.mFollow.setVisibility(View.GONE);
                }

                mStoreHolder.mFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfollow web service
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreId();
                        mStoreHolder.mFollow.setVisibility(View.GONE);
                        mStoreHolder.mUnfollow.setVisibility(View.VISIBLE);

                        mApiCall.UnFollow(mLoginContact, otherContact, "2", storeId, 0, 0, 0);
                        store_followcountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStorefollowcount();
                        store_followcountint--;
                        mStoreHolder.mFollowCount.setText("Followers(" + store_followcountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorefollowcount(store_followcountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorefollowstatus("no");

                    }
                });

                mStoreHolder.mUnfollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Follow web service
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreId();
                        mStoreHolder.mUnfollow.setVisibility(View.GONE);
                        mStoreHolder.mFollow.setVisibility(View.VISIBLE);

                        mApiCall.Follow(mLoginContact, otherContact, "2", storeId, 0, 0, 0);
                        store_followcountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStorefollowcount();
                        store_followcountint = store_followcountint + 1;
                        mStoreHolder.mFollowCount.setText("Followers(" + store_followcountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorefollowcount(store_followcountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStorefollowstatus("yes");

                    }
                });

                mStoreHolder.mStoreUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mStoreHolder.getAdapterPosition()).getId();
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.remove(mStoreHolder.getAdapterPosition());
                        notifyItemRemoved(mStoreHolder.getAdapterPosition());
                        notifyItemRangeChanged(mStoreHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                mStoreHolder.mStoreAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allStoreDetails = mStoreHolder.mStoreName.getText().toString() + "=" +
                                        mStoreHolder.mStoreWebSite.getText().toString() + "=" +
                                        mStoreHolder.mStoreTiming.getText().toString() + "=" +
                                        mStoreHolder.mStoreWorkingDay.getText().toString() + "=" +
                                        mStoreHolder.mStoreType.getText().toString() + "=" +
                                        mStoreHolder.mStoreLocation.getText().toString() + "=" +
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage() + "=" +
                                        mStoreHolder.mStoreRating.getRating() + "=" +
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getStorelikecount() + "=" +
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getStorefollowcount();

                                System.out.println("all store detailssss======Auto " + allStoreDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allStoreDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_store_id", notificationList.get(mStoreHolder.getAdapterPosition()).getStoreId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "store").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage().equalsIgnoreCase("") ||
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage().equalsIgnoreCase(null) ||
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage().equalsIgnoreCase("null")) {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage();
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

                                String allStoreDetails = "Store name : " + mStoreHolder.mStoreName.getText().toString() + "\n" +
                                        "Store type : " + mStoreHolder.mStoreType.getText().toString() + "\n" +
                                        "Ratings : " + mStoreHolder.mStoreRating.getRating() + "\n" +
                                        "Likes : " + notificationList.get(mStoreHolder.getAdapterPosition()).getStorelikecount() + "\n" +
                                        "Website : " + mStoreHolder.mStoreWebSite.getText().toString() + "\n" +
                                        "Timing : " + mStoreHolder.mStoreTiming.getText().toString() + "\n" +
                                        "Working Days : " + mStoreHolder.mStoreWorkingDay.getText().toString() + "\n" +
                                        "Location : " + mStoreHolder.mStoreLocation.getText().toString();

                                System.out.println("all store detailssss======Other " + allStoreDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/store/main/" + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreId()
                                        + "/" + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact()
                                        + "\n" + "\n" + allStoreDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));


                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allStoreDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });

                break;

            case 3:
                final GroupNotifications mGroupHolder = (GroupNotifications) holder;

                mGroupHolder.mDelete.setImageResource(R.drawable.ic_delete);

                mGroupHolder.mActionName.setText(notificationList.get(position).getSendername() + " " +
                        notificationList.get(position).getAction() + " " + notificationList.get(position).getReceivername() +
                        " in " + notificationList.get(position).getGroupName()
                        + " group");
                mGroupHolder.mGroupName.setText(notificationList.get(position).getGroupName());
                mGroupHolder.mActionTime.setText(notificationList.get(position).getDatetime());
                mGroupHolder.mGroupMembers.setText(String.valueOf(notificationList.get(position).getGroupMembers()));
                mGroupHolder.mGroupNoOfVehicles.setText(String.valueOf(notificationList.get(position).getGroupVehicles()));
               /* mGroupHolder.mGroupNoOfProducts.setText(String.valueOf(notificationList.get(position)));
                mGroupHolder.mGroupNoOfServices.setText(String.valueOf(notificationList.get(position)));*/

               /* Profile pic */
                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mGroupHolder.mUserPic.setBackgroundResource(R.mipmap.profile);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroupHolder.mUserPic);
                }

               /* Group pic */
                if (notificationList.get(position).getGroupImage() == null ||
                        notificationList.get(position).getGroupImage().equals("") ||
                        notificationList.get(position).getGroupImage().equals("null")) {
                    mGroupHolder.mGroupImage.setBackgroundResource(R.drawable.group);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getGroupImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroupHolder.mGroupImage);
                }

                mGroupHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mGroupHolder.getAdapterPosition()).getId();
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.remove(mGroupHolder.getAdapterPosition());
                        notifyItemRemoved(mGroupHolder.getAdapterPosition());
                        notifyItemRangeChanged(mGroupHolder.getAdapterPosition(), notificationList.size());
                    }
                });


                break;

            case 4:
                final VehicleNotifications mVehicleHolder = (VehicleNotifications) holder;

                mVehicleHolder.mVehicleUnfav.setImageResource(R.drawable.ic_delete);

                mVehicleHolder.mActionName.setText(notificationList.get(position).getSendername() + " "
                        + notificationList.get(position).getAction() + " " + notificationList.get(position).getReceivername() + " "
                        + notificationList.get(position).getUpVehicleTitle() + " " + "Vehicle");

                mVehicleHolder.mActionTime.setText(notificationList.get(position).getDatetime());
                mVehicleHolder.mVehicleName.setText(notificationList.get(position).getUpVehicleTitle());
                mVehicleHolder.mVehicleRegistration.setText(notificationList.get(position).getUpVehicleRegNo());
                mVehicleHolder.mVehiclePrice.setText(notificationList.get(position).getUpVehiclePrice());
                mVehicleHolder.mVehicleBrand.setText(notificationList.get(position).getUpVehicleBrand());
                mVehicleHolder.mVehicleModel.setText(notificationList.get(position).getUpVehicleModel());
                mVehicleHolder.mVehicleYearOfMfg.setText(notificationList.get(position).getUpVehicleManfYear());
                mVehicleHolder.mVehicleLocation.setText(notificationList.get(position).getUpVehicleLocationCity());
                mVehicleHolder.mRtoCity.setText(notificationList.get(position).getUpVehicleRtoCity());
                mVehicleHolder.mFollowCount.setText("Followers(" + notificationList.get(position).getUpVehicleFollowCount() + ")");
                mVehicleHolder.mLikes.setText("Likes(" + notificationList.get(position).getUpVehicleLikeCount() + ")");
                mVehicleHolder.mShares.setText("Shares(" + notificationList.get(position).getUpVehicleShareCount() + ")");
                mVehicleHolder.mVehicleKmsHrs.setText(notificationList.get(position).getUpVehicleKmsRun());

         /* Sender Profile Pic */

                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mVehicleHolder.mUserPic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mVehicleHolder.mUserPic);
                }

        /* Vehicle pic */

                if (notificationList.get(position).getUpVehicleImage() == null ||
                        notificationList.get(position).getUpVehicleImage().equals("") ||
                        notificationList.get(position).getUpVehicleImage().equals("null")) {
                    mVehicleHolder.mVehicleImage.setBackgroundResource(R.drawable.vehiimg);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getUpVehicleImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mVehicleHolder.mVehicleImage);
                }

                mVehicleHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        call(otherContact);
                    }
                });

        /* Like & Unlike Functionality */

                if (notificationList.get(position).getUpVehicleLikeStatus().equalsIgnoreCase("yes")) {
                    mVehicleHolder.mVehicleLike.setVisibility(View.VISIBLE);
                    mVehicleHolder.mVehicleUnlike.setVisibility(View.GONE);
                } else {
                    mVehicleHolder.mVehicleUnlike.setVisibility(View.VISIBLE);
                    mVehicleHolder.mVehicleLike.setVisibility(View.GONE);
                }

                mVehicleHolder.mVehicleLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getVehicleId();
                        mVehicleHolder.mVehicleLike.setVisibility(View.GONE);
                        mVehicleHolder.mVehicleUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "4", 0, 0, vehicleId, 0, 0, 0, 0);
                        vehicle_likecountint = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();
                        vehicle_likecountint = vehicle_likecountint - 1;
                        mVehicleHolder.mLikes.setText("Likes(" + vehicle_likecountint + ")");
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleLikeCount(vehicle_likecountint);
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleLikeStatus("no");
                    }
                });

                mVehicleHolder.mVehicleUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getVehicleId();
                        mVehicleHolder.mVehicleUnlike.setVisibility(View.GONE);
                        mVehicleHolder.mVehicleLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "4", 0, 0, vehicleId, 0, 0, 0, 0);
                        vehicle_likecountint = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();
                        vehicle_likecountint = vehicle_likecountint + 1;
                        mVehicleHolder.mLikes.setText("Likes(" + vehicle_likecountint + ")");
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleLikeCount(vehicle_likecountint);
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleLikeStatus("yes");
                    }
                });


        /* Follow & Unfollow Functionality */

                if (notificationList.get(position).getUpVehicleFollowStatus().equalsIgnoreCase("yes")) {
                    mVehicleHolder.mFollow.setVisibility(View.VISIBLE);
                    mVehicleHolder.mUnfollow.setVisibility(View.GONE);
                } else {
                    mVehicleHolder.mUnfollow.setVisibility(View.VISIBLE);
                    mVehicleHolder.mFollow.setVisibility(View.GONE);
                }

                mVehicleHolder.mFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfollow web service
                        String otherContact = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getVehicleId();
                        mVehicleHolder.mFollow.setVisibility(View.GONE);
                        mVehicleHolder.mUnfollow.setVisibility(View.VISIBLE);

                        mApiCall.UnFollow(mLoginContact, otherContact, "4", 0, vehicleId, 0, 0);
                        vehicle_followcountint = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleFollowCount();
                        vehicle_followcountint--;
                        mVehicleHolder.mFollowCount.setText("Followers(" + vehicle_followcountint + ")");
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleFollowCount(vehicle_followcountint);
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleFollowStatus("no");

                    }
                });

                mVehicleHolder.mUnfollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Follow web service
                        String otherContact = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getVehicleId();
                        mVehicleHolder.mUnfollow.setVisibility(View.GONE);
                        mVehicleHolder.mFollow.setVisibility(View.VISIBLE);

                        mApiCall.Follow(mLoginContact, otherContact, "4", 0, vehicleId, 0, 0);
                        vehicle_followcountint = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleFollowCount();
                        vehicle_followcountint = vehicle_followcountint + 1;
                        mVehicleHolder.mFollowCount.setText("Followers(" + vehicle_followcountint + ")");
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleFollowCount(vehicle_followcountint);
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setUpVehicleFollowStatus("yes");

                    }
                });

                mVehicleHolder.mVehicleUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Remove Favorite web service
                        int SearchId = notificationList.get(mVehicleHolder.getAdapterPosition()).getSearchId();
                        mApiCall.removeFromFavorite(mLoginContact, "", SearchId, "", 0);
                        notificationList.remove(mVehicleHolder.getAdapterPosition());
                        notifyItemRemoved(mVehicleHolder.getAdapterPosition());
                        notifyItemRangeChanged(mVehicleHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                mVehicleHolder.mVehicleAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allVehicleDetails = mVehicleHolder.mVehicleName.getText().toString() + "=" +
                                        mVehicleHolder.mVehiclePrice.getText().toString() + "=" +
                                        mVehicleHolder.mVehicleBrand.getText().toString() + "=" +
                                        mVehicleHolder.mVehicleModel.getText().toString() + "=" +
                                        mVehicleHolder.mVehicleYearOfMfg.getText().toString() + "=" +
                                        mVehicleHolder.mVehicleKmsHrs.getText().toString() + "=" +
                                        mVehicleHolder.mRtoCity.getText().toString() + "=" +
                                        mVehicleHolder.mVehicleLocation.getText().toString() + "=" +
                                        mVehicleHolder.mVehicleRegistration.getText().toString() + "=" +
                                        notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleImage() + "=" +
                                        notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();


                                System.out.println("all vehicle detailssss======Auto " + allVehicleDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allVehicleDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_vehicle_id", notificationList.get(mVehicleHolder.getAdapterPosition()).getVehicleId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "vehicle").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase("") ||
                                        notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase(null) ||
                                        notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase("null")) {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleImage();
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

                                String allVehicleDetails = "Vehicle Title : " + mVehicleHolder.mVehicleName.getText().toString() + "\n" +
                                        "Vehicle Brand : " + mVehicleHolder.mVehicleBrand.getText().toString() + "\n" +
                                        "Vehicle Model : " + mVehicleHolder.mVehicleModel.getText().toString() + "\n" +
                                        "Year Of Mfg : " + mVehicleHolder.mVehicleYearOfMfg.getText().toString() + "\n" +
                                        "Rto city : " + mVehicleHolder.mRtoCity.getText().toString() + "\n" +
                                        "Location : " + mVehicleHolder.mVehicleLocation.getText().toString() + "\n" +
                                        "Registeration No : " + mVehicleHolder.mVehicleRegistration.getText().toString() + "\n" +
                                        "Likes : " + notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();

                                System.out.println("all vehicle detailssss======Other " + allVehicleDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(mVehicleHolder.getAdapterPosition()).getVehicleId() + "/" + mLoginContact
                                        + "\n" + "\n" + allVehicleDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allVehicleDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });

                break;

            case 5:
                final ProductNotifications mProductHolder = (ProductNotifications) holder;

                mProductHolder.mDelete.setImageResource(R.drawable.ic_delete);

                /*Log.i("Favorite", "Product-LayType ->" + notificationList.get(position).getLayoutType());
                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    mProductHolder.mProductCall.setVisibility(View.GONE);
                    //mProductHolder.mProductLike.setVisibility(View.GONE);
                    //mProductHolder.mProductUnlike.setVisibility(View.GONE);
                    mProductHolder.mRelativeLike.setVisibility(View.GONE);
                } else {
                    mProductHolder.mProductCall.setVisibility(View.VISIBLE);
                    //mProductHolder.mProductLike.setVisibility(View.VISIBLE);
                    // mProductHolder.mProductUnlike.setVisibility(View.VISIBLE);
                    mProductHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }*/

                mProductHolder.mProductActionName.setText(notificationList.get(position).getSendername() + " " +
                        notificationList.get(position).getAction() + " " +
                        notificationList.get(position).getProductName()
                        + " product");

                mProductHolder.mProductActionTime.setText(notificationList.get(position).getDatetime());
                mProductHolder.mProductName.setText(notificationList.get(position).getProductName());
                mProductHolder.mProductTitle.setText(notificationList.get(position).getProductName());
                mProductHolder.mProductType.setText(notificationList.get(position).getProductType());
                mProductHolder.mLikes.setText("Likes(" + notificationList.get(position).getProductlikecount() + ")");
                mProductHolder.mShares.setText("Shares(" + notificationList.get(position).getProductShareCount() + ")");
                mProductHolder.mProductRating.setRating(notificationList.get(position).getProductRating());

                mProductHolder.mProductCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mProductHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });

                /* User profile pic */
                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mProductHolder.mUserPic.setBackgroundResource(R.drawable.profile);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mProductHolder.mUserPic);
                }

                /* Product pic */
                if (notificationList.get(position).getProductimages() == null ||
                        notificationList.get(position).getProductimages().equals("") ||
                        notificationList.get(position).getProductimages().equals("null")) {
                    mProductHolder.mProductImage.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getProductimages())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mProductHolder.mProductImage);
                }

                /* Like & Unlike Functionality */
                if (notificationList.get(position).getProductlikestatus().equalsIgnoreCase("yes")) {
                    mProductHolder.mProductLike.setVisibility(View.VISIBLE);
                    mProductHolder.mProductUnlike.setVisibility(View.GONE);
                } else {
                    mProductHolder.mProductUnlike.setVisibility(View.VISIBLE);
                    mProductHolder.mProductLike.setVisibility(View.GONE);
                }

                mProductHolder.mProductLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mProductHolder.getAdapterPosition()).getSender();
                        mProductHolder.mProductLike.setVisibility(View.GONE);
                        mProductHolder.mProductUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "5", 0, 0, 0,
                                notificationList.get(mProductHolder.getAdapterPosition()).getProductId(), 0, 0, 0);

                        product_likecountint = notificationList.get(mProductHolder.getAdapterPosition()).getProductlikecount();
                        product_likecountint = product_likecountint - 1;
                        mProductHolder.mLikes.setText("Likes(" + product_likecountint + ")");
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductlikecount(product_likecountint);
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductlikestatus("no");
                    }
                });

                mProductHolder.mProductUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mProductHolder.getAdapterPosition()).getSender();
                        mProductHolder.mProductUnlike.setVisibility(View.GONE);
                        mProductHolder.mProductLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "5", 0, 0, 0,
                                notificationList.get(mProductHolder.getAdapterPosition()).getProductId(), 0, 0, 0);

                        product_likecountint = notificationList.get(mProductHolder.getAdapterPosition()).getProductlikecount();
                        product_likecountint = product_likecountint + 1;
                        mProductHolder.mLikes.setText("Likes(" + product_likecountint + ")");
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductlikecount(product_likecountint);
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductlikestatus("yes");
                    }
                });


                mProductHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Un Favorite web service
                        int notiId = notificationList.get(mProductHolder.getAdapterPosition()).getId();
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.remove(mProductHolder.getAdapterPosition());
                        notifyItemRemoved(mProductHolder.getAdapterPosition());
                        notifyItemRangeChanged(mProductHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                mProductHolder.mProductAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allProductDetails = mProductHolder.mProductName.getText().toString() + "=" +
                                        mProductHolder.mProductType.getText().toString() + "=" +
                                        mProductHolder.mProductRating.getRating() + "=" +
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductlikecount() + "=" +
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductimages();

                                System.out.println("all product detailssss======Auto " + allProductDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allProductDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_product_id", notificationList.get(mProductHolder.getAdapterPosition()).getProductId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "product").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (notificationList.get(mProductHolder.getAdapterPosition()).getProductimages().equalsIgnoreCase("") ||
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductimages().equalsIgnoreCase(null) ||
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductimages().equalsIgnoreCase("null")) {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    imagename = "http://autokatta.com/mobile/profile_profile_pics/" + notificationList.get(mProductHolder.getAdapterPosition()).getProductimages();
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

                                String allProductDetails = "Product name : " + mProductHolder.mProductName.getText().toString() + "\n" +
                                        "Product type : " + mProductHolder.mProductType.getText().toString() + "\n" +
                                        "Ratings : " + mProductHolder.mProductRating.getRating() + "\n" +
                                        "Likes : " + notificationList.get(mProductHolder.getAdapterPosition()).getProductlikecount() /*+ "\n" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount() + "\n"+
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderFollowCount()*/;

                                System.out.println("all product detailssss======Other " + allProductDetails);

                                intent.setType("text/plain");

                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/product/" + notificationList.get(mProductHolder.getAdapterPosition()).getProductId()
                                        //  + "/" + notificationList.get(mProductHolder.getAdapterPosition()).getStoreContact()
                                        + "\n" + "\n" + allProductDetails);

                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));


                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allProductDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });

                break;

            case 6:
                final ServiceNotifications mServiceHolder = (ServiceNotifications) holder;

                mServiceHolder.mDelete.setImageResource(R.drawable.ic_delete);

                /*Log.i("Wall", "Service-LayType ->" + notificationList.get(position).getLayoutType());
                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    mServiceHolder.mServiceCall.setVisibility(View.GONE);
                    //mServiceHolder.mServiceLike.setVisibility(View.GONE);
                    //mServiceHolder.mServiceUnlike.setVisibility(View.GONE);
                    mServiceHolder.mRelativeLike.setVisibility(View.GONE);
                } else {
                    mServiceHolder.mServiceCall.setVisibility(View.VISIBLE);
                    //mServiceHolder.mServiceLike.setVisibility(View.VISIBLE);
                    // mServiceHolder.mServiceUnlike.setVisibility(View.VISIBLE);
                    mServiceHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }*/

                mServiceHolder.mServiceActionName.setText(notificationList.get(position).getSendername() + " " +
                        notificationList.get(position).getAction() + " " +
                        notificationList.get(position).getServiceName()
                        + " service");

                mServiceHolder.mServiceActionTime.setText(notificationList.get(position).getDatetime());
                mServiceHolder.mServiceName.setText(notificationList.get(position).getServiceName());
                mServiceHolder.mServiceTitle.setText(notificationList.get(position).getServiceName());
                mServiceHolder.mServiceType.setText(notificationList.get(position).getServiceType());
                mServiceHolder.mLikes.setText("Likes(" + notificationList.get(position).getServicelikecount() + ")");
                mServiceHolder.mShares.setText("Shares(" + notificationList.get(position).getServiceShareCount() + ")");
                mServiceHolder.mServiceRating.setRating(notificationList.get(position).getServiceRating());

                mServiceHolder.mServiceCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mServiceHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });

                /* User profile pic */
                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mServiceHolder.mUserPic.setBackgroundResource(R.drawable.profile);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mServiceHolder.mUserPic);
                }

                /* Product pic */
                if (notificationList.get(position).getServiceimages() == null ||
                        notificationList.get(position).getServiceimages().equals("") ||
                        notificationList.get(position).getServiceimages().equals("null")) {
                    mServiceHolder.mServiceImage.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getServiceimages())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mServiceHolder.mServiceImage);
                }

                /* Like & Unlike Functionality */
                if (notificationList.get(position).getServicelikestatus().equalsIgnoreCase("yes")) {
                    mServiceHolder.mServiceLike.setVisibility(View.VISIBLE);
                    mServiceHolder.mServiceUnlike.setVisibility(View.GONE);
                } else {
                    mServiceHolder.mServiceUnlike.setVisibility(View.VISIBLE);
                    mServiceHolder.mServiceLike.setVisibility(View.GONE);
                }

                mServiceHolder.mServiceLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mServiceHolder.getAdapterPosition()).getSender();
                        mServiceHolder.mServiceLike.setVisibility(View.GONE);
                        mServiceHolder.mServiceUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "6", 0, 0, 0, 0,
                                notificationList.get(mServiceHolder.getAdapterPosition()).getServiceId(), 0, 0);

                        service_likecountint = notificationList.get(mServiceHolder.getAdapterPosition()).getServicelikecount();
                        service_likecountint = service_likecountint - 1;
                        mServiceHolder.mLikes.setText("Likes(" + service_likecountint + ")");
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServicelikecount(service_likecountint);
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServicelikestatus("no");
                    }
                });

                mServiceHolder.mServiceUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mServiceHolder.getAdapterPosition()).getSender();
                        mServiceHolder.mServiceUnlike.setVisibility(View.GONE);
                        mServiceHolder.mServiceLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "6", 0, 0, 0, 0,
                                notificationList.get(mServiceHolder.getAdapterPosition()).getServiceId(), 0, 0);

                        service_likecountint = notificationList.get(mServiceHolder.getAdapterPosition()).getServicelikecount();
                        service_likecountint = service_likecountint + 1;
                        mServiceHolder.mLikes.setText("Likes(" + service_likecountint + ")");
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServicelikecount(service_likecountint);
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServicelikestatus("yes");
                    }
                });

                mServiceHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //un Favorite web service
                        int notiId = notificationList.get(mServiceHolder.getAdapterPosition()).getId();
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.remove(mServiceHolder.getAdapterPosition());
                        notifyItemRemoved(mServiceHolder.getAdapterPosition());
                        notifyItemRangeChanged(mServiceHolder.getAdapterPosition(), notificationList.size());
                    }
                });


                mServiceHolder.mServiceAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allServiceDetails = mServiceHolder.mServiceName.getText().toString() + "=" +
                                        mServiceHolder.mServiceType.getText().toString() + "=" +
                                        mServiceHolder.mServiceRating.getRating() + "=" +
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServicelikecount() + "=" +
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServiceimages();

                                System.out.println("all service detailssss======Auto " + allServiceDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allServiceDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_service_id", notificationList.get(mServiceHolder.getAdapterPosition()).getServiceId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "service").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (notificationList.get(mServiceHolder.getAdapterPosition()).getServiceimages().equalsIgnoreCase("") ||
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServiceimages().equalsIgnoreCase(null) ||
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServiceimages().equalsIgnoreCase("null")) {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    imagename = "http://autokatta.com/mobile/profile_profile_pics/" + notificationList.get(mServiceHolder.getAdapterPosition()).getServiceimages();
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

                                String allServiceDetails = "Service name : " + mServiceHolder.mServiceName.getText().toString() + "\n" +
                                        "Service type : " + mServiceHolder.mServiceType.getText().toString() + "\n" +
                                        "Ratings : " + mServiceHolder.mServiceRating.getRating() + "\n" +
                                        "Likes : " + notificationList.get(mServiceHolder.getAdapterPosition()).getServicelikecount() /*+ "\n" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount() + "\n"+
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderFollowCount()*/;

                                System.out.println("all service detailssss======Other " + allServiceDetails);

                                intent.setType("text/plain");

                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/service/" + notificationList.get(mServiceHolder.getAdapterPosition()).getServiceId()
                                        //  + "/" + notificationList.get(mServiceHolder.getAdapterPosition()).getStoreContact()
                                        + "\n" + "\n" + allServiceDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));


                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allServiceDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/
                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });

                break;

            case 7:
                PostNotifications mPostHolder = (PostNotifications) holder;
                //mPostHolder.fav.setImageResource(R.drawable.ic_delete);

                break;


            case 8:

                final SearchNotifications mSearchHolder = (SearchNotifications) holder;
                mSearchHolder.mSearchUnfav.setImageResource(R.drawable.ic_delete);

                mSearchHolder.mSearchActionName.setText(notificationList.get(position).getSendername() + " " +
                        notificationList.get(position).getAction() + " " + "search");

                mSearchHolder.mSearchActionTime.setText(notificationList.get(position).getDatetime());
                mSearchHolder.mSearchCategory.setText(notificationList.get(position).getSearchCategory());
                mSearchHolder.mSearchBrand.setText(notificationList.get(position).getSearchBrand());
                mSearchHolder.mSearchModel.setText(notificationList.get(position).getSearchModel());
                mSearchHolder.mSearchPrice.setText(notificationList.get(position).getSearchPrice());
                mSearchHolder.mSearchYear.setText(notificationList.get(position).getSearchManfYear());
                mSearchHolder.mSearchDate.setText(notificationList.get(position).getSearchDate());
                mSearchHolder.mSearchLeads.setText(String.valueOf(notificationList.get(position).getSearchLeads()));

            /* Sender Profile Pic */

                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mSearchHolder.mUserPic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mSearchHolder.mUserPic);
                }

                mSearchHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mSearchHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });

                /* Like & Unlike Functionality */

                if (notificationList.get(position).getSearchLikeStatus().equalsIgnoreCase("yes")) {
                    mSearchHolder.mSearchLike.setVisibility(View.VISIBLE);
                    mSearchHolder.mSearchUnlike.setVisibility(View.GONE);
                } else {
                    mSearchHolder.mSearchUnlike.setVisibility(View.VISIBLE);
                    mSearchHolder.mSearchLike.setVisibility(View.GONE);
                }

                mSearchHolder.mSearchLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mSearchHolder.getAdapterPosition()).getSender();
                        int searchId = notificationList.get(mSearchHolder.getAdapterPosition()).getSearchId();
                        mSearchHolder.mSearchLike.setVisibility(View.GONE);
                        mSearchHolder.mSearchUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "8", 0, 0, 0, 0, 0, 0, searchId);
                        notificationList.get(mSearchHolder.getAdapterPosition()).setSearchLikeStatus("no");
                    }
                });

                mSearchHolder.mSearchUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mSearchHolder.getAdapterPosition()).getSender();
                        int searchId = notificationList.get(mSearchHolder.getAdapterPosition()).getSearchId();
                        mSearchHolder.mSearchUnlike.setVisibility(View.GONE);
                        mSearchHolder.mSearchLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "8", 0, 0, 0, 0, 0, 0, searchId);
                        notificationList.get(mSearchHolder.getAdapterPosition()).setSearchLikeStatus("yes");
                    }
                });

                mSearchHolder.mSearchUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Remove Favorite web service
                        int SearchId = notificationList.get(mSearchHolder.getAdapterPosition()).getSearchId();
                        mApiCall.removeFromFavorite(mLoginContact, "", SearchId, "", 0);
                        notificationList.remove(mSearchHolder.getAdapterPosition());
                        notifyItemRemoved(mSearchHolder.getAdapterPosition());
                        notifyItemRangeChanged(mSearchHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                mSearchHolder.mSearchAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allSearchDetails = mSearchHolder.mSearchCategory.getText().toString() + "=" +
                                        mSearchHolder.mSearchBrand.getText().toString() + "=" +
                                        mSearchHolder.mSearchModel.getText().toString() + "=" +
                                        mSearchHolder.mSearchPrice.getText().toString() + "=" +
                                        mSearchHolder.mSearchYear.getText().toString() + "=" +
                                        mSearchHolder.mSearchDate.getText().toString() + "=" +
                                        mSearchHolder.mSearchLeads.getText().toString();


                                System.out.println("all search detailssss======Auto " + allSearchDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allSearchDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_search_id", notificationList.get(mSearchHolder.getAdapterPosition()).getSearchId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "mysearch").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                String allSearchDetails = "Search Category : " + mSearchHolder.mSearchCategory.getText().toString() + "\n" +
                                        "Search Brand : " + mSearchHolder.mSearchBrand.getText().toString() + "\n" +
                                        "Search Model : " + mSearchHolder.mSearchModel.getText().toString() + "\n" +
                                        "Year Of Mfg : " + mSearchHolder.mSearchYear.getText().toString() + "\n" +
                                        "Price : " + mSearchHolder.mSearchPrice.getText().toString() + "\n" +
                                        "Leads : " + mSearchHolder.mSearchLeads.getText().toString() + "\n" +
                                        "Date : " + mSearchHolder.mSearchDate.getText().toString();

                                System.out.println("all search detailssss======Other " + allSearchDetails);

                                intent.setType("text/plain");
                                /*intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(mSearchHolder.getAdapterPosition()).getSearchId() + "/" + mLoginContact
                                        + "\n" + "\n" + allSearchDetails);*/
                                intent.putExtra(Intent.EXTRA_TEXT, allSearchDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Search list from Autokatta User");
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allSearchDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });


                break;

            case 9:
                ActiveNotifications mActiveHolder = (ActiveNotifications) holder;
                //mActiveHolder.mAuctionUnfav.setImageResource(R.drawable.ic_delete);
                break;

            case 10:
                final UploadVehicleNotifications mUpVehicleHolder = (UploadVehicleNotifications) holder;

                mUpVehicleHolder.mVehicleUnfav.setImageResource(R.drawable.ic_delete);

                mUpVehicleHolder.mActionName.setText(notificationList.get(position).getSendername() + " "
                        + notificationList.get(position).getAction() + " "
                        + "'" + notificationList.get(position).getUpVehicleTitle() + "'"
                        + " Vehicle In"
                        + "'" + notificationList.get(position).getGroupName() + "'"
                        + " Group");

                mUpVehicleHolder.mActionTime.setText(notificationList.get(position).getDatetime());
                mUpVehicleHolder.mVehicleName.setText(notificationList.get(position).getUpVehicleTitle());
                mUpVehicleHolder.mVehicleRegistration.setText(notificationList.get(position).getUpVehicleRegNo());
                mUpVehicleHolder.mVehiclePrice.setText(notificationList.get(position).getUpVehiclePrice());
                mUpVehicleHolder.mVehicleBrand.setText(notificationList.get(position).getUpVehicleBrand());
                mUpVehicleHolder.mVehicleModel.setText(notificationList.get(position).getUpVehicleModel());
                mUpVehicleHolder.mVehicleYearOfMfg.setText(notificationList.get(position).getUpVehicleManfYear());
                mUpVehicleHolder.mVehicleLocation.setText(notificationList.get(position).getUpVehicleLocationCity());
                mUpVehicleHolder.mRtoCity.setText(notificationList.get(position).getUpVehicleRtoCity());
                mUpVehicleHolder.mFollowCount.setText("Followers(" + notificationList.get(position).getUpVehicleFollowCount() + ")");
                mUpVehicleHolder.mLikes.setText("Likes(" + notificationList.get(position).getUpVehicleLikeCount() + ")");
                mUpVehicleHolder.mShares.setText("Shares(" + notificationList.get(position).getUpVehicleShareCount() + ")");
                mUpVehicleHolder.mVehicleKmsHrs.setText(notificationList.get(position).getUpVehicleKmsRun());

         /* Sender Profile Pic */

                if (notificationList.get(position).getSenderPic() == null ||
                        notificationList.get(position).getSenderPic().equals("") ||
                        notificationList.get(position).getSenderPic().equals("null")) {
                    mUpVehicleHolder.mUserPic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url)+ notificationList.get(position).getSenderPic())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mUpVehicleHolder.mUserPic);
                }

        /* Vehicle pic */

                if (notificationList.get(position).getUpVehicleImage() == null ||
                        notificationList.get(position).getUpVehicleImage().equals("") ||
                        notificationList.get(position).getUpVehicleImage().equals("null")) {
                    mUpVehicleHolder.mVehicleImage.setBackgroundResource(R.drawable.vehiimg);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getUpVehicleImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mUpVehicleHolder.mVehicleImage);
                }

                mUpVehicleHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        call(otherContact);
                    }
                });

        /* Like & Unlike Functionality */

                if (notificationList.get(position).getUpVehicleLikeStatus().equalsIgnoreCase("yes")) {
                    mUpVehicleHolder.mVehicleLike.setVisibility(View.VISIBLE);
                    mUpVehicleHolder.mVehicleUnlike.setVisibility(View.GONE);
                } else {
                    mUpVehicleHolder.mVehicleUnlike.setVisibility(View.VISIBLE);
                    mUpVehicleHolder.mVehicleLike.setVisibility(View.GONE);
                }

                mUpVehicleHolder.mVehicleLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getVehicleId();
                        mUpVehicleHolder.mVehicleLike.setVisibility(View.GONE);
                        mUpVehicleHolder.mVehicleUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "4", 0, 0, vehicleId, 0, 0, 0, 0);
                        vehicle_likecountint = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();
                        vehicle_likecountint = vehicle_likecountint - 1;
                        mUpVehicleHolder.mLikes.setText("Likes(" + vehicle_likecountint + ")");
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleLikeCount(vehicle_likecountint);
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleLikeStatus("no");
                    }
                });

                mUpVehicleHolder.mVehicleUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getVehicleId();
                        mUpVehicleHolder.mVehicleUnlike.setVisibility(View.GONE);
                        mUpVehicleHolder.mVehicleLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "4", 0, 0, vehicleId, 0, 0, 0, 0);
                        vehicle_likecountint = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();
                        vehicle_likecountint = vehicle_likecountint + 1;
                        mUpVehicleHolder.mLikes.setText("Likes(" + vehicle_likecountint + ")");
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleLikeCount(vehicle_likecountint);
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleLikeStatus("yes");
                    }
                });


        /* Follow & Unfollow Functionality */

                if (notificationList.get(position).getUpVehicleFollowStatus().equalsIgnoreCase("yes")) {
                    mUpVehicleHolder.mFollow.setVisibility(View.VISIBLE);
                    mUpVehicleHolder.mUnfollow.setVisibility(View.GONE);
                } else {
                    mUpVehicleHolder.mUnfollow.setVisibility(View.VISIBLE);
                    mUpVehicleHolder.mFollow.setVisibility(View.GONE);
                }

                mUpVehicleHolder.mFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfollow web service
                        String otherContact = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getVehicleId();
                        mUpVehicleHolder.mFollow.setVisibility(View.GONE);
                        mUpVehicleHolder.mUnfollow.setVisibility(View.VISIBLE);

                        mApiCall.UnFollow(mLoginContact, otherContact, "4", 0, vehicleId, 0, 0);
                        vehicle_followcountint = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleFollowCount();
                        vehicle_followcountint--;
                        mUpVehicleHolder.mFollowCount.setText("Followers(" + vehicle_followcountint + ")");
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleFollowCount(vehicle_followcountint);
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleFollowStatus("no");

                    }
                });

                mUpVehicleHolder.mUnfollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Follow web service
                        String otherContact = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getVehicleId();
                        mUpVehicleHolder.mUnfollow.setVisibility(View.GONE);
                        mUpVehicleHolder.mFollow.setVisibility(View.VISIBLE);

                        mApiCall.Follow(mLoginContact, otherContact, "4", 0, vehicleId, 0, 0);
                        vehicle_followcountint = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleFollowCount();
                        vehicle_followcountint = vehicle_followcountint + 1;
                        mUpVehicleHolder.mFollowCount.setText("Followers(" + vehicle_followcountint + ")");
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleFollowCount(vehicle_followcountint);
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setUpVehicleFollowStatus("yes");

                    }
                });

                mUpVehicleHolder.mVehicleUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Remove Favorite web service
                        int SearchId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getSearchId();
                        mApiCall.removeFromFavorite(mLoginContact, "", SearchId, "", 0);
                        notificationList.remove(mUpVehicleHolder.getAdapterPosition());
                        notifyItemRemoved(mUpVehicleHolder.getAdapterPosition());
                        notifyItemRangeChanged(mUpVehicleHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                mUpVehicleHolder.mVehicleAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allVehicleDetails = mUpVehicleHolder.mVehicleName.getText().toString() + "=" +
                                        mUpVehicleHolder.mVehiclePrice.getText().toString() + "=" +
                                        mUpVehicleHolder.mVehicleBrand.getText().toString() + "=" +
                                        mUpVehicleHolder.mVehicleModel.getText().toString() + "=" +
                                        mUpVehicleHolder.mVehicleYearOfMfg.getText().toString() + "=" +
                                        mUpVehicleHolder.mVehicleKmsHrs.getText().toString() + "=" +
                                        mUpVehicleHolder.mRtoCity.getText().toString() + "=" +
                                        mUpVehicleHolder.mVehicleLocation.getText().toString() + "=" +
                                        mUpVehicleHolder.mVehicleRegistration.getText().toString() + "=" +
                                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage() + "=" +
                                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();


                                System.out.println("all vehicle detailssss======Auto " + allVehicleDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allVehicleDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_vehicle_id", notificationList.get(mUpVehicleHolder.getAdapterPosition()).getVehicleId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "vehicle").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase("") ||
                                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase(null) ||
                                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase("null")) {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + "a.jpg";
                                } else {
                                    imagename = "http://autokatta.com/mobile/store_profiles/" + notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage();
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

                                String allVehicleDetails = "Vehicle Title : " + mUpVehicleHolder.mVehicleName.getText().toString() + "\n" +
                                        "Vehicle Brand : " + mUpVehicleHolder.mVehicleBrand.getText().toString() + "\n" +
                                        "Vehicle Model : " + mUpVehicleHolder.mVehicleModel.getText().toString() + "\n" +
                                        "Year Of Mfg : " + mUpVehicleHolder.mVehicleYearOfMfg.getText().toString() + "\n" +
                                        "Rto city : " + mUpVehicleHolder.mRtoCity.getText().toString() + "\n" +
                                        "Location : " + mUpVehicleHolder.mVehicleLocation.getText().toString() + "\n" +
                                        "Registeration No : " + mUpVehicleHolder.mVehicleRegistration.getText().toString() + "\n" +
                                        "Likes : " + notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleLikeCount();

                                System.out.println("all vehicle detailssss======Other " + allVehicleDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(mUpVehicleHolder.getAdapterPosition()).getVehicleId() + "/" + mLoginContact
                                        + "\n" + "\n" + allVehicleDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allVehicleDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });


                break;

            case 11:
                ShareNotifications mShareHolder = (ShareNotifications) holder;
                //mShareHolder.mShareUnfav.setImageResource(R.drawable.ic_delete);
                break;

            case 111:
                //buyer

                final BuyerNotifications mBuyerHolder = (BuyerNotifications) holder;

                mBuyerHolder.mBuyerUserName.setText(notificationList.get(position).getSendername());
                mBuyerHolder.mBuyerLocation.setText(notificationList.get(position).getVlocationCity());

                mBuyerHolder.buyertext.setVisibility(View.VISIBLE);
                mBuyerHolder.mItemNameCity.setVisibility(View.GONE);

                mBuyerHolder.checkBox1.setText(notificationList.get(position).getVcategory());
                mBuyerHolder.checkBox2.setText(notificationList.get(position).getVmanufacturer());
                mBuyerHolder.checkBox3.setText(notificationList.get(position).getVmodel());
                mBuyerHolder.checkBox4.setText(notificationList.get(position).getVyearOfManufacture());
                mBuyerHolder.checkBox5.setText(notificationList.get(position).getVrtoCity());
                mBuyerHolder.checkBoxRc.setText(notificationList.get(position).getVrcAvailable());
                mBuyerHolder.checkBoxIns.setText(notificationList.get(position).getVinsuranceValid());
                mBuyerHolder.checkBoxHpcap.setText(notificationList.get(position).getVhpcapacity());


                mBuyerHolder.checkBox6.setText(notificationList.get(position).getScategory());
                mBuyerHolder.checkBox7.setText(notificationList.get(position).getSmanufacturer());
                mBuyerHolder.checkBox8.setText(notificationList.get(position).getSmodel());
                mBuyerHolder.checkBox9.setText(notificationList.get(position).getSyearOfManufacture());
                mBuyerHolder.checkBox10.setText(notificationList.get(position).getSrtoCity());
                mBuyerHolder.checkBoxRcRight.setText(notificationList.get(position).getSrcAvailable());
                mBuyerHolder.checkBoxINSRight.setText(notificationList.get(position).getSinsuranceValid());
                mBuyerHolder.checkBoxHPcapRight.setText(notificationList.get(position).getShpcapacity());

                mBuyerHolder.favouritebuyer.setVisibility(View.GONE);
                mBuyerHolder.deleteFav.setVisibility(View.VISIBLE);

                try {

                    if (mBuyerHolder.checkBox1.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBox6.getText().toString())) {
                        mBuyerHolder.checkBox1.setChecked(true);
                        mBuyerHolder.checkBox6.setChecked(true);
                    }

                    if (mBuyerHolder.checkBox2.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBox7.getText().toString())) {
                        mBuyerHolder.checkBox2.setChecked(true);
                        mBuyerHolder.checkBox7.setChecked(true);
                    }

                    if (mBuyerHolder.checkBox3.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBox8.getText().toString())) {
                        mBuyerHolder.checkBox3.setChecked(true);
                        mBuyerHolder.checkBox8.setChecked(true);
                    }

                    if (mBuyerHolder.checkBox4.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBox9.getText().toString())) {
                        mBuyerHolder.checkBox4.setChecked(true);
                        mBuyerHolder.checkBox9.setChecked(true);
                    }

                    if (mBuyerHolder.checkBox5.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBox10.getText().toString())) {
                        mBuyerHolder.checkBox5.setChecked(true);
                        mBuyerHolder.checkBox10.setChecked(true);
                    }

                    if (mBuyerHolder.checkBoxRc.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBoxRcRight.getText().toString())) {
                        mBuyerHolder.checkBoxRc.setChecked(true);
                        mBuyerHolder.checkBoxRcRight.setChecked(true);
                    }

                    if (mBuyerHolder.checkBoxIns.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBoxINSRight.getText().toString())) {
                        mBuyerHolder.checkBoxIns.setChecked(true);
                        mBuyerHolder.checkBoxINSRight.setChecked(true);
                    }

                    if (mBuyerHolder.checkBoxHpcap.getText().toString().equalsIgnoreCase(mBuyerHolder.checkBoxHPcapRight.getText().toString())) {
                        mBuyerHolder.checkBoxHpcap.setChecked(true);
                        mBuyerHolder.checkBoxHPcapRight.setChecked(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                mBuyerHolder.checkBox1.setEnabled(false);
                mBuyerHolder.checkBox2.setEnabled(false);
                mBuyerHolder.checkBox3.setEnabled(false);
                mBuyerHolder.checkBox4.setEnabled(false);
                mBuyerHolder.checkBox5.setEnabled(false);
                mBuyerHolder.checkBoxRc.setEnabled(false);
                mBuyerHolder.checkBoxIns.setEnabled(false);
                mBuyerHolder.checkBoxHpcap.setEnabled(false);

                mBuyerHolder.checkBox6.setEnabled(false);
                mBuyerHolder.checkBox7.setEnabled(false);
                mBuyerHolder.checkBox8.setEnabled(false);
                mBuyerHolder.checkBox9.setEnabled(false);
                mBuyerHolder.checkBox10.setEnabled(false);
                mBuyerHolder.checkBoxRcRight.setEnabled(false);
                mBuyerHolder.checkBoxINSRight.setEnabled(false);
                mBuyerHolder.checkBoxHPcapRight.setEnabled(false);


                if (notificationList.get(position).getSenderPic().equals(""))
                    mBuyerHolder.buyer_lead_image.setBackgroundResource(R.drawable.logo);
                else {

                    /****************
                     Glide code for image uploading

                     *****************/
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPic())
                            .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            //.placeholder(R.drawable.logo) //To show image before loading an original image.
                            //.error(R.drawable.blocked) //To show error image if problem in loading.
                            .into(mBuyerHolder.buyer_lead_image);
                }

                mBuyerHolder.mBuyerUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String otherContact = notificationList.get(mBuyerHolder.getAdapterPosition()).getVcontactNo();

                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", otherContact);
                        bundle.putString("action", "otherProfile");
                        Log.i("Contact", "->" + otherContact);
                        Intent mOtherProfile = new Intent(mActivity, OtherProfile.class);
                        mOtherProfile.putExtras(bundle);
                        mActivity.startActivity(mOtherProfile);
                    }
                });

                mBuyerHolder.buyer_lead_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mBuyerHolder.getAdapterPosition()).getVcontactNo();

                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", otherContact);
                        bundle.putString("action", "otherProfile");
                        Log.i("Contact", "->" + otherContact);
                        Intent mOtherProfile = new Intent(mActivity, OtherProfile.class);
                        mOtherProfile.putExtras(bundle);
                        mActivity.startActivity(mOtherProfile);
                    }
                });

                mBuyerHolder.callbuyer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mBuyerHolder.getAdapterPosition()).getVcontactNo();
                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => " + c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String calldate = df.format(c.getTime());

                        if (!otherContact.equals(mLoginContact)) {
                            call(otherContact);

                            mApiCall.sendLastCallDate(mLoginContact, otherContact, calldate, "1");
                        }
                    }
                });

                mBuyerHolder.deleteFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String BuyerId = notificationList.get(mBuyerHolder.getAdapterPosition()).getVvehicleId()
                                + "," + notificationList.get(mBuyerHolder.getAdapterPosition()).getSsearchId();
                        mApiCall.removeFromFavorite(mLoginContact, BuyerId, 0, "", 0);
                        notificationList.remove(mBuyerHolder.getAdapterPosition());
                        notifyItemRemoved(mBuyerHolder.getAdapterPosition());
                        notifyItemRangeChanged(mBuyerHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                break;

            case 112:
                //seller

                final SellerNotifications mSellerHolder = (SellerNotifications) holder;
                mSellerHolder.mUserName.setText(notificationList.get(position).getSendername());
                // mSellerHolder.UploadedDate.setText("Uploaded On:-"+"\n"+Vdate.get(position).toString());

                mSellerHolder.sellertext.setVisibility(View.VISIBLE);
                mSellerHolder.lastcall.setVisibility(View.GONE);

                //To set Date
                /*try {

                    DateFormat date1 = new SimpleDateFormat(" MMM dd ");
                    DateFormat time1 = new SimpleDateFormat(" hh:mm a");


                    mSellerHolder.mDateTime.setText("Uploaded On:-" + "\n" + date1.format(notificationList.get(position).getVdate()) +
                            time1.format(notificationList.get(position).getVdate()));

                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                try {
                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                    //format of date coming from services
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss",
                            Locale.US);
                    inputFormat.setTimeZone(utc);
                    //format of date which want to show
                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                            Locale.US);
                    outputFormat.setTimeZone(utc);

                    Date date = inputFormat.parse(notificationList.get(position).getVdate());
                    String output = outputFormat.format(date);
                    System.out.println("jjj" + output);
                    mSellerHolder.mDateTime.setText("Uploaded On:-" + "\n" + output);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mSellerHolder.Title.setText(notificationList.get(position).getVtitle());
                // mSellerHolder.buyerlocation.setText(location_city.get(position).toString());

                mSellerHolder.mFavimg.setVisibility(View.GONE);
                mSellerHolder.deleteFav.setVisibility(View.VISIBLE);

                mSellerHolder.checkBox1.setText(notificationList.get(position).getScategory());
                mSellerHolder.checkBox2.setText(notificationList.get(position).getSmanufacturer());
                mSellerHolder.checkBox3.setText(notificationList.get(position).getSmodel());
                mSellerHolder.checkBox4.setText(notificationList.get(position).getSyearOfManufacture());
                mSellerHolder.checkBox5.setText(notificationList.get(position).getSrtoCity());
                mSellerHolder.checkBoxRc.setText(notificationList.get(position).getSrcAvailable());
                mSellerHolder.checkBoxIns.setText(notificationList.get(position).getSinsuranceValid());
                mSellerHolder.checkBoxHp.setText(notificationList.get(position).getShpcapacity());


                mSellerHolder.checkBox6.setText(notificationList.get(position).getVcategory());
                mSellerHolder.checkBox7.setText(notificationList.get(position).getVmanufacturer());
                mSellerHolder.checkBox8.setText(notificationList.get(position).getVmodel());
                mSellerHolder.checkBox9.setText(notificationList.get(position).getVyearOfManufacture());
                mSellerHolder.checkBox10.setText(notificationList.get(position).getVrtoCity());
                mSellerHolder.checkBoxRcright.setText(notificationList.get(position).getVrcAvailable());
                mSellerHolder.checkBoxInsRight.setText(notificationList.get(position).getVinsuranceValid());
                mSellerHolder.checkBoxHpRight.setText(notificationList.get(position).getVhpcapacity());


                try {

                    if (mSellerHolder.checkBox1.getText().toString().equalsIgnoreCase(mSellerHolder.checkBox6.getText().toString())) {
                        mSellerHolder.checkBox1.setChecked(true);
                        mSellerHolder.checkBox6.setChecked(true);
                    }

                    if (mSellerHolder.checkBox2.getText().toString().equalsIgnoreCase(mSellerHolder.checkBox7.getText().toString())) {
                        mSellerHolder.checkBox2.setChecked(true);
                        mSellerHolder.checkBox7.setChecked(true);
                    }

                    if (mSellerHolder.checkBox3.getText().toString().equalsIgnoreCase(mSellerHolder.checkBox8.getText().toString())) {
                        mSellerHolder.checkBox3.setChecked(true);
                        mSellerHolder.checkBox8.setChecked(true);
                    }

                    if (mSellerHolder.checkBox4.getText().toString().equalsIgnoreCase(mSellerHolder.checkBox9.getText().toString())) {
                        mSellerHolder.checkBox4.setChecked(true);
                        mSellerHolder.checkBox9.setChecked(true);
                    }

                    if (mSellerHolder.checkBox5.getText().toString().equalsIgnoreCase(mSellerHolder.checkBox10.getText().toString())) {
                        mSellerHolder.checkBox5.setChecked(true);
                        mSellerHolder.checkBox10.setChecked(true);
                    }

                    if (mSellerHolder.checkBoxRc.getText().toString().equalsIgnoreCase(mSellerHolder.checkBoxRcright.getText().toString())) {
                        mSellerHolder.checkBoxRc.setChecked(true);
                        mSellerHolder.checkBoxRcright.setChecked(true);
                    }

                    if (mSellerHolder.checkBoxIns.getText().toString().equalsIgnoreCase(mSellerHolder.checkBoxInsRight.getText().toString())) {
                        mSellerHolder.checkBoxIns.setChecked(true);
                        mSellerHolder.checkBoxInsRight.setChecked(true);
                    }

                    if (mSellerHolder.checkBoxHp.getText().toString().equalsIgnoreCase(mSellerHolder.checkBoxHpRight.getText().toString())) {
                        mSellerHolder.checkBoxHp.setChecked(true);
                        mSellerHolder.checkBoxHpRight.setChecked(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                mSellerHolder.checkBox1.setEnabled(false);
                mSellerHolder.checkBox2.setEnabled(false);
                mSellerHolder.checkBox3.setEnabled(false);
                mSellerHolder.checkBox4.setEnabled(false);
                mSellerHolder.checkBox5.setEnabled(false);
                mSellerHolder.checkBoxRc.setEnabled(false);
                mSellerHolder.checkBoxIns.setEnabled(false);
                mSellerHolder.checkBoxHp.setEnabled(false);

                mSellerHolder.checkBox6.setEnabled(false);
                mSellerHolder.checkBox7.setEnabled(false);
                mSellerHolder.checkBox8.setEnabled(false);
                mSellerHolder.checkBox9.setEnabled(false);
                mSellerHolder.checkBox10.setEnabled(false);
                mSellerHolder.checkBoxRcright.setEnabled(false);
                mSellerHolder.checkBoxInsRight.setEnabled(false);
                mSellerHolder.checkBoxHpRight.setEnabled(false);


                /*mSellerHolder.Compare.setVisibility(View.GONE);
                mSellerHolder.chckbox.setVisibility(View.GONE);*/
                mSellerHolder.mVehicleCount.setVisibility(View.GONE);


                final String imagenames = notificationList.get(position).getVimage();
                List<String> iname = new ArrayList<String>();


                String[] imagenamecame = imagenames.split(",");

                if (imagenamecame.length != 0 && !imagenamecame[0].equals("")) {
                    for (int z = 0; z < imagenamecame.length; z++) {
                        iname.add(imagenamecame[z]);
                    }

                    System.out.println("lis=" + iname);

                    ImageView[] imageView = new ImageView[iname.size()];

                    for (int l = 0; l < imageView.length; l++) {
                        imageView[l] = new ImageView(mActivity);

                        /****************
                         Glide code for image uploading

                         *****************/
                        Glide.with(mActivity)
                                .load(mActivity.getString(R.string.base_image_url) + iname.get(l).replaceAll(" ", "%20"))
                                //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                                .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                                //.placeholder(R.drawable.logo) //To show image before loading an original image.
                                //.error(R.drawable.blocked) //To show error image if problem in loading.
                                .into(imageView[l]);

                        mSellerHolder.mViewFlippersell.addView(imageView[l]);
                    }
                } else {
                    ImageView[] imageView = new ImageView[2];
                    for (int l = 0; l < imageView.length; l++) {
                        imageView[l] = new ImageView(mActivity);
                        imageView[l].setBackgroundResource(R.drawable.vehiimg);
                        mSellerHolder.mViewFlippersell.addView(imageView[l]);
                    }

                }

                int mFlippingsell = 0;

                if (mFlippingsell == 0) {
                    /** Start Flipping */
                    mSellerHolder.mViewFlippersell.startFlipping();
                    mFlippingsell = 1;
                } else {
                    /** Stop Flipping */
                    mSellerHolder.mViewFlippersell.stopFlipping();
                    mFlippingsell = 0;
                }

                mSellerHolder.mUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String otherContact = notificationList.get(mSellerHolder.getAdapterPosition()).getVcontactNo();

                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", otherContact);
                        bundle.putString("action", "otherProfile");
                        Log.i("Contact", "->" + otherContact);
                        Intent mOtherProfile = new Intent(mActivity, OtherProfile.class);
                        mOtherProfile.putExtras(bundle);
                        mActivity.startActivity(mOtherProfile);
                    }
                });


                mSellerHolder.mCallimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mSellerHolder.getAdapterPosition()).getVcontactNo();
                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => " + c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String calldate = df.format(c.getTime());

                        if (!otherContact.equals(mLoginContact)) {
                            call(otherContact);
                            mApiCall.sendLastCallDate(mLoginContact, otherContact, calldate, "1");
                        }
                    }
                });


                mSellerHolder.deleteFav.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String SellerId = notificationList.get(mSellerHolder.getAdapterPosition()).getSsearchId()
                                + "," + notificationList.get(mSellerHolder.getAdapterPosition()).getVvehicleId();
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, SellerId, 0);
                        notificationList.remove(mSellerHolder.getAdapterPosition());
                        notifyItemRemoved(mSellerHolder.getAdapterPosition());
                        notifyItemRangeChanged(mSellerHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                break;

            case 113:
                final MySearch mySearchHolder = (MySearch) holder;

                mySearchHolder.relativeSearchTitle.setVisibility(View.VISIBLE);
                mySearchHolder.mCategory.setText(notificationList.get(position).getSearchCategory());
                mySearchHolder.mBrand.setText(notificationList.get(position).getSearchBrand());
                mySearchHolder.mModel.setText(notificationList.get(position).getSearchModel());
                mySearchHolder.mPrice.setText(notificationList.get(position).getSearchPrice());
                mySearchHolder.mYear.setText(notificationList.get(position).getSearchManfYear());
                mySearchHolder.mSearchDate.setText(notificationList.get(position).getSearchDate());
                mySearchHolder.mLeads.setText(String.valueOf(notificationList.get(position).getSearchLeads()));

                mySearchHolder.editImg.setImageResource(R.drawable.ic_delete);
                mySearchHolder.deleteImg.setVisibility(View.GONE);
                // mySearchHolder.sharevehi.setVisibility(View.GONE);
                mySearchHolder.unfavImg.setVisibility(View.GONE);
                mySearchHolder.favImg.setVisibility(View.GONE);
                mySearchHolder.relativeLayout.setVisibility(View.GONE);

                mySearchHolder.editImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //unfavorite service
                        int SearchId = notificationList.get(mySearchHolder.getAdapterPosition()).getSearchId();
                        mApiCall.removeFromFavorite(mLoginContact, "", SearchId, "", 0);
                        notificationList.remove(mySearchHolder.getAdapterPosition());
                        notifyItemRemoved(mySearchHolder.getAdapterPosition());
                        notifyItemRangeChanged(mySearchHolder.getAdapterPosition(), notificationList.size());
                    }
                });

                mySearchHolder.mAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allSearchDetails = mySearchHolder.mCategory.getText().toString() + "=" +
                                        mySearchHolder.mBrand.getText().toString() + "=" +
                                        mySearchHolder.mModel.getText().toString() + "=" +
                                        mySearchHolder.mPrice.getText().toString() + "=" +
                                        mySearchHolder.mYear.getText().toString() + "=" +
                                        mySearchHolder.mSearchDate.getText().toString() + "=" +
                                        mySearchHolder.mLeads.getText().toString();


                                System.out.println("all search detailssss======Auto " + allSearchDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allSearchDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_search_id", notificationList.get(mySearchHolder.getAdapterPosition()).getSearchId()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "mysearch").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                String allSearchDetails = "Search Category : " + mySearchHolder.mCategory.getText().toString() + "\n" +
                                        "Search Brand : " + mySearchHolder.mBrand.getText().toString() + "\n" +
                                        "Search Model : " + mySearchHolder.mModel.getText().toString() + "\n" +
                                        "Year Of Mfg : " + mySearchHolder.mYear.getText().toString() + "\n" +
                                        "Price : " + mySearchHolder.mPrice.getText().toString() + "\n" +
                                        "Leads : " + mySearchHolder.mLeads.getText().toString() + "\n" +
                                        "Date : " + mySearchHolder.mSearchDate.getText().toString();

                                System.out.println("all search detailssss======Other " + allSearchDetails);

                                intent.setType("text/plain");
                                /*intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(mySearchHolder.getAdapterPosition()).getSearchId() + "/" + mLoginContact
                                        + "\n" + "\n" + allSearchDetails);*/
                                intent.putExtra(Intent.EXTRA_TEXT, allSearchDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Search list from Autokatta User");
                                mActivity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allSearchDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/

                                dialog.dismiss();
                            }

                        });
                        alert.create();
                        alert.show();
                    }
                });


                break;


        }
    }

    private void call(String otherConatct) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + otherConatct));
        try {
            mActivity.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "success_follow":
                    CustomToast.customToast(mActivity, "Following");
                /*mFollow.setVisibility(View.GONE);
                mUnFollow.setVisibility(View.VISIBLE);
                mFolllowstr = "yes";*/
                    break;
                case "success_unfollow":
                    CustomToast.customToast(mActivity, "UnFollowing");
                /*mFollow.setVisibility(View.VISIBLE);
                mUnFollow.setVisibility(View.GONE);
                mFolllowstr = "no";*/
                    break;
                case "success_like":
                    CustomToast.customToast(mActivity, "Liked");
                /*mLike.setVisibility(View.VISIBLE);
                mUnlike.setVisibility(View.GONE);*/
                    //mLikestr = "yes";
                    break;
                case "success_unlike":
                    CustomToast.customToast(mActivity, "Unliked");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    //mLikestr = "no";
                    break;
                case "success_favourite":
                    CustomToast.customToast(mActivity, "Favorite");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    //mLikestr = "no";
                    break;
                case "success_remove":
                    CustomToast.customToast(mActivity, "Unfavorite");
                /*mLike.setVisibility(View.GONE);
                mUnlike.setVisibility(View.VISIBLE);*/
                    //mLikestr = "no";
                    break;
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
                    , "Favorite Notification Adapter");
            error.printStackTrace();
        }
    }
}
