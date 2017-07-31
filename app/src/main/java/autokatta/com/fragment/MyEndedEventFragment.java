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
import autokatta.com.events.MyEndedAuctionFragment;
import autokatta.com.events.MyEndedExchangeMelaFragment;
import autokatta.com.events.MyEndedLoanMelaFragment;
import autokatta.com.events.MyEndedSaleMelaFragment;
import autokatta.com.events.MyEndedServiceMelaFragment;

/**
 * Created by ak-004 on 27/3/17.
 */

public class MyEndedEventFragment extends Fragment {
    View mEndedEvent;

    public MyEndedEventFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEndedEvent = inflater.inflate(R.layout.my_ended_event_fragment, container, false);

        ViewPager mViewPager = (ViewPager) mEndedEvent.findViewById(R.id.my_ended_event_tabs_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mEndedEvent.findViewById(R.id.my_ended_event_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return mEndedEvent;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(new MyEndedAuctionFragment(), "Auction");
        tabAdapterName.addFragment(new MyEndedLoanMelaFragment(), "Loan Mela");
        tabAdapterName.addFragment(new MyEndedExchangeMelaFragment(), "Exchange Mela");
        tabAdapterName.addFragment(new MyEndedServiceMelaFragment(), "Service Mela");
        tabAdapterName.addFragment(new MyEndedSaleMelaFragment(), "Sale Mela");
        mViewPager.setAdapter(tabAdapterName);
    }
}
