package autokatta.com.groups;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.GroupContactListAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetRegisteredContactsResponse;
import autokatta.com.view.GroupsActivity;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-005 on 20/4/17.
 */

public class GroupContactFragment extends Fragment implements RequestNotifier {
    View mGcontact;
    String mContact, mGroup_id, call;
    ListView lv;
    ApiCall mApiCall;
    String receiver_contact;
    GroupContactListAdapter CntctListadapter;
    Bundle args = new Bundle();

    public static Button AddContacts;
    EditText inputSearch;
    List<String> clist = new ArrayList<>();
    List<GetRegisteredContactsResponse.Success> cntlist = new ArrayList<>();

    boolean flag = true;
    String allcontacts = "";

    public GroupContactFragment() {
        //empty constructor...
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGcontact = inflater.inflate(R.layout.group_contact_list, container, false);

        AddContacts = (Button) mGcontact.findViewById(R.id.add_contacts);
        inputSearch = (EditText) mGcontact.findViewById(R.id.inputSearch);
        lv = (ListView) mGcontact.findViewById(R.id.list_view);

        mContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        mApiCall = new ApiCall(getActivity(), this);
        args = getArguments();
        mApiCall.getRegisteredContacts();

        mGroup_id = args.getString("bundle_GroupId", "");
        call = args.getString("call", "");
        AddContacts.setEnabled(false);

        AddContacts.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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

                if (allcontacts.equalsIgnoreCase("")) {
                    Snackbar.make(v,
                            "Please add atleast one contact",
                            Snackbar.LENGTH_SHORT).show();
                    flag = false;

                } else if (allcontacts.contains(mContact)) {
                    Snackbar.make(v,
                            "Please check the number",
                            Snackbar.LENGTH_SHORT).show();
                    flag = false;
                }

                //Existing Group
                else if (call.equalsIgnoreCase("existGroup")) {
                    //For Group Which Doesn't Have Any Contact No In WebService
                    if (cntlist.size() == 0) {
                        flag = true;
                    }

                    //If Group Already Contains Selected Contact
                    else {
                        for (int i = 0; i < GetList.size(); i++) {
                            String no = GetList.get(i);
                            String[] parts = allcontacts.split(",");
                            for (int j = 0; j < parts.length; j++) {
                                if (parts[j].contains(no)) {
                                    Snackbar.make(getView(),
                                            "Sorry..No Is Already added in Group",
                                            Snackbar.LENGTH_SHORT).show();
                                    flag = false;
                                }
                            }
                        }
                    }
                }
                if (!flag) {

                    Intent intent = new Intent(getActivity(), GroupsActivity.class);
                    intent.putExtra("grouptype", "MyGroup");
                    intent.putExtra("className", "GroupContactFragment");
                    intent.putExtra("bundle_GroupId", mGroup_id);
                    getActivity().startActivity(intent);

                } else if (flag) {
                    if (call.equalsIgnoreCase("newGroup")) {
                        allcontacts = allcontacts + "," + mContact;
                    }
                    mApiCall.addContactInGroup(mGroup_id, allcontacts);
                    String[] parts = allcontacts.split(",");

                    for (int i = 0; i < parts.length; i++) {
                        receiver_contact = parts[i];
                        if (!receiver_contact.equalsIgnoreCase(mContact)) {
                            // mApiCall.groupLikeNotification(pass_id, mContact, receiver_contact, "3");
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
        if (response != null) {
            cntlist.clear();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};

            Cursor people = getActivity().getContentResolver().query(uri, projection, null, null, null);
            int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            GetRegisteredContactsResponse mGetRegisteredContactsResponse = (GetRegisteredContactsResponse) response.body();
            for (GetRegisteredContactsResponse.Success contactRegistered : mGetRegisteredContactsResponse.getSuccess()) {
                contactRegistered.setContact(contactRegistered.getContact());
                contactRegistered.setUsername(contactRegistered.getUsername());
                String contact = contactRegistered.getContact();
                if (contact.length() > 10)
                    contact = contact.substring(contact.length() - 10);

                people.moveToFirst();
                do {
                    String name = people.getString(indexName);
                    String number = people.getString(indexNumber);

                    number = number.replaceAll("-", "").replace("(", "").replace(")", "").replaceAll(" ", "");

                    if (number.length() > 10)
                        number = number.substring(number.length() - 10);

                    if (contact.equalsIgnoreCase(number) && !contact.equals(mContact)) {
                        contactRegistered.setContact(number);
                        contactRegistered.setUsername(name);
                        cntlist.add(contactRegistered);
                    }
                } while (people.moveToNext());
            }
            CntctListadapter = new GroupContactListAdapter(getActivity(), cntlist);
            lv.setAdapter(CntctListadapter);
            CntctListadapter.notifyDataSetChanged();

        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(getView(), getString(R.string._404_), Snackbar.LENGTH_SHORT).show();
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
            Log.i("Check Class-", "Group Contact Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("success")) {
                CustomToast.customToast(getActivity(), "Contact Added Successfully");
                Intent intent = new Intent(getActivity(), GroupsActivity.class);
                intent.putExtra("grouptype", "MyGroup");
                intent.putExtra("className", "GroupContactFragment");
                intent.putExtra("bundle_GroupId", mGroup_id);
                getActivity().startActivity(intent);
            } else {
                CustomToast.customToast(getActivity(), "Error");
            }
        }

    }
}
