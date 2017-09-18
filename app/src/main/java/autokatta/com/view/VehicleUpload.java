package autokatta.com.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.app_info.UploadVehicleAppIntro;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.upload_vehicle.VehicleList;
import retrofit2.Response;

public class VehicleUpload extends AppCompatActivity implements RequestNotifier {

    SharedPreferences sharedPreferences = null;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Upload Vehicle");
        getStore();
        sharedPreferences = getSharedPreferences(getString(R.string.firstRun), MODE_PRIVATE);
        startActivity(new Intent(getApplicationContext(), UploadVehicleAppIntro.class));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.vehicle_upload_container, new VehicleList(), "VehicleUpload")
                .addToBackStack("VehicleUpload")
                .commit();
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

    /*
    Get store Data...
     */

    private void getStore() {
        ApiCall mApiCall = new ApiCall(VehicleUpload.this, this);
        mApiCall.MyStoreList(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                MyStoreResponse myStoreResponse = (MyStoreResponse) response.body();
                if (!myStoreResponse.getSuccess().isEmpty()) {
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("isTrue", "yes").apply();
                } else {
                    getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("isTrue", "no").apply();
                }
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "VehicleUpload");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

}

