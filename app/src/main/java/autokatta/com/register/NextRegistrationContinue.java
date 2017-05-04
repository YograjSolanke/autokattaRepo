package autokatta.com.register;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import retrofit2.Response;

public class NextRegistrationContinue extends AppCompatActivity implements RequestNotifier,
        View.OnClickListener, View.OnTouchListener {

    EditText edtvehicleno, edtfit, edtyear, edttax, edtpermit, edtinsurance, edtpuc, edtlastservice, edtnextservice;
    Spinner mSpinnerVehitype, mSpinnerModel, mSpinnerBrand, mSpinnerVersion, mSpinnerSubType;

    LinearLayout relavite3;
    String whichclick = "", subcategoryId, subcategoryName;
    final ArrayList<String> mVehicleTypeList = new ArrayList<>();
    HashMap<String, String> mVehicleTypeList1 = new HashMap<>();
    List<String> parsedData1 = new ArrayList<>();
    //SubType
    List<String> mSubTypeList = new ArrayList<>();
    List<String> parsedData = new ArrayList<>();
    HashMap<String, String> mSubTypeList1 = new HashMap<>();
    //Brands
    List<String> mBrandIdList = new ArrayList<>();
    List<String> brandData = new ArrayList<>();
    HashMap<String, String> mBrandList1 = new HashMap<>();
    //Model
    List<String> mModelIdList = new ArrayList<>();
    List<String> modelData = new ArrayList<>();
    HashMap<String, String> mModelList1 = new HashMap<>();
    //Version
    List<String> mVersionIdList = new ArrayList<>();
    List<String> versionData = new ArrayList<>();
    HashMap<String, String> mVersionList1 = new HashMap<>();

    Bundle bundle = new Bundle();
    String vehicle_idD, brandId, brandName, modelId, modelName, versionId, versionName;
    ApiCall mApicall;

    String action = "", vehiType = "", vehiYear = "", vehiBrand = "", vehiModel = "", vehiVersion = "", vehiSubcat = "", ids = "", vehino = "",
            vehitaxValidity = "", vehifitnessValidity = "", vehipermitValidity = "", vehiinsurance = "", vehipuc = "", vehilastServicedate = "",
            vehinextservicedate = "";
    Button btnsub, btncancle;

    String vehiclenotext = "", vehicletypetext = "", taxvaltext = "", permitvaltext = "", fitnessvaltext = "",
            insurance = "", puc = "", lastservice = "", nextservice = "", subcattext = "", brandtext = "", modeltext = "",
            versiontext = "", yeartext = "", contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_registration_continue);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtvehicleno = (EditText) findViewById(R.id.editvehicleno);
        relavite3 = (LinearLayout) findViewById(R.id.RelativeLayout3);
        edttax = (EditText) findViewById(R.id.edittaxval);
        edtfit = (EditText) findViewById(R.id.editfitval);
        edtpermit = (EditText) findViewById(R.id.editpermitval);
        edtinsurance = (EditText) findViewById(R.id.editinsurance);
        edtpuc = (EditText) findViewById(R.id.editpuc);
        edtlastservice = (EditText) findViewById(R.id.editlastservice);
        edtnextservice = (EditText) findViewById(R.id.editestnextsrv);
        edtyear = (EditText) findViewById(R.id.edityear);
        btnsub = (Button) findViewById(R.id.btnSub);
        btncancle = (Button) findViewById(R.id.btnCancle);
        mSpinnerVehitype = (Spinner) findViewById(R.id.spinner_vehi_type);
        mSpinnerSubType = (Spinner) findViewById(R.id.spinner_sub_Type);
        mSpinnerBrand = (Spinner) findViewById(R.id.spinner_brand);
        mSpinnerModel = (Spinner) findViewById(R.id.spinner_model);
        mSpinnerVersion = (Spinner) findViewById(R.id.spinner_version);

        Intent i = getIntent();
        action = i.getStringExtra("action");

        edtyear.setOnTouchListener(this);
        edttax.setOnTouchListener(this);
        edtfit.setOnTouchListener(this);
        edtpermit.setOnTouchListener(this);
        edtpuc.setOnTouchListener(this);
        edtlastservice.setOnTouchListener(this);
        edtnextservice.setOnTouchListener(this);
        edtinsurance.setOnTouchListener(this);
        btncancle.setOnClickListener(this);
        btnsub.setOnClickListener(this);

        mApicall = new ApiCall(this, this);
        mApicall.getVehicleList();

        try {
            if (action.equalsIgnoreCase("MyVehicles")) {
                vehiType = i.getStringExtra("vehicletype");
                vehiYear = i.getStringExtra("vehicleyear");
                vehiBrand = i.getStringExtra("vehiclebrand");
                vehiModel = i.getStringExtra("vehiclemodel");
                vehiVersion = i.getStringExtra("vehicleversion");
                vehiSubcat = i.getStringExtra("vehiclesubcategory");

                ids = i.getStringExtra("idss");
                vehino = i.getStringExtra("vehicleno");
                vehitaxValidity = i.getStringExtra("taxvalidity");
                vehifitnessValidity = i.getStringExtra("fitnessvalidity");
                vehipermitValidity = i.getStringExtra("permitvalidity");
                vehiinsurance = i.getStringExtra("insurance");
                vehipuc = i.getStringExtra("puc");
                vehilastServicedate = i.getStringExtra("lastservice");
                vehinextservicedate = i.getStringExtra("nextservice");
                btnsub.setText("Update");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (action.equalsIgnoreCase("MyVehicles")) {
            if (vehitaxValidity.equals("0000-00-00")) {
                vehitaxValidity = "";
            }
            if (vehipermitValidity.equals("0000-00-00")) {
                vehipermitValidity = "";
            }
            if (vehifitnessValidity.equals("0000-00-00")) {
                vehifitnessValidity = "";
            }
            if (vehiinsurance.equals("0000-00-00")) {
                vehiinsurance = "";
            }
            if (vehipuc.equals("0000-00-00")) {
                vehipuc = "";
            }
            if (vehilastServicedate.equals("0000-00-00")) {
                vehilastServicedate = "";
            }
            if (vehinextservicedate.equals("0000-00-00")) {
                vehinextservicedate = "";
            }
            if (vehiYear.equals("0000-00-00")) {
                vehiYear = "";
            }


            edttax.setText(vehitaxValidity);
            edtpermit.setText(vehipermitValidity);
            edtfit.setText(vehifitnessValidity);
            edtinsurance.setText(vehiinsurance);
            edtpuc.setText(vehipuc);
            edtlastservice.setText(vehilastServicedate);
            edtnextservice.setText(vehinextservicedate);
            edtyear.setText(vehiYear);

            //Setting Spinner Values
            edtvehicleno.setText(vehino);
            mSpinnerVehitype.setSelection(getIndex(mSpinnerVehitype, vehiType));
            mSpinnerSubType.setSelection(getIndex(mSpinnerSubType, vehiSubcat));
            mSpinnerBrand.setSelection(getIndex(mSpinnerBrand, vehiBrand));
            mSpinnerModel.setSelection(getIndex(mSpinnerModel, vehiModel));
            mSpinnerVersion.setSelection(getIndex(mSpinnerVersion, vehiVersion));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSub:
                vehiclenotext = edtvehicleno.getText().toString();
                vehicletypetext = mSpinnerVehitype.getSelectedItem().toString();
                brandtext = mSpinnerBrand.getSelectedItem().toString();
                modeltext = mSpinnerModel.getSelectedItem().toString();
                versiontext = mSpinnerVersion.getSelectedItem().toString();
                subcattext = mSpinnerSubType.getSelectedItem().toString();
                yeartext = edtyear.getText().toString();
                taxvaltext = edttax.getText().toString();
                permitvaltext = edtpermit.getText().toString();
                fitnessvaltext = edtfit.getText().toString();
                insurance = edtinsurance.getText().toString();
                puc = edtpuc.getText().toString();
                lastservice = edtlastservice.getText().toString();
                nextservice = edtnextservice.getText().toString();

                int flag = 0;
/*

                if(vehicletypetext.equals("")||vehicletypetext.equals(null)||mSpinnerVehitype.getSelectedItem().toString().equalsIgnoreCase("----Select Vehicle----"))
                {
                    vehicletypetext=vehiType;
                }else

                {
                    vehicletypetext = mSpinnerVehitype.getSelectedItem().toString();
                }

                if(brandtext.equals("")||brandtext.equals(null)||brandtext.equalsIgnoreCase("Select Brands"))
                {
                    brandtext=vehiBrand;
                }else
                {
                    brandtext = mSpinnerBrand.getSelectedItem().toString();
                }
                if(modeltext.equals("")||modeltext.equals(null)||modeltext.equalsIgnoreCase("Select Model"))
                {
                    modeltext=vehiModel;

                }else {
                    modeltext = mSpinnerModel.getSelectedItem().toString();
                }
                if(versiontext.equals("")||versiontext.equals(null)||versiontext.equalsIgnoreCase("Select Version"))
                {
                    versiontext=vehiVersion;

                }else
                {
                    versiontext = mSpinnerVersion.getSelectedItem().toString();
                }
                if(subcattext.equals("")||subcattext.equals(null)||subcattext.equalsIgnoreCase("Select vehicle Sub Types"))
                {
                    subcattext=vehiSubcat;

                }else
                {
                    subcattext = mSpinnerSubType.getSelectedItem().toString();
                }

*/

                if (!yeartext.equals("")) {
                    flag = 1;
                } else {
                    flag = 0;
                    edtyear.setError("Please provide purchase date");
                }

                if (!taxvaltext.equalsIgnoreCase("")) {
                    if (!isValidDate(taxvaltext, yeartext)) {
                        edttax.setError("Invalid date");
                        flag = 0;
                    }
                }

                if (!fitnessvaltext.equals("")) {
                    if (!isValidDate(fitnessvaltext, yeartext)) {
                        edtfit.setError("Invalid date");
                        flag = 0;
                    }
                }


                if (!permitvaltext.equals("")) {
                    if (!isValidDate(permitvaltext, yeartext)) {
                        edtpermit.setError("Invalid date");
                        flag = 0;
                    }
                }

                if (!insurance.equals("")) {
                    if (!isValidDate(insurance, yeartext)) {
                        edtinsurance.setError("Invalid date");
                        flag = 0;
                    }
                }

                if (!puc.equals("")) {
                    if (!isValidDate(puc, yeartext)) {
                        edtpuc.setError("Invalid date");
                        flag = 0;
                    }
                }


                if (!lastservice.equals("")) {
                    if (!isValidDate(lastservice, yeartext)) {
                        edtlastservice.setError("Invalid date");
                        flag = 0;
                    }
                }


                if (!nextservice.equals("")) {
                    if (!isValidDate(nextservice, yeartext)) {
                        edtnextservice.setError("Invalid date");
                        flag = 0;
                    }
                }


                if (!nextservice.equalsIgnoreCase("") && !lastservice.equalsIgnoreCase("")) {
                    if (!isValidDate(nextservice, lastservice)) {
                        edtnextservice.setError("Invalid date");
                        flag = 0;
                    }
                }

                if (edtvehicleno.equals("")) {
                    edtvehicleno.setError("Enter Vehicle Number");
                    edtvehicleno.requestFocus();
                    flag = 0;
                }

                if (flag == 1 && !action.equalsIgnoreCase("MyVehicles")) {
                    /*Response is Success*/
                    mApicall.addOwn(contact, vehiclenotext, vehicletypetext, subcattext, modeltext, brandtext, versiontext, yeartext,
                            taxvaltext, fitnessvaltext, permitvaltext, insurance, puc, lastservice, nextservice);
                    //addOwn();

                } else if (flag == 1 && action.equalsIgnoreCase("MyVehicles")) {
                    /*Response is success*/
                    mApicall.uploadVehicle(ids, vehiclenotext, vehicletypetext, subcattext, modeltext, brandtext, versiontext, yeartext,
                            taxvaltext, fitnessvaltext, permitvaltext, insurance, puc, lastservice, nextservice);
                    CustomToast.customToast(getApplicationContext(), "Vehicle Uploaded Successfully!!!");


                } else {
                    CustomToast.customToast(getApplicationContext(), "Error please provide all details Correct!!!");
                }


                break;
            case R.id.btnCancle:
                edtvehicleno.setText("");
                edtfit.setText("");
                edtyear.setText("");
                edttax.setText("");
                edtpermit.setText("");
                edtinsurance.setText("");
                edtpuc.setText("");
                edtlastservice.setText("");
                edtnextservice.setText("");

                mSpinnerVehitype.setSelection(0);
                mSpinnerModel.setSelection(0);
                mSpinnerBrand.setSelection(0);
                mSpinnerVersion.setSelection(0);
                mSpinnerSubType.setSelection(0);

                edtvehicleno.requestFocus();
                edtfit.setError(null);
                edtyear.setError(null);
                edttax.setError(null);
                edtinsurance.setError(null);
                edtpuc.setError(null);
                edtlastservice.setError(null);
                edtnextservice.setError(null);
                onBackPressed();

                break;
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        System.out.println("Spinner Count:" + spinner.getCount());
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    public Boolean isValidDate(String datetocheck, String yeartextdate) {
        Boolean flag1 = true;
        String[] partc = datetocheck.split("-");
        String[] partu = yeartextdate.split("-");
        int currentyear = Integer.parseInt(partc[0]);
        int useryear = Integer.parseInt(partu[0]);

        if (currentyear - useryear > 0) {
            System.out.println("year ");
            System.out.println("valid date ");
        } else if (currentyear - useryear == 0) {
            System.out.println("year checking");
            if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) > 0) {
                System.out.println("Mothns checking");
                System.out.println("valid date ");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) < 0) {
                flag1 = false;
                System.out.println("Mothns checking");
                System.out.println("Months checked invalid date ");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {
                System.out.println("Mothns checking");
                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    System.out.println("day checking");
                    System.out.println("daty checked valid date ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) <= 0) {
                    flag1 = false;
                    System.out.println("day checking");
                    System.out.println("daty checked invalid date ");
                }
            }

        }
        return flag1;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (view.getId()) {
            case (R.id.edityear):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtyear.setInputType(InputType.TYPE_NULL);
                    edtyear.setError(null);
                    new SetMyDateAndTime("date", edtyear, NextRegistrationContinue.this);
                }
                break;
            case (R.id.edittaxval):
                if (action == MotionEvent.ACTION_DOWN) {
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);
                    new SetMyDateAndTime("date", edttax, NextRegistrationContinue.this);
                }
                break;
            case (R.id.editfitval):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtfit.setInputType(InputType.TYPE_NULL);
                    edtfit.setError(null);
                    new SetMyDateAndTime("date", edtfit, NextRegistrationContinue.this);
                }
                break;
            case (R.id.editpermitval):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtpermit.setInputType(InputType.TYPE_NULL);
                    edtpermit.setError(null);
                    new SetMyDateAndTime("date", edtpermit, NextRegistrationContinue.this);
                }
                break;
            case (R.id.editinsurance):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtinsurance.setInputType(InputType.TYPE_NULL);
                    edtinsurance.setError(null);
                    new SetMyDateAndTime("date", edtinsurance, NextRegistrationContinue.this);
                }
                break;
            case (R.id.editlastservice):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtlastservice.setInputType(InputType.TYPE_NULL);
                    edtlastservice.setError(null);
                    new SetMyDateAndTime("date", edtlastservice, NextRegistrationContinue.this);
                }
                break;
            case (R.id.editpuc):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtpuc.setInputType(InputType.TYPE_NULL);
                    edtpuc.setError(null);
                    new SetMyDateAndTime("date", edtpuc, NextRegistrationContinue.this);
                }
                break;
            case (R.id.editestnextsrv):
                if (action == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtnextservice";
                    edtnextservice.setInputType(InputType.TYPE_NULL);
                    edtnextservice.setError(null);
                    new SetMyDateAndTime("date", edtnextservice, NextRegistrationContinue.this);
                }
                break;
        }
        return false;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                Log.i("select--->", "vehicle" + response.body());
                if (response.body() instanceof GetVehicleListResponse) {
                    mVehicleTypeList.clear();
                    mVehicleTypeList1.clear();
                    parsedData1.clear();
                    mVehicleTypeList.add("Select Vehicle");
                    GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                    if (!mGetVehicleListResponse.getSuccess().isEmpty()) {
                        for (GetVehicleListResponse.Success vehicleTypeResonse : mGetVehicleListResponse.getSuccess()) {
                            vehicleTypeResonse.setId(vehicleTypeResonse.getId());
                            vehicleTypeResonse.setName(vehicleTypeResonse.getName());
                            mVehicleTypeList.add(vehicleTypeResonse.getName());
                            mVehicleTypeList1.put(vehicleTypeResonse.getName(), vehicleTypeResonse.getId());
                        }
                        parsedData1.addAll(mVehicleTypeList);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, parsedData1);
                        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpinnerVehitype.setAdapter(dataadapter);
                        mSpinnerSubType.setAdapter(null);
                        mSpinnerVehitype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                                if (position != 0) {
                                    vehicle_idD = mVehicleTypeList1.get(parsedData1.get(position));
                                    String Category = mVehicleTypeList.get(position);
                                    ((TextView) mSpinnerVehitype.getSelectedView()).setTextColor(getResources().getColor(R.color.red));
                                    getSubCategoryTask();
                                    if (Category.equalsIgnoreCase("2 Wheeler")) {
                                        edttax.setVisibility(View.GONE);
                                        edtpermit.setVisibility(View.GONE);
                                        edtfit.setVisibility(View.GONE);
                                    } else {
                                        edttax.setVisibility(View.VISIBLE);
                                        edtpermit.setVisibility(View.VISIBLE);
                                        edtfit.setVisibility(View.VISIBLE);
                                    }

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                } else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    Log.e("GetVehicle sub Types", "->");
                    mSubTypeList.clear();
                    mSubTypeList1.clear();
                    parsedData.clear();
                    mSubTypeList.add("Select Vehicle Sub Types");
                    GetVehicleSubTypeResponse mGetVehicleSubTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    for (GetVehicleSubTypeResponse.Success subTypeResponse : mGetVehicleSubTypeResponse.getSuccess()) {
                        subTypeResponse.setId(subTypeResponse.getId());
                        subTypeResponse.setName(subTypeResponse.getName());
                        mSubTypeList.add(subTypeResponse.getName());
                        mSubTypeList1.put(subTypeResponse.getName(), subTypeResponse.getId());
                    }
                    parsedData.addAll(mSubTypeList);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, parsedData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerSubType.setAdapter(adapter);
                    mSpinnerSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                subcategoryId = mSubTypeList1.get(parsedData.get(position));
                                subcategoryName = parsedData.get(position);
                                ((TextView) mSpinnerSubType.getSelectedView()).setTextColor(getResources().getColor(R.color.red));
                                System.out.println("Sub cat is::" + subcategoryId);
                                System.out.println("Sub cat name::" + subcategoryName);
                                getBrand(vehicle_idD, subcategoryId);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (response.body() instanceof GetVehicleBrandResponse) {
                    Log.e("GetVehicleBrands", "->");
                    mBrandIdList.clear();
                    mBrandList1.clear();
                    brandData.clear();
                    mBrandIdList.add("Select Brands");
                    GetVehicleBrandResponse getVehicleBrandResponse = (GetVehicleBrandResponse) response.body();
                    for (GetVehicleBrandResponse.Success brandResponse : getVehicleBrandResponse.getSuccess()) {
                        brandResponse.setBrandId(brandResponse.getBrandId());
                        brandResponse.setBrandTitle(brandResponse.getBrandTitle());
                        mBrandIdList.add(brandResponse.getBrandTitle());
                        mBrandList1.put(brandResponse.getBrandTitle(), brandResponse.getBrandId());
                    }
                    mBrandIdList.add("other");
                    brandData.addAll(mBrandIdList);
                    Log.i("ListBrand", "->" + mBrandIdList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, brandData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerBrand.setAdapter(adapter);
                    mSpinnerModel.setAdapter(null);
                    mSpinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                brandId = mBrandList1.get(brandData.get(position));
                                brandName = brandData.get(position);
                                ((TextView) mSpinnerBrand.getSelectedView()).setTextColor(getResources().getColor(R.color.red));
                            }

                            if (brandData.get(position).equalsIgnoreCase("other")) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(NextRegistrationContinue.this);
                                //  AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContinueNextRegistration.this);
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
                                                    CustomToast.customToast(getApplicationContext(), "Please enter brand");
                                                else
                                                    AddBrand("Brand", edbrand, vehicle_idD, subcategoryId);
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mSpinnerBrand.setSelection(0);
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else
                                getModel(vehicle_idD, subcategoryId, brandId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (response.body() instanceof GetVehicleModelResponse) {
                    mModelIdList.clear();
                    mModelList1.clear();
                    modelData.clear();
                    mModelIdList.add("Select Model");
                    GetVehicleModelResponse getVehicleModelResponse = (GetVehicleModelResponse) response.body();
                    for (GetVehicleModelResponse.Success modelResponse : getVehicleModelResponse.getSuccess()) {
                        modelResponse.setModelId(modelResponse.getModelId());
                        modelResponse.setModelTitle(modelResponse.getModelTitle());
                        mModelIdList.add(modelResponse.getModelTitle());
                        mModelList1.put(modelResponse.getModelTitle(), modelResponse.getModelId());
                    }
                    mModelIdList.add("other");
                    modelData.addAll(mModelIdList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, modelData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerModel.setAdapter(adapter);
                    mSpinnerVersion.setAdapter(null);
                    mSpinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                modelId = mModelList1.get(modelData.get(position));
                                modelName = modelData.get(position);
                                ((TextView) mSpinnerModel.getSelectedView()).setTextColor(getResources().getColor(R.color.red));
                            }

                            if (modelData.get(position).equalsIgnoreCase("other")) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(NextRegistrationContinue.this);
                                alertDialog.setTitle("Add Model");
                                alertDialog.setMessage("Enter model name");
                                final EditText input = new EditText(getApplicationContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                alertDialog.setPositiveButton("Add Model",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String edmodel = input.getText().toString();
                                                if (edmodel.equals(""))
                                                    Toast.makeText(getApplicationContext(), "Please enter model", Toast.LENGTH_LONG).show();
                                                else
                                                    AddModel("Model", edmodel, vehicle_idD, subcategoryId, brandId);
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mSpinnerModel.setSelection(0);
                                                dialog.dismiss();
                                            }
                                        });

                                alertDialog.show();
                            } else
                                getVersion(vehicle_idD, subcategoryId, brandId, modelId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (response.body() instanceof GetVehicleVersionResponse) {
                    Log.e("GetVehicleVersion", "->");
                    mVersionIdList.clear();
                    mVersionList1.clear();
                    versionData.clear();
                    mVersionIdList.add("Select Version");
                    GetVehicleVersionResponse getVehicleVersionResponse = (GetVehicleVersionResponse) response.body();
                    for (GetVehicleVersionResponse.Success versionResponse : getVehicleVersionResponse.getSuccess()) {
                        versionResponse.setVersionId(versionResponse.getVersionId());
                        versionResponse.setVersion(versionResponse.getVersion());
                        mVersionIdList.add(versionResponse.getVersion());
                        mVersionList1.put(versionResponse.getVersion(), versionResponse.getVersionId());
                    }
                    mVersionIdList.add("other");
                    versionData.addAll(mVersionIdList);
                    Log.i("ListVersion", "->" + mVersionIdList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, versionData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerVersion.setAdapter(adapter);
                    mSpinnerVersion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                versionId = mVersionList1.get(versionData.get(position));
                                versionName = versionData.get(position);
                                ((TextView) mSpinnerVersion.getSelectedView()).setTextColor(getResources().getColor(R.color.red));
                                System.out.println("Version id is::" + versionId);
                                System.out.println("Version name::" + versionName);
                            }
                            if (versionData.get(position).equalsIgnoreCase("other")) {

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NextRegistrationContinue.this);
                                alertDialog.setTitle("Add Version");
                                alertDialog.setMessage("Enter version name");
                                final EditText input = new EditText(getApplicationContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);

                                alertDialog.setPositiveButton("Add Version",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String edversion = input.getText().toString();
                                                if (edversion.equals(""))
                                                    Toast.makeText(getApplicationContext(), "Please enter version", Toast.LENGTH_LONG).show();
                                                else
                                                    AddVersion("Version", edversion, vehicle_idD, subcategoryId, brandId, modelId);
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mSpinnerVersion.setSelection(0);
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
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Continue Next Registration");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equals("success")) {
                if (action.equals("ContinueRegistration")) {
                    Intent i = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                    startActivity(i);
                    finish();
                } else if (action.equals("MyVehicles")) {
                    Intent i = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                    finish();
                }
            } else if (str.equals("Success")) {/*Response for Add Own*/
                Toast.makeText(getApplicationContext(), "Your Vehicle added Successfully",
                        Toast.LENGTH_LONG).show();
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(NextRegistrationContinue.this);
                // set title
                alertDialogBuilder.setTitle("Add Vehicle");
                // set dialog message
                alertDialogBuilder
                        .setMessage("You want to add another vehicle?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                        edtvehicleno.setText("");
                                        edtfit.setText("");
                                        edtyear.setText("");
                                        edttax.setText("");
                                        edtpermit.setText("");
                                        edtinsurance.setText("");
                                        edtpuc.setText("");
                                        edtlastservice.setText("");
                                        edtnextservice.setText("");

                                        mSpinnerVehitype.setSelection(0);
                                        mSpinnerModel.setSelection(0);
                                        mSpinnerBrand.setSelection(0);
                                        mSpinnerVersion.setSelection(0);
                                        mSpinnerSubType.setSelection(0);

                                        edtvehicleno.requestFocus();
                                        edtfit.setError(null);
                                        edtyear.setError(null);
                                        edttax.setError(null);
                                        edtinsurance.setError(null);
                                        edtpuc.setError(null);
                                        edtlastservice.setError(null);
                                        edtnextservice.setError(null);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                        Intent i = new Intent(getApplicationContext(), RegistrationCompanyBased.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });

                // create alert dialog
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            } else if (str.equals("success_brand_add")) {
                CustomToast.customToast(getApplicationContext(), "Brand added successfully");
                getBrand(vehicle_idD, subcategoryId);
                Log.i("msg", "Brand added successfully");
            } else if (str.equals("success_model_add")) {
                CustomToast.customToast(getApplicationContext(), "Model added successfully");
                getModel(vehicle_idD, subcategoryId, brandId);
            } else if (str.equals("success_version_add")) {
                CustomToast.customToast(getApplicationContext(), "Version added successfully");
                getVersion(vehicle_idD, subcategoryId, brandId, modelId);
            }

        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
    }

    /*
   Sub Category...
    */
    private void getSubCategoryTask() {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getVehicleSubtype(vehicle_idD);
    }

    /*
    Get Brand
     */
    private void getBrand(String categoryId, String subcategoryId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getBrand(categoryId, subcategoryId);
    }

    /*
    Get Model...
     */
    private void getModel(String categoryId, String subCategoryId, String brandId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getModel(categoryId, subCategoryId, brandId);
    }

    /*
    Get Version...
     */
    private void getVersion(String categoryId, String subCategoryId, String brandId, String modelId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getVersion(categoryId, subCategoryId, brandId, modelId);
    }

    /*
    Add Brand
     */
    private void AddBrand(String keyword, String title, String categoryId, String subCatID) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addBrand(keyword, title, categoryId, subCatID);
    }

    /*
    Add Model
     */
    private void AddModel(String keyword, String title, String categoryId, String subCatID, String brandId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addModel(keyword, title, categoryId, subCatID, brandId);
    }

    /*
    Add Version
     */
    private void AddVersion(String keyword, String title, String categoryId, String subCatID, String brandId, String modelId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addVersion(keyword, title, categoryId, subCatID, brandId, modelId);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(NextRegistrationContinue.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), RegistrationContinue.class), options.toBundle());
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), RegistrationContinue.class));
            finish();
        }
    }
}
