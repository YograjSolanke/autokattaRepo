package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreProductAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.OnLoadMoreListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.other.VerticalLineDecorator;
import autokatta.com.response.StoreInventoryResponse;
import autokatta.com.response.StoreInventoryResponse.Success.Product;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 22/5/17.
 */

public class MemberProductFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mProduct;
    String myContact;
    int mGroupId;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<Product> productList = new ArrayList<>();
    String mBundleContact;
    LinearLayoutManager mLayoutManager;
    StoreProductAdapter adapter;
    ConnectionDetector mTestConnection;
    boolean _hasLoadedOnce = false;
    Activity activity;
    TextView mNoData;
    String storeContact;
    int index = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProduct = inflater.inflate(R.layout.member_product_fragment, container, false);
        return mProduct;
    }


    private void getProducts(int GroupId, int pageNo, int viewRecords) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getGroupProducts(GroupId, mBundleContact, pageNo, viewRecords);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        getProducts(mGroupId, 1, 10);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser && !_hasLoadedOnce) {
                getProducts(mGroupId, 1, 10);
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                //productList.clear();
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (storeResponse.getSuccess().getProduct().size() > 0) {
                    if (!storeResponse.getSuccess().getProduct().isEmpty()) {
                        mNoData.setVisibility(View.GONE);
                        for (StoreInventoryResponse.Success.Product success : storeResponse.getSuccess().getProduct()) {
                            success.setProductId(success.getProductId());
                            success.setName(success.getName());
                            success.setBrandtags(success.getBrandtags());
                            success.setPrice(success.getPrice());
                            success.setProductType(success.getProductType());
                            success.setProductDetails(success.getProductDetails());
                            success.setProductTags(success.getProductTags());
                            success.setProductImage(success.getProductImage());
                            success.setCategory(success.getCategory());
                            success.setProductlikestatus(success.getProductlikestatus());
                            success.setProductlikecount(success.getProductlikecount());
                            success.setProductrating(success.getProductrating());
                            success.setPrate(success.getPrate());
                            success.setPrate1(success.getPrate1());
                            success.setPrate2(success.getPrate2());
                            success.setPrate3(success.getPrate3());
                            storeContact = success.getStorecontact();
                            productList.add(success);
                        }
                        //adapter.notifyDataSetChanged();
                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mNoData.setVisibility(View.VISIBLE);
                    }
                } else {//result size 0 means there is no more data available at server
                    adapter.setMoreDataAvailable(false);
                    //telling adapter to stop calling load more as no more server data available
                    if (isAdded())
                        Toast.makeText(getActivity(), "No More Data Available", Toast.LENGTH_LONG).show();
                }
                //adapter.notifyDataSetChanged();
                adapter.notifyDataChanged();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                // CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (activity != null) {
                activity = getActivity();
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            //  CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "memberproductfragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    private void loadMore(int index) {
        //add loading progress view
        adapter.notifyItemInserted(productList.size());
        getProducts(mGroupId, index, 10);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mNoData = (TextView) mProduct.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                mSwipeRefreshLayout = (SwipeRefreshLayout) mProduct.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) mProduct.findViewById(R.id.recycler_view);

                Bundle getBundle = getArguments();
                mGroupId = getBundle.getInt("bundle_GroupId");
                mBundleContact = getBundle.getString("Rcontact");

                adapter = new StoreProductAdapter(getActivity(), productList, myContact, storeContact);
                adapter.setLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                //int index = myUploadedVehiclesResponseList.size() - 1;
                                index++;
                                Log.i("index", "->" + index);
                                loadMore(index);
                            }
                        });
                        //Calling loadMore function in Runnable to fix the
                        // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
                    }
                });
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);

                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getProducts(mGroupId, 1, 10);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

}
