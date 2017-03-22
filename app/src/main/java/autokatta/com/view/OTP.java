package autokatta.com.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class OTP extends AppCompatActivity implements RequestNotifier,View.OnClickListener {

    TextView OtpText, emailOtpText;
    EditText OtpEdit, otpemail;
    String otpstr2;
    String  contact, email, call;
    Button mSubmit;

    Editor editor;
    SharedPreferences prefs;
    public static final String MyProfilePREFERENCES = "profilePrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        prefs = this.getSharedPreferences(MyProfilePREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();

        OtpEdit = (EditText) findViewById(R.id.edtotp1);
        otpemail = (EditText) findViewById(R.id.otpemail);

        OtpText = (TextView) findViewById(R.id.otp2);
        emailOtpText = (TextView) findViewById(R.id.emailOtpText);
        mSubmit = (Button) findViewById(R.id.submit_otp);
        mSubmit.setOnClickListener(this);


        Intent i = getIntent();
        contact = i.getStringExtra("contact");
       call = i.getStringExtra("call");

        System.out.println("text contact=" + contact + call + email);

            ApiCall mApiCall = new ApiCall(OTP.this, this);
            mApiCall.getOTP(contact);

    }

        @Override
        public void notifySuccess (Response < ? > response){

        }

        @Override
        public void notifyError (Throwable error){
            if (error instanceof SocketTimeoutException) {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            } else if (error instanceof NullPointerException) {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            } else if (error instanceof ClassCastException) {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            } else {
                Log.i("Check Class-", "Forget Password Activity");
            }
        }

        @Override
        public void notifyString (String str){
            if (str != null) {
              OtpText.setText(str);
             otpstr2 = "" + str;

            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }

        }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.submit_otp:
                String otpstr1 = OtpEdit.getText().toString();
                otpstr2 = OtpText.getText().toString().trim();
                if (otpstr1.equals(otpstr2)) {
                    if (call.equalsIgnoreCase("forgot")) {

                        Intent i = new Intent(OTP.this, NewPassword.class);
                        i.putExtra("contact", contact);
                        startActivity(i);
                    }
                } else {
                    OtpEdit.setError("Please enter valid OTP");

                }

                break;
        }
    }

        @Override
        public void onBackPressed ()
        {
            if (call.equals("forgot"))
            {
                Intent i = new Intent(OTP.this,Forget_Password.class);
                startActivity(i);
                OTP.this.finish();
            }

        }
    }

