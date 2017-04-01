package autokatta.com.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import autokatta.com.AutokattaApplication;
import autokatta.com.R;
import autokatta.com.adapter.InviteContactAdapter;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.response.Db_AutokattaContactResponse;

/**
 * Created by ak-003 on 19/3/17.
 */

public class InviteContactFragment extends Fragment {

    RecyclerView mRecyclerView;
    EditText edtSearchContact;
    View mInviteContact;
    ArrayList<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    List<String> finalContacts;
    List<String> names = new ArrayList<>();
    List<String> numbers = new ArrayList<>();
    AutokattaApplication autokattaApplication;
    InviteContactAdapter inviteContactAdapter;
    String myContact;
    String[] products;


    public InviteContactFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInviteContact = inflater.inflate(R.layout.fragment_invite_contacts, container, false);

        edtSearchContact = (EditText) mInviteContact.findViewById(R.id.inputSearch);
        mRecyclerView = (RecyclerView) mInviteContact.findViewById(R.id.rv_recycler_view);

        autokattaApplication = (AutokattaApplication) getActivity().getApplicationContext();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "7841023392");
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = getActivity().getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        people.moveToFirst();
        do {
            String name = people.getString(indexName);
            String number = people.getString(indexNumber);

            number = number.replaceAll("-", "");
            number = number.replace("(", "").replace(")", "").replaceAll(" ", "");

            if (number.length() > 10)
                number = number.substring(number.length() - 10);

            names.add(name + "=" + number);
            numbers.add(number);

        } while (people.moveToNext());

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
                obj.setMystatus(cursor.getString(cursor.getColumnIndex(DbConstants.myStatus)));
                obj.setFollowstatus(cursor.getString(cursor.getColumnIndex(DbConstants.followStatus)));
                obj.setUserprofile(cursor.getString(cursor.getColumnIndex(DbConstants.profilePic)));

                contactdata.add(obj);
            } while (cursor.moveToNext());
        }
        dbAdpter.CLOSE();

        finalContacts = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (!contactdata.contains(numbers.get(i)) && !myContact.equals(numbers.get(i))) {

                finalContacts.add(names.get(i));
            }
        }
        Log.i("contact b4", "->" + finalContacts);


        /*products = new String[finalContacts.size()];
        products = finalContacts.toArray(products);*/

        Collections.sort(finalContacts);
        inviteContactAdapter = new InviteContactAdapter(getActivity(), finalContacts);
        mRecyclerView.setAdapter(inviteContactAdapter);
        inviteContactAdapter.notifyDataSetChanged();


        edtSearchContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inviteContactAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return mInviteContact;
    }
}
