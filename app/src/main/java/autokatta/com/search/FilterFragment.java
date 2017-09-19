package autokatta.com.search;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.MultiSelectionSpinner;
import autokatta.com.Registration.Multispinner;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.RangeSeekBar;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ColorResponse;
import autokatta.com.response.GetBodyTypeResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 19/4/17.
 */

public class FilterFragment extends Fragment implements Multispinner.MultiSpinnerListener, MultiSelectionSpinner.MultiSpinnerListener, RequestNotifier {

    SharedPreferences prefs;
    FloatingActionButton getHelp;
    EditText useEdit, seatingEdit, transmissionEdit, impletementEdit, boatEdit, rvEdit;
    Button BtnRefresh, BtnApplySearch, BtnMoreOptions, btnCity, btnRto;
    Spinner rc, insurance, owner, hypo, brand, model, allcategory, subcategory, version, mBodyTypeSpinner;
    Spinner tax_validity, fitness_validity, permit_validity, drive, bus_type, air, invoice, finance;
    MultiSelectionSpinner spinnerfual;

    TextView man_yr_from, man_yr_to, tyre_from, tyre_to, reg_yr_from, reg_yr_to, kms_from, kms_to, pricefrom, priceto, hrs_from, hrs_to, minihpcapacity, maxhpcapcity;

    AutoCompleteTextView autoCity, autoRTO, autoCity1, autoCity2, autoCity3, autoCity4, autoRTO1, autoRTO2, autoRTO3, autoRTO4;
    CheckBox checkRTO;
    private RadioGroup radioPermitGroup;
    private RadioButton radioPermitButton;
    Multispinner multiSpinnercolor;
    LinearLayout moresearchlinear;
    List<String> mBodyType = new ArrayList<>();
    TableRow rowFinacial, rowCategory, rowSubcat, rowPermit, rowBrand, rowModel, rowVersion, rowCity, rowRTO, rowManyr, rowmanyr1, rowREGyr, rowREGyr1, rowPrice, rowprice1, rowKms, rowkms1, rowhrs, rowhrstb, rowhpcapacity, rowhpcap, rowowners;

    TableRow rowInvoice, rowbustype, rowaircondition, rowbody, rowboattype, rowrvtype, rowcolor, rowrc, rowinsurance1, rowhypo, rowtax, rowfitness, rowpermit, rowfual, rowseat, rowdrive, rowtransmission, rowuse, rowimpl, rowtyre, rowtyrerange;

    String action, Scategory, Sbrand, Smodel, Sprice, Syear, Category, subCategory, hrs1, hrs2, hpcap1, hpcap2;
    int position_brand_id, position_model_id;
    String spinnervalues[];

    String city1, city2, city11, city12, city13, city14, city21, city22, city23, city24, brand1, model1, color1, version1, man_yr1, man_yr2, reg_yr1, reg1, reg_yr2, rc1, insurance1, kms1, kms2, hypo1, price1, price2;
    String permit1, tax_validity1, fitness_validity1, permit_validity1, drive1, fual1, bus_type1, air1, invoice1;

    String use1, seating1, transmission1, implement1, body1, boat1, rv1, finance1, tyre1, tyre2, callPermission, myContact;
    int count = 0, owner1 = 0, Sid, vehicle_id, sub_category_id;

    final List<String> fuals = new ArrayList<String>();
    List<String> colors = new ArrayList<String>();

