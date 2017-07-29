package autokatta.com.initial_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 29/7/17.
 */

public class SoldVehicle extends Fragment {
    View mSoldVehicle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSoldVehicle = inflater.inflate(R.layout.fragment_sold_vehicle, container, false);
        return mSoldVehicle;
    }
}
