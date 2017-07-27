package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.events.MyEndedAuctionPreviewActivity;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.MyActiveAuctionResponse;

/**
 * Created by ak-004 on 29/3/17.
 */

public class EndedAuctionAdapter extends RecyclerView.Adapter<EndedAuctionAdapter.AuctionHolder> {

    private Activity mActivity;
    private List<MyActiveAuctionResponse.Success.Auction> mMainList = new ArrayList<>();
    private ConnectionDetector mConnectionDetector;
    private String myContact, spcl;


    public EndedAuctionAdapter(Activity mActivity, List<MyActiveAuctionResponse.Success.Auction> mItemList) {
        try {
            this.mActivity = mActivity;
            this.mMainList = mItemList;
            myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                    getString("loginContact", "");
            mConnectionDetector = new ConnectionDetector(mActivity);

        } catch (ClassCastException c) {
            c.printStackTrace();
        }
    }


    @Override
    public EndedAuctionAdapter.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ended_auction_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        AuctionHolder vh = new AuctionHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final EndedAuctionAdapter.AuctionHolder holder, final int position) {
        holder.action_title.setText(mMainList.get(position).getActionTitle());
        holder.auction_vehicle.setText(mMainList.get(position).getNoOfVehicle());
        holder.auction_enddate.setText(mMainList.get(position).getEndDate());
        holder.auction_endtime.setText(mMainList.get(position).getEndTime());
        holder.auction_startdate.setText(mMainList.get(position).getStartDate());
        holder.auction_starttime.setText(mMainList.get(position).getStartTime());
        holder.mAuction_category.setText(mMainList.get(position).getAuctioncategory());
        holder.mStockLocation.setText(mMainList.get(position).getStockLocation());

        spcl = mMainList.get(position).getSpecialClauses().replaceAll(",", "\n");
        holder.btnCluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mActivity);
                alertDialog.setTitle("Special Clauses");
                //alertDialog.setMessage("Enter Clause");
                //String names[] ={,};
                final TextView input = new TextView(mActivity);
                input.setText(spcl);
                //input.setText(st);

                //String[] parts = input.getText().toString().split(",");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMarginStart(30);
                input.setBackgroundColor(Color.LTGRAY);
                input.setLayoutParams(lp);
                input.setPadding(40, 40, 40, 40);
                input.setGravity(Gravity.CENTER_VERTICAL);
                input.setTextColor(Color.parseColor("#110359"));
                input.setTextSize(20);
                alertDialog.setView(input);


                // alertDialog.setIcon(R.drawable.key);

                alertDialog.setNeutralButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();*/

                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(mActivity)
                        .setTitle("Special Clauses")
                        .setMessage("YOUR_MSG")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                //textView.setMaxLines(5);
                if (textView != null) {
                    textView.setScroller(new Scroller(mActivity));
                    textView.setVerticalScrollBarEnabled(true);
                    textView.setText(mMainList.get(holder.getAdapterPosition()).getSpecialClauses().replaceAll(",", "\n"));
                    textView.setMovementMethod(new ScrollingMovementMethod());

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMarginStart(30);
                    textView.setBackgroundColor(Color.LTGRAY);
                    textView.setLayoutParams(lp);
                    textView.setPadding(40, 40, 40, 40);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setTextColor(Color.parseColor("#110359"));
                    textView.setTextSize(20);

                    dialog.setView(textView);
                }

            }
        });

        holder.btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("auctionid", mMainList.get(position).getAuctionId());
                b.putString("auctiontitle", mMainList.get(position).getActionTitle());
                b.putString("vehicle_count", mMainList.get(position).getNoOfVehicle());
                b.putString("auctionstartdate", mMainList.get(position).getStartDate());
                b.putString("auctionstarttime", mMainList.get(position).getStartTime());
                b.putString("auctionenddate", mMainList.get(position).getEndDate());
                b.putString("auctionendtime", mMainList.get(position).getEndTime());
                b.putString("specialclauses", mMainList.get(position).getSpecialClauses());
                b.putString("enddatetime", mMainList.get(position).getEndDateTime());
                b.putString("startdatetime", mMainList.get(position).getStartDateTime());
                b.putInt("participant_count", mMainList.get(position).getGoingcount());
                b.putString("category", mMainList.get(position).getAuctioncategory());
                b.putString("location", mMainList.get(position).getStockLocation());

                // mActivity.finish();
                Intent intent = new Intent(mActivity, MyEndedAuctionPreviewActivity.class);
                intent.putExtras(b);
                mActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    static class AuctionHolder extends RecyclerView.ViewHolder {
        TextView action_title, auction_vehicle, auction_enddate, auction_endtime, auction_startdate, auction_starttime;
        TextView timer, mAuction_category, mStockLocation;
        FloatingActionButton btnPreview, btnCluse;

        AuctionHolder(View itemview) {
            super(itemview);
            action_title = (TextView) itemview.findViewById(R.id.typeofauction2);
            auction_vehicle = (TextView) itemview.findViewById(R.id.editvehicle);
            auction_enddate = (TextView) itemview.findViewById(R.id.datetime2);
            auction_endtime = (TextView) itemview.findViewById(R.id.editText);
            auction_startdate = (TextView) itemview.findViewById(R.id.datetime1);
            auction_starttime = (TextView) itemview.findViewById(R.id.editTime);
            btnCluse = (FloatingActionButton) itemview.findViewById(R.id.btncluse);
            btnPreview = (FloatingActionButton) itemview.findViewById(R.id.gotopreview);
            timer = (TextView) itemview.findViewById(R.id.timer);
            mAuction_category = (TextView) itemview.findViewById(R.id.auction_category);
            mStockLocation = (TextView) itemview.findViewById(R.id.stockLocation);
        }
    }
}


