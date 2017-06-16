package autokatta.com.fragment_profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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
    ExpandableListView followersExpandableListView;
    List<ModelFollowers> list = new ArrayList<>();
    List<ModelFollowers> list1 = new ArrayList<>();
    HashMap<String, List<ModelFollowers>> mFollowersList;
    List<String> mHeaderList;
    FollowersExpandableListAdapter adapter;
    String contact, mOtherContact;
    boolean _hasLoadedOnce = false;
    ConnectionDetector mTestConnection;
    ApiCall apiCall;
    Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            if (mActivity != null)
                mActivity = getActivity();
        }
    }

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

                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {

            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {

            CustomToast.customToast(getActivity(), getString(R.string._404_));

        } else if (error instanceof NullPointerException) {

            CustomToast.customToast(getActivity(), getString(R.string.no_response));

        } else if (error instanceof ClassCastException) {

            CustomToast.customToast(getActivity(), getString(R.string.no_response));

        } else if (error instanceof ConnectException) {

            CustomToast.customToast(getActivity(), getString(R.string.no_internet));

        } else if (error instanceof UnknownHostException) {

            CustomToast.customToast(getActivity(), getString(R.string.no_internet));

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

                        CustomToast.customToast(getActivity(), getString(R.string.no_internet));
                    }
                } else {
                    if (mTestConnection.isConnectedToInternet()) {
                        apiCall.getFollowers(contact);
                    } else {

                        CustomToast.customToast(getActivity(), getString(R.string.no_internet));
                    }
                }
                _hasLoadedOnce = true;
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiCall = new ApiCall(getActivity(), this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTestConnection = new ConnectionDetector(getActivity());
                followersExpandableListView = (ExpandableListView) mFollow.findViewById(R.id.followexpanablelistview);
                ViewCompat.setNestedScrollingEnabled(followersExpandableListView, true);
                contact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");

                Bundle bundle = getArguments();
                if (bundle != null) {
                    mOtherContact = bundle.getString("otherContact");
                    if (mTestConnection.isConnectedToInternet()) {
                        apiCall.getFollowers(mOtherContact);
                    } else {

                        CustomToast.customToast(getActivity(), getString(R.string.no_internet));
                    }
                } else {
                    if (mTestConnection.isConnectedToInternet()) {
                        apiCall.getFollowers(contact);
                    } else {

                        CustomToast.customToast(getActivity(), getString(R.string.no_internet));
                    }
                }
            }
        });
    }

   /* public void showMessage(Activity activity, String message) {
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
                        Bundle bundle = getArguments();
                        if (bundle != null) {
                            mOtherContact = bundle.getString("otherContact");
                            if (mTestConnection.isConnectedToInternet()) {
                                apiCall.getFollowers(mOtherContact);
                            } else {
                                *//*if (mActivity != null)
                                    errorMessage(mActivity, getString(R.string.no_internet));*//*
                            }
                        } else {
                            if (mTestConnection.isConnectedToInternet()) {
                                apiCall.getFollowers(contact);
                            } else {
                                *//*if (mActivity != null)
                                    errorMessage(mActivity, getString(R.string.no_internet));*//*
                            }
                        }
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
