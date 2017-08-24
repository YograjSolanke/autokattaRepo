package autokatta.com.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.MultiSelectionSpinner;
import autokatta.com.Registration.Multispinner;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.RangeSeekBar;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ColorResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 19/4/17.
 */

public class FilterFragment extends Fragment implements Multispinner.MultiSpinnerListener, MultiSelectionSpinner.MultiSpinnerListener, RequestNotifier {

    //fields for google API
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyDQy-sYUScw5BJkClbJH6HC93gpk4B2Am4";
    private GoogleApiClient client;


    SharedPreferences prefs;
    public static final String MyContactPREFERENCES = "contact No";
    String contact;

    FloatingActionButton getHelp;

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

    TableRow rowFinacial, rowCategory, rowSubcat, rowPermit, rowBrand, rowModel, rowVersion, rowCity, rowRTO, rowManyr, rowmanyr1, rowREGyr, rowREGyr1, rowPrice, rowprice1, rowKms, rowkms1, rowhrs, rowhrstb, rowhpcapacity, rowhpcap, rowowners;

    TableRow rowInvoice, rowbustype, rowaircondition, rowbody, rowboattype, rowrvtype, rowcolor, rowrc, rowinsurance1, rowhypo, rowtax, rowfitness, rowpermit, rowfual, rowseat, rowdrive, rowtransmission, rowuse, rowimpl, rowtyre, rowtyrerange;

    String action, Scategory, Sbrand, Smodel, Sprice, Syear, Sid = "", Category, subCategory, hrs1, hrs2, hpcap1, hpcap2;
    int position_brand_id, position_model_id;
    String spinnervalues[];

    String city1, city2, city11, city12, city13, city14, city21, city22, city23, city24, brand1, model1, color1, version1, man_yr1, man_yr2, reg_yr1, reg1, reg_yr2, rc1, insurance1, kms1, kms2, hypo1, owner1, price1, price2;
    String permit1, tax_validity1, fitness_validity1, permit_validity1, drive1, fual1, bus_type1, air1, invoice1;

    String use1, seating1, transmission1, implement1, body1, boat1, rv1, finance1, tyre1, tyre2;
    int count = 0, vehicle_id, sub_category_id;

    final ArrayList fuals = new ArrayList();
    List<String> colors = new ArrayList<String>();

