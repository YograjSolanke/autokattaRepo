package autokatta.com.upload_vehicle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.adapter.GetVehicleListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetVehicleListResponse;
import retrofit2.Response;

/**
 * Created by ak-001 on 20/3/17.
 */

public class VehicleList extends Fragment  implements RequestNotifier {
    View mVehicleList;
    ListView mListView;
    List<GetVehicleListResponse.Success> mGetVehicle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVehicleList = inflater.inflate(R.layout.fragment_vehicle_upload_list, container, false);
        mGetVehicle = new ArrayList<>();
        mListView = (ListView) mVehicleList.findViewById(R.id.upload_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        getData();
        return mVehicleList;
    }

    private void getData() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleCount("7841023392");
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){
                GetVehicleListResponse mGetVehicleListResponse = (GetVehicleListResponse) response.body();
                if (!mGetVehicleListResponse.getSuccess().isEmpty()){
                    for (GetVehicleListResponse.Success mSuccess: mGetVehicleListResponse.getSuccess()){
                        mSuccess.setId(mSuccess.getId());
                        mSuccess.setName(mSuccess.getName());
                        mGetVehicle.add(mSuccess);
                    }
                    GetVehicleListAdapter mGetVehicleListAdapter = new GetVehicleListAdapter(getActivity(),mGetVehicle);
                    mListView.setAdapter(mGetVehicleListAdapter);
                    mGetVehicleListAdapter.notifyDataSetChanged();
                }
            }else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        }else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Vehicle List");
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("Upload Vehicle");
            alertDialog.setMessage("You already uploaded" + str + " vehicles. you want to upload another vehicle?");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    getVehicleList();
                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), AutokattaMainActivity.class));
                    dialog.cancel();
                    getActivity().finish();
                }
            });
            alertDialog.show();
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    /*
    Get Vehicle List...
     */
    private void getVehicleList() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getVehicleList();
    }
}
