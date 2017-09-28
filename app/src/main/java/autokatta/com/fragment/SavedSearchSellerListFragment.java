package autokatta.com.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import autokatta.com.R;
import autokatta.com.adapter.SavedSearchSellerListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SellerResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 13/4/17.
 */

public class SavedSearchSellerListFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    public SavedSearchSellerListFragment() {
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    List<SellerResponse.Success.MatchedResult> myUploadedVehiclesResponseList;
    RecyclerView mRecyclerView;
    boolean hasViewCreated = false;
    TextView mNoData;
    String myContact;
    View myVehicles;
    SavedSearchSellerListAdapter adapter;
    SharedPreferences mSharedPreferences;
    String b_category, b_brand, b_model, b_version, b_manu_year, b_rto_city, b_price;
    TextView textcategory, textbrand, textmodel, textprice, textyear, textsearchdate, BuyerLeads, Stopdate;
    int b_search_id;
    ImageView editImg, deleteData, favImg, unfavImg, autoshare;
    Button Stopsearch, Startsearch;
    RelativeLayout relativeLayout1, relativeLayout2;
    ConnectionDetector mTestConnection;
    LinearLayout llSearchDate, llBuyerLead;
    RelativeLayout relativeLayout;
    Button compare;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myVehicles = inflater.inflate(R.layout.saved_search_seller_list, container, false);

        return myVehicles;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTestConnection = new ConnectionDetector(getActivity());

        mNoData = (TextView) myVehicles.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        myContact = mSharedPreferences.getString("loginContact", "");

        mSwipeRefreshLayout = (SwipeRefreshLayout) myVehicles.findViewById(R.id.swipeRefreshLayoutMySearchSellerlst);
        mRecyclerView = (RecyclerView) myVehicles.findViewById(R.id.sellerlist);

        textcategory = (TextView) myVehicles.findViewById(R.id.mysearch_category);
        textbrand = (TextView) myVehicles.findViewById(R.id.mysearch_brand);
        textmodel = (TextView) myVehicles.findViewById(R.id.mysearch_model);
        textprice = (TextView) myVehicles.findViewById(R.id.mysearch_price);
        textyear = (TextView) myVehicles.findViewById(R.id.mysearch_year);
        textsearchdate = (TextView) myVehicles.findViewById(R.id.textView8);
        BuyerLeads = (TextView) myVehicles.findViewById(R.id.textView9);

        editImg = (ImageView) myVehicles.findViewById(R.id.editpen);
        deleteData = (ImageView) myVehicles.findViewById(R.id.deletevehi);
        favImg = (ImageView) myVehicles.findViewById(R.id.favsearch);
        unfavImg = (ImageView) myVehicles.findViewById(R.id.unfavsearch);
        autoshare = (ImageView) myVehicles.findViewById(R.id.sharesearch);
        Stopsearch = (Button) myVehicles.findViewById(R.id.stopsearch);
        Startsearch = (Button) myVehicles.findViewById(R.id.startsearch);

        relativeLayout1 = (RelativeLayout) myVehicles.findViewById(R.id.relbutton);
        relativeLayout2 = (RelativeLayout) myVehicles.findViewById(R.id.relnote);
        llSearchDate = (LinearLayout) myVehicles.findViewById(R.id.llDate);
        llBuyerLead = (LinearLayout) myVehicles.findViewById(R.id.llLeads);
        Stopdate = (TextView) myVehicles.findViewById(R.id.txtdate);

