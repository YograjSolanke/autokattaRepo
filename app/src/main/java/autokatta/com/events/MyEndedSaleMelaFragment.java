package autokatta.com.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import autokatta.com.adapter.EndedSaleMelaAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.EndedSaleMelaResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 27/4/17.
 */

public class MyEndedSaleMelaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {


    View mEndedLoan;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    ApiCall apiCall;
    List<EndedSaleMelaResponse.Success> endedSaleMelaResponseList = new ArrayList<>();
    boolean hasViewCreated = false;
    TextView mNoData;
    ConnectionDetector mTestConnection;

    public MyEndedSaleMelaFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEndedLoan = inflater.inflate(R.layout.fragment_simple_listview, container, false);


        return mEndedLoan;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTestConnection = new ConnectionDetector(getActivity());

        mNoData = (TextView) mEndedLoan.findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mEndedLoan.findViewById(R.id.swipeRefreshLayoutMain);
        mRecyclerView = (RecyclerView) mEndedLoan.findViewById(R.id.recyclerMain);

        mRecyclerView.setHasFixedSize(true);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getEndedSaleData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));

            }
        });

    }

    private void getEndedSaleData(String loginContact) {

        if (mTestConnection.isConnectedToInternet()) {
            apiCall = new ApiCall(getActivity(), this);
            apiCall.getEndedSaleMelaDetails(loginContact);
        } else {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
         //   errorMessage(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasViewCreated) {

                getEndedSaleData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
                hasViewCreated = true;
            }
        }
    }

    @Override
    public void onRefresh() {
        getEndedSaleData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {

            if (response.isSuccessful()) {

                EndedSaleMelaResponse myEndedSaleMelaResponse = (EndedSaleMelaResponse) response.body();
                if (!myEndedSaleMelaResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    endedSaleMelaResponseList.clear();
                    for (EndedSaleMelaResponse.Success loanSuccess : myEndedSaleMelaResponse.getSuccess()) {

                        loanSuccess.setId(loanSuccess.getId());
                        loanSuccess.setName(loanSuccess.getName());
                        loanSuccess.setLocation(loanSuccess.getLocation());
                        loanSuccess.setAddress(loanSuccess.getAddress());
                        loanSuccess.setStartDate(loanSuccess.getStartDate());
                        loanSuccess.setStartTime(loanSuccess.getStartTime());
                        loanSuccess.setEndDate(loanSuccess.getEndDate());
                        loanSuccess.setEndTime(loanSuccess.getEndTime());
                        loanSuccess.setImage(loanSuccess.getImage());
                        loanSuccess.setDetails(loanSuccess.getDetails());
                        loanSuccess.setContact(loanSuccess.getContact());

                        endedSaleMelaResponseList.add(loanSuccess);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    EndedSaleMelaAdapter adapter = new EndedSaleMelaAdapter(getActivity(), endedSaleMelaResponseList);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("size loan list", String.valueOf(endedSaleMelaResponseList.size()));
                } else
                    mNoData.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);

            } else
                CustomToast.customToast(getActivity(), getActivity().getString(R.string._404));

        } else
            CustomToast.customToast(getActivity(), getActivity().getString(R.string.no_response));
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(),getString(R.string._404_));
         //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
         // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
         //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
         //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(),getString(R.string.no_internet));
         //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "My Ended Sale Mela Fragment");
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
                        getEndedSaleData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "7841023392"));
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
