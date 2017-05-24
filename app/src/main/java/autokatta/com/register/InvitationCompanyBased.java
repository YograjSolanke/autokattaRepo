package autokatta.com.register;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.Registration.SkillsBasedInvitation;
import autokatta.com.adapter.CompanyBasedInvitationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.GetContactByCompanyResponse;
import retrofit2.Response;

public class InvitationCompanyBased extends AppCompatActivity implements RequestNotifier {

    private ListView lv;
    EditText inputSearch;
    String page = "1";
    List<GetContactByCompanyResponse.Success> invitationDataArrayList = new ArrayList<>();
    CompanyBasedInvitationAdapter adapter;
    Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_company_based);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lv = (ListView) findViewById(R.id.l1);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        Next = (Button) findViewById(R.id.next);

        ApiCall mApiCall = new ApiCall(InvitationCompanyBased.this, this);
        mApiCall.getContactByCompany(page, getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""));

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
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.ok_left_to_right, R.anim.ok_right_to_left);
                Intent i = new Intent(getApplicationContext(), SkillsBasedInvitation.class);
                startActivity(i, options.toBundle());
            }
        });
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response.isSuccessful()) {
            GetContactByCompanyResponse mgetContactByCompanyResponse = (GetContactByCompanyResponse) response.body();
            for (GetContactByCompanyResponse.Success contactbycompany : mgetContactByCompanyResponse.getSuccess()) {
                contactbycompany.setContact(contactbycompany.getContact());
                contactbycompany.setUsername(contactbycompany.getUsername());
                contactbycompany.setProfilePic(contactbycompany.getProfilePic());
                invitationDataArrayList.add(contactbycompany);
            }
            adapter = new CompanyBasedInvitationAdapter(InvitationCompanyBased.this, invitationDataArrayList);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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
            ActivityOptions options = ActivityOptions.makeCustomAnimation(InvitationCompanyBased.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), RegistrationCompanyBased.class), options.toBundle());
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), RegistrationCompanyBased.class));
            finish();
        }*/
    }
}
