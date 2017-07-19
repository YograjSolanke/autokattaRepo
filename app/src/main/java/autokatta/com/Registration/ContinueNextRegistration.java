package autokatta.com.Registration;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import retrofit2.Response;

/**
 * Created by ak-005 on 23/3/17.
 */

public class ContinueNextRegistration extends AppCompatActivity implements RequestNotifier,
        View.OnClickListener, View.OnTouchListener {


    EditText edtvehicleno, edtfit, edtyear, edttax, edtpermit, edtinsurance, edtpuc, edtlastservice, edtnextservice;
    Spinner mSpinnerVehitype, mSpinnerModel, mSpinnerBrand, mSpinnerVersion, mSpinnerSubType;

    RelativeLayout relavite3;
    String whichclick = "", subcategoryId, subcategoryName;
    final ArrayList<String> mVehicleTypeList = new ArrayList<>();
    HashMap<String, Integer> mVehicleTypeList1 = new HashMap<>();
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
    String brandId, brandName, modelId, modelName, versionId, versionName;
    ApiCall mApicall;
    int vehicle_idD;

    String action = "", vehiType = "", vehiYear = "", vehiBrand = "", vehiModel = "", vehiVersion = "", vehiSubcat = "", ids = "", vehino = "",
            vehitaxValidity = "", vehifitnessValidity = "", vehipermitValidity = "", vehiinsurance = "", vehipuc = "", vehilastServicedate = "",
            vehinextservicedate = "";
    Button btnsub, btncancle;

    String vehiclenotext = "", vehicletypetext = "", taxvaltext = "", permitvaltext = "", fitnessvaltext = "",
            insurance = "", puc = "", lastservice = "", nextservice = "", subcattext = "", brandtext = "", modeltext = "",
            versiontext = "", yeartext = "", contact;

    public static final String MyContactPREFERENCES = "contact No";
    SharedPreferences contactprefs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_next_registration);

        edtvehicleno = (EditText) findViewById(R.id.editvehicleno);

        relavite3 = (RelativeLayout) findViewById(R.id.RelativeLayout3);

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

        contactprefs = getApplicationContext().getSharedPreferences(MyContactPREFERENCES, Context.MODE_PRIVATE);
        contact = contactprefs.getString("contact", "");
