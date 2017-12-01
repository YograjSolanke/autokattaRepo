package autokatta.com.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.TransferEnquiriesFragment;

/**
 * Created by ak-005 on 16/11/17.
 */

public class ManualEnquiryTabs extends android.support.v4.app.Fragment {

    View manualenquirytabs;
    ManualEnquiry manualEnquiry = new ManualEnquiry();
    TransferEnquiriesFragment transferEnquiriesFragment = new TransferEnquiriesFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        manualenquirytabs = inflater.inflate(R.layout.manual_enquiry_tabs, container, false);
        getActivity().setTitle("Manual Enquiry");
        ViewPager mviewPager = (ViewPager) manualenquirytabs.findViewById(R.id.manual_enquiry_viewpager);
        if (mviewPager != null) {
            setupViewPager(mviewPager);
        }
        TabLayout tabLayout = (TabLayout) manualenquirytabs.findViewById(R.id.manualenquiry_tabs);
        tabLayout.setupWithViewPager(mviewPager);
        Bundle bundle = getArguments();
        Bundle putBundle = new Bundle();
        putBundle.putInt("bundle_storeId", bundle.getInt("bundle_storeId", 0));
        putBundle.putString("bundle_contact", bundle.getString("bundle_contact", ""));
        manualEnquiry.setArguments(putBundle);
        transferEnquiriesFragment.setArguments(putBundle);
        return manualenquirytabs;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(manualEnquiry, "Manual Enquiry");
        tabAdapterName.addFragment(transferEnquiriesFragment, "Transfered Enquiries");
        viewPager.setAdapter(tabAdapterName);
    }
}
