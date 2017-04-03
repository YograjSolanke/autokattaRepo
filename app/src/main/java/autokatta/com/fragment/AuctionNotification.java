package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.auction.GoingFragment;
import autokatta.com.auction.LiveFragment;
import autokatta.com.auction.UpComingFragment;

/**
 * Created by ak-001 on 17/3/17.
 */

public class AuctionNotification extends Fragment {
    View mAuctionNotification;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuctionNotification = inflater.inflate(R.layout.fragment_auction_notification, container, false);

        ViewPager viewPager = (ViewPager) mAuctionNotification.findViewById(R.id.auction_view_pager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) mAuctionNotification.findViewById(R.id.auction_tab);
        tabLayout.setupWithViewPager(viewPager);
        return mAuctionNotification;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getActivity().getSupportFragmentManager());
        adapter.addFragment(new LiveFragment(), "LIVE");
        adapter.addFragment(new GoingFragment(), "GOING");
        adapter.addFragment(new UpComingFragment(), "UPCOMING");
        viewPager.setAdapter(adapter);
    }


}
