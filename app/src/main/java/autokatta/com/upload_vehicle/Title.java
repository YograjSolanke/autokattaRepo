package autokatta.com.upload_vehicle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.fragment_profile.Modules;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.MonthYearPicker;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.ModelGroups;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 19/3/17.
 */

public class Title extends Fragment implements View.OnClickListener, RequestNotifier {
    View mTitle;
    ScrollView scrollView1;
    RadioButton radioButton1, radioButton2, storeradioyes, storeradiono, financeyes, financeno, exchangeyes, exchangeno;
    RadioButton mTouristPassing, mPrivatePassing;
    EditText title;
    TextView mCategory;
    Button mSubmit;
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    String[] stringTitles = new String[0];
    String[] storetitlearray = new String[0];
    String stringgroupids = "", stringstoreids = "", stringstorename = "", stringgroupname = "";
    String financests = null, exchangests = null;
    Spinner mSubType;
    Spinner mBrandSpinner, mModelSpinner, mVersionSpinner;
    Spinner mPumpSpinner, mBreakSpinner, mStaringSpinner;
    List<String> mSubTypeList = new ArrayList<>();
    List<String> parsedData = new ArrayList<>();
    HashMap<String, String> mSubTypeList1 = new HashMap<>();
    String category;

    /*
    Year Fragment...
     */
    private MonthYearPicker myp;
    EditText mMakeMonth, mMakeYear;
    EditText mRegisterMonth, mRegisterYear;

    public Title(){
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTitle = inflater.inflate(R.layout.fragment_upload_vehicle_title, container, false);
        scrollView1 = (ScrollView) mTitle.findViewById(R.id.scrollView1);
        title = (EditText) mTitle.findViewById(R.id.titleText1);
        mCategory = (TextView) mTitle.findViewById(R.id.categorytext);
        radioButton1 = (RadioButton) mTitle.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) mTitle.findViewById(R.id.radioButton2);
        storeradioyes = (RadioButton) mTitle.findViewById(R.id.storeradio1);
        storeradiono = (RadioButton) mTitle.findViewById(R.id.storeradio2);

        financeyes = (RadioButton) mTitle.findViewById(R.id.financeYes);
        financeno = (RadioButton) mTitle.findViewById(R.id.financeNo);
        exchangeyes = (RadioButton) mTitle.findViewById(R.id.exchangeYes);
        exchangeno = (RadioButton) mTitle.findViewById(R.id.exchangeNo);

         /*
        mSub type...
         */
        mSubType = (Spinner) mTitle.findViewById(R.id.subtypespinner);

        /*
        Sub Type...
         */
        mTouristPassing = (RadioButton) mTitle.findViewById(R.id.tourist_passing);
        mPrivatePassing = (RadioButton) mTitle.findViewById(R.id.private_passing);

        /*
        Brand Fragment...
         */
        mBrandSpinner = (Spinner) mTitle.findViewById(R.id.brandspinner);
        mModelSpinner = (Spinner) mTitle.findViewById(R.id.modelspinner);
        mVersionSpinner = (Spinner) mTitle.findViewById(R.id.versionspinner);

        /*
        kms fragment
         */
        mPumpSpinner = (Spinner) mTitle.findViewById(R.id.pumpspinner);
        mBreakSpinner = (Spinner) mTitle.findViewById(R.id.brakespinner);
        mStaringSpinner = (Spinner) mTitle.findViewById(R.id.stearingspinner);

        category = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("category", null);

