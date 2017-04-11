package autokatta.com.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 11/4/17.
 */

public class UploadedVehicleBuyerList extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    public UploadedVehicleBuyerList() {

    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    SharedPreferences mSharedPreferences;
    // List<MyUploadedVehiclesResponse.Success> myUploadedVehiclesResponseList = new ArrayList<>();
    View myVehicles;
    String vehicle_id, title, price, vcategory, brand, vmodel, image, noofleads, uploaddate;
    ImageView vehicleimage;
    TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myVehicles = inflater.inflate(R.layout.uploaded_vehicle_buyer_list, container, false);


        apiCall = new ApiCall(getActivity(), this);
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        final String myContact = mSharedPreferences.getString("loginContact", "7841023392");

        mSwipeRefreshLayout = (SwipeRefreshLayout) myVehicles.findViewById(R.id.swipeRefreshLayoutUploadedVehicleBuyer);
        mRecyclerView = (RecyclerView) myVehicles.findViewById(R.id.buyerlist);

        edittitles = (TextView) myVehicles.findViewById(R.id.edittitle);
        editprices = (TextView) myVehicles.findViewById(R.id.editprice);
        editcategorys = (TextView) myVehicles.findViewById(R.id.editcategory);
        editbrands = (TextView) myVehicles.findViewById(R.id.editbrand);
        editmodels = (TextView) myVehicles.findViewById(R.id.editmodel);
        editleads = (TextView) myVehicles.findViewById(R.id.editleads);
        edituploadedon = (TextView) myVehicles.findViewById(R.id.edituploadedon);
        vehicleimage = (ImageView) myVehicles.findViewById(R.id.vehiprofile);


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


        Bundle b = getArguments();
        vehicle_id = b.getString("vehicle_id");
        title = b.getString("title");
        price = b.getString("price");
        vcategory = b.getString("category");
        brand = b.getString("brand");
        vmodel = b.getString("model");
        image = b.getString("image");
        uploaddate = b.getString("uploaddate");
        noofleads = b.getString("noofleads");

        editbrands.setText(brand);
        edittitles.setText(title);
        editprices.setText(price);
        editcategorys.setText(vcategory);
        editleads.setText(noofleads);
        edituploadedon.setText(uploaddate);
        editmodels.setText(vmodel);


        return myVehicles;
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
