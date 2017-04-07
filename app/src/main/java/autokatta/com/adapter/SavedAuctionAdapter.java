package autokatta.com.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    List<MySavedAuctionResponse.Success> mMainlist = new ArrayList<>();

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
    public void onBindViewHolder(SavedAuctionAdapter.AuctionHolder holder, final int position) {


        holder.title.setText(mMainlist.get(position).getActionTitle());
        holder.start_date.setText(mMainlist.get(position).getStartDate());
        holder.start_time.setText(mMainlist.get(position).getStartTime());
        holder.end_date.setText(mMainlist.get(position).getEndDate());
        holder.end_time.setText(mMainlist.get(position).getEndTime());
        holder.editvehicle.setText(mMainlist.get(position).getNoOfVehicles());

        holder.special_clauses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clauses[] = new String[0];
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
                }).show();
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
//                b.putString("cluase", cluases);
//                b.putString("ids", ids);
                b.putString("cluases", mMainlist.get(position).getSpecialClauses());
//
                //Boolean [] array=(Boolean[]) mMainlist.get(position).getPositionArray().toArray();
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
        TextView timer;
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

        }

    }
}
