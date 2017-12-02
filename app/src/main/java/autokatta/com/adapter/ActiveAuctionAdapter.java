package autokatta.com.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import autokatta.com.R;
import autokatta.com.events.PreviewMyActiveAuctionActivity;
import autokatta.com.response.MyActiveAuctionResponse;
import autokatta.com.view.ShareWithinAppActivity;

/**
 * Created by ak-004 on 30/3/17.
 */

public class ActiveAuctionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HashMap<TextView, CountDownTimer> counters;
    Activity activity;
    private Handler handler;
    Runnable runnable;
    String[] st;
    private String spcl;
    private String allDetails;
    private List<MyActiveAuctionResponse.Success.Auction> auctionDetailsArrayList;

    public ActiveAuctionAdapter(Activity activity, List<MyActiveAuctionResponse.Success.Auction> itemist) {
        this.activity = activity;
        this.auctionDetailsArrayList = itemist;
        this.counters = new HashMap<TextView, CountDownTimer>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_auction_adapter, parent, false);
        AuctionHolder vh = new AuctionHolder(v);
        handler = new Handler();
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {

        final AuctionHolder holder = (AuctionHolder) holder1;
        holder.action_title.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getActionTitle());
        holder.auction_vehicle.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getNoOfVehicle());
        holder.auction_enddate.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndDate());
        holder.auction_endtime.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndTime());
        holder.auction_startdate.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getStartDate());
        holder.auction_starttime.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getStartTime());
        holder.mAuction_category.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getAuctioncategory());
        holder.mStockLocation.setText(auctionDetailsArrayList.get(holder.getAdapterPosition()).getStockLocation());

        final TextView tv = holder.timer;
        CountDownTimer cdt = counters.get(holder.timer);
        if (cdt != null) {
            cdt.cancel();
            cdt = null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date futureDate = dateFormat.parse(auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndDateTime().replace("T", " "));
            Date currentDate = dateFormat.parse(auctionDetailsArrayList.get(holder.getAdapterPosition()).getStartDateTime().replace("T", " "));
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

                    sDate += " " + String.format(Locale.getDefault(), "%02d", hours) + ":" +
                            String.format(Locale.getDefault(), "%02d", minutes) + ":" +
                            String.format(Locale.getDefault(), "%02d", seconds);

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

        String special_clause = auctionDetailsArrayList.get(holder.getAdapterPosition()).getSpecialClauses();
        spcl = special_clause.replaceAll(",", "\n");

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
                b.putInt("auctionid", auctionDetailsArrayList.get(holder.getAdapterPosition()).getAuctionId());
                b.putString("auctiontitle", auctionDetailsArrayList.get(holder.getAdapterPosition()).getActionTitle());
                b.putString("vehicle_count", auctionDetailsArrayList.get(holder.getAdapterPosition()).getNoOfVehicle());
                b.putString("auctionstartdate", auctionDetailsArrayList.get(holder.getAdapterPosition()).getStartDate());
                b.putString("auctionstarttime", auctionDetailsArrayList.get(holder.getAdapterPosition()).getStartTime());
                b.putString("auctionenddate", auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndDate());
                b.putString("auctionendtime", auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndTime());
                b.putString("specialclauses", auctionDetailsArrayList.get(holder.getAdapterPosition()).getSpecialClauses());
                b.putString("enddatetime", auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndDateTime().replace("T", " "));
                b.putString("startdatetime", auctionDetailsArrayList.get(holder.getAdapterPosition()).getStartDateTime().replace("T", " "));
                b.putInt("participant_count", auctionDetailsArrayList.get(holder.getAdapterPosition()).getGoingcount());
                b.putString("category", auctionDetailsArrayList.get(holder.getAdapterPosition()).getAuctioncategory());
                b.putString("location", auctionDetailsArrayList.get(holder.getAdapterPosition()).getStockLocation());

                //activity.finish();
                Intent intent = new Intent(activity, PreviewMyActiveAuctionActivity.class);
                intent.putExtras(b);
                activity.startActivity(intent);

            }
        });


        holder.btnshare.setOnClickListener(new View.OnClickListener() {
            String imageFilePath = "", imagename;
            Intent intent = new Intent(Intent.ACTION_SEND);

            @Override
            public void onClick(View v) {
                //shareProfileData();
                PopupMenu mPopupMenu = new PopupMenu(activity, holder.btnshare);
                mPopupMenu.getMenuInflater().inflate(R.menu.more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.autokatta:
                                allDetails = auctionDetailsArrayList.get(holder.getAdapterPosition()).getActionTitle() + "="
                                        + auctionDetailsArrayList.get(holder.getAdapterPosition()).getNoOfVehicle() + "="
                                        + auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndDate() + "=" +
                                        auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndTime() + "=" +
                                        auctionDetailsArrayList.get(holder.getAdapterPosition()).getAuctionType() + "=" +
                                        "0" + "=" + "0" + "=" + "a";
                                String mAuction = "auction";


                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_sharedata", allDetails).apply();
                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putInt("Share_auction_id", auctionDetailsArrayList.get(holder.getAdapterPosition()).getAuctionId()).apply();
                                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                                        putString("Share_keyword", mAuction).apply();

                                Intent i = new Intent(activity, ShareWithinAppActivity.class);
                                activity.startActivity(i);
                                //activity.finish();
                                break;

                            case R.id.other:


                                String imageFilePath = "", imagename = activity.getString(R.string.base_image_url) + "logo48x48.png";
                                Intent intent = new Intent(Intent.ACTION_SEND);


                                allDetails = "Auction Title: " + auctionDetailsArrayList.get(holder.getAdapterPosition()).getActionTitle() + "\n" +
                                        "No Of Vehicle: " + auctionDetailsArrayList.get(holder.getAdapterPosition()).getNoOfVehicle() + "\n" +
                                        "Auction End Date: " + auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndDate() + "\n" +
                                        "Auction End Time: " + auctionDetailsArrayList.get(holder.getAdapterPosition()).getEndTime() + "\n" +
                                        "Auction Type: " + auctionDetailsArrayList.get(holder.getAdapterPosition()).getAuctionType() + "\n";

                                Log.e("TAG", "img : " + imagename);

                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(imagename));
                                request.allowScanningByMediaScanner();
                                String filename = URLUtil.guessFileName(imagename, null, MimeTypeMap.getFileExtensionFromUrl(imagename));
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                                Log.e("ShareImagePath :", filename);
                                Log.e("TAG", "img : " + imagename);

                                DownloadManager manager = (DownloadManager) activity.getApplication()
                                        .getSystemService(Context.DOWNLOAD_SERVICE);

                                Log.e("TAG", "img URL: " + imagename);

                                assert manager != null;
                                manager.enqueue(request);

                                imageFilePath = "/storage/emulated/0/Download/" + filename;
                                System.out.println("ImageFilePath:" + imageFilePath);

                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, allDetails);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageFilePath)));
                                activity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                break;
                        }
                        return false;
                    }
                });
                mPopupMenu.show(); //showing popup menu
            }
        });


    }

    @Override
    public int getItemCount() {
        return auctionDetailsArrayList.size();
    }

    private class AuctionHolder extends RecyclerView.ViewHolder {

        TextView action_title, auction_vehicle, auction_enddate, auction_endtime, auction_startdate,
                auction_starttime, mAuction_category, mStockLocation;
        TextView timer;
        Button preview, btnshare, btnclause;


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
            timer = (TextView) view.findViewById(R.id.timer);
            btnclause = (Button) view.findViewById(R.id.btnclauses);
            mAuction_category = (TextView) view.findViewById(R.id.auction_category);
            mStockLocation = (TextView) view.findViewById(R.id.stockLocation);
        }

    }

}
