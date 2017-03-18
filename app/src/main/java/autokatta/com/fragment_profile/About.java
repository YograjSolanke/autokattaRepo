package autokatta.com.fragment_profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import autokatta.com.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 18/3/17.
 */

public class About extends Fragment {

    View mAbout;
    Bundle bundle;
    SharedPreferences mSharedPreferences = null;
    TextView txtContact, txtProfession, txtEmail, txtWebsite, txtCity, txtCompany, txtDesignation, txtSkills;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAbout = inflater.inflate(R.layout.fragment_profile_about, container, false);

        txtContact = (TextView) mAbout.findViewById(R.id.contact_no);
        txtProfession = (TextView) mAbout.findViewById(R.id.worked_at);
        txtEmail = (TextView) mAbout.findViewById(R.id.email);
        txtWebsite = (TextView) mAbout.findViewById(R.id.website);
        txtCity = (TextView) mAbout.findViewById(R.id.address);
        txtCompany = (TextView) mAbout.findViewById(R.id.company_name);
        txtDesignation = (TextView) mAbout.findViewById(R.id.designation);
        txtSkills = (TextView) mAbout.findViewById(R.id.skills);


        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE);
        String contact = mSharedPreferences.getString("user_contact", "");
        String profession = mSharedPreferences.getString("user_profession", "");
        String email = mSharedPreferences.getString("user_email", "");
        String website = mSharedPreferences.getString("user_website", "");
        String city = mSharedPreferences.getString("user_city", "");
        String company = mSharedPreferences.getString("user_company", "");
        String designation = mSharedPreferences.getString("user_designation", "");
        String skills = mSharedPreferences.getString("user_skills", "");


        txtContact.setText(contact);
        txtProfession.setText(profession);
        txtEmail.setText(email);
        txtWebsite.setText(website);
        txtCity.setText(city);
        txtCompany.setText(company);
        txtDesignation.setText(designation);
        txtSkills.setText(skills);


        Log.i("contact in ", "About=>" + contact);
        return mAbout;
    }
}
