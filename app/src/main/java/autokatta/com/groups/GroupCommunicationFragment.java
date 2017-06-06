package autokatta.com.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-003 on 24/4/17.
 */

public class GroupCommunicationFragment extends Fragment {
    View mGroupCommunication;

    public GroupCommunicationFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupCommunication = inflater.inflate(R.layout.fragment_group_communication, container, false);
        return mGroupCommunication;
    }
}

