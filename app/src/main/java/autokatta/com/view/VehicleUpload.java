package autokatta.com.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.adapter.GetVehicleListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleListResponse;
import retrofit2.Response;

public class VehicleUpload extends AppCompatActivity implements RequestNotifier {

    ListView mListView;
    List<GetVehicleListResponse.Success> mGetVehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mGetVehicle = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.upload_list);
        getData();
    }

    private void getData() {
        ApiCall mApiCall = new ApiCall(VehicleUpload.this, this);
        mApiCall.getVehicleCount("7841023392");
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                if (!mGetVehicleListResponse.getSuccess().isEmpty()){
                    for (GetVehicleListResponse.Success mSuccess: mGetVehicleListResponse.getSuccess()){
                            mSuccess.setId(mSuccess.getId());
                            mSuccess.setName(mSuccess.getName());
                        mGetVehicle.add(mSuccess);
                    }
                    GetVehicleListAdapter mGetVehicleListAdapter = new GetVehicleListAdapter(VehicleUpload.this,mGetVehicle);
                    mListView.setAdapter(mGetVehicleListAdapter);
                    mGetVehicleListAdapter.notifyDataSetChanged();
                }
            }else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }
        }else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
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
            Log.i("Check Class-", "Login Activity");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(VehicleUpload.this);
            alertDialog.setTitle("Upload Vehicle");
            alertDialog.setMessage("You already uploaded" + str + " vehicles. you want to upload another vehicle?");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    getVehicleList();
                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(VehicleUpload.this, AutokattaMainActivity.class));
                    dialog.cancel();
                    finish();
                }
            });
            alertDialog.show();
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    /*
    Get Vehicle List...
     */
    private void getVehicleList() {
        ApiCall mApiCall = new ApiCall(VehicleUpload.this, this);
        mApiCall.getVehicleList();
    }
}
