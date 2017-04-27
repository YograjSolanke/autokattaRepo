package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.events.MyEndedLoanMelaPreviewActivity;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.MyActiveLoanMelaResponse;
import autokatta.com.response.MyActiveLoanMelaResponse.Success;
import autokatta.com.view.ShareWithinAppActivity;

/**
 * Created by ak-005 on 27/4/17.
 */

public class MyEndedSaleMelaAdapter extends RecyclerView.Adapter<MyEndedSaleMelaAdapter.LoanHolder>  {

    private Activity mActivity;
    private List<Success> mMainList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    private String myContact;
    String allDetails;
    public MyEndedSaleMelaAdapter(Activity activity, List<MyActiveLoanMelaResponse.Success> itemlist) {
        this.mActivity = activity;
        this.mMainList = itemlist;
        myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
        mConnectionDetector = new ConnectionDetector(mActivity);


    }


    @Override
    public MyEndedSaleMelaAdapter.LoanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ended_loan_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        LoanHolder vh = new LoanHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyEndedSaleMelaAdapter.LoanHolder holder, final int position) {

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

        holder.mPreview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putString("title",mMainList.get(position).getName());
                b.putString("startdate",mMainList.get(position).getStartDate());
                b.putString("starttime",mMainList.get(position).getStartTime());
                b.putString("enddate",mMainList.get(position).getEndDate());
                b.putString("endtime",mMainList.get(position).getEndTime());
                b.putString("location",mMainList.get(position).getLocation());
                b.putString("enddatetime",mMainList.get(position).getEndDateTime());
                mActivity.finish();
                Intent i=new Intent(mActivity, MyEndedLoanMelaPreviewActivity.class);

                i.putExtras(b);
                mActivity.startActivity(i);

            }
        });
//Share Within App
        holder.relativeshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View v) {

                allDetails = mMainList.get(position).getName() + "="
                        + mMainList.get(position).getStartDate() + "="
                        + mMainList.get(position).getEndDate() + "=" +
                        mMainList.get(position).getEndTime() + "=" +
                        mMainList.get(position).getLocation() + "=" +
                        "0" + "=" + "0" + "=" + "a";


                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                //   mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                //       putString("Share_auction_id", mMainlist.get(position).getAuctionId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "loanmela").apply();

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

                allDetails = mMainList.get(position).getName() + "="
                        + mMainList.get(position).getStartDate() + "="
                        + mMainList.get(position).getEndDate() + "=" +
                        mMainList.get(position).getEndTime() + "=" +
                        mMainList.get(position).getLocation() + "=" +
                        "0" + "=" + "0" + "=" + "a";


                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
//                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
//                        putString("Share_auction_id", auctionDetailsArrayList.get(position).getAuctionId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "endedexchangemela").apply();

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
        return mMainList.size();
    }

    static class LoanHolder extends RecyclerView.ViewHolder {


        TextView title, enddate, endtime, startdate, starttime, location, address, details;
        ImageView image;
        Button mPreview,mShare;
        RelativeLayout relativeshare;


        LoanHolder(View view) {
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
            mPreview= (Button) itemView.findViewById(R.id.button);
            mShare= (Button) itemView.findViewById(R.id.share);
            relativeshare = (RelativeLayout) itemView.findViewById(R.id.relativeshare);




        }
    }
}

