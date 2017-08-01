package autokatta.com.register;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import autokatta.com.Registration.SkillsBasedInvitation;
import autokatta.com.adapter.CompanyBasedInvitationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.GetContactByCompanyResponse;
import retrofit2.Response;

public class InvitationCompanyBased extends AppCompatActivity implements RequestNotifier {

    private ListView lv;
    EditText inputSearch;
    String page = "1";
    List<GetContactByCompanyResponse.Success> invitationDataArrayList = new ArrayList<>();
    CompanyBasedInvitationAdapter adapter;
    Button Next;
    TextView mNoData;

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
        mNoData = (TextView) findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        ApiCall mApiCall = new ApiCall(InvitationCompanyBased.this, this);
        mApiCall.getContactByCompany(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""),page);


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

            if (!mgetContactByCompanyResponse.getSuccess().isEmpty()) {
                invitationDataArrayList.clear();
                mNoData.setVisibility(View.GONE);
                for (GetContactByCompanyResponse.Success contactbycompany : mgetContactByCompanyResponse.getSuccess()) {
                    contactbycompany.setContact(contactbycompany.getContact());
                    contactbycompany.setUsername(contactbycompany.getUsername());
                    contactbycompany.setProfilePic(contactbycompany.getProfilePic());
                    invitationDataArrayList.add(contactbycompany);
                }
                adapter = new CompanyBasedInvitationAdapter(InvitationCompanyBased.this, invitationDataArrayList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();


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

            } else {
                mNoData.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string._404_));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_response));
        } else if (error instanceof ConnectException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else if (error instanceof UnknownHostException) {
            CustomToast.customToast(getApplicationContext(), getString(R.string.no_internet));
        } else {
            Log.i("Check Class-", "InvitationCompanyBased Activity");
            error.printStackTrace();
        }
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
