package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.WallResponse;

/**
 * Created by ak-004 on 7/7/17.
 */

public class StoreNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<WallResponse.Success.WallNotification> storeNotiList = new ArrayList<>();
    private String mLoginContact;

    /* constructor */
    public StoreNotificationAdapter(Activity activity, List<WallResponse.Success.WallNotification> storeNotiList1,
                                    String myContact) {
        mActivity = activity;
        mActivity = activity;
        storeNotiList = storeNotiList1;
        mLoginContact = myContact;
    }

    @Override
    public int getItemCount() {
        return storeNotiList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(storeNotiList.get(position).getLayout());
    }

    /* view class Group*/
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;

        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_wall_store_notifications, parent, false);
        return new StoreNotifications(mView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}

