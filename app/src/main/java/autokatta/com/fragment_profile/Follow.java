package autokatta.com.fragment_profile;

import android.content.Intent;
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
import autokatta.com.adapter.FollowersExpandableListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.response.GetFollowersResponse;
import autokatta.com.response.ModelFollowers;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 18/3/17.
 */

public class Follow extends Fragment implements RequestNotifier {
    View mFollow;
    ExpandableListView followersExpandableListView;
    List<ModelFollowers> list = new ArrayList<>();
    List<ModelFollowers> list1 = new ArrayList<>();
    HashMap<String, List<ModelFollowers>> mFollowersList;
    List<String> mHeaderList;
    FollowersExpandableListAdapter adapter;
    String contact, mOtherContact;
    boolean _hasLoadedOnce = false;
    ConnectionDetector mTestConnection;

    public Follow() {
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFollow = inflater.inflate(R.layout.fragment_profile_follow, container, false);
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
                }
                mFollowersList = new HashMap<>();
                mFollowersList.put(mHeaderList.get(0), list);
                mFollowersList.put(mHeaderList.get(1), list1);

                adapter = new FollowersExpandableListAdapter(getActivity(), mHeaderList, mFollowersList);
                followersExpandableListView.setAdapter(adapter);
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
            error();
        } else if (error instanceof UnknownHostException) {
            error();
        } else {
            Log.i("Check Class-", "Follow Fragment");
        }
    }

    @Override
    public void notifyString(String str) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                ApiCall apiCall = new ApiCall(getActivity(), this);
                Bundle bundle = getArguments();
                if (bundle != null) {
                    mOtherContact = bundle.getString("otherContact");
                    if (mTestConnection.isConnectedToInternet()) {
                        apiCall.getFollowers(mOtherContact);
                    } else {
                        error();
                    }
                } else {
                    if (mTestConnection.isConnectedToInternet()) {
                        apiCall.getFollowers(contact);
                    } else {
                        error();
                    }
                }
                _hasLoadedOnce = true;
            }
        }
    }

    private void error() {
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                followersExpandableListView = (ExpandableListView) mFollow.findViewById(R.id.followexpanablelistview);
                ViewCompat.setNestedScrollingEnabled(followersExpandableListView, true);
                contact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
            }
        });
    }
}
