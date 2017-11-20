package autokatta.com.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import autokatta.com.R;
import autokatta.com.enquiries.AllEnquiryTabFragment;

public class EnquiryActivity extends AppCompatActivity {

    int mStoreID;
    String mBundleContact;
    Bundle mBundle;
    AllEnquiryTabFragment allEnquiryTabFragment = new AllEnquiryTabFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);

        setTitle("Enquiries");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            mStoreID = getIntent().getExtras().getInt("bundle_storeId", 0);
            mBundleContact = getIntent().getExtras().getString("bundle_contact", "");
        }

        mBundle = new Bundle();
        mBundle.putInt("bundle_storeId", mStoreID);
        mBundle.putString("bundle_contact", mBundleContact);
        allEnquiryTabFragment.setArguments(mBundle);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.enquiriesFrame, allEnquiryTabFragment, "enquiriesFrame")
                .commit();
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
