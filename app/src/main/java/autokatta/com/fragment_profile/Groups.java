package autokatta.com.fragment_profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
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


    public Groups() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroups = inflater.inflate(R.layout.fragment_profile_group, container, false);

        groupExpandableListView = (ExpandableListView) mGroups.findViewById(R.id.groupexpanablelistview);

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        String contact = mSharedPreferences.getString("user_contact", "");

        ApiCall apiCall = new ApiCall(getActivity(), this);
        apiCall.profileGroup(contact);
        return mGroups;
    }

    @Override
    public void notifySuccess(Response<?> response) {

        if (response != null) {
            if (response.isSuccessful()) {
                ProfileGroupResponse profileGroupResponse = (ProfileGroupResponse) response.body();

//                for(ProfileGroupResponse.Success mesaage : profileGroupResponse.getSuccess()){
//
//                }
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
