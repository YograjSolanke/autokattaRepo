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
import autokatta.com.response.AuctionAnalyticsResponse;

/**
 * Created by ak-003 on 7/4/17.
 */

public class AuctionAnalyticsAdapter extends RecyclerView.Adapter<AuctionAnalyticsAdapter.AuctionHolder> {

    Activity mActivity;
    private int strAuctionId = 0;
    private List<AuctionAnalyticsResponse.Success> mAnalyticsList;

    public AuctionAnalyticsAdapter(FragmentActivity activity, int strAuctionid1, List<AuctionAnalyticsResponse.Success> analyticsList) {
        mActivity = activity;
        strAuctionId = strAuctionid1;
        mAnalyticsList = analyticsList;
    }

    @Override
    public AuctionAnalyticsAdapter.AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_auction_analytics, parent, false);
        return new AuctionHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionAnalyticsAdapter.AuctionHolder holder, int position) {
        AuctionAnalyticsResponse.Success obj = mAnalyticsList.get(position);
        holder.reachd.setText(obj.getReachedCount());
        holder.going.setText(obj.getGoingCount());
        holder.ignore.setText(obj.getIgnoreCount());
        holder.share.setText(obj.getSharedCount());
        holder.goingstud.setText(obj.getGoingStudent());
        holder.goingselfemp.setText(obj.getGoingSelfStudent());
        holder.goingemp.setText(obj.getGoingEmployee());
        holder.ignorestud.setText(obj.getIgnoreStudent());
        holder.ignoreselfemp.setText(obj.getIgnoreSelfStudent());
        holder.ignoreemp.setText(obj.getIgnoreEmployee());

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

    static class AuctionHolder extends RecyclerView.ViewHolder {
        TextView reachd, going, ignore, share, goingstud, goingselfemp, goingemp, ignorestud, ignoreselfemp, ignoreemp;
        Button gotolive;

        AuctionHolder(View itemView) {
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
