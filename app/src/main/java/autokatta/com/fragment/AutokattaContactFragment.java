package autokatta.com.fragment;

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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.AutokattaContactAdapter;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;
import autokatta.com.response.Db_AutokattaContactResponse;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-003 on 19/3/17
 */

public class AutokattaContactFragment extends Fragment {
    View mAutoContact;
    RecyclerView mRecyclerView;
    EditText edtSearchContact;
    String myContact;
    List<Db_AutokattaContactResponse> contactdata = new ArrayList<>();
    AutokattaContactAdapter autokattaContactAdapter;

    public AutokattaContactFragment() {
        //Empty Constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAutoContact = inflater.inflate(R.layout.fragment_autokatta_contacts, container, false);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myContact = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                        .getString("loginContact", "");
                edtSearchContact = (EditText) mAutoContact.findViewById(R.id.inputSearch);
                mRecyclerView = (RecyclerView) mAutoContact.findViewById(R.id.rv_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                DbOperation dbAdpter = new DbOperation(getActivity());
                dbAdpter.OPEN();
                Cursor cursor = dbAdpter.getAutokattaContact();
                if (cursor.getCount() > 0) {
                    contactdata.clear();
                    cursor.moveToFirst();
                    do {
                        Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(DbConstants.userName)) + " = " + cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                        Db_AutokattaContactResponse obj = new Db_AutokattaContactResponse();
                        if (!cursor.getString(cursor.getColumnIndex(DbConstants.contact)).equals(myContact)) {
                            obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
                            obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
                            obj.setMystatus(cursor.getString(cursor.getColumnIndex(DbConstants.myStatus)));
                            obj.setFollowstatus(cursor.getString(cursor.getColumnIndex(DbConstants.followStatus)));
                            obj.setUserprofile(cursor.getString(cursor.getColumnIndex(DbConstants.profilePic)));
                            obj.setGroupIds(cursor.getString(cursor.getColumnIndex(DbConstants.groupIds)));
                            obj.setGroupNames(cursor.getString(cursor.getColumnIndex(DbConstants.groupNames)));
                            contactdata.add(obj);
                        }
                    } while (cursor.moveToNext());
                }
                dbAdpter.CLOSE();
                Collections.sort(contactdata, Db_AutokattaContactResponse.StuNameComparator);
                autokattaContactAdapter = new AutokattaContactAdapter(getActivity(), contactdata);
                mRecyclerView.setAdapter(autokattaContactAdapter);
                autokattaContactAdapter.notifyDataSetChanged();

                edtSearchContact.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        autokattaContactAdapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        return mAutoContact;
    }
}
