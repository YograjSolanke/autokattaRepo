package autokatta.com.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.enquiries.AllEnquiryTabActivity;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.StoreResponse;
import autokatta.com.view.MyStoreListActivity;
import autokatta.com.view.StoreViewActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreInfo extends Fragment implements RequestNotifier, View.OnClickListener {
    View mAbout;
    String myContact, Store_id, StoreContact;
    ImageView editStore, addEnquiry;
    boolean hasView;
    NestedScrollView scrollView;
    RelativeLayout mRel;
    TextView storeName, storeLocation, storeWebsite, storeWorkDays, storeOpen,
            storeClose, storeAddress, storeServiceOffered, storeType, storeDescription, mNoData;
    ConnectionDetector mTestConnection;
    Activity mActivity;
    public StoreInfo() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAbout = inflater.inflate(R.layout.store_info_fragment, container, false);
        return mAbout;
    }

    private void getStoredata(String myContact, String store_id) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getStoreData(myContact, store_id);
        } else {
            //errorMessage(mActivity, getString(R.string.no_internet));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editStore:
                Bundle bundle = new Bundle();
                bundle.putString("store_id", Store_id);
                bundle.putString("className", "StoreViewActivity");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(getActivity(), MyStoreListActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent, options.toBundle());
                getActivity().finish();
                break;

            case R.id.enquiry:
                ActivityOptions option = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getActivity(), AllEnquiryTabActivity.class), option.toBundle());
                break;
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                StoreResponse storeResponse = (StoreResponse) response.body();
                if (!storeResponse.getSuccess().isEmpty()) {

                    for (StoreResponse.Success success : storeResponse.getSuccess()) {
                        storeName.setText(success.getName());
                        StoreContact = success.getContact();
                        storeLocation.setText(success.getLocation());
                        storeWebsite.setText(success.getWebsite());
                        storeWorkDays.setText(success.getWorkingDays());
                        storeOpen.setText(success.getStoreOpenTime());
                        storeClose.setText(success.getStoreCloseTime());
                        storeAddress.setText(success.getAddress());
                        storeDescription.setText(success.getStoreDescription());
                        storeType.setText(success.getStoreType());
                        storeServiceOffered.setText(success.getCategory());
                        if (StoreContact.contains(myContact)) {
                            editStore.setVisibility(View.VISIBLE);
                            addEnquiry.setVisibility(View.VISIBLE);
                        }
                    }
                } else {

                }
            } else {
                /*if (mActivity != null)
                showMessage(mActivity, getString(R.string._404_));*/
            }
        } else {
            /*if (mActivity != null)
            showMessage(mActivity, getString(R.string.no_response));*/
        }

    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            /*if (mActivity != null)
            showMessage(mActivity, getString(R.string._404_));*/
        } else if (error instanceof NullPointerException) {
            /*if (mActivity != null)
            showMessage(mActivity, getString(R.string.no_response));*/
        } else if (error instanceof ClassCastException) {
            /*if (mActivity != null)
            showMessage(mActivity, getString(R.string.no_response));*/
        } else if (error instanceof ConnectException) {
            /*if (mActivity != null)
            errorMessage(mActivity, getString(R.string.no_internet));*/
        } else if (error instanceof UnknownHostException) {
            /*if (mActivity != null)
            errorMessage(mActivity, getString(R.string.no_internet));*/
        } else {
            Log.i("Check Class-"
                    , "StoreInfo");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !hasView) {
                getStoredata(myContact, Store_id);
                hasView = true;
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
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

                mRel = (RelativeLayout) mAbout.findViewById(R.id.info_home);
                storeName = (TextView) mAbout.findViewById(R.id.editstname);
                storeLocation = (TextView) mAbout.findViewById(R.id.autolocation);
                storeWebsite = (TextView) mAbout.findViewById(R.id.editwebsite);
                storeOpen = (TextView) mAbout.findViewById(R.id.edittiming);
                storeClose = (TextView) mAbout.findViewById(R.id.edittiming1);
                storeAddress = (TextView) mAbout.findViewById(R.id.editaddress);
                storeType = (TextView) mAbout.findViewById(R.id.storetype);
                editStore = (ImageView) mAbout.findViewById(R.id.editStore);
                addEnquiry = (ImageView) mAbout.findViewById(R.id.enquiry);
                storeDescription = (TextView) mAbout.findViewById(R.id.editstoredescription);
                storeWorkDays = (TextView) mAbout.findViewById(R.id.editworkingdays);
                storeServiceOffered = (TextView) mAbout.findViewById(R.id.autoservices);
                scrollView = (NestedScrollView) mAbout.findViewById(R.id.mainScroll);

                Bundle b = getArguments();
                Store_id = b.getString("store_id");
                getStoredata(myContact, Store_id);

                scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                        if (scrollY > oldScrollY) {
                            ((StoreViewActivity) getActivity()).hideFloatingButton();
                            Log.e("Hide", "->");
                        }
                        if (scrollY < oldScrollY) {
                            ((StoreViewActivity) getActivity()).showFloatingButton();
                            Log.e("show1", "->");
                        }
                        if (scrollY == 0) {
                            ((StoreViewActivity) getActivity()).showFloatingButton();
                            Log.e("show2", "->");
                        }
                        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                            ((StoreViewActivity) getActivity()).hideFloatingButton();
                            Log.e("show3", "->");
                        }
                    }
                });
            }
        });
        editStore.setOnClickListener(this);
        addEnquiry.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = getActivity();
        }
    }

    /*public void showMessage(Activity activity, String message) {
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
                        getStoredata(myContact, Store_id);
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
