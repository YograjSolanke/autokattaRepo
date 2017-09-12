package autokatta.com.upload_vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BodyAndSeatResponse;
import autokatta.com.response.GetBodyTypeResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetVehicleColor;
import autokatta.com.response.GetVehicleImplementsResponse;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 21/3/17.
 */

public class SubTypeFragment extends Fragment implements View.OnClickListener, RequestNotifier, View.OnTouchListener {

    View mSubtype;
    Button mUploadVehicle;
    String allimgpath = "";
    ArrayList<Image> mImages = new ArrayList<>();
    int REQUEST_CODE_PICKER = 2000;
    List<String> mRtoCity = new ArrayList<>();
    List<String> mVehicleColor = new ArrayList<>();
    List<String> mBodyType = new ArrayList<>();
    List<String> mVehicleImplements = new ArrayList<>();
    List<String> bodyManufacturerlist = new ArrayList<>();
    List<String> seatManufacturerlist = new ArrayList<>();
    List<String> mFuel = new ArrayList<>();

    Spinner mBodyTypeSpinner, mImplementSpinner, mSetColorSpinner, mSetFuel, mHypoSpinner, mRcSpinner, mInsuranceSpinner,
            mTaxValidSpinner, mPermitSpinner, mFitnessSpinner, mOwnerSpinner, mEmissionSpinner, mDriveSpinner, mTransmissionSpinner;
    /*
    Location...
     */
    AutoCompleteTextView mLocation, autoBodymanufacturer, autoSeatmanufacturer;
    AutoCompleteTextView mRTOcity;
    String subCategory, Category, permit, uploadauctioncat;
    CheckBox checkBox1;
    EditText registernumber, edtInsuranceIdv, edtTaxPaidDate, edtFitnessDate, edtChasis, edtEngine, edtInsuranceDate, edtPermitDate;
    AlertDialog alertDialog;
    RelativeLayout relInsurance, relTaxdate, relPermit, relFitness;

    EditText edtBody, edtApptext, edtSeatCap, edtTyreContext, edtSetHpcapa, edtSetJib, edtSetBoon, edtImplementStr;
    Button btnBody, btnRefreshBody;
    LinearLayout linearBody;
    Spinner mBustypeSpinner, mAircondSpinner, mInvoiceSpinner;
    TextView mCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSubtype = inflater.inflate(R.layout.fragment_subtype_fragment, container, false);
        mUploadVehicle = (Button) mSubtype.findViewById(R.id.upload_vehicle);
        checkBox1 = (CheckBox) mSubtype.findViewById(R.id.checkBox1);
        mCategory = (TextView) mSubtype.findViewById(R.id.categorytext1);
        mRTOcity = (AutoCompleteTextView) mSubtype.findViewById(R.id.rtoautocompletetext);
        mLocation = (AutoCompleteTextView) mSubtype.findViewById(R.id.autolocation);
        registernumber = (EditText) mSubtype.findViewById(R.id.registernumber);
        mBodyTypeSpinner = (Spinner) mSubtype.findViewById(R.id.bodytypespinner);


        mHypoSpinner = (Spinner) mSubtype.findViewById(R.id.hypospinner1);
        mRcSpinner = (Spinner) mSubtype.findViewById(R.id.rcspinner1);
        mInsuranceSpinner = (Spinner) mSubtype.findViewById(R.id.insurancespinner1);
        mTaxValidSpinner = (Spinner) mSubtype.findViewById(R.id.taxvalidspinner1);
        mPermitSpinner = (Spinner) mSubtype.findViewById(R.id.permitspinner);
        mFitnessSpinner = (Spinner) mSubtype.findViewById(R.id.fitnessspinner);
        mOwnerSpinner = (Spinner) mSubtype.findViewById(R.id.ownerstext);

        autoBodymanufacturer = (AutoCompleteTextView) mSubtype.findViewById(R.id.bodymanufacturer);
        autoSeatmanufacturer = (AutoCompleteTextView) mSubtype.findViewById(R.id.seatmanufacturer);

        relInsurance = (RelativeLayout) mSubtype.findViewById(R.id.relInsurance);
        relTaxdate = (RelativeLayout) mSubtype.findViewById(R.id.relTaxdate);
        relPermit = (RelativeLayout) mSubtype.findViewById(R.id.relPermit);
        relFitness = (RelativeLayout) mSubtype.findViewById(R.id.relFitness);

        mEmissionSpinner = (Spinner) mSubtype.findViewById(R.id.emVersion);
        mDriveSpinner = (Spinner) mSubtype.findViewById(R.id.driveSpinner);
        mTransmissionSpinner = (Spinner) mSubtype.findViewById(R.id.transmissionspinner);

        edtInsuranceIdv = (EditText) mSubtype.findViewById(R.id.insuranceidv1);
        edtInsuranceDate = (EditText) mSubtype.findViewById(R.id.editinsurancedate);
        edtTaxPaidDate = (EditText) mSubtype.findViewById(R.id.taxpaid1);
        edtFitnessDate = (EditText) mSubtype.findViewById(R.id.fitnesstext);
        edtPermitDate = (EditText) mSubtype.findViewById(R.id.permittext);

