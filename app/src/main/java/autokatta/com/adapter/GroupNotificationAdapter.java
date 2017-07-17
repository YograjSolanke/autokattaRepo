package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.WallResponse;

/**
 * Created by ak-003 on 7/7/17.
 */

public class GroupNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<WallResponse.Success.WallNotification> groupNotiList = new ArrayList<>();
    private String mLoginContact;

    /* constructor */
    public GroupNotificationAdapter(Activity activity, List<WallResponse.Success.WallNotification> groupNotiList1,
                                    String myContact) {
        mActivity = activity;
        groupNotiList = groupNotiList1;
        mLoginContact = myContact;
    }

    @Override
    public int getItemCount() {
        return groupNotiList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(groupNotiList.get(position).getLayout());
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
        ImageButton mShareAutokatta, mShareOther, mCall, mLike, mVehicleFavourite;
        TextView mActionName, mActionTime, mVehicleRegistration, mVehicleName, mVehiclePrice, mVehicleBrand,
                mVehicleModel, mVehicleYearOfMfg, mVehicleKmsHrs, mVehicleLocation, mRtoCity, mLikesTxt, mSharesTxt;

        private UploadVehicleNotifications(View upVehicleView) {
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

                mGroupHolder.mActionName.setText(groupNotiList.get(position).getSenderName() + " " +
                        groupNotiList.get(position).getAction() + " " + groupNotiList.get(position).getReceiverName() +
                        " in " + groupNotiList.get(position).getGroupName()
                        + " group");

                mGroupHolder.mGroupName.setText(groupNotiList.get(position).getGroupName());
                mGroupHolder.mActionTime.setText(groupNotiList.get(position).getDateTime());
                mGroupHolder.mGroupMembers.setText(groupNotiList.get(position).getGroupMembers());
                mGroupHolder.mGroupNoOfVehicles.setText(groupNotiList.get(position).getGroupVehicles());
               /* mGroupHolder.mGroupNoOfProducts.setText(groupNotiList.get(position));
                mGroupHolder.mGroupNoOfServices.setText(groupNotiList.get(position));*/

               /* Profile pic */
                if (groupNotiList.get(position).getSenderPicture() == null ||
                        groupNotiList.get(position).getSenderPicture().equals("") ||
                        groupNotiList.get(position).getSenderPicture().equals("null")) {
                    mGroupHolder.mUserPic.setBackgroundResource(R.mipmap.profile);
                } else {
                    /*Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + groupNotiList.get(position).getSenderPicture())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroupHolder.mUserPic);*/
                }

               /* Group pic */
                if (groupNotiList.get(position).getGroupImage() == null ||
                        groupNotiList.get(position).getGroupImage().equals("") ||
                        groupNotiList.get(position).getGroupImage().equals("null")) {
                    mGroupHolder.mGroupImage.setBackgroundResource(R.drawable.group);
                } else {
                    /*Glide.with(mActivity)
                            .load("http://autokatta.com/mobile/profile_profile_pics/" + groupNotiList.get(position).getGroupImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(mGroupHolder.mGroupImage);*/
                }

                /* Fav & Unfav Functionality */
                if (groupNotiList.get(position).getMyFavStatus().equalsIgnoreCase("yes")) {
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
                        String notiId = groupNotiList.get(mGroupHolder.getAdapterPosition()).getActionID();
                        mGroupHolder.mGroupFavourite.setVisibility(View.GONE);
                        mGroupHolder.mGroupUnFav.setVisibility(View.VISIBLE);
                        /*mApiCall.UnLike(mLoginContact, otherContact, "1", 0, "", "", "", "", "", "");
                        groupNotiList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("no");*/
                        Toast.makeText(mActivity, "unFavorite", Toast.LENGTH_SHORT).show();
                    }
                });

                mGroupHolder.mGroupUnFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Favorite web service
                        String notiId = groupNotiList.get(mGroupHolder.getAdapterPosition()).getActionID();
                        mGroupHolder.mGroupUnFav.setVisibility(View.GONE);
                        mGroupHolder.mGroupFavourite.setVisibility(View.VISIBLE);
                        /*mApiCall.addRemovefavouriteStatus(mLoginContact, notiId, "1", 0, "", "", "", "", "", "");
                        groupNotiList.get(mProfileHolder.getAdapterPosition()).setMyFavStatus("yes");*/
                        Toast.makeText(mActivity, "Favorite", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 10:
                UploadVehicleNotifications uploadVehicleNotifications = (UploadVehicleNotifications) holder;
                break;
        }

    }


}
