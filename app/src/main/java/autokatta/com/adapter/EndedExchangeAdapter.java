package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.MyActiveExchangeMelaResponse;

/**
 * Created by ak-004 on 29/3/17.
 */

public class EndedExchangeAdapter extends RecyclerView.Adapter<EndedExchangeAdapter.ExchangeHolder> {

    private Activity mActivity;
    private List<MyActiveExchangeMelaResponse.Success> mMainList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    private String myContact;

    public EndedExchangeAdapter(Activity activity, List<MyActiveExchangeMelaResponse.Success> itemlist) {
        this.mActivity = activity;
        this.mMainList = itemlist;
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "7841023392");
        mConnectionDetector = new ConnectionDetector(mActivity);


    }


    @Override
    public EndedExchangeAdapter.ExchangeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ended_loan_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ExchangeHolder vh = new ExchangeHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(EndedExchangeAdapter.ExchangeHolder holder, int position) {

        holder.title.setText(mMainList.get(position).getName());
        holder.startdate.setText(mMainList.get(position).getStartDate());
        holder.starttime.setText(mMainList.get(position).getStartTime());
        holder.enddate.setText(mMainList.get(position).getEndDate());
        holder.endtime.setText(mMainList.get(position).getEndTime());
        holder.location.setText(mMainList.get(position).getLocation());
        holder.address.setText(mMainList.get(position).getAddress());


        if (!mMainList.get(position).getImage().equals("null") || !mMainList.get(position).getImage().equals("") ||
                !mMainList.get(position).getImage().equals(null)) {
//            Picasso.with(activity)
//                    .load("http://autokatta.com/mobile/uploads/" + obj.vehicleSingleImage.replaceAll(" ","%20"))
//                    .resize(100,100)
//                    .into(holder.imageView);

//            Glide.with(mActivity)
//                    .load("http://autokatta.com/mobile/uploads/" + mMainList.get(position).getImage().replaceAll(" ","%20"))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .override(100,100)
//                    .into(holder.image);

        }
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    static class ExchangeHolder extends RecyclerView.ViewHolder {


        TextView title, enddate, endtime, startdate, starttime, location, address, details;
        ImageView image;

        ExchangeHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            location = (TextView) view.findViewById(R.id.setlocation);
            address = (TextView) view.findViewById(R.id.setaddress);
            startdate = (TextView) view.findViewById(R.id.datetime1);
            starttime = (TextView) view.findViewById(R.id.editTime);
            enddate = (TextView) view.findViewById(R.id.datetime2);
            endtime = (TextView) view.findViewById(R.id.editText);
            image = (ImageView) view.findViewById(R.id.loanmelaimg);
            details = (TextView) view.findViewById(R.id.typeofauction2);


        }
    }
}
