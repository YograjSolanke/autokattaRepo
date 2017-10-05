package autokatta.com.initial_fragment;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GetVehicleListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleListResponse;
import autokatta.com.view.NewVehicleCatalogActivity;
import retrofit2.Response;

/**
 * Created by ak-001 on 2/5/17.
 */

public class NewVehicle extends Fragment implements RequestNotifier {
    View mNewVehicle;
    com.github.clans.fab.FloatingActionButton button;
    ListView mListView;
    List<GetVehicleListResponse.Success> mGetVehicle;
    private ProgressDialog dialog;

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
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        mGetVehicle = new ArrayList<>();
        mListView = (ListView) mNewVehicle.findViewById(R.id.upload_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mGetVehicle.get(position).getName();
                int subTypeId = mGetVehicle.get(position).getId();
                if (s != null) {
                    NewVehicleCategory category = new NewVehicleCategory();
                    Bundle bundle = new Bundle();
                    bundle.putString("categoryName", s);
                    bundle.putInt("categoryId", subTypeId);
                    category.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.myNewVehicleFrame, category, "vehicle_list")
                            .addToBackStack("vehicle_list")
                            .commit();
                }
            }
        });
        getVehicleList();
        return mNewVehicle;
    }

    /*
    Get Vehicle List...
     */
    private void getVehicleList() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        //dialog.show();
        mApiCall.getVehicleList();
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                mGetVehicle.clear();
                if (response.body() instanceof GetVehicleListResponse) {
                    GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                    if (!mGetVehicleListResponse.getSuccess().isEmpty()) {
                        for (GetVehicleListResponse.Success mSuccess : mGetVehicleListResponse.getSuccess()) {
                            mSuccess.setId(mSuccess.getId());
                            mSuccess.setName(mSuccess.getName());
                            mGetVehicle.add(mSuccess);
                        }
                        GetVehicleListAdapter mGetVehicleListAdapter = new GetVehicleListAdapter(getActivity(), mGetVehicle);
                        mListView.setAdapter(mGetVehicleListAdapter);
                        mGetVehicleListAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
