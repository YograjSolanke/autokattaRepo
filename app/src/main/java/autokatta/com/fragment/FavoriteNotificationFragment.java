package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-003 on 20/4/17.
 */

public class FavoriteNotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mFavNotify;
    String nextCount = "0";


    public FavoriteNotificationFragment() {
        //Empty Constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFavNotify = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mRecyclerView = (RecyclerView) mFavNotify.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mFavNotify.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getFavouriteData();
            }
        });

        return mFavNotify;
    }

    private void getFavouriteData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.FavouriteNotification(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                getString("loginContact", "7841023392"), nextCount);
    }

    /*private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(initLog().build())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        SampleResponse sampleResponse = new SampleResponse("8007855589","0");
        Call<FavouriteResponse> addBid = serviceApi.getMyFavourites(sampleResponse);
        addBid.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                if (response.isSuccessful()) {

                } else {
                    Log.e("No", "Response");
                }
            }


            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {

            }
        });

    }*/

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

