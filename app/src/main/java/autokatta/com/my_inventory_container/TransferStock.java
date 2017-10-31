package autokatta.com.my_inventory_container;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_stock);
        setTitle("Transfer Stock Requests");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        listView = (ListView) findViewById(R.id.transfer_list);
        mNoData= (TextView) findViewById(R.id.no_category);
        getTransferData();
    }

    /*
    Get transfer data...
     */
    private void getTransferData() {
        ApiCall apiCall = new ApiCall(this, this);
        apiCall.GetTransferVehicleNotification(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));
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
                        mGetTransferVehicleList.add(vehicles);
                    }
                    GetTransferVehicleListAdapter adapter = new GetTransferVehicleListAdapter(TransferStock.this, mGetTransferVehicleList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else
                {
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                    CustomToast.customToast(TransferStock.this ,getString(R.string.no_response));
            }
        } else {
            CustomToast.customToast(TransferStock.this ,getString(R.string.no_response));
            // CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

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
    }
}
