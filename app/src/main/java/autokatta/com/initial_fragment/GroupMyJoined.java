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
import autokatta.com.groups.JoinedGroupsFragment;
import autokatta.com.groups.MyGroupsFragment;

/**
 * Created by ak-001 on 25/3/17.
 */

public class GroupMyJoined extends Fragment {
    View mGroupMyJoined;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupMyJoined = inflater.inflate(R.layout.fragment_group_my_joined, container, false);

        ViewPager mviewPager = (ViewPager) mGroupMyJoined.findViewById(R.id.activity_groups_tab_viewpager);
        if (mviewPager != null) {
            setupViewPager(mviewPager);
        }
        TabLayout tabLayout = (TabLayout) mGroupMyJoined.findViewById(R.id.activity_groups_tab);
        tabLayout.setupWithViewPager(mviewPager);
        return mGroupMyJoined;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getChildFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "My Groups");
        tabAdapterName.addFragment(new JoinedGroupsFragment(), "Joined Groups");
        viewPager.setAdapter(tabAdapterName);
    }
}
