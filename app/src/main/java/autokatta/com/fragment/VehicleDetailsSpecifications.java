package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;

/**
 * Created by ak-001 on 27/3/17.
 */

public class VehicleDetailsSpecifications extends Fragment {
    View mVehicleSpecifications;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehicleSpecifications = inflater.inflate(R.layout.fragment_standard_specification, container, false);
        return mVehicleSpecifications;
    }
}
