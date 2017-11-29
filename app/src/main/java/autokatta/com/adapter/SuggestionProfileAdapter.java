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
import autokatta.com.response.SuggestionsResponse;
import autokatta.com.view.OtherProfile;
import autokatta.com.view.UserProfile;

/**
 * Created by ak-004 on 27/11/17.
 */

public class SuggestionProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<SuggestionsResponse.Success.Profile> mSuggestionList;
    private TextView mSuggestionAbout;
    private String mLoginContact;

    /*Constructor*/
    public SuggestionProfileAdapter(Activity mActivity1, List<SuggestionsResponse.Success.Profile> storeResponseArrayList1, TextView txtSuggestion, String mLoginContact) {
        mActivity = mActivity1;
        mSuggestionList = storeResponseArrayList1;
        mSuggestionAbout = txtSuggestion;
        this.mLoginContact = mLoginContact;
        setHasStableIds(true);
    }

    @Override
    public int getItemCount() {
        return mSuggestionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
        //return mSuggestionList.get(position).getLayoutId();
        //return Integer.parseInt("-2");
    }

    /*View Holders Class List*/


    /*Profile Suggestions Class...*/
    private static class ProfileSuggestions extends RecyclerView.ViewHolder {
        CardView mProfileCardView;
        ImageView mProfilePic;
        Button mView;
        TextView mUserName, mLocation;

        private ProfileSuggestions(View profileView) {
            super(profileView);
            mProfileCardView = (CardView) profileView.findViewById(R.id.profile_card_view);
            mProfilePic = (ImageView) profileView.findViewById(R.id.profilePic);
            mView = (Button) profileView.findViewById(R.id.btnView);
            mUserName = (TextView) profileView.findViewById(R.id.userName);
            mLocation = (TextView) profileView.findViewById(R.id.userLocation);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("viewType", "Suggestion->" + viewType);
        View mView;
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_profile_layout_adapter, parent, false);
        return new ProfileSuggestions(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ProfileSuggestions mProfileSuggestions = (ProfileSuggestions) holder;
        mProfileSuggestions.setIsRecyclable(false);

        mSuggestionAbout.setText("Suggestion Based On Profile");
        mProfileSuggestions.mUserName.setText(mSuggestionList.get(position).getUserName());
        mProfileSuggestions.mLocation.setText(mSuggestionList.get(position).getCity());
        if (mSuggestionList.get(position).getProfilePicture().equals("")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mSuggestionList.get(position).getProfilePicture())
                    // .bitmapTransform(new CropSquareTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mProfileSuggestions.mProfilePic);
        } else {
            mProfileSuggestions.mProfilePic.setBackgroundResource(R.drawable.profile);
        }

        mProfileSuggestions.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", mSuggestionList.get(mProfileSuggestions.getAdapterPosition()).getContactNo());
                if (mLoginContact.equalsIgnoreCase(mSuggestionList.get(mProfileSuggestions.getAdapterPosition()).getContactNo())) {
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
    }
}

