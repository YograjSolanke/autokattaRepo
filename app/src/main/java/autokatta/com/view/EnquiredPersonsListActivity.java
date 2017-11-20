package autokatta.com.view;

import android.Manifest.permission;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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
import autokatta.com.adapter.GetPersonsEnquiriesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetManualEnquiryPersonDataResponse;
import retrofit2.Response;

public class EnquiredPersonsListActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mPersonRecyclerView;
    List<GetManualEnquiryPersonDataResponse.Success> mList = new ArrayList<>();
    RelativeLayout mRelativeLayout;
    private String strId, strKeyword, strTitle;
    TextView mNoData, mTitletxt, mTypetxt, mAddress, mContact, mCustname;
    ConnectionDetector mConnectionDetector;
    private ProgressDialog dialog;
    FloatingActionButton fabAddNewEnquiry;
    String bundlecontact, bundleAddress, bundleCustname;
    ImageView mCall;
    FloatingActionButton mRequests, mTransfer;
    FloatingActionMenu mFloatingMenu;
    String strNewDiscussion = "", strNewFollowDate = "", strNewStatus = "",mCallFrom,mOwnerContact;
    int bundleEnquiryID;
    ApiCall mApiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquired_persons_list);
        mConnectionDetector = new ConnectionDetector(EnquiredPersonsListActivity.this);
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
                bundlecontact = getIntent().getExtras().getString("contact");
                bundleAddress = getIntent().getExtras().getString("address");
                bundleCustname = getIntent().getExtras().getString("custname");
                bundleEnquiryID = getIntent().getExtras().getInt("enquiryid");
                mCallFrom = getIntent().getExtras().getString("callfrom");
                mOwnerContact = getIntent().getExtras().getString("ownercontact");


                mNoData = (TextView) findViewById(R.id.no_category);

                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.personList_swipeRefreshLayout);
                mPersonRecyclerView = (RecyclerView) findViewById(R.id.personList_recycler_view);
                mRelativeLayout = (RelativeLayout) findViewById(R.id.personList_enquiry_frame);
                mTypetxt = (TextView) findViewById(R.id.type);
                mTitletxt = (TextView) findViewById(R.id.title);
                mAddress = (TextView) findViewById(R.id.address);
                mContact = (TextView) findViewById(R.id.contact);
                mCall = (ImageView) findViewById(R.id.call);
                mCustname = (TextView) findViewById(R.id.custname);
                fabAddNewEnquiry = (FloatingActionButton) findViewById(R.id.fabAddNewEnquiry);
                mFloatingMenu = (FloatingActionMenu) findViewById(R.id.menu_red);
                mRequests = (FloatingActionButton) findViewById(R.id.requests);
                mTransfer = (FloatingActionButton) findViewById(R.id.shareenquiry);

                mTypetxt.setText(strKeyword);
                mTitletxt.setText(strTitle);
                mAddress.setText(bundleAddress);
                mContact.setText(bundlecontact);
                mCustname.setText(bundleCustname);

                if (!mCallFrom.equalsIgnoreCase("transferenquiry"))
                {
                    mTransfer.show(true);
                }

                dialog = new ProgressDialog(EnquiredPersonsListActivity.this);
                dialog.setMessage("Loading...");


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

        mCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                call(bundlecontact);
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
                    mFloatingMenu.hideMenu(true);

                } else {
                    // Scrolling down
                    mFloatingMenu.showMenu(true);
                }
            }
        });


        mTransfer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EnquiredPersonsListActivity.this, ShareEnquiryActivity.class);
                     i.putExtra("enquiryid",bundleEnquiryID);
                     i.putExtra("Ids",strId);
                     i.putExtra("keyword",strKeyword);
                     i.putExtra("enqcontact",bundlecontact);
                startActivity(i);
            }
        });

        mRequests.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EnquiredPersonsListActivity.this, TransferEnquiryRequestActivity.class);
                //     i.putExtra("enquiryid",mEnquiryId);
                startActivity(i);

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
            mApiCall.getManualEnquiryPersonData(bundlecontact, id, getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""), keyword);
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
                if (response.body() instanceof GetManualEnquiryPersonDataResponse) {
                    GetManualEnquiryPersonDataResponse mPersonDataResponse = (GetManualEnquiryPersonDataResponse) response.body();
                    if (mPersonDataResponse.getSuccess() != null) {

                        if (!mPersonDataResponse.getSuccess().isEmpty()) {

                            mNoData.setVisibility(View.GONE);
                            for (GetManualEnquiryPersonDataResponse.Success success : mPersonDataResponse.getSuccess()) {
                                //success.setUsername(success.getUsername());

                                if (success.getCustomerName().equals("") || success.getCustomerName().isEmpty())
                                    success.setCustomerName("Unknown");
                                else
                                    success.setCustomerName(success.getCustomerName());
                                success.setDiscussion(success.getDiscussion());
                                success.setCreatedDate(success.getCreatedDate());
                                success.setEnquiryStatus(success.getEnquiryStatus());
                                success.setNextFollowUpDate(success.getNextFollowUpDate());
                                success.setInventoryType(success.getInventoryType());

                                /*Date format*/      /*  two dates in different format *//*Date format*/
                                try {
                                    TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                    TimeZone utc1 = TimeZone.getTimeZone("etc/UTC");
                                    //format of date coming from services
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm ss", Locale.getDefault());
                                    inputFormat.setTimeZone(utc);
                                    inputFormat1.setTimeZone(utc1);

                                    //format of date which we want to show
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                    DateFormat outputFormat1 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                    outputFormat.setTimeZone(utc);
                                    outputFormat1.setTimeZone(utc1);

                                    Date date1 = inputFormat.parse(success.getNextFollowUpDate());
                                    Date date = inputFormat.parse(success.getCreatedDate());
                                    //System.out.println("jjj"+date);
                                    String output1 = outputFormat.format(date);
                                    String output = outputFormat.format(date1);
                                    //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                                    success.setNextFollowUpDate(output);
                                    success.setCreatedDate(output1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                mList.add(success);
                            }
                            GetPersonsEnquiriesAdapter adapter = new GetPersonsEnquiriesAdapter(this, mList, strId, strKeyword, strTitle, bundlecontact);
                            mPersonRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
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
        if (str != null) {
            if (str.equalsIgnoreCase("success")) {
                Snackbar.make(mRelativeLayout, "Enquiry Added Successfully", Snackbar.LENGTH_SHORT).show();
                getPersonData(strId, strKeyword);
            } else {
                Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mRelativeLayout, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
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
                    if (mCallFrom.equalsIgnoreCase("transferenquiry"))
                    {
                        addPersonsenquiryfromtransfer();
                        openDialog.dismiss();
                    }else
                    {
                        addPersonsenquiry();
                        openDialog.dismiss();
                    }
                }
            }
        });
        openDialog.show();
    }


    //Calling Functionality
    private void call(String rcontact) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rcontact));
        try {
            if (ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            System.out.println("No Activity Found For Call in Car Details Fragment\n");
        }
    }


    public void addPersonsenquiry()
    {
        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.addManualEnquiryPersonData(bundlecontact,strNewStatus,getSharedPreferences(getString(R.string.my_preference),MODE_PRIVATE).getString("loginContact", ""), strKeyword,strNewDiscussion,strNewFollowDate,strId,"");
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }


    public void addPersonsenquiryfromtransfer()
    {
        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.addManualEnquiryPersonData(bundlecontact,strNewStatus,mOwnerContact, strKeyword,strNewDiscussion,strNewFollowDate,strId,getSharedPreferences(getString(R.string.my_preference),MODE_PRIVATE).getString("loginContact", ""));
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
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
