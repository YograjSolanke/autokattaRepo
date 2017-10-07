package autokatta.com.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

public class SearchedNewVehicleResultActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {


    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ConnectionDetector mTestConnection;
    LinearLayoutManager mLinearLayoutManager;
    TextView mNoData;
    ImageView filterData;
    ApiCall apiCall;
    String myContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_new_vehicle_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        filterData = (ImageView) findViewById(R.id.filter);
        mRecyclerView = (RecyclerView) findViewById(R.id.searchResultRecycler);
        apiCall = new ApiCall(this, this);
        mTestConnection = new ConnectionDetector(this);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mRecyclerView.setHasFixedSize(true);
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
//                apiCall.MyStoreList(myContact);
                    }
                });
            }
        });


        /*
        More Items click listener...
         */
        filterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.custom_more_used_vehicle, null);
                ImageView mClose = (ImageView) view.findViewById(R.id.close);
                Button mEnquiry = (Button) view.findViewById(R.id.Enquiry);
                Button mQuotation = (Button) view.findViewById(R.id.quotation);
                mEnquiry.setVisibility(View.GONE);
                mQuotation.setVisibility(View.GONE);
                Button mTransferStock = (Button) view.findViewById(R.id.transfer_stock);
                Button mSold = (Button) view.findViewById(R.id.delete);
                Button mViewQuote = (Button) view.findViewById(R.id.view_quotation);
                mViewQuote.setVisibility(View.GONE);
                mTransferStock.setText("Low To High");
                mSold.setText("High To Low");

                final Dialog mBottomSheetDialog = new Dialog(SearchedNewVehicleResultActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();

                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });

            }
        });


    }

    @Override
    public void onRefresh() {
//        apiCall.MyStoreList(myContact);
//        mRecyclerView.getRecycledViewPool().clear();
//        adapter.notifyDataSetChanged();
    }


    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof NullPointerException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "SearchedNewVehicleResultActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
