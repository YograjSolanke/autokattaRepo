package autokatta.com.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.JoinedGroupsFragment;
import autokatta.com.fragment.MyGroupsFragment;

public class GroupsTab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }


        ViewPager mviewPager = (ViewPager) findViewById(R.id.activity_groups_tab_viewpager);
        if (mviewPager != null) {
            setupViewPager(mviewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_groups_tab);
        tabLayout.setupWithViewPager(mviewPager);
        //        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
        //        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher)
    }

    private void setupViewPager(ViewPager viewPager) {
       /* TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "My Groups");
        tabAdapterName.addFragment(new JoinedGroupsFragment(), "Joined Groups");
        viewPager.setAdapter(tabAdapterName);*/
    }

}
