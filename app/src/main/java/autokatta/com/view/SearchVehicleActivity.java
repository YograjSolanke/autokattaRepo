package autokatta.com.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.MultiSelectionSpinner;
import autokatta.com.Registration.Multispinner;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.RangeSeekBar;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ColorResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import retrofit2.Response;

public class SearchVehicleActivity extends AppCompatActivity implements MultiSelectionSpinner.MultiSpinnerListener, RequestNotifier, Multispinner.MultiSpinnerListener {

    EditText useEdit, seatingEdit, transmissionEdit, impletementEdit, bodyEdit, boatEdit, rvEdit;
    Button BtnRefresh, BtnApplySearch, BtnMoreOptions, btnCity, btnRto;
    Spinner rc, insurance, owner, hypo, brand, model, allcategory, subcategory, version;
    Spinner tax_validity, fitness_validity, permit_validity, drive, bus_type, air, invoice, finance;
    MultiSelectionSpinner spinnerfual;

    TextView man_yr_from, man_yr_to, tyre_from, tyre_to, reg_yr_from, reg_yr_to, kms_from, kms_to, pricefrom, priceto, hrs_from, hrs_to, minihpcapacity, maxhpcapcity;

    AutoCompleteTextView autoCity, autoRTO, autoCity1, autoCity2, autoCity3, autoCity4, autoRTO1, autoRTO2, autoRTO3, autoRTO4;
    CheckBox checkRTO;
    private RadioGroup radioPermitGroup;
    private RadioButton radioPermitButton;
    Multispinner multiSpinnercolor;
    LinearLayout moresearchlinear;

    RelativeLayout rowFinacial, rowCategory, rowSubcat, rowPermit, rowBrand, rowModel, rowVersion, rowCity, rowRTO, rowManyr, rowmanyr1, rowREGyr, rowREGyr1, rowPrice, rowprice1, rowKms, rowkms1, rowhrs, rowhrstb, rowhpcapacity, rowhpcap, rowowners;

    RelativeLayout rowInvoice, rowbustype, rowaircondition, rowbody, rowboattype, rowrvtype, rowcolor, rowrc, rowinsurance1, rowhypo, rowtax, rowfitness, rowpermit, rowfual, rowseat, rowdrive, rowtransmission, rowuse, rowimpl, rowtyre, rowtyrerange;

    String action = "", Scategory, Sbrand, Smodel, Sprice, Syear, Sid = "", Category, subCategory, hrs1, hrs2, hpcap1, hpcap2;
    String vehicle_id, sub_category_id, position_brand_id, position_model_id;
    String spinnervalues[];

    String city1, city2, city11, city12, city13, city14, city21, city22, city23, city24, brand1, model1, color1, version1, man_yr1, man_yr2, reg_yr1, reg1, reg_yr2, rc1, insurance1, kms1, kms2, hypo1, owner1, price1, price2;
    String permit1, tax_validity1, fitness_validity1, permit_validity1, drive1, fual1, bus_type1, air1, invoice1;

    String use1, seating1, transmission1, implement1, body1, boat1, rv1, finance1, tyre1, tyre2;
    int count = 0;

    List<String> fuals = new ArrayList<>();
    List<String> colors = new ArrayList<>();

    int counter = 0;
    int counter1 = 0;
    String myContact = "";
    ApiCall mApiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myContact = getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");
        mApiCall = new ApiCall(this, this);
        final TextView financetxt = (TextView) findViewById(R.id.financetxt);
        final TextView messageText = (TextView) findViewById(R.id.messageText);
        final TextView messageText1 = (TextView) findViewById(R.id.messageText1);
        final TextView brandtxt = (TextView) findViewById(R.id.brandtxt);
        final TextView modeltxt = (TextView) findViewById(R.id.modeltxt);
        TextView ownertxt = (TextView) findViewById(R.id.ownertxt);


        rowPermit = (RelativeLayout) findViewById(R.id.rowPermit);
        rowRTO = (RelativeLayout) findViewById(R.id.rowRTO);
        rowManyr = (RelativeLayout) findViewById(R.id.rowManyr);
        rowPrice = (RelativeLayout) findViewById(R.id.rowPrice);
        rowKms = (RelativeLayout) findViewById(R.id.rowKms);
        rowkms1 = (RelativeLayout) findViewById(R.id.rowkms1);
        rowhrs = (RelativeLayout) findViewById(R.id.rowhrs);
        rowhrstb = (RelativeLayout) findViewById(R.id.rowhrstb);
        rowhpcapacity = (RelativeLayout) findViewById(R.id.rowhpcapacity);
        rowhpcap = (RelativeLayout) findViewById(R.id.rowhpcap);

        rowInvoice = (RelativeLayout) findViewById(R.id.rowInvoice);
        rowbustype = (RelativeLayout) findViewById(R.id.rowbustype);
        rowaircondition = (RelativeLayout) findViewById(R.id.rowaircondition);
        rowbody = (RelativeLayout) findViewById(R.id.rowbody);
        rowboattype = (RelativeLayout) findViewById(R.id.rowboattype);
        rowrvtype = (RelativeLayout) findViewById(R.id.rowrvtype);
        rowcolor = (RelativeLayout) findViewById(R.id.rowcolor);
        rowrc = (RelativeLayout) findViewById(R.id.rowrc);
        rowinsurance1 = (RelativeLayout) findViewById(R.id.rowinsurance1);
        rowhypo = (RelativeLayout) findViewById(R.id.rowhypo);
        rowtax = (RelativeLayout) findViewById(R.id.rowtax);
        rowfitness = (RelativeLayout) findViewById(R.id.rowfitness);
        rowpermit = (RelativeLayout) findViewById(R.id.rowpermit);
        rowfual = (RelativeLayout) findViewById(R.id.rowfual);
        rowseat = (RelativeLayout) findViewById(R.id.rowseat);
        rowdrive = (RelativeLayout) findViewById(R.id.rowdrive);
        rowtransmission = (RelativeLayout) findViewById(R.id.rowtransmission);
        rowuse = (RelativeLayout) findViewById(R.id.rowuse);
        rowimpl = (RelativeLayout) findViewById(R.id.rowimpl);
        rowtyre = (RelativeLayout) findViewById(R.id.rowtyre);
        rowimpl = (RelativeLayout) findViewById(R.id.rowimpl);


