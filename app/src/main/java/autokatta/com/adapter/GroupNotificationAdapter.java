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

import autokatta.com.R;

/**
 * Created by ak-003 on 7/7/17.
 */

public class GroupNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;

    /* constructor */
    public GroupNotificationAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /* view class Group*/
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

    /* view class Upload vehicle*/
    private static class UploadVehicleNotifications extends RecyclerView.ViewHolder {
        CardView mGroupCardView;
        ImageView mGroupPic, mGroupImage;
        ImageButton mGroupFavourite;
        TextView mGroupActionName, mGroupActionTime, mGroupName, mGroupMembers, mGroupNoOfVehicles;

        private UploadVehicleNotifications(View groupView) {
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        Log.i("GroupNoti_Layout", "BindHolder" + viewType);
        switch (viewType) {
            case 3:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_group_notifications, parent, false);
                return new GroupNotifications(mView);

            case 10:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_group_notifications, parent, false);
                return new UploadVehicleNotifications(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


}
