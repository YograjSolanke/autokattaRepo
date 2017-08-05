package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.MyUpcomingExchangeMelaResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-004 on 31/3/17.
 */


public class UpcomingExchangeAdapter extends RecyclerView.Adapter<UpcomingExchangeAdapter.ExchangeHolder> {
    List<MyUpcomingExchangeMelaResponse.Success> mMainlist = new ArrayList<>();
    Activity mActivity;
    private String eventType;


    public UpcomingExchangeAdapter(Activity activity, List<MyUpcomingExchangeMelaResponse.Success> itemlist,
                                   String eventType) {

        this.mActivity = activity;
        this.mMainlist = itemlist;
        this.eventType = eventType;

    }

    @Override
    public UpcomingExchangeAdapter.ExchangeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_loan_adapter, parent, false);

        return new ExchangeHolder(v);
    }

    @Override
    public void onBindViewHolder(UpcomingExchangeAdapter.ExchangeHolder holder, int position) {

        //holder.Type.setText(eventType);
        holder.title.setText(mMainlist.get(position).getName());
        holder.startdate.setText(mMainlist.get(position).getStartDate());
        holder.starttime.setText(mMainlist.get(position).getStartTime());
        holder.enddate.setText(mMainlist.get(position).getEndDate());
        holder.endtime.setText(mMainlist.get(position).getEndTime());
        holder.location.setText(mMainlist.get(position).getLocation());
        holder.address.setText(mMainlist.get(position).getAddress());

        if (mMainlist.get(position).getImage().equals("") || mMainlist.get(position).getImage().equals("null")) {
            holder.image.setImageResource(R.mipmap.exchange_event);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = mActivity.getString(R.string.base_image_url) + mMainlist.get(position).getImage().trim();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo)
                    .into(holder.image);

        }
    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    static class ExchangeHolder extends RecyclerView.ViewHolder {

        TextView title, enddate, endtime, startdate, starttime, location, address, details, Type;
        ImageView image;
        Button mPreview, mShare;
        RelativeLayout relativeLayout;

        ExchangeHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            location = (TextView) itemView.findViewById(R.id.setlocation);
            address = (TextView) itemView.findViewById(R.id.setaddress);
            startdate = (TextView) itemView.findViewById(R.id.datetime1);
            starttime = (TextView) itemView.findViewById(R.id.editTime);
            enddate = (TextView) itemView.findViewById(R.id.datetime2);
            endtime = (TextView) itemView.findViewById(R.id.editText);
            image = (ImageView) itemView.findViewById(R.id.loanmelaimg);
            details = (TextView) itemView.findViewById(R.id.typeofauction2);
            //Type = (TextView) itemView.findViewById(R.id.auctiontitle2);
            mPreview = (Button) itemView.findViewById(R.id.button);
            mShare = (Button) itemView.findViewById(R.id.share);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rel);
            relativeLayout.setVisibility(View.GONE);

        }
    }
}


