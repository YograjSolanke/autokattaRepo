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
import autokatta.com.view.ProductViewActivity;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by ak-004 on 27/11/17.
 */

public class SuggestionProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    private List<ModelSuggestionsResponse> mSuggestionList;
    private TextView mSuggestionAbout;
    private String mLoginContact;

    /*Constructor*/
    public SuggestionProductAdapter(Activity mActivity1, List<ModelSuggestionsResponse> storeResponseArrayList1, TextView txtSuggestion, String mLoginContact) {
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

    /*Product Suggestions Class...*/
    private static class ProductSuggestions extends RecyclerView.ViewHolder {
        CardView mProductCardView;
        ImageView mProductPic;
        Button mView;
        TextView mProductname, mProductType;

        private ProductSuggestions(View profileView) {
            super(profileView);
            mProductCardView = (CardView) profileView.findViewById(R.id.product_card_view);
            mProductPic = (ImageView) profileView.findViewById(R.id.productPic);
            mView = (Button) profileView.findViewById(R.id.btnView);
            mProductname = (TextView) profileView.findViewById(R.id.productName);
            mProductType = (TextView) profileView.findViewById(R.id.productType);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("viewType", "Suggestion->" + viewType);
        View mView;
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_product_layout_adapter, parent, false);
        return new ProductSuggestions(mView);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ProductSuggestions mProductVSuggestions = (ProductSuggestions) holder;
        mProductVSuggestions.mProductname.setText(mSuggestionList.get(position).getName());
        //mStoreSuggestions.mStoreLocation.setText(mSuggestionList.get(position).getLocation());
        mSuggestionAbout.setText("Suggestion Based On Store");

        if (mSuggestionList.get(position).getImage().equals("")) {

            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.base_image_url) + mSuggestionList.get(position).getImage())
                    .bitmapTransform(new CropSquareTransformation(mActivity))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mProductVSuggestions.mProductPic);
        } else {
            mProductVSuggestions.mProductPic.setBackgroundResource(R.drawable.vehiimg);
        }

        mProductVSuggestions.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                int proId = mSuggestionList.get(mProductVSuggestions.getAdapterPosition()).getProductId();
                Intent intent = new Intent(mActivity, ProductViewActivity.class);
                intent.putExtra("product_id", proId);
                mActivity.startActivity(intent, options.toBundle());
            }
        });


    }


}

