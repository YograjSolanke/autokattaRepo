package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.MyStoreResponse;

/**
 * Created by ak-003 on 3/11/17.
 */

public class CustomSuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<MyStoreResponse.Success> storeResponseArrayList;

    /*Constructor*/
    CustomSuggestionAdapter(Activity mActivity1, List<MyStoreResponse.Success> storeResponseArrayList1) {
        mActivity = mActivity1;
        storeResponseArrayList = storeResponseArrayList1;
    }

    @Override
    public int getItemCount() {
        return storeResponseArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        //return Integer.parseInt(notificationList.get(position).getLayout());
        return Integer.parseInt("-2");
    }

    /*View Holders Class List*/

    /*Profile Suggestions Class...*/
    private static class ProfileSuggestions extends RecyclerView.ViewHolder {
        CardView mProfileCardView;
        ImageView mProfilePic;
        Button mView;
        TextView mUserName;

        private ProfileSuggestions(View profileView) {
            super(profileView);
            mProfileCardView = (CardView) profileView.findViewById(R.id.profile_card_view);
            mProfilePic = (ImageView) profileView.findViewById(R.id.profilePic);
            mView = (Button) profileView.findViewById(R.id.btnView);
            mUserName = (TextView) profileView.findViewById(R.id.userName);
        }
    }

    /*Store Suggestions Class...*/
    private static class StoreSuggestions extends RecyclerView.ViewHolder {
        CardView mStoreCardView;
        ImageView mStorePic;
        Button mView;
        TextView mStoreName, mStoreLocation;

        private StoreSuggestions(View profileView) {
            super(profileView);
            mStoreCardView = (CardView) profileView.findViewById(R.id.store_card_view);
            mStorePic = (ImageView) profileView.findViewById(R.id.storePic);
            mView = (Button) profileView.findViewById(R.id.btnView);
            mStoreName = (TextView) profileView.findViewById(R.id.storeName);
            mStoreLocation = (TextView) profileView.findViewById(R.id.storeAddress);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("viewType", "Suggestion->" + viewType);
        View mView;
        switch (viewType) {
            case -1:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_profile_layout_adapter, parent, false);
                return new ProfileSuggestions(mView);
            case -2:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_store_layout_adapter, parent, false);
                return new StoreSuggestions(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case -1:
                break;
            case -2:
                final StoreSuggestions mStoreSuggestions = (StoreSuggestions) holder;
                mStoreSuggestions.mStoreName.setText(storeResponseArrayList.get(position).getName());
                mStoreSuggestions.mStoreLocation.setText(storeResponseArrayList.get(position).getLocation());
                break;
        }

    }


}
