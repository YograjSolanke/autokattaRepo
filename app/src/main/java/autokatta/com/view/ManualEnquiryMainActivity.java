package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.adapter.TabAdapterName;
import autokatta.com.enquiries.TodaysFollowUp;
import autokatta.com.fragment.TransferEnquiriesFragment;

public class ManualEnquiryMainActivity extends AppCompatActivity {
    Bundle mBundle;
    String Sharedcontact;
    ManualEnquiry manualEnquiry;
    TransferEnquiriesFragment transferEnquiriesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enquiry_main);
        setTitle("Manual Enquiry");
        //startActivity(new Intent(getApplicationContext(), ManualAppIntro.class));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                try {
                    manualEnquiry = new ManualEnquiry();
                    transferEnquiriesFragment = new TransferEnquiriesFragment();
                    Sharedcontact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                    ViewPager mviewPager = (ViewPager) findViewById(R.id.manual_enquiry_viewpager);
                    if (mviewPager != null) {
                        setupViewPager(mviewPager);
                    }
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.manualenquiry_tabs);
                    tabLayout.setupWithViewPager(mviewPager);
                    if (getIntent().getExtras() != null) {
                        mBundle = new Bundle();
                        mBundle.putInt("bundle_storeId", getIntent().getExtras().getInt("bundle_storeId", 0));
                        mBundle.putString("bundle_contact", getIntent().getExtras().getString("bundle_contact", Sharedcontact));
                        manualEnquiry.setArguments(mBundle);
                        transferEnquiriesFragment.setArguments(mBundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterName tabAdapterName = new TabAdapterName(getSupportFragmentManager());
        tabAdapterName.addFragment(manualEnquiry, "Manual Enquiry");
        tabAdapterName.addFragment(transferEnquiriesFragment, "Transfered Enquiries");
        viewPager.setAdapter(tabAdapterName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manual_add, menu);
        if (getIntent().getExtras().getInt("bundle_storeId", 0) != 0) {
            MenuItem item = menu.findItem(R.id.add_manual);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.add_manual:
                startActivity(new Intent(getApplicationContext(), AddManualEnquiry.class));
                return true;

            case R.id.today_follow_up:
                startActivity(new Intent(getApplicationContext(), TodaysFollowUp.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

}
