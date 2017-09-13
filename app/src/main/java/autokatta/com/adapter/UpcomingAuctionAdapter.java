package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
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
    private String mLoginContact = "";

    public UpcomingAuctionAdapter(Activity activity1, List<MyUpcomingAuctionResponse.Success.Auction> itemlist) {
        this.activity = activity1;
        this.mMainlist = itemlist;
        mLoginContact = activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "");
    }

    @Override
    public UpcomingAuctionAdapter.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_auction_adapter, parent, false);
        return new AuctionHolder(v);
    }

    @Override
    public void onBindViewHolder(final UpcomingAuctionAdapter.AuctionHolder holder, int position) {


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
                b.putInt("auction_id", mMainlist.get(holder.getAdapterPosition()).getAuctionId());
                b.putString("actiontitle", mMainlist.get(holder.getAdapterPosition()).getActionTitle());
                b.putString("auctionvehicle", mMainlist.get(holder.getAdapterPosition()).getNoOfVehicle());
                b.putString("auctionstartdate", mMainlist.get(holder.getAdapterPosition()).getStartDate());
                b.putString("auctionstarttime", mMainlist.get(holder.getAdapterPosition()).getStartTime());
                b.putString("auctionenddate", mMainlist.get(holder.getAdapterPosition()).getEndDate());
                b.putString("auctionendtime", mMainlist.get(holder.getAdapterPosition()).getEndTime());
                b.putString("participants", mMainlist.get(holder.getAdapterPosition()).getGoingcount());


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
            String imageFilePath = "", imagename;
            Intent intent = new Intent(Intent.ACTION_SEND);

            @Override
            public void onClick(View v) {
                PopupMenu mPopupMenu = new PopupMenu(activity, holder.relativeshare);
                mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.autokatta:
                                String allVehicleDetails = holder.action_title.getText().toString() + "=" +
                                        holder.auction_vehicle.getText().toString() + "=" +
                                        holder.auction_enddate.getText().toString() + "=" +
                                        holder.auction_endtime.getText().toString() + "=" +
                                        mMainlist.get(holder.getAdapterPosition()).getAuctionType() + "=" +
                                        mMainlist.get(holder.getAdapterPosition()).getGoingcount() + "=" +
                                        "0" + "=" +
                                        "myauction";
                                System.out.println("all auction detailssss======Auto " + allVehicleDetails);

                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allVehicleDetails).apply();
                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_auction_id", mMainlist.get(holder.getAdapterPosition()).getAuctionId()).apply();
                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", "auction").apply();


                                Intent i = new Intent(activity, ShareWithinAppActivity.class);
                                activity.startActivity(i);
                                break;

                            case R.id.other:
                                String allVehicleDetailss = "Auction Title : " + holder.action_title.getText().toString() + "\n" +
                                        "No.of Vehicles : " + holder.auction_vehicle.getText().toString() + "\n" +
                                        "Auction End Date : " + holder.auction_enddate.getText().toString() + "\n" +
                                        "Auction End Time : " + holder.auction_endtime.getText().toString() + "\n" +
                                        "Auction Type : " + mMainlist.get(holder.getAdapterPosition()).getAuctionType() + "\n" +
                                        "Auction Going Count : " + mMainlist.get(holder.getAdapterPosition()).getGoingcount() + "\n" +
                                        "Auction Ignore Count : " + "0";

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + mMainlist.get(holder.getAdapterPosition()).getAuctionId() + "/" + mLoginContact
                                        + "\n" + "\n" + allVehicleDetailss);
//                                intent.setType("image/jpeg");
//                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                activity.startActivity(Intent.createChooser(intent, "Autokatta"));
                                break;
                        }
                        return false;
                    }
                });
                mPopupMenu.show();
            }
        });


        String spcl = mMainlist.get(position).getSpecialClauses().replaceAll(",", "\n");
        Log.i("ooo", "" + spcl);
        holder.btnclause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    textView.setText(mMainlist.get(holder.getAdapterPosition()).getSpecialClauses().replaceAll(",", "\n"));
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


    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    static class AuctionHolder extends RecyclerView.ViewHolder {

        TextView action_title, auction_vehicle, auction_enddate, auction_endtime, auction_startdate,
                auction_starttime;
        TextView timer;
        Button preview, btnclause;
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
            relativeshare = (RelativeLayout) view.findViewById(R.id.relativeshare);
            btnclause = (Button) view.findViewById(R.id.clauses);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rel);
            relativeLayout.setVisibility(View.GONE);

        }
    }
}
