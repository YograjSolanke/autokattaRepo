package autokatta.com.initial_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.adapter.MyStoreListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.my_store.CreateStoreFragment;
import autokatta.com.networkreceiver.ConnectionDetector;
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
    boolean hasViewCreated = false;
    ConnectionDetector mTestConnection;
    LinearLayoutManager mLinearLayoutManager;
    TextView mNoData;
    MyStoreListAdapter adapter;

    private static int firstVisibleInListview;


    public MyStoreListFragment() {
        //empty fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyStoreList = inflater.inflate(R.layout.fragment_mystorelist, container, false);
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
                    mNoData.setVisibility(View.GONE);
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
                        //getActivity().setTitle("My Store");
                        storeResponseArrayList.add(Sresponse);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter = new MyStoreListAdapter(getActivity(), storeResponseArrayList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);


                    Bundle bundle = new Bundle();
                    bundle.putString("className", "MyStoreListFragment");
                    CreateStoreFragment createStoreFragment = new CreateStoreFragment();
                    createStoreFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.myStoreListFrame, createStoreFragment, "createStoreFragment")
                            .addToBackStack("createStoreFragment")
                            .commitAllowingStateLoss();
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "MyStoreListFragment");
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
                        apiCall.MyStoreList(myContact);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabCreateStore:
                Bundle bundle = new Bundle();
                bundle.putString("className", "MyStoreListFragment");
                CreateStoreFragment createStoreFragment = new CreateStoreFragment();
                createStoreFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.myStoreListFrame, createStoreFragment, "createStoreFragment")
                        .addToBackStack("createStoreFragment")
                        .commit();

                /*getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.myStoreListFrame, createStoreFragment, "createStoreFragment")
                        .addToBackStack("createStoreFragment")
                        .commit();*/

        }
    }

    @Override
    public void onRefresh() {
        apiCall.MyStoreList(myContact);
        mRecyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                apiCall.MyStoreList(myContact);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiCall = new ApiCall(getActivity(), this);
        mTestConnection = new ConnectionDetector(getActivity());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().setTitle("My Store");
                mNoData = (TextView) mMyStoreList.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                fabCreateStore = (FloatingActionButton) mMyStoreList.findViewById(R.id.fabCreateStore);
                mRecyclerView = (RecyclerView) mMyStoreList.findViewById(R.id.rv_recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMyStoreList.findViewById(R.id.swipeRefreshLayoutMyStoreList);
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mRecyclerView.setHasFixedSize(true);
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                firstVisibleInListview = mLinearLayoutManager.findFirstVisibleItemPosition();
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
            }
        });
        fabCreateStore.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scrolling up
                    fabCreateStore.hide(true);

                } else {
                    // Scrolling down
                    fabCreateStore.show(true);
                }
            }
        });



    }
}
