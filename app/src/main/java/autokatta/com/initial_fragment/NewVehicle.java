package autokatta.com.initial_fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autokatta.com.R;
import autokatta.com.view.NewVehicleCatalogActivity;

/**
 * Created by ak-001 on 2/5/17.
 */

public class NewVehicle extends Fragment {
    View mNewVehicle;
    com.github.clans.fab.FloatingActionButton button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mNewVehicle = inflater.inflate(R.layout.fragment_new_vehicle, container, false);
        button = (com.github.clans.fab.FloatingActionButton) mNewVehicle.findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                startActivity(new Intent(getActivity(), NewVehicleCatalogActivity.class), options.toBundle());
            }
        });
        return mNewVehicle;
    }
}
