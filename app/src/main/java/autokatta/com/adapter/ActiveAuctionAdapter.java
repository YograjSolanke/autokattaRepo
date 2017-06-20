package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.events.PreviewMyActiveAuctionActivity;
import autokatta.com.response.MyActiveAuctionResponse;
import autokatta.com.view.ShareWithinAppActivity;

/**
 * Created by ak-004 on 30/3/17.
 */

public class ActiveAuctionAdapter extends RecyclerView.Adapter<ActiveAuctionAdapter.AuctionHolder> {

    private HashMap<TextView, CountDownTimer> counters;
    Activity activity;
    private Handler handler;
    Runnable runnable;
    String[] st;
    private String special_clause, spcl, allDetails;
    private static LayoutInflater inflater = null;
    private List<MyActiveAuctionResponse.Success.Auction> auctionDetailsArrayList;

    public ActiveAuctionAdapter(Activity activity, List<MyActiveAuctionResponse.Success.Auction> itemist) {
        this.activity = activity;
        this.auctionDetailsArrayList = itemist;
        this.counters = new HashMap<TextView, CountDownTimer>();
    }

    @Override
    public ActiveAuctionAdapter.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_auction_adapter, parent, false);
        AuctionHolder vh = new AuctionHolder(v);
        handler = new Handler();
        return vh;
    }

    @Override
    public void onBindViewHolder(final ActiveAuctionAdapter.AuctionHolder holder, final int position) {

        holder.action_title.setText(auctionDetailsArrayList.get(position).getActionTitle());
        holder.auction_vehicle.setText(auctionDetailsArrayList.get(position).getNoOfVehicle());
        holder.auction_enddate.setText(auctionDetailsArrayList.get(position).getEndDate());
        holder.auction_endtime.setText(auctionDetailsArrayList.get(position).getEndTime());
        holder.auction_startdate.setText(auctionDetailsArrayList.get(position).getStartDate());
        holder.auction_starttime.setText(auctionDetailsArrayList.get(position).getStartTime());
        holder.mAuction_category.setText(auctionDetailsArrayList.get(position).getAuctioncategory());
        holder.mStockLocation.setText(auctionDetailsArrayList.get(position).getStockLocation());

        final TextView tv = holder.timer;
        CountDownTimer cdt = counters.get(holder.timer);
        if (cdt != null) {
            cdt.cancel();
            cdt = null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date futureDate = dateFormat.parse(auctionDetailsArrayList.get(position).getEndDateTime());
            Date currentDate = dateFormat.parse(auctionDetailsArrayList.get(position).getStartDateTime());
            Date now = new Date();
            long difference = futureDate.getTime() - now.getTime();
            cdt = new CountDownTimer(difference, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int days = 0;
                    int hours = 0;
                    int minutes = 0;
                    int seconds = 0;
                    String sDate = "";

                    if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                        days = (int) (millisUntilFinished / DateUtils.DAY_IN_MILLIS);
                        sDate += days + "d";
                    }

                    millisUntilFinished -= (days * DateUtils.DAY_IN_MILLIS);

                    if (millisUntilFinished > DateUtils.HOUR_IN_MILLIS) {
                        hours = (int) (millisUntilFinished / DateUtils.HOUR_IN_MILLIS);
                    }

                    millisUntilFinished -= (hours * DateUtils.HOUR_IN_MILLIS);

                    if (millisUntilFinished > DateUtils.MINUTE_IN_MILLIS) {
                        minutes = (int) (millisUntilFinished / DateUtils.MINUTE_IN_MILLIS);
                    }

                    millisUntilFinished -= (minutes * DateUtils.MINUTE_IN_MILLIS);

                    if (millisUntilFinished > DateUtils.SECOND_IN_MILLIS) {
                        seconds = (int) (millisUntilFinished / DateUtils.SECOND_IN_MILLIS);
                    }

                    sDate += " " + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
                    tv.setText(sDate.trim());
                }

                @Override
                public void onFinish() {
                    tv.setText("Finished");
                    // mailAuctionData();
                }
            };

            counters.put(tv, cdt);
            cdt.start();


        } catch (ParseException e) {
            e.printStackTrace();
        }

        special_clause = auctionDetailsArrayList.get(position).getSpecialClauses();
        spcl = special_clause.replaceAll(",", "\n");

        holder.btnclause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                alertDialog.setTitle("Special Clauses");
                final TextView input = new TextView(activity);
                input.setText(spcl);
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

                alertDialog.setNeutralButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();*/
                AlertDialog dialog = new AlertDialog.Builder(activity)
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
                    textView.setScroller(new Scroller(activity));
                    textView.setVerticalScrollBarEnabled(true);
                    textView.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getSpecialClauses().replaceAll(",", "\n"));
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


        holder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("auctionid", auctionDetailsArrayList.get(position).getAuctionId());
                b.putString("auctiontitle", auctionDetailsArrayList.get(position).getActionTitle());
                b.putString("vehicle_count", auctionDetailsArrayList.get(position).getNoOfVehicle());
                b.putString("auctionstartdate", auctionDetailsArrayList.get(position).getStartDate());
                b.putString("auctionstarttime", auctionDetailsArrayList.get(position).getStartTime());
                b.putString("auctionenddate", auctionDetailsArrayList.get(position).getEndDate());
                b.putString("auctionendtime", auctionDetailsArrayList.get(position).getEndTime());
                b.putString("specialclauses", auctionDetailsArrayList.get(position).getSpecialClauses());
                b.putString("enddatetime", auctionDetailsArrayList.get(position).getEndDateTime());
                b.putString("startdatetime", auctionDetailsArrayList.get(position).getStartDateTime());
                b.putString("participant_count", auctionDetailsArrayList.get(position).getGoingcount());
                b.putString("category", auctionDetailsArrayList.get(position).getAuctioncategory());
                b.putString("location", auctionDetailsArrayList.get(position).getStockLocation());

                //activity.finish();
                Intent intent = new Intent(activity, PreviewMyActiveAuctionActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);

            }
        });


        holder.relativeshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View v) {

                allDetails = auctionDetailsArrayList.get(position).getActionTitle() + "="
                        + auctionDetailsArrayList.get(position).getNoOfVehicle() + "="
                        + auctionDetailsArrayList.get(position).getEndDate() + "=" +
                        auctionDetailsArrayList.get(position).getEndTime() + "=" +
                        auctionDetailsArrayList.get(position).getAuctionType() + "=" +
                        "0" + "=" + "0" + "=" + "a";
                String mAuction = "auction";


                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_auction_id", auctionDetailsArrayList.get(position).getAuctionId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", mAuction).apply();

                Intent i = new Intent(activity, ShareWithinAppActivity.class);
                activity.startActivity(i);
                //activity.finish();
            }
        });

        holder.btnshare.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String imageFilePath;

            @Override
            public void onClick(View v) {

                allDetails = auctionDetailsArrayList.get(position).getActionTitle() + "="
                        + auctionDetailsArrayList.get(position).getNoOfVehicle() + "="
                        + auctionDetailsArrayList.get(position).getEndDate() + "=" +
                        auctionDetailsArrayList.get(position).getEndTime() + "=" +
                        auctionDetailsArrayList.get(position).getAuctionType() + "=" +
                        "0" + "=" + "0" + "=" + "a";

                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_auction_id", auctionDetailsArrayList.get(position).getAuctionId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "auction").apply();

                System.out.println("Share Image \n");
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                intent.putExtra(Intent.EXTRA_TEXT, allDetails);
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return auctionDetailsArrayList.size();
    }

    static class AuctionHolder extends RecyclerView.ViewHolder {

        TextView action_title, auction_vehicle, auction_enddate, auction_endtime, auction_startdate,
                auction_starttime, mAuction_category, mStockLocation;
        TextView timer;
        Button preview, btnshare, btnclause;
        RelativeLayout relativeshare;

        AuctionHolder(View view) {
            super(view);

            action_title = (TextView) view.findViewById(R.id.typeofauction2);
            auction_vehicle = (TextView) view.findViewById(R.id.editvehicle);
            auction_enddate = (TextView) view.findViewById(R.id.datetime2);
            auction_endtime = (TextView) view.findViewById(R.id.editText);
            auction_startdate = (TextView) view.findViewById(R.id.datetime1);
            auction_starttime = (TextView) view.findViewById(R.id.editTime);
            preview = (Button) view.findViewById(R.id.button);

            btnshare = (Button) view.findViewById(R.id.share);
            relativeshare = (RelativeLayout) view.findViewById(R.id.relativeshare);
            timer = (TextView) view.findViewById(R.id.timer);
            btnclause = (Button) view.findViewById(R.id.btnclauses);
            mAuction_category = (TextView) view.findViewById(R.id.auction_category);
            mStockLocation = (TextView) view.findViewById(R.id.stockLocation);
        }

    }

}
