package autokatta.com.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.SocketTimeoutException;
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
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, android.location.LocationListener, RequestNotifier, View.OnTouchListener {

    EditText personName, mobileNo, email, dateOfBirth, pincode, otherIndustry, otherCategory, password, confirmPassword;
    Spinner moduleSpinner,usertypeSpinner,industrySpinner;
    Button btnSubmit,btnClear;
    AutoCompleteTextView address;
    TextInputLayout otherIndustryLayout, otherCategoryLayout;
    String namestr,contactstr,emailstr,DOBstr,pincodestr,passwordstr,confirmpassstr,addressstr,genderstr,
            profession,sub_profession,strIndustry;

    RadioButton rbtmale,rbtfemale;
    ImageView dob_calender;
    RadioGroup rg1;
    private String[] MODULE = null;
    private String[] INDUSTRY = null;
    ApiCall apiCall;
    GenericFunctions functions;
    private List<String> resultList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiCall = new ApiCall(this, this);

        personName=(EditText)findViewById(R.id.editPersonName);
        mobileNo=(EditText)findViewById(R.id.editMobileNo);
        email=(EditText)findViewById(R.id.editEmail);
        dateOfBirth=(EditText)findViewById(R.id.editdob);
        address=(AutoCompleteTextView) findViewById(R.id.editAddress);
        pincode=(EditText)findViewById(R.id.editPincode);
        otherIndustry = (EditText) findViewById(R.id.editOtherTypeIndustry);
        otherCategory = (EditText) findViewById(R.id.editOtherTypeCategory);
        password=(EditText)findViewById(R.id.editPassword);
        confirmPassword=(EditText)findViewById(R.id.editConfirmPassword);
        usertypeSpinner=(Spinner)findViewById(R.id.spinnerUsertype);
        industrySpinner=(Spinner)findViewById(R.id.spinnerindustry);
        moduleSpinner=(Spinner)findViewById(R.id.spinnerCategory);
        otherIndustryLayout = (TextInputLayout) findViewById(R.id.otherIndustryLayout);
        otherCategoryLayout = (TextInputLayout) findViewById(R.id.otherCategoryLayout);
        rbtmale = (RadioButton) findViewById(R.id.rbtmale);
        rbtfemale = (RadioButton) findViewById(R.id.rbtfemale);
        dob_calender=(ImageView)findViewById(R.id.dob_calender);

        rg1 = (RadioGroup) findViewById(R.id.radiogp1);
        functions=new GenericFunctions();


        btnSubmit=(Button)findViewById(R.id.btnsubmit);
        btnClear=(Button)findViewById(R.id.btnclear);
        btnClear.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        dateOfBirth.setInputType(InputType.TYPE_NULL);

        usertypeSpinner.setOnItemSelectedListener(this);
        industrySpinner.setOnItemSelectedListener(this);
        moduleSpinner.setOnItemSelectedListener(this);
        dob_calender.setOnTouchListener(this);
        dateOfBirth.setOnTouchListener(this);


        address.setAdapter(new GooglePlacesAdapter(RegistrationActivity.this, R.layout.simple));

        apiCall.Categories();
        apiCall.Industries();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case (R.id.btnsubmit) :

                Boolean flag = false;

                namestr = personName.getText().toString().trim();
                contactstr = mobileNo.getText().toString().trim();
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

                apiCall.registrationContactValidation(contactstr);


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


                } else if (!namestr.matches("[a-zA-Z ]*")) {

                    personName.setError("Enter Valid Name");
                    personName.requestFocus();

                } else if (contactstr.isEmpty()) {

                    mobileNo.setError("Enter Contact No");
                    mobileNo.requestFocus();

                } else if (contactstr.matches("[0]+")) {

                    mobileNo.setError("Enter Valid Contact");
                    mobileNo.requestFocus();
                } else if (!contactstr.matches("[0-9]*")) {

                    mobileNo.setError("Enter Valid Contact");
                    mobileNo.requestFocus();

                } else if (!(contactstr.length() == 10)) {

                    mobileNo.setError("Enter valid contact");
                    mobileNo.requestFocus();

                } else if (emailstr.isEmpty()) {

                    email.setError("Enter Email ID");
                    email.requestFocus();

                }
                // Validation for Email is not valid
                else if (!functions.isValidEmail(emailstr)) {
                    // flag=false;
                    email.setError("Enter Valid Email");
                    email.requestFocus();

                } else if (DOBstr.isEmpty()) {

                    dateOfBirth.setError("Enter Date Of Birth");
                    dateOfBirth.requestFocus();


                } else if (!functions.getbirthdate(DOBstr)) {

                    dateOfBirth.setError("Minimum 8 Year Age Required");
                    dateOfBirth.requestFocus();

                } else if (addressstr.isEmpty()) {
                    address.setError("Enter Address");

                } else if (!flag) {

                    address.setError("Please Select Address From Dropdown Only");
                    address.requestFocus();

                } else if (pincodestr.isEmpty()) {
                    pincode.setError("Enter Pincode");
                    pincode.requestFocus();

                } else if (!pincodestr.matches("[0-9]*")) {

                    pincode.setError("Please enter valid pincode");
                    pincode.requestFocus();

                    Toast.makeText(RegistrationActivity.this, "Please enter valid pincode ", Toast.LENGTH_LONG).show();

                } else if (pincodestr.length() < 6) {

                    pincode.setError("Enter valid pincode");
                    pincode.requestFocus();
                } else if (profession.equalsIgnoreCase("Select User Type")) {
                    Toast.makeText(getApplicationContext(), "Please select User type", Toast.LENGTH_LONG).show();
                } else if ((!profession.equalsIgnoreCase("Student") && strIndustry.equalsIgnoreCase("Select Industry"))) {
                    Toast.makeText(RegistrationActivity.this, "Please select industry", Toast.LENGTH_LONG).show();
                } else if (strIndustry.equalsIgnoreCase("Other") && otherIndustry.getText().toString().trim().equalsIgnoreCase("")) {
                    otherIndustry.setError("Enter Industry");
                } else if (strIndustry.equalsIgnoreCase("Other") && !otherIndustry.getText().toString().matches("[a-zA-Z ]*")) {
                    otherIndustry.setError("Enter  Valid Industry");
                } else if (strIndustry.equalsIgnoreCase("Automotive and vehicle manufacturing") && sub_profession.equalsIgnoreCase("Select Category")) {
                    Toast.makeText(RegistrationActivity.this, "Please select category", Toast.LENGTH_LONG).show();
                } else if (sub_profession.equalsIgnoreCase("other") && otherCategory.getText().toString().equalsIgnoreCase("")) {
                    otherCategory.setError("Enter Profession");
                } else if (sub_profession.equalsIgnoreCase("other") && !otherCategory.getText().toString().matches("[a-zA-Z ]*")) {
                    otherCategory.setError("Enter  Valid Profession");
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

                    if (sub_profession.equalsIgnoreCase("Other")) {
                        sub_profession = otherCategory.getText().toString().trim();
                        apiCall.addOtherCategory(sub_profession);

                    }
                    if (strIndustry.equalsIgnoreCase("Other")) {
                        strIndustry = otherIndustry.getText().toString().trim();

                        apiCall.addOtherIndustry(strIndustry);

                    }

                    if (sub_profession.equalsIgnoreCase("Select Category"))
                        sub_profession="";
                    if (strIndustry.equalsIgnoreCase("Select Industry"))
                        strIndustry="";

                    apiCall.registrationAfterOtp(namestr,contactstr,emailstr,DOBstr,genderstr,pincodestr,addressstr,profession,passwordstr,sub_profession,strIndustry);

                }

                break;




            case (R.id.btnclear) :
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
                System.out.println("In User Spinner");

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

                /*
                        Response to get category
                 */
                if (response.body() instanceof CategoryResponse) {
                    CategoryResponse moduleResponse = (CategoryResponse) response.body();
                    final List<String> module = new ArrayList<String>();

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
                    final List<String> module = new ArrayList<String>();

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
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
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
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            Log.i("String------", "->" + str);
            getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("loginregistrationid", str).apply();
            CustomToast.customToast(getApplicationContext(), "Registration Successfully");
            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(i);
            RegistrationActivity.this.finish();
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }

  /*      i.putExtra("username", namestr);
        i.putExtra("contact", contactstr);
        i.putExtra("email", emailstr);
        i.putExtra("pincode", pincodestr);
        i.putExtra("city", addressstr);
        i.putExtra("password", passwordstr);
        i.putExtra("dob", DOBstr);
        i.putExtra("gender", genderstr);
        i.putExtra("call", "register");*/
