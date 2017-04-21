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

/**
 * Created by ak-005 on 17/4/17.
 */

public class GroupDetailTabs extends Fragment {
    View mGroupDetail;
    MemberListFragment memberListFragment;
    GroupVehicleList groupVehicleList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupDetail = inflater.inflate(R.layout.fragment_group_details, container, false);
        Bundle b = new Bundle();
        b.putString("profile", "profile");
        memberListFragment = new MemberListFragment();
        memberListFragment.setArguments(b);
        groupVehicleList = new GroupVehicleList();
        groupVehicleList.setArguments(b);

        ViewPager mViewPager = (ViewPager) mGroupDetail.findViewById(R.id.groups_details_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mGroupDetail.findViewById(R.id.groups_details_tab);
        tabLayout.setupWithViewPager(mViewPager);

        return mGroupDetail;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getFragmentManager());
        tabAdapterName.addFragment(memberListFragment, "Group Members");
        tabAdapterName.addFragment(groupVehicleList, "Group Vehicles");
        mViewPager.setAdapter(tabAdapterName);
    }
}
