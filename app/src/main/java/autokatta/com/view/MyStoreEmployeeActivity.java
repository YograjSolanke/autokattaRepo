package autokatta.com.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreEmployeeResponse;
import retrofit2.Response;

public class MyStoreEmployeeActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {

    ApiCall mApiCall;
    String myContact;
    FloatingActionButton fab;
    int store_id;
    private ProgressDialog dialog;
    List<StoreEmployeeResponse.Success.Employee> employees = new ArrayList<>();
    ConnectionDetector mTestConnection;
    LinearLayoutManager mLinearLayoutManager;
    TextView mNoData;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Add Employee");

        mApiCall = new ApiCall(MyStoreEmployeeActivity.this, this);
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        fab = (FloatingActionButton) findViewById(R.id.fab);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                if (getIntent().getExtras() != null) {
                    store_id = getIntent().getExtras().getInt("store_id", 0);

                    System.out.println("output=" + store_id);
                }

                mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutMyStoreList);
                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mRecyclerView.setHasFixedSize(true);
                mLinearLayoutManager = new LinearLayoutManager(MyStoreEmployeeActivity.this);
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        // mApiCall.MyStoreList(myContact, 1, 10);
                    }
                });


            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof StoreEmployeeResponse) {
                    dialog.dismiss();
                    employees.clear();
                    StoreEmployeeResponse.Success Response = (StoreEmployeeResponse.Success) response.body();
                    for (StoreEmployeeResponse.Success.Employee success : Response.getEmployee()) {
                        success.setStoreEmplyeeID(success.getStoreEmplyeeID());
                        success.setName(success.getName());
                        success.setContactNo(success.getContactNo());
                        success.setStatus(success.getStatus());
                        success.setPermission(success.getPermission());
                        success.setDescription(success.getDescription());
                        success.setDesignation(success.getDesignation());
                        success.setStoreID(success.getStoreID());

                        employees.add(success);


                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                // CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            // CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "MyStoreEmployeeActivity");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
