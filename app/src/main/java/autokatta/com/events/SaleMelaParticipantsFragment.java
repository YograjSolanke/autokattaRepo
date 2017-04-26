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

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.AuctionParticipantAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.AuctionParticipantsResponse;
import autokatta.com.response.AuctionParticipantsResponse.Success;
import retrofit2.Response;

/**
 * Created by ak-005 on 26/4/17.
 */

public class SaleMelaParticipantsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {


    View mAuctionParticipants;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String strAuctionId = "";
    List<Success> participantList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuctionParticipants = inflater.inflate(R.layout.fragment_simple_listview, container, false);

        mRecyclerView = (RecyclerView) mAuctionParticipants.findViewById(R.id.recyclerMain);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mAuctionParticipants.findViewById(R.id.swipeRefreshLayoutMain);

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

                            getAuctionParticipant(strAuctionId);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mAuctionParticipants;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void getAuctionParticipant(String strAuctionId) {
        ApiCall apiCall = new ApiCall(getActivity(), this);
//        apiCall.AuctionParticipantData(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
//                .getString("loginContact", ""), strAuctionId);
        // apiCall.AuctionParticipantData("9890950817", "1047");
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                participantList.clear();
                AuctionParticipantsResponse participantsResponse = (AuctionParticipantsResponse) response.body();

                for (AuctionParticipantsResponse.Success success : participantsResponse.getSuccess()) {

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
                AuctionParticipantAdapter adapter = new AuctionParticipantAdapter(getActivity(), strAuctionId, participantList);
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
            Log.i("Check Class-", "Sale mela Participants Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
