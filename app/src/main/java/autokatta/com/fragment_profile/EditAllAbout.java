package autokatta.com.fragment_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import autokatta.com.response.BrandsTagResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.GetUserCategoryResponse;
import autokatta.com.response.IndustryResponse;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

import static autokatta.com.R.id.spinnerCategory;
import static autokatta.com.R.id.spinnerUsertype;
import static autokatta.com.R.id.spinnerbrand;
import static autokatta.com.R.id.spinnerindustry;
import static autokatta.com.adapter.GooglePlacesAdapter.resultList;

public class EditAllAbout extends AppCompatActivity implements RequestNotifier, OnClickListener, AdapterView.OnItemSelectedListener {

    ApiCall mApiCall;
    String[] parts;
    String   mUpdatedSkills1;
    String newcompanyname, newdesignation, newskills, strCompany, strDesignation, strskills;
    String email, contact, strprofession, company, designation, subProfession, websitestr, city, skills, interest, brand, strIndustry;
    int RegId;
    GenericFunctions mGenericFunctions;
    EditText mEmail, mWebsite, mAbouttxt;
    AutoCompleteTextView mCity ;
    MultiAutoCompleteTextView mSkills;
    LinearLayout mUsertypelay, mIndustrylay, mCategorylay, mBrandlay;
    TextView mUsertypetxt, mIndusttxt, mCattxt, mBrandtxt, mCount,mCompany,mDesignation;
    ImageView mEdtWorkedat, mEdtAddress, mEdtCompany, mEdtDesignation, mEdtSkills,
            mEdtAbout, mEdtEmail, mEdtWebsite, mEdtIntrest, mDoneAbout, mDoneMail, mDoneWebsite, mDoneSkills, mDoneworkat, mDoneAddress;

    List<String> parsedDataSkills = new ArrayList<>();
    final List<String> mSkillList = new ArrayList<>();
    final HashMap<String, String> mSkillList1 = new HashMap<>();

    EditText otherIndustry;
    EditText otherCategory;
    EditText otherbrand;