        BtnMoreOptions = (Button) findViewById(R.id.BtnMoreOptions);
        btnCity = (Button) findViewById(R.id.btncity);
        BtnApplySearch = (Button) findViewById(R.id.BtnApplySearch);
        btnRto = (Button) findViewById(R.id.btnrto);
        BtnRefresh = (Button) findViewById(R.id.BtnRefresh);

        multiSpinnercolor = (Multispinner) findViewById(R.id.colorEdit1);
        spinnerfual = (MultiSelectionSpinner) findViewById(R.id.fualEdit1);

        finance = (Spinner) findViewById(R.id.finacialEdit1);
        allcategory = (Spinner) findViewById(R.id.allCategory1);
        subcategory = (Spinner) findViewById(R.id.subCategory);
        brand = (Spinner) findViewById(R.id.BrandEdit1);
        model = (Spinner) findViewById(R.id.ModelEdit1);
        version = (Spinner) findViewById(R.id.VersionEdit1);
        owner = (Spinner) findViewById(R.id.ownerEdit);

        invoice = (Spinner) findViewById(R.id.invoiceEdit);
        bus_type = (Spinner) findViewById(R.id.bustypeEdit);
        air = (Spinner) findViewById(R.id.aircondEdit);
        rc = (Spinner) findViewById(R.id.rcEdit1);
        insurance = (Spinner) findViewById(R.id.insEdit1);
        hypo = (Spinner) findViewById(R.id.hypoEdit1);
        tax_validity = (Spinner) findViewById(R.id.taxEdit1);
        fitness_validity = (Spinner) findViewById(R.id.fitnessEdit1);
        permit_validity = (Spinner) findViewById(R.id.permitEdit1);
        drive = (Spinner) findViewById(R.id.driveEdit1);

        checkRTO = (CheckBox) findViewById(R.id.checkRTO);
        autoCity = (AutoCompleteTextView) findViewById(R.id.cityEdit);
        autoCity1 = (AutoCompleteTextView) findViewById(R.id.cityEdit1);
        autoCity2 = (AutoCompleteTextView) findViewById(R.id.cityEdit2);
        autoCity3 = (AutoCompleteTextView) findViewById(R.id.cityEdit3);
        autoCity4 = (AutoCompleteTextView) findViewById(R.id.cityEdit4);

        autoRTO = (AutoCompleteTextView) findViewById(R.id.rtoEdit);
        autoRTO1 = (AutoCompleteTextView) findViewById(R.id.rtoEdit1);
        autoRTO2 = (AutoCompleteTextView) findViewById(R.id.rtoEdit2);
        autoRTO3 = (AutoCompleteTextView) findViewById(R.id.rtoEdit3);
        autoRTO4 = (AutoCompleteTextView) findViewById(R.id.rtoEdit4);

        moresearchlinear = (LinearLayout) findViewById(R.id.moresearchlinear);

        bodyEdit = (EditText) findViewById(R.id.bodyEdit1);
        boatEdit = (EditText) findViewById(R.id.boatEdit1);
        rvEdit = (EditText) findViewById(R.id.rvEdit1);
        seatingEdit = (EditText) findViewById(R.id.seatsEdit1);
        transmissionEdit = (EditText) findViewById(R.id.transEdit1);
        useEdit = (EditText) findViewById(R.id.useEdit1);
        impletementEdit = (EditText) findViewById(R.id.implEdit);

        man_yr_from = (TextView) findViewById(R.id.ManufactureFromText);
        man_yr_to = (TextView) findViewById(R.id.ManufactureToText);
        kms_from = (TextView) findViewById(R.id.KmsFromText);
        kms_to = (TextView) findViewById(R.id.KmsToText);
        hrs_from = (TextView) findViewById(R.id.hrsFromText);
        hrs_to = (TextView) findViewById(R.id.hrsToText);
        pricefrom = (TextView) findViewById(R.id.PriceFromText);
        priceto = (TextView) findViewById(R.id.PriceToText);
        tyre_from = (TextView) findViewById(R.id.tyreText1);
        tyre_to = (TextView) findViewById(R.id.tyreText2);
        minihpcapacity = (TextView) findViewById(R.id.minihpcapacity);
        maxhpcapcity = (TextView) findViewById(R.id.maxhpcapcity);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String text = "<font color=#FF0000>*</font>";
                    financetxt.setText(Html.fromHtml("Finance Required:" + text));
                    messageText.setText(Html.fromHtml("Category:" + text));
                    messageText1.setText(Html.fromHtml("Sub category:" + text));
                    brandtxt.setText(Html.fromHtml("Brand:" + text));
                    modeltxt.setText(Html.fromHtml("Model:" + text));

