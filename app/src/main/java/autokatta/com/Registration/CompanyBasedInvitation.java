package autokatta.com.Registration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

public class CompanyBasedInvitation extends AppCompatActivity implements RequestNotifier {
    private ListView lv;
    EditText inputSearch;
    ApiCall mApiCall;
    String page="1";
    List<Success> invitationDataArrayList = new ArrayList<>();
    CompanyBasedInvitationAdapter adapter;
    RelativeLayout mRelative;
    Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_based_invitation);
        mApiCall=new ApiCall(this, this);
        lv = (ListView)findViewById(R.id.l1);
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        mRelative = (RelativeLayout) findViewById(R.id.company_base);
        Next = (Button)findViewById(R.id.next);
        mApiCall.getContactByCompany(page, getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", null));

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
                public void onClick(View v)
                {
                    Intent i =new Intent(getApplicationContext(),SkillsBasedInvitation.class);
                    startActivity(i);
                    finish();
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
            adapter = new CompanyBasedInvitationAdapter(CompanyBasedInvitation.this, invitationDataArrayList);
            lv.setAdapter( adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof NullPointerException) {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ClassCastException) {
            Snackbar.make(mRelative, getString(R.string.no_response), Snackbar.LENGTH_SHORT).show();
        } else if (error instanceof ConnectException) {
            //mNoInternetIcon.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(mRelative, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            Snackbar snackbar = Snackbar.make(mRelative, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
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
            Log.i("Check Class-", "Add more Admins Store Fragment");
            error.printStackTrace();
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
