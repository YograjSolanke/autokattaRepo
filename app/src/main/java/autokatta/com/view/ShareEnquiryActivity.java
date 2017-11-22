package autokatta.com.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import retrofit2.Response;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class ShareEnquiryActivity extends AppCompatActivity implements RequestNotifier, View.OnClickListener {
    EditText mContact, owner_name, full_address, reason_for_transfer, description;
    Button submit;
    String myContact;
    int mEnquiryId;
    LinearLayout txtUser, txtInvite, linear_transfer;
    String mEnquContact, mKeyword, mIds;
    ConnectionDetector mConnectionDetector;
    ApiCall mApiCall;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_enquiry);
        setTitle("Share Enquiry");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mContact = (EditText) findViewById(R.id.contact_no);
        owner_name = (EditText) findViewById(R.id.owner_name);
        reason_for_transfer = (EditText) findViewById(R.id.reason_for_transfer);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);
        txtUser = (LinearLayout) findViewById(R.id.txtUser);
        txtInvite = (LinearLayout) findViewById(R.id.txtInvite);
        linear_transfer = (LinearLayout) findViewById(R.id.linear_transfer);
        mConnectionDetector = new ConnectionDetector(this);
        mApiCall = new ApiCall(this, this);

        submit.setOnClickListener(this);

        dialog = new ProgressDialog(ShareEnquiryActivity.this);
        dialog.setMessage("Checking Contact in Autokatta...");

        Intent i = getIntent();
        mEnquiryId = i.getIntExtra("enquiryid", 0);
        mEnquContact = i.getStringExtra("enqcontact");
        mKeyword = i.getStringExtra("keyword");
        mIds = i.getStringExtra("Ids");
        //  vehicleId = bundle.getInt("bundle_VehicleId");
        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "");
        ImageView mContactList = (ImageView) findViewById(R.id.contact_list);
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
                Intent intent = new Intent(getApplicationContext(), OtherProfile.class);
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
    }


    private void checkUser(String contact) {
        if (mConnectionDetector.isConnectedToInternet()) {
            dialog.show();
            //ApiCall mApiCall = new ApiCall(getApplicationContext(), this);
            mApiCall.registrationContactValidation(contact);
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    private void addTransferrequest() {
        if (mConnectionDetector.isConnectedToInternet()) {
            //  ApiCall mApiCall = new ApiCall(getApplicationContext(), this);
            mApiCall.addtransferenquiry(mEnquiryId, mContact.getText().toString(), mEnquContact, myContact, owner_name.getText().toString(), description.getText().toString(), reason_for_transfer.getText().toString(), "yes", mKeyword, "", mIds);
        } else {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));

        }
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
                dialog.dismiss();
                txtUser.setVisibility(View.VISIBLE);
                linear_transfer.setVisibility(View.VISIBLE);
                txtInvite.setVisibility(View.GONE);
            } else if (str.equalsIgnoreCase("transfer_success")) {
                CustomToast.customToast(getApplicationContext(), "Transfered successfully");
                finish();

            } else {
                dialog.dismiss();
                txtInvite.setVisibility(View.VISIBLE);
                txtUser.setVisibility(View.GONE);
                linear_transfer.setVisibility(View.GONE);
            }
        } else
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
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
                            c = getApplicationContext().getContentResolver().query(contactData, null, null, null, null);

                            assert c != null;
                            if (c.moveToFirst()) {
                                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                                String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                                String num = "";
                                if (Integer.valueOf(hasNumber) == 1) {
                                    Cursor numbers = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
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

                if (owner_name.getText().toString().equalsIgnoreCase("")) {
                    owner_name.setFocusable(true);
                    owner_name.setError("Please Provide Name");
                } else if (reason_for_transfer.getText().toString().equalsIgnoreCase("")) {
                    reason_for_transfer.setFocusable(true);
                    reason_for_transfer.setError("Please Provide Reason");
                } else if (description.getText().toString().equalsIgnoreCase("")) {
                    description.setFocusable(true);
                    description.setError("Please Provide Description");
                } else {
                    /*ApiCall apiCall = new ApiCall(getApplicationContext(), this);
                    apiCall.add(vehicleId, owner_name.getText().toString(), mContact.getText().toString(),
                            reason_for_transfer.getText().toString(), address.getText().toString(), full_address.getText().toString(),
                            description.getText().toString(), myContact, "");*/
                    addTransferrequest();
                }
                break;
        }
    }

    private void sendSMSMessage(String con) {
        Log.i("Send SMS", "");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(con, null, "Hi I am using Autokatta\n Please click below link to download and connect with me for more https://play.google.com/store/apps/details?id=autokatta.com", null, null);
            CustomToast.customToast(getApplicationContext(), "SMS sent.");
        } catch (Exception e) {
            CustomToast.customToast(getApplicationContext(), "SMS failed, please try again.");
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
