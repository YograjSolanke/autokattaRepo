package autokatta.com.fragment;

import android.app.ProgressDialog;
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
import autokatta.com.adapter.SuggestionProductAdapter;
import autokatta.com.adapter.SuggestionServiceAdapter;
import autokatta.com.adapter.SuggestionStoreAdapter;
import autokatta.com.adapter.SuggestionVehicleAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ModelSuggestionsResponse;
import autokatta.com.response.SuggestionsResponse;
import retrofit2.Response;

/**
 * Created by ak-003 on 20/11/17.
 */

public class SuggestionFragment extends Fragment implements RequestNotifier {

    View mSuggestionView;
    RecyclerView mProfileView, mStoreView, mVehicleView, mProductView, mServiceView;
    boolean hasViewCreated = false;
    TextView mNoData, txtProfile, txtStore, txtVehicle, txtProduct, txtService;
    ConnectionDetector mTestConnection;
    String mLoginContact;
    private List<ModelSuggestionsResponse> suggestionResponseList = new ArrayList<>();
    LinearLayout mProfileLinear, mStoreLinear, mVehicleLinear, mProductLinear, mServiceLinear;
    private ProgressDialog dialog;

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

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading Suggestions...");

        mNoData = (TextView) view.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mProfileView = (RecyclerView) view.findViewById(R.id.profileRecyclerView);
        mStoreView = (RecyclerView) view.findViewById(R.id.storeRecyclerView);
        mVehicleView = (RecyclerView) view.findViewById(R.id.vehicleRecyclerView);
        mProductView = (RecyclerView) view.findViewById(R.id.productRecyclerView);
        mServiceView = (RecyclerView) view.findViewById(R.id.serviceRecyclerView);
        txtProfile = (TextView) view.findViewById(R.id.textProfile);
        txtStore = (TextView) view.findViewById(R.id.textStore);
        txtVehicle = (TextView) view.findViewById(R.id.textVehicle);
        txtProduct = (TextView) view.findViewById(R.id.textProduct);
        txtService = (TextView) view.findViewById(R.id.textService);
        mProfileLinear = (LinearLayout) view.findViewById(R.id.linearProfile);
        mStoreLinear = (LinearLayout) view.findViewById(R.id.linearStore);
        mVehicleLinear = (LinearLayout) view.findViewById(R.id.linearVehicle);
        mProductLinear = (LinearLayout) view.findViewById(R.id.linearProduct);
        mServiceLinear = (LinearLayout) view.findViewById(R.id.linearService);

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
        dialog.show();
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
                suggestionResponseList.clear();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                /*UsedVehicle Array*/
                if (!suggestionsResponse.getSuccess().getUsedVehicle().isEmpty()) {
                    mVehicleLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.UsedVehicle notification : suggestionsResponse.getSuccess().getUsedVehicle()) {

                        ModelSuggestionsResponse modelsuggestionsResponse = new ModelSuggestionsResponse();
                        modelsuggestionsResponse.setLayoutId(-4);
                        modelsuggestionsResponse.setName(notification.getTitile());
                        modelsuggestionsResponse.setImage(notification.getImage());
                        modelsuggestionsResponse.setUserContact(notification.getContactNo());
                        modelsuggestionsResponse.setVehicleId(notification.getUploadVehicleID());
                        modelsuggestionsResponse.setLocation(notification.getLocationCity());
                        modelsuggestionsResponse.setVehicleBrand(notification.getManufacturer());
                        modelsuggestionsResponse.setVehicleModel(notification.getModel());
                        modelsuggestionsResponse.setVehicleMfgYear(notification.getYearOfManufaturer());
                        modelsuggestionsResponse.setVehicleCategory(notification.getCategory());

                        suggestionResponseList.add(modelsuggestionsResponse);

                    }

