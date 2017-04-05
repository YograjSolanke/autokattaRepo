package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.MyActiveAuctionResponse;

/**
 * Created by ak-004 on 30/3/17.
 */

public class ActiveAuctionAdapter extends RecyclerView.Adapter<ActiveAuctionAdapter.AuctionHolder> {

    private HashMap<TextView, CountDownTimer> counters;
    Activity activity;
    Handler handler;
    Runnable runnable;
    String[] st;
    String special_clause, spcl, allDetails;
    private static LayoutInflater inflater = null;
    List<MyActiveAuctionResponse.Success.Auction> auctionDetailsArrayList;

    public ActiveAuctionAdapter(Activity activity, List<MyActiveAuctionResponse.Success.Auction> itemist) {
        this.activity = activity;
        this.auctionDetailsArrayList = itemist;
        this.counters = new HashMap<TextView, CountDownTimer>();


    }


    @Override
    public ActiveAuctionAdapter.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_auction_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        AuctionHolder vh = new AuctionHolder(v);
        handler = new Handler();
        return vh;
    }

    @Override
    public void onBindViewHolder(ActiveAuctionAdapter.AuctionHolder holder, final int position) {

        holder.action_title.setText(auctionDetailsArrayList.get(position).getActionTitle());
        holder.auction_vehicle.setText(auctionDetailsArrayList.get(position).getNoOfVehicle());
        holder.auction_enddate.setText(auctionDetailsArrayList.get(position).getEndDate());
        holder.auction_endtime.setText(auctionDetailsArrayList.get(position).getEndTime());
        holder.auction_startdate.setText(auctionDetailsArrayList.get(position).getStartDate());
        holder.auction_starttime.setText(auctionDetailsArrayList.get(position).getStartTime());


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
            //long diff=now.getTime()-currentDate.getTime();
            //long abc=difference-diff;

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


        holder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("auction_id", auctionDetailsArrayList.get(position).getAuctionId());
                b.putString("actiontitle", auctionDetailsArrayList.get(position).getActionTitle());
                b.putString("auctionvehicle", auctionDetailsArrayList.get(position).getNoOfVehicle());
                b.putString("auctionstartdate", auctionDetailsArrayList.get(position).getStartDate());
                b.putString("auctionstarttime", auctionDetailsArrayList.get(position).getStartTime());
                b.putString("auctionenddate", auctionDetailsArrayList.get(position).getEndDate());
                b.putString("auctionendtime", auctionDetailsArrayList.get(position).getEndTime());
                b.putString("specialclauses", auctionDetailsArrayList.get(position).getSpecialClauses());
                b.putString("enddatetime", auctionDetailsArrayList.get(position).getEndDateTime());
                b.putString("startdatetime", auctionDetailsArrayList.get(position).getStartDateTime());
                b.putString("participant", auctionDetailsArrayList.get(position).getGoingcount());
                b.putString("key", "auction");

//                MyLivePreview fr = new MyLivePreview();
//                fr.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fr);
//                fragmentTransaction.addToBackStack("mylivepreview");
//                fragmentTransaction.commit();
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


//                ShareWithinApp fr = new ShareWithinApp();
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fr);
//                fragmentTransaction.addToBackStack("sharewithinapp");
//                fragmentTransaction.commit();

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
                auction_starttime;
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
        }

    }


//
//    private void mailAuctionData()
//    {
//        RequestQueue requestQueue = Volley.newRequestQueue(activity);
//
//        final ProgressDialog dialog;
//        dialog = ProgressDialog.show(activity, "Sending Mail", "Please wait...", true);
//        dialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                "http://autokatta.com/mobile/email_v.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println("onResponse:mailAuctionData:" + response);
//                        dialog.dismiss();
//
//                        if(response.startsWith("1"))
//                        {
//                            Toast.makeText(activity,"Mail Sent Successfully",Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(activity,"Problem in sending mail",Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("onError:mailAuctionData:" + error.toString());
//                dialog.dismiss();
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("auction_id", auctionid);
//                params.put("contact", contactnumber);
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
}
