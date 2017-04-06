package autokatta.com.auction;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;

/**
 * Created by ak-001 on 6/4/17.
 */

public class BidRecyclerAdapter extends RecyclerView.Adapter<BidRecyclerAdapter.MyViewHolder> {

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mRegistrationNo, mLotNo, mTitle, mBrand, mModel, mYear, mKms, mRc, mLocation, mRtoCity, mBidPrice,
                mBidReceive, mBidStatus, mStartPrice, mBidIncrement;
        ImageView mImageView;
        Button mBid;

        MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.your_bid_card_view);

            mRegistrationNo = (TextView) itemView.findViewById(R.id.your_bid_registration_txt);
            mLotNo = (TextView) itemView.findViewById(R.id.your_bid_setlotno);
            mTitle = (TextView) itemView.findViewById(R.id.your_bid_name);
            mBrand = (TextView) itemView.findViewById(R.id.your_bid_brand);
            mModel = (TextView) itemView.findViewById(R.id.your_bid_model);
            mYear = (TextView) itemView.findViewById(R.id.your_bid_year);
            mKms = (TextView) itemView.findViewById(R.id.your_bid_kms_hrs);
            mRc = (TextView) itemView.findViewById(R.id.your_bid_rc);
            mLocation = (TextView) itemView.findViewById(R.id.your_bid_locations);
            mRtoCity = (TextView) itemView.findViewById(R.id.your_bid_rto_city);
            mBidPrice = (TextView) itemView.findViewById(R.id.current_bid_price);
            mBidReceive = (TextView) itemView.findViewById(R.id.bidrecieved);
            mBidStatus = (TextView) itemView.findViewById(R.id.bidderstatus);
            mStartPrice = (TextView) itemView.findViewById(R.id.startprice);
            mBidIncrement = (TextView) itemView.findViewById(R.id.bidincreament);

            mImageView = (ImageView) itemView.findViewById(R.id.your_bid_vehicle_image);
            mBid = (Button) itemView.findViewById(R.id.bid);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_your_bid_recycle_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        BidRecyclerAdapter.MyViewHolder vh = new BidRecyclerAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
