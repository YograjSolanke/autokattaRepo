package autokatta.com.groups;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

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
import autokatta.com.response.StoreInventoryResponse.Success.Product;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 22/5/17.
 */

public class MemberProductFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mProduct;
    String myContact;
    String mGroupId;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    List<Product> productList = new ArrayList<>();
    String mBundleContact;
    LinearLayoutManager mLayoutManager;
    StoreProductAdapter adapter;
    ConnectionDetector mTestConnection;
    KProgressHUD hud;
    private ImageButton mNoInternetIcon;
    private TextView mPlaceHolder;
    boolean _hasLoadedOnce = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProduct = inflater.inflate(R.layout.member_product_fragment, container, false);

        return mProduct;
    }

  /*  private void getProducts(String GroupId) {
        ApiCall apiCall = new ApiCall(getActivity(), this);
        //apiCall.getGroupProducts("512",myContact);
        apiCall.getGroupProducts(GroupId, mBundleContact);
    }*/

    private void getProducts(String GroupId) {
        if (mTestConnection.isConnectedToInternet()) {
            hud = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setMaxProgress(100)
                    .show();
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getGroupProducts(GroupId, mBundleContact);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onRefresh() {

        getProducts(mGroupId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser && !_hasLoadedOnce) {
                getProducts(mGroupId);
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                hud.dismiss();
                System.out.println("Product Response=============" + response);
                mSwipeRefreshLayout.setRefreshing(false);
                productList.clear();
                String storeContact = null;

                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getProduct().isEmpty()) {
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
                    adapter = new StoreProductAdapter(getActivity(), productList, myContact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    hud.dismiss();
                    mSwipeRefreshLayout.setRefreshing(false);
                    CustomToast.customToast(getActivity(), "No Products Found");

                }
            } else {
                hud.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            hud.dismiss();
            mSwipeRefreshLayout.setRefreshing(false);
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
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-"
                    , "memberproduct fragment");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mPlaceHolder = (TextView) mProduct.findViewById(R.id.no_category);
                mNoInternetIcon = (ImageButton) mProduct.findViewById(R.id.icon_nointernet);
                mPlaceHolder.setVisibility(View.GONE);

                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

                mSwipeRefreshLayout = (SwipeRefreshLayout) mProduct.findViewById(R.id.swipeRefreshLayout);
                mRecyclerView = (RecyclerView) mProduct.findViewById(R.id.recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                Bundle getBundle = getArguments();
                mGroupId = getBundle.getString("bundle_GroupId");
                mBundleContact = getBundle.getString("Rcontact");

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