    Spinner moduleSpinner, usertypeSpinner, industrySpinner, brandSpinner;
    String[] MODULE = null;
    String[] INDUSTRY = null;
    String[] BRAND = null;
    TextInputLayout otherIndustryLayout, otherCategoryLayout, otherbrandlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_all_about);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Edit Details");
        mApiCall = new ApiCall(this, EditAllAbout.this);
        mGenericFunctions = new GenericFunctions();


        mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));

        mEmail = (EditText) findViewById(R.id.email);
        mWebsite = (EditText) findViewById(R.id.website);
        mCity = (AutoCompleteTextView) findViewById(R.id.address);
        mCompany = (TextView) findViewById(R.id.company_name);
        mDesignation = (TextView) findViewById(R.id.designation);
        mSkills = (MultiAutoCompleteTextView) findViewById(R.id.skills);
        mUsertypelay = (LinearLayout) findViewById(R.id.llusertype);
        mIndustrylay = (LinearLayout) findViewById(R.id.llindustry);
        mCategorylay = (LinearLayout) findViewById(R.id.llcategory);
        mBrandlay = (LinearLayout) findViewById(R.id.llbrand);
        mUsertypetxt = (TextView) findViewById(R.id.usertypetxt);
        mIndusttxt = (TextView) findViewById(R.id.industrytxt);
        mCattxt = (TextView) findViewById(R.id.categorytxt);
        mBrandtxt = (TextView) findViewById(R.id.brandtxt);
        mAbouttxt = (EditText) findViewById(R.id.about);
        mCount = (TextView) findViewById(R.id.count);
        mEdtWorkedat = (ImageView) findViewById(R.id.editworkedat);
        mEdtAddress = (ImageView) findViewById(R.id.editaddress);
        mEdtCompany = (ImageView) findViewById(R.id.editcompany);
        mEdtDesignation = (ImageView) findViewById(R.id.editdesignation);
        mEdtSkills = (ImageView) findViewById(R.id.editskills);
        mEdtAbout = (ImageView) findViewById(R.id.editabout);
        mEdtEmail = (ImageView) findViewById(R.id.editmail);
        mEdtWebsite = (ImageView) findViewById(R.id.editwebsite);
        mEdtIntrest = (ImageView) findViewById(R.id.editintrest);
        mDoneAbout = (ImageView) findViewById(R.id.doneabout);
        mDoneMail = (ImageView) findViewById(R.id.donemail);
        mDoneWebsite = (ImageView) findViewById(R.id.donewebsite);
        mDoneSkills = (ImageView) findViewById(R.id.doneskills);
        mDoneAddress = (ImageView) findViewById(R.id.doneaddress);
        mDoneworkat = (ImageView) findViewById(R.id.doneworkat);


        usertypeSpinner = (Spinner) findViewById(spinnerUsertype);
        industrySpinner = (Spinner) findViewById(spinnerindustry);
        moduleSpinner = (Spinner) findViewById(spinnerCategory);
        brandSpinner = (Spinner) findViewById(spinnerbrand);
        otherIndustryLayout = (TextInputLayout) findViewById(R.id.otherIndustryLayout);
        otherCategoryLayout = (TextInputLayout) findViewById(R.id.otherCategoryLayout);
        otherbrandlayout = (TextInputLayout) findViewById(R.id.otherbrand);

        usertypeSpinner.setOnItemSelectedListener(EditAllAbout.this);
        industrySpinner.setOnItemSelectedListener(EditAllAbout.this);
        moduleSpinner.setOnItemSelectedListener(EditAllAbout.this);
        brandSpinner.setOnItemSelectedListener(EditAllAbout.this);

        mApiCall.getUserCategories();
        mApiCall.Industries();
        mApiCall.getSkills();
        mApiCall.getBrandTags("both");

        mEdtWorkedat.setOnClickListener(EditAllAbout.this);
        mEdtAddress.setOnClickListener(EditAllAbout.this);
        mEdtCompany.setOnClickListener(EditAllAbout.this);
        mEdtDesignation.setOnClickListener(EditAllAbout.this);
        mEdtSkills.setOnClickListener(EditAllAbout.this);
        mEdtAbout.setOnClickListener(EditAllAbout.this);
        mEdtWebsite.setOnClickListener(EditAllAbout.this);
        mEdtIntrest.setOnClickListener(EditAllAbout.this);
        mDoneAbout.setOnClickListener(EditAllAbout.this);
        mDoneMail.setOnClickListener(EditAllAbout.this);
        mDoneWebsite.setOnClickListener(EditAllAbout.this);
        mDoneSkills.setOnClickListener(EditAllAbout.this);
        mDoneAddress.setOnClickListener(EditAllAbout.this);
        mDoneworkat.setOnClickListener(EditAllAbout.this);

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

    }

    public void checkSkills() {
        String text = mSkills.getText().toString();
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        parts = text.split(",");
        if (parts.length > 5) {
            mSkills.setError("You can add maximum five skills");
            mSkills.setFocusable(true);
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
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof ProfileAboutResponse) {
                    ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                    if (!mProfileAboutResponse.getSuccess().isEmpty()) {
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

                        Log.i("RegId-->", "   --->" + RegId);
                        Log.i("SUBProfession-->", "   --->" + subProfession);

                        mEmail.setText(email);
                        mAbouttxt.setText(mProfileAboutResponse.getSuccess().get(0).getAbout());
                        mWebsite.setText(websitestr);
                        mCity.setText(city);
                        mAbouttxt.setEnabled(false);
                        mEmail.setEnabled(false);
                        mWebsite.setEnabled(false);
                        mCity.setEnabled(false);
                        mSkills.setEnabled(false);
                        mCompany.setText(company);
                        mDesignation.setText(designation);
                        mSkills.setText(skills);

                        mUsertypetxt.setText(strprofession);

                        mIndusttxt.setText(mProfileAboutResponse.getSuccess().get(0).getIndustry());
                        //   mCattxt.setText(mProfileAboutResponse.getSuccess().get(0).getSubProfession());
                        //  mBrandtxt.setText(mProfileAboutResponse.getSuccess().get(0).getBrandName());

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

                        CustomToast.customToast(EditAllAbout.this, getString(R.string.no_response));
                    }
                } else if (response.body() instanceof GetSkillsResponse) {
                    GetSkillsResponse mGetSkillsResponse = (GetSkillsResponse) response.body();
                    if (!mGetSkillsResponse.getSuccess().isEmpty()) {
                        mSkillList.clear();
                        mSkillList1.clear();
                        for (GetSkillsResponse.Success skillsResponse : mGetSkillsResponse.getSuccess()) {
                            skillsResponse.setSkillId(skillsResponse.getSkillId());
                            skillsResponse.setSkillNames(skillsResponse.getSkillNames());
                            mSkillList.add(skillsResponse.getSkillNames());
                            mSkillList1.put(skillsResponse.getSkillNames(), skillsResponse.getSkillId());
                        }
                        parsedDataSkills.addAll(mSkillList);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                                R.layout.addproductspinner_color, parsedDataSkills);
                        mSkills.setAdapter(dataAdapter);
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
                        INDUSTRY = new String[Industry.size()];
                        INDUSTRY = (String[]) Industry.toArray(INDUSTRY);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, INDUSTRY);
                        industrySpinner.setAdapter(dataadapter);
                    } else
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                } else if (response.body() instanceof BrandsTagResponse) {
                    BrandsTagResponse brandsTagResponse = (BrandsTagResponse) response.body();
                    final List<String> brand = new ArrayList<>();
                    if (!brandsTagResponse.getSuccess().isEmpty()) {
                        brand.add("-Select Brand");
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
                } else if (response.body() instanceof GetUserCategoryResponse) {
                    GetUserCategoryResponse moduleResponse = (GetUserCategoryResponse) response.body();
                    final List<String> Category = new ArrayList<>();
                    if (!moduleResponse.getSuccess().isEmpty()) {
                        Category.add("Select Category");
                        for (GetUserCategoryResponse.Success message : moduleResponse.getSuccess()) {
                            Category.add(message.getName());
                        }
                        Category.add("Other");
                        MODULE = new String[Category.size()];
                        MODULE = (String[]) Category.toArray(MODULE);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, MODULE);
                        moduleSpinner.setAdapter(dataadapter);
                    } else {
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
                    }
                } else {
                    CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
                }
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("")) {
            if (str.equals("success_update")) {
                CustomToast.customToast(getApplicationContext(), "Profile Updated");
                mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));


            }else  if (str.equals("success_update_About")) {
                CustomToast.customToast(getApplicationContext(), "About Updated");
                mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }else  if (str.equals("success_update_Email")) {
                CustomToast.customToast(getApplicationContext(), "Email Updated");
                mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }else  if (str.equals("success_update_Website")) {
                CustomToast.customToast(getApplicationContext(), "Website Updated");
                mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }else  if (str.equals("success_update_Skills")) {
                CustomToast.customToast(getApplicationContext(), "Skills Updated");
                mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }else  if (str.equals("success_update_Address")) {
                CustomToast.customToast(getApplicationContext(), "Address Updated");
                mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
            }else  if (str.equals("success_update_UserType")) {
                CustomToast.customToast(getApplicationContext(), "User Type Updated");
                mApiCall.profileAbout(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));

                usertypeSpinner.setAdapter(null);
                industrySpinner.setAdapter(null);
                moduleSpinner.setAdapter(null);
                brandSpinner.setAdapter(null);
            }
            }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editworkedat:
                usertypeSpinner.setVisibility(View.VISIBLE);
                mDoneworkat.setVisibility(View.VISIBLE);
                mEdtWorkedat.setVisibility(View.GONE);

                mUsertypelay.setVisibility(View.GONE);
                mIndustrylay.setVisibility(View.GONE);
                mCategorylay.setVisibility(View.GONE);
                mBrandlay.setVisibility(View.GONE);
                break;

            case R.id.editaddress:
                mCity.setAdapter(new GooglePlacesAdapter(EditAllAbout.this, R.layout.simple));
                mDoneAddress.setVisibility(View.VISIBLE);
                mEdtAddress.setVisibility(View.GONE);
                mCity.setEnabled(true);
                mCity.setFocusableInTouchMode(true);
                mCity.setFocusable(true);
                break;

            case R.id.editskills:
                mDoneSkills.setVisibility(View.VISIBLE);
                mEdtSkills.setVisibility(View.GONE);
                mSkills.setEnabled(true);
                mSkills.setFocusableInTouchMode(true);
                mSkills.setFocusable(true);