        edtChasis = (EditText) mSubtype.findViewById(R.id.chassi_no);
        edtEngine = (EditText) mSubtype.findViewById(R.id.engine_no);

        edtBody = (EditText) mSubtype.findViewById(R.id.editbody);
        btnBody = (Button) mSubtype.findViewById(R.id.btnbody);
        btnRefreshBody = (Button) mSubtype.findViewById(R.id.refreshbody);
        linearBody = (LinearLayout) mSubtype.findViewById(R.id.linearbody);
        mBustypeSpinner = (Spinner) mSubtype.findViewById(R.id.bustypeSpinner);
        mAircondSpinner = (Spinner) mSubtype.findViewById(R.id.aircondspinner);
        mInvoiceSpinner = (Spinner) mSubtype.findViewById(R.id.invoicespinner);
        edtApptext = (EditText) mSubtype.findViewById(R.id.apptext);
        mImplementSpinner = (Spinner) mSubtype.findViewById(R.id.implementSpinner);
        edtImplementStr = (EditText) mSubtype.findViewById(R.id.editimplement);
        edtSeatCap = (EditText) mSubtype.findViewById(R.id.seatcaptext);
        edtTyreContext = (EditText) mSubtype.findViewById(R.id.tyrecontext);
        mSetColorSpinner = (Spinner) mSubtype.findViewById(R.id.setcolor);
        mSetFuel = (Spinner) mSubtype.findViewById(R.id.setfuel);
        edtSetHpcapa = (EditText) mSubtype.findViewById(R.id.sethpcapacity);
        edtSetJib = (EditText) mSubtype.findViewById(R.id.setjib);
        edtSetBoon = (EditText) mSubtype.findViewById(R.id.setBoon);


        mUploadVehicle.setOnClickListener(this);
        btnBody.setOnClickListener(this);
        btnRefreshBody.setOnClickListener(this);

        edtInsuranceDate.setOnTouchListener(this);
        edtTaxPaidDate.setOnTouchListener(this);
        edtFitnessDate.setOnTouchListener(this);
        edtPermitDate.setOnTouchListener(this);

        Category = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_categoryName", null);
        subCategory = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_subCatName", null);
        permit = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_permit", null);
        uploadauctioncat = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_auction_categoryName", null);

        mCategory.setText(Category + "->" + uploadauctioncat);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getRTOcity();
                    getVehicleColor();
                    getImplements();
                    getBodyTypes();

