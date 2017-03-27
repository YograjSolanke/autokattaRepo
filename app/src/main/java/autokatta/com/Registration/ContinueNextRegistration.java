package autokatta.com.Registration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import retrofit2.Response;

/**
 * Created by ak-005 on 23/3/17.
 */

public class ContinueNextRegistration extends AppCompatActivity implements RequestNotifier,
        View.OnClickListener, View.OnTouchListener {


    EditText edtvehicleno, edtfit, edtyear, edttax, edtpermit, edtinsurance, edtpuc, edtlastservice, edtnextservice;
    Spinner mSpinnerVehitype, mSpinnerModel, mSpinnerBrand, mSpinnerVersion, mSpinnerSubType;

    RelativeLayout relavite3;
    String whichclick = "";
    final ArrayList<String> mVehicleTypeList = new ArrayList<>();
    final ArrayList<String> parsedData1 = new ArrayList<>();
    List<String> parsedData = new ArrayList<>();
    List<String> mSubTypeList = new ArrayList<>();
    HashMap<String, String> mSubTypeList1 = new HashMap<>();
    HashMap<String, String> mVehicleTypeList1 = new HashMap<>();
    final ArrayList<String> brands = new ArrayList<String>();
    final ArrayList<String> models = new ArrayList<String>();
    final ArrayList<String> versions = new ArrayList<String>();


    InputStream is = null;

    Bundle bundle = new Bundle();
    ApiCall mApicall;

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

                if (edtvehicleno.equals("")) {
                    edtvehicleno.setError("Enter Vehicle Number");
                    edtvehicleno.requestFocus();
                }

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

                if (flag == 1 && !action.equalsIgnoreCase("MyVehicles")) {
                    /*Response is Success*/
                    mApicall.addOwn(contact, vehiclenotext, vehicletypetext, subcattext, modeltext, brandtext, versiontext, yeartext,
                            taxvaltext, fitnessvaltext, permitvaltext, insurance, puc, lastservice, nextservice);
                    //addOwn();

                } else if (flag == 1 && action.equalsIgnoreCase("MyVehicles")) {
                    /*Response is success*/
                    mApicall.uploadVehicle(ids, vehiclenotext, vehicletypetext, subcattext, modeltext, brandtext, versiontext, yeartext,
                            taxvaltext, fitnessvaltext, permitvaltext, insurance, puc, lastservice, nextservice);
                    Toast.makeText(ContinueNextRegistration.this,
                            "Vehicle uploaded Successfully !!", Toast.LENGTH_LONG)
                            .show();

                } else {
                    Toast.makeText(getApplicationContext(), "Error please provide all details!!!", Toast.LENGTH_LONG).show();
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

        System.out.println("Result here==================================================");

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
                    mVehicleTypeList.add("----Select Vehicle----");
                    GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                    if (!mGetVehicleListResponse.getSuccess().isEmpty()) {

                        for (GetVehicleListResponse.Success vehicleTypeResonse : mGetVehicleListResponse.getSuccess()) {
                            vehicleTypeResonse.setId(vehicleTypeResonse.getId());
                            vehicleTypeResonse.setName(vehicleTypeResonse.getName());

                            mVehicleTypeList.add(vehicleTypeResonse.getName());
                            mVehicleTypeList1.put(vehicleTypeResonse.getName(), vehicleTypeResonse.getId());

                        }
                        parsedData.addAll(mVehicleTypeList);
                        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.registration_spinner, parsedData);
                        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpinnerVehitype.setAdapter(dataadapter);
                        mSpinnerSubType.setAdapter(null);
                        mSpinnerVehitype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                if (position != 0) {
                                    String vehicle_idD = mVehicleTypeList1.get(parsedData.get(position));
                                    String Category = mVehicleTypeList.get(position);
                                    System.out.println("vehicle iddddd in category============" + vehicle_idD);


                                    mApicall.getVehicleSubtype(vehicle_idD);
                                    // getSubcategory(vehicle_id);

                                    System.out.println("************************************" + Category);
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

                                    System.out.println("vehicle iddddd in category============" + vehicle_idD);

                                } else {
                                    Toast.makeText(ContinueNextRegistration.this,
                                            "Please Select the Vehicle type !!", Toast.LENGTH_LONG)
                                            .show();
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                } else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    Log.i("me in Sub Cat", "->" + response);
                    mSubTypeList.clear();
                    GetVehicleSubTypeResponse mGetVehicleSubTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    mSubTypeList.add("Select Sub Vehicle Types");
                    for (GetVehicleSubTypeResponse.Success subTypeResponse : mGetVehicleSubTypeResponse.getSuccess()) {
                        subTypeResponse.setId(subTypeResponse.getId());
                        subTypeResponse.setName(subTypeResponse.getName());
                        mSubTypeList.add(subTypeResponse.getName());
                        mSubTypeList1.put(subTypeResponse.getId(), subTypeResponse.getName());
                    }
                    mSubTypeList.clear();
                    mSubTypeList1.clear();
                    parsedData1.clear();
                    parsedData1.addAll(mSubTypeList);
                    final ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, parsedData1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerSubType.setAdapter(adapter);
                    mSpinnerSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String sub_category_id1 = mSubTypeList1.get(parsedData.get(position));
                                System.out.println("sub_category_id iddddd in Sub category============" + sub_category_id1);
                                mApicall.getBrandModelVersion(sub_category_id1);
                            } else {
                                Toast.makeText(ContinueNextRegistration.this,
                                        "Please Select the Vehicle Sub type !!", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        }
    }


    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != "") {
            Log.i("upload Sucess", "->");
            if (str.equals("success")) {
                if (action.equals("ContinueRegisteration")) {
                    Intent i = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                    startActivity(i);
                    finish();
                } else if (action.equals("MyVehicles")) ;
                {
                    Toast.makeText(getApplicationContext(), "to Do in Continue Next reg", Toast.LENGTH_LONG).show();
                }
            } else if (str.equals("Success")) {/*Response for Add Own*/

                Toast.makeText(getApplicationContext(), "Your Vehicle added Sucessfully",
                        Toast.LENGTH_LONG).show();


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

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
                                     /*   CompanyBasedRegisteration fr = new CompanyBasedRegisteration();
                                        //fr.setArguments(b);
                                        mFragmentManager = getActivity().getSupportFragmentManager();
                                        mFragmentTransaction = mFragmentManager.beginTransaction();
                                        mFragmentTransaction.replace(R.id.containerView, fr).addToBackStack("companybasedreg").commit();
*/
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            } else {
                Toast.makeText(getApplicationContext(), "Please try later",
                        Toast.LENGTH_LONG).show();

            }

        }

    }
}




