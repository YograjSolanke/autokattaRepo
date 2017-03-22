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
import autokatta.com.response.MySavedAuctionResponse;
import retrofit2.Response;

public class MySavedAuctionEventActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    SharedPreferences mSharedPreferences;

    List<MySavedAuctionResponse.Success> mysavedAuctionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_saved_auction_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutMySavedAuctions);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);

        apiCall = new ApiCall(this, this);
        mSharedPreferences = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        final String mContact = mSharedPreferences.getString("loginContact", "7841023392");
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                apiCall.getMySavedAuctions(mContact);
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

                MySavedAuctionResponse savedAuctionResponse = (MySavedAuctionResponse) response.body();
                if (!savedAuctionResponse.getSuccess().isEmpty()) {
                    for (MySavedAuctionResponse.Success success : savedAuctionResponse.getSuccess()) {

                        success.setAuctionId(success.getAuctionId());
                        success.setActionTitle(success.getActionTitle());
                        success.setStartDate(success.getStartDate());
                        success.setStartTime(success.getStartTime());
                        success.setEndDate(success.getEndDate());
                        success.setEndTime(success.getEndTime());
                        success.setNoOfVehicles(success.getNoOfVehicles());
                        success.setSpecialClauses(success.getSpecialClauses());
                        success.setSpecialPosition(success.getSpecialPosition());
                        success.setPositionArray(success.getPositionArray());

                        mysavedAuctionList.add(success);


                    }

                    Log.i("Ssize", String.valueOf(mysavedAuctionList.size()));
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
                    , "MySavedAuctionEventActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
