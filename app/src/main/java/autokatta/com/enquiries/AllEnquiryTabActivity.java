package autokatta.com.enquiries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;

public class AllEnquiryTabActivity extends AppCompatActivity {

    int mStoreID;
    String mBundleContact;
    Bundle mBundle;
    AllEnquiryTabFragment allEnquiryTabFragment = new AllEnquiryTabFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_enquiry_tab);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getIntent().getExtras() != null) {
                    mStoreID = getIntent().getExtras().getInt("bundle_storeId", 0);
                    mBundleContact = getIntent().getExtras().getString("bundle_contact", "");
                }

                mBundle = new Bundle();
                mBundle.putInt("bundle_storeId", mStoreID);
                mBundle.putString("bundle_contact", mBundleContact);
                allEnquiryTabFragment.setArguments(mBundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.all_enquiry, allEnquiryTabFragment, "allEnquiryTab")
                        .commit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
