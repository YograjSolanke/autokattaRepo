package autokatta.com.enquiries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import autokatta.com.R;
import autokatta.com.view.BussinessChatActivity;

/**
 * Created by ak-003 on 24/4/17.
 */

public class AllEnquiryTabFragment extends Fragment implements View.OnClickListener {
    public AllEnquiryTabFragment() {
    }

    View mEnquiryTab;
    RelativeLayout relativeBC, relativeTestDrive, relativeNewDealer;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEnquiryTab = inflater.inflate(R.layout.fragment_all_enquiry_tab, container, false);

        relativeBC = (RelativeLayout) mEnquiryTab.findViewById(R.id.relBC);
        relativeTestDrive = (RelativeLayout) mEnquiryTab.findViewById(R.id.relTest);
        relativeNewDealer = (RelativeLayout) mEnquiryTab.findViewById(R.id.relDealer);

        relativeBC.setOnClickListener(this);
        relativeTestDrive.setOnClickListener(this);
        relativeNewDealer.setOnClickListener(this);

        return mEnquiryTab;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relBC:
                startActivity(new Intent(getActivity(), BussinessChatActivity.class));
                break;

            case R.id.relTest:

                break;

            case R.id.relDealer:
                break;
        }
    }
}
