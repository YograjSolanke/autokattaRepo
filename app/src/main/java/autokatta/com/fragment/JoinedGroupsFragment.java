package autokatta.com.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

import static autokatta.com.R.id.fabCreateGroup;


/**
 * Created by ak-003 on 19/3/17.
 */

public class JoinedGroupsFragment extends Fragment implements RequestNotifier {

    View mJoinedGroups;
    RecyclerView mRecyclerView;
    FloatingActionButton floatCreateGroup;
    SharedPreferences mSharedPreferences = null;
    ArrayList<ProfileGroupResponse.Success.JoinedGroup> profileGroupResponseArrayList;

    public JoinedGroupsFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mJoinedGroups = inflater.inflate(R.layout.fragment_my_groups, container, false);

        mRecyclerView = (RecyclerView) mJoinedGroups.findViewById(R.id.rv_recycler_view);
        floatCreateGroup = (FloatingActionButton) mJoinedGroups.findViewById(fabCreateGroup);
        floatCreateGroup.setVisibility(View.GONE);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
//        String myContact = mSharedPreferences.getString("contact","");

        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.Groups("7841023392");


        return mJoinedGroups;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        Log.i("GroupOtherResponse=>", response.body().toString());

        if (response.isSuccessful()) {
            profileGroupResponseArrayList = new ArrayList<>();
            profileGroupResponseArrayList.clear();

            ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
            ProfileGroupResponse.Success profileResponseSuccess = profileGroupResponse.getSuccess();

            if (!profileResponseSuccess.getJoinedGroups().isEmpty()) {
                for (ProfileGroupResponse.Success.JoinedGroup joinedGroupObject : profileResponseSuccess.getJoinedGroups()) {
                    joinedGroupObject.setId(joinedGroupObject.getId());
                    joinedGroupObject.setTitle(joinedGroupObject.getTitle());
                    joinedGroupObject.setGroupcount(joinedGroupObject.getGroupcount());
                    joinedGroupObject.setVehiclecount(joinedGroupObject.getVehiclecount());
                    joinedGroupObject.setImage(joinedGroupObject.getImage());

                    profileGroupResponseArrayList.add(joinedGroupObject);

                }

                Log.i("GroupOther=>", String.valueOf(profileGroupResponseArrayList.size()));
            } else
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }


    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getString(R.string._404), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Toast.makeText(getActivity(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Check Class-"
                    , "JoinedGroupsFragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

}
