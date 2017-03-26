package autokatta.com.upload_vehicle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.MonthYearPicker;
import autokatta.com.response.GetBreaks;
import autokatta.com.response.GetPumpResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
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
    //SubType
    List<String> mSubTypeList = new ArrayList<>();
    List<String> parsedData = new ArrayList<>();
    HashMap<String, String> mSubTypeList1 = new HashMap<>();
    //Break
    List<String> mBreakList = new ArrayList<>();
    List<String> breakListData = new ArrayList<>();
    HashMap<String, String> mBreakList1 = new HashMap<>();
    //Pump
    List<String> mPumpList = new ArrayList<>();
    List<String> pumpListData = new ArrayList<>();
    HashMap<String, String> mPumpList1 = new HashMap<>();
    //Staring
    List<String> mStaringList = new ArrayList<>();
    String category, categoryId;

    /*
    Year Fragment...
     */
    private MonthYearPicker myp;
    EditText mMakeMonth, mMakeYear;
    EditText mRegisterMonth, mRegisterYear;
    LinearLayout linearPermit;

    /*
        kms/hrs fragment
     */
    EditText mHrs, mKms;

    public Title() {
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
        linearPermit = (LinearLayout) mTitle.findViewById(R.id.linearPermit);
        mTouristPassing = (RadioButton) mTitle.findViewById(R.id.tourist_passing);
        mPrivatePassing = (RadioButton) mTitle.findViewById(R.id.private_passing);

        /*
        Brand Fragment...
         */
        mBrandSpinner = (Spinner) mTitle.findViewById(R.id.brandspinner);
        mModelSpinner = (Spinner) mTitle.findViewById(R.id.modelspinner);
        mVersionSpinner = (Spinner) mTitle.findViewById(R.id.versionspinner);

        /*
        Year Fragment...
         */

        mMakeMonth = (EditText) mTitle.findViewById(R.id.make_month);
        mMakeYear = (EditText) mTitle.findViewById(R.id.make_year);
        mRegisterMonth = (EditText) mTitle.findViewById(R.id.register_month);
        mRegisterYear = (EditText) mTitle.findViewById(R.id.register_year);


        /*
        kms fragment
         */
        mPumpSpinner = (Spinner) mTitle.findViewById(R.id.pumpspinner);
        mBreakSpinner = (Spinner) mTitle.findViewById(R.id.brakespinner);
        mStaringSpinner = (Spinner) mTitle.findViewById(R.id.stearingspinner);
        mHrs = (EditText) mTitle.findViewById(R.id.hrstext1);
        mKms = (EditText) mTitle.findViewById(R.id.kmstext1);

        category = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_categoryName", null);
        categoryId = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_categoryId", null);

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
                    Sub type Fragment
                     */
                    if (!category.equalsIgnoreCase("Car")) {
                        linearPermit.setVisibility(View.GONE);
                    }

                    /*
                    Brand Fragment...
                     */
                    if (category.equalsIgnoreCase("construction Equipment"))
                        mVersionSpinner.setVisibility(View.GONE);

                    /*
                    Kms fragment
                     */
                    if (category.equalsIgnoreCase("Construction Equipment") || category.equalsIgnoreCase("Cranes")) {
                        mKms.setVisibility(View.GONE);
                        mHrs.setVisibility(View.VISIBLE);
                        mPumpSpinner.setVisibility(View.GONE);
                        mBreakSpinner.setVisibility(View.GONE);
                        mStaringSpinner.setVisibility(View.GONE);
                        //note.setText("In Hrs");

                    } else if (category.equalsIgnoreCase("Tractor")) {
                        mKms.setVisibility(View.GONE);
                        mHrs.setVisibility(View.VISIBLE);
                        mPumpSpinner.setVisibility(View.VISIBLE);
                        mBreakSpinner.setVisibility(View.VISIBLE);
                        mStaringSpinner.setVisibility(View.VISIBLE);
                        //note.setText("In Hrs");
                    } else {
                        mKms.setVisibility(View.VISIBLE);
                        mHrs.setVisibility(View.GONE);
                        mPumpSpinner.setVisibility(View.GONE);
                        mBreakSpinner.setVisibility(View.GONE);
                        mStaringSpinner.setVisibility(View.GONE);
                        //note.setText("In Kms");

                    }
                    /*
                    Staring Spinner
                     */
                    mStaringList.add("Select Staring Type");
                    mStaringList.add("Power");
                    mStaringList.add("Manual");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mStaringList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mStaringSpinner.setAdapter(adapter);
                    mStaringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {

                                String stearingname = mStaringList.get(position);

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_stearingName", stearingname).apply();

                                System.out.println("Stearing name is::" + stearingname);
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
        mApiCall.getVehicleSubtype(categoryId);
    }

    /*
    Get Brand
     */
    private void getBrand(String category, String subcategory) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        //mApiCall.getBrand();
    }

    /*
    Get Model...
     */
    private void getModel() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
    }

    /*
    Get Version...
     */
    private void getVersion() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
    }

    /*
    Get Breaks...
     */
    private void getBreaks() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBreaks();
    }

    /*
    Get Staring...
     */
    private void getPumps() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getPump();
    }

    /*
   Add other Break...
    */
    private void addOtherBreak(String otherBreak) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addBreak(otherBreak);
    }

    /*
   Add other Pump...
    */
    private void addOtherPump(String otherPump) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addPump(otherPump);
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
                            if (position != 0) {
                                String subcategory = mSubTypeList1.get(parsedData.get(position));
                                String subcategoryname = parsedData.get(position);

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_subCatId", subcategory).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_subCatName", subcategoryname).apply();

                                System.out.println("Sub cat is::" + subcategory);
                                System.out.println("Sub cat name::" + subcategoryname);

                                //getBrand();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (response.body() instanceof GetBreaks) {
                    Log.e("Get", "Breaks");
                    GetBreaks mGetBreaks = (GetBreaks) response.body();
                    mBreakList.add("Select Break Types");
                    for (GetBreaks.Success success : mGetBreaks.getSuccess()) {
                        success.setId(success.getId());
                        success.setTitle(success.getTitle());
                        mBreakList.add(success.getTitle());
                        mBreakList1.put(success.getTitle(), success.getId());
                    }
                    breakListData.addAll(mBreakList);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, breakListData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mBreakSpinner.setAdapter(adapter);
                    mBreakSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String brakeid = mBreakList1.get(breakListData.get(position));
                                String brakename = breakListData.get(position);

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_brakeId", brakeid).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_brakeName", brakename).apply();

                                System.out.println("Brake id:" + brakeid);
                                System.out.println("Brake name::" + brakename);
                            }

                            if (mBreakSpinner.getSelectedItem().toString().equalsIgnoreCase("Other")) {

                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Add Brakes");
                                alertDialog.setMessage("Enter Brake name");

                                final EditText input = new EditText(getActivity());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

                                alertDialog.setPositiveButton("Add Brake",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                String otherBreak = input.getText().toString();

                                                if (otherBreak.equals(""))
                                                    CustomToast.customToast(getActivity(), "Please enter Brake type");
                                                else
                                                    addOtherBreak(otherBreak);


                                                dialog.cancel();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBreakSpinner.setSelection(0);
                                                dialog.cancel();
                                            }
                                        });

                                alertDialog.show();

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (response.body() instanceof GetPumpResponse) {
                    Log.e("Get", "Pumps");
                    GetPumpResponse mGetPumpResponse = (GetPumpResponse) response.body();
                    mPumpList.add("Select Pump Types");
                    for (GetPumpResponse.Success success : mGetPumpResponse.getSuccess()) {
                        success.setId(success.getId());
                        success.setTitle(success.getTitle());
                        mPumpList.add(success.getTitle());
                        mPumpList1.put(success.getTitle(), success.getId());
                    }
                    pumpListData.addAll(mPumpList);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, pumpListData);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mPumpSpinner.setAdapter(adapter);
                    mPumpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                String pumpId = mPumpList1.get(pumpListData.get(position));
                                String pumpName = mPumpList1.get(pumpListData.get(position));

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_pumpId", pumpId).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_pumpName", pumpName).apply();

                                System.out.println("pump id::" + pumpId);
                                System.out.println("pump name::" + pumpName);
                            }
                            if (mPumpSpinner.getSelectedItem().toString().equalsIgnoreCase("Other")) {

                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Add Pump");
                                alertDialog.setMessage("Enter Pump name");

                                final EditText input = new EditText(getActivity());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

                                alertDialog.setPositiveButton("Add Pump",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                String otherPump = input.getText().toString();

                                                if (otherPump.equals(""))
                                                    CustomToast.customToast(getActivity(), "Please enter Brake type");
                                                else
                                                    addOtherPump(otherPump);


                                                dialog.cancel();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mPumpSpinner.setSelection(0);
                                                dialog.cancel();
                                            }
                                        });

                                alertDialog.show();

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

               /* vehicleEditor.putString("title", titletext);
                vehicleEditor.putString("privacy", privacy);
                vehicleEditor.putString("exchange", exchangeYesNo);
                vehicleEditor.putString("storeprivacy", storeprivacy);
                vehicleEditor.putString("financestatus", financests);
                vehicleEditor.putString("exchangestatus", exchangests);
                vehicleEditor.putString("OwnerGroupIds", stringgroupids);
                vehicleEditor.putString("OwnerStoreIds", stringstoreids);
                vehicleEditor.putString("OwnerGroupName", stringgroupname);
                vehicleEditor.putString("OwnerStoreName", stringstorename);
                */
                String strTitle = "", strGroupPriavcy = "", strStorePrivacy = "", strFinanceStatus = "", strExchangeStatus = "",
                        permit = "", strMakemonth = "", strMakeyear = "", strRegisterMonth = "", strRegisterYear = "",
                        strHrs = "", strKms = "";

                strTitle = title.getText().toString();
                strMakemonth = mMakeMonth.getText().toString();
                strMakeyear = mMakeYear.getText().toString();
                strRegisterMonth = mRegisterMonth.getText().toString();
                strRegisterYear = mRegisterYear.getText().toString();
                strHrs = mHrs.getText().toString();
                strKms = mKms.getText().toString();

                if (radioButton1.isChecked()) {
                    strGroupPriavcy = "Yes";
                } else if (radioButton2.isChecked()) {
                    strGroupPriavcy = "No";
                }
                if (storeradioyes.isChecked()) {
                    strStorePrivacy = "Yes";
                } else if (storeradiono.isChecked()) {

                    strStorePrivacy = "No";
                }


                if (financeyes.isChecked()) {
                    strFinanceStatus = "Yes";
                } else if (financeno.isChecked()) {

                    strFinanceStatus = "No";
                }


                if (exchangeyes.isChecked()) {
                    strExchangeStatus = "Yes";
                } else if (exchangeno.isChecked()) {

                    strExchangeStatus = "No";
                }


                if (category.equalsIgnoreCase("Car")) {

                    if (mTouristPassing.isChecked())
                        permit = "Tourist Passing";
                    if (mPrivatePassing.isChecked())
                        permit = "Private Passing";
                }

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_Title", strTitle).apply();

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_groupPrivacy", strGroupPriavcy).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_GroupIds", stringgroupids).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_GroupNames", stringgroupname).apply();

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_storePrivacy", strStorePrivacy).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_StoreIds", stringstoreids).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_StoreNames", stringstorename).apply();

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_financeStatus", strFinanceStatus).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_exchangeStatus", strExchangeStatus).apply();

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_permit", permit).apply();

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_makeMonth", strMakemonth).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_makeYear", strMakeyear).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_registerMonth", strRegisterMonth).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_registerYear", strRegisterYear).apply();

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_Hrs", strHrs).apply();
                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_Kms", strKms).apply();


                FragmentManager manager = getFragmentManager();
                FragmentTransaction mTransaction = manager.beginTransaction();
                mTransaction.replace(R.id.vehicle_upload_container, new SubTypeFragment()).addToBackStack("title").commit();
                break;
        }
    }
}
