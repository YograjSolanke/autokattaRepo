package autokatta.com.fragment_profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.ProfileMyStoreAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetStoreProfileInfoResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 18/3/17.
 */

public class AboutStore extends Fragment implements RequestNotifier {
    View mAboutStore;
    ListView mListView;
    List<GetStoreProfileInfoResponse.Success> mSuccesses = new ArrayList<>();
    ProfileMyStoreAdapter myStoreAdapter;

    public AboutStore(){
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAboutStore = inflater.inflate(R.layout.fragment_store_layout, container, false);

        mListView = (ListView) mAboutStore.findViewById(R.id.store_list);
        ViewCompat.setNestedScrollingEnabled(mListView, true);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getStoreProfileInfo(getActivity().getSharedPreferences(getString(R.string.my_preference),
                        Context.MODE_PRIVATE).getString("loginContact",""));
            }
        });
        return mAboutStore;
    }

    /*
    Get Store Profile Info...
     */
    private void getStoreProfileInfo(String loginContact) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getStoreProfileInfo(loginContact);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                GetStoreProfileInfoResponse mStoreProfileInfoResponse = (GetStoreProfileInfoResponse) response.body();
                for (GetStoreProfileInfoResponse.Success infoResponse : mStoreProfileInfoResponse.getSuccess()) {
                    infoResponse.setStoreId(infoResponse.getStoreId());
                    infoResponse.setStoreName(infoResponse.getStoreName());
                    infoResponse.setLocation(infoResponse.getLocation());
                    infoResponse.setStoreLogo(infoResponse.getStoreLogo());
                    infoResponse.setStoreType(infoResponse.getStoreType());
                    mSuccesses.add(infoResponse);
                }
                myStoreAdapter = new ProfileMyStoreAdapter(getActivity(), mSuccesses);
                mListView.setAdapter(myStoreAdapter);
                myStoreAdapter.notifyDataSetChanged();
            } else {
                //       Snackbar.make(findViewById(R.id.user_profile), getString(R.string._404), Snackbar.LENGTH_LONG).show();
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
