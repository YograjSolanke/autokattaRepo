package autokatta.com.view;

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
import autokatta.com.fragment.BussinessChatFragment;
import autokatta.com.fragment.VehicleOfferRecived;

/**
 * Created by ak-005 on 27/9/17.
 */

public class BussinessChatTabs extends Fragment {

    View bussinesschattabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bussinesschattabs = inflater.inflate(R.layout.bussiness_chat_tabs, container, false);
        getActivity().setTitle("Bussiness Chat");
        ViewPager mviewPager = (ViewPager) bussinesschattabs.findViewById(R.id.bussiness_chat_details_viewpager);
        if (mviewPager != null) {
            setupViewPager(mviewPager);
        }
        TabLayout tabLayout = (TabLayout) bussinesschattabs.findViewById(R.id.Bussiness_chat_details_tab);
        tabLayout.setupWithViewPager(mviewPager);
        //showMessage();
        return bussinesschattabs;
    }



    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(new BussinessChatFragment(), "Bussiness Chat");
        tabAdapterName.addFragment(new VehicleOfferRecived(), "Offer Recived");
        viewPager.setAdapter(tabAdapterName);
    }
}
