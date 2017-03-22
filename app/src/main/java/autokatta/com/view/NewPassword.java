package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class NewPassword extends AppCompatActivity implements View.OnClickListener,RequestNotifier {
    EditText mNewPass, mConfirmPass;
    Button mSubmit;
    String mContact;
    String StrNewPass, StrConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        mNewPass = (EditText) findViewById(R.id.editnewpass);
        mConfirmPass = (EditText) findViewById(R.id.confirmpass);
        mSubmit = (Button) findViewById(R.id.newsubmit);

        //get parameters from previous Activity
        Intent intent = getIntent();
        mContact = intent.getStringExtra("contact");

        mSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newsubmit:
                StrNewPass = mNewPass.getText().toString();
                StrConfirmPass = mConfirmPass.getText().toString();

                if (TextUtils.isEmpty(mNewPass.getText().toString().trim())) {
                    mNewPass.requestFocus();
                    mNewPass.setFocusable(true);
                    mNewPass.setError(getString(R.string.err_password));
                } else if (TextUtils.isEmpty(mConfirmPass.getText().toString().trim())) {
                    mConfirmPass.requestFocus();
                    mConfirmPass.setFocusable(true);
                    mConfirmPass.setError(getString(R.string.err_confirm_password));
                    {
                    }
                } else if (mNewPass.length() < 6) {
                    mNewPass.setError("Password should be minimum of 6 characters");
                } else if (!StrNewPass.equalsIgnoreCase(StrConfirmPass)) {
                    mConfirmPass.setError("Password & confirm password should be same");
                } else {

                    ApiCall mApiCall = new ApiCall(NewPassword.this, this);
                    mApiCall.newPassword(mContact, StrNewPass);
                }
                break;
        }
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
            Log.i("Check Class-", "New Password Activity");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            Log.i("String-----", "->" + str);
            if (str.equals("Success")) {
                Toast.makeText(getApplicationContext(), "Your password has been changed successfully ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NewPassword.this, LoginActivity.class);
                intent.putExtra("call", "forgot");
                startActivity(intent);
            }else {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }

    }
}

