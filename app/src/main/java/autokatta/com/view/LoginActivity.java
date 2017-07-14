package autokatta.com.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.broadcastreceiver.BackgroundService;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.SessionManagement;
import autokatta.com.register.Registration;
import autokatta.com.response.LoginResponse;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RequestNotifier {

    EditText mUserName, mPassword;
    TextView mForgetPassword;
    SessionManagement session;
    CoordinatorLayout mLogin;
    String userName;
    String password;
    //KProgressHUD hud;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManagement(getApplicationContext());
        if (session.isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
        } else {
            mUserName = (EditText) findViewById(R.id.username);
            mLogin = (CoordinatorLayout) findViewById(R.id.login_layout);
            mPassword = (EditText) findViewById(R.id.password);
            mForgetPassword = (TextView) findViewById(R.id.forget_password);
            Button mLogin = (Button) findViewById(R.id.login);
            Button mRegistration = (Button) findViewById(R.id.register);
            mLogin.setOnClickListener(this);
            mRegistration.setOnClickListener(this);
            mForgetPassword.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;

            case R.id.register:
                startActivity(new Intent(LoginActivity.this, Registration.class));
                finish();
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
            /*hud = KProgressHUD.create(LoginActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setMaxProgress(100)
                    .show();*/
            pd = new ProgressDialog(LoginActivity.this, R.style.MyTheme);
            pd.setCancelable(false);
            pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pd.show();
            mApiCall.userLogin(userName, password);
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                //hud.dismiss();
                pd.hide();
                LoginResponse mLoginResponse = (LoginResponse) response.body();
                String myContact = mUserName.getText().toString();
                if (!mLoginResponse.getSuccess().isEmpty()) {
                    int id = mLoginResponse.getSuccess().get(0).getRegID();
                    Snackbar.make(mLogin, "Success", Snackbar.LENGTH_SHORT).show();
                    if (getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginregistrationid", null) == null) {
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("loginregistrationid", id).apply();
                    }

                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("loginContact", myContact).apply();
                    session.createLoginSession(userName, password);
                    Intent is = new Intent(LoginActivity.this, BackgroundService.class);
                    startService(is);
                    finish();
                    startActivity(new Intent(getApplicationContext(), AutokattaMainActivity.class));
                } else {
                    Snackbar.make(mLogin, "Username or Password is incorrect", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                //hud.dismiss();
                pd.hide();
                Snackbar.make(mLogin, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            //hud.dismiss();
            pd.hide();
            Snackbar.make(mLogin, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        //hud.dismiss();
        pd.hide();
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mLogin, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mLogin, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mLogin, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mLogin, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mLogin, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-", "Login Activity");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.alert_title));
        alert.setMessage(getString(R.string.alert_msg));
        alert.setIconAttribute(android.R.attr.alertDialogIcon);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        alert.create();
        alert.show();
    }
}
