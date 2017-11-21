package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.app_info.ManualAppIntro;
import autokatta.com.enquiries.TodaysFollowUp;

public class ManualEnquiryMainActivity extends AppCompatActivity {

    Bundle mBundle;
    String Sharedcontact;


    ManualEnquiryTabs manualEnquiryTabs = new ManualEnquiryTabs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enquiry_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        setTitle("Manual Enquiry");
        startActivity(new Intent(getApplicationContext(), ManualAppIntro.class));


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    //    getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                }

                try {


                    Sharedcontact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                    if (getIntent().getExtras() != null) {
                        mBundle = new Bundle();
                        mBundle.putInt("bundle_storeId", getIntent().getExtras().getInt("bundle_storeId", 0));
                        mBundle.putString("bundle_contact", getIntent().getExtras().getString("bundle_contact", Sharedcontact));
                        manualEnquiryTabs.setArguments(mBundle);


                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.manual_enquiry_container, manualEnquiryTabs, "manualenquiry")
                            .addToBackStack("manualenquiry")
                            .commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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
              /*  if (mRecyclerView.getVisibility() == View.GONE) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    getManualData();
                } else {
                    onBackPressed();
                }*/
                return true;

            case R.id.add_manual:
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getApplicationContext(), AddManualEnquiry.class), options.toBundle());
                return true;

            case R.id.today_follow_up:
                startActivity(new Intent(getApplicationContext(), TodaysFollowUp.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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
