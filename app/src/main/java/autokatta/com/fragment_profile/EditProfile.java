package autokatta.com.fragment_profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import autokatta.com.R;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    ImageView mAddressClick, mDone;
    AutoCompleteTextView mAddress;
    String mContact, mProfession, mEmail, mWebSite, mCity, mCompany, mDesignation, mSkills, mSubProfession;
    RadioGroup usertype;
    RadioButton student, employee, selfemployee;
    Spinner mSpinner;
    TextView mSpinnerSelected, mWorkedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {

            mContact = getIntent().getExtras().getString("contact");
            mProfession = getIntent().getExtras().getString("profession");
            mEmail = getIntent().getExtras().getString("email");
            mWebSite = getIntent().getExtras().getString("websitestr");
            mCity = getIntent().getExtras().getString("city");
            mCompany = getIntent().getExtras().getString("company");
            mDesignation = getIntent().getExtras().getString("designation");
            mSkills = getIntent().getExtras().getString("skills");
            mSubProfession = getIntent().getExtras().getString("subProfession");

        }

        mAddressClick = (ImageView) findViewById(R.id.address_click);
        mDone = (ImageView) findViewById(R.id.done);
        mAddress = (AutoCompleteTextView) findViewById(R.id.address);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        usertype = (RadioGroup) findViewById(R.id.usertype);
        student = (RadioButton) findViewById(R.id.student);
        employee = (RadioButton) findViewById(R.id.employee);
        selfemployee = (RadioButton) findViewById(R.id.selfemployee);
        mSpinnerSelected = (TextView) findViewById(R.id.spinnerSelected);
        mWorkedAt = (TextView) findViewById(R.id.worked_at);

        mAddressClick.setOnClickListener(this);
        mDone.setOnClickListener(this);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        mAddress.setText(mCity);
        mAddress.setSelection(mAddress.getText().length());
        mSpinnerSelected.setText(mSubProfession);
        mWorkedAt.setText(mProfession);

        if (mProfession.equalsIgnoreCase("student")) {
            student.setChecked(true);
        } else if (mProfession.equalsIgnoreCase("employee")) {
            employee.setChecked(true);
        } else if (mProfession.equalsIgnoreCase("self employee")) {
            selfemployee.setChecked(true);
        }
        if (student.isChecked()) {
            mSpinner.setVisibility(View.GONE);
        } else if (employee.isChecked()) {
            mSpinner.setVisibility(View.VISIBLE);
            mSpinnerSelected.setVisibility(View.VISIBLE);
        } else if (selfemployee.isChecked()) {
            mSpinner.setVisibility(View.VISIBLE);
            mSpinnerSelected.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_click:
                mAddress.setEnabled(true);
                break;

            case R.id.done:
                mAddress.setEnabled(false);
                break;
        }
    }
}
