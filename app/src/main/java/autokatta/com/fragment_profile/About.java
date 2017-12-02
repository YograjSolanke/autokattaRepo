package autokatta.com.fragment_profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.GenericFunctions;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.AddTags;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.GetCompaniesResponse;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.GetUserCategoryResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.response.ProfileAboutResponse;
import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.prefs.PreferencesManager;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static autokatta.com.R.id.spinnerCategory;
import static autokatta.com.R.id.spinnerUsertype;
import static autokatta.com.R.id.spinnerbrand;
import static autokatta.com.R.id.spinnerindustry;
import static autokatta.com.adapter.GooglePlacesAdapter.resultList;

/**
 * Created by ak-001 on 18/3/17
 */

public class About extends Fragment implements RequestNotifier, MaterialIntroListener, AdapterView.OnItemSelectedListener {
    View mAbout;
    Button mDone;
    ImageView mEdit;
    EditText mWebsite, mEmail, mAbouttxt;
    TextView mContact, mProfession, mEditTags, mCount, mUsertypetxt, mCattxt, mIndusttxt, mBrandtxt;
    AutoCompleteTextView mCompany, mDesignation, mCity;
    MultiAutoCompleteTextView mSkills;
    String newcompanyname, newdesignation, newskills, strCompany, strDesignation, strskills;
    Bundle b = getArguments();
    LinearLayout mUsertypelay, mIndustrylay, mCategorylay, mBrandlay;
    String userName, email, contact, company, designation, subProfession, websitestr, city, skills, interest;
    String mUpdatedEmail, mUpdateAbout, mUpdatedCompany, mUpdatedDesignation, mUpdatedSkills, mUpdatedSkills1, mUpdatedCity, mUpdatedWebsite;
    final List<String> mSkillList = new ArrayList<>();
    final HashMap<String, String> mSkillList1 = new HashMap<>();
    final List<String> mCompanyList = new ArrayList<>();
    final HashMap<String, Integer> mCompanyList1 = new HashMap<>();
    final List<String> mDesignationList = new ArrayList<>();
    final HashMap<String, String> mDesignationList1 = new HashMap<>();
    List<String> parsedDataCompany = new ArrayList<>();
    List<String> parsedDataDesignation = new ArrayList<>();
    List<String> parsedDataSkills = new ArrayList<>();
    TextInputLayout otherIndustryLayout, otherCategoryLayout, otherbrandlayout;
    EditText otherIndustry;
    EditText otherCategory;
    EditText otherbrand;
    String Sharedcontact, strprofession = "", strIndustry = "", brand = "";
    Spinner moduleSpinner, usertypeSpinner, industrySpinner, brandSpinner;
    int RegId;
    ApiCall mApiCall;
    String[] MODULE = null;
    String[] INDUSTRY = null;
    String[] BRAND = null;
    String[] parts;
    boolean _hasLoadedOnce = false;
    Activity mActivity;
    LinearLayout mLinear;
    GenericFunctions mGenericFunctions;
    private static final String INTRO_FOCUS_1 = "intro_focus_1";
    ProgressDialog dialog;

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
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (response.body() instanceof ProfileAboutResponse) {
                    mLinear.setVisibility(View.VISIBLE);
                    ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                    if (!mProfileAboutResponse.getSuccess().isEmpty()) {
                        userName = mProfileAboutResponse.getSuccess().get(0).getUsername();
                        email = mProfileAboutResponse.getSuccess().get(0).getEmail();
                        contact = mProfileAboutResponse.getSuccess().get(0).getContact();
                        strprofession = mProfileAboutResponse.getSuccess().get(0).getProfession();
                        company = mProfileAboutResponse.getSuccess().get(0).getCompanyName();
                        designation = mProfileAboutResponse.getSuccess().get(0).getDesignation();
                        subProfession = mProfileAboutResponse.getSuccess().get(0).getSubProfession();
                        websitestr = mProfileAboutResponse.getSuccess().get(0).getWebsite();
                        city = mProfileAboutResponse.getSuccess().get(0).getCity();
                        skills = mProfileAboutResponse.getSuccess().get(0).getSkills();
                        RegId = mProfileAboutResponse.getSuccess().get(0).getRegId();
                        interest = mProfileAboutResponse.getSuccess().get(0).getInterests();
                        brand = mProfileAboutResponse.getSuccess().get(0).getBrandName();
                        strIndustry = mProfileAboutResponse.getSuccess().get(0).getIndustry();
                        mContact.setText(contact);
                        mEmail.setText(email);
                        mAbouttxt.setText(mProfileAboutResponse.getSuccess().get(0).getAbout());
                        mWebsite.setText(websitestr);
                        mCity.setText(city);
                        mCity.setEnabled(false);
                        mCompany.setText(company);
                        mDesignation.setText(designation);
                        mSkills.setText(skills);
                        mUsertypetxt.setText(strprofession);
                        mIndusttxt.setText(mProfileAboutResponse.getSuccess().get(0).getIndustry());
                        if (strprofession.equalsIgnoreCase("Student")) {
                            mBrandlay.setVisibility(View.GONE);
                            mIndustrylay.setVisibility(View.GONE);
                            mCategorylay.setVisibility(View.GONE);
                        }
                        if (!mProfileAboutResponse.getSuccess().get(0).getIndustry().equalsIgnoreCase("Automotive")) {
                            mBrandlay.setVisibility(View.GONE);
                            mCategorylay.setVisibility(View.GONE);
                            mIndusttxt.setText(mProfileAboutResponse.getSuccess().get(0).getIndustry());
                        } else {
                            mCategorylay.setVisibility(View.GONE);
                            mIndusttxt.setText(mProfileAboutResponse.getSuccess().get(0).getIndustry() + "-" + mProfileAboutResponse.getSuccess().get(0).getSubProfession());
                        }
                        if (subProfession.startsWith("New vehicle") || subProfession.startsWith("Used vehicle")) {
                            mBrandlay.setVisibility(View.VISIBLE);
                            mBrandtxt.setText(brand);
                        } else {
                            mBrandlay.setVisibility(View.GONE);
                        }
                        if (interest.contains(",")) {
                            String[] commaSplit = interest.split(",");
                            mCount.setText("You have added " + commaSplit.length + " interest");
                        } else {
                            mCount.setText("You have added " + interest.length() + " interest");
                        }
                    } else {
                        if (isAdded())
                            CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
                } else if (response.body() instanceof IndustryResponse) {
                    IndustryResponse moduleResponse = (IndustryResponse) response.body();
                    final List<String> Industry = new ArrayList<>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        Industry.add("Select Industry");
                        for (IndustryResponse.Success message : moduleResponse.getSuccess()) {
                            Industry.add(message.getIndusName());
                        }
                        Industry.add("Other");
                        if (getActivity() != null) {
                            INDUSTRY = new String[Industry.size()];
                            INDUSTRY = (String[]) Industry.toArray(INDUSTRY);
                            ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.registration_spinner, INDUSTRY);
                            industrySpinner.setAdapter(dataadapter);
                        }
                    } else if (isAdded())
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    final List<String> brand = new ArrayList<>();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        brand.add("-Select Brand");
                        for (BrandsTagResponse.Success message : brandsTagResponse.getSuccess()) {
                            brand.add(message.getTagName());
                        }
                        brand.add("Other");
                        if (getActivity() != null) {
                            BRAND = new String[brand.size()];
                            BRAND = (String[]) brand.toArray(BRAND);
                            ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.registration_spinner, BRAND);
                            brandSpinner.setAdapter(dataadapter);
                        }
                    } else if (isAdded())
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                } else if (response.body() instanceof GetUserCategoryResponse) {
                    GetUserCategoryResponse moduleResponse = (GetUserCategoryResponse) response.body();
                    final List<String> Category = new ArrayList<>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        Category.add("Select Category");
                        for (GetUserCategoryResponse.Success message : moduleResponse.getSuccess()) {
                            Category.add(message.getName());
                        }
                        Category.add("Other");
                        if (getActivity() != null) {
                            MODULE = new String[Category.size()];
                            MODULE = (String[]) Category.toArray(MODULE);
                            ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getActivity(), R.layout.registration_spinner, MODULE);
                            moduleSpinner.setAdapter(dataadapter);
                        }
                    } else {
                        if (isAdded())
                            CustomToast.customToast(getActivity(), getString(R.string.no_response));
                    }
                } else {
                    if (isAdded())
                        CustomToast.customToast(getActivity(), getString(R.string._404_));
                }
            } else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
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
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (!str.equals("")) {
            if (str.equals("success_update")) {
                if (isAdded())
                    CustomToast.customToast(getActivity(), "Profile Updated");
                mApiCall.profileAbout(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
                usertypeSpinner.setVisibility(View.GONE);
                industrySpinner.setVisibility(View.GONE);
                moduleSpinner.setVisibility(View.GONE);
                brandSpinner.setVisibility(View.GONE);
                usertypeSpinner.setAdapter(null);
                industrySpinner.setAdapter(null);
                moduleSpinner.setAdapter(null);
                brandSpinner.setAdapter(null);
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
                if (b != null) {
                    mApiCall.profileAbout(b.getString("otherContact"), b.getString("otherContact"));
                } else {
                    mApiCall.profileAbout(Sharedcontact, Sharedcontact);
                }
                mApiCall.getSkills();
                mApiCall.getDesignation();
                mApiCall.getCompany();
                mApiCall.getUserCategories();
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiCall = new ApiCall(getActivity(), this);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference),
                        MODE_PRIVATE).getString("loginContact", "");
                mContact = (TextView) mAbout.findViewById(R.id.contact_no);
                mCount = (TextView) mAbout.findViewById(R.id.count);
                mEditTags = (TextView) mAbout.findViewById(R.id.editTags);
                mProfession = (TextView) mAbout.findViewById(R.id.worked_at);
                mEdit = (ImageView) mAbout.findViewById(R.id.edit);
                mEmail = (EditText) mAbout.findViewById(R.id.email);
                mWebsite = (EditText) mAbout.findViewById(R.id.website);
                mCity = (AutoCompleteTextView) mAbout.findViewById(R.id.address);
                mCompany = (AutoCompleteTextView) mAbout.findViewById(R.id.company_name);
                mDesignation = (AutoCompleteTextView) mAbout.findViewById(R.id.designation);
                mSkills = (MultiAutoCompleteTextView) mAbout.findViewById(R.id.skills);
                mDone = (Button) mAbout.findViewById(R.id.done);
                mUsertypelay = (LinearLayout) mAbout.findViewById(R.id.llusertype);
                mIndustrylay = (LinearLayout) mAbout.findViewById(R.id.llindustry);
                mCategorylay = (LinearLayout) mAbout.findViewById(R.id.llcategory);
                mBrandlay = (LinearLayout) mAbout.findViewById(R.id.llbrand);
                mUsertypetxt = (TextView) mAbout.findViewById(R.id.usertypetxt);
                mIndusttxt = (TextView) mAbout.findViewById(R.id.industrytxt);
                mCattxt = (TextView) mAbout.findViewById(R.id.categorytxt);
                mBrandtxt = (TextView) mAbout.findViewById(R.id.brandtxt);
                mAbouttxt = (EditText) mAbout.findViewById(R.id.about);
                mLinear = (LinearLayout) mAbout.findViewById(R.id.profileAbout);
                usertypeSpinner = (Spinner) mAbout.findViewById(spinnerUsertype);
                industrySpinner = (Spinner) mAbout.findViewById(spinnerindustry);
                moduleSpinner = (Spinner) mAbout.findViewById(spinnerCategory);
                brandSpinner = (Spinner) mAbout.findViewById(spinnerbrand);
                otherIndustryLayout = (TextInputLayout) mAbout.findViewById(R.id.otherIndustryLayout);
                otherCategoryLayout = (TextInputLayout) mAbout.findViewById(R.id.otherCategoryLayout);
                otherbrandlayout = (TextInputLayout) mAbout.findViewById(R.id.otherbrand);

                usertypeSpinner.setOnItemSelectedListener(About.this);
                industrySpinner.setOnItemSelectedListener(About.this);
                moduleSpinner.setOnItemSelectedListener(About.this);
                brandSpinner.setOnItemSelectedListener(About.this);

                mGenericFunctions = new GenericFunctions();
                if (b != null) {
                    mApiCall.profileAbout(b.getString("otherContact"), b.getString("otherContact"));
                    mEdit.setVisibility(View.GONE);
                } else {
                    mApiCall.profileAbout(Sharedcontact, Sharedcontact);
                }
                dialog.show();
                mApiCall.getSkills();
                mApiCall.getDesignation();
                mApiCall.getCompany();
                mApiCall.getUserCategories();
                mApiCall.Industries();
                mApiCall.getBrandTags("both");
        /*Get Designation,Skills,Company From web service*/

                mContact.setEnabled(false);
                mAbouttxt.setEnabled(false);
                mWebsite.setEnabled(false);
                mCity.setEnabled(false);
                mEmail.setEnabled(false);
                mCompany.setEnabled(false);
                mDesignation.setEnabled(false);
                mSkills.setEnabled(false);
                usertypeSpinner.setEnabled(false);


                mSkills.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                mSkills.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                (i == KeyEvent.KEYCODE_ENTER)) {
                            mSkills.setText("" + mSkills.getText().toString() + ",");
                            mSkills.setSelection(mSkills.getText().toString().length());
                            checkSkills();
                            return true;
                        }
                        return false;
                    }
                });

                mEdit.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        getActivity().startActivity(new Intent(getActivity(), EditAllAbout.class));
                    }
                });

                mDone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean webflag = false;
                        mEditTags.setVisibility(View.GONE);
                        mUsertypelay.setVisibility(View.VISIBLE);
                        mIndustrylay.setVisibility(View.VISIBLE);
                        mCategorylay.setVisibility(View.VISIBLE);
                        mBrandlay.setVisibility(View.VISIBLE);
                        mUpdatedCity = mCity.getText().toString();
                        mUpdatedEmail = mEmail.getText().toString();
                        mUpdateAbout = mAbouttxt.getText().toString();
                        mUpdatedWebsite = mWebsite.getText().toString();
                        mUpdatedCompany = mCompany.getText().toString();
                        mUpdatedDesignation = mDesignation.getText().toString();
                        mUpdatedSkills = mSkills.getText().toString().trim();
                        strprofession = usertypeSpinner.getSelectedItem().toString().trim();

                        if (!strprofession.equalsIgnoreCase("Student")) {
                            strIndustry = industrySpinner.getSelectedItem().toString().trim();
                        }
                        if (moduleSpinner.getVisibility() == View.VISIBLE)
                            subProfession = moduleSpinner.getSelectedItem().toString().trim();
                        if (brandSpinner.getVisibility() == View.VISIBLE)
                            brand = brandSpinner.getSelectedItem().toString().trim();
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
                            /*Company Name code*/
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
                            } else if (strprofession.equalsIgnoreCase("Select User Type")) {
                                Toast.makeText(getActivity(), "Please select User type", Toast.LENGTH_LONG).show();
                            } else if ((!strprofession.equalsIgnoreCase("Student") && (strIndustry.equalsIgnoreCase("") || strIndustry.equalsIgnoreCase("Select Industry")))) {
                                Toast.makeText(getActivity(), "Please select industry", Toast.LENGTH_LONG).show();
                            } else if (strIndustry.equalsIgnoreCase("Other") && otherIndustry.getText().toString().trim().equalsIgnoreCase("")) {
                                otherIndustry.setError("Enter Industry");
                            } else if (strIndustry.equalsIgnoreCase("Other") && !otherIndustry.getText().toString().matches("[a-zA-Z ]*")) {
                                otherIndustry.setError("Enter  Valid Industry");
                            }// else if (strIndustry.equalsIgnoreCase("Automotive and vehicle manufacturing") && sub_profession.equalsIgnoreCase("Select Category")) {
                            else if (strIndustry.equalsIgnoreCase("Automotive") && subProfession.equalsIgnoreCase("Select Category")) {
                                Toast.makeText(getActivity(), "Please select category", Toast.LENGTH_LONG).show();
                            } else if (subProfession.equalsIgnoreCase("other") && otherCategory.getText().toString().equalsIgnoreCase("")) {
                                otherCategory.setError("Enter Profession");
                            } else if (subProfession.equalsIgnoreCase("other") && !otherCategory.getText().toString().matches("[a-zA-Z ]*")) {
                                otherCategory.setError("Enter  Valid Profession");
                            } else if ((subProfession.startsWith("New vehicle") || subProfession.startsWith("Used vehicle")) && brand.equalsIgnoreCase("-Select Brand")) {
                                Toast.makeText(getActivity(), "Please select Brand", Toast.LENGTH_LONG).show();
                            } else if (brand.equalsIgnoreCase("other") && otherbrand.getText().toString().equalsIgnoreCase("")) {
                                otherbrand.setError("Enter Brand");
                            } else if (brand.equalsIgnoreCase("other") && !otherbrand.getText().toString().matches("[a-zA-Z ]*")) {
                                otherbrand.setError("Enter  Valid Brand");
                            } else if (subProfession.equalsIgnoreCase("Other")) {
                                subProfession = otherCategory.getText().toString().trim();
                                mApiCall.addOtheruserCategory(subProfession);
                            } else if (brand.equalsIgnoreCase("Other")) {
                                brand = otherbrand.getText().toString().trim();
                                mApiCall.addOtherBrandTags(brand, "both");
                            } else if (strIndustry.equalsIgnoreCase("Other")) {
                                strIndustry = otherIndustry.getText().toString().trim();
                                mApiCall.addOtherIndustry(strIndustry);
                            } else if (subProfession.equalsIgnoreCase("Select Category")) {
                                subProfession = "";
                            } else if (brand.equalsIgnoreCase("-Select Brand")) {
                                brand = "";
                            } else if (strIndustry.equalsIgnoreCase("Select Industry")) {
                                strIndustry = "";
                            } else if (mUpdateAbout.equalsIgnoreCase("") || mUpdateAbout.equalsIgnoreCase(null)) {
                                mAbouttxt.setError("Enter About Name");
                                mAbouttxt.requestFocus();
                            } else {
                                Log.i("InterestsAbout", getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                                        .getString("interest", interest));

                                mApiCall.updateProfile(RegId, mUpdatedEmail, mUpdatedCity, strprofession, subProfession, mUpdatedWebsite, mUpdatedCompany,
                                        mUpdatedDesignation, mUpdatedSkills1, strIndustry, brand, mUpdateAbout, getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                                                .getString("interest", interest), "");

                                mDone.setVisibility(View.GONE);
                                mEdit.setVisibility(View.VISIBLE);
                                mEditTags.setVisibility(View.GONE);
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
                                mAbouttxt.setEnabled(false);
                            }
                        }
                    }
                });

                mEditTags.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AddTags.class);
                        intent.putExtra("interest", interest);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void showIntro(View view, String id, String text, Focus focusType) {
        new MaterialIntroView.Builder(getActivity())
                .enableDotAnimation(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(focusType)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .setListener(this)
                .performClick(true)
                .setInfoText(text)
                .setTarget(view)
                .setUsageId(id) //THIS SHOULD BE UNIQUE ID
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        new PreferencesManager(getActivity().getApplicationContext()).resetAll();
        //expandToolbar();
        mDone.setFocusable(true);
        mDone.setFocusableInTouchMode(true);
        mDone.requestFocus();
        if (b != null) {
            mApiCall.profileAbout(b.getString("otherContact"), b.getString("otherContact"));
            mEdit.setVisibility(View.GONE);
        } else
            mApiCall.profileAbout(Sharedcontact, Sharedcontact);
        if (mDone.getVisibility() == View.VISIBLE)
            showIntro(mDone, INTRO_FOCUS_1, "Please click this...to update profile", Focus.NORMAL);
    }

    @Override
    public void onUserClicked(String s) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case (spinnerUsertype):
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
            case (spinnerindustry):
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
            case (spinnerCategory):
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
            case (spinnerbrand):
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
}
