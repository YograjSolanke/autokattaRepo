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

public class InsuranceFinanceRepo extends Fragment implements RequestNotifier {
    View mInsuranceFinanceRepo;
    EditText loanaccno, borrowername, borrowercontact, inwarddate, branchmanagername, branchcmanagerontact, dealername, stockyardname;
    AutoCompleteTextView branchcity, stockyardadddress;
    LinearLayout txtUser;

    public InsuranceFinanceRepo() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInsuranceFinanceRepo = inflater.inflate(R.layout.custom_repo_insurance_info, container, false);
        getActivity().setTitle(getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("upload_auction_categoryName", ""));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button mAdd = (Button) mInsuranceFinanceRepo.findViewById(R.id.submit);
                loanaccno = (EditText) mInsuranceFinanceRepo.findViewById(R.id.loan_no);
                borrowername = (EditText) mInsuranceFinanceRepo.findViewById(R.id.borrower_name);
                borrowercontact = (EditText) mInsuranceFinanceRepo.findViewById(R.id.borrower_contact);
                inwarddate = (EditText) mInsuranceFinanceRepo.findViewById(R.id.edtDate);
                branchmanagername = (EditText) mInsuranceFinanceRepo.findViewById(R.id.manager_name);
                branchcmanagerontact = (EditText) mInsuranceFinanceRepo.findViewById(R.id.manager_cnt);
                dealername = (EditText) mInsuranceFinanceRepo.findViewById(R.id.dealer_name);
                stockyardname = (EditText) mInsuranceFinanceRepo.findViewById(R.id.stock_yard_name);
                branchcity = (AutoCompleteTextView) mInsuranceFinanceRepo.findViewById(R.id.branch_city);
                stockyardadddress = (AutoCompleteTextView) mInsuranceFinanceRepo.findViewById(R.id.stock_yard_addr);
                txtUser = (LinearLayout) mInsuranceFinanceRepo.findViewById(R.id.txtUser);

                branchcity.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));
                stockyardadddress.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));

                inwarddate.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            inwarddate.setInputType(InputType.TYPE_NULL);
                            inwarddate.setError(null);
                            new SetMyDateAndTime("date", inwarddate, getActivity());
                        }
                        return false;
                    }
                });

                ImageView mContactList = (ImageView) mInsuranceFinanceRepo.findViewById(R.id.contact_list);
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
                        bundle.putString("contactOtherProfile", borrowercontact.getText().toString());
                        Intent intent = new Intent(getActivity(), OtherProfile.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                borrowercontact.addTextChangedListener(new TextWatcher() {
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
                        String loanno = loanaccno.getText().toString();
                        String strborrowername = borrowername.getText().toString();
                        String strborrowercontact = borrowercontact.getText().toString();
                        String strbranchmanagername = branchmanagername.getText().toString();
                        String strbranchcmanagerontact = branchcmanagerontact.getText().toString();
                        String strdealername = dealername.getText().toString();
                        String strstockyardname = stockyardname.getText().toString();
                        String strbranchcity = branchcity.getText().toString();
                        String strstockyardadddress = stockyardadddress.getText().toString();
                        String strinwarddate = inwarddate.getText().toString();

                        if (!branchcity.getText().toString().isEmpty()) {
                            List<String> resultList = GooglePlacesAdapter.getResultList();
                            for (int i = 0; i < resultList.size(); i++) {
                                if (branchcity.getText().toString().equalsIgnoreCase(resultList.get(i))) {
                                    flag = true;
                                    break;
                                } else {
                                    flag = false;
                                }
                            }
                        }
                        if (!stockyardadddress.getText().toString().isEmpty()) {
                            List<String> resultList = GooglePlacesAdapter.getResultList();
                            for (int i = 0; i < resultList.size(); i++) {
                                if (stockyardadddress.getText().toString().equalsIgnoreCase(resultList.get(i))) {
                                    flag = true;
                                    break;
                                } else {
                                    flag = false;
                                }
                            }
                        }

                        if (loanno.equalsIgnoreCase("") || loanno.startsWith(" ") && loanno.startsWith(" ")) {
                            loanaccno.setError("Please provide Loan Number");
                            loanaccno.requestFocus();
                        } else if (strborrowername.isEmpty() || strborrowername.startsWith(" ") && strborrowername.startsWith(" ")) {
                            borrowername.setError("Please provide Borrower Name");
                            borrowername.requestFocus();
                        } else if (strdealername.isEmpty() || strdealername.startsWith(" ") && strdealername.startsWith(" ")) {
                            dealername.setError("Please provide Dealer Name");
                            dealername.requestFocus();
                        } else if (strborrowercontact.isEmpty() || strborrowercontact.startsWith(" ") && strborrowercontact.startsWith(" ")) {
                            borrowercontact.setError("Please provide Borrower Contact");
                            borrowercontact.requestFocus();
                        } else if (strbranchmanagername.isEmpty() || strbranchmanagername.startsWith(" ") && strbranchmanagername.startsWith(" ")) {
                            branchmanagername.setError("Please provide Branch Manager Name");
                            branchmanagername.requestFocus();
                        } else if (strbranchcity.equals("") || strbranchcity.startsWith(" ") && strbranchcity.startsWith(" ")) {
                            branchcity.setError("Enter Address");
                            branchcity.requestFocus();
                        } else if (!flag) {
                            branchcity.setError("Please provide proper address");
                            branchcity.requestFocus();
                        } else if (strstockyardadddress.equals("") || strstockyardadddress.startsWith(" ") && strstockyardadddress.startsWith(" ")) {
                            stockyardadddress.setError("Enter Address");
                            stockyardadddress.requestFocus();
                        } else if (!flag) {
                            stockyardadddress.setError("Please provide proper address");
                            stockyardadddress.requestFocus();
                        } else if (strstockyardname.equals("")) {
                            stockyardname.setError("Enter StockYard Name");
                            stockyardname.requestFocus();
                        } else if (strbranchcmanagerontact.equals("")) {
                            branchcmanagerontact.setError("Enter Branch Manager Contact");
                            branchcmanagerontact.requestFocus();
                        } else if (strinwarddate.equals("") || strinwarddate.startsWith(" ")) {
                            inwarddate.setError("Enter Date");
                            inwarddate.requestFocus();
                        } else {
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("loan_acc_no", loanno).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("borrower_name", strborrowername).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("borrower_contact", strborrowercontact).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("branchmanagername", strbranchmanagername).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("managercontact", strbranchcmanagerontact).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("dealername", strdealername).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("branchcity", strbranchcity).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("stockyardname", strstockyardname).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("stockyardaddr", strstockyardadddress).apply();
                            getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("inwarddate", strinwarddate).apply();

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.vehicle_upload_container, new Title(), "Category_list")
                                    .addToBackStack("Category_list")
                                    .commit();
                        }
                    }
                });
            }
        });
        return mInsuranceFinanceRepo;
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
                                    borrowercontact.setText(num);
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
