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
 * Created by ak-001 on 25/3/17.
 */

public class GroupNextTabFragment extends Fragment {
    View mGroupNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupNext = inflater.inflate(R.layout.fragment_group_next_tab, container, false);

        ViewPager mViewPager = (ViewPager) mGroupNext.findViewById(R.id.groups_next_tab_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) mGroupNext.findViewById(R.id.groups_next_tab);
        tabLayout.setupWithViewPager(mViewPager);

        return mGroupNext;
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getFragmentManager());
        tabAdapterName.addFragment(new MemberListFragment(), "Group Members");
        tabAdapterName.addFragment(new GroupVehicleList(), "Group Vehicles");
        mViewPager.setAdapter(tabAdapterName);
    }
}