                    if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                            || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                            || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                        mRTOcity.setVisibility(View.GONE);
                        registernumber.setVisibility(View.GONE);
                        checkBox1.setVisibility(View.GONE);


                    }

                    mLocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.addproductspinner_color));

                    checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (checkBox1.isChecked()) {
                                mRTOcity.setText("Unregistered");
                                mRTOcity.setEnabled(false);
                                registernumber.setText("");
                                registernumber.setEnabled(false);
                            } else {
                                mRTOcity.setText("");
                                mRTOcity.setEnabled(true);
                                registernumber.setEnabled(true);
                            }
                        }
                    });

          /*          mFuel.clear();
                    mFuel.add("-Select Fuel Type-");
                    mFuel.add("Petrol");
                    mFuel.add("Diesel");
                    mFuel.add("CNG");
                    mFuel.add("LPG");
                    mFuel.add("Electric");
                    mFuel.add("Hybrid");
                    if (getActivity() != null) {
                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, mFuel);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSetFuel.setAdapter(dataAdapter);
                    }
*/
                     /*else if (Category.equalsIgnoreCase("Tractor") || Category.equalsIgnoreCase("Construction Equipment") ||
                            Category.equalsIgnoreCase("2 Wheeler")) {

                        mEmissionSpinner.setVisibility(View.GONE);
                    } else if (Category.equalsIgnoreCase("2 Wheeler") || Category.equalsIgnoreCase("3 Wheeler")) {

                        mTaxValidSpinner.setVisibility(View.GONE);
                        mPermitSpinner.setVisibility(View.GONE);
                        mFitnessSpinner.setVisibility(View.GONE);
                    } else if (Category.equalsIgnoreCase("Car")) {

                        if (permit.equalsIgnoreCase("Private")) {
                            mTaxValidSpinner.setVisibility(View.GONE);
                            mPermitSpinner.setVisibility(View.GONE);
                            mFitnessSpinner.setVisibility(View.GONE);
                        }
                    } else if (Category.equalsIgnoreCase("Tractor")) {

                        mPermitSpinner.setVisibility(View.GONE);
                        mFitnessSpinner.setVisibility(View.GONE);
                        relInsurance.setVisibility(View.GONE);


                    }else if (Category.equalsIgnoreCase("Construction Equipment")) {

                        String RCInvoiceText = "";
                        if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                                || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                                || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                            // tax.setVisibility(View.GONE);
                            mHypoSpinner.setVisibility(View.GONE);
                            mPermitSpinner.setVisibility(View.GONE);
                            mFitnessSpinner.setVisibility(View.GONE);
                            RCInvoiceText = "-Select Invoice Available-";
                        } else
                            RCInvoiceText = "-Select RC Available-";

                        List<String> RcInvoice = new ArrayList<>();
                        RcInvoice.add(RCInvoiceText);
                        RcInvoice.add("Yes");
                        RcInvoice.add("No");

                        if (getActivity() != null) {
                            final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item, RcInvoice);
                            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mRcSpinner.setAdapter(dataAdapter1);
                        }

                    }*/

                    if (Category.equalsIgnoreCase("Bus")) {

                        autoBodymanufacturer.setVisibility(View.VISIBLE);
                        autoSeatmanufacturer.setVisibility(View.VISIBLE);
                        getBodyAndSeatManufacturer();
                        fuelType();
                        mBustypeSpinner.setVisibility(View.VISIBLE);
                        mAircondSpinner.setVisibility(View.VISIBLE);
                        edtSeatCap.setVisibility(View.VISIBLE);
                        edtTyreContext.setVisibility(View.VISIBLE);
                        mDriveSpinner.setVisibility(View.VISIBLE);
                        mTransmissionSpinner.setVisibility(View.VISIBLE);

                    /*23-8-17*/
                        mImplementSpinner.setVisibility(View.GONE);
                        edtSetHpcapa.setVisibility(View.GONE);
                        edtSetJib.setVisibility(View.GONE);
                        edtSetBoon.setVisibility(View.GONE);
                        mEmissionSpinner.setVisibility(View.VISIBLE);
                        mBodyTypeSpinner.setVisibility(View.GONE);

                    } else if (Category.equalsIgnoreCase("Car")) {
                        //edtTyreContext.setVisibility(View.VISIBLE);
                        fuelType();
                        mTransmissionSpinner.setVisibility(View.VISIBLE);
                        edtSeatCap.setVisibility(View.VISIBLE);
                        mDriveSpinner.setVisibility(View.VISIBLE);
                        mTransmissionSpinner.setVisibility(View.VISIBLE);
                        mAircondSpinner.setVisibility(View.VISIBLE);

                    /*23-8-17*/
                        mBustypeSpinner.setVisibility(View.GONE);
                        edtSetBoon.setVisibility(View.GONE);
                        edtSetJib.setVisibility(View.GONE);
                        edtSetHpcapa.setVisibility(View.GONE);
                        edtApptext.setVisibility(View.GONE);
                        mImplementSpinner.setVisibility(View.GONE);
                        mInvoiceSpinner.setVisibility(View.GONE);
                        mEmissionSpinner.setVisibility(View.VISIBLE);
                        edtTyreContext.setVisibility(View.VISIBLE);
                        mBodyTypeSpinner.setVisibility(View.GONE);

                        if (permit.equalsIgnoreCase("Private")) {
                            mTaxValidSpinner.setVisibility(View.GONE);
                            mPermitSpinner.setVisibility(View.GONE);
                            mFitnessSpinner.setVisibility(View.GONE);
                        }
                    } else if (Category.equalsIgnoreCase("Construction Equipment")) {

                        String RCInvoiceText;
                        edtTyreContext.setVisibility(View.GONE);
                        mInvoiceSpinner.setVisibility(View.VISIBLE);
                        edtSetHpcapa.setVisibility(View.GONE);
                        fuelType();
                    /*23-8-17*/
                        mBustypeSpinner.setVisibility(View.GONE);
                        edtSetBoon.setVisibility(View.GONE);
                        edtSetJib.setVisibility(View.GONE);
                        edtSetHpcapa.setVisibility(View.GONE);
                        mImplementSpinner.setVisibility(View.GONE);
                        edtTyreContext.setVisibility(View.GONE);
                        mSetFuel.setVisibility(View.GONE);
                        mEmissionSpinner.setVisibility(View.GONE);
                        mBodyTypeSpinner.setVisibility(View.GONE);
                        mAircondSpinner.setVisibility(View.GONE);
                        mDriveSpinner.setVisibility(View.GONE);
                        mTransmissionSpinner.setVisibility(View.GONE);
                /* RC Text */
                        String RCText = "";
                        if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                                || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                                || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                            // tax.setVisibility(View.GONE);
                            mHypoSpinner.setVisibility(View.GONE);
                            mPermitSpinner.setVisibility(View.GONE);
                            mFitnessSpinner.setVisibility(View.GONE);
                            mSetFuel.setVisibility(View.GONE);
                            RCText = "-Select Invoice Available-";
                        } else
                            RCText = "-Select RC Available-";

                        List<String> RcList = new ArrayList<>();
                        RcList.add(RCText);
                        RcList.add("Yes");
                        RcList.add("No");

                        if (getActivity() != null) {
                            final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item, RcList);
                            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mRcSpinner.setAdapter(dataAdapter1);
                        }

                /* RC Invoice */
                        if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                                || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                                || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                            RCInvoiceText = "Select Invoice Available";
                        } else
                            RCInvoiceText = "Select RC Available";


                        List<String> RcInvoice = new ArrayList<>();
                        RcInvoice.add(RCInvoiceText);
                        RcInvoice.add("Yes");
                        RcInvoice.add("No");
                        if (getActivity() != null) {
                            final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, RcInvoice);
                            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mInvoiceSpinner.setAdapter(dataAdapter1);
                        }
                        mInvoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    String invoice = mInvoiceSpinner.getSelectedItem().toString();
                                    int invoiceid = mInvoiceSpinner.getSelectedItemPosition();

                                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_invoice", invoice).apply();

                                    System.out.println("Invoice  is::" + invoice);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
