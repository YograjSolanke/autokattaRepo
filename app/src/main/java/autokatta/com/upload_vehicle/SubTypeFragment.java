package autokatta.com.upload_vehicle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.MonthYearPicker;
import autokatta.com.response.GetBodyTypeResponse;
import autokatta.com.response.GetRTOCityResponse;
import autokatta.com.response.GetVehicleColor;
import autokatta.com.response.GetVehicleImplementsResponse;
import retrofit2.Response;
import retrofit2.http.GET;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 21/3/17.
 */

public class SubTypeFragment extends Fragment implements View.OnClickListener, RequestNotifier {

    View mSubtype;
    private MonthYearPicker myp;
    EditText mMakeMonth, mMakeYear;
    EditText mRegisterMonth, mRegisterYear;
    Button mUploadVehicle;
    String allimgpath = "";
    ArrayList<Image> mImages = new ArrayList<>();
    int REQUEST_CODE_PICKER = 2000;
    List<String> mRtoCity = new ArrayList<>();
    List<String> mVehicleColor = new ArrayList<>();
    List<String> mBodyType = new ArrayList<>();
    List<String> mVehicleImplements = new ArrayList<>();
    List<String> mFuel = new ArrayList();

    Spinner mBodyTypeSpinner, mImplementSpinner, mSetColorSpinner, mSetFuel;
    /*
    Location...
     */
    AutoCompleteTextView mLocation;
    AutoCompleteTextView mRTOcity;
    String subCategory;
    CheckBox checkBox1;
    EditText registernumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSubtype = inflater.inflate(R.layout.fragment_subtype_fragment, container, false);
        mUploadVehicle = (Button) mSubtype.findViewById(R.id.upload_vehicle);
        checkBox1 = (CheckBox) mSubtype.findViewById(R.id.checkBox1);
        mRTOcity = (AutoCompleteTextView) mSubtype.findViewById(R.id.rtoautocompletetext);
        mLocation = (AutoCompleteTextView) mSubtype.findViewById(R.id.autolocation);
        registernumber = (EditText) mSubtype.findViewById(R.id.registernumber);
        mBodyTypeSpinner = (Spinner) mSubtype.findViewById(R.id.bodytypespinner);
        mImplementSpinner = (Spinner) mSubtype.findViewById(R.id.implementSpinner);
        mSetColorSpinner = (Spinner) mSubtype.findViewById(R.id.setcolor);
        mSetFuel = (Spinner) mSubtype.findViewById(R.id.setfuel);
        mUploadVehicle.setOnClickListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    getRTOcity();
                    getVehicleColor();
                    getImplements();
                    getBodyTypes();
                /*if (subCategory.equals("Excavator") || subCategory.equals("Skid Steers") || subCategory.equals("Crawlers")
                        || subCategory.equals("Dozer") || subCategory.equals("Concrete Mixers") || subCategory.equals("Road Rollers")
                        || subCategory.equals("Milling Equipment") || subCategory.equals("Trenches")) {
                    mRTOcity.setVisibility(View.GONE);
                    registernumber.setVisibility(View.GONE);
                    checkBox1.setVisibility(View.GONE);
                }*/

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


