package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class OTP extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    TextView emailOtpText, mResendOtp;
    EditText OtpEdit, otpemail;
    String otpstr2;
    String contact, call;
    Button mSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        setTitle("Enter OTP");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        OtpEdit = (EditText) findViewById(R.id.edtotp1);
        otpemail = (EditText) findViewById(R.id.otpemail);

        mResendOtp = (TextView) findViewById(R.id.textView4);
        emailOtpText = (TextView) findViewById(R.id.emailOtpText);
        mSubmit = (Button) findViewById(R.id.submit_otp);
        mSubmit.setOnClickListener(this);

        Intent i = getIntent();
        contact = i.getStringExtra("contact");
        call = i.getStringExtra("call");
        sendOtp();
        mResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp();
            }
        });
    }

    /*
    Send OTP...
     */
    public void sendOtp() {
        ApiCall mApiCall = new ApiCall(OTP.this, this);
        mApiCall.getOTP(contact);
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Otp");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            otpstr2 = "" + str;
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_otp:
                String otpstr1 = OtpEdit.getText().toString();
                //otpstr2 = OtpText.getText().toString().trim();
                if (otpstr1.equals(otpstr2)) {
                    if (call.equalsIgnoreCase("forgot")) {
                        Intent i = new Intent(OTP.this, NewPassword.class);
                        i.putExtra("contact", contact);
                        startActivity(i);
                        finish();
                    }
                } else {
                    OtpEdit.setError("Please enter valid OTP");
                }
                break;
        }
    }

  /*  @Override
    public void onBackPressed() {
        if (call.equals("forgot")) {
            Intent i = new Intent(OTP.this, Forget_Password.class);
            startActivity(i);
            finish();
        }

    }*/

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

