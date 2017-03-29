package autokatta.com.Registration;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    String Contact="7841023392";

    Button Next;

    SharedPreferences prefs ;
  //  public static final String MyProfilePREFERENCES = "contact No" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_based_invitation);

        mApiCall=new ApiCall(this, this);


        lv = (ListView)findViewById(R.id.l1);
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        Next = (Button)findViewById(R.id.next);



            //***** Shared Preference For Contact
        //    prefs = getApplicationContext().getSharedPreferences(MyProfilePREFERENCES, Context.MODE_PRIVATE);
           // Contact=prefs.getString("contact", "");
            System.out.println("Contact is ::"+Contact);
            /////////

             mApiCall.getContactByCompany(page,Contact);
              //  getcontactsbyCompany();
                //getContactsbyCompany();


            inputSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    System.out.println("Text [" + s + "]");

                   // adapter.getFilter().filter(s.toString());
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
                    /*SkillsBasedInvitation fr = new SkillsBasedInvitation();
                    mFragmentManager = getActivity().getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fr).addToBackStack("skillsbasedinvitation").commit();*/
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
            System.out.println("data size"+invitationDataArrayList.size());
            adapter = new CompanyBasedInvitationAdapter(CompanyBasedInvitation.this, invitationDataArrayList);
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
}
