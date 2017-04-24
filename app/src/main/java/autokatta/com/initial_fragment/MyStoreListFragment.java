package autokatta.com.initial_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import autokatta.com.R;
import autokatta.com.adapter.MyStoreListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.my_store.CreateStoreFragment;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyStoreResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 28/3/17.
 */

public class MyStoreListFragment extends Fragment implements View.OnClickListener, RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    View mMyStoreList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    FloatingActionButton fabCreateStore;
    ArrayList<MyStoreResponse.Success> storeResponseArrayList;
    ApiCall apiCall;
    String myContact;

    public MyStoreListFragment() {
        //empty fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyStoreList = inflater.inflate(R.layout.fragment_mystorelist, container, false);

        fabCreateStore = (FloatingActionButton) mMyStoreList.findViewById(R.id.fabCreateStore);
        mRecyclerView = (RecyclerView) mMyStoreList.findViewById(R.id.rv_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mMyStoreList.findViewById(R.id.swipeRefreshLayoutMyStoreList);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392");

        apiCall = new ApiCall(getActivity(), this);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        fabCreateStore.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                apiCall.MyStoreList(myContact);
            }
        });

        return mMyStoreList;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                storeResponseArrayList = new ArrayList<>();
                storeResponseArrayList.clear();

                MyStoreResponse myStoreResponse = (MyStoreResponse) response.body();


                if (!myStoreResponse.getSuccess().isEmpty()) {
                    for (MyStoreResponse.Success Sresponse : myStoreResponse.getSuccess()) {

                        Sresponse.setId(Sresponse.getId());
                        Sresponse.setName(Sresponse.getName());
                        Sresponse.setLocation(Sresponse.getLocation());
                        Sresponse.setWebsite(Sresponse.getWebsite());
                        Sresponse.setStoreOpenTime(Sresponse.getStoreOpenTime());
                        Sresponse.setStoreCloseTime(Sresponse.getStoreCloseTime());
                        Sresponse.setStoreImage(Sresponse.getStoreImage());
                        Sresponse.setCoverImage(Sresponse.getCoverImage());
                        Sresponse.setWorkingDays(Sresponse.getWorkingDays());
                        Sresponse.setRating(Sresponse.getRating());
                        Sresponse.setLikecount(Sresponse.getLikecount());
                        Sresponse.setFollowcount(Sresponse.getFollowcount());
                        Sresponse.setStoreType(Sresponse.getStoreType());

                        storeResponseArrayList.add(Sresponse);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    MyStoreListAdapter adapter = new MyStoreListAdapter(getActivity(), storeResponseArrayList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Log.i("Store list size=>", String.valueOf(storeResponseArrayList.size()));
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
                    , "MyStoreListFragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fabCreateStore:
                Bundle bundle = new Bundle();
                bundle.putString("className", "MyStoreListFragment");
                CreateStoreFragment createStoreFragment = new CreateStoreFragment();
                createStoreFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.myStoreListFrame, createStoreFragment).addToBackStack("mystorelist").commit();

        }
    }

    @Override
    public void onRefresh() {

    }
}
