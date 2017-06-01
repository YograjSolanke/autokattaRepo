package autokatta.com.register;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.MultiSelectionSpinner;
import autokatta.com.Registration.Multispinner;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.GetCompaniesResponse;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetDistrictsResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.GetStatesResponse;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.getDealsResponse;
import retrofit2.Response;

public class RegistrationCompanyBased extends AppCompatActivity implements RequestNotifier, View.OnClickListener
        , Multispinner.MultiSpinnerListener, MultiSelectionSpinner.MultiSpinnerListener {

    AutoCompleteTextView autoCompany, autoDesignation;
    MultiAutoCompleteTextView autoSkills, autoDeals;
    Button Next, Cancel;
    String strCompany, strDesignation, updatecompany, updatedesignation, RegiId = "", skillpart = "", skillid = "",
            dealpart = "", dealid = "", Skidlist = "", Deidlist = "", Skills = "", Deals = "", page = "2";
    boolean skillflag = false, dealflag = false;

    ArrayList<String> mSkillList = new ArrayList<>();
    HashMap<String, String> mSkillList1 = new HashMap<>();

    ArrayList<String> mDealList = new ArrayList<>();
    HashMap<String, String> mDealList1 = new HashMap<>();

    ArrayList<String> mCompanyList = new ArrayList<>();
    HashMap<String, String> mCompanyList1 = new HashMap<>();

    HashMap<String, String> mdistList1 = new HashMap<>();
    final List<String> distNameList = new ArrayList<>();

    HashMap<String, String> mStatelist1 = new HashMap<>();
    List<String> stateLst = new ArrayList<>();

    List<String> parsedDataCompany = new ArrayList<>();
    List<String> parsedDataDesignation = new ArrayList<>();
    List<String> parsedDataSkills = new ArrayList<>();
    List<String> parsedDataDeals = new ArrayList<>();

    ArrayList<String> mDesignationList = new ArrayList<>();
    HashMap<String, String> mDesignationList1 = new HashMap<>();

    HashMap<String, String> mCategoryListHash = new HashMap<>();
    List<String> mCategoryLis = new ArrayList<>();
    List<String> parsedDataCategory = new ArrayList<>();
    String categoryId = "";
    String categoryName = "";

    HashMap<String, String> mSubCategoryListHash = new HashMap<>();
    List<String> mSubCategoryList = new ArrayList<>();
    List<String> parsedDataSubCategory = new ArrayList<>();
    String subCategoryId = "";
    String subCategoryName = "";

    HashMap<String, String> mBrandListHash = new HashMap<>();
    List<String> mBrandList = new ArrayList<>();
    List<String> parsedDataBrand = new ArrayList<>();
    String brandId = "";
    String brandName = "";

    RelativeLayout relativeKms, relativeDistrict, relativeState;
    Spinner spinArea, spinKms, spinCategory, spinSubCategory, spinManufacturer;
    MultiSelectionSpinner spinDistrict, spinState;
    String strArea = "", strKms = "", strDistrict = "", strState = "";

    SharedPreferences prefs;
    public static final String MyloginPREFERENCES = "login";

    ApiCall mApiCall;
    RelativeLayout relativeDealingLayout, mCompanyBased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_company_based);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mApiCall = new ApiCall(this, this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                autoCompany = (AutoCompleteTextView) findViewById(R.id.autocompany);
                autoDesignation = (AutoCompleteTextView) findViewById(R.id.autodesignation);
                autoSkills = (MultiAutoCompleteTextView) findViewById(R.id.autoskills);
                autoDeals = (MultiAutoCompleteTextView) findViewById(R.id.autodeals);
                Next = (Button) findViewById(R.id.btnnext);
                Cancel = (Button) findViewById(R.id.btncancel);
                relativeDealingLayout = (RelativeLayout) findViewById(R.id.relnewvehidealer);

                spinArea = (Spinner) findViewById(R.id.spinnerArea);
                spinKms = (Spinner) findViewById(R.id.spinnerKms);
                spinCategory = (Spinner) findViewById(R.id.spinnerCatagory);
                spinSubCategory = (Spinner) findViewById(R.id.spinnerSubCatagory);
                spinManufacturer = (Spinner) findViewById(R.id.spinnerManufacture);

                spinDistrict = (MultiSelectionSpinner) findViewById(R.id.spinnerDistrict);
                spinState = (MultiSelectionSpinner) findViewById(R.id.spinnerState);

                relativeKms = (RelativeLayout) findViewById(R.id.relSpinnerKms);
                relativeDistrict = (RelativeLayout) findViewById(R.id.relSpinnerDistrict);
                relativeState = (RelativeLayout) findViewById(R.id.relSpinnerState);
                mCompanyBased = (RelativeLayout) findViewById(R.id.company_based);

                prefs = getApplicationContext().getSharedPreferences(MyloginPREFERENCES, Context.MODE_PRIVATE);
                RegiId = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginregistrationid", "");

                mApiCall.getCompany();
                mApiCall.getDeals();
                mApiCall.getDesignation();
                mApiCall.getSkills();
                autoSkills.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                autoSkills.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                (i == KeyEvent.KEYCODE_ENTER)) {
                            autoSkills.setText(autoSkills.getText().toString() + ",");
                            autoSkills.setSelection(autoSkills.getText().toString().length());
                            checkSkills();
                            return true;
                        }
                        return false;
                    }
                });

                autoSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        checkSkills();
                    }
                });

                //Dealing choice code
                autoDeals.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                autoDeals.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                (i == KeyEvent.KEYCODE_ENTER)) {
                            autoDeals.setText(autoDeals.getText().toString() + ",");
                            autoDeals.setSelection(autoDeals.getText().toString().length());
                            checkDeals();
                            return true;
                        }
                        return false;
                    }
                });

                autoDeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        checkDeals();
                    }
                });

                spinArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        strArea = spinArea.getSelectedItem().toString();
                        if (strArea.equalsIgnoreCase("By Kms")) {
                            relativeKms.setVisibility(View.VISIBLE);
                            relativeDistrict.setVisibility(View.GONE);
                            relativeState.setVisibility(View.GONE);
                        } else if (strArea.equalsIgnoreCase("By District")) {
                            relativeDistrict.setVisibility(View.VISIBLE);
                            relativeKms.setVisibility(View.GONE);
                            relativeState.setVisibility(View.GONE);
                            mApiCall.getDistricts();
                        } else if (strArea.equalsIgnoreCase("By State")) {
                            relativeState.setVisibility(View.VISIBLE);
                            relativeDistrict.setVisibility(View.GONE);
                            relativeKms.setVisibility(View.GONE);
                            mApiCall.getStates();
                        } else {
                            relativeKms.setVisibility(View.GONE);
                            relativeDistrict.setVisibility(View.GONE);
                            relativeState.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
        Next.setOnClickListener(this);
        Cancel.setOnClickListener(this);
    }

    public void checkSkills() {
        String text = autoSkills.getText().toString();
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        if (parts.length > 5) {
            autoSkills.setError("You can add maximum five skills");
        }

    }

    public void checkDeals() {
        String text = autoDeals.getText().toString();
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");

        for (String part : parts) {
            if (part.equalsIgnoreCase("New Vehicle")) {
                relativeDealingLayout.setVisibility(View.VISIBLE);
                getVehicleList();
            }
        }
    }

    /*
        Get Vehicle List...
         */
    private void getVehicleList() {
        mApiCall.getVehicleList();
    }

    /*
    Sub Category...
     */
    private void getSubCategoryTask(String categoryId) {
        mApiCall.getVehicleSubtype(categoryId);
    }

    /*
    Get Brand
     */
    private void getBrand(String categoryId, String subcategoryId) {
        mApiCall.getBrand(categoryId, subcategoryId);
    }

    /*
    Get Model...
     */
    private void getModel(String categoryId, String subCategoryId, String brandId) {
        mApiCall.getModel(categoryId, subCategoryId, brandId);
    }

    /*
    Add Brand
     */
    private void AddBrand(String keyword, String title, String categoryId, String subCatID) {
        mApiCall.addBrand(keyword, title, categoryId, subCatID);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof GetCompaniesResponse) {
                    mCompanyList.clear();
                    GetCompaniesResponse mGetCompanyList = (GetCompaniesResponse) response.body();
                    if (!mGetCompanyList.getSuccess().isEmpty()) {
                        for (GetCompaniesResponse.Success companyResponse : mGetCompanyList.getSuccess()) {
                            companyResponse.setCompid(companyResponse.getCompid());
                            companyResponse.setCompanyName(companyResponse.getCompanyName());
                            mCompanyList.add(companyResponse.getCompanyName());
                            mCompanyList1.put(companyResponse.getCompanyName(), companyResponse.getCompid());
                        }
                        parsedDataCompany.addAll(mCompanyList);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.registration_spinner, parsedDataCompany);
                        autoCompany.setAdapter(dataAdapter);
                    }

                } else if (response.body() instanceof GetDesignationResponse) {
                    mDesignationList.clear();
                    GetDesignationResponse mGetDesignationList = (GetDesignationResponse) response.body();
                    if (!mGetDesignationList.getSuccess().isEmpty()) {
                        for (GetDesignationResponse.Success designationResponse : mGetDesignationList.getSuccess()) {
                            designationResponse.setDesgId(designationResponse.getDesgId());
                            designationResponse.setDesignationName(designationResponse.getDesignationName());
                            mDesignationList.add(designationResponse.getDesignationName());
                            mDesignationList1.put(designationResponse.getDesignationName(), designationResponse.getDesgId());
                        }
                        parsedDataDesignation.addAll(mDesignationList);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.registration_spinner, parsedDataDesignation);
                        autoDesignation.setAdapter(dataAdapter);
                    }

                } else if (response.body() instanceof GetSkillsResponse) {
                    mSkillList.clear();
                    GetSkillsResponse mGetSkillsResponse = (GetSkillsResponse) response.body();
                    if (!mGetSkillsResponse.getSuccess().isEmpty()) {
                        for (GetSkillsResponse.Success skillsResponse : mGetSkillsResponse.getSuccess()) {
                            skillsResponse.setSkillId(skillsResponse.getSkillId());
                            skillsResponse.setSkillNames(skillsResponse.getSkillNames());
                            mSkillList.add(skillsResponse.getSkillNames());
                            mSkillList1.put(skillsResponse.getSkillNames(), skillsResponse.getSkillId());
                        }
                        parsedDataSkills.addAll(mSkillList);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.registration_spinner, parsedDataSkills);
                        autoSkills.setAdapter(dataAdapter);
                    }

                } else if (response.body() instanceof getDealsResponse) {
                    mDealList.clear();
                    getDealsResponse mGetDealsList = (getDealsResponse) response.body();
                    if (!mGetDealsList.getSuccess().isEmpty()) {
                        for (getDealsResponse.Success dealsResponse : mGetDealsList.getSuccess()) {
                            dealsResponse.setDealId(dealsResponse.getDealId());
                            dealsResponse.setDealNames(dealsResponse.getDealNames());
                            mDealList.add(dealsResponse.getDealNames());
                            mDealList1.put(dealsResponse.getDealNames(), dealsResponse.getDealId());
                        }
                        parsedDataDeals.addAll(mDealList);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.registration_spinner, parsedDataDeals);
                        autoDeals.setAdapter(dataAdapter);
                    }

                } else if (response.body() instanceof GetDistrictsResponse) {
                    distNameList.clear();
                    GetDistrictsResponse mGetDistrict = (GetDistrictsResponse) response.body();
                    if (!mGetDistrict.getSuccess().isEmpty()) {
                        for (GetDistrictsResponse.Success DistrictResponse : mGetDistrict.getSuccess()) {
                            DistrictResponse.setDistId(DistrictResponse.getDistId());
                            DistrictResponse.setDistName(DistrictResponse.getDistName());
                            distNameList.add(DistrictResponse.getDistName());
                            mdistList1.put(DistrictResponse.getDistName(), DistrictResponse.getDistId());
                        }
                        spinDistrict.setItems(distNameList, "Select District", this);
                    }
                } else if (response.body() instanceof GetStatesResponse) {
                    stateLst.clear();
                    GetStatesResponse mGetState = (GetStatesResponse) response.body();
                    if (!mGetState.getSuccess().isEmpty()) {
                        for (GetStatesResponse.Success StateResponse : mGetState.getSuccess()) {
                            StateResponse.setStateId(StateResponse.getStateId());
                            StateResponse.setStateName(StateResponse.getStateName());
                            stateLst.add(StateResponse.getStateName());
                            mStatelist1.put(StateResponse.getStateName(), StateResponse.getStateId());
                        }
                        spinState.setItems(distNameList, "Select State", this);
                    }
                }

            /*Category data*/
                else if (response.body() instanceof GetVehicleListResponse) {
                    mCategoryLis.clear();
                    mCategoryListHash.clear();
                    parsedDataCategory.clear();

                    mCategoryLis.add("Select Vehicle Types");
                    GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                    if (!mGetVehicleListResponse.getSuccess().isEmpty()) {
                        for (GetVehicleListResponse.Success mSuccess : mGetVehicleListResponse.getSuccess()) {
                            mSuccess.setId(mSuccess.getId());
                            mSuccess.setName(mSuccess.getName());
                            mCategoryLis.add(mSuccess.getName());
                            mCategoryListHash.put(mSuccess.getName(), mSuccess.getId());
                        }

                        if (getApplicationContext() != null) {
                            parsedDataCategory.addAll(mCategoryLis);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.registration_spinner, parsedDataCategory);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinCategory.setAdapter(adapter);
                            spinSubCategory.setAdapter(null);
                            spinManufacturer.setAdapter(null);
                        }
                        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    categoryId = mCategoryListHash.get(parsedDataCategory.get(position));
                                    categoryName = parsedDataCategory.get(position);
                                    getSubCategoryTask(categoryId);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }

                /*
                  Vehicle sub type 
                */
                else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    mSubCategoryList.clear();
                    mSubCategoryListHash.clear();
                    parsedDataSubCategory.clear();

                    mSubCategoryList.add("Select Sub Category");
                    GetVehicleSubTypeResponse mGetVehicleSubTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    for (GetVehicleSubTypeResponse.Success subTypeResponse : mGetVehicleSubTypeResponse.getSuccess()) {
                        subTypeResponse.setId(subTypeResponse.getId());
                        subTypeResponse.setName(subTypeResponse.getName());
                        mSubCategoryList.add(subTypeResponse.getName());
                        mSubCategoryListHash.put(subTypeResponse.getName(), subTypeResponse.getId());
                    }
                    parsedDataSubCategory.addAll(mSubCategoryList);
                    if (getApplicationContext() != null) {
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, parsedDataSubCategory);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinSubCategory.setAdapter(adapter);
                        spinManufacturer.setAdapter(null);
                    }
                    spinSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                subCategoryId = mSubCategoryListHash.get(parsedDataSubCategory.get(position));
                                subCategoryName = parsedDataSubCategory.get(position);
                                getBrand(categoryId, subCategoryId);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                /* Vehicle Manufacture/Brand */
                else if (response.body() instanceof GetVehicleBrandResponse) {
                    mBrandList.clear();
                    mBrandListHash.clear();
                    parsedDataBrand.clear();

                    mBrandList.add("Select Brands");
                    GetVehicleBrandResponse getVehicleBrandResponse = (GetVehicleBrandResponse) response.body();
                    for (GetVehicleBrandResponse.Success brandResponse : getVehicleBrandResponse.getSuccess()) {
                        brandResponse.setBrandId(brandResponse.getBrandId());
                        brandResponse.setBrandTitle(brandResponse.getBrandTitle());
                        mBrandList.add(brandResponse.getBrandTitle());
                        mBrandListHash.put(brandResponse.getBrandTitle(), brandResponse.getBrandId());
                    }
                    mBrandList.add("other");
                    parsedDataBrand.addAll(mBrandList);

                    if (getApplicationContext() != null) {
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(getApplicationContext(), R.layout.addproductspinner_color, parsedDataBrand);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinManufacturer.setAdapter(adapter);
                    }
                    spinManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                brandId = mBrandListHash.get(parsedDataBrand.get(position));
                                brandName = parsedDataBrand.get(position);
                            }

                            if (parsedDataBrand.get(position).equalsIgnoreCase("other")) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getApplicationContext());
                                alertDialog.setTitle("Add Brand");
                                alertDialog.setMessage("Enter brand name");

                                final EditText input = new EditText(getApplicationContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

                                alertDialog.setPositiveButton("Add Brand",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String edbrand = input.getText().toString();
                                                if (edbrand.equals(""))
                                                    Toast.makeText(getApplicationContext(), "Please enter brand", Toast.LENGTH_LONG).show();
                                                else
                                                    AddBrand("Brand", edbrand, categoryId, subCategoryId);

                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                spinManufacturer.setSelection(0);
                                                dialog.dismiss();
                                            }
                                        });

                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        }
    }


    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mCompanyBased, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mCompanyBased, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mCompanyBased, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mCompanyBased, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mCompanyBased, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-", "Company Based Registration");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "success":
                    Snackbar.make(mCompanyBased, "Registered", Snackbar.LENGTH_SHORT).show();
                    Skills = autoSkills.getText().toString().trim();
                    Deals = autoDeals.getText().toString().trim();
                    if (strArea.equalsIgnoreCase("Select Area Of Operations"))
                        strArea = "";
                    if (strKms.equalsIgnoreCase("By Kms"))
                        strKms = "";
                    if (strDistrict.equalsIgnoreCase("By District"))
                        strDistrict = "";
                    if (strState.equalsIgnoreCase("By State"))
                        strState = "";

                    strDistrict = strDistrict.replaceAll(" ", "");
                    strState = strState.replaceAll(" ", "");
                    Skills = Skills.replaceAll(" ", "");
                    // Deals = Deals.replaceAll(" ","");

                    spinArea.setSelection(0);
                    spinKms.setSelection(0);
                    spinDistrict.setSelection(0);
                    spinState.setSelection(0);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationCompanyBased.this);
                    // set title
                    alertDialogBuilder.setTitle("Interest Area ");
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Create Business Profile ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                            Bundle b = new Bundle();
                                            b.putString("className", "interestbased");
                                            Intent intent = new Intent(getApplicationContext(), CreateStoreContainer.class);
                                            intent.putExtras(b);
                                            startActivity(intent, options.toBundle());
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                            Intent i = new Intent(getApplicationContext(), InvitationCompanyBased.class);
                                            startActivity(i, options.toBundle());
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                    break;

                case "success_brand_add":
                    Snackbar.make(mCompanyBased, "Brand Added", Snackbar.LENGTH_SHORT).show();
                    getBrand(categoryId, subCategoryId);
                    Log.i("msg", "Brand added successfully");
                    break;

                default:
                    Snackbar.make(mCompanyBased, "Check all fields are filled", Snackbar.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Snackbar.make(mCompanyBased, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnnext:
                try {
                    strArea = spinArea.getSelectedItem().toString();
                    if (strArea.equalsIgnoreCase("Select Area Of Operations")) {
                        strArea = "";
                        strKms = "";
                        strDistrict = "";
                        strState = "";
                    } else {
                        strArea = spinArea.getSelectedItem().toString();
                        if (!strArea.equals("By Kms")) {
                            strKms = "";
                        } else {
                            strKms = spinKms.getSelectedItem().toString();
                        }
                        if (!strArea.equals("By District")) {
                            strDistrict = "";
                        } else {
                            strDistrict = spinDistrict.getSelectedItem().toString();
                        }
                        if (!strArea.equals("By State")) {
                            strState = "";
                        } else {
                            strState = spinState.getSelectedItem().toString();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /******************************** Skills code *****************************************************/

                String strSkill = autoSkills.getText().toString();
                ArrayList<String> skillList = new ArrayList<>();
                ArrayList<String> otherSkills = new ArrayList<>();

                if (strSkill.endsWith(","))
                    strSkill = strSkill.substring(0, strSkill.length() - 1);
                System.out.println("Skill=" + strSkill);
                strSkill = strSkill.trim();

                String[] parts = strSkill.split(",");
                for (int l = 0; l < parts.length; l++) {
                    System.out.println(parts[l]);
                    skillpart = parts[l].trim();

                    if (!skillpart.equalsIgnoreCase("") && !skillpart.equalsIgnoreCase(" "))
                        skillList.add(skillpart);

                    //if skill names not contains the skill provided by user
                    if (!mSkillList.contains(skillpart) && !skillpart.equalsIgnoreCase("") && !skillpart.equalsIgnoreCase(" ")) {
                        //add new skill in our database.
                        otherSkills.add(skillpart);
                        mApiCall.addNewSkills(skillpart);
                    }
                }

                //new getSkils().execute();
                mApiCall.getSkills();
                // getskills();
                for (int i = 0; i < skillList.size(); i++) {
                    for (int j = 0; j < mSkillList.size(); j++) {
                        if (skillList.get(i).equalsIgnoreCase(mSkillList.get(j)))
                            //Skidlist = Skidlist + "," + mSkillList1.get(j);
                            Skidlist = Skidlist + "," + mSkillList1.get(parsedDataSkills.get(j));
                    }
                }
                // System.out.println("id array" + SkillId);
                if (!autoSkills.getText().toString().equalsIgnoreCase("") && Skidlist.length() > 0) {
                    Skidlist = Skidlist.substring(1);
                }
                if (skillflag) {
                    skillid = skillid.substring(1);
                    if (!Skidlist.equalsIgnoreCase(""))
                        Skidlist = Skidlist + "," + skillid;
                    else
                        Skidlist = skillid;
                }

                /**********************************************Ends Skill Code ********************************************/

                /***********************************************Dealing  code *********************************************/

                String strDeal = autoDeals.getText().toString();
                ArrayList<String> dealList = new ArrayList<>();
                ArrayList<String> otherDeals = new ArrayList<>();

                if (strDeal.endsWith(","))
                    strDeal = strDeal.substring(0, strDeal.length() - 1);

                strDeal = strDeal.trim();
                String[] partsDeal = strDeal.split(",");

                for (int l = 0; l < partsDeal.length; l++) {
                    System.out.println(partsDeal[l]);
                    dealpart = partsDeal[l].trim();
                    if (!dealpart.equalsIgnoreCase("") && !dealpart.equalsIgnoreCase(" "))
                        dealList.add(dealpart);
                    //if skill names not contains the skill provided by user
                    if (!mDealList.contains(dealpart) && !dealpart.equalsIgnoreCase("") && !dealpart.equalsIgnoreCase(" ")) {
                        //add new skill in our database.
                        otherDeals.add(dealpart);
                        // addnewDealsVolley(dealpart);
                        mApiCall.addNewDeal(dealpart);
                    }
                }
                mApiCall.getDeals();
                //  getDealsVolley();
                for (int i = 0; i < dealList.size(); i++) {

                    for (int j = 0; j < mDealList.size(); j++) {
                        if (dealList.get(i).equalsIgnoreCase(mDealList.get(j)))
                            //  Deidlist = Deidlist + "," + mDealList1.get(j);
                            Deidlist = Deidlist + "," + mDealList1.get(parsedDataSkills.get(j));
                    }
                }

                if (!autoDeals.getText().toString().equalsIgnoreCase("") && Deidlist.length() > 0) {
                    Deidlist = Deidlist.substring(1);
                }
                if (dealflag) {
                    dealid = dealid.substring(1);
                    if (!Deidlist.equalsIgnoreCase(""))
                        Deidlist = Deidlist + "," + dealid;
                    else
                        Deidlist = dealid;

                }
                /************************************** End of Dealing with code *****************************************/

                /************************Company Name code *******************************************/
                autoCompany.clearFocus();
                updatecompany = autoCompany.getText().toString();
                if (!mCompanyList.contains(updatecompany)) {
                    // addnewcompanyName(updatecompany);
                    mApiCall.addNewCompany(updatecompany);
                    //addNewCompanyName(updatecompany);
                }
                strCompany = autoCompany.getText().toString();
                //***************************************************************
                String splChrs = "-/@#$%^&_+=()";
                boolean found = strCompany.matches("["
                        + splChrs + "]+");
             /*Designation Name code */
                autoDesignation.clearFocus();
                updatedesignation = autoDesignation.getText().toString();
                try {
                    if (!mDesignationList.contains(updatedesignation)) {
                        mApiCall.addNewDesignation(updatedesignation);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                strDesignation = autoDesignation.getText().toString();
                //***************************************************************

                boolean found1 = strDesignation.matches("["
                        + splChrs + "]+");

//                //setting validation for reg fields
                if (strCompany.equals("") || strCompany.equals("null") || strCompany.equals(null)) {
                    autoCompany.setError("Enter Company Name");
                    autoCompany.requestFocus();
                } else if (found) {
                    autoCompany.setError("Please enter valid company");
                    autoCompany.requestFocus();
                } else if (found1) {
                    autoDesignation.setError("Please enter valid designation");
                    autoDesignation.requestFocus();
                } else if (strDesignation.equals("") || strDesignation.equals("null") || strDesignation.equals(null)) {
                    autoDesignation.setError("Enter Designation Name");
                    autoDesignation.requestFocus();
                } else if (strSkill.equals("") || strSkill.equals("null") || strSkill.equals(null)) {
                    autoSkills.setError("Enter Skills Name");
                    autoSkills.requestFocus();
                } else if (strDeal.equals("") || strDeal.equals("null") || strDeal.equals(null)) {
                    autoDeals.setError("Enter Deals Name");
                    autoDeals.requestFocus();
                } else {
                    mApiCall.updateRegistration(RegiId, page, strArea, strKms, strDistrict, strState,
                            autoCompany.getText().toString(), autoDesignation.getText().toString(), strSkill,
                            strDeal, categoryName, subCategoryName, brandName);
                }

                break;
            case R.id.btncancel:
                autoCompany.setText("");
                autoCompany.setError(null);
                autoDesignation.setText("");
                autoDesignation.setError(null);
                autoSkills.setText("");
                autoSkills.setError(null);
                autoDeals.setText("");
                autoDeals.setError(null);
                autoCompany.requestFocus();
                spinArea.setSelection(0);
                spinKms.setSelection(0);
                spinDistrict.setSelection(0);
                spinState.setSelection(0);
                spinCategory.setSelection(0);
                spinSubCategory.setSelection(0);
                spinManufacturer.setSelection(0);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationCompanyBased.this);
                // set title
                alertDialogBuilder.setTitle("Interest Area ");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Create Business Profile ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                        Bundle b = new Bundle();
                                        b.putString("className", "interestbased");
                                        Intent intent = new Intent(getApplicationContext(), CreateStoreContainer.class);
                                        intent.putExtras(b);
                                        startActivity(intent, options.toBundle());
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                        Intent i = new Intent(getApplicationContext(), InvitationCompanyBased.class);
                                        startActivity(i, options.toBundle());
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

                break;
        }
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

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
