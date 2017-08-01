package autokatta.com.my_store;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
import autokatta.com.other.CustomToast;
import autokatta.com.register.InvitationCompanyBased;
import autokatta.com.response.Db_AutokattaContactResponse;
import autokatta.com.response.StoreOldAdminResponse;
import autokatta.com.view.StoreViewActivity;
import retrofit2.Response;

import static autokatta.com.database.DbConstants.userName;

/**
 * Created by ak-003 on 29/3/17.
 */

public class AddMoreAdminsForStoreFrag extends Fragment implements RequestNotifier {

    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    RecyclerView mRecyclerView;
    Button cancel;
    public static Button ok;
    StoreAdminAdapter adapter;

    RelativeLayout btnlayout;
    EditText inputSearch;
    String callFrom, storetype;
    int store_id;
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

        mRecyclerView = (RecyclerView) root.findViewById(R.id.l1);
        btnlayout = (RelativeLayout) root.findViewById(R.id.btnrelative);
        ok = (Button) root.findViewById(R.id.ok);
        cancel = (Button) root.findViewById(R.id.cancel);
        inputSearch = (EditText) root.findViewById(R.id.inputSearch);
        inputSearch.setVisibility(View.GONE);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Set animation attribute to each item
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        apiCall = new ApiCall(getActivity(), this);
        dbOperation = new DbOperation(getActivity());

        try {
            Bundle b = getArguments();
            store_id = b.getInt("store_id");
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
                Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(userName)) + " = " + cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
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
                Bundle b = new Bundle();
                //  b.putString("action", "main");
                b.putInt("store_id", store_id);
                b.putString("flow_tab_name", "adminMore");
                if (!callFrom.equalsIgnoreCase("interestbased")) {
                    getActivity().finish();
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                    intent.putExtras(b);
                    getActivity().startActivity(intent, options.toBundle());

                } else {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(getActivity(), InvitationCompanyBased.class);
                    getActivity().startActivity(i, options.toBundle());
                    getActivity().finish();

                }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;

                for (int i = 0; i < StoreAdminAdapter.boxdata.size(); i++) {
                        if (!StoreAdminAdapter.boxdata.get(i).equalsIgnoreCase("0")) {
                            if (StoreAdminAdapter.isSave.get(i).equals(false)) {
                                CustomToast.customToast(getActivity(), "Save Role First");
                                flag = true;
                                break;
                            } else {
                                flag = false;
                                if (finaladmins.equals(""))
                                    finaladmins = StoreAdminAdapter.boxdata.get(i);
                                else
                                    finaladmins = finaladmins + "," + StoreAdminAdapter.boxdata.get(i);
                            }

                        }
                    }

                if (flag == false) {

                    if (!finaladmins.equals("")) {

                        System.out.println("finalAdmins=====" + finaladmins);
                        addStoreAdmins(store_id, finaladmins);
                    } else {
                        CustomToast.customToast(getActivity(), "Please Select Atleast Single contact");

                    }
                }
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

    private void addStoreAdmins(int storeId, String adminNos) {
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
                            // if(alreadyAdmin.equals(""))
                            alreadyAdmin.add(success.getAdmin());
//                            else
//                                alreadyAdmin=alreadyAdmin+","+success.getAdmin();
                        }

                        System.out.println("alreadyadmin=" + alreadyAdmin);
                    } /*else
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));*/
                    if (alreadyAdmin.size() != 0)
                        adapter = new StoreAdminAdapter(getActivity(), contactdata, alreadyAdmin);
                    else {
                        adapter = new StoreAdminAdapter(getActivity(), contactdata);
                    }
                    mRecyclerView.setAdapter(adapter);
                }
            } else {
                CustomToast.customToast(getActivity(), getString(R.string._404_));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //mNoInternetIcon.setVisibility(View.VISIBLE);
           /* Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();*/
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
            //mNoInternetIcon.setVisibility(View.VISIBLE);
           /* Snackbar snackbar = Snackbar.make(getView(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            snackbar.show();*/
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
                b.putInt("store_id", store_id);
                b.putString("flow_tab_name", "adminMore");
                if (!callFrom.equalsIgnoreCase("interestbased")) {
                    getActivity().finish();
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent intent = new Intent(getActivity(), StoreViewActivity.class);
                    intent.putExtras(b);
                    getActivity().startActivity(intent, options.toBundle());

                } else {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                    Intent i = new Intent(getActivity(), CompanyBasedInvitation.class);
                    getActivity().startActivity(i, options.toBundle());
                    getActivity().finish();

                }
            }
        } else {

        }
        //Snackbar.make(getView(), getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
    }
}
