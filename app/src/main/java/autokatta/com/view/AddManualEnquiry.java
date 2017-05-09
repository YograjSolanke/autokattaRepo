package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.adapter.InventoryAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AddManualEnquiryResponse;
import autokatta.com.response.GetInventoryResponse;
import retrofit2.Response;

public class AddManualEnquiry extends AppCompatActivity implements RequestNotifier, View.OnTouchListener {

    EditText edtName, edtContact, edtAddress, edtDiscussion, edtDate, edtTime;
    AutoCompleteTextView autoAddress;
    Spinner spnInventory, spnStatus;
    String myContact;
    TextView txtUser;
    RelativeLayout mRelative;
    List<GetInventoryResponse.Success> mItemList = new ArrayList<>();
    List<String> arrayList = new ArrayList<>();
    InventoryAdapter adapter;
    ListView mListView;
    String addArray = "";
    android.support.v4.widget.NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manual_enquiry);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRelative = (RelativeLayout) findViewById(R.id.add_manual);
                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                edtName = (EditText) findViewById(R.id.edtName);
                edtContact = (EditText) findViewById(R.id.edtContact);
                edtAddress = (EditText) findViewById(R.id.edtAddress);
                edtDiscussion = (EditText) findViewById(R.id.edtDiscussion);
                autoAddress = (AutoCompleteTextView) findViewById(R.id.autoAddress);
                spnInventory = (Spinner) findViewById(R.id.spnInventory);
                spnStatus = (Spinner) findViewById(R.id.spnStatus);
                edtDate = (EditText) findViewById(R.id.edtDate);
                edtTime = (EditText) findViewById(R.id.edtTime);
                txtUser = (TextView) findViewById(R.id.txtUser);
                mListView = (ListView) findViewById(R.id.vehicle_list);
                scrollView = (NestedScrollView) findViewById(R.id.scroll_view);

                edtDate.setOnTouchListener(AddManualEnquiry.this);
                edtTime.setOnTouchListener(AddManualEnquiry.this);
                autoAddress.setAdapter(new GooglePlacesAdapter(AddManualEnquiry.this, R.layout.registration_spinner));

                edtContact.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        txtUser.setVisibility(View.GONE);
                        if (s.length() == 10) {
                            if (!myContact.equalsIgnoreCase(s.toString()))
                                checkUser(s.toString());
                            else {
                                edtContact.requestFocus();
                                edtContact.setError("admin not allowed");
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        txtUser.setVisibility(View.GONE);
                    }
                });

                txtUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.left_to_right, R.anim.right_to_left);
                        Bundle bundle = new Bundle();
                        bundle.putString("contactOtherProfile", edtContact.getText().toString());
                        Intent intent = new Intent(AddManualEnquiry.this, OtherProfile.class);
                        intent.putExtras(bundle);
                        startActivity(intent, options.toBundle());
                    }
                });

                spnInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            String str = spnInventory.getSelectedItem().toString();
                            getMyInventoryData(str);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    private void checkUser(String contact) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.registrationContactValidation(contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_manual_enquiry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:
                if (mListView.getVisibility() == View.VISIBLE) {
                    mListView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                } else {
                    onBackPressed();
                }
                break;

            case R.id.ok_manual:
                String custInventoryType = "", custEnquiryStatus = "";
                Boolean flag = false;
                int strPos = spnInventory.getSelectedItemPosition();
                int strPos1 = spnStatus.getSelectedItemPosition();
                String custName = edtName.getText().toString();
                String custContact = edtContact.getText().toString();
                String custAddress = autoAddress.getText().toString();
                String custFullAddress = edtAddress.getText().toString();

                if (strPos != 0) {
                    custInventoryType = spnInventory.getSelectedItem().toString();
                }
                if (strPos1 != 0)
                    custEnquiryStatus = spnStatus.getSelectedItem().toString();

                String discussion = edtDiscussion.getText().toString();
                String nextFollowupDate = edtDate.getText().toString() + " " + edtTime.getText().toString();

                if (!custAddress.isEmpty()) {
                    List<String> resultList = GooglePlacesAdapter.getResultList();
                    for (int i = 0; i < resultList.size(); i++) {
                        if (custAddress.equalsIgnoreCase(resultList.get(i))) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                }

                if (arrayList != null) {
                    arrayList = adapter.getInventoryList();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (!arrayList.get(i).equals("0")) {
                            if (addArray.equals("")) {
                                addArray = arrayList.get(i);
                            } else {
                                addArray = addArray + "," + arrayList.get(i);
                                Log.i("addArray", "->" + addArray);
                            }
                        }
                    }
                }

                if (custName.equalsIgnoreCase("")) {
                    edtName.setError("Please provide customer name");
                    edtName.requestFocus();
                } else if (custContact.isEmpty()) {
                    edtContact.setError("Please provide customer contact");
                    edtContact.requestFocus();
                } else if (custAddress.equals("")) {
                    autoAddress.setError("Enter Address");
                    autoAddress.requestFocus();
                } else if (!flag) {
                    autoAddress.setError("Please provide proper address");
                    autoAddress.requestFocus();
                } else if (custFullAddress.equals("")) {
                    edtAddress.setError("Enter detailed address");
                    edtAddress.requestFocus();
                } else if (spnInventory.getSelectedItemPosition() == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please provide inventory");
                    spnInventory.requestFocus();
                } else if (spnStatus.getSelectedItemPosition() == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please provide status");
                    spnStatus.requestFocus();
                } else if (discussion.equals("")) {
                    edtDiscussion.setError("Enter discussion data");
                    edtDiscussion.requestFocus();
                } else if (nextFollowupDate.equals("") || nextFollowupDate.startsWith(" ")) {
                    edtDate.setError("Enter Date");
                    edtDate.requestFocus();
                } else {
                    AddEnquiryData(custName, custContact, custAddress, custFullAddress, custInventoryType, custEnquiryStatus,
                            discussion, nextFollowupDate, addArray);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Inventory Type...
     */
    private void getMyInventoryData(String inventoryType) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getMyInventoryData(myContact, inventoryType);
    }

    private void AddEnquiryData(String custName, String custContact, String custAddress, String custFullAddress,
                                String custInventoryType, String custEnquiryStatus, String discussion,
                                String nextFollowupDate, String addArray) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addManualEnquiryData(myContact, custName, custContact, custAddress, custFullAddress, custInventoryType,
                custEnquiryStatus, discussion, nextFollowupDate, addArray);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (view.getId()) {

            case (R.id.edtDate):
                if (action == MotionEvent.ACTION_DOWN) {
                    edtDate.setInputType(InputType.TYPE_NULL);
                    edtDate.setError(null);
                    new SetMyDateAndTime("date", edtDate, this);
                }
                break;

            case (R.id.edtTime):

                if (action == MotionEvent.ACTION_DOWN) {
                    edtTime.setInputType(InputType.TYPE_NULL);
                    edtTime.setError(null);
                    new SetMyDateAndTime("time", edtTime, this);
                }
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.left_to_right, R.anim.right_to_left);
            startActivity(new Intent(getApplicationContext(), ManualEnquiry.class), options.toBundle());
            finish();
        } else {
            finish();
            startActivity(new Intent(getApplicationContext(), ManualEnquiry.class));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof AddManualEnquiryResponse) {
                    AddManualEnquiryResponse enquiryResponse = (AddManualEnquiryResponse) response.body();
                    if (enquiryResponse.getSuccess() != null) {
                        if (enquiryResponse.getSuccess().getMessage().equalsIgnoreCase("Data successfully Inserted.")) {
                            Snackbar.make(mRelative, enquiryResponse.getSuccess().getMessage(), Snackbar.LENGTH_SHORT).show();
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(AddManualEnquiry.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                            startActivity(new Intent(getApplicationContext(), ManualEnquiry.class), options.toBundle());
                            finish();
                        }
                    } else {
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                    }
                } else if (response.body() instanceof GetInventoryResponse) {
                    GetInventoryResponse mInventoryResponse = (GetInventoryResponse) response.body();
                    if (mInventoryResponse.getSuccess() != null) {
                        scrollView.setVisibility(View.GONE);
                        mListView.setVisibility(View.VISIBLE);
                        for (GetInventoryResponse.Success success : mInventoryResponse.getSuccess()) {
                            if (success.getInventoryType().equals("UsedVehicle")) {
                                success.setVehicleId(success.getVehicleId());
                                success.setTitle(success.getTitle());
                                success.setCategory(success.getCategory());
                                success.setSubCategory(success.getSubCategory());
                                success.setModel(success.getModel());
                                mItemList.add(success);
                            }
                        }
                        adapter = new InventoryAdapter(AddManualEnquiry.this, mItemList, "Used Vehicle");
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
            } else {
                Snackbar.make(mRelative, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mRelative, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "Add Manual Enquiry");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("Success")) {
                txtUser.setVisibility(View.VISIBLE);
            }
        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
    }
}