    int counter = 0;
    int counter1 = 0;
    ConnectionDetector mConnectionDetector = new ConnectionDetector(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View root = inflater.inflate(R.layout.filter_all_activity, container, false);
        client = new GoogleApiClient.Builder(getActivity()).addApi(AppIndex.API).build();
        prefs = getActivity().getSharedPreferences(MyContactPREFERENCES, Context.MODE_PRIVATE);
        contact = prefs.getString("contact", "");

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

        bodyEdit = (EditText) root.findViewById(R.id.bodyEdit1);
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
            Sid = b.getString("search_id");
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            autoCity.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.simple));
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
            autoCity1.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.simple));
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
            autoCity2.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.simple));
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
            autoCity3.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.simple));
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
            autoCity4.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.simple));
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
                        Toast.makeText(getActivity(), "Please provide fianance required or not", Toast.LENGTH_LONG).show();
                    } else if (Category.equals("-Select Category-")) {
                        Toast.makeText(getActivity(), "select catagory", Toast.LENGTH_LONG).show();
                    } else if (subcategory.getSelectedItem().toString().equals("-Select subcategory-")) {
                        Toast.makeText(getActivity(), "select subcatagory", Toast.LENGTH_LONG).show();
                    } else if (brand.getSelectedItem().toString().equals("-Select Brand-")) {
                        Toast.makeText(getActivity(), "select brand", Toast.LENGTH_LONG).show();
                    } else if (model.getSelectedItem().toString().equals("-Select Models-")) {
                        Toast.makeText(getActivity(), "select model", Toast.LENGTH_LONG).show();
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
                    reg_yr_from.setText("" + minValue);
                    reg_yr_to.setText("" + maxValue);
                    Log.i("RAnge is :", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                }
            });
            // Add to layout
            LinearLayout layout = (LinearLayout) root.findViewById(R.id.REGYRLinear);
            layout.addView(rangeSeekBar);

            /**********************Range bar for price*****************************/
            RangeSeekBar<Integer> rangeSeekBar2 = new RangeSeekBar<Integer>(getActivity());
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
            LinearLayout layout2 = (LinearLayout) root.findViewById(R.id.PriceLinear);
            layout2.addView(rangeSeekBar2);


            /**********************KMS Running*****************************/
            RangeSeekBar<Integer> rangeSeekBar3 = new RangeSeekBar<Integer>(getActivity());
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
            LinearLayout layouthpcap = (LinearLayout) root.findViewById(R.id.hpcaplinear);
            layouthpcap.addView(rangeSeekBarhpcap);

            /**********************tyre condition*****************************/
            RangeSeekBar<Integer> rangeSeekBar4 = new RangeSeekBar<Integer>(getActivity());
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

                            if (Category.equals("Bus")) {
                                rowInvoice.setVisibility(View.GONE);
                                rowbody.setVisibility(View.GONE);
                                rowboattype.setVisibility(View.GONE);
                                rowrvtype.setVisibility(View.GONE);
                                rowdrive.setVisibility(View.GONE);
                                rowtransmission.setVisibility(View.GONE);
                                rowuse.setVisibility(View.GONE);
                                rowimpl.setVisibility(View.GONE);
                            } else if (Category.equals("Car")) {
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
                            } else if (Category.equals(" Construction Equipment ")) {
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

                            } else if (Category.equals("Commercial Vehicle")) {
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

                            } else if (Category.equals("Tractor")) {
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

                            } else if (Category.equals("2 Wheeler")) {
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

                            } else if (Category.equals("3 Wheeler")) {
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

                    getfual();
                }
            });

            /*getHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog openDialog = new Dialog(getActivity());
                    openDialog.setContentView(R.layout.customdialog_help);

                    TextView dialogText = (TextView) openDialog.findViewById(R.id.dialog_text);
                    ImageView dialogImage = (ImageView) openDialog.findViewById(R.id.dialog_image);
                    Button dialogClose = (Button) openDialog.findViewById(R.id.dialog_button);

                    openDialog.setTitle("Help");
                    dialogText.setText(Html.fromHtml("<HTML><HEAD></HEAD><BODY>" + "Filter Activity " + "</BODY></HTML>"));

                    dialogClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openDialog.dismiss();
                        }
                    });
                    openDialog.show();
                }
            });*/
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

    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: " + url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            // Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {

            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            //Log.e(LOG_TAG, "Cannot process JSON results", e);
            e.printStackTrace();
        }
        return resultList;
    }


    /*
    volley getAllCategory()
     */
    private void getAllCategory() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleList();
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


        SaveSearchTask();

    }

    private void SaveSearchTask() {
       /* StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://autokatta.com/mobile/saveSearch.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("onResponse:SaveSearchTask:" + response);

                        try {
                            if (response.startsWith("New record created successfully")) {

                                FragmentManager mFragmentManager;
                                FragmentTransaction mFragmentTransaction;
                                Toast.makeText(getActivity(), "Your search saved successfully! you will get notification soon..!", Toast.LENGTH_SHORT).show();

                                Bundle b = new Bundle();
                                b.putInt("call", 0);

                                TabFragment fr = new TabFragment();
                                fr.setArguments(b);
                                mFragmentManager = getActivity().getSupportFragmentManager();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView, fr).commit();

                            } else if (response.startsWith("success in update")) {
                                FragmentManager mFragmentManager;
                                FragmentTransaction mFragmentTransaction;
                                Toast.makeText(getActivity(), "Your search updated successfully! you will get notification soon..!", Toast.LENGTH_SHORT).show();

                                sqlite_obj.open();
                                SQlitewallDB.DatabaseHelper obg = new SQlitewallDB.DatabaseHelper(getActivity());
                                obg.updateMySearch(Sid, Category, brand1, model1, price1, man_yr1 + "-" + man_yr2);

                                sqlite_obj.close();

                                Bundle b = new Bundle();
                                b.putInt("call", 0);

                                TabFragment fr = new TabFragment();
                                fr.setArguments(b);
                                mFragmentManager = getActivity().getSupportFragmentManager();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView, fr).commit();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("onError:SaveSearchTask:" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("contact", contact);
                params.put("category", Category);
                params.put("subcategory", subCategory);
                params.put("brand", brand1);
                params.put("model", model1);
                params.put("version", version1);

                params.put("color", color1);
                params.put("man_year", man_yr1 + "-" + man_yr2);
                params.put("reg_year", reg_yr1 + "-" + reg_yr2);
                params.put("insurance", insurance1);
                params.put("kms_running", kms1 + "-" + kms2);
                params.put("hrs_running", hrs1 + "-" + hrs2);
                params.put("hpcapacity", hpcap1 + "-" + hpcap2);
                params.put("owners", owner1);
                params.put("price", price1 + "-" + price2);
                params.put("tyre_condition", tyre1 + "-" + tyre2);

                params.put("city", city1);
                params.put("city1", city11);
                params.put("city2", city12);
                params.put("city3", city13);
                params.put("city4", city14);
                params.put("RTO_city", city2);
                params.put("RTO_city1", city21);
                params.put("RTO_city2", city22);
                params.put("RTO_city3", city23);
                params.put("RTO_city4", city24);

                params.put("rc_available", rc1);
                params.put("ins_valid", insurance1);
                params.put("tax_validity", tax_validity1);
                params.put("fitness_validity", fitness_validity1);
                params.put("permit_validity", permit_validity1);
                params.put("fual", fual1);
                params.put("seat_cap", seating1);
                params.put("permit", permit1);
                params.put("hypothetication", hypo1);
                params.put("drive", drive1);
                params.put("finance", finance1);
                params.put("transmission", transmission1);
                params.put("body_type", body1);
                params.put("boat_type", boat1);
                params.put("rv_type", rv1);
                params.put("application", use1);

                params.put("implements", implement1);
                params.put("bus_type", bus_type1);
                params.put("air_condition", air1);
                params.put("invoice", invoice1);
                params.put("keyword", action);
                params.put("search_id", Sid);

                return params;
            }
        };
        requestQueue.add(stringRequest);*/

    }

    //class for google API
    private class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
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
        bodyEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    bodyEdit.clearFocus();
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
                        for (GetVehicleListResponse.Success success : listResponse.getSuccess()) {
                            vehicle_iddd.add(success.getId());
                            vehicles.add(success.getName());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, vehicles);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        allcategory.setAdapter(dataAdapter);

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

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                } else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    final ArrayList<String> vehicles = new ArrayList<String>();
                    final ArrayList<Integer> vehicles_id = new ArrayList<>();
                    GetVehicleSubTypeResponse subTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    if (!subTypeResponse.getSuccess().isEmpty()) {
                        for (GetVehicleSubTypeResponse.Success success : subTypeResponse.getSuccess()) {
                            vehicles_id.add(success.getId());
                            vehicles.add(success.getName());
                        }
                        spinnervalues = new String[vehicles_id.size()];
                        spinnervalues = vehicles_id.toArray(spinnervalues);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, vehicles);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subcategory.setAdapter(dataAdapter);

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
                    final ArrayList<String> brands = new ArrayList<String>();
                    final ArrayList<Integer> brand_id = new ArrayList<>();
                    GetVehicleBrandResponse brandResponse = (GetVehicleBrandResponse) response.body();
                    if (!brandResponse.getSuccess().isEmpty()) {
                        for (GetVehicleBrandResponse.Success success : brandResponse.getSuccess()) {
                            brand_id.add(success.getBrandId());
                            brands.add(success.getBrandTitle());
                        }
                        spinnervalues = new String[brand_id.size()];
                        spinnervalues = brand_id.toArray(spinnervalues);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, brands);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        brand.setAdapter(dataAdapter);

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
                    final ArrayList<String> models = new ArrayList<String>();
                    final ArrayList<Integer> model_id = new ArrayList<>();
                    GetVehicleModelResponse modelResponse = (GetVehicleModelResponse) response.body();
                    if (!modelResponse.getSuccess().isEmpty()) {
                        for (GetVehicleModelResponse.Success success : modelResponse.getSuccess()) {
                            model_id.add(success.getModelId());
                            models.add(success.getModelTitle());
                        }
                        spinnervalues = new String[model_id.size()];
                        spinnervalues = model_id.toArray(spinnervalues);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, models);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        model.setAdapter(dataAdapter);

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
                    final ArrayList<String> versions = new ArrayList<String>();
                    final ArrayList<Integer> version_id = new ArrayList<Integer>();
                    GetVehicleVersionResponse versionResponse = (GetVehicleVersionResponse) response.body();
                    if (!versionResponse.getSuccess().isEmpty()) {
                        for (GetVehicleVersionResponse.Success success : versionResponse.getSuccess()) {
                            version_id.add(success.getVersionId());
                            versions.add(success.getVersion());
                        }

                        spinnervalues = new String[version_id.size()];
                        spinnervalues = version_id.toArray(spinnervalues);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, versions);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        version.setAdapter(dataAdapter);

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
                    final ArrayList<String> RtoCity = new ArrayList<String>();
                    GetRTOCityResponse cityResponse = (GetRTOCityResponse) response.body();
                    if (!cityResponse.getSuccess().isEmpty()) {
                        for (GetRTOCityResponse.Success success : cityResponse.getSuccess()) {
                            RtoCity.add(success.getRtoCityName());
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, RtoCity);
                        autoRTO.setAdapter(dataAdapter);
                        autoRTO1.setAdapter(dataAdapter);
                        autoRTO2.setAdapter(dataAdapter);
                        autoRTO3.setAdapter(dataAdapter);
                        autoRTO4.setAdapter(dataAdapter);
                    }
                }

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