        relativeLayout = (RelativeLayout) myVehicles.findViewById(R.id.tablerow1);
        compare = (Button) myVehicles.findViewById(R.id.conpare);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        try {

            Bundle b = getArguments();
            b_search_id = b.getInt("search_id");
            b_category = b.getString("category");
            b_brand = b.getString("brand");
            b_model = b.getString("model");
            b_manu_year = b.getString("year");
            b_rto_city = b.getString("rto_city");
            b_price = b.getString("price");
        } catch (Exception e) {
            e.printStackTrace();
        }


        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getSellerdata(myContact);

            }
        });

        textcategory.setText(b_category);
        textbrand.setText(b_brand);
        textmodel.setText(b_model);
        textprice.setText(b_price);
        textyear.setText(b_manu_year);


        relativeLayout1.setVisibility(View.GONE);
        relativeLayout2.setVisibility(View.GONE);
        textsearchdate.setVisibility(View.GONE);
        BuyerLeads.setVisibility(View.GONE);
        editImg.setVisibility(View.GONE);
        deleteData.setVisibility(View.GONE);
        favImg.setVisibility(View.GONE);
        unfavImg.setVisibility(View.GONE);
        autoshare.setVisibility(View.GONE);
        llSearchDate.setVisibility(View.GONE);
        llBuyerLead.setVisibility(View.GONE);

    }

    private void getSellerdata(String myContact) {

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getSavedSearchSellerList(myContact);
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        getSellerdata(myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {

        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

        if (response != null) {
            if (response.isSuccessful()) {

                myUploadedVehiclesResponseList = new ArrayList<>();
                SellerResponse sellerResponse = (SellerResponse) response.body();

                if (!sellerResponse.getSuccess().getMatchedResult().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    myUploadedVehiclesResponseList.clear();
                    for (SellerResponse.Success.MatchedResult found : sellerResponse.getSuccess().getMatchedResult()) {

                        if (found.getSearchId() == b_search_id) {

                            found.setVehicleId(found.getVehicleId());
                            found.setTitle(found.getTitle());
                            found.setCategory(found.getCategory());
                            found.setManufacturer(found.getManufacturer());
                            found.setModel(found.getModel());
                            found.setYearOfManufacture(found.getYearOfManufacture());
                            found.setLocationCity(found.getLocationCity());
                            found.setVersion(found.getVersion());
                            found.setRtoCity(found.getRtoCity());
                            found.setUsername(found.getUsername());
                            found.setProfilePic(found.getProfilePic());
                            found.setContactNo(found.getContactNo());
                            found.setFavstatus(found.getFavstatus());

                            Date d = null, d1 = null;
                            try {
                                d = f.parse(found.getLastcall());
                                d1 = f.parse(found.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            found.setLastCallDateNew(d);
                            found.setUploaddate(d1);

                            //to set buyer last call date
                            try {
                                TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                //format of date coming from services
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                inputFormat.setTimeZone(utc);

                                //format of date which we want to show
                                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                outputFormat.setTimeZone(utc);

                                Date date = inputFormat.parse(found.getLastcall());
                                String output = outputFormat.format(date);

                                found.setLastcall(output);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //to set vehicle uploaded date
                            try {
                                TimeZone utc = TimeZone.getTimeZone("etc/UTC");
                                //format of date coming from services
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                inputFormat.setTimeZone(utc);

                                //format of date which we want to show
                                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                outputFormat.setTimeZone(utc);

                                Date date = inputFormat.parse(found.getDate());
                                String output = outputFormat.format(date);

                                found.setDate(output);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            myUploadedVehiclesResponseList.add(found);
                        }


                    }

                    if (myUploadedVehiclesResponseList.size() != 0) {
                        mSwipeRefreshLayout.setRefreshing(false);

                        adapter = new SavedSearchSellerListAdapter(getActivity(), myUploadedVehiclesResponseList, b_search_id,
                                b_category, b_brand, b_model, b_manu_year, b_rto_city, relativeLayout, compare);
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        if (isAdded())
                            CustomToast.customToast(getActivity(), "No Seller Leads Found");
                        mSwipeRefreshLayout.setRefreshing(false);
                        mNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    //CustomToast.customToast(getActivity(), "No Seller Leads Found");
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string._404));
            }


        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "SavedSearchSellerListFragment Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                getSellerdata(myContact);
                hasViewCreated = true;
            }
        }
    }
}
