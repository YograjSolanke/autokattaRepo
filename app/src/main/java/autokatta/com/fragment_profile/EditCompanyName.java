package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.EditCompanyNameAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetCompaniesResponse;
import retrofit2.Response;

public class EditCompanyName extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    final List<GetCompaniesResponse.Success> mCompanyList = new ArrayList<>();
    final HashMap<String, Integer> mCompanyList1 = new HashMap<>();
    List<String> parsedDataCompany = new ArrayList<>();
    EditCompanyNameAdapter mAdapter;
    ApiCall mApiCall;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mApiCall = new ApiCall(EditCompanyName.this, this);

        mApiCall.getCompany();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBGroup);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerBGroup);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        //  mLinearLayoutManager.setReverseLayout(true);
     //   mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mSwipeRefreshLayout.setOnRefreshListener(EditCompanyName.this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                mApiCall.getCompany();
            }
        });
    }

    @Override
    public void onRefresh() {
        mApiCall.getCompany();
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                GetCompaniesResponse mGetCompanyList = (GetCompaniesResponse) response.body();
                if (!mGetCompanyList.getSuccess().isEmpty()) {
                    for (GetCompaniesResponse.Success companyResponse : mGetCompanyList.getSuccess()) {
                        companyResponse.setCompanyID(companyResponse.getCompanyID());
                        companyResponse.setCompanyName(companyResponse.getCompanyName());
                        mCompanyList.add(companyResponse);
                        //     mCompanyList.add(companyResponse.getCompanyName());
                        //  mCompanyList1.put(companyResponse.getCompanyName(), companyResponse.getCompanyID());
                    }
                    //  parsedDataCompany.addAll(mCompanyList);
                    mAdapter = new EditCompanyNameAdapter(EditCompanyName.this, mCompanyList);
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
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
//                CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_address_menu, menu);
        this.menu = menu;
        MenuItem item = menu.findItem(R.id.edit_profile);

        return true;
    }
}


