package autokatta.com.auction;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.response.YourBidResponse;

/**
 * Created by ak-001 on 6/4/17.
 */

public class BidRecyclerAdapter extends RecyclerView.Adapter<BidRecyclerAdapter.MyViewHolder> {

    private Activity mActivity;
    private List<YourBidResponse.Success> mItemList = new ArrayList<>();
    private String auctionId, openClose, showPrice;

    public BidRecyclerAdapter(Activity mActivity, List<YourBidResponse.Success> mItemList, String auctionId, String openClose,
                              String showPrice) {
        this.mActivity = mActivity;
        this.mItemList = mItemList;
        this.auctionId = auctionId;
        this.openClose = openClose;
        this.showPrice = showPrice;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mRegistrationNo, mLotNo, mTitle, mBrand, mModel, mYear, mKms, mRc, mLocation, mRtoCity, mBidPrice,
                mBidReceive, mBidStatus, mStartPrice, mBidIncrement, mStartPriceText;
        ImageView mImageView;
        Button mBid;

        MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.your_bid_card_view);

            mRegistrationNo = (TextView) itemView.findViewById(R.id.your_bid_registration_txt);
            //mLotNo = (TextView) itemView.findViewById(R.id.your_bid_setlotno);
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
            mStartPriceText = (TextView) itemView.findViewById(R.id.startpricetxt);

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
        holder.mRegistrationNo.setText(mItemList.get(position).getRegNo());
        //holder.mLotNo.setText(mItemList.get(position).getLotNo());
        holder.mTitle.setText(mItemList.get(position).getTitle());
        holder.mBrand.setText(mItemList.get(position).getBrand());
        holder.mModel.setText(mItemList.get(position).getModel());
        holder.mYear.setText(mItemList.get(position).getYear());
        holder.mKms.setText(mItemList.get(position).getKmsRunning());
        holder.mRc.setText(mItemList.get(position).getRcAvailable());
        holder.mLocation.setText(mItemList.get(position).getLocationCity());
        holder.mRtoCity.setText(mItemList.get(position).getRtoCity());
        holder.mBidPrice.setText(mItemList.get(position).getCurrentBidPrice());
        holder.mBidReceive.setText(mItemList.get(position).getBidReceivedPrice());
        holder.mStartPrice.setText(mItemList.get(position).getStartPrice());
        holder.mBidIncrement.setText("000000Rs");

        holder.mBidStatus.setVisibility(View.VISIBLE);
        holder.mBidStatus.setText("You are not highest bidder for this vehicle");

        if (mItemList.get(position).getKmsRunning() != null || !mItemList.get(position).getKmsRunning().equals("")) {
            holder.mKms.setText(mItemList.get(position).getKmsRunning() + " " + "Kms");
        } else {
            holder.mKms.setText(mItemList.get(position).getKmsRunning() + " " + "Hrs");
        }


        if (openClose.equalsIgnoreCase("Open")) {
            holder.mBidReceive.setVisibility(View.VISIBLE);
            if (mItemList.get(position).getBidReceivedPrice() != null || !mItemList.get(position).getBidReceivedPrice().isEmpty()
                    || !mItemList.get(position).getBidReceivedPrice().equals("0") || !mItemList.get(position).getBidReceivedPrice().equals("null"))
                holder.mBidReceive.setText("Bid received Rs. " + mItemList.get(position).getBidReceivedPrice());
            else
                holder.mBidReceive.setText("Bid not received yet");
        } else
            holder.mBidReceive.setVisibility(View.GONE);


        if (showPrice.equalsIgnoreCase("Show")) {
            holder.mStartPrice.setVisibility(View.VISIBLE);
            holder.mStartPriceText.setVisibility(View.VISIBLE);
            holder.mStartPrice.setText("Rs." + " " + mItemList.get(position).getStartPrice());
        } else {
            holder.mStartPrice.setVisibility(View.GONE);
            holder.mStartPriceText.setVisibility(View.GONE);
        }

        holder.mBidPrice.setVisibility(View.VISIBLE);
        holder.mBidPrice.setText("Your current bid price Rs." + " " + mItemList.get(position).getCurrentBidPrice());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
