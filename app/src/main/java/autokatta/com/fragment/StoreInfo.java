package autokatta.com.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.StoreResponse;
import autokatta.com.view.MyStoreListActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-004 on 15/4/17.
 */

public class StoreInfo extends Fragment implements RequestNotifier, View.OnClickListener {

    public StoreInfo() {

    }

    View mAbout;
    String myContact, Store_id, StoreContact;
    ImageView editStore;
    TextView storeName, storeLocation, storeWebsite, storeWorkDays, storeOpen, storeClose, storeAddress, storeServiceOffered, storeType, storeDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAbout = inflater.inflate(R.layout.store_info_fragment, container, false);
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        storeName = (TextView) mAbout.findViewById(R.id.editstname);
        storeLocation = (TextView) mAbout.findViewById(R.id.autolocation);
        storeWebsite = (TextView) mAbout.findViewById(R.id.editwebsite);
        storeOpen = (TextView) mAbout.findViewById(R.id.edittiming);
        storeClose = (TextView) mAbout.findViewById(R.id.edittiming1);
        storeAddress = (TextView) mAbout.findViewById(R.id.editaddress);
        storeType = (TextView) mAbout.findViewById(R.id.storetype);
        editStore = (ImageView) mAbout.findViewById(R.id.editStore);
        storeDescription = (TextView) mAbout.findViewById(R.id.editstoredescription);
        storeWorkDays = (TextView) mAbout.findViewById(R.id.editworkingdays);
        storeServiceOffered = (TextView) mAbout.findViewById(R.id.autoservices);

        editStore.setOnClickListener(this);
        Bundle b = getArguments();
        Store_id = b.getString("store_id");
        //  StoreContact = b.getString("StoreContact");
        getStoredata(myContact, Store_id);




        return mAbout;
    }

    private void getStoredata(String myContact, String store_id) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.getStoreData(myContact, store_id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.editStore:
                Bundle bundle = new Bundle();
                bundle.putString("store_id", Store_id);
                bundle.putString("className", "StoreViewActivity");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent intent = new Intent(getActivity(), MyStoreListActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent, options.toBundle());
                getActivity().finish();


                break;
        }

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                StoreResponse storeResponse = (StoreResponse) response.body();
                for (StoreResponse.Success success : storeResponse.getSuccess()) {

                    storeName.setText(success.getName());
                    StoreContact = success.getContact();
                    storeLocation.setText(success.getLocation());
                    storeWebsite.setText(success.getWebsite());
                    storeWorkDays.setText(success.getWorkingDays());
                    storeOpen.setText(success.getStoreOpenTime());
                    storeClose.setText(success.getStoreCloseTime());
                    storeAddress.setText(success.getAddress());
                    storeDescription.setText(success.getStoreDescription());
                    storeType.setText(success.getStoreType());
                    storeServiceOffered.setText(success.getCategory());


                    if (StoreContact.contains(myContact)) {
                        editStore.setVisibility(View.VISIBLE);

                    }
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
