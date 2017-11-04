package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.ModelSuggestionsResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by ak-003 on 3/11/17.
 */

public class CustomSuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<ModelSuggestionsResponse> mSuggestionList;
    private TextView mSuggestionAbout;
    private String mLoginContact;

    /*Constructor*/
    CustomSuggestionAdapter(Activity mActivity1, List<ModelSuggestionsResponse> storeResponseArrayList1, TextView txtSuggestion, String mLoginContact) {
        mActivity = mActivity1;
        mSuggestionList = storeResponseArrayList1;
        mSuggestionAbout = txtSuggestion;
        this.mLoginContact = mLoginContact;
    }

    @Override
    public int getItemCount() {
        return mSuggestionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return mSuggestionList.get(position).getLayoutId();
        //return Integer.parseInt("-2");
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
        CardView mProfileCardView;
        ImageView mProfilePic;
        Button mView;
        TextView mUserName;

        private StoreSuggestions(View profileView) {
            super(profileView);
            mProfileCardView = (CardView) profileView.findViewById(R.id.profile_card_view);
            mProfilePic = (ImageView) profileView.findViewById(R.id.profilePic);
            mView = (Button) profileView.findViewById(R.id.btnView);
            mUserName = (TextView) profileView.findViewById(R.id.userName);
        }
    }

    /*Vehicle Suggestions Class...*/
    private static class VehicleSuggestions extends RecyclerView.ViewHolder {
        CardView mStoreCardView;
        ImageView mStorePic;
        Button mView;
        TextView mStoreName, mStoreLocation;

        private VehicleSuggestions(View vehicleView) {
            super(vehicleView);
            mStoreCardView = (CardView) vehicleView.findViewById(R.id.store_card_view);
            mStorePic = (ImageView) vehicleView.findViewById(R.id.storePic);
            mView = (Button) vehicleView.findViewById(R.id.btnView);
            mStoreName = (TextView) vehicleView.findViewById(R.id.storeName);
            mStoreLocation = (TextView) vehicleView.findViewById(R.id.storeAddress);
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
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_profile_layout_adapter, parent, false);
                return new StoreSuggestions(mView);
            case -4:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_store_layout_adapter, parent, false);
                return new VehicleSuggestions(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case -1:
                final ProfileSuggestions mProfileSuggestions = (ProfileSuggestions) holder;

                mSuggestionAbout.setText("Suggestion Based On Skills");

                mProfileSuggestions.mUserName.setText(mSuggestionList.get(position).getName());
                if (mSuggestionList.get(position).getImage().equals("")) {

                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + mSuggestionList.get(position).getImage())
                            .bitmapTransform(new CropSquareTransformation(mActivity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mProfileSuggestions.mProfilePic);
                } else {
                    mProfileSuggestions.mProfilePic.setBackgroundResource(R.drawable.profile);
                }

                mProfileSuggestions.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", mSuggestionList.get(mProfileSuggestions.getAdapterPosition()).getUserContact());
                        if (mLoginContact.equalsIgnoreCase(mSuggestionList.get(mProfileSuggestions.getAdapterPosition()).getUserContact())) {
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            Intent i = new Intent(mActivity, UserProfile.class);
                            i.putExtras(bundle);
                            mActivity.startActivity(i, options.toBundle());

                        } else {
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            Intent i = new Intent(mActivity, OtherProfile.class);
                            i.putExtras(bundle);
                            mActivity.startActivity(i, options.toBundle());

                        }
                    }
                });
                break;

            case -2:
                final StoreSuggestions mStoreSuggestions = (StoreSuggestions) holder;
                mStoreSuggestions.mUserName.setText(mSuggestionList.get(position).getName());
                //mStoreSuggestions.mStoreLocation.setText(mSuggestionList.get(position).getLocation());
                mSuggestionAbout.setText("Suggestion Based On Company");

                if (mSuggestionList.get(position).getImage().equals("")) {

                    Glide.with(mActivity)
                            .load(mActivity.getString(R.string.base_image_url) + mSuggestionList.get(position).getImage())
                            .bitmapTransform(new CropSquareTransformation(mActivity))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mStoreSuggestions.mProfilePic);
                } else {
                    mStoreSuggestions.mProfilePic.setBackgroundResource(R.drawable.profile);
                }

                mStoreSuggestions.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", mSuggestionList.get(mStoreSuggestions.getAdapterPosition()).getUserContact());
                        if (mLoginContact.equalsIgnoreCase(mSuggestionList.get(mStoreSuggestions.getAdapterPosition()).getUserContact())) {
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            Intent i = new Intent(mActivity, UserProfile.class);
                            i.putExtras(bundle);
                            mActivity.startActivity(i, options.toBundle());

                        } else {
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            Intent i = new Intent(mActivity, OtherProfile.class);
                            i.putExtras(bundle);
                            mActivity.startActivity(i, options.toBundle());

                        }
                    }
                });
                break;

            case -4:

        }

    }


}
