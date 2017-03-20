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

public class BrowseStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ViewPager browseViewPager = (ViewPager) findViewById(R.id.browse_store_viewpager);
        if (browseViewPager != null) {
            //custom method
            setupViewPager(browseViewPager);
        }

        TabLayout browseTab = (TabLayout) findViewById(R.id.browse_store_tab);
        browseTab.setupWithViewPager(browseViewPager);
//        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);
    }

    private void setupViewPager(ViewPager viewPager) {

        TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(new MyGroupsFragment(), "Product Based");
        tabAdapterName.addFragment(new JoinedGroupsFragment(), "Service Based");

        viewPager.setAdapter(tabAdapterName);
    }

}
