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
import android.widget.Toast;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class OTP extends AppCompatActivity implements RequestNotifier,View.OnClickListener {

    TextView OtpText, emailOtpText;
    EditText OtpEdit, otpemail;
    String otpstr2, msg;
    String username, contact, email, city, profession, sub_profession, password, dob, gender, industry,
            pincode, call;
    String reg_id;
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
        username = i.getStringExtra("username");
        contact = i.getStringExtra("contact");
        email = i.getStringExtra("email");
        dob = i.getStringExtra("dob");
        gender = i.getStringExtra("gender");

        pincode = i.getStringExtra("pincode");
        city = i.getStringExtra("city");

        profession = i.getStringExtra("profession");
        sub_profession = i.getStringExtra("sub_profession");
        industry = i.getStringExtra("industry");
        password = i.getStringExtra("password");
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
                    Log.i("String", "->" + str);
                    {
                        OtpText.setText(str);
                        otpstr2 = "" + str;
                    }

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
                String otpemailvalue = otpemail.getText().toString();
                String otpemailpass = emailOtpText.getText().toString();
                if (otpstr1.equals(otpstr2)) {
                    if (call.equalsIgnoreCase("forgot")) {

                        Intent back = new Intent(OTP.this, NewPassword.class);
                        back.putExtra("contact", contact);
                        startActivity(back);
                    } else if (call.equalsIgnoreCase("register")) {
                        if (otpemailvalue.equalsIgnoreCase(otpemailpass)) {
                            ApiCall mApiCall = new ApiCall(OTP.this, this);
                            mApiCall.registrationAfterOtp(contact,username,email,dob,gender,pincode,city,profession,password,sub_profession,industry);
                            Toast.makeText(getApplicationContext(), "to do",
                                    Toast.LENGTH_LONG).show();
                            //  Registration();
                            /// new Registrationtask().execute();
                        } else {
                            otpemail.setError("Please enter valid OTP");
                        }

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
            else {
                Intent i = new Intent(OTP.this, RegistrationActivity.class);
                i.putExtra("username", username);
                i.putExtra("contact", contact);
                i.putExtra("email", email);
                i.putExtra("pincode", pincode);
                i.putExtra("city", city);
                i.putExtra("password", password);
                i.putExtra("dob", dob);
                i.putExtra("gender", gender);

                startActivity(i);
                OTP.this.finish();
            }
        }
    }

