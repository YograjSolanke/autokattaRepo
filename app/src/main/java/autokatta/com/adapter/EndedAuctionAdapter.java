package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
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
    private HashMap<TextView, CountDownTimer> counters;


    public EndedAuctionAdapter(Activity mActivity, List<MyActiveAuctionResponse.Success.Auction> mItemList) {
        try {
            this.mActivity = mActivity;
            this.mMainList = mItemList;
            myContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                    getString("loginContact", "7841023392");
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
    public void onBindViewHolder(EndedAuctionAdapter.AuctionHolder holder, int position) {


        holder.action_title.setText(mMainList.get(position).getActionTitle());
        holder.auction_vehicle.setText(mMainList.get(position).getNoOfVehicle());
        holder.auction_enddate.setText(mMainList.get(position).getEndDate());
        holder.auction_endtime.setText(mMainList.get(position).getEndTime());
        holder.auction_startdate.setText(mMainList.get(position).getStartDate());
        holder.auction_starttime.setText(mMainList.get(position).getStartTime());

        spcl = mMainList.get(position).getSpecialClauses().replaceAll(",", "\n");
        holder.btnCluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mActivity);
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
                input.setLayoutParams(lp);
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

        holder.btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    static class AuctionHolder extends RecyclerView.ViewHolder {


        TextView action_title, auction_vehicle, auction_enddate, auction_endtime, auction_startdate, auction_starttime;
        TextView timer;
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

        }

    }


}


