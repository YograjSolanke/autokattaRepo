package autokatta.com.my_inventory_container;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.TransferListResponse;
import retrofit2.Response;

public class TransferStock extends AppCompatActivity implements RequestNotifier {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_stock);
        listView = (ListView) findViewById(R.id.transfer_list);
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
                TransferListResponse listResponse = (TransferListResponse) response.body();
                if (!listResponse.getSuccess().isEmpty()) {
                    for (TransferListResponse.Success success : listResponse.getSuccess()) {

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
}
