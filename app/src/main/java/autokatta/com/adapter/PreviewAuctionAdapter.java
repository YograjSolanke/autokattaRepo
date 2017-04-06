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
import autokatta.com.response.GetAuctionEventResponse;

/**
 * Created by ak-001 on 5/4/17.
 */

public class PreviewAuctionAdapter extends RecyclerView.Adapter<PreviewAuctionAdapter.MyViewHolder> {
    Activity mActivity;
    private List<GetAuctionEventResponse.Vehicle> mItemList = new ArrayList<>();

    public PreviewAuctionAdapter(Activity mActivity, List<GetAuctionEventResponse.Vehicle> mItemList) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mAuctionCardView;
        TextView mRegistrationNo, mAuctioneer, mAuctioneerTitle, mAuctioneerNoOfVehicles, mAuctioneerType, mTimer, mStartDate, mStartTime,
                mEndDate, mEndTime;
        Button mSpecialClauses, mAuctionPreview, mShare;

        MyViewHolder(View itemView) {
            super(itemView);
            mAuctionCardView = (CardView) itemView.findViewById(R.id.auction_card_view);
            mRegistrationNo = (TextView) itemView.findViewById(R.id.live_registration_no_txt);
        }
    }

    @Override
    public PreviewAuctionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_preview_live_event_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PreviewAuctionAdapter.MyViewHolder vh = new PreviewAuctionAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PreviewAuctionAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
