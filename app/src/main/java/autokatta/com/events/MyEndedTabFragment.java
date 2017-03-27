package autokatta.com.events;

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
import autokatta.com.fragment.MyEndedEventFragment;
import autokatta.com.fragment.MyParticipatedEndedFragment;

/**
 * Created by ak-004 on 27/3/17.
 */

public class MyEndedTabFragment extends Fragment {

    View mEndedEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEndedEvent = inflater.inflate(R.layout.ended_event_fragment, container, false);

        ViewPager mViewPager = (ViewPager) mEndedEvent.findViewById(R.id.ended_event_tabs_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mEndedEvent.findViewById(R.id.ended_event_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return mEndedEvent;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getFragmentManager());
        tabAdapterName.addFragment(new MyEndedEventFragment(), "My Ended Events");
        tabAdapterName.addFragment(new MyParticipatedEndedFragment(), "My Participated Ended");
        mViewPager.setAdapter(tabAdapterName);
    }
}
