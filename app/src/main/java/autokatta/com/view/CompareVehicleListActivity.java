package autokatta.com.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

public class CompareVehicleListActivity extends AppCompatActivity implements RequestNotifier {

    TextView companyname, insurencevaltext, insurenceIDVtext, ownertext, fueltext, colortext, rctext, insvaltext, taxpaidtext, fitnesstext, permitvaltext, seatingtext;
    TextView permittext, hypotext, drivetext, transmissiontext, bodytext, boattext, rvtext, applicationtext, citytext, askpricetext, kmdriventext;
    TextView tyretext, bustypetext, airtext, invoicetext, impltext, registrationtext, enginetext, chassictext, makeyeartext, taxavailabeltext;
    TextView modeltext, versiontext, regyeartext, regcitytext;
    ImageView imgvehi;
    RecyclerView mHorizontalRecyclerView;
    int content = 0;
    TextView textcount;
    String vehicle_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_vehicle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.comphorizontl);

        textcount = (TextView) findViewById(R.id.countcomp);

        companyname = (TextView) findViewById(R.id.txtcompname);
        modeltext = (TextView) findViewById(R.id.txtmodel);
        versiontext = (TextView) findViewById(R.id.txtversion);
        insurencevaltext = (TextView) findViewById(R.id.txtinsurenceval);
        insurenceIDVtext = (TextView) findViewById(R.id.txtinsurenceIdv);
        ownertext = (TextView) findViewById(R.id.txtowner);
        citytext = (TextView) findViewById(R.id.txtcity);
        regcitytext = (TextView) findViewById(R.id.txtregcity);
        regyeartext = (TextView) findViewById(R.id.txtregyr);
        rctext = (TextView) findViewById(R.id.txtrcavailabel);
        taxpaidtext = (TextView) findViewById(R.id.txttaxpaid);
        fitnesstext = (TextView) findViewById(R.id.txtfeatness);
        seatingtext = (TextView) findViewById(R.id.txtseatingcap);
        permitvaltext = (TextView) findViewById(R.id.txtpermit);
        hypotext = (TextView) findViewById(R.id.txthypo);
        drivetext = (TextView) findViewById(R.id.txtdrivetype);
        bodytext = (TextView) findViewById(R.id.txtbodystyle);
        applicationtext = (TextView) findViewById(R.id.txtappl);
        tyretext = (TextView) findViewById(R.id.txttyrecon);
        bustypetext = (TextView) findViewById(R.id.txtbustype);
        airtext = (TextView) findViewById(R.id.txtair);
        invoicetext = (TextView) findViewById(R.id.txtinvoice);
        impltext = (TextView) findViewById(R.id.txtimpl);
        fueltext = (TextView) findViewById(R.id.txtfualtype);
        colortext = (TextView) findViewById(R.id.txtcolor);
        registrationtext = (TextView) findViewById(R.id.txtregno);
        enginetext = (TextView) findViewById(R.id.txtengineno);
        chassictext = (TextView) findViewById(R.id.txtchessicno);
        askpricetext = (TextView) findViewById(R.id.txtprice);
        kmdriventext = (TextView) findViewById(R.id.txtkm);
        makeyeartext = (TextView) findViewById(R.id.txtmakeyr);
        taxavailabeltext = (TextView) findViewById(R.id.txttaxavailable);

        imgvehi = (ImageView) findViewById(R.id.imgvehicle);

        mHorizontalRecyclerView.setHasFixedSize(true);
        mHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (getIntent().getExtras() != null) {
            vehicle_ids = getIntent().getExtras().getString("vehicle_ids");
            getComparisonData(vehicle_ids);
        }
    }

    private void getComparisonData(String vehicle_ids) {
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

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                /*GetGroupQuotationResponse quotationResponse = (GetGroupQuotationResponse) response.body();
                if (!quotationResponse.getSuccess().getUsedVehicle().isEmpty()) {
                    //mNoData.setVisibility(View.GONE);
                    groupQuotationList.clear();
                    for (GetGroupQuotationResponse.Success.UsedVehicle success : quotationResponse.getSuccess().getUsedVehicle()) {



                        groupQuotationList.add(success);

                    }
                    GetGroupQuotationAdapter mAdapter = new GetGroupQuotationAdapter(this, groupQuotationList, mLoginContact,
                            mGrpId);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();*/
            } else {
                    /*mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);*/
            }
        } else {
            //mSwipeRefreshLayout.setRefreshing(false);
        }
         /*else{
            // mSwipeRefreshLayout.setRefreshing(false);
        }*/
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
