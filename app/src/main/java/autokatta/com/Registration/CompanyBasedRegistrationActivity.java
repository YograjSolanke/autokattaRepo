package autokatta.com.Registration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.my_store.CreateStoreFragment;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetCompaniesResponse;
import autokatta.com.response.GetDesignationResponse;
import autokatta.com.response.GetDistrictsResponse;
import autokatta.com.response.GetSkillsResponse;
import autokatta.com.response.GetStatesResponse;
import autokatta.com.response.getDealsResponse;
import retrofit2.Response;

public class CompanyBasedRegistrationActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener ,Multispinner.MultiSpinnerListener,MultiSelectionSpinner.MultiSpinnerListener{

    AutoCompleteTextView autoCompany, autoDesignation;
    MultiAutoCompleteTextView autoSkills, autoDeals;
    Button Next, Cancel;
    String strCompany, strDesignation, updatecompany, updatedesignation, RegiId = "234";
    String skillpart = "", skillid = "";
    String dealpart = "", dealid = "";
    String Skidlist = "", Deidlist = "";
    boolean skillflag = false;
    boolean dealflag = false;
    String Skills = "", Deals = "";
    String result = "";
    String page = "2";
    private String[] MODULE = null;


    final ArrayList<String> mSkillList = new ArrayList<>();
    final HashMap<String, String> mSkillList1 = new HashMap<>();

    final ArrayList<String> mDealList = new ArrayList<>();
    final HashMap<String, String> mDealList1 = new HashMap<>();

    final ArrayList<String> mCompanyList = new ArrayList<>();
    final HashMap<String, String> mCompanyList1 = new HashMap<>();

    final HashMap<String,String> mdistList1 = new HashMap();
    final List<String> distNameList = new ArrayList<String>();


    final HashMap<String,String> mStatelist1 = new HashMap();
    final List<String> stateLst = new ArrayList<String>();

    List<String> parsedDataCompany = new ArrayList<>();
    List<String> parsedDataDesignation = new ArrayList<>();
    List<String> parsedDataSkills = new ArrayList<>();
    List<String> parsedDataDeals = new ArrayList<>();


    final ArrayList<String> mDesignationList = new ArrayList<>();
    final HashMap<String, String> mDesignationList1 = new HashMap<>();


    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    RelativeLayout relativeKms, relativeDistrict, relativeState;
    Spinner spinArea, spinKms;
    MultiSelectionSpinner spinDistrict, spinState;
    String strArea = "", strKms = "", strDistrict = "", strState = "";

    SharedPreferences prefs;
    public static final String MyloginPREFERENCES = "login";

