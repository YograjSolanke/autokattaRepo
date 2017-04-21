package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.FavouriteAllResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 20/4/17.
 */

public class FavouriteNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<FavouriteAllResponse> allResponseList = new ArrayList<>();
    private int storelikecountint;
    private String mLoginContact = "";
    private ApiCall mApiCall;

    public FavouriteNotificationAdapter(Activity mActivity, List<FavouriteAllResponse> responseList) {
        this.mActivity = mActivity;
        allResponseList = responseList;
        mApiCall = new ApiCall(mActivity, this);
        mLoginContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "7841023392");
    }

    /*
    Profile Notification Class...
     */
    private static class ProfileNotifications extends RecyclerView.ViewHolder {
        CardView mProfileCardView;
        ImageView mProfilePic;
        ImageButton mShareAutokatta, mShareOther, mCall, mLike;
        TextView mProfileName, mProfileContact, mProfileTitle, mUserName, mProfileWorkAt, mProfileWebSite, mLocation,
                mFollowCount, mLikes, mShares;

        private ProfileNotifications(View profileView) {
            super(profileView);
            mProfileCardView = (CardView) profileView.findViewById(R.id.profile_card_view);
            mProfilePic = (ImageView) profileView.findViewById(R.id.pro_pic);
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
        TextView mStoreActionName, mActionTime, mStoreName, mStoreText, mStoreWorkAt, mStoreWebSite, mStoreTiming, mStoreWorkingDay,
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
            mStoreText = (TextView) storeView.findViewById(R.id.store_txt);
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
        ImageView mGroupPic, mGroupImage;
        ImageButton mGroupFavourite;
        TextView mGroupActionName, mGroupActionTime, mGroupName, mGroupMembers, mGroupNoOfVehicles;

        private GroupNotifications(View groupView) {
            super(groupView);
            mGroupCardView = (CardView) groupView.findViewById(R.id.group_card_view);
            mGroupPic = (ImageView) groupView.findViewById(R.id.group_pic);
            mGroupImage = (ImageView) groupView.findViewById(R.id.group_image);

            mGroupFavourite = (ImageButton) groupView.findViewById(R.id.group_favourite);

            mGroupActionName = (TextView) groupView.findViewById(R.id.group_action_names);
            mGroupActionTime = (TextView) groupView.findViewById(R.id.group_action_time);
            mGroupName = (TextView) groupView.findViewById(R.id.group_name);
            mGroupMembers = (TextView) groupView.findViewById(R.id.group_members);
            mGroupNoOfVehicles = (TextView) groupView.findViewById(R.id.group_no_of_vehicles);
        }
    }

    /*
    Vehicle Notification Class...
     */
    private static class VehicleNotifications extends RecyclerView.ViewHolder {
        CardView mVehicleCardView;
        ImageView mVehiclePic, mVehicleImage;
        ImageButton mVehicleAutokatta, mVehicleOther, mCall, mLike, mVehicleFavourite;
        TextView mVehicleActionName, mVehicleActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikes, mShares;

        private VehicleNotifications(View vehicleView) {
            super(vehicleView);
            mVehicleCardView = (CardView) vehicleView.findViewById(R.id.store_card_view);
            mVehiclePic = (ImageView) vehicleView.findViewById(R.id.store_pic);
            mVehicleImage = (ImageView) vehicleView.findViewById(R.id.store_image);

            mVehicleAutokatta = (ImageButton) vehicleView.findViewById(R.id.share_autokatta);
            mVehicleOther = (ImageButton) vehicleView.findViewById(R.id.share_other);
            mCall = (ImageButton) vehicleView.findViewById(R.id.call);
            mLike = (ImageButton) vehicleView.findViewById(R.id.like);
            mVehicleFavourite = (ImageButton) vehicleView.findViewById(R.id.vehicle_favourite);

            mVehicleActionName = (TextView) vehicleView.findViewById(R.id.vehicle_action_name);
            mVehicleActionTime = (TextView) vehicleView.findViewById(R.id.vehicle_action_time);
            mVehicleRegistration = (TextView) vehicleView.findViewById(R.id.vehicle_registration);
            mVehicleName = (TextView) vehicleView.findViewById(R.id.vehicle_name);
            mVehiclePrice = (TextView) vehicleView.findViewById(R.id.vehicle_price);
            mVehicleBrand = (TextView) vehicleView.findViewById(R.id.vehicle_brand);
            mVehicleModel = (TextView) vehicleView.findViewById(R.id.vehicle_model);
            mVehicleYearOfMfg = (TextView) vehicleView.findViewById(R.id.vehicle_year_of_mfg);
            mVehicleKmsHrs = (TextView) vehicleView.findViewById(R.id.vehicle_kms_hrs);
            mVehicleLocation = (TextView) vehicleView.findViewById(R.id.vehicle_locations);
            mRtoCity = (TextView) vehicleView.findViewById(R.id.vehicle_rto_city);
            mLikes = (TextView) vehicleView.findViewById(R.id.likes);
            mShares = (TextView) vehicleView.findViewById(R.id.share);
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
        ImageView mVehiclePic, mVehicleImage;
        ImageButton mVehicleAutokatta, mVehicleOther, mCall, mLike, mVehicleFavourite;
        TextView mVehicleActionName, mVehicleActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikes, mShares;

        private UpVehicleNotifications(View upVehicleView) {
            super(upVehicleView);
            mVehicleCardView = (CardView) upVehicleView.findViewById(R.id.store_card_view);
            mVehiclePic = (ImageView) upVehicleView.findViewById(R.id.store_pic);
            mVehicleImage = (ImageView) upVehicleView.findViewById(R.id.store_image);

            mVehicleAutokatta = (ImageButton) upVehicleView.findViewById(R.id.share_autokatta);
            mVehicleOther = (ImageButton) upVehicleView.findViewById(R.id.share_other);
            mCall = (ImageButton) upVehicleView.findViewById(R.id.call);
            mLike = (ImageButton) upVehicleView.findViewById(R.id.like);
            mVehicleFavourite = (ImageButton) upVehicleView.findViewById(R.id.vehicle_favourite);

            mVehicleActionName = (TextView) upVehicleView.findViewById(R.id.vehicle_action_name);
            mVehicleActionTime = (TextView) upVehicleView.findViewById(R.id.vehicle_action_time);
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
        //return position % 2 * 2;
        return allResponseList.get(position).getLayoutNo();
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
                return new UpVehicleNotifications(mView);

            case 11:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_adding_share_notifications, parent, false);
                return new ShareNotifications(mView);

            case 111:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_adding_share_notifications, parent, false);
                return new ShareNotifications(mView);

            case 112:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_adding_share_notifications, parent, false);
                return new ShareNotifications(mView);

            case 113:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_adding_share_notifications, parent, false);
                return new ShareNotifications(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final FavouriteAllResponse objAllResponse = allResponseList.get(position);
        Log.i("Layout", "BindHolder" + holder.getItemViewType());
        switch (holder.getItemViewType()) {

            case 1:
                ProfileNotifications profile = (ProfileNotifications) holder;

                break;
            case 2:
                final String allDetails, store_id;

                final StoreNotifications store = (StoreNotifications) holder;

                /*
                ImageView mStorePic, mStoreImage;
        ImageButton mShareAutokatta, mShareOther, mCall, mLike, mFollow;
        RatingBar mStoreRating;
        TextView mStoreActionName, mActionTime, mStoreName, mStoreText, mStoreWorkAt, mStoreWebSite, mStoreTiming, mStoreWorkingDay,
                mStoreLocation, mFollowCount, mLikes, mShares;
                 */
                //holder.deleteStoreNoti.setBackgroundResource(R.drawable.deletimage);

                try {
                    DateFormat date = new SimpleDateFormat(" MMM dd ");
                    DateFormat time = new SimpleDateFormat(" hh:mm a");
                    System.out.println("Date: " + date.format(objAllResponse.getDatetime()));
                    System.out.println("Time: " + time.format(objAllResponse.getDatetime()));

                    store.mActionTime.setText(date.format(objAllResponse.getDatetime()) + time.format(objAllResponse.getDatetime()));


                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*if(objAllResponse.sendernameld.toString().equals("you") && (objAllResponse.actionld.toString().equals("likes") ||objAllResponse.actionld.toString().equals("unlikes")))
                {
                    if(objAllResponse.actionld.toString().equals("likes")) {
                        holder.storeaction.setText(objAllResponse.sendernameld.toString() + " like " + objAllResponse.store_nameld.toString() + " " + "store");
                    }
                    if(objAllResponse.actionld.toString().equals("unlikes")) {
                        holder.storeaction.setText(objAllResponse.sendernameld.toString() + " unlike " + objAllResponse.store_nameld.toString() + " " + "store");
                    }
                }
                else {
                    holder.storeaction.setText(objAllResponse.sendernameld.toString() + " " + objAllResponse.actionld.toString() + " " + objAllResponse.store_nameld.toString() + " " + "store");
                }*/

                store.mStoreName.setText(objAllResponse.getStoreName());
                //store.storetype.setText(objAllResponse.store_typeld.toString());
                store.mStoreLocation.setText(objAllResponse.getStoreLocation());
                store.mStoreWebSite.setText(objAllResponse.getStoreWebsite());
                store.mStoreWorkingDay.setText(objAllResponse.getWorkingDays());
                store.mStoreTiming.setText(objAllResponse.getStoretiming());
                store.mFollowCount.setText("Followers(" + objAllResponse.getStorefollowcount() + ")");
                //store.storelikecnt.setText("Likes(" + objAllResponse.storelikecountld.toString() + ")");

                allDetails = "storename: " + store.mStoreName.getText().toString() + "\n" +
                        /*"storetype: "+holder.storetype.getText().toString()+"\n"+*/
                        "location: " + store.mStoreLocation.getText().toString() + "\n" +
                        "website: " + store.mStoreWebSite.getText().toString() + "\n" +
                        "workingday: " + store.mStoreWorkingDay.getText().toString() + "\n";
                System.out.println("all details=========" + allDetails);


                store_id = objAllResponse.getStoreId();

                if (objAllResponse.getStorelikestatus().equalsIgnoreCase("yes")) {
                    //store.mLike.setImageTintMode(PorterDuff.Mode.DARKEN);
                    store.mLike.setColorFilter(R.color.black); // black Tint
                }
                if (objAllResponse.getStorelikestatus().equalsIgnoreCase("no")) {
                    //store.mLike.setImageTintMode(PorterDuff.Mode.LIGHTEN);
                    store.mLike.setColorFilter(R.color.button_grey); // grey Tint
                }

                if (objAllResponse.getStorefollowstatus().equalsIgnoreCase("yes")) {
                    //store.mFollow.setImageTintMode(PorterDuff.Mode.DARKEN);
                    store.mFollow.setColorFilter(R.color.black); // black Tint
                }
                if (objAllResponse.getStorefollowstatus().equalsIgnoreCase("no")) {
                    //store.mFollow.setImageTintMode(PorterDuff.Mode.LIGHTEN);
                    store.mFollow.setColorFilter(R.color.button_grey); // grey Tint
                }

                if (objAllResponse.getStoreImage() == null || objAllResponse.getStoreImage().equals("") || objAllResponse.getStoreImage().equals("null"))

                    store.mStoreImage.setBackgroundResource(R.drawable.store);

                else {

                    /****************
                     Glide code for image uploading

                     *****************/
                    Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/store_profiles/" + objAllResponse.getStoreImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(store.mStoreImage);
                }

                store.mLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mOtherContact = "";
                        if (objAllResponse.getStorelikestatus().equalsIgnoreCase("no")) {


                            String storelikecountstr = objAllResponse.getStorelikecount();
                            storelikecountint = Integer.parseInt(storelikecountstr);

                            store.mLike.setColorFilter(R.color.black); // black Tint


                            if (objAllResponse.getStoreContact().contains(",")) {
                                String parts[] = objAllResponse.getStoreContact().split(",");
                                mOtherContact = parts[0];

                            } else
                                mOtherContact = objAllResponse.getStoreContact();

                            mApiCall.otherStoreLike(mLoginContact, mOtherContact, "2", store_id);
                            storelikecountint++;

                            objAllResponse.setStorelikecount(String.valueOf(storelikecountint));

                            store.mLikes.setText("Likes(" + storelikecountint + ")");
                            // locallist.get(position).storelikestatusld.toString();

                            objAllResponse.setStorelikestatus("yes");
                            allResponseList.set(position, objAllResponse);
                        } else {
                            String storelikecountstr = objAllResponse.getStorelikecount();
                            storelikecountint = Integer.parseInt(storelikecountstr);

                            store.mLike.setColorFilter(R.color.button_grey); // black Tint


                            if (objAllResponse.getStoreContact().contains(",")) {
                                String parts[] = objAllResponse.getStoreContact().split(",");
                                mOtherContact = parts[0];

                            } else
                                mOtherContact = objAllResponse.getStoreContact();

                            mApiCall.otherStoreUnlike(mLoginContact, mOtherContact, "2", store_id);
                            storelikecountint--;

                            objAllResponse.setStorelikecount(String.valueOf(storelikecountint));

                            store.mLikes.setText("Likes(" + storelikecountint + ")");
                            // locallist.get(position).storelikestatusld.toString();

                            objAllResponse.setStorelikestatus("yes");
                            allResponseList.set(position, objAllResponse);


                        }
                    }
                });

                break;

            case 3:
                GroupNotifications groups = (GroupNotifications) holder;

                break;

            case 4:
                VehicleNotifications vehicle = (VehicleNotifications) holder;

                break;

            case 5:
                ProductNotifications products = (ProductNotifications) holder;

                break;

            case 6:
                ServiceNotifications service = (ServiceNotifications) holder;

                break;

            case 7:
                PostNotifications post = (PostNotifications) holder;

                break;


            case 8:
                SearchNotifications search = (SearchNotifications) holder;

                break;

            case 9:
                ActiveNotifications active = (ActiveNotifications) holder;

                break;

            case 10:
                UpVehicleNotifications upVehicle = (UpVehicleNotifications) holder;

                break;

            case 11:
                ShareNotifications share = (ShareNotifications) holder;

                break;

            case 111:
                break;

            case 112:
                break;

            case 113:
                break;


        }
    }

    @Override
    public int getItemCount() {
        return allResponseList.size();
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
