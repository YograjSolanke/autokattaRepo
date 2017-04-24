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
import autokatta.com.events.MyActiveAuctionFragment;
import autokatta.com.events.MyActiveExchangeMelaFrament;
import autokatta.com.events.MyActiveLoanMelaFragment;
import autokatta.com.events.MyActiveSaleMelaFragment;
import autokatta.com.events.MyActiveServiceFragment;

/**
 * Created by ak-004 on 30/3/17.
 */

public class MyActiveEventsTabFragment extends Fragment {
    ViewPager mViewPager;
    TabLayout mTabLayout;

    public MyActiveEventsTabFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_active_events_tab, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.activity_myactive_event_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.activity_myactive_event_tab);


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
        tabAdapterName.addFragment(new MyActiveAuctionFragment(), "Auction");
        tabAdapterName.addFragment(new MyActiveLoanMelaFragment(), "Loan Mela");
        tabAdapterName.addFragment(new MyActiveExchangeMelaFrament(), "Exchange Mela");
        tabAdapterName.addFragment(new MyActiveServiceFragment(), "Service ");
        tabAdapterName.addFragment(new MyActiveSaleMelaFragment(), "Sale Mela");

        viewPager.setAdapter(tabAdapterName);
    }
}

