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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.SocketTimeoutException;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 18/3/17.
 */

public class About extends Fragment implements RequestNotifier{

    View mAbout;
    TextView txtContact, txtProfession, txtEmail, txtWebsite, txtCity, txtCompany, txtDesignation, txtSkills;
    String userName, email, contact, profession, company, designation, subProfession, websitestr, city, skills;

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

        ApiCall mApiCall = new ApiCall(getActivity(),this);
        mApiCall.profileAbout("8007855589");
        return mAbout;
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                if (!mProfileAboutResponse.getSuccess().isEmpty()) {
                    userName = mProfileAboutResponse.getSuccess().get(0).getUsername();
                    email = mProfileAboutResponse.getSuccess().get(0).getEmail();
                    contact = mProfileAboutResponse.getSuccess().get(0).getContact();
                    profession = mProfileAboutResponse.getSuccess().get(0).getProfession();
                    company = mProfileAboutResponse.getSuccess().get(0).getCompanyName();
                    designation = mProfileAboutResponse.getSuccess().get(0).getDesignation();
                    subProfession = mProfileAboutResponse.getSuccess().get(0).getSubProfession();
                    websitestr = mProfileAboutResponse.getSuccess().get(0).getWebsite();
                    city = mProfileAboutResponse.getSuccess().get(0).getCity();
                    skills = mProfileAboutResponse.getSuccess().get(0).getSkills();
                }

                txtContact.setText(contact);
                txtProfession.setText(profession);
                txtEmail.setText(email);
                txtWebsite.setText(websitestr);
                txtCity.setText(city);
                txtCompany.setText(company);
                txtDesignation.setText(designation);
                txtSkills.setText(skills);
            } else {
               CustomToast.customToast(getActivity(), getString(R.string._404));
            }
        } else {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            CustomToast.customToast(getActivity(), getString(R.string._404));
        } else if (error instanceof NullPointerException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else if (error instanceof ClassCastException) {
            CustomToast.customToast(getActivity(), getString(R.string.no_response));
        } else {
            Log.i("Check Class-", "Login Activity");
        }
    }

    @Override
    public void notifyString(String str) {

    }
}