/*
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
                });*/
                break;

            case R.id.editcompany:
                Intent intent1 = new Intent(EditAllAbout.this, EditCompanyName.class);
                startActivity(intent1);
                break;

            case R.id.editdesignation:
                Intent intent2 = new Intent(EditAllAbout.this, EditDesignation.class);
                startActivity(intent2);
                break;

            case R.id.editabout:
                mDoneAbout.setVisibility(View.VISIBLE);
                mEdtAbout.setVisibility(View.GONE);
                mAbouttxt.setEnabled(true);
                mAbouttxt.setFocusableInTouchMode(true);
                mAbouttxt.setFocusable(true);

                break;

            case R.id.editwebsite:
                mDoneWebsite.setVisibility(View.VISIBLE);
                mEdtWebsite.setVisibility(View.GONE);
                mWebsite.setEnabled(true);
                mWebsite.setFocusableInTouchMode(true);
                mWebsite.setFocusable(true);

                break;

            case R.id.editmail:
                mDoneMail.setVisibility(View.VISIBLE);
                mEdtEmail.setVisibility(View.GONE);
                mEmail.setEnabled(true);
                mEmail.setFocusableInTouchMode(true);
                mEmail.setFocusable(true);
                break;

            case R.id.editintrest:
                Intent intent = new Intent(EditAllAbout.this, AddTags.class);
                intent.putExtra("interest", interest);
                startActivity(intent);
                break;

            case R.id.doneabout:

                if (mAbouttxt.getText().toString().equalsIgnoreCase("") || mAbouttxt.getText().toString().equalsIgnoreCase(null)) {
                    mAbouttxt.setError("Enter About ");
                    mAbouttxt.requestFocus();
                } else {
                    mApiCall.updateProfile(RegId,"","","","","","","","","","", mAbouttxt.getText().toString(),"", "About");
                    mDoneAbout.setVisibility(View.GONE);
                    mEdtAbout.setVisibility(View.VISIBLE);
                    mAbouttxt.setEnabled(false);
                }
                break;

            case R.id.donemail:

                if (!mGenericFunctions.isValidEmail(mEmail.getText().toString())) {
                    mEmail.setError("Invalid Email");
                    mEmail.requestFocus();
                } else if (mEmail.getText().toString().equalsIgnoreCase("") || mEmail.getText().toString().equalsIgnoreCase(null)) {
                    mEmail.setError("Enter Email");
                    mEmail.requestFocus();
                } else {
                    mApiCall.updateProfile(RegId, mEmail.getText().toString(),"","","","","","","","","","","","Email");
                    mDoneMail.setVisibility(View.GONE);
                    mEdtEmail.setVisibility(View.VISIBLE);
                    mEmail.setEnabled(false);
                }
                break;

            case R.id.donewebsite:

                if (!isValidUrl(mWebsite.getText().toString())) {
                    mWebsite.setError("Invalid Website");
                    mWebsite.requestFocus();
                } else  if (mWebsite.getText().toString().equalsIgnoreCase("") || mWebsite.getText().toString().equalsIgnoreCase(null)) {
                    mWebsite.setError("Enter Email");
                    mWebsite.requestFocus();
                } else {
                    mApiCall.updateProfile(RegId,"","","","", mWebsite.getText().toString(),"","","","","","","", "Website");
                    mDoneWebsite.setVisibility(View.GONE);
                    mEdtWebsite.setVisibility(View.VISIBLE);
                    mWebsite.setEnabled(false);

                }
                break;

            case R.id.doneskills:
                String mUpdatedSkills = mSkills.getText().toString().trim();

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
                String splChrs = "-/@#$%^&_+=()";
