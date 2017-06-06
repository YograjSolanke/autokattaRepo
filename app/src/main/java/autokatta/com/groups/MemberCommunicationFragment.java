package autokatta.com.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-005 on 22/5/17.
 */

public class MemberCommunicationFragment extends Fragment {
    View mMemberCommunication;

    public MemberCommunicationFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemberCommunication = inflater.inflate(R.layout.fragment_group_communication, container, false);
        return mMemberCommunication;
    }
}
