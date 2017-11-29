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

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreEmployeeResponse;
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
    String name, contact, designation, description, permission, status;
    int store_id, emp_id;
    String keyword = "";
    String position;
    String contactFetched = "";
    StoreEmployeeResponse.Success.Employee employee;


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

                if (getIntent().getExtras() != null) {

                    if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("update")) {

                        // employee=(StoreEmployeeResponse.Success.Employee)getIntent().getSerializableExtra("EmployeeData");
                        empName.setText(getIntent().getExtras().getString("name", ""));
                        empName.setText(getIntent().getExtras().getString("name", ""));
                        empContact.setText(getIntent().getExtras().getString("contact", ""));
                        empDescription.setText(getIntent().getExtras().getString("description", ""));
                        empDesignation.setText(getIntent().getExtras().getString("designation", ""));
                        if (getIntent().getExtras().getString("permission", "").equalsIgnoreCase("yes")) {
                            permissionCheck.setChecked(true);
                        }

                        store_id = getIntent().getExtras().getInt("store_id", 0);
                        btnAdd.setText("update");
                        setTitle("Update Employee");
                    } else {

                        store_id = getIntent().getExtras().getInt("store_id", 0);
                    }

                    System.out.println("output=" + store_id);
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
                        checkUser(s.toString(), "begin");
                    } else if (empContact.getText().toString().equals(myContact)) {
                        empContact.setError("admin not allowed");
                        empContact.requestFocus();
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
                contactFetched = "";
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


    }

    private void checkUser(String contact, String keyword) {
        position = keyword;
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.registrationContactValidation(contact);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.addEmployee:

                name = empName.getText().toString();
                contact = empContact.getText().toString();
                designation = empDesignation.getText().toString();
                description = empDescription.getText().toString();
                if (permissionCheck.isChecked())
                    permission = "yes";
                else
                    permission = "no";
                status = "";

                empName.setError(null);
                empContact.setError(null);
                empDescription.setError(null);
                empDescription.setError(null);


                if (empName.getText().toString().isEmpty()) {
                    empName.setError("Please Enter Name");
                } else if (empContact.getText().toString().isEmpty()) {
                    empContact.setError("Please Enter Contact");
                } else if (empContact.getText().toString().equals(myContact)) {
                    empContact.setError("Admin Not Allowed");
                    empContact.requestFocus();
                } else if (empDesignation.getText().toString().isEmpty()) {
                    empDesignation.setError("Please Enter Designation");
                } else if (empDescription.getText().toString().isEmpty()) {
                    empDescription.setError("Please Enter Short Description");
                } else if (!contactFetched.equalsIgnoreCase("") && contactFetched.equals("Admin")) {
                    empContact.setError("Admin Not Allowed");
                    empContact.requestFocus();
                } else {

                    checkUser(contact, "end");
//                    if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("Add")) {
//                        mApiCall.AddEmloyeeInStore(name, contact, myContact, designation, store_id, description,
//                                status, permission);
//                    } else if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("update")) {
//
//                        emp_id = getIntent().getExtras().getInt("id", 0);
//
//                        mApiCall.updateDeleteEmployee(emp_id, name, contact, designation, description,
//                                permission, "Edit");
//                    }
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
                                    // Toast.makeText(AddEmployeeActivity.this, "Number=" + num, Toast.LENGTH_LONG).show();
                                    empContact.setText(num);
                                    if (num.equals(myContact)) {
                                        empContact.setError("Admin Not Allowed");
                                        contactFetched = "Admin";
                                        empContact.requestFocus();
                                    } else {
                                        contactFetched = "other";
                                        checkUser(num, "begin");
                                        empContact.setError(null);
                                    }
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

            System.out.println("output=" + str);
            if (str.equalsIgnoreCase("Success")) {

                if (position.equalsIgnoreCase("begin"))
                    txtUser.setVisibility(View.VISIBLE);
                else {
                    if (getIntent().getExtras() != null) {
                        if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("Add")) {
                            mApiCall.AddEmloyeeInStore(name, contact, myContact, designation, store_id, description,
                                    status, permission);
                        } else if (getIntent().getExtras().getString("keyword", "").equalsIgnoreCase("update")) {

                            emp_id = getIntent().getExtras().getInt("id", 0);

                            mApiCall.updateDeleteEmployee(emp_id, name, contact, designation, description,
                                    permission, "Edit");
                        }
                    }
                }

                //txtInvite.setVisibility(View.GONE);
            } else if (str.equals("success_request_sent")) {
                CustomToast.customToast(getApplicationContext(), "Request Sent");
                finish();
            } else if (str.equals("already_request_sent")) {
                CustomToast.customToast(getApplicationContext(), "Already Employee");
                //finish();
            } else if (str.equals("Success_Updated")) {
                CustomToast.customToast(getApplicationContext(), " Data Updated");
                finish();
            } else {
                empContact.setError("Not Autokatta User");
                empContact.requestFocus();
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
