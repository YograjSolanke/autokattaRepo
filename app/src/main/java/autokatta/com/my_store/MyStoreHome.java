package autokatta.com.my_store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 1/6/17.
 */

public class MyStoreHome extends Fragment {
    View mMyStoreHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMyStoreHome = inflater.inflate(R.layout.fragment_my_store_home, container, false);
        return mMyStoreHome;
    }
}