                    SuggestionVehicleAdapter adapter = new SuggestionVehicleAdapter(getActivity(), suggestionResponseList, txtVehicle, mLoginContact);
                    mVehicleView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                /*NewVehicle Array*/
                if (!suggestionsResponse.getSuccess().getNewvehicle().isEmpty()) {
                    mStoreLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.Newvehicle notification : suggestionsResponse.getSuccess().getNewvehicle()) {

                        ModelSuggestionsResponse modelsuggestionsResponse = new ModelSuggestionsResponse();
                        modelsuggestionsResponse.setLayoutId(-2);
                        modelsuggestionsResponse.setName(notification.getName());
                        modelsuggestionsResponse.setImage(notification.getStoreImage());
                        modelsuggestionsResponse.setUserContact(notification.getContactNo());

                        modelsuggestionsResponse.setStoreId(notification.getStoreID());
                        modelsuggestionsResponse.setUserContact(notification.getContactNo());
                        modelsuggestionsResponse.setLocation(notification.getLocation());

                        suggestionResponseList.add(modelsuggestionsResponse);
                    }

                    SuggestionStoreAdapter adapter = new SuggestionStoreAdapter(getActivity(), suggestionResponseList, txtStore, mLoginContact);
                    mStoreView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

                /*Store Array*/
                if (!suggestionsResponse.getSuccess().getStore().isEmpty()) {
                    mStoreLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.Store notification : suggestionsResponse.getSuccess().getStore()) {

                        ModelSuggestionsResponse modelsuggestionsResponse = new ModelSuggestionsResponse();
                        modelsuggestionsResponse.setLayoutId(-2);
                        modelsuggestionsResponse.setName(notification.getName());
                        modelsuggestionsResponse.setImage(notification.getStoreImage());
                        modelsuggestionsResponse.setUserContact(notification.getContactNo());

                        modelsuggestionsResponse.setStoreId(notification.getStoreID());
                        modelsuggestionsResponse.setUserContact(notification.getContactNo());
                        modelsuggestionsResponse.setLocation(notification.getLocation());

                        suggestionResponseList.add(modelsuggestionsResponse);
                    }

                    SuggestionStoreAdapter adapter = new SuggestionStoreAdapter(getActivity(), suggestionResponseList, txtStore, mLoginContact);
                    mStoreView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                /*Product Array*/
                if (!suggestionsResponse.getSuccess().getProduct().isEmpty()) {
                    mProductLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.Product notification : suggestionsResponse.getSuccess().getProduct()) {

                        ModelSuggestionsResponse modelsuggestionsResponse = new ModelSuggestionsResponse();
                        modelsuggestionsResponse.setLayoutId(-5);
                        modelsuggestionsResponse.setName(notification.getName());
                        modelsuggestionsResponse.setImage(notification.getImage());
                        modelsuggestionsResponse.setUserContact(notification.getAddedBy());

                        modelsuggestionsResponse.setProductId(notification.getProductID());
                        modelsuggestionsResponse.setLocation("");

                        suggestionResponseList.add(modelsuggestionsResponse);
                    }

                    SuggestionProductAdapter adapter = new SuggestionProductAdapter(getActivity(), suggestionResponseList, txtProduct, mLoginContact);
                    mProductView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                /*Service Array*/
                if (!suggestionsResponse.getSuccess().getService().isEmpty()) {
                    mServiceLinear.setVisibility(View.VISIBLE);
                    for (SuggestionsResponse.Success.Service notification : suggestionsResponse.getSuccess().getService()) {

                        ModelSuggestionsResponse modelsuggestionsResponse = new ModelSuggestionsResponse();
                        modelsuggestionsResponse.setLayoutId(-6);
                        modelsuggestionsResponse.setName(notification.getName());
                        modelsuggestionsResponse.setImage(notification.getImage());
                        modelsuggestionsResponse.setUserContact(notification.getAddedBy());

                        modelsuggestionsResponse.setServiceId(notification.getStoreServiceID());
                        modelsuggestionsResponse.setLocation("");

                        suggestionResponseList.add(modelsuggestionsResponse);
                    }

                    SuggestionServiceAdapter adapter = new SuggestionServiceAdapter(getActivity(), suggestionResponseList, txtService, mLoginContact);
                    mServiceView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                /*SuggestionStoreAdapter adapter = new SuggestionStoreAdapter(getActivity(), suggestionResponseList, txtSuggestion, mLoginContact);
                mProfileView.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/

            } else {
                mNoData.setVisibility(View.VISIBLE);
            }
        } else {
            mNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        mNoData.setVisibility(View.VISIBLE);
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
