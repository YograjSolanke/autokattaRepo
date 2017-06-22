package autokatta.com.view;

import android.app.Dialog;
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
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GetPersonsEnquiriesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetPersonDataResponse;
import retrofit2.Response;

public class EnquiredPersonsListActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mPersonRecyclerView;
    List<GetPersonDataResponse.Success> mList = new ArrayList<>();
    RelativeLayout mRelativeLayout;
    private String strId, strKeyword, strTitle;
    TextView mNoData, mTitletxt, mTypetxt;
    ConnectionDetector mConnectionDetector;
    private ProgressDialog dialog;
    FloatingActionButton fabAddNewEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquired_persons_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Personal Enquiries");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                strId = getIntent().getExtras().getString("id");
                strKeyword = getIntent().getExtras().getString("keyword");
                strTitle = getIntent().getExtras().getString("name");

                mNoData = (TextView) findViewById(R.id.no_category);

                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.personList_swipeRefreshLayout);
                mPersonRecyclerView = (RecyclerView) findViewById(R.id.personList_recycler_view);
                mRelativeLayout = (RelativeLayout) findViewById(R.id.personList_enquiry_frame);
                mTypetxt = (TextView) findViewById(R.id.type);
                mTitletxt = (TextView) findViewById(R.id.title);
                fabAddNewEnquiry = (FloatingActionButton) findViewById(R.id.fabAddNewEnquiry);

                mTypetxt.setText(strKeyword);
                mTitletxt.setText(strTitle);

                dialog = new ProgressDialog(EnquiredPersonsListActivity.this);
                dialog.setMessage("Loading...");

                mConnectionDetector = new ConnectionDetector(EnquiredPersonsListActivity.this);

                mPersonRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayout = new LinearLayoutManager(getApplicationContext());
                mLinearLayout.setReverseLayout(true);
                mLinearLayout.setStackFromEnd(true);

                mPersonRecyclerView.setLayoutManager(mLinearLayout);
                mPersonRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mPersonRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));


                mSwipeRefreshLayout.setOnRefreshListener(EnquiredPersonsListActivity.this);

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

        fabAddNewEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEnquiryPopUp();
            }
        });

        mPersonRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scrolling up
                    fabAddNewEnquiry.hide(true);

                } else {
                    // Scrolling down
                    fabAddNewEnquiry.show(true);
                }
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
                                mList.add(success);
                            }
                            GetPersonsEnquiriesAdapter adapter = new GetPersonsEnquiriesAdapter(this, mList, strId, strKeyword, strTitle);
                            mPersonRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(mRelativeLayout, mPersonDataResponse.getError(), Snackbar.LENGTH_SHORT).show();
                            mNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mRelativeLayout, getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mRelativeLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            Snackbar snackbar = Snackbar.make(mRelativeLayout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "EnquiredPersonsList Activity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }


    public void newEnquiryPopUp() {
        final Dialog openDialog = new Dialog(EnquiredPersonsListActivity.this);
        openDialog.setContentView(R.layout.add_new_enquiry);


        final EditText edtDiscussion = (EditText) openDialog.findViewById(R.id.edtDiscussion);
        final Spinner spnStatus = (Spinner) openDialog.findViewById(R.id.spnStatus);
        final EditText edtDate = (EditText) openDialog.findViewById(R.id.edtDate);

        Button addEnquiry = (Button) openDialog.findViewById(R.id.btnSend);

        /*titleText.setText(mMainList.get(holder.getAdapterPosition()).getTitle() + " of Category "
                + mMainList.get(holder.getAdapterPosition()).getCategory());*/

        edtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    edtDate.setInputType(InputType.TYPE_NULL);
                    edtDate.setError(null);
                    new SetMyDateAndTime("date", edtDate, EnquiredPersonsListActivity.this);
                }
                return false;
            }
        });
        addEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String strNewDiscussion = "", strNewFollowDate = "", strNewStatus = "";

                strNewDiscussion = edtDiscussion.getText().toString();
                strNewFollowDate = edtDate.getText().toString();
                int i = spnStatus.getSelectedItemPosition();
                //if (i!=0)

                if (strNewDiscussion.equals("")) {
                    edtDiscussion.setError("Enter Discussion");
                    edtDiscussion.requestFocus();
                } else if (!strNewDiscussion.equals("") && strNewDiscussion.startsWith(" ") && strNewDiscussion.endsWith(" ")) {
                    edtDiscussion.setError("Enter Valid Discussion");
                    edtDiscussion.requestFocus();
                } else if (strNewFollowDate.equals("") || strNewFollowDate.startsWith(" ")) {
                    edtDate.setError("Enter Date");
                    edtDate.requestFocus();
                } else if (spnStatus.getSelectedItemPosition() == 0) {
                    CustomToast.customToast(getApplicationContext(), "Please provide status");
                    spnStatus.requestFocus();
                } else {
                    strNewStatus = spnStatus.getSelectedItem().toString();
                    Log.i("Result", "-" + strNewDiscussion + "\n" + strNewFollowDate + "\n" + strNewStatus);
                    openDialog.dismiss();
                }
            }
        });
        openDialog.show();
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
