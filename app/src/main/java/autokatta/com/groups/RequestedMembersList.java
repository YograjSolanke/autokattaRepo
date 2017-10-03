package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import autokatta.com.adapter.RequestedMemberListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetRequestedContactsResponse;
import retrofit2.Response;

/**
 * Created by ak-005 on 28/9/17.
 */

public class RequestedMembersList extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {

    View mMemberList;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<GetRequestedContactsResponse.Success> mSuccesses = new ArrayList<>();
    List<String> ContactNoList;
    RequestedMemberListAdapter mMemberListAdapter;
    String call;
    Bundle bundle = new Bundle();
    int mGroupId;
    TextView mNoData;
    ConnectionDetector mTestConnection;
    boolean _hasLoadedOnce = false;
    Activity activity;

    public RequestedMembersList() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemberList = inflater.inflate(R.layout.requested_members_list, container, false);
        return mMemberList;
    }

    /*
    Get Group Contact...
     */
    private void getGroupContact(int group_id) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getRequestedContacts(group_id);
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //errorMessage(activity, getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                ContactNoList = new ArrayList<>();
                ContactNoList.clear();
                mSuccesses.clear();
                Cursor people = null;
                mSwipeRefreshLayout.setRefreshing(false);
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};

                GetRequestedContactsResponse mGetGroupContactsResponse = (GetRequestedContactsResponse) response.body();
                if (!mGetGroupContactsResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (GetRequestedContactsResponse.Success success : mGetGroupContactsResponse.getSuccess()) {
                        success.setUsername(success.getUsername());
                        success.setRequestID(success.getRequestID());
                        success.setContact(success.getContact());
                        success.setProfilePic(success.getProfilePic());
                        success.setGroupID(success.getGroupID());

                        success.setContact(success.getContact().replaceAll(" ", "").replaceAll(",", "").replaceAll("-", "").
                                replace("(", "").replace(")", ""));

                        if (success.getContact().length() > 10)
                            success.setContact(success.getContact().substring(success.getContact().length() - 10));

                        Boolean found = false;
                        try {
                            if (getActivity() != null) {
                                people = getActivity().getContentResolver().query(uri, projection, null, null, null);
                            }
                            int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                            int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                            people.moveToFirst();
                            do {
                                String namesfound = people.getString(indexName);
                                String numberfound = people.getString(indexNumber);

                                numberfound = numberfound.replaceAll(" ", "").replaceAll(",", "").replaceAll("-", "");
                                numberfound = numberfound.replace("(", "").replace(")", "");

                                if (numberfound.length() > 10)
                                    numberfound = numberfound.substring(numberfound.length() - 10);

                                //Remove All Space From Web Service Contacts And Mobile Contacts And Match Each Other
                                if (success.getContact().equalsIgnoreCase(numberfound)) {
                                    //NameList.add(namesfound+"="+getcont+"="+status+"="+image+"="+userName+"="+type+"="+vehicle_cnt);
                                    success.setUsername(namesfound);
                                    ContactNoList.add(success.getContact());
                                    found = true;
                                    break;
                                }
                            } while (people.moveToNext());

                            if (!found) {
                                // NameList.add("Unknown="+getcont+"="+status+"="+image+"="+userName+"="+type+"="+vehicle_cnt);
                                success.setUsername("Unknown");
                                ContactNoList.add(success.getContact());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mSuccesses.add(success);
                    }
                    mMemberListAdapter = new RequestedMemberListAdapter(getActivity(),mSuccesses);
                    mRecyclerView.setAdapter(mMemberListAdapter);
                    mMemberListAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                //  CustomToast.customToast(getActivity(), getString(R.string._404_));
                //showMessage(activity, getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
            //showMessage(activity, getString(R.string.no_response));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (activity != null) {
                activity = getActivity();
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            //   showMessage(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            //CustomToast.customToast(getActivity(), getString(R.string.no_response));
            // showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            // CustomToast.customToast(getActivity(), getString(R.string.no_response));
            //   showMessage(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //   errorMessage(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "memberlistfrgment");
            error.printStackTrace();
        }
    }


    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onRefresh() {
        mSuccesses.clear();
        mSwipeRefreshLayout.setRefreshing(false);
        getGroupContact(mGroupId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                getGroupContact(mGroupId);
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
                mRecyclerView = (RecyclerView) mMemberList.findViewById(R.id.rv_recycler_view);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMemberList.findViewById(R.id.swipeRefreshLayout);
                mNoData = (TextView) mMemberList.findViewById(R.id.no_category);
                mRecyclerView.setHasFixedSize(true);
                Bundle b=getArguments();
               mGroupId= b.getInt("bundle_GroupId");
                //group_id = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("group_id", "");


                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                //getData();//Get Api...

              //  getActivity().setTitle("Recived Requests To Add");
                mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getGroupContact(mGroupId);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        getGroupContact(mGroupId);
    }

}
