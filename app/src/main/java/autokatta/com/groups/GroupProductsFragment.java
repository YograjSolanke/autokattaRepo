package autokatta.com.groups;

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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupProductAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 24/4/17.
 */

public class GroupProductsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mProduct;
    String myContact, mGroupType;
    int mGroupId;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<StoreInventoryResponse.Success.Product> productList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    GroupProductAdapter adapter;
    boolean _hasLoadedOnce = false;
    ConnectionDetector mTestConnection;
    TextView mNoData;
    Activity activity;
    RelativeLayout filterToHide;

    public GroupProductsFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProduct = inflater.inflate(R.layout.store_product_fragment, container, false);
        return mProduct;
    }

    private void getProducts(int GroupId) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getGroupProducts(GroupId, "");
        } else {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        getProducts(mGroupId);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mSwipeRefreshLayout.setRefreshing(false);
                productList.clear();
                String storeContact = null;
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
                    adapter = new GroupProductAdapter(getActivity(), productList, myContact, storeContact, mGroupType);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
//                if(isAdded())
//                CustomToast.customToast(getActivity(), getString(R.string._404_));
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
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
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
                    , "groupproductfragent");
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
            if (isVisibleToUser && !_hasLoadedOnce) {
                getProducts(mGroupId);
                _hasLoadedOnce = true;
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
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
                mSwipeRefreshLayout = (SwipeRefreshLayout) mProduct.findViewById(R.id.swipeRefreshLayout);
                mNoData = (TextView) mProduct.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                filterToHide = (RelativeLayout) mProduct.findViewById(R.id.rel);
                filterToHide.setVisibility(View.GONE);
                mRecyclerView = (RecyclerView) mProduct.findViewById(R.id.recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);

                Bundle bundle = getArguments();
                if (bundle != null) {
                    mGroupType = bundle.getString("grouptype");
                    mGroupId = bundle.getInt("bundle_GroupId");
                    if (bundle.getString("bundle_Contact") != null) {
                        myContact = bundle.getString("bundle_Contact");
                    }

                }
                Log.i("Group", "ProductsContact->" + myContact);
                Log.i("Group", "ProductsType->" + mGroupType);
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getProducts(mGroupId);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }
}
