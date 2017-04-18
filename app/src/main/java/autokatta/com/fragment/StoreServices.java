package autokatta.com.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreServiceAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreServices extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    public StoreServices() {

    }

    View mService;
    String Sharedcontact, storeContact, store_id;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView titleText;
    List<StoreInventoryResponse.Success.Service> serviceList;
    LinearLayoutManager mLayoutManager;
    StoreServiceAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mService = inflater.inflate(R.layout.store_product_fragment, container, false);
        Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        mSwipeRefreshLayout = (SwipeRefreshLayout) mService.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) mService.findViewById(R.id.recycler_view);
        titleText = (TextView) mService.findViewById(R.id.titleText);
        titleText.setText("Services");
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Bundle bundle = getArguments();
        store_id = bundle.getString("store_id");
        storeContact = bundle.getString("StoreContact");
        //  System.out.println("Product Response============="+response);

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
                getStoreService(store_id, Sharedcontact, storeContact);
            }
        });
        return mService;
    }

    private void getStoreService(String store_id, String sharedcontact, String storeContact) {
        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.getStoreInventory(store_id, sharedcontact, storeContact);
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {

                System.out.println("Product Response=============" + response);
                mSwipeRefreshLayout.setRefreshing(false);
                serviceList = new ArrayList<>();

                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getService().isEmpty()) {
                    for (StoreInventoryResponse.Success.Service success : storeResponse.getSuccess().getService()) {

                        success.setServiceId(success.getServiceId());
                        success.setServiceName(success.getServiceName());
                        success.setBrandtags(success.getBrandtags());
                        success.setServicePrice(success.getServicePrice());
                        success.setServiceType(success.getServiceType());
                        success.setServiceDetails(success.getServiceDetails());
                        success.setServicetags(success.getServicetags());
                        success.setServiceImages(success.getServiceImages());
                        success.setServicecategory(success.getServicecategory());
                        success.setServicelikestatus(success.getServicelikestatus());
                        success.setServicelikecount(success.getServicelikecount());
                        success.setServicerating(success.getServicerating());
                        success.setSrate(success.getSrate());
                        success.setSrate1(success.getSrate1());
                        success.setSrate2(success.getSrate2());
                        success.setSrate3(success.getSrate3());
                        serviceList.add(success);

                    }
                    adapter = new StoreServiceAdapter(getActivity(), serviceList, Sharedcontact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    CustomToast.customToast(getActivity(), "No Services Found");

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
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "StoreServices");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}