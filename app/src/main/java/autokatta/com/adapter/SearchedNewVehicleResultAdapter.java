package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.FullImageActivity;
import autokatta.com.response.NewVehicleSearchResponse;
import autokatta.com.view.StoreViewActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-003 on 16/10/17.
 */

public class SearchedNewVehicleResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private String myContact;
    private List<NewVehicleSearchResponse.Success> mSearchNewVehicleList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;

    public SearchedNewVehicleResultAdapter(Activity activity, List<NewVehicleSearchResponse.Success>
            mSearchNewVehicleList, String myContact) {
        this.mActivity = activity;
        this.mSearchNewVehicleList = mSearchNewVehicleList;
        this.myContact = myContact;
        mConnectionDetector = new ConnectionDetector(activity);
    }

    public static class YoHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView stname, stlocation, stwebsite, storetiming, stlike, stshare, stfollow, stworkdays, serviceOffered;
        ImageView img, storedelete, storeShare;
        RatingBar storerating;
        LinearLayout linearlike, linearunlike, linearshare, linearshare1, linearfollow, linearunfollow;


        YoHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.adapter_mystoreListCard_view);
            stname = (TextView) itemView.findViewById(R.id.editstname);
            stlocation = (TextView) itemView.findViewById(R.id.autolocation);
            stwebsite = (TextView) itemView.findViewById(R.id.editwebsite);
            storetiming = (TextView) itemView.findViewById(R.id.edittiming);
            serviceOffered = (TextView) itemView.findViewById(R.id.servicesOffered);
            stworkdays = (TextView) itemView.findViewById(R.id.editworkingdays);
            img = (ImageView) itemView.findViewById(R.id.profile);
            storeShare = (ImageView) itemView.findViewById(R.id.storeShare);
            storerating = (RatingBar) itemView.findViewById(R.id.storerating);
            storedelete = (ImageView) itemView.findViewById(R.id.storedelete);
            stlike = (TextView) itemView.findViewById(R.id.textlike);
            stshare = (TextView) itemView.findViewById(R.id.textshare);
            stfollow = (TextView) itemView.findViewById(R.id.textfollow);
            linearlike = (LinearLayout) itemView.findViewById(R.id.linearlike);
            linearunlike = (LinearLayout) itemView.findViewById(R.id.linearunlike);
            linearshare = (LinearLayout) itemView.findViewById(R.id.linearshare);
            linearshare1 = (LinearLayout) itemView.findViewById(R.id.linearshare1);
            linearfollow = (LinearLayout) itemView.findViewById(R.id.linearfollow);
            linearunfollow = (LinearLayout) itemView.findViewById(R.id.linearunfollow);

            storedelete.setVisibility(View.GONE);
            storeShare.setVisibility(View.GONE);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_search_new_vehicle_result, parent, false);
        return new YoHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final YoHolder mStoreHolder = (YoHolder) holder;

        mStoreHolder.stname.setText(mSearchNewVehicleList.get(position).getName());
        mStoreHolder.stlocation.setText(mSearchNewVehicleList.get(position).getLocation());
        mStoreHolder.stwebsite.setText(mSearchNewVehicleList.get(position).getWebSite());
        mStoreHolder.serviceOffered.setText(mSearchNewVehicleList.get(position).getCategory());
        //mStoreHolder.storetiming.setText(mSearchNewVehicleList.get(position).getOpenTime()+" TO "+(mSearchNewVehicleList.get(position).getc()));
        //mStoreHolder.stlike.setText("Like(" + mSearchNewVehicleList.get(position).getLikecount() + ")");
        //mStoreHolder.stfollow.setText("Follow(" + mSearchNewVehicleList.get(position).getFollowcount() + ")");
        mStoreHolder.stworkdays.setText(mSearchNewVehicleList.get(position).getWorkingDays());
        mStoreHolder.storerating.setEnabled(false);
        if (mSearchNewVehicleList.get(position).getRating() != 0)
            mStoreHolder.storerating.setRating(mSearchNewVehicleList.get(position).getRating());


        if (mSearchNewVehicleList.get(position).getWebSite() == null || mSearchNewVehicleList.get(position).getWebSite().isEmpty() ||
                mSearchNewVehicleList.get(position).getWebSite().equals("null")) {
            mStoreHolder.stwebsite.setText("No website found");
        } else {
            mStoreHolder.stwebsite.setText(mSearchNewVehicleList.get(position).getWebSite());
        }

        if (mSearchNewVehicleList.get(position).getStoreImage().equals("") || mSearchNewVehicleList.get(position).getStoreImage().equals(null) ||
                mSearchNewVehicleList.get(position).getStoreImage().equals("null")) {
            mStoreHolder.img.setBackgroundResource(R.drawable.logo);
        } else {
            String dppath = mActivity.getString(R.string.base_image_url) + mSearchNewVehicleList.get(position).getStoreImage().trim();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo)
                    .into(mStoreHolder.img);
        }

        mStoreHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image;
                if (mSearchNewVehicleList.get(mStoreHolder.getAdapterPosition()).getStoreImage().equals(""))
                    image = mActivity.getString(R.string.base_image_url) + "logo48x48.png";
                else
                    image = mActivity.getString(R.string.base_image_url) + mSearchNewVehicleList.get(mStoreHolder.getAdapterPosition()).getStoreImage();
                Intent intent = new Intent(mActivity, FullImageActivity.class);
                Bundle b = new Bundle();
                b.putString("image", image);
                intent.putExtras(b);
                mActivity.startActivity(intent);
            }
        });


        /***Card Click Listener***/
        mStoreHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();

                b.putInt("store_id", mSearchNewVehicleList.get(mStoreHolder.getAdapterPosition()).getStoreID());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(mActivity, StoreViewActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent, options.toBundle());
                //mActivity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSearchNewVehicleList.size();
    }


}
