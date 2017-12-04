package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.SuggestionNewVehicleAdapter;
import autokatta.com.adapter.SuggestionProductAdapter;
import autokatta.com.adapter.SuggestionProfileAdapter;
import autokatta.com.adapter.SuggestionServiceAdapter;
import autokatta.com.adapter.SuggestionStoreAdapter;
import autokatta.com.adapter.SuggestionVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.SuggestionsResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 20/11/17
 */

public class SuggestionFragment extends Fragment implements RequestNotifier {
    View mSuggestionView;
    RecyclerView mProfileView, mStoreView, mVehicleView, mProductView, mServiceView, mNewVehicleView;
    boolean hasViewCreated = false;
    TextView mNoData, txtProfile, txtStore, txtVehicle, txtProduct, txtService, txtNwVehicle;
    ConnectionDetector mTestConnection;
    String mLoginContact;
    private List<SuggestionsResponse.Success.UsedVehicle> vehicleResponseList = new ArrayList<>();
    private List<SuggestionsResponse.Success.Newvehicle> newVehicleResponseList = new ArrayList<>();
    private List<SuggestionsResponse.Success.Store> storeResponseList = new ArrayList<>();
    private List<SuggestionsResponse.Success.Product> productResponseList = new ArrayList<>();
    private List<SuggestionsResponse.Success.Service> serviceResponseList = new ArrayList<>();
    private List<SuggestionsResponse.Success.Profile> profileResponseList = new ArrayList<>();
    LinearLayout mProfileLinear, mStoreLinear, mVehicleLinear, mProductLinear, mServiceLinear,
            mNwVehicleLinear;

