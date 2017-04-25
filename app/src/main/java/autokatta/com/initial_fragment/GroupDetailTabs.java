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
import autokatta.com.groups.GroupCommunicationFragment;
import autokatta.com.groups.GroupProductsFragment;
import autokatta.com.groups.GroupServicesFragment;
import autokatta.com.groups.GroupVehicleList;
import autokatta.com.groups.MemberListFragment;

/**
 * Created by ak-005 on 17/4/17.
 */

public class GroupDetailTabs extends Fragment {
    View mGroupDetail;
    GroupCommunicationFragment communicationListFragment;
    GroupProductsFragment productListFragment;
    GroupServicesFragment serviceListFragment;
    MemberListFragment memberListFragment;
    GroupVehicleList groupVehicleList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupDetail = inflater.inflate(R.layout.fragment_group_details, container, false);
        Bundle b = new Bundle();
        Bundle b1 = getArguments();

        if (b1 != null) {
            b1.getString("grouptype");
            b.putString("grouptype", b1.getString("grouptype"));
            b.putString("className", b1.getString("className"));
        }/* else {
            b.putString("grouptype", "profile");
        }*/
        memberListFragment = new MemberListFragment();
        memberListFragment.setArguments(b);

        groupVehicleList = new GroupVehicleList();
        groupVehicleList.setArguments(b);

        communicationListFragment = new GroupCommunicationFragment();
        communicationListFragment.setArguments(b);

        productListFragment = new GroupProductsFragment();
        productListFragment.setArguments(b);

        serviceListFragment = new GroupServicesFragment();
        serviceListFragment.setArguments(b);

        ViewPager mViewPager = (ViewPager) mGroupDetail.findViewById(R.id.groups_details_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mGroupDetail.findViewById(R.id.groups_details_tab);
        tabLayout.setupWithViewPager(mViewPager);

        return mGroupDetail;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getActivity().getSupportFragmentManager());
        tabAdapterName.addFragment(communicationListFragment, "Communication");
        tabAdapterName.addFragment(memberListFragment, "Group Members");
        tabAdapterName.addFragment(groupVehicleList, "Group Vehicles");
        tabAdapterName.addFragment(productListFragment, "Products");
        tabAdapterName.addFragment(serviceListFragment, "Services");
        mViewPager.setAdapter(tabAdapterName);
    }
}
