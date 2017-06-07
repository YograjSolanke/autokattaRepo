package autokatta.com.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
    boolean hasViewCreated = false;
    TextView mNoData;
    String myContact, storecontact, location, phrase, radius, brands, finalCategory;
    ViewSearchedStoreAdapter adapter;

    public SearchStoreFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchStore = inflater.inflate(R.layout.fragment_search_store, container, false);
        return mSearchStore;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mSearchStore.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
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
        myContact = b.getString("myContact");
        storecontact = b.getString("contact_to_search");
        location = b.getString("location");
        String category = b.getString("category");
        phrase = b.getString("phrase");
        radius = b.getString("radius");
        brands = b.getString("brands");

        if (!storecontact.equalsIgnoreCase(""))
        {
            getActivity().setTitle(storecontact);
        }
        if (!location.equalsIgnoreCase(""))
        {
            getActivity().setTitle(location);
        }
        if (!category.equalsIgnoreCase("")&&!category.equalsIgnoreCase("Select Category"))
        {
            getActivity().setTitle(category);
        }

        if (category.equalsIgnoreCase("Select Category"))
            category = "";

        if (!phrase.equalsIgnoreCase(""))
        {
            getActivity().setTitle(phrase);
        }
        if (!radius.equalsIgnoreCase("")&&!radius.equalsIgnoreCase("Select radius"))
        {
            getActivity().setTitle(radius);
        }
        if (!brands.equalsIgnoreCase(""))
        {
            getActivity().setTitle(brands);
        }
        final int radiuspos = b.getInt("radiuspos");

        finalCategory = category;
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getData(myContact, storecontact, location, finalCategory, phrase, radius, brands);
            }
        });

    }

    private void getData(String myContact, String storecontact, String location, String finalCategory, String phrase, String radius, String brands) {
        final ApiCall apiCall = new ApiCall(getActivity(), this);
        //API Call
        apiCall.SearchStore(myContact, storecontact, location, finalCategory, phrase, radius, brands);
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
                    mNoData.setVisibility(View.GONE);
                    searchStoreResponseArrayList.clear();
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
                    adapter = new ViewSearchedStoreAdapter(getActivity(), searchStoreResponseArrayList);
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
            Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.BLUE);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.BLUE);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } else {
            Log.i("Check Class-", "Search Store Fragment");
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
                getData(myContact, storecontact, location, finalCategory, phrase, radius, brands);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
