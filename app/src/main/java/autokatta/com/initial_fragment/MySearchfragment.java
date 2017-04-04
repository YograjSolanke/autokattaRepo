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
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
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
    List<MySearchResponse.Success> mySearchResponseList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mySearch = inflater.inflate(R.layout.fragment_my_search, container, false);


        apiCall = new ApiCall(getActivity(), this);
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        final String myContact = mSharedPreferences.getString("loginContact", "7841023392");

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
                apiCall.MySearchResult(myContact);
            }
        });

        return mySearch;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {

            if (response.isSuccessful()) {

                MySearchResponse mySearchResponse = (MySearchResponse) response.body();
                if (!mySearchResponse.getSuccess().isEmpty()) {
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
                        mySearchSuccess.setSearchdate(mySearchSuccess.getSearchdate());
                        mySearchSuccess.setStopdate(mySearchSuccess.getStopdate());

                        mySearchResponseList.add(mySearchSuccess);
                    }
                    Log.i("sizeS", String.valueOf(mySearchResponseList.size()));
                    mSwipeRefreshLayout.setRefreshing(false);
                    MySearchAdapter adapter = new MySearchAdapter(getActivity(), mySearchResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "MySearchActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
