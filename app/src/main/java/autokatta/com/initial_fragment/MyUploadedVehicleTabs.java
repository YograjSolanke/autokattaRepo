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
import autokatta.com.enquiries.AllEnquiryTabFragment;
import autokatta.com.fragment.BuyerNotificationFragment;

/**
 * Created by ak-001 on 25/4/17.
 */

public class MyUploadedVehicleTabs extends Fragment {
    View mUploadedTabs;

    public MyUploadedVehicleTabs() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mUploadedTabs = inflater.inflate(R.layout.fragment_social_fragment, container, false);
        ViewPager viewPager = (ViewPager) mUploadedTabs.findViewById(R.id.social_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) mUploadedTabs.findViewById(R.id.social_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return mUploadedTabs;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getChildFragmentManager());
        adapter.addFragment(new MyUploadedVehiclesFragment(), "Vehicles");
        adapter.addFragment(new BuyerNotificationFragment(), "Products");
        adapter.addFragment(new AllEnquiryTabFragment(), "Services");
        viewPager.setAdapter(adapter);
    }
}
