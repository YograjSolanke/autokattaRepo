package autokatta.com.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class AddEmployeeActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ApiCall mApiCall;
    String myContact;
    EditText empName, empContact, empDescription, empDesignation;
    CheckBox permissionCheck;
    Button btnAdd;
    ImageView imgContact;
    TextView txtUser;


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
        imgContact = (ImageView) findViewById(R.id.contact_list);
        empContact = (EditText) findViewById(R.id.empContact);
        empDesignation = (EditText) findViewById(R.id.empDesignation);
        empDescription = (EditText) findViewById(R.id.empDescription);
        btnAdd = (Button) findViewById(R.id.addEmployee);
        txtUser = (TextView) findViewById(R.id.txtUser);

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
                txtUser.setVisibility(View.GONE);

                if (s.length() == 10) {
                    if (!myContact.equalsIgnoreCase(s.toString())) {
                        checkUser(s.toString());
                    } else if (empContact.equals(myContact)) {
                        empContact.requestFocus();
                        empContact.setError("admin not allowed");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                txtUser.setVisibility(View.GONE);
            }
        });

        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(AddEmployeeActivity.this, R.anim.left_to_right, R.anim.right_to_left);
                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", empContact.getText().toString());
                Intent intent = new Intent(AddEmployeeActivity.this, OtherProfile.class);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
            }
        });

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


    }

    private void checkUser(String contact) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.registrationContactValidation(contact);
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
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri contactData = data.getData();
                        Cursor c = getContentResolver().query(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                while (numbers.moveToNext()) {
                                    num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    num = num.replaceAll("-", "");
                                    num = num.replace("(", "").replace(")", "").replaceAll(" ", "").replaceAll("[\\D]", "");

                                    if (num.length() > 10)
                                        num = num.substring(num.length() - 10);
                                    Toast.makeText(AddEmployeeActivity.this, "Number=" + num, Toast.LENGTH_LONG).show();
                                    empContact.setText(num);
                                    checkUser(num);
                                }
                            }
                        }
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
        if (str != null) {
            if (str.equalsIgnoreCase("Success")) {
                txtUser.setVisibility(View.VISIBLE);
                //txtInvite.setVisibility(View.GONE);

            } else {
                empContact.setError("Not Autokatta User");
                txtUser.setVisibility(View.GONE);
            }
        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));

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
