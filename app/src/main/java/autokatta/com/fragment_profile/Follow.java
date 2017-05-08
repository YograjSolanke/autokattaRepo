package autokatta.com.fragment_profile;

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
import autokatta.com.adapter.FollowersExpandableListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetFollowersResponse;
import autokatta.com.response.ModelFollowers;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 18/3/17.
 */

public class Follow extends Fragment implements RequestNotifier {
    View mFollow;

    public Follow() {
        //Empty Constructor
    }

    ExpandableListView followersExpandableListView;
    List<ModelFollowers> list = new ArrayList<>();
    List<ModelFollowers> list1 = new ArrayList<>();
    HashMap<String, List<ModelFollowers>> mFollowersList;
    List<String> mHeaderList;
    FollowersExpandableListAdapter adapter;
    String contact;//="7841023392";
    String mOtherContact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFollow = inflater.inflate(R.layout.fragment_profile_follow, container, false);
        followersExpandableListView = (ExpandableListView) mFollow.findViewById(R.id.followexpanablelistview);
        ViewCompat.setNestedScrollingEnabled(followersExpandableListView, true);
        contact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        ApiCall apiCall = new ApiCall(getActivity(), this);


        Bundle bundle = getArguments();
        if (bundle != null) {
            mOtherContact = bundle.getString("otherContact");
            apiCall.getFollowers(mOtherContact);
            Log.i("Other in follow", "->" + contact);
        } else {
            apiCall.getFollowers(contact);
            Log.i("User", "->" + contact);
        }
        return mFollow;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mHeaderList = new ArrayList<>();
                mHeaderList.add("Followers");
                mHeaderList.add("Following");
                list.clear();
                list1.clear();
                GetFollowersResponse getFollowersResponse = (GetFollowersResponse) response.body();
                for (GetFollowersResponse.Follower success : getFollowersResponse.getSuccess().getFollowers()) {
                    ModelFollowers modelFollower = new ModelFollowers();
                    modelFollower.setName(success.getName());
                    modelFollower.setContact(success.getContact());
                    modelFollower.setProfilePic(success.getProfilePic());

                    list.add(modelFollower);
                }
                for (GetFollowersResponse.Following following : getFollowersResponse.getSuccess().getFollowing()) {
                    ModelFollowers modelFollowers = new ModelFollowers();
                    modelFollowers.setName(following.getName());
                    modelFollowers.setContact(following.getContact());
                    modelFollowers.setProfilePic(following.getProfilePic());

                    list1.add(modelFollowers);
                    Log.i("list1", "->" + list1.get(0).getName());
                }
                mFollowersList = new HashMap<>();
                mFollowersList.put(mHeaderList.get(0), list);
                mFollowersList.put(mHeaderList.get(1), list1);

                adapter = new FollowersExpandableListAdapter(getActivity(), mHeaderList, mFollowersList);
                followersExpandableListView.setAdapter(adapter);
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
            Log.i("Check Class-", "Follow Fragment");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
