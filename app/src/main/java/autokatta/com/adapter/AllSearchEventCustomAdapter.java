package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.auction.PreviewLiveEvents;
import autokatta.com.events.PreviewMyActiveAuctionActivity;
import autokatta.com.response.ModelSearchAuction;

/**
 * Created by ak-004 on 31/12/16.
 */

public class AllSearchEventCustomAdapter extends BaseAdapter {

    Activity activity;
    FragmentActivity ctx;
    private List<ModelSearchAuction> allSearchDataArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private Handler handler;
    private String startdatetime, enddatetime, special_clause, spcl;
    private HashMap<TextView, CountDownTimer> counters;

    public AllSearchEventCustomAdapter(Activity activity1, List<ModelSearchAuction> allSearchDataArrayList) {
        this.activity = activity1;
        this.allSearchDataArrayList = allSearchDataArrayList;
        this.counters = new HashMap<>();
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getItemViewType(int position) {
        Log.i("Key", "->>>>0" + allSearchDataArrayList.get(position).getKey());
        return allSearchDataArrayList.get(position).getKey();
    }

    @Override
    public int getViewTypeCount() {
        Log.i("Count", "->>>>0" + allSearchDataArrayList.size());
        return allSearchDataArrayList.size();
    }

    @Override
    public int getCount() {
        return allSearchDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class YoHolder {
        TextView action_title, auction_vehicle, auction_enddate, auction_endtime, auction_startdate,
                auction_starttime;
        TextView timer;
        Button preview, btnshare, btnclause;
        RelativeLayout relativeshare, relativepreview;
        TextView title, enddate, endtime, startdate, starttime, location, address, details, mainTitle;
        ImageView image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final YoHolder holder;
        int type = getItemViewType(position);
        System.out.println("Type case:" + type);
        if (convertView == null) {
            holder = new YoHolder();
            switch (type) {
                case 0:
                    convertView = inflater.inflate(R.layout.my_live_event_adapter, null);
                    handler = new Handler();
                    holder.action_title = (TextView) convertView.findViewById(R.id.typeofauction2);
                    holder.auction_vehicle = (TextView) convertView.findViewById(R.id.editvehicle);
                    holder.auction_enddate = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.auction_endtime = (TextView) convertView.findViewById(R.id.editText);
                    holder.auction_startdate = (TextView) convertView.findViewById(R.id.datetime1);
                    holder.auction_starttime = (TextView) convertView.findViewById(R.id.editTime);
                    holder.preview = (Button) convertView.findViewById(R.id.button);
                    holder.btnshare = (Button) convertView.findViewById(R.id.share);
                    holder.relativeshare = (RelativeLayout) convertView.findViewById(R.id.relativeshare);
                    holder.timer = (TextView) convertView.findViewById(R.id.timer);
                    holder.btnclause = (Button) convertView.findViewById(R.id.clauses);
                    holder.relativepreview = (RelativeLayout) convertView.findViewById(R.id.bottom);
                    holder.btnshare.setVisibility(View.GONE);
                    holder.relativeshare.setVisibility(View.GONE);
                    holder.relativepreview.setVisibility(View.GONE);
                    break;

                case 1:
                    convertView = inflater.inflate(R.layout.activated_list_with_hori, null);
                    handler = new Handler();
                    holder.action_title = (TextView) convertView.findViewById(R.id.typeofauction2);
                    holder.auction_vehicle = (TextView) convertView.findViewById(R.id.editvehicle);
                    holder.auction_enddate = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.auction_endtime = (TextView) convertView.findViewById(R.id.editText);
                    holder.auction_startdate = (TextView) convertView.findViewById(R.id.datetime1);
                    holder.auction_starttime = (TextView) convertView.findViewById(R.id.editTime);
                    holder.preview = (Button) convertView.findViewById(R.id.button);
                    holder.btnshare = (Button) convertView.findViewById(R.id.share);
                    holder.relativeshare = (RelativeLayout) convertView.findViewById(R.id.relativeshare);
                    holder.btnclause = (Button) convertView.findViewById(R.id.clauses);
                    holder.relativepreview = (RelativeLayout) convertView.findViewById(R.id.bottom);
                    holder.btnshare.setVisibility(View.GONE);
                    holder.relativeshare.setVisibility(View.GONE);
                    holder.relativepreview.setVisibility(View.GONE);
                    break;

                case 2:

                    convertView = inflater.inflate(R.layout.child_loan_mela_event, null);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.location = (TextView) convertView.findViewById(R.id.setlocation);
                    holder.address = (TextView) convertView.findViewById(R.id.setaddress);
                    holder.startdate = (TextView) convertView.findViewById(R.id.datetime1);
                    holder.starttime = (TextView) convertView.findViewById(R.id.editTime);
                    holder.enddate = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.endtime = (TextView) convertView.findViewById(R.id.editText);
                    holder.image = (ImageView) convertView.findViewById(R.id.loanmelaimg);
                    holder.details = (TextView) convertView.findViewById(R.id.typeofauction2);
                    holder.mainTitle = (TextView) convertView.findViewById(R.id.auctiontitle2);
                    holder.mainTitle.setText("Live Exchange Title :");
                    break;

                case 3:
                    convertView = inflater.inflate(R.layout.child_loan_mela_event, null);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.location = (TextView) convertView.findViewById(R.id.setlocation);
                    holder.address = (TextView) convertView.findViewById(R.id.setaddress);
                    holder.startdate = (TextView) convertView.findViewById(R.id.datetime1);
                    holder.starttime = (TextView) convertView.findViewById(R.id.editTime);
                    holder.enddate = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.endtime = (TextView) convertView.findViewById(R.id.editText);
                    holder.image = (ImageView) convertView.findViewById(R.id.loanmelaimg);
                    holder.details = (TextView) convertView.findViewById(R.id.typeofauction2);
                    holder.mainTitle = (TextView) convertView.findViewById(R.id.auctiontitle2);
                    holder.mainTitle.setText("Upcoming Exchange Title :");
                    break;

                case 4:

                    convertView = inflater.inflate(R.layout.child_loan_mela_event, null);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.location = (TextView) convertView.findViewById(R.id.setlocation);
                    holder.address = (TextView) convertView.findViewById(R.id.setaddress);
                    holder.startdate = (TextView) convertView.findViewById(R.id.datetime1);
                    holder.starttime = (TextView) convertView.findViewById(R.id.editTime);
                    holder.enddate = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.endtime = (TextView) convertView.findViewById(R.id.editText);
                    holder.image = (ImageView) convertView.findViewById(R.id.loanmelaimg);
                    holder.details = (TextView) convertView.findViewById(R.id.typeofauction2);
                    holder.mainTitle = (TextView) convertView.findViewById(R.id.auctiontitle2);
                    holder.mainTitle.setText("Live Loan Title :");
                    break;

                case 5:
                    convertView = inflater.inflate(R.layout.child_loan_mela_event, null);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.location = (TextView) convertView.findViewById(R.id.setlocation);
                    holder.address = (TextView) convertView.findViewById(R.id.setaddress);
                    holder.startdate = (TextView) convertView.findViewById(R.id.datetime1);
                    holder.starttime = (TextView) convertView.findViewById(R.id.editTime);
                    holder.enddate = (TextView) convertView.findViewById(R.id.datetime2);
                    holder.endtime = (TextView) convertView.findViewById(R.id.editText);
                    holder.image = (ImageView) convertView.findViewById(R.id.loanmelaimg);
                    holder.details = (TextView) convertView.findViewById(R.id.typeofauction2);
                    holder.mainTitle = (TextView) convertView.findViewById(R.id.auctiontitle2);
                    holder.mainTitle.setText("Upcoming Loan Title :");
                    break;

                default:
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (YoHolder) convertView.getTag();
        }

        final ModelSearchAuction object = allSearchDataArrayList.get(position);

        switch (type) {
            case 0:
                holder.action_title.setText(object.getActionTitle());
                holder.auction_vehicle.setText(object.getNoOfVehicle());
                holder.auction_enddate.setText(object.getEndDate());
                holder.auction_endtime.setText(object.getEndTime());
                holder.auction_startdate.setText(object.getStartDate());
                holder.auction_starttime.setText(object.getStartTime());
                startdatetime = object.getStartDateTime();
                enddatetime = object.getEndDateTime();

                final TextView tv = holder.timer;
                CountDownTimer cdt = counters.get(holder.timer);
                if (cdt != null) {
                    cdt.cancel();
                    cdt = null;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date futureDate = dateFormat.parse(enddatetime);
                    System.out.println("date============================================" + enddatetime);
                    Date currentDate = dateFormat.parse(startdatetime);
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
                        }
                    };
                    counters.put(tv, cdt);
                    cdt.start();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                special_clause = object.getSpecialClauses();
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
                        input.setLayoutParams(lp);
                        alertDialog.setView(input);
                        // alertDialog.setIcon(R.drawable.key);

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
                            textView.setText(spcl);
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
                        b.putString("auction_id", object.getAuctionId());
                        b.putString("action_title", object.getActionTitle());
                        b.putString("no_of_vehicles", object.getNoOfVehicle());
                        b.putString("auction_startdate", object.getStartDate());
                        b.putString("auction_starttime", object.getStartTime());
                        b.putString("auction_enddate", object.getEndDate());
                        b.putString("auction_endtime", object.getEndTime());
                        b.putString("specialcluases", object.getSpecialClauses());
                        b.putString("endDateTime", object.getEndDateTime());
                        b.putString("startDateTime", object.getStartDateTime());

                        /*b.putString("auctioneer", object.get(position).getUsername());
                        b.putString("auction_type", object.get(position).getAuctionType());
                        b.putString("auctioncontact", object.get(position).getContact());
                        b.putString("ignoreGoingStatus", object.get(position).getIgnoreGoingStatus());
                        b.putString("startDateTime", object.get(position).getStartDateTime());
                        b.putString("endDateTime", object.get(position).getEndDateTime());
                        b.putString("specialcluases", object.get(position).getSpecialClauses());
                        b.putString("blackListStatus", object.get(position).getBlackListStatus());
                        b.putString("openClose", object.get(position).getOpenClose());
                        b.putString("showPrice", object.get(position).getShowPrice());
                        b.putString("keyword", object.get(position).getKeyWord());
                        b.putString("category", object.get(position).getAuctionCategory());
                        b.putString("location", object.get(position).getLocation());*/

                        Intent intent = new Intent(activity, PreviewLiveEvents.class);
                        intent.putExtras(b);
                        activity.startActivity(intent);
                    }
                });
                break;

            case 1:
                holder.action_title.setText(object.getActionTitle());
                holder.auction_vehicle.setText(object.getNoOfVehicle());
                holder.auction_enddate.setText(object.getEndDate());
                holder.auction_endtime.setText(object.getEndTime());
                holder.auction_startdate.setText(object.getStartDate());
                holder.auction_starttime.setText(object.getStartTime());

                holder.preview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle b = new Bundle();
                        b.putString("auction_id", object.getAuctionId());
                        b.putString("actiontitle", object.getActionTitle());
                        b.putString("auctionvehicle", object.getNoOfVehicle());
                        b.putString("auctionstartdate", object.getStartDate());
                        b.putString("auctionstarttime", object.getStartTime());
                        b.putString("auctionenddate", object.getEndDate());
                        b.putString("auctionendtime", object.getEndTime());
                        b.putString("participants", object.getGoingcount());

                        Intent intent = new Intent(activity, PreviewMyActiveAuctionActivity.class);
                        intent.putExtras(b);
                        activity.startActivity(intent);
                       /* ActiveAuctionPreview fr = new ActiveAuctionPreview();
                        fr.setArguments(b);

                        FragmentManager fragmentManager = ctx.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, fr);
                        fragmentTransaction.addToBackStack("activeauctionpreview");
                        fragmentTransaction.commit();*/
                    }
                });

                special_clause = object.getSpecialClauses();
                spcl = special_clause.replaceAll(",", "\n");
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

                break;

            case 2:

                holder.title.setText(object.getName());
                holder.startdate.setText(object.getStartDate());
                holder.starttime.setText(object.getStartTime());
                holder.enddate.setText(object.getEndDate());
                holder.endtime.setText(object.getEndTime());
                holder.location.setText(object.getLocation());
                holder.address.setText(object.getAddress());

                if (object.getImage().equals("") || object.getImage().equals("null")) {
                    holder.image.setImageResource(R.mipmap.exchange_event);
                } else {
                    //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
                    String dppath = "http://autokatta.com/mobile/loan_exchange_events_pics/" + object.getImage().trim();
                    Glide.with(activity)
                            .load(dppath)
                            //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .placeholder(R.drawable.logo)
                            .into(holder.image);

                }

                break;

            case 3:

                holder.title.setText(object.getName());
                holder.startdate.setText(object.getStartDate());
                holder.starttime.setText(object.getStartTime());
                holder.enddate.setText(object.getEndDate());
                holder.endtime.setText(object.getEndTime());
                holder.location.setText(object.getLocation());
                holder.address.setText(object.getAddress());

                if (object.getImage().equals("") || object.getImage().equals("null")) {
                    holder.image.setImageResource(R.mipmap.exchange_event);
                } else {
                    //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
                    String dppath = "http://autokatta.com/mobile/loan_exchange_events_pics/" + object.getImage().trim();
                    Glide.with(activity)
                            .load(dppath)
                            //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .placeholder(R.drawable.logo)
                            .into(holder.image);

                }

                break;

            case 4:

                holder.title.setText(object.getName());
                holder.startdate.setText(object.getStartDate());
                holder.starttime.setText(object.getStartTime());
                holder.enddate.setText(object.getEndDate());
                holder.endtime.setText(object.getEndTime());
                holder.location.setText(object.getLocation());
                holder.address.setText(object.getAddress());

                if (object.getImage().equals("") || object.getImage().equals("null")) {
                    holder.image.setImageResource(R.mipmap.loan_mela);
                } else {
                    //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
                    String dppath = "http://autokatta.com/mobile/loan_exchange_events_pics/" + object.getImage().trim();
                    Glide.with(activity)
                            .load(dppath)
                            //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .placeholder(R.drawable.logo)
                            .into(holder.image);

                }

                break;

            case 5:

                holder.title.setText(object.getName());
                holder.startdate.setText(object.getStartDate());
                holder.starttime.setText(object.getStartTime());
                holder.enddate.setText(object.getEndDate());
                holder.endtime.setText(object.getEndTime());
                holder.location.setText(object.getLocation());
                holder.address.setText(object.getAddress());

                if (object.getImage().equals("") || object.getImage().equals("null")) {
                    holder.image.setImageResource(R.mipmap.loan_mela);
                } else {
                    //mItemList.get(position).getImage() = mItemList.get(position).getImage().replaceAll(" ", "%20");
                    String dppath = "http://autokatta.com/mobile/loan_exchange_events_pics/" + object.getImage().trim();
                    Glide.with(activity)
                            .load(dppath)
                            //.bitmapTransform(new CropCircleTransformation(activity)) //To display image in Circular form.
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .placeholder(R.drawable.logo)
                            .into(holder.image);

                }
                break;

            default:
                break;

        }

        return convertView;
    }
}

