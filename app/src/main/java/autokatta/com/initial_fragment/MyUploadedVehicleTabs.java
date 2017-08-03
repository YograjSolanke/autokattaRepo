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
import autokatta.com.inventory_catalog.InventoryProductsFragment;
import autokatta.com.inventory_catalog.InventoryServicesFragment;

/**
 * Created by ak-001 on 25/4/17.
 */

public class MyUploadedVehicleTabs extends Fragment {
    View mUploadedTabs;
    TabLayout tabLayout;

    public MyUploadedVehicleTabs() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mUploadedTabs = inflater.inflate(R.layout.fragment_my_uploaded_vehicle, container, false);
        ViewPager viewPager = (ViewPager) mUploadedTabs.findViewById(R.id.social_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        tabLayout = (TabLayout) mUploadedTabs.findViewById(R.id.social_tabs);
        tabLayout.setupWithViewPager(viewPager);
        //setupTabIcons();
        return mUploadedTabs;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName adapter = new TabAdapterName(getChildFragmentManager());
        adapter.addFragment(new InventoryProductsFragment(), "Products");
        adapter.addFragment(new InventoryServicesFragment(), "Services");
        adapter.addFragment(new MyUploadedVehiclesFragment(), "Used Vehicle");
        adapter.addFragment(new NewVehicle(), "New Vehicle");
        //adapter.addFragment(new SoldVehicle(), "Sold Vehicle");
        viewPager.setAdapter(adapter);
    }

    /*private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_custom_demo, null);
        tabOne.setText("Products");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_custom_demo, null);
        tabTwo.setText("Services");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_custom_demo, null);
        tabThree.setText("Used Vehicle");
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabfour = (TextView) LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tab_custom_demo, null);
        tabfour.setText("New Vehicle");
        tabLayout.getTabAt(3).setCustomView(tabfour);
    }*/
}
