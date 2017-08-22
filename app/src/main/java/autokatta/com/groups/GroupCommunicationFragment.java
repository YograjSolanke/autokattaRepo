package autokatta.com.groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import autokatta.com.R;

/**
 * Created by ak-003 on 24/4/17.
 */

public class GroupCommunicationFragment extends Fragment {
    View mGroupCommunication;
    RelativeLayout mGetQuotation;

    public GroupCommunicationFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupCommunication = inflater.inflate(R.layout.fragment_group_communication, container, false);
        return mGroupCommunication;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGetQuotation = (RelativeLayout) mGroupCommunication.findViewById(R.id.get_quotation);
                mGetQuotation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), GetQuotation.class));
                    }
                });
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


}

