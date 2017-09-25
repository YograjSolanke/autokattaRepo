package autokatta.com.fragment_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import autokatta.com.R;
import autokatta.com.apicall.ApiCall;
import autokatta.com.interfaces.RequestNotifier;
import autokatta.com.other.AddTags;
import autokatta.com.other.CustomToast;
import autokatta.com.response.ProfileAboutResponse;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static autokatta.com.R.string.city;

/**
 * Created by ak-005 on 25/9/17.
 */

public class OtherAbout extends Fragment implements RequestNotifier{

    View mOtherProfile;
    boolean _hasLoadedOnce = false;
    LinearLayout mUsertypelay,mIndustrylay,mBrandlay;
    TextView mIntrest,mUsertype,mIndust,mBrand,mCompany,address,mDesignation,skill,about,website,mEmail;
    String profstr,brandstr,industr,intresttxt, emailstr,abouttxt, companystr, designationstr, subProfessionstr, websitestr, citystr, skillstr, intereststr;

    String mOthercontact,mAction;
    ApiCall mApicall;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     mOtherProfile=inflater.inflate(R.layout.other_profile_about,container,false);

        return mOtherProfile;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser && !_hasLoadedOnce) {
                Bundle b=getArguments();
                if (b!=null)
                {
                    mOthercontact=b.getString("otherContact");
                    mAction=b.getString("action");
                    mApicall.profileAbout(mOthercontact,getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""));
                }
                _hasLoadedOnce = true;
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApicall=new ApiCall(getActivity(),this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsertype= (TextView) mOtherProfile.findViewById(R.id.usertypetxt);
                mIndust= (TextView) mOtherProfile.findViewById(R.id.industrytxt);
                mBrand= (TextView) mOtherProfile.findViewById(R.id.brandtxt);
                mCompany= (TextView) mOtherProfile.findViewById(R.id.company_name);
                address= (TextView) mOtherProfile.findViewById(R.id.address);
                mDesignation= (TextView) mOtherProfile.findViewById(R.id.designation);
                skill= (TextView) mOtherProfile.findViewById(R.id.skills);
                about= (TextView) mOtherProfile.findViewById(R.id.about);
                website= (TextView) mOtherProfile.findViewById(R.id.website);
                mEmail= (TextView) mOtherProfile.findViewById(R.id.email);
                mUsertypelay = (LinearLayout) mOtherProfile.findViewById(R.id.llusertype);
                mIndustrylay = (LinearLayout) mOtherProfile.findViewById(R.id.llindustry);
                mIntrest = (TextView) mOtherProfile.findViewById(R.id.count);
             //  intresttxt = (LinearLayout) mOtherProfile.findViewById(R.id.llcategory);
                mBrandlay = (LinearLayout) mOtherProfile.findViewById(R.id.llbrand);

                Bundle b=getArguments();
                if (b!=null){
                    mOthercontact=b.getString("otherContact");
                    mAction=b.getString("action");
                    mApicall.profileAbout(mOthercontact,getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                            .getString("loginContact", ""));

                  //  mApicall.profileAbout(mOthercontact, mOthercontact);
                }
                mIntrest.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), AddTags.class);
                        intent.putExtra("interest", intresttxt);
                        intent.putExtra("action", mAction);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public void notifySuccess(Response<?> response) {
        if (response != null) {
            if (response.isSuccessful()) {
                if (response.body() instanceof ProfileAboutResponse) {
                    ProfileAboutResponse mProfileAboutResponse = (ProfileAboutResponse) response.body();
                    if (!mProfileAboutResponse.getSuccess().isEmpty()) {
                         emailstr = mProfileAboutResponse.getSuccess().get(0).getEmail();
                        profstr = mProfileAboutResponse.getSuccess().get(0).getProfession();
                        companystr = mProfileAboutResponse.getSuccess().get(0).getCompanyName();
                        designationstr = mProfileAboutResponse.getSuccess().get(0).getDesignation();
                        subProfessionstr = mProfileAboutResponse.getSuccess().get(0).getSubProfession();
                        websitestr = mProfileAboutResponse.getSuccess().get(0).getWebsite();
                        citystr = mProfileAboutResponse.getSuccess().get(0).getCity();
                        skillstr = mProfileAboutResponse.getSuccess().get(0).getSkills();
                        intereststr = mProfileAboutResponse.getSuccess().get(0).getInterests();
                        brandstr = mProfileAboutResponse.getSuccess().get(0).getBrandName();
                        industr = mProfileAboutResponse.getSuccess().get(0).getIndustry();
                        intresttxt=mProfileAboutResponse.getSuccess().get(0).getInterests();
                        abouttxt=mProfileAboutResponse.getSuccess().get(0).getAbout();

                        mEmail.setText(emailstr);
                        about.setText(abouttxt);
                        website.setText(websitestr);
                        address.setText(city);
                        // mCity.setFocusable(false);
                        mCompany.setText(companystr);
                        mDesignation.setText(designationstr);
                        skill.setText(skillstr);

                        mUsertype.setText(profstr);

                        mIntrest.setText("Click To View Intrest");
                        mIndust.setText(mProfileAboutResponse.getSuccess().get(0).getIndustry());

                        if (profstr.equalsIgnoreCase("Student")) {
                            mBrandlay.setVisibility(View.GONE);
                            mIndustrylay.setVisibility(View.GONE);
                        //    mCategorylay.setVisibility(View.GONE);
                        }

                        if (!mProfileAboutResponse.getSuccess().get(0).getIndustry().equalsIgnoreCase("Automotive")) {
                            mBrandlay.setVisibility(View.GONE);
                        //    mCategorylay.setVisibility(View.GONE);
                            mIndust.setText(mProfileAboutResponse.getSuccess().get(0).getIndustry());
                        } else {
                          //  mCategorylay.setVisibility(View.GONE);
                            mIndust.setText(mProfileAboutResponse.getSuccess().get(0).getIndustry() + "-" + mProfileAboutResponse.getSuccess().get(0).getSubProfession());
                        }
                        if (!mProfileAboutResponse.getSuccess().get(0).getSubProfession().startsWith("New vehicle") || !mProfileAboutResponse.getSuccess().get(0).getSubProfession().startsWith("Used vehicle")) {
                            mBrandlay.setVisibility(View.GONE);
                        } else {
                            mBrandlay.setVisibility(View.VISIBLE);
                            mBrand.setText(mProfileAboutResponse.getSuccess().get(0).getBrandName());
                        }
                        if (citystr.equalsIgnoreCase("")||citystr.equalsIgnoreCase(null))
                        {
                            address.setText("NA");
                        } if (companystr.equalsIgnoreCase("")||companystr.equalsIgnoreCase(null))
                        {
                            mCompany.setText("NA");
                        } if (designationstr.equalsIgnoreCase("")||designationstr.equalsIgnoreCase(null))
                        {
                            mDesignation.setText("NA");
                        } if (skillstr.equalsIgnoreCase("")||skillstr.equalsIgnoreCase(null))
                        {
                            skill.setText("NA");
                        } if (abouttxt.equalsIgnoreCase("")||abouttxt.equalsIgnoreCase(null))
                        {
                            about.setText("NA");
                        } if (websitestr.equalsIgnoreCase("")||websitestr.equalsIgnoreCase(null))
                        {
                            website.setText("NA");
                        } if (emailstr.equalsIgnoreCase("")||emailstr.equalsIgnoreCase(null))
                        {
                            mEmail.setText("NA");
                        }
                    } else {
                        if (isAdded())
                            CustomToast.customToast(getActivity(), getString(R.string.no_response));
                    }
                }else {
                    if (isAdded())
                        CustomToast.customToast(getActivity(), getString(R.string.no_response));
                }
            }else {
                if (isAdded())
                    CustomToast.customToast(getActivity(), getString(R.string.no_response));
            }
        }else {
            if (isAdded())
                CustomToast.customToast(getActivity(), getString(R.string.no_response));
        }
    }

    @Override
    public void notifyError(Throwable error) {

    }

    @Override
    public void notifyString(String str) {

    }
}