                    if (getIntent().getExtras() != null) {
                        action = getIntent().getExtras().getString("className");
                        System.out.println("className: " + action);
                        if (action.equalsIgnoreCase("MySearchAdapter")) {
                            Scategory = getIntent().getExtras().getString("category");
                            Sbrand = getIntent().getExtras().getString("brand");
                            Smodel = getIntent().getExtras().getString("model");
                            Sprice = getIntent().getExtras().getString("price");
                            Syear = getIntent().getExtras().getString("year");
                            Sid = getIntent().getExtras().getString("search_id");
                            System.out.println("Search Id....: " + Sid);
                        }
                    }

                    getVehicleCategory();
                    getRTOCity();
                    getColors();

                    autoCity.setAdapter(new GooglePlacesAdapter(getApplicationContext(), R.layout.simple));
                    autoCity1.setAdapter(new GooglePlacesAdapter(getApplicationContext(), R.layout.simple));
                    autoCity2.setAdapter(new GooglePlacesAdapter(getApplicationContext(), R.layout.simple));
                    autoCity3.setAdapter(new GooglePlacesAdapter(getApplicationContext(), R.layout.simple));
                    autoCity4.setAdapter(new GooglePlacesAdapter(getApplicationContext(), R.layout.simple));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fuals.add("Diesel");
        fuals.add("Petrol");
        fuals.add("Electric");
        fuals.add("LPG");
        fuals.add("CNG");
        fuals.add("Hybrid");
        spinnerfual.setItems(fuals, "-Select Fuel-", this);

        /************************Range seek bar for manufacture***************************/
        RangeSeekBar<Integer> rangeSeekBar1 = new RangeSeekBar<Integer>(getApplicationContext());

        int regyear = Calendar.getInstance().get(Calendar.YEAR);
        int regyear1 = regyear - 25;
        rangeSeekBar1.setRangeValues(regyear1, regyear);
        rangeSeekBar1.setSelectedMinValue(regyear1);
        rangeSeekBar1.setSelectedMaxValue(regyear);

