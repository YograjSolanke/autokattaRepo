package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import autokatta.com.R;
import autokatta.com.auction.PreviewGoingEvents;
import autokatta.com.auction.PreviewLiveEvents;
import autokatta.com.auction.PreviewUpcomingEvent;
import autokatta.com.response.GetLiveEventsResponse;

/**
 * Created by ak-001 on 4/4/17.
 */

public class AuctionNotificationAdapter extends RecyclerView.Adapter<AuctionNotificationAdapter.MyViewHolder> {

    Activity mActivity;
    private List<GetLiveEventsResponse.Success> mItemList = new ArrayList<>();
    private String auctionType, whoseAuction = "";
    ;
    private HashMap<TextView, CountDownTimer> counters;

    public AuctionNotificationAdapter(Activity mActivity, List<GetLiveEventsResponse.Success> mItemList, String auctionType) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.auctionType = auctionType;
        this.counters = new HashMap<TextView, CountDownTimer>();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mAuctionCardView;
        TextView mAuctioneer, mAuctioneerTitle, mAuctioneerNoOfVehicles, mAuctioneerType, mTimer, mStartDate, mStartTime,
                mEndDate, mEndTime, mAuction_category, mStockLocation;
        Button mSpecialClauses, mAuctionPreview, mShare;
        ImageView mStamp;

        public MyViewHolder(View itemView) {
            super(itemView);
            mAuctionCardView = (CardView) itemView.findViewById(R.id.auction_card_view);
            mAuctioneer = (TextView) itemView.findViewById(R.id.auctioneer);
            mAuctioneerTitle = (TextView) itemView.findViewById(R.id.auctioneer_title);
            mAuctioneerNoOfVehicles = (TextView) itemView.findViewById(R.id.auctioneer_no_of_vehicles);
            mAuctioneerType = (TextView) itemView.findViewById(R.id.auctioneer_type);
            mTimer = (TextView) itemView.findViewById(R.id.timer);
            mStartDate = (TextView) itemView.findViewById(R.id.start_date);
            mStartTime = (TextView) itemView.findViewById(R.id.start_time);
            mEndDate = (TextView) itemView.findViewById(R.id.end_date);
            mEndTime = (TextView) itemView.findViewById(R.id.end_time);
            mAuction_category = (TextView) itemView.findViewById(R.id.auction_category);
            mStockLocation = (TextView) itemView.findViewById(R.id.stockLocation);

            mSpecialClauses = (Button) itemView.findViewById(R.id.btnspecial_clauses);
            mAuctionPreview = (Button) itemView.findViewById(R.id.auction_preview);
            mShare = (Button) itemView.findViewById(R.id.auction_share);
            mStamp = (ImageView) itemView.findViewById(R.id.stamp);
        }
    }

    @Override
    public AuctionNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_auction, parent, false);
        MyViewHolder holder = new MyViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(AuctionNotificationAdapter.MyViewHolder holder, final int position) {
        holder.mAuctioneer.setText(mItemList.get(position).getAuctioneer());
        holder.mAuctioneerTitle.setText(mItemList.get(position).getActionTitle());
        holder.mAuctioneerNoOfVehicles.setText(mItemList.get(position).getNoOfVehicles());
        holder.mAuctioneerType.setText(mItemList.get(position).getAuctionType());
        holder.mStartDate.setText(mItemList.get(position).getStartDate());
        holder.mStartTime.setText(mItemList.get(position).getStartTime());
        holder.mEndDate.setText(mItemList.get(position).getEndDate());
        holder.mEndTime.setText(mItemList.get(position).getEndTime());
        holder.mAuction_category.setText(mItemList.get(position).getAuctioncategory());
        holder.mStockLocation.setText(mItemList.get(position).getStockLocation());

        final TextView tv = holder.mTimer;
        CountDownTimer cdt = counters.get(tv);
        if (cdt != null) {
            cdt.cancel();
        }

        /*
        Timer...
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date futureDate = dateFormat.parse(mItemList.get(position).getEndDateTime());
            Date currentDate = dateFormat.parse(mItemList.get(position).getEndDateTime());

            Date now = new Date();
            long difference = futureDate.getTime() - currentDate.getTime();
            long diff = futureDate.getTime() - now.getTime();
            long abc = difference - diff;

            cdt = new CountDownTimer(diff, 1000) {
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
                }
            };

            counters.put(tv, cdt);
            cdt.start();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (auctionType.equals("Upcoming")) {
            holder.mTimer.setVisibility(View.GONE);
            holder.mStamp.setVisibility(View.VISIBLE);
            holder.mStamp.setImageResource(R.mipmap.upcomingstamp);
        } else if (auctionType.equals("Going")) {
            holder.mTimer.setVisibility(View.VISIBLE);
        }

        if (mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("loginContact", "").equals(mItemList.get(position).getContact())) {
            whoseAuction = "myauction";
        } else {
            whoseAuction = "otherauction";
        }
        //buttons...
        holder.mSpecialClauses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clauses[] = new String[0];
                try {
                    clauses = mItemList.get(position).getSpecialClauses().split(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Special Clauses");
                builder.setCancelable(true);
                builder.setItems(clauses, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        //preview...
        holder.mAuctionPreview.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();
            @Override
            public void onClick(View v) {
                bundle.putString("auctioneer", mItemList.get(position).getAuctioneer());
                bundle.putString("auction_id", mItemList.get(position).getAuctionId());
                bundle.putString("action_title", mItemList.get(position).getActionTitle());
                bundle.putString("auction_startdate", mItemList.get(position).getStartDate());
                bundle.putString("auction_starttime", mItemList.get(position).getStartTime());
                bundle.putString("auction_enddate", mItemList.get(position).getEndDate());
                bundle.putString("auction_endtime", mItemList.get(position).getEndTime());
                bundle.putString("no_of_vehicles", mItemList.get(position).getNoOfVehicles());
                bundle.putString("auction_type", mItemList.get(position).getAuctionType());
                bundle.putString("auctioncontact", mItemList.get(position).getContact());
                bundle.putString("ignoreGoingStatus", mItemList.get(position).getIgnoreGoingStatus());
                bundle.putString("startDateTime", mItemList.get(position).getStartDateTime());
                bundle.putString("endDateTime", mItemList.get(position).getEndDateTime());
                bundle.putString("specialcluases", mItemList.get(position).getSpecialClauses());
                bundle.putString("blackListStatus", mItemList.get(position).getBlackListStatus());
                bundle.putString("openClose", mItemList.get(position).getOpenClose());
                bundle.putString("showPrice", mItemList.get(position).getShowPrice());
                bundle.putString("keyword", mItemList.get(position).getKeyWord());

                if (auctionType.equals("Live")) {
                    Intent intent = new Intent(mActivity, PreviewLiveEvents.class);
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                } else if (auctionType.equals("Going")) {
                    Intent intent = new Intent(mActivity, PreviewGoingEvents.class);
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                } else if (auctionType.equals("Upcoming")) {
                    Intent intent = new Intent(mActivity, PreviewUpcomingEvent.class);
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    public void cancelAllTimers() {
        Set<Map.Entry<TextView, CountDownTimer>> s = counters.entrySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                CountDownTimer cdt = (CountDownTimer) pairs.getValue();

                cdt.cancel();
                cdt = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        counters.clear();
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }
}
