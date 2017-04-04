package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import autokatta.com.response.GetLiveEventsResponse;

/**
 * Created by ak-001 on 4/4/17.
 */

public class AuctionNotificationAdapter extends RecyclerView.Adapter<AuctionNotificationAdapter.MyViewHolder> {

    Activity mActivity;
    private List<GetLiveEventsResponse.Success> mItemList = new ArrayList<>();
    private String auctionType;
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
                mEndDate, mEndTime;
        Button mSpecialClauses, mAuctionPreview, mShare;

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

            mSpecialClauses = (Button) itemView.findViewById(R.id.btnspecial_clauses);
            mAuctionPreview = (Button) itemView.findViewById(R.id.auction_preview);
            mShare = (Button) itemView.findViewById(R.id.auction_share);
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