/*

        if (sub_profession.equalsIgnoreCase("Other")) {
            sub_profession = otherCategory.getText().toString().trim();
            i.putExtra("sub_profession", sub_profession);
            apiCall.addOtherCategory(sub_profession);

        } else {
            i.putExtra("sub_profession", sub_profession);
        }

        if (strIndustry.equalsIgnoreCase("Other")) {
            strIndustry = otherIndustry.getText().toString().trim();
            i.putExtra("industry", strIndustry);

            apiCall.addOtherIndustry(strIndustry);

        } else {
            i.putExtra("industry", strIndustry);
        }

        i.putExtra("profession", profession);

*/



   /*     if (str.equalsIgnoreCase("Success")) {


            mobileNo.setError("Contact Already Exists!!!!!!!");
            mobileNo.requestFocus();
        }
*/

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()) {
            case (R.id.dob_calender):

                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    DialogFragment newFragment = new SelectDateFragment();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;

            case (R.id.editdob):

                int action1 = motionEvent.getAction();
                if (action1 == MotionEvent.ACTION_DOWN) {
                    DialogFragment newFragment = new SelectDateFragment();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;

        }


        return false;
    }


    //date  setting class
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String m = String.valueOf(month);
            String d = String.valueOf(day);
            if (d.length() < 2) {
                d = "0" + d;
            }
            if (m.length() < 2) {
                m = "0" + m;
            }
            //dobtext.setText(d+"-"+m+"-"+year);
            dateOfBirth.setText(year + "-" + m + "-" + d);
        }

    }
}