//                boolean found2 = strCompany.matches("["
  //                   + splChrs + "]+");


                if (mUpdatedSkills.endsWith(",")) {
                    mUpdatedSkills1 = mUpdatedSkills.substring(0, mUpdatedSkills.length() - 1);
                } else {
                    mUpdatedSkills1 = mUpdatedSkills;
                }
                if (strskills.equals("") || strskills.equals("null") || strskills.equals(null)) {
                    mSkills.setError("Enter Skills Name");
                    mSkills.requestFocus();
                }else
                {
                    mApiCall.updateProfile(RegId,"","","","","","","", mUpdatedSkills1,"","","","","Skills");
                    mDoneSkills.setVisibility(View.GONE);
                    mEdtSkills.setVisibility(View.VISIBLE);
                    mSkills.setEnabled(false);

                }

                break;

            case R.id.doneaddress:

                Boolean flag = false;
                try {
                    for (int i = 0; i < resultList.size(); i++) {
                        if (mCity.getText().toString().equalsIgnoreCase(resultList.get(i))) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mCity.getText().toString().isEmpty()) {
                    mCity.setError("Please Enter Address");
                }else
                    if (!flag) {
                        mApiCall.updateProfile(RegId,"", mCity.getText().toString(),"","","","","","","","","","", "Address");
                        mDoneAddress.setVisibility(View.VISIBLE);
                        mEdtAddress.setVisibility(View.GONE);
                        mCity.setEnabled(false);

                    }else {
                        mCity.setError("Please Select Address From Dropdown Only");
                        mCity.requestFocus();
                        }


                break;

            case R.id.doneworkat:

                mUsertypelay.setVisibility(View.VISIBLE);
                mIndustrylay.setVisibility(View.VISIBLE);
                mCategorylay.setVisibility(View.VISIBLE);
                mBrandlay.setVisibility(View.VISIBLE);


       /*         usertypeSpinner.setAdapter(null);
                industrySpinner.setAdapter(null);
                moduleSpinner.setAdapter(null);
                brandSpinner.setAdapter(null);*/

                strprofession = usertypeSpinner.getSelectedItem().toString().trim();

                if (!strprofession.equalsIgnoreCase("Student")) {
                    strIndustry = industrySpinner.getSelectedItem().toString().trim();

                }
                if (moduleSpinner.getVisibility() == View.VISIBLE)
                    subProfession = moduleSpinner.getSelectedItem().toString().trim();


                if (brandSpinner.getVisibility() == View.VISIBLE)
                    brand = brandSpinner.getSelectedItem().toString().trim();

                if (strprofession.equalsIgnoreCase("Select User Type")) {
                    Toast.makeText(getApplicationContext(), "Please select User type", Toast.LENGTH_LONG).show();
                } else if ((!strprofession.equalsIgnoreCase("Student") && (strIndustry.equalsIgnoreCase("") || strIndustry.equalsIgnoreCase("Select Industry")))) {
                    Toast.makeText(getApplicationContext(), "Please select industry", Toast.LENGTH_LONG).show();
                } else if (strIndustry.equalsIgnoreCase("Other") && otherIndustry.getText().toString().trim().equalsIgnoreCase("")) {
                    otherIndustry.setError("Enter Industry");
                } else if (strIndustry.equalsIgnoreCase("Other") && !otherIndustry.getText().toString().matches("[a-zA-Z ]*")) {
                    otherIndustry.setError("Enter  Valid Industry");
                }// else if (strIndustry.equalsIgnoreCase("Automotive and vehicle manufacturing") && sub_profession.equalsIgnoreCase("Select Category")) {
                else if (strIndustry.equalsIgnoreCase("Automotive") && subProfession.equalsIgnoreCase("Select Category")) {
                    Toast.makeText(getApplicationContext(), "Please select category", Toast.LENGTH_LONG).show();
                } else if (subProfession.equalsIgnoreCase("other") && otherCategory.getText().toString().equalsIgnoreCase("")) {
                    otherCategory.setError("Enter Profession");
                } else if (subProfession.equalsIgnoreCase("other") && !otherCategory.getText().toString().matches("[a-zA-Z ]*")) {
                    otherCategory.setError("Enter  Valid Profession");
                } else if ((subProfession.startsWith("New vehicle") || subProfession.startsWith("Used vehicle")) && brand.equalsIgnoreCase("-Select Brand")) {
                    Toast.makeText(getApplicationContext(), "Please select Brand", Toast.LENGTH_LONG).show();
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
                } else {
                    mApiCall.updateProfile(RegId,"","",strprofession,subProfession,"","","","",strIndustry,brand, "","","UserType");

                    mDoneworkat.setVisibility(View.GONE);
                    mEdtWorkedat.setVisibility(View.VISIBLE);

                    usertypeSpinner.setVisibility(View.GONE);
                    industrySpinner.setVisibility(View.GONE);
                    moduleSpinner.setVisibility(View.GONE);
                    brandSpinner.setVisibility(View.GONE);
                }
                break;
        }
    }


/*For user typew spinner*/
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

    public boolean isValidUrl(String txtWebsite) {
        Pattern regex = Pattern.compile("^(WWW|www)\\.+[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|in|IN|COM|ORG|NET|MIL|EDU)$");
        Matcher matcher = regex.matcher(txtWebsite);
        return matcher.matches();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