        mSubmit = (Button) mTitle.findViewById(R.id.title_next);
        mSubmit.setOnClickListener(this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getGroup();
                    getStore();
                    getSubCategoryTask();
                    getModel();
                    getVersion();
                    getBrand();
                    getBreaks();
                    getPumps();

                    radioButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (stringTitles.length == 0) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("No Group Found");
                                alertDialog.setMessage("Do you want to create Group...");
                                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        FragmentManager manager = getFragmentManager();
                                        FragmentTransaction mFragmentTransaction = manager.beginTransaction();
                                        mFragmentTransaction.replace(R.id.vehicle_upload_container, new Upload_Group_Create_Fragment()).commit();
                                    }
                                });
                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        radioButton2.setChecked(true);
                                    }
                                });
                                alertDialog.show();
                            } else {
                                alertBoxToSelectExcelSheet(stringTitles);
                            }
                        }
                    });

                    //radio store
                    radioButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            stringgroupids = "";
                            stringgroupname = "";
                        }
                    });
                    storeradiono.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            stringstoreids = "";
                            stringstorename = "";
                        }
                    });

                    storeradioyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertBoxToSelectStore(storetitlearray);
                        }
                    });

                    if (financeyes.isChecked()) {
                        financests = "Yes";
                    } else if (financeno.isChecked()) {

                        financests = "No";
                    }

                    if (exchangeyes.isChecked()) {
                        exchangests = "Yes";
                    } else if (exchangeno.isChecked()) {

                        exchangests = "No";
                    }

                    String permit = "";
                    if (category.equalsIgnoreCase("Car")) {

                        if (mTouristPassing.isChecked())
                            permit = "Tourist Passing";
                        if (mPrivatePassing.isChecked())
                            permit = "Private Passing";
                    }

                    /*
                    Brand Fragment...
                     */
                    if(category.equalsIgnoreCase("construction Equipment"))
                        mVersionSpinner.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return mTitle;
    }

    /*
    Get Group Data...
     */
    private void getGroup() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.Groups(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));
    }

    /*
    Get store Data...
     */

    private void getStore() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.MyStoreList(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));
    }

    /*
    Sub Category...
     */
    private void getSubCategoryTask() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleSubtype(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("category_id", null));
    }

    /*
    Get Brand
     */
    private void getBrand() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
    }

    /*
    Get Model...
     */
    private void getModel(){
        ApiCall mApiCall = new ApiCall(getActivity(), this);
    }

    /*
    Get Version...
     */
    private void getVersion(){
        ApiCall mApiCall = new ApiCall(getActivity(), this);
    }

    /*
    Get Breaks...
     */
    private void getBreaks(){
        ApiCall mApiCall = new ApiCall(getActivity(), this);
    }

    /*
    Get Staring...
     */
    private void getPumps(){
        ApiCall mApiCall = new ApiCall(getActivity(), this);
    }


    /*
    Alert Dialog
     */
    private void alertBoxToSelectExcelSheet(final String[] stringTitles) {
        final ArrayList<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        stringgroupids = "";
        stringgroupname = "";

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        // set the dialog title
        builder.setTitle("Select Groups From Following")
                .setCancelable(true)
                .setMultiChoiceItems(stringTitles, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(stringTitles[which]);
                        } else if (mSelectedItems.contains(stringTitles[which])) {
                            mSelectedItems.remove(stringTitles[which]);
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < stringTitles.length; j++) {
                                if (mSelectedItems.get(i).equals(stringTitles[j])) {
                                    if (stringgroupids.equals("")) {
                                        stringgroupids = list.get(j);
                                        stringgroupname = stringTitles[j];
                                    } else {
                                        stringgroupids = stringgroupids + "," + list.get(j);
                                        stringgroupname = stringgroupname + "," + stringTitles[j];
                                    }
                                }
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(true);
                    }

                })
                .show();
    }

    /*
    Store
     */

    private void alertBoxToSelectStore(final String[] choices) {
        final ArrayList<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        stringstoreids = "";
        stringstorename = "";
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        // set the dialog title
        builder.setTitle("Select Stores From Following")
                .setCancelable(true)
                .setMultiChoiceItems(choices, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(choices[which]);
                        } else if (mSelectedItems.contains(choices[which])) {
                            mSelectedItems.remove(choices[which]);
                        }
                    }

                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < storetitlearray.length; j++) {
                                if (mSelectedItems.get(i).equals(storetitlearray[j])) {
                                    if (stringstoreids.equals("")) {
                                        stringstoreids = list1.get(i);
                                        stringstorename = storetitlearray[j];
                                    } else {
                                        stringstoreids = stringstoreids + "," + list1.get(i);
                                        stringstorename = stringstorename + "," + storetitlearray[j];
                                    }
                                }
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                        storeradioyes.setChecked(false);
                        storeradiono.setChecked(true);
                        stringstoreids = "";
                        stringstorename = "";
                    }

                })
                .show();
    }


    /*
    Response from Retrofit
     */
    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof ProfileGroupResponse) {
                    Log.e("ProfileGroupResponse", "->");
                    ProfileGroupResponse mProfileGroupResponse = (ProfileGroupResponse) response.body();
                    for (ProfileGroupResponse.MyGroup success : mProfileGroupResponse.getSuccess().getMyGroups()) {
                        list.add(success.getTitle());
                    }
                    stringTitles = list.toArray(new String[list.size()]);
                } else if (response.body() instanceof MyStoreResponse) {
                    Log.e("MyStoreResponse", "->");
                    MyStoreResponse myStoreResponse = (MyStoreResponse) response.body();
                    for (MyStoreResponse.Success success : myStoreResponse.getSuccess()) {
                        list1.add(success.getName());
                    }
                    storetitlearray = list1.toArray(new String[list1.size()]);
                } else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    Log.e("GetVehicleTypes", "->");
                    mSubTypeList.add("Select Vehicle Types");
                    GetVehicleSubTypeResponse mGetVehicleSubTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    for (GetVehicleSubTypeResponse.Success subTypeResponse : mGetVehicleSubTypeResponse.getSuccess()) {
                        subTypeResponse.setId(subTypeResponse.getId());
                        subTypeResponse.setName(subTypeResponse.getName());
                        mSubTypeList.add(subTypeResponse.getName());
                        mSubTypeList1.put(subTypeResponse.getName(), subTypeResponse.getId());
                    }
                    parsedData.addAll(mSubTypeList);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, parsedData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSubType.setAdapter(adapter);
                    mSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position!=0){
                                String subcategory = mSubTypeList1.get(parsedData.get(position));
                                System.out.println("Sub cat is::" + subcategory);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    Log.e("Title Fragment", "No Response found");
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

    /*
    On Click Listener...
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_next:
                FragmentManager manager = getFragmentManager();
                FragmentTransaction mTransaction = manager.beginTransaction();
                mTransaction.replace(R.id.vehicle_upload_container, new SubTypeFragment()).addToBackStack("title").commit();
                break;
        }
    }
}
