package autokatta.com.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.WallNotificationFragment;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.WallResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.ShareWithinAppActivity;
import autokatta.com.view.UserProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private String mLoginContact = "", shareKey = "";
    private ApiCall mApiCall;
    private int mAuctionId, profile_likecountint, profile_followcountint, product_likecountint, service_likecountint, store_likecountint,
            store_followcountint, store_sharecountint, vehicle_likecountint, vehicle_followcountint, vehicle_sharecountint;
    private ConnectionDetector mConnectionDetector;

    public WallNotificationAdapter(Activity mActivity1, List<WallResponse.Success.WallNotification> notificationList) {
        this.mActivity = mActivity1;
        this.notificationList = notificationList;
        this.mApiCall = new ApiCall(mActivity, this);
        mConnectionDetector = new ConnectionDetector(mActivity);
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
        ImageButton mShareAutokatta, mCall, mLike, mUnlike, mFav, mUnfav;
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
            //mCall = (ImageButton) profileView.findViewById(R.id.call);
            mLike = (ImageButton) profileView.findViewById(R.id.like);
            mUnlike = (ImageButton) profileView.findViewById(R.id.unlike);
            mFav = (ImageButton) profileView.findViewById(R.id.profile_favourite);
            mUnfav = (ImageButton) profileView.findViewById(R.id.profile_unfavourite);
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
            //mCall = (ImageButton) storeView.findViewById(R.id.call);
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
            //mCall = (ImageButton) upVehicleView.findViewById(R.id.call);
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
        ImageButton mProductAutokattaShare, mProductCall, mProductLike, mProductFav, mProductUnfav,
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
            //mProductCall = (ImageButton) productView.findViewById(R.id.call);
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
            mProductUnfav = (ImageButton) productView.findViewById(R.id.product_unfavourite);
            mRelativeLike = (RelativeLayout) productView.findViewById(R.id.rlLike);
        }
    }

    /*
    Service Notification Class...
     */
    private static class ServiceNotifications extends RecyclerView.ViewHolder {
        CardView mServiceCardView;
        ImageView mUserPic, mServiceImage;
        ImageButton mServiceAutokattaShare, mServiceCall, mServiceLike, mServiceUnlike, mServiceFavourite, mServiceUnfav;
        RatingBar mServiceRating;
        TextView mServiceActionName, mServiceActionTime, mServiceTitle, mServiceName, mServiceType, mLikes, mShares;
        RelativeLayout mRelativeLike;

        private ServiceNotifications(View serviceView) {
            super(serviceView);
            mServiceCardView = (CardView) serviceView.findViewById(R.id.service_card_view);
            mUserPic = (ImageView) serviceView.findViewById(R.id.service_pro_pic);
            mServiceImage = (ImageView) serviceView.findViewById(R.id.service_image);

            mServiceAutokattaShare = (ImageButton) serviceView.findViewById(R.id.share_autokatta);
            //mServiceCall = (ImageButton) serviceView.findViewById(R.id.call);
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
            mServiceUnfav = (ImageButton) serviceView.findViewById(R.id.service_unfavourite);
            mRelativeLike = (RelativeLayout) serviceView.findViewById(R.id.rlLike);
        }
    }

    /*
    Post Notification Class...
     */
    private static class PostNotifications extends RecyclerView.ViewHolder {
        CardView mPostCardView;
        ImageView mProfile_pic;
        TextView mAction, mActionTime, mStatusText;
        ImageButton mPostAutokattaShare, mCall, mLike, mUnlike;
        RelativeLayout mRelativeLike;
        //Preview mPreview;

        private PostNotifications(View postView) {
            super(postView);

            mPostCardView = (CardView) postView.findViewById(R.id.post_card_view);
            mProfile_pic = (ImageView) postView.findViewById(R.id.profile_pro_pic);
            mAction = (TextView) postView.findViewById(R.id.post_action_names);
            mActionTime = (TextView) postView.findViewById(R.id.post_action_time);
            mStatusText = (TextView) postView.findViewById(R.id.statustxt);
            mPostAutokattaShare = (ImageButton) postView.findViewById(R.id.share_autokatta);
            //mCall = (ImageButton) postView.findViewById(R.id.call);
            mLike = (ImageButton) postView.findViewById(R.id.like);
            mUnlike = (ImageButton) postView.findViewById(R.id.unlike);
            mRelativeLike = (RelativeLayout) postView.findViewById(R.id.rlLike);
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
            //mCall = (ImageButton) searchView.findViewById(R.id.call);
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
    Upload Vehicle Notification Class...
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
            //mCall = (ImageButton) upVehicleView.findViewById(R.id.call);
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
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_post_notification, parent, false);
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
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.i("Wall", "Adapter-LayoutNo ->" + holder.getItemViewType());
        mLoginContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
        switch (holder.getItemViewType()) {
            case 1:
                final ProfileNotifications mProfileHolder = (ProfileNotifications) holder;
                Log.i("Wall", "Profile-LayType ->" + notificationList.get(position).getLayoutType());
                SpannableStringBuilder sb = new SpannableStringBuilder();
                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    //mProfileHolder.mCall.setVisibility(View.GONE);
                    mProfileHolder.mRelativeLike.setVisibility(View.GONE);

                } else {
                    //mProfileHolder.mCall.setVisibility(View.VISIBLE);
                    mProfileHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                sb.append(notificationList.get(position).getSenderName());
                sb.append(" ");
                sb.append(notificationList.get(position).getAction());
                sb.append(" ");
                sb.append(notificationList.get(position).getReceiverName());
                sb.append(" Profile");

                sb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Intent intent = new Intent(mActivity, OtherProfile.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", notificationList.get(position).getSender());
                        intent.putExtras(bundle);
                        mActivity.startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setUnderlineText(false);
                        ds.setColor(mActivity.getResources().getColor(R.color.colorPrimaryDark));
                        ds.setFakeBoldText(true);
                        ds.setTextSize((float) 30.0);
                        Log.i("TextSize", "->" + ds.getTextSize());
                    }
                }, 0, notificationList.get(position).getSenderName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                sb.setSpan(new ClickableSpan() {
                               @Override
                               public void onClick(View widget) {
                                   mActivity.startActivity(new Intent(mActivity, UserProfile.class));
                               }

                               @Override
                               public void updateDrawState(TextPaint ds) {
                                   ds.setUnderlineText(false);
                                   ds.setColor(mActivity.getResources().getColor(R.color.colorPrimaryDark));
                                   ds.setFakeBoldText(true);
                                   ds.setTextSize((float) 30.0);
                               }
                           }, notificationList.get(position).getSenderName().length() + notificationList.get(position).getAction().length() + 2,
                        notificationList.get(position).getSenderName().length() +
                                notificationList.get(position).getAction().length() + 2 + notificationList.get(position).getReceiverName().length()
                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                mProfileHolder.mProfileAction.setText(sb);
                mProfileHolder.mProfileAction.setMovementMethod(LinkMovementMethod.getInstance());
                mProfileHolder.mProfileAction.setHighlightColor(Color.TRANSPARENT);
                /*mProfileHolder.mProfileAction.setText(notificationList.get(position).getSenderName() + " "
                        + notificationList.get(position).getAction() + "\n" + notificationList.get(position).getReceiverName() + " " + "Profile");
*/
                mProfileHolder.mActionTime.setText(notificationList.get(position).getDateTime());
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
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mProfileHolder.mProfileImage);
                }

                /*mProfileHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mProfileHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });*/

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
                        mApiCall.UnLike(mLoginContact, otherContact, "1", 0, 0, 0, 0, 0, 0, 0);
                        profile_likecountint = notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount();
                        profile_likecountint = profile_likecountint - 1;
                        mProfileHolder.mLikes.setText("Likes(" + profile_likecountint + ")");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderLikeCount(profile_likecountint);
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
                        mApiCall.Like(mLoginContact, otherContact, "1", 0, 0, 0, 0, 0, 0, 0);
                        profile_likecountint = notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount();
                        profile_likecountint = profile_likecountint + 1;
                        mProfileHolder.mLikes.setText("Likes(" + profile_likecountint + ")");
                        notificationList.get(mProfileHolder.getAdapterPosition()).setSenderLikeCount(profile_likecountint);
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
                        int notiId = notificationList.get(mProfileHolder.getAdapterPosition()).getActionID();
                        mProfileHolder.mFav.setVisibility(View.GONE);
                        mProfileHolder.mUnfav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mProfileHolder.mUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mProfileHolder.getAdapterPosition()).getActionID();
                        mProfileHolder.mUnfav.setVisibility(View.GONE);
                        mProfileHolder.mFav.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("yes");
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
                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mProfileHolder.getAdapterPosition()).getSenderPicture();
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
               /* CardView mStoreCardView;
                ImageView mProfilePic, mStoreImage;
                ImageButton mShareAutokatta, mCall, mLike, mUnlike, mFollow, mUnfollow;
                RatingBar mStoreRating;
                TextView mStoreActionName, mActionTime, mStoreName, mStoreCategory, mStoreType, mStoreWebSite, mStoreTiming,
                        mStoreWorkingDay, mStoreLocation, mFollowCount, mLikes, mShares;*/

                final StoreNotifications mStoreHolder = (StoreNotifications) holder;
                Log.i("Wall", "Store-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    //mStoreHolder.mCall.setVisibility(View.GONE);
                    mStoreHolder.mRelativeLike.setVisibility(View.GONE);

                } else {
                    //mStoreHolder.mCall.setVisibility(View.VISIBLE);
                    mStoreHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                mStoreHolder.mStoreActionName.setText(notificationList.get(position).getSenderName() + " "
                        + notificationList.get(position).getAction() + "\n" + notificationList.get(position).getReceiverName() + " "
                        + notificationList.get(position).getStoreName() + " " + "Store");

                mStoreHolder.mActionTime.setText(notificationList.get(position).getDateTime());
                mStoreHolder.mStoreName.setText(notificationList.get(position).getStoreName());
                mStoreHolder.mStoreCategory.setText(notificationList.get(position).getStoreCategory());
                mStoreHolder.mStoreType.setText(notificationList.get(position).getStoreType());
                mStoreHolder.mStoreWebSite.setText(notificationList.get(position).getStoreWebsite());
                mStoreHolder.mStoreTiming.setText(notificationList.get(position).getStoreTiming());
                mStoreHolder.mStoreWorkingDay.setText(notificationList.get(position).getWorkingDays());
                mStoreHolder.mStoreLocation.setText(notificationList.get(position).getStoreLocation());
                mStoreHolder.mFollowCount.setText("Followers(" + notificationList.get(position).getStoreFollowCount() + ")");
                mStoreHolder.mLikes.setText("Likes(" + notificationList.get(position).getStoreLikeCount() + ")");
                mStoreHolder.mShares.setText("Shares(" + notificationList.get(position).getStoreShareCount() + ")");
                mStoreHolder.mStoreRating.setRating(notificationList.get(position).getStoreRating());

         /* Sender Profile Pic */

                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mStoreHolder.mProfilePic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
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
                            .into(mStoreHolder.mStoreImage);
                }

                /*mStoreHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });*/

     /* Like & Unlike Functionality */

                if (notificationList.get(position).getStoreLikeStatus().equalsIgnoreCase("yes")) {
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
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                        mStoreHolder.mLike.setVisibility(View.GONE);
                        mStoreHolder.mUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "2", storeId, 0, 0, 0, 0, 0, 0);
                        store_likecountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount();
                        store_likecountint = store_likecountint - 1;
                        mStoreHolder.mLikes.setText("Likes(" + store_likecountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeCount(store_likecountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeStatus("no");
                    }
                });

                mStoreHolder.mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                        mStoreHolder.mUnlike.setVisibility(View.GONE);
                        mStoreHolder.mLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "2", storeId, 0, 0, 0, 0, 0, 0);
                        store_likecountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount();
                        store_likecountint = store_likecountint + 1;
                        mStoreHolder.mLikes.setText("Likes(" + store_likecountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeCount(store_likecountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreLikeStatus("yes");
                    }
                });

      /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mStoreHolder.mStoreFav.setVisibility(View.VISIBLE);
                    mStoreHolder.mStoreUnfav.setVisibility(View.GONE);
                } else {
                    mStoreHolder.mStoreUnfav.setVisibility(View.VISIBLE);
                    mStoreHolder.mStoreFav.setVisibility(View.GONE);
                }

                mStoreHolder.mStoreFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mStoreHolder.getAdapterPosition()).getActionID();
                        mStoreHolder.mStoreFav.setVisibility(View.GONE);
                        mStoreHolder.mStoreUnfav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mStoreHolder.mStoreUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mStoreHolder.getAdapterPosition()).getActionID();
                        mStoreHolder.mStoreUnfav.setVisibility(View.GONE);
                        mStoreHolder.mStoreFav.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setMyFavStatus("yes");
                    }
                });


         /* Follow & UnfollowFunctionality */

                if (notificationList.get(position).getStoreFollowStatus().equalsIgnoreCase("yes")) {
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
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                        mStoreHolder.mFollow.setVisibility(View.GONE);
                        mStoreHolder.mUnfollow.setVisibility(View.VISIBLE);

                        mApiCall.UnFollow(mLoginContact, otherContact, "2", storeId, 0, 0, 0);
                        store_followcountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreFollowCount();
                        store_followcountint--;
                        mStoreHolder.mFollowCount.setText("Followers(" + store_followcountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowCount(store_followcountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowStatus("no");

                    }
                });

                mStoreHolder.mUnfollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Follow web service
                        String otherContact = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreContact();
                        int storeId = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID();
                        mStoreHolder.mUnfollow.setVisibility(View.GONE);
                        mStoreHolder.mFollow.setVisibility(View.VISIBLE);

                        mApiCall.Follow(mLoginContact, otherContact, "2", storeId, 0, 0, 0);
                        store_followcountint = notificationList.get(mStoreHolder.getAdapterPosition()).getStoreFollowCount();
                        store_followcountint = store_followcountint + 1;
                        mStoreHolder.mFollowCount.setText("Followers(" + store_followcountint + ")");
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowCount(store_followcountint);
                        notificationList.get(mStoreHolder.getAdapterPosition()).setStoreFollowStatus("yes");

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
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount() + "=" +
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getStoreFollowCount();

                                System.out.println("all store detailssss======Auto " + allStoreDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allStoreDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_store_id", notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID()).apply();
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
                                if (notificationList.get(mStoreHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase("") ||
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase(null) ||
                                        notificationList.get(mStoreHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase("null")) {
                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreImage();
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
                                        "Likes : " + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreLikeCount() + "\n" +
                                        "Website : " + mStoreHolder.mStoreWebSite.getText().toString() + "\n" +
                                        "Timing : " + mStoreHolder.mStoreTiming.getText().toString() + "\n" +
                                        "Working Days : " + mStoreHolder.mStoreWorkingDay.getText().toString() + "\n" +
                                        "Location : " + mStoreHolder.mStoreLocation.getText().toString();

                                System.out.println("all store detailssss======Other " + allStoreDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/store/main/" + notificationList.get(mStoreHolder.getAdapterPosition()).getStoreID()
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
                /*ImageView mUserPic, mGroupImage;
                ImageButton mGroupFavourite;
                TextView mActionName, mActionTime, mGroupName, mGroupMembers, mGroupNoOfVehicles, mGroupNoOfProducts,
                        mGroupNoOfServices;*/
                final GroupNotifications mGroupHolder = (GroupNotifications) holder;

                mGroupHolder.mActionName.setText(notificationList.get(position).getSenderName() + " " +
                        notificationList.get(position).getAction() + "\n" + notificationList.get(position).getReceiverName() +
                        " in " + notificationList.get(position).getGroupName()
                        + " group");
                mGroupHolder.mGroupName.setText(notificationList.get(position).getGroupName());
                mGroupHolder.mActionTime.setText(notificationList.get(position).getDateTime());
                mGroupHolder.mGroupMembers.setText(String.valueOf(notificationList.get(position).getGroupMembers()));
                mGroupHolder.mGroupNoOfVehicles.setText(String.valueOf(notificationList.get(position).getGroupVehicles()));
                mGroupHolder.mGroupNoOfProducts.setText(String.valueOf(notificationList.get(position).getGroupProductCount()));
                mGroupHolder.mGroupNoOfServices.setText(String.valueOf(notificationList.get(position).getGroupServiceCount()));

               /* Profile pic */
                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mGroupHolder.mUserPic.setBackgroundResource(R.mipmap.profile);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
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
                        int notiId = notificationList.get(mGroupHolder.getAdapterPosition()).getActionID();
                        mGroupHolder.mGroupFavourite.setVisibility(View.GONE);
                        mGroupHolder.mGroupUnFav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mGroupHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mGroupHolder.mGroupUnFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mGroupHolder.getAdapterPosition()).getActionID();
                        mGroupHolder.mGroupUnFav.setVisibility(View.GONE);
                        mGroupHolder.mGroupFavourite.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mGroupHolder.getAdapterPosition()).setMyFavStatus("yes");
                    }
                });
                break;

            case 4:
                /*CardView mVehicleCardView;
                ImageView mUserPic, mVehicleImage;
                ImageButton mVehicleAutokattaShare, mCall, mVehicleLike, mVehicleUnlike, mVehicleFavourite, mVehicleUnfav,
                        mFollow, mUnfollow;
                TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                        mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikes, mShares;*/

                final VehicleNotifications mVehicleHolder = (VehicleNotifications) holder;
                Log.i("Wall", "Vehicle-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    //mStoreHolder.mCall.setVisibility(View.GONE);
                    mVehicleHolder.mRelativeLike.setVisibility(View.GONE);

                } else {
                    //mStoreHolder.mCall.setVisibility(View.VISIBLE);
                    mVehicleHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                mVehicleHolder.mActionName.setText(notificationList.get(position).getSenderName() + " "
                        + notificationList.get(position).getAction() + "\n" + notificationList.get(position).getReceiverName() + " "
                        + notificationList.get(position).getUpVehicleTitle() + " " + "Vehicle");

                mVehicleHolder.mActionTime.setText(notificationList.get(position).getDateTime());
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

                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mVehicleHolder.mUserPic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
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

                /*mVehicleHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        call(otherContact);
                    }
                });*/

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
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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

      /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mVehicleHolder.mVehicleFavourite.setVisibility(View.VISIBLE);
                    mVehicleHolder.mVehicleUnfav.setVisibility(View.GONE);
                } else {
                    mVehicleHolder.mVehicleUnfav.setVisibility(View.VISIBLE);
                    mVehicleHolder.mVehicleFavourite.setVisibility(View.GONE);
                }

                mVehicleHolder.mVehicleFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mVehicleHolder.getAdapterPosition()).getActionID();
                        mVehicleHolder.mVehicleFavourite.setVisibility(View.GONE);
                        mVehicleHolder.mVehicleUnfav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mVehicleHolder.mVehicleUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mVehicleHolder.getAdapterPosition()).getActionID();
                        mVehicleHolder.mVehicleUnfav.setVisibility(View.GONE);
                        mVehicleHolder.mVehicleFavourite.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mVehicleHolder.getAdapterPosition()).setMyFavStatus("yes");
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
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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
                        int vehicleId = notificationList.get(mVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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
                                        putInt("Share_vehicle_id", notificationList.get(mVehicleHolder.getAdapterPosition()).getUploadVehicleID()).apply();
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
                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mVehicleHolder.getAdapterPosition()).getUpVehicleImage();
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
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(mVehicleHolder.getAdapterPosition()).getUploadVehicleID() + "/" + mLoginContact
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
                /*CardView mProductCardView;
                    ImageView mUserPic, mProductImage;
                    ImageButton mProductAutokattaShare, mProductOtherShare, mCall, mLike, mProductFav, mProductUnfav;
                    RatingBar mProductRating;
                    TextView mProductActionName, mProductActionTime, mProductTitle, mProductName, mProductType, mLikes, mShares;*/
                final ProductNotifications mProductHolder = (ProductNotifications) holder;
                Log.i("Wall", "Product-LayType ->" + notificationList.get(position).getLayoutType());
                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    //mProductHolder.mProductCall.setVisibility(View.GONE);
                    //mProductHolder.mProductLike.setVisibility(View.GONE);
                    //mProductHolder.mProductUnlike.setVisibility(View.GONE);
                    mProductHolder.mRelativeLike.setVisibility(View.GONE);
                } else {
                    //mProductHolder.mProductCall.setVisibility(View.VISIBLE);
                    //mProductHolder.mProductLike.setVisibility(View.VISIBLE);
                    // mProductHolder.mProductUnlike.setVisibility(View.VISIBLE);
                    mProductHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                mProductHolder.mProductActionName.setText(notificationList.get(position).getSenderName() + " " +
                        notificationList.get(position).getAction() + "\n" +
                        notificationList.get(position).getProductName()
                        + " product");

                mProductHolder.mProductActionTime.setText(notificationList.get(position).getDateTime());
                mProductHolder.mProductName.setText(notificationList.get(position).getProductName());
                mProductHolder.mProductTitle.setText(notificationList.get(position).getProductName());
                mProductHolder.mProductType.setText(notificationList.get(position).getProductType());
                mProductHolder.mLikes.setText("Likes(" + notificationList.get(position).getProductLikeCount() + ")");
                mProductHolder.mShares.setText("Shares(" + notificationList.get(position).getProductShareCount() + ")");
                mProductHolder.mProductRating.setRating(notificationList.get(position).getProductRating());

                /*mProductHolder.mProductCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mProductHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });*/

                /* User profile pic */
                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mProductHolder.mUserPic.setBackgroundResource(R.drawable.profile);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url)+ notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mProductHolder.mUserPic);
                }

                /* Product pic */
                if (notificationList.get(position).getProductImage() == null ||
                        notificationList.get(position).getProductImage().equals("") ||
                        notificationList.get(position).getProductImage().equals("null")) {
                    mProductHolder.mProductImage.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getProductImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mProductHolder.mProductImage);
                }

                /* Like & Unlike Functionality */
                if (notificationList.get(position).getProductLikeStatus().equalsIgnoreCase("yes")) {
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
                                notificationList.get(mProductHolder.getAdapterPosition()).getProductID(), 0, 0, 0);

                        product_likecountint = notificationList.get(mProductHolder.getAdapterPosition()).getProductLikeCount();
                        product_likecountint = product_likecountint - 1;
                        mProductHolder.mLikes.setText("Likes(" + product_likecountint + ")");
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductLikeCount(product_likecountint);
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductLikeStatus("no");
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
                                notificationList.get(mProductHolder.getAdapterPosition()).getProductID(), 0, 0, 0);

                        product_likecountint = notificationList.get(mProductHolder.getAdapterPosition()).getProductLikeCount();
                        product_likecountint = product_likecountint + 1;
                        mProductHolder.mLikes.setText("Likes(" + product_likecountint + ")");
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductLikeCount(product_likecountint);
                        notificationList.get(mProductHolder.getAdapterPosition()).setProductLikeStatus("yes");
                    }
                });
                
                /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mProductHolder.mProductFav.setVisibility(View.VISIBLE);
                    mProductHolder.mProductUnfav.setVisibility(View.GONE);
                } else {
                    mProductHolder.mProductUnfav.setVisibility(View.VISIBLE);
                    mProductHolder.mProductFav.setVisibility(View.GONE);
                }

                mProductHolder.mProductFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mProductHolder.getAdapterPosition()).getActionID();
                        mProductHolder.mProductFav.setVisibility(View.GONE);
                        mProductHolder.mProductUnfav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mProductHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mProductHolder.mProductUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mProductHolder.getAdapterPosition()).getActionID();
                        mProductHolder.mProductUnfav.setVisibility(View.GONE);
                        mProductHolder.mProductFav.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mProductHolder.getAdapterPosition()).setMyFavStatus("yes");
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
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductLikeCount() + "=" +
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductImage();

                                System.out.println("all product detailssss======Auto " + allProductDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allProductDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_product_id", notificationList.get(mProductHolder.getAdapterPosition()).getProductID()).apply();
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
                                if (notificationList.get(mProductHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase("") ||
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase(null) ||
                                        notificationList.get(mProductHolder.getAdapterPosition()).getProductImage().equalsIgnoreCase("null")) {
                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mProductHolder.getAdapterPosition()).getProductImage();
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
                                        "Likes : " + notificationList.get(mProductHolder.getAdapterPosition()).getProductLikeCount() /*+ "\n" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount() + "\n"+
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderFollowCount()*/;

                                System.out.println("all product detailssss======Other " + allProductDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/product/" + notificationList.get(mProductHolder.getAdapterPosition()).getProductID()
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
                /*ImageView mServicePic, mServiceImage;
                ImageButton mServiceAutokatta, mServiceOther, mCall, mLike, mServiceFavourite;
                RatingBar mServiceRating;
                TextView mServiceActionName, mServiceActionTime, mServiceTitle, mServiceName, mServiceType, mLikes, mShares;*/
                final ServiceNotifications mServiceHolder = (ServiceNotifications) holder;

                Log.i("Wall", "Service-LayType ->" + notificationList.get(position).getLayoutType());
                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    //mServiceHolder.mServiceCall.setVisibility(View.GONE);
                    //mServiceHolder.mServiceLike.setVisibility(View.GONE);
                    //mServiceHolder.mServiceUnlike.setVisibility(View.GONE);
                    mServiceHolder.mRelativeLike.setVisibility(View.GONE);
                } else {
                    //mServiceHolder.mServiceCall.setVisibility(View.VISIBLE);
                    //mServiceHolder.mServiceLike.setVisibility(View.VISIBLE);
                    // mServiceHolder.mServiceUnlike.setVisibility(View.VISIBLE);
                    mServiceHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                mServiceHolder.mServiceActionName.setText(notificationList.get(position).getSenderName() + " " +
                        notificationList.get(position).getAction() + "\n" +
                        notificationList.get(position).getServiceName()
                        + " service");

                mServiceHolder.mServiceActionTime.setText(notificationList.get(position).getDateTime());
                mServiceHolder.mServiceName.setText(notificationList.get(position).getServiceName());
                mServiceHolder.mServiceTitle.setText(notificationList.get(position).getServiceName());
                mServiceHolder.mServiceType.setText(notificationList.get(position).getServiceType());
                mServiceHolder.mLikes.setText("Likes(" + notificationList.get(position).getServiceLikeCount() + ")");
                mServiceHolder.mShares.setText("Shares(" + notificationList.get(position).getServiceShareCount() + ")");
                mServiceHolder.mServiceRating.setRating(notificationList.get(position).getServiceRating());

                /*mServiceHolder.mServiceCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mServiceHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });*/

                /* User profile pic */
                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mServiceHolder.mUserPic.setBackgroundResource(R.drawable.profile);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mServiceHolder.mUserPic);
                }

                /* Product pic */
                if (notificationList.get(position).getServiceImage() == null ||
                        notificationList.get(position).getServiceImage().equals("") ||
                        notificationList.get(position).getServiceImage().equals("null")) {
                    mServiceHolder.mServiceImage.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url)+ notificationList.get(position).getServiceImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mServiceHolder.mServiceImage);
                }

                /* Like & Unlike Functionality */
                if (notificationList.get(position).getServiceLikeStatus().equalsIgnoreCase("yes")) {
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
                                notificationList.get(mServiceHolder.getAdapterPosition()).getServiceID(), 0, 0);

                        service_likecountint = notificationList.get(mServiceHolder.getAdapterPosition()).getServiceLikeCount();
                        service_likecountint = service_likecountint - 1;
                        mServiceHolder.mLikes.setText("Likes(" + service_likecountint + ")");
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServiceLikeCount(service_likecountint);
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServiceLikeStatus("no");
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
                                notificationList.get(mServiceHolder.getAdapterPosition()).getServiceID(), 0, 0);

                        service_likecountint = notificationList.get(mServiceHolder.getAdapterPosition()).getServiceLikeCount();
                        service_likecountint = service_likecountint + 1;
                        mServiceHolder.mLikes.setText("Likes(" + service_likecountint + ")");
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServiceLikeCount(service_likecountint);
                        notificationList.get(mServiceHolder.getAdapterPosition()).setServiceLikeStatus("yes");
                    }
                });
                
                /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mServiceHolder.mServiceFavourite.setVisibility(View.VISIBLE);
                    mServiceHolder.mServiceUnfav.setVisibility(View.GONE);
                } else {
                    mServiceHolder.mServiceUnfav.setVisibility(View.VISIBLE);
                    mServiceHolder.mServiceFavourite.setVisibility(View.GONE);
                }

                mServiceHolder.mServiceFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mServiceHolder.getAdapterPosition()).getActionID();
                        mServiceHolder.mServiceFavourite.setVisibility(View.GONE);
                        mServiceHolder.mServiceUnfav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mServiceHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mServiceHolder.mServiceUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mServiceHolder.getAdapterPosition()).getActionID();
                        mServiceHolder.mServiceUnfav.setVisibility(View.GONE);
                        mServiceHolder.mServiceFavourite.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mServiceHolder.getAdapterPosition()).setMyFavStatus("yes");
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
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServiceLikeCount() + "=" +
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServiceImage();

                                System.out.println("all service detailssss======Auto " + allServiceDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allServiceDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_service_id", notificationList.get(mServiceHolder.getAdapterPosition()).getServiceID()).apply();
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

                                if (notificationList.get(mServiceHolder.getAdapterPosition()).getServiceImage().equalsIgnoreCase("") ||
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServiceImage().equalsIgnoreCase(null) ||
                                        notificationList.get(mServiceHolder.getAdapterPosition()).getServiceImage().equalsIgnoreCase("null")) {
                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mServiceHolder.getAdapterPosition()).getServiceImage();
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
                                        "Likes : " + notificationList.get(mServiceHolder.getAdapterPosition()).getServiceLikeCount() /*+ "\n" +
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderLikeCount() + "\n"+
                                        notificationList.get(mProfileHolder.getAdapterPosition()).getSenderFollowCount()*/;

                                System.out.println("all service detailssss======Other " + allServiceDetails);

                                intent.setType("text/plain");

                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my store on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/service/" + notificationList.get(mServiceHolder.getAdapterPosition()).getServiceID()
                                        //   + "/" + notificationList.get(mServiceHolder.getAdapterPosition()).getStoreContact()
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

                final PostNotifications mPostHolder = (PostNotifications) holder;

                Log.i("Wall", "Post-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    mPostHolder.mRelativeLike.setVisibility(View.GONE);

                } else {
                    mPostHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                mPostHolder.mAction.setText(notificationList.get(position).getSenderName() + " "
                        + notificationList.get(position).getAction() + " " + "status");

                /*if (notificationList.get(position).getStatus().startsWith("http://")||notificationList.get(position).getStatus().startsWith("https://")){
                    mPreview.setVisibility(View.VISIBLE);
                    mPostHolder.mStatusText.setVisibility(View.GONE);
                    mPreview.setData(obj.statusld.toString());
                    Log.i("SetData","->"+obj.statusld.toString());
                    sid = obj.status_idld.toString();
                }else{
                    mPreview.setVisibility(View.GONE);
                    mPostHolder.mStatusText.setVisibility(View.VISIBLE);
                    mPostHolder.mStatusText.setText(notificationList.get(position).getStatus());
                }*/

                mPostHolder.mActionTime.setText(notificationList.get(position).getDateTime());
                mPostHolder.mStatusText.setText(notificationList.get(position).getStatus());
                
                 /* Sender Profile Pic */

                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mPostHolder.mProfile_pic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mPostHolder.mProfile_pic);
                }

                /*mPostHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mPostHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });*/
                
                /* Like & Unlike Functionality */

                if (notificationList.get(position).getStatusLikeStatus().equalsIgnoreCase("yes")) {
                    mPostHolder.mLike.setVisibility(View.VISIBLE);
                    mPostHolder.mUnlike.setVisibility(View.GONE);
                } else {
                    mPostHolder.mUnlike.setVisibility(View.VISIBLE);
                    mPostHolder.mLike.setVisibility(View.GONE);
                }

                mPostHolder.mLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unlike web service
                        String otherContact = notificationList.get(mPostHolder.getAdapterPosition()).getSender();
                        int statusId = notificationList.get(mPostHolder.getAdapterPosition()).getStatusID();
                        mPostHolder.mLike.setVisibility(View.GONE);
                        mPostHolder.mUnlike.setVisibility(View.VISIBLE);
                        mApiCall.UnLike(mLoginContact, otherContact, "7", 0, 0, 0, 0, 0, statusId, 0);
                        notificationList.get(mPostHolder.getAdapterPosition()).setStatusLikeStatus("no");
                    }
                });

                mPostHolder.mUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Like web service
                        String otherContact = notificationList.get(mPostHolder.getAdapterPosition()).getSender();
                        int statusId = notificationList.get(mPostHolder.getAdapterPosition()).getStatusID();
                        mPostHolder.mUnlike.setVisibility(View.GONE);
                        mPostHolder.mLike.setVisibility(View.VISIBLE);
                        mApiCall.Like(mLoginContact, otherContact, "7", 0, 0, 0, 0, 0, statusId, 0);
                        notificationList.get(mPostHolder.getAdapterPosition()).setStatusLikeStatus("yes");
                    }
                });

                break;

            case 8:
                /*CardView mSearchCardView;
                ImageView mUserPic;
                ImageButton mSearchAutokattaShare, mCall, mSearchLike, mSearchUnlike, mSearchFavorite, mSearchUnfav;
                TextView mSearchActionName, mSearchActionTime, mSearchCategory, mSearchBrand, mSearchModel, mSearchPrice, mSearchYear,
                        mSearchDate, mSearchLeads;*/
                final SearchNotifications mSearchHolder = (SearchNotifications) holder;

                Log.i("Wall", "Search-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    //mStoreHolder.mCall.setVisibility(View.GONE);
                    mSearchHolder.mRelativeLike.setVisibility(View.GONE);

                } else {
                    //mStoreHolder.mCall.setVisibility(View.VISIBLE);
                    mSearchHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                mSearchHolder.mSearchActionName.setText(notificationList.get(position).getSenderName() + " " +
                        notificationList.get(position).getAction() + " " + "search");

                mSearchHolder.mSearchActionTime.setText(notificationList.get(position).getDateTime());
                mSearchHolder.mSearchCategory.setText(notificationList.get(position).getSearchCategory());
                mSearchHolder.mSearchBrand.setText(notificationList.get(position).getSearchBrand());
                mSearchHolder.mSearchModel.setText(notificationList.get(position).getSearchModel());
                mSearchHolder.mSearchPrice.setText(notificationList.get(position).getSearchPrice());
                mSearchHolder.mSearchYear.setText(notificationList.get(position).getSearchManfYear());
                mSearchHolder.mSearchDate.setText(notificationList.get(position).getSearchDate());
                mSearchHolder.mSearchLeads.setText(String.valueOf(notificationList.get(position).getSearchLeads()));

            /* Sender Profile Pic */

                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mSearchHolder.mUserPic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mSearchHolder.mUserPic);
                }

                /*mSearchHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mSearchHolder.getAdapterPosition()).getSender();
                        call(otherContact);
                    }
                });*/
                
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

      /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mSearchHolder.mSearchFavorite.setVisibility(View.VISIBLE);
                    mSearchHolder.mSearchUnfav.setVisibility(View.GONE);
                } else {
                    mSearchHolder.mSearchUnfav.setVisibility(View.VISIBLE);
                    mSearchHolder.mSearchFavorite.setVisibility(View.GONE);
                }

                mSearchHolder.mSearchFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mSearchHolder.getAdapterPosition()).getActionID();
                        mSearchHolder.mSearchFavorite.setVisibility(View.GONE);
                        mSearchHolder.mSearchUnfav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mSearchHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mSearchHolder.mSearchUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mSearchHolder.getAdapterPosition()).getActionID();
                        mSearchHolder.mSearchUnfav.setVisibility(View.GONE);
                        mSearchHolder.mSearchFavorite.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mSearchHolder.getAdapterPosition()).setMyFavStatus("yes");
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


                final ActiveNotifications mActiveHolder = (ActiveNotifications) holder;
                Log.i("Wall", "Auction-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    shareKey = "myauction";
                    mActiveHolder.mAuctionGoing.setVisibility(View.GONE);
                    mActiveHolder.mAuctionIgnore.setVisibility(View.GONE);

                } else {
                    shareKey = "auction";
                    mActiveHolder.mAuctionGoing.setVisibility(View.VISIBLE);
                    mActiveHolder.mAuctionIgnore.setVisibility(View.VISIBLE);
                }

                mActiveHolder.mAuctionTitle.setText(notificationList.get(position).getActionTitle());
                mActiveHolder.mAuctionNoOfVehicles.setText(notificationList.get(position).getNoOfVehicles());
                mActiveHolder.mAuctionEndDate.setText(notificationList.get(position).getEndDate());
                mActiveHolder.mAuctionEndTime.setText(notificationList.get(position).getEndTime());
                mActiveHolder.mAuctionType.setText(notificationList.get(position).getAuctionType());
                mActiveHolder.mAuctionGoingCount.setText(notificationList.get(position).getGoingCount());
                mActiveHolder.mAuctionIgnoreCount.setText(notificationList.get(position).getIgnoreCount());

                mActiveHolder.mAuctionIgnore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mConnectionDetector.isConnectedToInternet()) {
                            //JSON to Gson conversion
                            Gson gson = new GsonBuilder()
                                    .setLenient()
                                    .create();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(mActivity.getString(R.string.base_url))
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .client(initLog().build())
                                    .build();

                            ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                            Call<String> add = serviceApi.addIgnoreGoingMe(mLoginContact, mAuctionId, 0, 0, 0, 0, "ignore");
                            add.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body() != null) {
                                            if (response.body().equals("success")) {
                                                mActiveHolder.mAuctionIgnore.setEnabled(false);
                                                mActiveHolder.mAuctionGoing.setEnabled(true);
                                                //mGoLive.setEnabled(false);
                                            }
                                        }
                                    } else {
                                        Log.e("No", "Response");
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        } else
                            CustomToast.customToast(mActivity.getApplicationContext(), mActivity.getString(R.string.no_internet));

                    }

                });

                mActiveHolder.mAuctionGoing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
                        alertDialogBuilder
                                .setMessage("Pay EMD or auction platform fees")
                                .setCancelable(false)
                                .setPositiveButton("Now",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                try {
                                                    if (mConnectionDetector.isConnectedToInternet()) {
                                                        //JSON to Gson conversion
                                                        Gson gson = new GsonBuilder()
                                                                .setLenient()
                                                                .create();
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                                .client(initLog().build())
                                                                .build();

                                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                        Call<String> add = serviceApi.addIgnoreGoingMe(mLoginContact, mAuctionId, 0, 0, 0, 0, "going");
                                                        add.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                if (response.isSuccessful()) {
                                                                    if (response.body() != null) {
                                                                        if (response.body().equals("success")) {
                                                                            mActiveHolder.mAuctionGoing.setVisibility(View.GONE);
                                                                            mActiveHolder.mAuctionIgnore.setVisibility(View.GONE);

                                                                            //paymentMethodCall(null, null, "");
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.e("No", "Response");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                t.printStackTrace();
                                                            }
                                                        });
                                                    } else
                                                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        })
                                .setNegativeButton("Later",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                // if this button is clicked, just close
                                                // the dialog box and do nothing
                                                //dialog.cancel();
                                                //boolGoing = false;
                                                try {
                                                    if (mConnectionDetector.isConnectedToInternet()) {
                                                        //JSON to Gson conversion
                                                        Gson gson = new GsonBuilder()
                                                                .setLenient()
                                                                .create();
                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                                .client(initLog().build())
                                                                .build();

                                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                                        Call<String> add = serviceApi.addIgnoreGoingMe(mLoginContact, mAuctionId, 0, 0, 0, 0, "going");
                                                        add.enqueue(new Callback<String>() {
                                                            @Override
                                                            public void onResponse(Call<String> call, Response<String> response) {
                                                                if (response.isSuccessful()) {
                                                                    if (response.body() != null) {
                                                                        if (response.body().equals("success")) {
                                                                            mActiveHolder.mAuctionGoing.setVisibility(View.GONE);
                                                                            mActiveHolder.mAuctionIgnore.setVisibility(View.GONE);
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.e("No", "Response");
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                t.printStackTrace();
                                                            }
                                                        });
                                                    } else
                                                        CustomToast.customToast(mActivity.getApplicationContext(), mActivity.getString(R.string.no_internet));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
                mActiveHolder.mAuctionAutokattaShare.setOnClickListener(new View.OnClickListener() {
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

                                String allVehicleDetails = mActiveHolder.mAuctionTitle.getText().toString() + "=" +
                                        mActiveHolder.mAuctionNoOfVehicles.getText().toString() + "=" +
                                        mActiveHolder.mAuctionEndDate.getText().toString() + "=" +
                                        mActiveHolder.mAuctionEndTime.getText().toString() + "=" +
                                        mActiveHolder.mAuctionType.getText().toString() + "=" +
                                        mActiveHolder.mAuctionGoingCount.getText().toString() + "=" +
                                        mActiveHolder.mAuctionIgnoreCount.getText().toString() + "=" +
                                        shareKey;


                                System.out.println("all vehicle detailssss======Auto " + allVehicleDetails);

                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allVehicleDetails).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_auction_id", notificationList.get(mActiveHolder.getAdapterPosition()).getAuctionID()).apply();
                                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "auction").apply();


                                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                                mActivity.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                if (notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase("") ||
//                                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase(null) ||
//                                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage().equalsIgnoreCase("null")) {
//                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
//                                } else {
//                                    imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage();
//                                }
//                                Log.e("TAG", "img : " + imagename);
//
//                                DownloadManager.Request request = new DownloadManager.Request(
//                                        Uri.parse(imagename));
//                                request.allowScanningByMediaScanner();
//                                String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
//                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
//                                Log.e("ShareImagePath :", filename);
//                                Log.e("TAG", "img : " + imagename);
//
//                                DownloadManager manager = (DownloadManager) mActivity.getApplication()
//                                        .getSystemService(Context.DOWNLOAD_SERVICE);
//
//                                Log.e("TAG", "img URL: " + imagename);
//
//                                manager.enqueue(request);
//
//                                imageFilePath = "/storage/emulated/0/Download/" + filename;
//                                System.out.println("ImageFilePath:" + imageFilePath);

                                String allVehicleDetails = "Auction Title : " + mActiveHolder.mAuctionTitle.getText().toString() + "\n" +
                                        "No.of Vehicles : " + mActiveHolder.mAuctionNoOfVehicles.getText().toString() + "\n" +
                                        "Auction End Date : " + mActiveHolder.mAuctionEndDate.getText().toString() + "\n" +
                                        "Auction End Time : " + mActiveHolder.mAuctionEndTime.getText().toString() + "\n" +
                                        "Auction Type : " + mActiveHolder.mAuctionType.getText().toString() + "\n" +
                                        "Auction Going Count : " + mActiveHolder.mAuctionGoingCount.getText().toString() + "\n" +
                                        "Auction Ignore Count : " + mActiveHolder.mAuctionIgnoreCount.getText().toString();


                                System.out.println("all vehicle detailssss======Other " + allVehicleDetails);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(mActiveHolder.getAdapterPosition()).getActionID() + "/" + mLoginContact
                                        + "\n" + "\n" + allVehicleDetails);
//                                intent.setType("image/jpeg");
//                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
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


                ImageView mAuctionPic;
                ImageButton mAuctionAutokatta, mAuctionOther, mAuctionFavourite;

                break;

            case 10:
                /*CardView mVehicleCardView;
                ImageView mUserPic, mVehicleImage;
                ImageButton mVehicleAutokattaShare, mCall, mVehicleLike, mVehicleUnlike, mVehicleFavourite, mVehicleUnfav,
                        mFollow, mUnfollow;
                TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                        mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikes, mShares;*/

                final UploadVehicleNotifications mUpVehicleHolder = (UploadVehicleNotifications) holder;
                Log.i("Wall", "Vehicle-LayType ->" + notificationList.get(position).getLayoutType());

                if (notificationList.get(position).getLayoutType().equalsIgnoreCase("MyAction")) {
                    //mStoreHolder.mCall.setVisibility(View.GONE);
                    mUpVehicleHolder.mRelativeLike.setVisibility(View.GONE);

                } else {
                    //mStoreHolder.mCall.setVisibility(View.VISIBLE);
                    mUpVehicleHolder.mRelativeLike.setVisibility(View.VISIBLE);
                }

                mUpVehicleHolder.mActionName.setText(notificationList.get(position).getSenderName() + " "
                        + notificationList.get(position).getAction() + " "
                        + "\n'" + notificationList.get(position).getUpVehicleTitle() + "'"
                        + " Vehicle In"
                        + "'" + notificationList.get(position).getGroupName() + "'"
                        + " Group");

                mUpVehicleHolder.mActionTime.setText(notificationList.get(position).getDateTime());
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

                if (notificationList.get(position).getSenderPicture() == null ||
                        notificationList.get(position).getSenderPicture().equals("") ||
                        notificationList.get(position).getSenderPicture().equals("null")) {
                    mUpVehicleHolder.mUserPic.setBackgroundResource(R.drawable.logo48x48);
                } else {
                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + notificationList.get(position).getSenderPicture())
                            .bitmapTransform(new CropCircleTransformation(mActivity))
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
                            .load(mActivity.getString(R.string.base_image_url)+ notificationList.get(position).getUpVehicleImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mUpVehicleHolder.mVehicleImage);
                }

               /* mUpVehicleHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleContact();
                        call(otherContact);
                    }
                });*/

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
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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

      /* Fav & Unfav Functionality */
                if (notificationList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
                    mUpVehicleHolder.mVehicleFavourite.setVisibility(View.VISIBLE);
                    mUpVehicleHolder.mVehicleUnfav.setVisibility(View.GONE);
                } else {
                    mUpVehicleHolder.mVehicleUnfav.setVisibility(View.VISIBLE);
                    mUpVehicleHolder.mVehicleFavourite.setVisibility(View.GONE);
                }

                mUpVehicleHolder.mVehicleFavourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Unfavorite web service
                        int notiId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getActionID();
                        mUpVehicleHolder.mVehicleFavourite.setVisibility(View.GONE);
                        mUpVehicleHolder.mVehicleUnfav.setVisibility(View.VISIBLE);
                        mApiCall.removeFromFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setMyFavStatus("no");
                    }
                });

                mUpVehicleHolder.mVehicleUnfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        int notiId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getActionID();
                        mUpVehicleHolder.mVehicleUnfav.setVisibility(View.GONE);
                        mUpVehicleHolder.mVehicleFavourite.setVisibility(View.VISIBLE);
                        mApiCall.addToFavorite(mLoginContact, "", 0, "", notiId);
                        notificationList.get(mUpVehicleHolder.getAdapterPosition()).setMyFavStatus("yes");
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
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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
                        int vehicleId = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUploadVehicleID();
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
                                        putInt("Share_vehicle_id", notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUploadVehicleID()).apply();
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
                                    imagename = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                                } else {
                                    imagename = mActivity.getString(R.string.base_image_url) + notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUpVehicleImage();
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
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(mUpVehicleHolder.getAdapterPosition()).getUploadVehicleID() + "/" + mLoginContact
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
                final ShareNotifications mShareolder = (ShareNotifications) holder;

                mShareolder.mCaptionData.setText(notificationList.get(position).getShareSubData());
                int sublayout = Integer.parseInt(notificationList.get(position).getSubLayout());
                Log.i("oooo", "subId" + String.valueOf(sublayout));
                Log.i("oooo", "subData" + notificationList.get(position).getShareSubData());

                switch (sublayout) {
                    case 1:
                        break;

                    case 2:
                        break;

                    case 4:
                        break;

                    case 5:
                        break;

                    case 6:
                        break;

                    case 7:
                        break;

                    case 8:
                        break;

                    case 9:
                        break;

                }
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
