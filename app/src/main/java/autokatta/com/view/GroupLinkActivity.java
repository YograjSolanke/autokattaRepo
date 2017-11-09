package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

/* This activity is called on the click of invitation url to join the group */
public class GroupLinkActivity extends AppCompatActivity implements View.OnClickListener, RequestNotifier {
    String mBundleContact;
    int mGroupID;
    Button mBtnJoion, mBtnCancel;
    private String mLoginContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_link);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Group Link Requests");

        mLoginContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        mBtnJoion = (Button) findViewById(R.id.btnJoin);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);

        mBtnJoion.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            mGroupID = getIntent().getExtras().getInt("bundle_GroupId", 0);
            mBundleContact = getIntent().getExtras().getString("bundle_Contact", "");
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
            case R.id.btnJoin:
                acceptGroupRequest();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void acceptGroupRequest() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addContactInGroup(mGroupID, mLoginContact);
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
                    , "GroupLinkActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("you_are_added")) {
                CustomToast.customToast(this, "you joined the group");
            } else if (str.equalsIgnoreCase("you_are_already_in_group")) {
                CustomToast.customToast(this, "you are already in group");
            }
            finish();
        }
        CustomToast.customToast(this, getString(R.string.no_response));
    }
}
