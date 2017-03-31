package autokatta.com.initial_fragment;

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
import autokatta.com.events.MyUpcomingAuctionFragment;
import autokatta.com.events.MyUpcomingExchangeMelaFragment;
import autokatta.com.events.MyUpcomingLoanMelaFragment;

/**
 * Created by ak-004 on 31/3/17.
 */

public class MyUpcomingEventTabFragment extends Fragment {
    ViewPager mViewPager;
    TabLayout mTabLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_upcoming_event_tabs, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.activity_myupcoming_event_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.activity_myupcoming_event_tab);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (mViewPager != null) {
                        setUpPager(mViewPager);
                    }


                    mTabLayout.setupWithViewPager(mViewPager);
                    //        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
                    //        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void setUpPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getActivity().getSupportFragmentManager());
        tabAdapterName.addFragment(new MyUpcomingAuctionFragment(), "Auction");
        tabAdapterName.addFragment(new MyUpcomingLoanMelaFragment(), "Loan Mela");
        tabAdapterName.addFragment(new MyUpcomingExchangeMelaFragment(), "Exchange Mela");

        viewPager.setAdapter(tabAdapterName);
    }


}