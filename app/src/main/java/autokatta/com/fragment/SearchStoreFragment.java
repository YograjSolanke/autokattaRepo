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

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

/**
 * Created by ak-003 on 21/3/17.
 */

public class SearchStoreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mSearchStore;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

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
        final int radiuspos = b.getInt("radiuspos");

        final ApiCall apiCall = new ApiCall(getActivity(), this);
        final String finalCategory = category;
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                //API Call
                apiCall.SearchStore(myContact, storecontact, location, finalCategory, phrase, radius);

            }
        });

        return mSearchStore;
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
