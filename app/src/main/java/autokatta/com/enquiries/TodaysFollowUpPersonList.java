package autokatta.com.enquiries;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class TodaysFollowUpPersonList extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mPersonRecyclerView;
    List<GetPersonDataResponse.Success> mList = new ArrayList<>();
    String formattedDate;
    int mEnquiryID;
    private String strId, strKeyword, strTitle;
    TextView mNoData, mTitletxt, mTypetxt;
    ConnectionDetector mConnectionDetector;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_follow_up_person_list);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Today's follow-up person");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = df.format(c.getTime());

                strId = getIntent().getExtras().getString("id");
                strKeyword = getIntent().getExtras().getString("keyword");
                strTitle = getIntent().getExtras().getString("name");
                mEnquiryID = getIntent().getExtras().getInt("enquiryid");
                mNoData = (TextView) findViewById(R.id.no_category);

                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.person_swipeRefreshLayout);
                mPersonRecyclerView = (RecyclerView) findViewById(R.id.person_recycler_view);
                mTypetxt = (TextView) findViewById(R.id.type);
                mTitletxt = (TextView) findViewById(R.id.title);

                mTypetxt.setText(strKeyword);
                mTitletxt.setText(strTitle);

                dialog = new ProgressDialog(TodaysFollowUpPersonList.this);
                dialog.setMessage("Loading...");

                mConnectionDetector = new ConnectionDetector(TodaysFollowUpPersonList.this);
                mPersonRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayout = new LinearLayoutManager(getApplicationContext());
                mLinearLayout.setReverseLayout(true);
                mLinearLayout.setStackFromEnd(true);

                mPersonRecyclerView.setLayoutManager(mLinearLayout);
                mPersonRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mPersonRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                mSwipeRefreshLayout.setOnRefreshListener(TodaysFollowUpPersonList.this);

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
                                if (success.getNextFollowupDate().equals(formattedDate)) {
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
                            }
                            GetPersonDataAdapter adapter = new GetPersonDataAdapter(this, mList, strId, strKeyword, strTitle,mEnquiryID);
                            mPersonRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            mNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void notifyError(Throwable error) {

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
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
