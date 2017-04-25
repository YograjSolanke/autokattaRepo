package autokatta.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.events.ActiveLoanMelaPreviewActivity;
import autokatta.com.response.MyActiveLoanMelaResponse;

/**
 * Created by ak-004 on 30/3/17.
 */

public class ActiveLoanMelaAdapter extends RecyclerView.Adapter<ActiveLoanMelaAdapter.LoanHolder> {
    List<MyActiveLoanMelaResponse.Success> mMainlist = new ArrayList<>();
    Activity mActivity;
    FragmentActivity ctx;


    public ActiveLoanMelaAdapter(Activity activity, List<MyActiveLoanMelaResponse.Success> itemlist) {

        this.mActivity = activity;
        this.mMainlist = itemlist;

    }

    @Override
    public ActiveLoanMelaAdapter.LoanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_loan_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        LoanHolder vh = new LoanHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ActiveLoanMelaAdapter.LoanHolder holder, int position) {

        holder.title.setText(mMainlist.get(position).getName());
        holder.startdate.setText(mMainlist.get(position).getStartDate());
        holder.starttime.setText(mMainlist.get(position).getStartTime());
        holder.enddate.setText(mMainlist.get(position).getEndDate());
        holder.endtime.setText(mMainlist.get(position).getEndTime());
        holder.location.setText(mMainlist.get(position).getLocation());
        holder.address.setText(mMainlist.get(position).getAddress());

        holder.image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
                Intent i=new Intent(mActivity, ActiveLoanMelaPreviewActivity.class);
                mActivity.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMainlist.size();
    }

    static class LoanHolder extends RecyclerView.ViewHolder {

        TextView title, enddate, endtime, startdate, starttime, location, address, details;
        ImageView image;

        public LoanHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            location = (TextView) itemView.findViewById(R.id.setlocation);
            address = (TextView) itemView.findViewById(R.id.setaddress);
            startdate = (TextView) itemView.findViewById(R.id.datetime1);
            starttime = (TextView) itemView.findViewById(R.id.editTime);
            enddate = (TextView) itemView.findViewById(R.id.datetime2);
            endtime = (TextView) itemView.findViewById(R.id.editText);
            image = (ImageView) itemView.findViewById(R.id.loanmelaimg);
            details = (TextView) itemView.findViewById(R.id.typeofauction2);


        }
    }
}
