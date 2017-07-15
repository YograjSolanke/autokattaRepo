package autokatta.com.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import autokatta.com.R;
import autokatta.com.response.LoanMelaAnalyticsResponse;

/**
 * Created by ak-005 on 15/7/17.
 */

public class LoanAnalyticsAdapter extends RecyclerView.Adapter<LoanAnalyticsAdapter.LoanHolder> {
    Activity mActivity;
    private String strAuctionId;
    private List<LoanMelaAnalyticsResponse.Success> mAnalyticsList;

    public LoanAnalyticsAdapter(FragmentActivity activity, String strAuctionid, List<LoanMelaAnalyticsResponse.Success> analyticsList) {
        mActivity = activity;
        strAuctionId = strAuctionid;
        mAnalyticsList = analyticsList;
    }

    @Override
    public LoanAnalyticsAdapter.LoanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_loan_analytics, parent, false);
        return new LoanHolder(view);
    }

    @Override
    public void onBindViewHolder(LoanAnalyticsAdapter.LoanHolder holder, int position) {
        LoanMelaAnalyticsResponse.Success obj = mAnalyticsList.get(position);
        holder.reachd.setText(String.valueOf(obj.getReachedCount()));
        holder.going.setText(String.valueOf(obj.getGoingCount()));
        holder.ignore.setText(String.valueOf(obj.getIgnoreCount()));
        holder.goingstud.setText(String.valueOf(obj.getGoingStudent()));
        holder.goingselfemp.setText(String.valueOf(obj.getGoingSelfStudent()));
        holder.goingemp.setText(String.valueOf(obj.getGoingEmployee()));
        holder.ignorestud.setText(String.valueOf(obj.getIgnoreStudent()));
        holder.ignoreselfemp.setText(String.valueOf(obj.getIgnoreSelfStudent()));
        holder.ignoreemp.setText(String.valueOf(obj.getIgnoreEmployee()));

        /*holder.gotolive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("auction_id", auction_id);

                System.out.println("Analytics adpter in auction id=============================================================" + auction_id);

                AuctionFinancerPage fr = new AuctionFinancerPage();
                fr.setArguments(b);

                FragmentManager fragmentManager = ctx.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, fr);
                fragmentTransaction.addToBackStack("auctionfinancerpage");
                fragmentTransaction.commit();

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mAnalyticsList.size();
    }

    static class LoanHolder extends RecyclerView.ViewHolder {
        TextView reachd, going, ignore, share, goingstud, goingselfemp, goingemp, ignorestud, ignoreselfemp, ignoreemp;
        Button gotolive;

        LoanHolder(View itemView) {
            super(itemView);
            reachd = (TextView) itemView.findViewById(R.id.reached);
            going = (TextView) itemView.findViewById(R.id.goingcnt);
            ignore = (TextView) itemView.findViewById(R.id.ignore);
            share = (TextView) itemView.findViewById(R.id.share);
            goingstud = (TextView) itemView.findViewById(R.id.studcnt);
            goingselfemp = (TextView) itemView.findViewById(R.id.selfemp);
            goingemp = (TextView) itemView.findViewById(R.id.emp);
            ignorestud = (TextView) itemView.findViewById(R.id.studignorecnt);
            ignoreselfemp = (TextView) itemView.findViewById(R.id.selfempignore);
            ignoreemp = (TextView) itemView.findViewById(R.id.empignore);
            gotolive = (Button) itemView.findViewById(R.id.live);
        }
    }
}
