package autokatta.com.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import autokatta.com.R;
import autokatta.com.view.BussinessChatActivity;
import autokatta.com.view.ManualEnquiryMainActivity;

public class EnquiryActivity extends AppCompatActivity implements View.OnClickListener {
    int mStoreID;
    String mBundleContact;
    RelativeLayout relativeBC, relativeTestDrive, relativeNewDealer, relativeManualEnquiry;
    TextView mManualCount;
    Bundle putBundle;

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

        putBundle = new Bundle();
        putBundle.putInt("bundle_storeId", mStoreID);
        putBundle.putString("bundle_contact", mBundleContact);
        relativeBC = (RelativeLayout) findViewById(R.id.relBC);
        relativeTestDrive = (RelativeLayout) findViewById(R.id.relTD);
        relativeNewDealer = (RelativeLayout) findViewById(R.id.relND);
        relativeManualEnquiry = (RelativeLayout) findViewById(R.id.relME);
        mManualCount = (TextView) findViewById(R.id.manual_count);
        relativeBC.setOnClickListener(this);
        relativeTestDrive.setOnClickListener(this);
        relativeNewDealer.setOnClickListener(this);
        relativeManualEnquiry.setOnClickListener(this);

        /*DbOperation operation;
        operation = new DbOperation(getApplicationContext());
        operation.OPEN();
        Cursor cursor = operation.getEnquiryCount();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            mManualCount.setText(cursor.getString(cursor.getColumnIndex(DbConstants.enq_val)));
            Log.i("dsafdsfads", "->" + cursor.getString(cursor.getColumnIndex(DbConstants.enq_val)));
        }
        operation.CLOSE();*/
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relBC:
                startActivity(new Intent(getApplicationContext(), BussinessChatActivity.class));
                break;
            case R.id.relTD:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.relND:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.relME:
                startActivity(new Intent(getApplicationContext(), ManualEnquiryMainActivity.class).putExtras(putBundle));
                break;
            case R.id.rel5:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rel6:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
