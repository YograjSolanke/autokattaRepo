package autokatta.com.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;

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
    SharedPreferences mSharedPreferences = null;
    SharedPreferences.Editor mEditor;
    private ProgressDialog dialog;
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSharedPreferences = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        setTitle("Login");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");

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
    protected void onResume() {
        super.onResume();
        if (mSharedPreferences.getBoolean("mChooseLanguage", true)) {
            openDialog();
            mEditor = mSharedPreferences.edit();
            mEditor.putBoolean("mChooseLanguage", false);
            mEditor.apply();
        } else {
            Log.i("Language", "->" + myLocale);
        }
    }

    private void openDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.change_language_title, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autokatta");
        builder.setIcon(R.drawable.logo48x48);
        builder.setView(view);

        //final TextView mMarathi = (TextView) view.findViewById(R.id.marathi);
        //final TextView mEnglish = (TextView) view.findViewById(R.id.english);

        RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.myRadioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.english) {
                    setLocale("en");
                } else if (checkedId == R.id.marathi) {
                    setLocale("mr");
                } else if (checkedId == R.id.hindi) {
                    setLocale("hi");
                }
            }
        });
        /*mMarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMarathiStr = mMarathi.getText().toString();
                mMarathi.setBackgroundColor(Color.parseColor("#f7f7f7"));
                setLocale("mr");
            }
        });

        mEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEnglishStr = mEnglish.getText().toString();
                mEnglish.setBackgroundColor(Color.parseColor("#f7f7f7"));
                setLocale("en");
            }
        });*/

        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent refresh = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(refresh);
                        finish();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setLocale(String language) {
        myLocale = new Locale(language);
        saveLocale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = myLocale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void saveLocale(String language) {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Language", language);
        editor.apply();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myLocale != null) {
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            getResources().updateConfiguration(newConfig, getResources().getDisplayMetrics());
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
            dialog.show();
            mApiCall.userLogin(userName, password);
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {
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
                    Snackbar.make(mLogin, "Invalid Username or Password", Snackbar.LENGTH_SHORT).show();
                }
            } else {

                Snackbar.make(mLogin, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mLogin, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
