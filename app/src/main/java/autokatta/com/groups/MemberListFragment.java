package autokatta.com.groups;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import autokatta.com.adapter.MemberListRefreshAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.GetGroupContactsResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 25/3/17.
 */

public class MemberListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier {
    View mMemberList;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton floatCreateGroup;
    List<GetGroupContactsResponse.Success> mSuccesses = new ArrayList<>();
    List<String> ContactNoList;
    MemberListRefreshAdapter mMemberListAdapter;
    String call;
    Bundle bundle = new Bundle();
    //String group_id;
    String mCallfrom = "", mGroupId = "";
    TextView mNoData;
    ConnectionDetector mTestConnection;
    boolean _hasLoadedOnce = false;

    public MemberListFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemberList = inflater.inflate(R.layout.fragment_member_list, container, false);
        return mMemberList;
    }

    /*
    Get Group Contact...
     */
    private void getGroupContact(String group_id) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getGroupContacts(group_id);
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
                ContactNoList = new ArrayList<>();
                ContactNoList.clear();
                mSuccesses.clear();
                Cursor people = null;
                mSwipeRefreshLayout.setRefreshing(false);
                mNoData.setVisibility(View.GONE);
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};

                GetGroupContactsResponse mGetGroupContactsResponse = (GetGroupContactsResponse) response.body();
                if (!mGetGroupContactsResponse.getSuccess().isEmpty()) {
                    for (GetGroupContactsResponse.Success success : mGetGroupContactsResponse.getSuccess()) {
                        success.setUsername(success.getUsername());
                        success.setContact(success.getContact());
                        success.setStatus(success.getStatus());
                        success.setDp(success.getDp());
                        success.setMember(success.getMember());
                        success.setVehiclecount(success.getVehiclecount());
                        if (success.getStatus().equals("null"))
                            success.setStatus("No Status");

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
                    mMemberListAdapter = new MemberListRefreshAdapter(getActivity(), mGroupId, mSuccesses, mCallfrom);
                    mRecyclerView.setAdapter(mMemberListAdapter);
                    mMemberListAdapter.notifyDataSetChanged();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNoData.setVisibility(View.GONE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
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
            Log.i("Check Class-", "MemberList Fragment");
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
                floatCreateGroup = (FloatingActionButton) mMemberList.findViewById(R.id.fab);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMemberList.findViewById(R.id.swipeRefreshLayout);
                mNoData = (TextView) mMemberList.findViewById(R.id.no_category);
                mRecyclerView.setHasFixedSize(true);
                //group_id = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("group_id", "");
                bundle = getArguments();
                if (bundle != null) {
                    // group_id = bundle.getString("id");
                    call = bundle.getString("call");
                    System.out.println("Call in MemberList:" + call);
                }
                Bundle bundle = getArguments();
                if (bundle != null) {
                    mCallfrom = bundle.getString("grouptype");
                    mGroupId = bundle.getString("bundle_GroupId");
                    Log.i("Other", "->" + mCallfrom);
                    Log.i("GroupId", "MemberList->" + mGroupId);
                }

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                //getData();//Get Api...

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

                //For Other Profile
                if (mCallfrom.equalsIgnoreCase("OtherGroup") || mCallfrom.equalsIgnoreCase("JoinedGroups")) {
                    floatCreateGroup.setVisibility(View.GONE);
                }

                floatCreateGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupContactFragment fragment = new GroupContactFragment();
                        Bundle b = new Bundle();
                        b.putString("bundle_GroupId", mGroupId);
                        b.putStringArrayList("list", (ArrayList<String>) ContactNoList);
                        b.putString("call", "existGroup");
                        fragment.setArguments(b);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.profile_groups_container, fragment);
                        fragmentTransaction.addToBackStack("groupcontactfragment");
                        fragmentTransaction.commit();
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }
}