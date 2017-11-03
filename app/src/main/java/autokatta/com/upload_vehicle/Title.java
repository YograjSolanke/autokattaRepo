package autokatta.com.upload_vehicle;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.initial_fragment.CreateGroupFragment;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.other.MonthYearPicker;
import autokatta.com.response.GetBreaks;
import autokatta.com.response.GetPumpResponse;
import autokatta.com.response.GetVehicleBrandResponse;
import autokatta.com.response.GetVehicleModelResponse;
import autokatta.com.response.GetVehicleSubTypeResponse;
import autokatta.com.response.GetVehicleVersionResponse;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.ProfileGroupResponse;
import autokatta.com.view.ManualEnquiryVehicleList;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static autokatta.com.R.id.edtTime;

/**
 * Created by ak-001 on 19/3/17.
 */

public class Title extends Fragment implements View.OnClickListener, RequestNotifier {
    View mTitle;
    ScrollView scrollView1;
    RadioButton radioButton1, radioButton2, storeradioyes, storeradiono, financeyes, financeno, exchangeyes, exchangeno;
    RadioButton mTouristPassing, mPrivatePassing;
    RadioGroup mExchangeradio;
    EditText title;
    TextView mCategory;
    Button mSubmit;
    Dialog mBottomSheetDialog;
    List<String> groupIdList = new ArrayList<>();
    List<String> groupTitleList = new ArrayList<>();
    List<String> storeIdList = new ArrayList<>();
    List<String> storeTitleList = new ArrayList<>();
    String[] groupTitleArray = new String[0];
    String[] groupIdArray = new String[0];
    String[] storeTitleArray = new String[0];
    String[] storeIdArray = new String[0];
    String stringgroupids = "", stringstoreids = "", stringstorename = "", stringgroupname = "";
    String financests = null, exchangests = null;
    Spinner mSubType;
    Spinner mBrandSpinner, mModelSpinner, mVersionSpinner;
    Spinner mPumpSpinner, mBreakSpinner, mStaringSpinner;
    //SubType
    List<String> mSubTypeList = new ArrayList<>();
    List<String> parsedData = new ArrayList<>();
    HashMap<String, Integer> mSubTypeList1 = new HashMap<>();
    //Brands
    List<String> mBrandIdList = new ArrayList<>();
    List<String> brandData = new ArrayList<>();
    HashMap<String, Integer> mBrandList1 = new HashMap<>();
    //Model
    List<String> mModelIdList = new ArrayList<>();
    List<String> modelData = new ArrayList<>();
    HashMap<String, Integer> mModelList1 = new HashMap<>();
    //Version
    List<String> mVersionIdList = new ArrayList<>();
    List<String> versionData = new ArrayList<>();
    HashMap<String, Integer> mVersionList1 = new HashMap<>();
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
    String category, brandName = "", modelName = "", versionName = "",
            subcategoryName, brakeId, brakeName, pumpId, pumpName, uploadauctioncat;

    int categoryId, subcategoryId, brandId, modelId, versionId;
    /*
    Year Fragment...
     */
    private MonthYearPicker myp;
    private MonthYearPicker myp1;
    EditText mMakeMonth, mMakeYear;
    EditText mRegisterMonth, mRegisterYear;
    LinearLayout linearPermit;

    /*
        kms/hrs fragment
     */
    EditText mHrs, mKms;
    ImageView mMakePick, mRegisterPick;

    public Title() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTitle = inflater.inflate(R.layout.fragment_upload_vehicle_title, container, false);
        getActivity().setTitle("Category");
        scrollView1 = (ScrollView) mTitle.findViewById(R.id.scrollView1);
        title = (EditText) mTitle.findViewById(R.id.titleText1);
        mCategory = (TextView) mTitle.findViewById(R.id.categorytext1);
        radioButton1 = (RadioButton) mTitle.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) mTitle.findViewById(R.id.radioButton2);
        storeradioyes = (RadioButton) mTitle.findViewById(R.id.storeradio1);
        storeradiono = (RadioButton) mTitle.findViewById(R.id.storeradio2);

        financeyes = (RadioButton) mTitle.findViewById(R.id.financeYes);
        financeno = (RadioButton) mTitle.findViewById(R.id.financeNo);
        exchangeyes = (RadioButton) mTitle.findViewById(R.id.exchangeYes);
        exchangeno = (RadioButton) mTitle.findViewById(R.id.exchangeNo);

        mExchangeradio = (RadioGroup) mTitle.findViewById(R.id.exchangeradio);

