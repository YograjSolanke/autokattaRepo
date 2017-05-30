package autokatta.com.fragment_profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.ProfileMyStoreAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.GetStoreProfileInfoResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 18/3/17.
 */

public class AboutStore extends Fragment implements RequestNotifier, View.OnClickListener {
    View mAboutStore;
    ListView mListView;
    List<GetStoreProfileInfoResponse.Success> mSuccesses = new ArrayList<>();
    ProfileMyStoreAdapter myStoreAdapter;
    //android.support.design.widget.FloatingActionButton mCreateStore;
    //FloatingActionButton mCreateStore;
    boolean _hasLoadedOnce = false;
    ConnectionDetector mTestConnection;

    public AboutStore() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAboutStore = inflater.inflate(R.layout.fragment_store_layout, container, false);
        return mAboutStore;
    }

    /*
    Get Store Profile Info...
     */
    private void getStoreProfileInfo(String loginContact) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getStoreProfileInfo(loginContact);
        } else {
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
        }
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
                Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_LONG).show();
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
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.create_store:

                Bundle bundle = new Bundle();
                // bundle.putString("store_id", Store_id);
                bundle.putString("className", "AboutStore");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(getActivity(), MyStoreListActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent, options.toBundle());
                break;*/
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                getStoreProfileInfo(getActivity().getSharedPreferences(getString(R.string.my_preference),
                        Context.MODE_PRIVATE).getString("loginContact", ""));
                _hasLoadedOnce = true;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                mListView = (ListView) mAboutStore.findViewById(R.id.store_list);
                ViewCompat.setNestedScrollingEnabled(mListView, true);
               /* getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getStoreProfileInfo(getActivity().getSharedPreferences(getString(R.string.my_preference),
                                Context.MODE_PRIVATE).getString("loginContact",""));
                    }
                });*/
            }
        });
    }
}
