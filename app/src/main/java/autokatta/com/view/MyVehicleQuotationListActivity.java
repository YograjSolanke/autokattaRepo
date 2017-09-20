package autokatta.com.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.QuotationListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyVehicleQuotationListResponse;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;

public class MyVehicleQuotationListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    RecyclerView quotationRecycler;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mNoData;
    String myContact;
    ConnectionDetector mTestConnection;
    LinearLayoutManager mLinearLayoutManager;
    private static int firstVisibleInListview;
    TextView Title, Category, Brand, Model, Keyword, Price;
    RelativeLayout relCategory, relBrand, relModel, relPrice, MainRel;
    ImageView Image;
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


        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
        quotationRecycler = (RecyclerView) findViewById(R.id.quotationList);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mNoData = (TextView) findViewById(R.id.no_category);
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
        Keyword.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutQuotList);

        mSwipeRefreshLayout.setOnRefreshListener(this);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }


                Intent i = getIntent();
                Title.setText(i.getStringExtra("Title"));
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
                                getQuotationList(bundle_GroupId, bundle_VehicleId, bundle_Type);
                            } else {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }


                    });
                }

            }
        });



    }

    private void getQuotationList(int bundle_groupId, int bundle_vehicleId, String bundle_type) {

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(this, this);
            mApiCall.GetVehicleQuotationList(bundle_groupId, bundle_vehicleId, bundle_type);
        } else
            CustomToast.customToast(this, getString(R.string.no_internet));
    }

    @Override
    public void onRefresh() {

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

    }

    @Override
    public void notifyString(String str) {

    }
}
