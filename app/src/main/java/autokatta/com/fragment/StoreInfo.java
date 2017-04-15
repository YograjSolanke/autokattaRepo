package autokatta.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreInfo extends Fragment implements RequestNotifier, View.OnClickListener {

    public StoreInfo() {

    }

    View mAbout;
    String Sharedcontact;
    TextView storeName, storeLocation, storeWebsite, storeWorkDays, storeOpen, storeClose, storeAddress, storeServiceOffered, storeType, storeDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAbout = inflater.inflate(R.layout.store_info_fragment, container, false);
        Sharedcontact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        storeName = (TextView) mAbout.findViewById(R.id.editstname);
        storeLocation = (TextView) mAbout.findViewById(R.id.autolocation);
        storeWebsite = (TextView) mAbout.findViewById(R.id.editwebsite);
        storeOpen = (TextView) mAbout.findViewById(R.id.edittiming);
        storeClose = (TextView) mAbout.findViewById(R.id.edittiming1);
        storeAddress = (TextView) mAbout.findViewById(R.id.editaddress);
        storeType = (TextView) mAbout.findViewById(R.id.storetype);
        storeDescription = (TextView) mAbout.findViewById(R.id.editstoredescription);
        storeWorkDays = (TextView) mAbout.findViewById(R.id.editworkingdays);
        storeServiceOffered = (TextView) mAbout.findViewById(R.id.autoservices);

        Bundle b = getArguments();
        String Store_id = b.getString("store_id");
        String StoreContact = b.getString("StoreContact");
        getStoredata(StoreContact, Store_id);

        return mAbout;
    }

    private void getStoredata(String storeContact, String store_id) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getStoreData(storeContact, store_id);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                StoreResponse storeResponse = (StoreResponse) response.body();
                for (StoreResponse.Success success : storeResponse.getSuccess()) {

                    storeName.setText(success.getName());
                    storeLocation.setText(success.getLocation());
                    storeWebsite.setText(success.getWebsite());
                    storeWorkDays.setText(success.getWorkingDays());
                    storeOpen.setText(success.getStoreOpenTime());
                    storeClose.setText(success.getStoreCloseTime());
                    storeAddress.setText(success.getAddress());
                    storeDescription.setText(success.getStoreDescription());
                    storeType.setText(success.getStoreType());
                    storeServiceOffered.setText(success.getCategory());

                }

            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
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