/*Exchange vehicle customer info*/
        mExchangeradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.exchangeYes) {

                    View view = getActivity().getLayoutInflater().inflate(R.layout.custom_upload_exchange, null);

                    final ImageView[] mClose = {(ImageView) view.findViewById(R.id.close)};
                    Button mAdd = (Button) view.findViewById(R.id.submit);
                    final EditText excustomername = (EditText) view.findViewById(R.id.cust_name);
                    final EditText excustcontact = (EditText) view.findViewById(R.id.cust_contact);
                    final EditText excustdescription = (EditText) view.findViewById(R.id.exchange_description);
                    final EditText excustdetailaddress = (EditText) view.findViewById(R.id.cust_detailaddress);
                    final EditText excustfollowupdate = (EditText) view.findViewById(R.id.edtDate);
                    final EditText excustfollowuptime = (EditText) view.findViewById(edtTime);
                    final Spinner excuststatus = (Spinner) view.findViewById(R.id.status);
                    final Spinner inventory = (Spinner) view.findViewById(R.id.inventory);
                    final AutoCompleteTextView excustautoAddress = (AutoCompleteTextView) view.findViewById(R.id.cust_address);
                    final int strPos = excuststatus.getSelectedItemPosition();
                    final int strPos1 = inventory.getSelectedItemPosition();
                    excustautoAddress.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));

                    mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
                    mBottomSheetDialog.setContentView(view);
                    mBottomSheetDialog.setCancelable(true);
                    mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mBottomSheetDialog.show();

                    mClose[0].setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBottomSheetDialog.dismiss();
                        }
                    });

                    excustfollowupdate.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {

                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                excustfollowupdate.setInputType(InputType.TYPE_NULL);
                                excustfollowupdate.setError(null);
                                new SetMyDateAndTime("date", excustfollowupdate, getActivity());

                            }
                            return false;
                        }
                    });

                    excustfollowuptime.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                excustfollowuptime.setInputType(InputType.TYPE_NULL);
                                excustfollowuptime.setError(null);
                                new SetMyDateAndTime("time", excustfollowuptime, getActivity());
                            }
                            return false;
                        }
                    });
                    mAdd.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String custInventoryType = "", custEnquiryStatus = "";
                            Boolean flag = false;
                            if (strPos != 0)
                                custEnquiryStatus = excuststatus.getSelectedItem().toString();

                            if (strPos1 != 0)
                                custEnquiryStatus = inventory.getSelectedItem().toString();


                            String discussion = excustdescription.getText().toString();
                            String custName = excustomername.getText().toString();
                            String custContact = excustcontact.getText().toString();
                            String custFullAddress = excustdetailaddress.getText().toString();
                            String custAddress = excustautoAddress.getText().toString();
                            String nextFollowupDate = excustfollowupdate.getText().toString() + " " + excustfollowuptime.getText().toString();

                            if (!excustautoAddress.getText().toString().isEmpty()) {
                                List<String> resultList = GooglePlacesAdapter.getResultList();
                                for (int i = 0; i < resultList.size(); i++) {
                                    if (excustautoAddress.getText().toString().equalsIgnoreCase(resultList.get(i))) {
                                        flag = true;
                                        break;
                                    } else {
                                        flag = false;
                                    }
                                }
                            }

                            if (custName.equalsIgnoreCase("") || custName.startsWith(" ") && custName.startsWith(" ")) {
                                excustomername.setError("Please provide customer name");
                                excustomername.requestFocus();
                            } else if (custContact.isEmpty() || custContact.startsWith(" ") && custContact.startsWith(" ")) {
                                excustcontact.setError("Please provide customer contact");
                                excustcontact.requestFocus();
                            } else if (custAddress.equals("") || custAddress.startsWith(" ") && custAddress.startsWith(" ")) {
                                excustautoAddress.setError("Enter Address");
                                excustautoAddress.requestFocus();
                            } else if (!flag) {
                                excustautoAddress.setError("Please provide proper address");
                                excustautoAddress.requestFocus();
                            } else if (custFullAddress.equals("")) {
                                excustdetailaddress.setError("Enter detailed address");
                                excustdetailaddress.requestFocus();
                            } else if (discussion.equals("")) {
                                excustdescription.setError("Enter discussion");
                                excustdescription.requestFocus();
                            } else if (excuststatus.getSelectedItemPosition() == 0) {
                                CustomToast.customToast(getActivity(), "Please provide status");
                                excuststatus.requestFocus();
                            } else if (inventory.getSelectedItemPosition() == 0) {
                                CustomToast.customToast(getActivity(), "Please provide inventory Type");
                                excuststatus.requestFocus();
                            } /*else if (discussion.equals("")) {
                    edtDiscussion.setError("Enter discussion data");
                    edtDiscussion.requestFocus();
                }*/ else if (nextFollowupDate.equals("") || nextFollowupDate.startsWith(" ")) {
                                excustfollowupdate.setError("Enter Date");
                                excustfollowupdate.requestFocus();
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("spinnerValue", inventory.getSelectedItem().toString());
                                bundle.putString("custName", custName);
                                bundle.putString("custContact", custContact);
                                bundle.putString("custAddress", custAddress);
                                bundle.putString("custFullAddress", custFullAddress);
                                bundle.putString("custInventoryType", custInventoryType);
                                bundle.putString("custEnquiryStatus", custEnquiryStatus);
                                bundle.putString("discussion", discussion);
                                bundle.putString("nextFollowupDate", nextFollowupDate);
                                bundle.putString("callfrom", "uploadvehicle");

                                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                Intent intent = new Intent(getActivity(), ManualEnquiryVehicleList.class);
                                intent.putExtras(bundle);
                                startActivity(intent, options.toBundle());

                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("ex_cust_name", custName).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("ex_cust_contact", custContact).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("ex_cust_address", custAddress).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("ex_cust_full_addr", custFullAddress).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("inventory_type", custInventoryType).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("enquiry_status", custEnquiryStatus).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("discussion", discussion).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("nextFollowupDate", nextFollowupDate).apply();
                                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("exchange", "yes").apply();

                            }
                        }
                    });
                }
            }
        });

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

        mMakePick = (ImageView) mTitle.findViewById(R.id.make_pick);
        mRegisterPick = (ImageView) mTitle.findViewById(R.id.register_pick);
        mMakeMonth = (EditText) mTitle.findViewById(R.id.make_month);
        mMakeYear = (EditText) mTitle.findViewById(R.id.make_year);
        mRegisterMonth = (EditText) mTitle.findViewById(R.id.register_month);
        mRegisterYear = (EditText) mTitle.findViewById(R.id.register_year);

        mMakeMonth.setInputType(InputType.TYPE_NULL);
        mRegisterMonth.setInputType(InputType.TYPE_NULL);

        mMakePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp.show();
            }
        });
        myp = new MonthYearPicker(getActivity());
        myp.build(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMakeMonth.setText("       " + myp.getSelectedMonthName() + "          " + myp.getSelectedYear());
            }
        }, null);

        mRegisterPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myp1.show();
            }
        });

        myp1 = new MonthYearPicker(getActivity());
        myp1.build(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRegisterMonth.setText("       " + myp1.getSelectedMonthName() + "          " + myp1.getSelectedYear());
            }
        }, null);

        /*
        kms fragment
         */
        mPumpSpinner = (Spinner) mTitle.findViewById(R.id.pumpspinner);
        mBreakSpinner = (Spinner) mTitle.findViewById(R.id.brakespinner);
        mStaringSpinner = (Spinner) mTitle.findViewById(R.id.stearingspinner);
        mHrs = (EditText) mTitle.findViewById(R.id.hrstext1);
        mKms = (EditText) mTitle.findViewById(R.id.kmstext1);

        category = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_categoryName", null);
        categoryId = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getInt("upload_categoryId", 0);
        uploadauctioncat = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("upload_auction_categoryName", null);

        mCategory.setText(category + "->" + uploadauctioncat);

        mSubmit = (Button) mTitle.findViewById(R.id.title_next);
        mSubmit.setOnClickListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getGroup();
                    getStore();
                    getSubCategoryTask();
                    getBreaks();
                    getPumps();

                    radioButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (groupTitleArray.length == 0) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("No Group Found");
                                alertDialog.setMessage("Do you want to create Group...");
                                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        CreateGroupFragment createGroupFragment = new CreateGroupFragment();
                                        Bundle b = new Bundle();
                                        b.putString("classname", "uploadvehicle");
                                        createGroupFragment.setArguments(b);

                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.vehicle_upload_container, createGroupFragment, "Title")
                                                .addToBackStack("Title")
                                                .commit();


                                    }
                                });
                                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        radioButton2.setChecked(true);
                                    }
                                });
                                alertDialog.show();
                            } else {
                                alertBoxToSelectExcelSheet(groupTitleArray);
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
                            alertBoxToSelectStore(storeTitleArray);
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
                /*23-8-17*/
                        mKms.setVisibility(View.VISIBLE);
                        mHrs.setVisibility(View.GONE);
                        mPumpSpinner.setVisibility(View.GONE);
                    } else if (category.equalsIgnoreCase("construction Equipment")) {
                        mVersionSpinner.setVisibility(View.GONE);

                    /*23-8-17*/
                        mKms.setVisibility(View.GONE);
                        mHrs.setVisibility(View.VISIBLE);
                        mPumpSpinner.setVisibility(View.GONE);
                        mBreakSpinner.setVisibility(View.GONE);
                        mStaringSpinner.setVisibility(View.GONE);
                        linearPermit.setVisibility(View.GONE);

                    }

                    /*23-8-17*/
                    else if (category.equalsIgnoreCase("Cranes")) {
                        mKms.setVisibility(View.GONE);
                        mHrs.setVisibility(View.VISIBLE);
                        mPumpSpinner.setVisibility(View.GONE);
                        mBreakSpinner.setVisibility(View.GONE);
                        mStaringSpinner.setVisibility(View.GONE);
                        linearPermit.setVisibility(View.GONE);
                        //note.setText("In Hrs");
                    } else if (category.equalsIgnoreCase("Tractor")) {
                        mKms.setVisibility(View.GONE);
                        mHrs.setVisibility(View.VISIBLE);
                        mPumpSpinner.setVisibility(View.VISIBLE);
                        mBreakSpinner.setVisibility(View.VISIBLE);
                        mStaringSpinner.setVisibility(View.VISIBLE);
                        //note.setText("In Hrs");

                    /*23-8-17*/
                        linearPermit.setVisibility(View.GONE);
                    } else {
                        mKms.setVisibility(View.VISIBLE);
                        mHrs.setVisibility(View.GONE);
                        mPumpSpinner.setVisibility(View.GONE);
                        mBreakSpinner.setVisibility(View.GONE);
                        mStaringSpinner.setVisibility(View.GONE);
                        linearPermit.setVisibility(View.GONE);
                        //note.setText("In Kms");

                    }

                    /*
                    Staring Spinner
                     */
                    mStaringList.clear();
                    mStaringList.add("Select Steering Type");
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
        mApiCall.Groups(getActivity().getSharedPreferences(getString(R.string.my_preference),
                MODE_PRIVATE).getString("loginContact", null), 1, 10);
    }



    /*
    Get store Data...
     */

    private void getStore() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.MyStoreList(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null), 1, 10);
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
    private void getBrand(int categoryId, int subcategoryId) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getBrand(categoryId, subcategoryId);
    }

    /*
    Get Model...
     */
    private void getModel(int categoryId, int subCategoryId, int brandId) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getModel(categoryId, subCategoryId, brandId);
    }

    /*
    Get Version...
     */
    private void getVersion(int categoryId, int subCategoryId, int brandId, int modelId) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVersion(categoryId, subCategoryId, brandId, modelId);
    }


    /*
    Add Brand
     */
    private void AddBrand(String keyword, String title, int categoryId, int subCatID) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addVersionModelBrand(keyword, title, categoryId, subCatID, 0, 0);
    }

    /*
    Add Model
     */
    private void AddModel(String keyword, String title, int categoryId, int subCatID, int brandId) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addVersionModelBrand(keyword, title, categoryId, subCatID, brandId, 0);
    }

    /*
    Add Version
     */
    private void AddVersion(String keyword, String title, int categoryId, int subCatID, int brandId, int modelId) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.addVersionModelBrand(keyword, title, categoryId, subCatID, brandId, modelId);
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
    private void alertBoxToSelectExcelSheet(final String[] groupTitleArray) {
        final List<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        stringgroupids = "";
        stringgroupname = "";

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        // set the dialog title
        builder.setTitle("Select Groups From Following")
                .setCancelable(true)
                .setMultiChoiceItems(groupTitleArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(groupTitleArray[which]);
                        } else if (mSelectedItems.contains(groupTitleArray[which])) {
                            mSelectedItems.remove(groupTitleArray[which]);
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        stringgroupids = "";
                        stringgroupname = "";
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < groupTitleArray.length; j++) {
                                if (mSelectedItems.get(i).equals(groupTitleArray[j])) {
                                    if (stringgroupids.equals("")) {
                                        stringgroupids = groupIdList.get(j);
                                        stringgroupname = groupTitleArray[j];
                                    } else {
                                        stringgroupids = stringgroupids + "," + groupIdList.get(j);
                                        stringgroupname = stringgroupname + "," + groupTitleArray[j];
                                    }
                                }
                            }
                        }


                        if (mSelectedItems.size() == 0) {
                            if (isAdded())
                                CustomToast.customToast(getActivity(), "No Group Was Selected");
                            radioButton1.setChecked(false);
                            radioButton2.setChecked(true);
                            stringgroupids = "";
                            stringgroupname = "";
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(true);
                        stringgroupids = "";
                        stringgroupname = "";
                    }

                })
                .show();
    }

    /*
    Store
     */

    private void alertBoxToSelectStore(final String[] choices) {
        final List<String> mSelectedItems = new ArrayList<>();
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
                        stringstoreids = "";
                        stringstorename = "";

                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < storeTitleArray.length; j++) {
                                if (mSelectedItems.get(i).equals(storeTitleArray[j])) {
                                    if (stringstoreids.equals("")) {
                                        stringstoreids = storeIdList.get(j);
                                        Log.i("IF-stringstoreids", "->" + stringstoreids);
                                        stringstorename = storeTitleArray[j];
                                    } else {
                                        stringstoreids = stringstoreids + "," + storeIdList.get(j);
                                        Log.i("ELSE-stringstoreids", "->" + stringstoreids);
                                        stringstorename = stringstorename + "," + storeTitleArray[j];
                                    }
                                }
                            }
                        }
                        if (mSelectedItems.size() == 0) {
                            if (isAdded())
                                CustomToast.customToast(getActivity(), "No Store Was Selected");
                            storeradioyes.setChecked(false);
                            storeradiono.setChecked(true);
                            stringstoreids = "";
                            stringstorename = "";

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
                    groupIdList.clear();
                    ProfileGroupResponse mProfileGroupResponse = (ProfileGroupResponse) response.body();
                    for (ProfileGroupResponse.MyGroup success : mProfileGroupResponse.getSuccess().getMyGroups()) {
                        groupIdList.add(String.valueOf(success.getId()));
                        groupTitleList.add(success.getTitle());
                    }
                    for (ProfileGroupResponse.JoinedGroup success : mProfileGroupResponse.getSuccess().getJoinedGroups()) {
                        groupIdList.add(String.valueOf(success.getId()));
                        groupTitleList.add(success.getTitle());
                    }

                    groupTitleArray = groupTitleList.toArray(new String[groupTitleList.size()]);
                    groupIdArray = groupIdList.toArray(new String[groupIdList.size()]);
                } else if (response.body() instanceof MyStoreResponse) {
                    Log.e("MyStoreResponse", "->");
                    storeIdList.clear();
                    MyStoreResponse myStoreResponse = (MyStoreResponse) response.body();
                    for (MyStoreResponse.Success success : myStoreResponse.getSuccess()) {
                        storeIdList.add(String.valueOf(success.getId()));
                        storeTitleList.add(success.getName());
                    }
                    Log.i("Data", "storIds -" + storeIdList);
                    storeTitleArray = storeTitleList.toArray(new String[storeTitleList.size()]);
                    storeIdArray = storeIdList.toArray(new String[storeIdList.size()]);
                } else if (response.body() instanceof GetVehicleSubTypeResponse) {
                    Log.e("GetVehicleTypes", "->");
                    mSubTypeList.clear();
                    mSubTypeList1.clear();
                    parsedData.clear();
                    mSubTypeList.add("Select Vehicle Types");
                    GetVehicleSubTypeResponse mGetVehicleSubTypeResponse = (GetVehicleSubTypeResponse) response.body();
                    for (GetVehicleSubTypeResponse.Success subTypeResponse : mGetVehicleSubTypeResponse.getSuccess()) {
                        subTypeResponse.setId(subTypeResponse.getId());
                        subTypeResponse.setName(subTypeResponse.getName());
                        mSubTypeList.add(subTypeResponse.getName());
                        mSubTypeList1.put(subTypeResponse.getName(), subTypeResponse.getId());
                    }
                    parsedData.addAll(mSubTypeList);
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, parsedData);
                        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSubType.setAdapter(adapter);
                    }
                    mSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                subcategoryId = mSubTypeList1.get(parsedData.get(position));
                                subcategoryName = parsedData.get(position);

                                System.out.println("Sub cat is::" + subcategoryId);
                                System.out.println("Sub cat name::" + subcategoryName);

                                getBrand(categoryId, subcategoryId);
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
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, brandData);
                        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mBrandSpinner.setAdapter(adapter);
                        mModelSpinner.setAdapter(null);
                    }
                    mBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0 && !brandData.get(position).equalsIgnoreCase("other")) {
                                brandId = mBrandList1.get(brandData.get(position));
                                brandName = brandData.get(position);

                                System.out.println("Brand id is::" + brandId);
                                System.out.println("Brand name::" + brandName);
                            }

                            if (brandData.get(position).equalsIgnoreCase("other")) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Add Brand");
                                alertDialog.setMessage("Enter brand name");

                                final EditText input = new EditText(getActivity());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                input.setTextColor(Color.BLACK);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

                                alertDialog.setPositiveButton("Add Brand",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                String edbrand = input.getText().toString();

                                                if (edbrand.equals("")) {
                                                    if (isAdded())
                                                        CustomToast.customToast(getActivity(), "Please enter brand");
                                                } else {
                                                    AddBrand("Brand", edbrand, categoryId, subcategoryId);
                                                }

                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBrandSpinner.setSelection(0);
                                                dialog.dismiss();
                                            }
                                        });

                                alertDialog.show();
                            } else
                                getModel(categoryId, subcategoryId, brandId);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (response.body() instanceof GetVehicleModelResponse) {
                    Log.e("GetVehicleModel", "->");
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
                    Log.i("ListModel", "->" + mModelIdList);
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, modelData);
                        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mModelSpinner.setAdapter(adapter);
                        mVersionSpinner.setAdapter(null);
                    }
                    mModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0 && !modelData.get(position).equalsIgnoreCase("other")) {
                                modelId = mModelList1.get(modelData.get(position));
                                modelName = modelData.get(position);

                                System.out.println("Model id is::" + modelId);
                                System.out.println("Model name::" + modelName);
                            }

                            if (modelData.get(position).equalsIgnoreCase("other")) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Add Model");
                                alertDialog.setMessage("Enter model name");

                                final EditText input = new EditText(getActivity());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                input.setTextColor(Color.BLACK);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

                                alertDialog.setPositiveButton("Add Model",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                String edmodel = input.getText().toString();

                                                if (edmodel.equals("")) {
                                                    if (isAdded())
                                                        CustomToast.customToast(getActivity(), "Please enter model");
                                                } else {
                                                    AddModel("Model", edmodel, categoryId, subcategoryId, brandId);
                                                }

                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mModelSpinner.setSelection(0);
                                                dialog.dismiss();
                                            }
                                        });

                                alertDialog.show();
                            } else
                                getVersion(categoryId, subcategoryId, brandId, modelId);

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
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, versionData);
                        //   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mVersionSpinner.setAdapter(adapter);
                    }
                    mVersionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0 && !versionData.get(position).equalsIgnoreCase("other")) {
                                versionId = mVersionList1.get(versionData.get(position));
                                versionName = versionData.get(position);

                                System.out.println("Version id is::" + versionId);
                                System.out.println("Version name::" + versionName);
                            }

                            if (versionData.get(position).equalsIgnoreCase("other")) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Add Version");
                                alertDialog.setMessage("Enter version name");

                                final EditText input = new EditText(getActivity());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                input.setTextColor(Color.BLACK);
                                alertDialog.setView(input);
                                // alertDialog.setIcon(R.drawable.key);

                                alertDialog.setPositiveButton("Add Version",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                String edversion = input.getText().toString();

                                                if (edversion.equals(""))
                                                    Toast.makeText(getActivity(), "Please enter version", Toast.LENGTH_LONG).show();
                                                else
                                                    AddVersion("Version", edversion, categoryId, subcategoryId, brandId, modelId);

                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mVersionSpinner.setSelection(0);
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
                } else if (response.body() instanceof GetBreaks) {
                    Log.e("Get", "Breaks");
                    GetBreaks mGetBreaks = (GetBreaks) response.body();
                    mBreakList.clear();
                    mBreakList1.clear();
                    breakListData.clear();
                    mBreakList.add("Select Break Types");
                    for (GetBreaks.Success success : mGetBreaks.getSuccess()) {
                        success.setId(success.getId());
                        success.setTitle(success.getTitle());
                        mBreakList.add(success.getTitle());
                        mBreakList1.put(success.getTitle(), success.getId());
                    }
                    breakListData.addAll(mBreakList);
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, breakListData);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mBreakSpinner.setAdapter(adapter);
                    }
                    mBreakSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                brakeId = mBreakList1.get(breakListData.get(position));
                                brakeName = breakListData.get(position);

                                System.out.println("Brake id:" + brakeId);
                                System.out.println("Brake name::" + brakeName);
                            }

                            if (breakListData.get(position).equalsIgnoreCase("Other")) {

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

                                                if (otherBreak.equals("")) {
                                                    if (isAdded())
                                                        CustomToast.customToast(getActivity(), "Please enter Brake type");
                                                } else {
                                                    addOtherBreak(otherBreak);
                                                }


                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mBreakSpinner.setSelection(0);
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

                } else if (response.body() instanceof GetPumpResponse) {
                    Log.e("Get", "Pumps");
                    GetPumpResponse mGetPumpResponse = (GetPumpResponse) response.body();
                    mPumpList.clear();
                    mPumpList1.clear();
                    pumpListData.clear();
                    mPumpList.add("Select Pump Types");
                    for (GetPumpResponse.Success success : mGetPumpResponse.getSuccess()) {
                        success.setId(success.getId());
                        success.setTitle(success.getTitle());
                        mPumpList.add(success.getTitle());
                        mPumpList1.put(success.getTitle(), success.getId());
                    }
                    pumpListData.addAll(mPumpList);
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, pumpListData);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mPumpSpinner.setAdapter(adapter);
                    }
                    mPumpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                pumpId = mPumpList1.get(pumpListData.get(position));
                                pumpName = pumpListData.get(position);

                                System.out.println("pump id::" + pumpId);
                                System.out.println("pump name::" + pumpName);
                            }
                            if (pumpListData.get(position).equalsIgnoreCase("Other")) {

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

                                                if (otherPump.equals("")) {
                                                    if (isAdded())
                                                        CustomToast.customToast(getActivity(), "Please enter Brake type");
                                                } else {
                                                    addOtherPump(otherPump);
                                                }


                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                mPumpSpinner.setSelection(0);
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
//                if (isAdded())
//                    CustomToast.customToast(getActivity(), getString(R.string._404));
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
            Log.i("Check Class-", "Title");
        }
    }

    @Override
    public void notifyString(String str) {

        if (str != null) {
            switch (str) {
                case "success_brand_add":
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Brand added successfully");
                    getBrand(categoryId, subcategoryId);
                    Log.i("msg", "Brand added successfully");

                    break;
                case "success_model_add":
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Model added successfully");
                    getModel(categoryId, subcategoryId, brandId);
                    break;
                case "success_version_add":
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Version added successfully");
                    getVersion(categoryId, subcategoryId, brandId, modelId);
                    break;
                case "success_break_add":
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Break added successfully");
                    getBreaks();
                    break;
                case "success_pump_add":
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Pump added successfully");
                    getPumps();
                    break;
            }

        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    /*
    On Click Listener...
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_next:

                String strTitle = "", strGroupPriavcy = "", strStorePrivacy = "", strFinanceStatus = "", strExchangeStatus = "",
                        permit = "", strMakemonth = "", strMakeyear = "", strRegisterMonth = "", strRegisterYear = "",
                        strHrs = "", brandstr = "", modelstr = "", versionstr = "";
                int strKms = 0;
                strTitle = title.getText().toString();
                strMakemonth = myp.getSelectedMonthName();
                strMakeyear = String.valueOf(myp.getSelectedYear());
                strRegisterMonth = myp1.getSelectedMonthName();
                strRegisterYear = String.valueOf(myp1.getSelectedYear());

                int man_monthposition = myp.getSelectedMonthPosition();
                int reg_monthposition = myp1.getSelectedMonthPosition();

                int man_yearposition = myp.getSelectedYearPosition();
                int reg_yearposition = myp1.getSelectedYearPosition();

                if (category.equalsIgnoreCase("Tractor")) {
                    strHrs = mHrs.getText().toString();
                } else {
                    if (mKms.getText().toString().isEmpty())
                        strKms = 0;
                    else
                        strKms = Integer.parseInt(mKms.getText().toString());
                }

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

                try {
                    brandstr = mBrandSpinner.getSelectedItem().toString();
                    modelstr = mModelSpinner.getSelectedItem().toString();
                    versionstr = mVersionSpinner.getSelectedItem().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (strTitle.equals("")) {
                    title.setError("Please provide title");
                    title.requestFocus();
                } else if (category.equalsIgnoreCase("Car") && !mTouristPassing.isChecked() && !mPrivatePassing.isChecked()) {
                    Toast.makeText(getActivity(), "Please select permit type", Toast.LENGTH_SHORT).show();
                } else if (brandstr.startsWith("Select") || modelstr.startsWith("Select") || brandstr.equals("") || modelstr.equals("")) {

                    if (brandstr.startsWith("Select")) {
                        Toast.makeText(getActivity(), "Please select Brand", Toast.LENGTH_SHORT).show();
                    } else if (modelstr.startsWith("Select")) {
                        Toast.makeText(getActivity(), "Please select Model", Toast.LENGTH_SHORT).show();
                    }
                    if (isAdded())
                        CustomToast.customToast(getActivity(), "Brand and Model are compulsory");
                } else if (reg_yearposition < man_yearposition) {
                    Toast.makeText(getActivity(), "Sorry Registration year not valid", Toast.LENGTH_LONG).show();
                } else if (reg_yearposition == man_yearposition && reg_monthposition > man_monthposition) {
                    Toast.makeText(getActivity(), "Sorry Registration Month not valid", Toast.LENGTH_LONG).show();
                } else {

                    Log.i("Data", "GroupIds" + stringgroupids);
                    Log.i("Data", "GroupNames" + stringgroupname);
                    Log.i("Data", "StoreIds" + stringstoreids);
                    Log.i("Data", "StoreNames" + stringstorename);

                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("upload_brandId", brandId).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_brandName", brandstr).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("upload_modelId", modelId).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_modelName", modelstr).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("upload_versionId", versionId).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_versionName", versionstr).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("upload_subCatId", subcategoryId).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_subCatName", subcategoryName).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_brakeId", brakeId).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_brakeName", brakeName).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_pumpId", pumpId).apply();
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_pumpName", pumpName).apply();


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
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putLong("upload_Kms", strKms).apply();


                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction mTransaction = manager.beginTransaction();
                    mTransaction.replace(R.id.vehicle_upload_container, new SubTypeFragment()).addToBackStack("title").commit();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.vehicle_upload_container, new SubTypeFragment(), "title")
                            .addToBackStack("title")
                            .commit();
                }
                break;
        }
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
