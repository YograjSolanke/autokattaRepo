package autokatta.com.fragment_profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
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
import autokatta.com.other.CustomToast;
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
    TextView mNoData;
    private ProgressDialog dialog;

    Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = getActivity();
        }
    }

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
            dialog.show();
            mApiCall.getStoreProfileInfo(loginContact);
        } else {
//           if (mActivity != null)
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null) {
            if (response.isSuccessful()) {
                GetStoreProfileInfoResponse mStoreProfileInfoResponse = (GetStoreProfileInfoResponse) response.body();
                if (!mStoreProfileInfoResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    mSuccesses.clear();
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
                    mNoData.setVisibility(View.VISIBLE);
                    CustomToast.customToast(getActivity(), "No product found");
                }
            } else {
//                if (mActivity != null)
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
//            if (mActivity != null)
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (error instanceof SocketTimeoutException) {

            CustomToast.customToast(getActivity(), getString(R.string._404_));

        } else if (error instanceof NullPointerException) {
//
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
//
        } else if (error instanceof ClassCastException) {

            CustomToast.customToast(getActivity(), getString(R.string.no_response));

        } else if (error instanceof ConnectException) {

            CustomToast.customToast(getActivity(), getString(R.string.no_internet));

        } else if (error instanceof UnknownHostException) {

            CustomToast.customToast(getActivity(), getString(R.string.no_internet));

        } else {
            Log.i("Check Class-", "About Store");
            error.printStackTrace();
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
                mNoData = (TextView) mAboutStore.findViewById(R.id.no_category);
                mNoData.setVisibility(View.GONE);
                ViewCompat.setNestedScrollingEnabled(mListView, true);

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");

                getStoreProfileInfo(getActivity().getSharedPreferences(getString(R.string.my_preference),
                                Context.MODE_PRIVATE).getString("loginContact",""));

            }
        });
    }

   /* public void showMessage(Activity activity, String message) {
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
                        getStoreProfileInfo(getActivity().getSharedPreferences(getString(R.string.my_preference),
                                Context.MODE_PRIVATE).getString("loginContact", ""));
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
