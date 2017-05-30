package autokatta.com.fragment_profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupsExpandableListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.ModelGroups;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 18/3/17.
 */

public class Groups extends Fragment implements RequestNotifier, View.OnClickListener {
    View mGroups;
    ExpandableListView groupExpandableListView;
    SharedPreferences mSharedPreferences = null;
    List<ModelGroups> list = new ArrayList<>();
    List<ModelGroups> list1 = new ArrayList<>();
    HashMap<String, List<ModelGroups>> mGroupList;
    List<String> mHeaderList;
    GroupsExpandableListAdapter adapter;
    //FloatingActionButton mCreateGroup;
    String contact, GroupType;
    boolean _hasLoadedOnce = false;
    ConnectionDetector mTestConnection;

    public Groups() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroups = inflater.inflate(R.layout.fragment_profile_group, container, false);
        return mGroups;
    }

    private void getGroups() {
        if (mTestConnection.isConnectedToInternet()) {
            ApiCall apiCall = new ApiCall(getActivity(), this);
            apiCall.profileGroup(contact);
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
                mHeaderList = new ArrayList<>();
                mHeaderList.add("MyGroups");
                mHeaderList.add("JoinedGroups");

                ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
                for (ProfileGroupResponse.MyGroup success : profileGroupResponse.getSuccess().getMyGroups()) {
                    ModelGroups modelGroups = new ModelGroups();
                    modelGroups.setId(success.getId());
                    modelGroups.setTitle(success.getTitle());
                    modelGroups.setImage(success.getImage());
                    modelGroups.setGroupCount(success.getGroupcount());
                    modelGroups.setVehicleCount(success.getVehiclecount());
                    modelGroups.setAdminVehicleCount(success.getAdminVehicleCount());
                    list.add(modelGroups);
                }
                for (ProfileGroupResponse.JoinedGroup joinedGroup : profileGroupResponse.getSuccess().getJoinedGroups()) {
                    ModelGroups modelGroups = new ModelGroups();
                    modelGroups.setId(joinedGroup.getId());
                    modelGroups.setTitle(joinedGroup.getTitle());
                    modelGroups.setImage(joinedGroup.getImage());
                    modelGroups.setGroupCount(joinedGroup.getGroupcount());
                    modelGroups.setVehicleCount(joinedGroup.getVehiclecount());
                    list1.add(modelGroups);
                }
                mGroupList = new HashMap<>();
                mGroupList.put(mHeaderList.get(0), list);
                mGroupList.put(mHeaderList.get(1), list1);

                adapter = new GroupsExpandableListAdapter(getActivity(), mHeaderList, mGroupList,GroupType);
                groupExpandableListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

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
        } else {
            Log.i("Check Class-", "Groups Fragment");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.create_group:
                Intent intent = new Intent(getActivity(), GroupTabs.class);
                intent.putExtra("ClassName", "Groups");
                startActivity(intent);
                getActivity().finish();
                break;*/
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser && !_hasLoadedOnce) {
                getGroups();
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
                groupExpandableListView = (ExpandableListView) mGroups.findViewById(R.id.groupexpanablelistview);
                ViewCompat.setNestedScrollingEnabled(groupExpandableListView, true);
                mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);

                Bundle bundle = getArguments();
                if (bundle != null) {
                    contact = bundle.getString("otherContact");
                    GroupType = "OtherGroup";
                    getGroups();
                } else {
                    contact = mSharedPreferences.getString("loginContact", "");
                    GroupType = "MyGroup";
                    getGroups();
                }
            }
        });
    }
}
