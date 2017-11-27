package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
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
import autokatta.com.view.ServiceViewActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by ak-004 on 27/11/17.
 */

public class SuggestionServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<ModelSuggestionsResponse> mSuggestionList;
    private TextView mSuggestionAbout;
    private String mLoginContact;

    /*Constructor*/
    public SuggestionServiceAdapter(Activity mActivity1, List<ModelSuggestionsResponse> storeResponseArrayList1, TextView txtSuggestion, String mLoginContact) {
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

    /*Serviece Suggestions Class...*/
    private static class ServiceSuggestions extends RecyclerView.ViewHolder {
        CardView mServiceCardView;
        ImageView mServicePic;
        Button mView;
        TextView mServiceName, mServiceType;

        private ServiceSuggestions(View serviceView) {
            super(serviceView);
            mServiceCardView = (CardView) serviceView.findViewById(R.id.profile_card_view);
            mServicePic = (ImageView) serviceView.findViewById(R.id.profilePic);
            mView = (Button) serviceView.findViewById(R.id.btnView);
            mServiceName = (TextView) serviceView.findViewById(R.id.serviceName);
            mServiceType = (TextView) serviceView.findViewById(R.id.serviceType);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("viewType", "Suggestion->" + viewType);
        View mView;
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_service_layout_adapter, parent, false);
        return new ServiceSuggestions(mView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ServiceSuggestions mServiceVSuggestions = (ServiceSuggestions) holder;
        mServiceVSuggestions.mServiceName.setText(mSuggestionList.get(position).getName());
        //mStoreSuggestions.mStoreLocation.setText(mSuggestionList.get(position).getLocation());
        mSuggestionAbout.setText("Suggestion Based On Store");

        if (mSuggestionList.get(position).getImage().equals("")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mSuggestionList.get(position).getImage())
                    .bitmapTransform(new CropSquareTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mServiceVSuggestions.mServicePic);
        } else {
            mServiceVSuggestions.mServicePic.setBackgroundResource(R.drawable.vehiimg);
        }

        mServiceVSuggestions.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                int servId = mSuggestionList.get(mServiceVSuggestions.getAdapterPosition()).getServiceId();
                Intent intent = new Intent(mActivity, ServiceViewActivity.class);
                intent.putExtra("service_id", servId);
                mActivity.startActivity(intent, options.toBundle());
            }
        });


    }


}