        rangeSeekBar1.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValue, Integer maxValue) {
                // TODO Auto-generated method stub
                man_yr_from.setText("" + minValue);
                man_yr_to.setText("" + maxValue);
                Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });
        // Add to layout
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.manYRLinear);
        layout1.addView(rangeSeekBar1);

        /**************RANGE BAR FOR REgistration ***********
         RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(getApplicationContext());
         // Set the range
         int year = Calendar.getInstance().get(Calendar.YEAR);
         int year1 = year - 25;
         rangeSeekBar.setRangeValues(year1, year);
         rangeSeekBar.setSelectedMinValue(year1);
         rangeSeekBar.setSelectedMaxValue(year);

         rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

        @Override public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
        Integer minValue, Integer maxValue) {
        // TODO Auto-generated method stub
        reg_yr_from.setText("" + minValue);
        reg_yr_to.setText("" + maxValue);
        Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
        }
        });
         // Add to layout
         LinearLayout layout = (LinearLayout)findViewById(R.id.REGYRLinear);
         layout.addView(rangeSeekBar);*/

        /**********************Range bar for price*****************************/
        RangeSeekBar<Integer> rangeSeekBar2 = new RangeSeekBar<Integer>(getApplicationContext());
        // Set the range
        int minprice = 50000;
        int maxprice = 1000000;
        rangeSeekBar2.setRangeValues(minprice, maxprice);
        rangeSeekBar2.setSelectedMinValue(minprice);
        rangeSeekBar2.setSelectedMaxValue(maxprice);

        rangeSeekBar2.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValue, Integer maxValue) {
                // TODO Auto-generated method stub
                pricefrom.setText("" + minValue);
                priceto.setText("" + maxValue);
                Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });
        // Add to layout
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.PriceLinear);
        layout2.addView(rangeSeekBar2);


        /**********************KMS Running*****************************/
        RangeSeekBar<Integer> rangeSeekBar3 = new RangeSeekBar<Integer>(getApplicationContext());
        // Set the range
        int min = 10000;
        int max = 100000;
        rangeSeekBar3.setRangeValues(min, max);
        rangeSeekBar3.setSelectedMinValue(min);
        rangeSeekBar3.setSelectedMaxValue(max);

        rangeSeekBar3.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValue, Integer maxValue) {
                // TODO Auto-generated method stub
                kms_from.setText("" + minValue);
                kms_to.setText("" + maxValue);
                Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });

        // Add to layout
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.kmsLinear);
        layout3.addView(rangeSeekBar3);

        /**********************Hrs Running*****************************/
        RangeSeekBar<Integer> rangeSeekBarhrs = new RangeSeekBar<Integer>(getApplicationContext());
        // Set the range
        int minhr = 0;
        int maxhr = 100000;
        rangeSeekBarhrs.setRangeValues(minhr, maxhr);
        rangeSeekBarhrs.setSelectedMinValue(minhr);
        rangeSeekBarhrs.setSelectedMaxValue(maxhr);

        rangeSeekBarhrs.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValue, Integer maxValue) {
                // TODO Auto-generated method stub
                hrs_from.setText("" + minValue);
                hrs_to.setText("" + maxValue);
                Log.i("Range is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });
        // Add to layout
        LinearLayout layouthrs = (LinearLayout) findViewById(R.id.hrslinear);
        layouthrs.addView(rangeSeekBarhrs);

        /**********************Hp Capacity*****************************/
        RangeSeekBar<Integer> rangeSeekBarhpcap = new RangeSeekBar<Integer>(getApplicationContext());
        // Set the range
        int minhpcap = 0;
        int maxhpcap = 100000;
        rangeSeekBarhpcap.setRangeValues(minhpcap, maxhpcap);
        rangeSeekBarhpcap.setSelectedMinValue(minhpcap);
        rangeSeekBarhpcap.setSelectedMaxValue(maxhpcap);

        rangeSeekBarhpcap.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValue, Integer maxValue) {
                // TODO Auto-generated method stub
                minihpcapacity.setText("" + minValue);
                maxhpcapcity.setText("" + maxValue);
                Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });
        // Add to layout
        LinearLayout layouthpcap = (LinearLayout) findViewById(R.id.hpcaplinear);
        layouthpcap.addView(rangeSeekBarhpcap);

        /**********************tyre condition*****************************/
        RangeSeekBar<Integer> rangeSeekBar4 = new RangeSeekBar<Integer>(getApplicationContext());
        // Set the range
        int minkm = 10000;
        int maxkm = 100000;
        rangeSeekBar4.setRangeValues(minkm, maxkm);
        rangeSeekBar4.setSelectedMinValue(minkm);
        rangeSeekBar4.setSelectedMaxValue(maxkm);

        rangeSeekBar4.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValue, Integer maxValue) {
                tyre_from.setText("" + minValue);
                tyre_to.setText("" + maxValue);
                Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });

        // Add to layout
        LinearLayout layout4 = (LinearLayout) findViewById(R.id.tyreLinear);
        layout4.addView(rangeSeekBar4);


        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                if (counter == 1) {
                    autoCity1.setVisibility(View.VISIBLE);
                } else if (counter == 2) {
                    autoCity1.setVisibility(View.VISIBLE);
                    autoCity2.setVisibility(View.VISIBLE);
                } else if (counter == 3) {
                    autoCity1.setVisibility(View.VISIBLE);
                    autoCity2.setVisibility(View.VISIBLE);
                    autoCity3.setVisibility(View.VISIBLE);
                } else if (counter == 4) {
                    autoCity1.setVisibility(View.VISIBLE);
                    autoCity2.setVisibility(View.VISIBLE);
                    autoCity3.setVisibility(View.VISIBLE);
                    autoCity4.setVisibility(View.VISIBLE);
                } else {
                    autoCity1.setVisibility(View.GONE);
                    autoCity2.setVisibility(View.GONE);
                    autoCity3.setVisibility(View.GONE);
                    autoCity4.setVisibility(View.GONE);
                    counter = 0;
                }
            }


        });

        btnRto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter1++;
                if (counter1 == 1) {
                    autoRTO1.setVisibility(View.VISIBLE);
                    checkRTO.setChecked(false);
                } else if (counter1 == 2) {
                    autoRTO1.setVisibility(View.VISIBLE);
                    autoRTO2.setVisibility(View.VISIBLE);
                    checkRTO.setChecked(false);
                } else if (counter1 == 3) {
                    autoRTO1.setVisibility(View.VISIBLE);
                    autoRTO2.setVisibility(View.VISIBLE);
                    autoRTO3.setVisibility(View.VISIBLE);
                } else if (counter1 == 4) {
                    autoRTO1.setVisibility(View.VISIBLE);
                    autoRTO2.setVisibility(View.VISIBLE);
                    autoRTO3.setVisibility(View.VISIBLE);
                    autoRTO4.setVisibility(View.VISIBLE);
                } else {
                    autoRTO1.setVisibility(View.GONE);
                    autoRTO2.setVisibility(View.GONE);
                    autoRTO3.setVisibility(View.GONE);
                    autoRTO4.setVisibility(View.GONE);
                    counter1 = 0;
                }

            }
        });

        if (action.equalsIgnoreCase("MySearchAdapter")) {
            allcategory.setSelection(getIndex(allcategory, Scategory));
            brand.setSelection(getIndex(brand, Sbrand));
            model.setSelection(getIndex(model, Smodel));

        }

        checkRTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkRTO.isChecked()) {
                    autoRTO.setVisibility(View.VISIBLE);
                    autoRTO1.setVisibility(View.GONE);
                    autoRTO2.setVisibility(View.GONE);
                    autoRTO3.setVisibility(View.GONE);
                    autoRTO4.setVisibility(View.GONE);

                    autoRTO.setText("Unregistered");

                    autoRTO.setEnabled(false);
                    autoRTO1.setEnabled(false);
                    autoRTO2.setEnabled(false);
                    autoRTO3.setEnabled(false);
                    autoRTO4.setEnabled(false);
                } else {
                    autoRTO.setVisibility(View.VISIBLE);
                    counter1 = 0;
                    autoRTO.setText("");
                    autoRTO1.setText("");
                    autoRTO2.setText("");
                    autoRTO3.setText("");
                    autoRTO4.setText("");

                    autoRTO.setEnabled(true);
                    autoRTO1.setEnabled(true);
                    autoRTO2.setEnabled(true);
                    autoRTO3.setEnabled(true);
                    autoRTO4.setEnabled(true);
                }
            }
        });

        BtnMoreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    count++;

                    System.out.println("moresearchlinear:" + count);

                    if (count % 2 == 1) {

                        moresearchlinear.setVisibility(View.VISIBLE);
                        Category = allcategory.getSelectedItem().toString();

                        switch (Category) {
                            case "Bus":

                                rowInvoice.setVisibility(View.GONE);
                                rowbody.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowdrive.setVisibility(View.GONE);
                                rowtransmission.setVisibility(View.GONE);
                                rowuse.setVisibility(View.GONE);
                                rowimpl.setVisibility(View.GONE);
                                break;
                            case "Car":

                                rowInvoice.setVisibility(View.GONE);
                                rowbustype.setVisibility(View.GONE);
                                rowbody.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowuse.setVisibility(View.GONE);
                                rowimpl.setVisibility(View.GONE);

                                radioPermitGroup = (RadioGroup) findViewById(R.id.radiopermit);
                                int selectedId = radioPermitGroup.getCheckedRadioButtonId();
                                radioPermitButton = (RadioButton) findViewById(selectedId);
                                permit1 = radioPermitButton.getText().toString();

                                if (permit1.equalsIgnoreCase("Private")) {
                                    rowpermit.setVisibility(View.GONE);
                                    rowfitness.setVisibility(View.GONE);
                                    rowtax.setVisibility(View.GONE);
                                }

                                break;
                            case " Construction Equipment ":

                                String subCategory = subcategory.getSelectedItem().toString();

                                rowbustype.setVisibility(View.GONE);
                                rowaircondition.setVisibility(View.GONE);
                                rowbody.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowcolor.setVisibility(View.GONE);
                                rowseat.setVisibility(View.GONE);
                                rowdrive.setVisibility(View.GONE);
                                rowtransmission.setVisibility(View.GONE);
                                rowuse.setVisibility(View.GONE);
                                rowimpl.setVisibility(View.GONE);

                                if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                                        || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                                        || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                                    rowInvoice.setVisibility(View.VISIBLE);
                                    rowpermit.setVisibility(View.GONE);
                                    rowfitness.setVisibility(View.GONE);
                                    rowtax.setVisibility(View.GONE);
                                    rowrc.setVisibility(View.GONE);
                                } else {
                                    rowrc.setVisibility(View.VISIBLE);
                                    rowpermit.setVisibility(View.VISIBLE);
                                    rowfitness.setVisibility(View.VISIBLE);
                                    rowtax.setVisibility(View.VISIBLE);
                                    rowInvoice.setVisibility(View.GONE);
                                }

                                break;
                            case "Commercial Vehicle":

                                rowInvoice.setVisibility(View.GONE);
                                rowbustype.setVisibility(View.GONE);
                                rowaircondition.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowseat.setVisibility(View.GONE);
                                rowdrive.setVisibility(View.GONE);
                                rowtransmission.setVisibility(View.GONE);
                                rowuse.setVisibility(View.GONE);
                                rowimpl.setVisibility(View.GONE);

                                break;
                            case "Tractor":

                                rowInvoice.setVisibility(View.GONE);
                                rowbustype.setVisibility(View.GONE);
                                rowaircondition.setVisibility(View.GONE);
                                rowbody.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowcolor.setVisibility(View.GONE);
                                rowseat.setVisibility(View.GONE);
                                rowdrive.setVisibility(View.GONE);
                                rowtransmission.setVisibility(View.GONE);

                                break;
                            case "2 Wheeler":

                                rowInvoice.setVisibility(View.GONE);
                                rowbustype.setVisibility(View.GONE);
                                rowaircondition.setVisibility(View.GONE);
                                rowbody.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowtax.setVisibility(View.GONE);
                                rowfitness.setVisibility(View.GONE);
                                rowpermit.setVisibility(View.GONE);
                                rowseat.setVisibility(View.GONE);
                                rowdrive.setVisibility(View.GONE);
                                rowtransmission.setVisibility(View.GONE);
                                rowuse.setVisibility(View.GONE);
                                rowimpl.setVisibility(View.GONE);

                                break;
                            case "3 Wheeler":

                                rowInvoice.setVisibility(View.GONE);
                                rowbustype.setVisibility(View.GONE);
                                rowaircondition.setVisibility(View.GONE);
                                rowbody.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowdrive.setVisibility(View.GONE);
                                rowtransmission.setVisibility(View.GONE);
                                rowuse.setVisibility(View.GONE);
                                rowimpl.setVisibility(View.GONE);

                                break;
                        }

                    } else
                        moresearchlinear.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        BtnRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                finance.setSelection(0);
                allcategory.setSelection(0);
                subcategory.setSelection(0);
                brand.setSelection(0);
                model.setSelection(0);
                version.setSelection(0);
                rc.setSelection(0);
                insurance.setSelection(0);
                owner.setSelection(0);
                hypo.setSelection(0);
                tax_validity.setSelection(0);
                fitness_validity.setSelection(0);
                permit_validity.setSelection(0);
                drive.setSelection(0);
                bus_type.setSelection(0);
                air.setSelection(0);
                invoice.setSelection(0);

                //AutoCompleteTextView  , autoRTO,,,autoCity3,autoCity4,autoRTO1,,,;

                autoCity.setText("");
                autoCity1.setText("");
                autoCity2.setText("");
                autoCity3.setText("");
                autoCity4.setText("");
                autoRTO.setText("");
                autoRTO1.setText("");
                autoRTO2.setText("");
                autoRTO3.setText("");
                autoRTO4.setText("");

                //EditText ,,,,,,;

                useEdit.setText("");
                seatingEdit.setText("");
                transmissionEdit.setText("");
                impletementEdit.setText("");
                bodyEdit.setText("");
                boatEdit.setText("");
                rvEdit.setText("");

                getfuel();


            }
        });

        BtnApplySearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finance1 = finance.getSelectedItem().toString();
                Category = allcategory.getSelectedItem().toString();

                try {
                    if (Category.equalsIgnoreCase("Car")) {
                        permit1 = radioPermitButton.getText().toString();
                    } else {
                        permit1 = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (finance1.startsWith("-Select")) {
                    Toast.makeText(getApplicationContext(), "Please provide fianance required or not", Toast.LENGTH_LONG).show();

                } else if (Category.equalsIgnoreCase("Select Category")) {
                    Toast.makeText(getApplicationContext(), "select catagory", Toast.LENGTH_LONG).show();

                } else if (subcategory.getSelectedItem().toString().equalsIgnoreCase("Select subcategory")) {
                    Toast.makeText(getApplicationContext(), "select subcatagory", Toast.LENGTH_LONG).show();

                } else if (brand.getSelectedItem().toString().equalsIgnoreCase("Select Brands")) {
                    Toast.makeText(getApplicationContext(), "select brand", Toast.LENGTH_LONG).show();

                } else if (model.getSelectedItem().toString().equalsIgnoreCase("Select Model")) {
                    Toast.makeText(getApplicationContext(), "select model", Toast.LENGTH_LONG).show();

                } else {
                    applySearch();
                }
            }
        });


    }

    public void applySearch() {

        System.out.println("inside apply search ");
        try {

            brand1 = brand.getSelectedItem().toString();
            Category = allcategory.getSelectedItem().toString();
            subCategory = subcategory.getSelectedItem().toString();
            model1 = model.getSelectedItem().toString();
            version1 = version.getSelectedItem().toString();
            man_yr1 = man_yr_from.getText().toString();
            man_yr2 = man_yr_to.getText().toString();
            reg_yr1 = reg_yr_from.getText().toString();
            reg_yr2 = reg_yr_to.getText().toString();

            rc1 = rc.getSelectedItem().toString();
            insurance1 = insurance.getSelectedItem().toString();
            tax_validity1 = tax_validity.getSelectedItem().toString();
            permit_validity1 = permit_validity.getSelectedItem().toString();
            fitness_validity1 = fitness_validity.getSelectedItem().toString();
            fual1 = spinnerfual.getSelectedItem().toString();
            color1 = multiSpinnercolor.getSelectedItem().toString();

            hypo1 = hypo.getSelectedItem().toString();
            drive1 = drive.getSelectedItem().toString();
            bus_type1 = bus_type.getSelectedItem().toString();
            air1 = air.getSelectedItem().toString();
            invoice1 = invoice.getSelectedItem().toString();

            tyre1 = tyre_from.getText().toString();
            tyre2 = tyre_to.getText().toString();

            kms1 = kms_from.getText().toString();
            kms2 = kms_to.getText().toString();

            hrs1 = hrs_from.getText().toString();
            hrs2 = hrs_to.getText().toString();
            hpcap1 = minihpcapacity.getText().toString();
            hpcap2 = maxhpcapcity.getText().toString();

            owner1 = owner.getSelectedItem().toString();
            price1 = pricefrom.getText().toString();
            price2 = priceto.getText().toString();
            city2 = autoRTO.getText().toString();
            city21 = autoRTO1.getText().toString();
            city22 = autoRTO2.getText().toString();
            city23 = autoRTO3.getText().toString();
            city24 = autoRTO4.getText().toString();
            city1 = autoCity.getText().toString();
            city11 = autoCity1.getText().toString();
            city12 = autoCity2.getText().toString();
            city13 = autoCity3.getText().toString();
            city14 = autoCity4.getText().toString();

            use1 = useEdit.getText().toString();
            rv1 = rvEdit.getText().toString();
            boat1 = boatEdit.getText().toString();
            body1 = bodyEdit.getText().toString();
            transmission1 = transmissionEdit.getText().toString();
            seating1 = seatingEdit.getText().toString();
            implement1 = impletementEdit.getText().toString();


            if (color1.startsWith("-Select")) {
                color1 = "";
            } else {
                color1 = multiSpinnercolor.getSelectedItem().toString();
            }


            if (rc1.startsWith("-Select")) {
                rc1 = "";
            } else {
                rc1 = rc.getSelectedItem().toString();
            }

            if (insurance1.startsWith("-Select")) {
                insurance1 = "";
            } else {
                insurance1 = insurance.getSelectedItem().toString();
            }

            if (tax_validity1.startsWith("-Select")) {
                tax_validity1 = "";
            } else {
                tax_validity1 = tax_validity.getSelectedItem().toString();
            }

            if (permit_validity1.startsWith("-Select")) {
                permit_validity1 = "";
            } else {
                permit_validity1 = permit_validity.getSelectedItem().toString();
            }

            if (fitness_validity1.startsWith("-Select")) {
                fitness_validity1 = "";
            } else {
                fitness_validity1 = fitness_validity.getSelectedItem().toString();
            }

            if (fual1.startsWith("-Select")) {
                fual1 = "";
            } else {
                fual1 = spinnerfual.getSelectedItem().toString();
            }

            if (hypo1.startsWith("-Select")) {
                hypo1 = "";
            } else {
                hypo1 = hypo.getSelectedItem().toString();
            }

            if (drive1.startsWith("-Select")) {
                drive1 = "";
            } else {
                drive1 = drive.getSelectedItem().toString();
            }


            if (finance1.startsWith("-Select")) {
                finance1 = "";
            } else {
                finance1 = finance.getSelectedItem().toString();
            }

            if (bus_type1.startsWith("-Select")) {
                bus_type1 = "";
            } else {
                bus_type1 = bus_type.getSelectedItem().toString();
            }

            if (air1.startsWith("-Select")) {
                air1 = "";
            } else {
                air1 = air.getSelectedItem().toString();
            }

            if (invoice1.startsWith("-Select")) {
                invoice1 = "";
            } else {
                invoice1 = invoice.getSelectedItem().toString();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        //SaveSearchTask();

    }


    private void getVehicleCategory() {
        mApiCall.getVehicleList();
    }

    /*
    Sub Category...
     */
    private void getSubCategoryTask(String vehicle_id) {
        mApiCall.getVehicleSubtype(vehicle_id);
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
    Get Version...
     */
    private void getVersion(String categoryId, String subCategoryId, String brandId, String modelId) {
        mApiCall.getVersion(categoryId, subCategoryId, brandId, modelId);
    }

    /*
    Get Colors
     */
    private void getColors() {
        mApiCall.getColor();
    }

    /*
    Get Rto city
    */
    private void getRTOCity() {
        mApiCall.getVehicleRTOCity();
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

    public void getfuel() {
        spinnerfual.setItems(fuals, "-Select Fuel-", this);
        multiSpinnercolor.setItems(colors, "-Select Color-", this);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                //Vehicle type
                if (response.body() instanceof GetVehicleListResponse) {
                    GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                    //Category
                    List<String> mCategoryId = new ArrayList<>();
                    final List<String> parsedData = new ArrayList<>();
                    final HashMap<String, String> mCategoryMap = new HashMap<>();

                    mCategoryId.add("Select Category");
                    if (!mGetVehicleListResponse.getSuccess().isEmpty()) {

                        for (GetVehicleListResponse.Success mSuccess : mGetVehicleListResponse.getSuccess()) {
                            mSuccess.setId(mSuccess.getId());
                            mSuccess.setName(mSuccess.getName());

                            mCategoryId.add(mSuccess.getName());
                            mCategoryMap.put(mSuccess.getName(), mSuccess.getId());

                        }

                        parsedData.addAll(mCategoryId);
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, parsedData);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        allcategory.setAdapter(adapter);
                        allcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    vehicle_id = mCategoryMap.get(parsedData.get(position));
                                    String Category = parsedData.get(position);

                                    System.out.println("cat is::" + vehicle_id);
                                    System.out.println("cat name::" + Category);
                                    moresearchlinear.setVisibility(View.GONE);
                                    count = 0;

                                    getSubCategoryTask(vehicle_id);

                                    if (Category.equals("Car"))
                                        rowPermit.setVisibility(View.VISIBLE);
                                    else
                                        rowPermit.setVisibility(View.GONE);


                                    if (Category.equals("Tractor") || Category.equals("Cranes") || Category.equalsIgnoreCase(" Construction Equipment ")) {

                                        rowKms.setVisibility(View.GONE);
                                        rowkms1.setVisibility(View.GONE);
                                        rowhrs.setVisibility(View.VISIBLE);
                                        rowhpcapacity.setVisibility(View.VISIBLE);
                                        rowhrstb.setVisibility(View.VISIBLE);
                                        rowhpcap.setVisibility(View.VISIBLE);

                                    } else {
                                        rowKms.setVisibility(View.VISIBLE);
                                        rowkms1.setVisibility(View.VISIBLE);
                                        rowhrs.setVisibility(View.GONE);
                                        rowhpcapacity.setVisibility(View.GONE);
                                        rowhrstb.setVisibility(View.GONE);
                                        rowhpcap.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                }
                //Vehicle Sub type
                else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    Log.e("GetVehicleTypes", "->");
                    List<String> mSubTypeList = new ArrayList<>();
                    final List<String> parsedData = new ArrayList<>();
                    final HashMap<String, String> mSubTypeMap = new HashMap<>();

                    mSubTypeList.add("Select subcategory");
                    GetVehicleSubTypeResponse mGetVehicleSubTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    for (GetVehicleSubTypeResponse.Success subTypeResponse : mGetVehicleSubTypeResponse.getSuccess()) {
                        subTypeResponse.setId(subTypeResponse.getId());
                        subTypeResponse.setName(subTypeResponse.getName());
                        mSubTypeList.add(subTypeResponse.getName());
                        mSubTypeMap.put(subTypeResponse.getName(), subTypeResponse.getId());
                    }
                    parsedData.addAll(mSubTypeList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, parsedData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subcategory.setAdapter(adapter);
                    brand.setAdapter(null);
                    subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                sub_category_id = mSubTypeMap.get(parsedData.get(position));
                                subCategory = parsedData.get(position);

                                System.out.println("Sub cat is::" + sub_category_id);
                                System.out.println("Sub cat name::" + subCategory);

                                getBrand(vehicle_id, sub_category_id);

                                moresearchlinear.setVisibility(View.GONE);
                                count = 0;

                                if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                                        || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                                        || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                                    rowRTO.setVisibility(View.GONE);

                                } else {
                                    rowRTO.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                //Vehicle Brand
                else if (response.body() instanceof GetVehicleBrandResponse) {
                    Log.e("GetVehicleBrands", "->");
                    List<String> mBrandList = new ArrayList<>();
                    final List<String> brandData = new ArrayList<>();
                    final HashMap<String, String> mBrandMap = new HashMap<>();

                    mBrandList.add("Select Brands");
                    GetVehicleBrandResponse getVehicleBrandResponse = (GetVehicleBrandResponse) response.body();
                    for (GetVehicleBrandResponse.Success brandResponse : getVehicleBrandResponse.getSuccess()) {
                        brandResponse.setBrandId(brandResponse.getBrandId());
                        brandResponse.setBrandTitle(brandResponse.getBrandTitle());
                        mBrandList.add(brandResponse.getBrandTitle());
                        mBrandMap.put(brandResponse.getBrandTitle(), brandResponse.getBrandId());
                    }
                    //mBrandList.add("other");
                    brandData.addAll(mBrandList);
                    Log.i("ListBrand", "->" + mBrandList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, brandData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    brand.setAdapter(adapter);
                    model.setAdapter(null);
                    brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                position_brand_id = mBrandMap.get(brandData.get(position));
                                String brandName = brandData.get(position);

                                System.out.println("Brand id is::" + position_brand_id);
                                System.out.println("Brand name::" + brandName);

                                getModel(vehicle_id, sub_category_id, position_brand_id);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                //Vehicle Model
                else if (response.body() instanceof GetVehicleModelResponse) {
                    Log.e("GetVehicleModel", "->");
                    List<String> mModelList = new ArrayList<>();
                    final List<String> modelData = new ArrayList<>();
                    final HashMap<String, String> mModelMap = new HashMap<>();

                    mModelList.add("Select Model");
                    GetVehicleModelResponse getVehicleModelResponse = (GetVehicleModelResponse) response.body();
                    for (GetVehicleModelResponse.Success modelResponse : getVehicleModelResponse.getSuccess()) {
                        modelResponse.setModelId(modelResponse.getModelId());
                        modelResponse.setModelTitle(modelResponse.getModelTitle());
                        mModelList.add(modelResponse.getModelTitle());
                        mModelMap.put(modelResponse.getModelTitle(), modelResponse.getModelId());
                    }
                    //mModelList.add("other");
                    modelData.addAll(mModelList);
                    Log.i("ListModel", "->" + mModelList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(adapter);
                    version.setAdapter(null);
                    model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                position_model_id = mModelMap.get(modelData.get(position));
                                String modelName = modelData.get(position);

                                System.out.println("Model id is::" + position_model_id);
                                System.out.println("Model name::" + modelName);

                                getVersion(vehicle_id, sub_category_id, position_brand_id, position_model_id);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                //Vehicle Version
                else if (response.body() instanceof GetVehicleVersionResponse) {
                    Log.e("GetVehicleVersion", "->");
                    List<String> mVersionIdList = new ArrayList<>();
                    final List<String> versionData = new ArrayList<>();
                    final HashMap<String, String> mVersionMap = new HashMap<>();

                    mVersionIdList.add("Select Version");
                    GetVehicleVersionResponse getVehicleVersionResponse = (GetVehicleVersionResponse) response.body();
                    for (GetVehicleVersionResponse.Success versionResponse : getVehicleVersionResponse.getSuccess()) {
                        versionResponse.setVersionId(versionResponse.getVersionId());
                        versionResponse.setVersion(versionResponse.getVersion());
                        mVersionIdList.add(versionResponse.getVersion());
                        mVersionMap.put(versionResponse.getVersion(), versionResponse.getVersionId());
                    }
                    //mVersionIdList.add("other");
                    versionData.addAll(mVersionIdList);
                    Log.i("ListVersion", "->" + mVersionIdList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, versionData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    version.setAdapter(adapter);
                    version.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String versionId = mVersionMap.get(versionData.get(position));
                                String versionName = versionData.get(position);

                                System.out.println("Version id is::" + versionId);
                                System.out.println("Version name::" + versionName);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                //Color Response

                else if (response.body() instanceof ColorResponse) {
                    Log.e("GetColor", "->");
                    final List<String> mColorList = new ArrayList<>();
                    /*final List<String> versionData = new ArrayList<>();
                    final HashMap<String, String> mVersionMap = new HashMap<>();*/

                    mColorList.add("Select Color");
                    ColorResponse getVehicleVersionResponse = (ColorResponse) response.body();
                    for (ColorResponse.Success colorResponse : getVehicleVersionResponse.getSuccess()) {
                        colorResponse.setTitle(colorResponse.getTitle());
                        mColorList.add(colorResponse.getTitle());
                        /*mVersionIdList.add(versionResponse.getVersion());
                        mVersionMap.put(versionResponse.getVersion(), versionResponse.getVersionId());*/
                    }
                    /*mVersionIdList.add("other");
                    versionData.addAll(mVersionIdList);*/
                    Log.i("ListColor", "->" + mColorList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, mColorList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    multiSpinnercolor.setAdapter(adapter);
                    multiSpinnercolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String ColorName = multiSpinnercolor.getSelectedItem().toString();

                                System.out.println("Color name::" + ColorName);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                //Rto city
                else if (response.body() instanceof GetRTOCityResponse) {
                    final List<String> mRtoCity = new ArrayList<String>();
                    mRtoCity.add("-Select RTO city-");
                    GetRTOCityResponse mGetRTOCityResponse = (GetRTOCityResponse) response.body();
                    for (GetRTOCityResponse.Success success : mGetRTOCityResponse.getSuccess()) {
                        success.setRtoCityId(success.getRtoCityId());
                        success.setRtoCityName(success.getRtoCityName());
                        mRtoCity.add(success.getRtoCityName());
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, mRtoCity);
                    autoRTO.setAdapter(dataAdapter);
                    autoRTO1.setAdapter(dataAdapter);
                    autoRTO2.setAdapter(dataAdapter);
                    autoRTO3.setAdapter(dataAdapter);
                    autoRTO4.setAdapter(dataAdapter);
                }

                //

            } else
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));

        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));


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
            Log.i("Check Class-", "Search Vehicle Activity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
