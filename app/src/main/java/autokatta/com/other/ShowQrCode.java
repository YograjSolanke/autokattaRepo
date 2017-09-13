package autokatta.com.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.ShowQrAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.GetStoreProfileInfoResponse;
import retrofit2.Response;

public class ShowQrCode extends AppCompatActivity implements RequestNotifier {

    ConnectionDetector mTestConnection;
    List<GetStoreProfileInfoResponse.Success> mSuccesses = new ArrayList<>();
    ListView mListView;
    ShowQrAdapter myStoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr_code);
        setTitle("My Store QR");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mTestConnection = new ConnectionDetector(ShowQrCode.this);
        mListView = (ListView) findViewById(R.id.store_qr_list);
        /*ApiCall apiCall = new ApiCall(this, this);
        apiCall.getStoreQrCode(getSharedPreferences(getString(R.string.my_preference),MODE_PRIVATE).getString("loginContact",""));*/
        getStoreProfileInfo(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
    }

    /*
    Get Store Profile Info...
     */
    private void getStoreProfileInfo(String loginContact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(ShowQrCode.this, this);
            mApiCall.getStoreProfileInfo(loginContact);
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetStoreProfileInfoResponse mStoreProfileInfoResponse = (GetStoreProfileInfoResponse) response.body();
                if (!mStoreProfileInfoResponse.getSuccess().isEmpty()) {
                    //mNoData.setVisibility(View.GONE);
                    mSuccesses.clear();
                    for (GetStoreProfileInfoResponse.Success infoResponse : mStoreProfileInfoResponse.getSuccess()) {
                        infoResponse.setStoreId(infoResponse.getStoreId());
                        infoResponse.setStoreName(infoResponse.getStoreName());
                        infoResponse.setLocation(infoResponse.getLocation());
                        infoResponse.setStoreLogo(infoResponse.getStoreLogo());
                        infoResponse.setStoreType(infoResponse.getStoreType());
                        mSuccesses.add(infoResponse);
                    }
                    myStoreAdapter = new ShowQrAdapter(getApplicationContext(), mSuccesses);
                    mListView.setAdapter(myStoreAdapter);
                    myStoreAdapter.notifyDataSetChanged();
                } else {
                    //mNoData.setVisibility(View.VISIBLE);
                    //CustomToast.customToast(getActivity(), "No Store found");
                }
            } else {
//                if (mActivity != null)
                //CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
//            if (mActivity != null)
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
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
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
