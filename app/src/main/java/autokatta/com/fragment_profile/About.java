package autokatta.com.fragment_profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.AddTags;
import autokatta.com.other.CustomToast;
import autokatta.com.response.CategoryResponse;
import autokatta.com.response.GetCompaniesResponse;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.ProfileAboutResponse;
import me.gujun.android.taggroup.TagGroup;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static autokatta.com.adapter.GooglePlacesAdapter.resultList;

/**
 * Created by ak-001 on 18/3/17.
 */

public class About extends Fragment implements RequestNotifier {
    View mAbout;
    String[] MODULE = null;
    ImageView mDone;
    ImageView mEdit;
    EditText mWebsite, mEmail;
    TextView mContact, mProfession, msubprofession, mEditTags, mCount;
    AutoCompleteTextView mCompany, mDesignation, mCity;
    MultiAutoCompleteTextView mSkills;
    String newcompanyname, newdesignation, newskills, strCompany, strDesignation, strskills;

    String userName, email, contact, profession, company, designation, subProfession, websitestr, city, skills, interest;
    String mUpdatedEmail, mUpdatedProfession, mUpdatedCompany, mUpdatedDesignation, mUpdatedSkills, mUpdatedSkills1, mUpdatedCity, mUpdatedWebsite;

    final ArrayList<String> mSkillList = new ArrayList<>();
    final HashMap<String, String> mSkillList1 = new HashMap<>();

    final ArrayList<String> mCompanyList = new ArrayList<>();
    final HashMap<String, Integer> mCompanyList1 = new HashMap<>();

    final ArrayList<String> mDesignationList = new ArrayList<>();
    final HashMap<String, String> mDesignationList1 = new HashMap<>();

    List<String> parsedDataCompany = new ArrayList<>();
    List<String> parsedDataDesignation = new ArrayList<>();
    List<String> parsedDataSkills = new ArrayList<>();

    Spinner spinner;
    RadioGroup usertype;
    RadioButton student, employee, selfemployee;
    String Sharedcontact;
    int RegId;
    String spinnervalue = "";
    ApiCall mApiCall;
    String[] parts;
    boolean _hasLoadedOnce = false;
    Activity mActivity;
    LinearLayout mLinear;
    GenericFunctions mGenericFunctions;
    private TagGroup mTagGroup;
    String tags[] = {"Launch",
            "Review",
            "Demo",
            "News",
            "Buying",
            "Selling",
            "Spare Parts",
            "Vehicles",
            "Accessories",
            "New Vehicle",
            "Used Vehicle",
            "Auction",
            "Racing",
            "Auto Sports",
            "Information",
            "Jobs",
            "Car pool",
            "Others",
            "2 wheelers",
            "3 wheelers",
            "Cars",
            "Trucks",
            "Transport & Cargo",
            "Construction Equipment"};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAbout = inflater.inflate(R.layout.fragment_profile_about, container, false);

