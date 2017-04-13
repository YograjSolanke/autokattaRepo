package autokatta.com.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 13/4/17.
 */

public class SavedSearchSellerListFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    public SavedSearchSellerListFragment() {

    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    View myVehicles;
    SharedPreferences mSharedPreferences;
    String search_id;
    TextView textcategory, textbrand, textmodel, textprice, textyear, textsearchdate, BuyerLeads, Stopdate;
    ImageView editImg, deleteData, favImg, unfavImg, share;
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
            search_id = b.getString("search_id");
            // getSellerData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        relativeLayout1.setVisibility(View.GONE);
        relativeLayout2.setVisibility(View.GONE);
        textsearchdate.setVisibility(View.GONE);
        BuyerLeads.setVisibility(View.GONE);
        editImg.setVisibility(View.GONE);
        deleteData.setVisibility(View.GONE);
        favImg.setVisibility(View.GONE);
        unfavImg.setVisibility(View.GONE);
        share.setVisibility(View.GONE);
        return myVehicles;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
