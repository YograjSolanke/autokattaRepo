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
import android.widget.Toast;

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
    com.github.clans.fab.FloatingActionButton floatCreateGroup;
    List<GetGroupContactsResponse.Success> mSuccesses = new ArrayList<>();
    List<String> ContactNoList;
    MemberListRefreshAdapter mMemberListAdapter;
    String call;
    Bundle bundle = new Bundle();
    //String group_id;
    String mCallfrom = "", mGroupId = "",bundle_GroupName="";
    TextView mNoData;
    ConnectionDetector mTestConnection;
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
    private void getGroupContact(String group_id) {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall mApiCall = new ApiCall(getActivity(), this);
            mApiCall.getGroupContacts(group_id);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
                    mNoData.setVisibility(View.VISIBLE);
                }
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), getString(R.string._404_), Toast.LENGTH_SHORT).show();
                //showMessage(activity, getString(R.string._404_));
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);

            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), getString(R.string._404_), Toast.LENGTH_SHORT).show();
                //showMessage(activity, getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
                Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                //showMessage(activity, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
                Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                //showMessage(activity, getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
                Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                //errorMessage(activity, getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
                Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                //errorMessage(activity, getString(R.string.no_internet));
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
                floatCreateGroup = (com.github.clans.fab.FloatingActionButton) mMemberList.findViewById(R.id.fab);
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
                    floatCreateGroup.setVisibility(View.GONE);
                }

                floatCreateGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupContactFragment fragment = new GroupContactFragment();
                        Bundle b = new Bundle();
                        b.putString("bundle_GroupId", mGroupId);
                        b.putString("bundle_GroupName", bundle_GroupName);
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
                            if (floatCreateGroup.isShown()) {
                                floatCreateGroup.hide(true);
                            }
                        } else if (dy < 0) {
                            // Scroll Up
                            if (!floatCreateGroup.isShown()) {
                                floatCreateGroup.show(true);
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
                        getGroupContact(mGroupId);
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