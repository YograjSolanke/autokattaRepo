package autokatta.com.notifications;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class NotificationAddEmployeeActivity extends AppCompatActivity implements View.OnClickListener, RequestNotifier {

    String mBundleContact;
    String strMessage, strName, strBody;
    Button mBtnApprove, mBtnReject;
    private String mLoginContact;
    TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_add_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Employee Requests");

        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        mBtnApprove = (Button) findViewById(R.id.btnApprove);
        mBtnReject = (Button) findViewById(R.id.btnReject);
        mMessage = (TextView) findViewById(R.id.message);

        mBtnApprove.setOnClickListener(this);
        mBtnReject.setOnClickListener(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            strMessage = getIntent().getExtras().getString("like", "");
            strName = getIntent().getExtras().getString("name", "");
            strBody = getIntent().getExtras().getString("body", "");
            mBundleContact = getIntent().getExtras().getString("firebaseContact", "");

            mMessage.setText(strName + " " + strBody);
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnApprove:
                acceptOrReject("Accept");
                break;
            case R.id.btnReject:
                acceptOrReject("Reject");
                break;
        }
    }

    private void acceptOrReject(String reject) {
        ApiCall mApiCall = new ApiCall(this, this);
        //mApiCall.AddContactForPublicGroup(mGroupID, mLoginContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "NotificationAddEmployeeActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("you are added")) {
                CustomToast.customToast(this, "you joined the group");
                finish();
            } else if (str.equalsIgnoreCase("you are already in group")) {
                CustomToast.customToast(this, "you are already in group");
                finish();
            }

        } else
            CustomToast.customToast(this, getString(R.string.no_response));
    }
}