//        bundle = getArguments();
        Intent i = getIntent();
        action = "ContinueRegisteration";
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
                vehiType = bundle.getString("vehicletype");
                vehiYear = bundle.getString("vehicleyear");
                vehiBrand = bundle.getString("vehiclebrand");
                vehiModel = bundle.getString("vehiclemodel");
                vehiVersion = bundle.getString("vehicleversion");
                vehiSubcat = bundle.getString("vehiclesubcategory");

                ids = bundle.getString("idss");
                vehino = bundle.getString("vehicleno");
                vehitaxValidity = bundle.getString("taxvalidity");
                vehifitnessValidity = bundle.getString("fitnessvalidity");
                vehipermitValidity = bundle.getString("permitvalidity");
                vehiinsurance = bundle.getString("insurance");
                vehipuc = bundle.getString("puc");
                vehilastServicedate = bundle.getString("lastservice");
                vehinextservicedate = bundle.getString("nextservice");
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
                subcattext = mSpinnerSubType.getSelectedItem().toString();
                brandtext = mSpinnerBrand.getSelectedItem().toString();
                modeltext = mSpinnerModel.getSelectedItem().toString();
                versiontext = mSpinnerVersion.getSelectedItem().toString();
                yeartext = edtyear.getText().toString();
                taxvaltext = edttax.getText().toString();
                permitvaltext = edtpermit.getText().toString();
                fitnessvaltext = edtfit.getText().toString();
                insurance = edtinsurance.getText().toString();
                puc = edtpuc.getText().toString();
                lastservice = edtlastservice.getText().toString();
                nextservice = edtnextservice.getText().toString();


                int flag = 0;

                if (!yeartext.equals("")) {

                    flag = 1;

                } else {
                    flag = 0;
                    edtyear.setError("Please prodive purchase date");
                }

                if (!vehiclenotext.equalsIgnoreCase("")) {
                    Toast.makeText(ContinueNextRegistration.this,
                            "Please Enter Vehicle Number", Toast.LENGTH_LONG)
                            .show();

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
                    flag=0;
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
                    CustomToast.customToast(getApplicationContext(),"Vehicle Uploaded Successfully!!!");


                } else {
                    CustomToast.customToast(getApplicationContext(),"Error please provide all details Correct!!!");
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

        // dob = dobtext.getText().toString();

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
                //dobtext.setError("Minimum 8 year age required");
            } else if (Integer.parseInt(partc[1]) - Integer.parseInt(partu[1]) == 0) {

                System.out.println("Mothns checking");
                if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) > 0) {
                    System.out.println("day checking");
                    System.out.println("daty checked valid date ");
                } else if (Integer.parseInt(partc[2]) - Integer.parseInt(partu[2]) <= 0) {
                    flag1 = false;
                    System.out.println("day checking");
                    System.out.println("daty checked invalid date ");
                    //dobtext.setError("Minimum 8 year age required");
                }
            }

        }
        return flag1;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case (R.id.edityear):
                //setting date for year validity
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtyear";
                    edtyear.setInputType(InputType.TYPE_NULL);
                    edtyear.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
                break;
            case (R.id.edittaxval):
                //setting date for Tax validity
                int action1 = motionEvent.getAction();
                if (action1 == MotionEvent.ACTION_DOWN) {
                    whichclick = "edttax";
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;
            case (R.id.editfitval):
                //setting date for fit validity
                int action2 = motionEvent.getAction();
                if (action2 == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtfit";
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;
            case (R.id.editpermitval):
                //setting date for permit validity
                int action3 = motionEvent.getAction();
                if (action3 == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtpermit";
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;
            case (R.id.editinsurance):
                //setting date for Insurance validity
                int action4 = motionEvent.getAction();
                if (action4 == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtinsurance";
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;
            case (R.id.editlastservice):
                //setting date for last Service validity
                int action7 = motionEvent.getAction();
                if (action7 == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtlastservice";
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;
            case (R.id.editpuc):
                //setting date for puc validity
                int action6 = motionEvent.getAction();
                if (action6 == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtpuc";
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;
            case (R.id.editestnextsrv):
                //setting date for next Service validity
                int action5 = motionEvent.getAction();
                if (action5 == MotionEvent.ACTION_DOWN) {
                    whichclick = "edtnextservice";
                    edttax.setInputType(InputType.TYPE_NULL);
                    edttax.setError(null);

                    DialogFragment newFragment = new SelectDate();
                    newFragment.show(getFragmentManager(), "DatePicker");

                }
                break;
        }
        return false;
    }


    //date  setting class
    public class SelectDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yyyy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yyyy, mm, dd);

        }

        public void onDateSet(DatePicker view, int yyyy, int mm, int dd) {
            populateSetDate(yyyy, mm + 1, dd);

        }

        public void populateSetDate(int year, int month, int day) {

            if (whichclick.equalsIgnoreCase("edtfit")) {

                edtfit.setText(year + "-" + month + "-" + day);
            } else if (whichclick.equalsIgnoreCase("edtyear")) {
                edtyear.setText(year + "-" + month + "-" + day);
            } else if (whichclick.equalsIgnoreCase("edttax")) {
                edttax.setText(year + "-" + month + "-" + day);
            } else if (whichclick.equalsIgnoreCase("edtpermit")) {
                edtpermit.setText(year + "-" + month + "-" + day);
            } else if (whichclick.equalsIgnoreCase("edtinsurance")) {
                edtinsurance.setText(year + "-" + month + "-" + day);
            } else if (whichclick.equalsIgnoreCase("edtpuc")) {
                edtpuc.setText(year + "-" + month + "-" + day);
            } else if (whichclick.equalsIgnoreCase("edtlastservice")) {
                edtlastservice.setText(year + "-" + month + "-" + day);
            } else {
                edtnextservice.setText(year + "-" + month + "-" + day);
            }
        }

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
                    mVehicleTypeList.add("----Select Vehicle----");
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

                                    //  mApicall.getVehicleSubtype(vehicle_idD);
                                    // getSubcategory(vehicle_id);
                                    getSubCategoryTask();
                                    if (Category.equalsIgnoreCase("2 Wheeler")) {
                                        //Toast.makeText(Continue_reg_y.this,"Commercial vehicle is selected",Toast.LENGTH_LONG).show();

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
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,parsedData);
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
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, brandData);
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

                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ContinueNextRegistration.this);
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
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelData);
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

                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ContinueNextRegistration.this);
                                alertDialog.setTitle("Add Model");
                                alertDialog.setMessage("Enter model name");

                                final EditText input = new EditText(getApplicationContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

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
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, versionData);
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

                                Builder alertDialog = new Builder(ContinueNextRegistration.this);
                                alertDialog.setTitle("Add Version");
                                alertDialog.setMessage("Enter version name");

                                final EditText input = new EditText(getApplicationContext());
                                LayoutParams lp = new LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

                                alertDialog.setPositiveButton("Add Version",
                                        new OnClickListener() {
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
                                        new OnClickListener() {
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
        if (str != "") {
            if (str.equals("success")) {
                if (action.equals("ContinueRegisteration")) {
                    Intent i = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                    startActivity(i);
                    finish();
                } else if (action.equals("MyVehicles")) ;
                {
                    CustomToast.customToast(getApplication(),"updated Successfully");
                  //  Toast.makeText(getApplicationContext(), "updated Successfully", Toast.LENGTH_LONG).show();
                }
            } else if (str.equals("Success")) {/*Response for Add Own*/

                Toast.makeText(getApplicationContext(), "Your Vehicle added Sucessfully",
                        Toast.LENGTH_LONG).show();


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ContinueNextRegistration.this);

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
//
                                        Intent i = new Intent(getApplicationContext(), CompanyBasedRegistrationActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
            if (str != null) {
                if (str.equals("success_brand_add")) {
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

            }

        }
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
    private void getBrand(int categoryId, String subcategoryId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getBrand(categoryId, subcategoryId);
    }

    /*
    Get Model...
     */
    private void getModel(int categoryId, String subCategoryId, String brandId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getModel(categoryId, subCategoryId, brandId);
    }

    /*
    Get Version...
     */
    private void getVersion(int categoryId, String subCategoryId, String brandId, String modelId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getVersion(categoryId, subCategoryId, brandId, modelId);
    }


    /*
    Add Brand
     */
    private void AddBrand(String keyword, String title, int categoryId, String subCatID) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addBrand(keyword, title, categoryId, subCatID);
    }

    /*
    Add Model
     */
    private void AddModel(String keyword, String title, int categoryId, String subCatID, String brandId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addModel(keyword, title, categoryId, subCatID, brandId);
    }

    /*
    Add Version
     */
    private void AddVersion(String keyword, String title, int categoryId, String subCatID, String brandId, String modelId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addVersion(keyword, title, categoryId, subCatID, brandId, modelId);
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ContinueRegistration.class);
        startActivity(i);
        finish();
    }

}




