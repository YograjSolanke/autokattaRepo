package autokatta.com.initial_fragment;

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

import autokatta.com.R;
import autokatta.com.adapter.MySearchAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MySearchResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 3/4/17.
 */

public class MySearchfragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mySearch;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    SharedPreferences mSharedPreferences;
    ApiCall apiCall;
    boolean hasViewCreated = false;
    TextView mNoData;
    String myContact;
    List<MySearchResponse.Success> mySearchResponseList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mySearch = inflater.inflate(R.layout.fragment_my_search, container, false);
        return mySearch;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mNoData = (TextView) mySearch.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        myContact = mSharedPreferences.getString("loginContact", "");

        mSwipeRefreshLayout = (SwipeRefreshLayout) mySearch.findViewById(R.id.swipeRefreshLayoutMySearch);
        mRecyclerView = (RecyclerView) mySearch.findViewById(R.id.recyclermySearch);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                getMySearch(myContact);
            }
        });
    }

    private void getMySearch(String myContact) {
        apiCall = new ApiCall(getActivity(), this);
        apiCall.MySearchResult(myContact);
    }

    @Override
    public void onRefresh() {
        getMySearch(myContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (response != null) {

            if (response.isSuccessful()) {

                MySearchResponse mySearchResponse = (MySearchResponse) response.body();
                if (!mySearchResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    mySearchResponseList.clear();
                    for (MySearchResponse.Success mySearchSuccess : mySearchResponse.getSuccess()) {

                        mySearchSuccess.setSearchId(mySearchSuccess.getSearchId());
                        mySearchSuccess.setCategory(mySearchSuccess.getCategory());
                        mySearchSuccess.setBrand(mySearchSuccess.getBrand());
                        mySearchSuccess.setModel(mySearchSuccess.getModel());
                        mySearchSuccess.setPrice(mySearchSuccess.getPrice());
                        mySearchSuccess.setYearOfManufactur(mySearchSuccess.getYearOfManufactur());
                        mySearchSuccess.setSearchstatus(mySearchSuccess.getSearchstatus());
                        mySearchSuccess.setBuyerLeads(mySearchSuccess.getBuyerLeads());
                        mySearchSuccess.setMysearchstatus(mySearchSuccess.getMysearchstatus());
//                        mySearchSuccess.setSearchdate(mySearchSuccess.getSearchdate());
//                        mySearchSuccess.setStopdate(mySearchSuccess.getStopdate());


                        Date d = null, d1 = null;
                        try {
                            d = f.parse(mySearchSuccess.getSearchdate());
                            d1 = f.parse(mySearchSuccess.getStopdate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        mySearchSuccess.setSearchDateNew(d);
                        mySearchSuccess.setStopDateNew(d1);

                        mySearchResponseList.add(mySearchSuccess);
                    }
                    Log.i("sizeS", String.valueOf(mySearchResponseList.size()));
                    mSwipeRefreshLayout.setRefreshing(false);
                    MySearchAdapter adapter = new MySearchAdapter(getActivity(), mySearchResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }

        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(),getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        }  else {
            Log.i("Check Class-"
                    , "MySearchActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getMySearch(myContact);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