    int counter = 0;
    int counter1 = 0;
    ConnectionDetector mConnectionDetector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View root = inflater.inflate(R.layout.filter_all_activity, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");

        mConnectionDetector = new ConnectionDetector(getActivity());

        TextView financetxt = (TextView) root.findViewById(R.id.financetxt);
        TextView messageText = (TextView) root.findViewById(R.id.messageText);
        TextView messageText1 = (TextView) root.findViewById(R.id.messageText1);
        TextView brandtxt = (TextView) root.findViewById(R.id.brandtxt);
        TextView modeltxt = (TextView) root.findViewById(R.id.modeltxt);
        TextView ownertxt = (TextView) root.findViewById(R.id.ownertxt);
//		TextView versiontxt=(TextView)root.findViewById(R.id.versiontxt);

        getHelp = (FloatingActionButton) root.findViewById(R.id.fabHelp);

        rowPermit = (TableRow) root.findViewById(R.id.rowPermit);
        rowRTO = (TableRow) root.findViewById(R.id.rowRTO);
        rowManyr = (TableRow) root.findViewById(R.id.rowManyr);
        rowmanyr1 = (TableRow) root.findViewById(R.id.rowmanyr1);
        rowREGyr = (TableRow) root.findViewById(R.id.rowREGyr);
        rowREGyr1 = (TableRow) root.findViewById(R.id.rowREGyr1);
        rowPrice = (TableRow) root.findViewById(R.id.rowPrice);
        rowprice1 = (TableRow) root.findViewById(R.id.rowprice1);
        rowKms = (TableRow) root.findViewById(R.id.rowKms);
        rowkms1 = (TableRow) root.findViewById(R.id.rowkms1);
        rowhrs = (TableRow) root.findViewById(R.id.rowhrs);
        rowhrstb = (TableRow) root.findViewById(R.id.rowhrstb);
        rowhpcapacity = (TableRow) root.findViewById(R.id.rowhpcapacity);
        rowhpcap = (TableRow) root.findViewById(R.id.rowhpcap);

        rowInvoice = (TableRow) root.findViewById(R.id.rowInvoice);
        rowbustype = (TableRow) root.findViewById(R.id.rowbustype);
        rowaircondition = (TableRow) root.findViewById(R.id.rowaircondition);
        rowbody = (TableRow) root.findViewById(R.id.rowbody);
        rowboattype = (TableRow) root.findViewById(R.id.rowboattype);
        rowrvtype = (TableRow) root.findViewById(R.id.rowrvtype);
        rowcolor = (TableRow) root.findViewById(R.id.rowcolor);
        rowrc = (TableRow) root.findViewById(R.id.rowrc);
        rowinsurance1 = (TableRow) root.findViewById(R.id.rowinsurance1);
        rowhypo = (TableRow) root.findViewById(R.id.rowhypo);
        rowtax = (TableRow) root.findViewById(R.id.rowtax);
        rowfitness = (TableRow) root.findViewById(R.id.rowfitness);
        rowpermit = (TableRow) root.findViewById(R.id.rowpermit);
        rowfual = (TableRow) root.findViewById(R.id.rowfual);
        rowseat = (TableRow) root.findViewById(R.id.rowseat);
        rowdrive = (TableRow) root.findViewById(R.id.rowdrive);
        rowtransmission = (TableRow) root.findViewById(R.id.rowtransmission);
        rowuse = (TableRow) root.findViewById(R.id.rowuse);
        rowimpl = (TableRow) root.findViewById(R.id.rowimpl);
        rowtyre = (TableRow) root.findViewById(R.id.rowtyre);
        rowimpl = (TableRow) root.findViewById(R.id.rowimpl);
        rowtyre.setVisibility(View.GONE);


        BtnMoreOptions = (Button) root.findViewById(R.id.BtnMoreOptions);
        btnCity = (Button) root.findViewById(R.id.btncity);
        BtnApplySearch = (Button) root.findViewById(R.id.BtnApplySearch);
        btnRto = (Button) root.findViewById(R.id.btnrto);
        BtnRefresh = (Button) root.findViewById(R.id.BtnRefresh);

        multiSpinnercolor = (Multispinner) root.findViewById(R.id.colorEdit1);
        spinnerfual = (MultiSelectionSpinner) root.findViewById(R.id.fualEdit1);

        finance = (Spinner) root.findViewById(R.id.finacialEdit1);
        allcategory = (Spinner) root.findViewById(R.id.allCategory1);
        subcategory = (Spinner) root.findViewById(R.id.subCategory);
        brand = (Spinner) root.findViewById(R.id.BrandEdit1);
        model = (Spinner) root.findViewById(R.id.ModelEdit1);
        version = (Spinner) root.findViewById(R.id.VersionEdit1);
        owner = (Spinner) root.findViewById(R.id.ownerEdit);

        invoice = (Spinner) root.findViewById(R.id.invoiceEdit);
        bus_type = (Spinner) root.findViewById(R.id.bustypeEdit);
        air = (Spinner) root.findViewById(R.id.aircondEdit);
        rc = (Spinner) root.findViewById(R.id.rcEdit1);
        insurance = (Spinner) root.findViewById(R.id.insEdit1);
        hypo = (Spinner) root.findViewById(R.id.hypoEdit1);
        tax_validity = (Spinner) root.findViewById(R.id.taxEdit1);
        fitness_validity = (Spinner) root.findViewById(R.id.fitnessEdit1);
        permit_validity = (Spinner) root.findViewById(R.id.permitEdit1);
        drive = (Spinner) root.findViewById(R.id.driveEdit1);

        checkRTO = (CheckBox) root.findViewById(R.id.checkRTO);
        autoCity = (AutoCompleteTextView) root.findViewById(R.id.cityEdit);
        autoCity1 = (AutoCompleteTextView) root.findViewById(R.id.cityEdit1);
        autoCity2 = (AutoCompleteTextView) root.findViewById(R.id.cityEdit2);
        autoCity3 = (AutoCompleteTextView) root.findViewById(R.id.cityEdit3);
        autoCity4 = (AutoCompleteTextView) root.findViewById(R.id.cityEdit4);

        autoRTO = (AutoCompleteTextView) root.findViewById(R.id.rtoEdit);
        autoRTO1 = (AutoCompleteTextView) root.findViewById(R.id.rtoEdit1);
        autoRTO2 = (AutoCompleteTextView) root.findViewById(R.id.rtoEdit2);
        autoRTO3 = (AutoCompleteTextView) root.findViewById(R.id.rtoEdit3);
        autoRTO4 = (AutoCompleteTextView) root.findViewById(R.id.rtoEdit4);

        moresearchlinear = (LinearLayout) root.findViewById(R.id.moresearchlinear);

        mBodyTypeSpinner = (Spinner) root.findViewById(R.id.bodytypespinner);
        boatEdit = (EditText) root.findViewById(R.id.boatEdit1);
        rvEdit = (EditText) root.findViewById(R.id.rvEdit1);
        seatingEdit = (EditText) root.findViewById(R.id.seatsEdit1);
        transmissionEdit = (EditText) root.findViewById(R.id.transEdit1);
        useEdit = (EditText) root.findViewById(R.id.useEdit1);
        impletementEdit = (EditText) root.findViewById(R.id.implEdit);

        man_yr_from = (TextView) root.findViewById(R.id.ManufactureFromText);
        man_yr_to = (TextView) root.findViewById(R.id.ManufactureToText);
        reg_yr_from = (TextView) root.findViewById(R.id.RegistrationFromText);
        reg_yr_to = (TextView) root.findViewById(R.id.RegistrationToText);
        kms_from = (TextView) root.findViewById(R.id.KmsFromText);
        kms_to = (TextView) root.findViewById(R.id.KmsToText);
        hrs_from = (TextView) root.findViewById(R.id.hrsFromText);
        hrs_to = (TextView) root.findViewById(R.id.hrsToText);
        pricefrom = (TextView) root.findViewById(R.id.PriceFromText);
        priceto = (TextView) root.findViewById(R.id.PriceToText);
        tyre_from = (TextView) root.findViewById(R.id.tyreText1);
        tyre_to = (TextView) root.findViewById(R.id.tyreText2);
        minihpcapacity = (TextView) root.findViewById(R.id.minihpcapacity);
        maxhpcapcity = (TextView) root.findViewById(R.id.maxhpcapcity);

        String text = "<font color=#FF0000>*</font>";
        financetxt.setText(Html.fromHtml("Finance Required:" + text));
        messageText.setText(Html.fromHtml("Category:" + text));
        messageText1.setText(Html.fromHtml("Sub category:" + text));
        brandtxt.setText(Html.fromHtml("Brand:" + text));
        modeltxt.setText(Html.fromHtml("Model:" + text));
        ownertxt.setText(Html.fromHtml("Owner(s):" + text));

        fuals.add("Diesel");
        fuals.add("Petrol");
        fuals.add("Eletric");
        fuals.add("LPG");
        fuals.add("CNG");
        fuals.add("Hybrid");
        spinnerfual.setItems(fuals, "-Select Fuel-", this);

        Bundle b = getArguments();
        action = b.getString("action");
        System.out.println("Actionnnnnnnnnnnn: " + action);
        if (action.equalsIgnoreCase("update")) {
            Scategory = b.getString("category");
            Sbrand = b.getString("brand");
            Smodel = b.getString("model");
            Sprice = b.getString("price");
            Syear = b.getString("year");
            Sid = b.getInt("search_id");
            System.out.println("Search Id....: " + Sid);
        }

        rowREGyr.setVisibility(View.GONE);
        rowREGyr1.setVisibility(View.GONE);

        if (!mConnectionDetector.isConnectedToInternet())
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
        else {
            try {
                getAllCategory();
                getRTOCity();
                callColorWebservices();
                getBodyTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }

            autoCity.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
            autoCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        autoCity.setEnabled(true);
                    } else {
                        autoCity.setEnabled(true);
                    }
                }
            });
            autoCity1.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
            autoCity1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        autoCity1.setEnabled(true);
                    } else {
                        autoCity1.setEnabled(true);
                    }
                }
            });
            autoCity2.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
            autoCity2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        autoCity2.setEnabled(true);
                    } else {
                        autoCity2.setEnabled(true);
                    }
                }
            });
            autoCity3.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
            autoCity3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        autoCity3.setEnabled(true);

                    } else {
                        autoCity3.setEnabled(true);
                    }
                }
            });
            autoCity4.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
            autoCity4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        autoCity4.setEnabled(true);
                    } else {
                        autoCity4.setEnabled(true);
                    }
                }
            });

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

            if (action.equalsIgnoreCase("update")) {
                allcategory.setSelection(getIndex(allcategory, Scategory));
                brand.setSelection(getIndex(brand, Sbrand));
                model.setSelection(getIndex(model, Smodel));
            }

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
                        Toast.makeText(getActivity(), "Please provide finance required or not", Toast.LENGTH_SHORT).show();
                    } else if (Category.equals("-Select Category-")) {
                        Toast.makeText(getActivity(), "select catagory", Toast.LENGTH_SHORT).show();
                    } else if (subcategory.getSelectedItem().toString().equals("-Select subcategory-")) {
                        Toast.makeText(getActivity(), "select subcatagory", Toast.LENGTH_SHORT).show();
                    } else if (brand.getSelectedItem().toString().equals("-Select Brand-")) {
                        Toast.makeText(getActivity(), "select brand", Toast.LENGTH_SHORT).show();
                    } else if (model.getSelectedItem().toString().equals("-Select Models-")) {
                        Toast.makeText(getActivity(), "select model", Toast.LENGTH_SHORT).show();
                    } else {
                        applySearch();
                    }
                }
            });


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


            /************************Range seek bar for manufacture***************************/
            RangeSeekBar<Integer> rangeSeekBar1 = new RangeSeekBar<Integer>(getActivity());
            int regyear = Calendar.getInstance().get(Calendar.YEAR);
            int regyear1 = regyear - 25;
            rangeSeekBar1.setRangeValues(regyear1, regyear);
            rangeSeekBar1.setSelectedMinValue(regyear1);
            rangeSeekBar1.setSelectedMaxValue(regyear);

            man_yr_from.setText(String.valueOf(regyear1));
            man_yr_to.setText(String.valueOf(regyear));

            rangeSeekBar1.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                        Integer minValue, Integer maxValue) {
                    // TODO Auto-generated method stub
                    man_yr_from.setText(String.valueOf(minValue));
                    man_yr_to.setText(String.valueOf(maxValue));
                    Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });
            // Add to layout
            LinearLayout layout1 = (LinearLayout) root.findViewById(R.id.manYRLinear);
            layout1.addView(rangeSeekBar1);

            /**************RANGE BAR FOR REgistration ************/
            RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(getActivity());
            // Set the range
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int year1 = year - 25;
            rangeSeekBar.setRangeValues(year1, year);
            rangeSeekBar.setSelectedMinValue(year1);
            rangeSeekBar.setSelectedMaxValue(year);

            rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                        Integer minValue, Integer maxValue) {
                    // TODO Auto-generated method stub
                    reg_yr_from.setText(String.valueOf(minValue));
                    reg_yr_to.setText(String.valueOf(maxValue));
                    Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });
            // Add to layout
            LinearLayout layout = (LinearLayout) root.findViewById(R.id.REGYRLinear);
            layout.addView(rangeSeekBar);

            /**********************Range bar for price*****************************/
            RangeSeekBar<Integer> rangeSeekBar2 = new RangeSeekBar<Integer>(getActivity());
            // Set the range
            int minprice = 0;
            int maxprice = 1000000;
            rangeSeekBar2.setRangeValues(minprice, maxprice);
            rangeSeekBar2.setSelectedMinValue(minprice);
            rangeSeekBar2.setSelectedMaxValue(maxprice);
            pricefrom.setText(String.valueOf(minprice));
            priceto.setText(String.valueOf(maxprice));

            rangeSeekBar2.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                        Integer minValue, Integer maxValue) {
                    // TODO Auto-generated method stub
                    pricefrom.setText(String.valueOf(minValue));
                    priceto.setText(String.valueOf(maxValue));
                    Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });
            // Add to layout
            LinearLayout layout2 = (LinearLayout) root.findViewById(R.id.PriceLinear);
            layout2.addView(rangeSeekBar2);


            /**********************KMS Running*****************************/
            RangeSeekBar<Integer> rangeSeekBar3 = new RangeSeekBar<Integer>(getActivity());
            // Set the range
            int min = 0;
            int max = 100000;
            rangeSeekBar3.setRangeValues(min, max);
            rangeSeekBar3.setSelectedMinValue(min);
            rangeSeekBar3.setSelectedMaxValue(max);
            kms_from.setText(String.valueOf(min));
            kms_to.setText(String.valueOf(max));

            rangeSeekBar3.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                        Integer minValue, Integer maxValue) {
                    // TODO Auto-generated method stub
                    kms_from.setText(String.valueOf(minValue));
                    kms_to.setText(String.valueOf(maxValue));
                    Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });

            // Add to layout
            LinearLayout layout3 = (LinearLayout) root.findViewById(R.id.kmsLinear);
            layout3.addView(rangeSeekBar3);

            /**********************Hrs Running*****************************/
            RangeSeekBar<Integer> rangeSeekBarhrs = new RangeSeekBar<Integer>(getActivity());
            // Set the range
            int minhr = 0;
            int maxhr = 100000;
            rangeSeekBarhrs.setRangeValues(minhr, maxhr);
            rangeSeekBarhrs.setSelectedMinValue(minhr);
            rangeSeekBarhrs.setSelectedMaxValue(maxhr);

            hrs_from.setText(String.valueOf(minhr));
            hrs_to.setText(String.valueOf(maxhr));

            rangeSeekBarhrs.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                        Integer minValue, Integer maxValue) {
                    // TODO Auto-generated method stub
                    hrs_from.setText(String.valueOf(minValue));
                    hrs_to.setText(String.valueOf(maxValue));
                    Log.i("Range is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });
            // Add to layout
            LinearLayout layouthrs = (LinearLayout) root.findViewById(R.id.hrslinear);
            layouthrs.addView(rangeSeekBarhrs);

            /**********************Hp Capacity*****************************/
            RangeSeekBar<Integer> rangeSeekBarhpcap = new RangeSeekBar<Integer>(getActivity());
            // Set the range
            int minhpcap = 0;
            int maxhpcap = 100000;
            rangeSeekBarhpcap.setRangeValues(minhpcap, maxhpcap);
            rangeSeekBarhpcap.setSelectedMinValue(minhpcap);
            rangeSeekBarhpcap.setSelectedMaxValue(maxhpcap);

            minihpcapacity.setText(String.valueOf(minhpcap));
            maxhpcapcity.setText(String.valueOf(maxhpcap));

            rangeSeekBarhpcap.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                        Integer minValue, Integer maxValue) {
                    // TODO Auto-generated method stub
                    minihpcapacity.setText(String.valueOf(minValue));
                    maxhpcapcity.setText(String.valueOf(maxValue));
                    Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });
            // Add to layout
            LinearLayout layouthpcap = (LinearLayout) root.findViewById(R.id.hpcaplinear);
            layouthpcap.addView(rangeSeekBarhpcap);

            /**********************tyre condition*****************************/
            RangeSeekBar<Integer> rangeSeekBar4 = new RangeSeekBar<Integer>(getActivity());
            // Set the range
            int minkm = 0;
            int maxkm = 100000;
            rangeSeekBar4.setRangeValues(minkm, maxkm);
            rangeSeekBar4.setSelectedMinValue(minkm);
            rangeSeekBar4.setSelectedMaxValue(maxkm);

            tyre_from.setText(String.valueOf(minkm));
            tyre_to.setText(String.valueOf(maxkm));

            rangeSeekBar4.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                        Integer minValue, Integer maxValue) {
                    tyre_from.setText(String.valueOf(minValue));
                    tyre_to.setText(String.valueOf(maxValue));
                    Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });

            // Add to layout
            LinearLayout layout4 = (LinearLayout) root.findViewById(R.id.tyreLinear);
            layout4.addView(rangeSeekBar4);


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

                                    radioPermitGroup = (RadioGroup) root.findViewById(R.id.radiopermit);
                                    int selectedId = radioPermitGroup.getCheckedRadioButtonId();
                                    radioPermitButton = (RadioButton) root.findViewById(selectedId);
                                    permit1 = radioPermitButton.getText().toString();

                                    if (permit1.equalsIgnoreCase("Private")) {
                                        rowpermit.setVisibility(View.GONE);
                                        rowfitness.setVisibility(View.GONE);
                                        rowtax.setVisibility(View.GONE);
                                    }
                                    break;
                                case "Construction Equipment":
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
                                    rowbody.setVisibility(View.VISIBLE);
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
                                    air.setVisibility(View.GONE);

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

                    useEdit.setText("");
                    seatingEdit.setText("");
                    transmissionEdit.setText("");
                    impletementEdit.setText("");
                    mBodyTypeSpinner.setSelection(0);
                    boatEdit.setText("");
                    rvEdit.setText("");

                    getfual();
                }
            });
        }
        return root;
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
    }


    public void getfual() {
        spinnerfual.setItems(fuals, "-Select Fuel-", this);
        multiSpinnercolor.setItems(colors, "-Select Color-", this);
    }

    /*
    volley getAllCategory()
     */
    private void getAllCategory() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleList();
    }

    /*
    Get Body Types...
     */
    private void getBodyTypes() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBodyType();
    }

    /*
    getSubCategory()
     */
    private void getSubCategory(int vehicle_id) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleSubtype(vehicle_id);
    }

    /*
    volley getBrand()
     */
    private void getBrand(final int subcatid, final int vehicle_id) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBrand(vehicle_id, subcatid);
    }

    /*
    volley getModel()
     */
    private void getModel(final int brand_idsss, final int vehicle_id, final int subcatid) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getModel(vehicle_id, subcatid, brand_idsss);
    }

    /*
    volley getVersion()
     */
    private void getVersion(final int model_idsss, final int brand_idsss, final int vehicle_id, final int subcatid) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVersion(vehicle_id, subcatid, brand_idsss, model_idsss);
    }

    private void callColorWebservices() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getColor();
    }

    public void applySearch() {
        System.out.println("inside apply search ");
        boolean flag;
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

            int ownerPos = owner.getSelectedItemPosition();
            if (ownerPos != 0)
                owner1 = Integer.parseInt(owner.getSelectedItem().toString());

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

            if (!city1.isEmpty()) {
                List<String> resultList = GooglePlacesAdapter.getResultList();
                for (int i = 0; i < resultList.size(); i++) {

                    if (city1.equalsIgnoreCase(resultList.get(i))) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
            }

            use1 = useEdit.getText().toString();
            rv1 = rvEdit.getText().toString();
            boat1 = boatEdit.getText().toString();
            body1 = mBodyTypeSpinner.getSelectedItem().toString();
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
        RequestAlertBox();

    }

    private void RequestAlertBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());


        alertDialogBuilder
                .setMessage("Want a call back ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        callPermission = "Yes";
                        SaveSearchTask();
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        callPermission = "No";
                        SaveSearchTask();
                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void SaveSearchTask() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.saveMySearch(myContact, Category, subCategory, brand1, model1, version1, color1,
                man_yr1 + "-" + man_yr2, insurance1, kms1 + "-" + kms2,
                hrs1 + "-" + hrs2, hpcap1 + "-" + hpcap2, owner1, price1 + "-" + price2, tyre1 + "-" + tyre2,
                city1, city11, city12, city13, city14, city2, city21, city22, city23, city24, rc1, insurance1,
                tax_validity1, fitness_validity1, permit_validity1, fual1, seating1, permit1, hypo1, drive1, finance1, transmission1,
                body1, boat1, rv1, use1, implement1, bus_type1, air1, invoice1, action, Sid, callPermission);
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

    private void getRTOCity() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleRTOCity();
    }

    @Override
    public void onResume() {
        super.onResume();
        rc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    rc.clearFocus();
                }
                return false;
            }
        });
        insurance.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    insurance.clearFocus();
                }
                return false;
            }
        });

        owner.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    owner.clearFocus();
                }
                return false;
            }
        });
        hypo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    hypo.clearFocus();
                }
                return false;
            }
        });
        brand.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    brand.clearFocus();
                }
                return false;
            }
        });
        model.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    model.clearFocus();
                }
                return false;
            }
        });
        allcategory.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    allcategory.clearFocus();
                }
                return false;
            }
        });
        subcategory.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    subcategory.clearFocus();
                }
                return false;
            }
        });
        version.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    version.clearFocus();
                }
                return false;
            }
        });
        tax_validity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    tax_validity.clearFocus();
                }
                return false;
            }
        });
        fitness_validity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    fitness_validity.clearFocus();
                }
                return false;
            }
        });
        permit_validity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    permit_validity.clearFocus();
                }
                return false;
            }
        });
        drive.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    drive.clearFocus();
                }
                return false;
            }
        });
        bus_type.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    bus_type.clearFocus();
                }
                return false;
            }
        });
        air.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    air.clearFocus();
                }
                return false;
            }
        });
        invoice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    invoice.clearFocus();
                }
                return false;
            }
        });
        finance.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finance.clearFocus();
                }
                return false;
            }
        });
        spinnerfual.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    spinnerfual.clearFocus();
                }
                return false;
            }
        });
        multiSpinnercolor.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    multiSpinnercolor.clearFocus();
                }
                return false;
            }
        });
        useEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    useEdit.clearFocus();
                }
                return false;
            }
        });
        seatingEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    seatingEdit.clearFocus();
                }
                return false;
            }
        });
        transmissionEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    transmissionEdit.clearFocus();
                }
                return false;
            }
        });
        impletementEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    impletementEdit.clearFocus();
                }
                return false;
            }
        });
        mBodyTypeSpinner.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mBodyTypeSpinner.clearFocus();
                }
                return false;
            }
        });
        boatEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    boatEdit.clearFocus();
                }
                return false;
            }
        });
        rvEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    rvEdit.clearFocus();
                }
                return false;
            }
        });
        autoCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoCity.clearFocus();
                }
                return false;
            }
        });
        autoCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoCity.clearFocus();
                }
                return false;
            }
        });

        autoRTO.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoRTO.clearFocus();
                }
                return false;
            }
        });

        autoCity1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoCity1.clearFocus();
                }
                return false;
            }
        });
        autoCity2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoCity2.clearFocus();
                }
                return false;
            }
        });

        autoCity3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoCity3.clearFocus();
                }
                return false;
            }
        });
        autoCity4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoCity4.clearFocus();
                }
                return false;
            }
        });

        autoRTO1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoRTO1.clearFocus();
                }
                return false;
            }
        });
        autoRTO2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoRTO2.clearFocus();
                }
                return false;
            }
        });
        autoRTO3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoRTO3.clearFocus();
                }
                return false;
            }
        });
        autoRTO4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    autoRTO4.clearFocus();
                }
                return false;
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    //onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof GetVehicleListResponse) {
                    final List<String> vehicles = new ArrayList<>();
                    final List<Integer> vehicle_iddd = new ArrayList<>();
                    GetVehicleListResponse listResponse = (GetVehicleListResponse) response.body();
                    if (!listResponse.getSuccess().isEmpty()) {
                        vehicles.add("-Select Category-");
                        for (GetVehicleListResponse.Success success : listResponse.getSuccess()) {
                            vehicle_iddd.add(success.getId());
                            vehicles.add(success.getName());
                        }
                        if (isAdded()) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, vehicles);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            allcategory.setAdapter(dataAdapter);
                        }

                        allcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                vehicle_id = vehicle_iddd.get(position);
                                String Category = vehicles.get(position);

                                moresearchlinear.setVisibility(View.GONE);
                                count = 0;
                                System.out.println("Selected category:" + Category);
                                if (!Category.equalsIgnoreCase("-Select Category-"))
                                    getSubCategory(vehicle_id);

                                if (Category.equals("Car"))
                                    rowPermit.setVisibility(View.VISIBLE);
                                else
                                    rowPermit.setVisibility(View.GONE);

                                if (Category.equals("Tractor") || Category.equals("Cranes") || Category.equalsIgnoreCase("Construction Equipment")) {
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

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                } else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    final List<String> vehicles = new ArrayList<String>();
                    final List<Integer> vehicles_id = new ArrayList<>();
                    GetVehicleSubTypeResponse subTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    if (!subTypeResponse.getSuccess().isEmpty()) {
                        vehicles.add("-Select subcategory-");

                        for (GetVehicleSubTypeResponse.Success success : subTypeResponse.getSuccess()) {
                            vehicles_id.add(success.getId());
                            vehicles.add(success.getName());
                        }
//                        spinnervalues = new String[vehicles_id.size()];
//                        spinnervalues = vehicles_id.toArray(spinnervalues);
                        if (isAdded()) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, vehicles);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subcategory.setAdapter(dataAdapter);
                        }

                        subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    sub_category_id = vehicles_id.get(position);
                                    subCategory = vehicles.get(position);

                                    moresearchlinear.setVisibility(View.GONE);
                                    count = 0;

                                    if (sub_category_id != 0) {
                                        getBrand(sub_category_id, vehicle_id);
                                    }
                                    if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                                            || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                                            || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                                        rowRTO.setVisibility(View.GONE);

                                    } else {
                                        rowRTO.setVisibility(View.VISIBLE);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                } else if (response.body() instanceof GetVehicleBrandResponse) {
                    final List<String> brands = new ArrayList<String>();
                    final List<Integer> brand_id = new ArrayList<>();
                    GetVehicleBrandResponse brandResponse = (GetVehicleBrandResponse) response.body();
                    if (!brandResponse.getSuccess().isEmpty()) {
                        brands.add("-Select Brand-");
                        for (GetVehicleBrandResponse.Success success : brandResponse.getSuccess()) {
                            brand_id.add(success.getBrandId());
                            brands.add(success.getBrandTitle());
                        }
//                        spinnervalues = new String[brand_id.size()];
//                        spinnervalues = brand_id.toArray(spinnervalues);
                        if (isAdded()) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, brands);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            brand.setAdapter(dataAdapter);
                        }

                        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    position_brand_id = brand_id.get(position);
                                    if (position_brand_id != 0)
                                        getModel(position_brand_id, vehicle_id, sub_category_id);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                } else if (response.body() instanceof GetVehicleModelResponse) {
                    final List<String> models = new ArrayList<String>();
                    final List<Integer> model_id = new ArrayList<>();
                    GetVehicleModelResponse modelResponse = (GetVehicleModelResponse) response.body();
                    if (!modelResponse.getSuccess().isEmpty()) {
                        models.add("-Select Models-");
                        for (GetVehicleModelResponse.Success success : modelResponse.getSuccess()) {
                            model_id.add(success.getModelId());
                            models.add(success.getModelTitle());
                        }
//                        spinnervalues = new String[model_id.size()];
//                        spinnervalues = model_id.toArray(spinnervalues);
                        if (isAdded()) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, models);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            model.setAdapter(dataAdapter);
                        }

                        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    position_model_id = model_id.get(position);
                                    if (position_model_id != 0)
                                        getVersion(position_model_id, position_brand_id, vehicle_id, sub_category_id);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });
                    }
                } else if (response.body() instanceof GetVehicleVersionResponse) {
                    final List<String> versions = new ArrayList<String>();
                    final List<Integer> version_id = new ArrayList<Integer>();
                    GetVehicleVersionResponse versionResponse = (GetVehicleVersionResponse) response.body();
                    if (!versionResponse.getSuccess().isEmpty()) {
                        versions.add("-Select Version-");
                        for (GetVehicleVersionResponse.Success success : versionResponse.getSuccess()) {
                            version_id.add(success.getVersionId());
                            versions.add(success.getVersion());
                        }
//
//                        spinnervalues = new String[version_id.size()];
//                        spinnervalues = version_id.toArray(spinnervalues);
                        if (isAdded()) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, versions);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            version.setAdapter(dataAdapter);
                        }

                        version.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                } else if (response.body() instanceof ColorResponse) {
                    ColorResponse colorResponse = (ColorResponse) response.body();
                    if (!colorResponse.getSuccess().isEmpty()) {
                        for (ColorResponse.Success success : colorResponse.getSuccess()) {
                            colors.add(success.getTitle());
                        }
                        multiSpinnercolor.setItems(colors, "-Select Color-", FilterFragment.this);
                    }
                } else if (response.body() instanceof GetRTOCityResponse) {
                    final List<String> RtoCity = new ArrayList<String>();
                    GetRTOCityResponse cityResponse = (GetRTOCityResponse) response.body();
                    if (!cityResponse.getSuccess().isEmpty()) {
                        for (GetRTOCityResponse.Success success : cityResponse.getSuccess()) {
                            /*RtoCity.add(success.getRtoCode());*/
                            RtoCity.add(success.getRtoCode() + " " +
                                    success.getRtoCityName());
                        }
                        if (isAdded()) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, RtoCity);
                            autoRTO.setAdapter(dataAdapter);
                            autoRTO1.setAdapter(dataAdapter);
                            autoRTO2.setAdapter(dataAdapter);
                            autoRTO3.setAdapter(dataAdapter);
                            autoRTO4.setAdapter(dataAdapter);
                        }
                    }
                }
                //Body Type
                else if (response.body() instanceof GetBodyTypeResponse) {
                    mBodyType.clear();
                    GetBodyTypeResponse mGetBodyTypeResponse = (GetBodyTypeResponse) response.body();
                    mBodyType.add("--Select Body Type--");
                    for (GetBodyTypeResponse.Success success : mGetBodyTypeResponse.getSuccess()) {
                        success.setTitle(success.getTitle());
                        mBodyType.add(success.getTitle());
                    }
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, mBodyType);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mBodyTypeSpinner.setAdapter(adapter);
                    }
                    mBodyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String bodyType = mBodyTypeSpinner.getSelectedItem().toString();
                                int bodyTypeid = mBodyTypeSpinner.getSelectedItemPosition();

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_bodyType", bodyType).apply();

                                System.out.println("Body Type is::" + bodyType);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            } else {
                //CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
//            if (isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", " Filter Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
