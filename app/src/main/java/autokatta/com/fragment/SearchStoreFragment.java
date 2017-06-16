package autokatta.com.fragment;

import android.app.ProgressDialog;
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
    private ProgressDialog dialog;

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
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
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

       /* Log.i("ssss",category);
        if (category.endsWith(","))
            category = category.substring(0,category.length()-1);
        category = category.trim();*/

        category = category.replaceAll(", $", "");
        Log.i("ssss", category);

        phrase = b.getString("phrase");
        radius = b.getString("radius");
        brands = b.getString("brands");

       /* if (!storecontact.equalsIgnoreCase(""))
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
        }*/
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
        dialog.show();
        apiCall.SearchStore(myContact, storecontact, location, finalCategory, phrase, radius, brands);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        getData(myContact, storecontact, location, finalCategory, phrase, radius, brands);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
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
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
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
        }else {
            Log.i("Check Class-", "Search Store Fragment");
            error.printStackTrace();
        }
    }

    /*public void showMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void errorMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData(myContact, storecontact, location, finalCategory, phrase, radius, brands);
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }*/

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
        getData(myContact, storecontact, location, finalCategory, phrase, radius, brands);
    }
}
