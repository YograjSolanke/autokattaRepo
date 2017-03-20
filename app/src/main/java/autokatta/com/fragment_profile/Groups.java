package autokatta.com.fragment_profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupsExpandableListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ModelGroups;
import autokatta.com.response.ProfileAboutResponse;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 18/3/17.
 */

public class Groups extends Fragment implements RequestNotifier {
    View mGroups;

    ExpandableListView groupExpandableListView;
    SharedPreferences mSharedPreferences = null;
    List<ModelGroups> list = new ArrayList<>();
    List<ModelGroups> list1 = new ArrayList<>();
    HashMap<String, List<ModelGroups>> mGroupList;
    List<String> mHeaderList;
    GroupsExpandableListAdapter adapter;

    public Groups() {
     //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroups = inflater.inflate(R.layout.fragment_profile_group, container, false);

        groupExpandableListView = (ExpandableListView) mGroups.findViewById(R.id.groupexpanablelistview);

        ViewCompat.setNestedScrollingEnabled(groupExpandableListView, true);
        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        String contact = mSharedPreferences.getString("loginContact", "");

        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.profileGroup(contact);
        return mGroups;
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                mHeaderList = new ArrayList<>();
                mHeaderList.add("My Groups");
                mHeaderList.add("Joined groups");

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
                    Log.i("list1","->"+list1.get(0).getTitle());
                }
                mGroupList = new HashMap<>();
                mGroupList.put(mHeaderList.get(0), list);
                mGroupList.put(mHeaderList.get(1), list1);

                adapter = new GroupsExpandableListAdapter(getActivity(), mHeaderList, mGroupList);
                groupExpandableListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Login Activity");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
