package autokatta.com.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.register.Registration;
import retrofit2.Response;

public class Forget_Password extends AppCompatActivity implements View.OnClickListener, RequestNotifier {

    EditText edtContact;
    String contact;
    Button submit, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        edtContact = (EditText) findViewById(R.id.edit_contact);
        clear = (Button) findViewById(R.id.clear);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                forgetPassword();
                break;
            case R.id.clear:
                edtContact.setText("");
        }
    }

    private void forgetPassword() {
        contact = edtContact.getText().toString().trim();
        if (TextUtils.isEmpty(edtContact.getText().toString().trim())) {
            edtContact.requestFocus();
            edtContact.setFocusable(true);
            edtContact.setError(getString(R.string.err_mobile));
        } else {
            ApiCall mApiCall = new ApiCall(Forget_Password.this, this);
            mApiCall.forgetPassword(contact);
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "S" + response.body(), Toast.LENGTH_SHORT).show();
            }
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
            Log.i("Check Class-", "Forget Password Activity");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("Success")) {
                Log.i("String", "->" + str);
                CustomToast.customToast(getApplicationContext(), "You are a valid user");
                Intent i = new Intent(Forget_Password.this, OTP.class);
                i.putExtra("contact", contact);
                i.putExtra("call", "forgot");
                startActivity(i);
                finish();
            } else {
                CustomToast.customToast(getApplicationContext(), "You are not a valid user");
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                // alertDialog.setTitle("Upload Vehicle");
                alertDialog.setMessage("You are not registered user. Do you want to register now?");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Forget_Password.this, Registration.class);
                        startActivity(i);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Forget_Password.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
