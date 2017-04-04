package autokatta.com.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.GetLiveEventsResponse;

/**
 * Created by ak-001 on 4/4/17.
 */

public class AuctionNotificationAdapter extends RecyclerView.Adapter<AuctionNotificationAdapter.MyViewHolder> {

    Activity mActivity;
    private List<GetLiveEventsResponse.Success> mItemList = new ArrayList<>();
    private String auctionType;

    public AuctionNotificationAdapter(Activity mActivity, List<GetLiveEventsResponse.Success> mItemList, String auctionType) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.auctionType = auctionType;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mAuctionCardView;
        TextView mAuctioneer, mAuctioneerTitle, mAuctioneerNoOfVehicles, mAuctioneerType, mTimer, mStartDate, mStartTime,
                mEndDate, mEndTime;
        Button mSpecialClauses, mAuctionPreview, mShare;

        public MyViewHolder(View itemView) {
            super(itemView);
            mAuctionCardView = (CardView) itemView.findViewById(R.id.auction_card_view);
            mAuctioneer = (TextView) itemView.findViewById(R.id.auctioneer);
            mAuctioneerTitle = (TextView) itemView.findViewById(R.id.auctioneer_title);
            mAuctioneerNoOfVehicles = (TextView) itemView.findViewById(R.id.auctioneer_no_of_vehicles);
            mAuctioneerType = (TextView) itemView.findViewById(R.id.auctioneer_type);
            mTimer = (TextView) itemView.findViewById(R.id.timer);
            mStartDate = (TextView) itemView.findViewById(R.id.start_date);
            mStartTime = (TextView) itemView.findViewById(R.id.start_time);
            mEndDate = (TextView) itemView.findViewById(R.id.end_date);
            mEndTime = (TextView) itemView.findViewById(R.id.end_time);

            mSpecialClauses = (Button) itemView.findViewById(R.id.btnspecial_clauses);
            mAuctionPreview = (Button) itemView.findViewById(R.id.auction_preview);
            mShare = (Button) itemView.findViewById(R.id.auction_share);
        }
    }

    @Override
    public AuctionNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_auction, parent, false);
        MyViewHolder holder = new MyViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(AuctionNotificationAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
