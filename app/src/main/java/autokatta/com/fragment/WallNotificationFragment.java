package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;

/**
 * Created by ak-001 on 17/3/17.
 */

public class WallNotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mWallNotify;
    TextView mNoData;
    boolean _hasLoadedOnce = false;

    public WallNotificationFragment(){
        //Empty Constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWallNotify = inflater.inflate(R.layout.fragment_wall_notification, container, false);
        return mWallNotify;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                //getData();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNoData = (TextView) mWallNotify.findViewById(R.id.no_category);
                mRecyclerView = (RecyclerView) mWallNotify.findViewById(R.id.wall_recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mWallNotify.findViewById(R.id.wall_swipe_refresh_layout);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        //getData();
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }
}
