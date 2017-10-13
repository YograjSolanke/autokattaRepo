package autokatta.com.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.adapter.NewVehicleListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import autokatta.com.response.NewVehicleAllResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewVehicleListActivity extends AppCompatActivity implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView mNoData;
    String myContact;
    List<NewVehicleAllResponse.Success.NewVehicle> newVehicleList = new ArrayList<>();
    int categoryId = 0, subCategoryId = 0, brandId = 0;
    Button mSelectStore;
    NewVehicleListAdapter mAdapter;
    ConnectionDetector mConnectionDetector;
    private List<String> storeIdList = new ArrayList<>();
    private List<String> storeTitleList = new ArrayList<>();
    private String[] storeTitleArray = new String[0];
    private String[] storeIdArray = new String[0];
    private KProgressHUD hud;
    private String stringstoreids = "", stringstorename = "";
    int store_id = 0;
    String callFrom = "";
    CheckBox selectAll;
    // boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) findViewById(R.id.newVehicleResultRecycler);
                mSelectStore = (Button) findViewById(R.id.selectStore);
                selectAll = (CheckBox) findViewById(R.id.selectAllCheck);
                mNoData = (TextView) findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);

                if (getIntent().getExtras() != null) {
                    categoryId = getIntent().getExtras().getInt("categoryId");
                    subCategoryId = getIntent().getExtras().getInt("subCategoryId");
                    brandId = getIntent().getExtras().getInt("brandId");
                    store_id = getIntent().getExtras().getInt("store_id");
                    callFrom = getIntent().getExtras().getString("callFrom");

                }

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mRecyclerView.setHasFixedSize(true);

                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(NewVehicleListActivity.this);
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getNewVehicleList(categoryId, subCategoryId, brandId);
                    }
                });

            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mConnectionDetector = new ConnectionDetector(this);


        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mAdapter = new NewVehicleListAdapter(NewVehicleListActivity.this, newVehicleList, mSelectStore, true, selectAll);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {

                    mAdapter = new NewVehicleListAdapter(NewVehicleListActivity.this, newVehicleList, mSelectStore, false, selectAll);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });



        mSelectStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> getVehicleIdList = mAdapter.getVehicleIds();
                System.out.println("Sizeeeeeeeeee=" + getVehicleIdList.size());
                String VehicleIds = "";
                if (!(getVehicleIdList.size() == 0)) {
                    for (int i = 0; i < getVehicleIdList.size(); i++) {

                        if (!getVehicleIdList.get(i).equals("0")) {
                            if (VehicleIds.equals(""))
                                VehicleIds = getVehicleIdList.get(i);
                            else
                                VehicleIds = VehicleIds + "," + getVehicleIdList.get(i);
                        }
                    }
                }
                Log.i("vehicleIds", VehicleIds);
                if (VehicleIds.equals("")) {
                    CustomToast.customToast(getApplicationContext(), "Please select vehicles");
                } else if (callFrom.equals("StoreViewActivity")) {
                    newVehicleStoreAsso(String.valueOf(store_id), VehicleIds);
                } else {
                    getStores(VehicleIds);
                }

            }
        });

    }


    private void getNewVehicleList(int categoryId, int subCategoryId, int brandId) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getNewVehicleList(categoryId, subCategoryId, brandId);
    }

    @Override
    public void onRefresh() {
        getNewVehicleList(categoryId, subCategoryId, brandId);
    }

    /*
    Get Store...
     */
    private void getStores(final String VehicleIds) {
        if (mConnectionDetector.isConnectedToInternet()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initLog().build())
                    .build();

            ServiceApi serviceApi = retrofit.create(ServiceApi.class);
            Call<MyStoreResponse> add = serviceApi._autokattaGetMyStoreList(myContact);

            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("loading stores...")
                    .setMaxProgress(100)
                    .show();

            add.enqueue(new Callback<MyStoreResponse>() {
                @Override
                public void onResponse(Call<MyStoreResponse> call, Response<MyStoreResponse> response) {
                    if (response.isSuccessful()) {
                        storeIdList.clear();
                        storeTitleList.clear();
                        hud.dismiss();

                        MyStoreResponse mProfileGroupResponse = response.body();
                        for (MyStoreResponse.Success success : mProfileGroupResponse.getSuccess()) {
                            storeIdList.add(String.valueOf(success.getId()));
                            storeTitleList.add(success.getName());
                        }
                        storeTitleArray = storeTitleList.toArray(new String[storeTitleList.size()]);
                        storeIdArray = storeIdList.toArray(new String[storeIdList.size()]);

                        /*if (storeTitleArray.length == 0) {
                            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(activity);
                            alertDialog.setTitle("No Store Found");
                            alertDialog.setMessage("Do you want to create Store...");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    CreateGroupFragment createGroupFragment = new CreateGroupFragment();
                                    Bundle b = new Bundle();
                                    b.putString("classname", "uploadvehicle");
                                    createGroupFragment.setArguments(b);

                                    ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.vehicle_upload_container, createGroupFragment, "Title")
                                            .addToBackStack("Title")
                                            .commit();
                                }
                            });
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        } else {*/
                        //itemsCheckedStores = new boolean[storeTitleArray.length];
                        alertBoxStore(storeTitleArray, VehicleIds);
                        //}
                    } else {
                        hud.dismiss();
                        CustomToast.customToast(getApplicationContext(), getString(R.string._404));
                    }
                }

                @Override
                public void onFailure(Call<MyStoreResponse> call, Throwable t) {
                    t.printStackTrace();
                }

            });
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    /*
    Alert Dialog For Stores
     */
    private void alertBoxStore(final String[] storeTitleArray, final String VehicleIds) {
        final List<String> mSelectedItems = new ArrayList<>();
        mSelectedItems.clear();
        /*String[] prearra = prevStoreIds.split(",");

        for (int i = 0; i < storeIdList.size(); i++) {
            if (Arrays.asList(prearra).contains(storeIdList.get(i))) {
                itemsCheckedStores[i] = true;
                mSelectedItems.add(storeIdList.get(i));
            } else
                itemsCheckedStores[i] = false;
        }*/

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewVehicleListActivity.this);

        // set the dialog title
        builder.setTitle("Select Store From Following")
                .setCancelable(true)
                .setMultiChoiceItems(storeTitleArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            mSelectedItems.add(storeIdArray[which]);
                            //itemsCheckedStores[which] = true;
                        } else if (mSelectedItems.contains(storeIdArray[which])) {
                            mSelectedItems.remove(storeIdArray[which]);
                            //itemsCheckedStores[which] = false;
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        stringstoreids = "";
                        stringstorename = "";
                        //prevStoreIds = "";
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            for (int j = 0; j < storeIdArray.length; j++) {
                                if (mSelectedItems.get(i).equals(storeIdArray[j])) {
                                    if (stringstoreids.equals("")) {
                                        stringstoreids = storeIdList.get(j);
                                        stringstorename = storeTitleArray[j];
                                    } else {
                                        stringstoreids = stringstoreids + "," + storeIdList.get(j);
                                        stringstorename = stringstorename + "," + storeTitleArray[j];
                                    }
                                }
                            }
                        }
                        //prevStoreIds = stringstoreids;
                        //mMainList.get(position).setGroupIDs(prevGroupIds);

                        if (mSelectedItems.size() == 0) {
                            CustomToast.customToast(getApplicationContext(), "No store Was Selected");
                            stringstoreids = "";
                            stringstorename = "";
                        } else {
                            newVehicleStoreAsso(stringstoreids, VehicleIds);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        stringstoreids = "";
                        stringstorename = "";
                    }

                })
                .show();
    }

    private void newVehicleStoreAsso(String stringstoreids, String vehicleIds) {
        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.newVehicleStoreAssoc(stringstoreids, vehicleIds, myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                NewVehicleAllResponse vehicleAllResponse = (NewVehicleAllResponse) response.body();
                if (!vehicleAllResponse.getSuccess().getNewVehicle().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    newVehicleList.clear();

                    for (NewVehicleAllResponse.Success.NewVehicle success : vehicleAllResponse.getSuccess().getNewVehicle()) {

                        success.setNewVehicleID(success.getNewVehicleID());
                        success.setCategoryID(success.getCategoryID());
                        success.setSubCategoryID(success.getSubCategoryID());
                        success.setBrandID(success.getBrandID());
                        success.setModelID(success.getModelID());
                        success.setVersionID(success.getVersionID());
                        success.setCategoryName(success.getCategoryName());
                        success.setSubCategoryName(success.getSubCategoryName());
                        success.setBrandName(success.getBrandName());
                        success.setModelName(success.getModelName());
                        success.setVersionName(success.getVersionName());

                        success.setThreePointLinkage((success.getThreePointLinkage() == null ||
                                success.getThreePointLinkage().equalsIgnoreCase("null") ||
                                success.getThreePointLinkage().equalsIgnoreCase("")) ? "NA" : success.getThreePointLinkage());

                        success.setABS((success.getABS() == null ||
                                success.getABS().equalsIgnoreCase("null") ||
                                success.getABS().equalsIgnoreCase("")) ? "NA" : success.getABS());


                        //success.setPrice((success.getPrice().equalsIgnoreCase("")) ? "NA" : success.getPrice());


                            /*String vehicleImage = success.getImage();
                            if (vehicleImage.contains(",")) {
                                String[] items = vehicleImage.split(",");
                                success.setImage(items[0]);
                            } else {
                                success.setImage(vehicleImage);
                            }*/

                        newVehicleList.add(success);

                    }

                    mAdapter = new NewVehicleListAdapter(this, newVehicleList, mSelectStore, false, selectAll);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                    selectAll.setVisibility(View.GONE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);
                selectAll.setVisibility(View.GONE);
                CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_response));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.VISIBLE);
            selectAll.setVisibility(View.GONE);
            CustomToast.customToast(getApplicationContext(), getApplicationContext().getString(R.string.no_internet));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        selectAll.setVisibility(View.GONE);
        mNoData.setVisibility(View.VISIBLE);
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
                    , "NewVehicleListActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("success_newvehicleadded_tostore")) {
                CustomToast.customToast(getApplicationContext(), "Vehicle added in store");
                finish();
            }
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging).readTimeout(90, TimeUnit.SECONDS);
        return httpClient;
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
