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
import android.widget.Toast;

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
    String discussion, nextFollowupDate;
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

        setTitle("Select Vehicle");
        mListView = (ListView) findViewById(R.id.vehicle_list);
        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            getMyInventoryData(getIntent().getExtras().getString("spinnerValue"));

            custName = getIntent().getExtras().getString("custName");
            custContact = getIntent().getExtras().getString("custContact");
            custAddress = getIntent().getExtras().getString("custAddress");
            custFullAddress = getIntent().getExtras().getString("custFullAddress");
            custInventoryType = getIntent().getExtras().getString("spinnerValue");
          //  custInventoryType = getIntent().getExtras().getString("custInventoryType");
            custEnquiryStatus = getIntent().getExtras().getString("custEnquiryStatus");
            discussion = getIntent().getExtras().getString("discussion");
            nextFollowupDate = getIntent().getExtras().getString("nextFollowupDate");
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
                            if (success.getInventoryType().equals("UsedVehicle")) {
                                success.setVehicleId(success.getVehicleId());
                                success.setTitle(success.getTitle());
                                success.setCategory(success.getCategory());
                                success.setSubCategory(success.getSubCategory());
                                success.setModel(success.getModel());
                                mInventoryType = success.getInventoryType();
                                Log.i("used", "->" + mInventoryType);
                                mItemList.add(success);
                            } else if (success.getInventoryType().equals("New Vehicle")) {
                            } else if (success.getInventoryType().equals("Products")) {
                                success.setId(success.getId());
                                success.setProductName(success.getProductName());
                                success.setProductType(success.getProductType());
                                success.setCategory(success.getCategory());
                                mInventoryType = success.getInventoryType();
                                Log.i("product", "->" + mInventoryType);
                                mItemList.add(success);
                            } else if (success.getInventoryType().equals("Services")) {
                                success.setId(success.getId());
                                success.setName(success.getName());
                                success.setType(success.getType());
                                success.setCategory(success.getCategory());
                                mInventoryType = success.getInventoryType();
                                Log.i("service", "->" + mInventoryType);
                                mItemList.add(success);
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
                                if (getIntent().getExtras().getString("spinnerValue").equals("New Vehicle")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No New Vehicle Found");
                                    alertDialog.setMessage("Do you want to add New Vehicle...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(ManualEnquiryVehicleList.this, NewVehicleCatalogActivity.class));
                                        }
                                    });
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                } else if (getIntent().getExtras().getString("spinnerValue").equals("Used Vehicle")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No Used Vehicle Found");
                                    alertDialog.setMessage("Do you want to add Used Vehicle...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(ManualEnquiryVehicleList.this, "used", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                } else if (getIntent().getExtras().getString("spinnerValue").equals("Products")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No Products Found");
                                    alertDialog.setMessage("Do you want to add Products...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(ManualEnquiryVehicleList.this, "products", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                } else if (getIntent().getExtras().getString("spinnerValue").equals("Services")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManualEnquiryVehicleList.this);
                                    alertDialog.setTitle("No Services Found");
                                    alertDialog.setMessage("Do you want to add Services...?");
                                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(ManualEnquiryVehicleList.this, "services", Toast.LENGTH_SHORT).show();
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

                if (!addArray.equals("")) {
                    AddEnquiryData(custName, custContact, custAddress, custFullAddress, custInventoryType, custEnquiryStatus,
                            discussion, nextFollowupDate, addArray);
                } else {
                    Toast.makeText(this, "Please add your Inventory...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void AddEnquiryData(String custName, String custContact, String custAddress, String custFullAddress,
                                String custInventoryType, String custEnquiryStatus, String discussion,
                                String nextFollowupDate, String addArray) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.addManualEnquiryData(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "")
                , custName, custContact, custAddress, custFullAddress, custInventoryType,
                custEnquiryStatus, discussion, nextFollowupDate, addArray);
    }
}
