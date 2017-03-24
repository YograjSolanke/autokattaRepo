package autokatta.com.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.GroupVehicleList;
import autokatta.com.fragment.JoinedGroupsFragment;
import autokatta.com.fragment.MyGroupsFragment;

public class GroupNextTab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_next_tab);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.groups_next_tab_viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.groups_next_tab);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager mViewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "Group Members");
        tabAdapterName.addFragment(new GroupVehicleList(), "Group Vehicles");
        mViewPager.setAdapter(tabAdapterName);
    }
}