        return mAbout;
    }


    public boolean isValidUrl(String txtWebsite) {
        Pattern regex = Pattern.compile("^(WWW|www)\\.+[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|in|IN|COM|ORG|NET|MIL|EDU)$");
        Matcher matcher = regex.matcher(txtWebsite);
        return matcher.matches();
    }

    public void checkSkills() {
        String text = mSkills.getText().toString();
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        parts = text.split(",");
        if (parts.length > 5) {
            mSkills.setError("You can add maximum five skills");
        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof ProfileAboutResponse) {
                    mLinear.setVisibility(View.VISIBLE);
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
                        interest = mProfileAboutResponse.getSuccess().get(0).getInterests();

                        mContact.setText(contact);
                        Log.i("RegId-->", "   --->" + RegId);
                        Log.i("SUBProfession-->", "   --->" + subProfession);
                        if (subProfession == null || subProfession.equalsIgnoreCase("Select Category")) {
                            msubprofession.setText("NA");
                        } else {
                            msubprofession.setText("Sub Profession- " + subProfession);
                        }
                        mProfession.setText(profession);
                        if (profession != "Student" || !profession.equalsIgnoreCase("Student")) {
                            msubprofession.setVisibility(View.VISIBLE);
                        } else
                            msubprofession.setVisibility(View.GONE);
                        mEmail.setText(email);
                        mWebsite.setText(websitestr);
                        mCity.setText(city);
                        mCity.setEnabled(false);
                        // mCity.setFocusable(false);
                        mCompany.setText(company);
                        mDesignation.setText(designation);
                        mSkills.setText(skills);

                        if (interest.contains(",")) {
                            String[] commaSplit = interest.split(",");
                            /*for (int i=0; i<commaSplit.length; i++){
                                Log.i("Splited","->"+commaSplit[i]);
                            }*/
                            mCount.setText("You have added " + commaSplit.length + " interest");
                        } else {
                            mCount.setText("You have added " + interest.length() + " interest");
                        }
                    }
                } else if (response.body() instanceof GetCompaniesResponse) {
                    GetCompaniesResponse mGetCompanyList = (GetCompaniesResponse) response.body();
                    if (!mGetCompanyList.getSuccess().isEmpty()) {
                        for (GetCompaniesResponse.Success companyResponse : mGetCompanyList.getSuccess()) {
                            companyResponse.setCompanyID(companyResponse.getCompanyID());
                            companyResponse.setCompanyName(companyResponse.getCompanyName());
                            mCompanyList.add(companyResponse.getCompanyName());
                            mCompanyList1.put(companyResponse.getCompanyName(), companyResponse.getCompanyID());
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
                         /*   List<String> name = new ArrayList<>();
                            name.add(subProfession);*/
                            for (int i = 0; i < module.size(); i++) {
                                if (subProfession != null) {
                                    if (subProfession.equals(module.get(i))) {
                                        spinner.setSelection(i);
                                        if (getActivity() != null) {
                                            ArrayAdapter<String> dataadapter1 = new ArrayAdapter<>(getActivity(), R.layout.registration_spinner, MODULE);
                                            spinner.setAdapter(dataadapter1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "About Activity");
        }
    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("")) {
            if (str.equals("success_update")) {
                CustomToast.customToast(getActivity(), "Profile Updated");
                mApiCall.profileAbout(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""), getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
             /*   mCity.setEnabled(false);
                mCity.setFocusable(false);*/
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser && !_hasLoadedOnce) {
                mApiCall.profileAbout(Sharedcontact, Sharedcontact);
                mApiCall.getSkills();
                mApiCall.getDesignation();
                mApiCall.getCompany();
                mApiCall.Categories("");
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiCall = new ApiCall(getActivity(), this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference),
                        MODE_PRIVATE).getString("loginContact", "");
                //mTagGroup = (TagGroup) mAbout.findViewById(R.id.tag_group);
                //mTagGroup.setTags(tags);
                mContact = (TextView) mAbout.findViewById(R.id.contact_no);
                mCount = (TextView) mAbout.findViewById(R.id.count);
                mEditTags = (TextView) mAbout.findViewById(R.id.editTags);
                mProfession = (TextView) mAbout.findViewById(R.id.worked_at);
                msubprofession = (TextView) mAbout.findViewById(R.id.subprofession);
                mEmail = (EditText) mAbout.findViewById(R.id.email);
                mWebsite = (EditText) mAbout.findViewById(R.id.website);
                mCity = (AutoCompleteTextView) mAbout.findViewById(R.id.address);
                mCompany = (AutoCompleteTextView) mAbout.findViewById(R.id.company_name);
                mDesignation = (AutoCompleteTextView) mAbout.findViewById(R.id.designation);
                mSkills = (MultiAutoCompleteTextView) mAbout.findViewById(R.id.skills);
                mDone = (ImageView) mAbout.findViewById(R.id.done);
                mEdit = (ImageView) mAbout.findViewById(R.id.edit);
                usertype = (RadioGroup) mAbout.findViewById(R.id.usertype);
                student = (RadioButton) mAbout.findViewById(R.id.student);
                employee = (RadioButton) mAbout.findViewById(R.id.employee);
                selfemployee = (RadioButton) mAbout.findViewById(R.id.selfemployee);
                spinner = (Spinner) mAbout.findViewById(R.id.spinner);
                spinner.setVisibility(View.GONE);

                mLinear = (LinearLayout) mAbout.findViewById(R.id.profileAbout);

                mGenericFunctions = new GenericFunctions();


                mApiCall.profileAbout(Sharedcontact, Sharedcontact);
                mApiCall.getSkills();
                mApiCall.getDesignation();
                mApiCall.getCompany();
                mApiCall.Categories("");
        /*Get Designation,Skills,Company From web service*/


                mProfession.setEnabled(false);
                mContact.setEnabled(false);
                mWebsite.setEnabled(false);
                mCity.setEnabled(false);
                mEmail.setEnabled(false);
                mCompany.setEnabled(false);
                mDesignation.setEnabled(false);
                mSkills.setEnabled(false);

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

                usertype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.student) {
                            mProfession.setText("Student");
                            spinner.setVisibility(View.GONE);
                            msubprofession.setVisibility(View.GONE);

                        } else if (checkedId == R.id.employee) {
                            mProfession.setText("Employee");
                            spinner.setVisibility(View.VISIBLE);
                            msubprofession.setVisibility(View.VISIBLE);
                            spinner.setSelection(0);

                        } else if (checkedId == R.id.selfemployee) {
                            mProfession.setText("Self Employee");
                            spinner.setVisibility(View.VISIBLE);
                            msubprofession.setVisibility(View.VISIBLE);
                            spinner.setSelection(0);

                        }
                    }
                });

                mEdit.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        mDone.setVisibility(View.VISIBLE);
                        mEdit.setVisibility(View.GONE);
                        if (profession.equalsIgnoreCase("Student")) {
                            student.setChecked(true);
                        } else if (profession.equalsIgnoreCase("Employee")) {
                            employee.setChecked(true);
                        } else if (profession.equalsIgnoreCase("Self Employee")) {
                            selfemployee.setChecked(true);
                        }
                        if (student.isChecked()) {
                            spinner.setVisibility(View.GONE);
                            msubprofession.setVisibility(View.GONE);
                        } else if (employee.isChecked()) {
                            spinner.setVisibility(View.VISIBLE);
                        } else if (selfemployee.isChecked()) {
                            spinner.setVisibility(View.VISIBLE);
                        }

                        mCity.setFocusableInTouchMode(true);
                        mCity.setFocusable(true);

                        mCompany.setFocusableInTouchMode(true);
                        mCompany.setFocusable(true);

                        mDesignation.setFocusableInTouchMode(true);
                        mDesignation.setFocusable(true);

                        mSkills.setFocusableInTouchMode(true);
                        mSkills.setFocusable(true);

                        mCity.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
                        mProfession.setEnabled(true);
                        mCity.setEnabled(true);
                        // mCity.setSelection(mCity.getText().length());
                        mContact.setEnabled(true);
                        mEmail.setEnabled(true);
                        mCompany.setEnabled(true);
                        mDesignation.setEnabled(true);
                        mSkills.setEnabled(true);
                        mWebsite.setEnabled(true);
                        usertype.setVisibility(View.VISIBLE);
                        mDone.setEnabled(true);
                        mContact.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                //showMessage(getActivity(), "You can't edit contact");
                                return false;
                            }
                        });
                    }
                });

                mDone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean webflag = false;
                        usertype.setVisibility(View.GONE);
                        spinner.setVisibility(View.GONE);
                        mUpdatedProfession = mProfession.getText().toString();
                        mUpdatedCity = mCity.getText().toString();
                        mUpdatedEmail = mEmail.getText().toString();
                        mUpdatedWebsite = mWebsite.getText().toString();
                        spinnervalue = spinner.getSelectedItem().toString();
                        mUpdatedCompany = mCompany.getText().toString();
                        mUpdatedDesignation = mDesignation.getText().toString();
                        mUpdatedSkills = mSkills.getText().toString().trim();

                        if (mUpdatedSkills.endsWith(",")) {
                            mUpdatedSkills1 = mUpdatedSkills.substring(0, mUpdatedSkills.length() - 1);
                        } else {
                            mUpdatedSkills1 = mUpdatedSkills;
                        }
                        webflag = mUpdatedWebsite.equalsIgnoreCase("");
                        Boolean flag = false;
                        try {
                            for (int i = 0; i < resultList.size(); i++) {
                                if (mUpdatedCity.equalsIgnoreCase(resultList.get(i))) {
                                    flag = true;
                                    break;
                                } else {
                                    flag = false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (!mGenericFunctions.isValidEmail(mUpdatedEmail))
                            mEmail.setError("Invalid Email");
                        else if (!webflag && !isValidUrl(mUpdatedWebsite)) {
                            mWebsite.setError("Invalid Website");
                        } else if (mCity.getText().toString().isEmpty()) {
                            mCity.setError("Please Select Address From Dropdown Only");
                        } else {

                            /************************Company Name code *******************************************/
                            mCompany.clearFocus();
                            newcompanyname = mCompany.getText().toString();
                            try {
                                if (!mCompanyList.contains(newcompanyname) && !newcompanyname.equalsIgnoreCase("")) {
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
                                if (!mDesignationList.contains(newdesignation) && !newdesignation.equalsIgnoreCase("")) {
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
                                mApiCall.updateProfile(RegId, mUpdatedEmail, mUpdatedCity, mUpdatedProfession, spinnervalue, mUpdatedWebsite, mUpdatedCompany, mUpdatedDesignation, mUpdatedSkills1);
                                mDone.setVisibility(View.GONE);
                                mEdit.setVisibility(View.VISIBLE);

                                mProfession.setEnabled(false);
                                mContact.setEnabled(false);
                                mWebsite.setEnabled(false);
                                mCity.setEnabled(false);
                                mCity.setFocusable(false);
                                mEmail.setEnabled(false);
                                mCompany.setEnabled(false);
                                mCompany.setFocusable(false);
                                mDesignation.setEnabled(false);
                                mDesignation.setFocusable(false);
                                mSkills.setEnabled(false);
                                mSkills.setFocusable(false);
                            }
                        }
                    }
                });

                mEditTags.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Intent intent = new Intent(getActivity(), EditTags.class);
                        intent.putExtra("stringArray", tags);
                        startActivity(intent);*/
                        Intent intent = new Intent(getActivity(), AddTags.class);
                        intent.putExtra("interest", interest);
                        startActivity(intent);
                    }
                });
            }
        });
    }

   /* public void showMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void errorMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mApiCall.profileAbout(Sharedcontact, Sharedcontact);
                        mApiCall.getSkills();
                        mApiCall.getDesignation();
                        mApiCall.getCompany();
                        mApiCall.Categories("");
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }*/
}
