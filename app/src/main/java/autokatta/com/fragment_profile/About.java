package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetCompaniesResponse;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static autokatta.com.adapter.GooglePlacesAdapter.resultList;

/**
 * Created by ak-001 on 18/3/17.
 */

public class About extends Fragment implements RequestNotifier {

    View mAbout;
    String[] MODULE = null;
    Button mDone;
    ImageView mEdit;
    EditText mWebsite, mEmail;
    TextView mContact, mProfession;
    AutoCompleteTextView mCompany, mDesignation, mCity;
    MultiAutoCompleteTextView mSkills;
    String newcompanyname, newdesignation, newskills, strCompany, strDesignation, strskills;

    String userName, email, contact, profession, company, designation, subProfession, websitestr, city, skills;
    String mUpdatedEmail, mUpdatedProfession, mUpdatedCompany, mUpdatedDesignation, mUpdatedSkills, mUpdatedSkills1, mUpdatedCity, mUpdatedWebsite;

    final ArrayList<String> mSkillList = new ArrayList<>();
    final HashMap<String, String> mSkillList1 = new HashMap<>();

    final ArrayList<String> mCompanyList = new ArrayList<>();
    final HashMap<String, String> mCompanyList1 = new HashMap<>();

    final ArrayList<String> mDesignationList = new ArrayList<>();
    final HashMap<String, String> mDesignationList1 = new HashMap<>();

    List<String> parsedDataCompany = new ArrayList<>();
    List<String> parsedDataDesignation = new ArrayList<>();
    List<String> parsedDataSkills = new ArrayList<>();

    RelativeLayout radiolayout;
    Spinner spinner;
    RadioGroup usertype;
    RadioButton student, employee, selfemployee;
    RelativeLayout mrel;
    String Sharedcontact, RegId;
    String spinnervalue = "";
    ApiCall mApiCall;
    String[] parts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAbout = inflater.inflate(R.layout.fragment_profile_about, container, false);
        Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        // RegId=getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginregistrationid", "");
        System.out.println("REGID--------------------------->" + RegId);
        mContact = (TextView) mAbout.findViewById(R.id.contact_no);
        mProfession = (TextView) mAbout.findViewById(R.id.worked_at);
        mEmail = (EditText) mAbout.findViewById(R.id.email);
        mWebsite = (EditText) mAbout.findViewById(R.id.website);
        mCity = (AutoCompleteTextView) mAbout.findViewById(R.id.address);
        mCompany = (AutoCompleteTextView) mAbout.findViewById(R.id.company_name);
        mDesignation = (AutoCompleteTextView) mAbout.findViewById(R.id.designation);
        mSkills = (MultiAutoCompleteTextView) mAbout.findViewById(R.id.skills);
        mDone = (Button) mAbout.findViewById(R.id.done);
        mEdit = (ImageView) mAbout.findViewById(R.id.edit);
        usertype = (RadioGroup) mAbout.findViewById(R.id.usertype);
        student = (RadioButton) mAbout.findViewById(R.id.student);
        employee = (RadioButton) mAbout.findViewById(R.id.employee);
        selfemployee = (RadioButton) mAbout.findViewById(R.id.selfemployee);
        radiolayout = (RelativeLayout) mAbout.findViewById(R.id.radiolayout);
        spinner = (Spinner) mAbout.findViewById(R.id.spinner);
        mrel = (RelativeLayout) mAbout.findViewById(R.id.rel);
        spinner.setVisibility(View.GONE);
        /*Get Designation,Skills,Company From web service*/
        mApiCall = new ApiCall(getActivity(), this);
        mApiCall.profileAbout(Sharedcontact, Sharedcontact);
        mApiCall.getSkills();
        mApiCall.getDesignation();
        mApiCall.getCompany();
        mApiCall.Categories("");

