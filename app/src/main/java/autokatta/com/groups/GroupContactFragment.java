package autokatta.com.groups;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupContactListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetRegisteredContactsResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 20/4/17
 */

public class GroupContactFragment extends Fragment implements RequestNotifier {
    View mGcontact;
    String mContact, call, bundle_GroupName;
    int mGroup_id;
    ListView lv;
    ApiCall mApiCall;
    String receiver_contact;
    GroupContactListAdapter CntctListadapter;
    Bundle args = new Bundle();
    ConnectionDetector mTestConnection;
    public Button mBtnAddContacts;
    EditText inputSearch;
    List<String> clist = new ArrayList<>();
    List<String> alreadyMemberList = new ArrayList<>();
    List<GetRegisteredContactsResponse> cntlist = new ArrayList<>();
    private ProgressDialog dialog;

    String allcontacts = "";

    public GroupContactFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGcontact = inflater.inflate(R.layout.group_contact_list, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        mTestConnection = new ConnectionDetector(getActivity());
        getActivity().setTitle("Add Contacts");
        mBtnAddContacts = (Button) mGcontact.findViewById(R.id.add_contacts);
        inputSearch = (EditText) mGcontact.findViewById(R.id.inputSearch);
        lv = (ListView) mGcontact.findViewById(R.id.list_view);

        mContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        mApiCall = new ApiCall(getActivity(), this);
        args = getArguments();

        mGroup_id = args.getInt("bundle_GroupId", 0);
        bundle_GroupName = args.getString("bundle_GroupName", "");
        call = args.getString("call", "");
        if(call.equalsIgnoreCase("newGroup")){
            alreadyMemberList.add("");
        }else{
            alreadyMemberList = args.getStringArrayList("list");
        }

        dialog.show();
        DbOperation dbAdpter = new DbOperation(getActivity());
        dbAdpter.OPEN();
        Cursor cursor = dbAdpter.getAutokattaContact();
        if (cursor.getCount() > 0) {
            cntlist.clear();
            cursor.moveToFirst();
            do {
                Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(DbConstants.userName)) + " = " + cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                GetRegisteredContactsResponse obj = new GetRegisteredContactsResponse();

                if (!cursor.getString(cursor.getColumnIndex(DbConstants.contact)).equals(mContact) &&
                        !alreadyMemberList.contains(cursor.getString(cursor.getColumnIndex(DbConstants.contact)))) {
                    obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                    obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                    cntlist.add(obj);
                }
            } while (cursor.moveToNext());
        }
        dbAdpter.CLOSE();
        if (cntlist.size() != 0) {
            dialog.dismiss();
            Collections.sort(cntlist, GetRegisteredContactsResponse.StuNameComparator);
            CntctListadapter = new GroupContactListAdapter(getActivity(), cntlist, mBtnAddContacts);
            lv.setAdapter(CntctListadapter);
            CntctListadapter.notifyDataSetChanged();
        } else {
            if (isAdded())
                dialog.dismiss();
            CustomToast.customToast(getActivity(), "No Contacts Found For Add");
        }

        mBtnAddContacts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> GetList = args.getStringArrayList("list");
                clist = CntctListadapter.checkboxselect();
                for (int i = 0; i < clist.size(); i++) {
                    if (!clist.get(i).equals("0")) {
                        if (allcontacts.equals(""))
                            allcontacts = clist.get(i);
                        else
                            allcontacts = allcontacts + "," + clist.get(i);
                    }
                }
                if (!call.equalsIgnoreCase("request")) {
                    if (call.equalsIgnoreCase("newGroup")) {
                        allcontacts = allcontacts + "," + mContact;
                    }
                    mApiCall.addContactInGroup(mGroup_id, allcontacts);
                    String[] parts = allcontacts.split(",");
                    for (int i = 0; i < parts.length; i++) {
                        receiver_contact = parts[i];
                        if (!receiver_contact.equalsIgnoreCase(mContact)) {
                            mApiCall.Like(mContact, receiver_contact, "3", 0, mGroup_id, 0, 0, 0, 0, 0);
                        }
                    }
                } else if (call.equalsIgnoreCase("request")) {
                    /*Request to add contact*/
                    mApiCall.requestToAddMember(mGroup_id, allcontacts);
                    String[] parts = allcontacts.split(",");
                    for (int i = 0; i < parts.length; i++) {
                        receiver_contact = parts[i];
                        if (!receiver_contact.equalsIgnoreCase(mContact)) {
                          //  mApiCall.Like(mContact, receiver_contact, "3", 0, mGroup_id, 0, 0, 0, 0, 0);
                        }
                    }
                }
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CntctListadapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return mGcontact;
    }


    @Override
    public void notifySuccess(Response<?> response) {
        /*if (response != null) {
            cntlist.clear();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};

            GetRegisteredContactsResponse mGetRegisteredContactsResponse = (GetRegisteredContactsResponse) response.body();
            for (GetRegisteredContactsResponse.Success contactRegistered : mGetRegisteredContactsResponse.getSuccess()) {
//                contactRegistered.setContact(contactRegistered.getContact());
//                contactRegistered.setUsername(contactRegistered.getUsername());
                String contact = contactRegistered.getContact();
                if (contact.length() > 10)
                    contact = contact.substring(contact.length() - 10);

                Cursor people = getActivity().getContentResolver().query(uri, projection, null, null, null);
                if (people != null) {
                    int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    people.moveToFirst();
                    do {
                        if (people.getCount() != 0) {
                            String name = people.getString(indexName);
                            String number = people.getString(indexNumber);
                            number = number.replaceAll("-", "").replace("(", "").replace(")", "").replaceAll(" ", "");
                            if (number.length() > 10)
                                number = number.substring(number.length() - 10);
                            if (contact.equalsIgnoreCase(number) && !contact.equals(mContact) && !alreadyMemberList.contains(number)) {
                                contactRegistered.setContact(number);
                                contactRegistered.setUsername(name);
                                cntlist.add(contactRegistered);
                            }
                        }
                    } while (people.moveToNext());
                    people.close();
                }
            }
            if (cntlist.size() != 0) {
                CntctListadapter = new GroupContactListAdapter(getActivity(), cntlist,mBtnAddContacts);
                lv.setAdapter(CntctListadapter);
                CntctListadapter.notifyDataSetChanged();
            } else {
                if (isAdded())
                    CustomToast.customToast(getActivity(), "No Contacts Found For Add");
            }
        }*/
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            if (isAdded())
            CustomToast.customToast(getActivity(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-"
                    , "groupcontact");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("success")) {
                if (isAdded()) {
                    CustomToast.customToast(getActivity(), "Contact Added");
                /*Intent intent = new Intent(getActivity(), GroupsActivity.class);
                intent.putExtra("grouptype", "MyGroup");
                intent.putExtra("className", "GroupContactFragment");
                intent.putExtra("bundle_GroupId", mGroup_id);
                getActivity().startActivity(intent);*/
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }else   if (str.equalsIgnoreCase("sent_request")) {
                if(isAdded())
                    CustomToast.customToast(getActivity(), "Request to Add Contact Sent");
                    getActivity().getSupportFragmentManager().popBackStack();
            } else {
                if (isAdded())
                CustomToast.customToast(getActivity(), "Error");
            }
        }
    }
}
