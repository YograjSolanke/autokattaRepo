package autokatta.com.my_inventory_container;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GetTransferVehicleListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetTransferVehicleNotificationResponse;
import autokatta.com.response.GetTransferVehicleNotificationResponse.Success;
import retrofit2.Response;

public class TransferStock extends AppCompatActivity implements RequestNotifier {

    ListView listView;
    List<Success> mGetTransferVehicleList = new ArrayList<>();
    TextView mNoData;
    String myContact;
    Bundle mBundle;
    int mStoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_stock);
        setTitle("Transfer Stock Requests");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        if (getIntent().getExtras() != null) {
            mBundle = new Bundle();
            mStoreId = getIntent().getExtras().getInt("bundle_storeId", 0);
            myContact = getIntent().getExtras().getString("bundle_contact", myContact);
        }

        listView = (ListView) findViewById(R.id.transfer_list);
        mNoData = (TextView) findViewById(R.id.no_category);
        getTransferData();
    }

    /*
    Get transfer data...
     */
    private void getTransferData() {
        ApiCall apiCall = new ApiCall(this, this);
        apiCall.GetTransferVehicleNotification(myContact, mStoreId);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mNoData.setVisibility(View.GONE);
                GetTransferVehicleNotificationResponse getTransferVehicleNotificationResponse = (GetTransferVehicleNotificationResponse) response.body();
                if (!getTransferVehicleNotificationResponse.getSuccess().isEmpty()) {
                    mGetTransferVehicleList.clear();
                    for (GetTransferVehicleNotificationResponse.Success vehicles : getTransferVehicleNotificationResponse.getSuccess()) {
                        vehicles.setTransferID(vehicles.getTransferID());
                        vehicles.setAddress(vehicles.getAddress());
                        vehicles.setCustomerContact(vehicles.getCustomerContact());
                        vehicles.setDescription(vehicles.getDescription());
                        vehicles.setFullAddress(vehicles.getFullAddress());
                        vehicles.setImage(vehicles.getImage());
                        vehicles.setOldOwnerName(vehicles.getOldOwnerName());
                        vehicles.setTransferReason(vehicles.getTransferReason());
                        vehicles.setVehicleName(vehicles.getVehicleName());
                        vehicles.setVehicleID(vehicles.getVehicleID());
                        String vehicleImage = vehicles.getImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            vehicles.setImage(items[0]);
                        } else {
                            vehicles.setImage(vehicleImage);
                        }


                        mGetTransferVehicleList.add(vehicles);
                    }
                    GetTransferVehicleListAdapter adapter = new GetTransferVehicleListAdapter(TransferStock.this, mGetTransferVehicleList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                mNoData.setVisibility(View.VISIBLE);
                CustomToast.customToast(TransferStock.this, getString(R.string.no_response));
            }
        } else {
            mNoData.setVisibility(View.VISIBLE);
            CustomToast.customToast(TransferStock.this, getString(R.string.no_response));
            // CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

        }
    }

    @Override
    public void notifyError(Throwable error) {
        mNoData.setVisibility(View.VISIBLE);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "TransferStock");
            error.printStackTrace();
        }

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
    }
}
