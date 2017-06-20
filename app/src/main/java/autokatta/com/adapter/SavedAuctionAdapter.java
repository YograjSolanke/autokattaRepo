package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.events.AddVehiclesForAuctionFragment;
import autokatta.com.response.MySavedAuctionResponse;

/**
 * Created by ak-004 on 31/3/17.
 */

public class SavedAuctionAdapter extends RecyclerView.Adapter<SavedAuctionAdapter.AuctionHolder> {

    Activity activity;
    private List<MySavedAuctionResponse.Success> mMainlist = new ArrayList<>();

    public SavedAuctionAdapter(Activity activity, List<MySavedAuctionResponse.Success> itemlist) {
        this.activity = activity;
        this.mMainlist = itemlist;
    }


    @Override
    public SavedAuctionAdapter.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_auction_adapter, parent, false);
        AuctionHolder holder = new AuctionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SavedAuctionAdapter.AuctionHolder holder, final int position) {
        holder.title.setText(mMainlist.get(position).getActionTitle());
        holder.start_date.setText(mMainlist.get(position).getStartDate());
        holder.start_time.setText(mMainlist.get(position).getStartTime());
        holder.end_date.setText(mMainlist.get(position).getEndDate());
        holder.end_time.setText(mMainlist.get(position).getEndTime());
        holder.editvehicle.setText(mMainlist.get(position).getNoOfVehicles());
        holder.mAuction_category.setText(mMainlist.get(position).getAuctioncategory());
        holder.mStockLocation.setText(mMainlist.get(position).getStockLocation());

        holder.special_clauses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String clauses[] = new String[0];
                try {
                    clauses = mMainlist.get(position).getSpecialClauses().split(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Special Clauses");
                builder.setCancelable(true);
                builder.setItems(clauses, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        dialog.dismiss();
                    }
                }).show();*/

                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(activity)
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

        holder.btnactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("auction_id", mMainlist.get(position).getAuctionId());
                b.putString("title", mMainlist.get(position).getActionTitle());
                b.putString("startdate", mMainlist.get(position).getStartDate());
                b.putString("starttime", mMainlist.get(position).getStartTime());
                b.putString("enddate", mMainlist.get(position).getEndDate());
                b.putString("endtime", mMainlist.get(position).getEndTime());
                b.putString("className", "SavedAuction");
                b.putString("cluases", mMainlist.get(position).getSpecialClauses());
                b.putString("category", mMainlist.get(position).getAuctioncategory());
                b.putString("location", mMainlist.get(position).getStockLocation());
               if ( mMainlist.get(position).getNoOfVehicles().equalsIgnoreCase("")
                       ||mMainlist.get(position).getNoOfVehicles().equalsIgnoreCase(null)
               || mMainlist.get(position).getNoOfVehicles().isEmpty())
               {
                   b.putString("noofvehicles", "0");
               }else
               {
                   b.putString("noofvehicles", mMainlist.get(position).getNoOfVehicles());
               }
                b.putBooleanArray("positionArray", mMainlist.get(position).getPositionArray());

                AddVehiclesForAuctionFragment frag = new AddVehiclesForAuctionFragment();
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                frag.setArguments(b);
                fragmentTransaction.replace(R.id.saved_auctionFrame, frag);
                fragmentTransaction.addToBackStack("AddVehiclesForAuction");
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    static class AuctionHolder extends RecyclerView.ViewHolder {

        EditText title;
        EditText start_date;
        EditText start_time;
        EditText end_date;
        EditText end_time;
        EditText editvehicle;
        TextView timer, mAuction_category, mStockLocation;
        Button btnactivate, special_clauses;

        AuctionHolder(View view) {
            super(view);
            title = (EditText) view.findViewById(R.id.typeofauction2);
            start_date = (EditText) view.findViewById(R.id.datetime3);
            start_time = (EditText) view.findViewById(R.id.editText2);
            end_date = (EditText) view.findViewById(R.id.datetime2);
            end_time = (EditText) view.findViewById(R.id.editText);
            editvehicle = (EditText) view.findViewById(R.id.editvehicle);
            special_clauses = (Button) view.findViewById(R.id.special_clauses);

            btnactivate = (Button) view.findViewById(R.id.btnactivate);
            mAuction_category = (TextView) view.findViewById(R.id.auction_category);
            mStockLocation = (TextView) view.findViewById(R.id.stockLocation);

        }

    }
}
