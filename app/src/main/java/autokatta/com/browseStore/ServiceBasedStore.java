

package autokatta.com.browseStore;

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
import autokatta.com.adapter.BrowseStoreAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BrowseStoreResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 20/3/17.
 */

public class ServiceBasedStore extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    View mProductBased;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<BrowseStoreResponse.Success> mSuccesses;

    public ServiceBasedStore() {
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductBased = inflater.inflate(R.layout.fragment_product_based_store, container, false);
        mRecyclerView = (RecyclerView) mProductBased.findViewById(R.id.BrowseStore_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mProductBased.findViewById(R.id.swipeRefreshLayoutBrowseStore);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //getData();//Get Api...
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getStoreData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", ""));
            }
        });

        return mProductBased;
    }

    private void getStoreData(String contact) {

        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.getBrowseStores(contact, "Service");
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSuccesses = new ArrayList<>();
                BrowseStoreResponse browseStoreResponse = (BrowseStoreResponse) response.body();
                for (BrowseStoreResponse.Success success : browseStoreResponse.getSuccess()) {
                    success.setStoreId(success.getStoreId());
                    success.setContactNo(success.getContactNo());
                    success.setStoreName(success.getStoreName());
                    success.setStoreImage(success.getStoreImage());
                    success.setLocation(success.getLocation());
                    success.setCategory(success.getCategory());
                    success.setWebsite(success.getWebsite());
                    success.setStoreType(success.getStoreType());
                    success.setWorkingDays(success.getWorkingDays());
                    success.setModifiedDate(success.getModifiedDate());
                    success.setLikestatus(success.getLikestatus());
                    success.setFollowstatus(success.getFollowstatus());
                    success.setLikecount(success.getLikecount());
                    success.setFollowcount(success.getFollowcount());
                    success.setRating(success.getRating());
                    mSuccesses.add(success);

                }
                BrowseStoreAdapter adapter = new BrowseStoreAdapter(getActivity(), mSuccesses);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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
            Log.i("Check Class-", "ServiceBasedStore Fragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

    }
}

