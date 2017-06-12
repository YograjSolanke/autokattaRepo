package autokatta.com.fragment_profile;

import android.app.Activity;
import android.content.Context;
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
    Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = (Activity) context;
        }
    }

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
            errorMessage(mActivity, getString(R.string.no_internet));
        }
    }

    @Override
    public void notifySuccess(Response<?> response) {
        list.clear();
        list1.clear();
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
                showMessage(mActivity, getString(R.string._404_));
            }
        } else {
            showMessage(mActivity, getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            showMessage(mActivity, getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            showMessage(mActivity, getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            showMessage(mActivity, getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            errorMessage(mActivity, getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            errorMessage(mActivity, getString(R.string.no_internet));
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

    public void showMessage(Activity activity, String message) {
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
                        getGroups();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.BLUE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
