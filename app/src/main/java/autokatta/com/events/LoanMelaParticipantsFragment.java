package autokatta.com.events;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import autokatta.com.adapter.LoanParticipantAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.LoanMelaParticipantsResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 26/4/17.
 */

public class LoanMelaParticipantsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {


    View mLoanParticipants;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String strLoanId = "";
    List<LoanMelaParticipantsResponse.Success> participantList = new ArrayList<>();
    boolean hasViewCreated = false;
    TextView mNoData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoanParticipants = inflater.inflate(R.layout.fragment_simple_listview, container, false);
        return mLoanParticipants;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNoData = (TextView) mLoanParticipants.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) mLoanParticipants.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mLoanParticipants.findViewById(R.id.swipeRefreshLayoutMain);

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
                    strLoanId = bundle.getString("loanid");

                    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);

                            getLoanParticipant(strLoanId);
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

                getLoanParticipant(strLoanId);
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getLoanParticipant(strLoanId);
    }

    private void getLoanParticipant(String strLoanId) {
        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.getLoanMelaParticipants(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""), strLoanId);
        // apiCall.AuctionParticipantData("9890950817", "1047");
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                participantList.clear();
                LoanMelaParticipantsResponse participantsResponse = (LoanMelaParticipantsResponse) response.body();

                if (!participantsResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (LoanMelaParticipantsResponse.Success success : participantsResponse.getSuccess()) {
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
                    LoanParticipantAdapter adapter = new LoanParticipantAdapter(getActivity(), strLoanId, participantList);
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
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-", "Loan Participants Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
