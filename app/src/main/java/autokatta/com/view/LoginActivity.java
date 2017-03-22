package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.SocketTimeoutException;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.LoginResponse;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RequestNotifier {

    EditText mUserName, mPassword;
    TextView mForgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserName = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mForgetPassword= (TextView) findViewById(R.id.forget_password);
        Button mLogin = (Button) findViewById(R.id.login);
        Button mRegistration = (Button) findViewById(R.id.register);
        mLogin.setOnClickListener(this);
        mRegistration.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                break;

            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;

            case R.id.forget_password:
                startActivity(new Intent(LoginActivity.this, Forget_Password.class));
                break;
        }
    }

    private void login() {
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mUserName.getText().toString().trim())) {
            mUserName.requestFocus();
            mUserName.setFocusable(true);
            mUserName.setError(getString(R.string.err_username));
        } else if (TextUtils.isEmpty(mPassword.getText().toString().trim())) {
            mPassword.requestFocus();
            mPassword.setFocusable(true);
            mPassword.setError(getString(R.string.err_password));
        } else {
            ApiCall mApiCall = new ApiCall(LoginActivity.this, this);
            mApiCall.userLogin(userName, password);
        }
    }
    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                LoginResponse mLoginResponse = (LoginResponse) response.body();
                String myContact = mUserName.getText().toString();
                if (mLoginResponse.getSuccess()!=null){
                    String id = mLoginResponse.getSuccess().get(0).getRegID();
                    Log.i("id","->"+id);
                    CustomToast.customToast(getApplicationContext(),"Login Successful");
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("loginContact", myContact).apply();
                    startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
                }else{
                    CustomToast.customToast(getApplicationContext(),mLoginResponse.getError().get(0));
                }
            }else{
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        }else{
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
        }
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
            Log.i("Check Class-", "Login Activity");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
