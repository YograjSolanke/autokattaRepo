package autokatta.com.initial_fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * Created by ak-001 on 3/10/17.
 */

public class AddTransferVehicle extends Fragment implements RequestNotifier, View.OnClickListener {
    View mTransferVehicle;
    EditText mContact, owner_name, full_address, reason_for_transfer, description;
    Button submit;
    AutoCompleteTextView address;
    String myContact;
    int vehicleId;
    LinearLayout txtUser, txtInvite, linear_transfer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTransferVehicle = inflater.inflate(R.layout.custom_bottom_transfer_stock, container, false);
        mContact = (EditText) mTransferVehicle.findViewById(R.id.contact_no);
        owner_name = (EditText) mTransferVehicle.findViewById(R.id.owner_name);
        address = (AutoCompleteTextView) mTransferVehicle.findViewById(R.id.address);
        full_address = (EditText) mTransferVehicle.findViewById(R.id.full_address);
        reason_for_transfer = (EditText) mTransferVehicle.findViewById(R.id.reason_for_transfer);
        description = (EditText) mTransferVehicle.findViewById(R.id.description);
        submit = (Button) mTransferVehicle.findViewById(R.id.submit);
        txtUser = (LinearLayout) mTransferVehicle.findViewById(R.id.txtUser);
        txtInvite = (LinearLayout) mTransferVehicle.findViewById(R.id.txtInvite);
        linear_transfer = (LinearLayout) mTransferVehicle.findViewById(R.id.linear_transfer);

        submit.setOnClickListener(this);

        Bundle bundle = getArguments();
        vehicleId = bundle.getInt("bundle_VehicleId");
        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        ImageView mContactList = (ImageView) mTransferVehicle.findViewById(R.id.contact_list);
        mContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("contactOtherProfile", mContact.getText().toString());
                Intent intent = new Intent(getActivity(), OtherProfile.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        txtInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage(mContact.getText().toString());
            }
        });

        address.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.simple));
        mContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    if (!myContact.equalsIgnoreCase(s.toString()))
                        checkUser(s.toString());
                    else {
                        //Toast.makeText(getActivity(), "Admin not allowed", Toast.LENGTH_SHORT).show();
                        mContact.setError("Admin not allowed");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                txtInvite.setVisibility(View.GONE);
                txtUser.setVisibility(View.GONE);
                linear_transfer.setVisibility(View.GONE);
            }
        });
        return mTransferVehicle;
    }

    private void checkUser(String contact) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.registrationContactValidation(contact);
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
            if (str.equalsIgnoreCase("Success")) {
                txtUser.setVisibility(View.VISIBLE);
                linear_transfer.setVisibility(View.VISIBLE);
                txtInvite.setVisibility(View.GONE);
            } else if (str.equalsIgnoreCase("transfer_success")) {
                CustomToast.customToast(getActivity(), "Transfered successfully");
                getActivity().finish();

            } else {
                txtInvite.setVisibility(View.VISIBLE);
                txtUser.setVisibility(View.GONE);
                linear_transfer.setVisibility(View.GONE);
            }
        } else
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri contactData = data.getData();
                        Cursor c = null;
                        if (contactData != null) {
                            c = getActivity().getContentResolver().query(contactData, null, null, null, null);

                            assert c != null;
                            if (c.moveToFirst()) {
                                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                                String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                                String num = "";
                                if (Integer.valueOf(hasNumber) == 1) {
                                    Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                    assert numbers != null;
                                    while (numbers.moveToNext()) {
                                        num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                        num = num.replaceAll("-", "");
                                        num = num.replace("(", "").replace(")", "").replaceAll(" ", "").replaceAll("[\\D]", "");

                                        if (num.length() > 10)
                                            num = num.substring(num.length() - 10);
                                        mContact.setText(num);
                                    }
                                    numbers.close();
                                }
                                c.close();
                            }
                        }
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                Boolean flag = false;
                if (!address.getText().toString().equalsIgnoreCase("")) {

                    List<String> resultList = GooglePlacesAdapter.getResultList();
                    for (int i = 0; i < resultList.size(); i++) {

                        if (address.getText().toString().equalsIgnoreCase(resultList.get(i))) {
                            flag = true;
                            break;

                        } else {

                            flag = false;
                        }
                    }
                }
                if (owner_name.getText().toString().equalsIgnoreCase("")) {
                    owner_name.setFocusable(true);
                    owner_name.setError("Please Provide Name");
                } else if (address.getText().toString().equalsIgnoreCase("")) {
                    address.setFocusable(true);
                    address.setError("Please Provide Name");
                } else if (full_address.getText().toString().equalsIgnoreCase("")) {
                    full_address.setFocusable(true);
                    full_address.setError("Please Provide Name");
                } else if (reason_for_transfer.getText().toString().equalsIgnoreCase("")) {
                    reason_for_transfer.setFocusable(true);
                    reason_for_transfer.setError("Please Provide Name");
                } else if (description.getText().toString().equalsIgnoreCase("")) {
                    description.setFocusable(true);
                    description.setError("Please Provide Name");
                } else if (address.getText().toString().equalsIgnoreCase("")) {
                    address.setError("Enter Location");
                    address.requestFocus();

                } else if (!address.getText().toString().equalsIgnoreCase("") && !flag) {
                    address.setError("Please Select Location From Dropdown Only");
                    address.requestFocus();
                } else {
                    ApiCall apiCall = new ApiCall(getActivity(), this);
                    apiCall._autokattaRequestForTransferVehicle(vehicleId, owner_name.getText().toString(), mContact.getText().toString(),
                            reason_for_transfer.getText().toString(), address.getText().toString(), full_address.getText().toString(),
                            description.getText().toString(), myContact, "");
                }
                break;
        }
    }

    private void sendSMSMessage(String con) {
        Log.i("Send SMS", "");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "Hi I am using Autokatta\n Please click below link to download and connect with me for more https://play.google.com/store/apps/details?id=autokatta.com", null, null);
            CustomToast.customToast(getActivity(), "SMS sent.");
        } catch (Exception e) {
            CustomToast.customToast(getActivity(), "SMS failed, please try again.");
            e.printStackTrace();
        }
    }
}
