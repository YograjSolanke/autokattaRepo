package autokatta.com.initial_fragment;

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
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.MyUploadedVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.MyUploadedVehiclesResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 1/4/17.
 */

public class MyUploadedVehiclesFragment extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    List<MyUploadedVehiclesResponse.Success> myUploadedVehiclesResponseList = new ArrayList<>();
    View myVehicles;
    String myContact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myVehicles = inflater.inflate(R.layout.fragment_my_uploaded_vehicles, container, false);


        apiCall = new ApiCall(getActivity(), this);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        mSwipeRefreshLayout = (SwipeRefreshLayout) myVehicles.findViewById(R.id.swipeRefreshLayoutMyUploadedVehicle);
        mRecyclerView = (RecyclerView) myVehicles.findViewById(R.id.recyclerMyUploadedVehicle);

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
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                apiCall.MyUploadedVehicles(myContact);
            }
        });


        return myVehicles;
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                MyUploadedVehiclesResponse myVehicleResponse = (MyUploadedVehiclesResponse) response.body();
                if (!myVehicleResponse.getSuccess().isEmpty()) {
                    for (MyUploadedVehiclesResponse.Success myVehicleSuccess : myVehicleResponse.getSuccess()) {
                        myVehicleSuccess.setVehicleId(myVehicleSuccess.getVehicleId());
                        myVehicleSuccess.setTitle(myVehicleSuccess.getTitle());
                        myVehicleSuccess.setPrice(myVehicleSuccess.getPrice());
                        myVehicleSuccess.setCategory(myVehicleSuccess.getCategory());
                        myVehicleSuccess.setModel(myVehicleSuccess.getModel());
                        myVehicleSuccess.setManufacturer(myVehicleSuccess.getManufacturer());
                        myVehicleSuccess.setBuyerLeads(myVehicleSuccess.getBuyerLeads());
                        myVehicleSuccess.setNotificationstatus(myVehicleSuccess.getNotificationstatus());
                        myVehicleSuccess.setImages(myVehicleSuccess.getImages());
                        myVehicleSuccess.setDate(myVehicleSuccess.getDate());
                        myVehicleSuccess.setYearOfManufacture(myVehicleSuccess.getYearOfManufacture());
                        myVehicleSuccess.setKmsRunning(myVehicleSuccess.getKmsRunning());
                        myVehicleSuccess.setHrsRunning(myVehicleSuccess.getHrsRunning());
                        myVehicleSuccess.setRtoCity(myVehicleSuccess.getRtoCity());
                        myVehicleSuccess.setLocationCity(myVehicleSuccess.getLocationCity());
                        myVehicleSuccess.setRegistrationNumber(myVehicleSuccess.getRegistrationNumber());
                        myUploadedVehiclesResponseList.add(myVehicleSuccess);
                    }
                    Log.i("size", String.valueOf(myUploadedVehiclesResponseList.size()));
                    mSwipeRefreshLayout.setRefreshing(false);
                    MyUploadedVehicleAdapter adapter = new MyUploadedVehicleAdapter(getActivity(), myUploadedVehiclesResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
                    , "MyUploadedVehiclesActivity");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

}
