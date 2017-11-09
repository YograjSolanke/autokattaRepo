package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

public class AddEmployeeActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ApiCall mApiCall;
    String myContact;
    EditText empName, empContact, empDescription, empDesignation;
    CheckBox permissionCheck;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Employee");

        mApiCall = new ApiCall(AddEmployeeActivity.this, this);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        permissionCheck = (CheckBox) findViewById(R.id.permissionCheck);
        empName = (EditText) findViewById(R.id.empName);
        empContact = (EditText) findViewById(R.id.empContact);
        empDesignation = (EditText) findViewById(R.id.empDesignation);
        empDescription = (EditText) findViewById(R.id.empDescription);
        btnAdd = (Button) findViewById(R.id.addEmployee);

        btnAdd.setOnClickListener(this);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

            }
        });

        empContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length() == 10) {
                    if (!myContact.equalsIgnoreCase(s.toString())) {
                        // checkUser(s.toString());
                    } else {
                        empContact.requestFocus();
                        empContact.setError("admin not allowed");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.addEmployee:
                if (empName.getText().toString().isEmpty()) {
                    empName.setError("Please Enter Name");
                } else if (empContact.getText().toString().isEmpty()) {
                    empContact.setError("Please Enter Contact");
                } else if (empDesignation.getText().toString().isEmpty()) {
                    empDesignation.setError("Please Enter Designation");
                } else if (empDescription.getText().toString().isEmpty()) {
                    empDescription.setError("Please Enter Short Description");
                } else {

                }


                break;


        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

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