//
//                        if (!invoice.equalsIgnoreCase("")) {
//                            if (invoice.equalsIgnoreCase(RCInvoiceText))
//                                invoicespinner.setSelection(0);
//                            else if (invoice.equalsIgnoreCase("Yes"))
//                                invoicespinner.setSelection(1);
//                            else
//                                invoicespinner.setSelection(2);
//                        }

                    } else if (Category.equalsIgnoreCase("Commercial Vehicle")) {
                        fuelType();
                        edtTyreContext.setVisibility(View.GONE);
                        edtBody.setVisibility(View.VISIBLE);
                        mAircondSpinner.setVisibility(View.VISIBLE);
                        mDriveSpinner.setVisibility(View.VISIBLE);
                        mTransmissionSpinner.setVisibility(View.VISIBLE);

                    /*23-8-17*/
                        mBustypeSpinner.setVisibility(View.GONE);
                        mInvoiceSpinner.setVisibility(View.GONE);
                        mImplementSpinner.setVisibility(View.GONE);
                        edtSeatCap.setVisibility(View.GONE);
                        edtSetHpcapa.setVisibility(View.GONE);
                        edtSetJib.setVisibility(View.GONE);
                        edtSetBoon.setVisibility(View.GONE);
                        edtTyreContext.setVisibility(View.VISIBLE);
                        mBodyTypeSpinner.setVisibility(View.VISIBLE);
                    } else if (Category.equalsIgnoreCase("Tractor")) {

                        fuelType();
                        mSetFuel.setVisibility(View.GONE);
                        edtTyreContext.setVisibility(View.VISIBLE);
                        mImplementSpinner.setVisibility(View.GONE);
                        edtImplementStr.setVisibility(View.VISIBLE);
                        edtApptext.setVisibility(View.VISIBLE);
                        edtSetHpcapa.setVisibility(View.VISIBLE);
                        mDriveSpinner.setVisibility(View.VISIBLE);
                        mTransmissionSpinner.setVisibility(View.GONE);
                        mAircondSpinner.setVisibility(View.GONE);
                        mDriveSpinner.setVisibility(View.GONE);
                        /*23-8-17*/
                        mBustypeSpinner.setVisibility(View.GONE);
                        edtSetJib.setVisibility(View.GONE);
                        edtSetBoon.setVisibility(View.GONE);
                        edtSeatCap.setVisibility(View.GONE);
                        mSetFuel.setVisibility(View.GONE);
                        mPermitSpinner.setVisibility(View.GONE);
                        mFitnessSpinner.setVisibility(View.GONE);
                        relInsurance.setVisibility(View.GONE);
                        mEmissionSpinner.setVisibility(View.GONE);
                        mBodyTypeSpinner.setVisibility(View.GONE);

                    } else if (Category.equalsIgnoreCase("2 Wheeler")) {
                        //edtTyreContext.setVisibility(View.GONE);
                        mFuel.clear();
                        mFuel.add("-Select Fuel Type-");
                        mFuel.add("Petrol");
                        mFuel.add("Electric");
                        mFuel.add("Gas");

                        if (getActivity() != null) {
                            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item, mFuel);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSetFuel.setAdapter(dataAdapter);
                        }

                    /*23-8-17*/
                        mBustypeSpinner.setVisibility(View.GONE);
                        edtSetBoon.setVisibility(View.GONE);
                        edtSetJib.setVisibility(View.GONE);
                        edtSetHpcapa.setVisibility(View.GONE);
                        edtApptext.setVisibility(View.GONE);
                        mImplementSpinner.setVisibility(View.GONE);
                        mInvoiceSpinner.setVisibility(View.GONE);
                        edtTyreContext.setVisibility(View.VISIBLE);

                        mEmissionSpinner.setVisibility(View.GONE);
                        mTaxValidSpinner.setVisibility(View.GONE);
                        mPermitSpinner.setVisibility(View.GONE);
                        mFitnessSpinner.setVisibility(View.GONE);
                        mBodyTypeSpinner.setVisibility(View.GONE);
                        mDriveSpinner.setVisibility(View.GONE);
                        mAircondSpinner.setVisibility(View.GONE);
                        edtSeatCap.setVisibility(View.GONE);

                    } else if (Category.equalsIgnoreCase("3 Wheeler")) {
                        /*edtTyreContext.setVisibility(View.VISIBLE);
                        edtApptext.setVisibility(View.VISIBLE);*/

                    /*23-8-17*/
                        mBustypeSpinner.setVisibility(View.GONE);
                        edtSetBoon.setVisibility(View.GONE);
                        edtSetJib.setVisibility(View.GONE);
                        edtSetHpcapa.setVisibility(View.VISIBLE);
                        mImplementSpinner.setVisibility(View.GONE);
                        mInvoiceSpinner.setVisibility(View.GONE);
                        edtTyreContext.setVisibility(View.VISIBLE);
                        mEmissionSpinner.setVisibility(View.VISIBLE);
                        edtApptext.setVisibility(View.VISIBLE);
                        mAircondSpinner.setVisibility(View.GONE);

                        // seatrow.setVisibility(View.VISIBLE);
                        fuelType();
                        mTaxValidSpinner.setVisibility(View.GONE);
                        mPermitSpinner.setVisibility(View.GONE);
                        mFitnessSpinner.setVisibility(View.GONE);
                        mBodyTypeSpinner.setVisibility(View.GONE);

                    } else if (Category.equalsIgnoreCase("Cranes")) {
                        fuelType();
                        edtTyreContext.setVisibility(View.VISIBLE);
                        mInvoiceSpinner.setVisibility(View.VISIBLE);
                        edtSetHpcapa.setVisibility(View.VISIBLE);
                        edtSetJib.setVisibility(View.VISIBLE);
                        edtSetBoon.setVisibility(View.VISIBLE);
                        mBodyTypeSpinner.setVisibility(View.GONE);
                        mDriveSpinner.setVisibility(View.GONE);
                        mAircondSpinner.setVisibility(View.GONE);
                        edtSeatCap.setVisibility(View.GONE);
                        mSetFuel.setVisibility(View.GONE);
                    }

                    mTaxValidSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (mTaxValidSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes"))
                                relTaxdate.setVisibility(View.VISIBLE);
                            else
                                relTaxdate.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    mPermitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (mPermitSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes"))
                                relPermit.setVisibility(View.VISIBLE);
                            else
                                relPermit.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    mFitnessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (mFitnessSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes"))
                                relFitness.setVisibility(View.VISIBLE);
                            else
                                relFitness.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    mInsuranceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (mInsuranceSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
                                relInsurance.setVisibility(View.VISIBLE);
                                edtInsuranceIdv.setVisibility(View.VISIBLE);
                            } else {
                                relInsurance.setVisibility(View.GONE);

//                                if (insOtherRow.getVisibility() == View.VISIBLE)
//                                    insOtherRow.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mSubtype;
    }

    /*Fuel type for other*/
    private void fuelType() {
        mFuel.clear();
        mFuel.add("-Select Fuel Type-");
        mFuel.add("Petrol");
        mFuel.add("Diesel");
        mFuel.add("CNG");
        mFuel.add("LPG");
        mFuel.add("Electric");
        mFuel.add("Hybrid");
        if (getActivity() != null) {
            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, mFuel);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSetFuel.setAdapter(dataAdapter);
        }
    }

    /*
    Get Body Types...
     */
    private void getBodyTypes() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBodyType();
    }

    /*
    Get Implements
     */
    private void getImplements() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleImplements();
    }

    /*
    Get Vehicle Color...
     */
    private void getVehicleColor() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleColor();
    }

    /*
    Get Body and Seat Manufacture...
     */
    private void getBodyAndSeatManufacturer() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBodyAndSeatManufacture();
    }

    /*
            Get RTO City...
     */
    private void getRTOcity() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleRTOCity();
    }

    /*
            Add Body and seat manufacture....
     */
    private void addBodyAndSeatManufacturers(String bodymanufacturerstr, String seatmanufacturerstr) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addBodyAndSeatManufacturers(bodymanufacturerstr, seatmanufacturerstr);
    }

    /*
           Add Body Type....
    */
    private void AddBodytask(String keyword, String title) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addBodyType(keyword, title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_vehicle:
                upload();
                break;

            case R.id.btnbody:
                String edbody = edtBody.getText().toString();
                AddBodytask("BodyType", edbody);
                break;

            case R.id.refreshbody:
                getBodyTypes();
                linearBody.setVisibility(View.GONE);
                btnRefreshBody.setVisibility(View.GONE);
                break;
        }
    }

    private void upload() {

        Boolean flag = false;

        String rtostr = "", registernumberstr = "", locationstr = "", hypostr = "", bodymanufacturerstr = "", seatmanufacturerstr = "", rcStr = "", insuranceStr = "", taxStr = "", permitStr = "", fitnessStr = "", insuranceDate = "", taxDate = "", fitnessDate = "",
                permitDate = "", insuranceIdv = "", chasisstr = "", enginestr = "", emissionstr = "", drivestr = "", transstr = "",
                bustypestr = "", airstr = "", appstr = "", implementstr = "", seatcapstr = "", tyrecontextstr = "", fuelstr = "", hpcapstr = "",
                jibstr = "", boonstr = "";
        int ownerstr = 0;

        bodymanufacturerstr = autoBodymanufacturer.getText().toString();
        seatmanufacturerstr = autoSeatmanufacturer.getText().toString();
        insuranceIdv = edtInsuranceIdv.getText().toString();
        insuranceDate = edtInsuranceDate.getText().toString();
        taxDate = edtTaxPaidDate.getText().toString();
        fitnessDate = edtFitnessDate.getText().toString();
        permitDate = edtPermitDate.getText().toString();

        chasisstr = edtChasis.getText().toString();
        enginestr = edtEngine.getText().toString();
        appstr = edtApptext.getText().toString();
        implementstr = edtImplementStr.getText().toString();
        seatcapstr = edtSeatCap.getText().toString();
        tyrecontextstr = edtTyreContext.getText().toString();
        hpcapstr = edtSetHpcapa.getText().toString();
        jibstr = edtSetJib.getText().toString();
        boonstr = edtSetBoon.getText().toString();

        if (!bodymanufacturerstr.equals("") || !seatmanufacturerstr.equals("") && (!bodyManufacturerlist.contains(bodymanufacturerstr) && !seatManufacturerlist.contains(seatmanufacturerstr)))
            addBodyAndSeatManufacturers(bodymanufacturerstr, seatmanufacturerstr);

        int ownerPos = mOwnerSpinner.getSelectedItemPosition();
        if (ownerPos != 0)
            ownerstr = Integer.parseInt(mOwnerSpinner.getSelectedItem().toString());

        int emissionPos = mEmissionSpinner.getSelectedItemPosition();
        if (emissionPos != 0)
            emissionstr = mEmissionSpinner.getSelectedItem().toString();

        int hypoPos = mHypoSpinner.getSelectedItemPosition();
        if (hypoPos != 0)
            hypostr = mHypoSpinner.getSelectedItem().toString();

        int rcPos = mRcSpinner.getSelectedItemPosition();
        if (rcPos != 0)
            rcStr = mRcSpinner.getSelectedItem().toString();

        int insPos = mInsuranceSpinner.getSelectedItemPosition();
        if (insPos != 0)
            insuranceStr = mInsuranceSpinner.getSelectedItem().toString();

        int taxPos = mTaxValidSpinner.getSelectedItemPosition();
        if (taxPos != 0)
            taxStr = mTaxValidSpinner.getSelectedItem().toString();

        int permitPos = mPermitSpinner.getSelectedItemPosition();
        if (permitPos != 0)
            permitStr = mPermitSpinner.getSelectedItem().toString();

        int fitnessPos = mFitnessSpinner.getSelectedItemPosition();
        if (fitnessPos != 0)
            fitnessStr = mFitnessSpinner.getSelectedItem().toString();

        int drivePos = mDriveSpinner.getSelectedItemPosition();
        if (drivePos != 0)
            drivestr = mDriveSpinner.getSelectedItem().toString();

        int transPos = mTransmissionSpinner.getSelectedItemPosition();
        if (transPos != 0)
            transstr = mTransmissionSpinner.getSelectedItem().toString();

        int bustypePos = mBustypeSpinner.getSelectedItemPosition();
        if (bustypePos != 0)
            bustypestr = mBustypeSpinner.getSelectedItem().toString();

        int airPos = mAircondSpinner.getSelectedItemPosition();
        if (airPos != 0)
            airstr = mAircondSpinner.getSelectedItem().toString();

        int fuelPos = mSetFuel.getSelectedItemPosition();
        if (fuelPos != 0)
            fuelstr = mSetFuel.getSelectedItem().toString();

        if (!checkBox1.isChecked()) {
            rtostr = mRTOcity.getText().toString();
            registernumberstr = registernumber.getText().toString();
        } else {
            rtostr = "Unregistered";
            registernumberstr = "";
        }

        locationstr = mLocation.getText().toString();
        if (!locationstr.isEmpty()) {

            List<String> locationList = GooglePlacesAdapter.getResultList();
            for (int i = 0; i < locationList.size(); i++) {

                if (locationstr.equalsIgnoreCase(locationList.get(i))) {
                    flag = true;
                    break;

                } else {

                    flag = false;
                }
            }
        }

        if (!flag) {
            mLocation.setError("Please Select Address From Dropdown Only");
            mLocation.setFocusable(true);
            mLocation.requestFocus();
        } else if (!checkBox1.isChecked() && rtostr.equalsIgnoreCase(""))
            Toast.makeText(getActivity(), "Please provide RTO city", Toast.LENGTH_LONG).show();
        else {
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_Location", locationstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_Rto", rtostr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_RegistNo", registernumberstr).apply();

            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_BodyManufacture", bodymanufacturerstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_SeatManufacture", seatmanufacturerstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("upload_owner", ownerstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_emission", emissionstr).apply();

            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_hypo", hypostr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_rc", rcStr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_insurance", insuranceStr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_taxValid", taxStr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_permitValid", permitStr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_fitnessValid", fitnessStr).apply();

            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_insuranceIdv", insuranceIdv).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_insuranceDate", insuranceDate).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_taxDate", taxDate).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_fitnessDate", fitnessDate).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_permitDate", permitDate).apply();

            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_chasisNo", chasisstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_engineNo", enginestr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_drive", drivestr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_transmission", transstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_busType", bustypestr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_airCondition", airstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_application", appstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_implementStr", implementstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_seatingCapacity", seatcapstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_tyreContext", tyrecontextstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_fuel", fuelstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_hpCapacity", hpcapstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_Jib", jibstr).apply();
            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_Boon", boonstr).apply();

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View mViewDialogOtp = layoutInflater.inflate(R.layout.custom_alert_dialog_image, null);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle("Upload Image");
            builder1.setIcon(R.mipmap.ic_launcher);
            builder1.setView(mViewDialogOtp);

            ImageView mGallery = (ImageView) mViewDialogOtp.findViewById(R.id.gallery);
            mGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    start();
                }
            });
            alertDialog = builder1.create();
            alertDialog.show();
        }


    }

    private void start() {
        ImagePicker.create(this)
                .folderMode(true) // set folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(10) // max images can be selected (999 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .origin(mImages) // original selected images, used in multi mode
                .start(REQUEST_CODE_PICKER); // start image picker activity with request code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            mImages = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            StringBuilder sb = new StringBuilder();
            List<String> addData = new ArrayList<>();
            ArrayList<String> mPath = new ArrayList<>();
            for (int i = 0; i < mImages.size(); i++) {
                sb.append(mImages.get(i).getPath());
                mPath.add(mImages.get(i).getPath());
            }
            ArrayList<String> mPath1 = new ArrayList<>();
            int cnt = 0;
            String selectImages = "";
            String selectedimg = "";
            String allimg = "";
            for (int i = 0; i < mPath.size(); i++) {
                cnt++;
                if (cnt <= 12) {
                    selectImages = mPath.get(i);
                    String lastWord = selectImages.substring(selectImages.lastIndexOf("/") + 1);
                    mPath1.add(selectImages);
                    if (allimgpath.equalsIgnoreCase("")) {
                        allimgpath = "" + selectImages;
                    } else {
                        allimgpath = allimgpath + "," + selectImages;
                    }
                    if (selectedimg.equalsIgnoreCase("") && allimg.equalsIgnoreCase("")) {
                        selectedimg = "" + selectImages;
                        allimg = "" + lastWord.replace(" ", "");
                    } else {
                        selectedimg = selectedimg + "," + selectImages;
                        allimg = allimg + "," + lastWord.replace(" ", "");
                    }

                } else {
                    Toast.makeText(getActivity(),
                            "You can upload 12 picture only",
                            Toast.LENGTH_LONG).show();
                }
            }

            if (cnt == 0) {
                Toast.makeText(getActivity(),
                        "Please select at least one image",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(),
                        "You've selected Total " + cnt + " image(s).",
                        Toast.LENGTH_LONG).show();
                Log.d("SelectedImages", selectImages);

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("images", allimgpath).apply();


                alertDialog.dismiss();

                Bundle b = new Bundle();
                b.putStringArrayList("IMAGE", mPath1);
                b.putInt("call", 1);
                SelectedImagesFragment mSelectedImagesFragment = new SelectedImagesFragment();
                mSelectedImagesFragment.setArguments(b);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.vehicle_upload_container, mSelectedImagesFragment, "selectedimagefragment")
                        .addToBackStack("selectedimagefragment")
                        .commit();

            }
            System.out.println(selectedimg);
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                //Rto city
                if (response.body() instanceof GetRTOCityResponse) {
                    mRtoCity.clear();
                    GetRTOCityResponse mGetRTOCityResponse = (GetRTOCityResponse) response.body();
                    for (GetRTOCityResponse.Success success : mGetRTOCityResponse.getSuccess()) {
                        /*success.setRtoCityId(success.getRtoCityId());
                        success.setRtoCityName(success.getRtoCityName());
                        success.setRtoCode(success.getRtoCode());
                        mRtoCity.add(success.getRtoCode());*/
                        mRtoCity.add(success.getRtoCode() + " " +
                                success.getRtoCityName());
                    }
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, mRtoCity);
                        mRTOcity.setAdapter(adapter);
                    }
                }
                //Vehicle color
                else if (response.body() instanceof GetVehicleColor) {
                    mVehicleColor.clear();
                    GetVehicleColor mGetVehicleColor = (GetVehicleColor) response.body();
                    mVehicleColor.add("--Select Color--");
                    for (GetVehicleColor.Success success : mGetVehicleColor.getSuccess()) {
                        success.setColorId(success.getColorId());
                        success.setColor(success.getColor());
                        mVehicleColor.add(success.getColor());
                    }
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, mVehicleColor);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSetColorSpinner.setAdapter(adapter);
                    }
                    mSetColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String color = mSetColorSpinner.getSelectedItem().toString();
                                int colorid = mSetColorSpinner.getSelectedItemPosition();

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_color", color).apply();

                                System.out.println("Color is::" + color);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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
                //Vehicle Implements
                else if (response.body() instanceof GetVehicleImplementsResponse) {
                    mVehicleImplements.clear();
                    GetVehicleImplementsResponse mGetVehicleImplementsResponse = (GetVehicleImplementsResponse) response.body();
                    mVehicleImplements.add("--Select Implements--");
                    for (GetVehicleImplementsResponse.Success success : mGetVehicleImplementsResponse.getSuccess()) {
                        success.setId(success.getId());
                        success.setImplementName(success.getImplementName());
                        mVehicleImplements.add(success.getImplementName());
                    }
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, mVehicleImplements);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mImplementSpinner.setAdapter(adapter);
                    }
                    mImplementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String implement = mImplementSpinner.getSelectedItem().toString();
                                int implementid = mImplementSpinner.getSelectedItemPosition();

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_implement", implement).apply();

                                System.out.println("Implement is::" + implement);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                //Body and Seat
                else if (response.body() instanceof BodyAndSeatResponse) {
                    BodyAndSeatResponse bodyAndSeatResponse = (BodyAndSeatResponse) response.body();
                    if (!bodyAndSeatResponse.getSuccess().getBodyManufac().isEmpty()) {
                        bodyManufacturerlist.clear();
                        for (BodyAndSeatResponse.Success.BodyManufac bodySuccess : bodyAndSeatResponse.getSuccess().getBodyManufac()) {
                            bodySuccess.setBodyManufacturerId(bodySuccess.getBodyManufacturerId());
                            bodySuccess.setBodyManufacturerName(bodySuccess.getBodyManufacturerName());
                            bodyManufacturerlist.add(bodySuccess.getBodyManufacturerName());
                        }
                        if (getActivity() != null) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item, bodyManufacturerlist);
                            autoBodymanufacturer.setAdapter(dataAdapter);
                        }
                    }

                    if (!bodyAndSeatResponse.getSuccess().getSeatManufac().isEmpty()) {
                        seatManufacturerlist.clear();
                        for (BodyAndSeatResponse.Success.SeatManufac seatSuccess : bodyAndSeatResponse.getSuccess().getSeatManufac()) {
                            seatSuccess.setSeatManufacturerId(seatSuccess.getSeatManufacturerId());
                            seatSuccess.setSeatManufacturerName(seatSuccess.getSeatManufacturerName());
                            seatManufacturerlist.add(seatSuccess.getSeatManufacturerName());
                        }
                        if (getActivity() != null) {
                            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item, seatManufacturerlist);
                            autoSeatmanufacturer.setAdapter(dataAdapter1);
                        }
                    }
                }
            } else {
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string._404));
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
            Log.i("Check Class-", "Sub Type Fragment");
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {

            if (str.equals("success_body")) {
                if (isAdded())
                    CustomToast.customToast(getActivity(), "Data added successfully");
                getBodyAndSeatManufacturer();
            } else if (str.equals("success_body_type_add")) {
                if (isAdded())
                    CustomToast.customToast(getActivity(), "Body type added successfully");
                getBodyTypes();
            }
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (v.getId()) {

            case R.id.editinsurancedate:
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    edtInsuranceDate.setInputType(InputType.TYPE_NULL);
                    edtInsuranceDate.setError(null);
                    new SetMyDateAndTime("date", edtInsuranceDate, getActivity());
                }
                break;

            case R.id.taxpaid1:
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    edtTaxPaidDate.setInputType(InputType.TYPE_NULL);
                    edtTaxPaidDate.setError(null);
                    new SetMyDateAndTime("date", edtTaxPaidDate, getActivity());
                }
                break;

            case R.id.fitnesstext:
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    edtFitnessDate.setInputType(InputType.TYPE_NULL);
                    edtFitnessDate.setError(null);
                    new SetMyDateAndTime("date", edtFitnessDate, getActivity());
                }
                break;

            case R.id.permittext:
                if (action == MotionEvent.ACTION_DOWN) {
                    //whichclick = "enddate";
                    edtPermitDate.setInputType(InputType.TYPE_NULL);
                    edtPermitDate.setError(null);
                    new SetMyDateAndTime("date", edtPermitDate, getActivity());
                }
                break;

        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.vehicle_upload_container);

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }
}