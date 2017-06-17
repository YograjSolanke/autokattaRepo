package autokatta.com.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.auction.PreviewGoingEvents;
import autokatta.com.auction.PreviewLiveEvents;
import autokatta.com.auction.PreviewUpcomingEvent;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ModelLiveFragment;
import autokatta.com.view.ShareWithinAppActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ak-001 on 4/4/17.
 */

public class AuctionNotificationAdapter extends RecyclerView.Adapter<AuctionNotificationAdapter.MyViewHolder> {

    Activity mActivity;
    private List<ModelLiveFragment> mItemList = new ArrayList<>();
    private String auctionType, whoseAuction = "", special_clause, auction_id = "", loan_id = "", exchange_id = "",
            sale_id = "", service_id = "";
    private String allDetails, mContact;
    private HashMap<TextView, CountDownTimer> counters;
    private ConnectionDetector mConnectionDetector;
    private Boolean boolGoing = true;

    public AuctionNotificationAdapter(Activity mActivity, List<ModelLiveFragment> mItemList, String auctionType) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.auctionType = auctionType;
        this.counters = new HashMap<TextView, CountDownTimer>();
        mConnectionDetector = new ConnectionDetector(mActivity);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mAuctionCardView;
        TextView mAuctioneer, mAuctioneerTitle, mAuctioneerNoOfVehicles, mAuctioneerType, mTimer, mStartDate, mStartTime,
                mEndDate, mEndTime, mAuction_category, mStockLocation, closeopentxt, title;
        Button mSpecialClauses, mAuctionPreview, mShare, mAuctionGoing;
        ImageView mStamp;
        RelativeLayout profilenamerel1, profilenamerel2, Rl_auction_category;

