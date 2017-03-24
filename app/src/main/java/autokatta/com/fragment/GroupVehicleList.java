package autokatta.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

/**
 * Created by ak-001 on 24/3/17.
 */

public class GroupVehicleList extends Fragment implements RequestNotifier {
    View mGroupVehicleList;
    ListView mListView;
    String brand = "", model = "", version = "", city = "", RTOcity = "", reg_year = "",
            mgf_year = "", price = "", kmsrunning = "", no_of_owner = "";
    EditText editbrand,editmodel,editversion,editcity,editregyr,editmgfyr,editprice,editkms,editowner;
    AutoCompleteTextView editRTOcity;
    ImageView filterimg;
    RelativeLayout relativefilter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGroupVehicleList = inflater.inflate(R.layout.fragment_group_vehicle_list, container, false);
        mListView = (ListView) mGroupVehicleList.findViewById(R.id.group_vehicle_list);
        editbrand = (EditText) mGroupVehicleList.findViewById(R.id.editbrand);
        editmodel = (EditText) mGroupVehicleList.findViewById(R.id.editmodel);
        editversion = (EditText) mGroupVehicleList.findViewById(R.id.editversion);
        editcity = (EditText) mGroupVehicleList.findViewById(R.id.editcity);
        editRTOcity = (AutoCompleteTextView) mGroupVehicleList.findViewById(R.id.editrtocity);
        editregyr = (EditText) mGroupVehicleList.findViewById(R.id.editregyear);
        editmgfyr = (EditText) mGroupVehicleList.findViewById(R.id.editmgfyear);
        editprice = (EditText) mGroupVehicleList.findViewById(R.id.editprice);
        editkms = (EditText) mGroupVehicleList.findViewById(R.id.editkmsrun);
        editowner = (EditText) mGroupVehicleList.findViewById(R.id.editowners);
        filterimg = (ImageView) mGroupVehicleList.findViewById(R.id.filterimg);
        relativefilter = (RelativeLayout) mGroupVehicleList.findViewById(R.id.relative_filter);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getGroupVehicles();
                filterimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (mListView.getVisibility() == View.VISIBLE) {
                            mListView.setVisibility(View.GONE);
                            relativefilter.setVisibility(View.VISIBLE);
                        } else {
                            mListView.setVisibility(View.VISIBLE);
                            relativefilter.setVisibility(View.GONE);
                        }

                    }
                });

                /*
                get Relative layout data...
                 */
                brand = editbrand.getText().toString();
                model = editmodel.getText().toString();
                version = editversion.getText().toString();
                city = editcity.getText().toString();
                RTOcity = editRTOcity.getText().toString();
                price = editprice.getText().toString();
                reg_year = editregyr.getText().toString();
                mgf_year = editmgfyr.getText().toString();
                kmsrunning = editkms.getText().toString();
                no_of_owner = editowner.getText().toString();
            }
        });
        return mGroupVehicleList;
    }

    /*
    Group Vehicle List...
     */
    private void getGroupVehicles() {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getGroupVehicles(getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE)
                .getString("group_id",""),brand,model,version,city,RTOcity,reg_year,mgf_year,price,kmsrunning,no_of_owner);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response!=null){
            if (response.isSuccessful()){

            }else {
                CustomToast.customToast(getActivity(),getString(R.string._404));
            }
        }else {
            CustomToast.customToast(getActivity(),getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
