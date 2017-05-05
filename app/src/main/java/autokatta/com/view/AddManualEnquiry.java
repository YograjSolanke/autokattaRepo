package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

public class AddManualEnquiry extends AppCompatActivity implements RequestNotifier, View.OnTouchListener {

    EditText edtName, edtContact, edtAddress, edtDiscussion, edtDate, edtTime;
    AutoCompleteTextView autoAddress;
    Spinner spnInventory, spnStatus;
    String myContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manual_enquiry);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392");
        edtName = (EditText) findViewById(R.id.edtName);
        edtContact = (EditText) findViewById(R.id.edtContact);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtDiscussion = (EditText) findViewById(R.id.edtDiscussion);
        autoAddress = (AutoCompleteTextView) findViewById(R.id.autoAddress);
        spnInventory = (Spinner) findViewById(R.id.spnInventory);
        spnStatus = (Spinner) findViewById(R.id.spnStatus);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtTime = (EditText) findViewById(R.id.edtTime);

        edtDate.setOnTouchListener(this);
        edtTime.setOnTouchListener(this);
        autoAddress.setAdapter(new GooglePlacesAdapter(AddManualEnquiry.this, R.layout.registration_spinner));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_manual_enquiry, menu);
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
                break;

            case R.id.ok_manual:
                String custInventoryType = "", custEnquiryStatus = "";
                int strPos = spnInventory.getSelectedItemPosition();
                int strPos1 = spnStatus.getSelectedItemPosition();

                String custName = edtName.getText().toString();
                String custContact = edtContact.getText().toString();
                String custAddress = autoAddress.getText().toString();
                String custFullAddress = edtAddress.getText().toString();

                if (strPos != 0)
                    custInventoryType = spnInventory.getSelectedItem().toString();

                if (strPos1 != 0)
                    custEnquiryStatus = spnStatus.getSelectedItem().toString();


                String discussion = edtDiscussion.getText().toString();
                String nextFollowupDate = edtDate.getText().toString() + " " + edtTime.getText().toString();

                AddEnquiryData(custName, custContact, custAddress, custFullAddress, custInventoryType, custEnquiryStatus, discussion, nextFollowupDate);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getApplicationContext(), ManualEnquiry.class), options.toBundle());
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void AddEnquiryData(String custName, String custContact, String custAddress, String custFullAddress,
                                String custInventoryType, String custEnquiryStatus, String discussion,
                                String nextFollowupDate) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addManualEnquiryData(myContact, custName, custContact, custAddress, custFullAddress, custInventoryType,
                custEnquiryStatus, discussion, nextFollowupDate);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (view.getId()) {

            case (R.id.edtDate):

                if (action == MotionEvent.ACTION_DOWN) {
                    edtDate.setInputType(InputType.TYPE_NULL);
                    edtDate.setError(null);
                    new SetMyDateAndTime("date", edtDate, this);
                }
                break;

            case (R.id.edtTime):

                if (action == MotionEvent.ACTION_DOWN) {
                    edtTime.setInputType(InputType.TYPE_NULL);
                    edtTime.setError(null);
                    new SetMyDateAndTime("time", edtTime, this);
                }
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.left_to_right, R.anim.right_to_left);
            startActivity(new Intent(getApplicationContext(), ManualEnquiry.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), ManualEnquiry.class));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "Add Manual Enquiry");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
