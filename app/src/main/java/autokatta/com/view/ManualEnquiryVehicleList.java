package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.InventoryAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AddManualEnquiryResponse;
import autokatta.com.response.GetInventoryResponse;
import retrofit2.Response;

public class ManualEnquiryVehicleList extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    List<GetInventoryResponse.Success> mItemList = new ArrayList<>();
    InventoryAdapter adapter;
    String mInventoryType, addArray = "";
    ListView mListView;
    String custName, custContact, custAddress, custFullAddress, custInventoryType = "", custEnquiryStatus = "";
    String discussion, nextFollowupDate, callfrom,mSource,mFinancerName,mFinanceStatus;
    int mLoanAmt;
    float mLoanPercent;
    Button mSubmit;
    List<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_enquiry_vehicle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        mListView = (ListView) findViewById(R.id.vehicle_list);
        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            getMyInventoryData(getIntent().getExtras().getString("spinnerValue"));
            setTitle("Select " + getIntent().getExtras().getString("spinnerValue"));
            custName = getIntent().getExtras().getString("custName");
            custContact = getIntent().getExtras().getString("custContact");
            custAddress = getIntent().getExtras().getString("custAddress");
            custFullAddress = getIntent().getExtras().getString("custFullAddress");
            custInventoryType = getIntent().getExtras().getString("spinnerValue");
            //  custInventoryType = getIntent().getExtras().getString("custInventoryType");
            custEnquiryStatus = getIntent().getExtras().getString("custEnquiryStatus");
            discussion = getIntent().getExtras().getString("discussion");
            nextFollowupDate = getIntent().getExtras().getString("nextFollowupDate");
            callfrom = getIntent().getExtras().getString("callfrom", "");
            mSource = getIntent().getExtras().getString("source", "");
            mLoanPercent = getIntent().getExtras().getFloat("loanpercent",0);
            mLoanAmt = getIntent().getExtras().getInt("loanamt", 0);
            mFinancerName = getIntent().getExtras().getString("financername", "");
            mFinanceStatus = getIntent().getExtras().getString("financerstatus", "");
        }
    }

    /*
    Inventory Type...
     */
    private void getMyInventoryData(String inventoryType) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getMyInventoryData(getSharedPreferences(getString(R.string.my_preference),
                MODE_PRIVATE).getString("loginContact", ""), inventoryType);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof AddManualEnquiryResponse) {
                    AddManualEnquiryResponse enquiryResponse = (AddManualEnquiryResponse) response.body();
                    if (enquiryResponse.getSuccess() != null) {
                        if (enquiryResponse.getSuccess().getSuccess().equalsIgnoreCase("Data successfully Inserted.")) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                            alertDialog.setTitle("Add Enquiry");
                            alertDialog.setMessage("Do you want to add another enquiry...?");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent i = new Intent(getApplicationContext(), AddManualEnquiry.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                            });
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityOptions options = ActivityOptions.makeCustomAnimation(ManualEnquiryVehicleList.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                                    Intent intent = new Intent(getApplicationContext(), ManualEnquiry.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent, options.toBundle());
                                    finish();
                                }
                            });
                            alertDialog.show();
                        }
                    } else {
                        CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
                    }
                } else if (response.body() instanceof GetInventoryResponse) {
                    GetInventoryResponse mInventoryResponse = (GetInventoryResponse) response.body();
                    if (!mInventoryResponse.getSuccess().isEmpty()) {
                        mItemList.clear();
                        for (GetInventoryResponse.Success success : mInventoryResponse.getSuccess()) {
                            switch (success.getInventoryType()) {
                                case "UsedVehicle":
                                    success.setVehicleId(success.getVehicleId());
                                    success.setTitle(success.getTitle());
                                    success.setCategory(success.getCategory());
                                    success.setSubCategory(success.getSubCategory());
                                    success.setModel(success.getModel());
                                    mInventoryType = success.getInventoryType();
                                    Log.i("used", "->" + mInventoryType);
                                    mItemList.add(success);
                                    break;
                                case "New Vehicle":
                                    success.setNewVehicleID(success.getNewVehicleID());
                                    success.setTitle("new vehicle");
                                    success.setCategory(success.getCategoryName());
                                    success.setSubCategory(success.getSubCategoryName());
                                    success.setModel(success.getModelName());
                                    mInventoryType = success.getInventoryType();
                                    Log.i("new", "->" + mInventoryType);
                                    mItemList.add(success);
                                    break;
                                case "Products":
                                    success.setId(success.getId());
                                    success.setProductName(success.getProductName());
                                    success.setProductType(success.getProductType());
                                    success.setCategory(success.getCategory());
                                    mInventoryType = success.getInventoryType();
                                    Log.i("product", "->" + mInventoryType);
                                    mItemList.add(success);
                                    break;
                                case "Services":
                                    success.setId(success.getId());
                                    success.setName(success.getName());
                                    success.setType(success.getType());
                                    success.setCategory(success.getCategory());
                                    mInventoryType = success.getInventoryType();
                                    Log.i("service", "->" + mInventoryType);
                                    mItemList.add(success);
                                    break;
                            }
                        }
                        Log.i("Inventory", "->" + mInventoryType);

                        if (mItemList.size() != 0) {
                            //mNoData.setVisibility(View.GONE);
                            adapter = new InventoryAdapter(ManualEnquiryVehicleList.this, mItemList, mInventoryType);
                            mListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            // menu.findItem(R.id.ok_manual).setVisible(true);

                        } else {
                            // mNoData.setVisibility(View.VISIBLE);
                            // menu.findItem(R.id.ok_manual).setVisible(false);
                        }
                    } else {
                        //Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
                        try {
                            if (getIntent().getExtras().getString("spinnerValue") != null) {
                                if (getIntent().getExtras().getString("spinnerValue", "").equals("New Vehicle")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No New Vehicle Found");
                                    alertDialog.setMessage("Do you want to add New Vehicle...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(ManualEnquiryVehicleList.this, AddNewVehicleActivity.class));
                                        }
                                    });
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                } else if (getIntent().getExtras().getString("spinnerValue", "").equals("Used Vehicle")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No Used Vehicle Found");
                                    alertDialog.setMessage("Do you want to add Used Vehicle...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(ManualEnquiryVehicleList.this, VehicleUpload.class));
                                        }
                                    });
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                } else if (getIntent().getExtras().getString("spinnerValue", "").equals("Products")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No Products Found");
                                    alertDialog.setMessage("add Products in particular store...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(ManualEnquiryVehicleList.this, MyStoreListActivity.class));
                                        }
                                    });
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                } else if (getIntent().getExtras().getString("spinnerValue", "").equals("Services")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No Services Found");
                                    alertDialog.setMessage("add Services in particular store...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(ManualEnquiryVehicleList.this, MyStoreListActivity.class));
                                        }
                                    });
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:

                arrayList = adapter.getInventoryList();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (!arrayList.get(i).equals("0")) {
                        if (addArray.equals("")) {
                            addArray = arrayList.get(i);
                        } else {
                            addArray = addArray + "," + arrayList.get(i);
                        }
                    }
                }

                /*if (!callfrom.equalsIgnoreCase("uploadvehicle")) {*/
                if (!addArray.equals("")) {
                    AddEnquiryData(custName, custContact, custAddress, custFullAddress, custInventoryType, custEnquiryStatus,
                            discussion, nextFollowupDate, addArray);
                } else {
                    CustomToast.customToast(this, "Please Select Atleast One...");
                }
               /* }else {
                    CustomToast.customToast(getApplicationContext(),"Enquiry For Exchange Will Be Added");
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("Ids_for_manual_enquiry", addArray).apply();
                    finish();
                }*/
                break;
        }
    }

    private void AddEnquiryData(String custName, String custContact, String custAddress, String custFullAddress,
                                String custInventoryType, String custEnquiryStatus, String discussion,
                                String nextFollowupDate, String addArray) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addManualEnquiryData(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "")
                , custName, custContact, custAddress, custFullAddress, custInventoryType,
                custEnquiryStatus, discussion, nextFollowupDate, addArray,mSource,mFinancerName,mLoanAmt,mLoanPercent,mFinanceStatus);
    }
}
