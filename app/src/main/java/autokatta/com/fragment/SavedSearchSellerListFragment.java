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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.SavedSearchSellerListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
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
    ApiCall apiCall;
    View myVehicles;
    SavedSearchSellerListAdapter adapter;
    SharedPreferences mSharedPreferences;
    String b_search_id, b_category, b_brand, b_model, b_version, b_manu_year, b_rto_city, b_price;
    TextView textcategory, textbrand, textmodel, textprice, textyear, textsearchdate, BuyerLeads, Stopdate;
    ImageView editImg, deleteData, favImg, unfavImg, share, autoshare;
    Button Stopsearch, Startsearch;
    RelativeLayout relativeLayout1, relativeLayout2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myVehicles = inflater.inflate(R.layout.saved_search_seller_list, container, false);


        apiCall = new ApiCall(getActivity(), this);
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        final String myContact = mSharedPreferences.getString("loginContact", "7841023392");

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
        share = (ImageView) myVehicles.findViewById(R.id.sharesearch);
        autoshare = (ImageView) myVehicles.findViewById(R.id.sharesearch1);
        Stopsearch = (Button) myVehicles.findViewById(R.id.stopsearch);
        Startsearch = (Button) myVehicles.findViewById(R.id.startsearch);

        relativeLayout1 = (RelativeLayout) myVehicles.findViewById(R.id.relbutton);
        relativeLayout2 = (RelativeLayout) myVehicles.findViewById(R.id.relnote);
        Stopdate = (TextView) myVehicles.findViewById(R.id.txtdate);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        try {

            Bundle b = getArguments();
            b_search_id = b.getString("search_id");
            b_category = b.getString("category");
            b_brand = b.getString("brand");
            b_model = b.getString("model");
            b_manu_year = b.getString("year");
            b_rto_city = b.getString("rto_city");
            b_price = b.getString("price");
            // getSellerData();
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
        share.setVisibility(View.GONE);
        autoshare.setVisibility(View.GONE);

        return myVehicles;
    }

    private void getSellerdata(String myContact) {
        apiCall.getSavedSearchSellerList(myContact);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void notifySuccess(Response<?> response) {

        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (response != null) {
            if (response.isSuccessful()) {
                //mSwipeRefreshLayout.setRefreshing(false);
                myUploadedVehiclesResponseList = new ArrayList<>();
                SellerResponse sellerResponse = (SellerResponse) response.body();

                if (!sellerResponse.getSuccess().getMatchedResult().isEmpty()) {
                    for (SellerResponse.Success.MatchedResult found : sellerResponse.getSuccess().getMatchedResult()) {

                        if (found.getSearchId().equals(b_search_id)) {

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
                            found.setLastcall(found.getLastcall());


                            Date d = null, d1 = null;
                            try {
                                d = f.parse(found.getLastcall());
                                d1 = f.parse(found.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            found.setLastCallDateNew(d);
                            found.setUploaddate(d1);

                            myUploadedVehiclesResponseList.add(found);
                        }


                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    adapter = new SavedSearchSellerListAdapter(getActivity(), myUploadedVehiclesResponseList, b_search_id,
                            b_category, b_brand, b_model, b_manu_year, b_rto_city);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else
                    CustomToast.customToast(getActivity(), "No Seller Leads Found");


            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }


        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {

        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "SavedSearchSellerListFragment Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
