package autokatta.com.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import autokatta.com.events.ActiveServiceMelaPreviewActivity;
import autokatta.com.response.MyActiveServiceMelaResponse;
import autokatta.com.view.ShareWithinAppActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ak-005 on 27/4/17.
 */

public class ActiveServiceMelaAdapter extends RecyclerView.Adapter<ActiveServiceMelaAdapter.LoanHolder>  {

    List<MyActiveServiceMelaResponse.Success> mMainlist = new ArrayList<>();
    Activity mActivity;
    FragmentActivity ctx;
    String allDetails;


    public ActiveServiceMelaAdapter(Activity activity, List<MyActiveServiceMelaResponse.Success> itemlist) {

        this.mActivity = activity;
        this.mMainlist = itemlist;

    }

    @Override
    public ActiveServiceMelaAdapter.LoanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_loan_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        LoanHolder vh = new LoanHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ActiveServiceMelaAdapter.LoanHolder holder, final int position) {

        holder.mtitle.setText("Service Title:");
        holder.title.setText(mMainlist.get(position).getName());
        holder.startdate.setText(mMainlist.get(position).getStartDate());
        holder.starttime.setText(mMainlist.get(position).getStartTime());
        holder.enddate.setText(mMainlist.get(position).getEndDate());
        holder.endtime.setText(mMainlist.get(position).getEndTime());
        holder.location.setText(mMainlist.get(position).getLocation());
        holder.address.setText(mMainlist.get(position).getAddress());


        if (mMainlist.get(position).getImage().equals("") || mMainlist.get(position).getImage().equals("null")) {
            holder.image.setImageResource(R.mipmap.service);
        } else {
            //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
            String dppath = "http://autokatta.com/mobile/loan_exchange_events_pics/" + mMainlist.get(position).getImage().trim();
            Glide.with(mActivity)
                    .load(dppath)
                    .bitmapTransform(new CropCircleTransformation(mActivity)) //To display image in Circular form.
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .placeholder(R.drawable.logo)
                    .into(holder.image);

        }

        holder.mPreview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putString("title",mMainlist.get(position).getName());
                b.putString("startdate",mMainlist.get(position).getStartDate());
                b.putString("starttime",mMainlist.get(position).getStartTime());
                b.putString("enddate",mMainlist.get(position).getEndDate());
                b.putString("endtime",mMainlist.get(position).getEndTime());
                b.putString("location",mMainlist.get(position).getLocation());
                b.putString("enddatetime",mMainlist.get(position).getEndDateTime().replace("T"," "));
                b.putInt("serviceid",mMainlist.get(position).getId());
                ActivityOptions options = ActivityOptions.makeCustomAnimation(mActivity, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i=new Intent(mActivity, ActiveServiceMelaPreviewActivity.class);
                i.putExtras(b);
                mActivity.startActivityForResult(i, 1, options.toBundle());


            }
        });

        //Share Within App
        holder.relativeshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View v) {

                allDetails = mMainlist.get(position).getName() + "="
                        + mMainlist.get(position).getStartDate() + "="
                        + mMainlist.get(position).getEndDate() + "=" +
                        mMainlist.get(position).getEndTime() + "=" +
                        mMainlist.get(position).getLocation() + "=" +
                        "0" + "=" + "0" + "=" + "a";


                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                   mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                       putInt("Share_service_id", mMainlist.get(position).getId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "servicemela").apply();

                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                mActivity.startActivity(i);
                mActivity.finish();
            }
        });

        //Share With Other
        holder.mShare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View v) {

                allDetails = mMainlist.get(position).getName() + "="
                        + mMainlist.get(position).getStartDate() + "="
                        + mMainlist.get(position).getEndDate() + "=" +
                        mMainlist.get(position).getEndTime() + "=" +
                        mMainlist.get(position).getLocation() + "=" +
                        "0" + "=" + "0" + "=" + "a";


                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
//                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
//                        putString("Share_auction_id", auctionDetailsArrayList.get(position).getAuctionId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "loanmela").apply();

                System.out.println("Share Image \n");

                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                intent.putExtra(Intent.EXTRA_TEXT, allDetails);
                mActivity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    static class LoanHolder extends RecyclerView.ViewHolder {

        TextView title, enddate, endtime, startdate, starttime, location, address, details,mtitle;
        ImageView image;
        Button mPreview,mShare;
        RelativeLayout relativeshare;
        public LoanHolder(View itemView) {
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
            mPreview= (Button) itemView.findViewById(R.id.button);
            mShare= (Button) itemView.findViewById(R.id.share);
            mtitle= (TextView) itemView.findViewById(R.id.title2);
            relativeshare = (RelativeLayout) itemView.findViewById(R.id.relativeshare);

        }
    }
}
