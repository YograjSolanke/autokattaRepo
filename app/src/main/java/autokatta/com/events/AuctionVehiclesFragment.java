package autokatta.com.events;

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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.PreviewAuctionAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetAuctionEventResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 6/4/17.
 */

public class AuctionVehiclesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    public AuctionVehiclesFragment() {
        //empty constructor
    }

    View mAuctionVehicles;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String strAuctionId = "";
    List<GetAuctionEventResponse.Vehicle> vehicles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuctionVehicles = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mRecyclerView = (RecyclerView) mAuctionVehicles.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mAuctionVehicles.findViewById(R.id.swipeRefreshLayoutMain);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bundle = getArguments();
                    strAuctionId = bundle.getString("auctionid");

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                            /*apiCall.MyActiveAuction(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"),
                                    "ACTIVE");*/
                            getAuctionVehicle(strAuctionId);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mAuctionVehicles;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void getAuctionVehicle(String strAuctionId) {
        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.getAuctionEvent(strAuctionId);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                vehicles.clear();
                GetAuctionEventResponse mAuctionEventResponse = (GetAuctionEventResponse) response.body();
                for (GetAuctionEventResponse.Vehicle vehicle : mAuctionEventResponse.getSuccess().getVehicles()) {
                    vehicle.setVehicleId(vehicle.getVehicleId());
                    vehicle.setTitle(vehicle.getTitle());
                    vehicle.setCategory(vehicle.getCategory());
                    vehicle.setSubCategory(vehicle.getSubCategory());
                    vehicle.setBrand(vehicle.getBrand());
                    vehicle.setModel(vehicle.getModel());
                    vehicle.setLocationCity(vehicle.getLocationCity());
                    vehicle.setColor(vehicle.getColor());
                    vehicle.setImage(vehicle.getImage());
                    vehicle.setRegNo(vehicle.getRegNo());
                    vehicle.setRtoCity(vehicle.getRtoCity());
                    vehicle.setKmsRunning(vehicle.getKmsRunning());
                    vehicle.setHrsRunning(vehicle.getHrsRunning());
                    vehicle.setYear(vehicle.getYear());
                    vehicle.setStartPrice(vehicle.getStartPrice());
                    vehicle.setReservePrice(vehicle.getReservePrice());
                    vehicle.setChassisNo(vehicle.getChassisNo());
                    vehicle.setEngineNo(vehicle.getEngineNo());
                    vehicle.setLotNo(vehicle.getLotNo());
                    vehicle.setVehicleStatus(vehicle.getVehicleStatus());
                    vehicles.add(vehicle);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                PreviewAuctionAdapter adapter = new PreviewAuctionAdapter(getActivity(), vehicles, strAuctionId, "0",
                        getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                                .getString("loginContact", ""));
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Auction Vehicles Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
