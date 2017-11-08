package autokatta.com.groups;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
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
import autokatta.com.interfaces.ItemClickListener;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetGroupContactsResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 25/3/17.
 */

public class MemberListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RequestNotifier, ItemClickListener {
    View mMemberList;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    com.github.clans.fab.FloatingActionButton floatCreateGroup;
    com.github.clans.fab.FloatingActionMenu floatingActionMenu;
    List<GetGroupContactsResponse.Success> mSuccesses = new ArrayList<>();
    List<String> ContactNoList;
    MemberListRefreshAdapter mMemberListAdapter;
    String call;
    Bundle bundle = new Bundle();
    //String group_id;
    String mCallfrom = "", bundle_GroupName = "";
    int mGroupId;
    TextView mNoData;
    ConnectionDetector mTestConnection;
    com.github.clans.fab.FloatingActionButton mRequestedContacts;
    boolean _hasLoadedOnce = false;
    Activity activity;

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
    private void getGroupContact(int group_id) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getGroupContacts(group_id, 1, 10);
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

                GetGroupContactsResponse mGetGroupContactsResponse = (GetGroupContactsResponse) response.body();
                if (!mGetGroupContactsResponse.getSuccess().isEmpty()) {
                    mNoData.setVisibility(View.GONE);
                    for (GetGroupContactsResponse.Success success : mGetGroupContactsResponse.getSuccess()) {
                        success.setUsername(success.getUsername());
                        success.setContact(success.getContact());
                        success.setStatus(success.getStatus());
                        success.setDp(success.getDp());
                        success.setMember(success.getMember());
                        success.setVehiclecount(success.getVehiclecount());
                        success.setProductcount(success.getProductcount());
                        success.setServicecount(success.getServicecount());
                        if (success.getStatus() == null)
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
                    mMemberListAdapter = new MemberListRefreshAdapter(getActivity(), mGroupId, mSuccesses, mCallfrom, bundle_GroupName,mRequestedContacts);
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
                //getGroupContact(mGroupId);
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
                floatCreateGroup = (com.github.clans.fab.FloatingActionButton) mMemberList.findViewById(R.id.fab);
                floatingActionMenu = (com.github.clans.fab.FloatingActionMenu) mMemberList.findViewById(R.id.menu_red);
                mSwipeRefreshLayout = (SwipeRefreshLayout) mMemberList.findViewById(R.id.swipeRefreshLayout);
                mRequestedContacts = (com.github.clans.fab.FloatingActionButton) mMemberList.findViewById(R.id.requesttoadd);
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
                    mGroupId = bundle.getInt("bundle_GroupId");
                    bundle_GroupName = bundle.getString("bundle_GroupName");
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
                 //   floatCreateGroup.setVisibility(View.GONE);

                    floatCreateGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GroupContactFragment fragment = new GroupContactFragment();
                            Bundle b = new Bundle();
                            b.putInt("bundle_GroupId", mGroupId);
                            b.putString("bundle_GroupName", bundle_GroupName);
                            b.putStringArrayList("list", (ArrayList<String>) ContactNoList);
                            b.putString("call", "request");
                            fragment.setArguments(b);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.memberFrame, fragment);
                            fragmentTransaction.addToBackStack("groupcontactfragment");
                            fragmentTransaction.commit();
                        }
                    });

                }else {

                    floatCreateGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GroupContactFragment fragment = new GroupContactFragment();
                            Bundle b = new Bundle();
                            b.putInt("bundle_GroupId", mGroupId);
                            b.putString("bundle_GroupName", bundle_GroupName);
                            b.putStringArrayList("list", (ArrayList<String>) ContactNoList);
                            b.putString("call", "existGroup");
                            fragment.setArguments(b);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.memberFrame, fragment);
                            fragmentTransaction.addToBackStack("groupcontactfragment");
                            fragmentTransaction.commit();
                        }
                    });
                }
                /*
                On Scrolled Changed Listener...
                 */

                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        /*if (dy > 0 ||dy<0 && mFab.isShown())
                            mFab.hide();*/
                        if (dy > 0) {
                            // Scroll Down
                            if (floatingActionMenu.isShown()) {
                                floatingActionMenu.hideMenu(true);
                            }
                        } else if (dy < 0) {
                            // Scroll Up
                            if (!floatingActionMenu.isShown()) {
                                floatingActionMenu.showMenu(true);
                            }
                        }

                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        /*if (newState == RecyclerView.SCROLL_STATE_IDLE){
                            mFab.show();
                        }*/
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

 /*   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.requests, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                Bundle b=new Bundle();
                b.putInt("bundle_GroupId", mGroupId);
                RequestedMembersList mRequestedMembersList = new RequestedMembersList();
                mRequestedMembersList.setArguments(b);
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.memberFrame, mRequestedMembersList, "requestedMemberList")
                        .addToBackStack("MemberList")
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        getGroupContact(mGroupId);
    }

    @Override
    public void onClick(View view, int position) {

    }
}