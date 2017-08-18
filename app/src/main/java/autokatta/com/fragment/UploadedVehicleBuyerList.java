package autokatta.com.fragment;

import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.VehicleBuyerListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.BuyerResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 11/4/17.
 */

public class UploadedVehicleBuyerList extends Fragment implements RequestNotifier, SwipeRefreshLayout.OnRefreshListener {

    public UploadedVehicleBuyerList() {

    }

    VehicleBuyerListAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    SharedPreferences mSharedPreferences;
    List<BuyerResponse.Success.Found> myUploadedVehiclesResponseList;
    View myVehicles;
    String vimagename = "";
    ArrayList<String> vimages = new ArrayList<>();
    String title, price, vcategory, brand, vmodel, image, noofleads, uploaddate, rto_city, manufacture_year;
    ImageView vehicleimage;
    int vehicle_id;
    TextView edittitles, editprices, editcategorys, editbrands, editmodels, editleads, edituploadedon;
    String myContact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myVehicles = inflater.inflate(R.layout.uploaded_vehicle_buyer_list, container, false);


        apiCall = new ApiCall(getActivity(), this);
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        myContact = mSharedPreferences.getString("loginContact", "7841023392");

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


        Bundle b = getArguments();
        vehicle_id = b.getInt("vehicle_id");
        title = b.getString("title");
        price = b.getString("price");
        vcategory = b.getString("category");
        brand = b.getString("brand");
        vmodel = b.getString("model");
        image = b.getString("image");
        uploaddate = b.getString("uploaddate");
        noofleads = b.getString("noofleads");
        rto_city = b.getString("rto_city");
        manufacture_year = b.getString("manufacture_year");

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getBuyerdata(myContact);

            }
        });


        editbrands.setText(brand);
        edittitles.setText(title);
        editprices.setText(price);
        editcategorys.setText(vcategory);
        editleads.setText(noofleads);
        edituploadedon.setText(uploaddate);
        editmodels.setText(vmodel);


        if (image.equalsIgnoreCase("") || image.equalsIgnoreCase(null) || image.equalsIgnoreCase("null")) {

            vehicleimage.setBackgroundResource(R.drawable.vehiimg);
        }
        if (!image.equals("") || !image.equalsIgnoreCase(null) || !image.equalsIgnoreCase("null")) {
            String[] parts = image.split(",");

            for (int l = 0; l < parts.length; l++) {
                vimages.add(parts[l]);
                System.out.println(parts[l]);
            }
            System.out.println(getString(R.string.base_image_url) + vimages.get(0));

            vimagename = getString(R.string.base_image_url) + vimages.get(0);
            vimagename = vimagename.replaceAll(" ", "%20");
            try {

                Glide.with(getActivity())
                        .load(getString(R.string.base_image_url) + vimages.get(0))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo)
                        .override(75, 75)
                        .into(vehicleimage);

            } catch (Exception e) {
                System.out.println("Error in uploading images");
            }
        }


        return myVehicles;
    }

    private void getBuyerdata(String myContact) {
        apiCall.getUploadedVehicleBuyerlist(myContact);

    }

    @Override
    public void onRefresh() {
        getBuyerdata(myContact);
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void notifySuccess(Response<?> response) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (response != null) {
            if (response.isSuccessful()) {
                //mSwipeRefreshLayout.setRefreshing(false);
                myUploadedVehiclesResponseList = new ArrayList<>();
                BuyerResponse buyerResponse = (BuyerResponse) response.body();

                if (!buyerResponse.getSuccess().getFound().isEmpty()) {
                    for (BuyerResponse.Success.Found found : buyerResponse.getSuccess().getFound()) {

                        if (found.getVehicleId().equals(vehicle_id)) {

                            found.setSearchId(found.getSearchId());
                            found.setCategory(found.getCategory());
                            found.setManufacturer(found.getManufacturer());
                            found.setModel(found.getModel());
                            found.setYearOfManufacture(found.getYearOfManufacture());
                            found.setLocationCity(found.getLocationCity());
                            found.setVersion(found.getVersion());
                            found.setRtoCity(found.getRtoCity());
                            found.setReceivername(found.getReceivername());
                            found.setReceiverPic(found.getReceiverPic());
                            found.setContactNo(found.getContactNo());
                            found.setFavstatus(found.getFavstatus());
                            found.setLastcall(found.getLastcall());


                            Date d = null;
                            try {
                                d = f.parse(found.getLastcall());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            found.setLastCallDateNew(d);

                            myUploadedVehiclesResponseList.add(found);
                        }
                    }
                }

                mSwipeRefreshLayout.setRefreshing(false);
                adapter = new VehicleBuyerListAdapter(getActivity(), myUploadedVehiclesResponseList, vehicle_id, vcategory, brand, vmodel, manufacture_year, rto_city);
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
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "UploadedVehicleBuyerList Fragment");
            error.printStackTrace();
        }

    }

    @Override
    public void notifyString(String str) {

    }
}
