package autokatta.com.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.adapter.StoreAdminAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.register.InvitationCompanyBased;
import autokatta.com.response.Db_AutokattaContactResponse;
import autokatta.com.response.StoreOldAdminResponse;
import retrofit2.Response;

import static autokatta.com.database.DbConstants.userName;

public class AddAdminsForStoreActivity extends AppCompatActivity implements RequestNotifier {

    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    RecyclerView mRecyclerView;
    Button cancel;
    public static Button ok;
    StoreAdminAdapter adapter;
    RelativeLayout mainlayout;
    TextView noContacts;

    RelativeLayout btnlayout;
    EditText inputSearch;
    String callFrom, storetype;
    int store_id;
    String finaladmins = "";
    ArrayList<String> alreadyAdmin = new ArrayList<>();
    ApiCall apiCall;
    DbOperation dbOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admins_for_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);


        mRecyclerView = (RecyclerView) findViewById(R.id.l1);
        btnlayout = (RelativeLayout) findViewById(R.id.btnrelative);
        ok = (Button) findViewById(R.id.ok);
        cancel = (Button) findViewById(R.id.cancel);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        noContacts = (TextView) findViewById(R.id.textview);
        inputSearch.setVisibility(View.GONE);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Set animation attribute to each item
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        apiCall = new ApiCall(this, this);
        dbOperation = new DbOperation(this);

        try {
            if (getIntent().getExtras() != null) {
                store_id = getIntent().getExtras().getInt("store_id");
                callFrom = getIntent().getExtras().getString("call");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DbOperation dbAdpter = new DbOperation(this);
        dbAdpter.OPEN();
        Cursor cursor = dbAdpter.getAutokattaContact();
        if (cursor.getCount() > 0) {
            contactdata.clear();
            cursor.moveToFirst();
            do {
                Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(userName)) + " = " + cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                Db_AutokattaContactResponse obj = new Db_AutokattaContactResponse();
                obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                contactdata.add(obj);
            } while (cursor.moveToNext());
        }
        dbAdpter.CLOSE();

        if (!(contactdata.size() == 0)) {
            getAlreadyAdminData();
        } else {
            noContacts.setVisibility(View.VISIBLE);
            mainlayout.setVisibility(View.GONE);
        }

        btnlayout.setVisibility(View.VISIBLE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                //  b.putString("action", "main");
                b.putInt("store_id", store_id);
                b.putString("flow_tab_name", "adminMore");
                if (!callFrom.equalsIgnoreCase("interestbased")) {
                    finish();
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(AddAdminsForStoreActivity.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent intent = new Intent(AddAdminsForStoreActivity.this, StoreViewActivity.class);
                    intent.putExtras(b);
                    startActivity(intent, options.toBundle());

                } else {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(AddAdminsForStoreActivity.this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(AddAdminsForStoreActivity.this, InvitationCompanyBased.class);
                    startActivity(i, options.toBundle());
                    finish();

                }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;

                for (int i = 0; i < StoreAdminAdapter.boxdata.size(); i++) {
                    if (!StoreAdminAdapter.boxdata.get(i).equalsIgnoreCase("0")) {
                        if (StoreAdminAdapter.isSave.get(i).equals(false)) {
                            CustomToast.customToast(getApplicationContext(), "Save Role First");
                            flag = true;
                            break;
                        } else {
                            flag = false;
                            if (finaladmins.equals(""))
                                finaladmins = StoreAdminAdapter.boxdata.get(i);
                            else
                                finaladmins = finaladmins + "," + StoreAdminAdapter.boxdata.get(i);
                        }

                    }
                }

                if (!flag) {

                    if (!finaladmins.equals("")) {

                        System.out.println("finalAdmins=====" + finaladmins);
                        addStoreAdmins(store_id, finaladmins);
                    } else {
                        CustomToast.customToast(getApplicationContext(), "Please Select Atleast Single contact");

                    }
                }
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void getAlreadyAdminData() {
        apiCall.StoreAdmin(store_id);
    }

    private void addStoreAdmins(int storeId, String adminNos) {
        apiCall.newStoreAdmin(storeId, adminNos);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                /*
                        Response to get store admins
                 */
                if (response.body() instanceof StoreOldAdminResponse) {
                    StoreOldAdminResponse adminResponse = (StoreOldAdminResponse) response.body();
                    if (!adminResponse.getSuccess().isEmpty()) {
                        for (StoreOldAdminResponse.Success success : adminResponse.getSuccess()) {
                            // if(alreadyAdmin.equals(""))
                            alreadyAdmin.add(success.getAdmin());
//                            else
//                                alreadyAdmin=alreadyAdmin+","+success.getAdmin();
                        }

                        System.out.println("alreadyadmin=" + alreadyAdmin);
                    } /*else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));*/
                    if (alreadyAdmin.size() != 0)
                        adapter = new StoreAdminAdapter(this, contactdata, alreadyAdmin);
                    else {
                        adapter = new StoreAdminAdapter(this, contactdata);
                    }
                    mRecyclerView.setAdapter(adapter);
                }
            } else {
                //  CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof NullPointerException) {
            //  CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Add more Admins Store Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("success")) {
                Bundle b = new Bundle();
                //  b.putString("action", "main");
                b.putInt("store_id", store_id);
                b.putString("flow_tab_name", "adminMore");
                if (!callFrom.equalsIgnoreCase("interestbased")) {
                    finish();
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent intent = new Intent(this, StoreViewActivity.class);
                    intent.putExtras(b);
                    startActivity(intent, options.toBundle());

                } else {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(this, InvitationCompanyBased.class);
                    startActivity(i, options.toBundle());
                    finish();

                }
            }
        } else {

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
