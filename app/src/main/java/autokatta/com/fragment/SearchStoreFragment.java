package autokatta.com.fragment;

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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.ViewSearchedStoreAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SearchStoreResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 21/3/17.
 */

public class SearchStoreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mSearchStore;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<SearchStoreResponse.Success> searchStoreResponseArrayList = new ArrayList<>();

    public SearchStoreFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchStore = inflater.inflate(R.layout.fragment_search_store, container, false);

        mRecyclerView = (RecyclerView) mSearchStore.findViewById(R.id.recyclerSearchStore);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mSearchStore.findViewById(R.id.swipeRefreshLayoutSearchStore);

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


        Bundle b = getArguments();
        final String myContact = b.getString("myContact");
        final String storecontact = b.getString("contact_to_search");
        final String location = b.getString("location");
        String category = b.getString("category");

        if (category.equalsIgnoreCase("Select Category"))
            category = "";

        final String phrase = b.getString("phrase");
        final String radius = b.getString("radius");
        final String brands = b.getString("brands");
        final int radiuspos = b.getInt("radiuspos");

        Log.i("brand", ":" + brands);
        final ApiCall apiCall = new ApiCall(getActivity(), this);
        final String finalCategory = category;
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                //API Call
                apiCall.SearchStore(myContact, storecontact, location, finalCategory, phrase, radius, brands);

            }
        });

        return mSearchStore;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                SearchStoreResponse searchStoreResponse = (SearchStoreResponse) response.body();

                if (!searchStoreResponse.getSuccess().isEmpty()) {

                    for (SearchStoreResponse.Success searchSuccess : searchStoreResponse.getSuccess()) {
                        searchSuccess.setStoreId(searchSuccess.getStoreId());
                        searchSuccess.setStoreName(searchSuccess.getStoreName());
                        searchSuccess.setStoreImage(searchSuccess.getStoreImage());
                        searchSuccess.setLocation(searchSuccess.getLocation());
                        searchSuccess.setWebsite(searchSuccess.getWebsite());
                        searchSuccess.setOpenTime(searchSuccess.getOpenTime());
                        searchSuccess.setCloseTime(searchSuccess.getCloseTime());
                        searchSuccess.setRatings(searchSuccess.getRatings());
                        searchSuccess.setAddress(searchSuccess.getAddress());
                        searchSuccess.setCategory(searchSuccess.getCategory());
                        searchSuccess.setStoreType(searchSuccess.getStoreType());
                        searchSuccess.setWorkingDays(searchSuccess.getWorkingDays());
                        searchSuccess.setContact(searchSuccess.getContact());
                        searchSuccess.setLikestatus(searchSuccess.getLikestatus());
                        searchSuccess.setLikecount(searchSuccess.getLikecount());
                        searchSuccess.setFollowstatus(searchSuccess.getFollowstatus());
                        searchSuccess.setFollowcount(searchSuccess.getFollowcount());
                        searchSuccess.setRating(searchSuccess.getRating());

                        searchStoreResponseArrayList.add(searchSuccess);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    ViewSearchedStoreAdapter adapter = new ViewSearchedStoreAdapter(getActivity(), searchStoreResponseArrayList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else
                    mSwipeRefreshLayout.setRefreshing(false);

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
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Search Store Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
