package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.WallResponse;
import autokatta.com.view.ShareWithinAppActivity;
import retrofit2.Response;

/**
 * Created by ak-003 on 7/7/17.
 */

public class GroupNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestNotifier {

    private Activity mActivity;
    private List<WallResponse.Success.WallNotification> notificationList = new ArrayList<>();
    private String mLoginContact;
    private ApiCall mApiCall;
    private int vehicle_likecountint, vehicle_followcountint, vehicle_sharecountint;

    /* constructor */
    public GroupNotificationAdapter(Activity activity, List<WallResponse.Success.WallNotification> groupNotiList1,
                                    String myContact) {
        mActivity = activity;
        notificationList = groupNotiList1;
        mApiCall = new ApiCall(mActivity, this);
        mLoginContact = myContact;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(notificationList.get(position).getLayout());
    }


    /* view class Group*/
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
        Uploaded Vehicle Notification Class...
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        Log.i("GroupNoti_Layout", "onCreate" + viewType);
        switch (viewType) {
            case 3:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_group_notifications, parent, false);
                return new GroupNotifications(mView);

            case 10:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_vehicle_notifications, parent, false);
                return new UploadVehicleNotifications(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //final FavouriteAllResponse objAllResponse = allResponseList.get(position);
        Log.i("GroupNoti_Layout", "BindHolder" + holder.getItemViewType());
        switch (holder.getItemViewType()) {

            case 3:
                final GroupNotifications mGroupHolder = (GroupNotifications) holder;

                mGroupHolder.mActionName.setText(notificationList.get(position).getSenderName() + " " +
                        notificationList.get(position).getAction() + " " + notificationList.get(position).getReceiverName() +
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

            case 10:
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
                        + "'" + notificationList.get(position).getUpVehicleTitle() + "'"
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

               /* mUpVehicleHolder.mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String otherContact = notificationList.get(mUpVehicleHolder.getAdapterPosition()).getSender();
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
        }

    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "success_like":
                    CustomToast.customToast(mActivity, "Liked");
                    break;
                case "success_unlike":
                    CustomToast.customToast(mActivity, "Unliked");
                    break;
                case "success_favourite":
                    CustomToast.customToast(mActivity, "Favorite");
                    break;
                case "success_remove":
                    CustomToast.customToast(mActivity, "Unfavorite");
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
                    , "Group Notification Adapter");
            error.printStackTrace();
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


}
