package autokatta.com.initial_fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * Created by ak-001 on 3/10/17.
 */

public class AddSoldVehicle extends Fragment implements RequestNotifier {
    View mSoldVehicle;
    int getVehicleId;
    ApiCall apiCall;
    EditText mCustomerName, mContact, mAddress, mSellingPrice;
    AutoCompleteTextView mLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        apiCall = new ApiCall(getActivity(), this);
        mSoldVehicle = inflater.inflate(R.layout.custom_sold_vehicle_info, container, false);
        mCustomerName = (EditText) mSoldVehicle.findViewById(R.id.customer_name);
        mContact = (EditText) mSoldVehicle.findViewById(R.id.contact);
        mAddress = (EditText) mSoldVehicle.findViewById(R.id.address);
        mLocation = (AutoCompleteTextView) mSoldVehicle.findViewById(R.id.autoAddress);
        mSellingPrice = (EditText) mSoldVehicle.findViewById(R.id.selling_price);
        Button mSave = (Button) mSoldVehicle.findViewById(R.id.submit);
        ImageView mContactList = (ImageView) mSoldVehicle.findViewById(R.id.contact_list);
        mLocation.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));

        Bundle bundle = getArguments();
        getVehicleId = bundle.getInt("getVehicleId");
        mContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCall.soldVehicle(getVehicleId, mCustomerName.getText().toString(), mContact.getText().toString(),
                        Integer.parseInt(mSellingPrice.getText().toString()),
                        mAddress.getText().toString(), getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", ""),
                        mLocation.getText().toString());

                apiCall.deleteUploadedVehicle(getVehicleId, "delete");
            }
        });
        return mSoldVehicle;
    }

    @Override
    public void notifySuccess(Response<?> response) {

    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            switch (str) {
                case "sold_success":
                    CustomToast.customToast(getActivity(), "vehicle sold");
                    getActivity().finish();
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri contactData = data.getData();
                        Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                while (numbers.moveToNext()) {
                                    num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    num = num.replaceAll("-", "");
                                    num = num.replace("(", "").replace(")", "").replaceAll(" ", "").replaceAll("[\\D]", "");

                                    if (num.length() > 10)
                                        num = num.substring(num.length() - 10);
                                    mContact.setText(num);
                                }
                            }
                        }
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
