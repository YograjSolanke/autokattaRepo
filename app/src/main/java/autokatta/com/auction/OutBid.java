package autokatta.com.auction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 6/4/17.
 */

public class OutBid extends Fragment {
    View mOutBid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mOutBid = inflater.inflate(R.layout.fragment_all_bid_items, container, false);
        return mOutBid;
    }
}
