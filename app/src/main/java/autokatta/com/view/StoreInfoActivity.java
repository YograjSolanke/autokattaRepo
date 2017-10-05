package autokatta.com.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.enquiries.AllEnquiryTabActivity;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreOldAdminResponse;
import autokatta.com.response.StoreResponse;
import retrofit2.Response;

public class StoreInfoActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {


    String myContact, StoreContact;
    int Store_id;
    RelativeLayout ownerLayout;
    ImageView editStore, addEnquiry;
    RelativeLayout adminContactLayout;
    NestedScrollView scrollView;
    RelativeLayout mRel;
    private ProgressDialog dialog;
    TextView storeName, storeLocation, storeWebsite, storeWorkDays, storeOpen, editbrandtags, storeOwner,
            storeClose, storeAddress, storeServiceOffered, storeType, storeDescription, mNoData, adminContacts;
    ConnectionDetector mTestConnection;
    Activity mActivity;
    String storeAdmins = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTestConnection = new ConnectionDetector(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

                mRel = (RelativeLayout) findViewById(R.id.info_home);
                storeName = (TextView) findViewById(R.id.editstname);
                storeLocation = (TextView) findViewById(R.id.autolocation);
                storeWebsite = (TextView) findViewById(R.id.editwebsite);
                storeOpen = (TextView) findViewById(R.id.edittiming);
                storeClose = (TextView) findViewById(R.id.edittiming1);
                storeAddress = (TextView) findViewById(R.id.editaddress);
                storeType = (TextView) findViewById(R.id.storetype);
                editStore = (ImageView) findViewById(R.id.editStore);
                addEnquiry = (ImageView) findViewById(R.id.enquiry);
                storeDescription = (TextView) findViewById(R.id.editstoredescription);
                storeWorkDays = (TextView) findViewById(R.id.editworkingdays);
                storeServiceOffered = (TextView) findViewById(R.id.autoservices);
                scrollView = (NestedScrollView) findViewById(R.id.mainScroll);
                adminContacts = (TextView) findViewById(R.id.editAdminContact);
                editbrandtags = (TextView) findViewById(R.id.editbrandtags);
                adminContactLayout = (RelativeLayout) findViewById(R.id.linear14);
                storeOwner = (TextView) findViewById(R.id.editstOwner);
                ownerLayout = (RelativeLayout) findViewById(R.id.relativestoreOwner);

                storeOwner.setTextColor(Color.BLUE);
                storeOwner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(StoreInfoActivity.this, OtherProfile.class);
                        i.putExtra("contactOtherProfile", StoreContact);
                        startActivity(i);
                    }
                });

                if (getIntent().getExtras() != null) {
                    Store_id = getIntent().getExtras().getInt("store_id");
                    getStoredata(myContact, Store_id);
                }
//
//                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                    @Override
//                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                        if (scrollY > oldScrollY) {
//                            ((StoreViewActivity) getApplicationContext()).hideFloatingButton();
//                            Log.e("Hide", "->");
//                        }
//                        if (scrollY < oldScrollY) {
//                            ((StoreViewActivity) getApplicationContext()).showFloatingButton();
//                            Log.e("show1", "->");
//                        }
//                        if (scrollY == 0) {
//                            ((StoreViewActivity) getApplicationContext()).showFloatingButton();
//                            Log.e("show2", "->");
//                        }
//                        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                            ((StoreViewActivity) getApplicationContext()).hideFloatingButton();
//                            Log.e("show3", "->");
//                        }
//                    }
//                });
            }
        });
        editStore.setOnClickListener(this);
        addEnquiry.setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void getStoredata(String myContact, int store_id) {
        if (mTestConnection.isConnectedToInternet()) {
            dialog.show();
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.getStoreData(myContact, store_id);
            mApiCall.StoreAdmin(store_id);

        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
            //errorMessage(mActivity, getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof StoreResponse) {
                    StoreResponse storeResponse = (StoreResponse) response.body();
                    if (!storeResponse.getSuccess().isEmpty()) {

                        dialog.dismiss();
                        for (StoreResponse.Success success : storeResponse.getSuccess()) {
                            storeName.setText(success.getName());
                            storeOwner.setText(success.getOwnerName());
                            StoreContact = success.getContact();
                            storeLocation.setText(success.getLocation());
                            if (!success.getWebsite().equals(""))
                                storeWebsite.setText(success.getWebsite());
                            else
                                storeWebsite.setText("No Website Found");
                            storeWorkDays.setText(success.getWorkingDays());
                            storeOpen.setText(success.getStoreOpenTime());
                            storeClose.setText(success.getStoreCloseTime());
                            if (!success.getAddress().equals(""))
                                storeAddress.setText(success.getAddress());
                            else
                                storeAddress.setText("No Address");
                            if (!success.getStoreDescription().equals(""))
                                storeDescription.setText(success.getStoreDescription());
                            else
                                storeDescription.setText("No Description");
                            storeType.setText(success.getStoreType());
                            storeServiceOffered.setText(success.getCategory());
                            editbrandtags.setText(success.getBrandtags());

                            if (StoreContact.contains(myContact)) {
                                editStore.setVisibility(View.VISIBLE);
                                addEnquiry.setVisibility(View.VISIBLE);
                                ownerLayout.setVisibility(View.GONE);

                            } else {
                                adminContactLayout.setVisibility(View.GONE);


                            }
                        }
                    } else {
                        dialog.dismiss();
                    }
                } else if (response.body() instanceof StoreOldAdminResponse) {
                    StoreOldAdminResponse adminResponse = (StoreOldAdminResponse) response.body();
                    if (!adminResponse.getSuccess().isEmpty()) {
                        storeAdmins = "";
                        for (StoreOldAdminResponse.Success success : adminResponse.getSuccess()) {
                            String admin = success.getAdmin();
                            String[] arr = admin.split(",");

                            for (int i = 0; i < arr.length; i++) {
                                String[] join = arr[i].split("-");
                                if (storeAdmins.equals(""))
                                    storeAdmins = join[2] + "-" + join[1];
                                else
                                    storeAdmins = storeAdmins + "," + join[2] + "-" + join[1];

                            }

                        }


                        System.out.println("alreadyadmin=" + storeAdmins);
                        adminContacts.setText(storeAdmins);
                    } else {
                        adminContacts.setText("No Data");
                    }
                }
            } else {
                dialog.dismiss();
                //     CustomToast.customToast(getActivity(),getString(R.string._404));
            }
        } else {
            dialog.dismiss();
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {

            CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            //  CustomToast.customToast(getActivity(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            //CustomToast.customToast(getActivity(),getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {

            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {

            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreInfo");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editStore:
                Bundle bundle = new Bundle();
                bundle.putInt("store_id", Store_id);
                bundle.putString("className", "StoreViewActivity");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(StoreInfoActivity.this, CreateStoreActivity.class);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
                //getActivity().finish();
                break;

            case R.id.enquiry:
                ActivityOptions option = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(StoreInfoActivity.this, AllEnquiryTabActivity.class), option.toBundle());
                break;
        }
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
