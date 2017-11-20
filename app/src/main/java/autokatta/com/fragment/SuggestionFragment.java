package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.CustomSuggestionAdapter;
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

public class SuggestionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mSuggestionView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    boolean hasViewCreated = false;
    TextView mNoData, txtSuggestion;
    ConnectionDetector mTestConnection;
    String mLoginContact;
    private List<ModelSuggestionsResponse> suggestionResponseList = new ArrayList<>();

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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.profileRecyclerView);
        txtSuggestion = (TextView) view.findViewById(R.id.textProfile);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutMain);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mSuggestions.mSuggestionRecycler.addItemDecoration(new VerticalLineDecorator(2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setOnRefreshListener(this);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mTestConnection = new ConnectionDetector(getActivity());
                    mLoginContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).
                            getString("loginContact", "");

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);

                            getSuggestionData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSuggestionData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getSuggestionData("1122334455", "Vehicles");
    }

    @Override
    public void onRefresh() {
        getSuggestionData();
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
                if (!suggestionsResponse.getSuccess().getWallSuggestions().isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);
                    suggestionResponseList.clear();
                    for (SuggestionsResponse.Success.WallSuggestion notification : suggestionsResponse.getSuccess().getWallSuggestions()) {

                        ModelSuggestionsResponse modelsuggestionsResponse = new ModelSuggestionsResponse();
                        modelsuggestionsResponse.setName(notification.getUsername());
                        modelsuggestionsResponse.setImage(notification.getProfilePic());
                        modelsuggestionsResponse.setLayoutId(notification.getLayout());
                        modelsuggestionsResponse.setUserContact(notification.getContact());


                        modelsuggestionsResponse.setVehicleId(notification.getUploadVehicleID());
                        modelsuggestionsResponse.setUserContact(notification.getContactNo());
                        modelsuggestionsResponse.setImage(notification.getImage());
                        modelsuggestionsResponse.setLocation(notification.getLocationCity());

                        suggestionResponseList.add(modelsuggestionsResponse);

                    }
                    CustomSuggestionAdapter adapter = new CustomSuggestionAdapter(getActivity(), suggestionResponseList, txtSuggestion, mLoginContact);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mNoData.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            mNoData.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
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