                    mFuel.add("-Select Fuel Type-");
                    mFuel.add("Petrol");
                    mFuel.add("Diesel");
                    mFuel.add("CNG");
                    mFuel.add("LPG");
                    mFuel.add("Electric");
                    mFuel.add("Hybrid");

                    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_spinner_item, mFuel);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSetFuel.setAdapter(dataAdapter);

                /*
                Owner Fragment...
                 */
                /*if (category.equalsIgnoreCase("Bus")) {

                    bodymanufacturer.setVisibility(View.VISIBLE);
                    seatmanufacturer.setVisibility(View.VISIBLE);

                    getBodyAndSeatManufacturer();
                } else if (category.equalsIgnoreCase("Tractor") || category.equalsIgnoreCase("Construction Equipment") ||
                        category.equalsIgnoreCase("2 Wheeler")) {

                    emmisionVersionspn.setVisibility(View.GONE);
                }*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        /*mMakeMonth = (EditText) mSubtype.findViewById(R.id.make_month);
        mMakeYear = (EditText) mSubtype.findViewById(R.id.make_year);
        mRegisterMonth = (EditText) mSubtype.findViewById(R.id.register_month);
        mRegisterYear = (EditText) mSubtype.findViewById(R.id.register_year);

        mMakeMonth.setInputType(InputType.TYPE_NULL);
        mMakeYear.setInputType(InputType.TYPE_NULL);
        mRegisterMonth.setInputType(InputType.TYPE_NULL);
        mRegisterYear.setInputType(InputType.TYPE_NULL);

        mMakeMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp.show();
                makeMonth();
            }
        });

        mMakeYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp.show();
                myp.build(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMakeMonth.setText(myp.getSelectedMonthName());
                        mMakeYear.setText(myp.getSelectedYear());
                    }
                }, null);
            }
        });

        mRegisterMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp.show();
                myp.build(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRegisterMonth.setText(myp.getSelectedMonthName());
                        mRegisterYear.setText(myp.getSelectedYear());
                    }
                }, null);
            }
        });

        mRegisterYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp.show();
                myp.build(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRegisterMonth.setText(myp.getSelectedMonthName());
                        mRegisterYear.setText(myp.getSelectedYear());
                    }
                }, null);
            }
        });
        *//*mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp.show();
            }
        });*/

        return mSubtype;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_vehicle:
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
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
                break;
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
            ArrayList<String> addData = new ArrayList<>();
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
                /*SharedPreferences settings = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_WORLD_READABLE | Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("images", allimgpath);
                editor.commit();*/

                Bundle b = new Bundle();
                b.putStringArrayList("IMAGE", mPath1);
                b.putInt("call", 1);
                SelectedImagesFragment mSelectedImagesFragment = new SelectedImagesFragment();
                mSelectedImagesFragment.setArguments(b);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = manager.beginTransaction();
                mFragmentTransaction.replace(R.id.vehicle_upload_container, mSelectedImagesFragment).commit();
                /*SelectedImagesFragment fragment2 = new SelectedImagesFragment();
                fragment2.setArguments(b);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, fragment2);
                fragmentTransaction.addToBackStack("selectedimagefragment");
                fragmentTransaction.commit();*/
            }
            System.out.println(selectedimg);
            System.out.println(selectedimg);
        }
        //textView.setText(sb.toString());
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                if (response.body() instanceof GetRTOCityResponse){
                    GetRTOCityResponse mGetRTOCityResponse = (GetRTOCityResponse) response.body();
                    for (GetRTOCityResponse.Success success : mGetRTOCityResponse.getSuccess()){
                        success.setRtoCityId(success.getRtoCityId());
                        success.setRtoCityName(success.getRtoCityName());
                        mRtoCity.add(success.getRtoCityName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_spinner_item, mRtoCity);
                    mRTOcity.setAdapter(adapter);
                } else if (response.body() instanceof GetVehicleColor){
                    GetVehicleColor mGetVehicleColor = (GetVehicleColor) response.body();
                    for (GetVehicleColor.Success success : mGetVehicleColor.getSuccess()){
                        success.setColorId(success.getColorId());
                        success.setColor(success.getColor());
                        mVehicleColor.add(success.getColor());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, mVehicleColor);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSetColorSpinner.setAdapter(adapter);
                }else if (response.body() instanceof GetBodyTypeResponse){
                    GetBodyTypeResponse mGetBodyTypeResponse = (GetBodyTypeResponse) response.body();
                    for (GetBodyTypeResponse.Success success : mGetBodyTypeResponse.getSuccess()){
                        success.setTitle(success.getTitle());
                        mBodyType.add(success.getTitle());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_spinner_item,mBodyType);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mBodyTypeSpinner.setAdapter(adapter);
                }else if (response.body() instanceof GetVehicleImplementsResponse){
                    GetVehicleImplementsResponse mGetVehicleImplementsResponse = (GetVehicleImplementsResponse) response.body();
                    for (GetVehicleImplementsResponse.Success success : mGetVehicleImplementsResponse.getSuccess()){
                        success.setId(success.getId());
                        success.setImplementName(success.getImplementName());
                        mVehicleImplements.add(success.getImplementName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, mVehicleImplements);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mImplementSpinner.setAdapter(adapter);
                }
            }else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        }else {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
    /*private void makeMonth() {
        myp = new MonthYearPicker(getActivity());
        myp.build(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMakeMonth.setText(myp.getSelectedMonthName());
                mMakeYear.setText(myp.getSelectedYear());
            }
        }, null);
    }*/
}

    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.make_month:
                myp.show();
                break;
        }
        return false;
    }*/