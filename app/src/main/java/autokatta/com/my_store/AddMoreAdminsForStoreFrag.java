package autokatta.com.my_store;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.Registration.CompanyBasedInvitation;
import autokatta.com.adapter.StoreAdminAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.Db_AutokattaContactResponse;
import autokatta.com.response.StoreOldAdminResponse;
import autokatta.com.view.StoreViewActivity;
import retrofit2.Response;

/**
 * Created by ak-003 on 29/3/17.
 */

public class AddMoreAdminsForStoreFrag extends Fragment implements RequestNotifier {

    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    ListView list;
    Button ok, cancel;
    StoreAdminAdapter adapter;

    RelativeLayout btnlayout;
    EditText inputSearch;
    String store_id, callFrom, storetype;
    String finaladmins = "";
    ArrayList<String> alreadyAdmin = new ArrayList<>();
    ApiCall apiCall;
    DbOperation dbOperation;

    public AddMoreAdminsForStoreFrag() {
        //empty  constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View root = inflater.inflate(R.layout.fragment_add_more_admin, container, false);

        list = (ListView) root.findViewById(R.id.l1);
        btnlayout = (RelativeLayout) root.findViewById(R.id.btnrelative);
        ok = (Button) root.findViewById(R.id.ok);
        cancel = (Button) root.findViewById(R.id.cancel);
        inputSearch = (EditText) root.findViewById(R.id.inputSearch);

        apiCall = new ApiCall(getActivity(), this);
        dbOperation = new DbOperation(getActivity());

        try {
            Bundle b = getArguments();
            store_id = b.getString("store_id");
            callFrom = b.getString("call");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DbOperation dbAdpter = new DbOperation(getActivity());
        dbAdpter.OPEN();
        Cursor cursor = dbAdpter.getAutokattaContact();
        if (cursor.getCount() > 0) {
            contactdata.clear();
            cursor.moveToFirst();
            do {
                Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(DbConstants.userName)) + " = " + cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                Db_AutokattaContactResponse obj = new Db_AutokattaContactResponse();
                obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                contactdata.add(obj);
            } while (cursor.moveToNext());
        }
        dbAdpter.CLOSE();

        if (!(contactdata.size() == 0)) {
            getAlreadyAdminData();
        }

        btnlayout.setVisibility(View.VISIBLE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreAdminAdapter.boxdata.clear();
                Bundle b = new Bundle();
                // b.putString("action", "main");
                b.putString("store_id", store_id);

                if (!callFrom.equalsIgnoreCase("interestbased")) {
                    Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                    intent.putExtras(b);
                    getActivity().startActivity(intent);
                } else {
                    Intent i = new Intent(getActivity(), CompanyBasedInvitation.class);
                    getActivity().startActivity(i);
                }
                getActivity().finish();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StoreAdminAdapter.boxdata.size() != 0) {
                    for (int i = 0; i < StoreAdminAdapter.boxdata.size(); i++) {
                        if (!StoreAdminAdapter.boxdata.get(i).equalsIgnoreCase("0")) {
                            if (finaladmins.equals(""))
                                finaladmins = StoreAdminAdapter.boxdata.get(i);
                            else
                                finaladmins = finaladmins + "," + StoreAdminAdapter.boxdata.get(i);
                        }
                    }
                }
                addStoreAdmins(store_id, finaladmins);
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return root;
    }

    private void getAlreadyAdminData() {
        apiCall.StoreAdmin(store_id);
    }

    private void addStoreAdmins(String storeId, String adminNos) {
        apiCall.newStoreAdmin(storeId, adminNos);
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                /*
                        Response to get store admins
                 */
                if (response.body() instanceof StoreOldAdminResponse) {
                    StoreOldAdminResponse adminResponse = (StoreOldAdminResponse) response.body();
                    if (!adminResponse.getSuccess().isEmpty()) {
                        for (StoreOldAdminResponse.Success success : adminResponse.getSuccess()) {
                            alreadyAdmin.add(success.getAdmin());
                        }
                    } /*else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));*/
                    if (!(alreadyAdmin.size() == 0))
                        adapter = new StoreAdminAdapter(getActivity(), contactdata, alreadyAdmin);
                    else {
                        adapter = new StoreAdminAdapter(getActivity(), contactdata);
                    }
                    list.setAdapter(adapter);
                }
            } else {
                Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else if (error instanceof UnknownHostException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            Log.i("Check Class-", "Add more Admins Store Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("success")) {
                Bundle b = new Bundle();
                //  b.putString("action", "main");
                b.putString("store_id", store_id);
                if (!callFrom.equalsIgnoreCase("interestbased")) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                    intent.putExtras(b);
                    getActivity().startActivity(intent, options.toBundle());
                } else {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(getActivity(), CompanyBasedInvitation.class);
                    getActivity().startActivity(i, options.toBundle());
                }
            }
        } else
            Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
    }
}
