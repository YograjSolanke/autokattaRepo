package autokatta.com.register;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.view.LoginActivity;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, android.location.LocationListener, RequestNotifier, View.OnTouchListener {

    EditText personName, mobileNo, email, dateOfBirth, pincode, otherIndustry, otherCategory, password, confirmPassword;
    Spinner moduleSpinner, usertypeSpinner, industrySpinner;
    Button btnSubmit, btnClear;
    AutoCompleteTextView address;
    TextInputLayout otherIndustryLayout, otherCategoryLayout;
    ImageView clearDate;
    String namestr, contactstr, emailstr, DOBstr, pincodestr, passwordstr, confirmpassstr, addressstr, genderstr,
            profession, sub_profession, strIndustry;

    RelativeLayout mRegistration;
    LinearLayout mLinear, mButton;
    ScrollView mScrollView;
    Button mNext;
    String mSuccess = "";
    RadioButton rbtmale, rbtfemale;
    RadioGroup rg1;
    String[] MODULE = null;
    String[] INDUSTRY = null;
    ApiCall apiCall;
    GenericFunctions functions;
    List<String> resultList = new ArrayList<>();
    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        apiCall = new ApiCall(this, this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        personName = (EditText) findViewById(R.id.editPersonName);
        mobileNo = (EditText) findViewById(R.id.editMobileNo);
        email = (EditText) findViewById(R.id.editEmail);
        dateOfBirth = (EditText) findViewById(R.id.editdob);
        address = (AutoCompleteTextView) findViewById(R.id.editAddress);
        pincode = (EditText) findViewById(R.id.editPincode);
        otherIndustry = (EditText) findViewById(R.id.editOtherTypeIndustry);
        otherCategory = (EditText) findViewById(R.id.editOtherTypeCategory);
        password = (EditText) findViewById(R.id.editPassword);
        confirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        usertypeSpinner = (Spinner) findViewById(R.id.spinnerUsertype);
        industrySpinner = (Spinner) findViewById(R.id.spinnerindustry);
        moduleSpinner = (Spinner) findViewById(R.id.spinnerCategory);
        otherIndustryLayout = (TextInputLayout) findViewById(R.id.otherIndustryLayout);
        otherCategoryLayout = (TextInputLayout) findViewById(R.id.otherCategoryLayout);
        rbtmale = (RadioButton) findViewById(R.id.rbtmale);
        rbtfemale = (RadioButton) findViewById(R.id.rbtfemale);
        clearDate = (ImageView) findViewById(R.id.clearDate);

        mRegistration = (RelativeLayout) findViewById(R.id.registration);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mButton = (LinearLayout) findViewById(R.id.button);
        mNext = (Button) findViewById(R.id.next);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        rg1 = (RadioGroup) findViewById(R.id.radiogp1);
        functions = new GenericFunctions();

        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        btnClear = (Button) findViewById(R.id.btnclear);
        btnClear.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        clearDate.setOnClickListener(this);
        mNext.setOnClickListener(this);
        dateOfBirth.setInputType(InputType.TYPE_NULL);

        usertypeSpinner.setOnItemSelectedListener(this);
        industrySpinner.setOnItemSelectedListener(this);
        moduleSpinner.setOnItemSelectedListener(this);
        dateOfBirth.setOnTouchListener(this);
        showDatePicker();
        address.setAdapter(new GooglePlacesAdapter(Registration.this, R.layout.simple));
        apiCall.Categories("");
        apiCall.Industries();

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
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.next:
                contactstr = mobileNo.getText().toString().trim();
                if (TextUtils.isEmpty(mobileNo.getText().toString().trim())) {
                    mobileNo.setError("Enter Contact No");
                    mobileNo.requestFocus();
                    mobileNo.setFocusable(true);
                } else if (!(contactstr.length() == 10)) {
                    mobileNo.setError("not valid");
                    mobileNo.requestFocus();
                    mobileNo.setFocusable(true);
                } else {
                    apiCall.registrationContactValidation(contactstr);
                }
                break;

            case R.id.btnsubmit:
                Boolean flag = false;
                namestr = personName.getText().toString().trim();
                emailstr = email.getText().toString().trim();
                DOBstr = dateOfBirth.getText().toString().trim();
                addressstr = address.getText().toString().trim();
                pincodestr = pincode.getText().toString().trim();
                passwordstr = password.getText().toString().trim();
                confirmpassstr = confirmPassword.getText().toString().trim();
                genderstr = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString().trim();

                profession = usertypeSpinner.getSelectedItem().toString().trim();
                strIndustry = industrySpinner.getSelectedItem().toString().trim();
                sub_profession = moduleSpinner.getSelectedItem().toString().trim();

                if (!addressstr.isEmpty()) {
                    resultList = GooglePlacesAdapter.getResultList();
                    for (int i = 0; i < resultList.size(); i++) {
                        if (addressstr.equalsIgnoreCase(resultList.get(i))) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                }

                if (namestr.isEmpty()) {
                    personName.setError("Enter User Name");
                    personName.requestFocus();
                    personName.setFocusable(true);
                } else if (emailstr.isEmpty()) {
                    email.setError("Enter Email ID");
                    email.requestFocus();
                    email.setFocusable(true);
                } else if (!functions.isValidEmail(emailstr)) {
                    // flag=false;
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                    email.setFocusable(true);
                } else if (DOBstr.isEmpty()) {
                    dateOfBirth.setError("Enter Date Of Birth");
                    dateOfBirth.requestFocus();
                    dateOfBirth.setFocusable(true);
                } else if (!functions.getbirthdate(DOBstr)) {
                    dateOfBirth.setError("Minimum 8 Year Age Required");
                    dateOfBirth.requestFocus();
                    dateOfBirth.setFocusable(true);
                } else if (addressstr.isEmpty()) {
                    address.setError("Enter Address");
                    address.setFocusable(true);
                } else if (!flag) {
                    address.setError("Please Select Address From Dropdown Only");
                    address.requestFocus();
                    address.setFocusable(true);
                } else if (pincodestr.isEmpty()) {
                    pincode.setError("Enter Pincode");
                    pincode.requestFocus();
                    pincode.setFocusable(true);
                } else if (passwordstr.isEmpty()) {
                    password.setError("Enter Password");
                } else if (passwordstr.length() < 6) {
                    password.setError("Password should be minimum 6 characters");
                    password.requestFocus();
                } else if (confirmpassstr.isEmpty()) {
                    confirmPassword.setError("Enter Password to confirm");
                    confirmPassword.requestFocus();
                } else if (!passwordstr.equals(confirmpassstr)) {
                    confirmPassword.setError("Confirm password is wrong");
                    confirmPassword.requestFocus();
                } else {
                    apiCall.registrationAfterOtp(namestr, contactstr, emailstr, DOBstr, genderstr, pincodestr, addressstr, profession, passwordstr, sub_profession, strIndustry);
                }

                break;

            case R.id.btnclear:
                personName.setText("");
                mobileNo.setText("");
                mobileNo.clearFocus();
                email.setText("");
                email.clearFocus();
                dateOfBirth.setText("");
                dateOfBirth.clearFocus();
                address.setText("");
                address.clearFocus();
                pincode.setText("");
                pincode.clearFocus();
                otherIndustry.setText("");
                otherIndustry.clearFocus();
                password.setText("");
                password.clearFocus();
                confirmPassword.setText("");
                confirmPassword.clearFocus();
                industrySpinner.clearFocus();
                usertypeSpinner.clearFocus();

                usertypeSpinner.setSelection(0);
                industrySpinner.setSelection(0);
                moduleSpinner.setSelection(0);

                personName.setError(null);
                mobileNo.setError(null);
                email.setError(null);
                dateOfBirth.setError(null);
                address.setError(null);
                pincode.setError(null);
                otherIndustry.setError(null);
                password.setError(null);
                confirmPassword.setError(null);
                industrySpinner.setVisibility(View.GONE);
                break;

            case R.id.clearDate:

                dateOfBirth.setText(null);
                dateOfBirth.clearFocus();
                dateOfBirth.setError(null);
                clearDate.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case (R.id.spinnerUsertype):
                // profession = usertypeSpinner.getSelectedItem().toString();
                if (!usertypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Student")
                        && !usertypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Select User Type")) {
                    // otherCategorylayout.setVisibility(View.VISIBLE);
                    industrySpinner.setVisibility(View.VISIBLE);
                } else {
//                        otherCategorylayout.setVisibility(View.GONE);
                    industrySpinner.setVisibility(View.GONE);

                    if (otherCategoryLayout.getVisibility() == View.VISIBLE) {
                        otherCategoryLayout.setVisibility(View.GONE);
                    }
                    if (otherIndustryLayout.getVisibility() == View.VISIBLE) {
                        otherIndustryLayout.setVisibility(View.GONE);
                    }
                }
                break;
            case (R.id.spinnerindustry):
                // strIndustry = industrySpinner.getSelectedItem().toString();
                if (industrySpinner.getSelectedItem().toString().equalsIgnoreCase("Automotive and vehicle manufacturing")) {
                    // otherCmoduleSpinner = (Spinner) findViewById(R.id.spinner_cou);ategorylayout.setVisibility(View.VISIBLE);
                    moduleSpinner.setVisibility(View.VISIBLE);
                    otherIndustryLayout.setVisibility(View.GONE);
                } else if (industrySpinner.getSelectedItem().toString().equalsIgnoreCase("Other")) {
//                        otherCategorylayout.setVisibility(View.GONE);
                    otherIndustryLayout.setVisibility(View.VISIBLE);
                    moduleSpinner.setVisibility(View.GONE);
                } else {
                    moduleSpinner.setVisibility(View.GONE);
                    otherIndustryLayout.setVisibility(View.GONE);
                }

                break;
            case (R.id.spinnerCategory):
                // sub_profession = moduleSpinner.getSelectedItem().toString();
                if (moduleSpinner.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    otherCategoryLayout.setVisibility(View.VISIBLE);
                } else {
                    otherCategoryLayout.setVisibility(View.GONE);
                }

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof CategoryResponse) {
                    CategoryResponse moduleResponse = (CategoryResponse) response.body();
                    final List<String> module = new ArrayList<>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        module.add("Select Category");
                        for (CategoryResponse.Success message : moduleResponse.getSuccess()) {
                            module.add(message.getTitle());
                        }
                        module.add("Other");
                        MODULE = new String[module.size()];
                        MODULE = (String[]) module.toArray(MODULE);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, MODULE);
                        moduleSpinner.setAdapter(dataadapter);
                    }
                } else if (response.body() instanceof IndustryResponse) {
                    IndustryResponse moduleResponse = (IndustryResponse) response.body();
                    final List<String> module = new ArrayList<>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        module.add("Select Industry");
                        for (IndustryResponse.Success message : moduleResponse.getSuccess()) {
                            module.add(message.getIndusName());
                        }
                        module.add("Other");
                        INDUSTRY = new String[module.size()];
                        INDUSTRY = (String[]) module.toArray(INDUSTRY);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, INDUSTRY);
                        industrySpinner.setAdapter(dataadapter);
                    }
                }
            } else {
                CustomToast.customToast(getApplicationContext(),getString(R.string._404_));
            }
        } else {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", " Registration");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        mSuccess = str;
        if (str != null) {
            if (str.equalsIgnoreCase("Success")) {
                getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("loginregistrationid", str).apply();
                    CustomToast.customToast(getApplicationContext(),"Already Registered Please Login");
                Intent i = new Intent(Registration.this, LoginActivity.class);
                startActivity(i);
                finish();
            } else if (str.equalsIgnoreCase("Fail")) {
                mScrollView.setVisibility(View.VISIBLE);
                mLinear.setVisibility(View.GONE);
                mButton.setVisibility(View.VISIBLE);
            } else {
                getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("loginregistrationid", str).apply();
                CustomToast.customToast(getApplicationContext()," Registered Successfully");
                Intent i = new Intent(Registration.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        } else {
            CustomToast.customToast(getApplicationContext(),getString(R.string.no_response));
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.editdob:
                dateOfBirth.setInputType(InputType.TYPE_NULL);
                datePicker.show();
                dateOfBirth.setError(null);
                break;
        }
        return false;
    }

    private void showDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (dayOfMonth < 10) {
                    if ((monthOfYear + 1) < 11) {
                        if ((monthOfYear + 1) == 10) {
                            dateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            clearDate.setVisibility(View.VISIBLE);
                        } else if (monthOfYear < 10) {
                            dateOfBirth.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                            clearDate.setVisibility(View.VISIBLE);
                        }
                    } else {
                        dateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        clearDate.setVisibility(View.VISIBLE);
                    }
                } else if (dayOfMonth >= 10) {
                    if ((monthOfYear + 1) < 11) {
                        if ((monthOfYear + 1) == 10) {
                            dateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            clearDate.setVisibility(View.VISIBLE);
                        } else if (monthOfYear < 10) {
                            dateOfBirth.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            clearDate.setVisibility(View.VISIBLE);
                        }
                    } else {
                        dateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        clearDate.setVisibility(View.VISIBLE);
                    }
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(Registration.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class), options.toBundle());
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }
}

