package autokatta.com.upload_vehicle;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GooglePlacesAdapter;
import autokatta.com.generic.SetMyDateAndTime;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 7/6/17.
 */

public class CategoryList extends Fragment {
    View mCategory;
    List<String> mList = new ArrayList<>();
    ListView mListView;
    Dialog mBottomSheetDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCategory = inflater.inflate(R.layout.upload_vehicle_categries_list, null);
        mListView = (ListView) mCategory.findViewById(R.id.upload_category_list);
        mList.clear();
        getActivity().setTitle("Select Stock Type");
        mList.add("Finance/Repo");
        mList.add("Insurance");
        mList.add("Scrap");
        mList.add("Inventory");
        mList.add("Market Place");

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mList.get(position);
                // String subTypeId = mList.get(position).getId();
                if (s != null) {
                    getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("upload_auction_categoryName", s).apply();

                    if (s.equalsIgnoreCase("Scrap") || s.equalsIgnoreCase("Inventory")) {
                        View view1 = getActivity().getLayoutInflater().inflate(R.layout.custom_scrap_inventory_cust_info, null);

                        final ImageView[] mClose = {(ImageView) view1.findViewById(R.id.close)};
                        Button mAdd = (Button) view1.findViewById(R.id.submit);
                        Button mSkip = (Button) view1.findViewById(R.id.skip);
                        final EditText scrapcustomername = (EditText) view1.findViewById(R.id.cust_name);
                        final EditText scrapcustcontact = (EditText) view1.findViewById(R.id.cust_contact);
                        final EditText scrapcustdetailaddress = (EditText) view1.findViewById(R.id.cust_detailaddress);
                        final EditText scrapcustpurchasedate = (EditText) view1.findViewById(R.id.edtDate);
                        final EditText scrappurchaseprice = (EditText) view1.findViewById(R.id.purchaseprice);
                        final AutoCompleteTextView excustautoAddress = (AutoCompleteTextView) view1.findViewById(R.id.cust_address);

                        excustautoAddress.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));

                        mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
                        mBottomSheetDialog.setContentView(view1);
                        mBottomSheetDialog.setCancelable(true);
                        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                        mBottomSheetDialog.show();

                        mClose[0].setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mBottomSheetDialog.dismiss();
                            }
                        });

                        mAdd.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean flag = false;


                                String custName = scrapcustomername.getText().toString();
                                String custContact = scrapcustcontact.getText().toString();
                                String custFullAddress = scrapcustdetailaddress.getText().toString();
                                String custAddress = excustautoAddress.getText().toString();
                                String custpurchaseprice1 =  scrappurchaseprice.getText().toString();
                                int custpurchaseprice=Integer.parseInt(custpurchaseprice1);
                                String purchaseDate = scrapcustpurchasedate.getText().toString();


                                scrapcustpurchasedate.setOnTouchListener(new OnTouchListener() {
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
                                    mBottomSheetDialog.dismiss();
                                }
                            }
                        });

                        mSkip.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mBottomSheetDialog.dismiss();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.vehicle_upload_container, new Title(), "Category_list")
                                        .addToBackStack("Category_list")
                                        .commit();
                            }
                        });
                    }else if (s.equalsIgnoreCase("Finance/Repo")||s.equalsIgnoreCase("Insurance"))
                    {
                        View view1 = getActivity().getLayoutInflater().inflate(R.layout.custom_repo_insurance_info, null);

                        final ImageView[] mClose = {(ImageView) view1.findViewById(R.id.close)};
                        Button mAdd = (Button) view1.findViewById(R.id.submit);
                        final EditText loanaccno = (EditText) view1.findViewById(R.id.loan_no);
                        final EditText borrowername = (EditText) view1.findViewById(R.id.borrower_name);
                        final EditText borrowercontact = (EditText) view1.findViewById(R.id.borrower_contact);
                        final EditText inwarddate = (EditText) view1.findViewById(R.id.edtDate);
                        final EditText branchmanagername = (EditText) view1.findViewById(R.id.manager_name);
                        final EditText branchcmanagerontact = (EditText) view1.findViewById(R.id.manager_cnt);
                        final EditText dealername = (EditText) view1.findViewById(R.id.dealer_name);
                        final EditText stockyardname = (EditText) view1.findViewById(R.id.stock_yard_name);
                        final AutoCompleteTextView branchcity = (AutoCompleteTextView) view1.findViewById(R.id.branch_city);
                        final AutoCompleteTextView stockyardadddress = (AutoCompleteTextView) view1.findViewById(R.id.stock_yard_addr);

                        branchcity.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));
                        stockyardadddress.setAdapter(new GooglePlacesAdapter(getActivity(), R.layout.registration_spinner));

                        mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
                        mBottomSheetDialog.setContentView(view1);
                        mBottomSheetDialog.setCancelable(true);
                        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                        mBottomSheetDialog.show();

                        mClose[0].setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mBottomSheetDialog.dismiss();
                            }
                        });

                        mAdd.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean flag = false;


                                String loanno = loanaccno.getText().toString();
                                String strborrowername = borrowername.getText().toString();
                                String strborrowercontact = borrowercontact.getText().toString();
                                String strbranchmanagername = branchmanagername.getText().toString();
                                String strbranchcmanagerontact =  branchcmanagerontact.getText().toString();
                                String strdealername = dealername.getText().toString();
                                String strstockyardname = stockyardname.getText().toString();
                                String strbranchcity = branchcity.getText().toString();
                                String strstockyardadddress = stockyardadddress.getText().toString();
                                String strinwarddate = inwarddate.getText().toString();


                                inwarddate.setOnTouchListener(new OnTouchListener() {
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
                                }else if (strborrowercontact.isEmpty() || strborrowercontact.startsWith(" ") && strborrowercontact.startsWith(" ")) {
                                    borrowercontact.setError("Please provide Borrower Contact");
                                    borrowercontact.requestFocus();
                                }else if (strbranchmanagername.isEmpty() || strbranchmanagername.startsWith(" ") && strbranchmanagername.startsWith(" ")) {
                                    branchmanagername.setError("Please provide Branch Manager Name");
                                    branchmanagername.requestFocus();
                                } else if (strbranchcity.equals("") || strbranchcity.startsWith(" ") && strbranchcity.startsWith(" ")) {
                                    branchcity.setError("Enter Address");
                                    branchcity.requestFocus();
                                } else if (!flag) {
                                    branchcity.setError("Please provide proper address");
                                    branchcity.requestFocus();
                                }else if (strstockyardadddress.equals("") || strstockyardadddress.startsWith(" ") && strstockyardadddress.startsWith(" ")) {
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
                                    mBottomSheetDialog.dismiss();
                                }
                            }
                        });
                    }else
                    {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.vehicle_upload_container, new Title(), "Category_list")
                                .addToBackStack("Category_list")
                                .commit();
                    }

                }
            }
        });
        return mCategory;
    }
}
