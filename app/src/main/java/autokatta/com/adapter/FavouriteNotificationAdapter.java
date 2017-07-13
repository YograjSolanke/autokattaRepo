package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
import autokatta.com.view.OtherProfile;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
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
        ImageButton mGroupFavourite;
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
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_profile_notifications, parent, false);
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
                //buyer
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_buyer_notification, parent, false);
                return new BuyerNotifications(mView);

            case 112:
                //seller
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_seller_notification, parent, false);
                return new SellerNotifications(mView);

            case 113:
                //search
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_search_notifications, parent, false);
                return new SearchNotifications(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final FavouriteAllResponse objAllResponse = allResponseList.get(position);
        Log.i("Layout", "BindHolder" + holder.getItemViewType());
        switch (holder.getItemViewType()) {

            case 1:
                ProfileNotifications mProfileHolder = (ProfileNotifications) holder;

                break;
            case 2:
                final String allDetails, store_id;

                final StoreNotifications mStoreHolder = (StoreNotifications) holder;

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

                    mStoreHolder.mActionTime.setText(date.format(objAllResponse.getDatetime()) + time.format(objAllResponse.getDatetime()));


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

                mStoreHolder.mStoreName.setText(objAllResponse.getStoreName());
                //mStoreHolder.storetype.setText(objAllResponse.store_typeld.toString());
                mStoreHolder.mStoreLocation.setText(objAllResponse.getStoreLocation());
                mStoreHolder.mStoreWebSite.setText(objAllResponse.getStoreWebsite());
                mStoreHolder.mStoreWorkingDay.setText(objAllResponse.getWorkingDays());
                mStoreHolder.mStoreTiming.setText(objAllResponse.getStoretiming());
                mStoreHolder.mFollowCount.setText("Followers(" + objAllResponse.getStorefollowcount() + ")");
                //mStoreHolder.storelikecnt.setText("Likes(" + objAllResponse.storelikecountld.toString() + ")");

                allDetails = "storename: " + mStoreHolder.mStoreName.getText().toString() + "\n" +
                        /*"storetype: "+holder.storetype.getText().toString()+"\n"+*/
                        "location: " + mStoreHolder.mStoreLocation.getText().toString() + "\n" +
                        "website: " + mStoreHolder.mStoreWebSite.getText().toString() + "\n" +
                        "workingday: " + mStoreHolder.mStoreWorkingDay.getText().toString() + "\n";
                System.out.println("all details=========" + allDetails);


                store_id = objAllResponse.getStoreId();

                if (objAllResponse.getStorelikestatus().equalsIgnoreCase("yes")) {
                    //mStoreHolder.mLike.setImageTintMode(PorterDuff.Mode.DARKEN);
                    mStoreHolder.mLike.setColorFilter(R.color.black); // black Tint
                }
                if (objAllResponse.getStorelikestatus().equalsIgnoreCase("no")) {
                    //mStoreHolder.mLike.setImageTintMode(PorterDuff.Mode.LIGHTEN);
                    mStoreHolder.mLike.setColorFilter(R.color.button_grey); // grey Tint
                }

                if (objAllResponse.getStorefollowstatus().equalsIgnoreCase("yes")) {
                    //mStoreHolder.mFollow.setImageTintMode(PorterDuff.Mode.DARKEN);
                    mStoreHolder.mFollow.setColorFilter(R.color.black); // black Tint
                }
                if (objAllResponse.getStorefollowstatus().equalsIgnoreCase("no")) {
                    //mStoreHolder.mFollow.setImageTintMode(PorterDuff.Mode.LIGHTEN);
                    mStoreHolder.mFollow.setColorFilter(R.color.button_grey); // grey Tint
                }

                if (objAllResponse.getStoreImage() == null || objAllResponse.getStoreImage().equals("") || objAllResponse.getStoreImage().equals("null"))

                    mStoreHolder.mStoreImage.setBackgroundResource(R.drawable.store);

                else {

                    /****************
                     Glide code for image uploading

                     *****************/
                    Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/store_profiles/" + objAllResponse.getStoreImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mStoreHolder.mStoreImage);
                }

                mStoreHolder.mLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mOtherContact = "";
                        if (objAllResponse.getStorelikestatus().equalsIgnoreCase("no")) {


                            String storelikecountstr = objAllResponse.getStorelikecount();
                            storelikecountint = Integer.parseInt(storelikecountstr);

                            mStoreHolder.mLike.setColorFilter(R.color.black); // black Tint


                            if (objAllResponse.getStoreContact().contains(",")) {
                                String parts[] = objAllResponse.getStoreContact().split(",");
                                mOtherContact = parts[0];

                            } else
                                mOtherContact = objAllResponse.getStoreContact();

                            mApiCall.otherStoreLike(mLoginContact, mOtherContact, "2", store_id);
                            storelikecountint++;

                            objAllResponse.setStorelikecount(String.valueOf(storelikecountint));

                            mStoreHolder.mLikes.setText("Likes(" + storelikecountint + ")");
                            // allResponseList.get(position).storelikestatusld.toString();

                            objAllResponse.setStorelikestatus("yes");
                            allResponseList.set(mStoreHolder.getAdapterPosition(), objAllResponse);
                        } else {
                            String storelikecountstr = objAllResponse.getStorelikecount();
                            storelikecountint = Integer.parseInt(storelikecountstr);

                            mStoreHolder.mLike.setColorFilter(R.color.button_grey); // black Tint


                            if (objAllResponse.getStoreContact().contains(",")) {
                                String parts[] = objAllResponse.getStoreContact().split(",");
                                mOtherContact = parts[0];

                            } else
                                mOtherContact = objAllResponse.getStoreContact();

                            mApiCall.otherStoreUnlike(mLoginContact, mOtherContact, "2", store_id);
                            storelikecountint--;

                            objAllResponse.setStorelikecount(String.valueOf(storelikecountint));

                            mStoreHolder.mLikes.setText("Likes(" + storelikecountint + ")");
                            // allResponseList.get(position).storelikestatusld.toString();

                            objAllResponse.setStorelikestatus("yes");
                            allResponseList.set(mStoreHolder.getAdapterPosition(), objAllResponse);


                        }
                    }
                });

                break;

            case 3:
                GroupNotifications mGroupHolder = (GroupNotifications) holder;

                break;

            case 4:
                VehicleNotifications mVehicleHolder = (VehicleNotifications) holder;

                break;

            case 5:
                ProductNotifications mProductHolder = (ProductNotifications) holder;

                break;

            case 6:
                ServiceNotifications mServiceHolder = (ServiceNotifications) holder;

                break;

            case 7:
                PostNotifications mPostHolder = (PostNotifications) holder;

                break;


            case 8:
                SearchNotifications search = (SearchNotifications) holder;

                break;

            case 9:
                ActiveNotifications active = (ActiveNotifications) holder;

                break;

            case 10:
                UpVehicleNotifications mUpVehicleHolder = (UpVehicleNotifications) holder;

                break;

            case 11:
                ShareNotifications mShareHolder = (ShareNotifications) holder;

                break;

            case 111:
                //buyer

                final BuyerNotifications mBuyerHolder = (BuyerNotifications) holder;

                mBuyerHolder.mBuyerUserName.setText(allResponseList.get(position).getSendername());
                mBuyerHolder.mBuyerLocation.setText(allResponseList.get(position).getVlocationCity());

                mBuyerHolder.buyertext.setVisibility(View.VISIBLE);


                mBuyerHolder.checkBox1.setText(allResponseList.get(position).getVcategory());
                mBuyerHolder.checkBox2.setText(allResponseList.get(position).getVmanufacturer());
                mBuyerHolder.checkBox3.setText(allResponseList.get(position).getVmodel());
                mBuyerHolder.checkBox4.setText(allResponseList.get(position).getVyearOfManufacture());
                mBuyerHolder.checkBox5.setText(allResponseList.get(position).getVrtoCity());


                mBuyerHolder.checkBox6.setText(allResponseList.get(position).getScategory());
                mBuyerHolder.checkBox7.setText(allResponseList.get(position).getSmanufacturer());
                mBuyerHolder.checkBox8.setText(allResponseList.get(position).getSmodel());
                mBuyerHolder.checkBox9.setText(allResponseList.get(position).getSyearOfManufacture());
                mBuyerHolder.checkBox10.setText(allResponseList.get(position).getSrtoCity());

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
                } catch (Exception e) {
                    e.printStackTrace();
                }


                mBuyerHolder.checkBox1.setEnabled(false);
                mBuyerHolder.checkBox2.setEnabled(false);
                mBuyerHolder.checkBox3.setEnabled(false);
                mBuyerHolder.checkBox4.setEnabled(false);
                mBuyerHolder.checkBox5.setEnabled(false);

                mBuyerHolder.checkBox6.setEnabled(false);
                mBuyerHolder.checkBox7.setEnabled(false);
                mBuyerHolder.checkBox8.setEnabled(false);
                mBuyerHolder.checkBox9.setEnabled(false);
                mBuyerHolder.checkBox10.setEnabled(false);


                if (allResponseList.get(position).getSenderPic().equals(""))
                    mBuyerHolder.buyer_lead_image.setBackgroundResource(R.drawable.logo);
                else {

                    /****************
                     Glide code for image uploading

                     *****************/
                    Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + allResponseList.get(position).getSenderPic())
                            .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            //.placeholder(R.drawable.logo) //To show image before loading an original image.
                            //.error(R.drawable.blocked) //To show error image if problem in loading.
                            .into(mBuyerHolder.buyer_lead_image);
                }

                mBuyerHolder.mBuyerUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String otherContact = allResponseList.get(mBuyerHolder.getAdapterPosition()).getVcontactNo();

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
                        String otherContact = allResponseList.get(mBuyerHolder.getAdapterPosition()).getVcontactNo();

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
                        String otherContact = allResponseList.get(mBuyerHolder.getAdapterPosition()).getVcontactNo();
                        call(otherContact);
                    }
                });

                mBuyerHolder.deleteFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(mActivity)
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to delete this?")

                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        /*deleteFromFavourite("",mBuyerHolder.getAdapterPosition(),
                                                allResponseList.get(mBuyerHolder.getAdapterPosition()).getSsearchId(),
                                                allResponseList.get(mBuyerHolder.getAdapterPosition()).getVvehicleId(),
                                                "buyer");*/

                                    }
                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                });

                break;

            case 112:
                //seller

                final SellerNotifications mSellerHolder = (SellerNotifications) holder;
                mSellerHolder.mUserName.setText(allResponseList.get(position).getSendername());
                // mSellerHolder.UploadedDate.setText("Uploaded On:-"+"\n"+Vdate.get(position).toString());

                mSellerHolder.sellertext.setVisibility(View.VISIBLE);

                //To set Date
                try {

                    DateFormat date1 = new SimpleDateFormat(" MMM dd ");
                    DateFormat time1 = new SimpleDateFormat(" hh:mm a");
                    System.out.println("Date: " + date1.format(allResponseList.get(position).getVdate()));
                    System.out.println("Time: " + time1.format(allResponseList.get(position).getVdate()));

                    mSellerHolder.mDateTime.setText("Uploaded On:-" + "\n" + date1.format(allResponseList.get(position).getVdate()) +
                            time1.format(allResponseList.get(position).getVdate()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mSellerHolder.Title.setText(allResponseList.get(position).getVdate());
                // mSellerHolder.buyerlocation.setText(location_city.get(position).toString());

                mSellerHolder.mFavimg.setVisibility(View.GONE);
                mSellerHolder.deleteFav.setVisibility(View.VISIBLE);

                mSellerHolder.checkBox1.setText(allResponseList.get(position).getScategory());
                mSellerHolder.checkBox2.setText(allResponseList.get(position).getSmanufacturer());
                mSellerHolder.checkBox3.setText(allResponseList.get(position).getSmodel());
                mSellerHolder.checkBox4.setText(allResponseList.get(position).getSyearOfManufacture());
                mSellerHolder.checkBox5.setText(allResponseList.get(position).getSrtoCity());


                mSellerHolder.checkBox6.setText(allResponseList.get(position).getVcategory());
                mSellerHolder.checkBox7.setText(allResponseList.get(position).getVmonthOfManufacture());
                mSellerHolder.checkBox8.setText(allResponseList.get(position).getVmodel());
                mSellerHolder.checkBox9.setText(allResponseList.get(position).getVyearOfManufacture());
                mSellerHolder.checkBox10.setText(allResponseList.get(position).getVrtoCity());


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
                } catch (Exception e) {
                    e.printStackTrace();
                }


                mSellerHolder.checkBox1.setEnabled(false);
                mSellerHolder.checkBox2.setEnabled(false);
                mSellerHolder.checkBox3.setEnabled(false);
                mSellerHolder.checkBox4.setEnabled(false);
                mSellerHolder.checkBox5.setEnabled(false);

                mSellerHolder.checkBox6.setEnabled(false);
                mSellerHolder.checkBox7.setEnabled(false);
                mSellerHolder.checkBox8.setEnabled(false);
                mSellerHolder.checkBox9.setEnabled(false);
                mSellerHolder.checkBox10.setEnabled(false);


                /*mSellerHolder.Compare.setVisibility(View.GONE);
                mSellerHolder.Vehic_cnt.setVisibility(View.GONE);
                mSellerHolder.chckbox.setVisibility(View.GONE);*/


                final String imagenames = allResponseList.get(position).getVimage();
                ArrayList<String> iname = new ArrayList<String>();

                try {
                    String[] imagenamecame = imagenames.split(",");

                    if (imagenamecame.length != 0) {
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
                                    .load("http://autokatta.com/mobile/uploads/" + iname.get(l).replaceAll(" ", "%20"))
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
                } catch (Exception e) {
                    e.printStackTrace();
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
                        String otherContact = allResponseList.get(mSellerHolder.getAdapterPosition()).getVcontactNo();

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
                        String otherContact = allResponseList.get(mSellerHolder.getAdapterPosition()).getVcontactNo();
                        call(otherContact);
                    }
                });


                mSellerHolder.deleteFav.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(mActivity)
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to delete this?")

                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        /*deleteFromFavourite("", mSellerHolder.getAdapterPosition(),
                                                allResponseList.get(mSellerHolder.getAdapterPosition()).getSsearchId(),
                                                allResponseList.get(mSellerHolder.getAdapterPosition()).getVvehicleId(),
                                                "seller");*/
                                        dialog.dismiss();
                                    }
                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                });

                break;

            case 113:
                SearchNotifications mSearchHolder = (SearchNotifications) holder;

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
