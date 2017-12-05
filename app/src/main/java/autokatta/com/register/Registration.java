package autokatta.com.register;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.app_info.GradientBackgroundExampleActivity;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.GenerateOtpResponse;
import autokatta.com.response.GetUserCategoryResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.view.LoginActivity;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, android.location.LocationListener, RequestNotifier, View.OnTouchListener {

    EditText personName, mobileNo, email, dateOfBirth, pincode, otherIndustry, otherCategory, otherbrand, password,
            confirmPassword, edtotp1;
    Spinner moduleSpinner, usertypeSpinner, industrySpinner, brandSpinner;
    Button btnSubmit, btnClear;
    AutoCompleteTextView address;
    TextInputLayout otherIndustryLayout, otherCategoryLayout, otherbrandlayout;
    ImageView clearDate;
    String namestr, contactstr, emailstr, DOBstr, pincodestr, passwordstr, confirmpassstr, addressstr, genderstr,
            profession = "", sub_profession = "", strIndustry = "", brand = "";

    RelativeLayout mRegistration;
    LinearLayout mLinear, mButton, mOtpLayout;
    ScrollView mScrollView;
    Button mNext, submit_otp;
    String mSuccess = "";
    RadioButton rbtmale, rbtfemale;
    RadioGroup rg1;
    String[] MODULE = null;
    String[] INDUSTRY = null;
    String[] BRAND = null;
    ApiCall apiCall;
    GenericFunctions functions;
    List<String> resultList = new ArrayList<>();
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;
    private DatePickerDialog datePicker;
    TextView termsOfAgreement;
    CheckBox mCheckBox;
    String otpstr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        apiCall = new ApiCall(this, this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        sharedPreferences = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        termsOfAgreement = (TextView) findViewById(R.id.termsOfAgreement);

        Spannable span = Spannable.Factory.getInstance().newSpannable("I agree to the Terms of Agreement.");
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TermsConditions.class));
            }
        }, 15, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsOfAgreement.setText(span);
        termsOfAgreement.setMovementMethod(LinkMovementMethod.getInstance());

        personName = (EditText) findViewById(R.id.editPersonName);
        mobileNo = (EditText) findViewById(R.id.editMobileNo);
        email = (EditText) findViewById(R.id.editEmail);
        dateOfBirth = (EditText) findViewById(R.id.editdob);
        address = (AutoCompleteTextView) findViewById(R.id.editAddress);
        pincode = (EditText) findViewById(R.id.editPincode);
        otherIndustry = (EditText) findViewById(R.id.editOtherTypeIndustry);
        otherCategory = (EditText) findViewById(R.id.editOtherTypeCategory);
        otherbrand = (EditText) findViewById(R.id.editOtherBrand);
        password = (EditText) findViewById(R.id.editPassword);
        edtotp1 = (EditText) findViewById(R.id.edtotp1);
        confirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        usertypeSpinner = (Spinner) findViewById(R.id.spinnerUsertype);
        industrySpinner = (Spinner) findViewById(R.id.spinnerindustry);
        moduleSpinner = (Spinner) findViewById(R.id.spinnerCategory);
        brandSpinner = (Spinner) findViewById(R.id.spinnerbrand);
        otherIndustryLayout = (TextInputLayout) findViewById(R.id.otherIndustryLayout);
        otherCategoryLayout = (TextInputLayout) findViewById(R.id.otherCategoryLayout);
        otherbrandlayout = (TextInputLayout) findViewById(R.id.otherbrand);
        rbtmale = (RadioButton) findViewById(R.id.rbtmale);
        rbtfemale = (RadioButton) findViewById(R.id.rbtfemale);
        clearDate = (ImageView) findViewById(R.id.clearDate);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);

        mRegistration = (RelativeLayout) findViewById(R.id.registration);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mButton = (LinearLayout) findViewById(R.id.button);
        mOtpLayout = (LinearLayout) findViewById(R.id.otp_layout);
        mNext = (Button) findViewById(R.id.next);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        rg1 = (RadioGroup) findViewById(R.id.radiogp1);
        functions = new GenericFunctions();

        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        submit_otp = (Button) findViewById(R.id.submit_otp);
        btnClear = (Button) findViewById(R.id.btnclear);
        btnClear.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        submit_otp.setOnClickListener(this);
        clearDate.setOnClickListener(this);
        mNext.setOnClickListener(this);
        dateOfBirth.setInputType(InputType.TYPE_NULL);

        usertypeSpinner.setOnItemSelectedListener(this);
        industrySpinner.setOnItemSelectedListener(this);
        moduleSpinner.setOnItemSelectedListener(this);
        brandSpinner.setOnItemSelectedListener(this);
        dateOfBirth.setOnTouchListener(this);
        showDatePicker();
        address.setAdapter(new GooglePlacesAdapter(Registration.this, R.layout.simple));
        apiCall.getUserCategories();
        apiCall.Industries();
        apiCall.getBrandTags("both");
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
                if (!profession.equalsIgnoreCase("Student")) {
                    strIndustry = industrySpinner.getSelectedItem().toString().trim();
                }
                if (moduleSpinner.getVisibility() == View.VISIBLE)
                    sub_profession = moduleSpinner.getSelectedItem().toString().trim();

                if (brandSpinner.getVisibility() == View.VISIBLE)
                    brand = brandSpinner.getSelectedItem().toString().trim();

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
                } else if (profession.equalsIgnoreCase("Select User Type")) {
                    Toast.makeText(getApplicationContext(), "Please select User type", Toast.LENGTH_LONG).show();
                } else if ((!profession.equalsIgnoreCase("Student") && (strIndustry.equalsIgnoreCase("") || strIndustry.equalsIgnoreCase("Select Industry")))) {
                    Toast.makeText(Registration.this, "Please select industry", Toast.LENGTH_LONG).show();
                } else if (strIndustry.equalsIgnoreCase("Other") && otherIndustry.getText().toString().trim().equalsIgnoreCase("")) {
                    otherIndustry.setError("Enter Industry");
                } else if (strIndustry.equalsIgnoreCase("Other") && !otherIndustry.getText().toString().matches("[a-zA-Z ]*")) {
                    otherIndustry.setError("Enter  Valid Industry");
                }// else if (strIndustry.equalsIgnoreCase("Automotive and vehicle manufacturing") && sub_profession.equalsIgnoreCase("Select Category")) {
                else if (strIndustry.equalsIgnoreCase("Automotive") && sub_profession.equalsIgnoreCase("Select Category")) {
                    Toast.makeText(Registration.this, "Please select category", Toast.LENGTH_LONG).show();
                } else if (sub_profession.equalsIgnoreCase("other") && otherCategory.getText().toString().equalsIgnoreCase("")) {
                    otherCategory.setError("Enter Profession");
                } else if (sub_profession.equalsIgnoreCase("other") && !otherCategory.getText().toString().matches("[a-zA-Z ]*")) {
                    otherCategory.setError("Enter  Valid Profession");
                } else if ((sub_profession.startsWith("New vehicle") || sub_profession.startsWith("Used vehicle")) && brand.equalsIgnoreCase("-Select Brand-")) {
                    Toast.makeText(Registration.this, "Please select Brand", Toast.LENGTH_LONG).show();
                } else if (brand.equalsIgnoreCase("other") && otherbrand.getText().toString().equalsIgnoreCase("")) {
                    otherbrand.setError("Enter Brand");
                } else if (brand.equalsIgnoreCase("other") && !otherbrand.getText().toString().matches("[a-zA-Z ]*")) {
                    otherbrand.setError("Enter  Valid Brand");
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
                } else if (sub_profession.equalsIgnoreCase("Other")) {
                    sub_profession = otherCategory.getText().toString().trim();
                    apiCall.addOtheruserCategory(sub_profession);
                } else if (brand.equalsIgnoreCase("Other")) {
                    brand = otherbrand.getText().toString().trim();
                    apiCall.addOtherBrandTags(brand, "both");
                } else if (strIndustry.equalsIgnoreCase("Other")) {
                    strIndustry = otherIndustry.getText().toString().trim();
                    apiCall.addOtherIndustry(strIndustry);
                } else if (sub_profession.equalsIgnoreCase("Select Category")) {
                    sub_profession = "";
                } else if (brand.equalsIgnoreCase("-Select Brand")) {
                    brand = "";
                } else if (strIndustry.equalsIgnoreCase("Select Industry")) {
                    strIndustry = "";
                } else if (!mCheckBox.isChecked()) {
                    CustomToast.customToast(getApplicationContext(), "Please accept terms and conditions");
                } else {
                    apiCall.registrationAfterOtp(namestr, contactstr, emailstr, DOBstr, genderstr, pincodestr, addressstr, profession, passwordstr, sub_profession, strIndustry, brand);
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
                brandSpinner.setSelection(0);
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
                brandSpinner.setVisibility(View.GONE);
                break;

            case R.id.clearDate:
                dateOfBirth.setText(null);
                dateOfBirth.clearFocus();
                dateOfBirth.setError(null);
                clearDate.setVisibility(View.GONE);
                break;

            case R.id.submit_otp:
                String otpstr1 = edtotp1.getText().toString();
                if (otpstr1.equals(otpstr2)) {
                    mButton.setVisibility(View.VISIBLE);
                    mScrollView.setVisibility(View.VISIBLE);
                }
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
                if (industrySpinner.getSelectedItem().toString().equalsIgnoreCase("Automotive")) {
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
                if (moduleSpinner.getSelectedItem().toString().startsWith("Used vehicle") || moduleSpinner.getSelectedItem().toString().startsWith("New vehicle")) {
                    brandSpinner.setVisibility(View.VISIBLE);
                    otherbrandlayout.setVisibility(View.GONE);
                } else if (moduleSpinner.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    otherCategoryLayout.setVisibility(View.VISIBLE);
                } else {
                    otherCategoryLayout.setVisibility(View.GONE);
                }
                break;
            case (R.id.spinnerbrand):

                if (brandSpinner.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    otherbrandlayout.setVisibility(View.VISIBLE);
                } else {
                    otherbrandlayout.setVisibility(View.GONE);
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
                if (response.body() instanceof GetUserCategoryResponse) {
                    GetUserCategoryResponse moduleResponse = (GetUserCategoryResponse) response.body();
                    final List<String> module = new ArrayList<>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        module.add("Select Category");
                        for (GetUserCategoryResponse.Success message : moduleResponse.getSuccess()) {
                            module.add(message.getName());
                        }
                        module.add("Other");
                        MODULE = new String[module.size()];
                        MODULE = (String[]) module.toArray(MODULE);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, MODULE);
                        moduleSpinner.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
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
                    } else
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    final List<String> brand = new ArrayList<>();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        brand.add("-Select Brands");
                        for (BrandsTagResponse.Success message : brandsTagResponse.getSuccess()) {
                            brand.add(message.getTagName());
                        }
                        brand.add("Other");
                        BRAND = new String[brand.size()];
                        BRAND = (String[]) brand.toArray(BRAND);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, BRAND);
                        brandSpinner.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                } else if (response.body() instanceof GenerateOtpResponse) {
                    GenerateOtpResponse otpResponse = (GenerateOtpResponse) response.body();
                    if (otpResponse.getSuccess().getMsg().equals("OTP Gernated")) {
                        otpstr2 = "" + otpResponse.getSuccess().getOTP();
                    }
                } else {
                    CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                }
            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
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
                //  getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("loginregistrationid", str).apply();
                CustomToast.customToast(getApplicationContext(), "Already Registered Please Login");
                Intent i = new Intent(Registration.this, LoginActivity.class);
                startActivity(i);
                finish();
            } else if (str.equalsIgnoreCase("Fail")) {
                mOtpLayout.setVisibility(View.VISIBLE);
                mLinear.setVisibility(View.GONE);
                sendOtp(contactstr);
                //mButton.setVisibility(View.VISIBLE);
                //mScrollView.setVisibility(View.VISIBLE);
            } else {
                getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("loginregistrationid", Integer.parseInt(str)).apply();
                CustomToast.customToast(getApplicationContext(), " Registered Successfully");
                Intent i = new Intent(Registration.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    /*
    Send OTP...
     */
    public void sendOtp(String contactstr) {
        ApiCall mApiCall = new ApiCall(Registration.this, this);
        mApiCall.getOTP(contactstr);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("registerFirstRun", true)) {
            startActivity(new Intent(getApplicationContext(), GradientBackgroundExampleActivity.class));
            editor = sharedPreferences.edit();
            editor.putBoolean("registerFirstRun", false);
            editor.apply();
        }
    }
}

