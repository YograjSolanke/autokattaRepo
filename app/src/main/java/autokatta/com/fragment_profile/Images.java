package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 25/7/17.
 */

public class Images extends Fragment {
    View mImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImages = inflater.inflate(R.layout.fragment_update, container, false);
        return mImages;
    }
}
