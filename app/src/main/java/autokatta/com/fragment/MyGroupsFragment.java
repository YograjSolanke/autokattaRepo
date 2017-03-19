package autokatta.com.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import autokatta.com.fragment_profile.About;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileGroupResponse;
import retrofit2.Response;

import static autokatta.com.R.id.fabCreateGroup;

/**
 * Created by ak-003 on 19/3/17.
 */

public class MyGroupsFragment extends Fragment implements RequestNotifier, View.OnClickListener {

    View mMyGroups;
    RecyclerView mRecyclerView;
    FloatingActionButton floatCreateGroup;
    SharedPreferences mSharedPreferences = null;
    ArrayList<ProfileGroupResponse.Success.MyGroup> profileGroupResponseArrayList;

    public MyGroupsFragment() {
        //Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyGroups = inflater.inflate(R.layout.fragment_my_groups, container, false);

        mRecyclerView = (RecyclerView) mMyGroups.findViewById(R.id.rv_recycler_view);
        floatCreateGroup = (FloatingActionButton) mMyGroups.findViewById(fabCreateGroup);
        floatCreateGroup.setOnClickListener(this);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

//        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
//        String myContact = mSharedPreferences.getString("contact","");

        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.Groups("7841023392");


        return mMyGroups;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        Log.i("GroupResponse=>", response.body().toString());

        if (response.isSuccessful()) {
            profileGroupResponseArrayList = new ArrayList<>();
            profileGroupResponseArrayList.clear();

            ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();
            ProfileGroupResponse.Success profileResponseSuccess = profileGroupResponse.getSuccess();

            if (!profileResponseSuccess.getMyGroups().isEmpty()) {
                for (ProfileGroupResponse.Success.MyGroup myGroupObject : profileResponseSuccess.getMyGroups()) {
                    myGroupObject.setId(myGroupObject.getId());
                    myGroupObject.setTitle(myGroupObject.getTitle());
                    myGroupObject.setGroupcount(myGroupObject.getGroupcount());
                    myGroupObject.setVehiclecount(myGroupObject.getVehiclecount());
                    myGroupObject.setImage(myGroupObject.getImage());
                    myGroupObject.setAdminVehicleCount(myGroupObject.getAdminVehicleCount());

                    profileGroupResponseArrayList.add(myGroupObject);

                }

                Log.i("Group=>", String.valueOf(profileGroupResponseArrayList.size()));
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
                    , "MyGroupsFragment");
        }

    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case fabCreateGroup:
                About about = new About();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.groupFrame, about).commit();

                break;

        }

    }
}