        mSkills.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        mSkills.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {

                    mSkills.setText(mSkills.getText().toString() + ",");
                    mSkills.setSelection(mSkills.getText().toString().length());
                    checkSkills();
                    return true;
                }
                return false;
            }
        });

        if (mProfession.getText().toString().equals("Student"))
            student.setChecked(true);
        if (mProfession.getText().toString().equals("Employee"))
            employee.setChecked(true);
        if (mProfession.getText().toString().equals("Self Employee"))
            selfemployee.setChecked(true);

        usertype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.student) {
                    mProfession.setText("Student");
                    spinner.setVisibility(View.GONE);
                } else if (checkedId == R.id.employee) {
                    mProfession.setText("Employee");
                    spinner.setVisibility(View.VISIBLE);
                    spinner.setSelection(0);
                } else if (checkedId == R.id.selfemployee) {
                    mProfession.setText("Self Employee");
                    spinner.setVisibility(View.VISIBLE);
                    spinner.setSelection(0);
                }
            }
        });
        mEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mrel.setVisibility(View.VISIBLE);
                if (student.isChecked()) {
                    spinner.setVisibility(View.GONE);
                } else if (employee.isChecked()) {
                    spinner.setVisibility(View.VISIBLE);
                } else if (selfemployee.isChecked()) {
                    spinner.setVisibility(View.VISIBLE);
                }
                mCity.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
                mProfession.setEnabled(true);
                mContact.setEnabled(true);
                mWebsite.setEnabled(true);
                mCity.setEnabled(true);
                mEmail.setEnabled(true);
                mCompany.setEnabled(true);
                mDesignation.setEnabled(true);
                mSkills.setEnabled(true);

                radiolayout.setVisibility(View.VISIBLE);
                mDone.setEnabled(true);

                mContact.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        CustomToast.customToast(getActivity(), "You Cant Edit Contact");
                        return false;
                    }
                });
            }
        });
        mProfession.setEnabled(false);
        mContact.setEnabled(false);
        mWebsite.setEnabled(false);
        mCity.setEnabled(false);
        mEmail.setEnabled(false);
        mCompany.setEnabled(false);
        mDesignation.setEnabled(false);
        mSkills.setEnabled(false);

        mDone.setEnabled(false);
        mDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean webflag = false;

                radiolayout.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);

                mUpdatedProfession = mProfession.getText().toString();
                mUpdatedCity = mCity.getText().toString();
                mUpdatedEmail = mEmail.getText().toString();
                mUpdatedWebsite = mWebsite.getText().toString();
                spinnervalue = spinner.getSelectedItem().toString();
                mUpdatedCompany = mCompany.getText().toString();
                mUpdatedDesignation = mDesignation.getText().toString();
                mUpdatedSkills = mSkills.getText().toString().trim();
                if (mUpdatedSkills.endsWith(","))
                    mUpdatedSkills1 = mUpdatedSkills.substring(0, mUpdatedSkills.length() - 1);

                webflag = mUpdatedWebsite.equalsIgnoreCase("");

                Boolean flag = false;

                try {
                    for (int i = 0; i < resultList.size(); i++) {

                        System.out.println("resultlist " + i + "item=" + resultList.get(i).toString());

                        if (mUpdatedCity.equalsIgnoreCase(resultList.get(i).toString())) {

                            System.out.println("user selected location===" + mUpdatedCity);
                            System.out.println(" user selected google location" + resultList.get(i));
                            flag = true;
                            break;

                        } else {

                            flag = false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (!isValidEmail(mUpdatedEmail))
                    mEmail.setError("Invalid Email");
                else if (!webflag && !isValidUrl(mUpdatedWebsite)) {
                    mWebsite.setError("Invalid Website");

                } else if (flag == false) {

                    mCity.setError("Please Select Address From Dropdown Only");
                } else {
                    mProfession.setEnabled(false);
                    mContact.setEnabled(false);
                    mWebsite.setEnabled(false);
                    mCity.setEnabled(false);
                    mEmail.setEnabled(false);
                    mCompany.setEnabled(false);
                    mDesignation.setEnabled(false);
                    mSkills.setEnabled(false);
                    mDone.setEnabled(false);


                    /************************Company Name code *******************************************/
                    mCompany.clearFocus();
                    newcompanyname = mCompany.getText().toString();

                    try {

                        if (!mCompanyList.contains(newcompanyname)) {
                            mApiCall.addNewCompany(newcompanyname);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    strCompany = mCompany.getText().toString();
                    //***************************************************************
                    String splChrs = "-/@#$%^&_+=()";
                    boolean found = strCompany.matches("["
                            + splChrs + "]+");

                 /*Designation Name code */

                    mDesignation.clearFocus();
                    newdesignation = mDesignation.getText().toString();

                    try {
                        if (!mDesignationList.contains(newdesignation)) {
                            mApiCall.addNewDesignation(newdesignation);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    strDesignation = mDesignation.getText().toString();
                    //***************************************************************

                    boolean found1 = strDesignation.matches("["
                            + splChrs + "]+");
                    /*Skills*/

                    mSkills.clearFocus();
                    newskills = mSkills.getText().toString().trim();
                    if (newskills.endsWith(","))
                        newskills = newskills.substring(0, newskills.length() - 1);
                    parts = newskills.split(",");
                    for (int i = 0; i < parts.length; i++) {
                        try {

                            if (!mSkillList.contains(parts[i])) {
                                mApiCall.addNewSkills(parts[i]);
                                //addNewCompanyName(updatecompany);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    strskills = mSkills.getText().toString().trim();

                    //***************************************************************
                    splChrs = "-/@#$%^&_+=()";
                    boolean found2 = strCompany.matches("["
                            + splChrs + "]+");

                    //setting validation for reg fields
                    if (strCompany.equals("") || strCompany.equals("null") || strCompany.equals(null)) {
                        mCompany.setError("Enter Company Name");
                        mCompany.requestFocus();
                    } else if (found) {

                        mCompany.setError("Please enter valid company");
                        mCompany.requestFocus();
                    } else if (found1) {

                        mDesignation.setError("Please enter valid designation");
                        mDesignation.requestFocus();
                    } else if (strDesignation.equals("") || strDesignation.equals("null") || strDesignation.equals(null)) {
                        mDesignation.setError("Enter Designation Name");
                        mDesignation.requestFocus();
                    } else if (strskills.equals("") || strskills.equals("null") || strskills.equals(null)) {
                        mSkills.setError("Enter Skills Name");
                        mSkills.requestFocus();
                    } else {
                        mApiCall.updateProfile(mUpdatedEmail, mUpdatedWebsite, mUpdatedProfession, mUpdatedCompany, mUpdatedDesignation, mUpdatedSkills1, mUpdatedCity, spinnervalue, RegId);
                        // submitData();
                    }
                    mrel.setVisibility(View.GONE);
                }
            }
        });
        return mAbout;
    }


    public boolean isValidUrl(String txtWebsite) {

        Pattern regex = Pattern.compile("^(WWW|www)\\.+[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|in|IN|COM|ORG|NET|MIL|EDU)$");
        Matcher matcher = regex.matcher(txtWebsite);
        return matcher.matches();

    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void checkSkills() {
        String text = mSkills.getText().toString();
        System.out.println("texttttttttttttttttt Skills" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        parts = text.split(",");
        System.out.println("size of partssssssssssssssssss skills" + parts.length);
        if (parts.length > 5) {
            mSkills.setError("You can add maximum five skills");
        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof ProfileAboutResponse) {
                    ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                    if (!mProfileAboutResponse.getSuccess().isEmpty()) {
                        userName = mProfileAboutResponse.getSuccess().get(0).getUsername();
                        email = mProfileAboutResponse.getSuccess().get(0).getEmail();
                        contact = mProfileAboutResponse.getSuccess().get(0).getContact();
                        profession = mProfileAboutResponse.getSuccess().get(0).getProfession();
                        company = mProfileAboutResponse.getSuccess().get(0).getCompanyName();
                        designation = mProfileAboutResponse.getSuccess().get(0).getDesignation();
                        subProfession = mProfileAboutResponse.getSuccess().get(0).getSubProfession();
                        websitestr = mProfileAboutResponse.getSuccess().get(0).getWebsite();
                        city = mProfileAboutResponse.getSuccess().get(0).getCity();
                        skills = mProfileAboutResponse.getSuccess().get(0).getSkills();
                        RegId = mProfileAboutResponse.getSuccess().get(0).getRegId();

                        mContact.setText(contact);
                        mProfession.setText(profession);
                        mEmail.setText(email);
                        mWebsite.setText(websitestr);
                        mCity.setText(city);
                        mCompany.setText(company);
                        mDesignation.setText(designation);
                        mSkills.setText(skills);
                    }
                } else if (response.body() instanceof GetCompaniesResponse) {
                    GetCompaniesResponse mGetCompanyList = (GetCompaniesResponse) response.body();
                    if (!mGetCompanyList.getSuccess().isEmpty()) {
                        for (GetCompaniesResponse.Success companyResponse : mGetCompanyList.getSuccess()) {
                            companyResponse.setCompid(companyResponse.getCompid());
                            companyResponse.setCompanyName(companyResponse.getCompanyName());
                            mCompanyList.add(companyResponse.getCompanyName());
                            mCompanyList1.put(companyResponse.getCompanyName(), companyResponse.getCompid());
                        }
                        if (getActivity() != null) {
                            parsedDataCompany.addAll(mCompanyList);
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.addproductspinner_color, parsedDataCompany);
                            mCompany.setAdapter(dataAdapter);
                        }
                    }

                } else if (response.body() instanceof GetDesignationResponse) {
                    GetDesignationResponse mGetDesignationList = (GetDesignationResponse) response.body();
                    if (!mGetDesignationList.getSuccess().isEmpty()) {
                        for (GetDesignationResponse.Success designationResponse : mGetDesignationList.getSuccess()) {
                            designationResponse.setDesgId(designationResponse.getDesgId());
                            designationResponse.setDesignationName(designationResponse.getDesignationName());
                            mDesignationList.add(designationResponse.getDesignationName());
                            mDesignationList1.put(designationResponse.getDesignationName(), designationResponse.getDesgId());
                        }
                        if (getActivity() != null) {
                            parsedDataDesignation.addAll(mDesignationList);
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.addproductspinner_color, parsedDataDesignation);
                            mDesignation.setAdapter(dataAdapter);
                        }
                    }

                } else if (response.body() instanceof GetSkillsResponse) {
                    GetSkillsResponse mGetSkillsResponse = (GetSkillsResponse) response.body();
                    if (!mGetSkillsResponse.getSuccess().isEmpty()) {
                        for (GetSkillsResponse.Success skillsResponse : mGetSkillsResponse.getSuccess()) {
                            skillsResponse.setSkillId(skillsResponse.getSkillId());
                            skillsResponse.setSkillNames(skillsResponse.getSkillNames());
                            mSkillList.add(skillsResponse.getSkillNames());
                            mSkillList1.put(skillsResponse.getSkillNames(), skillsResponse.getSkillId());
                        }
                        if (getActivity() != null) {
                            parsedDataSkills.addAll(mSkillList);
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                    R.layout.addproductspinner_color, parsedDataSkills);
                            mSkills.setAdapter(dataAdapter);
                        }
                    }
                } else if (response.body() instanceof CategoryResponse) {
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
                        if (getActivity() != null) {
                            ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.registration_spinner, MODULE);
                            spinner.setAdapter(dataadapter);
                            for (int i = 0; i < module.size(); i++) {
                                if (subProfession != null) {
                                    if (subProfession.equals(module.get(i))) {
                                        spinner.setSelection(i);
                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "About Activity");
        }
    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("")) {
            if (str.equals("Success_update_profile")) {
                CustomToast.customToast(getActivity(), "Profile Updated Successfully");
                mApiCall.profileAbout(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""), getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }
        }
    }
}
