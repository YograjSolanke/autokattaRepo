package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;

/**
 * Created by ak-001 on 18/3/17.
 */

public class OtherWall extends Fragment {
    View mOtherWall;
    TextView mNoData;

    public OtherWall() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mOtherWall = inflater.inflate(R.layout.fragment_profile_other_wall, container, false);
        return mOtherWall;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mNoData = (TextView) mOtherWall.findViewById(R.id.no_category);
                mNoData.setText("Coming soon... please be connected for update..");
                //mNoData.setVisibility(View.GONE);


            }
        });
    }
}
