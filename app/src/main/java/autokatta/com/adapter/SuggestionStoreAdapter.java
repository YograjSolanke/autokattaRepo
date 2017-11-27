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
import autokatta.com.view.StoreViewActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by ak-003 on 3/11/17.
 */

public class SuggestionStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<ModelSuggestionsResponse> mSuggestionList;
    private TextView mSuggestionAbout;
    private String mLoginContact;

    /*Constructor*/
    public SuggestionStoreAdapter(Activity mActivity1, List<ModelSuggestionsResponse> storeResponseArrayList1, TextView txtSuggestion, String mLoginContact) {
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
        return super.getItemViewType(position);
        //return mSuggestionList.get(position).getLayoutId();
        //return Integer.parseInt("-2");
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

        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_store_layout_adapter, parent, false);
        return new StoreSuggestions(mView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final StoreSuggestions mStoreSuggestions = (StoreSuggestions) holder;
        mStoreSuggestions.mStoreName.setText(mSuggestionList.get(position).getName());
        mStoreSuggestions.mStoreLocation.setText(mSuggestionList.get(position).getLocation());
        mSuggestionAbout.setText("Suggestion Based On Store");

        if (mSuggestionList.get(position).getImage().equals("")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mSuggestionList.get(position).getImage())
                    .bitmapTransform(new CropSquareTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mStoreSuggestions.mStorePic);
        } else {
            mStoreSuggestions.mStorePic.setBackgroundResource(R.drawable.profile);
        }

        mStoreSuggestions.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();

                b.putInt("store_id", mSuggestionList.get(mStoreSuggestions.getAdapterPosition()).getStoreId());
                b.putString("StoreContact", mSuggestionList.get(mStoreSuggestions.getAdapterPosition()).getUserContact());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, StoreViewActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, options.toBundle());
            }
        });

    }
}
