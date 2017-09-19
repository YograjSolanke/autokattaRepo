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
    Bundle b, b1;

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
        b = new Bundle();
        b1 = getArguments();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGetQuotation = (RelativeLayout) mGroupCommunication.findViewById(R.id.get_quotation);
                mGetQuotation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        b.putString("grouptype", b1.getString("grouptype", ""));
                        b.putString("className", b1.getString("className", ""));
                        b.putInt("bundle_GroupId", b1.getInt("bundle_GroupId", 0));
                        b.putString("bundle_GroupName", b1.getString("bundle_GroupName", ""));
                        b.putString("bundle_Contact", b1.getString("bundle_Contact", ""));
                        b.putString("tabIndex", b1.getString("tabIndex", ""));

                        Intent intent = new Intent(getActivity(), GetGroupQuotation.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        //startActivity(new Intent(getActivity(), GetGroupQuotation.class));
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

