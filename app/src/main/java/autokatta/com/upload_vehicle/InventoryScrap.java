package autokatta.com.upload_vehicle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.generic.SetMyDateAndTime;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.view.OtherProfile;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * Created by ak-001 on 16/10/17.
 */

public class InventoryScrap extends Fragment implements RequestNotifier {
    View mInventoryScrap;
    EditText scrapcustomername, scrapcustcontact, scrapcustdetailaddress, scrapcustpurchasedate, scrappurchaseprice;
    AutoCompleteTextView excustautoAddress;
    LinearLayout txtUser;

    public InventoryScrap() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInventoryScrap = inflater.inflate(R.layout.custom_scrap_inventory_cust_info, container, false);
        getActivity().setTitle(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("upload_auction_categoryName", ""));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button mAdd = (Button) mInventoryScrap.findViewById(R.id.submit);
                Button mSkip = (Button) mInventoryScrap.findViewById(R.id.skip);
                scrapcustomername = (EditText) mInventoryScrap.findViewById(R.id.cust_name);
                scrapcustcontact = (EditText) mInventoryScrap.findViewById(R.id.cust_contact);
                scrapcustdetailaddress = (EditText) mInventoryScrap.findViewById(R.id.cust_detailaddress);
                scrapcustpurchasedate = (EditText) mInventoryScrap.findViewById(R.id.edtDate);
                scrappurchaseprice = (EditText) mInventoryScrap.findViewById(R.id.purchaseprice);
                excustautoAddress = (AutoCompleteTextView) mInventoryScrap.findViewById(R.id.cust_address);
                txtUser = (LinearLayout) mInventoryScrap.findViewById(R.id.txtUser);
                excustautoAddress.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));

                scrapcustpurchasedate.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            scrapcustpurchasedate.setInputType(InputType.TYPE_NULL);
                            scrapcustpurchasedate.setError(null);
                            new SetMyDateAndTime("date", scrapcustpurchasedate, getActivity());
                        }
                        return false;
                    }
                });

                ImageView mContactList = (ImageView) mInventoryScrap.findViewById(R.id.contact_list);
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
                        bundle.putString("contactOtherProfile", scrapcustcontact.getText().toString());
                        Intent intent = new Intent(getActivity(), OtherProfile.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                scrapcustcontact.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 10) {
                            if (!getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("loginContact", "")
                                    .equalsIgnoreCase(s.toString()))
                                checkUser(s.toString());
                            else {
                                Toast.makeText(getActivity(), "Admin not allowed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean flag = false;
                        String custName = scrapcustomername.getText().toString();
                        String custContact = scrapcustcontact.getText().toString();
                        String custFullAddress = scrapcustdetailaddress.getText().toString();
                        String custAddress = excustautoAddress.getText().toString();
                        String custpurchaseprice1 = scrappurchaseprice.getText().toString();
                        int custpurchaseprice = Integer.parseInt(custpurchaseprice1);
                        String purchaseDate = scrapcustpurchasedate.getText().toString();

                        if (!excustautoAddress.getText().toString().isEmpty()) {
                            List<String> resultList = GooglePlacesAdapter.getResultList();
                            for (int i = 0; i < resultList.size(); i++) {
                                if (excustautoAddress.getText().toString().equalsIgnoreCase(resultList.get(i))) {
                                    flag = true;
                                    break;
                                } else {
                                    flag = false;
                                }
                            }
                        }

                        if (custName.equalsIgnoreCase("") || custName.startsWith(" ") && custName.startsWith(" ")) {
                            scrapcustomername.setError("Please provide customer name");
                            scrapcustomername.requestFocus();
                        } else if (custContact.isEmpty() || custContact.startsWith(" ") && custContact.startsWith(" ")) {
                            scrapcustcontact.setError("Please provide customer contact");
                            scrapcustcontact.requestFocus();
                        } else if (custAddress.equals("") || custAddress.startsWith(" ") && custAddress.startsWith(" ")) {
                            excustautoAddress.setError("Enter Address");
                            excustautoAddress.requestFocus();
                        } else if (!flag) {
                            excustautoAddress.setError("Please provide proper address");
                            excustautoAddress.requestFocus();
                        } else if (custFullAddress.equals("")) {
                            scrapcustdetailaddress.setError("Enter detailed address");
                            scrapcustdetailaddress.requestFocus();
                        } else if (custpurchaseprice1.equals("")) {
                            scrappurchaseprice.setError("Enter Purchase price");
                            scrappurchaseprice.requestFocus();
                        } else if (purchaseDate.equals("") || purchaseDate.startsWith(" ")) {
                            scrapcustpurchasedate.setError("Enter Date");
                            scrapcustpurchasedate.requestFocus();
                        } else {
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("scrap_cust_name", custName).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("scap_cust_contact", custContact).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("scrap_cust_address", custAddress).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("scrap_cust_full_addr", custFullAddress).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putInt("scrap_purchase_price", custpurchaseprice).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("scrap_purchase_date", purchaseDate).apply();

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.vehicle_upload_container, new Title(), "Category_list")
                                    .addToBackStack("Category_list")
                                    .commit();
                        }
                    }
                });
                mSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.vehicle_upload_container, new Title(), "Category_list")
                                .addToBackStack("Category_list")
                                .commit();
                    }
                });
            }
        });
        return mInventoryScrap;
    }

    private void checkUser(String contact) {
        ApiCall mApiCall = new ApiCall(getActivity(), this);
        mApiCall.registrationContactValidation(contact);
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
                                    scrapcustcontact.setText(num);
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
            } else {
                txtUser.setVisibility(View.GONE);
            }
        } else
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
    }
}
