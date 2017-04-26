package autokatta.com.groups;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    String mContact, mGroup_id, grp_id, call, grp1_id, pass_id;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGcontact = inflater.inflate(R.layout.group_contact_list, container, false);

        AddContacts = (Button) mGcontact.findViewById(R.id.add_contacts);
        inputSearch = (EditText) mGcontact.findViewById(R.id.inputSearch);
        lv = (ListView) mGcontact.findViewById(R.id.list_view);

        mContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");
        mGroup_id = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("group_id", "");
        mApiCall = new ApiCall(getActivity(), this);


        System.out.println("----------------->GroupID" + mGroup_id);


        args = getArguments();
        mApiCall.getRegisteredContacts();

        grp_id = args.getString("GrpId", "");
        System.out.println("Group Id From Create Fragment Bundle In Group Contact Fragment:" + grp_id);

        call = args.getString("call", "");

        grp1_id = args.getString("id", "");
        System.out.println("Group Id From Grp Member List Bundle In Group Contct Frag:" + grp1_id);


        if (grp_id == "") {
            pass_id = grp1_id;
        } else if (grp1_id == "") {
            pass_id = grp_id;
        }

        System.out.println("----------------->grp_id" + grp_id);
        System.out.println("----------------->grp_id1" + grp1_id);

        AddContacts.setEnabled(false);

        AddContacts.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                List<String> GetList = args.getStringArrayList("list");
                System.out.println("List From Web Service: " + GetList);

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
/*
                                    Snackbar.make(v,
                                            "Sorry..No Is Already added in Group",
                                            Snackbar.LENGTH_LONG).show();*/
                                    CustomToast.customToast(getActivity(), "Sorry..No Is Already added in Group");
                                    flag = false;
                                }
                            }
                        }
                    }
                }
                if (!flag) {

                    /*GroupNextTabFragment groupMyJoined=new GroupNextTabFragment();
                    args.putString("id",pass_id);
                    args.putString("call", "groupContact");
                    args.putString("grouptype", "groups");
                    groupMyJoined.setArguments(args);

                    System.out.println("Back To Fragment From Group Contact Fragment:\n");
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.group_container, groupMyJoined);
                    fragmentTransaction.commit();*/

                    Intent intent = new Intent(getActivity(), GroupsActivity.class);
                    intent.putExtra("grouptype", "MyGroup");
                    intent.putExtra("className", "GroupContactFragment");
                    intent.putExtra("bundle_GroupId", pass_id);
                    getActivity().startActivity(intent);
                    getActivity().finish();


                } else if (flag) {
                    if (call.equalsIgnoreCase("newGroup")) {
                        allcontacts = allcontacts + "," + mContact;
                    }

                    mApiCall.addContactInGroup(pass_id, allcontacts);
                    String[] parts = allcontacts.split(",");

                    for (int i = 0; i < parts.length; i++) {

                        receiver_contact = parts[i];
                        if (!receiver_contact.equalsIgnoreCase(mContact)) {
                            // mApiCall.groupLikeNotification(pass_id, mContact, receiver_contact, "3");
                        }
                    }

                    /*GroupNextTabFragment groupMyJoined=new GroupNextTabFragment();
                    args.putString("id",pass_id);
                    args.putString("call", "groupContact");
                    args.putString("grouptype", "groups");
                    groupMyJoined.setArguments(args);

                    System.out.println("Back To Fragment From Group Contact Fragment:\n");
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.group_container, groupMyJoined);
                    fragmentTransaction.commit();*/


                    System.out.println("All contacts ::" + allcontacts);
                }
            }


        });


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "]");

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
                        /*names.add(name + "-" + number);
                        numbers.add(number);*/
                        contactRegistered.setContact(number);
                        contactRegistered.setUsername(name);
                        cntlist.add(contactRegistered);
                    }

                } while (people.moveToNext());

                //cntlist.add(contactRegistered);
            }

            CntctListadapter = new GroupContactListAdapter(getActivity(), cntlist);
            lv.setAdapter(CntctListadapter);
            CntctListadapter.notifyDataSetChanged();

        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {
        if (str != null) {
            if (str.startsWith("success_add_contact")) {
                CustomToast.customToast(getActivity(), "Contact Added Successfully");
                Intent intent = new Intent(getActivity(), GroupsActivity.class);
                intent.putExtra("grouptype", "MyGroup");
                intent.putExtra("className", "GroupContactFragment");
                intent.putExtra("bundle_GroupId", pass_id);
                getActivity().startActivity(intent);
                getActivity().finish();
            } else {
                CustomToast.customToast(getActivity(), "Error");
            }
        }

    }
}
