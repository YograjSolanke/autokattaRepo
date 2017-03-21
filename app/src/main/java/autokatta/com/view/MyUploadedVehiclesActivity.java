package autokatta.com.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUploadedVehiclesResponse;
import retrofit2.Response;

public class MyUploadedVehiclesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    SharedPreferences mSharedPreferences;
    List<MyUploadedVehiclesResponse.Success> myUploadedVehiclesResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploaded_vehicles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiCall = new ApiCall(MyUploadedVehiclesActivity.this, this);
        mSharedPreferences = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        final String myContact = mSharedPreferences.getString("loginContact", "7841023392");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutMyUploadedVehicle);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerMyUploadedVehicle);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                apiCall.MyUploadedVehicles(myContact);
            }
        });

    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                MyUploadedVehiclesResponse myVehicleResponse = (MyUploadedVehiclesResponse) response.body();
                if (!myVehicleResponse.getSuccess().isEmpty()) {
                    for (MyUploadedVehiclesResponse.Success myVehicleSuccess : myVehicleResponse.getSuccess()) {

                        myVehicleSuccess.setVehicleId(myVehicleSuccess.getVehicleId());
                        myVehicleSuccess.setTitle(myVehicleSuccess.getTitle());
                        myVehicleSuccess.setPrice(myVehicleSuccess.getPrice());
                        myVehicleSuccess.setCategory(myVehicleSuccess.getCategory());
                        myVehicleSuccess.setModel(myVehicleSuccess.getModel());
                        myVehicleSuccess.setManufacturer(myVehicleSuccess.getManufacturer());
                        myVehicleSuccess.setBuyerLeads(myVehicleSuccess.getBuyerLeads());
                        myVehicleSuccess.setNotificationstatus(myVehicleSuccess.getNotificationstatus());
                        myVehicleSuccess.setImages(myVehicleSuccess.getImages());
                        myVehicleSuccess.setDate(myVehicleSuccess.getDate());
                        myVehicleSuccess.setYearOfManufacture(myVehicleSuccess.getYearOfManufacture());
                        myVehicleSuccess.setKmsRunning(myVehicleSuccess.getKmsRunning());
                        myVehicleSuccess.setHrsRunning(myVehicleSuccess.getHrsRunning());
                        myVehicleSuccess.setRtoCity(myVehicleSuccess.getRtoCity());
                        myVehicleSuccess.setLocationCity(myVehicleSuccess.getLocationCity());
                        myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());

                        myUploadedVehiclesResponseList.add(myVehicleSuccess);
                    }
                    Log.i("size", String.valueOf(myUploadedVehiclesResponseList.size()));
                    mSwipeRefreshLayout.setRefreshing(false);

                }

            } else {
                CustomToast.customToast(getApplicationContext(), getString(R.string._404));
            }

        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "MyUploadedVehiclesActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
