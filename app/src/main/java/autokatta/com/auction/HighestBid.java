package autokatta.com.auction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 6/4/17.
 */

public class HighestBid extends Fragment {
    View mHighestBid;
    String auctionId, showPrice, openClose;
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHighestBid = inflater.inflate(R.layout.fragment_all_bid_items, container, false);

        Bundle b = getArguments();
        auctionId = b.getString("auction_id");
        openClose = b.getString("openClose");
        showPrice = b.getString("showPrice");

        mRecyclerView = (RecyclerView) mHighestBid.findViewById(R.id.all_bids);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getYourBidData();
            }
        });
        return mHighestBid;
    }

    /*
    Get Bid Data...
     */
    private void getYourBidData() {

    }
}
