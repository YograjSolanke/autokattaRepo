package autokatta.com.Registration;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import autokatta.com.R;
import autokatta.com.adapter.CompanyBasedInvitationAdapter;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.response.GetContactByCompanyResponse;
import autokatta.com.response.GetContactByCompanyResponse.Success;
import retrofit2.Response;

public class SkillsBasedInvitation extends AppCompatActivity implements RequestNotifier {

    private ListView lv;
    EditText inputSearch;
    String page = "2";
    List<Success> invitationDataArrayList = new ArrayList<>();
    CompanyBasedInvitationAdapter adapter;
    Button Next;
    RelativeLayout mSkillRelative;
    TextView mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_based_invitation);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lv = (ListView) findViewById(R.id.l1);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        Next = (Button) findViewById(R.id.next);
        mSkillRelative = (RelativeLayout) findViewById(R.id.skill_based);
        mNoData = (TextView) findViewById(R.id.no_category);
        mNoData.setVisibility(View.GONE);

        ApiCall mApiCall = new ApiCall(this, this);
        mApiCall.getContactByCompany(getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", ""),page);

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
                Intent i = new Intent(getApplicationContext(), InviteFriends.class);
                startActivity(i, options.toBundle());
            }
        });

    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response.isSuccessful()) {
            GetContactByCompanyResponse mgetContactByCompanyResponse = (GetContactByCompanyResponse) response.body();
            if (!mgetContactByCompanyResponse.getSuccess().isEmpty()) {
                mNoData.setVisibility(View.GONE);
                for (GetContactByCompanyResponse.Success contactbycompany : mgetContactByCompanyResponse.getSuccess()) {
                    contactbycompany.setContact(contactbycompany.getContact());
                    contactbycompany.setUsername(contactbycompany.getUsername());
                    contactbycompany.setProfilePic(contactbycompany.getProfilePic());
                    invitationDataArrayList.add(contactbycompany);
                }

                adapter = new CompanyBasedInvitationAdapter(SkillsBasedInvitation.this, invitationDataArrayList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else
                mNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mSkillRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mSkillRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mSkillRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mSkillRelative, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            Snackbar snackbar = Snackbar.make(mSkillRelative, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            Log.i("Check Class-", "SkillsBasedInvitation Activity");
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
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(SkillsBasedInvitation.this, R.anim.pull_in_left, R.anim.push_out_right);
            startActivity(new Intent(getApplicationContext(), InvitationCompanyBased.class), options.toBundle());
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), InvitationCompanyBased.class));
            finish();
        }*/
    }
}

