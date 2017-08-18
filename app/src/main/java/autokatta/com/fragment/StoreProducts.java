package autokatta.com.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.widget.RelativeLayout;
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
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreProducts extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mProduct;
    String Sharedcontact, storeContact;
    int store_id;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<StoreInventoryResponse.Success.Product> productList;
    LinearLayoutManager mLayoutManager;
    RelativeLayout filterToHide;
    StoreProductAdapter adapter;
    boolean hasView = false;
    ConnectionDetector mTestConnection;
    TextView mNoData;
    Activity mActivity;

    public StoreProducts() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProduct = inflater.inflate(R.layout.store_product_fragment, container, false);
        return mProduct;
    }

    private void getStoreProducts(int store_id, String sharedcontact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getStoreInventory(store_id, sharedcontact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mNoData.setVisibility(View.GONE);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        productList.clear();
        getStoreProducts(store_id, Sharedcontact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                productList = new ArrayList<>();
                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
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
                    adapter = new StoreProductAdapter(getActivity(), productList, Sharedcontact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), R.string._404, Toast.LENGTH_SHORT).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.no_response, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "StoreProducts List");
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
            if (isVisibleToUser && !hasView) {
                getStoreProducts(store_id, Sharedcontact);
                hasView = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", null);
                mNoData = (TextView) mProduct.findViewById(R.id.no_category);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mProduct.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) mProduct.findViewById(R.id.recycler_view);
                filterToHide = (RelativeLayout) mProduct.findViewById(R.id.rel);
                filterToHide.setVisibility(View.GONE);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                Bundle bundle = getArguments();
                store_id = bundle.getInt("store_id");
                //getData();//Get Api...
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getStoreProducts(store_id, Sharedcontact);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = getActivity();
        }
    }
}
