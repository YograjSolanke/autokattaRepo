package autokatta.com.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStream;
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
    Button invite,skip;

    InputStream is;
    String result;
    ArrayList<String> names = new ArrayList<String>();
    List<String> numbers = new ArrayList<String>();

    InviteFriendsAdapter adapter;
    String products[] ;

    ArrayList<Success> webcontact;
    ArrayList<String> finalcontacts;

    ApiCall mApiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);

        mApiCall=new ApiCall(this, this);

        invite=(Button)findViewById(R.id.invite);
        skip=(Button)findViewById(R.id.skip);
        lv = (ListView)findViewById(R.id.l1);
        inputSearch = (EditText)findViewById(R.id.inputSearch);

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
                Intent i = new Intent(getApplicationContext(), AutokattaMainActivity.class);
                ///i.putExtra("from","invite");
                startActivity(i);
                //Invitefriends.this.finish();
            }
        });


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "]");

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
            webcontact=new ArrayList<>();
            finalcontacts=new ArrayList<>();

            GetRegisteredContactsResponse mGetRegisteredContactsResponse = (GetRegisteredContactsResponse) response.body();

            for (GetRegisteredContactsResponse.Success contactRegistered : mGetRegisteredContactsResponse.getSuccess()) {
                contactRegistered.setContact(contactRegistered.getContact());
                contactRegistered.setUsername(contactRegistered.getUsername());


                webcontact.add(contactRegistered);

            }
            System.out.println("data size"+webcontact.size());
            adapter = new InviteFriendsAdapter(InviteFriends.this, webcontact);
            lv.setAdapter( adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }



    public void onBackPressed()
    {
       Intent i=new Intent(getApplicationContext(),CompanyBasedInvitation.class);
        startActivity(i);
        finish();
        }

}

