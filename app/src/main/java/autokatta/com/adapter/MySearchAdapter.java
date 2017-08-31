package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment.SavedSearchSellerListFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MySearchResponse;
import autokatta.com.view.SearchVehicleActivity;
import autokatta.com.view.ShareWithinAppActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 3/4/17.
 */

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.SearchHolder> implements RequestNotifier {

    Activity activity;
    private String keyword;
    private int SearchId;
    List<MySearchResponse.Success> mMainlist;
    ApiCall apiCall;
    String myContact;

    public MySearchAdapter(Activity activity1, List<MySearchResponse.Success> successList) {
        this.activity = activity1;
        this.mMainlist = successList;
        apiCall = new ApiCall(activity, this);
        myContact = activity.getSharedPreferences(activity.getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_search_adapter, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchHolder holder, int position) {
        //To Check Search Status
        if (mMainlist.get(position).getMysearchstatus().equalsIgnoreCase("stop")) {
            holder.Stopsearch.setVisibility(View.GONE);
            holder.Startsearch.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);
        }

        if (mMainlist.get(position).getMysearchstatus() == null ||
                mMainlist.get(position).getMysearchstatus().isEmpty() ||
                mMainlist.get(position).getMysearchstatus().equalsIgnoreCase("start")) {
            holder.Stopsearch.setVisibility(View.VISIBLE);
            holder.Startsearch.setVisibility(View.GONE);
            holder.relativeLayout.setVisibility(View.GONE);
        }

        //To Check Favourite Status
        if (mMainlist.get(position).getSearchstatus().equals("yes")) {
            holder.unfavImg.setVisibility(View.VISIBLE);
            holder.favImg.setVisibility(View.GONE);
        }

        if (mMainlist.get(position).getSearchstatus().equals("no")) {
            holder.unfavImg.setVisibility(View.GONE);
            holder.favImg.setVisibility(View.VISIBLE);
        }

        holder.textcategory.setText(mMainlist.get(position).getCategory());
        holder.textbrand.setText(mMainlist.get(position).getBrand());
        holder.textmodel.setText(mMainlist.get(position).getModel());
        holder.textprice.setText(mMainlist.get(position).getPrice());
        holder.textyear.setText(mMainlist.get(position).getYearOfManufactur());
        holder.BuyerLeads.setText(mMainlist.get(position).getBuyerLeads());

        //To set Date
        /*try {
            DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");
            holder.textsearchdate.setText(date.format(mMainlist.get(position).getSearchDateNew()) +
                    time.format(mMainlist.get(position).getSearchDateNew()));

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        try {
            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
            //format of date coming from services
            DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss",
                    Locale.US);
            inputFormat.setTimeZone(utc);
            //format of date which want to show
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                    Locale.US);
            outputFormat.setTimeZone(utc);

            Date date = inputFormat.parse(mMainlist.get(position).getSearchdate());
            String output = outputFormat.format(date);
            System.out.println("jjj" + output);
            holder.textsearchdate.setText(output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //To set LastDate
        /*try {
            DateFormat date1 = new SimpleDateFormat(" MMM dd ");
            DateFormat time1 = new SimpleDateFormat(" hh:mm a");
            holder.Stopdate.setText(date1.format(mMainlist.get(position).getStopDateNew()) +
                    time1.format(mMainlist.get(position).getStopDateNew()));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            TimeZone utc = TimeZone.getTimeZone("etc/UTC");
            //format of date coming from services
            DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss a",
                    Locale.US);
            inputFormat.setTimeZone(utc);
            //format of date which want to show
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                    Locale.US);
            outputFormat.setTimeZone(utc);

            Date date = inputFormat.parse(mMainlist.get(position).getStopdate());
            String output = outputFormat.format(date);
            System.out.println("jjj" + output);
            holder.Stopdate.setText(output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("className", "MySearchAdapter");
                bundle.putString("category", holder.textcategory.getText().toString());
                bundle.putString("brand", holder.textbrand.getText().toString());
                bundle.putString("model", holder.textmodel.getText().toString());
                bundle.putString("price", holder.textprice.getText().toString());
                bundle.putString("year", holder.textyear.getText().toString());
                bundle.putInt("search_id", mMainlist.get(holder.getAdapterPosition()).getSearchId());

                //activity.finish();
                Intent intent = new Intent(activity, SearchVehicleActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);

            }
        });

        //Delete my search event
        holder.deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();
                new AlertDialog.Builder(activity)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this Search?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                keyword = "delete";
                                //new deleteData().execute();
                                apiCall.deleteMySearch(SearchId, keyword);
                                mMainlist.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), mMainlist.size());
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        //Make my search as favourites
        holder.favImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();
                apiCall.addToFavorite(myContact, "", SearchId, "", 0);
                holder.favImg.setVisibility(View.INVISIBLE);
                holder.unfavImg.setVisibility(View.VISIBLE);
                mMainlist.get(holder.getAdapterPosition()).setSearchstatus("yes");
                mMainlist.set(holder.getAdapterPosition(), mMainlist.get(holder.getAdapterPosition()));
                // obj.searchFavouritestatus.set(position, "yes");

            }
        });
//
//        //Stop notification related to my search
        holder.Stopsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();
                new AlertDialog.Builder(activity)
                        .setTitle("Stop Notification")
                        .setMessage("Are you sure you want to Stop Notification?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                keyword = "stop";
                                //new deleteData().execute();
                                apiCall.deleteMySearch(SearchId, keyword);
                                holder.Stopsearch.setVisibility(View.INVISIBLE);
                                holder.Startsearch.setVisibility(View.VISIBLE);
                                holder.relativeLayout.setVisibility(View.VISIBLE);
                                mMainlist.get(holder.getAdapterPosition()).setSearchstatus("stop");
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });
//
//        //Start notification related to my search
        holder.Startsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();

                new AlertDialog.Builder(activity)
                        .setTitle("Start Notification")
                        .setMessage("Are you sure you want to Start Notification?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                keyword = "start";
                                //new deleteData().execute();
                                apiCall.deleteMySearch(SearchId, keyword);
                                holder.Startsearch.setVisibility(View.INVISIBLE);
                                holder.Stopsearch.setVisibility(View.VISIBLE);
                                holder.relativeLayout.setVisibility(View.GONE);
                                mMainlist.get(holder.getAdapterPosition()).setSearchstatus("start");
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        //Share my search
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);

                String allSearchDetails = "Search Category : " + holder.textcategory.getText().toString() + "\n" +
                        "Search Brand : " + holder.textbrand.getText().toString() + "\n" +
                        "Search Model : " + holder.textmodel.getText().toString() + "\n" +
                        "Year Of Mfg : " + holder.textyear.getText().toString() + "\n" +
                        "Price : " + holder.textprice.getText().toString() + "\n" +
                        "Leads : " + holder.BuyerLeads.getText().toString() + "\n" +
                        "Date : " + holder.textsearchdate.getText().toString();

                System.out.println("all search detailssss======Other " + allSearchDetails);

                intent.setType("text/plain");
                                /*intent.putExtra(Intent.EXTRA_TEXT, "Please visit and Follow my vehicle on Autokatta. Stay connected for Product and Service updates and enquiries"
                                        + "\n" + "http://autokatta.com/vehicle/main/" + notificationList.get(holder.getAdapterPosition()).getSearchId() + "/" + mLoginContact
                                        + "\n" + "\n" + allSearchDetails);*/
                intent.putExtra(Intent.EXTRA_TEXT, allSearchDetails);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Search list from Autokatta User");
                activity.startActivity(Intent.createChooser(intent, "Autokatta"));

                                /*intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Please Find Below Attachments");
                                intent.putExtra(Intent.EXTRA_TEXT, allSearchDetails);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                mActivity.startActivity(intent);*/


            }
        });

        holder.share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String allSearchDetails = holder.textcategory.getText().toString() + "=" +
                        holder.textbrand.getText().toString() + "=" +
                        holder.textmodel.getText().toString() + "=" +
                        holder.textprice.getText().toString() + "=" +
                        holder.textyear.getText().toString() + "=" +
                        holder.textsearchdate.getText().toString() + "=" +
                        holder.BuyerLeads.getText().toString();


                System.out.println("all search detailssss======Auto " + allSearchDetails);

                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allSearchDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putInt("Share_search_id", mMainlist.get(holder.getAdapterPosition()).getSearchId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "mysearch").apply();


                Intent i = new Intent(activity, ShareWithinAppActivity.class);
                activity.startActivity(i);
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.BuyerLeads.getText().toString().equalsIgnoreCase("0")) {
                    CustomToast.customToast(activity, "No leads found");
                    //Snackbar.make(v, "No leads found", Snackbar.LENGTH_SHORT).show();
                } else {
                    SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();
                    Bundle b = new Bundle();
                    b.putInt("search_id", SearchId);
                    b.putString("category", holder.textcategory.getText().toString());
                    b.putString("brand", holder.textbrand.getText().toString());
                    b.putString("model", holder.textmodel.getText().toString());
                    b.putString("price", holder.textprice.getText().toString());
                    b.putString("year", holder.textyear.getText().toString());
                    b.putString("rto_city", mMainlist.get(holder.getAdapterPosition()).getRtoCity());
                    SavedSearchSellerListFragment frag2 = new SavedSearchSellerListFragment();    // Call Another Fragment
                    frag2.setArguments(b);

                    FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mysearchFrame, frag2);
                    fragmentTransaction.addToBackStack("mysearchsellerlist");
                    fragmentTransaction.commit(); //
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }


    static class SearchHolder extends RecyclerView.ViewHolder {

        TextView textcategory, textbrand, textmodel, textprice, textyear, textsearchdate, BuyerLeads, Stopdate;
        ImageView editImg, deleteData, favImg, unfavImg, share, share1;
        Button Stopsearch, Startsearch;
        RelativeLayout relativeLayout;
        CardView cardView;

        SearchHolder(View itemView) {
            super(itemView);

            textcategory = (TextView) itemView.findViewById(R.id.mysearch_category);
            textbrand = (TextView) itemView.findViewById(R.id.mysearch_brand);
            textmodel = (TextView) itemView.findViewById(R.id.mysearch_model);
            textprice = (TextView) itemView.findViewById(R.id.mysearch_price);
            textyear = (TextView) itemView.findViewById(R.id.mysearch_year);
            textsearchdate = (TextView) itemView.findViewById(R.id.searchdate);
            BuyerLeads = (TextView) itemView.findViewById(R.id.buyerleads);

            editImg = (ImageView) itemView.findViewById(R.id.editpen);
            deleteData = (ImageView) itemView.findViewById(R.id.deletevehi);
            favImg = (ImageView) itemView.findViewById(R.id.favsearch);
            unfavImg = (ImageView) itemView.findViewById(R.id.unfavsearch);
            share = (ImageView) itemView.findViewById(R.id.sharesearch);
            share1 = (ImageView) itemView.findViewById(R.id.sharesearch1);
            Stopsearch = (Button) itemView.findViewById(R.id.stopsearch);
            Startsearch = (Button) itemView.findViewById(R.id.startsearch);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relnote);
            Stopdate = (TextView) itemView.findViewById(R.id.txtdate);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(activity, activity.getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(activity, activity.getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "MySearchAdapter");
            error.printStackTrace();
        }


    }

    @Override
    public void notifyString(String str) {

        if (str != null) {

            switch (str) {
                case "successdelete":
                    CustomToast.customToast(activity, "Search deleted");
                    break;
                case "successstop":
                    CustomToast.customToast(activity, "Notification Stopped");
                    break;
                case "successstart":
                    CustomToast.customToast(activity, "Notification Started");
                    break;
                case "success_favourite":
                    CustomToast.customToast(activity, "Favourite data send");
                    break;
            }

        }

    }
}
