package autokatta.com.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import autokatta.com.R;
import autokatta.com.adapter.QuotationListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.interfaces.ServiceApi;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyVehicleQuotationListResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyVehicleQuotationListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    RecyclerView quotationRecycler;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mNoData, headText;
    String myContact;
    ConnectionDetector mTestConnection;
    LinearLayoutManager mLinearLayoutManager;
    private static int firstVisibleInListview;
    TextView Title, Category, Brand, Model, Keyword, Price;
    RelativeLayout relCategory, relBrand, relModel, relPrice, MainRel;
    ImageView Image;
    Button view_vehicle, get_Quote;
    int bundle_GroupId;
    int bundle_VehicleId;
    FloatingActionButton fab;
    String bundle_Type, bundle_Contact;
    String image;
    List<MyVehicleQuotationListResponse.Success> quotationList = new ArrayList<>();
    LinearLayout linearButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle_quotation_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
        quotationRecycler = (RecyclerView) findViewById(R.id.quotationList);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mNoData = (TextView) findViewById(R.id.no_category);
        headText = (TextView) findViewById(R.id.headText);
        mNoData.setVisibility(View.GONE);
        quotationRecycler.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        quotationRecycler.setLayoutManager(mLinearLayoutManager);
        firstVisibleInListview = mLinearLayoutManager.findFirstVisibleItemPosition();

        Keyword = (TextView) findViewById(R.id.keyword);
        Title = (TextView) findViewById(R.id.settitle);
        Category = (TextView) findViewById(R.id.setcategory);
        Brand = (TextView) findViewById(R.id.setbrand);
        Model = (TextView) findViewById(R.id.setmodel);
        Image = (ImageView) findViewById(R.id.image);
        Price = (TextView) findViewById(R.id.setprice);
        relCategory = (RelativeLayout) findViewById(R.id.relative2);
        relBrand = (RelativeLayout) findViewById(R.id.relative3);
        relModel = (RelativeLayout) findViewById(R.id.relative4);
        relPrice = (RelativeLayout) findViewById(R.id.relative5);
        MainRel = (RelativeLayout) findViewById(R.id.MainRel);
        linearButton = (LinearLayout) findViewById(R.id.llButton);
        view_vehicle = (Button) findViewById(R.id.view_vehicle);
        get_Quote = (Button) findViewById(R.id.view_quot_list);
        get_Quote.setVisibility(View.GONE);
        Keyword.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutQuotList);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent i = getIntent();
                Title.setText(i.getStringExtra("Title"));
                setTitle(i.getStringExtra("Title") + " " + "Quotation's");
                Category.setText(i.getStringExtra("Category"));
                Brand.setText(i.getStringExtra("Brand"));
                Model.setText(i.getStringExtra("Model"));
                Price.setText(i.getStringExtra("Price"));
                bundle_GroupId = i.getIntExtra("bundle_GroupId", 0);
                bundle_VehicleId = i.getIntExtra("bundle_VehicleId", 0);
                bundle_Type = i.getStringExtra("bundle_Type");
                bundle_Contact = i.getStringExtra("bundle_Contact");
                image = i.getStringExtra("Image");

                if (image.equalsIgnoreCase("") || image == null) {
                    Image.setBackgroundResource(R.drawable.vehiimg);
                } else {
                    Glide.with(MyVehicleQuotationListActivity.this)
                            .load(getString(R.string.base_image_url) + image)
                            .bitmapTransform(new CropCircleTransformation(MyVehicleQuotationListActivity.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                            .into(Image);

                    mTestConnection = new ConnectionDetector(MyVehicleQuotationListActivity.this);
                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);

                            if (bundle_Contact.equals(myContact)) {
                                fab.setVisibility(View.GONE);
                                getOtherQuotationList(bundle_GroupId, bundle_VehicleId, bundle_Type);
                            } else {
                                headText.setText("Your Quotation List");
                                getMyQuotationList(bundle_GroupId, bundle_VehicleId, bundle_Type);
                            }
                        }


                    });
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton();
            }
        });

        view_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putInt("vehicle_id", bundle_VehicleId);
                Intent intent = new Intent(MyVehicleQuotationListActivity.this, VehicleDetails.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });



    }

    private void getMyQuotationList(int bundle_groupId, int bundle_vehicleId, String bundle_type) {
        //here user will see list of Quotation which belongs him

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.GetMyQuotationList(bundle_groupId, bundle_vehicleId, bundle_type, myContact);
        } else
            CustomToast.customToast(this, getString(R.string.no_internet));
    }

    private void getOtherQuotationList(int bundle_groupId, int bundle_vehicleId, String bundle_type) {
        //here user will see list of Quotation send by other user for that particular vehicle

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.GetVehicleQuotationList(bundle_groupId, bundle_vehicleId, bundle_type);
        } else
            CustomToast.customToast(this, getString(R.string.no_internet));
    }

    @Override
    public void onRefresh() {

        if (bundle_Contact.equals(myContact)) {
            fab.setVisibility(View.GONE);
            getOtherQuotationList(bundle_GroupId, bundle_VehicleId, bundle_Type);
        } else {
            getMyQuotationList(bundle_GroupId, bundle_VehicleId, bundle_Type);
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                MyVehicleQuotationListResponse quotationResponse = (MyVehicleQuotationListResponse) response.body();
                if (!quotationResponse.getSuccess().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);
                    quotationList.clear();
                    for (MyVehicleQuotationListResponse.Success success : quotationResponse.getSuccess()) {
                        success.setCustContact(success.getCustContact());
                        success.setCustomerName(success.getCustomerName());
                        success.setReservePrice(success.getReservePrice());
                        success.setQuery(success.getQuery());


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

                            Date date = inputFormat.parse(success.getCreatedDate());
                            //System.out.println("jjj"+date);
                            String output = outputFormat.format(date);
                            //System.out.println(mainList.get(i).getDate()+" jjj " + output);
                            success.setCreatedDate(output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        quotationList.add(success);

                    }
                    QuotationListAdapter mAdapter = new QuotationListAdapter(this, quotationList, myContact);
                    quotationRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
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
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void notifyString(String str) {

    }

    private void clickButton() {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialog.setTitle("Quotation for vehicle");
        alertDialog.setMessage("Enter amount");

        final EditText input = new EditText(this);
        input.setHint("Amount");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        final EditText inputQuery = new EditText(this);
        inputQuery.setHint("Enter Query If Any..");

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(input);
        ll.addView(inputQuery);

//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
        alertDialog.setView(ll);
        // alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String Amount = input.getText().toString();
                            String query = inputQuery.getText().toString();
                            if (Amount.equals("")) {
                                input.setError("Please Enter Amount");
                                Toast.makeText(MyVehicleQuotationListActivity.this, "Price should not be empty", Toast.LENGTH_LONG).show();
                            } else {

                                try {
                                    if (mTestConnection.isConnectedToInternet()) {
                                        //JSON to Gson conversion
                                        Gson gson = new GsonBuilder()
                                                .setLenient()
                                                .create();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(getString(R.string.base_url))
                                                .addConverterFactory(GsonConverterFactory.create(gson))
                                                .client(initLog().build())
                                                .build();

                                        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
                                        Call<String> addBid = serviceApi._autokattaAddQuotation(bundle_VehicleId, bundle_GroupId,
                                                myContact, Double.parseDouble(Amount), bundle_Type, query);
                                        addBid.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if (response.isSuccessful()) {
                                                    String result;
                                                    result = response.body();

                                                    if (result.equals("success")) {
                                                        CustomToast.customToast(MyVehicleQuotationListActivity.this, "Price Sent");
                                                        getMyQuotationList(bundle_GroupId, bundle_VehicleId, bundle_Type);
                                                    }

                                                } else {
                                                    Log.e("No", "Response");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                                    } else
                                        CustomToast.customToast(MyVehicleQuotationListActivity.this, getString(R.string.no_internet));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener()

                {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    /***
     * Retrofit Logs
     ***/
    private OkHttpClient.Builder initLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
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
