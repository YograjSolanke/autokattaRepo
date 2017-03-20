package autokatta.com.browseStore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-003 on 20/3/17.
 */

public class ServiceBasedStore extends Fragment {

    View mServiceBased;

    public ServiceBasedStore() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mServiceBased = inflater.inflate(R.layout.fragment_service_based_store, container, false);

        return mServiceBased;
    }
}
