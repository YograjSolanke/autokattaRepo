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

        //showMessage();
        return manualenquirytabs;
    }



    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(new ManualEnquiry(), "Manual Enquiry");
        tabAdapterName.addFragment(new TransferEnquiriesFragment(), "Transfered Enquiries");
        viewPager.setAdapter(tabAdapterName);
    }
}