    ApiCall mApiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_based_registration);

        mApiCall = new ApiCall(this, this);
        autoCompany = (AutoCompleteTextView) findViewById(R.id.autocompany);
        autoDesignation = (AutoCompleteTextView) findViewById(R.id.autodesignation);
        autoSkills = (MultiAutoCompleteTextView) findViewById(R.id.autoskills);
        autoDeals = (MultiAutoCompleteTextView) findViewById(R.id.autodeals);
        Next = (Button) findViewById(R.id.btnnext);
        Cancel = (Button) findViewById(R.id.btncancel);
        Next.setOnClickListener(this);
        Cancel.setOnClickListener(this);

        spinArea = (Spinner) findViewById(R.id.spinnerArea);
        spinKms = (Spinner) findViewById(R.id.spinnerKms);

        spinDistrict = (MultiSelectionSpinner) findViewById(R.id.spinnerDistrict);
        spinState = (MultiSelectionSpinner) findViewById(R.id.spinnerState);

        relativeKms = (RelativeLayout) findViewById(R.id.relSpinnerKms);
        relativeDistrict = (RelativeLayout) findViewById(R.id.relSpinnerDistrict);
        relativeState = (RelativeLayout) findViewById(R.id.relSpinnerState);


        prefs = getApplicationContext().getSharedPreferences(MyloginPREFERENCES, Context.MODE_PRIVATE);
        //RegiId = prefs.getString("RegID", "");
        System.out.println("Registration id in company based registeration" + RegiId);

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
                System.out.println("Areaaaaaaaaaaaaaaaaaa" + strArea);

                if (strArea.equalsIgnoreCase("By Kms")) {
                    relativeKms.setVisibility(View.VISIBLE);

                    relativeDistrict.setVisibility(View.GONE);
                    relativeState.setVisibility(View.GONE);
                } else if (strArea.equalsIgnoreCase("By District")) {

                    relativeDistrict.setVisibility(View.VISIBLE);

                    relativeKms.setVisibility(View.GONE);
                    relativeState.setVisibility(View.GONE);

                    mApiCall.getDistricts();
                    // getdistricts();

                } else if (strArea.equalsIgnoreCase("By State")) {
                    relativeState.setVisibility(View.VISIBLE);

                    relativeDistrict.setVisibility(View.GONE);
                    relativeKms.setVisibility(View.GONE);
                    mApiCall.getStates();
                    // getStates();

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


    public void checkSkills() {
        String text = autoSkills.getText().toString();
        System.out.println("texttttttttttttttttt Skills" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss skills" + parts.length);
        if (parts.length > 5) {
            autoSkills.setError("You can add maximum five skills");
        }

    }

    public void checkDeals() {
        String text = autoDeals.getText().toString();
        System.out.println("texttttttttttttttttt Deals" + text.substring(0, text.length() - 1));
        if (text.endsWith(","))
            text = text.substring(0, text.length() - 1);
        String[] parts = text.split(",");
        System.out.println("size of partssssssssssssssssss Deals" + parts.length);
//            if(parts.length>5)
//            {
//                autoDeals.setError("You can add maximum five Deals");
//            }

    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                if (response.body() instanceof GetCompaniesResponse) {
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
                                R.layout.addproductspinner_color, parsedDataCompany);
                        autoCompany.setAdapter(dataAdapter);
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
                        parsedDataDesignation.addAll(mDesignationList);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.addproductspinner_color, parsedDataDesignation);
                        autoDesignation.setAdapter(dataAdapter);
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
                        parsedDataSkills.addAll(mSkillList);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.addproductspinner_color, parsedDataSkills);
                        autoSkills.setAdapter(dataAdapter);
                    }

                } else if (response.body() instanceof getDealsResponse) {
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
                                R.layout.addproductspinner_color, parsedDataDeals);
                        autoDeals.setAdapter(dataAdapter);
                    }

                }
                else if (response.body() instanceof GetDistrictsResponse) {
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

                }
                else if (response.body() instanceof GetStatesResponse) {
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

            }
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
            Log.i("Check Class-", "Company Based Registration");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (!str.equals("null")) {
            if (str.equals("success")) {
                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();

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


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompanyBasedRegistrationActivity.this);

                // set title
                alertDialogBuilder.setTitle("Intrest Area ");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Create Business Profile ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                    Bundle b = new Bundle();
                                    b.putString("call","interestbased");
                                    CreateStoreFragment fr = new CreateStoreFragment();
                                    fr.setArguments(b);
                                        finish();
                                    dialog.cancel();

                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        dialog.cancel();

                                        Intent i = new Intent(getApplicationContext(), CompanyBasedInvitation.class);
                                        startActivity(i);
                                        finish();

                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            } else {
                Toast.makeText(getApplicationContext(), "Please Check Whether all Fields Are filled ", Toast.LENGTH_LONG).show();

            }
        }else {
            Toast.makeText(getApplicationContext(), "Somthing went wrong no response", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnnext:
                try {
                    strArea = spinArea.getSelectedItem().toString();

                    System.out.println("***************Area Before:" + strArea + "Kms:" + strKms + "District:" + strDistrict + "State:" + strState);
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
                System.out.println("***************Area After:" + strArea + "Kms:" + strKms + "District:" + strDistrict + "State:" + strState);

//                    updateRegisteration(strArea, strKms, strDistrict, strState);

                /******************************** Skills code *****************************************************/

                String strSkill = autoSkills.getText().toString();
                ArrayList<String> skillList = new ArrayList<>();
                ArrayList<String> otherSkills = new ArrayList<>();

                if (strSkill.endsWith(","))
                    strSkill = strSkill.substring(0, strSkill.length() - 1);
                System.out.println("Skill=" + strSkill);
                strSkill = strSkill.trim();
                System.out.println("Skill after trim.............................." + strSkill);

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
                        System.out.println("skill going to add=" + skillpart);

                        mApiCall.addNewSkills(skillpart);
                        // addnewskills(skillpart);
                    }


                }

                System.out.println("skillnames array before change***************" + mSkillList);
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

                System.out.println("idddddddddd" + Skidlist);
                System.out.println("skillnames array after change****************" + mSkillList);
                // System.out.println("id array" + SkillId);


                if (!autoSkills.getText().toString().equalsIgnoreCase("") && Skidlist.length() > 0) {
                    Skidlist = Skidlist.substring(1);
                    System.out.println("substring idddddddddd=" + Skidlist);


                }
                if (skillflag) {
                    skillid = skillid.substring(1);
                    System.out.println("response skill iddddddddddddddd=" + skillid);
                    if (!Skidlist.equalsIgnoreCase(""))
                        Skidlist = Skidlist + "," + skillid;
                    else
                        Skidlist = skillid;
                    System.out.println("final Skidlist iddddddddddddddd=" + Skidlist);

                }

//                if (autoSkills.getText().toString().equalsIgnoreCase(""))
//                    autoSkills.setError("Please provide atleast 1 and maximum 5 skills");

                /**********************************************Ends Skill Code ********************************************/

                /***********************************************Dealing  code *********************************************/


                String strDeal = autoDeals.getText().toString();
                ArrayList<String> dealList = new ArrayList<>();
                ArrayList<String> otherDeals = new ArrayList<>();

                if (strDeal.endsWith(","))
                    strDeal = strDeal.substring(0, strDeal.length() - 1);
                System.out.println("Deal=" + strDeal);
                strDeal = strDeal.trim();
                System.out.println("Deal after trim.............................." + strDeal);

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
                        System.out.println("skill going to add=" + dealpart);

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

                System.out.println("idddddddddd" + Deidlist);
                //  System.out.println("skillnames array after change****************" + dealNames);
                //System.out.println("id array" + dealIdsList);


                if (!autoDeals.getText().toString().equalsIgnoreCase("") && Deidlist.length() > 0) {
                    Deidlist = Deidlist.substring(1);
                    System.out.println("substring idddddddddd=" + Deidlist);
                }
                if (dealflag) {
                    dealid = dealid.substring(1);
                    System.out.println("response skill iddddddddddddddd=" + dealid);
                    if (!Deidlist.equalsIgnoreCase(""))
                        Deidlist = Deidlist + "," + dealid;
                    else
                        Deidlist = dealid;
                    System.out.println("final idlist iddddddddddddddd=" + Deidlist);

                }

//                if (autoDeals.getText().toString().equalsIgnoreCase(""))
//                    autoDeals.setError("Please provide atleast 1 and maximum 5 deals");

                /************************************** End of Dealing with code *****************************************/

                /************************Company Name code *******************************************/
                autoCompany.clearFocus();
                updatecompany = autoCompany.getText().toString();


                if (!mCompanyList.contains(updatecompany)) {
                    // addnewcompanyName(updatecompany);
                    mApiCall.addNewCompany(updatecompany);
                    //addNewCompanyName(updatecompany);
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
                            // addnewDesignation(updatedesignation);
                            // addNewDesignation(updatedesignation);
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
                    }

                    else if (found1) {

                        autoDesignation.setError("Please enter valid designation");
                        autoDesignation.requestFocus();
                    } else
                    if (strDesignation.equals("") || strDesignation.equals("null") || strDesignation.equals(null)) {
                        autoDesignation.setError("Enter Designation Name");
                        autoDesignation.requestFocus();
                    }else if (strSkill.equals("") || strSkill.equals("null") || strSkill.equals(null)) {
                        autoSkills.setError("Enter Skills Name");
                        autoSkills.requestFocus();
                    }else if (strDeal.equals("") || strDeal.equals("null") || strDeal.equals(null)) {
                        autoDeals.setError("Enter Deals Name");
                        autoDeals.requestFocus();
                    }


                }else
                {
                    mApiCall.updateRegistration(RegiId, page, strArea, strKms, strDistrict, strState, autoCompany.getText().toString(), autoDesignation.getText().toString(), Skills, Deals);
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
                break;
        }
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }
    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),ContinueNextRegistration.class);
        startActivity(i);
        finish();
        }

    }

