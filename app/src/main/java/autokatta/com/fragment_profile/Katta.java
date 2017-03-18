package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 18/3/17.
 */

public class Katta extends Fragment {
    View mKatta;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mKatta = inflater.inflate(R.layout.fragment_profile_katta, container, false);
        return mKatta;
    }
}
