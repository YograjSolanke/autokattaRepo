package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.getBussinessChatResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 8/4/17.
 */

public class BussinessChatFragment extends Fragment implements RequestNotifier,SwipeRefreshLayout.OnRefreshListener{
    View mMychat;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ApiCall apiCall;
    List<getBussinessChatResponse.Success> mSuccesses = new ArrayList<>();

    FragmentActivity ctx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMychat = inflater.inflate(R.layout.fragment_buissness_chat, container, false);
        apiCall =new  ApiCall(getActivity(), this);
        mSwipeRefreshLayout =(SwipeRefreshLayout)mMychat.findViewById(R.id.swipeRefreshLayoutBussinessChat);

        mRecyclerView =(RecyclerView) mMychat.findViewById(R.id.recyclerBussinessChat);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener((OnRefreshListener) ctx);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new

                                         Runnable() {
                                             @Override
                                             public void run () {
                                                 mSwipeRefreshLayout.setRefreshing(true);
                                                 apiCall.getBussinessChat(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""));
                                             }
                                         });
        return mMychat;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                mSwipeRefreshLayout.setRefreshing(false);
                getBussinessChatResponse mGetBussinessChatResponse = (getBussinessChatResponse) response.body();
                for (getBussinessChatResponse.Success success : mGetBussinessChatResponse.getSuccess()){
                    success.setProductId(success.getProductId());
                    success.setProductName(success.getProductName());
                    success.setImage(success.getImage());
                    success.setPrice(success.getPrice());
                    success.setId(success.getId());
                    success.setName(success.getName());
                    success.setImages(success.getImages());
                    success.setPrice(success.getPrice());
                    success.setVehicleId(success.getVehicleId());
                    success.setCategory(success.getCategory());
                    success.setManufacturer(success.getManufacturer());
                    success.setModel(success.getModel());

                    mSuccesses.add(success);


                }
               // mMemberListAdapter = new MemberListRefreshAdapter(getActivity(), mSuccesses);
               /// mRecyclerView.setAdapter(mMemberListAdapter);
              //  mMemberListAdapter.notifyDataSetChanged();
            }else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        }else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {

    }
}
