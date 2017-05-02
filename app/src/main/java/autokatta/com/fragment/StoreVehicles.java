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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.StoreVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreInventoryResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreVehicles extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    public StoreVehicles() {

    }

    View mVehicle;
    String Sharedcontact, storeContact, store_id;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView titleText;
    List<StoreInventoryResponse.Success.Vehicle> vehicleList;
    LinearLayoutManager mLayoutManager;
    //
    StoreVehicleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehicle = inflater.inflate(R.layout.store_product_fragment, container, false);
        Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

        mSwipeRefreshLayout = (SwipeRefreshLayout) mVehicle.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) mVehicle.findViewById(R.id.recycler_view);
        titleText = (TextView) mVehicle.findViewById(R.id.titleText);
        titleText.setText("Vehicles");
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Bundle bundle = getArguments();
        store_id = bundle.getString("store_id");

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
                getStoreVehicles(store_id, Sharedcontact);
            }
        });
        return mVehicle;
    }

    private void getStoreVehicles(String store_id, String sharedcontact) {
        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.getStoreInventory(store_id, sharedcontact);
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (response != null) {
            if (response.isSuccessful()) {

                System.out.println("Product Response=============" + response);
                mSwipeRefreshLayout.setRefreshing(false);
                vehicleList = new ArrayList<>();

                StoreInventoryResponse storeResponse = (StoreInventoryResponse) response.body();
                if (!storeResponse.getSuccess().getVehicle().isEmpty()) {
                    for (StoreInventoryResponse.Success.Vehicle success : storeResponse.getSuccess().getVehicle()) {


                        success.setVehicleId(success.getVehicleId());
                        success.setTitle(success.getTitle());
                        success.setPrice(success.getPrice());
                        success.setCategory(success.getCategory());
                        success.setModel(success.getModel());
                        success.setManufacturer(success.getManufacturer());
                        success.setBuyerLeads(success.getBuyerLeads());
                        success.setRegno(success.getRegno());
                        success.setYear(success.getYear());
                        success.setLocation(success.getLocation());
                        success.setRto(success.getRto());
                        success.setKms(success.getKms());
                        success.setImages(success.getImages());
                        Date d = null;
                        try {
                            d = f.parse(success.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        success.setVehicleDate(d);
                        vehicleList.add(success);

                    }
                    adapter = new StoreVehicleAdapter(getActivity(), vehicleList, Sharedcontact, storeContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    CustomToast.customToast(getActivity(), "No Vehicles Found");

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