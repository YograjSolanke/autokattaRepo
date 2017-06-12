package autokatta.com.events;

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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.ServiceParticipantsAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ServiceMelaParticipantsResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 26/4/17.
 */

public class ServiceParticipantsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {


    View mServiceParticipants;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String strServiceId = "";
    List<ServiceMelaParticipantsResponse.Success> participantList = new ArrayList<>();
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mTestConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mServiceParticipants = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mServiceParticipants;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mServiceParticipants.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) mServiceParticipants.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mServiceParticipants.findViewById(R.id.swipeRefreshLayoutMain);

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
                    mTestConnection = new ConnectionDetector(getActivity());
                    Bundle bundle = getArguments();
                    strServiceId = bundle.getString("serviceid");

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);

                            getServiceParticipant(strServiceId);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getServiceParticipant(strServiceId);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getServiceParticipant(strServiceId);
    }

    private void getServiceParticipant(String strServiceId) {

        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.getServiceMelaParticipants(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                    .getString("loginContact", ""), strServiceId);
            // apiCall.AuctionParticipantData("9890950817", "1047");
        } else {
          //  errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                participantList.clear();
                ServiceMelaParticipantsResponse participantsResponse = (ServiceMelaParticipantsResponse) response.body();

                if (!participantsResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (ServiceMelaParticipantsResponse.Success success : participantsResponse.getSuccess()) {

                        success.setContact(success.getContact());
                        success.setProfilePhoto(success.getProfilePhoto());
                        success.setUsername(success.getUsername());
                        success.setLocation(success.getLocation());
                        success.setProfession(success.getProfession());
                        success.setSubprofession(success.getSubprofession());
                        success.setBlackliststatus(success.getBlackliststatus());

                        participantList.add(success);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    ServiceParticipantsAdapter adapter = new ServiceParticipantsAdapter(getActivity(), strServiceId, participantList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
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
         //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
         //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
         //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
         //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
          //  errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "Service  Participants Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
/*
    public void showMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void errorMessage(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getServiceParticipant(strServiceId);
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }*/
}
