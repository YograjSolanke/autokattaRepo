package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 3/5/17.
 */

public class AddManualEnquiry extends Fragment {
    View mManualEnquiry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mManualEnquiry = inflater.inflate(R.layout.fragment_add_manual_enquiry, container, false);
        return mManualEnquiry;
    }
}
