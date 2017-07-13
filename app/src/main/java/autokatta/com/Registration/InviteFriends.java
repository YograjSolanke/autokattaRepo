package autokatta.com.Registration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.adapter.InviteContactAdapter;
import autokatta.com.database.DbConstants;
import autokatta.com.database.DbOperation;

public class InviteFriends extends AppCompatActivity {


    RecyclerView mRecyclerView;
    EditText edtSearchContact;
    Button mNext;
    List<String> contactdata = new ArrayList<>();
    List<String> finalContacts;
    List<String> names = new ArrayList<>();
    List<String> numbers = new ArrayList<>();
    InviteContactAdapter inviteContactAdapter;
    String myContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invite_contacts);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtSearchContact = (EditText) findViewById(R.id.inputSearch);
        mNext = (Button) findViewById(R.id.next);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);


        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(InviteFriends.this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        myContact = getApplicationContext().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).getString("loginContact", "");
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);

        int indexName, indexNumber;
        if (people != null) {
            indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

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
            people.close();
        }

        DbOperation dbAdpter = new DbOperation(InviteFriends.this);
        dbAdpter.OPEN();
        Cursor cursor = dbAdpter.getAutokattaContact();
        if (cursor.getCount() > 0) {
            contactdata.clear();
            cursor.moveToFirst();
            do {
                Log.i(DbConstants.TAG, cursor.getString(cursor.getColumnIndex(DbConstants.userName)) + " = " + cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
//                Db_AutokattaContactResponse obj = new Db_AutokattaContactResponse();
//
//                obj.setContact(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
//                obj.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.userName)));
//                obj.setMystatus(cursor.getString(cursor.getColumnIndex(DbConstants.myStatus)));
//                obj.setFollowstatus(cursor.getString(cursor.getColumnIndex(DbConstants.followStatus)));
//                obj.setUserprofile(cursor.getString(cursor.getColumnIndex(DbConstants.profilePic)));

                contactdata.add(cursor.getString(cursor.getColumnIndex(DbConstants.contact)));
            } while (cursor.moveToNext());
        }
        dbAdpter.CLOSE();

        finalContacts = new ArrayList<>();
        finalContacts.clear();
        for (int i = 0; i < numbers.size(); i++) {
            if (!contactdata.contains(numbers.get(i)) && !myContact.equals(numbers.get(i))) {

                finalContacts.add(names.get(i));
            }
        }
        Log.i("contact b4", "->" + finalContacts);


        /*products = new String[finalContacts.size()];
        products = finalContacts.toArray(products);*/

        Collections.sort(finalContacts, Collections.<String>reverseOrder());
        inviteContactAdapter = new InviteContactAdapter(InviteFriends.this, finalContacts);
        mRecyclerView.setAdapter(inviteContactAdapter);
        inviteContactAdapter.notifyDataSetChanged();


        edtSearchContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inviteContactAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(InviteFriends.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), SkillsBasedInvitation.class), options.toBundle());
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), SkillsBasedInvitation.class));
            finish();
        }*/
    }

}