        public MyViewHolder(View itemView) {
            super(itemView);
            mAuctionCardView = (CardView) itemView.findViewById(R.id.auction_card_view);
            mAuctioneer = (TextView) itemView.findViewById(R.id.auctioneer_name);
            mAuctioneerTitle = (TextView) itemView.findViewById(R.id.auctioneer_title);
            title = (TextView) itemView.findViewById(R.id.title);
            mAuctioneerNoOfVehicles = (TextView) itemView.findViewById(R.id.auctioneer_no_of_vehicles);
            mAuctioneerType = (TextView) itemView.findViewById(R.id.auctioneer_type);
            closeopentxt = (TextView) itemView.findViewById(R.id.closeopentxt);
            mTimer = (TextView) itemView.findViewById(R.id.timer);
            mStartDate = (TextView) itemView.findViewById(R.id.start_date);
            mStartTime = (TextView) itemView.findViewById(R.id.start_time);
            mEndDate = (TextView) itemView.findViewById(R.id.end_date);
            mEndTime = (TextView) itemView.findViewById(R.id.end_time);
            mAuction_category = (TextView) itemView.findViewById(R.id.auction_category);
            mStockLocation = (TextView) itemView.findViewById(R.id.stockLocation);

            mSpecialClauses = (Button) itemView.findViewById(R.id.btnspecial_clauses);
            mAuctionPreview = (Button) itemView.findViewById(R.id.auction_preview);
            mAuctionGoing = (Button) itemView.findViewById(R.id.auction_going);
            mShare = (Button) itemView.findViewById(R.id.auction_share);
            mStamp = (ImageView) itemView.findViewById(R.id.stamp);

            profilenamerel1 = (RelativeLayout) itemView.findViewById(R.id.profilenamerel1);
            profilenamerel2 = (RelativeLayout) itemView.findViewById(R.id.profilenamerel2);
            Rl_auction_category = (RelativeLayout) itemView.findViewById(R.id.Rl_auction_category);
        }
    }

    @Override
    public AuctionNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_auction, parent, false);
        MyViewHolder holder = new MyViewHolder(mView);
        mContact = mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        return holder;
    }

    @Override
    public void onBindViewHolder(final AuctionNotificationAdapter.MyViewHolder holder, final int position) {
        auction_id = mItemList.get(position).getAuctionId();
        loan_id = mItemList.get(position).getLoan_id();
        exchange_id = mItemList.get(position).getExchange_id();
        sale_id = mItemList.get(position).getSale_id();
        service_id = mItemList.get(position).getService_id();

        holder.mAuctioneer.setText(mItemList.get(position).getUsername());
        holder.mAuctioneerTitle.setText(mItemList.get(position).getName());
        holder.mAuctioneerNoOfVehicles.setText(mItemList.get(position).getNoOfVehicles());
        holder.mAuctioneerType.setText(mItemList.get(position).getAuctionType());
        holder.mStartDate.setText(mItemList.get(position).getStartDate());
        holder.mStartTime.setText(mItemList.get(position).getStartTime());
        holder.mEndDate.setText(mItemList.get(position).getEndDate());
        holder.mEndTime.setText(mItemList.get(position).getEndTime());
        holder.mAuction_category.setText(mItemList.get(position).getAuctionCategory());
        holder.mStockLocation.setText(mItemList.get(position).getLocation());

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

        special_clause = mItemList.get(position).getSpecialClauses();

        //buttons...
        holder.mSpecialClauses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mActivity);
                alertDialog.setTitle("Special Clauses");

                final TextView input = new TextView(mActivity);
                input.setText(special_clause.replaceAll(",", "\n"));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setNeutralButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

        //preview...
        if (mItemList.get(position).getKeyWord().equals("auction")) {
            holder.mAuctionGoing.setVisibility(View.GONE);
        } else {
            holder.mAuctionGoing.setVisibility(View.VISIBLE);
            holder.mAuctionPreview.setVisibility(View.GONE);
            holder.profilenamerel1.setVisibility(View.GONE);
            holder.profilenamerel2.setVisibility(View.GONE);
            holder.Rl_auction_category.setVisibility(View.GONE);
            holder.mSpecialClauses.setVisibility(View.GONE);
            holder.closeopentxt.setVisibility(View.GONE);
        }

        /*
        Set title...
         */
        if (mItemList.get(position).getKeyWord().equals("sale")) {
            holder.title.setText("Sale Title");
            holder.mShare.setVisibility(View.GONE);
        } else if (mItemList.get(position).getKeyWord().equals("loan")) {
            holder.title.setText("Loan Title");
        } else if (mItemList.get(position).getKeyWord().equals("exchange")) {
            holder.title.setText("Exchange Title");
        } else if (mItemList.get(position).getKeyWord().equals("service")) {
            holder.title.setText("Service Title");
            holder.mShare.setVisibility(View.GONE);
        }

        holder.mAuctionGoing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });

        holder.mAuctionPreview.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();

            @Override
            public void onClick(View v) {
                bundle.putString("auctioneer", mItemList.get(position).getUsername());
                bundle.putString("auction_id", mItemList.get(position).getAuctionId());
                bundle.putString("action_title", mItemList.get(position).getName());
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
                bundle.putString("category", mItemList.get(position).getAuctionCategory());
                bundle.putString("location", mItemList.get(position).getLocation());

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


        holder.mShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                shareButton(position);
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

    /*
    On Going Butoon Clicked....
     */
    private void clickButton() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder
                .setMessage("Pay EMD or auction platform fees")
                .setCancelable(false)
                .setPositiveButton("Now",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                try {
                                    if (mConnectionDetector.isConnectedToInternet()) {
                                        //JSON to Gson conversion
                                        Gson gson = new GsonBuilder()
                                                .setLenient()
                                                .create();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                .client(initLog().build())
                                                .build();

                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                        Call<String> add = serviceApi.addIgnoreGoingMe(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                                                Context.MODE_PRIVATE).getString("loginContact", ""),
                                                auction_id, loan_id, exchange_id, sale_id, service_id, "going");
                                        add.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if (response.isSuccessful()) {
                                                    if (response.body() != null) {
                                                        if (response.body().equals("success")) {
                                                            Log.e("Success", "Going");
                                                        }
                                                    }
                                                } else {
                                                    Log.e("No", "Response");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                                    } else
                                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("Later",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                //dialog.cancel();
                                boolGoing = false;
                                try {
                                    if (mConnectionDetector.isConnectedToInternet()) {
                                        //JSON to Gson conversion
                                        Gson gson = new GsonBuilder()
                                                .setLenient()
                                                .create();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(mActivity.getString(R.string.base_url))
                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                .client(initLog().build())
                                                .build();

                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                        Call<String> add = serviceApi.addIgnoreGoingMe(mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference),
                                                Context.MODE_PRIVATE).getString("loginContact", ""), auction_id, loan_id, exchange_id, sale_id, service_id, "going");
                                        add.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if (response.isSuccessful()) {
                                                    if (response.body() != null) {
                                                        if (response.body().equals("success")) {
                                                            Log.e("Success", "Later");
                                                        }
                                                    }
                                                } else {
                                                    Log.e("No", "Response");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                                    } else
                                        CustomToast.customToast(mActivity, mActivity.getString(R.string.no_internet));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*
    On Share Butoon Clicked....
     */
    private void shareButton(final int position) {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(mActivity);
        alert.setTitle("Share");
        alert.setMessage("with Autokatta or to other?");
        alert.setIconAttribute(android.R.attr.alertDialogIcon);

        alert.setPositiveButton("Autokatta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (mItemList.get(position).getKeyWord().equals("auction")) {
                    allDetails = mItemList.get(position).getName() + "="
                            + mItemList.get(position).getNoOfVehicles() + "="
                            + mItemList.get(position).getEndDate() + "=" +
                            mItemList.get(position).getEndTime() + "=" +
                            mItemList.get(position).getAuctionType() + "=" +
                            "0" + "=" + "0" + "=" + "a";
                    String mAuction = "auction";


                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_sharedata", allDetails).apply();
                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_auction_id", mItemList.get(position).getAuctionId()).apply();
                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_keyword", mAuction).apply();
                } else if (mItemList.get(position).getKeyWord().equals("loan")) {

                    allDetails = mItemList.get(position).getUsername() + "="
                            + "" + "="
                            + mItemList.get(position).getName() + "="
                            + mItemList.get(position).getLocation() + "="
                            + "" + "="
                            + mItemList.get(position).getStartDate() + "="
                            + mItemList.get(position).getStartTime() + "="
                            + mItemList.get(position).getEndDate() + "="
                            + mItemList.get(position).getEndTime() + "="
                            + mItemList.get(position).getImage();
                    String mAuction = "loan";


                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_sharedata", allDetails).apply();
                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_loan_id", mItemList.get(position).getLoan_id()).apply();
                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_keyword", mAuction).apply();
                } else if (mItemList.get(position).getKeyWord().equals("exchange")) {

                    allDetails = mItemList.get(position).getUsername() + "="
                            + "" + "="
                            + mItemList.get(position).getName() + "="
                            + mItemList.get(position).getLocation() + "="
                            + "" + "="
                            + mItemList.get(position).getStartDate() + "="
                            + mItemList.get(position).getStartTime() + "="
                            + mItemList.get(position).getEndDate() + "="
                            + mItemList.get(position).getEndTime() + "="
                            + mItemList.get(position).getImage();
                    String mAuction = "exchange";


                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_sharedata", allDetails).apply();
                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_exchange_id", mItemList.get(position).getExchange_id()).apply();
                    mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                            putString("Share_keyword", mAuction).apply();
                }

                Intent i = new Intent(mActivity, ShareWithinAppActivity.class);
                mActivity.startActivity(i);
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Other", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mContact.equals(mItemList.get(position).getContact()))
                    whoseAuction = "your";
                else
                    //whoseAuction = "otherauction";
                    whoseAuction = mItemList.get(position).getUsername();

                if (mItemList.get(position).getKeyWord().equals("auction")) {
                    allDetails = "Auction Title: " + mItemList.get(position).getName() + "\n" +
                            "No Of Vehicle: " + mItemList.get(position).getNoOfVehicles() + "\n" +
                            "Auction End Date: " + mItemList.get(position).getEndDate() + "\n" +
                            "Auction End Time: " + mItemList.get(position).getEndTime() + "\n" +
                            "Auction Type: " + mItemList.get(position).getAuctionType() + "\n" +
                       /* "0" + "\n"+//.auctionGoingcount+"="+
                        "0" + "\n"+//auctionIgnorecount*/
                            "Auctioneer: " + whoseAuction;
                } else if (mItemList.get(position).getKeyWord().equals("loan")) {

                    allDetails = "Loan Title: " + mItemList.get(position).getName() + "\n" +
                            /*"No Of Vehicle: " + mItemList.get(position).getNoOfVehicles() + "\n" +*/
                            "Loan End Date: " + mItemList.get(position).getEndDate() + "\n" +
                            "Loan End Time: " + mItemList.get(position).getEndTime() + "\n" +
                            "Loan Loacation: " + mItemList.get(position).getLocation() + "\n" +
                       /* "0" + "\n"+//.auctionGoingcount+"="+
                        "0" + "\n"+//auctionIgnorecount*/
                            "Loan Owner: " + whoseAuction;
                } else if (mItemList.get(position).getKeyWord().equals("exchange")) {

                    allDetails = "Exchange Title: " + mItemList.get(position).getName() + "\n" +
                            /*"No Of Vehicle: " + mItemList.get(position).getNoOfVehicles() + "\n" +*/
                            "Exchange End Date: " + mItemList.get(position).getEndDate() + "\n" +
                            "Exchange End Time: " + mItemList.get(position).getEndTime() + "\n" +
                            "Exchange Loacation: " + mItemList.get(position).getLocation() + "\n" +
                       /* "0" + "\n"+//.auctionGoingcount+"="+
                        "0" + "\n"+//auctionIgnorecount*/
                            "Exchange Owner: " + whoseAuction;
                }


                Intent intent = new Intent(Intent.ACTION_SEND);

                /*mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_auction_id", mItemList.get(position).getAuctionId()).apply();
                mActivity.getSharedPreferences(mActivity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "auction").apply();*/

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                intent.putExtra(Intent.EXTRA_TEXT, allDetails);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mActivity.startActivity(intent);
                dialog.dismiss();
            }

        });
        alert.create();
        alert.show();
    }


    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
    }
}