    public SuggestionFragment() {
        // empty fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSuggestionView = inflater.inflate(R.layout.fragment_wall_profile_suggestions, container, false);
        return mSuggestionView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        mProfileView = (RecyclerView) view.findViewById(R.id.profileRecyclerView);
        mStoreView = (RecyclerView) view.findViewById(R.id.storeRecyclerView);
        mVehicleView = (RecyclerView) view.findViewById(R.id.vehicleRecyclerView);
        mProductView = (RecyclerView) view.findViewById(R.id.productRecyclerView);
        mServiceView = (RecyclerView) view.findViewById(R.id.serviceRecyclerView);
        mNewVehicleView = (RecyclerView) view.findViewById(R.id.newVehicleRecyclerView);
        txtProfile = (TextView) view.findViewById(R.id.textProfile);
        txtStore = (TextView) view.findViewById(R.id.textStore);
        txtVehicle = (TextView) view.findViewById(R.id.textVehicle);
        txtProduct = (TextView) view.findViewById(R.id.textProduct);
        txtService = (TextView) view.findViewById(R.id.textService);
        txtNwVehicle = (TextView) view.findViewById(R.id.textNewVehicle);
        mProfileLinear = (LinearLayout) view.findViewById(R.id.linearProfile);
        mStoreLinear = (LinearLayout) view.findViewById(R.id.linearStore);
        mVehicleLinear = (LinearLayout) view.findViewById(R.id.linearVehicle);
        mProductLinear = (LinearLayout) view.findViewById(R.id.linearProduct);
        mServiceLinear = (LinearLayout) view.findViewById(R.id.linearService);
        mNwVehicleLinear = (LinearLayout) view.findViewById(R.id.linearNewVehicle);
        mProfileView.setHasFixedSize(true);
        mProfileView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mSuggestions.mSuggestionRecycler.addItemDecoration(new VerticalLineDecorator(2));
        mProfileView.setItemAnimator(new DefaultItemAnimator());

        mStoreView.setHasFixedSize(true);
        mStoreView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mSuggestions.mSuggestionRecycler.addItemDecoration(new VerticalLineDecorator(2));
        mStoreView.setItemAnimator(new DefaultItemAnimator());

        mVehicleView.setHasFixedSize(true);
        mVehicleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mSuggestions.mSuggestionRecycler.addItemDecoration(new VerticalLineDecorator(2));
        mVehicleView.setItemAnimator(new DefaultItemAnimator());

        mProductView.setHasFixedSize(true);
        mProductView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mSuggestions.mSuggestionRecycler.addItemDecoration(new VerticalLineDecorator(2));
        mProductView.setItemAnimator(new DefaultItemAnimator());

        mServiceView.setHasFixedSize(true);
        mServiceView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mSuggestions.mSuggestionRecycler.addItemDecoration(new VerticalLineDecorator(2));
        mServiceView.setItemAnimator(new DefaultItemAnimator());

        mNewVehicleView.setHasFixedSize(true);
        mNewVehicleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mSuggestions.mSuggestionRecycler.addItemDecoration(new VerticalLineDecorator(2));
        mNewVehicleView.setItemAnimator(new DefaultItemAnimator());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mTestConnection = new ConnectionDetector(getActivity());
                    mLoginContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                            getString("loginContact", "");
                    getSuggestionData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSuggestionData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getSuggestionData(mLoginContact);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {
                getSuggestionData();
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                SuggestionsResponse suggestionsResponse = (SuggestionsResponse) response.body();
                mNoData.setVisibility(View.GONE);
            /*Profile Array*/
                if (!suggestionsResponse.getSuccess().getProfile().isEmpty()) {
                    profileResponseList.clear();
                    mProfileLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.Profile notification : suggestionsResponse.getSuccess().getProfile()) {
                        notification.setUserName(notification.getUserName());
                        notification.setProfilePicture(notification.getProfilePicture());
                        notification.setContactNo(notification.getContactNo());
                        notification.setCity(notification.getCity());
                        profileResponseList.add(notification);
                    }
                    SuggestionProfileAdapter adapter = new SuggestionProfileAdapter(getActivity(), profileResponseList, txtProfile, mLoginContact);
                    mProfileView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                /*UsedVehicle Array*/
                if (!suggestionsResponse.getSuccess().getUsedVehicle().isEmpty()) {
                    vehicleResponseList.clear();
                    mVehicleLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.UsedVehicle notification : suggestionsResponse.getSuccess().getUsedVehicle()) {
                        notification.setTitile(notification.getTitile());
                        //modelsuggestionsResponse.setImage(notification.getImage());
                        notification.setContactNo(notification.getContactNo());
                        notification.setUploadVehicleID(notification.getUploadVehicleID());
                        notification.setLocationCity(notification.getLocationCity());
                        notification.setManufacturer(notification.getManufacturer());
                        notification.setModel(notification.getModel());
                        notification.setYearOfManufaturer(notification.getYearOfManufaturer());
                        notification.setCategory(notification.getCategory());
                        String vehicleImage = notification.getImage();
                        if (vehicleImage.contains(",")) {
                            String[] items = vehicleImage.split(",");
                            notification.setImage(items[0]);
                        } else {
                            notification.setImage(vehicleImage);
                        }
                        vehicleResponseList.add(notification);
                    }
                    SuggestionVehicleAdapter adapter = new SuggestionVehicleAdapter(getActivity(), vehicleResponseList, txtVehicle, mLoginContact);
                    mVehicleView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                /*NewVehicle Array*/
                if (!suggestionsResponse.getSuccess().getNewvehicle().isEmpty()) {
                    newVehicleResponseList.clear();
                    mNwVehicleLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.Newvehicle notification : suggestionsResponse.getSuccess().getNewvehicle()) {
                        notification.setName(notification.getName());
                        notification.setStoreImage(notification.getStoreImage());
                        notification.setContactNo(notification.getContactNo());
                        notification.setStoreID(notification.getStoreID());
                        notification.setLocation(notification.getLocation());
                        newVehicleResponseList.add(notification);
                    }
                    SuggestionNewVehicleAdapter adapter = new SuggestionNewVehicleAdapter(getActivity(), newVehicleResponseList, txtNwVehicle, mLoginContact);
                    mNewVehicleView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                /*Store Array*/
                if (!suggestionsResponse.getSuccess().getStore().isEmpty()) {
                    mStoreLinear.setVisibility(View.VISIBLE);
                    storeResponseList.clear();
                    for (SuggestionsResponse.Success.Store notification : suggestionsResponse.getSuccess().getStore()) {
                        notification.setName(notification.getName());
                        notification.setStoreImage(notification.getStoreImage());
                        notification.setContactNo(notification.getContactNo());
                        notification.setStoreID(notification.getStoreID());
                        notification.setLocation(notification.getLocation());
                        storeResponseList.add(notification);
                    }
                    SuggestionStoreAdapter adapter = new SuggestionStoreAdapter(getActivity(), storeResponseList, txtStore, mLoginContact);
                    mStoreView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                /*Product Array*/
                if (!suggestionsResponse.getSuccess().getProduct().isEmpty()) {
                    mProductLinear.setVisibility(View.VISIBLE);
                    productResponseList.clear();
                    for (SuggestionsResponse.Success.Product notification : suggestionsResponse.getSuccess().getProduct()) {
                        notification.setName(notification.getName());
                        notification.setAddedBy(notification.getAddedBy());
                        notification.setProductID(notification.getProductID());
                        String productImage = notification.getImage();
                        if (productImage.contains(",")) {
                            String[] items = productImage.split(",");
                            notification.setImage(items[0]);
                        } else {
                            notification.setImage(productImage);
                        }
                        productResponseList.add(notification);
                    }
                    SuggestionProductAdapter adapter = new SuggestionProductAdapter(getActivity(), productResponseList, txtProduct, mLoginContact);
                    mProductView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                /*Service Array*/
                if (!suggestionsResponse.getSuccess().getService().isEmpty()) {
                    mServiceLinear.setVisibility(View.VISIBLE);
                    serviceResponseList.clear();
                    for (SuggestionsResponse.Success.Service notification : suggestionsResponse.getSuccess().getService()) {
                        notification.setName(notification.getName());
                        notification.setAddedBy(notification.getAddedBy());
                        notification.setStoreServiceID(notification.getStoreServiceID());
                        String serviceImage = notification.getImage();
                        if (serviceImage.contains(",")) {
                            String[] items = serviceImage.split(",");
                            notification.setImage(items[0]);
                        } else {
                            notification.setImage(serviceImage);
                        }
                        serviceResponseList.add(notification);
                    }
                    SuggestionServiceAdapter adapter = new SuggestionServiceAdapter(getActivity(), serviceResponseList, txtService, mLoginContact);
                    mServiceView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            } else {
                mNoData.setVisibility(View.VISIBLE);
            }
        } else {
            mNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notifyError(Throwable error) {
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
            Log.i("Check Class-", "SuggestionFragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
