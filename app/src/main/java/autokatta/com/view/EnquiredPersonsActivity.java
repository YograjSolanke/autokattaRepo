package autokatta.com.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.GetPersonDataAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetPersonDataResponse;
import retrofit2.Response;

public class EnquiredPersonsActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mPersonRecyclerView;
    List<GetPersonDataResponse.Success> mList = new ArrayList<>();
    FrameLayout mFrameLayout;
    private String strId, strKeyword, strTitle;
    LinearLayout filterLayout;
    TextView mNoData, mTitletxt, mTypetxt;
    ConnectionDetector mConnectionDetector;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquired_persons);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Enquired Person's List");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                strId = getIntent().getExtras().getString("id");
                strKeyword = getIntent().getExtras().getString("keyword");
                strTitle = getIntent().getExtras().getString("name");

                mNoData = (TextView) findViewById(R.id.no_category);

                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.person_swipeRefreshLayout);
                mPersonRecyclerView = (RecyclerView) findViewById(R.id.person_recycler_view);
                mFrameLayout = (FrameLayout) findViewById(R.id.person_enquiry_frame);
                mTypetxt = (TextView) findViewById(R.id.type);
                mTitletxt = (TextView) findViewById(R.id.title);
                filterLayout = (LinearLayout) findViewById(R.id.below);
                filterLayout.setVisibility(View.GONE);

                mTypetxt.setText(strKeyword);
                mTitletxt.setText(strTitle);

                dialog = new ProgressDialog(EnquiredPersonsActivity.this);
                dialog.setMessage("Loading...");

                mConnectionDetector = new ConnectionDetector(EnquiredPersonsActivity.this);

                mPersonRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayout = new LinearLayoutManager(getApplicationContext());
                mLinearLayout.setReverseLayout(true);
                mLinearLayout.setStackFromEnd(true);

                mPersonRecyclerView.setLayoutManager(mLinearLayout);
                mPersonRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mPersonRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));


                mSwipeRefreshLayout.setOnRefreshListener(EnquiredPersonsActivity.this);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);

                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getPersonData(strId, strKeyword);
                    }
                });

            }
        });

        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToast.customToast(getApplicationContext(), "Coming soon....");
            }
        });
    }

    @Override
    public void onRefresh() {
        getPersonData(strId, strKeyword);
    }

    /*
    Get Person Data...
     */
    private void getPersonData(String id, String keyword) {

        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.getPersonData(id, keyword);
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {

                mSwipeRefreshLayout.setRefreshing(false);
                mList.clear();

                /*Person's information */
                if (response.body() instanceof GetPersonDataResponse) {
                    GetPersonDataResponse mPersonDataResponse = (GetPersonDataResponse) response.body();
                    if (mPersonDataResponse.getSuccess() != null) {

                        if (!mPersonDataResponse.getSuccess().isEmpty()) {

                            mNoData.setVisibility(View.GONE);
                            for (GetPersonDataResponse.Success success : mPersonDataResponse.getSuccess()) {
                                //success.setUsername(success.getUsername());

                                if (success.getUsername().equals("") || success.getUsername().isEmpty())
                                    success.setUsername("Unknown");
                                else
                                    success.setUsername(success.getUsername());
                                success.setContactNo(success.getContactNo());
                                success.setCity(success.getCity());
                                success.setProfilePic(success.getProfilePic());
                                success.setNextFollowupDate(success.getNextFollowupDate());
                                success.setIsPresent(success.getIsPresent());
                                success.setLastEnquiryDate(success.getLastEnquiryDate());
                                success.setCustEnquiryStatus(success.getCustEnquiryStatus());
                                success.setLastDiscussion(success.getLastDiscussion());

                               /*   *//*Date format*/
                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                    //format of date coming from services
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                Locale.getDefault());*/
                                    inputFormat.setTimeZone(utc);

                                    //format of date which we want to show
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        /*DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa",
                                Locale.getDefault());*/
                                    outputFormat.setTimeZone(utc);

                                    Date date = inputFormat.parse(success.getNextFollowupDate());
                                    Date date1 = inputFormat.parse(success.getLastEnquiryDate());
                                    //System.out.println("jjj"+date);
                                    String output = outputFormat.format(date);
                                    String output1 = outputFormat.format(date1);
                                    //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                                    success.setNextFollowupDate(output);
                                    success.setLastEnquiryDate(output1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                mList.add(success);
                            }
                            GetPersonDataAdapter adapter = new GetPersonDataAdapter(this, mList, strId, strKeyword, strTitle);
                            mPersonRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(mFrameLayout, mPersonDataResponse.getError(), Snackbar.LENGTH_SHORT).show();
                            mNoData.setVisibility(View.VISIBLE);
                            filterLayout.setVisibility(View.GONE);
                        }
                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mFrameLayout, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mFrameLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mFrameLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof IOException) {
            Snackbar.make(mFrameLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "EnquiredPersons Activity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            /*case R.id.add_manual:
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ManualEnquiry.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getApplicationContext(), AddManualEnquiry.class), options.toBundle());
                finish();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
