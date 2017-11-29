package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.EditDesignationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetDesignationResponse;
import retrofit2.Response;

public class EditDesignation extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    final List<GetDesignationResponse.Success> mDesigList = new ArrayList<>();
    EditDesignationAdapter mAdapter;
    ApiCall mApiCall;
    Menu menu;
    Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_designation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mApiCall = new ApiCall(EditDesignation.this, this);
        mApiCall.getDesignation();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBGroup);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerBGroup);
        mAdd = (Button) findViewById(R.id.add);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(EditDesignation.this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mApiCall.getDesignation();
            }
        });
    }


    @Override
    public void onRefresh() {
        mApiCall.getDesignation();
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mDesigList.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                GetDesignationResponse mGetCompanyList = (GetDesignationResponse) response.body();
                if (!mGetCompanyList.getSuccess().isEmpty()) {
                    for (GetDesignationResponse.Success designationResponse : mGetCompanyList.getSuccess()) {
                        designationResponse.setDesgId(designationResponse.getDesgId());
                        designationResponse.setDesignationName(designationResponse.getDesignationName());
                        mDesigList.add(designationResponse);
                    }
                    mAdapter = new EditDesignationAdapter(EditDesignation.this, mDesigList,mAdd);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", " Activity");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_address_menu, menu);
        this.menu = menu;
        MenuItem item = menu.findItem(R.id.edit_address);

        final EditText editText = (EditText) item.getActionView().findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
                if (s.toString().isEmpty()) {
                    mApiCall.getDesignation();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return true;
    }
}


