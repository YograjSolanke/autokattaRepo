package autokatta.com.Registration;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;
import autokatta.com.adapter.InviteFriendsAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.GetRegisteredContactsResponse;
import autokatta.com.response.GetRegisteredContactsResponse.Success;
import retrofit2.Response;

public class InviteFriends extends AppCompatActivity implements RequestNotifier {

    private ListView lv;
    EditText inputSearch;
    Button invite, skip;
    InviteFriendsAdapter adapter;
    List<Success> webcontact;
    List<String> finalcontacts;
    TextView mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNoData = (TextView) findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);
        invite = (Button) findViewById(R.id.invite);
        skip = (Button) findViewById(R.id.skip);
        lv = (ListView) findViewById(R.id.l1);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getRegisteredContacts();

        invite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setVisibility(View.VISIBLE);
            }
        });

        skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i, options.toBundle());

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
    }


    @Override
    public void notifySuccess(Response<?> response) {
        if (response.isSuccessful()) {
            webcontact = new ArrayList<>();
            finalcontacts = new ArrayList<>();
            GetRegisteredContactsResponse mGetRegisteredContactsResponse = (GetRegisteredContactsResponse) response.body();
            if (!mGetRegisteredContactsResponse.getSuccess().isEmpty()) {
                webcontact.clear();
                mNoData.setVisibility(View.GONE);
                for (GetRegisteredContactsResponse.Success contactRegistered : mGetRegisteredContactsResponse.getSuccess()) {
                    contactRegistered.setContact(contactRegistered.getContact());
                    contactRegistered.setUsername(contactRegistered.getUsername());
                    webcontact.add(contactRegistered);
                }

                adapter = new InviteFriendsAdapter(InviteFriends.this, webcontact);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                mNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

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

