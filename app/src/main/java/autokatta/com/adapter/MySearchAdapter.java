package autokatta.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.MySearchResponse;
import retrofit2.Response;

/**
 * Created by ak-004 on 3/4/17.
 */

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.SearchHolder> implements RequestNotifier {

    Activity activity;
    String SearchId, keyword, allDetails;
    List<MySearchResponse.Success> mMainlist;
    int flag = 0;
    ApiCall apiCall;


    public MySearchAdapter(Activity activity, List<MySearchResponse.Success> successList) {
        this.activity = activity;
        this.mMainlist = successList;
        apiCall = new ApiCall(activity, this);
    }
    @Override
    public MySearchAdapter.SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_search_adapter, parent, false);

        SearchHolder holder = new SearchHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MySearchAdapter.SearchHolder holder, final int position) {


        //To Check Search Status
        if (mMainlist.get(position).getSearchstatus().equalsIgnoreCase("stop")) {
            holder.Stopsearch.setVisibility(View.GONE);
            holder.Startsearch.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);

        }

        if (mMainlist.get(position).getMysearchstatus().equalsIgnoreCase("") ||
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
        try {

            DateFormat date = new SimpleDateFormat(" MMM dd ");
            DateFormat time = new SimpleDateFormat(" hh:mm a");
            holder.textsearchdate.setText(date.format(mMainlist.get(position).getSearchdate()) +
                    time.format(mMainlist.get(position).getSearchdate()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //To set LastDate
        try {

            DateFormat date1 = new SimpleDateFormat(" MMM dd ");
            DateFormat time1 = new SimpleDateFormat(" hh:mm a");
            holder.Stopdate.setText(date1.format(mMainlist.get(position).getStopdate()) +
                    time1.format(mMainlist.get(position).getStopdate()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("action", "update");
                bundle.putString("category", holder.textcategory.getText().toString());
                bundle.putString("brand", holder.textbrand.getText().toString());
                bundle.putString("model", holder.textmodel.getText().toString());
                bundle.putString("price", holder.textprice.getText().toString());
                bundle.putString("year", holder.textyear.getText().toString());
                bundle.putString("search_id", mMainlist.get(holder.getAdapterPosition()).getSearchId());

//                FilterActivity fragment = new FilterActivity();
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fragment);
//                fragmentTransaction.addToBackStack("filteractivity");
//                fragmentTransaction.commit();
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
                                mMainlist.remove(position);
                                notifyDataSetChanged();

//                                MySearchFragment fragment = new MySearchFragment();
//                                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.replace(R.id.containerView, fragment);
//                                fragmentTransaction.addToBackStack("mysearchfragment");
//                                fragmentTransaction.commit();

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
//        holder.favImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SearchId = obj.searchId;
//                //new AddToFavourite().execute();
//                AddToFav(obj.searchId);
//                holder.favImg.setVisibility(View.INVISIBLE);
//                holder.unfavImg.setVisibility(View.VISIBLE);
//                obj.searchStatus = "yes";
//
//                searchlist.set(position, obj);
//                // obj.searchFavouritestatus.set(position, "yes");
//
//            }
//        });
//
//        //Stop notification related to my search
//        holder.Stopsearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchId = obj.searchId;
//
//                new AlertDialog.Builder(activity)
//                        .setTitle("Stop Notification")
//                        .setMessage("Are you sure you want to Stop Notification?")
//
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                keyword = "stop";
//                                //new deleteData().execute();
//                                DeleteSearch(obj.searchId, position, keyword);
//
//                                holder.Stopsearch.setVisibility(View.INVISIBLE);
//                                holder.Startsearch.setVisibility(View.VISIBLE);
//                                holder.relativeLayout.setVisibility(View.VISIBLE);
//                                obj.searchStatus = "stop";
//
//                                searchlist.set(position, obj);
//                            }
//                        })
//
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//
//
//            }
//        });
//
//        //Start notification related to my search
//        holder.Startsearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchId = obj.searchId;
//
//                new AlertDialog.Builder(activity)
//                        .setTitle("Start Notification")
//                        .setMessage("Are you sure you want to Start Notification?")
//
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                keyword = "start";
//                                //new deleteData().execute();
//                                DeleteSearch(obj.searchId, position, keyword);
//
//                                holder.Startsearch.setVisibility(View.INVISIBLE);
//                                holder.Stopsearch.setVisibility(View.VISIBLE);
//                                holder.relativeLayout.setVisibility(View.GONE);
//                                obj.searchStatus = "start";
//
//                                searchlist.set(position, obj);
//                            }
//                        })
//
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//
//
//            }
//        });

        //Share my search
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();

                allDetails = mMainlist.get(holder.getAdapterPosition()).getCategory() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getBrand() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getModel() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getPrice() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getYearOfManufactur() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getSearchdate() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getBuyerLeads();


                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_search_id", mMainlist.get(holder.getAdapterPosition()).getSearchId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "mysearch").apply();


                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.setType("image/*");

                intent1.putExtra(Intent.EXTRA_TEXT, allDetails);
                activity.startActivity(Intent.createChooser(intent1, "Share via"));

                if (intent1.getAction().equals(Intent.ACTION_SEND)) {
                    System.out.println("Send Complete From Text Apps");
                    flag = 1;
                }
//via Email:

                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("message/rfc822");
                //intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL1, EMAIL2});
                intent2.putExtra(Intent.EXTRA_SUBJECT, "Search list from Autokatta User");
                //intent2.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(allDetails));
                intent2.putExtra(Intent.EXTRA_TEXT, allDetails);
                activity.startActivity(intent2);


            }
        });

        holder.share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();


                allDetails = mMainlist.get(holder.getAdapterPosition()).getCategory() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getBrand() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getModel() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getPrice() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getYearOfManufactur() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getSearchdate() + "=" +
                        mMainlist.get(holder.getAdapterPosition()).getBuyerLeads();


                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_sharedata", allDetails).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_search_id", mMainlist.get(holder.getAdapterPosition()).getSearchId()).apply();
                activity.getSharedPreferences(activity.getString(R.string.my_preference), Context.MODE_PRIVATE).edit().
                        putString("Share_keyword", "mysearch").apply();


//                ShareWithinApp fr = new ShareWithinApp();
//                // fr.setArguments(b);
//
//                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerView, fr);
//                fragmentTransaction.addToBackStack("sharewithinapp");
//                fragmentTransaction.commit();


            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.BuyerLeads.getText().toString().equalsIgnoreCase("0")) {
                    // Toast.makeText(activity, "No leads found", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "No leads found", Snackbar.LENGTH_SHORT).show();
                } else {
                    SearchId = mMainlist.get(holder.getAdapterPosition()).getSearchId();
                    Bundle b = new Bundle();
                    b.putString("search_id", SearchId);
//                    MySearchSellerList frag2 = new MySearchSellerList();    // Call Another Fragment
//                    frag2.setArguments(b);
//
//                    FragmentManager fragmentManager = ctx.getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView, frag2);
//                    //content_frame is a Id  of Frame Layout
//                    fragmentTransaction.addToBackStack("mysearchsellerlist");
//                    fragmentTransaction.commit(); //
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class SearchHolder extends RecyclerView.ViewHolder {

        public TextView textcategory;
        public TextView textbrand;
        public TextView textmodel;
        public TextView textprice;
        public TextView textyear;
        public TextView textsearchdate;
        public TextView BuyerLeads;
        public ImageView editImg;
        public ImageView deleteData;
        public ImageView favImg;
        public ImageView unfavImg;
        public ImageView share;
        public ImageView share1;
        public Button Stopsearch;
        public Button Startsearch;
        public RelativeLayout relativeLayout;
        public TextView Stopdate;
        CardView cardView;

        public SearchHolder(View itemView) {
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

    }

    @Override
    public void notifyString(String str) {

    }
}
