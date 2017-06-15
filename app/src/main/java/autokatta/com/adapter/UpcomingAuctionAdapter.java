package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.MyUpcomingAuctionResponse;
import autokatta.com.view.ShareWithinAppActivity;

/**
 * Created by ak-004 on 31/3/17.
 */

public class UpcomingAuctionAdapter extends RecyclerView.Adapter<UpcomingAuctionAdapter.AuctionHolder> {
    Activity activity;
    List<MyUpcomingAuctionResponse.Success.Auction> mMainlist = new ArrayList<>();
    private String allDetails, spcl;

    public UpcomingAuctionAdapter(Activity activity, List<MyUpcomingAuctionResponse.Success.Auction> itemlist) {
        this.activity = activity;
        this.mMainlist = itemlist;

    }

    @Override
    public UpcomingAuctionAdapter.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_auction_adapter, parent, false);
        return new AuctionHolder(v);
    }

    @Override
    public void onBindViewHolder(UpcomingAuctionAdapter.AuctionHolder holder, final int position) {


        holder.action_title.setText(mMainlist.get(position).getActionTitle());
        holder.auction_vehicle.setText(mMainlist.get(position).getNoOfVehicle());
        holder.auction_enddate.setText(mMainlist.get(position).getEndDate());
        holder.auction_endtime.setText(mMainlist.get(position).getEndTime());
        holder.auction_startdate.setText(mMainlist.get(position).getStartDate());
        holder.auction_starttime.setText(mMainlist.get(position).getStartTime());


        holder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("auction_id", mMainlist.get(position).getAuctionId());
                b.putString("actiontitle", mMainlist.get(position).getActionTitle());
                b.putString("auctionvehicle", mMainlist.get(position).getNoOfVehicle());
                b.putString("auctionstartdate", mMainlist.get(position).getStartDate());
                b.putString("auctionstarttime", mMainlist.get(position).getStartTime());
                b.putString("auctionenddate", mMainlist.get(position).getEndDate());
                b.putString("auctionendtime", mMainlist.get(position).getEndTime());
                b.putString("participants", mMainlist.get(position).getGoingcount());


//                ActiveAuctionPreview fr = new ActiveAuctionPreview();
//                fr.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fr);
//                fragmentTransaction.addToBackStack("activeauctionpreview");
//                fragmentTransaction.commit();
            }
        });


        holder.relativeshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View view) {

                allDetails = mMainlist.get(position).getActionTitle() + "="
                        + mMainlist.get(position).getNoOfVehicle() + "="
                        + mMainlist.get(position).getEndDate() + "=" +
                        mMainlist.get(position).getEndTime() + "=" +
                        mMainlist.get(position).getAuctionType() + "=" +
                        "0" + "=" + "0" + "=" + "a";
                String mAuction = "auction";


                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_auction_id", mMainlist.get(position).getAuctionId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", mAuction).apply();

                activity.startActivity(new Intent(activity, ShareWithinAppActivity.class));
                activity.finish();

            }
        });

        holder.btnshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View v) {


                allDetails = mMainlist.get(position).getActionTitle() + "="
                        + mMainlist.get(position).getNoOfVehicle() + "="
                        + mMainlist.get(position).getEndDate() + "=" +
                        mMainlist.get(position).getEndTime() + "=" +
                        mMainlist.get(position).getAuctionType() + "=" +
                        "0" + "=" + "0" + "=" + "a";
                String mAuction = "auction";


                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_auction_id", mMainlist.get(position).getAuctionId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", mAuction).apply();

                System.out.println("Share Image \n");

                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                intent.putExtra(Intent.EXTRA_TEXT, allDetails);

                activity.startActivity(intent);


            }
        });


        spcl = mMainlist.get(position).getSpecialClauses().replaceAll(",", "\n");
        holder.btnclause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                alertDialog.setTitle("Special Clauses");

                final TextView input = new TextView(activity);
                input.setText(spcl);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMarginStart(30);
                input.setLayoutParams(lp);
                input.setPadding(40, 40, 40, 40);
                input.setGravity(Gravity.CENTER_VERTICAL);
                input.setTextColor(Color.parseColor("#C39BD3"));
                input.setTextSize(20);
                alertDialog.setView(input);

                // alertDialog.setIcon(R.drawable.key);

                alertDialog.setNeutralButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    static class AuctionHolder extends RecyclerView.ViewHolder {

        TextView action_title, auction_vehicle, auction_enddate, auction_endtime, auction_startdate,
                auction_starttime;
        TextView timer;
        Button preview, btnshare, btnshare1, btnclause;
        RelativeLayout relativeshare, relativeLayout;

        AuctionHolder(View view) {
            super(view);
            action_title = (TextView) view.findViewById(R.id.typeofauction2);
            auction_vehicle = (TextView) view.findViewById(R.id.editvehicle);
            auction_enddate = (TextView) view.findViewById(R.id.datetime2);
            auction_endtime = (TextView) view.findViewById(R.id.editText);
            auction_startdate = (TextView) view.findViewById(R.id.datetime1);
            auction_starttime = (TextView) view.findViewById(R.id.editTime);
            preview = (Button) view.findViewById(R.id.button);
            //holder.btngoing=(Button)convertView.findViewById(R.id.going);
            btnshare = (Button) view.findViewById(R.id.share);
            //holder.btnshare1=(Button)convertView.findViewById(R.id.share1);
            relativeshare = (RelativeLayout) view.findViewById(R.id.relativeshare);
            btnclause = (Button) view.findViewById(R.id.clauses);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rel);
            relativeLayout.setVisibility(View.GONE);

        }
    }
}
