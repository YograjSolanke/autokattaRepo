package autokatta.com.view;

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
import autokatta.com.adapter.TransferEnquiryAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetTransferEnquiryRequestResponse;
import retrofit2.Response;

/**
 * Created by ak-005 on 17/11/17.
 */

public class TransferEnquiryRequestActivity extends AppCompatActivity implements RequestNotifier{
    ListView listView;
    List<GetTransferEnquiryRequestResponse.Success.Employee> mGetTransferEnquryList = new ArrayList<>();
    TextView mNoData;
    String myContact;
    Bundle mBundle;
    int mStoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_transfer_request_activity);
        setTitle("Transfer Enquiries Requests");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        listView = (ListView) findViewById(R.id.transfer_list);
        mNoData = (TextView) findViewById(R.id.no_category);
        getTransferData();
    }

    /*
    Get transfer data...
     */
    private void getTransferData() {
        ApiCall apiCall = new ApiCall(this, this);
        apiCall.getTransferEnquryRequests(myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mNoData.setVisibility(View.GONE);
                GetTransferEnquiryRequestResponse getTransferVehicleNotificationResponse = (GetTransferEnquiryRequestResponse) response.body();
                if (!getTransferVehicleNotificationResponse.getSuccess().getEmployee().isEmpty()) {
                    mGetTransferEnquryList.clear();
                    for (GetTransferEnquiryRequestResponse.Success.Employee vehicles : getTransferVehicleNotificationResponse.getSuccess().getEmployee()) {
                               vehicles.setDate(vehicles.getDate());
                               vehicles.setDescription(vehicles.getDescription());
                               vehicles.setEnquiryContact(vehicles.getEnquiryContact());
                               vehicles.setEnquiryID(vehicles.getEnquiryID());
                               vehicles.setPSVNID(vehicles.getPSVNID());
                               vehicles.setReasonForTransfer(vehicles.getReasonForTransfer());
                               vehicles.setTransferToName(vehicles.getTransferToName());
                               vehicles.setTransferEnquiryID(vehicles.getTransferEnquiryID());


                        mGetTransferEnquryList.add(vehicles);
                    }
                    TransferEnquiryAdapter adapter = new TransferEnquiryAdapter(TransferEnquiryRequestActivity.this, mGetTransferEnquryList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                mNoData.setVisibility(View.VISIBLE);
                CustomToast.customToast(TransferEnquiryRequestActivity.this, getString(R.string.no_response));
            }
        } else {
            mNoData.setVisibility(View.VISIBLE);
            CustomToast.customToast(TransferEnquiryRequestActivity.this, getString(R.string.no_response));
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
