package autokatta.com.apicall;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.fragment.StoreInfo;
import autokatta.com.fragment.StoreProducts;
import autokatta.com.fragment.StoreServices;
import autokatta.com.fragment.StoreVehicles;
import autokatta.com.my_store.MyStoreHome;

public class DeleteActivity extends AppCompatActivity {

    MyStoreHome myStoreHome;
    StoreInfo storeInfo;
    StoreProducts storeProducts;
    StoreServices storeServices;
    StoreVehicles storeVehicles;
    Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        // Locate the viewpager in activity_main.xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        mBundle.putString("store_id", "346");

        myStoreHome = new MyStoreHome();
        storeInfo = new StoreInfo();
        storeInfo.setArguments(mBundle);
        storeProducts = new StoreProducts();
        storeProducts.setArguments(mBundle);
        storeServices = new StoreServices();
        storeServices.setArguments(mBundle);
        storeVehicles = new StoreVehicles();
        storeVehicles.setArguments(mBundle);

        // Set the ViewPagerAdapter into ViewPager
        TabAdapterName adapter = new TabAdapterName(getSupportFragmentManager());
        adapter.addFragment(new MyStoreHome(), "");
        adapter.addFragment(storeInfo, "About");
        adapter.addFragment(storeProducts, "Products");
        adapter.addFragment(storeServices, "Services");
        adapter.addFragment(storeVehicles, "Vehicles");

        viewPager.setAdapter(adapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.pager_header);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.getTabAt(0).setIcon(R.mipmap.ic_web);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
   /*class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/
}
